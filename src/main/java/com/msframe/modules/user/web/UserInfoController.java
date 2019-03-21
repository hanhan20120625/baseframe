/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.cms.entity.CmsProgram;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.msframe.common.utils.DateUtils;
import com.msframe.common.utils.MyBeanUtils;
import com.msframe.common.config.Global;
import com.msframe.common.persistence.Page;
import com.msframe.common.web.BaseController;
import com.msframe.common.utils.StringUtils;
import com.msframe.common.utils.excel.ExportExcel;
import com.msframe.common.utils.excel.ImportExcel;
import com.msframe.modules.user.entity.UserInfo;
import com.msframe.modules.user.service.UserInfoService;

/**
 * 客户管理Controller
 *
 * @author wlh
 * @version 2018-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/user/user")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @ModelAttribute
    public UserInfo get(@RequestParam(required = false) String id) {
        UserInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userInfoService.get(id);
        }
        if (entity == null) {
            entity = new UserInfo();
        }
        return entity;
    }

    /**
     * 客户信息列表页面
     */
    @RequiresPermissions("user:user:list")
    @RequestMapping(value = {"list", ""})
    public String list(UserInfo user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), user);
        model.addAttribute("page", page);
        return "modules/user/userList";
    }

    /**
     * 增加，编辑客户信息表单页面
     */
    @RequiresPermissions(value = {"user:user:add", "user:user:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(UserInfo user, Model model) {
        if(StringUtils.isBlank(user.getId())){
            List<UserInfo> list = userInfoService.findList(new UserInfo());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                user.setSort(list.get(0).getSort() + 10);
            }else{
                user.setSort(10L);
            }
        }
        model.addAttribute("user", user);
        return "modules/user/userForm";
    }

    /**
     * 查看客户信息表单页面
     */
    @RequiresPermissions(value = {"user:user:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(UserInfo user, Model model) {
        model.addAttribute("user", user);
        return "modules/user/userDetails";
    }

    /**
     * 保存客户信息
     */
    @RequiresPermissions(value = {"user:user:add", "user:user:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(UserInfo user, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, user)) {
            return form(user, model);
        }
        if (!user.getIsNewRecord()) {//编辑表单保存
            UserInfo t = userInfoService.get(user.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(user, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            userInfoService.save(t);//保存
        } else {//新增表单保存
            userInfoService.save(user);//保存
        }
        addMessage(redirectAttributes, "保存客户信息成功");
        return "redirect:" + Global.getAdminPath() + "/user/user/?repage";
    }

    /**
     * 删除客户信息
     */
    @RequiresPermissions("user:user:del")
    @RequestMapping(value = "delete")
    public String delete(UserInfo user, RedirectAttributes redirectAttributes) {
        userInfoService.delete(user);
        addMessage(redirectAttributes, "删除客户信息成功");
        return "redirect:" + Global.getAdminPath() + "/user/user/?repage";
    }

    /**
     * 批量删除客户信息
     */
    @RequiresPermissions("user:user:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            userInfoService.delete(userInfoService.get(id));
        }
        addMessage(redirectAttributes, "删除客户信息成功");
        return "redirect:" + Global.getAdminPath() + "/user/user/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("user:user:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(UserInfo user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "客户信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response, -1), user);
            new ExportExcel("客户信息", UserInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出客户信息记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/user/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("user:user:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<UserInfo> list = ei.getDataList(UserInfo.class);
            for (UserInfo user : list) {
                try {
                    userInfoService.save(user);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条客户信息记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条客户信息记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入客户信息失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/user/?repage";
    }

    /**
     * 下载导入客户信息数据模板
     */
    @RequiresPermissions("user:user:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "客户信息数据导入模板.xlsx";
            List<UserInfo> list = Lists.newArrayList();
            new ExportExcel("客户信息数据", UserInfo.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/user/?repage";
    }

    /**
     * @param userInfo
     * @param url
     * @param fieldLabels
     * @param fieldKeys
     * @param searchLabel
     * @param searchKey
     * @param request
     * @param response
     * @param model
     * @return
     * @description 选择客户
     * @author leon
     * @date 2018/11/16
     */
    @RequestMapping(value = "selectUserInfo")
    public String selectoaAssessmentModel(UserInfo userInfo, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), userInfo);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", userInfo);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }

}
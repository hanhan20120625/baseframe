/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.user.entity.UserInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.msframe.modules.user.entity.UserBindStbid;
import com.msframe.modules.user.service.UserBindStbidService;

/**
 * 用户绑定设备Controller
 *
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userBindStbid")
public class UserBindStbidController extends BaseController {

    @Autowired
    private UserBindStbidService userBindStbidService;

    @ModelAttribute
    public UserBindStbid get(@RequestParam(required = false) String id) {
        UserBindStbid entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userBindStbidService.get(id);
        }
        if (entity == null) {
            entity = new UserBindStbid();
        }
        return entity;
    }

    /**
     * 用户绑定设备列表页面
     */
    @RequiresPermissions("user:userBindStbid:list")
    @RequestMapping(value = {"list", ""})
    public String list(UserBindStbid userBindStbid, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserBindStbid> page = userBindStbidService.findPage(new Page<UserBindStbid>(request, response), userBindStbid);
        model.addAttribute("page", page);

        UserInfo userInfo = userBindStbid.getUser();

        if (userInfo != null && userInfo.getId() != null) {
            model.addAttribute("userInfoId", userInfo.getId());
        } else {
            model.addAttribute("userInfoId", "");
        }

        return "modules/user/userBindStbidList";
    }

    /**
     * 增加，编辑用户绑定设备表单页面
     */
    @RequiresPermissions(value = {"user:userBindStbid:add", "user:userBindStbid:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(UserBindStbid userBindStbid, Model model) {
        model.addAttribute("userBindStbid", userBindStbid);
        return "modules/user/userBindStbidForm";
    }

    /**
     * 查看用户绑定设备表单页面
     */
    @RequiresPermissions(value = {"user:userBindStbid:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(UserBindStbid userBindStbid, Model model) {
        model.addAttribute("userBindStbid", userBindStbid);
        return "modules/user/userBindStbidDetails";
    }

    /**
     * 保存用户绑定设备
     */
    @RequiresPermissions(value = {"user:userBindStbid:add", "user:userBindStbid:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(UserBindStbid userBindStbid, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, userBindStbid)) {
            return form(userBindStbid, model);
        }
        if (!userBindStbid.getIsNewRecord()) {//编辑表单保存
            UserBindStbid t = userBindStbidService.get(userBindStbid.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(userBindStbid, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            userBindStbidService.save(t);//保存
        } else {//新增表单保存
            userBindStbidService.save(userBindStbid);//保存
        }
        addMessage(redirectAttributes, "保存用户绑定设备成功");

        String userInfoId = "";
        UserInfo userInfo = userBindStbid.getUser();
        if (userInfo != null && userInfo.getId() != null) {
            userInfoId = userInfo.getId();
        }

        return "redirect:" + Global.getAdminPath() + "/user/userBindStbid/?repage&user=" + userInfoId;
    }

    /**
     * 删除用户绑定设备
     */
    @RequiresPermissions("user:userBindStbid:del")
    @RequestMapping(value = "delete")
    public String delete(UserBindStbid userBindStbid, RedirectAttributes redirectAttributes) {
        userBindStbidService.delete(userBindStbid);
        addMessage(redirectAttributes, "删除用户绑定设备成功");
        return "redirect:" + Global.getAdminPath() + "/user/userBindStbid/?repage";
    }

    /**
     * 批量删除用户绑定设备
     */
    @RequiresPermissions("user:userBindStbid:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            userBindStbidService.delete(userBindStbidService.get(id));
        }
        addMessage(redirectAttributes, "删除用户绑定设备成功");
        return "redirect:" + Global.getAdminPath() + "/user/userBindStbid/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("user:userBindStbid:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(UserBindStbid userBindStbid, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户绑定设备" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<UserBindStbid> page = userBindStbidService.findPage(new Page<UserBindStbid>(request, response, -1), userBindStbid);
            new ExportExcel("用户绑定设备", UserBindStbid.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户绑定设备记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userBindStbid/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("user:userBindStbid:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<UserBindStbid> list = ei.getDataList(UserBindStbid.class);
            for (UserBindStbid userBindStbid : list) {
                try {
                    userBindStbidService.save(userBindStbid);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户绑定设备记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户绑定设备记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户绑定设备失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userBindStbid/?repage";
    }

    /**
     * 下载导入用户绑定设备数据模板
     */
    @RequiresPermissions("user:userBindStbid:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户绑定设备数据导入模板.xlsx";
            List<UserBindStbid> list = Lists.newArrayList();
            new ExportExcel("用户绑定设备数据", UserBindStbid.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userBindStbid/?repage";
    }


}
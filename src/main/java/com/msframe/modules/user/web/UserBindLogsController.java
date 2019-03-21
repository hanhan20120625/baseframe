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
import com.msframe.modules.user.entity.UserBindLogs;
import com.msframe.modules.user.service.UserBindLogsService;

/**
 * 用户绑定iptv信息Controller
 *
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userBindLogs")
public class UserBindLogsController extends BaseController {

    @Autowired
    private UserBindLogsService userBindLogsService;

    @ModelAttribute
    public UserBindLogs get(@RequestParam(required = false) String id) {
        UserBindLogs entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userBindLogsService.get(id);
        }
        if (entity == null) {
            entity = new UserBindLogs();
        }
        return entity;
    }

    /**
     * 用户绑定iptv信息列表页面
     */
    @RequiresPermissions("user:userBindLogs:list")
    @RequestMapping(value = {"list", ""})
    public String list(UserBindLogs userBindLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserBindLogs> page = userBindLogsService.findPage(new Page<UserBindLogs>(request, response), userBindLogs);
        model.addAttribute("page", page);

        UserInfo userInfo = userBindLogs.getUser();

        if (userInfo != null && userInfo.getId() != null) {
            model.addAttribute("userInfoId", userInfo.getId());
        } else {
            model.addAttribute("userInfoId", "");
        }

        return "modules/user/userBindLogsList";
    }

    /**
     * 增加，编辑用户绑定iptv信息表单页面
     */
    @RequiresPermissions(value = {"user:userBindLogs:add", "user:userBindLogs:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(UserBindLogs userBindLogs, Model model) {
        model.addAttribute("userBindLogs", userBindLogs);
        return "modules/user/userBindLogsForm";
    }

    /**
     * 查看用户绑定iptv信息表单页面
     */
    @RequiresPermissions(value = {"user:userBindLogs:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(UserBindLogs userBindLogs, Model model) {
        model.addAttribute("userBindLogs", userBindLogs);
        return "modules/user/userBindLogsDetails";
    }

    /**
     * 保存用户绑定iptv信息
     */
    @RequiresPermissions(value = {"user:userBindLogs:add", "user:userBindLogs:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(UserBindLogs userBindLogs, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, userBindLogs)) {
            return form(userBindLogs, model);
        }
        if (!userBindLogs.getIsNewRecord()) {//编辑表单保存
            UserBindLogs t = userBindLogsService.get(userBindLogs.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(userBindLogs, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            userBindLogsService.save(t);//保存
        } else {//新增表单保存
            userBindLogsService.save(userBindLogs);//保存
        }
        addMessage(redirectAttributes, "保存用户绑定iptv信息成功");

        String userInfoId = "";
        UserInfo userInfo = userBindLogs.getUser();
        if (userInfo != null && userInfo.getId() != null) {
            userInfoId = userInfo.getId();
        }

        return "redirect:" + Global.getAdminPath() + "/user/userBindLogs/?repage&user=" + userInfoId;
    }

    /**
     * 删除用户绑定iptv信息
     */
    @RequiresPermissions("user:userBindLogs:del")
    @RequestMapping(value = "delete")
    public String delete(UserBindLogs userBindLogs, RedirectAttributes redirectAttributes) {
        userBindLogsService.delete(userBindLogs);
        addMessage(redirectAttributes, "删除用户绑定iptv信息成功");
        return "redirect:" + Global.getAdminPath() + "/user/userBindLogs/?repage";
    }

    /**
     * 批量删除用户绑定iptv信息
     */
    @RequiresPermissions("user:userBindLogs:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            userBindLogsService.delete(userBindLogsService.get(id));
        }
        addMessage(redirectAttributes, "删除用户绑定iptv信息成功");
        return "redirect:" + Global.getAdminPath() + "/user/userBindLogs/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("user:userBindLogs:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(UserBindLogs userBindLogs, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户绑定iptv信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<UserBindLogs> page = userBindLogsService.findPage(new Page<UserBindLogs>(request, response, -1), userBindLogs);
            new ExportExcel("用户绑定iptv信息", UserBindLogs.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户绑定iptv信息记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userBindLogs/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("user:userBindLogs:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<UserBindLogs> list = ei.getDataList(UserBindLogs.class);
            for (UserBindLogs userBindLogs : list) {
                try {
                    userBindLogsService.save(userBindLogs);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户绑定iptv信息记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户绑定iptv信息记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户绑定iptv信息失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userBindLogs/?repage";
    }

    /**
     * 下载导入用户绑定iptv信息数据模板
     */
    @RequiresPermissions("user:userBindLogs:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户绑定iptv信息数据导入模板.xlsx";
            List<UserBindLogs> list = Lists.newArrayList();
            new ExportExcel("用户绑定iptv信息数据", UserBindLogs.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userBindLogs/?repage";
    }


}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.web;

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
import com.msframe.modules.zms.entity.ZmsUserChannelFavorite;
import com.msframe.modules.zms.service.ZmsUserChannelFavoriteService;

/**
 * 用户收藏直播管理Controller
 *
 * @author wlh
 * @version 2018-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/zms/zmsUserChannelFavorite")
public class ZmsUserChannelFavoriteController extends BaseController {

    @Autowired
    private ZmsUserChannelFavoriteService zmsUserChannelFavoriteService;

    @ModelAttribute
    public ZmsUserChannelFavorite get(@RequestParam(required = false) String id) {
        ZmsUserChannelFavorite entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = zmsUserChannelFavoriteService.get(id);
        }
        if (entity == null) {
            entity = new ZmsUserChannelFavorite();
        }
        return entity;
    }

    /**
     * 用户收藏直播列表页面
     */
    @RequiresPermissions("zms:zmsUserChannelFavorite:list")
    @RequestMapping(value = {"list", ""})
    public String list(ZmsUserChannelFavorite zmsUserChannelFavorite, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ZmsUserChannelFavorite> page = zmsUserChannelFavoriteService.findPage(new Page<ZmsUserChannelFavorite>(request, response), zmsUserChannelFavorite);
        model.addAttribute("page", page);

        UserInfo userInfo = zmsUserChannelFavorite.getUser();
        if (userInfo != null && userInfo.getId() != null) {
            model.addAttribute("userInfoId", userInfo.getId());
        } else {
            model.addAttribute("userInfoId", "");
        }

        return "modules/zms/zmsUserChannelFavoriteList";
    }

    /**
     * 增加，编辑用户收藏直播表单页面
     */
    @RequiresPermissions(value = {"zms:zmsUserChannelFavorite:add", "zms:zmsUserChannelFavorite:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(ZmsUserChannelFavorite zmsUserChannelFavorite, Model model) {
        model.addAttribute("zmsUserChannelFavorite", zmsUserChannelFavorite);
        return "modules/zms/zmsUserChannelFavoriteForm";
    }

    /**
     * 查看用户收藏直播表单页面
     */
    @RequiresPermissions(value = {"zms:zmsUserChannelFavorite:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(ZmsUserChannelFavorite zmsUserChannelFavorite, Model model) {
        model.addAttribute("zmsUserChannelFavorite", zmsUserChannelFavorite);
        return "modules/zms/zmsUserChannelFavoriteDetails";
    }

    /**
     * 保存用户收藏直播
     */
    @RequiresPermissions(value = {"zms:zmsUserChannelFavorite:add", "zms:zmsUserChannelFavorite:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(ZmsUserChannelFavorite zmsUserChannelFavorite, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, zmsUserChannelFavorite)) {
            return form(zmsUserChannelFavorite, model);
        }
        if (!zmsUserChannelFavorite.getIsNewRecord()) {//编辑表单保存
            ZmsUserChannelFavorite t = zmsUserChannelFavoriteService.get(zmsUserChannelFavorite.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(zmsUserChannelFavorite, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            zmsUserChannelFavoriteService.save(t);//保存
        } else {//新增表单保存
            zmsUserChannelFavoriteService.save(zmsUserChannelFavorite);//保存
        }
        addMessage(redirectAttributes, "保存用户收藏直播成功");

        String userInfoId = "";
        UserInfo userInfo = zmsUserChannelFavorite.getUser();
        if (userInfo != null && userInfo.getId() != null) {
            userInfoId = userInfo.getId();
        }

        return "redirect:" + Global.getAdminPath() + "/zms/zmsUserChannelFavorite/?repage&user=" + userInfoId;
    }

    /**
     * 删除用户收藏直播
     */
    @RequiresPermissions("zms:zmsUserChannelFavorite:del")
    @RequestMapping(value = "delete")
    public String delete(ZmsUserChannelFavorite zmsUserChannelFavorite, RedirectAttributes redirectAttributes) {
        zmsUserChannelFavoriteService.delete(zmsUserChannelFavorite);
        addMessage(redirectAttributes, "删除用户收藏直播成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsUserChannelFavorite/?repage";
    }

    /**
     * 批量删除用户收藏直播
     */
    @RequiresPermissions("zms:zmsUserChannelFavorite:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            zmsUserChannelFavoriteService.delete(zmsUserChannelFavoriteService.get(id));
        }
        addMessage(redirectAttributes, "删除用户收藏直播成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsUserChannelFavorite/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("zms:zmsUserChannelFavorite:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(ZmsUserChannelFavorite zmsUserChannelFavorite, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户收藏直播" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ZmsUserChannelFavorite> page = zmsUserChannelFavoriteService.findPage(new Page<ZmsUserChannelFavorite>(request, response, -1), zmsUserChannelFavorite);
            new ExportExcel("用户收藏直播", ZmsUserChannelFavorite.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户收藏直播记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsUserChannelFavorite/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("zms:zmsUserChannelFavorite:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<ZmsUserChannelFavorite> list = ei.getDataList(ZmsUserChannelFavorite.class);
            for (ZmsUserChannelFavorite zmsUserChannelFavorite : list) {
                try {
                    zmsUserChannelFavoriteService.save(zmsUserChannelFavorite);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户收藏直播记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户收藏直播记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户收藏直播失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsUserChannelFavorite/?repage";
    }

    /**
     * 下载导入用户收藏直播数据模板
     */
    @RequiresPermissions("zms:zmsUserChannelFavorite:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户收藏直播数据导入模板.xlsx";
            List<ZmsUserChannelFavorite> list = Lists.newArrayList();
            new ExportExcel("用户收藏直播数据", ZmsUserChannelFavorite.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsUserChannelFavorite/?repage";
    }


}
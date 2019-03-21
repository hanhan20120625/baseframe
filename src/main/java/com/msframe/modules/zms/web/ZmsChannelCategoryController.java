/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.zms.entity.ZmsCategory;
import com.msframe.modules.zms.service.ZmsCategoryService;
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
import com.msframe.modules.zms.entity.ZmsChannelCategory;
import com.msframe.modules.zms.service.ZmsChannelCategoryService;

/**
 * 直播频道类别Controller
 *
 * @author wlh
 * @version 2018-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/zms/zmsChannelCategory")
public class ZmsChannelCategoryController extends BaseController {

    @Autowired
    private ZmsChannelCategoryService zmsChannelCategoryService;

    @ModelAttribute
    public ZmsChannelCategory get(@RequestParam(required = false) String id) {
        ZmsChannelCategory entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = zmsChannelCategoryService.get(id);
        }
        if (entity == null) {
            entity = new ZmsChannelCategory();
        }
        return entity;
    }

    /**
     * 直播频道类别列表页面
     */
    @RequiresPermissions("zms:zmsChannelCategory:list")
    @RequestMapping(value = {"list", ""})
    public String list(ZmsChannelCategory zmsChannelCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ZmsChannelCategory> page = zmsChannelCategoryService.findPage(new Page<ZmsChannelCategory>(request, response), zmsChannelCategory);
        model.addAttribute("page", page);
        return "modules/zms/zmsChannelCategoryList";
    }

    /**
     * 增加，编辑直播频道类别表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannelCategory:add", "zms:zmsChannelCategory:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(ZmsChannelCategory zmsChannelCategory, Model model) {
        model.addAttribute("zmsChannelCategory", zmsChannelCategory);
        return "modules/zms/zmsChannelCategoryForm";
    }

    /**
     * 查看直播频道类别表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannelCategory:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(ZmsChannelCategory zmsChannelCategory, Model model) {
        model.addAttribute("zmsChannelCategory", zmsChannelCategory);
        return "modules/zms/zmsChannelCategoryDetails";
    }

    /**
     * 保存直播频道类别
     */
    @RequiresPermissions(value = {"zms:zmsChannelCategory:add", "zms:zmsChannelCategory:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(ZmsChannelCategory zmsChannelCategory, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, zmsChannelCategory)) {
            return form(zmsChannelCategory, model);
        }
        if (!zmsChannelCategory.getIsNewRecord()) {//编辑表单保存
            ZmsChannelCategory t = zmsChannelCategoryService.get(zmsChannelCategory.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(zmsChannelCategory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            zmsChannelCategoryService.save(t);//保存
        } else {//新增表单保存
            zmsChannelCategoryService.save(zmsChannelCategory);//保存
        }
        addMessage(redirectAttributes, "保存直播频道类别成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelCategory/?repage";
    }

    /**
     * 删除直播频道类别
     */
    @RequiresPermissions("zms:zmsChannelCategory:del")
    @RequestMapping(value = "delete")
    public String delete(ZmsChannelCategory zmsChannelCategory, RedirectAttributes redirectAttributes) {
        zmsChannelCategoryService.delete(zmsChannelCategory);
        addMessage(redirectAttributes, "删除直播频道类别成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelCategory/?repage";
    }

    /**
     * 批量删除直播频道类别
     */
    @RequiresPermissions("zms:zmsChannelCategory:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            zmsChannelCategoryService.delete(zmsChannelCategoryService.get(id));
        }
        addMessage(redirectAttributes, "删除直播频道类别成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelCategory/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("zms:zmsChannelCategory:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(ZmsChannelCategory zmsChannelCategory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "直播频道类别" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ZmsChannelCategory> page = zmsChannelCategoryService.findPage(new Page<ZmsChannelCategory>(request, response, -1), zmsChannelCategory);
            new ExportExcel("直播频道类别", ZmsChannelCategory.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出直播频道类别记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelCategory/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("zms:zmsChannelCategory:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<ZmsChannelCategory> list = ei.getDataList(ZmsChannelCategory.class);
            for (ZmsChannelCategory zmsChannelCategory : list) {
                try {
                    zmsChannelCategoryService.save(zmsChannelCategory);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条直播频道类别记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条直播频道类别记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入直播频道类别失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelCategory/?repage";
    }

    /**
     * 下载导入直播频道类别数据模板
     */
    @RequiresPermissions("zms:zmsChannelCategory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "直播频道类别数据导入模板.xlsx";
            List<ZmsChannelCategory> list = Lists.newArrayList();
            new ExportExcel("直播频道类别数据", ZmsChannelCategory.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelCategory/?repage";
    }


}
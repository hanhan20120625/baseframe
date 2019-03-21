/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.msframe.modules.cms.entity.CmsProgramType;
import com.msframe.modules.cms.service.CmsProgramTypeService;

/**
 * 影片关联类别Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsProgramType")
public class CmsProgramTypeController extends BaseController {

    @Autowired
    private CmsProgramTypeService cmsProgramTypeService;

    @ModelAttribute
    public CmsProgramType get(@RequestParam(required = false) String id) {
        CmsProgramType entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsProgramTypeService.get(id);
        }
        if (entity == null) {
            entity = new CmsProgramType();
        }
        return entity;
    }

    /**
     * 影片关联类别列表页面
     */
    @RequiresPermissions("cms:cmsProgramType:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsProgramType cmsProgramType, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsProgramType> page = cmsProgramTypeService.findPage(new Page<CmsProgramType>(request, response), cmsProgramType);
        model.addAttribute("page", page);
        return "modules/cms/cmsProgramTypeList";
    }

    /**
     * 增加，编辑影片关联类别表单页面
     */
    @RequiresPermissions(value = {"cms:cmsProgramType:add", "cms:cmsProgramType:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsProgramType cmsProgramType, Model model) {
        model.addAttribute("cmsProgramType", cmsProgramType);
        return "modules/cms/cmsProgramTypeForm";
    }

    /**
     * 查看影片关联类别表单页面
     */
    @RequiresPermissions(value = {"cms:cmsProgramType:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsProgramType cmsProgramType, Model model) {
        model.addAttribute("cmsProgramType", cmsProgramType);
        return "modules/cms/cmsProgramTypeDetails";
    }

    /**
     * 保存影片关联类别
     */
    @RequiresPermissions(value = {"cms:cmsProgramType:add", "cms:cmsProgramType:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsProgramType cmsProgramType, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsProgramType)) {
            return form(cmsProgramType, model);
        }
        if (!cmsProgramType.getIsNewRecord()) {//编辑表单保存
            CmsProgramType t = cmsProgramTypeService.get(cmsProgramType.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsProgramType, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsProgramTypeService.save(t);//保存
        } else {//新增表单保存
            cmsProgramTypeService.save(cmsProgramType);//保存
        }
        addMessage(redirectAttributes, "保存影片关联类别成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramType/?repage";
    }

    /**
     * 删除影片关联类别
     */
    @RequiresPermissions("cms:cmsProgramType:del")
    @RequestMapping(value = "delete")
    public String delete(CmsProgramType cmsProgramType, RedirectAttributes redirectAttributes) {
        cmsProgramTypeService.delete(cmsProgramType);
        addMessage(redirectAttributes, "删除影片关联类别成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramType/?repage";
    }

    /**
     * 批量删除影片关联类别
     */
    @RequiresPermissions("cms:cmsProgramType:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsProgramTypeService.delete(cmsProgramTypeService.get(id));
        }
        addMessage(redirectAttributes, "删除影片关联类别成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramType/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsProgramType:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsProgramType cmsProgramType, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "影片关联类别" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsProgramType> page = cmsProgramTypeService.findPage(new Page<CmsProgramType>(request, response, -1), cmsProgramType);
            new ExportExcel("影片关联类别", CmsProgramType.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出影片关联类别记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramType/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsProgramType:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsProgramType> list = ei.getDataList(CmsProgramType.class);
            for (CmsProgramType cmsProgramType : list) {
                try {
                    cmsProgramTypeService.save(cmsProgramType);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条影片关联类别记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条影片关联类别记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入影片关联类别失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramType/?repage";
    }

    /**
     * 下载导入影片关联类别数据模板
     */
    @RequiresPermissions("cms:cmsProgramType:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "影片关联类别数据导入模板.xlsx";
            List<CmsProgramType> list = Lists.newArrayList();
            new ExportExcel("影片关联类别数据", CmsProgramType.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramType/?repage";
    }

}
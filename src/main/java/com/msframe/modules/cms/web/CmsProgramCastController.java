/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

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
import com.msframe.modules.cms.entity.CmsProgramCast;
import com.msframe.modules.cms.service.CmsProgramCastService;

/**
 * 视频演员Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsProgramCast")
public class CmsProgramCastController extends BaseController {

    @Autowired
    private CmsProgramCastService cmsProgramCastService;

    @ModelAttribute
    public CmsProgramCast get(@RequestParam(required = false) String id) {
        CmsProgramCast entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsProgramCastService.get(id);
        }
        if (entity == null) {
            entity = new CmsProgramCast();
        }
        return entity;
    }

    /**
     * 视频演员列表页面
     */
    @RequiresPermissions(value = {"cms:cmsProgramCast:list", "cms:cmsProgram:cmsCast:list"}, logical = Logical.OR)
    @RequestMapping(value = {"list", ""})
    public String list(CmsProgramCast cmsProgramCast, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsProgramCast> page = cmsProgramCastService.findPage(new Page<CmsProgramCast>(request, response), cmsProgramCast);
        model.addAttribute("page", page);
        model.addAttribute("cmsProgramCast", cmsProgramCast);

        CmsProgram program = cmsProgramCast.getProgramId();
        if (program != null && program.getId() != null) {
            model.addAttribute("programId", program.getId());
        }

        return "modules/cms/cmsProgramCastList";
    }

    /**
     * 增加，编辑视频演员表单页面
     */
    @RequiresPermissions(value = {"cms:cmsProgramCast:add", "cms:cmsProgramCast:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsProgramCast cmsProgramCast, Model model) {
        model.addAttribute("cmsProgramCast", cmsProgramCast);
        return "modules/cms/cmsProgramCastForm";
    }

    /**
     * 查看视频演员表单页面
     */
    @RequiresPermissions(value = {"cms:cmsProgramCast:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsProgramCast cmsProgramCast, Model model) {
        model.addAttribute("cmsProgramCast", cmsProgramCast);
        return "modules/cms/cmsProgramCastDetails";
    }

    /**
     * 保存视频演员
     */
    @RequiresPermissions(value = {"cms:cmsProgramCast:add", "cms:cmsProgramCast:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsProgramCast cmsProgramCast, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsProgramCast)) {
            return form(cmsProgramCast, model);
        }
        if (!cmsProgramCast.getIsNewRecord()) {//编辑表单保存
            CmsProgramCast t = cmsProgramCastService.get(cmsProgramCast.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsProgramCast, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsProgramCastService.save(t);//保存
        } else {//新增表单保存
            cmsProgramCastService.save(cmsProgramCast);//保存
        }
        addMessage(redirectAttributes, "保存视频演员成功");

        String programId = "";
        CmsProgram program = cmsProgramCast.getProgramId();
        if (program != null && program.getId() != null) {
            programId = program.getId();
        }

        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramCast/?repage&programId=" + programId;
    }

    /**
     * 删除视频演员
     */
    @RequiresPermissions("cms:cmsProgramCast:del")
    @RequestMapping(value = "delete")
    public String delete(CmsProgramCast cmsProgramCast, RedirectAttributes redirectAttributes) {
        cmsProgramCastService.delete(cmsProgramCast);
        addMessage(redirectAttributes, "删除视频演员成功");
        String programId = cmsProgramCast.getProgramId().getId();
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramCast/?repage&programId="+programId;
    }

    /**
     * 批量删除视频演员
     */
    @RequiresPermissions("cms:cmsProgramCast:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsProgramCastService.delete(cmsProgramCastService.get(id));
        }
        addMessage(redirectAttributes, "删除视频演员成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramCast/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsProgramCast:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsProgramCast cmsProgramCast, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频演员" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsProgramCast> page = cmsProgramCastService.findPage(new Page<CmsProgramCast>(request, response, -1), cmsProgramCast);
            new ExportExcel("视频演员", CmsProgramCast.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出视频演员记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramCast/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsProgramCast:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsProgramCast> list = ei.getDataList(CmsProgramCast.class);
            for (CmsProgramCast cmsProgramCast : list) {
                try {
                    cmsProgramCastService.save(cmsProgramCast);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条视频演员记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条视频演员记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入视频演员失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramCast/?repage";
    }

    /**
     * 下载导入视频演员数据模板
     */
    @RequiresPermissions("cms:cmsProgramCast:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频演员数据导入模板.xlsx";
            List<CmsProgramCast> list = Lists.newArrayList();
            new ExportExcel("视频演员数据", CmsProgramCast.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgramCast/?repage";
    }


}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.msframe.modules.cms.entity.CmsLang;
import com.msframe.modules.cms.service.CmsLangService;

/**
 * 语言Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsLang")
public class CmsLangController extends BaseController {

    @Autowired
    private CmsLangService cmsLangService;

    @ModelAttribute
    public CmsLang get(@RequestParam(required = false) String id) {
        CmsLang entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsLangService.get(id);
        }
        if (entity == null) {
            entity = new CmsLang();
        }
        return entity;
    }

    /**
     * 语言列表页面
     */
    @RequiresPermissions("cms:cmsLang:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsLang cmsLang, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsLang> page = cmsLangService.findPage(new Page<CmsLang>(request, response), cmsLang);
        model.addAttribute("page", page);
        model.addAttribute("isSearch", isSearch);
        return "modules/cms/cmsLangList";
    }

    /**
     * 增加，编辑语言表单页面
     */
    @RequiresPermissions(value = {"cms:cmsLang:add", "cms:cmsLang:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsLang cmsLang, Model model) {
        model.addAttribute("cmsLang", cmsLang);
        if(StringUtils.isBlank(cmsLang.getId())){
            List<CmsLang> list = cmsLangService.findList(new CmsLang());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                cmsLang.setSort(list.get(0).getSort() + 10);
            }else{
                cmsLang.setSort(10L);
            }
        }
        return "modules/cms/cmsLangForm";
    }

    /**
     * 查看语言表单页面
     */
    @RequiresPermissions(value = {"cms:cmsLang:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsLang cmsLang, Model model) {
        model.addAttribute("cmsLang", cmsLang);
        return "modules/cms/cmsLangDetails";
    }

    /**
     * 保存语言
     */
    @RequiresPermissions(value = {"cms:cmsLang:add", "cms:cmsLang:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsLang cmsLang, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsLang)) {
            return form(cmsLang, model);
        }
        if (!cmsLang.getIsNewRecord()) {//编辑表单保存
            CmsLang t = cmsLangService.get(cmsLang.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsLang, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsLangService.save(t);//保存
        } else {//新增表单保存
            cmsLangService.save(cmsLang);//保存
        }
        addMessage(redirectAttributes, "保存语言成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsLang/?repage";
    }

    /**
     * 删除语言
     */
    @RequiresPermissions("cms:cmsLang:del")
    @RequestMapping(value = "delete")
    public String delete(CmsLang cmsLang, RedirectAttributes redirectAttributes) {
        cmsLangService.delete(cmsLang);
        addMessage(redirectAttributes, "删除语言成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsLang/?repage";
    }

    /**
     * 批量删除语言
     */
    @RequiresPermissions("cms:cmsLang:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsLangService.delete(cmsLangService.get(id));
        }
        addMessage(redirectAttributes, "删除语言成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsLang/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsLang:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsLang cmsLang, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "语言" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsLang> page = cmsLangService.findPage(new Page<CmsLang>(request, response, -1), cmsLang);
            new ExportExcel("语言", CmsLang.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出语言记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsLang/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsLang:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsLang> list = ei.getDataList(CmsLang.class);
            for (CmsLang cmsLang : list) {
                try {
                    cmsLangService.save(cmsLang);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条语言记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条语言记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入语言失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsLang/?repage";
    }

    /**
     * 下载导入语言数据模板
     */
    @RequiresPermissions("cms:cmsLang:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "语言数据导入模板.xlsx";
            List<CmsLang> list = Lists.newArrayList();
            new ExportExcel("语言数据", CmsLang.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsLang/?repage";
    }

    /**
     * @param cmsLang
     * @param url
     * @param fieldLabels
     * @param fieldKeys
     * @param searchLabel
     * @param searchKey
     * @param request
     * @param response
     * @param model
     * @return
     * @description 选择语言
     * @author leon
     * @date 2018/11/19
     */
    @RequestMapping(value = "selectCmsLang")
    public String selectCmsLang(CmsLang cmsLang, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsLang> page = cmsLangService.findPage(new Page<CmsLang>(request, response), cmsLang);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsLang);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }


    /**
     *  排序
     * @param request
     * @param resopns
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "changeSort")
    public String changeSort(HttpServletRequest request, HttpServletResponse resopns) {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        Map<String, Object> queryPreviousSortMap = new HashMap<>();
        queryPreviousSortMap.put("name", request.getParameter("name"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        CmsLang cmsLang = get(id);
        boolean result = cmsLangService.changeSort(cmsLang.getId(), cmsLang.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }

}
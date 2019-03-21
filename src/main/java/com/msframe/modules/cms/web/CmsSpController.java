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

import com.msframe.modules.cms.entity.CmsMovie;
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
import com.msframe.modules.cms.entity.CmsSp;
import com.msframe.modules.cms.service.CmsSpService;

/**
 * 基本信息Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsSp")
public class CmsSpController extends BaseController {

    @Autowired
    private CmsSpService cmsSpService;

    @ModelAttribute
    public CmsSp get(@RequestParam(required = false) String id) {
        CmsSp entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsSpService.get(id);
        }
        if (entity == null) {
            entity = new CmsSp();
        }
        return entity;
    }

    /**
     * 基本信息列表页面
     */
    @RequiresPermissions("cms:cmsSp:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsSp cmsSp, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsSp> page = cmsSpService.findPage(new Page<CmsSp>(request, response), cmsSp);
        model.addAttribute("page", page);
        model.addAttribute("isSearch", isSearch);
        return "modules/cms/cmsSpList";
    }

    /**
     * 增加，编辑基本信息表单页面
     */
    @RequiresPermissions(value = {"cms:cmsSp:add", "cms:cmsSp:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsSp cmsSp, Model model) {
        model.addAttribute("cmsSp", cmsSp);

        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(cmsSp.getId())) {
            List<CmsSp> list = cmsSpService.findList(new CmsSp());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                cmsSp.setSort(list.get(0).getSort() + 10);
            } else {
                cmsSp.setSort(10L);
            }
        }

        return "modules/cms/cmsSpForm";
    }

    /**
     * 查看基本信息表单页面
     */
    @RequiresPermissions(value = {"cms:cmsSp:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsSp cmsSp, Model model) {
        model.addAttribute("cmsSp", cmsSp);
        return "modules/cms/cmsSpDetails";
    }

    /**
     * 保存基本信息
     */
    @RequiresPermissions(value = {"cms:cmsSp:add", "cms:cmsSp:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsSp cmsSp, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsSp)) {
            return form(cmsSp, model);
        }
        if (!cmsSp.getIsNewRecord()) {//编辑表单保存
            CmsSp t = cmsSpService.get(cmsSp.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsSp, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsSpService.save(t);//保存
        } else {//新增表单保存
            cmsSpService.save(cmsSp);//保存
        }
        addMessage(redirectAttributes, "保存基本信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsSp/?repage";
    }

    /**
     * 删除基本信息
     */
    @RequiresPermissions("cms:cmsSp:del")
    @RequestMapping(value = "delete")
    public String delete(CmsSp cmsSp, RedirectAttributes redirectAttributes) {
        cmsSpService.delete(cmsSp);
        addMessage(redirectAttributes, "删除基本信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsSp/?repage";
    }

    /**
     * 批量删除基本信息
     */
    @RequiresPermissions("cms:cmsSp:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsSpService.delete(cmsSpService.get(id));
        }
        addMessage(redirectAttributes, "删除基本信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsSp/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsSp:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsSp cmsSp, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "基本信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsSp> page = cmsSpService.findPage(new Page<CmsSp>(request, response, -1), cmsSp);
            new ExportExcel("基本信息", CmsSp.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出基本信息记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsSp/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsSp:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsSp> list = ei.getDataList(CmsSp.class);
            for (CmsSp cmsSp : list) {
                try {
                    cmsSpService.save(cmsSp);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条基本信息记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条基本信息记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入基本信息失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsSp/?repage";
    }

    /**
     * 下载导入基本信息数据模板
     */
    @RequiresPermissions("cms:cmsSp:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "基本信息数据导入模板.xlsx";
            List<CmsSp> list = Lists.newArrayList();
            new ExportExcel("基本信息数据", CmsSp.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsSp/?repage";
    }

    /**
     * @param cmsSp
     * @param url
     * @param fieldLabels
     * @param fieldKeys
     * @param searchLabel
     * @param searchKey
     * @param request
     * @param response
     * @param model
     * @return
     * @description 选择基本信息
     * @author leon
     * @date 2018/11/15
     */
    @RequestMapping(value = "selectCmsSp")
    public String selectCmsSp(CmsSp cmsSp, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsSp> page = cmsSpService.findPage(new Page<CmsSp>(request, response), cmsSp);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsSp);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }

    @ResponseBody
    @RequestMapping(value = "changeSort")
    public String changeSort(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        Map<String, Object> queryPreviousSortMap = new HashMap<>();
        queryPreviousSortMap.put("name", request.getParameter("name"));
        queryPreviousSortMap.put("linkMan", request.getParameter("linkMan"));
        queryPreviousSortMap.put("mobile", request.getParameter("mobile"));
        queryPreviousSortMap.put("compactNumber", request.getParameter("compactNumber"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        CmsSp cmsSp = get(id);
        boolean result = cmsSpService.changeSort(cmsSp.getId(), cmsSp.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }


}
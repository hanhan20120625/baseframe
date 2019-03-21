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
import com.msframe.modules.cms.entity.CmsRegion;
import com.msframe.modules.cms.service.CmsRegionService;

/**
 * 视频内容地区Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsRegion")
public class CmsRegionController extends BaseController {

    @Autowired
    private CmsRegionService cmsRegionService;

    @ModelAttribute
    public CmsRegion get(@RequestParam(required = false) String id) {
        CmsRegion entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsRegionService.get(id);
        }
        if (entity == null) {
            entity = new CmsRegion();
        }
        return entity;
    }

    /**
     * 视频内容地区列表页面
     */
    @RequiresPermissions("cms:cmsRegion:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsRegion cmsRegion, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsRegion> page = cmsRegionService.findPage(new Page<CmsRegion>(request, response), cmsRegion);
        model.addAttribute("page", page);
        model.addAttribute("isSearch", isSearch);
        return "modules/cms/cmsRegionList";
    }

    /**
     * 增加，编辑视频内容地区表单页面
     */
    @RequiresPermissions(value = {"cms:cmsRegion:add", "cms:cmsRegion:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsRegion cmsRegion, Model model) {
        model.addAttribute("cmsRegion", cmsRegion);
        if(StringUtils.isBlank(cmsRegion.getId())){
            List<CmsRegion> list = cmsRegionService.findList(new CmsRegion());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                cmsRegion.setSort(list.get(0).getSort() + 10);
            }else{
                cmsRegion.setSort(10L);
            }
        }
        return "modules/cms/cmsRegionForm";
    }

    /**
     * 查看视频内容地区表单页面
     */
    @RequiresPermissions(value = {"cms:cmsRegion:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsRegion cmsRegion, Model model) {
        model.addAttribute("cmsRegion", cmsRegion);
        return "modules/cms/cmsRegionDetails";
    }

    /**
     * 保存视频内容地区
     */
    @RequiresPermissions(value = {"cms:cmsRegion:add", "cms:cmsRegion:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsRegion cmsRegion, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsRegion)) {
            return form(cmsRegion, model);
        }
        if (!cmsRegion.getIsNewRecord()) {//编辑表单保存
            CmsRegion t = cmsRegionService.get(cmsRegion.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsRegion, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsRegionService.save(t);//保存
        } else {//新增表单保存
            cmsRegionService.save(cmsRegion);//保存
        }
        addMessage(redirectAttributes, "保存视频内容地区成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRegion/?repage";
    }

    /**
     * 删除视频内容地区
     */
    @RequiresPermissions("cms:cmsRegion:del")
    @RequestMapping(value = "delete")
    public String delete(CmsRegion cmsRegion, RedirectAttributes redirectAttributes) {
        cmsRegionService.delete(cmsRegion);
        addMessage(redirectAttributes, "删除视频内容地区成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRegion/?repage";
    }

    /**
     * 批量删除视频内容地区
     */
    @RequiresPermissions("cms:cmsRegion:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsRegionService.delete(cmsRegionService.get(id));
        }
        addMessage(redirectAttributes, "删除视频内容地区成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRegion/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsRegion:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsRegion cmsRegion, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频内容地区" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsRegion> page = cmsRegionService.findPage(new Page<CmsRegion>(request, response, -1), cmsRegion);
            new ExportExcel("视频内容地区", CmsRegion.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出视频内容地区记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRegion/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsRegion:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsRegion> list = ei.getDataList(CmsRegion.class);
            for (CmsRegion cmsRegion : list) {
                try {
                    cmsRegionService.save(cmsRegion);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条视频内容地区记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条视频内容地区记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入视频内容地区失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRegion/?repage";
    }

    /**
     * 下载导入视频内容地区数据模板
     */
    @RequiresPermissions("cms:cmsRegion:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频内容地区数据导入模板.xlsx";
            List<CmsRegion> list = Lists.newArrayList();
            new ExportExcel("视频内容地区数据", CmsRegion.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRegion/?repage";
    }

    /**
     * @param cmsRegion
     * @param url
     * @param fieldLabels
     * @param fieldKeys
     * @param searchLabel
     * @param searchKey
     * @param request
     * @param response
     * @param model
     * @return
     * @description 选择国家地区
     * @author leon
     * @date 2018/11/19
     */
    @RequestMapping(value = "selectCmsRegion")
    public String selectCmsRegion(CmsRegion cmsRegion, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsRegion> page = cmsRegionService.findPage(new Page<CmsRegion>(request, response), cmsRegion);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsRegion);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }


    /**
     *  排序
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "changeSort")
    public String changeSort(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        Map<String, Object> queryPreviousSortMap = new HashMap<>();
        queryPreviousSortMap.put("name", request.getParameter("name"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        CmsRegion cmsRegion = get(id);
        boolean result = cmsRegionService.changeSort(cmsRegion.getId(), cmsRegion.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }
}
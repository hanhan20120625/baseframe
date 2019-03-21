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

import com.msframe.modules.cms.entity.CmsCategory;
import com.msframe.modules.cms.entity.CmsType;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.msframe.modules.cms.entity.CmsProgram;
import com.google.common.collect.Lists;
import com.msframe.common.utils.DateUtils;
import com.msframe.common.utils.MyBeanUtils;
import com.msframe.common.config.Global;
import com.msframe.common.persistence.Page;
import com.msframe.common.web.BaseController;
import com.msframe.common.utils.StringUtils;
import com.msframe.common.utils.excel.ExportExcel;
import com.msframe.common.utils.excel.ImportExcel;
import com.msframe.modules.cms.entity.CmsRecommend;
import com.msframe.modules.cms.service.CmsRecommendService;

/**
 * 影片推荐Controller
 *
 * @author leon
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsRecommend")
public class CmsRecommendController extends BaseController {

    @Autowired
    private CmsRecommendService cmsRecommendService;

    @ModelAttribute
    public CmsRecommend get(@RequestParam(required = false) String id) {
        CmsRecommend entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsRecommendService.get(id);
        }
        if (entity == null) {
            entity = new CmsRecommend();
        }
        return entity;
    }

    /**
     * 影片推荐列表页面
     */
    @RequiresPermissions("cms:cmsRecommend:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsRecommend cmsRecommend, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsRecommend> page = cmsRecommendService.findPage(new Page<CmsRecommend>(request, response), cmsRecommend);
        model.addAttribute("page", page);
        model.addAttribute("isSearch", isSearch);
        return "modules/cms/cmsRecommendList";
    }

    /**
     * 增加，编辑影片推荐表单页面
     */
    @RequiresPermissions(value = {"cms:cmsRecommend:add", "cms:cmsRecommend:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsRecommend cmsRecommend, Model model) {

        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(cmsRecommend.getId())) {
            List<CmsRecommend> list = cmsRecommendService.findList(new CmsRecommend());
            if (list.size() > 0 && list.get(list.size() - 1).getSort() != null) {
                cmsRecommend.setSort(list.get(list.size() - 1).getSort() + 10);
            } else {
                cmsRecommend.setSort(10L);
            }
        }

        model.addAttribute("cmsRecommend", cmsRecommend);
        return "modules/cms/cmsRecommendForm";
    }

    /**
     * 查看影片推荐表单页面
     */
    @RequiresPermissions(value = {"cms:cmsRecommend:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsRecommend cmsRecommend, Model model) {
        model.addAttribute("cmsRecommend", cmsRecommend);
        return "modules/cms/cmsRecommendDetails";
    }

    /**
     * 保存影片推荐
     */
    @RequiresPermissions(value = {"cms:cmsRecommend:add", "cms:cmsRecommend:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsRecommend cmsRecommend, @RequestParam("files") MultipartFile file, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsRecommend)) {
            return form(cmsRecommend, model);
        }
        if (!cmsRecommend.getIsNewRecord()) {//编辑表单保存
            CmsRecommend t = cmsRecommendService.get(cmsRecommend.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsRecommend, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsRecommendService.save(t, file);//保存
        } else {//新增表单保存
            cmsRecommendService.save(cmsRecommend, file);//保存
        }
        addMessage(redirectAttributes, "保存影片推荐成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRecommend/?repage";
    }

    /**
     * 删除影片推荐
     */
    @RequiresPermissions("cms:cmsRecommend:del")
    @RequestMapping(value = "delete")
    public String delete(CmsRecommend cmsRecommend, RedirectAttributes redirectAttributes) {
        cmsRecommendService.delete(cmsRecommend);
        addMessage(redirectAttributes, "删除影片推荐成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRecommend/?repage";
    }

    /**
     * 批量删除影片推荐
     */
    @RequiresPermissions("cms:cmsRecommend:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            cmsRecommendService.delete(cmsRecommendService.get(id));
        }
        addMessage(redirectAttributes, "删除影片推荐成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRecommend/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsRecommend:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsRecommend cmsRecommend, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "影片推荐" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsRecommend> page = cmsRecommendService.findPage(new Page<CmsRecommend>(request, response, -1), cmsRecommend);
            new ExportExcel("影片推荐", CmsRecommend.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出影片推荐记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRecommend/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsRecommend:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsRecommend> list = ei.getDataList(CmsRecommend.class);
            for (CmsRecommend cmsRecommend : list) {
                try {
                    cmsRecommendService.save(cmsRecommend);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条影片推荐记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条影片推荐记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入影片推荐失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRecommend/?repage";
    }

    /**
     * 下载导入影片推荐数据模板
     */
    @RequiresPermissions("cms:cmsRecommend:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "影片推荐数据导入模板.xlsx";
            List<CmsRecommend> list = Lists.newArrayList();
            new ExportExcel("影片推荐数据", CmsRecommend.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsRecommend/?repage";
    }


    /**
     * 选择影片编号
     */
    @RequestMapping(value = "selectprogramId")
    public String selectprogramId(CmsProgram programId, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsProgram> page = cmsRecommendService.findPageByprogramId(new Page<CmsProgram>(request, response), programId);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", programId);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }

    /**
     * @param cmsCategory
     * @param url
     * @param fieldLabels
     * @param fieldKeys
     * @param searchLabel
     * @param searchKey
     * @param request
     * @param response
     * @param model
     * @return
     * @description 选择所属栏目
     * @author leon
     * @date 2018/11/29
     */
    @RequestMapping(value = "selectCategoryId")
    public String selectCategoryId(CmsCategory cmsCategory, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsCategory> page = cmsRecommendService.findPageByCategoryId(new Page<CmsCategory>(request, response), cmsCategory);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsCategory);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }

    @RequestMapping(value = "selectTypeId")
    public String selectTypeId(CmsType cmsType, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey,String linkageId, HttpServletRequest request, HttpServletResponse response, Model model) {
        cmsType.setCategory(new CmsCategory(linkageId));
        Page<CmsType> page = cmsRecommendService.findPageByTypeId(new Page<CmsType>(request, response), cmsType);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsType);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }


    /**
     * 排序功能
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "changeSort")
    public String changeSort(HttpServletRequest request){
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        Map<String, Object> queryPreviousSortMap = new HashMap<>();
        queryPreviousSortMap.put("categoryId", request.getParameter("categoryId"));
        queryPreviousSortMap.put("isindex", request.getParameter("isindex"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        queryPreviousSortMap.put("programId", request.getParameter("programId"));
        queryPreviousSortMap.put("name", request.getParameter("name"));
        CmsRecommend cmsRecommend = get(id);
        boolean result = cmsRecommendService.changeSort(cmsRecommend.getId(), cmsRecommend.getSort(), type, queryPreviousSortMap);
        return String.valueOf(result);
    }
}
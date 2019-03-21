/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.cms.entity.CmsCategory;
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
import com.msframe.modules.app.entity.AppRecommend;
import com.msframe.modules.app.service.AppRecommendService;

/**
 * banner推荐Controller
 *
 * @author leon
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/app/appRecommend")
public class AppRecommendController extends BaseController {

    @Autowired
    private AppRecommendService appRecommendService;

    @ModelAttribute
    public AppRecommend get(@RequestParam(required = false) String id) {
        AppRecommend entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = appRecommendService.get(id);
        }
        if (entity == null) {
            entity = new AppRecommend();
        }
        return entity;
    }

    /**
     * banner推荐列表页面
     */
    @RequiresPermissions("app:appRecommend:list")
    @RequestMapping(value = {"list", ""})
    public String list(AppRecommend appRecommend, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<AppRecommend> page = appRecommendService.findPage(new Page<AppRecommend>(request, response), appRecommend);
        model.addAttribute("page", page);
        model.addAttribute("isSearch", isSearch);
        return "modules/app/appRecommendList";
    }

    /**
     * 增加，编辑banner推荐表单页面
     */
    @RequiresPermissions(value = {"app:appRecommend:add", "app:appRecommend:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(AppRecommend appRecommend, Model model) {

        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(appRecommend.getId())) {
            List<AppRecommend> list = appRecommendService.findList(new AppRecommend());
            if (list.size() > 0 && list.get(list.size() - 1).getSort() != null) {
                appRecommend.setSort(list.get(list.size() - 1).getSort() + 10);
            } else {
                appRecommend.setSort(10L);
            }
        }

        model.addAttribute("appRecommend", appRecommend);
        return "modules/app/appRecommendForm";
    }

    /**
     * 查看banner推荐表单页面
     */
    @RequiresPermissions(value = {"app:appRecommend:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(AppRecommend appRecommend, Model model) {
        model.addAttribute("appRecommend", appRecommend);
        return "modules/app/appRecommendDetails";
    }

    /**
     * 保存banner推荐
     */
    @RequiresPermissions(value = {"app:appRecommend:add", "app:appRecommend:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(AppRecommend appRecommend, @RequestParam("files") MultipartFile file, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, appRecommend)) {
            return form(appRecommend, model);
        }
        if (!appRecommend.getIsNewRecord()) {//编辑表单保存
            AppRecommend t = appRecommendService.get(appRecommend.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(appRecommend, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            appRecommendService.save(t, file);//保存
        } else {//新增表单保存
            appRecommendService.save(appRecommend, file);//保存
        }
        addMessage(redirectAttributes, "保存banner推荐成功");
        return "redirect:" + Global.getAdminPath() + "/app/appRecommend/?repage";
    }

    /**
     * 删除banner推荐
     */
    @RequiresPermissions("app:appRecommend:del")
    @RequestMapping(value = "delete")
    public String delete(AppRecommend appRecommend, RedirectAttributes redirectAttributes) {
        appRecommendService.delete(appRecommend);
        addMessage(redirectAttributes, "删除banner推荐成功");
        return "redirect:" + Global.getAdminPath() + "/app/appRecommend/?repage";
    }

    /**
     * 批量删除banner推荐
     */
    @RequiresPermissions("app:appRecommend:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            appRecommendService.delete(appRecommendService.get(id));
        }
        addMessage(redirectAttributes, "删除banner推荐成功");
        return "redirect:" + Global.getAdminPath() + "/app/appRecommend/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("app:appRecommend:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(AppRecommend appRecommend, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "banner推荐" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<AppRecommend> page = appRecommendService.findPage(new Page<AppRecommend>(request, response, -1), appRecommend);
            new ExportExcel("banner推荐", AppRecommend.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出banner推荐记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/app/appRecommend/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("app:appRecommend:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<AppRecommend> list = ei.getDataList(AppRecommend.class);
            for (AppRecommend appRecommend : list) {
                try {
                    appRecommendService.save(appRecommend);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条banner推荐记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条banner推荐记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入banner推荐失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/app/appRecommend/?repage";
    }

    /**
     * 下载导入banner推荐数据模板
     */
    @RequiresPermissions("app:appRecommend:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "banner推荐数据导入模板.xlsx";
            List<AppRecommend> list = Lists.newArrayList();
            new ExportExcel("banner推荐数据", AppRecommend.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/app/appRecommend/?repage";
    }


    /**
     * 选择影片编号
     */
    @RequestMapping(value = "selectprogramId")
    public String selectprogramId(CmsProgram programId, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsProgram> page = appRecommendService.findPageByprogramId(new Page<CmsProgram>(request, response), programId);
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
     * @description 选择影片分类
     * @author leon
     * @date 2018/11/29
     */
    @RequestMapping(value = "selectCategoryId")
    public String selectCategoryId(CmsCategory cmsCategory, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsCategory> page = appRecommendService.findPageByCategoryId(new Page<CmsCategory>(request, response), cmsCategory);
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


    /**
     * 排序功能
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "changeSort")
    public String changeSort(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        Map<String, Object> queryPreviousSortMap = new HashMap<>();
        queryPreviousSortMap.put("name", request.getParameter("name"));
        queryPreviousSortMap.put("programId", request.getParameter("programId"));
        queryPreviousSortMap.put("categoryId", request.getParameter("categoryId"));
        queryPreviousSortMap.put("isindex", request.getParameter("isindex"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        AppRecommend appRecommend = get(id);
        boolean result = appRecommendService.changeSort(appRecommend.getId(), appRecommend.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }

}
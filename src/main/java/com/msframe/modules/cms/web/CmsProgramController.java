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

import com.msframe.modules.cms.entity.*;
import com.msframe.modules.cms.service.*;
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

/**
 * 视频项目Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsProgram")
public class CmsProgramController extends BaseController {

    @Autowired
    private CmsProgramService cmsProgramService;

    @Autowired
    private CmsTypeService cmsTypeService;

    @Autowired
    private CmsRegionService cmsRegionService;

    @Autowired
    private CmsLangService cmsLangService;

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @ModelAttribute
    public CmsProgram get(@RequestParam(required = false) String id) {
        CmsProgram entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsProgramService.get(id);
        }
        if (entity == null) {
            entity = new CmsProgram();
        }
        return entity;
    }

    /**
     * 视频项目列表页面
     */
    @RequiresPermissions("cms:cmsProgram:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsProgram cmsProgram, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsProgram> page = cmsProgramService.findPage(new Page<CmsProgram>(request, response), cmsProgram);
        model.addAttribute("page", page);
        model.addAttribute("isSearch", isSearch);
        return "modules/cms/cmsProgramList";
    }

    /**
     * 增加，编辑视频项目表单页面
     */
    @RequiresPermissions(value = {"cms:cmsProgram:add", "cms:cmsProgram:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsProgram cmsProgram, Model model) {
        model.addAttribute("cmsProgram", cmsProgram);

        // 获取类型列表
        List<CmsType> cmsTypeList = cmsTypeService.findList(new CmsType());
        List<CmsProgramType> cmsProgramTypeList = cmsProgram.getCmsProgramTypeList();
        for (CmsProgramType cmsProgramType : cmsProgramTypeList) {
            String typeId = cmsProgramType.getTypeId().getId();
            for (CmsType cmsType : cmsTypeList) {
                String cmsTypeId = cmsType.getId();
                if (typeId.equals(cmsTypeId)) {
                    cmsType.setCheckFlag(true);
                    break;
                }
            }
        }

        // 获取地区列表
        List<CmsRegion> cmsRegionList = cmsRegionService.findList(new CmsRegion());
        List<CmsProgramRegion> cmsProgramRegionList = cmsProgram.getCmsProgramRegionList();
        for (CmsProgramRegion cmsProgramRegion : cmsProgramRegionList) {
            String regionId = cmsProgramRegion.getRegion().getId();
            for (CmsRegion cmsRegion : cmsRegionList) {
                String cmsRegionId = cmsRegion.getId();
                if (regionId.equals(cmsRegionId)) {
                    cmsRegion.setCheckFlag(true);
                    break;
                }
            }
        }

        // 获取语言列表
        List<CmsLang> cmsLangList = cmsLangService.findList(new CmsLang());
        List<CmsProgramLang> cmsProgramLangList = cmsProgram.getCmsProgramLangList();
        for (CmsProgramLang cmsProgramLang : cmsProgramLangList) {
            String langId = cmsProgramLang.getLangId().getId();
            for (CmsLang cmsLang : cmsLangList) {
                String cmsLangId = cmsLang.getId();
                if (langId.equals(cmsLangId)) {
                    cmsLang.setCheckFlag(true);
                    break;
                }
            }
        }

        // 获取栏目列表
        List<CmsCategory> cmsCategoryList = cmsCategoryService.findList(new CmsCategory());
        List<CmsProgramCategory> cmsProgramCategoryList = cmsProgram.getCmsProgramCategoryList();
        for (CmsProgramCategory cmsProgramCategory : cmsProgramCategoryList) {
            String categoryId = cmsProgramCategory.getCategoryId().getId();
            for (CmsCategory cmsCategory : cmsCategoryList) {
                String cmsCategoryId = cmsCategory.getId();
                if (categoryId.equals(cmsCategoryId)) {
                    cmsCategory.setCheckFlag(true);
                    break;
                }
            }
        }

        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(cmsProgram.getId())) {
            List<CmsProgram> list = cmsProgramService.findList(new CmsProgram());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                cmsProgram.setSort(list.get(0).getSort() + 10);
            } else {
                cmsProgram.setSort(10L);
            }
        }

        model.addAttribute("cmsTypeList", cmsTypeList);
        model.addAttribute("cmsRegionList", cmsRegionList);
        model.addAttribute("cmsLangList", cmsLangList);
        model.addAttribute("cmsCategoryList", cmsCategoryList);

        return "modules/cms/cmsProgramForm";
    }

    /**
     * 查看视频项目表单页面
     */
    @RequiresPermissions(value = {"cms:cmsProgram:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsProgram cmsProgram, Model model) {
        model.addAttribute("cmsProgram", cmsProgram);
        return "modules/cms/cmsProgramDetails";
    }

    /**
     * 保存视频项目
     */
    @RequiresPermissions(value = {"cms:cmsProgram:add", "cms:cmsProgram:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsProgram cmsProgram, @RequestParam("files") MultipartFile[] files, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsProgram)) {
            return form(cmsProgram, model);
        }
        if (!cmsProgram.getIsNewRecord()) {//编辑表单保存
            CmsProgram t = cmsProgramService.get(cmsProgram.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsProgram, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsProgramService.save(t, files);//保存
        } else {//新增表单保存
            cmsProgramService.save(cmsProgram, files);//保存
        }
        addMessage(redirectAttributes, "保存视频项目成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgram/?repage";
    }

    /**
     * 删除视频项目
     */
    @RequiresPermissions("cms:cmsProgram:del")
    @RequestMapping(value = "delete")
    public String delete(CmsProgram cmsProgram, RedirectAttributes redirectAttributes) {
        cmsProgramService.delete(cmsProgram);
        addMessage(redirectAttributes, "删除视频项目成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgram/?repage";
    }

    /**
     * 批量删除视频项目
     */
    @RequiresPermissions("cms:cmsProgram:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsProgramService.delete(cmsProgramService.get(id));
        }
        addMessage(redirectAttributes, "删除视频项目成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgram/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsProgram:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsProgram cmsProgram, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频项目" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsProgram> page = cmsProgramService.findPage(new Page<CmsProgram>(request, response, -1), cmsProgram);
            new ExportExcel("视频项目", CmsProgram.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出视频项目记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgram/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsProgram:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsProgram> list = ei.getDataList(CmsProgram.class);
            for (CmsProgram cmsProgram : list) {
                try {
                    cmsProgramService.save(cmsProgram);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条视频项目记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条视频项目记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入视频项目失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgram/?repage";
    }

    /**
     * 下载导入视频项目数据模板
     */
    @RequiresPermissions("cms:cmsProgram:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频项目数据导入模板.xlsx";
            List<CmsProgram> list = Lists.newArrayList();
            new ExportExcel("视频项目数据", CmsProgram.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsProgram/?repage";
    }

    /**
     * 选择视频项目
     */
    @RequestMapping(value = "selectCmsProgram")
    public String selectoaAssessmentModel(CmsProgram cmsProgram, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsProgram> page = cmsProgramService.findPage(new Page<CmsProgram>(request, response), cmsProgram);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsProgram);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }

    /**
     * 排序功能
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
        queryPreviousSortMap.put("cmsCategoryVoId", request.getParameter("cmsCategoryVoId"));
        queryPreviousSortMap.put("seriesFlag", request.getParameter("seriesFlag"));
        queryPreviousSortMap.put("tagId", request.getParameter("tagId"));
        queryPreviousSortMap.put("totalEpisode", request.getParameter("totalEpisode"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        CmsProgram cmsProgram = get(id);
        boolean result = cmsProgramService.changeSort(cmsProgram.getId(), cmsProgram.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }


}
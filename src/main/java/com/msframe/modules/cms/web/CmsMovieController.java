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

import com.msframe.common.constant.Constant;
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
import com.msframe.modules.cms.entity.CmsMovie;
import com.msframe.modules.cms.service.CmsMovieService;

/**
 * 视频Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsMovie")
public class CmsMovieController extends BaseController {

    @Autowired
    private CmsMovieService cmsMovieService;

    @ModelAttribute
    public CmsMovie get(@RequestParam(required = false) String id) {
        CmsMovie entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsMovieService.get(id);
        }
        if (entity == null) {
            entity = new CmsMovie();
        }
        return entity;
    }

    /**
     * 视频列表页面
     */
    @RequiresPermissions(value = {"cms:cmsMovie:list", "cms:cmsProgram:cmsMovie:list"}, logical = Logical.OR)
    @RequestMapping(value = {"list", ""})
    public String list(CmsMovie cmsMovie, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsMovie> page = cmsMovieService.findPage(new Page<CmsMovie>(request, response), cmsMovie);
        model.addAttribute("page", page);

        Integer isFromProgram = cmsMovie.getIsFromProgram();
        if (isFromProgram != null) {
            model.addAttribute("isFromProgram", isFromProgram);
        }

        CmsProgram program = cmsMovie.getProgramId();
        if (program != null && program.getId() != null) {
            model.addAttribute("programId", program.getId());
        }
        model.addAttribute("isSearch", isSearch);

        return "modules/cms/cmsMovieList";
    }

    /**
     * 增加，编辑视频表单页面
     */
    @RequiresPermissions(value = {"cms:cmsMovie:add", "cms:cmsMovie:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsMovie cmsMovie, Model model) {
        model.addAttribute("cmsMovie", cmsMovie);

        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(cmsMovie.getId())) {
            List<CmsMovie> list = cmsMovieService.findList(new CmsMovie());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                cmsMovie.setSort(list.get(0).getSort() + 10);
            } else {
                cmsMovie.setSort(10L);
            }
        }

        return "modules/cms/cmsMovieForm";
    }

    /**
     * 查看视频表单页面
     */
    @RequiresPermissions(value = {"cms:cmsMovie:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsMovie cmsMovie, Model model) {
        model.addAttribute("cmsMovie", cmsMovie);
        return "modules/cms/cmsMovieDetails";
    }

    /**
     * 保存视频
     */
    @RequiresPermissions(value = {"cms:cmsMovie:add", "cms:cmsMovie:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsMovie cmsMovie, @RequestParam("files") MultipartFile file, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsMovie)) {
            return form(cmsMovie, model);
        }
        if (!cmsMovie.getIsNewRecord()) {//编辑表单保存
            CmsMovie t = cmsMovieService.get(cmsMovie.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsMovie, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsMovieService.save(t, file);//保存
        } else {//新增表单保存
            cmsMovieService.save(cmsMovie, file);//保存
        }
        addMessage(redirectAttributes, "保存视频成功");

        String programId = "";
        CmsProgram program = cmsMovie.getProgramId();
        Integer isFromProgram = cmsMovie.getIsFromProgram();

        if (Constant.IS_FROM_PROGRAM_1.equals(isFromProgram)) {
            // 来自于影片页面
            programId = program.getId();
            return "redirect:" + Global.getAdminPath() + "/cms/cmsMovie/?repage&programId=" + programId + "&isFromProgram=" + isFromProgram;
        } else {
            return "redirect:" + Global.getAdminPath() + "/cms/cmsMovie/?repage";
        }
    }

    /**
     * 删除视频
     */
    @RequiresPermissions("cms:cmsMovie:del")
    @RequestMapping(value = "delete")
    public String delete(CmsMovie cmsMovie, RedirectAttributes redirectAttributes) {
        cmsMovieService.delete(cmsMovie);
        addMessage(redirectAttributes, "删除视频成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovie/?repage";
    }

    /**
     * 批量删除视频
     */
    @RequiresPermissions("cms:cmsMovie:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsMovieService.delete(cmsMovieService.get(id));
        }
        addMessage(redirectAttributes, "删除视频成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovie/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsMovie:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsMovie cmsMovie, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsMovie> page = cmsMovieService.findPage(new Page<CmsMovie>(request, response, -1), cmsMovie);
            new ExportExcel("视频", CmsMovie.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出视频记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovie/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsMovie:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsMovie> list = ei.getDataList(CmsMovie.class);
            for (CmsMovie cmsMovie : list) {
                try {
                    cmsMovieService.save(cmsMovie);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条视频记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条视频记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入视频失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovie/?repage";
    }

    /**
     * 下载导入视频数据模板
     */
    @RequiresPermissions("cms:cmsMovie:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "视频数据导入模板.xlsx";
            List<CmsMovie> list = Lists.newArrayList();
            new ExportExcel("视频数据", CmsMovie.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovie/?repage";
    }

    @ResponseBody
    @RequestMapping(value = "changeSort")
    public String changeSort(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        Map<String, Object> queryPreviousSortMap = new HashMap<>();
        queryPreviousSortMap.put("programName", request.getParameter("programName"));
        queryPreviousSortMap.put("name", request.getParameter("name"));
        queryPreviousSortMap.put("episode", request.getParameter("episode"));
        queryPreviousSortMap.put("status", request.getParameter("status"));

        CmsMovie cmsMovie = get(id);
        boolean result = cmsMovieService.changeSort(cmsMovie.getId(), cmsMovie.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }


}
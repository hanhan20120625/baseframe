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
import com.msframe.modules.cms.entity.CmsPictureInfo;
import com.msframe.modules.cms.service.CmsPictureInfoService;

/**
 * 图片信息Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsPictureInfo")
public class CmsPictureInfoController extends BaseController {

    @Autowired
    private CmsPictureInfoService cmsPictureInfoService;

    @ModelAttribute
    public CmsPictureInfo get(@RequestParam(required = false) String id) {
        CmsPictureInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsPictureInfoService.get(id);
        }
        if (entity == null) {
            entity = new CmsPictureInfo();
        }
        return entity;
    }

    /**
     * 图片信息列表页面
     */
    @RequiresPermissions(value = {"cms:cmsPictureInfo:list", "cms:cmsProgram:cmsPicture:list"}, logical = Logical.OR)
    @RequestMapping(value = {"list", ""})
    public String list(CmsPictureInfo cmsPictureInfo, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsPictureInfo> page = cmsPictureInfoService.findPage(new Page<CmsPictureInfo>(request, response), cmsPictureInfo);
        model.addAttribute("page", page);
        CmsMovie movie = cmsPictureInfo.getMovieId();
        CmsProgram program = cmsPictureInfo.getProgramId();

        if (movie != null && movie.getId() != null) {
            model.addAttribute("movieId", movie.getId());
        } else {
            model.addAttribute("movieId", "");
        }

        if (program != null && program.getId() != null) {
            model.addAttribute("programId", program.getId());
        } else {
            model.addAttribute("programId", "");
        }
        model.addAttribute("isSearch", isSearch);
        return "modules/cms/cmsPictureInfoList";
    }

    /**
     * 增加，编辑图片信息表单页面
     */
    @RequiresPermissions(value = {"cms:cmsPictureInfo:add", "cms:cmsPictureInfo:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsPictureInfo cmsPictureInfo, Model model) {
        if(StringUtils.isBlank(cmsPictureInfo.getId())){
            List<CmsPictureInfo> list = cmsPictureInfoService.findList(new CmsPictureInfo());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                cmsPictureInfo.setSort(list.get(0).getSort() + 10);
            }else {
                cmsPictureInfo.setSort(10L);
            }
        }
        model.addAttribute("cmsPictureInfo", cmsPictureInfo);
        return "modules/cms/cmsPictureInfoForm";
    }

    /**
     * 查看图片信息表单页面
     */
    @RequiresPermissions(value = {"cms:cmsPictureInfo:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsPictureInfo cmsPictureInfo, Model model) {
        model.addAttribute("cmsPictureInfo", cmsPictureInfo);
        return "modules/cms/cmsPictureInfoDetails";
    }

    /**
     * 保存图片信息
     */
    @RequiresPermissions(value = {"cms:cmsPictureInfo:add", "cms:cmsPictureInfo:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsPictureInfo cmsPictureInfo,@RequestParam("files") MultipartFile files, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsPictureInfo)) {
            return form(cmsPictureInfo, model);
        }
        if (!cmsPictureInfo.getIsNewRecord()) {//编辑表单保存
            CmsPictureInfo t = cmsPictureInfoService.get(cmsPictureInfo.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsPictureInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsPictureInfoService.save(t,files);//保存
        } else {//新增表单保存
            cmsPictureInfoService.save(cmsPictureInfo,files);//保存
        }
        addMessage(redirectAttributes, "保存图片信息成功");

        String programId = "";
        String movieId = "";
        CmsMovie movie = cmsPictureInfo.getMovieId();
        CmsProgram program = cmsPictureInfo.getProgramId();
        if (movie != null && movie.getId() != null) {
            movieId = movie.getId();
        }

        if (program != null && program.getId() != null) {
            programId = program.getId();
        }

        return "redirect:" + Global.getAdminPath() + "/cms/cmsPictureInfo/?repage&programId=" + programId + "&movieId=" + movieId;
    }

    /**
     * 删除图片信息
     */
    @RequiresPermissions("cms:cmsPictureInfo:del")
    @RequestMapping(value = "delete")
    public String delete(CmsPictureInfo cmsPictureInfo, RedirectAttributes redirectAttributes) {
        cmsPictureInfoService.delete(cmsPictureInfo);
        String programId = "";
        String movieId = "";
        CmsMovie movie = cmsPictureInfo.getMovieId();
        CmsProgram program = cmsPictureInfo.getProgramId();
        if (movie != null && movie.getId() != null) {
            movieId = movie.getId();
        }

        if (program != null && program.getId() != null) {
            programId = program.getId();
        }
        addMessage(redirectAttributes, "删除图片信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsPictureInfo/?repage&programId=" + programId + "&movieId=" + movieId;
    }

    /**
     * 批量删除图片信息
     */
    @RequiresPermissions("cms:cmsPictureInfo:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsPictureInfoService.delete(cmsPictureInfoService.get(id));
        }
        addMessage(redirectAttributes, "删除图片信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsPictureInfo/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsPictureInfo:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsPictureInfo cmsPictureInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "图片信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsPictureInfo> page = cmsPictureInfoService.findPage(new Page<CmsPictureInfo>(request, response, -1), cmsPictureInfo);
            new ExportExcel("图片信息", CmsPictureInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出图片信息记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsPictureInfo/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsPictureInfo:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsPictureInfo> list = ei.getDataList(CmsPictureInfo.class);
            for (CmsPictureInfo cmsPictureInfo : list) {
                try {
                    cmsPictureInfoService.save(cmsPictureInfo);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条图片信息记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条图片信息记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入图片信息失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsPictureInfo/?repage";
    }

    /**
     * 下载导入图片信息数据模板
     */
    @RequiresPermissions("cms:cmsPictureInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "图片信息数据导入模板.xlsx";
            List<CmsPictureInfo> list = Lists.newArrayList();
            new ExportExcel("图片信息数据", CmsPictureInfo.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsPictureInfo/?repage";
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
        queryPreviousSortMap.put("name", request.getParameter("name"));
        queryPreviousSortMap.put("description", request.getParameter("description"));
        CmsPictureInfo cmsPictureInfo = get(id);
        boolean result = cmsPictureInfoService.changeSort(cmsPictureInfo.getId(), cmsPictureInfo.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }

}
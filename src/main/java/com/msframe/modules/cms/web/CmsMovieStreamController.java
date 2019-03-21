/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.cms.entity.CmsMovie;
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
import com.msframe.modules.cms.entity.CmsMovieStream;
import com.msframe.modules.cms.service.CmsMovieStreamService;

/**
 * 电影播放流Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsMovieStream")
public class CmsMovieStreamController extends BaseController {

    @Autowired
    private CmsMovieStreamService cmsMovieStreamService;

    @ModelAttribute
    public CmsMovieStream get(@RequestParam(required = false) String id) {
        CmsMovieStream entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsMovieStreamService.get(id);
        }
        if (entity == null) {
            entity = new CmsMovieStream();
        }
        return entity;
    }

    /**
     * 电影播放流列表页面
     */
    @RequiresPermissions("cms:cmsMovieStream:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsMovieStream cmsMovieStream, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsMovieStream> page = cmsMovieStreamService.findPage(new Page<CmsMovieStream>(request, response), cmsMovieStream);
        model.addAttribute("page", page);

        CmsMovie movie = cmsMovieStream.getMovieId();

        if (movie != null && movie.getId() != null) {
            model.addAttribute("movieId", movie.getId());
        } else {
            model.addAttribute("movieId", "");
        }

        return "modules/cms/cmsMovieStreamList";
    }

    /**
     * 增加，编辑电影播放流表单页面
     */
    @RequiresPermissions(value = {"cms:cmsMovieStream:add", "cms:cmsMovieStream:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsMovieStream cmsMovieStream, Model model) {
        model.addAttribute("cmsMovieStream", cmsMovieStream);
        return "modules/cms/cmsMovieStreamForm";
    }

    /**
     * 查看电影播放流表单页面
     */
    @RequiresPermissions(value = {"cms:cmsMovieStream:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsMovieStream cmsMovieStream, Model model) {
        model.addAttribute("cmsMovieStream", cmsMovieStream);
        return "modules/cms/cmsMovieStreamDetails";
    }

    /**
     * 保存电影播放流
     */
    @RequiresPermissions(value = {"cms:cmsMovieStream:add", "cms:cmsMovieStream:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsMovieStream cmsMovieStream, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsMovieStream)) {
            return form(cmsMovieStream, model);
        }
        if (!cmsMovieStream.getIsNewRecord()) {//编辑表单保存
            CmsMovieStream t = cmsMovieStreamService.get(cmsMovieStream.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsMovieStream, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsMovieStreamService.save(t);//保存
        } else {//新增表单保存
            cmsMovieStreamService.save(cmsMovieStream);//保存
        }
        addMessage(redirectAttributes, "保存电影播放流成功");

        String movieId = "";
        CmsMovie movie = cmsMovieStream.getMovieId();
        if (movie != null && movie.getId() != null) {
            movieId = movie.getId();
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovieStream/?repage&movieId=" + movieId;
    }

    /**
     * 删除电影播放流
     */
    @RequiresPermissions("cms:cmsMovieStream:del")
    @RequestMapping(value = "delete")
    public String delete(CmsMovieStream cmsMovieStream, RedirectAttributes redirectAttributes) {
        cmsMovieStreamService.delete(cmsMovieStream);
        addMessage(redirectAttributes, "删除电影播放流成功");
        String movieId = cmsMovieStream.getMovieId().getId();
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovieStream/?repage&movieId="+movieId;
    }

    /**
     * 批量删除电影播放流
     */
    @RequiresPermissions("cms:cmsMovieStream:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsMovieStreamService.delete(cmsMovieStreamService.get(id));
        }
        addMessage(redirectAttributes, "删除电影播放流成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovieStream/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsMovieStream:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsMovieStream cmsMovieStream, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "电影播放流" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsMovieStream> page = cmsMovieStreamService.findPage(new Page<CmsMovieStream>(request, response, -1), cmsMovieStream);
            new ExportExcel("电影播放流", CmsMovieStream.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出电影播放流记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovieStream/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsMovieStream:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsMovieStream> list = ei.getDataList(CmsMovieStream.class);
            for (CmsMovieStream cmsMovieStream : list) {
                try {
                    cmsMovieStreamService.save(cmsMovieStream);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条电影播放流记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条电影播放流记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入电影播放流失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovieStream/?repage";
    }

    /**
     * 下载导入电影播放流数据模板
     */
    @RequiresPermissions("cms:cmsMovieStream:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "电影播放流数据导入模板.xlsx";
            List<CmsMovieStream> list = Lists.newArrayList();
            new ExportExcel("电影播放流数据", CmsMovieStream.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsMovieStream/?repage";
    }


}
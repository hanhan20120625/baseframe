/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

import com.google.common.collect.Lists;
import com.msframe.common.config.Global;
import com.msframe.common.persistence.Page;
import com.msframe.common.utils.DateUtils;
import com.msframe.common.utils.MyBeanUtils;
import com.msframe.common.utils.StringUtils;
import com.msframe.common.utils.excel.ExportExcel;
import com.msframe.common.utils.excel.ImportExcel;
import com.msframe.common.web.BaseController;
import com.msframe.modules.cms.entity.CmsCast;
import com.msframe.modules.cms.entity.CmsMovie;
import com.msframe.modules.cms.service.CmsCastService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 演员信息Controller
 *
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsCast")
public class CmsCastController extends BaseController {

    @Autowired
    private CmsCastService cmsCastService;

    @ModelAttribute
    public CmsCast get(@RequestParam(required = false) String id) {
        CmsCast entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsCastService.get(id);
        }
        if (entity == null) {
            entity = new CmsCast();
        }
        return entity;
    }

    /**
     * 演员信息列表页面
     */
    @RequiresPermissions("cms:cmsCast:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsCast cmsCast, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<CmsCast> page = cmsCastService.findPage(new Page<CmsCast>(request, response), cmsCast);
        model.addAttribute("page", page);
        model.addAttribute("isSearch", isSearch);
        return "modules/cms/cmsCastList";
    }

    /**
     * 增加，编辑演员信息表单页面
     */
    @RequiresPermissions(value = {"cms:cmsCast:add", "cms:cmsCast:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsCast cmsCast, Model model) {
        model.addAttribute("cmsCast", cmsCast);

        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(cmsCast.getId())) {
            List<CmsCast> list = cmsCastService.findList(new CmsCast());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                cmsCast.setSort(list.get(0).getSort() + 10);
            } else {
                cmsCast.setSort(10L);
            }
        }

        return "modules/cms/cmsCastForm";
    }

    /**
     * 查看演员信息表单页面
     */
    @RequiresPermissions(value = {"cms:cmsCast:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsCast cmsCast, Model model) {
        model.addAttribute("cmsCast", cmsCast);
        return "modules/cms/cmsCastDetails";
    }

    /**
     * 保存演员信息
     */
    @RequiresPermissions(value = {"cms:cmsCast:add", "cms:cmsCast:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsCast cmsCast, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, cmsCast)) {
            return form(cmsCast, model);
        }
        if (!cmsCast.getIsNewRecord()) {//编辑表单保存
            CmsCast t = cmsCastService.get(cmsCast.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsCast, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsCastService.save(t);//保存
        } else {//新增表单保存
            cmsCastService.save(cmsCast);//保存
        }
        addMessage(redirectAttributes, "保存演员信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsCast/?repage";
    }

    /**
     * 删除演员信息
     */
    @RequiresPermissions("cms:cmsCast:del")
    @RequestMapping(value = "delete")
    public String delete(CmsCast cmsCast, RedirectAttributes redirectAttributes) {
        cmsCastService.delete(cmsCast);
        addMessage(redirectAttributes, "删除演员信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsCast/?repage";
    }

    /**
     * 批量删除演员信息
     */
    @RequiresPermissions("cms:cmsCast:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            cmsCastService.delete(cmsCastService.get(id));
        }
        addMessage(redirectAttributes, "删除演员信息成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsCast/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("cms:cmsCast:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(CmsCast cmsCast, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "演员信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<CmsCast> page = cmsCastService.findPage(new Page<CmsCast>(request, response, -1), cmsCast);
            new ExportExcel("演员信息", CmsCast.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出演员信息记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cm/cmsCast/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:cmsCast:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<CmsCast> list = ei.getDataList(CmsCast.class);
            for (CmsCast cmsCast : list) {
                try {
                    cmsCastService.save(cmsCast);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条演员信息记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条演员信息记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入演员信息失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsCast/?repage";
    }

    /**
     * 下载导入演员信息数据模板
     */
    @RequiresPermissions("cms:cmsCast:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "演员信息数据导入模板.xlsx";
            List<CmsCast> list = Lists.newArrayList();
            new ExportExcel("演员信息数据", CmsCast.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/cmsCast/?repage";
    }

    /**
     * @param cmsCast
     * @param url
     * @param fieldLabels
     * @param fieldKeys
     * @param searchLabel
     * @param searchKey
     * @param request
     * @param response
     * @param model
     * @return
     * @description 演员选择
     * @author leon
     * @date 2018/11/21
     */
    @RequestMapping(value = "selectCmsCast")
    public String selectCmsCast(CmsCast cmsCast, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsCast> page = cmsCastService.findPage(new Page<CmsCast>(request, response), cmsCast);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsCast);
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
        queryPreviousSortMap.put("hometown", request.getParameter("hometown"));
        queryPreviousSortMap.put("education", request.getParameter("education"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        CmsCast cmsCast = get(id);
        boolean result = cmsCastService.changeSort(cmsCast.getId(), cmsCast.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }
}
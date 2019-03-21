/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.msframe.common.utils.MyBeanUtils;
import com.msframe.common.config.Global;
import com.msframe.common.web.BaseController;
import com.msframe.common.utils.StringUtils;
import com.msframe.modules.cms.entity.CmsCategory;
import com.msframe.modules.cms.service.CmsCategoryService;

/**
 * 栏目Controller
 *
 * @author lpz
 * @version 2018-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsCategory")
public class CmsCategoryController extends BaseController {

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @ModelAttribute
    public CmsCategory get(@RequestParam(required = false) String id) {
        CmsCategory entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cmsCategoryService.get(id);
        }
        if (entity == null) {
            entity = new CmsCategory();
        }
        return entity;
    }

    /**
     * 栏目列表页面
     */
    @RequiresPermissions("cms:cmsCategory:list")
    @RequestMapping(value = {"list", ""})
    public String list(CmsCategory cmsCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CmsCategory> list = cmsCategoryService.findList(cmsCategory);
        model.addAttribute("list", list);
        return "modules/cms/cmsCategoryList";
    }

    /**
     * 增加，编辑栏目表单页面
     */
    @RequiresPermissions(value = {"cms:cmsCategory:add", "cms:cmsCategory:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(CmsCategory cmsCategory, Model model) {
        if (cmsCategory.getParent() != null && StringUtils.isNotBlank(cmsCategory.getParent().getId())) {
            cmsCategory.setParent(cmsCategoryService.get(cmsCategory.getParent().getId()));
            // 获取排序号，最末节点排序号+30
            if (StringUtils.isBlank(cmsCategory.getId())) {
                CmsCategory cmsCategoryChild = new CmsCategory();
                cmsCategoryChild.setParent(new CmsCategory(cmsCategory.getParent().getId()));
                List<CmsCategory> list = cmsCategoryService.findList(cmsCategory);
                if (list.size() > 0) {
                    cmsCategory.setSort(list.get(list.size() - 1).getSort());
                    if (cmsCategory.getSort() != null) {
                        cmsCategory.setSort(cmsCategory.getSort() + 30);
                    }
                }
            }
        }
        if (cmsCategory.getSort() == null) {
            cmsCategory.setSort(30);
        }
        model.addAttribute("cmsCategory", cmsCategory);
        return "modules/cms/cmsCategoryForm";
    }

    /**
     * 查看栏目表单页面
     */
    @RequiresPermissions(value = {"cms:cmsCategory:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(CmsCategory cmsCategory, Model model) {
        if (cmsCategory.getParent() != null && StringUtils.isNotBlank(cmsCategory.getParent().getId())) {
            cmsCategory.setParent(cmsCategoryService.get(cmsCategory.getParent().getId()));
            // 获取排序号，最末节点排序号+30
            if (StringUtils.isBlank(cmsCategory.getId())) {
                CmsCategory cmsCategoryChild = new CmsCategory();
                cmsCategoryChild.setParent(new CmsCategory(cmsCategory.getParent().getId()));
                List<CmsCategory> list = cmsCategoryService.findList(cmsCategory);
                if (list.size() > 0) {
                    cmsCategory.setSort(list.get(list.size() - 1).getSort());
                    if (cmsCategory.getSort() != null) {
                        cmsCategory.setSort(cmsCategory.getSort() + 30);
                    }
                }
            }
        }
        if (cmsCategory.getSort() == null) {
            cmsCategory.setSort(30);
        }
        model.addAttribute("cmsCategory", cmsCategory);
        return "modules/cms/cmsCategoryDetails";
    }

    /**
     * 保存栏目
     */
    @RequiresPermissions(value = {"cms:cmsCategory:add", "cms:cmsCategory:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(CmsCategory cmsCategory, @RequestParam("files") MultipartFile[] files, Model model, RedirectAttributes redirectAttributes) throws Exception {

        if (!beanValidator(model, cmsCategory)) {
            return form(cmsCategory, model);
        }
        if (!cmsCategory.getIsNewRecord()) {//编辑表单保存
            CmsCategory t = cmsCategoryService.get(cmsCategory.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(cmsCategory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            cmsCategoryService.save(t, files);//保存
        } else {//新增表单保存
            cmsCategoryService.save(cmsCategory, files);//保存
        }
        addMessage(redirectAttributes, "保存栏目成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsCategory/?repage";
    }

    /**
     * 删除栏目
     */
    @RequiresPermissions("cms:cmsCategory:del")
    @RequestMapping(value = "delete")
    public String delete(CmsCategory cmsCategory, RedirectAttributes redirectAttributes) {
        cmsCategoryService.delete(cmsCategory);
        addMessage(redirectAttributes, "删除栏目成功");
        return "redirect:" + Global.getAdminPath() + "/cms/cmsCategory/?repage";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<CmsCategory> list = cmsCategoryService.findList(new CmsCategory());
        for (int i = 0; i < list.size(); i++) {
            CmsCategory e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile files, String testName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(testName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String newName = simpleDateFormat.format(new Date()) + System.currentTimeMillis();

        JSONObject json = new JSONObject();
        String msg = "添加成功";
        String path = "/webapp/static/qhiptvfiles";
        String returnPath = "";
        try {
            String name = files.getOriginalFilename();
            String suffixName = name.substring(name.indexOf("."), name.length());
            path = path + File.separator + newName + suffixName;
            returnPath = File.separator + newName + suffixName;
            File uploadFile = new File(path);
            files.transferTo(uploadFile);
        } catch (Exception e) {
            msg = "添加失败";
            e.printStackTrace();
        }
        json.put("msg", msg);
        json.put("url", returnPath);
        return json.toJSONString();
    }
}
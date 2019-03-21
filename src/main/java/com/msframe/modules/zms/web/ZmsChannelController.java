/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.cms.entity.CmsProgram;
import com.msframe.modules.zms.entity.ZmsCategory;
import com.msframe.modules.zms.entity.ZmsChannelCategory;
import com.msframe.modules.zms.service.ZmsCategoryService;
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
import com.msframe.modules.zms.entity.ZmsChannel;
import com.msframe.modules.zms.service.ZmsChannelService;

/**
 * 频道信息管理Controller
 *
 * @author wlh
 * @version 2018-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/zms/zmsChannel")
public class ZmsChannelController extends BaseController {

    @Autowired
    private ZmsChannelService zmsChannelService;

    @Autowired
    private ZmsCategoryService zmsCategoryService;

    @ModelAttribute
    public ZmsChannel get(@RequestParam(required = false) String id) {
        ZmsChannel entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = zmsChannelService.get(id);
        }
        if (entity == null) {
            entity = new ZmsChannel();
        }
        return entity;
    }

    /**
     * 频道信息管理列表页面
     */
    @RequiresPermissions("zms:zmsChannel:list")
    @RequestMapping(value = {"list", ""})
    public String list(ZmsChannel zmsChannel, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
        Page<ZmsChannel> page = zmsChannelService.findPage(new Page<ZmsChannel>(request, response), zmsChannel);
        model.addAttribute("page", page);
        model.addAttribute("isSearch",isSearch);
        return "modules/zms/zmsChannelList";
    }

    /**
     * 增加，编辑频道信息管理表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannel:add", "zms:zmsChannel:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(ZmsChannel zmsChannel, Model model) {
        model.addAttribute("zmsChannel", zmsChannel);

        List<ZmsCategory> zmsCategoryList = zmsCategoryService.findList(new ZmsCategory());
        List<ZmsChannelCategory> zmsChannelCategoryList = zmsChannel.getZmsChannelCategoryList();
        for (ZmsChannelCategory zmsChannelCategory : zmsChannelCategoryList) {
            String categoryId = zmsChannelCategory.getCategoryId().getId();
            for (ZmsCategory zmsCategory : zmsCategoryList) {
                String zmsCategoryId = zmsCategory.getId();
                if (categoryId.equals(zmsCategoryId)) {
                    zmsCategory.setCheckFlag(true);
                    break;
                }
            }

        }

        model.addAttribute("zmsCategoryList", zmsCategoryList);

        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(zmsChannel.getId())) {
            List<ZmsChannel> list = zmsChannelService.findList(new ZmsChannel());
            if (list.size() > 0 && list.get(0).getSort() != null) {
                zmsChannel.setSort(list.get(0).getSort() + 10);
            } else {
                zmsChannel.setSort(10L);
            }
        }

        return "modules/zms/zmsChannelForm";
    }

    /**
     * 查看频道信息管理表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannel:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(ZmsChannel zmsChannel, Model model) {
        model.addAttribute("zmsChannel", zmsChannel);
        return "modules/zms/zmsChannelDetails";
    }

    /**
     * 保存频道信息管理
     */
    @RequiresPermissions(value = {"zms:zmsChannel:add", "zms:zmsChannel:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(ZmsChannel zmsChannel, @RequestParam("files") MultipartFile[] files, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, zmsChannel)) {
            return form(zmsChannel, model);
        }
        if (!zmsChannel.getIsNewRecord()) {//编辑表单保存
            ZmsChannel t = zmsChannelService.get(zmsChannel.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(zmsChannel, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            zmsChannelService.save(t, files);//保存
        } else {//新增表单保存
            zmsChannelService.save(zmsChannel, files);//保存
        }
        addMessage(redirectAttributes, "保存频道信息管理成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannel/?repage";
    }

    /**
     * 删除频道信息管理
     */
    @RequiresPermissions("zms:zmsChannel:del")
    @RequestMapping(value = "delete")
    public String delete(ZmsChannel zmsChannel, RedirectAttributes redirectAttributes) {
        zmsChannelService.delete(zmsChannel);
        addMessage(redirectAttributes, "删除频道信息管理成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannel/?repage";
    }

    /**
     * 批量删除频道信息管理
     */
    @RequiresPermissions("zms:zmsChannel:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            zmsChannelService.delete(zmsChannelService.get(id));
        }
        addMessage(redirectAttributes, "删除频道信息管理成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannel/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("zms:zmsChannel:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(ZmsChannel zmsChannel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "频道信息管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ZmsChannel> page = zmsChannelService.findPage(new Page<ZmsChannel>(request, response, -1), zmsChannel);
            new ExportExcel("频道信息管理", ZmsChannel.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出频道信息管理记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannel/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("zms:zmsChannel:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<ZmsChannel> list = ei.getDataList(ZmsChannel.class);
            for (ZmsChannel zmsChannel : list) {
                try {
                    zmsChannelService.save(zmsChannel);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条频道信息管理记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条频道信息管理记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入频道信息管理失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannel/?repage";
    }

    /**
     * 下载导入频道信息管理数据模板
     */
    @RequiresPermissions("zms:zmsChannel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "频道信息管理数据导入模板.xlsx";
            List<ZmsChannel> list = Lists.newArrayList();
            new ExportExcel("频道信息管理数据", ZmsChannel.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannel/?repage";
    }

    /**
     * 排序功能
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
        queryPreviousSortMap.put("channelNumber", request.getParameter("channelNumber"));
        queryPreviousSortMap.put("zmsType", request.getParameter("zmsType"));
        queryPreviousSortMap.put("language", request.getParameter("language"));
        queryPreviousSortMap.put("status", request.getParameter("status"));
        ZmsChannel zmsChannel = get(id);
        boolean result = zmsChannelService.changeSort(zmsChannel.getId(), zmsChannel.getSort(), type,queryPreviousSortMap);
        return String.valueOf(result);
    }

}

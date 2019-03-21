/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.zms.entity.ZmsChannel;
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
import com.msframe.modules.zms.entity.ZmsChannelSchedule;
import com.msframe.modules.zms.service.ZmsChannelScheduleService;

/**
 * 直播节目单列表Controller
 * @author wlh
 * @version 2018-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/zms/zmsChannelSchedule")
public class ZmsChannelScheduleController extends BaseController {

    @Autowired
    private ZmsChannelScheduleService zmsChannelScheduleService;

    @ModelAttribute
    public ZmsChannelSchedule get(@RequestParam(required = false) String id) {
        ZmsChannelSchedule entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = zmsChannelScheduleService.get(id);
        }
        if (entity == null) {
            entity = new ZmsChannelSchedule();
        }
        return entity;
    }

    /**
     * 直播节目单列表列表页面
     */
    @RequiresPermissions("zms:zmsChannelSchedule:list")
    @RequestMapping(value = {"list", ""})
    public String list(ZmsChannelSchedule zmsChannelSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ZmsChannelSchedule> page = zmsChannelScheduleService.findPage(new Page<ZmsChannelSchedule>(request, response), zmsChannelSchedule);
        model.addAttribute("page", page);

        ZmsChannel channel = zmsChannelSchedule.getChannelId();
        if (channel != null && channel.getId() != null) {
            model.addAttribute("channelId", channel.getId());
        } else {
            model.addAttribute("channelId", "");
        }


        return "modules/zms/zmsChannelScheduleList";
    }

    /**
     * 增加，编辑直播节目单列表表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannelSchedule:add", "zms:zmsChannelSchedule:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(ZmsChannelSchedule zmsChannelSchedule, Model model) {
        model.addAttribute("zmsChannelSchedule", zmsChannelSchedule);
        return "modules/zms/zmsChannelScheduleForm";
    }

    /**
     * 查看直播节目单列表表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannelSchedule:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(ZmsChannelSchedule zmsChannelSchedule, Model model) {
        model.addAttribute("zmsChannelSchedule", zmsChannelSchedule);
        return "modules/zms/zmsChannelScheduleDetails";
    }

    /**
     * 保存直播节目单列表
     */
    @RequiresPermissions(value = {"zms:zmsChannelSchedule:add", "zms:zmsChannelSchedule:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(ZmsChannelSchedule zmsChannelSchedule, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, zmsChannelSchedule)) {
            return form(zmsChannelSchedule, model);
        }
        if (!zmsChannelSchedule.getIsNewRecord()) {//编辑表单保存
            ZmsChannelSchedule t = zmsChannelScheduleService.get(zmsChannelSchedule.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(zmsChannelSchedule, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            zmsChannelScheduleService.save(t);//保存
        } else {//新增表单保存
            zmsChannelScheduleService.save(zmsChannelSchedule);//保存
        }
        addMessage(redirectAttributes, "保存直播节目单列表成功");

        String channelId = "";
        ZmsChannel channel = zmsChannelSchedule.getChannelId();
        if (channel != null && channel.getId() != null) {
            channelId = channel.getId();
        }


        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelSchedule/?repage&channelId=" + channelId;
    }

    /**
     * 删除直播节目单列表
     */
    @RequiresPermissions("zms:zmsChannelSchedule:del")
    @RequestMapping(value = "delete")
    public String delete(ZmsChannelSchedule zmsChannelSchedule, RedirectAttributes redirectAttributes) {
        zmsChannelScheduleService.delete(zmsChannelSchedule);
        addMessage(redirectAttributes, "删除直播节目单列表成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelSchedule/?repage";
    }

    /**
     * 批量删除直播节目单列表
     */
    @RequiresPermissions("zms:zmsChannelSchedule:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            zmsChannelScheduleService.delete(zmsChannelScheduleService.get(id));
        }
        addMessage(redirectAttributes, "删除直播节目单列表成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelSchedule/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("zms:zmsChannelSchedule:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(ZmsChannelSchedule zmsChannelSchedule, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "直播节目单列表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ZmsChannelSchedule> page = zmsChannelScheduleService.findPage(new Page<ZmsChannelSchedule>(request, response, -1), zmsChannelSchedule);
            new ExportExcel("直播节目单列表", ZmsChannelSchedule.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出直播节目单列表记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelSchedule/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("zms:zmsChannelSchedule:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<ZmsChannelSchedule> list = ei.getDataList(ZmsChannelSchedule.class);
            for (ZmsChannelSchedule zmsChannelSchedule : list) {
                try {
                    zmsChannelScheduleService.save(zmsChannelSchedule);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条直播节目单列表记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条直播节目单列表记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入直播节目单列表失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelSchedule/?repage";
    }

    /**
     * 下载导入直播节目单列表数据模板
     */
    @RequiresPermissions("zms:zmsChannelSchedule:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "直播节目单列表数据导入模板.xlsx";
            List<ZmsChannelSchedule> list = Lists.newArrayList();
            new ExportExcel("直播节目单列表数据", ZmsChannelSchedule.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelSchedule/?repage";
    }


}
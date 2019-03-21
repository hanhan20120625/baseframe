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
import com.msframe.modules.zms.entity.ZmsChannelStream;
import com.msframe.modules.zms.service.ZmsChannelStreamService;

/**
 * 直播节目流信息Controller
 *
 * @author wlh
 * @version 2018-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/zms/zmsChannelStream")
public class ZmsChannelStreamController extends BaseController {

    @Autowired
    private ZmsChannelStreamService zmsChannelStreamService;

    @ModelAttribute
    public ZmsChannelStream get(@RequestParam(required = false) String id) {
        ZmsChannelStream entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = zmsChannelStreamService.get(id);
        }
        if (entity == null) {
            entity = new ZmsChannelStream();
        }
        return entity;
    }

    /**
     * 直播节目流信息列表页面
     */
    @RequiresPermissions("zms:zmsChannelStream:list")
    @RequestMapping(value = {"list", ""})
    public String list(ZmsChannelStream zmsChannelStream, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ZmsChannelStream> page = zmsChannelStreamService.findPage(new Page<ZmsChannelStream>(request, response), zmsChannelStream);
        model.addAttribute("page", page);

        ZmsChannel channel = zmsChannelStream.getChannelId();
        if (channel != null && channel.getId() != null) {
            model.addAttribute("channelId", channel.getId());
        } else {
            model.addAttribute("channelId", "");
        }

        return "modules/zms/zmsChannelStreamList";
    }

    /**
     * 增加，编辑直播节目流信息表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannelStream:add", "zms:zmsChannelStream:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(ZmsChannelStream zmsChannelStream, Model model) {
        model.addAttribute("zmsChannelStream", zmsChannelStream);
        return "modules/zms/zmsChannelStreamForm";
    }

    /**
     * 查看直播节目流信息表单页面
     */
    @RequiresPermissions(value = {"zms:zmsChannelStream:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(ZmsChannelStream zmsChannelStream, Model model) {
        model.addAttribute("zmsChannelStream", zmsChannelStream);
        return "modules/zms/zmsChannelStreamDetails";
    }

    /**
     * 保存直播节目流信息
     */
    @RequiresPermissions(value = {"zms:zmsChannelStream:add", "zms:zmsChannelStream:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(ZmsChannelStream zmsChannelStream, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, zmsChannelStream)) {
            return form(zmsChannelStream, model);
        }
        if (!zmsChannelStream.getIsNewRecord()) {//编辑表单保存
            ZmsChannelStream t = zmsChannelStreamService.get(zmsChannelStream.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(zmsChannelStream, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            zmsChannelStreamService.save(t);//保存
        } else {//新增表单保存
            zmsChannelStreamService.save(zmsChannelStream);//保存
        }
        addMessage(redirectAttributes, "保存直播节目流信息成功");

        String channelId = "";
        ZmsChannel channel = zmsChannelStream.getChannelId();
        if (channel != null && channel.getId() != null) {
            channelId = channel.getId();
        }

        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelStream/?repage&channelId=" + channelId;
    }

    /**
     * 删除直播节目流信息
     */
    @RequiresPermissions("zms:zmsChannelStream:del")
    @RequestMapping(value = "delete")
    public String delete(ZmsChannelStream zmsChannelStream, RedirectAttributes redirectAttributes) {
        zmsChannelStreamService.delete(zmsChannelStream);
        addMessage(redirectAttributes, "删除直播节目流信息成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelStream/?repage";
    }

    /**
     * 批量删除直播节目流信息
     */
    @RequiresPermissions("zms:zmsChannelStream:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            zmsChannelStreamService.delete(zmsChannelStreamService.get(id));
        }
        addMessage(redirectAttributes, "删除直播节目流信息成功");
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelStream/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("zms:zmsChannelStream:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(ZmsChannelStream zmsChannelStream, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "直播节目流信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ZmsChannelStream> page = zmsChannelStreamService.findPage(new Page<ZmsChannelStream>(request, response, -1), zmsChannelStream);
            new ExportExcel("直播节目流信息", ZmsChannelStream.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出直播节目流信息记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelStream/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("zms:zmsChannelStream:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<ZmsChannelStream> list = ei.getDataList(ZmsChannelStream.class);
            for (ZmsChannelStream zmsChannelStream : list) {
                try {
                    zmsChannelStreamService.save(zmsChannelStream);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条直播节目流信息记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条直播节目流信息记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入直播节目流信息失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelStream/?repage";
    }

    /**
     * 下载导入直播节目流信息数据模板
     */
    @RequiresPermissions("zms:zmsChannelStream:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "直播节目流信息数据导入模板.xlsx";
            List<ZmsChannelStream> list = Lists.newArrayList();
            new ExportExcel("直播节目流信息数据", ZmsChannelStream.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/zms/zmsChannelStream/?repage";
    }


}
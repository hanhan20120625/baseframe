/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.cms.entity.CmsMovie;
import com.msframe.modules.cms.entity.CmsProgram;
import com.msframe.modules.user.entity.UserInfo;
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
import com.msframe.modules.user.entity.UserFavorite;
import com.msframe.modules.user.service.UserFavoriteService;

/**
 * 影片收藏信息Controller
 *
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userFavorite")
public class UserFavoriteController extends BaseController {

    @Autowired
    private UserFavoriteService userFavoriteService;

    @ModelAttribute
    public UserFavorite get(@RequestParam(required = false) String id) {
        UserFavorite entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userFavoriteService.get(id);
        }
        if (entity == null) {
            entity = new UserFavorite();
        }
        return entity;
    }

    /**
     * 影片收藏信息列表页面
     */
    @RequiresPermissions("user:userFavorite:list")
    @RequestMapping(value = {"list", ""})
    public String list(UserFavorite userFavorite, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserFavorite> page = userFavoriteService.findPage(new Page<UserFavorite>(request, response), userFavorite);
        model.addAttribute("page", page);

        UserInfo userInfo = userFavorite.getUser();
        if (userInfo != null && userInfo.getId() != null) {
            model.addAttribute("userInfoId", userInfo.getId());
        } else {
            model.addAttribute("userInfoId", "");
        }

        return "modules/user/userFavoriteList";
    }

    /**
     * 增加，编辑影片收藏信息表单页面
     */
    @RequiresPermissions(value = {"user:userFavorite:add", "user:userFavorite:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(UserFavorite userFavorite, Model model) {
        model.addAttribute("userFavorite", userFavorite);
        return "modules/user/userFavoriteForm";
    }

    /**
     * 查看影片收藏信息表单页面
     */
    @RequiresPermissions(value = {"user:userFavorite:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String viewDetails(UserFavorite userFavorite, Model model) {
        model.addAttribute("userFavorite", userFavorite);
        return "modules/user/userFavoriteDetails";
    }

    /**
     * 保存影片收藏信息
     */
    @RequiresPermissions(value = {"user:userFavorite:add", "user:userFavorite:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(UserFavorite userFavorite, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, userFavorite)) {
            return form(userFavorite, model);
        }
        if (!userFavorite.getIsNewRecord()) {//编辑表单保存
            UserFavorite t = userFavoriteService.get(userFavorite.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(userFavorite, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            userFavoriteService.save(t);//保存
        } else {//新增表单保存
            userFavoriteService.save(userFavorite);//保存
        }
        addMessage(redirectAttributes, "保存影片收藏信息成功");

        String userInfoId = "";
        UserInfo userInfo = userFavorite.getUser();
        if (userInfo != null && userInfo.getId() != null) {
            userInfoId = userInfo.getId();
        }

        return "redirect:" + Global.getAdminPath() + "/user/userFavorite/?repage&user=" + userInfoId;
    }

    /**
     * 删除影片收藏信息
     */
    @RequiresPermissions("user:userFavorite:del")
    @RequestMapping(value = "delete")
    public String delete(UserFavorite userFavorite, RedirectAttributes redirectAttributes) {
        userFavoriteService.delete(userFavorite);
        addMessage(redirectAttributes, "删除影片收藏信息成功");
        return "redirect:" + Global.getAdminPath() + "/user/userFavorite/?repage";
    }

    /**
     * 批量删除影片收藏信息
     */
    @RequiresPermissions("user:userFavorite:del")
    @RequestMapping(value = "deleteAll")
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            userFavoriteService.delete(userFavoriteService.get(id));
        }
        addMessage(redirectAttributes, "删除影片收藏信息成功");
        return "redirect:" + Global.getAdminPath() + "/user/userFavorite/?repage";
    }

    /**
     * 导出excel文件
     */
    @RequiresPermissions("user:userFavorite:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(UserFavorite userFavorite, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "影片收藏信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<UserFavorite> page = userFavoriteService.findPage(new Page<UserFavorite>(request, response, -1), userFavorite);
            new ExportExcel("影片收藏信息", UserFavorite.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出影片收藏信息记录失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userFavorite/?repage";
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("user:userFavorite:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<UserFavorite> list = ei.getDataList(UserFavorite.class);
            for (UserFavorite userFavorite : list) {
                try {
                    userFavoriteService.save(userFavorite);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条影片收藏信息记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条影片收藏信息记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入影片收藏信息失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userFavorite/?repage";
    }

    /**
     * 下载导入影片收藏信息数据模板
     */
    @RequiresPermissions("user:userFavorite:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "影片收藏信息数据导入模板.xlsx";
            List<UserFavorite> list = Lists.newArrayList();
            new ExportExcel("影片收藏信息数据", UserFavorite.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/user/userFavorite/?repage";
    }

    @RequestMapping(value = "selectProgramId")
    public String selectProgramId(CmsProgram cmsProgram, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsProgram> page = userFavoriteService.findPageByProgramId(new Page<CmsProgram>(request, response), cmsProgram);
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

    @RequestMapping(value = "selectMovieId")
    public String selectMovieId(CmsMovie cmsMovie, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CmsMovie> page = userFavoriteService.findPageByMovieId(new Page<CmsMovie>(request, response), cmsMovie);
        model.addAttribute("labelNames", fieldLabels.split(";"));
        model.addAttribute("labelValues", fieldKeys.split(";"));
        model.addAttribute("fieldLabels", fieldLabels);
        model.addAttribute("fieldKeys", fieldKeys);
        model.addAttribute("url", url);
        model.addAttribute("searchLabel", searchLabel);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("obj", cmsMovie);
        model.addAttribute("page", page);
        return "modules/sys/gridselect";
    }

}
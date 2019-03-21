/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.msframe.modules.user.entity.UserPlayHistory;
import com.msframe.modules.user.service.UserPlayHistoryService;

/**
 * 用户播放记录Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userPlayHistory")
public class UserPlayHistoryController extends BaseController {

	@Autowired
	private UserPlayHistoryService userPlayHistoryService;
	
	@ModelAttribute
	public UserPlayHistory get(@RequestParam(required=false) String id) {
		UserPlayHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userPlayHistoryService.get(id);
		}
		if (entity == null){
			entity = new UserPlayHistory();
		}
		return entity;
	}
	
	/**
	 * 用户播放记录列表页面
	 */
	@RequiresPermissions("user:userPlayHistory:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserPlayHistory userPlayHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserPlayHistory> page = userPlayHistoryService.findPage(new Page<UserPlayHistory>(request, response), userPlayHistory); 
		model.addAttribute("page", page);
		return "modules/user/userPlayHistoryList";
	}

	/**
	 * 增加，编辑用户播放记录表单页面
	 */
	@RequiresPermissions(value={"user:userPlayHistory:add","user:userPlayHistory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserPlayHistory userPlayHistory, Model model) {
		model.addAttribute("userPlayHistory", userPlayHistory);
		return "modules/user/userPlayHistoryForm";
	}

	/**
	 * 查看用户播放记录表单页面
	 */
	@RequiresPermissions(value={"user:userPlayHistory:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(UserPlayHistory userPlayHistory, Model model) {
		model.addAttribute("userPlayHistory", userPlayHistory);
		return "modules/user/userPlayHistoryDetails";
	}

	/**
	 * 保存用户播放记录
	 */
	@RequiresPermissions(value={"user:userPlayHistory:add","user:userPlayHistory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserPlayHistory userPlayHistory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userPlayHistory)){
			return form(userPlayHistory, model);
		}
		if(!userPlayHistory.getIsNewRecord()){//编辑表单保存
			UserPlayHistory t = userPlayHistoryService.get(userPlayHistory.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userPlayHistory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userPlayHistoryService.save(t);//保存
		}else{//新增表单保存
			userPlayHistoryService.save(userPlayHistory);//保存
		}
		addMessage(redirectAttributes, "保存用户播放记录成功");
		return "redirect:"+Global.getAdminPath()+"/user/userPlayHistory/?repage";
	}
	
	/**
	 * 删除用户播放记录
	 */
	@RequiresPermissions("user:userPlayHistory:del")
	@RequestMapping(value = "delete")
	public String delete(UserPlayHistory userPlayHistory, RedirectAttributes redirectAttributes) {
		userPlayHistoryService.delete(userPlayHistory);
		addMessage(redirectAttributes, "删除用户播放记录成功");
		return "redirect:"+Global.getAdminPath()+"/user/userPlayHistory/?repage";
	}
	
	/**
	 * 批量删除用户播放记录
	 */
	@RequiresPermissions("user:userPlayHistory:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			userPlayHistoryService.delete(userPlayHistoryService.get(id));
		}
		addMessage(redirectAttributes, "删除用户播放记录成功");
		return "redirect:"+Global.getAdminPath()+"/user/userPlayHistory/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("user:userPlayHistory:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserPlayHistory userPlayHistory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户播放记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserPlayHistory> page = userPlayHistoryService.findPage(new Page<UserPlayHistory>(request, response, -1), userPlayHistory);
    		new ExportExcel("用户播放记录", UserPlayHistory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户播放记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userPlayHistory/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user:userPlayHistory:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserPlayHistory> list = ei.getDataList(UserPlayHistory.class);
			for (UserPlayHistory userPlayHistory : list){
				try{
					userPlayHistoryService.save(userPlayHistory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户播放记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户播放记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户播放记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userPlayHistory/?repage";
    }
	
	/**
	 * 下载导入用户播放记录数据模板
	 */
	@RequiresPermissions("user:userPlayHistory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户播放记录数据导入模板.xlsx";
    		List<UserPlayHistory> list = Lists.newArrayList(); 
    		new ExportExcel("用户播放记录数据", UserPlayHistory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userPlayHistory/?repage";
    }
}
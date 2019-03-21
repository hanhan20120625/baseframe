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
import com.msframe.modules.user.entity.UserLoginLogs;
import com.msframe.modules.user.service.UserLoginLogsService;

/**
 * 用户登录日志Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userLoginLogs")
public class UserLoginLogsController extends BaseController {

	@Autowired
	private UserLoginLogsService userLoginLogsService;
	
	@ModelAttribute
	public UserLoginLogs get(@RequestParam(required=false) String id) {
		UserLoginLogs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userLoginLogsService.get(id);
		}
		if (entity == null){
			entity = new UserLoginLogs();
		}
		return entity;
	}
	
	/**
	 * 用户登录日志列表页面
	 */
	@RequiresPermissions("user:userLoginLogs:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserLoginLogs userLoginLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserLoginLogs> page = userLoginLogsService.findPage(new Page<UserLoginLogs>(request, response), userLoginLogs); 
		model.addAttribute("page", page);
		return "modules/user/userLoginLogsList";
	}

	/**
	 * 增加，编辑用户登录日志表单页面
	 */
	@RequiresPermissions(value={"user:userLoginLogs:add","user:userLoginLogs:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserLoginLogs userLoginLogs, Model model) {
		if(StringUtils.isBlank(userLoginLogs.getId())){
			List<UserLoginLogs> list = userLoginLogsService.findList(new UserLoginLogs());
			if (list.size() > 0 && list.get(0).getSort() != null) {
				userLoginLogs.setSort(list.get(0).getSort() + 10);
			}else{
				userLoginLogs.setSort(10L);
			}
		}
		model.addAttribute("userLoginLogs", userLoginLogs);
		return "modules/user/userLoginLogsForm";
	}

	/**
	 * 查看用户登录日志表单页面
	 */
	@RequiresPermissions(value={"user:userLoginLogs:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(UserLoginLogs userLoginLogs, Model model) {
		model.addAttribute("userLoginLogs", userLoginLogs);
		return "modules/user/userLoginLogsDetails";
	}

	/**
	 * 保存用户登录日志
	 */
	@RequiresPermissions(value={"user:userLoginLogs:add","user:userLoginLogs:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserLoginLogs userLoginLogs, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userLoginLogs)){
			return form(userLoginLogs, model);
		}
		if(!userLoginLogs.getIsNewRecord()){//编辑表单保存
			UserLoginLogs t = userLoginLogsService.get(userLoginLogs.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userLoginLogs, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userLoginLogsService.save(t);//保存
		}else{//新增表单保存
			userLoginLogsService.save(userLoginLogs);//保存
		}
		addMessage(redirectAttributes, "保存用户登录日志成功");
		return "redirect:"+Global.getAdminPath()+"/user/userLoginLogs/?repage";
	}
	
	/**
	 * 删除用户登录日志
	 */
	@RequiresPermissions("user:userLoginLogs:del")
	@RequestMapping(value = "delete")
	public String delete(UserLoginLogs userLoginLogs, RedirectAttributes redirectAttributes) {
		userLoginLogsService.delete(userLoginLogs);
		addMessage(redirectAttributes, "删除用户登录日志成功");
		return "redirect:"+Global.getAdminPath()+"/user/userLoginLogs/?repage";
	}
	
	/**
	 * 批量删除用户登录日志
	 */
	@RequiresPermissions("user:userLoginLogs:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			userLoginLogsService.delete(userLoginLogsService.get(id));
		}
		addMessage(redirectAttributes, "删除用户登录日志成功");
		return "redirect:"+Global.getAdminPath()+"/user/userLoginLogs/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("user:userLoginLogs:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserLoginLogs userLoginLogs, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户登录日志"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserLoginLogs> page = userLoginLogsService.findPage(new Page<UserLoginLogs>(request, response, -1), userLoginLogs);
    		new ExportExcel("用户登录日志", UserLoginLogs.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户登录日志记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userLoginLogs/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user:userLoginLogs:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserLoginLogs> list = ei.getDataList(UserLoginLogs.class);
			for (UserLoginLogs userLoginLogs : list){
				try{
					userLoginLogsService.save(userLoginLogs);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户登录日志记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户登录日志记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户登录日志失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userLoginLogs/?repage";
    }
	
	/**
	 * 下载导入用户登录日志数据模板
	 */
	@RequiresPermissions("user:userLoginLogs:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户登录日志数据导入模板.xlsx";
    		List<UserLoginLogs> list = Lists.newArrayList(); 
    		new ExportExcel("用户登录日志数据", UserLoginLogs.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userLoginLogs/?repage";
    }

}
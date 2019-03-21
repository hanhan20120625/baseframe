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
import com.msframe.modules.user.entity.UserOpenid;
import com.msframe.modules.user.service.UserOpenidService;

/**
 * 第三方登录Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userOpenid")
public class UserOpenidController extends BaseController {

	@Autowired
	private UserOpenidService userOpenidService;
	
	@ModelAttribute
	public UserOpenid get(@RequestParam(required=false) String id) {
		UserOpenid entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userOpenidService.get(id);
		}
		if (entity == null){
			entity = new UserOpenid();
		}
		return entity;
	}
	
	/**
	 * 第三方登录列表页面
	 */
	@RequiresPermissions("user:userOpenid:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserOpenid userOpenid, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserOpenid> page = userOpenidService.findPage(new Page<UserOpenid>(request, response), userOpenid); 
		model.addAttribute("page", page);
		return "modules/user/userOpenidList";
	}

	/**
	 * 增加，编辑第三方登录表单页面
	 */
	@RequiresPermissions(value={"user:userOpenid:add","user:userOpenid:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserOpenid userOpenid, Model model) {
		model.addAttribute("userOpenid", userOpenid);
		return "modules/user/userOpenidForm";
	}

	/**
	 * 查看第三方登录表单页面
	 */
	@RequiresPermissions(value={"user:userOpenid:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(UserOpenid userOpenid, Model model) {
		model.addAttribute("userOpenid", userOpenid);
		return "modules/user/userOpenidDetails";
	}

	/**
	 * 保存第三方登录
	 */
	@RequiresPermissions(value={"user:userOpenid:add","user:userOpenid:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserOpenid userOpenid, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userOpenid)){
			return form(userOpenid, model);
		}
		if(!userOpenid.getIsNewRecord()){//编辑表单保存
			UserOpenid t = userOpenidService.get(userOpenid.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userOpenid, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userOpenidService.save(t);//保存
		}else{//新增表单保存
			userOpenidService.save(userOpenid);//保存
		}
		addMessage(redirectAttributes, "保存第三方登录成功");
		return "redirect:"+Global.getAdminPath()+"/user/userOpenid/?repage";
	}
	
	/**
	 * 删除第三方登录
	 */
	@RequiresPermissions("user:userOpenid:del")
	@RequestMapping(value = "delete")
	public String delete(UserOpenid userOpenid, RedirectAttributes redirectAttributes) {
		userOpenidService.delete(userOpenid);
		addMessage(redirectAttributes, "删除第三方登录成功");
		return "redirect:"+Global.getAdminPath()+"/user/userOpenid/?repage";
	}
	
	/**
	 * 批量删除第三方登录
	 */
	@RequiresPermissions("user:userOpenid:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			userOpenidService.delete(userOpenidService.get(id));
		}
		addMessage(redirectAttributes, "删除第三方登录成功");
		return "redirect:"+Global.getAdminPath()+"/user/userOpenid/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("user:userOpenid:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserOpenid userOpenid, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "第三方登录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserOpenid> page = userOpenidService.findPage(new Page<UserOpenid>(request, response, -1), userOpenid);
    		new ExportExcel("第三方登录", UserOpenid.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出第三方登录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userOpenid/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user:userOpenid:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserOpenid> list = ei.getDataList(UserOpenid.class);
			for (UserOpenid userOpenid : list){
				try{
					userOpenidService.save(userOpenid);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条第三方登录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条第三方登录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入第三方登录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userOpenid/?repage";
    }
	
	/**
	 * 下载导入第三方登录数据模板
	 */
	@RequiresPermissions("user:userOpenid:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "第三方登录数据导入模板.xlsx";
    		List<UserOpenid> list = Lists.newArrayList(); 
    		new ExportExcel("第三方登录数据", UserOpenid.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userOpenid/?repage";
    }
	
	
	

}
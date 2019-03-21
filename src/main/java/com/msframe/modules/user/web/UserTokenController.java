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
import com.msframe.modules.user.entity.UserToken;
import com.msframe.modules.user.service.UserTokenService;

/**
 * tokenController
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userToken")
public class UserTokenController extends BaseController {

	@Autowired
	private UserTokenService userTokenService;
	
	@ModelAttribute
	public UserToken get(@RequestParam(required=false) String id) {
		UserToken entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userTokenService.get(id);
		}
		if (entity == null){
			entity = new UserToken();
		}
		return entity;
	}
	
	/**
	 * token列表页面
	 */
	@RequiresPermissions("user:userToken:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserToken userToken, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserToken> page = userTokenService.findPage(new Page<UserToken>(request, response), userToken); 
		model.addAttribute("page", page);
		return "modules/user/userTokenList";
	}

	/**
	 * 增加，编辑token表单页面
	 */
	@RequiresPermissions(value={"user:userToken:add","user:userToken:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserToken userToken, Model model) {
		model.addAttribute("userToken", userToken);
		return "modules/user/userTokenForm";
	}

	/**
	 * 查看token表单页面
	 */
	@RequiresPermissions(value={"user:userToken:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(UserToken userToken, Model model) {
		model.addAttribute("userToken", userToken);
		return "modules/user/userTokenDetails";
	}

	/**
	 * 保存token
	 */
	@RequiresPermissions(value={"user:userToken:add","user:userToken:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserToken userToken, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userToken)){
			return form(userToken, model);
		}
		if(!userToken.getIsNewRecord()){//编辑表单保存
			UserToken t = userTokenService.get(userToken.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userToken, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userTokenService.save(t);//保存
		}else{//新增表单保存
			userTokenService.save(userToken);//保存
		}
		addMessage(redirectAttributes, "保存token成功");
		return "redirect:"+Global.getAdminPath()+"/user/userToken/?repage";
	}
	
	/**
	 * 删除token
	 */
	@RequiresPermissions("user:userToken:del")
	@RequestMapping(value = "delete")
	public String delete(UserToken userToken, RedirectAttributes redirectAttributes) {
		userTokenService.delete(userToken);
		addMessage(redirectAttributes, "删除token成功");
		return "redirect:"+Global.getAdminPath()+"/user/userToken/?repage";
	}
	
	/**
	 * 批量删除token
	 */
	@RequiresPermissions("user:userToken:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			userTokenService.delete(userTokenService.get(id));
		}
		addMessage(redirectAttributes, "删除token成功");
		return "redirect:"+Global.getAdminPath()+"/user/userToken/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("user:userToken:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserToken userToken, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "token"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserToken> page = userTokenService.findPage(new Page<UserToken>(request, response, -1), userToken);
    		new ExportExcel("token", UserToken.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出token记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userToken/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user:userToken:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserToken> list = ei.getDataList(UserToken.class);
			for (UserToken userToken : list){
				try{
					userTokenService.save(userToken);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条token记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条token记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入token失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userToken/?repage";
    }
	
	/**
	 * 下载导入token数据模板
	 */
	@RequiresPermissions("user:userToken:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "token数据导入模板.xlsx";
    		List<UserToken> list = Lists.newArrayList(); 
    		new ExportExcel("token数据", UserToken.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userToken/?repage";
    }
	
	
	

}
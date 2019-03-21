/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.web;

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
import com.msframe.modules.bms.entity.BmsServices;
import com.msframe.modules.bms.service.BmsServicesService;

/**
 * 服务项目Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bms/bmsServices")
public class BmsServicesController extends BaseController {

	@Autowired
	private BmsServicesService bmsServicesService;
	
	@ModelAttribute
	public BmsServices get(@RequestParam(required=false) String id) {
		BmsServices entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bmsServicesService.get(id);
		}
		if (entity == null){
			entity = new BmsServices();
		}
		return entity;
	}
	
	/**
	 * 服务项目列表页面
	 */
	@RequiresPermissions("bms:bmsServices:list")
	@RequestMapping(value = {"list", ""})
	public String list(BmsServices bmsServices, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BmsServices> page = bmsServicesService.findPage(new Page<BmsServices>(request, response), bmsServices); 
		model.addAttribute("page", page);
		return "modules/bms/bmsServicesList";
	}

	/**
	 * 增加，编辑服务项目表单页面
	 */
	@RequiresPermissions(value={"bms:bmsServices:add","bms:bmsServices:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BmsServices bmsServices, Model model) {
		model.addAttribute("bmsServices", bmsServices);
		return "modules/bms/bmsServicesForm";
	}

	/**
	 * 查看服务项目表单页面
	 */
	@RequiresPermissions(value={"bms:bmsServices:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(BmsServices bmsServices, Model model) {
		model.addAttribute("bmsServices", bmsServices);
		return "modules/bms/bmsServicesDetails";
	}

	/**
	 * 保存服务项目
	 */
	@RequiresPermissions(value={"bms:bmsServices:add","bms:bmsServices:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BmsServices bmsServices, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bmsServices)){
			return form(bmsServices, model);
		}
		if(!bmsServices.getIsNewRecord()){//编辑表单保存
			BmsServices t = bmsServicesService.get(bmsServices.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bmsServices, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bmsServicesService.save(t);//保存
		}else{//新增表单保存
			bmsServicesService.save(bmsServices);//保存
		}
		addMessage(redirectAttributes, "保存服务项目成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsServices/?repage";
	}
	
	/**
	 * 删除服务项目
	 */
	@RequiresPermissions("bms:bmsServices:del")
	@RequestMapping(value = "delete")
	public String delete(BmsServices bmsServices, RedirectAttributes redirectAttributes) {
		bmsServicesService.delete(bmsServices);
		addMessage(redirectAttributes, "删除服务项目成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsServices/?repage";
	}
	
	/**
	 * 批量删除服务项目
	 */
	@RequiresPermissions("bms:bmsServices:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			bmsServicesService.delete(bmsServicesService.get(id));
		}
		addMessage(redirectAttributes, "删除服务项目成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsServices/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bms:bmsServices:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BmsServices bmsServices, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "服务项目"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BmsServices> page = bmsServicesService.findPage(new Page<BmsServices>(request, response, -1), bmsServices);
    		new ExportExcel("服务项目", BmsServices.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出服务项目记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsServices/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bms:bmsServices:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BmsServices> list = ei.getDataList(BmsServices.class);
			for (BmsServices bmsServices : list){
				try{
					bmsServicesService.save(bmsServices);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条服务项目记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条服务项目记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入服务项目失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsServices/?repage";
    }
	
	/**
	 * 下载导入服务项目数据模板
	 */
	@RequiresPermissions("bms:bmsServices:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "服务项目数据导入模板.xlsx";
    		List<BmsServices> list = Lists.newArrayList(); 
    		new ExportExcel("服务项目数据", BmsServices.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsServices/?repage";
    }
	
	
	

}
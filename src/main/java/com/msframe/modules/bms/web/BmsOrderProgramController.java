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
import com.msframe.modules.bms.entity.BmsOrderProgram;
import com.msframe.modules.bms.service.BmsOrderProgramService;

/**
 * 产品内容Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bms/bmsOrderProgram")
public class BmsOrderProgramController extends BaseController {

	@Autowired
	private BmsOrderProgramService bmsOrderProgramService;
	
	@ModelAttribute
	public BmsOrderProgram get(@RequestParam(required=false) String id) {
		BmsOrderProgram entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bmsOrderProgramService.get(id);
		}
		if (entity == null){
			entity = new BmsOrderProgram();
		}
		return entity;
	}
	
	/**
	 * 产品内容列表页面
	 */
	@RequiresPermissions("bms:bmsOrderProgram:list")
	@RequestMapping(value = {"list", ""})
	public String list(BmsOrderProgram bmsOrderProgram, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BmsOrderProgram> page = bmsOrderProgramService.findPage(new Page<BmsOrderProgram>(request, response), bmsOrderProgram); 
		model.addAttribute("page", page);
		return "modules/bms/bmsOrderProgramList";
	}

	/**
	 * 增加，编辑产品内容表单页面
	 */
	@RequiresPermissions(value={"bms:bmsOrderProgram:add","bms:bmsOrderProgram:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BmsOrderProgram bmsOrderProgram, Model model) {
		model.addAttribute("bmsOrderProgram", bmsOrderProgram);
		return "modules/bms/bmsOrderProgramForm";
	}

	/**
	 * 查看产品内容表单页面
	 */
	@RequiresPermissions(value={"bms:bmsOrderProgram:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(BmsOrderProgram bmsOrderProgram, Model model) {
		model.addAttribute("bmsOrderProgram", bmsOrderProgram);
		return "modules/bms/bmsOrderProgramDetails";
	}

	/**
	 * 保存产品内容
	 */
	@RequiresPermissions(value={"bms:bmsOrderProgram:add","bms:bmsOrderProgram:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BmsOrderProgram bmsOrderProgram, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bmsOrderProgram)){
			return form(bmsOrderProgram, model);
		}
		if(!bmsOrderProgram.getIsNewRecord()){//编辑表单保存
			BmsOrderProgram t = bmsOrderProgramService.get(bmsOrderProgram.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bmsOrderProgram, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bmsOrderProgramService.save(t);//保存
		}else{//新增表单保存
			bmsOrderProgramService.save(bmsOrderProgram);//保存
		}
		addMessage(redirectAttributes, "保存产品内容成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrderProgram/?repage";
	}
	
	/**
	 * 删除产品内容
	 */
	@RequiresPermissions("bms:bmsOrderProgram:del")
	@RequestMapping(value = "delete")
	public String delete(BmsOrderProgram bmsOrderProgram, RedirectAttributes redirectAttributes) {
		bmsOrderProgramService.delete(bmsOrderProgram);
		addMessage(redirectAttributes, "删除产品内容成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrderProgram/?repage";
	}
	
	/**
	 * 批量删除产品内容
	 */
	@RequiresPermissions("bms:bmsOrderProgram:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			bmsOrderProgramService.delete(bmsOrderProgramService.get(id));
		}
		addMessage(redirectAttributes, "删除产品内容成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrderProgram/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bms:bmsOrderProgram:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BmsOrderProgram bmsOrderProgram, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品内容"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BmsOrderProgram> page = bmsOrderProgramService.findPage(new Page<BmsOrderProgram>(request, response, -1), bmsOrderProgram);
    		new ExportExcel("产品内容", BmsOrderProgram.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出产品内容记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrderProgram/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bms:bmsOrderProgram:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BmsOrderProgram> list = ei.getDataList(BmsOrderProgram.class);
			for (BmsOrderProgram bmsOrderProgram : list){
				try{
					bmsOrderProgramService.save(bmsOrderProgram);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产品内容记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条产品内容记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入产品内容失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrderProgram/?repage";
    }
	
	/**
	 * 下载导入产品内容数据模板
	 */
	@RequiresPermissions("bms:bmsOrderProgram:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品内容数据导入模板.xlsx";
    		List<BmsOrderProgram> list = Lists.newArrayList(); 
    		new ExportExcel("产品内容数据", BmsOrderProgram.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrderProgram/?repage";
    }
	
	
	

}
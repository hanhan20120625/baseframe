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
import com.msframe.modules.bms.entity.BmsProductSrv;
import com.msframe.modules.bms.service.BmsProductSrvService;

/**
 * 产品服务信息Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bms/bmsProductService")
public class BmsProductSrvController extends BaseController {

	@Autowired
	private BmsProductSrvService bmsProductSrvService;
	
	@ModelAttribute
	public BmsProductSrv get(@RequestParam(required=false) String id) {
		BmsProductSrv entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bmsProductSrvService.get(id);
		}
		if (entity == null){
			entity = new BmsProductSrv();
		}
		return entity;
	}
	
	/**
	 * 产品服务信息列表页面
	 */
	@RequiresPermissions("bms:bmsProductService:list")
	@RequestMapping(value = {"list", ""})
	public String list(BmsProductSrv bmsProductService, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BmsProductSrv> page = bmsProductSrvService.findPage(new Page<BmsProductSrv>(request, response), bmsProductService); 
		model.addAttribute("page", page);
		return "modules/bms/bmsProductServiceList";
	}

	/**
	 * 增加，编辑产品服务信息表单页面
	 */
	@RequiresPermissions(value={"bms:bmsProductService:add","bms:bmsProductService:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BmsProductSrv bmsProductService, Model model) {
		model.addAttribute("bmsProductService", bmsProductService);
		return "modules/bms/bmsProductServiceForm";
	}

	/**
	 * 查看产品服务信息表单页面
	 */
	@RequiresPermissions(value={"bms:bmsProductService:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(BmsProductSrv bmsProductService, Model model) {
		model.addAttribute("bmsProductService", bmsProductService);
		return "modules/bms/bmsProductServiceDetails";
	}

	/**
	 * 保存产品服务信息
	 */
	@RequiresPermissions(value={"bms:bmsProductService:add","bms:bmsProductService:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BmsProductSrv bmsProductService, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bmsProductService)){
			return form(bmsProductService, model);
		}
		if(!bmsProductService.getIsNewRecord()){//编辑表单保存
			BmsProductSrv t = bmsProductSrvService.get(bmsProductService.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bmsProductService, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bmsProductSrvService.save(t);//保存
		}else{//新增表单保存
			bmsProductSrvService.save(bmsProductService);//保存
		}
		addMessage(redirectAttributes, "保存产品服务信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProductService/?repage";
	}
	
	/**
	 * 删除产品服务信息
	 */
	@RequiresPermissions("bms:bmsProductService:del")
	@RequestMapping(value = "delete")
	public String delete(BmsProductSrv bmsProductService, RedirectAttributes redirectAttributes) {
		bmsProductSrvService.delete(bmsProductService);
		addMessage(redirectAttributes, "删除产品服务信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProductService/?repage";
	}
	
	/**
	 * 批量删除产品服务信息
	 */
	@RequiresPermissions("bms:bmsProductService:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			bmsProductSrvService.delete(bmsProductSrvService.get(id));
		}
		addMessage(redirectAttributes, "删除产品服务信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProductService/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bms:bmsProductService:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BmsProductSrv bmsProductService, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品服务信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BmsProductSrv> page = bmsProductSrvService.findPage(new Page<BmsProductSrv>(request, response, -1), bmsProductService);
    		new ExportExcel("产品服务信息", BmsProductSrv.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出产品服务信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProductService/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bms:bmsProductService:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BmsProductSrv> list = ei.getDataList(BmsProductSrv.class);
			for (BmsProductSrv bmsProductService : list){
				try{
					bmsProductSrvService.save(bmsProductService);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产品服务信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条产品服务信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入产品服务信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProductService/?repage";
    }
	
	/**
	 * 下载导入产品服务信息数据模板
	 */
	@RequiresPermissions("bms:bmsProductService:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品服务信息数据导入模板.xlsx";
    		List<BmsProductSrv> list = Lists.newArrayList(); 
    		new ExportExcel("产品服务信息数据", BmsProductSrv.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProductService/?repage";
    }
	
	
	

}
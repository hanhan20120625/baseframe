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
import com.msframe.modules.bms.entity.BmsProgramSrv;
import com.msframe.modules.bms.service.BmsProgramSrvService;

/**
 * 影片绑定资源Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bms/bmsProgramService")
public class BmsProgramSrvController extends BaseController {

	@Autowired
	private BmsProgramSrvService bmsProgramSrvService;
	
	@ModelAttribute
	public BmsProgramSrv get(@RequestParam(required=false) String id) {
		BmsProgramSrv entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bmsProgramSrvService.get(id);
		}
		if (entity == null){
			entity = new BmsProgramSrv();
		}
		return entity;
	}
	
	/**
	 * 影片绑定资源列表页面
	 */
	@RequiresPermissions("bms:bmsProgramService:list")
	@RequestMapping(value = {"list", ""})
	public String list(BmsProgramSrv bmsProgramService, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BmsProgramSrv> page = bmsProgramSrvService.findPage(new Page<BmsProgramSrv>(request, response), bmsProgramService);
		model.addAttribute("page", page);
		return "modules/bms/bmsProgramServiceList";
	}

	/**
	 * 增加，编辑影片绑定资源表单页面
	 */
	@RequiresPermissions(value={"bms:bmsProgramService:add","bms:bmsProgramService:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BmsProgramSrv bmsProgramService, Model model) {
		model.addAttribute("bmsProgramService", bmsProgramService);
		return "modules/bms/bmsProgramServiceForm";
	}

	/**
	 * 查看影片绑定资源表单页面
	 */
	@RequiresPermissions(value={"bms:bmsProgramService:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(BmsProgramSrv bmsProgramService, Model model) {
		model.addAttribute("bmsProgramService", bmsProgramService);
		return "modules/bms/bmsProgramServiceDetails";
	}

	/**
	 * 保存影片绑定资源
	 */
	@RequiresPermissions(value={"bms:bmsProgramService:add","bms:bmsProgramService:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BmsProgramSrv bmsProgramService, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bmsProgramService)){
			return form(bmsProgramService, model);
		}
		if(!bmsProgramService.getIsNewRecord()){//编辑表单保存
			BmsProgramSrv t = bmsProgramSrvService.get(bmsProgramService.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bmsProgramService, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bmsProgramSrvService.save(t);//保存
		}else{//新增表单保存
			bmsProgramSrvService.save(bmsProgramService);//保存
		}
		addMessage(redirectAttributes, "保存影片绑定资源成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProgramService/?repage";
	}
	
	/**
	 * 删除影片绑定资源
	 */
	@RequiresPermissions("bms:bmsProgramService:del")
	@RequestMapping(value = "delete")
	public String delete(BmsProgramSrv bmsProgramService, RedirectAttributes redirectAttributes) {
		bmsProgramSrvService.delete(bmsProgramService);
		addMessage(redirectAttributes, "删除影片绑定资源成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProgramService/?repage";
	}
	
	/**
	 * 批量删除影片绑定资源
	 */
	@RequiresPermissions("bms:bmsProgramService:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			bmsProgramSrvService.delete(bmsProgramSrvService.get(id));
		}
		addMessage(redirectAttributes, "删除影片绑定资源成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProgramService/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bms:bmsProgramService:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BmsProgramSrv bmsProgramService, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片绑定资源"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BmsProgramSrv> page = bmsProgramSrvService.findPage(new Page<BmsProgramSrv>(request, response, -1), bmsProgramService);
    		new ExportExcel("影片绑定资源", BmsProgramSrv.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出影片绑定资源记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProgramService/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bms:bmsProgramService:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BmsProgramSrv> list = ei.getDataList(BmsProgramSrv.class);
			for (BmsProgramSrv bmsProgramService : list){
				try{
					bmsProgramSrvService.save(bmsProgramService);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条影片绑定资源记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条影片绑定资源记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入影片绑定资源失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProgramService/?repage";
    }
	
	/**
	 * 下载导入影片绑定资源数据模板
	 */
	@RequiresPermissions("bms:bmsProgramService:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片绑定资源数据导入模板.xlsx";
    		List<BmsProgramSrv> list = Lists.newArrayList();
    		new ExportExcel("影片绑定资源数据", BmsProgramSrv.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProgramService/?repage";
    }
	
	
	

}
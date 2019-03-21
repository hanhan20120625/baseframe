/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

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
import com.msframe.modules.cms.entity.CmsProgramRegion;
import com.msframe.modules.cms.service.CmsProgramRegionService;

/**
 * 视频区域关联Controller
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsProgramRegion")
public class CmsProgramRegionController extends BaseController {

	@Autowired
	private CmsProgramRegionService cmsProgramRegionService;
	
	@ModelAttribute
	public CmsProgramRegion get(@RequestParam(required=false) String id) {
		CmsProgramRegion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsProgramRegionService.get(id);
		}
		if (entity == null){
			entity = new CmsProgramRegion();
		}
		return entity;
	}
	
	/**
	 * 视频区域关联列表页面
	 */
	@RequiresPermissions("cms:cmsProgramRegion:list")
	@RequestMapping(value = {"list", ""})
	public String list(CmsProgramRegion cmsProgramRegion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsProgramRegion> page = cmsProgramRegionService.findPage(new Page<CmsProgramRegion>(request, response), cmsProgramRegion); 
		model.addAttribute("page", page);
		return "modules/cms/cmsProgramRegionList";
	}

	/**
	 * 增加，编辑视频区域关联表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramRegion:add","cms:cmsProgramRegion:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsProgramRegion cmsProgramRegion, Model model) {
		model.addAttribute("cmsProgramRegion", cmsProgramRegion);
		return "modules/cms/cmsProgramRegionForm";
	}

	/**
	 * 查看视频区域关联表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramRegion:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(CmsProgramRegion cmsProgramRegion, Model model) {
		model.addAttribute("cmsProgramRegion", cmsProgramRegion);
		return "modules/cms/cmsProgramRegionDetails";
	}

	/**
	 * 保存视频区域关联
	 */
	@RequiresPermissions(value={"cms:cmsProgramRegion:add","cms:cmsProgramRegion:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsProgramRegion cmsProgramRegion, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsProgramRegion)){
			return form(cmsProgramRegion, model);
		}
		if(!cmsProgramRegion.getIsNewRecord()){//编辑表单保存
			CmsProgramRegion t = cmsProgramRegionService.get(cmsProgramRegion.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(cmsProgramRegion, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			cmsProgramRegionService.save(t);//保存
		}else{//新增表单保存
			cmsProgramRegionService.save(cmsProgramRegion);//保存
		}
		addMessage(redirectAttributes, "保存视频区域关联成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramRegion/?repage";
	}
	
	/**
	 * 删除视频区域关联
	 */
	@RequiresPermissions("cms:cmsProgramRegion:del")
	@RequestMapping(value = "delete")
	public String delete(CmsProgramRegion cmsProgramRegion, RedirectAttributes redirectAttributes) {
		cmsProgramRegionService.delete(cmsProgramRegion);
		addMessage(redirectAttributes, "删除视频区域关联成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramRegion/?repage";
	}
	
	/**
	 * 批量删除视频区域关联
	 */
	@RequiresPermissions("cms:cmsProgramRegion:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			cmsProgramRegionService.delete(cmsProgramRegionService.get(id));
		}
		addMessage(redirectAttributes, "删除视频区域关联成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramRegion/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("cms:cmsProgramRegion:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CmsProgramRegion cmsProgramRegion, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "视频区域关联"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsProgramRegion> page = cmsProgramRegionService.findPage(new Page<CmsProgramRegion>(request, response, -1), cmsProgramRegion);
    		new ExportExcel("视频区域关联", CmsProgramRegion.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出视频区域关联记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramRegion/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsProgramRegion:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsProgramRegion> list = ei.getDataList(CmsProgramRegion.class);
			for (CmsProgramRegion cmsProgramRegion : list){
				try{
					cmsProgramRegionService.save(cmsProgramRegion);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条视频区域关联记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条视频区域关联记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入视频区域关联失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramRegion/?repage";
    }
	
	/**
	 * 下载导入视频区域关联数据模板
	 */
	@RequiresPermissions("cms:cmsProgramRegion:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "视频区域关联数据导入模板.xlsx";
    		List<CmsProgramRegion> list = Lists.newArrayList(); 
    		new ExportExcel("视频区域关联数据", CmsProgramRegion.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramRegion/?repage";
    }
	
	
	

}
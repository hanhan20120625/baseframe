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
import com.msframe.modules.cms.entity.CmsProgramCategory;
import com.msframe.modules.cms.service.CmsProgramCategoryService;

/**
 * 影片与栏目关联Controller
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsProgramCategory")
public class CmsProgramCategoryController extends BaseController {

	@Autowired
	private CmsProgramCategoryService cmsProgramCategoryService;
	
	@ModelAttribute
	public CmsProgramCategory get(@RequestParam(required=false) String id) {
		CmsProgramCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsProgramCategoryService.get(id);
		}
		if (entity == null){
			entity = new CmsProgramCategory();
		}
		return entity;
	}
	
	/**
	 * 影片与栏目关联列表页面
	 */
	@RequiresPermissions("cms:cmsProgramCategory:list")
	@RequestMapping(value = {"list", ""})
	public String list(CmsProgramCategory cmsProgramCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsProgramCategory> page = cmsProgramCategoryService.findPage(new Page<CmsProgramCategory>(request, response), cmsProgramCategory); 
		model.addAttribute("page", page);
		return "modules/cms/cmsProgramCategoryList";
	}

	/**
	 * 增加，编辑影片与栏目关联表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramCategory:add","cms:cmsProgramCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsProgramCategory cmsProgramCategory, Model model) {
		model.addAttribute("cmsProgramCategory", cmsProgramCategory);
		return "modules/cms/cmsProgramCategoryForm";
	}

	/**
	 * 查看影片与栏目关联表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramCategory:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(CmsProgramCategory cmsProgramCategory, Model model) {
		model.addAttribute("cmsProgramCategory", cmsProgramCategory);
		return "modules/cms/cmsProgramCategoryDetails";
	}

	/**
	 * 保存影片与栏目关联
	 */
	@RequiresPermissions(value={"cms:cmsProgramCategory:add","cms:cmsProgramCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsProgramCategory cmsProgramCategory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsProgramCategory)){
			return form(cmsProgramCategory, model);
		}
		if(!cmsProgramCategory.getIsNewRecord()){//编辑表单保存
			CmsProgramCategory t = cmsProgramCategoryService.get(cmsProgramCategory.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(cmsProgramCategory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			cmsProgramCategoryService.save(t);//保存
		}else{//新增表单保存
			cmsProgramCategoryService.save(cmsProgramCategory);//保存
		}
		addMessage(redirectAttributes, "保存影片与栏目关联成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramCategory/?repage";
	}
	
	/**
	 * 删除影片与栏目关联
	 */
	@RequiresPermissions("cms:cmsProgramCategory:del")
	@RequestMapping(value = "delete")
	public String delete(CmsProgramCategory cmsProgramCategory, RedirectAttributes redirectAttributes) {
		cmsProgramCategoryService.delete(cmsProgramCategory);
		addMessage(redirectAttributes, "删除影片与栏目关联成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramCategory/?repage";
	}
	
	/**
	 * 批量删除影片与栏目关联
	 */
	@RequiresPermissions("cms:cmsProgramCategory:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			cmsProgramCategoryService.delete(cmsProgramCategoryService.get(id));
		}
		addMessage(redirectAttributes, "删除影片与栏目关联成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramCategory/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("cms:cmsProgramCategory:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CmsProgramCategory cmsProgramCategory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片与栏目关联"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsProgramCategory> page = cmsProgramCategoryService.findPage(new Page<CmsProgramCategory>(request, response, -1), cmsProgramCategory);
    		new ExportExcel("影片与栏目关联", CmsProgramCategory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出影片与栏目关联记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramCategory/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsProgramCategory:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsProgramCategory> list = ei.getDataList(CmsProgramCategory.class);
			for (CmsProgramCategory cmsProgramCategory : list){
				try{
					cmsProgramCategoryService.save(cmsProgramCategory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条影片与栏目关联记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条影片与栏目关联记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入影片与栏目关联失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramCategory/?repage";
    }
	
	/**
	 * 下载导入影片与栏目关联数据模板
	 */
	@RequiresPermissions("cms:cmsProgramCategory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片与栏目关联数据导入模板.xlsx";
    		List<CmsProgramCategory> list = Lists.newArrayList(); 
    		new ExportExcel("影片与栏目关联数据", CmsProgramCategory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramCategory/?repage";
    }
	
	
	

}
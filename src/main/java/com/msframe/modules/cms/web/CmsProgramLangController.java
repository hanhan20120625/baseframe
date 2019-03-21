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
import com.msframe.modules.cms.entity.CmsProgramLang;
import com.msframe.modules.cms.service.CmsProgramLangService;

/**
 * 影片关联语言Controller
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsProgramLang")
public class CmsProgramLangController extends BaseController {

	@Autowired
	private CmsProgramLangService cmsProgramLangService;
	
	@ModelAttribute
	public CmsProgramLang get(@RequestParam(required=false) String id) {
		CmsProgramLang entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsProgramLangService.get(id);
		}
		if (entity == null){
			entity = new CmsProgramLang();
		}
		return entity;
	}
	
	/**
	 * 影片关联语言列表页面
	 */
	@RequiresPermissions("cms:cmsProgramLang:list")
	@RequestMapping(value = {"list", ""})
	public String list(CmsProgramLang cmsProgramLang, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsProgramLang> page = cmsProgramLangService.findPage(new Page<CmsProgramLang>(request, response), cmsProgramLang); 
		model.addAttribute("page", page);
		return "modules/cms/cmsProgramLangList";
	}

	/**
	 * 增加，编辑影片关联语言表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramLang:add","cms:cmsProgramLang:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsProgramLang cmsProgramLang, Model model) {
		model.addAttribute("cmsProgramLang", cmsProgramLang);
		return "modules/cms/cmsProgramLangForm";
	}

	/**
	 * 查看影片关联语言表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramLang:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(CmsProgramLang cmsProgramLang, Model model) {
		model.addAttribute("cmsProgramLang", cmsProgramLang);
		return "modules/cms/cmsProgramLangDetails";
	}

	/**
	 * 保存影片关联语言
	 */
	@RequiresPermissions(value={"cms:cmsProgramLang:add","cms:cmsProgramLang:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsProgramLang cmsProgramLang, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsProgramLang)){
			return form(cmsProgramLang, model);
		}
		if(!cmsProgramLang.getIsNewRecord()){//编辑表单保存
			CmsProgramLang t = cmsProgramLangService.get(cmsProgramLang.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(cmsProgramLang, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			cmsProgramLangService.save(t);//保存
		}else{//新增表单保存
			cmsProgramLangService.save(cmsProgramLang);//保存
		}
		addMessage(redirectAttributes, "保存影片关联语言成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramLang/?repage";
	}
	
	/**
	 * 删除影片关联语言
	 */
	@RequiresPermissions("cms:cmsProgramLang:del")
	@RequestMapping(value = "delete")
	public String delete(CmsProgramLang cmsProgramLang, RedirectAttributes redirectAttributes) {
		cmsProgramLangService.delete(cmsProgramLang);
		addMessage(redirectAttributes, "删除影片关联语言成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramLang/?repage";
	}
	
	/**
	 * 批量删除影片关联语言
	 */
	@RequiresPermissions("cms:cmsProgramLang:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			cmsProgramLangService.delete(cmsProgramLangService.get(id));
		}
		addMessage(redirectAttributes, "删除影片关联语言成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramLang/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("cms:cmsProgramLang:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CmsProgramLang cmsProgramLang, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片关联语言"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsProgramLang> page = cmsProgramLangService.findPage(new Page<CmsProgramLang>(request, response, -1), cmsProgramLang);
    		new ExportExcel("影片关联语言", CmsProgramLang.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出影片关联语言记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramLang/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsProgramLang:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsProgramLang> list = ei.getDataList(CmsProgramLang.class);
			for (CmsProgramLang cmsProgramLang : list){
				try{
					cmsProgramLangService.save(cmsProgramLang);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条影片关联语言记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条影片关联语言记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入影片关联语言失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramLang/?repage";
    }
	
	/**
	 * 下载导入影片关联语言数据模板
	 */
	@RequiresPermissions("cms:cmsProgramLang:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片关联语言数据导入模板.xlsx";
    		List<CmsProgramLang> list = Lists.newArrayList(); 
    		new ExportExcel("影片关联语言数据", CmsProgramLang.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramLang/?repage";
    }
	
	
	

}
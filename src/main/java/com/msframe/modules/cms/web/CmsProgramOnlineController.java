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
import com.msframe.modules.cms.entity.CmsProgramOnline;
import com.msframe.modules.cms.service.CmsProgramOnlineService;

/**
 * 影片上上线记录Controller
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsProgramOnline")
public class CmsProgramOnlineController extends BaseController {

	@Autowired
	private CmsProgramOnlineService cmsProgramOnlineService;
	
	@ModelAttribute
	public CmsProgramOnline get(@RequestParam(required=false) String id) {
		CmsProgramOnline entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsProgramOnlineService.get(id);
		}
		if (entity == null){
			entity = new CmsProgramOnline();
		}
		return entity;
	}
	
	/**
	 * 影片上上线记录列表页面
	 */
	@RequiresPermissions("cms:cmsProgramOnline:list")
	@RequestMapping(value = {"list", ""})
	public String list(CmsProgramOnline cmsProgramOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsProgramOnline> page = cmsProgramOnlineService.findPage(new Page<CmsProgramOnline>(request, response), cmsProgramOnline); 
		model.addAttribute("page", page);
		return "modules/cms/cmsProgramOnlineList";
	}

	/**
	 * 增加，编辑影片上上线记录表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramOnline:add","cms:cmsProgramOnline:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsProgramOnline cmsProgramOnline, Model model) {
		model.addAttribute("cmsProgramOnline", cmsProgramOnline);
		return "modules/cms/cmsProgramOnlineForm";
	}

	/**
	 * 查看影片上上线记录表单页面
	 */
	@RequiresPermissions(value={"cms:cmsProgramOnline:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(CmsProgramOnline cmsProgramOnline, Model model) {
		model.addAttribute("cmsProgramOnline", cmsProgramOnline);
		return "modules/cms/cmsProgramOnlineDetails";
	}

	/**
	 * 保存影片上上线记录
	 */
	@RequiresPermissions(value={"cms:cmsProgramOnline:add","cms:cmsProgramOnline:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsProgramOnline cmsProgramOnline, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsProgramOnline)){
			return form(cmsProgramOnline, model);
		}
		if(!cmsProgramOnline.getIsNewRecord()){//编辑表单保存
			CmsProgramOnline t = cmsProgramOnlineService.get(cmsProgramOnline.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(cmsProgramOnline, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			cmsProgramOnlineService.save(t);//保存
		}else{//新增表单保存
			cmsProgramOnlineService.save(cmsProgramOnline);//保存
		}
		addMessage(redirectAttributes, "保存影片上上线记录成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramOnline/?repage";
	}
	
	/**
	 * 删除影片上上线记录
	 */
	@RequiresPermissions("cms:cmsProgramOnline:del")
	@RequestMapping(value = "delete")
	public String delete(CmsProgramOnline cmsProgramOnline, RedirectAttributes redirectAttributes) {
		cmsProgramOnlineService.delete(cmsProgramOnline);
		addMessage(redirectAttributes, "删除影片上上线记录成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramOnline/?repage";
	}
	
	/**
	 * 批量删除影片上上线记录
	 */
	@RequiresPermissions("cms:cmsProgramOnline:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			cmsProgramOnlineService.delete(cmsProgramOnlineService.get(id));
		}
		addMessage(redirectAttributes, "删除影片上上线记录成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramOnline/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("cms:cmsProgramOnline:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CmsProgramOnline cmsProgramOnline, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片上上线记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsProgramOnline> page = cmsProgramOnlineService.findPage(new Page<CmsProgramOnline>(request, response, -1), cmsProgramOnline);
    		new ExportExcel("影片上上线记录", CmsProgramOnline.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出影片上上线记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramOnline/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsProgramOnline:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsProgramOnline> list = ei.getDataList(CmsProgramOnline.class);
			for (CmsProgramOnline cmsProgramOnline : list){
				try{
					cmsProgramOnlineService.save(cmsProgramOnline);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条影片上上线记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条影片上上线记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入影片上上线记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramOnline/?repage";
    }
	
	/**
	 * 下载导入影片上上线记录数据模板
	 */
	@RequiresPermissions("cms:cmsProgramOnline:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片上上线记录数据导入模板.xlsx";
    		List<CmsProgramOnline> list = Lists.newArrayList(); 
    		new ExportExcel("影片上上线记录数据", CmsProgramOnline.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsProgramOnline/?repage";
    }
	
	
	

}
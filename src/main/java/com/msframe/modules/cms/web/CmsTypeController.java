/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.msframe.modules.cms.entity.CmsProgramType;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import com.msframe.modules.cms.entity.CmsType;
import com.msframe.modules.cms.service.CmsTypeService;

/**
 * 影片类型Controller
 * @author lpz
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsType")
public class CmsTypeController extends BaseController {

	@Autowired
	private CmsTypeService cmsTypeService;
	
	@ModelAttribute
	public CmsType get(@RequestParam(required=false) String id) {
		CmsType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsTypeService.get(id);
		}
		if (entity == null){
			entity = new CmsType();
		}
		return entity;
	}
	
	/**
	 * 影片类型列表页面
	 */
	@RequiresPermissions("cms:cmsType:list")
	@RequestMapping(value = {"list", ""})
	public String list(CmsType cmsType, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
		Page<CmsType> page = cmsTypeService.findPage(new Page<CmsType>(request, response), cmsType); 
		model.addAttribute("page", page);
		model.addAttribute("isSearch", isSearch);
		return "modules/cms/cmsTypeList";
	}

	/**
	 * 增加，编辑影片类型表单页面
	 */
	@RequiresPermissions(value={"cms:cmsType:add","cms:cmsType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsType cmsType, Model model) {
		model.addAttribute("cmsType", cmsType);
		if(StringUtils.isBlank(cmsType.getId())){
			List<CmsType> list = cmsTypeService.findList(new CmsType());
			if (list.size() > 0 && list.get(0).getSort() != null) {
				cmsType.setSort(list.get(0).getSort() + 10);
			}else {
				cmsType.setSort(10L);
			}
		}
		return "modules/cms/cmsTypeForm";
	}

	/**
	 * 查看影片类型表单页面
	 */
	@RequiresPermissions(value={"cms:cmsType:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(CmsType cmsType, Model model) {
		model.addAttribute("cmsType", cmsType);
		return "modules/cms/cmsTypeDetails";
	}

	/**
	 * 保存影片类型
	 */
	@RequiresPermissions(value={"cms:cmsType:add","cms:cmsType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsType cmsType, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsType)){
			return form(cmsType, model);
		}
		if(!cmsType.getIsNewRecord()){//编辑表单保存
			CmsType t = cmsTypeService.get(cmsType.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(cmsType, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			cmsTypeService.save(t);//保存
		}else{//新增表单保存
			cmsTypeService.save(cmsType);//保存
		}
		addMessage(redirectAttributes, "保存影片类型成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsType/?repage";
	}
	
	/**
	 * 删除影片类型
	 */
	@RequiresPermissions("cms:cmsType:del")
	@RequestMapping(value = "delete")
	public String delete(CmsType cmsType, RedirectAttributes redirectAttributes) {
		cmsTypeService.delete(cmsType);
		addMessage(redirectAttributes, "删除影片类型成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsType/?repage";
	}
	
	/**
	 * 批量删除影片类型
	 */
	@RequiresPermissions("cms:cmsType:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			cmsTypeService.delete(cmsTypeService.get(id));
		}
		addMessage(redirectAttributes, "删除影片类型成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsType/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("cms:cmsType:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CmsType cmsType, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsType> page = cmsTypeService.findPage(new Page<CmsType>(request, response, -1), cmsType);
    		new ExportExcel("影片类型", CmsType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出影片类型记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsType/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsType:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsType> list = ei.getDataList(CmsType.class);
			for (CmsType cmsType : list){
				try{
					cmsTypeService.save(cmsType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条影片类型记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条影片类型记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入影片类型失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsType/?repage";
    }
	
	/**
	 * 下载导入影片类型数据模板
	 */
	@RequiresPermissions("cms:cmsType:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "影片类型数据导入模板.xlsx";
    		List<CmsType> list = Lists.newArrayList(); 
    		new ExportExcel("影片类型数据", CmsType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsType/?repage";
    }

	/**
	 * @param cmsType
	 * @param url
	 * @param fieldLabels
	 * @param fieldKeys
	 * @param searchLabel
	 * @param searchKey
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author leon
	 * @date 2018/11/19
	 * @description 选择影片类型
	 */
	@RequestMapping(value = "selectCmsType")
	public String selectCmsType(CmsType cmsType, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsType> page = cmsTypeService.findPage(new Page<CmsType>(request, response), cmsType);
		model.addAttribute("labelNames", fieldLabels.split(";"));
		model.addAttribute("labelValues", fieldKeys.split(";"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", cmsType);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}

	/**
	 * 排序功能
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeSort")
	public String changeSort(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, Object> queryPreviousSortMap = new HashMap<>();
		queryPreviousSortMap.put("status", request.getParameter("status"));
		queryPreviousSortMap.put("name", request.getParameter("name"));
		queryPreviousSortMap.put("categoryId", request.getParameter("categoryId"));
		CmsType cmsType = get(id);
		boolean result = cmsTypeService.changeSort(cmsType.getId(), cmsType.getSort(), type, queryPreviousSortMap);
		return String.valueOf(result);
	}
}
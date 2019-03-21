/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.msframe.modules.bms.entity.BmsProduct;
import com.msframe.modules.bms.service.BmsProductService;

/**
 * 产品信息Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bms/bmsProduct")
public class BmsProductController extends BaseController {

	@Autowired
	private BmsProductService bmsProductService;
	
	@ModelAttribute
	public BmsProduct get(@RequestParam(required=false) String id) {
		BmsProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bmsProductService.get(id);
		}
		if (entity == null){
			entity = new BmsProduct();
		}
		return entity;
	}
	
	/**
	 * 产品信息列表页面
	 */
	@RequiresPermissions("bms:bmsProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list(BmsProduct bmsProduct, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
		Page<BmsProduct> page = bmsProductService.findPage(new Page<BmsProduct>(request, response), bmsProduct); 
		model.addAttribute("page", page);
		model.addAttribute("isSearch", isSearch);
		return "modules/bms/bmsProductList";
	}

	/**
	 * 增加，编辑产品信息表单页面
	 */
	@RequiresPermissions(value={"bms:bmsProduct:add","bms:bmsProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BmsProduct bmsProduct, Model model) {
		model.addAttribute("bmsProduct", bmsProduct);
		if(StringUtils.isBlank(bmsProduct.getId())){
			List<BmsProduct> list = bmsProductService.findList(new BmsProduct());
			if (list.size() > 0 && list.get(0).getSort() != null) {
				bmsProduct.setSort(list.get(0).getSort() + 10);
			}else {
				bmsProduct.setSort(10L);
			}
		}
		return "modules/bms/bmsProductForm";
	}

	/**
	 * 查看产品信息表单页面
	 */
	@RequiresPermissions(value={"bms:bmsProduct:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(BmsProduct bmsProduct, Model model) {
		model.addAttribute("bmsProduct", bmsProduct);
		return "modules/bms/bmsProductDetails";
	}

	/**
	 * 保存产品信息
	 */
	@RequiresPermissions(value={"bms:bmsProduct:add","bms:bmsProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BmsProduct bmsProduct, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bmsProduct)){
			return form(bmsProduct, model);
		}
		if(!bmsProduct.getIsNewRecord()){//编辑表单保存
			BmsProduct t = bmsProductService.get(bmsProduct.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bmsProduct, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bmsProductService.save(t);//保存
		}else{//新增表单保存
			bmsProductService.save(bmsProduct);//保存
		}
		addMessage(redirectAttributes, "保存产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProduct/?repage";
	}
	
	/**
	 * 删除产品信息
	 */
	@RequiresPermissions("bms:bmsProduct:del")
	@RequestMapping(value = "delete")
	public String delete(BmsProduct bmsProduct, RedirectAttributes redirectAttributes) {
		bmsProductService.delete(bmsProduct);
		addMessage(redirectAttributes, "删除产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProduct/?repage";
	}
	
	/**
	 * 批量删除产品信息
	 */
	@RequiresPermissions("bms:bmsProduct:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			bmsProductService.delete(bmsProductService.get(id));
		}
		addMessage(redirectAttributes, "删除产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProduct/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bms:bmsProduct:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BmsProduct bmsProduct, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BmsProduct> page = bmsProductService.findPage(new Page<BmsProduct>(request, response, -1), bmsProduct);
    		new ExportExcel("产品信息", BmsProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出产品信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProduct/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bms:bmsProduct:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BmsProduct> list = ei.getDataList(BmsProduct.class);
			for (BmsProduct bmsProduct : list){
				try{
					bmsProductService.save(bmsProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产品信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条产品信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入产品信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProduct/?repage";
    }
	
	/**
	 * 下载导入产品信息数据模板
	 */
	@RequiresPermissions("bms:bmsProduct:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品信息数据导入模板.xlsx";
    		List<BmsProduct> list = Lists.newArrayList(); 
    		new ExportExcel("产品信息数据", BmsProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsProduct/?repage";
    }


	/**
	 * 排序功能
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeSort")
    public String changeSort(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, Object> queryPreviousSortMap = new HashMap<>();
		queryPreviousSortMap.put("name", request.getParameter("name"));
		queryPreviousSortMap.put("productIdent", request.getParameter("productIdent"));
		queryPreviousSortMap.put("startTimeBegin", request.getParameter("startTimeBegin"));
		queryPreviousSortMap.put("startTimeEnd", request.getParameter("startTimeEnd"));
		queryPreviousSortMap.put("expireTimeBegin", request.getParameter("expireTimeBegin"));
		queryPreviousSortMap.put("expireTimeEnd", request.getParameter("expireTimeEnd"));
		queryPreviousSortMap.put("status", request.getParameter("status"));
		BmsProduct bmsProduct = get(id);
		boolean result = bmsProductService.changeSort(bmsProduct.getId(), bmsProduct.getSort(), type,queryPreviousSortMap);
		return String.valueOf(result);
	}
}
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
import com.msframe.modules.bms.entity.BmsOrder;
import com.msframe.modules.bms.service.BmsOrderService;

/**
 * 用户订单产品信息Controller
 * @author jjj
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bms/bmsOrder")
public class BmsOrderController extends BaseController {

	@Autowired
	private BmsOrderService bmsOrderService;
	
	@ModelAttribute
	public BmsOrder get(@RequestParam(required=false) String id) {
		BmsOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bmsOrderService.get(id);
		}
		if (entity == null){
			entity = new BmsOrder();
		}
		return entity;
	}
	
	/**
	 * 用户订单产品信息列表页面
	 */
	@RequiresPermissions("bms:bmsOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(BmsOrder bmsOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BmsOrder> page = bmsOrderService.findPage(new Page<BmsOrder>(request, response), bmsOrder); 
		model.addAttribute("page", page);
		return "modules/bms/bmsOrderList";
	}

	/**
	 * 增加，编辑用户订单产品信息表单页面
	 */
	@RequiresPermissions(value={"bms:bmsOrder:add","bms:bmsOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BmsOrder bmsOrder, Model model) {
		model.addAttribute("bmsOrder", bmsOrder);
		return "modules/bms/bmsOrderForm";
	}

	/**
	 * 查看用户订单产品信息表单页面
	 */
	@RequiresPermissions(value={"bms:bmsOrder:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(BmsOrder bmsOrder, Model model) {
		model.addAttribute("bmsOrder", bmsOrder);
		return "modules/bms/bmsOrderDetails";
	}

	/**
	 * 保存用户订单产品信息
	 */
	@RequiresPermissions(value={"bms:bmsOrder:add","bms:bmsOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BmsOrder bmsOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bmsOrder)){
			return form(bmsOrder, model);
		}
		if(!bmsOrder.getIsNewRecord()){//编辑表单保存
			BmsOrder t = bmsOrderService.get(bmsOrder.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bmsOrder, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bmsOrderService.save(t);//保存
		}else{//新增表单保存
			bmsOrderService.save(bmsOrder);//保存
		}
		addMessage(redirectAttributes, "保存用户订单产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrder/?repage";
	}
	
	/**
	 * 删除用户订单产品信息
	 */
	@RequiresPermissions("bms:bmsOrder:del")
	@RequestMapping(value = "delete")
	public String delete(BmsOrder bmsOrder, RedirectAttributes redirectAttributes) {
		bmsOrderService.delete(bmsOrder);
		addMessage(redirectAttributes, "删除用户订单产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrder/?repage";
	}
	
	/**
	 * 批量删除用户订单产品信息
	 */
	@RequiresPermissions("bms:bmsOrder:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			bmsOrderService.delete(bmsOrderService.get(id));
		}
		addMessage(redirectAttributes, "删除用户订单产品信息成功");
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrder/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bms:bmsOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BmsOrder bmsOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户订单产品信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BmsOrder> page = bmsOrderService.findPage(new Page<BmsOrder>(request, response, -1), bmsOrder);
    		new ExportExcel("用户订单产品信息", BmsOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户订单产品信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrder/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bms:bmsOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BmsOrder> list = ei.getDataList(BmsOrder.class);
			for (BmsOrder bmsOrder : list){
				try{
					bmsOrderService.save(bmsOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户订单产品信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户订单产品信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户订单产品信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrder/?repage";
    }
	
	/**
	 * 下载导入用户订单产品信息数据模板
	 */
	@RequiresPermissions("bms:bmsOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户订单产品信息数据导入模板.xlsx";
    		List<BmsOrder> list = Lists.newArrayList(); 
    		new ExportExcel("用户订单产品信息数据", BmsOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bms/bmsOrder/?repage";
    }
	
	
	

}
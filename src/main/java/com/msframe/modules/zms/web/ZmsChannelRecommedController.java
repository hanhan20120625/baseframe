/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.web;

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

import com.msframe.modules.zms.entity.ZmsChannel;
import com.msframe.modules.zms.entity.ZmsCategory;
import com.google.common.collect.Lists;
import com.msframe.common.utils.DateUtils;
import com.msframe.common.utils.MyBeanUtils;
import com.msframe.common.config.Global;
import com.msframe.common.persistence.Page;
import com.msframe.common.web.BaseController;
import com.msframe.common.utils.StringUtils;
import com.msframe.common.utils.excel.ExportExcel;
import com.msframe.common.utils.excel.ImportExcel;
import com.msframe.modules.zms.entity.ZmsChannelRecommed;
import com.msframe.modules.zms.service.ZmsChannelRecommedService;

/**
 * 频道推荐Controller
 * @author leon
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zms/zmsChannelRecommed")
public class ZmsChannelRecommedController extends BaseController {

	@Autowired
	private ZmsChannelRecommedService zmsChannelRecommedService;
	
	@ModelAttribute
	public ZmsChannelRecommed get(@RequestParam(required=false) String id) {
		ZmsChannelRecommed entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zmsChannelRecommedService.get(id);
		}
		if (entity == null){
			entity = new ZmsChannelRecommed();
		}
		return entity;
	}
	
	/**
	 * 频道推荐列表页面
	 */
	@RequiresPermissions("zms:zmsChannelRecommed:list")
	@RequestMapping(value = {"list", ""})
	public String list(ZmsChannelRecommed zmsChannelRecommed, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
		Page<ZmsChannelRecommed> page = zmsChannelRecommedService.findPage(new Page<ZmsChannelRecommed>(request, response), zmsChannelRecommed); 
		model.addAttribute("page", page);
		model.addAttribute("isSearch", isSearch);
		return "modules/zms/zmsChannelRecommedList";
	}

	/**
	 * 增加，编辑频道推荐表单页面
	 */
	@RequiresPermissions(value={"zms:zmsChannelRecommed:add","zms:zmsChannelRecommed:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ZmsChannelRecommed zmsChannelRecommed, Model model) {
		if(StringUtils.isBlank(zmsChannelRecommed.getId())){
			List<ZmsChannelRecommed> list = zmsChannelRecommedService.findList(new ZmsChannelRecommed());
			if (list.size() > 0 && list.get(0).getSort() != null) {
				zmsChannelRecommed.setSort(list.get(0).getSort() + 10);
			}else{
				zmsChannelRecommed.setSort(10L);
			}
		}
		model.addAttribute("zmsChannelRecommed", zmsChannelRecommed);
		return "modules/zms/zmsChannelRecommedForm";
	}

	/**
	 * 查看频道推荐表单页面
	 */
	@RequiresPermissions(value={"zms:zmsChannelRecommed:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(ZmsChannelRecommed zmsChannelRecommed, Model model) {
		model.addAttribute("zmsChannelRecommed", zmsChannelRecommed);
		return "modules/zms/zmsChannelRecommedDetails";
	}

	/**
	 * 保存频道推荐
	 */
	@RequiresPermissions(value={"zms:zmsChannelRecommed:add","zms:zmsChannelRecommed:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ZmsChannelRecommed zmsChannelRecommed, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, zmsChannelRecommed)){
			return form(zmsChannelRecommed, model);
		}
		if(!zmsChannelRecommed.getIsNewRecord()){//编辑表单保存
			ZmsChannelRecommed t = zmsChannelRecommedService.get(zmsChannelRecommed.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(zmsChannelRecommed, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			zmsChannelRecommedService.save(t);//保存
		}else{//新增表单保存
			zmsChannelRecommedService.save(zmsChannelRecommed);//保存
		}
		addMessage(redirectAttributes, "保存频道推荐成功");
		return "redirect:"+Global.getAdminPath()+"/zms/zmsChannelRecommed/?repage";
	}
	
	/**
	 * 删除频道推荐
	 */
	@RequiresPermissions("zms:zmsChannelRecommed:del")
	@RequestMapping(value = "delete")
	public String delete(ZmsChannelRecommed zmsChannelRecommed, RedirectAttributes redirectAttributes) {
		zmsChannelRecommedService.delete(zmsChannelRecommed);
		addMessage(redirectAttributes, "删除频道推荐成功");
		return "redirect:"+Global.getAdminPath()+"/zms/zmsChannelRecommed/?repage";
	}
	
	/**
	 * 批量删除频道推荐
	 */
	@RequiresPermissions("zms:zmsChannelRecommed:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			zmsChannelRecommedService.delete(zmsChannelRecommedService.get(id));
		}
		addMessage(redirectAttributes, "删除频道推荐成功");
		return "redirect:"+Global.getAdminPath()+"/zms/zmsChannelRecommed/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("zms:zmsChannelRecommed:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ZmsChannelRecommed zmsChannelRecommed, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "频道推荐"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ZmsChannelRecommed> page = zmsChannelRecommedService.findPage(new Page<ZmsChannelRecommed>(request, response, -1), zmsChannelRecommed);
    		new ExportExcel("频道推荐", ZmsChannelRecommed.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出频道推荐记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/zms/zmsChannelRecommed/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("zms:zmsChannelRecommed:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ZmsChannelRecommed> list = ei.getDataList(ZmsChannelRecommed.class);
			for (ZmsChannelRecommed zmsChannelRecommed : list){
				try{
					zmsChannelRecommedService.save(zmsChannelRecommed);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条频道推荐记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条频道推荐记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入频道推荐失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/zms/zmsChannelRecommed/?repage";
    }
	
	/**
	 * 下载导入频道推荐数据模板
	 */
	@RequiresPermissions("zms:zmsChannelRecommed:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "频道推荐数据导入模板.xlsx";
    		List<ZmsChannelRecommed> list = Lists.newArrayList(); 
    		new ExportExcel("频道推荐数据", ZmsChannelRecommed.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/zms/zmsChannelRecommed/?repage";
    }
	
	
	/**
	 * 选择频道编号
	 */
	@RequestMapping(value = "selectchannelId")
	public String selectchannelId(ZmsChannel channelId, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ZmsChannel> page = zmsChannelRecommedService.findPageBychannelId(new Page<ZmsChannel>(request, response),  channelId);
		model.addAttribute("labelNames", fieldLabels.split(";"));
		model.addAttribute("labelValues", fieldKeys.split(";"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", channelId);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	/**
	 * 选择隶属分类
	 */
	@RequestMapping(value = "selectcategoryId")
	public String selectcategoryId(ZmsCategory categoryId, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ZmsCategory> page = zmsChannelRecommedService.findPageBycategoryId(new Page<ZmsCategory>(request, response),  categoryId);
		model.addAttribute("labelNames", fieldLabels.split(";"));
		model.addAttribute("labelValues", fieldKeys.split(";"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", categoryId);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}


	/**
	 * 排序功能
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeSort")
	public String changeSort(HttpServletRequest request){
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, Object> queryPreviousSortMap = new HashMap<>();
		queryPreviousSortMap.put("channelId", request.getParameter("channelId"));
		queryPreviousSortMap.put("categoryId", request.getParameter("categoryId"));
		queryPreviousSortMap.put("status", request.getParameter("status"));
		ZmsChannelRecommed zmsChannelRecommed = get(id);
		boolean result = zmsChannelRecommedService.changeSort(zmsChannelRecommed.getId(), zmsChannelRecommed.getSort(), type,queryPreviousSortMap);
		return String.valueOf(result);
	}
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.msframe.common.utils.MyBeanUtils;
import com.msframe.common.config.Global;
import com.msframe.common.web.BaseController;
import com.msframe.common.utils.StringUtils;
import com.msframe.modules.zms.entity.ZmsCategory;
import com.msframe.modules.zms.service.ZmsCategoryService;

/**
 * 直播类别Controller
 * @author wlh
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/zms/zmsCategory")
public class ZmsCategoryController extends BaseController {

	@Autowired
	private ZmsCategoryService zmsCategoryService;
	
	@ModelAttribute
	public ZmsCategory get(@RequestParam(required=false) String id) {
		ZmsCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zmsCategoryService.get(id);
		}
		if (entity == null){
			entity = new ZmsCategory();
		}
		return entity;
	}
	
	/**
	 * 直播类别列表页面
	 */
	@RequiresPermissions("zms:zmsCategory:list")
	@RequestMapping(value = {"list", ""})
	public String list(ZmsCategory zmsCategory, HttpServletRequest request, HttpServletResponse response, Model model,boolean isSearch) {
		List<ZmsCategory> list = zmsCategoryService.findList(zmsCategory); 
		model.addAttribute("list", list);
		model.addAttribute("isSearch", isSearch);
		return "modules/zms/zmsCategoryList";
	}

	/**
	 * 增加，编辑直播类别表单页面
	 */
	@RequiresPermissions(value={"zms:zmsCategory:add","zms:zmsCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ZmsCategory zmsCategory, Model model) {
		/*if (zmsCategory.getParent()!=null && StringUtils.isNotBlank(zmsCategory.getParent().getId())){
			zmsCategory.setParent(zmsCategoryService.get(zmsCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(zmsCategory.getId())){
				ZmsCategory zmsCategoryChild = new ZmsCategory();
				zmsCategoryChild.setParent(new ZmsCategory(zmsCategory.getParent().getId()));
				List<ZmsCategory> list = zmsCategoryService.findList(zmsCategory); 
				if (list.size() > 0){
					zmsCategory.setSort(list.get(list.size()-1).getSort());
					if (zmsCategory.getSort() != null){
						zmsCategory.setSort(zmsCategory.getSort() + 30);
					}
				}
			}
		}
		if (zmsCategory.getSort() == null){
			zmsCategory.setSort(30);
		}*/
		if(StringUtils.isBlank(zmsCategory.getId())){
			List<ZmsCategory> list = zmsCategoryService.findList(new ZmsCategory());
			if (list.size() > 0 && list.get(0).getSort() != null) {
				zmsCategory.setSort(list.get(0).getSort() + 10);
			}else{
				zmsCategory.setSort(10);
			}
		}
		model.addAttribute("zmsCategory", zmsCategory);
		return "modules/zms/zmsCategoryForm";
	}

	/**
	 * 查看直播类别表单页面
	 */
	@RequiresPermissions(value={"zms:zmsCategory:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String viewDetails(ZmsCategory zmsCategory, Model model) {
		if (zmsCategory.getParent()!=null && StringUtils.isNotBlank(zmsCategory.getParent().getId())){
			zmsCategory.setParent(zmsCategoryService.get(zmsCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(zmsCategory.getId())){
				ZmsCategory zmsCategoryChild = new ZmsCategory();
				zmsCategoryChild.setParent(new ZmsCategory(zmsCategory.getParent().getId()));
				List<ZmsCategory> list = zmsCategoryService.findList(zmsCategory);
				if (list.size() > 0){
					zmsCategory.setSort(list.get(list.size()-1).getSort());
					if (zmsCategory.getSort() != null){
						zmsCategory.setSort(zmsCategory.getSort() + 30);
					}
				}
			}
		}
		if (zmsCategory.getSort() == null){
			zmsCategory.setSort(30);
		}
		model.addAttribute("zmsCategory", zmsCategory);
		return "modules/zms/zmsCategoryDetails";
	}

	/**
	 * 保存直播类别
	 */
	@RequiresPermissions(value={"zms:zmsCategory:add","zms:zmsCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ZmsCategory zmsCategory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		zmsCategory.setParent(new ZmsCategory());
		if (!beanValidator(model, zmsCategory)){
			return form(zmsCategory, model);
		}
		if(!zmsCategory.getIsNewRecord()){//编辑表单保存
			ZmsCategory t = zmsCategoryService.get(zmsCategory.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(zmsCategory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			zmsCategoryService.save(t);//保存
		}else{//新增表单保存
			zmsCategoryService.save(zmsCategory);//保存
		}
		addMessage(redirectAttributes, "保存直播类别成功");
		return "redirect:"+Global.getAdminPath()+"/zms/zmsCategory/?repage";
	}
	
	/**
	 * 删除直播类别
	 */
	@RequiresPermissions("zms:zmsCategory:del")
	@RequestMapping(value = "delete")
	public String delete(ZmsCategory zmsCategory, RedirectAttributes redirectAttributes) {
		zmsCategoryService.delete(zmsCategory);
		addMessage(redirectAttributes, "删除直播类别成功");
		return "redirect:"+Global.getAdminPath()+"/zms/zmsCategory/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ZmsCategory> list = zmsCategoryService.findList(new ZmsCategory());
		for (int i=0; i<list.size(); i++){
			ZmsCategory e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
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
	public String changeSort(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, Object> queryPreviousSortMap = new HashMap<>();
		queryPreviousSortMap.put("name", request.getParameter("name"));
		queryPreviousSortMap.put("status", request.getParameter("status"));
		ZmsCategory zmsCategory = get(id);
		boolean result = zmsCategoryService.changeSort(zmsCategory.getId(), Long.valueOf(zmsCategory.getSort()), type, queryPreviousSortMap);
		return String.valueOf(result);
	}
}
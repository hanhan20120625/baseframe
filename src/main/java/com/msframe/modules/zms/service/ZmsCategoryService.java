/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.service.TreeService;
import com.msframe.common.utils.StringUtils;
import com.msframe.modules.zms.entity.ZmsCategory;
import com.msframe.modules.zms.dao.ZmsCategoryDao;

/**
 * 直播类别Service
 * @author wlh
 * @version 2018-11-23
 */
@Service
@Transactional(readOnly = true)
public class ZmsCategoryService extends TreeService<ZmsCategoryDao, ZmsCategory> {

	public ZmsCategory get(String id) {
		return super.get(id);
	}
	
	public List<ZmsCategory> findList(ZmsCategory zmsCategory) {
		if (StringUtils.isNotBlank(zmsCategory.getParentIds())){
			zmsCategory.setParentIds(","+zmsCategory.getParentIds()+",");
		}
		return super.findList(zmsCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(ZmsCategory zmsCategory) {
		super.save(zmsCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ZmsCategory zmsCategory) {
		super.delete(zmsCategory);
	}
	
}
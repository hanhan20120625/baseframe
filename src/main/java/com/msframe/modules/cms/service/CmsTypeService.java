/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsType;
import com.msframe.modules.cms.dao.CmsTypeDao;

/**
 * 影片类型Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsTypeService extends CrudService<CmsTypeDao, CmsType> {

	@Override
	public CmsType get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsType> findList(CmsType cmsType) {
		return super.findList(cmsType);
	}
	
	@Override
	public Page<CmsType> findPage(Page<CmsType> page, CmsType cmsType) {
		return super.findPage(page, cmsType);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsType cmsType) {
		super.save(cmsType);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsType cmsType) {
		super.delete(cmsType);
	}
	
	
	
	
}
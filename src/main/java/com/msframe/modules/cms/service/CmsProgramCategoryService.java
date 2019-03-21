/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsProgramCategory;
import com.msframe.modules.cms.dao.CmsProgramCategoryDao;

/**
 * 影片与栏目关联Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsProgramCategoryService extends CrudService<CmsProgramCategoryDao, CmsProgramCategory> {

	@Override
	public CmsProgramCategory get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsProgramCategory> findList(CmsProgramCategory cmsProgramCategory) {
		return super.findList(cmsProgramCategory);
	}
	
	@Override
	public Page<CmsProgramCategory> findPage(Page<CmsProgramCategory> page, CmsProgramCategory cmsProgramCategory) {
		return super.findPage(page, cmsProgramCategory);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsProgramCategory cmsProgramCategory) {
		super.save(cmsProgramCategory);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsProgramCategory cmsProgramCategory) {
		super.delete(cmsProgramCategory);
	}
	
	
	
	
}
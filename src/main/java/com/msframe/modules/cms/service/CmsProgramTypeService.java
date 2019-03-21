/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsProgramType;
import com.msframe.modules.cms.dao.CmsProgramTypeDao;

/**
 * 影片关联类别Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsProgramTypeService extends CrudService<CmsProgramTypeDao, CmsProgramType> {

	@Override
	public CmsProgramType get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsProgramType> findList(CmsProgramType cmsProgramType) {
		return super.findList(cmsProgramType);
	}
	
	@Override
	public Page<CmsProgramType> findPage(Page<CmsProgramType> page, CmsProgramType cmsProgramType) {
		return super.findPage(page, cmsProgramType);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsProgramType cmsProgramType) {
		super.save(cmsProgramType);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsProgramType cmsProgramType) {
		super.delete(cmsProgramType);
	}
	
	
	
	
}
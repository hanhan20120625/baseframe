/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsProgramRegion;
import com.msframe.modules.cms.dao.CmsProgramRegionDao;

/**
 * 视频区域关联Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsProgramRegionService extends CrudService<CmsProgramRegionDao, CmsProgramRegion> {

	@Override
	public CmsProgramRegion get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsProgramRegion> findList(CmsProgramRegion cmsProgramRegion) {
		return super.findList(cmsProgramRegion);
	}
	
	@Override
	public Page<CmsProgramRegion> findPage(Page<CmsProgramRegion> page, CmsProgramRegion cmsProgramRegion) {
		return super.findPage(page, cmsProgramRegion);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsProgramRegion cmsProgramRegion) {
		super.save(cmsProgramRegion);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsProgramRegion cmsProgramRegion) {
		super.delete(cmsProgramRegion);
	}
	
	
	
	
}
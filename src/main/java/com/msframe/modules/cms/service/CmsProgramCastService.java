/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsProgramCast;
import com.msframe.modules.cms.dao.CmsProgramCastDao;

/**
 * 视频演员Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsProgramCastService extends CrudService<CmsProgramCastDao, CmsProgramCast> {

	@Override
	public CmsProgramCast get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsProgramCast> findList(CmsProgramCast cmsProgramCast) {
		return super.findList(cmsProgramCast);
	}
	
	@Override
	public Page<CmsProgramCast> findPage(Page<CmsProgramCast> page, CmsProgramCast cmsProgramCast) {
		return super.findPage(page, cmsProgramCast);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsProgramCast cmsProgramCast) {
		super.save(cmsProgramCast);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsProgramCast cmsProgramCast) {
		super.delete(cmsProgramCast);
	}
	
	
	
	
}
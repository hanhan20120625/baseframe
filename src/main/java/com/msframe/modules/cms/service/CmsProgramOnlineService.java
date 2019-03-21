/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsProgramOnline;
import com.msframe.modules.cms.dao.CmsProgramOnlineDao;

/**
 * 影片上上线记录Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsProgramOnlineService extends CrudService<CmsProgramOnlineDao, CmsProgramOnline> {

	@Override
	public CmsProgramOnline get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsProgramOnline> findList(CmsProgramOnline cmsProgramOnline) {
		return super.findList(cmsProgramOnline);
	}
	
	@Override
	public Page<CmsProgramOnline> findPage(Page<CmsProgramOnline> page, CmsProgramOnline cmsProgramOnline) {
		return super.findPage(page, cmsProgramOnline);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsProgramOnline cmsProgramOnline) {
		super.save(cmsProgramOnline);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsProgramOnline cmsProgramOnline) {
		super.delete(cmsProgramOnline);
	}
	
	
	
	
}
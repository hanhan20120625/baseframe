/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.dao.CmsCastDao;
import com.msframe.modules.cms.entity.CmsCast;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 演员信息Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsCastService extends CrudService<CmsCastDao, CmsCast> {

	@Override
	public CmsCast get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsCast> findList(CmsCast cmsCast) {
		return super.findList(cmsCast);
	}
	
	@Override
	public Page<CmsCast> findPage(Page<CmsCast> page, CmsCast cmsCast) {
		return super.findPage(page, cmsCast);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsCast cmsCast) {
		super.save(cmsCast);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsCast cmsCast) {
		super.delete(cmsCast);
	}
	
	
	
	
}
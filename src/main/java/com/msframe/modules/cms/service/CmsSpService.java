/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsSp;
import com.msframe.modules.cms.dao.CmsSpDao;

/**
 * 基本信息Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsSpService extends CrudService<CmsSpDao, CmsSp> {

	@Override
	public CmsSp get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsSp> findList(CmsSp cmsSp) {
		return super.findList(cmsSp);
	}
	
	@Override
	public Page<CmsSp> findPage(Page<CmsSp> page, CmsSp cmsSp) {
		return super.findPage(page, cmsSp);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsSp cmsSp) {
		super.save(cmsSp);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsSp cmsSp) {
		super.delete(cmsSp);
	}
	
	
	
	
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.bms.entity.BmsProgramSrv;
import com.msframe.modules.bms.dao.BmsProgramSrvDao;

/**
 * 影片绑定资源Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class BmsProgramSrvService extends CrudService<BmsProgramSrvDao, BmsProgramSrv> {

	@Override
	public BmsProgramSrv get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BmsProgramSrv> findList(BmsProgramSrv bmsProgramService) {
		return super.findList(bmsProgramService);
	}
	
	@Override
	public Page<BmsProgramSrv> findPage(Page<BmsProgramSrv> page, BmsProgramSrv bmsProgramService) {
		return super.findPage(page, bmsProgramService);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(BmsProgramSrv bmsProgramService) {
		super.save(bmsProgramService);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(BmsProgramSrv bmsProgramService) {
		super.delete(bmsProgramService);
	}
	
	
	
	
}
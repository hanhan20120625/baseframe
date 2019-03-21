/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.bms.entity.BmsServices;
import com.msframe.modules.bms.dao.BmsServicesDao;

/**
 * 服务项目Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class BmsServicesService extends CrudService<BmsServicesDao, BmsServices> {

	@Override
    public BmsServices get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BmsServices> findList(BmsServices bmsServices) {
		return super.findList(bmsServices);
	}
	
	@Override
    public Page<BmsServices> findPage(Page<BmsServices> page, BmsServices bmsServices) {
		return super.findPage(page, bmsServices);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BmsServices bmsServices) {
		super.save(bmsServices);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BmsServices bmsServices) {
		super.delete(bmsServices);
	}
	
	
	
	
}
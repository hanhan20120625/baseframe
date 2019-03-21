/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.bms.entity.BmsProductSrv;
import com.msframe.modules.bms.dao.BmsProductSrvDao;

/**
 * 产品服务信息Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class BmsProductSrvService extends CrudService<BmsProductSrvDao, BmsProductSrv> {

	@Override
    public BmsProductSrv get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BmsProductSrv> findList(BmsProductSrv bmsProductService) {
		return super.findList(bmsProductService);
	}
	
	@Override
    public Page<BmsProductSrv> findPage(Page<BmsProductSrv> page, BmsProductSrv bmsProductService) {
		return super.findPage(page, bmsProductService);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BmsProductSrv bmsProductService) {
		super.save(bmsProductService);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BmsProductSrv bmsProductService) {
		super.delete(bmsProductService);
	}
	
	
	
	
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.bms.entity.BmsProduct;
import com.msframe.modules.bms.dao.BmsProductDao;

/**
 * 产品信息Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class BmsProductService extends CrudService<BmsProductDao, BmsProduct> {

	@Override
    public BmsProduct get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BmsProduct> findList(BmsProduct bmsProduct) {
		return super.findList(bmsProduct);
	}
	
	@Override
    public Page<BmsProduct> findPage(Page<BmsProduct> page, BmsProduct bmsProduct) {
		return super.findPage(page, bmsProduct);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BmsProduct bmsProduct) {
		super.save(bmsProduct);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BmsProduct bmsProduct) {
		super.delete(bmsProduct);
	}
	
	
	
	
}
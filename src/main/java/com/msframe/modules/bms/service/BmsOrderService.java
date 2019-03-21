/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.bms.entity.BmsOrder;
import com.msframe.modules.bms.dao.BmsOrderDao;

/**
 * 用户订单产品信息Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class BmsOrderService extends CrudService<BmsOrderDao, BmsOrder> {

	@Override
    public BmsOrder get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BmsOrder> findList(BmsOrder bmsOrder) {
		return super.findList(bmsOrder);
	}
	
	@Override
    public Page<BmsOrder> findPage(Page<BmsOrder> page, BmsOrder bmsOrder) {
		return super.findPage(page, bmsOrder);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BmsOrder bmsOrder) {
		super.save(bmsOrder);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BmsOrder bmsOrder) {
		super.delete(bmsOrder);
	}
	
	
	
	
}
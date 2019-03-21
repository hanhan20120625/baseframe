/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.bms.entity.BmsOrderProgram;
import com.msframe.modules.bms.dao.BmsOrderProgramDao;

/**
 * 产品内容Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class BmsOrderProgramService extends CrudService<BmsOrderProgramDao, BmsOrderProgram> {

	@Override
    public BmsOrderProgram get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BmsOrderProgram> findList(BmsOrderProgram bmsOrderProgram) {
		return super.findList(bmsOrderProgram);
	}
	
	@Override
    public Page<BmsOrderProgram> findPage(Page<BmsOrderProgram> page, BmsOrderProgram bmsOrderProgram) {
		return super.findPage(page, bmsOrderProgram);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BmsOrderProgram bmsOrderProgram) {
		super.save(bmsOrderProgram);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BmsOrderProgram bmsOrderProgram) {
		super.delete(bmsOrderProgram);
	}
	
	
	
	
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.zms.entity.ZmsChannelCategory;
import com.msframe.modules.zms.dao.ZmsChannelCategoryDao;

/**
 * 直播频道类别Service
 * @author wlh
 * @version 2018-11-13
 */
@Service
@Transactional(readOnly = true)
public class ZmsChannelCategoryService extends CrudService<ZmsChannelCategoryDao, ZmsChannelCategory> {

	@Override
    public ZmsChannelCategory get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<ZmsChannelCategory> findList(ZmsChannelCategory zmsChannelCategory) {
		return super.findList(zmsChannelCategory);
	}
	
	@Override
    public Page<ZmsChannelCategory> findPage(Page<ZmsChannelCategory> page, ZmsChannelCategory zmsChannelCategory) {
		return super.findPage(page, zmsChannelCategory);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(ZmsChannelCategory zmsChannelCategory) {
		super.save(zmsChannelCategory);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(ZmsChannelCategory zmsChannelCategory) {
		super.delete(zmsChannelCategory);
	}
	
	
	
	
}
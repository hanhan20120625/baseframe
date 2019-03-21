/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.zms.entity.ZmsChannelStream;
import com.msframe.modules.zms.dao.ZmsChannelStreamDao;

/**
 * 直播节目流信息Service
 * @author wlh
 * @version 2018-11-13
 */
@Service
@Transactional(readOnly = true)
public class ZmsChannelStreamService extends CrudService<ZmsChannelStreamDao, ZmsChannelStream> {

	@Override
    public ZmsChannelStream get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<ZmsChannelStream> findList(ZmsChannelStream zmsChannelStream) {
		return super.findList(zmsChannelStream);
	}
	
	@Override
    public Page<ZmsChannelStream> findPage(Page<ZmsChannelStream> page, ZmsChannelStream zmsChannelStream) {
		return super.findPage(page, zmsChannelStream);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(ZmsChannelStream zmsChannelStream) {
		super.save(zmsChannelStream);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(ZmsChannelStream zmsChannelStream) {
		super.delete(zmsChannelStream);
	}
	
	
	
	
}
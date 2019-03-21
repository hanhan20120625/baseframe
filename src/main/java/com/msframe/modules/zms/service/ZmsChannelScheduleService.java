/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.zms.entity.ZmsChannelSchedule;
import com.msframe.modules.zms.dao.ZmsChannelScheduleDao;

/**
 * 直播节目单列表Service
 * @author wlh
 * @version 2018-11-13
 */
@Service
@Transactional(readOnly = true)
public class ZmsChannelScheduleService extends CrudService<ZmsChannelScheduleDao, ZmsChannelSchedule> {

	@Override
    public ZmsChannelSchedule get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<ZmsChannelSchedule> findList(ZmsChannelSchedule zmsChannelSchedule) {
		return super.findList(zmsChannelSchedule);
	}
	
	@Override
    public Page<ZmsChannelSchedule> findPage(Page<ZmsChannelSchedule> page, ZmsChannelSchedule zmsChannelSchedule) {
		return super.findPage(page, zmsChannelSchedule);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(ZmsChannelSchedule zmsChannelSchedule) {
		super.save(zmsChannelSchedule);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(ZmsChannelSchedule zmsChannelSchedule) {
		super.delete(zmsChannelSchedule);
	}
	
	
	
	
}
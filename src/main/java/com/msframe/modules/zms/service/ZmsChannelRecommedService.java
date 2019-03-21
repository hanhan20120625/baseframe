/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.modules.zms.entity.ZmsChannel;
import com.msframe.modules.zms.entity.ZmsCategory;
import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.zms.entity.ZmsChannelRecommed;
import com.msframe.modules.zms.dao.ZmsChannelRecommedDao;

/**
 * 频道推荐Service
 * @author leon
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly = true)
public class ZmsChannelRecommedService extends CrudService<ZmsChannelRecommedDao, ZmsChannelRecommed> {

	public ZmsChannelRecommed get(String id) {
		return super.get(id);
	}
	
	public List<ZmsChannelRecommed> findList(ZmsChannelRecommed zmsChannelRecommed) {
		return super.findList(zmsChannelRecommed);
	}
	
	public Page<ZmsChannelRecommed> findPage(Page<ZmsChannelRecommed> page, ZmsChannelRecommed zmsChannelRecommed) {
		return super.findPage(page, zmsChannelRecommed);
	}
	
	@Transactional(readOnly = false)
	public void save(ZmsChannelRecommed zmsChannelRecommed) {
		super.save(zmsChannelRecommed);
	}
	
	@Transactional(readOnly = false)
	public void delete(ZmsChannelRecommed zmsChannelRecommed) {
		super.delete(zmsChannelRecommed);
	}
	
	public Page<ZmsChannel> findPageBychannelId(Page<ZmsChannel> page, ZmsChannel channelId) {
		//channelId.setPage(page);
		page.setList(dao.findListBychannelId(channelId));
		return page;
	}
	public Page<ZmsCategory> findPageBycategoryId(Page<ZmsCategory> page, ZmsCategory categoryId) {
		//categoryId.setPage(page);
		page.setList(dao.findListBycategoryId(categoryId));
		return page;
	}
	
	
	
}
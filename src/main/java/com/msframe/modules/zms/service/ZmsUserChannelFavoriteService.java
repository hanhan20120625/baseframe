/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.zms.entity.ZmsUserChannelFavorite;
import com.msframe.modules.zms.dao.ZmsUserChannelFavoriteDao;

/**
 * 用户收藏直播管理Service
 * @author wlh
 * @version 2018-11-13
 */
@Service
@Transactional(readOnly = true)
public class ZmsUserChannelFavoriteService extends CrudService<ZmsUserChannelFavoriteDao, ZmsUserChannelFavorite> {

	@Override
    public ZmsUserChannelFavorite get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<ZmsUserChannelFavorite> findList(ZmsUserChannelFavorite zmsUserChannelFavorite) {
		return super.findList(zmsUserChannelFavorite);
	}
	
	@Override
    public Page<ZmsUserChannelFavorite> findPage(Page<ZmsUserChannelFavorite> page, ZmsUserChannelFavorite zmsUserChannelFavorite) {
		return super.findPage(page, zmsUserChannelFavorite);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(ZmsUserChannelFavorite zmsUserChannelFavorite) {
		super.save(zmsUserChannelFavorite);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(ZmsUserChannelFavorite zmsUserChannelFavorite) {
		super.delete(zmsUserChannelFavorite);
	}
	
	
	
	
}
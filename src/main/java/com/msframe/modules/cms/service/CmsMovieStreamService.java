/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsMovieStream;
import com.msframe.modules.cms.dao.CmsMovieStreamDao;

/**
 * 电影播放流Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsMovieStreamService extends CrudService<CmsMovieStreamDao, CmsMovieStream> {

	@Override
	public CmsMovieStream get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsMovieStream> findList(CmsMovieStream cmsMovieStream) {
		return super.findList(cmsMovieStream);
	}
	
	@Override
	public Page<CmsMovieStream> findPage(Page<CmsMovieStream> page, CmsMovieStream cmsMovieStream) {
		return super.findPage(page, cmsMovieStream);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsMovieStream cmsMovieStream) {
		super.save(cmsMovieStream);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsMovieStream cmsMovieStream) {
		super.delete(cmsMovieStream);
	}
	
	
	
	
}
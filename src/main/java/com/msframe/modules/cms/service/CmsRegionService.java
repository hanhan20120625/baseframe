/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsRegion;
import com.msframe.modules.cms.dao.CmsRegionDao;

/**
 * 视频内容地区Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsRegionService extends CrudService<CmsRegionDao, CmsRegion> {

	@Override
	public CmsRegion get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsRegion> findList(CmsRegion cmsRegion) {
		return super.findList(cmsRegion);
	}
	
	@Override
	public Page<CmsRegion> findPage(Page<CmsRegion> page, CmsRegion cmsRegion) {
		return super.findPage(page, cmsRegion);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsRegion cmsRegion) {
		super.save(cmsRegion);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsRegion cmsRegion) {
		super.delete(cmsRegion);
	}
	
	
	
	
}
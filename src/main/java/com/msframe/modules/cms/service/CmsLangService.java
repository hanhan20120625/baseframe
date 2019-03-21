/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsLang;
import com.msframe.modules.cms.dao.CmsLangDao;

/**
 * 语言Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsLangService extends CrudService<CmsLangDao, CmsLang> {

	@Override
	public CmsLang get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsLang> findList(CmsLang cmsLang) {
		return super.findList(cmsLang);
	}
	
	@Override
	public Page<CmsLang> findPage(Page<CmsLang> page, CmsLang cmsLang) {
		return super.findPage(page, cmsLang);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsLang cmsLang) {
		super.save(cmsLang);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsLang cmsLang) {
		super.delete(cmsLang);
	}
	
	
	
	
}
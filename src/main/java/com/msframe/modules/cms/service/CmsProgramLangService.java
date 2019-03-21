/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.cms.entity.CmsProgramLang;
import com.msframe.modules.cms.dao.CmsProgramLangDao;

/**
 * 影片关联语言Service
 * @author lpz
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class CmsProgramLangService extends CrudService<CmsProgramLangDao, CmsProgramLang> {

	@Override
	public CmsProgramLang get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CmsProgramLang> findList(CmsProgramLang cmsProgramLang) {
		return super.findList(cmsProgramLang);
	}
	
	@Override
	public Page<CmsProgramLang> findPage(Page<CmsProgramLang> page, CmsProgramLang cmsProgramLang) {
		return super.findPage(page, cmsProgramLang);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CmsProgramLang cmsProgramLang) {
		super.save(cmsProgramLang);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CmsProgramLang cmsProgramLang) {
		super.delete(cmsProgramLang);
	}
	
	
	
	
}
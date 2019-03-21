/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserInfo;
import com.msframe.modules.user.dao.UserInfoDao;

/**
 * 客户管理Service
 * @author wlh
 * @version 2018-11-13
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService extends CrudService<UserInfoDao, UserInfo> {

	@Override
	public UserInfo get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<UserInfo> findList(UserInfo user) {
		return super.findList(user);
	}
	
	@Override
	public Page<UserInfo> findPage(Page<UserInfo> page, UserInfo user) {
		return super.findPage(page, user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(UserInfo user) {
		super.save(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(UserInfo user) {
		super.delete(user);
	}
	
	
	
	
}
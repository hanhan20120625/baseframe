/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserToken;
import com.msframe.modules.user.dao.UserTokenDao;

/**
 * tokenService
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class UserTokenService extends CrudService<UserTokenDao, UserToken> {

	@Override
    public UserToken get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<UserToken> findList(UserToken userToken) {
		return super.findList(userToken);
	}
	
	@Override
    public Page<UserToken> findPage(Page<UserToken> page, UserToken userToken) {
		return super.findPage(page, userToken);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(UserToken userToken) {
		super.save(userToken);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(UserToken userToken) {
		super.delete(userToken);
	}
	
	
	
	
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserOpenid;
import com.msframe.modules.user.dao.UserOpenidDao;

/**
 * 第三方登录Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class UserOpenidService extends CrudService<UserOpenidDao, UserOpenid> {

	@Override
    public UserOpenid get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<UserOpenid> findList(UserOpenid userOpenid) {
		return super.findList(userOpenid);
	}
	
	@Override
    public Page<UserOpenid> findPage(Page<UserOpenid> page, UserOpenid userOpenid) {
		return super.findPage(page, userOpenid);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(UserOpenid userOpenid) {
		super.save(userOpenid);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(UserOpenid userOpenid) {
		super.delete(userOpenid);
	}
	
	
	
	
}
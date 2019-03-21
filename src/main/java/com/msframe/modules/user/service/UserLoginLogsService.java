/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserLoginLogs;
import com.msframe.modules.user.dao.UserLoginLogsDao;

/**
 * 用户登录日志Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class UserLoginLogsService extends CrudService<UserLoginLogsDao, UserLoginLogs> {

	@Override
    public UserLoginLogs get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<UserLoginLogs> findList(UserLoginLogs userLoginLogs) {
		return super.findList(userLoginLogs);
	}
	
	@Override
    public Page<UserLoginLogs> findPage(Page<UserLoginLogs> page, UserLoginLogs userLoginLogs) {
		return super.findPage(page, userLoginLogs);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(UserLoginLogs userLoginLogs) {
		super.save(userLoginLogs);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(UserLoginLogs userLoginLogs) {
		super.delete(userLoginLogs);
	}
	
	
	
	
}
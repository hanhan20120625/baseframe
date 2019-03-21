/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserBindLogs;
import com.msframe.modules.user.dao.UserBindLogsDao;

/**
 * 用户绑定iptv信息Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class UserBindLogsService extends CrudService<UserBindLogsDao, UserBindLogs> {

	@Override
	public UserBindLogs get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<UserBindLogs> findList(UserBindLogs userBindLogs) {
		return super.findList(userBindLogs);
	}
	
	@Override
	public Page<UserBindLogs> findPage(Page<UserBindLogs> page, UserBindLogs userBindLogs) {
		return super.findPage(page, userBindLogs);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(UserBindLogs userBindLogs) {
		super.save(userBindLogs);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(UserBindLogs userBindLogs) {
		super.delete(userBindLogs);
	}
	
	
	
	
}
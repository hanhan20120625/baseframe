/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserPlayHistory;
import com.msframe.modules.user.dao.UserPlayHistoryDao;

/**
 * 用户播放记录Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class UserPlayHistoryService extends CrudService<UserPlayHistoryDao, UserPlayHistory> {

	@Override
    public UserPlayHistory get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<UserPlayHistory> findList(UserPlayHistory userPlayHistory) {
		return super.findList(userPlayHistory);
	}
	
	@Override
    public Page<UserPlayHistory> findPage(Page<UserPlayHistory> page, UserPlayHistory userPlayHistory) {
		return super.findPage(page, userPlayHistory);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(UserPlayHistory userPlayHistory) {
		super.save(userPlayHistory);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(UserPlayHistory userPlayHistory) {
		super.delete(userPlayHistory);
	}
	
	
	
	
}
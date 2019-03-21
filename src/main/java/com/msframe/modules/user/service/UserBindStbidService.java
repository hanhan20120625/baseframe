/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msframe.common.persistence.Page;
import com.msframe.common.service.CrudService;
import com.msframe.modules.user.entity.UserBindStbid;
import com.msframe.modules.user.dao.UserBindStbidDao;

/**
 * 用户绑定设备Service
 * @author jjj
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class UserBindStbidService extends CrudService<UserBindStbidDao, UserBindStbid> {

	@Override
    public UserBindStbid get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<UserBindStbid> findList(UserBindStbid userBindStbid) {
		return super.findList(userBindStbid);
	}
	
	@Override
    public Page<UserBindStbid> findPage(Page<UserBindStbid> page, UserBindStbid userBindStbid) {
		return super.findPage(page, userBindStbid);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(UserBindStbid userBindStbid) {
		super.save(userBindStbid);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(UserBindStbid userBindStbid) {
		super.delete(userBindStbid);
	}
	
	
	
	
}
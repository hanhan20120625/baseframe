/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.dao;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.user.entity.UserInfo;

/**
 * 客户管理DAO接口
 * @author wlh
 * @version 2018-11-13
 */
@MyBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {

	
}
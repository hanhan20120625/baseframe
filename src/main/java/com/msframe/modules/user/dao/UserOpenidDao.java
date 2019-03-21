/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.dao;
import java.util.List;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.user.entity.UserOpenid;

/**
 * 第三方登录DAO接口
 * @author jjj
 * @version 2018-11-14
 */
@MyBatisDao
public interface UserOpenidDao extends CrudDao<UserOpenid> {

	
}
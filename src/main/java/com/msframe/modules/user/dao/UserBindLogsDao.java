/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.dao;
import java.util.List;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.user.entity.UserBindLogs;

/**
 * 用户绑定iptv信息DAO接口
 * @author jjj
 * @version 2018-11-14
 */
@MyBatisDao
public interface UserBindLogsDao extends CrudDao<UserBindLogs> {

	
}
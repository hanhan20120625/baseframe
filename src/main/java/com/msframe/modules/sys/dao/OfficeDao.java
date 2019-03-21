/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.sys.dao;

import com.msframe.common.persistence.TreeDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author msframe
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
}

/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.dao;

import com.msframe.common.persistence.TreeDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.zms.entity.ZmsCategory;

/**
 * 直播类别DAO接口
 * @author wlh
 * @version 2018-11-23
 */
@MyBatisDao
public interface ZmsCategoryDao extends TreeDao<ZmsCategory> {
	
}
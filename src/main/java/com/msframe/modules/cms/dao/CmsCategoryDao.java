/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.dao;

import com.msframe.common.persistence.TreeDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsCategory;

/**
 * 栏目DAO接口
 * @author lpz
 * @version 2018-11-16
 */
@MyBatisDao
public interface CmsCategoryDao extends TreeDao<CmsCategory> {
	
}
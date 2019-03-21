/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.dao;
import java.util.List;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsProgramOnline;

/**
 * 影片上上线记录DAO接口
 * @author lpz
 * @version 2018-11-14
 */
@MyBatisDao
public interface CmsProgramOnlineDao extends CrudDao<CmsProgramOnline> {

	
}
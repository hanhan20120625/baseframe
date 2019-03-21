/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.dao;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.bms.entity.BmsProductSrv;

/**
 * 产品服务信息DAO接口
 * @author jjj
 * @version 2018-11-14
 */
@MyBatisDao
public interface BmsProductSrvDao extends CrudDao<BmsProductSrv> {

	
}
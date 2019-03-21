/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.dao;
import java.util.List;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.zms.entity.ZmsChannel;

/**
 * 频道信息管理DAO接口
 * @author wlh
 * @version 2018-11-13
 */
@MyBatisDao
public interface ZmsChannelDao extends CrudDao<ZmsChannel> {

	
}
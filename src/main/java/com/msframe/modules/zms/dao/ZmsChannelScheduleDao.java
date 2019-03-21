/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.dao;
import java.util.List;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.zms.entity.ZmsChannelSchedule;

/**
 * 直播节目单列表DAO接口
 * @author wlh
 * @version 2018-11-13
 */
@MyBatisDao
public interface ZmsChannelScheduleDao extends CrudDao<ZmsChannelSchedule> {

	
}
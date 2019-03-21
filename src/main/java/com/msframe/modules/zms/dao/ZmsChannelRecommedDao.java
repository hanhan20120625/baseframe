/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.dao;
import java.util.List;
import com.msframe.modules.zms.entity.ZmsChannel;
import com.msframe.modules.zms.entity.ZmsCategory;
import java.util.List;
import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.zms.entity.ZmsChannelRecommed;

/**
 * 频道推荐DAO接口
 * @author leon
 * @version 2018-11-28
 */
@MyBatisDao
public interface ZmsChannelRecommedDao extends CrudDao<ZmsChannelRecommed> {

	public List<ZmsChannel> findListBychannelId(ZmsChannel channelId);
	public List<ZmsCategory> findListBycategoryId(ZmsCategory categoryId);
	
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.entity;

import com.msframe.modules.zms.entity.ZmsChannel;
import com.msframe.modules.zms.entity.ZmsCategory;
import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 频道推荐Entity
 * @author leon
 * @version 2018-11-28
 */
public class ZmsChannelRecommed extends DataEntity<ZmsChannelRecommed> {
	
	private static final long serialVersionUID = 1L;
	private ZmsChannel channelId;		// 频道编号
	private ZmsCategory categoryId;		// 隶属分类
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段
	
	public ZmsChannelRecommed() {
		super();
	}

	public ZmsChannelRecommed(String id){
		super(id);
	}

	@ExcelField(title="频道编号", align=2, sort=1)
	public ZmsChannel getChannelId() {
		return channelId;
	}

	public void setChannelId(ZmsChannel channelId) {
		this.channelId = channelId;
	}
	
	@ExcelField(title="隶属分类", align=2, sort=2)
	public ZmsCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(ZmsCategory categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=4)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段", align=2, sort=11)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
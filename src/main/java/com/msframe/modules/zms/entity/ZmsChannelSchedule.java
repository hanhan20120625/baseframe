/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 直播节目单列表Entity
 * @author wlh
 * @version 2018-11-13
 */
public class ZmsChannelSchedule extends DataEntity<ZmsChannelSchedule> {
	
	private static final long serialVersionUID = 1L;
	private ZmsChannel channelId;		// 频道编号
	private String contentId;		// 内容标识
	private String code;		// 直播单编号
	private String channelCode;		// 标识code
	private String searchName;		// 搜索名称
	private String programName;		// 节目名称
	private String startDate;		// 播放日期
	private String startTime;		// 开始时间
	private Long duration;		// 时长（精确到秒）
	private String description;		// 描述
	private Integer orderNum;		// 订阅人数
	private Integer replay;		// 评论数
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public ZmsChannelSchedule() {
		super();
	}

	public ZmsChannelSchedule(String id){
		super(id);
	}

	@ExcelField(title="频道编号", align=2, sort=1)
	public ZmsChannel getChannelId() {
		return channelId;
	}

	public void setChannelId(ZmsChannel channelId) {
		this.channelId = channelId;
	}
	
	@Length(min=0, max=64, message="内容标识长度必须介于 0 和 64 之间")
	@ExcelField(title="内容标识", align=2, sort=2)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@Length(min=0, max=64, message="直播单编号长度必须介于 0 和 64 之间")
	@ExcelField(title="直播单编号", align=2, sort=3)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=64, message="标识code长度必须介于 0 和 64 之间")
	@ExcelField(title="标识code", align=2, sort=4)
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=128, message="搜索名称长度必须介于 0 和 128 之间")
	@ExcelField(title="搜索名称", align=2, sort=5)
	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	@Length(min=0, max=128, message="节目名称长度必须介于 0 和 128 之间")
	@ExcelField(title="节目名称", align=2, sort=6)
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	@Length(min=0, max=16, message="播放日期长度必须介于 0 和 16 之间")
	@ExcelField(title="播放日期", align=2, sort=7)
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=16, message="开始时间长度必须介于 0 和 16 之间")
	@ExcelField(title="开始时间", align=2, sort=8)
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@ExcelField(title="时长（精确到秒）", align=2, sort=9)
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	@ExcelField(title="描述", align=2, sort=10)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="订阅人数", align=2, sort=11)
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	@ExcelField(title="评论数", align=2, sort=12)
	public Integer getReplay() {
		return replay;
	}

	public void setReplay(Integer replay) {
		this.replay = replay;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=13)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=14)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=21)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
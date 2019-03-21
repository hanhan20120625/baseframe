/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.entity;

import org.hibernate.validator.constraints.Length;
import com.msframe.modules.sys.entity.User;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 产品内容Entity
 * @author jjj
 * @version 2018-11-14
 */
public class BmsOrderProgram extends DataEntity<BmsOrderProgram> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 产品编号
	private User user;		// 用户编号
	private String programId;		// 影片编号
	private String orderId;		// 订单编号
	private String startTime;		// 开始时间
	private String endTime;		// edntime
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public BmsOrderProgram() {
		super();
	}

	public BmsOrderProgram(String id){
		super(id);
	}

	@Length(min=0, max=64, message="产品编号长度必须介于 0 和 64 之间")
	@ExcelField(title="产品编号", align=2, sort=1)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@ExcelField(title="用户编号", fieldType=User.class, value="user.name", align=2, sort=2)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="影片编号长度必须介于 0 和 64 之间")
	@ExcelField(title="影片编号", align=2, sort=3)
	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	@Length(min=0, max=64, message="订单编号长度必须介于 0 和 64 之间")
	@ExcelField(title="订单编号", align=2, sort=4)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=16, message="开始时间长度必须介于 0 和 16 之间")
	@ExcelField(title="开始时间", align=2, sort=5)
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Length(min=0, max=4, message="edntime长度必须介于 0 和 4 之间")
	@ExcelField(title="edntime", align=2, sort=6)
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=7)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=8)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=15)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
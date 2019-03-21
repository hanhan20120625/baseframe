/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.entity;

import com.msframe.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 用户绑定设备Entity
 * @author jjj
 * @version 2018-11-14
 */
public class UserBindStbid extends DataEntity<UserBindStbid> {
	
	private static final long serialVersionUID = 1L;
	private UserInfo user;		// 用户编号
	private String stbid;		// 设备编号
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public UserBindStbid() {
		super();
	}

	public UserBindStbid(String id){
		super(id);
	}

	@ExcelField(title="用户编号", fieldType=UserInfo.class, value="user.name", align=2, sort=1)
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="设备编号长度必须介于 0 和 64 之间")
	@ExcelField(title="设备编号", align=2, sort=2)
	public String getStbid() {
		return stbid;
	}

	public void setStbid(String stbid) {
		this.stbid = stbid;
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
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=11)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
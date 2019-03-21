/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.entity;

import org.hibernate.validator.constraints.Length;
import com.msframe.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * tokenEntity
 * @author jjj
 * @version 2018-11-14
 */
public class UserToken extends DataEntity<UserToken> {
	
	private static final long serialVersionUID = 1L;
	private String token;		// token标识
	private User user;		// 用户编号
	private String adminId;		// 管理员编号
	private Date expireTime;		// 过期时间
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public UserToken() {
		super();
	}

	public UserToken(String id){
		super(id);
	}

	@Length(min=0, max=100, message="token标识长度必须介于 0 和 100 之间")
	@ExcelField(title="token标识", align=2, sort=1)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@ExcelField(title="用户编号", fieldType=User.class, value="user.name", align=2, sort=2)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="管理员编号长度必须介于 0 和 64 之间")
	@ExcelField(title="管理员编号", align=2, sort=3)
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="过期时间", align=2, sort=4)
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=5)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=6)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=13)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
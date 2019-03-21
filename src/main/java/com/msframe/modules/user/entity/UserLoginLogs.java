/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.entity;

import com.msframe.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 用户登录日志Entity
 * @author jjj
 * @version 2018-11-14
 */
public class UserLoginLogs extends DataEntity<UserLoginLogs> {
	
	private static final long serialVersionUID = 1L;
	private UserInfo user;		// 用户编号
	private Date lastLoginTime;		// 最后登陆时间
	private String loginIp;		// 登录IP
	private String loginType;		// 登录类型|
	private String openid;		// 第三方平台标识|
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public UserLoginLogs() {
		super();
	}

	public UserLoginLogs(String id){
		super(id);
	}

	@ExcelField(title="用户编号", fieldType=User.class, value="user.name", align=2, sort=1)
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="最后登陆时间不能为空")
	@ExcelField(title="最后登陆时间", align=2, sort=2)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@Length(min=0, max=64, message="登录IP长度必须介于 0 和 64 之间")
	@ExcelField(title="登录IP", align=2, sort=3)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@Length(min=0, max=20, message="登录类型|长度必须介于 0 和 20 之间")
	@ExcelField(title="登录类型|", align=2, sort=4)
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	@Length(min=0, max=64, message="第三方平台标识|长度必须介于 0 和 64 之间")
	@ExcelField(title="第三方平台标识|", align=2, sort=5)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=6)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=7)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=14)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
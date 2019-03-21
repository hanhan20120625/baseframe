/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.entity;

import com.msframe.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 用户绑定iptv信息Entity
 * @author jjj
 * @version 2018-11-14
 */
public class UserBindLogs extends DataEntity<UserBindLogs> {
	
	private static final long serialVersionUID = 1L;
	private UserInfo user;		// 用户编号
	private String iptvcode;		// 二维码
	private Date bindtime;		// 绑定结果
	private String result;		// 接口返回结果
	private String stbid;		// 设备编号
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	private String description;		// 描述
	
	public UserBindLogs() {
		super();
	}

	public UserBindLogs(String id){
		super(id);
	}

	@ExcelField(title="用户编号", fieldType=User.class, value="user.name", align=2, sort=1)
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	@Length(min=0, max=255, message="二维码长度必须介于 0 和 255 之间")
	@ExcelField(title="二维码", align=2, sort=2)
	public String getIptvcode() {
		return iptvcode;
	}

	public void setIptvcode(String iptvcode) {
		this.iptvcode = iptvcode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="绑定结果", align=2, sort=3)
	public Date getBindtime() {
		return bindtime;
	}

	public void setBindtime(Date bindtime) {
		this.bindtime = bindtime;
	}
	
	@Length(min=0, max=50, message="接口返回结果长度必须介于 0 和 50 之间")
	@ExcelField(title="接口返回结果", align=2, sort=4)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Length(min=0, max=64, message="设备编号长度必须介于 0 和 64 之间")
	@ExcelField(title="设备编号", align=2, sort=5)
	public String getStbid() {
		return stbid;
	}

	public void setStbid(String stbid) {
		this.stbid = stbid;
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
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	@ExcelField(title="描述", align=2, sort=15)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.entity;

import com.msframe.modules.user.entity.UserInfo;
import org.hibernate.validator.constraints.Length;
import com.msframe.modules.sys.entity.User;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 用户收藏直播管理Entity
 * @author wlh
 * @version 2018-11-13
 */
public class ZmsUserChannelFavorite extends DataEntity<ZmsUserChannelFavorite> {
	
	private static final long serialVersionUID = 1L;
	private ZmsChannel channelId;		// 频道编号
	private UserInfo user;		// 用户编号
	private String username;		// 用户名
	private String nickname;		// 昵称
	private String name;		// 名称
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public ZmsUserChannelFavorite() {
		super();
	}

	public ZmsUserChannelFavorite(String id){
		super(id);
	}

	@ExcelField(title="频道编号", align=2, sort=1)
	public ZmsChannel getChannelId() {
		return channelId;
	}

	public void setChannelId(ZmsChannel channelId) {
		this.channelId = channelId;
	}
	
	@ExcelField(title="用户编号", fieldType=User.class, value="user.name", align=2, sort=2)
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="用户名长度必须介于 0 和 64 之间")
	@ExcelField(title="用户名", align=2, sort=3)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=64, message="昵称长度必须介于 0 和 64 之间")
	@ExcelField(title="昵称", align=2, sort=4)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	@ExcelField(title="名称", align=2, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
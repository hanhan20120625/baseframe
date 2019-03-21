/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.entity;

import org.hibernate.validator.constraints.Length;
import com.msframe.modules.sys.entity.User;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 第三方登录Entity
 * @author jjj
 * @version 2018-11-14
 */
public class UserOpenid extends DataEntity<UserOpenid> {
	
	private static final long serialVersionUID = 1L;
	private String openid;		// 第三方平台标识|
	private String planform;		// 第三方平台| webo  wechat qq
	private String nickname;		// 昵称
	private Integer sex;		// 性别|0:女;1:男;2:未知;
	private String headpic;		// 头像地址
	private User user;		// 用户编号
	private String description;		// 描述
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public UserOpenid() {
		super();
	}

	public UserOpenid(String id){
		super(id);
	}

	@Length(min=0, max=64, message="第三方平台标识|长度必须介于 0 和 64 之间")
	@ExcelField(title="第三方平台标识|", align=2, sort=1)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=20, message="第三方平台| webo  wechat qq长度必须介于 0 和 20 之间")
	@ExcelField(title="第三方平台| webo  wechat qq", align=2, sort=2)
	public String getPlanform() {
		return planform;
	}

	public void setPlanform(String planform) {
		this.planform = planform;
	}
	
	@Length(min=0, max=64, message="昵称长度必须介于 0 和 64 之间")
	@ExcelField(title="昵称", align=2, sort=3)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@ExcelField(title="性别|0:女;1:男;2:未知;", dictType="general_sex", align=2, sort=4)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="头像地址长度必须介于 0 和 255 之间")
	@ExcelField(title="头像地址", align=2, sort=5)
	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	
	@ExcelField(title="用户编号", fieldType=User.class, value="user.name", align=2, sort=6)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	@ExcelField(title="描述", align=2, sort=7)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=8)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=9)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=16)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
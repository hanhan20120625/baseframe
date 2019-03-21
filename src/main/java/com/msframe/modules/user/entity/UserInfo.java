/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 客户管理Entity
 * @author wlh
 * @version 2018-11-13
 */
public class UserInfo extends DataEntity<UserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String loginName;		// 用户名
	private String password;		// 密码
	private String realName;		// 真实名称
	private String nickname;		// 昵称
	private Integer idType;		// 证件类型
	private String idNumber;		// 证件号
	private Integer sex;		// 性别
	private String mobile;		// 手机号
	private String address;		// 地址
	private String telephone;		// 联系电话
	private String email;		// 电子邮件
	private String fax;		// 传真
	private String headpic;		// 头像地址
	private Long loginTimes;		// 登陆次数
	private Date lastLoginTime;		// 最后登陆时间
	private String lastLoginIp;		// 最后登录IP地址
	private String isvip;		// 是否是vip会员
	private Date vipStartTime;		// vip开始时间
	private Date vipEndTime;		// vip结束时间
	private Integer payType;		// 付费方式|0:后付费;1:预付费;2:外部代收费
	private String imsi;		// imsi
	private String mac;		// mac地址
	private Integer score;		// 积分数量
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public UserInfo() {
		super();
	}

	public UserInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="用户名长度必须介于 1 和 64 之间")
	@ExcelField(title="用户名", align=2, sort=1)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=1, max=64, message="密码长度必须介于 1 和 64 之间")
	@ExcelField(title="密码", align=2, sort=2)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=64, message="真实名称长度必须介于 0 和 64 之间")
	@ExcelField(title="真实名称", align=2, sort=3)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Length(min=0, max=64, message="昵称长度必须介于 0 和 64 之间")
	@ExcelField(title="昵称", align=2, sort=4)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@ExcelField(title="证件类型", dictType="user_id_type", align=2, sort=5)
	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=50, message="证件号长度必须介于 0 和 50 之间")
	@ExcelField(title="证件号", align=2, sort=6)
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@ExcelField(title="性别", dictType="general_sex", align=2, sort=7)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=20, message="手机号长度必须介于 0 和 20 之间")
	@ExcelField(title="手机号", align=2, sort=8)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=256, message="地址长度必须介于 0 和 256 之间")
	@ExcelField(title="地址", align=2, sort=9)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=40, message="联系电话长度必须介于 0 和 40 之间")
	@ExcelField(title="联系电话", align=2, sort=10)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=255, message="电子邮件长度必须介于 0 和 255 之间")
	@ExcelField(title="电子邮件", align=2, sort=11)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=256, message="传真长度必须介于 0 和 256 之间")
	@ExcelField(title="传真", align=2, sort=12)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Length(min=0, max=255, message="头像地址长度必须介于 0 和 255 之间")
	@ExcelField(title="头像地址", align=2, sort=13)
	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	
	@ExcelField(title="登陆次数", align=2, sort=14)
	public Long getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Long loginTimes) {
		this.loginTimes = loginTimes;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后登陆时间", align=2, sort=15)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@Length(min=0, max=40, message="最后登录IP地址长度必须介于 0 和 40 之间")
	@ExcelField(title="最后登录IP地址", align=2, sort=16)
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	@Length(min=0, max=1, message="是否是vip会员长度必须介于 0 和 1 之间")
	@ExcelField(title="是否是vip会员", dictType="user_isvip", align=2, sort=17)
	public String getIsvip() {
		return isvip;
	}

	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="vip开始时间不能为空")
	@ExcelField(title="vip开始时间", align=2, sort=18)
	public Date getVipStartTime() {
		return vipStartTime;
	}

	public void setVipStartTime(Date vipStartTime) {
		this.vipStartTime = vipStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="vip结束时间不能为空")
	@ExcelField(title="vip结束时间", align=2, sort=19)
	public Date getVipEndTime() {
		return vipEndTime;
	}

	public void setVipEndTime(Date vipEndTime) {
		this.vipEndTime = vipEndTime;
	}
	
	@ExcelField(title="付费方式|0:后付费;1:预付费;2:外部代收费", dictType="user_pay_type", align=2, sort=20)
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=64, message="imsi长度必须介于 0 和 64 之间")
	@ExcelField(title="imsi", align=2, sort=21)
	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	@Length(min=0, max=64, message="mac地址长度必须介于 0 和 64 之间")
	@ExcelField(title="mac地址", align=2, sort=22)
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@ExcelField(title="积分数量", align=2, sort=23)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	@ExcelField(title="状态", align=2, sort=24)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=25)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=32)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
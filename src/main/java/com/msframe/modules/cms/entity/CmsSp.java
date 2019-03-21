/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 基本信息Entity
 * @author lpz
 * @version 2018-11-14
 */
public class CmsSp extends DataEntity<CmsSp> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String linkMan;		// 联系人
	private String mobile;		// 手机号
	private String telephone;		// 联系电话
	private String email;		// 电子邮件
	private String address;		// 地址
	private String code;		// 标识代码
	private String intro;		// 简介
	private String secretKey;		// 密匙
	private String compactNumber;		// 合同编号
	private Date compactStarttime;		// 合同开始时间
	private Date compactEndtime;		// 合同结束时间
	private String movieDowned;		// 是否提供下载
	private String supplyStream;		// 是否提供高清流
	private String companyName;		// 公司全称
	private String telCode;		// 电信业务经营许可证编号
	private String ctype;		// CP类型
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public CmsSp() {
		super();
	}

	public CmsSp(String id){
		super(id);
	}

	@Length(min=0, max=30, message="名称长度必须介于 0 和 30 之间")
	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=30, message="联系人长度必须介于 0 和 30 之间")
	@ExcelField(title="联系人", align=2, sort=2)
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
	@Length(min=0, max=20, message="手机号长度必须介于 0 和 20 之间")
	@ExcelField(title="手机号", align=2, sort=3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=40, message="联系电话长度必须介于 0 和 40 之间")
	@ExcelField(title="联系电话", align=2, sort=4)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=255, message="电子邮件长度必须介于 0 和 255 之间")
	@ExcelField(title="电子邮件", align=2, sort=5)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=256, message="地址长度必须介于 0 和 256 之间")
	@ExcelField(title="地址", align=2, sort=6)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=20, message="标识代码长度必须介于 0 和 20 之间")
	@ExcelField(title="标识代码", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=2000, message="简介长度必须介于 0 和 2000 之间")
	@ExcelField(title="简介", align=2, sort=8)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Length(min=0, max=50, message="密匙长度必须介于 0 和 50 之间")
	@ExcelField(title="密匙", align=2, sort=9)
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	@ExcelField(title="合同编号", align=2, sort=10)
	public String getCompactNumber() {
		return compactNumber;
	}

	public void setCompactNumber(String compactNumber) {
		this.compactNumber = compactNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="合同开始时间", align=2, sort=11)
	public Date getCompactStarttime() {
		return compactStarttime;
	}

	public void setCompactStarttime(Date compactStarttime) {
		this.compactStarttime = compactStarttime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="合同结束时间", align=2, sort=12)
	public Date getCompactEndtime() {
		return compactEndtime;
	}

	public void setCompactEndtime(Date compactEndtime) {
		this.compactEndtime = compactEndtime;
	}
	
	@Length(min=0, max=1, message="是否提供下载长度必须介于 0 和 1 之间")
	@ExcelField(title="是否提供下载", dictType="cms_sp_movie_downed", align=2, sort=13)
	public String getMovieDowned() {
		return movieDowned;
	}

	public void setMovieDowned(String movieDowned) {
		this.movieDowned = movieDowned;
	}
	
	@Length(min=0, max=1, message="是否提供高清流长度必须介于 0 和 1 之间")
	@ExcelField(title="是否提供高清流", dictType="cms_sp_supply_stream", align=2, sort=14)
	public String getSupplyStream() {
		return supplyStream;
	}

	public void setSupplyStream(String supplyStream) {
		this.supplyStream = supplyStream;
	}
	
	@Length(min=0, max=150, message="公司全称长度必须介于 0 和 150 之间")
	@ExcelField(title="公司全称", align=2, sort=15)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=150, message="电信业务经营许可证编号长度必须介于 0 和 150 之间")
	@ExcelField(title="电信业务经营许可证编号", align=2, sort=16)
	public String getTelCode() {
		return telCode;
	}

	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}
	
	@Length(min=0, max=1, message="CP类型长度必须介于 0 和 1 之间")
	@ExcelField(title="CP类型", dictType="cms_sp_ctype", align=2, sort=17)
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=18)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=19)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=26)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
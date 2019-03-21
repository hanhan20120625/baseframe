/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 演员信息Entity
 * @author lpz
 * @version 2018-11-14
 */
public class CmsCast extends DataEntity<CmsCast> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String cpCastid;		// cp演员标识
	private String personDisplayName;		// 显示名称
	private String personSortName;		// 排序名
	private String personSearchName;		// 搜索名
	private String firstname;		// 姓
	private String middlename;		// 中间名
	private String lastname;		// 名
	private Integer sex;		// 性别|0:女;1:男;2:未知;
	private Date birthday;		// 生日
	private String hometown;		// 家乡
	private String education;		// 教育
	private String height;		// 身高
	private String weight;		// 体重
	private String bloodGroup;		// 血型
	private Integer marriage;		// 婚姻
	private String favorite;		// 爱好
	private String webpage;		// 个人主页
	private String description;		// 描述
	private Integer cmsState;		// CMS状态
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	private Integer syncState;		// 同步状态
	
	public CmsCast() {
		super();
	}

	public CmsCast(String id){
		super(id);
	}

	@Length(min=0, max=64, message="name长度必须介于 0 和 64 之间")
	@ExcelField(title="name", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="cp演员标识长度必须介于 0 和 64 之间")
	@ExcelField(title="cp演员标识", align=2, sort=2)
	public String getCpCastid() {
		return cpCastid;
	}

	public void setCpCastid(String cpCastid) {
		this.cpCastid = cpCastid;
	}
	
	@Length(min=0, max=64, message="显示名称长度必须介于 0 和 64 之间")
	@ExcelField(title="显示名称", align=2, sort=3)
	public String getPersonDisplayName() {
		return personDisplayName;
	}

	public void setPersonDisplayName(String personDisplayName) {
		this.personDisplayName = personDisplayName;
	}
	
	@Length(min=0, max=64, message="排序名长度必须介于 0 和 64 之间")
	@ExcelField(title="排序名", align=2, sort=4)
	public String getPersonSortName() {
		return personSortName;
	}

	public void setPersonSortName(String personSortName) {
		this.personSortName = personSortName;
	}
	
	@Length(min=0, max=64, message="搜索名长度必须介于 0 和 64 之间")
	@ExcelField(title="搜索名", align=2, sort=5)
	public String getPersonSearchName() {
		return personSearchName;
	}

	public void setPersonSearchName(String personSearchName) {
		this.personSearchName = personSearchName;
	}
	
	@Length(min=0, max=64, message="姓长度必须介于 0 和 64 之间")
	@ExcelField(title="姓", align=2, sort=6)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Length(min=0, max=64, message="中间名长度必须介于 0 和 64 之间")
	@ExcelField(title="中间名", align=2, sort=7)
	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	@Length(min=0, max=64, message="名长度必须介于 0 和 64 之间")
	@ExcelField(title="名", align=2, sort=8)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@ExcelField(title="性别|0:女;1:男;2:未知;", align=2, sort=9)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生日", align=2, sort=10)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=256, message="家乡长度必须介于 0 和 256 之间")
	@ExcelField(title="家乡", align=2, sort=11)
	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	
	@Length(min=0, max=256, message="教育长度必须介于 0 和 256 之间")
	@ExcelField(title="教育", align=2, sort=12)
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@Length(min=0, max=64, message="身高长度必须介于 0 和 64 之间")
	@ExcelField(title="身高", align=2, sort=13)
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	@Length(min=0, max=64, message="体重长度必须介于 0 和 64 之间")
	@ExcelField(title="体重", align=2, sort=14)
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@Length(min=0, max=64, message="血型长度必须介于 0 和 64 之间")
	@ExcelField(title="血型", align=2, sort=15)
	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	
	@ExcelField(title="婚姻", dictType="cms_cast_marriage", align=2, sort=16)
	public Integer getMarriage() {
		return marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}
	
	@Length(min=0, max=256, message="爱好长度必须介于 0 和 256 之间")
	@ExcelField(title="爱好", align=2, sort=17)
	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	
	@Length(min=0, max=256, message="个人主页长度必须介于 0 和 256 之间")
	@ExcelField(title="个人主页", align=2, sort=18)
	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	@ExcelField(title="描述", align=2, sort=19)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="CMS状态", align=2, sort=20)
	public Integer getCmsState() {
		return cmsState;
	}

	public void setCmsState(Integer cmsState) {
		this.cmsState = cmsState;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=21)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=22)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=29)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="同步状态", align=2, sort=30)
	public Integer getSyncState() {
		return syncState;
	}

	public void setSyncState(Integer syncState) {
		this.syncState = syncState;
	}
	
}
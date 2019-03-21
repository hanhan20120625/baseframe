/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.app.entity;

import com.msframe.modules.cms.entity.CmsCategory;
import org.hibernate.validator.constraints.Length;
import com.msframe.modules.cms.entity.CmsProgram;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * banner推荐Entity
 * @author leon
 * @version 2018-11-28
 */
public class AppRecommend extends DataEntity<AppRecommend> {
	
	private static final long serialVersionUID = 1L;
	private CmsCategory categoryId;		// 隶属分类
	private String isindex;		// 是否首页
	private String name;		// 名称
	private String picurl;		// 图片地址
	private CmsProgram programId;		// 影片编号
	private Integer bizType;		// 业务类型
	private String linkurl;		// 链接地址
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段
	
	public AppRecommend() {
		super();
	}

	public AppRecommend(String id){
		super(id);
	}

	@ExcelField(title="隶属分类", align=2, sort=1)
	public CmsCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(CmsCategory categoryId) {
		this.categoryId = categoryId;
	}
	
	@Length(min=0, max=1, message="是否首页长度必须介于 0 和 1 之间")
	@ExcelField(title="是否首页", dictType="general_is_index", align=2, sort=2)
	public String getIsindex() {
		return isindex;
	}

	public void setIsindex(String isindex) {
		this.isindex = isindex;
	}
	
	@Length(min=0, max=128, message="名称长度必须介于 0 和 128 之间")
	@ExcelField(title="名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1024, message="图片地址长度必须介于 0 和 1024 之间")
	@ExcelField(title="图片地址", align=2, sort=4)
	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	@ExcelField(title="影片编号", align=2, sort=5)
	public CmsProgram getProgramId() {
		return programId;
	}

	public void setProgramId(CmsProgram programId) {
		this.programId = programId;
	}
	
	@ExcelField(title="业务类型", align=2, sort=6)
	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	
	@Length(min=0, max=255, message="链接地址长度必须介于 0 和 255 之间")
	@ExcelField(title="链接地址", align=2, sort=7)
	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
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
	
	@Length(min=0, max=255, message="附加字段长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段", align=2, sort=16)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import com.msframe.modules.cms.entity.CmsCategory;
import com.msframe.modules.cms.entity.CmsType;
import com.msframe.modules.cms.entity.CmsProgram;
import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 影片推荐Entity
 * @author leon
 * @version 2018-11-28
 */
public class CmsRecommend extends DataEntity<CmsRecommend> {
	
	private static final long serialVersionUID = 1L;
	private CmsCategory categoryId;		// 隶属分类
	private CmsType typeId;		// 影片类型
	private CmsProgram programId;		// 影片编号
	private String picurl;		// 图片地址
	private String name;		// 名称
	private String subhead;		// 副标题
	private String description;		// 描述
	private String isindex;		// 是否首页
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段
	
	public CmsRecommend() {
		super();
	}

	public CmsRecommend(String id){
		super(id);
	}

	@ExcelField(title="隶属分类", align=2, sort=1)
	public CmsCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(CmsCategory categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="影片类型", align=2, sort=2)
	public CmsType getTypeId() {
		return typeId;
	}

	public void setTypeId(CmsType typeId) {
		this.typeId = typeId;
	}
	
	@ExcelField(title="影片编号", align=2, sort=3)
	public CmsProgram getProgramId() {
		return programId;
	}

	public void setProgramId(CmsProgram programId) {
		this.programId = programId;
	}
	
	@Length(min=0, max=1024, message="图片地址长度必须介于 0 和 1024 之间")
	@ExcelField(title="图片地址", align=2, sort=4)
	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	@Length(min=0, max=128, message="名称长度必须介于 0 和 128 之间")
	@ExcelField(title="名称", align=2, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="副标题长度必须介于 0 和 255 之间")
	@ExcelField(title="副标题", align=2, sort=6)
	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	@ExcelField(title="描述", align=2, sort=7)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=1, message="是否首页长度必须介于 0 和 1 之间")
	@ExcelField(title="是否首页", dictType="general_is_index", align=2, sort=8)
	public String getIsindex() {
		return isindex;
	}

	public void setIsindex(String isindex) {
		this.isindex = isindex;
	}
	
	@ExcelField(title="状态", align=2, sort=9)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=10)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段", align=2, sort=17)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
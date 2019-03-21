/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 视频演员Entity
 * @author lpz
 * @version 2018-11-14
 */
public class CmsProgramCast extends DataEntity<CmsProgramCast> {
	
	private static final long serialVersionUID = 1L;
	private CmsProgram programId;		// 视频编号
	private CmsCast castId;		// 演员
	private Integer castType;		// 类型
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public CmsProgramCast() {
		super();
	}

	public CmsProgramCast(String id){
		super(id);
	}

	@ExcelField(title="视频编号", align=2, sort=1)
	public CmsProgram getProgramId() {
		return programId;
	}

	public void setProgramId(CmsProgram programId) {
		this.programId = programId;
	}
	
	@ExcelField(title="演员", align=2, sort=2)
	public CmsCast getCastId() {
		return castId;
	}

	public void setCastId(CmsCast castId) {
		this.castId = castId;
	}
	
	@ExcelField(title="类型", dictType="cms_program_cast_cast_type", align=2, sort=3)
	public Integer getCastType() {
		return castType;
	}

	public void setCastType(Integer castType) {
		this.castType = castType;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=4)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=5)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=12)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
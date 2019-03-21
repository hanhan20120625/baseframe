/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 影片上上线记录Entity
 * @author lpz
 * @version 2018-11-14
 */
public class CmsProgramOnline extends DataEntity<CmsProgramOnline> {
	
	private static final long serialVersionUID = 1L;
	private String movieId;		// 剧集编号
	private String programId;		// 影片编号
	private String dealed;		// 是否处理通知
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public CmsProgramOnline() {
		super();
	}

	public CmsProgramOnline(String id){
		super(id);
	}

	@Length(min=0, max=64, message="剧集编号长度必须介于 0 和 64 之间")
	@ExcelField(title="剧集编号", align=2, sort=1)
	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	
	@Length(min=0, max=64, message="影片编号长度必须介于 0 和 64 之间")
	@ExcelField(title="影片编号", align=2, sort=2)
	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	@Length(min=0, max=1, message="是否处理通知长度必须介于 0 和 1 之间")
	@ExcelField(title="是否处理通知", dictType="cms_program_online_dealed", align=2, sort=3)
	public String getDealed() {
		return dealed;
	}

	public void setDealed(String dealed) {
		this.dealed = dealed;
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
/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 图片信息Entity
 * @author lpz
 * @version 2018-11-14
 */
public class CmsPictureInfo extends DataEntity<CmsPictureInfo> {
	
	private static final long serialVersionUID = 1L;
	private CmsMovie movieId;		// 视频资源ID
	private String name;		// 名称
	private CmsProgram programId;		// 影片编号
	private String picConfigId;		// 图片格式ID
	private String picUrl;		// 图片路径
	private String format;		// 图片类型
	private String description;		// 描述
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public CmsPictureInfo() {
		super();
	}

	public CmsPictureInfo(String id){
		super(id);
	}

	@ExcelField(title="视频资源ID", align=2, sort=1)
	public CmsMovie getMovieId() {
		return movieId;
	}

	public void setMovieId(CmsMovie movieId) {
		this.movieId = movieId;
	}
	
	@Length(min=0, max=128, message="名称长度必须介于 0 和 128 之间")
	@ExcelField(title="名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="影片编号", align=2, sort=3)
	public CmsProgram getProgramId() {
		return programId;
	}

	public void setProgramId(CmsProgram programId) {
		this.programId = programId;
	}
	
	@Length(min=0, max=64, message="图片格式ID长度必须介于 0 和 64 之间")
	@ExcelField(title="图片格式ID", align=2, sort=4)
	public String getPicConfigId() {
		return picConfigId;
	}

	public void setPicConfigId(String picConfigId) {
		this.picConfigId = picConfigId;
	}
	
	@Length(min=0, max=200, message="图片路径长度必须介于 0 和 200 之间")
	@ExcelField(title="图片路径", align=2, sort=5)
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@Length(min=0, max=4, message="图片类型长度必须介于 0 和 4 之间")
	@ExcelField(title="图片类型", align=2, sort=6)
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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
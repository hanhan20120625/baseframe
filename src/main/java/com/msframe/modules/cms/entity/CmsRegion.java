/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 视频内容地区Entity
 * @author lpz
 * @version 2018-11-14
 */
public class CmsRegion extends DataEntity<CmsRegion> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 地区/国家名称
	private String description;		// 描述
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1

	/**
	 * 选中状态
	 */
	private Boolean checkFlag;
	
	public CmsRegion() {
		super();
	}

	public CmsRegion(String id){
		super(id);
	}

	@Length(min=0, max=128, message="地区/国家名称长度必须介于 0 和 128 之间")
	@ExcelField(title="地区/国家名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	@ExcelField(title="描述", align=2, sort=2)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=4)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=11)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

    public Boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
}
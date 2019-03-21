/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 视频区域关联Entity
 *
 * @author lpz
 * @version 2018-11-14
 */
public class CmsProgramRegion extends DataEntity<CmsProgramRegion> {

    private static final long serialVersionUID = 1L;
    private CmsProgram programId;        // 影片编号
    private CmsRegion region;        // 区域编号
    private Integer status;        // 状态
    private Long sort;        // 排序
    private String remark1;        // 附加字段1

    public CmsProgramRegion() {
        super();
    }

    public CmsProgramRegion(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "影片编号长度必须介于 0 和 64 之间")
    @ExcelField(title = "影片编号", align = 2, sort = 1)
    public CmsProgram getProgramId() {
        return programId;
    }

    public void setProgramId(CmsProgram programId) {
        this.programId = programId;
    }

    @Length(min = 0, max = 64, message = "区域编号长度必须介于 0 和 64 之间")
    @ExcelField(title = "区域编号", align = 2, sort = 2)
    public CmsRegion getRegion() {
        return region;
    }

    public void setRegion(CmsRegion region) {
        this.region = region;
    }

    @ExcelField(title = "状态", dictType = "general_status", align = 2, sort = 3)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ExcelField(title = "排序", align = 2, sort = 4)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Length(min = 0, max = 255, message = "附加字段1长度必须介于 0 和 255 之间")
    @ExcelField(title = "附加字段1", align = 2, sort = 11)
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

}
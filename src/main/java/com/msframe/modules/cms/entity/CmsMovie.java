/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 视频Entity
 *
 * @author lpz
 * @version 2018-11-14
 */
public class CmsMovie extends DataEntity<CmsMovie> {

    private static final long serialVersionUID = 1L;
    private CmsProgram programId;        // 影片编号
    private String name;        // 名称
    private String cpContentid;        // cp内容标识
    private Integer episode;        // 第几集
    private String contentId;        // 内容标识
    private String intro;        // 简介
    private String picurl;        // 图片地址
    private Integer status;        // 状态
    private Long sort;        // 排序
    private String remark1;        // 附加字段1

    private Integer isFromProgram;  // 来源

    public CmsMovie() {
        super();
    }

    public CmsMovie(String id) {
        super(id);
    }

    @ExcelField(title = "影片编号", align = 2, sort = 1)
    public CmsProgram getProgramId() {
        return programId;
    }

    public void setProgramId(CmsProgram programId) {
        this.programId = programId;
    }

    @Length(min = 0, max = 128, message = "名称长度必须介于 0 和 128 之间")
    @ExcelField(title = "名称", align = 2, sort = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 64, message = "cp内容标识长度必须介于 0 和 64 之间")
    @ExcelField(title = "cp内容标识", align = 2, sort = 3)
    public String getCpContentid() {
        return cpContentid;
    }

    public void setCpContentid(String cpContentid) {
        this.cpContentid = cpContentid;
    }

    @ExcelField(title = "第几集", align = 2, sort = 4)
    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    @Length(min = 0, max = 64, message = "内容标识长度必须介于 0 和 64 之间")
    @ExcelField(title = "内容标识", align = 2, sort = 5)
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Length(min = 0, max = 2000, message = "简介长度必须介于 0 和 2000 之间")
    @ExcelField(title = "简介", align = 2, sort = 6)
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Length(min = 0, max = 1024, message = "图片地址长度必须介于 0 和 1024 之间")
    @ExcelField(title = "图片地址", align = 2, sort = 7)
    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    @ExcelField(title = "状态", dictType = "general_status", align = 2, sort = 8)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ExcelField(title = "排序", align = 2, sort = 9)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Length(min = 0, max = 255, message = "附加字段1长度必须介于 0 和 255 之间")
    @ExcelField(title = "附加字段1", align = 2, sort = 16)
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public Integer getIsFromProgram() {
        return isFromProgram;
    }

    public void setIsFromProgram(Integer isFromProgram) {
        this.isFromProgram = isFromProgram;
    }
}
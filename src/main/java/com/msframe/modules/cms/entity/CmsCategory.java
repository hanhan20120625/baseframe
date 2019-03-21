/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.TreeEntity;

/**
 * 栏目Entity
 *
 * @author lpz
 * @version 2018-11-16
 */
public class CmsCategory extends TreeEntity<CmsCategory> {

    private static final long serialVersionUID = 1L;
    private String description;        // 描述
    private String icon;        // 图标
    private String poster;        // 海报
    private String bgImage;        // 背景图
    private Integer syncState;        // 同步状态
    private Integer status;        // 状态
    private String remark1;        // 附加字段1

    /**
     * 选中状态
     */
    private Boolean checkFlag;

    public CmsCategory() {
        super();
    }

    public CmsCategory(String id) {
        super(id);
    }

    @Length(min = 0, max = 256, message = "描述长度必须介于 0 和 256 之间")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Length(min = 0, max = 1000, message = "图标长度必须介于 0 和 1000 之间")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Length(min = 0, max = 1000, message = "海报长度必须介于 0 和 1000 之间")
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Length(min = 0, max = 1000, message = "背景图长度必须介于 0 和 1000 之间")
    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public Integer getSyncState() {
        return syncState;
    }

    public void setSyncState(Integer syncState) {
        this.syncState = syncState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Length(min = 0, max = 255, message = "附加字段1长度必须介于 0 和 255 之间")
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public CmsCategory getParent() {
        return parent;
    }

    public void setParent(CmsCategory parent) {
        this.parent = parent;
    }

    public Boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
}
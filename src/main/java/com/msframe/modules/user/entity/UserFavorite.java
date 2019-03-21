/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.user.entity;

import com.msframe.modules.cms.entity.CmsMovie;
import com.msframe.modules.cms.entity.CmsProgram;
import com.msframe.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 影片收藏信息Entity
 *
 * @author jjj
 * @version 2018-11-14
 */
public class UserFavorite extends DataEntity<UserFavorite> {

    private static final long serialVersionUID = 1L;
    private UserInfo user;        // 用户编号
    private CmsProgram programId;        // 影片编号
    private CmsMovie moviceId;        // 视频编号
    private Integer status;        // 状态
    private Long sort;        // 排序
    private String remark1;        // 附加字段1

    public UserFavorite() {
        super();
    }

    public UserFavorite(String id) {
        super(id);
    }

    @ExcelField(title = "用户编号", fieldType = User.class, value = "user.name", align = 2, sort = 1)
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @ExcelField(title = "影片编号", align = 2, sort = 2)
    public CmsProgram getProgramId() {
        return programId;
    }

    public void setProgramId(CmsProgram programId) {
        this.programId = programId;
    }

    @ExcelField(title = "视频编号", align = 2, sort = 3)
    public CmsMovie getMoviceId() {
        return moviceId;
    }

    public void setMoviceId(CmsMovie moviceId) {
        this.moviceId = moviceId;
    }

    @ExcelField(title = "状态", dictType = "general_status", align = 2, sort = 4)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ExcelField(title = "排序", align = 2, sort = 5)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Length(min = 0, max = 255, message = "附加字段1长度必须介于 0 和 255 之间")
    @ExcelField(title = "附加字段1", align = 2, sort = 12)
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

}
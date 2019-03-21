/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.entity;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 频道信息管理Entity
 *
 * @author wlh
 * @version 2018-11-13
 */
public class ZmsChannel extends DataEntity<ZmsChannel> {

    private static final long serialVersionUID = 1L;
    private ZmsChannelCategory categoryId;        // 隶属分类
    private String contentId;        // 内容标识
    private String name;        // 名称
    private Integer channelNumber;        // 频道号
    private Integer type;        // 频道类别
    private Integer subtype;        // 子类别
    private String callSign;        // 台标名称
    private Integer timeShift;        // 时移标志
    private String startTime;        // 开始时间
    private String endTime;        // edntime
    private String channelCode;        // 标识code
    private Integer storageDuration;        // 存储时长
    private Integer timeShiftDuration;        // 默认时移标志
    private String description;        // 描述
    private String country;        // 国家
    private String province;        // 省
    private String city;        // 城市
    private String zipCode;        // 邮编
    private Integer language;        // 语言
    private Integer macroVision;        // 拷贝保护标志
    private Integer streamType;        // 码流标志
    private Integer videoType;        // 视频编码格式
    private Integer audioType;        // 音频编码格式
    private Integer bilingual;        // 双语标志
    private String channelUrl;        // 频道地址
    private String bigPicurl;        // 台标大图
    private String picurl;        // 台标小图
    private Integer status;        // 状态
    private Long sort;        // 排序
    private String remark1;        // 附加字段1

    /**
     * 频道分类列表
     */
    private List<ZmsChannelCategory> zmsChannelCategoryList = Lists.newArrayList();
    private List<String> zmsCategorySaveList;

    public ZmsChannel() {
        super();
    }

    public ZmsChannel(String id) {
        super(id);
    }

    @ExcelField(title = "隶属分类", align = 2, sort = 1)
    public ZmsChannelCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ZmsChannelCategory categoryId) {
        this.categoryId = categoryId;
    }

    @Length(min = 0, max = 64, message = "内容标识长度必须介于 0 和 64 之间")
    @ExcelField(title = "内容标识", align = 2, sort = 2)
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Length(min = 0, max = 64, message = "名称长度必须介于 0 和 64 之间")
    @ExcelField(title = "名称", align = 2, sort = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "频道号", align = 2, sort = 4)
    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }

    @ExcelField(title = "频道类别", dictType = "zms_channel_type", align = 2, sort = 5)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @ExcelField(title = "子类别", dictType = "zms_channel_subtype", align = 2, sort = 6)
    public Integer getSubtype() {
        return subtype;
    }

    public void setSubtype(Integer subtype) {
        this.subtype = subtype;
    }

    @Length(min = 0, max = 10, message = "台标名称长度必须介于 0 和 10 之间")
    @ExcelField(title = "台标名称", align = 2, sort = 7)
    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    @ExcelField(title = "时移标志", align = 2, sort = 8)
    public Integer getTimeShift() {
        return timeShift;
    }

    public void setTimeShift(Integer timeShift) {
        this.timeShift = timeShift;
    }

    @Length(min = 0, max = 16, message = "开始时间长度必须介于 0 和 16 之间")
    @ExcelField(title = "开始时间", align = 2, sort = 9)
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Length(min = 0, max = 4, message = "edntime长度必须介于 0 和 4 之间")
    @ExcelField(title = "edntime", align = 2, sort = 10)
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Length(min = 0, max = 64, message = "标识code长度必须介于 0 和 64 之间")
    @ExcelField(title = "标识code", align = 2, sort = 11)
    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @ExcelField(title = "存储时长", align = 2, sort = 12)
    public Integer getStorageDuration() {
        return storageDuration;
    }

    public void setStorageDuration(Integer storageDuration) {
        this.storageDuration = storageDuration;
    }

    @ExcelField(title = "默认时移标志", dictType = "zms_channel_time_shift", align = 2, sort = 13)
    public Integer getTimeShiftDuration() {
        return timeShiftDuration;
    }

    public void setTimeShiftDuration(Integer timeShiftDuration) {
        this.timeShiftDuration = timeShiftDuration;
    }

    @Length(min = 0, max = 256, message = "描述长度必须介于 0 和 256 之间")
    @ExcelField(title = "描述", align = 2, sort = 14)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Length(min = 0, max = 256, message = "国家长度必须介于 0 和 256 之间")
    @ExcelField(title = "国家", align = 2, sort = 15)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Length(min = 0, max = 256, message = "省长度必须介于 0 和 256 之间")
    @ExcelField(title = "省", align = 2, sort = 16)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Length(min = 0, max = 256, message = "城市长度必须介于 0 和 256 之间")
    @ExcelField(title = "城市", align = 2, sort = 17)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Length(min = 0, max = 10, message = "邮编长度必须介于 0 和 10 之间")
    @ExcelField(title = "邮编", align = 2, sort = 18)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @ExcelField(title = "语言", align = 2, sort = 19)
    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    @ExcelField(title = "拷贝保护标志", dictType = "general_macro_vision", align = 2, sort = 20)
    public Integer getMacroVision() {
        return macroVision;
    }

    public void setMacroVision(Integer macroVision) {
        this.macroVision = macroVision;
    }

    @ExcelField(title = "码流标志", align = 2, sort = 21)
    public Integer getStreamType() {
        return streamType;
    }

    public void setStreamType(Integer streamType) {
        this.streamType = streamType;
    }

    @ExcelField(title = "视频编码格式", dictType = "general_video_type", align = 2, sort = 22)
    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    @ExcelField(title = "音频编码格式", dictType = "general_audio_type", align = 2, sort = 23)
    public Integer getAudioType() {
        return audioType;
    }

    public void setAudioType(Integer audioType) {
        this.audioType = audioType;
    }

    @ExcelField(title = "双语标志", align = 2, sort = 24)
    public Integer getBilingual() {
        return bilingual;
    }

    public void setBilingual(Integer bilingual) {
        this.bilingual = bilingual;
    }

    @Length(min = 0, max = 512, message = "频道地址长度必须介于 0 和 512 之间")
    @ExcelField(title = "频道地址", align = 2, sort = 25)
    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    @Length(min = 0, max = 255, message = "台标大图长度必须介于 0 和 255 之间")
    @ExcelField(title = "台标大图", align = 2, sort = 26)
    public String getBigPicurl() {
        return bigPicurl;
    }

    public void setBigPicurl(String bigPicurl) {
        this.bigPicurl = bigPicurl;
    }

    @Length(min = 0, max = 255, message = "台标小图长度必须介于 0 和 255 之间")
    @ExcelField(title = "台标小图", align = 2, sort = 27)
    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    @ExcelField(title = "状态", align = 2, sort = 28)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ExcelField(title = "排序", align = 2, sort = 29)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Length(min = 0, max = 255, message = "附加字段1长度必须介于 0 和 255 之间")
    @ExcelField(title = "附加字段1", align = 2, sort = 36)
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public List<ZmsChannelCategory> getZmsChannelCategoryList() {
        return zmsChannelCategoryList;
    }

    public void setZmsChannelCategoryList(List<ZmsChannelCategory> zmsChannelCategoryList) {
        this.zmsChannelCategoryList = zmsChannelCategoryList;
    }

    public List<String> getZmsCategorySaveList() {
        return zmsCategorySaveList;
    }

    public void setZmsCategorySaveList(List<String> zmsCategorySaveList) {
        this.zmsCategorySaveList = zmsCategorySaveList;
    }
}
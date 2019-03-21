/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 视频项目Entity
 *
 * @author lpz
 * @version 2018-11-14
 */
public class CmsProgram extends DataEntity<CmsProgram> {

    private static final long serialVersionUID = 1L;
    private String cpContentid;        // cp内容标识
    private CmsSp spId;        // 内容提供商
    private String name;        // 名称
    private String originalName;        // 原始名称
    private String spCategoryid;        // sp栏目id
    private String sortName;        // 排序名称
    private String searchName;        // 搜索名称
    private String genre;        // 风格
    private CmsProgramLang langId;        // 语言
    private CmsProgramRegion regionId;        // 国家地区
    private CmsProgramType typeId;        // 影片类型
    private String tagId;        // 标签
    private Integer releaseYear;        // 上映年份
    private Date orgAirDate;        // 首播日期
    private Date licensingWindowStart;        // 有效开始时间
    private Date licensingWindowEnd;        // 有效结束时间
    private String displayAsNew;        // 新到天数
    private String displayAsLastChance;        // 剩余天数
    private Integer macroVision;        // 拷贝保护标志
    private String description;        // 描述
    private Integer priceTaxin;        // 列表定价
    private String sourceType;        // 源类型
    private String seriesFlag;        // 连续剧标志
    private Integer totalEpisode;        // 总集数
    private String keyWord;        // 关键字
    private String viewPoint;        // 看点
    private String starLevel;        // 星级推荐
    private String rating;        // 分级
    private String awards;        // 奖项
    private String length;        // 播放时长(秒)
    private Integer syncState;        // 同步状态
    private Integer cmsState;        // CMS状态

    private String hBigPic;         // 横大图
    private String hSmallPic;       // 横小图
    private String vBigPic;         // 竖大图
    private String vSmallPic;       // 竖小图
    private String squarePic;       // 方形图
    private String picUrl;          // 海报图

    private Integer status;        // 状态
    private Long sort;        // 排序
    private String remark1;        // 附加字段1

    /** 影片展示类型 0:电视剧 1:电影 2:综艺 */
    private String showType;


    /**
     * 影片类型列表
     */
    private List<CmsProgramType> cmsProgramTypeList = Lists.newArrayList();
    private List<String> cmsTypeSaveList;

    /**
     * 影片国家地区列表
     */
    private List<CmsProgramRegion> cmsProgramRegionList = Lists.newArrayList();
    private List<String> cmsRegionSaveList;

    /**
     * 影片语言列表
     */
    private List<CmsProgramLang> cmsProgramLangList = Lists.newArrayList();
    private List<String> cmsLangSaveList;

    /**
     * 影片栏目列表
     */
    private List<CmsProgramCategory> cmsProgramCategoryList = Lists.newArrayList();
    private List<String> cmsCategorySaveList;

    private CmsCategory cmsCategoryVo;

    public CmsProgram() {
        super();
    }

    public CmsProgram(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "cp内容标识长度必须介于 0 和 64 之间")
    @ExcelField(title = "cp内容标识", align = 2, sort = 1)
    public String getCpContentid() {
        return cpContentid;
    }

    public void setCpContentid(String cpContentid) {
        this.cpContentid = cpContentid;
    }

    @ExcelField(title = "内容提供商", align = 2, sort = 2)
    public CmsSp getSpId() {
        return spId;
    }

    public void setSpId(CmsSp spId) {
        this.spId = spId;
    }

    @Length(min = 0, max = 128, message = "名称长度必须介于 0 和 128 之间")
    @ExcelField(title = "名称", align = 2, sort = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 128, message = "原始名称长度必须介于 0 和 128 之间")
    @ExcelField(title = "原始名称", align = 2, sort = 4)
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Length(min = 0, max = 64, message = "sp栏目id长度必须介于 0 和 64 之间")
    @ExcelField(title = "sp栏目id", align = 2, sort = 5)
    public String getSpCategoryid() {
        return spCategoryid;
    }

    public void setSpCategoryid(String spCategoryid) {
        this.spCategoryid = spCategoryid;
    }

    @Length(min = 0, max = 128, message = "排序名称长度必须介于 0 和 128 之间")
    @ExcelField(title = "排序名称", align = 2, sort = 6)
    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @Length(min = 0, max = 128, message = "搜索名称长度必须介于 0 和 128 之间")
    @ExcelField(title = "搜索名称", align = 2, sort = 7)
    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Length(min = 0, max = 128, message = "风格长度必须介于 0 和 128 之间")
    @ExcelField(title = "风格", align = 2, sort = 8)
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @ExcelField(title = "语言", align = 2, sort = 9)
    public CmsProgramLang getLangId() {
        return langId;
    }

    public void setLangId(CmsProgramLang langId) {
        this.langId = langId;
    }

    @ExcelField(title = "国家地区", align = 2, sort = 10)
    public CmsProgramRegion getRegionId() {
        return regionId;
    }

    public void setRegionId(CmsProgramRegion regionId) {
        this.regionId = regionId;
    }

    @ExcelField(title = "影片类型", align = 2, sort = 11)
    public CmsProgramType getTypeId() {
        return typeId;
    }

    public void setTypeId(CmsProgramType typeId) {
        this.typeId = typeId;
    }

    @Length(min = 0, max = 2000, message = "标签长度必须介于 0 和 2000 之间")
    @ExcelField(title = "标签", align = 2, sort = 12)
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @ExcelField(title = "上映年份", align = 2, sort = 13)
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "首播日期", align = 2, sort = 14)
    public Date getOrgAirDate() {
        return orgAirDate;
    }

    public void setOrgAirDate(Date orgAirDate) {
        this.orgAirDate = orgAirDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "有效开始时间", align = 2, sort = 15)
    public Date getLicensingWindowStart() {
        return licensingWindowStart;
    }

    public void setLicensingWindowStart(Date licensingWindowStart) {
        this.licensingWindowStart = licensingWindowStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "有效结束时间", align = 2, sort = 16)
    public Date getLicensingWindowEnd() {
        return licensingWindowEnd;
    }

    public void setLicensingWindowEnd(Date licensingWindowEnd) {
        this.licensingWindowEnd = licensingWindowEnd;
    }

    @Length(min = 0, max = 8, message = "新到天数长度必须介于 0 和 8 之间")
    @ExcelField(title = "新到天数", align = 2, sort = 17)
    public String getDisplayAsNew() {
        return displayAsNew;
    }

    public void setDisplayAsNew(String displayAsNew) {
        this.displayAsNew = displayAsNew;
    }

    @Length(min = 0, max = 8, message = "剩余天数长度必须介于 0 和 8 之间")
    @ExcelField(title = "剩余天数", align = 2, sort = 18)
    public String getDisplayAsLastChance() {
        return displayAsLastChance;
    }

    public void setDisplayAsLastChance(String displayAsLastChance) {
        this.displayAsLastChance = displayAsLastChance;
    }

    @ExcelField(title = "拷贝保护标志", align = 2, sort = 19)
    public Integer getMacroVision() {
        return macroVision;
    }

    public void setMacroVision(Integer macroVision) {
        this.macroVision = macroVision;
    }

    @Length(min = 0, max = 256, message = "描述长度必须介于 0 和 256 之间")
    @ExcelField(title = "描述", align = 2, sort = 20)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ExcelField(title = "列表定价", align = 2, sort = 21)
    public Integer getPriceTaxin() {
        return priceTaxin;
    }

    public void setPriceTaxin(Integer priceTaxin) {
        this.priceTaxin = priceTaxin;
    }

    @Length(min = 0, max = 8, message = "源类型长度必须介于 0 和 8 之间")
    @ExcelField(title = "源类型", dictType = "cms_program_source_type", align = 2, sort = 22)
    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @Length(min = 0, max = 8, message = "连续剧标志长度必须介于 0 和 8 之间")
    @ExcelField(title = "连续剧标志", dictType = "cms_program_series_flag", align = 2, sort = 23)
    public String getSeriesFlag() {
        return seriesFlag;
    }

    public void setSeriesFlag(String seriesFlag) {
        this.seriesFlag = seriesFlag;
    }

    @ExcelField(title = "总集数", align = 2, sort = 24)
    public Integer getTotalEpisode() {
        return totalEpisode;
    }

    public void setTotalEpisode(Integer totalEpisode) {
        this.totalEpisode = totalEpisode;
    }

    @Length(min = 0, max = 256, message = "关键字长度必须介于 0 和 256 之间")
    @ExcelField(title = "关键字", align = 2, sort = 25)
    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Length(min = 0, max = 256, message = "看点长度必须介于 0 和 256 之间")
    @ExcelField(title = "看点", align = 2, sort = 26)
    public String getViewPoint() {
        return viewPoint;
    }

    public void setViewPoint(String viewPoint) {
        this.viewPoint = viewPoint;
    }

    @Length(min = 0, max = 8, message = "星级推荐长度必须介于 0 和 8 之间")
    @ExcelField(title = "星级推荐", align = 2, sort = 27)
    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    @Length(min = 0, max = 64, message = "分级长度必须介于 0 和 64 之间")
    @ExcelField(title = "分级", dictType = "cms_program_rating", align = 2, sort = 28)
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Length(min = 0, max = 128, message = "奖项长度必须介于 0 和 128 之间")
    @ExcelField(title = "奖项", align = 2, sort = 29)
    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    @Length(min = 0, max = 4, message = "播放时长(秒)长度必须介于 0 和 4 之间")
    @ExcelField(title = "播放时长(秒)", align = 2, sort = 30)
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @ExcelField(title = "同步状态", align = 2, sort = 31)
    public Integer getSyncState() {
        return syncState;
    }

    public void setSyncState(Integer syncState) {
        this.syncState = syncState;
    }

    @ExcelField(title = "CMS状态", align = 2, sort = 32)
    public Integer getCmsState() {
        return cmsState;
    }

    public void setCmsState(Integer cmsState) {
        this.cmsState = cmsState;
    }

    @ExcelField(title = "状态", dictType = "general_status", align = 2, sort = 33)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ExcelField(title = "排序", align = 2, sort = 34)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Length(min = 0, max = 255, message = "附加字段1长度必须介于 0 和 255 之间")
    @ExcelField(title = "附加字段1", align = 2, sort = 41)
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public List<CmsProgramType> getCmsProgramTypeList() {
        return cmsProgramTypeList;
    }

    public void setCmsProgramTypeList(List<CmsProgramType> cmsProgramTypeList) {
        this.cmsProgramTypeList = cmsProgramTypeList;
    }

    public List<String> getCmsTypeSaveList() {
        return cmsTypeSaveList;
    }

    public void setCmsTypeSaveList(List<String> cmsTypeSaveList) {
        this.cmsTypeSaveList = cmsTypeSaveList;
    }

    public List<CmsProgramRegion> getCmsProgramRegionList() {
        return cmsProgramRegionList;
    }

    public void setCmsProgramRegionList(List<CmsProgramRegion> cmsProgramRegionList) {
        this.cmsProgramRegionList = cmsProgramRegionList;
    }

    public List<String> getCmsRegionSaveList() {
        return cmsRegionSaveList;
    }

    public void setCmsRegionSaveList(List<String> cmsRegionSaveList) {
        this.cmsRegionSaveList = cmsRegionSaveList;
    }

    public List<CmsProgramLang> getCmsProgramLangList() {
        return cmsProgramLangList;
    }

    public void setCmsProgramLangList(List<CmsProgramLang> cmsProgramLangList) {
        this.cmsProgramLangList = cmsProgramLangList;
    }

    public List<String> getCmsLangSaveList() {
        return cmsLangSaveList;
    }

    public void setCmsLangSaveList(List<String> cmsLangSaveList) {
        this.cmsLangSaveList = cmsLangSaveList;
    }

    public List<CmsProgramCategory> getCmsProgramCategoryList() {
        return cmsProgramCategoryList;
    }

    public void setCmsProgramCategoryList(List<CmsProgramCategory> cmsProgramCategoryList) {
        this.cmsProgramCategoryList = cmsProgramCategoryList;
    }

    public List<String> getCmsCategorySaveList() {
        return cmsCategorySaveList;
    }

    public void setCmsCategorySaveList(List<String> cmsCategorySaveList) {
        this.cmsCategorySaveList = cmsCategorySaveList;
    }

    public String gethBigPic() {
        return hBigPic;
    }

    public void sethBigPic(String hBigPic) {
        this.hBigPic = hBigPic;
    }

    public String gethSmallPic() {
        return hSmallPic;
    }

    public void sethSmallPic(String hSmallPic) {
        this.hSmallPic = hSmallPic;
    }

    public String getvBigPic() {
        return vBigPic;
    }

    public void setvBigPic(String vBigPic) {
        this.vBigPic = vBigPic;
    }

    public String getvSmallPic() {
        return vSmallPic;
    }

    public void setvSmallPic(String vSmallPic) {
        this.vSmallPic = vSmallPic;
    }

    public String getSquarePic() {
        return squarePic;
    }

    public void setSquarePic(String squarePic) {
        this.squarePic = squarePic;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public CmsCategory getCmsCategoryVo() {
        return cmsCategoryVo;
    }

    public void setCmsCategoryVo(CmsCategory cmsCategoryVo) {
        this.cmsCategoryVo = cmsCategoryVo;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
}
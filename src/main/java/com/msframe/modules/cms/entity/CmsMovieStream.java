/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 电影播放流Entity
 * @author lpz
 * @version 2018-11-14
 */
public class CmsMovieStream extends DataEntity<CmsMovieStream> {
	
	private static final long serialVersionUID = 1L;
	private CmsMovie movieId;		// 视频资源ID
	private String aspectRatioId;		// 媒体纵横比（与表TAspectRatio关联）
	private Long duration;		// 时长（精确到秒）
	private String closedCaptioning;		// 存在字幕信息
	private String resolutionId;		// 分辨率类型
	private String fileSize;		// 文件大小（精确到MB）
	private String streamId;		// 码流(与表TStream)关联
	private String contentId;		// 内容标识
	private String videoTypeId;		// 视频格式(与表TVideoType)关联
	private Integer audioType;		// 音频编码格式mp2 2 aac 3 amr
	private String systemLayerId;		// 系统编码格式(与表TSystemLayer )关联
	private String copyrightId;		// 版权证号
	private Date copyrightEndtime;		// 版权到期日期
	private String copyrightProvider;		// 授权方信息
	private String licenseNumber;		// 引进批准文号
	private String license;		// 公映放映许可证号
	private String moviesPlayinfo;		// 视频的播放地址
	private Integer volume;		// 该流属于第几集（暂时使用在手机平台）
	private String auditNumber;		// 审核情况
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public CmsMovieStream() {
		super();
	}

	public CmsMovieStream(String id){
		super(id);
	}

	@ExcelField(title="视频资源ID", align=2, sort=1)
	public CmsMovie getMovieId() {
		return movieId;
	}

	public void setMovieId(CmsMovie movieId) {
		this.movieId = movieId;
	}
	
	@Length(min=0, max=64, message="媒体纵横比（与表TAspectRatio关联）长度必须介于 0 和 64 之间")
	@ExcelField(title="媒体纵横比（与表TAspectRatio关联）", align=2, sort=2)
	public String getAspectRatioId() {
		return aspectRatioId;
	}

	public void setAspectRatioId(String aspectRatioId) {
		this.aspectRatioId = aspectRatioId;
	}
	
	@ExcelField(title="时长（精确到秒）", align=2, sort=3)
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	@Length(min=0, max=2, message="存在字幕信息长度必须介于 0 和 2 之间")
	@ExcelField(title="存在字幕信息", align=2, sort=4)
	public String getClosedCaptioning() {
		return closedCaptioning;
	}

	public void setClosedCaptioning(String closedCaptioning) {
		this.closedCaptioning = closedCaptioning;
	}
	
	@Length(min=0, max=64, message="分辨率类型长度必须介于 0 和 64 之间")
	@ExcelField(title="分辨率类型", align=2, sort=5)
	public String getResolutionId() {
		return resolutionId;
	}

	public void setResolutionId(String resolutionId) {
		this.resolutionId = resolutionId;
	}
	
	@Length(min=0, max=10, message="文件大小（精确到MB）长度必须介于 0 和 10 之间")
	@ExcelField(title="文件大小（精确到MB）", align=2, sort=6)
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	@Length(min=0, max=64, message="码流(与表TStream)关联长度必须介于 0 和 64 之间")
	@ExcelField(title="码流(与表TStream)关联", align=2, sort=7)
	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	
	@Length(min=0, max=64, message="内容标识长度必须介于 0 和 64 之间")
	@ExcelField(title="内容标识", align=2, sort=8)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@Length(min=0, max=64, message="视频格式(与表TVideoType)关联长度必须介于 0 和 64 之间")
	@ExcelField(title="视频格式(与表TVideoType)关联", align=2, sort=9)
	public String getVideoTypeId() {
		return videoTypeId;
	}

	public void setVideoTypeId(String videoTypeId) {
		this.videoTypeId = videoTypeId;
	}
	
	@ExcelField(title="音频编码格式mp2 2 aac 3 amr", align=2, sort=10)
	public Integer getAudioType() {
		return audioType;
	}

	public void setAudioType(Integer audioType) {
		this.audioType = audioType;
	}
	
	@Length(min=0, max=64, message="系统编码格式(与表TSystemLayer )关联长度必须介于 0 和 64 之间")
	@ExcelField(title="系统编码格式(与表TSystemLayer )关联", align=2, sort=11)
	public String getSystemLayerId() {
		return systemLayerId;
	}

	public void setSystemLayerId(String systemLayerId) {
		this.systemLayerId = systemLayerId;
	}
	
	@Length(min=0, max=50, message="版权证号长度必须介于 0 和 50 之间")
	@ExcelField(title="版权证号", align=2, sort=12)
	public String getCopyrightId() {
		return copyrightId;
	}

	public void setCopyrightId(String copyrightId) {
		this.copyrightId = copyrightId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="版权到期日期", align=2, sort=13)
	public Date getCopyrightEndtime() {
		return copyrightEndtime;
	}

	public void setCopyrightEndtime(Date copyrightEndtime) {
		this.copyrightEndtime = copyrightEndtime;
	}
	
	@Length(min=0, max=100, message="授权方信息长度必须介于 0 和 100 之间")
	@ExcelField(title="授权方信息", align=2, sort=14)
	public String getCopyrightProvider() {
		return copyrightProvider;
	}

	public void setCopyrightProvider(String copyrightProvider) {
		this.copyrightProvider = copyrightProvider;
	}
	
	@Length(min=0, max=50, message="引进批准文号长度必须介于 0 和 50 之间")
	@ExcelField(title="引进批准文号", align=2, sort=15)
	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	@Length(min=0, max=50, message="公映放映许可证号长度必须介于 0 和 50 之间")
	@ExcelField(title="公映放映许可证号", align=2, sort=16)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	@Length(min=0, max=500, message="视频的播放地址长度必须介于 0 和 500 之间")
	@ExcelField(title="视频的播放地址", align=2, sort=17)
	public String getMoviesPlayinfo() {
		return moviesPlayinfo;
	}

	public void setMoviesPlayinfo(String moviesPlayinfo) {
		this.moviesPlayinfo = moviesPlayinfo;
	}
	
	@ExcelField(title="该流属于第几集（暂时使用在手机平台）", dictType="cms_movie_stream_volume", align=2, sort=18)
	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	
	@Length(min=0, max=2, message="审核情况长度必须介于 0 和 2 之间")
	@ExcelField(title="审核情况", dictType="cms_movie_stream_audit_number", align=2, sort=19)
	public String getAuditNumber() {
		return auditNumber;
	}

	public void setAuditNumber(String auditNumber) {
		this.auditNumber = auditNumber;
	}
	
	@ExcelField(title="状态", align=2, sort=20)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=21)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=28)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
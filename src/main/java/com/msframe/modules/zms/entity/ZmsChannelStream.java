/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.zms.entity;

import org.hibernate.validator.constraints.Length;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 直播节目流信息Entity
 * @author wlh
 * @version 2018-11-13
 */
public class ZmsChannelStream extends DataEntity<ZmsChannelStream> {
	
	private static final long serialVersionUID = 1L;
	private String contentId;		// 内容标识
	private ZmsChannel channelId;		// 频道编号
	private String channelCode;		// 标识code
	private Integer bitratecode;		// 码流
	private String multicastIp;		// 直播ip地址
	private String multicastPort;		// 直播端口号
	private String channelContentId;		// 频道标志
	private Integer videoType;		// 视频编码格式
	private Integer audioType;		// 音频编码格式
	private Integer resolution;		// 分辨率
	private Integer systemLayer;		// 封装格式
	private String playUrl;		// 具体播放地址
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public ZmsChannelStream() {
		super();
	}

	public ZmsChannelStream(String id){
		super(id);
	}

	@Length(min=0, max=64, message="内容标识长度必须介于 0 和 64 之间")
	@ExcelField(title="内容标识", align=2, sort=1)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@ExcelField(title="频道编号", align=2, sort=2)
	public ZmsChannel getChannelId() {
		return channelId;
	}

	public void setChannelId(ZmsChannel channelId) {
		this.channelId = channelId;
	}
	
	@Length(min=0, max=64, message="标识code长度必须介于 0 和 64 之间")
	@ExcelField(title="标识code", align=2, sort=3)
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@ExcelField(title="码流", dictType="zms_channel_stream_bitratecode", align=2, sort=4)
	public Integer getBitratecode() {
		return bitratecode;
	}

	public void setBitratecode(Integer bitratecode) {
		this.bitratecode = bitratecode;
	}
	
	@Length(min=0, max=20, message="直播ip地址长度必须介于 0 和 20 之间")
	@ExcelField(title="直播ip地址", align=2, sort=5)
	public String getMulticastIp() {
		return multicastIp;
	}

	public void setMulticastIp(String multicastIp) {
		this.multicastIp = multicastIp;
	}
	
	@Length(min=0, max=20, message="直播端口号长度必须介于 0 和 20 之间")
	@ExcelField(title="直播端口号", align=2, sort=6)
	public String getMulticastPort() {
		return multicastPort;
	}

	public void setMulticastPort(String multicastPort) {
		this.multicastPort = multicastPort;
	}
	
	@Length(min=0, max=64, message="频道标志长度必须介于 0 和 64 之间")
	@ExcelField(title="频道标志", align=2, sort=7)
	public String getChannelContentId() {
		return channelContentId;
	}

	public void setChannelContentId(String channelContentId) {
		this.channelContentId = channelContentId;
	}
	
	@ExcelField(title="视频编码格式", dictType="general_video_type", align=2, sort=8)
	public Integer getVideoType() {
		return videoType;
	}

	public void setVideoType(Integer videoType) {
		this.videoType = videoType;
	}
	
	@ExcelField(title="音频编码格式", dictType="general_audio_type", align=2, sort=9)
	public Integer getAudioType() {
		return audioType;
	}

	public void setAudioType(Integer audioType) {
		this.audioType = audioType;
	}
	
	@ExcelField(title="分辨率", dictType="zms_channel_stream_resolution", align=2, sort=10)
	public Integer getResolution() {
		return resolution;
	}

	public void setResolution(Integer resolution) {
		this.resolution = resolution;
	}
	
	@ExcelField(title="封装格式", dictType="zms_channel_stream_system_layer", align=2, sort=11)
	public Integer getSystemLayer() {
		return systemLayer;
	}

	public void setSystemLayer(Integer systemLayer) {
		this.systemLayer = systemLayer;
	}
	
	@Length(min=0, max=512, message="具体播放地址长度必须介于 0 和 512 之间")
	@ExcelField(title="具体播放地址", align=2, sort=12)
	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=13)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=14)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=21)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
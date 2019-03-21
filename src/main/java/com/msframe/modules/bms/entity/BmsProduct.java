/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 产品信息Entity
 * @author jjj
 * @version 2018-11-14
 */
public class BmsProduct extends DataEntity<BmsProduct> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String productIdent;		// 产品标识
	private String description;		// 描述
	private Integer productType;		// 产品类型
	private String cpspId;		// 服务商
	private Integer bizType;		// 业务类型
	private Integer renderPeriod;		// 租用有效期
	private String tariffPolicyCode;		// tariff_policy_code
	private String discountPolicyCode;		// 优惠策略编号
	private Integer createSrc;		// 来源
	private Integer feeType;		// 计费类别
	private Integer fixedFee;		// 包月封顶费
	private Integer listPrice;		// 价格
	private Integer limitTimes;		// 包次
	private Date promoteExpireTime;		// 推广期截止时间
	private Integer usageMethod;		// 使用方式
	private Integer rollCharge;		// 自动续订
	private Integer creditLevel;		// 信用度
	private Integer level;		// 级别
	private Integer multiScreenPd;		// 多屏产品
	private Integer subEffectType;		// 定购生效方式
	private Integer trialDuration;		// 试用时间（天）
	private Integer subPaymentType;		// 定购付款方式
	private Integer unsubEffectType;		// 取消定购生效方式
	private Integer unsubRefundType;		// 退定退款方式
	private Date startTime;		// 开始时间
	/**
	 * 开始 开始时间
	 */
	private Date startTimeBegin;
	/**
	 * 结束 开始时间
	 */
	private Date startTimeEnd;

	private Date expireTime;		// 过期时间
	/**
	 * 开始 过期时间
	 */
	private Date expireTimeBegin;
	/**
	 * 结束 过期时间
	 */
	private Date expireTimeEnd;

	private Date reviewTime;		// 审核时间
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public BmsProduct() {
		super();
	}

	public BmsProduct(String id){
		super(id);
	}

	@Length(min=0, max=128, message="名称长度必须介于 0 和 128 之间")
	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="产品标识长度必须介于 0 和 64 之间")
	@ExcelField(title="产品标识", align=2, sort=2)
	public String getProductIdent() {
		return productIdent;
	}

	public void setProductIdent(String productIdent) {
		this.productIdent = productIdent;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	@ExcelField(title="描述", align=2, sort=3)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="产品类型", dictType="bms_product_product_type", align=2, sort=4)
	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
	@Length(min=0, max=64, message="服务商长度必须介于 0 和 64 之间")
	@ExcelField(title="服务商", align=2, sort=5)
	public String getCpspId() {
		return cpspId;
	}

	public void setCpspId(String cpspId) {
		this.cpspId = cpspId;
	}
	
	@ExcelField(title="业务类型", align=2, sort=6)
	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	
	@ExcelField(title="租用有效期", align=2, sort=7)
	public Integer getRenderPeriod() {
		return renderPeriod;
	}

	public void setRenderPeriod(Integer renderPeriod) {
		this.renderPeriod = renderPeriod;
	}
	
	@Length(min=0, max=64, message="tariff_policy_code长度必须介于 0 和 64 之间")
	@ExcelField(title="tariff_policy_code", align=2, sort=8)
	public String getTariffPolicyCode() {
		return tariffPolicyCode;
	}

	public void setTariffPolicyCode(String tariffPolicyCode) {
		this.tariffPolicyCode = tariffPolicyCode;
	}
	
	@Length(min=0, max=64, message="优惠策略编号长度必须介于 0 和 64 之间")
	@ExcelField(title="优惠策略编号", align=2, sort=9)
	public String getDiscountPolicyCode() {
		return discountPolicyCode;
	}

	public void setDiscountPolicyCode(String discountPolicyCode) {
		this.discountPolicyCode = discountPolicyCode;
	}
	
	@ExcelField(title="来源", dictType="bms_product_create_src", align=2, sort=10)
	public Integer getCreateSrc() {
		return createSrc;
	}

	public void setCreateSrc(Integer createSrc) {
		this.createSrc = createSrc;
	}
	
	@ExcelField(title="计费类别", dictType="bms_product_fee_type", align=2, sort=11)
	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	
	@ExcelField(title="包月封顶费", align=2, sort=12)
	public Integer getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(Integer fixedFee) {
		this.fixedFee = fixedFee;
	}
	
	@ExcelField(title="价格", align=2, sort=13)
	public Integer getListPrice() {
		return listPrice;
	}

	public void setListPrice(Integer listPrice) {
		this.listPrice = listPrice;
	}
	
	@ExcelField(title="包次", align=2, sort=14)
	public Integer getLimitTimes() {
		return limitTimes;
	}

	public void setLimitTimes(Integer limitTimes) {
		this.limitTimes = limitTimes;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="推广期截止时间", align=2, sort=15)
	public Date getPromoteExpireTime() {
		return promoteExpireTime;
	}

	public void setPromoteExpireTime(Date promoteExpireTime) {
		this.promoteExpireTime = promoteExpireTime;
	}
	
	@ExcelField(title="使用方式", dictType="bms_product_usage_method", align=2, sort=16)
	public Integer getUsageMethod() {
		return usageMethod;
	}

	public void setUsageMethod(Integer usageMethod) {
		this.usageMethod = usageMethod;
	}
	
	@ExcelField(title="自动续订", dictType="bms_product_roll_charge", align=2, sort=17)
	public Integer getRollCharge() {
		return rollCharge;
	}

	public void setRollCharge(Integer rollCharge) {
		this.rollCharge = rollCharge;
	}
	
	@ExcelField(title="信用度", dictType="bms_product_credit_level", align=2, sort=18)
	public Integer getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(Integer creditLevel) {
		this.creditLevel = creditLevel;
	}
	
	@ExcelField(title="级别", dictType="bms_product_level", align=2, sort=19)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@ExcelField(title="多屏产品", dictType="bms_product_multi_screen_pd", align=2, sort=20)
	public Integer getMultiScreenPd() {
		return multiScreenPd;
	}

	public void setMultiScreenPd(Integer multiScreenPd) {
		this.multiScreenPd = multiScreenPd;
	}
	
	@ExcelField(title="定购生效方式", dictType="bms_product_sub_effect_type", align=2, sort=21)
	public Integer getSubEffectType() {
		return subEffectType;
	}

	public void setSubEffectType(Integer subEffectType) {
		this.subEffectType = subEffectType;
	}
	
	@ExcelField(title="试用时间（天）", align=2, sort=22)
	public Integer getTrialDuration() {
		return trialDuration;
	}

	public void setTrialDuration(Integer trialDuration) {
		this.trialDuration = trialDuration;
	}
	
	@ExcelField(title="定购付款方式", dictType="bms_product_sub_payment_type", align=2, sort=23)
	public Integer getSubPaymentType() {
		return subPaymentType;
	}

	public void setSubPaymentType(Integer subPaymentType) {
		this.subPaymentType = subPaymentType;
	}
	
	@ExcelField(title="取消定购生效方式", dictType="bms_product_unsub_effect_type", align=2, sort=24)
	public Integer getUnsubEffectType() {
		return unsubEffectType;
	}

	public void setUnsubEffectType(Integer unsubEffectType) {
		this.unsubEffectType = unsubEffectType;
	}
	
	@ExcelField(title="退定退款方式", dictType="bms_product_unsub_refund_type", align=2, sort=25)
	public Integer getUnsubRefundType() {
		return unsubRefundType;
	}

	public void setUnsubRefundType(Integer unsubRefundType) {
		this.unsubRefundType = unsubRefundType;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间", align=2, sort=27)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTimeBegin(){
		return startTimeBegin;
	}

	public void setStartTimeBegin(Date startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}

	public Date getStartTimeEnd(){
		return startTimeEnd;
	}

	public void setStartTimeEnd(Date startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="过期时间", align=2, sort=27)
	public Date getExpireTime() {
		return expireTime;
	}

	public Date getExpireTimeBegin() {
		return expireTimeBegin;
	}

	public void setExpireTimeBegin(Date expireTimeBegin) {
		this.expireTimeBegin = expireTimeBegin;
	}

	public Date getExpireTimeEnd() {
		return expireTimeEnd;
	}

	public void setExpireTimeEnd(Date expireTimeEnd) {
		this.expireTimeEnd = expireTimeEnd;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=28)
	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=29)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="排序", align=2, sort=30)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="附加字段1长度必须介于 0 和 255 之间")
	@ExcelField(title="附加字段1", align=2, sort=37)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
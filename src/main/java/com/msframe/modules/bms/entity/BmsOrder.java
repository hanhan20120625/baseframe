/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.bms.entity;

import com.msframe.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.msframe.common.persistence.DataEntity;
import com.msframe.common.utils.excel.annotation.ExcelField;

/**
 * 用户订单产品信息Entity
 * @author jjj
 * @version 2018-11-14
 */
public class BmsOrder extends DataEntity<BmsOrder> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户编号
	private String orderNumber;		// 订单编号
	private String username;		// 用户名
	private String nickname;		// 昵称
	private String productId;		// 产品编号
	private String productName;		// 产品名称
	private String productIdent;		// 产品标识
	private String price;		// 产品价格
	private Integer ispay;		// 是否支付
	private Integer payType;		// 支付类型
	private String payOrderNumber;		// 支付订单号
	private Date orderTime;		// 支付时间
	private Date payReturnTime;		// 支付返回日期
	private Integer result;		// 支付结果
	private Integer rollCharge;		// 自动续订
	private String rollAccountType;		// 滚账类型
	private Date lastAccountTime;		// 下一次滚账时间
	private String lastAccountStatus;		// 滚账状态
	private String lastAccountReason;		// 滚账失败原因
	private Integer status;		// 状态
	private Long sort;		// 排序
	private String remark1;		// 附加字段1
	
	public BmsOrder() {
		super();
	}

	public BmsOrder(String id){
		super(id);
	}

	@ExcelField(title="用户编号", fieldType=User.class, value="user.name", align=2, sort=1)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="订单编号长度必须介于 0 和 64 之间")
	@ExcelField(title="订单编号", align=2, sort=2)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	@Length(min=0, max=64, message="用户名长度必须介于 0 和 64 之间")
	@ExcelField(title="用户名", align=2, sort=3)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=64, message="昵称长度必须介于 0 和 64 之间")
	@ExcelField(title="昵称", align=2, sort=4)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=64, message="产品编号长度必须介于 0 和 64 之间")
	@ExcelField(title="产品编号", align=2, sort=5)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=64, message="产品名称长度必须介于 0 和 64 之间")
	@ExcelField(title="产品名称", align=2, sort=6)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=64, message="产品标识长度必须介于 0 和 64 之间")
	@ExcelField(title="产品标识", align=2, sort=7)
	public String getProductIdent() {
		return productIdent;
	}

	public void setProductIdent(String productIdent) {
		this.productIdent = productIdent;
	}
	
	@ExcelField(title="产品价格", align=2, sort=8)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@ExcelField(title="是否支付", dictType="bms_order_ispay", align=2, sort=9)
	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}
	
	@ExcelField(title="支付类型", align=2, sort=10)
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=100, message="支付订单号长度必须介于 0 和 100 之间")
	@ExcelField(title="支付订单号", align=2, sort=11)
	public String getPayOrderNumber() {
		return payOrderNumber;
	}

	public void setPayOrderNumber(String payOrderNumber) {
		this.payOrderNumber = payOrderNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付时间", align=2, sort=12)
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付返回日期", align=2, sort=13)
	public Date getPayReturnTime() {
		return payReturnTime;
	}

	public void setPayReturnTime(Date payReturnTime) {
		this.payReturnTime = payReturnTime;
	}
	
	@ExcelField(title="支付结果", align=2, sort=14)
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
	
	@ExcelField(title="自动续订", dictType="bms_order_roll_charge", align=2, sort=15)
	public Integer getRollCharge() {
		return rollCharge;
	}

	public void setRollCharge(Integer rollCharge) {
		this.rollCharge = rollCharge;
	}
	
	@Length(min=0, max=1, message="滚账类型长度必须介于 0 和 1 之间")
	@ExcelField(title="滚账类型", dictType="bms_order_roll_account_type", align=2, sort=16)
	public String getRollAccountType() {
		return rollAccountType;
	}

	public void setRollAccountType(String rollAccountType) {
		this.rollAccountType = rollAccountType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="下一次滚账时间不能为空")
	@ExcelField(title="下一次滚账时间", align=2, sort=17)
	public Date getLastAccountTime() {
		return lastAccountTime;
	}

	public void setLastAccountTime(Date lastAccountTime) {
		this.lastAccountTime = lastAccountTime;
	}
	
	@Length(min=0, max=1, message="滚账状态长度必须介于 0 和 1 之间")
	@ExcelField(title="滚账状态", dictType="bms_order_last_account_status", align=2, sort=18)
	public String getLastAccountStatus() {
		return lastAccountStatus;
	}

	public void setLastAccountStatus(String lastAccountStatus) {
		this.lastAccountStatus = lastAccountStatus;
	}
	
	@Length(min=0, max=1000, message="滚账失败原因长度必须介于 0 和 1000 之间")
	@ExcelField(title="滚账失败原因", align=2, sort=19)
	public String getLastAccountReason() {
		return lastAccountReason;
	}

	public void setLastAccountReason(String lastAccountReason) {
		this.lastAccountReason = lastAccountReason;
	}
	
	@ExcelField(title="状态", dictType="general_status", align=2, sort=20)
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
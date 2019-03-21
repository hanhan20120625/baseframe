<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>产品信息管理</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
					laydate({
			            elem: '#promoteExpireTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#expireTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#reviewTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="bmsProduct" action="${ctx}/bms/bmsProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right">名称</label></td>
					<td class="width-35">
						${bmsProduct.name}
					</td>
					<td class="width-15 active"><label class="pull-right">产品标识</label></td>
					<td class="width-35">
						${bmsProduct.productIdent}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">描述</label></td>
					<td class="width-35">
						${bmsProduct.description}
					</td>
					<td class="width-15 active"><label class="pull-right">产品类型</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.productType, 'bms_product_product_type', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">服务商</label></td>
					<td class="width-35">
						${bmsProduct.cpspId}
					</td>
					<td class="width-15 active"><label class="pull-right">业务类型</label></td>
					<td class="width-35">
						${bmsProduct.bizType}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">租用有效期</label></td>
					<td class="width-35">
						${bmsProduct.renderPeriod}
					</td>
					<td class="width-15 active"><label class="pull-right">tariff_policy_code</label></td>
					<td class="width-35">
						${bmsProduct.tariffPolicyCode}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">优惠策略编号</label></td>
					<td class="width-35">
						${bmsProduct.discountPolicyCode}
					</td>
					<td class="width-15 active"><label class="pull-right">来源</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.createSrc, 'bms_product_create_src', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">计费类别</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.feeType, 'bms_product_fee_type', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">包月封顶费</label></td>
					<td class="width-35">
						${bmsProduct.fixedFee}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">价格</label></td>
					<td class="width-35">
						${bmsProduct.listPrice}
					</td>
					<td class="width-15 active"><label class="pull-right">包次</label></td>
					<td class="width-35">
						${bmsProduct.limitTimes}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">推广期截止时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${bmsProduct.promoteExpireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="width-15 active"><label class="pull-right">使用方式</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.usageMethod, 'bms_product_usage_method', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">自动续订</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.rollCharge, 'bms_product_roll_charge', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">信用度</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.creditLevel, 'bms_product_credit_level', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">级别</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.level, 'bms_product_level', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">多屏产品</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.multiScreenPd, 'bms_product_multi_screen_pd', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">定购生效方式</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.subEffectType, 'bms_product_sub_effect_type', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">试用时间（天）</label></td>
					<td class="width-35">
						${bmsProduct.trialDuration}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">定购付款方式</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.subPaymentType, 'bms_product_sub_payment_type', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">取消定购生效方式</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.unsubEffectType, 'bms_product_unsub_effect_type', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">退定退款方式</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.unsubRefundType, 'bms_product_unsub_refund_type', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">开始时间</label></td>
					<td class="width-35">
						${bmsProduct.startTime}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">过期时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${bmsProduct.expireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="width-15 active"><label class="pull-right">审核时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${bmsProduct.reviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">状态</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsProduct.status, 'general_status', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">排序</label></td>
					<td class="width-35">
						${bmsProduct.sort}
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
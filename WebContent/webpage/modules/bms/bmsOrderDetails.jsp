<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户订单产品信息管理</title>
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
			            elem: '#orderTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#payReturnTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#lastAccountTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="bmsOrder" action="${ctx}/bms/bmsOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right">用户编号</label></td>
					<td class="width-35">
						${bmsOrder.user.name}
					</td>
					<td class="width-15 active"><label class="pull-right">订单编号</label></td>
					<td class="width-35">
						${bmsOrder.orderNumber}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">用户名</label></td>
					<td class="width-35">
						${bmsOrder.username}
					</td>
					<td class="width-15 active"><label class="pull-right">昵称</label></td>
					<td class="width-35">
						${bmsOrder.nickname}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">产品编号</label></td>
					<td class="width-35">
						${bmsOrder.productId}
					</td>
					<td class="width-15 active"><label class="pull-right">产品名称</label></td>
					<td class="width-35">
						${bmsOrder.productName}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">产品标识</label></td>
					<td class="width-35">
						${bmsOrder.productIdent}
					</td>
					<td class="width-15 active"><label class="pull-right">产品价格</label></td>
					<td class="width-35">
						${bmsOrder.price}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否支付</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsOrder.ispay, 'bms_order_ispay', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">支付类型</label></td>
					<td class="width-35">
						${bmsOrder.payType}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">支付订单号</label></td>
					<td class="width-35">
						${bmsOrder.payOrderNumber}
					</td>
					<td class="width-15 active"><label class="pull-right">支付时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${bmsOrder.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">支付返回日期</label></td>
					<td class="width-35">
						<fmt:formatDate value="${bmsOrder.payReturnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="width-15 active"><label class="pull-right">支付结果</label></td>
					<td class="width-35">
						${bmsOrder.result}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">自动续订</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsOrder.rollCharge, 'bms_order_roll_charge', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">滚账类型</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsOrder.rollAccountType, 'bms_order_roll_account_type', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下一次滚账时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${bmsOrder.lastAccountTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="width-15 active"><label class="pull-right">滚账状态</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsOrder.lastAccountStatus, 'bms_order_last_account_status', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">滚账失败原因</label></td>
					<td class="width-35">
						${bmsOrder.lastAccountReason}
					</td>
					<td class="width-15 active"><label class="pull-right">状态</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsOrder.status, 'general_status', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">排序</label></td>
					<td class="width-35">
						${bmsOrder.sort}
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>产品内容管理</title>
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
			
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="bmsOrderProgram" action="${ctx}/bms/bmsOrderProgram/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right">产品编号</label></td>
					<td class="width-35">
						${bmsOrderProgram.productId}
					</td>
					<td class="width-15 active"><label class="pull-right">用户编号</label></td>
					<td class="width-35">
						${bmsOrderProgram.user.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">影片编号</label></td>
					<td class="width-35">
						${bmsOrderProgram.programId}
					</td>
					<td class="width-15 active"><label class="pull-right">订单编号</label></td>
					<td class="width-35">
						${bmsOrderProgram.orderId}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">开始时间</label></td>
					<td class="width-35">
						${bmsOrderProgram.startTime}
					</td>
					<td class="width-15 active"><label class="pull-right">edntime</label></td>
					<td class="width-35">
						${bmsOrderProgram.endTime}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">状态</label></td>
					<td class="width-35">
						${fns:getDictLabel(bmsOrderProgram.status, 'general_status', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">排序</label></td>
					<td class="width-35">
						${bmsOrderProgram.sort}
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
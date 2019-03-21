<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>token管理</title>
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
			            elem: '#expireTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="userToken" action="${ctx}/user/userToken/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right">token标识</label></td>
					<td class="width-35">
						${userToken.token}
					</td>
					<td class="width-15 active"><label class="pull-right">用户编号</label></td>
					<td class="width-35">
						${userToken.user.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">管理员编号</label></td>
					<td class="width-35">
						${userToken.adminId}
					</td>
					<td class="width-15 active"><label class="pull-right">过期时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${userToken.expireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">状态</label></td>
					<td class="width-35">
						${fns:getDictLabel(userToken.status, 'general_status', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">排序</label></td>
					<td class="width-35">
						${userToken.sort}
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>第三方登录管理</title>
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
		<form:form id="inputForm" modelAttribute="userOpenid" action="${ctx}/user/userOpenid/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right">第三方平台标识|</label></td>
					<td class="width-35">
						${userOpenid.openid}
					</td>
					<td class="width-15 active"><label class="pull-right">第三方平台| webo  wechat qq</label></td>
					<td class="width-35">
						${userOpenid.planform}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">昵称</label></td>
					<td class="width-35">
						${userOpenid.nickname}
					</td>
					<td class="width-15 active"><label class="pull-right">性别|0:女;1:男;2:未知;</label></td>
					<td class="width-35">
						${fns:getDictLabel(userOpenid.sex, 'general_sex', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">头像地址</label></td>
					<td class="width-35">
						${userOpenid.headpic}
					</td>
					<td class="width-15 active"><label class="pull-right">用户编号</label></td>
					<td class="width-35">
						${userOpenid.user.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">描述</label></td>
					<td class="width-35">
						${userOpenid.description}
					</td>
					<td class="width-15 active"><label class="pull-right">状态</label></td>
					<td class="width-35">
						${fns:getDictLabel(userOpenid.status, 'general_status', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">排序</label></td>
					<td class="width-35">
						${userOpenid.sort}
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
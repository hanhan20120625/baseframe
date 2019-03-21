<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户信息管理</title>
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
			            elem: '#lastLoginTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#vipStartTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#vipEndTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="user" action="${ctx}/user/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>用户名</label></td>
					<td class="width-35">
						${user.loginName}
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>密码</label></td>
					<td class="width-35">
						${user.password}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">真实名称</label></td>
					<td class="width-35">
						${user.realName}
					</td>
					<td class="width-15 active"><label class="pull-right">昵称</label></td>
					<td class="width-35">
						${user.nickname}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">证件类型</label></td>
					<td class="width-35">
						${fns:getDictLabel(user.idType, 'user_id_type', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">证件号</label></td>
					<td class="width-35">
						${user.idNumber}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">性别</label></td>
					<td class="width-35">
						${fns:getDictLabel(user.sex, 'general_sex', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">手机号</label></td>
					<td class="width-35">
						${user.mobile}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">地址</label></td>
					<td class="width-35">
						${user.address}
					</td>
					<td class="width-15 active"><label class="pull-right">联系电话</label></td>
					<td class="width-35">
						${user.telephone}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">电子邮件</label></td>
					<td class="width-35">
						${user.email}
					</td>
					<td class="width-15 active"><label class="pull-right">传真</label></td>
					<td class="width-35">
						${user.fax}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">头像地址</label></td>
					<td class="width-35">
						${user.headpic}
					</td>
					<td class="width-15 active"><label class="pull-right">登陆次数</label></td>
					<td class="width-35">
						${user.loginTimes}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>最后登陆时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="width-15 active"><label class="pull-right">最后登录IP地址</label></td>
					<td class="width-35">
						${user.lastLoginIp}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否是vip会员</label></td>
					<td class="width-35">
						${fns:getDictLabel(user.isvip, 'user_isvip', '')}
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>vip开始时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${user.vipStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>vip结束时间</label></td>
					<td class="width-35">
						<fmt:formatDate value="${user.vipEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="width-15 active"><label class="pull-right">付费方式</label></td>
					<td class="width-35">
						${fns:getDictLabel(user.payType, 'user_pay_type', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">imsi</label></td>
					<td class="width-35">
						${user.imsi}
					</td>
					<td class="width-15 active"><label class="pull-right">mac地址</label></td>
					<td class="width-35">
						${user.mac}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">积分数量</label></td>
					<td class="width-35">
						${user.score}
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
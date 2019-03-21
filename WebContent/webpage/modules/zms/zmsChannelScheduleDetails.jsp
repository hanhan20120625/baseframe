<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>直播节目单列表管理</title>
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
		<form:form id="inputForm" modelAttribute="zmsChannelSchedule" action="${ctx}/zms/zmsChannelSchedule/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right">频道编号</label></td>
					<td class="width-35">
						${zmsChannelSchedule.channelId}
					</td>
					<td class="width-15 active"><label class="pull-right">内容标识</label></td>
					<td class="width-35">
						${zmsChannelSchedule.contentId}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">直播单编号</label></td>
					<td class="width-35">
						${zmsChannelSchedule.code}
					</td>
					<td class="width-15 active"><label class="pull-right">标识code</label></td>
					<td class="width-35">
						${zmsChannelSchedule.channelCode}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">搜索名称</label></td>
					<td class="width-35">
						${zmsChannelSchedule.searchName}
					</td>
					<td class="width-15 active"><label class="pull-right">节目名称</label></td>
					<td class="width-35">
						${zmsChannelSchedule.programName}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">播放日期</label></td>
					<td class="width-35">
						${zmsChannelSchedule.startDate}
					</td>
					<td class="width-15 active"><label class="pull-right">开始时间</label></td>
					<td class="width-35">
						${zmsChannelSchedule.startTime}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">时长（精确到秒）</label></td>
					<td class="width-35">
						${zmsChannelSchedule.duration}
					</td>
					<td class="width-15 active"><label class="pull-right">描述</label></td>
					<td class="width-35">
						${zmsChannelSchedule.description}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">订阅人数</label></td>
					<td class="width-35">
						${zmsChannelSchedule.orderNum}
					</td>
					<td class="width-15 active"><label class="pull-right">评论数</label></td>
					<td class="width-35">
						${zmsChannelSchedule.replay}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">状态</label></td>
					<td class="width-35">
						${fns:getDictLabel(zmsChannelSchedule.status, 'general_status', '')}
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
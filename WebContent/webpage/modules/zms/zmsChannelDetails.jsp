<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>频道信息管理管理</title>
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
		<form:form id="inputForm" modelAttribute="zmsChannel" action="${ctx}/zms/zmsChannel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		
				<tr>
					<td class="width-15 active"><label class="pull-right">隶属分类</label></td>
					<td class="width-35">
						${zmsChannel.categoryId}
					</td>
					<td class="width-15 active"><label class="pull-right">内容标识</label></td>
					<td class="width-35">
						${zmsChannel.contentId}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">名称</label></td>
					<td class="width-35">
						${zmsChannel.name}
					</td>
					<td class="width-15 active"><label class="pull-right">频道号</label></td>
					<td class="width-35">
						${zmsChannel.channelNumber}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">频道类别</label></td>
					<td class="width-35">
						${fns:getDictLabel(zmsChannel.type, 'zms_channel_type', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">子类别</label></td>
					<td class="width-35">
						${fns:getDictLabel(zmsChannel.subtype, 'zms_channel_subtype', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">台标名称</label></td>
					<td class="width-35">
						${zmsChannel.callSign}
					</td>
					<td class="width-15 active"><label class="pull-right">时移标志</label></td>
					<td class="width-35">
						${zmsChannel.timeShift}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">开始时间</label></td>
					<td class="width-35">
						${zmsChannel.startTime}
					</td>
					<td class="width-15 active"><label class="pull-right">edntime</label></td>
					<td class="width-35">
						${zmsChannel.endTime}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">标识code</label></td>
					<td class="width-35">
						${zmsChannel.channelCode}
					</td>
					<td class="width-15 active"><label class="pull-right">存储时长</label></td>
					<td class="width-35">
						${zmsChannel.storageDuration}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">默认时移标志</label></td>
					<td class="width-35">
						${fns:getDictLabel(zmsChannel.timeShiftDuration, 'zms_channel_time_shift', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">描述</label></td>
					<td class="width-35">
						${zmsChannel.description}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">国家</label></td>
					<td class="width-35">
						${zmsChannel.country}
					</td>
					<td class="width-15 active"><label class="pull-right">省</label></td>
					<td class="width-35">
						${zmsChannel.province}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">城市</label></td>
					<td class="width-35">
						${zmsChannel.city}
					</td>
					<td class="width-15 active"><label class="pull-right">邮编</label></td>
					<td class="width-35">
						${zmsChannel.zipCode}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">语言</label></td>
					<td class="width-35">
						${zmsChannel.language}
					</td>
					<td class="width-15 active"><label class="pull-right">拷贝保护标志</label></td>
					<td class="width-35">
						${fns:getDictLabel(zmsChannel.macroVision, 'general_macro_vision', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">码流标志</label></td>
					<td class="width-35">
						${zmsChannel.streamType}
					</td>
					<td class="width-15 active"><label class="pull-right">视频编码格式</label></td>
					<td class="width-35">
						${fns:getDictLabel(zmsChannel.videoType, 'general_video_type', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">音频编码格式</label></td>
					<td class="width-35">
						${fns:getDictLabel(zmsChannel.audioType, 'general_audio_type', '')}
					</td>
					<td class="width-15 active"><label class="pull-right">双语标志</label></td>
					<td class="width-35">
						${zmsChannel.bilingual}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">频道地址</label></td>
					<td class="width-35">
						${zmsChannel.channelUrl}
					</td>
					<td class="width-15 active"><label class="pull-right">台标大图</label></td>
					<td class="width-35">
						${zmsChannel.bigPicurl}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">台标小图</label></td>
					<td class="width-35">
						${zmsChannel.picurl}
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>
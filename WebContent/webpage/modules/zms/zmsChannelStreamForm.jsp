<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>直播节目流信息管理</title>
    <meta name="decorator" content="default"/>

    <script type="text/javascript">
        var validateForm;

        function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if (validateForm.form()) {
                $("#inputForm").submit();
                return true;
            }

            return false;
        }

        $(document).ready(function () {
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
<%--@elvariable id="zmsChannelStream" type="com.msframe.modules.zms.entity.ZmsChannelStream"--%>
<form:form id="inputForm" modelAttribute="zmsChannelStream" action="${ctx}/zms/zmsChannelStream/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="channelId.id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">内容标识：</label></td>
            <td class="width-35">
                <form:input path="contentId" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">标识code：</label></td>
            <td class="width-35">
                <form:input path="channelCode" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">码流：</label></td>
            <td class="width-35">
                <form:select path="bitratecode" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('zms_channel_stream_bitratecode')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">直播ip地址：</label></td>
            <td class="width-35">
                <form:input path="multicastIp" htmlEscape="false" maxlength="20" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">直播端口号：</label></td>
            <td class="width-35">
                <form:input path="multicastPort" htmlEscape="false" maxlength="20" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">频道标志：</label></td>
            <td class="width-35">
                <form:input path="channelContentId" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">视频编码格式：</label></td>
            <td class="width-35">
                <form:select path="videoType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_video_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">音频编码格式：</label></td>
            <td class="width-35">
                <form:select path="audioType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_audio_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">分辨率：</label></td>
            <td class="width-35">
                <form:select path="resolution" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('zms_channel_stream_resolution')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">封装格式：</label></td>
            <td class="width-35">
                <form:select path="systemLayer" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('zms_channel_stream_system_layer')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">具体播放地址：</label></td>
            <td class="width-35">
                <form:input path="playUrl" htmlEscape="false" maxlength="512" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                <form:select path="status" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
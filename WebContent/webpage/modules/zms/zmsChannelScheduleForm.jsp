<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>直播节目单列表管理</title>
    <meta name="decorator" content="default"/>
    <!-- 引入laydate5.0.9版本 -->
    <script type="text/javascript" src="${ctxStatic}/laydate-5.0.9/laydate.js"></script>
    <%--<link rel="stylesheet" href="${ctxStatic}/laydate-5.0.9/theme/default/laydate.css">--%>
    <script type="text/javascript">

        /** 初始化时间选择控件 */
        // 开始时间
        laydate.render({
            elem: '#startTime',
            type: 'time'
        });

        // 播放日期
        laydate.render({
            elem: '#startDate'
        });

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

    <style>
        .laydate-icon {
            background-image: none;
        }

        .layui-icon {
            border: none;
        }

        .layer-date {
            max-width: none;
        }

        .date-input-border {
            border: 1px solid #e5e6e7;
        }
    </style>
</head>
<body>
<%--@elvariable id="zmsChannelSchedule" type="com.msframe.modules.zms.entity.ZmsChannelSchedule"--%>
<form:form id="inputForm" modelAttribute="zmsChannelSchedule" action="${ctx}/zms/zmsChannelSchedule/save" method="post"
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
            <td class="width-15 active"><label class="pull-right">直播单编号：</label></td>
            <td class="width-35">
                <form:input path="code" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">标识code：</label></td>
            <td class="width-35">
                <form:input path="channelCode" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">搜索名称：</label></td>
            <td class="width-35">
                <form:input path="searchName" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">节目名称：</label></td>
            <td class="width-35">
                <form:input path="programName" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">播放日期：</label></td>
            <td class="width-35">
                <input id="startDate" name="startDate" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date date-input-border" autocomplete="off"
                       value="${zmsChannelSchedule.startDate}"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">开始时间：</label></td>
            <td class="width-35">
                <input id="startTime" name="startTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date date-input-border" autocomplete="off"
                       value="${zmsChannelSchedule.startTime}"/>
            </td>
            <td class="width-15 active"><label class="pull-right">时长（精确到秒）：</label></td>
            <td class="width-35">
                <form:input path="duration" htmlEscape="false" maxlength="16" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">订阅人数：</label></td>
            <td class="width-35">
                <form:input path="orderNum" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">评论数：</label></td>
            <td class="width-35">
                <form:input path="replay" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                <form:input path="description" htmlEscape="false" maxlength="256" class="form-control "/>
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户绑定iptv信息管理</title>
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

            laydate({
                elem: '#bindtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="userBindLogs" type="com.msframe.modules.user.entity.UserBindLogs"--%>
<form:form id="inputForm" modelAttribute="userBindLogs" action="${ctx}/user/userBindLogs/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="user.id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">设备编号：</label></td>
            <td class="width-35">
                <form:input path="stbid" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">二维码：</label></td>
            <td class="width-35">
                <form:input path="iptvcode" htmlEscape="false" maxlength="255" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">绑定结果：</label></td>
            <td class="width-35">
                <input id="bindtime" name="bindtime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date "
                       value="<fmt:formatDate value="${userBindLogs.bindtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
            <td class="width-15 active"><label class="pull-right">接口返回结果：</label></td>
            <td class="width-35">
                <form:input path="result" htmlEscape="false" maxlength="50" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                <form:select path="status" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                <form:input path="sort" htmlEscape="false" maxlength="64" class="form-control  digits"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
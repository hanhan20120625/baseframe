<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户登录日志管理</title>
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
                elem: '#lastLoginTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="userLoginLogs" type="com.msframe.modules.user.entity.UserLoginLogs"--%>
<form:form id="inputForm" modelAttribute="userLoginLogs" action="${ctx}/user/userLoginLogs/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">用户编号：</label></td>
            <td class="width-35">
                <sys:treeselect id="user" name="user.id" value="${userLoginLogs.user.id}" labelName="user.nickname"
                                labelValue="${userLoginLogs.user.nickname}"
                                title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true"
                                notAllowSelectParent="true"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>最后登陆时间：</label></td>
            <td class="width-35">
                <input id="lastLoginTime" name="lastLoginTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date required"
                       value="<fmt:formatDate value="${userLoginLogs.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">登录IP：</label></td>
            <td class="width-35">
                <form:input path="loginIp" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">登录类型|：</label></td>
            <td class="width-35">
                <form:input path="loginType" htmlEscape="false" maxlength="20" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">第三方平台标识|：</label></td>
            <td class="width-35">
                <form:input path="openid" htmlEscape="false" maxlength="64" class="form-control "/>
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
        <tr>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                <form:input path="sort" htmlEscape="false" maxlength="64" class="form-control  digits"/>
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
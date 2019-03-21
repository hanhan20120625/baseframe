<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>客户信息管理</title>
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
<%--@elvariable id="user" type="com.msframe.modules.user.entity.UserInfo"--%>
<form:form id="inputForm" modelAttribute="user" action="${ctx}/user/user/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="sort"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>用户名：</label></td>
            <td class="width-35">
                <form:input path="loginName" htmlEscape="false" maxlength="64" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>密码：</label></td>
            <td class="width-35">
                <form:input path="password" htmlEscape="false" maxlength="64" class="form-control required"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">真实名称：</label></td>
            <td class="width-35">
                <form:input path="realName" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">昵称：</label></td>
            <td class="width-35">
                <form:input path="nickname" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">证件类型：</label></td>
            <td class="width-35">
                <form:select path="idType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('user_id_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">证件号：</label></td>
            <td class="width-35">
                <form:input path="idNumber" htmlEscape="false" maxlength="50" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">性别：</label></td>
            <td class="width-35">
                <form:select path="sex" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_sex')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">手机号：</label></td>
            <td class="width-35">
                <form:input path="mobile" htmlEscape="false" maxlength="20" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">地址：</label></td>
            <td class="width-35">
                <form:input path="address" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">联系电话：</label></td>
            <td class="width-35">
                <form:input path="telephone" htmlEscape="false" maxlength="40" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">电子邮件：</label></td>
            <td class="width-35">
                <form:input path="email" htmlEscape="false" maxlength="255" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">传真：</label></td>
            <td class="width-35">
                <form:input path="fax" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">头像地址：</label></td>
            <td class="width-35">
                <form:input path="headpic" htmlEscape="false" maxlength="255" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">是否是vip会员：</label></td>
            <td class="width-35">
                <form:select path="isvip" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('user_isvip')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">vip开始时间：</label></td>
            <td class="width-35">
                <input id="vipStartTime" name="vipStartTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date"
                       value="<fmt:formatDate value="${user.vipStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
            <td class="width-15 active"><label class="pull-right">vip结束时间：</label></td>
            <td class="width-35">
                <input id="vipEndTime" name="vipEndTime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date"
                       value="<fmt:formatDate value="${user.vipEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">积分数量：</label></td>
            <td class="width-35">
                <form:input path="score" htmlEscape="false" maxlength="10" class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">付费方式</label></td>
            <td class="width-35">
                <form:select path="payType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('user_pay_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">imsi：</label></td>
            <td class="width-35">
                <form:input path="imsi" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">mac地址：</label></td>
            <td class="width-35">
                <form:input path="mac" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
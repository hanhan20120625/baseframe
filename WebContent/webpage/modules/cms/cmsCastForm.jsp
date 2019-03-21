<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>演员信息管理</title>
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
                elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="cmsCast" type="com.msframe.modules.cms.entity.CmsCast"--%>
<form:form id="inputForm" modelAttribute="cmsCast" action="${ctx}/cms/cmsCast/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">姓名：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="64" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right">CP演员标识：</label></td>
            <td class="width-35">
                <form:input path="cpCastid" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">显示名称：</label></td>
            <td class="width-35">
                <form:input path="personDisplayName" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">排序名：</label></td>
            <td class="width-35">
                <form:input path="personSortName" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">搜索名：</label></td>
            <td class="width-35">
                <form:input path="personSearchName" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">姓：</label></td>
            <td class="width-35">
                <form:input path="firstname" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">中间名：</label></td>
            <td class="width-35">
                <form:input path="middlename" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">名：</label></td>
            <td class="width-35">
                <form:input path="lastname" htmlEscape="false" maxlength="64" class="form-control "/>
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
            <td class="width-15 active"><label class="pull-right">生日：</label></td>
            <td class="width-35">
                <input id="birthday" name="birthday" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date "
                       value="<fmt:formatDate value="${cmsCast.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">家乡：</label></td>
            <td class="width-35">
                <form:input path="hometown" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">教育：</label></td>
            <td class="width-35">
                <form:input path="education" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">身高：</label></td>
            <td class="width-35">
                <form:input path="height" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">体重：</label></td>
            <td class="width-35">
                <form:input path="weight" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">血型：</label></td>
            <td class="width-35">
                <form:input path="bloodGroup" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">婚姻：</label></td>
            <td class="width-35">
                <form:select path="marriage" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_cast_marriage')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">爱好：</label></td>
            <td class="width-35">
                <form:input path="favorite" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">个人主页：</label></td>
            <td class="width-35">
                <form:input path="webpage" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                <form:input path="description" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">CMS状态：</label></td>
            <td class="width-35">
                <form:input path="cmsState" htmlEscape="false" maxlength="11" class="form-control  digits"/>
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
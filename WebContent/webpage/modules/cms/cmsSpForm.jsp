<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>基本信息管理</title>
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
                elem: '#compactStarttime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#compactEndtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="cmsSp" type="com.msframe.modules.cms.entity.CmsSp"--%>
<form:form id="inputForm" modelAttribute="cmsSp" action="${ctx}/cms/cmsSp/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="sort"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="30" class="form-control required"/>
            </td>
            <td class="width-15 active"><label class="pull-right">联系人：</label></td>
            <td class="width-35">
                <form:input path="linkMan" htmlEscape="false" maxlength="30" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">手机号：</label></td>
            <td class="width-35">
                <form:input path="mobile" htmlEscape="false" maxlength="20" class="form-control "/>
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
            <td class="width-15 active"><label class="pull-right">标识代码：</label></td>
            <td class="width-35">
                <form:input path="code" htmlEscape="false" maxlength="20" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">密匙：</label></td>
            <td class="width-35">
                <form:input path="secretKey" htmlEscape="false" maxlength="50" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">合同编号：</label></td>
            <td class="width-35">
                <form:input path="compactNumber" htmlEscape="false" maxlength="50" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">合同开始时间：</label></td>
            <td class="width-35">
                <input id="compactStarttime" name="compactStarttime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date required" readonly="readonly"
                       value="<fmt:formatDate value="${cmsSp.compactStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
            <td class="width-15 active"><label class="pull-right">合同结束时间：</label></td>
            <td class="width-35">
                <input id="compactEndtime" name="compactEndtime" type="text" maxlength="20"
                       class="laydate-icon form-control layer-date required" readonly="readonly"
                       value="<fmt:formatDate value="${cmsSp.compactEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">是否提供下载：</label></td>
            <td class="width-35">
                <form:select path="movieDowned" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_sp_movie_downed')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">是否提供高清流：</label></td>
            <td class="width-35">
                <form:select path="supplyStream" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_sp_supply_stream')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">公司全称：</label></td>
            <td class="width-35">
                <form:input path="companyName" htmlEscape="false" maxlength="150" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">电信业务经营许可证编号：</label></td>
            <td class="width-35">
                <form:input path="telCode" htmlEscape="false" maxlength="150" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">CP类型：</label></td>
            <td class="width-35">
                <form:select path="ctype" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_sp_ctype')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">地址：</label></td>
            <td class="width-35">
                <form:input path="address" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                <form:select path="status" class="form-control  ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>

            </td>
            <td class="width-15 active"><label class="pull-right">简介：</label></td>
            <td class="width-35" colspan="3">
                <form:textarea path="intro" htmlEscape="false" rows="10" class="form-control"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
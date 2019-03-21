<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频内容地区管理</title>
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
<%--@elvariable id="cmsRegion" type="com.msframe.modules.cms.entity.CmsRegion"--%>
<form:form id="inputForm" modelAttribute="cmsRegion" action="${ctx}/cms/cmsRegion/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="sort"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">地区/国家名称：</label></td>
            <td class="width-85">
                <form:input path="name" htmlEscape="false" maxlength="128" class="form-control required"/>
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
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-85">
                <form:textarea path="description" htmlEscape="false" rows="10" class="form-control"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频演员管理</title>
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
<%--@elvariable id="cmsProgramCast" type="com.msframe.modules.cms.entity.CmsProgramCast"--%>
<form:form id="inputForm" modelAttribute="cmsProgramCast" action="${ctx}/cms/cmsProgramCast/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="programId.id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">演员：</label></td>
            <td class="width-35">
                <sys:gridselect id="castId" name="castId.id" value="${cmsProgramCast.castId.id}" labelName="castId.name"
                                labelValue="${cmsProgramCast.castId.name}" fieldLabels="姓名" fieldKeys="name"
                                searchLabel="姓名" searchKey="name" title="选择演员" url="${ctx}/cms/cmsCast/selectCmsCast"
                                cssClass="form-control required" linkage="false"/>
            </td>
            <td class="width-15 active"><label class="pull-right">类型：</label></td>
            <td class="width-35">
                <form:select path="castType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('cms_program_cast_cast_type')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
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
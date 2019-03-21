<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>影片收藏信息管理</title>
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
<%--@elvariable id="userFavorite" type="com.msframe.modules.user.entity.UserFavorite"--%>
<form:form id="inputForm" modelAttribute="userFavorite" action="${ctx}/user/userFavorite/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="user.id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">影片名称：</label></td>
            <td class="width-35">
                <sys:gridselect id="programId" name="programId.id" value="${userFavorite.programId.id}"
                                labelName="programId.name" labelValue="${userFavorite.programId.name}"
                                fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name"
                                title="选择影片" url="${ctx}/user/userFavorite/selectProgramId"
                                cssClass="form-control" linkage="false"/>
            </td>
            <td class="width-15 active"><label class="pull-right">视频名称：</label></td>
            <td class="width-35">
                <sys:gridselect id="moviceId" name="moviceId.id" value="${userFavorite.moviceId.id}"
                                labelName="moviceId.name" labelValue="${userFavorite.moviceId.name}"
                                fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name"
                                title="选择影片" url="${ctx}/user/userFavorite/selectMovieId"
                                cssClass="form-control" linkage="false"/>
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
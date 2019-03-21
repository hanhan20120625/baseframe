<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>频道推荐管理</title>
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
<%--@elvariable id="zmsChannelRecommed" type="com.msframe.modules.zms.entity.ZmsChannelRecommed"--%>
<form:form id="inputForm" modelAttribute="zmsChannelRecommed" action="${ctx}/zms/zmsChannelRecommed/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">频道编号：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/zms/zmsChannelRecommed/selectchannelId" id="channelId" name="channelId.id"
                                value="${zmsChannelRecommed.channelId.id}" title="选择频道编号" labelName="channelId.name"
                                labelValue="${zmsChannelRecommed.channelId.name}" cssClass="form-control required"
                                fieldLabels="频道" fieldKeys="name" searchLabel="频道" searchKey="name" linkage="false"></sys:gridselect>
            </td>
            <td class="width-15 active"><label class="pull-right">隶属分类：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/zms/zmsChannelRecommed/selectcategoryId" id="categoryId"
                                name="categoryId.id" value="${zmsChannelRecommed.categoryId.id}" title="选择隶属分类"
                                labelName="categoryId.name"
                                labelValue="${zmsChannelRecommed.categoryId.name}" cssClass="form-control"
                                fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name" linkage="false"></sys:gridselect>
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
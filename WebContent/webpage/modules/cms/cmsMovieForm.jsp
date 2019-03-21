<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频管理</title>
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
<%--@elvariable id="cmsMovie" type="com.msframe.modules.cms.entity.CmsMovie"--%>
<form:form id="inputForm" modelAttribute="cmsMovie" action="${ctx}/cms/cmsMovie/save" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <form:hidden path="isFromProgram"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">影片编号：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/cms/cmsProgram/selectCmsProgram" id="programId" name="programId.id"
                                value="${cmsMovie.programId.id}"
                                title="选择影片编号" labelName="programId.name"
                                labelValue="${cmsMovie.programId.name}" cssClass="form-control required"
                                fieldLabels="名称" fieldKeys="name" searchLabel="视频项目名称"
                                searchKey="name" linkage="false"></sys:gridselect>
            </td>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">CP内容标识：</label></td>
            <td class="width-35">
                <form:input path="cpContentid" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">第几集：</label></td>
            <td class="width-35">
                <form:input path="episode" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">内容标识：</label></td>
            <td class="width-35">
                <form:input path="contentId" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">图片地址：</label></td>
            <td class="width-35">
                <c:if test="${cmsMovie.picurl ne '' and cmsMovie.picurl ne null}">
                    <img src="${crc}${cmsMovie.picurl}"/>
                </c:if>
                <input type="hidden" name="picurl" value="${cmsMovie.picurl}"/>
                <input type="file" name="files" class="form-control"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                <form:input path="sort" htmlEscape="false" maxlength="64" cssClass="form-control"/>
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
            <td class="width-15 active"><label class="pull-right">简介：</label></td>
            <td class="width-35" colspan="3">
                <form:textarea path="intro" htmlEscape="false" rows="8" class="form-control"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
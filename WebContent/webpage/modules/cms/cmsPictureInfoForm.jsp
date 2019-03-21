<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>图片信息管理</title>
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
<%--@elvariable id="cmsPictureInfo" type="com.msframe.modules.cms.entity.CmsPictureInfo"--%>
<form:form id="inputForm" modelAttribute="cmsPictureInfo" action="${ctx}/cms/cmsPictureInfo/save" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <form:hidden path="programId.id"/>
    <form:hidden path="movieId.id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">图片格式：</label></td>
            <td class="width-35">
                <form:input path="picConfigId" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">图片路径：</label></td>
            <td class="width-35">
                <c:if test="${cmsPictureInfo.picUrl ne '' and cmsPictureInfo.picUrl ne null}">
                    <img src="${crc}${cmsPictureInfo.picUrl}" class="program-img-style"/>
                </c:if>
                <input type="hidden" name="picUrl" value="${cmsPictureInfo.picUrl}"/>
                <div class="upload-box">
                    <div class="file-input">
                        <i class="fa fa-upload upload-icon"></i>
                        <span>选择文件</span>
                        <input type="file" name="files" class="form-control "/>
                    </div>
                    <span class="showFileName1"></span>
                </div>
            </td>
            <td class="width-15 active"><label class="pull-right">图片类型：</label></td>
            <td class="width-35">
                <form:input path="format" htmlEscape="false" maxlength="4" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                <form:input path="description" htmlEscape="false" maxlength="256" class="form-control "/>
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
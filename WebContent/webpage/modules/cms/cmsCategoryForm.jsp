<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>栏目管理</title>
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
            $("#name").focus();
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
<%--@elvariable id="cmsCategory" type="com.msframe.modules.cms.entity.CmsCategory"--%>
<form:form id="inputForm" modelAttribute="cmsCategory" enctype="multipart/form-data"
           action="${ctx}/cms/cmsCategory/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">上级父级编号:</label></td>
            <td class="width-35">
                <sys:treeselect id="parent" name="parent.id" value="${cmsCategory.parent.id}" labelName="parent.name"
                                labelValue="${cmsCategory.parent.name}"
                                title="父级编号" url="/cms/cmsCategory/treeData" extId="${cmsCategory.id}"
                                cssClass="form-control " allowClear="true"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">栏目名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="128" class="form-control required"/>

            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                <form:input path="description" htmlEscape="false" maxlength="256" class="form-control  "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">图标：</label></td>
            <td class="width-35">
                <c:if test="${cmsCategory.icon ne '' and cmsCategory.icon ne null}">
                    <img src="${crc}${cmsCategory.icon}" class="category-img-style"/>
                </c:if>
                <input type="hidden" name="icon" value="${cmsCategory.icon}"/>
                <div class="upload-box">
                    <div class="file-input">
                        <i class="fa fa-upload upload-icon"></i>
                        <span>选择文件</span>
                        <input type="file" name="files" class="form-control"/>
                    </div>
                    <span class="showFileName1"></span>
                </div>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">海报：</label></td>
            <td class="width-35">
                <c:if test="${cmsCategory.poster ne '' and cmsCategory.poster ne null}">
                    <img src="${crc}${cmsCategory.poster}" class="category-img-style"/>
                </c:if>
                <input type="hidden" name="poster" value="${cmsCategory.poster}"/>
                <div class="upload-box">
                    <div class="file-input">
                        <i class="fa fa-upload upload-icon"></i>
                        <span>选择文件</span>
                        <input type="file" name="files" class="form-control"/>
                    </div>
                    <span class="showFileName1"></span>
                </div>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">背景图：</label></td>
            <td class="width-35">
                <c:if test="${cmsCategory.bgImage ne '' and cmsCategory.bgImage ne null}">
                    <img src="${crc}${cmsCategory.bgImage}" class="category-img-style"/>
                </c:if>
                <input type="hidden" name="bgImage" value="${cmsCategory.bgImage}"/>
                <div class="upload-box">
                    <div class="file-input">
                        <i class="fa fa-upload upload-icon"></i>
                        <span>选择文件</span>
                        <input type="file" name="files" class="form-control"/>
                    </div>
                    <span class="showFileName1"></span>
                </div>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">同步状态：</label></td>
            <td class="width-35">
                <form:select path="syncState" class="form-control  ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_sync_state')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
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
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                <form:input path="sort" htmlEscape="false" maxlength="64" class="form-control   digits"/>

            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
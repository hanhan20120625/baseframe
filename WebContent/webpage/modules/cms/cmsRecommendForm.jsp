<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>影片推荐管理</title>
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
<%--@elvariable id="cmsRecommend" type="com.msframe.modules.cms.entity.CmsRecommend"--%>
<form:form id="inputForm" modelAttribute="cmsRecommend" action="${ctx}/cms/cmsRecommend/save" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">隶属分类：</label></td>
            <td class="width-35">
                <sys:gridselect id="categoryId" name="categoryId.id" value="${cmsRecommend.categoryId.id}"
                                labelName="categoryId.name" labelValue="${cmsRecommend.categoryId.name}"
                                fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name" title="选择所属栏目"
                                url="${ctx}/cms/cmsRecommend/selectCategoryId" cssClass="form-control" linkage="false"/>
            </td>
            <td class="width-15 active"><label class="pull-right">影片名称：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/cms/cmsRecommend/selectprogramId" id="programId" name="programId.id"
                                value="${cmsRecommend.programId.id}" title="选择影片名称" labelName="programId.name"
                                labelValue="${cmsRecommend.programId.name}" cssClass="form-control required"
                                fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name" linkage="false"></sys:gridselect>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">影片类型：</label></td>
            <td class="width-35">
                <sys:gridselect url="${ctx}/cms/cmsRecommend/selectTypeId" id="typeId" name="typeId.id"
                                value="${cmsRecommend.typeId.id}" title="选择影片名称" labelName="typeId.name"
                                labelValue="${cmsRecommend.typeId.name}" cssClass="form-control required"
                                fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name"
                                linkage="true" linkageId="categoryId" prompt="请选择分类"></sys:gridselect>
            </td>
            <td class="width-15 active"><label class="pull-right">是否首页：</label></td>
            <td class="width-35">
                <form:select path="isindex" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_is_index')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="128" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">副标题：</label></td>
            <td class="width-35">
                <form:input path="subhead" htmlEscape="false" maxlength="255" class="form-control "/>
            </td>

        </tr>
        <tr>

            <td class="width-15 active"><label class="pull-right">图片地址：</label></td>
            <td class="width-35">
                <c:if test="${cmsRecommend.picurl ne '' and cmsRecommend.picurl ne null}">
                    <img src="${crc}${cmsRecommend.picurl}" alt="">
                </c:if>
                <input type="hidden" name="picurl" value="${cmsRecommend.picurl}"/>
                <input type="file" name="files" class="form-control"/>
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
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35" colspan="3">
                <form:textarea path="description" htmlEscape="false" rows="6" class="form-control"/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
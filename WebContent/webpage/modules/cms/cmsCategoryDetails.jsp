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
<form:form id="inputForm" modelAttribute="cmsCategory" action="${ctx}/cms/cmsCategory/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right">栏目名称：</label></td>
            <td class="width-35">
                    ${cmsCategory.name}

            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                    ${cmsCategory.description}

            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">图标：</label></td>
            <td class="width-35">
                <img src="${crc}${cmsCategory.icon}" class="category-img-style"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">海报：</label></td>
            <td class="width-35">
                    ${cmsCategory.poster}

            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">背景图：</label></td>
            <td class="width-35">
                    ${cmsCategory.bgImage}

            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">同步状态：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsCategory.syncState,'general_sync_state','')}

            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsCategory.status, 'general_status', '')}

            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                    ${cmsCategory.sort}

            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
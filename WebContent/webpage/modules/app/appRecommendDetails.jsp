<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>banner推荐管理</title>
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
<%--@elvariable id="appRecommend" type="com.msframe.modules.app.entity.AppRecommend"--%>
<form:form id="inputForm" modelAttribute="appRecommend" action="${ctx}/app/appRecommend/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">隶属分类</label></td>
            <td class="width-35">
                    ${appRecommend.categoryId.name}
            </td>
            <td class="width-15 active"><label class="pull-right">是否首页</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(appRecommend.isindex, 'general_is_index', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">名称</label></td>
            <td class="width-35">
                    ${appRecommend.name}
            </td>
            <td class="width-15 active"><label class="pull-right">图片地址</label></td>
            <td class="width-35">
                <c:if test="${appRecommend.picurl ne '' and appRecommend.picurl ne null}">
                    <img src="${crc}${appRecommend.picurl}" alt="">
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">影片编号</label></td>
            <td class="width-35">
                    ${appRecommend.programId.name}
            </td>
            <td class="width-15 active"><label class="pull-right">业务类型</label></td>
            <td class="width-35">
                    ${appRecommend.bizType}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">链接地址</label></td>
            <td class="width-35">
                    ${appRecommend.linkurl}
            </td>
            <td class="width-15 active"><label class="pull-right">状态</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(appRecommend.status, 'general_status', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序</label></td>
            <td class="width-35">
                    ${appRecommend.sort}
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
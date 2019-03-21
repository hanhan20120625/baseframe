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
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">隶属分类</label></td>
            <td class="width-35">
                    ${cmsRecommend.categoryId.name}
            </td>
            <td class="width-15 active"><label class="pull-right">影片编号</label></td>
            <td class="width-35">
                    ${cmsRecommend.programId.name}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">是否首页</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsRecommend.isindex, 'general_is_index', '')}
            </td>
            <td class="width-15 active"><label class="pull-right">图片地址</label></td>
            <td class="width-35">
                <c:if test="${cmsRecommend.picurl ne '' and cmsRecommend.picurl ne null}">
                    <img src="${crc}${cmsRecommend.picurl}" alt="">
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">名称</label></td>
            <td class="width-35">
                    ${cmsRecommend.name}
            </td>
            <td class="width-15 active"><label class="pull-right">副标题</label></td>
            <td class="width-35">
                    ${cmsRecommend.subhead}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">状态</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsRecommend.status,'general_status','')}
            </td>
            <td class="width-15 active"><label class="pull-right">排序</label></td>
            <td class="width-35">
                    ${cmsRecommend.sort}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述</label></td>
            <td class="width-35" colspan="3">
                    ${cmsRecommend.description}
            </td>
        </tr>

        </tbody>
    </table>
</form:form>
</body>
</html>
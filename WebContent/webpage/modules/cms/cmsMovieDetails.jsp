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
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">影片编号</label></td>
            <td class="width-35">
                    ${cmsMovie.programId.name}
            </td>
            <td class="width-15 active"><label class="pull-right">名称</label></td>
            <td class="width-35">
                    ${cmsMovie.name}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">cp内容标识</label></td>
            <td class="width-35">
                    ${cmsMovie.cpContentid}
            </td>
            <td class="width-15 active"><label class="pull-right">第几集</label></td>
            <td class="width-35">
                    ${cmsMovie.episode}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">内容标识</label></td>
            <td class="width-35">
                    ${cmsMovie.contentId}
            </td>
            <td class="width-15 active"><label class="pull-right">简介</label></td>
            <td class="width-35">
                    ${cmsMovie.intro}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">图片地址</label></td>
            <td class="width-35">
                    ${cmsMovie.picurl}
            </td>
            <td class="width-15 active"><label class="pull-right">状态</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsMovie.status, 'general_status', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序</label></td>
            <td class="width-35">
                    ${cmsMovie.sort}
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
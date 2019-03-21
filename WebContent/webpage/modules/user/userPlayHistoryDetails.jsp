<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户播放记录管理</title>
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
<%--@elvariable id="userPlayHistory" type="com.msframe.modules.user.entity.UserPlayHistory"--%>
<form:form id="inputForm" modelAttribute="userPlayHistory" action="${ctx}/user/userPlayHistory/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">用户编号</label></td>
            <td class="width-35">
                    ${userPlayHistory.user.nickname}
            </td>
            <td class="width-15 active"><label class="pull-right">影片编号</label></td>
            <td class="width-35">
                    ${userPlayHistory.programId.name}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">视频编号</label></td>
            <td class="width-35">
                    ${userPlayHistory.moviceId.name}
            </td>
            <td class="width-15 active"><label class="pull-right">播放流编号</label></td>
            <td class="width-35">
                    ${userPlayHistory.movicestreamId.resolutionId}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">播放时长(秒)</label></td>
            <td class="width-35">
                    ${userPlayHistory.length}
            </td>
            <td class="width-15 active"><label class="pull-right">状态</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(userPlayHistory.status, 'general_status', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序</label></td>
            <td class="width-35">
                    ${userPlayHistory.sort}
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
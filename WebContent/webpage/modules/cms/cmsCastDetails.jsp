<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>演员信息管理</title>
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

            laydate({
                elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="cmsCast" type="com.msframe.modules.cms.entity.CmsCast"--%>
<form:form id="inputForm" modelAttribute="cmsCast" action="${ctx}/cms/cmsCast/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">姓名</label></td>
            <td class="width-35">
                    ${cmsCast.name}
            </td>
            <td class="width-15 active"><label class="pull-right">CP演员标识</label></td>
            <td class="width-35">
                    ${cmsCast.cpCastid}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">显示名称</label></td>
            <td class="width-35">
                    ${cmsCast.personDisplayName}
            </td>
            <td class="width-15 active"><label class="pull-right">排序名</label></td>
            <td class="width-35">
                    ${cmsCast.personSortName}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">搜索名</label></td>
            <td class="width-35">
                    ${cmsCast.personSearchName}
            </td>
            <td class="width-15 active"><label class="pull-right">姓</label></td>
            <td class="width-35">
                    ${cmsCast.firstname}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">中间名</label></td>
            <td class="width-35">
                    ${cmsCast.middlename}
            </td>
            <td class="width-15 active"><label class="pull-right">名</label></td>
            <td class="width-35">
                    ${cmsCast.lastname}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">性别</label></td>
            <td class="width-35">
                    ${fns:getDictLabels(cmsCast.sex,'general_sex','')}
            </td>
            <td class="width-15 active"><label class="pull-right">生日</label></td>
            <td class="width-35">
                <fmt:formatDate value="${cmsCast.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">家乡</label></td>
            <td class="width-35">
                    ${cmsCast.hometown}
            </td>
            <td class="width-15 active"><label class="pull-right">教育</label></td>
            <td class="width-35">
                    ${cmsCast.education}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">身高</label></td>
            <td class="width-35">
                    ${cmsCast.height}
            </td>
            <td class="width-15 active"><label class="pull-right">体重</label></td>
            <td class="width-35">
                    ${cmsCast.weight}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">血型</label></td>
            <td class="width-35">
                    ${cmsCast.bloodGroup}
            </td>
            <td class="width-15 active"><label class="pull-right">婚姻</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsCast.marriage, 'cms_cast_marriage', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">爱好</label></td>
            <td class="width-35">
                    ${cmsCast.favorite}
            </td>
            <td class="width-15 active"><label class="pull-right">个人主页</label></td>
            <td class="width-35">
                    ${cmsCast.webpage}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">描述</label></td>
            <td class="width-35">
                    ${cmsCast.description}
            </td>
            <td class="width-15 active"><label class="pull-right">CMS状态</label></td>
            <td class="width-35">
                    ${cmsCast.cmsState}
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
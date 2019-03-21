<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>基本信息管理</title>
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
                elem: '#compactStarttime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#compactEndtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="cmsSp" type="com.msframe.modules.cms.entity.CmsSp"--%>
<form:form id="inputForm" modelAttribute="cmsSp" action="${ctx}/cms/cmsSp/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">名称</label></td>
            <td class="width-35">
                    ${cmsSp.name}
            </td>
            <td class="width-15 active"><label class="pull-right">联系人</label></td>
            <td class="width-35">
                    ${cmsSp.linkMan}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">手机号</label></td>
            <td class="width-35">
                    ${cmsSp.mobile}
            </td>
            <td class="width-15 active"><label class="pull-right">联系电话</label></td>
            <td class="width-35">
                    ${cmsSp.telephone}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">电子邮件</label></td>
            <td class="width-35">
                    ${cmsSp.email}
            </td>
            <td class="width-15 active"><label class="pull-right">标识代码</label></td>
            <td class="width-35">
                    ${cmsSp.code}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">密匙</label></td>
            <td class="width-35">
                    ${cmsSp.secretKey}
            </td>
            <td class="width-15 active"><label class="pull-right">合同编号</label></td>
            <td class="width-35">
                    ${cmsSp.compactNumber}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">合同开始时间</label></td>
            <td class="width-35">
                <fmt:formatDate value="${cmsSp.compactStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td class="width-15 active"><label class="pull-right">合同结束时间</label></td>
            <td class="width-35">
                <fmt:formatDate value="${cmsSp.compactEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">是否提供下载</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsSp.movieDowned, 'cms_sp_movie_downed', '')}
            </td>
            <td class="width-15 active"><label class="pull-right">是否提供高清流</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsSp.supplyStream, 'cms_sp_supply_stream', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">公司全称</label></td>
            <td class="width-35">
                    ${cmsSp.companyName}
            </td>
            <td class="width-15 active"><label class="pull-right">电信业务经营许可证编号</label></td>
            <td class="width-35">
                    ${cmsSp.telCode}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">CP类型</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsSp.ctype, 'cms_sp_ctype', '')}
            </td>
            <td class="width-15 active"><label class="pull-right">地址</label></td>
            <td class="width-35">
                    ${cmsSp.address}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">简介</label></td>
            <td class="width-35" colspan="3">
                    ${cmsSp.intro}
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
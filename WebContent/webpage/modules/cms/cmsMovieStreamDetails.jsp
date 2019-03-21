<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>电影播放流管理</title>
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
                elem: '#copyrightEndtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body>
<%--@elvariable id="cmsMovieStream" type="com.msframe.modules.cms.entity.CmsMovieStream"--%>
<form:form id="inputForm" modelAttribute="cmsMovieStream" action="${ctx}/cms/cmsMovieStream/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">视频资源ID：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.movieId.name}
            </td>
            <td class="width-15 active"><label class="pull-right">媒体纵横比（与表TAspectRatio关联）：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.aspectRatioId}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">时长（精确到秒）：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.duration}
            </td>
            <td class="width-15 active"><label class="pull-right">存在字幕信息：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.closedCaptioning}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">分辨率类型：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.resolutionId}
            </td>
            <td class="width-15 active"><label class="pull-right">文件大小（精确到MB）：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.fileSize}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">码流(与表TStream)关联：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.streamId}
            </td>
            <td class="width-15 active"><label class="pull-right">内容标识：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.contentId}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">视频格式(与表TVideoType)关联：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.videoTypeId}
            </td>
            <td class="width-15 active"><label class="pull-right">音频编码格式mp2 2 aac 3 amr：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.audioType}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">系统编码格式(与表TSystemLayer )关联：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.systemLayerId}
            </td>
            <td class="width-15 active"><label class="pull-right">版权证号：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.copyrightId}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">版权到期日期：</label></td>
            <td class="width-35">
                <fmt:formatDate value="${cmsMovieStream.copyrightEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td class="width-15 active"><label class="pull-right">授权方信息：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.copyrightProvider}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">引进批准文号：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.licenseNumber}
            </td>
            <td class="width-15 active"><label class="pull-right">公映放映许可证号：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.license}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">视频的播放地址：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.moviesPlayinfo}
            </td>
            <td class="width-15 active"><label class="pull-right">该流属于第几集（暂时使用在手机平台）：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsMovieStream.volume, 'cms_movie_stream_volume', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">审核情况：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsMovieStream.auditNumber, 'cms_movie_stream_audit_number', '')}
            </td>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsMovieStream.status,'general_status','')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                    ${cmsMovieStream.sort}
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
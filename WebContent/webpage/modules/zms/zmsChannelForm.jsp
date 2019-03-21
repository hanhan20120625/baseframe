<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>频道信息管理管理</title>
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
<%--@elvariable id="zmsChannel" type="com.msframe.modules.zms.entity.ZmsChannel"--%>
<form:form id="inputForm" modelAttribute="zmsChannel" action="${ctx}/zms/zmsChannel/save" method="post"
           class="form-horizontal" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">隶属分类：</label></td>
            <td class="width-35">
                <c:forEach items="${zmsCategoryList}" var="zmsCategory" varStatus="state">
                    <div style="float: left;padding: 0px;" class="col-md-4 col-sm-6">
                        <input type="checkbox" id="${zmsCategory.id}" name="zmsCategorySaveList"
                               value="${zmsCategory.id}"
                               <c:if test="${zmsCategory.checkFlag}">checked="checked"</c:if>
                               class="i-checks required"/>
                        <label for="${zmsCategory.id}">${zmsCategory.name}</label>
                    </div>
                </c:forEach>
            </td>
            <td class="width-15 active"><label class="pull-right">内容标识：</label></td>
            <td class="width-35">
                <form:input path="contentId" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                <form:input path="name" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">频道号：</label></td>
            <td class="width-35">
                <form:input path="channelNumber" htmlEscape="false" maxlength="11" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">频道类别：</label></td>
            <td class="width-35">
                <form:select path="type" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('zms_channel_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">子类别：</label></td>
            <td class="width-35">
                <form:select path="subtype" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('zms_channel_subtype')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">台标名称：</label></td>
            <td class="width-35">
                <form:input path="callSign" htmlEscape="false" maxlength="10" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">时移标志：</label></td>
            <td class="width-35">
                <form:input path="timeShift" htmlEscape="false" maxlength="1" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">开始时间：</label></td>
            <td class="width-35">
                <form:input path="startTime" htmlEscape="false" maxlength="16" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">edntime：</label></td>
            <td class="width-35">
                <form:input path="endTime" htmlEscape="false" maxlength="4" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">标识code：</label></td>
            <td class="width-35">
                <form:input path="channelCode" htmlEscape="false" maxlength="64" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">存储时长：</label></td>
            <td class="width-35">
                <form:input path="storageDuration" htmlEscape="false" maxlength="4" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">默认时移标志：</label></td>
            <td class="width-35">
                <form:select path="timeShiftDuration" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('zms_channel_time_shift')}" itemLabel="label"
                                  itemValue="value" htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                <form:input path="description" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">国家：</label></td>
            <td class="width-35">
                <form:input path="country" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">省：</label></td>
            <td class="width-35">
                <form:input path="province" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">城市：</label></td>
            <td class="width-35">
                <form:input path="city" htmlEscape="false" maxlength="256" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">邮编：</label></td>
            <td class="width-35">
                <form:input path="zipCode" htmlEscape="false" maxlength="10" class="form-control "/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">语言：</label></td>
            <td class="width-35">
                <form:input path="language" htmlEscape="false" maxlength="4" class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">拷贝保护标志：</label></td>
            <td class="width-35">
                <form:select path="macroVision" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_macro_vision')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">码流标志：</label></td>
            <td class="width-35">
                <form:input path="streamType" htmlEscape="false" maxlength="1" class="form-control  digits"/>
            </td>
            <td class="width-15 active"><label class="pull-right">视频编码格式：</label></td>
            <td class="width-35">
                <form:select path="videoType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_video_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">音频编码格式：</label></td>
            <td class="width-35">
                <form:select path="audioType" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_audio_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">双语标志：</label></td>
            <td class="width-35">
                <form:input path="bilingual" htmlEscape="false" maxlength="1" class="form-control  digits"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">台标大图：</label></td>
            <td class="width-35">
                <c:if test="${zmsChannel.bigPicurl ne '' and zmsChannel.bigPicurl ne null}">
                    <img src="${crc}${zmsChannel.bigPicurl}"/>
                </c:if>
                <input type="hidden" name="bigPicurl" value="${zmsChannel.bigPicurl}"/>
                <input type="file" name="files" class="form-control"/>
            </td>
            <td class="width-15 active"><label class="pull-right">台标小图：</label></td>
            <td class="width-35">
                <c:if test="${zmsChannel.picurl ne '' and zmsChannel.picurl ne null}">
                    <img src="${crc}${zmsChannel.picurl}"/>
                </c:if>
                <input type="hidden" name="picurl" value="${zmsChannel.picurl}"/>
                <input type="file" name="files" class="form-control"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">频道地址：</label></td>
            <td class="width-35">
                <form:input path="channelUrl" htmlEscape="false" maxlength="512" class="form-control "/>
            </td>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                <form:select path="status" class="form-control ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('general_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                <form:input path="sort" htmlEscape="false" maxlength="64" class="form-control  digits"/>
            </td>
            <td class="width-15 active"></td>
            <td class="width-35"></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
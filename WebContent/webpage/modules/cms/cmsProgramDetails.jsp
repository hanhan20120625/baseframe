<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频项目管理</title>
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
                elem: '#orgAirDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#licensingWindowStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#licensingWindowEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
    <style>
        .common-img-style {
            width: 500px;
            height: 300px;
            object-fit: cover;
        }

        .file-input {
            position: relative;
            display: inline-block;
            background: #4898d5;
            /* border: 1px solid #333; */
            padding: 4px 10px;
            overflow: hidden;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
            border-radius: 4px;
            color: #fff;
            font-size: 13px;

        }

        .file-input input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file-input .fa {
            margin-right: 5px;
        }

        .upload-box {
            position: relative;
            margin-top: 10px;
            height: 30px;
        }

        .showFileName1 {
            height: 30px;
            display: inline-block;
            line-height: 30px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<%--@elvariable id="cmsProgram" type="com.msframe.modules.cms.entity.CmsProgram"--%>
<form:form id="inputForm" modelAttribute="cmsProgram" action="${ctx}/cms/cmsProgram/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>

        <tr>
            <td class="width-15 active"><label class="pull-right">cp内容标识：</label></td>
            <td class="width-35">
                    ${cmsProgram.cpContentid}
            </td>
            <td class="width-15 active"><label class="pull-right">内容提供商：</label></td>
            <td class="width-35">
                    ${cmsProgram.spId.name}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">名称：</label></td>
            <td class="width-35">
                    ${cmsProgram.name}
            </td>
            <td class="width-15 active"><label class="pull-right">原始名称：</label></td>
            <td class="width-35">
                    ${cmsProgram.originalName}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">sp栏目id：</label></td>
            <td class="width-35">
                    ${cmsProgram.spCategoryid}
            </td>
            <td class="width-15 active"><label class="pull-right">排序名称：</label></td>
            <td class="width-35">
                    ${cmsProgram.sortName}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">搜索名称：</label></td>
            <td class="width-35">
                    ${cmsProgram.searchName}
            </td>
            <td class="width-15 active"><label class="pull-right">风格：</label></td>
            <td class="width-35">
                    ${cmsProgram.genre}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">栏目：</label></td>
            <td class="width-35">
                <c:forEach items="${cmsProgram.cmsProgramCategoryList}" var="category" varStatus="state">
                    <c:choose>
                        <c:when test="${state.last}">
                            ${category.categoryId.name}
                        </c:when>
                        <c:otherwise>
                            ${category.categoryId.name};
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
            <td class="width-15 active"><label class="pull-right">影片类型：</label></td>
            <td class="width-35">
                <c:forEach items="${cmsProgram.cmsProgramTypeList}" var="type" varStatus="state">
                    <c:choose>
                        <c:when test="${state.last}">
                            ${type.typeId.name}
                        </c:when>
                        <c:otherwise>
                            ${type.typeId.name};
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">语言：</label></td>
            <td class="width-35">
                <c:forEach items="${cmsProgram.cmsProgramLangList}" var="lang" varStatus="state">
                    <c:choose>
                        <c:when test="${state.last}">
                            ${lang.langId.name}
                        </c:when>
                        <c:otherwise>
                            ${lang.langId.name};
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
            <td class="width-15 active"><label class="pull-right">国家地区：</label></td>
            <td class="width-35">
                <c:forEach items="${cmsProgram.cmsProgramRegionList}" var="region" varStatus="state">
                    <c:choose>
                        <c:when test="${state.last}">
                            ${region.region.name}
                        </c:when>
                        <c:otherwise>
                            ${region.region.name};
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">横大图</label></td>
            <td class="width-35">
                <c:if test="${cmsProgram.hBigPic ne '' and cmsProgram.hBigPic ne null}">
                    <img src="${crc}${cmsProgram.hBigPic}" class="common-img-style"/>
                </c:if>
            </td>
            <td class="width-15 active"><label class="pull-right">横小图</label></td>
            <td class="width-35">
                <c:if test="${cmsProgram.hSmallPic ne '' and cmsProgram.hSmallPic ne null}">
                    <img src="${crc}${cmsProgram.hSmallPic}" class="common-img-style"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">竖大图</label></td>
            <td class="width-35">
                <c:if test="${cmsProgram.vBigPic ne '' and cmsProgram.vBigPic ne null}">
                    <img src="${crc}${cmsProgram.vBigPic}" class="common-img-style"/>
                </c:if>
            </td>
            <td class="width-15 active"><label class="pull-right">竖小图</label></td>
            <td class="width-35">
                <c:if test="${cmsProgram.vSmallPic ne '' and cmsProgram.vSmallPic ne null}">
                    <img src="${crc}${cmsProgram.vSmallPic}" class="common-img-style"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">方形图</label></td>
            <td class="width-35">
                <c:if test="${cmsProgram.squarePic ne '' and cmsProgram.squarePic ne null}">
                    <img src="${crc}${cmsProgram.squarePic}" class="common-img-style"/>
                </c:if>
            </td>
            <td class="width-15 active"><label class="pull-right">海报图</label></td>
            <td class="width-35">
                <c:if test="${cmsProgram.picUrl ne '' and cmsProgram.picUrl ne null}">
                    <img src="${crc}${cmsProgram.picUrl}" class="common-img-style" alt="">
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">拷贝保护标志：</label></td>
            <td class="width-35">
                    ${cmsProgram.macroVision}
            </td>
            <td class="width-15 active"><label class="pull-right">标签：</label></td>
            <td class="width-35">
                    ${cmsProgram.tagId}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">上映年份：</label></td>
            <td class="width-35">
                    ${cmsProgram.releaseYear}
            </td>
            <td class="width-15 active"><label class="pull-right">首播日期：</label></td>
            <td class="width-35">
                <fmt:formatDate value="${cmsProgram.orgAirDate}" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">有效开始时间：</label></td>
            <td class="width-35">
                <fmt:formatDate value="${cmsProgram.licensingWindowStart}" pattern="yyyy-MM-dd"/>
            </td>
            <td class="width-15 active"><label class="pull-right">有效结束时间：</label></td>
            <td class="width-35">
                <fmt:formatDate value="${cmsProgram.licensingWindowEnd}" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">新到天数：</label></td>
            <td class="width-35">
                    ${cmsProgram.displayAsNew}
            </td>
            <td class="width-15 active"><label class="pull-right">剩余天数：</label></td>
            <td class="width-35">
                    ${cmsProgram.displayAsLastChance}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">列表定价：</label></td>
            <td class="width-35">
                    ${cmsProgram.priceTaxin}
            </td>
            <td class="width-15 active"><label class="pull-right">源类型：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsProgram.sourceType, 'cms_program_source_type', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">连续剧标志：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsProgram.seriesFlag, 'cms_program_series_flag', '')}
            </td>
            <td class="width-15 active"><label class="pull-right">总集数：</label></td>
            <td class="width-35">
                    ${cmsProgram.totalEpisode}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">关键字：</label></td>
            <td class="width-35">
                    ${cmsProgram.keyWord}
            </td>
            <td class="width-15 active"><label class="pull-right">看点：</label></td>
            <td class="width-35">
                    ${cmsProgram.viewPoint}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">星级推荐：</label></td>
            <td class="width-35">
                    ${cmsProgram.starLevel}
            </td>
            <td class="width-15 active"><label class="pull-right">分级：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsProgram.rating, 'cms_program_rating', '')}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">奖项：</label></td>
            <td class="width-35">
                    ${cmsProgram.awards}
            </td>
            <td class="width-15 active"><label class="pull-right">播放时长(秒)：</label></td>
            <td class="width-35">
                    ${cmsProgram.length}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">同步状态：</label></td>
            <td class="width-35">
                    ${cmsProgram.syncState}
            </td>
            <td class="width-15 active"><label class="pull-right">CMS状态：</label></td>
            <td class="width-35">
                    ${cmsProgram.cmsState}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">状态：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsProgram.status, 'general_status', '')}
            </td>
            <td class="width-15 active"><label class="pull-right">排序：</label></td>
            <td class="width-35">
                    ${cmsProgram.sort}
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">影片展示类型：</label></td>
            <td class="width-35">
                    ${fns:getDictLabel(cmsProgram.showType,'cms_program_show_type','')}
            </td>
            <td class="width-15 active"><label class="pull-right">描述：</label></td>
            <td class="width-35">
                    ${cmsProgram.description}
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
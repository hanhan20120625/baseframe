<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频项目管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
        });

        /**
         * @description 更改排序
         * @param id
         * @param type
         * @param obj
         */
        function changeSort(id, type, obj) {
            var current = $(obj).parent().parent();
            var prev = current.prev();
            var next = current.next();
            var url = "";

            var name = $("#name").val();
            var cmsCategoryVoId = $("#cmsCategoryVoId").val();
            var seriesFlag = $("#seriesFlag").val();
            var tagId = $("#tagId").val();
            var totalEpisode = $("#totalEpisode").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();

            if(isSearch=="true"){
                url = "${ctx}/cms/cmsProgram/changeSort?id=" + id + "&type=" + type + "&name=" + name +
                    "&cmsCategoryVoId=" + cmsCategoryVoId + "&seriesFlag=" + seriesFlag + "&tagId=" + tagId +
                    "&totalEpisode=" + totalEpisode + "&status=" + status;
            }else{
                url = "${ctx}/cms/cmsProgram/changeSort?id=" + id + "&type=" + type;
            }
            $.ajax({
                url: url,
                type: "GET",
                success: function (data) {
                    console.log(data);
                    if (data == 'true') {
                        if (type == 'up') {
                            if (current.index() > 0) {
                                current.insertBefore(prev);
                            }
                        } else {
                            if (next) {
                                current.insertAfter(next);
                            }
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.status);
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);
                }
            });
        }

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>视频项目列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsProgram" type="com.msframe.modules.cms.entity.CmsProgram"--%>
                    <form:form id="searchForm" modelAttribute="cmsProgram" action="${ctx}/cms/cmsProgram/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="128"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box from-screen-btnbox clearfloat">
                                <span class="screen-title-style">栏目</span>
                                <sys:gridselect url="${ctx}/cms/cmsRecommend/selectCategoryId" id="cmsCategoryVo"
                                                name="cmsCategoryVo" value="${cmsProgram.cmsCategoryVo.id}"
                                                title="选择栏目"
                                                labelName="cmsCategoryVo.name"
                                                labelValue="${cmsProgram.cmsCategoryVo.name}"
                                                cssClass="form-control required screen-control" fieldLabels="名称"
                                                fieldKeys="name" searchLabel="名称" searchKey="name"
                                                linkage="false"></sys:gridselect>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">连续剧标志</span>
                                <form:select path="seriesFlag" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('cms_program_series_flag')}"
                                                  itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">标签</span>
                                <form:input path="tagId" htmlEscape="false" maxlength="11"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">总集数</span>
                                <form:input path="totalEpisode" htmlEscape="false" maxlength="11"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">状态</span>
                                <form:select path="status" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('general_status')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>

                        </div>
                    </form:form>
                    <br/>
                </div>
            </div>

            <!-- 工具栏 -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="pull-left">
                        <shiro:hasPermission name="cms:cmsProgram:add">
                            <table:addRow url="${ctx}/cms/cmsProgram/form" title="视频项目" width="90%" height="90%">
                            </table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgram:edit">
                            <table:editRow url="${ctx}/cms/cmsProgram/form" title="视频项目" width="90%" height="90%"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgram:del">
                            <table:delRow url="${ctx}/cms/cmsProgram/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgram:import">
                            <table:importExcel url="${ctx}/cms/cmsProgram/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgram:export">
                            <table:exportExcel url="${ctx}/cms/cmsProgram/export"></table:exportExcel><!-- 导出按钮 -->
                        </shiro:hasPermission>
                        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left"
                                onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新
                        </button>

                    </div>
                    <div class="pull-right">
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()"><i
                                class="fa fa-search"></i> 查询
                        </button>
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()"><i
                                class="fa fa-refresh"></i> 重置
                        </button>
                    </div>
                </div>
            </div>

            <!-- 表格 -->
            <table id="contentTable"
                   class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th><input type="checkbox" class="i-checks"></th>
                    <th>名称</th>
                    <th>原始名称</th>
                    <th>搜索名称</th>
                    <th>所属栏目</th>
                    <th>标签</th>
                    <th>连续剧标志</th>
                    <th>总集数</th>
                    <th>状态</th>
                    <th>排序操作</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsProgram" varStatus="state">
                    <tr>
                        <td><input type="checkbox" id="${cmsProgram.id}" class="i-checks"></td>
                        <td><a href="javaScript:void(0)"
                               onclick="openDialogView('查看视频项目', '${ctx}/cms/cmsProgram/view?id=${cmsProgram.id}','90%', '90%')">
                                ${cmsProgram.name}
                        </a></td>
                        <td>
                                ${cmsProgram.originalName}
                        </td>
                        <td>
                                ${cmsProgram.searchName}
                        </td>
                        <td>
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
                        <td>
                                ${cmsProgram.tagId}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsProgram.seriesFlag, 'cms_program_series_flag', '')}
                        </td>
                        <td>
                                ${cmsProgram.totalEpisode}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsProgram.status,'general_status','')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsProgram.id}','up',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsProgram.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsProgram:view">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('查看视频项目', '${ctx}/cms/cmsProgram/view?id=${cmsProgram.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsProgram:edit">
                                <a href="javaScript:void(0)"
                                   onclick="openDialog('修改视频项目', '${ctx}/cms/cmsProgram/form?id=${cmsProgram.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsProgram:del">
                                <a href="${ctx}/cms/cmsProgram/delete?id=${cmsProgram.id}"
                                   onclick="return confirmx('确认要删除该视频项目吗？', this.href)" class="btn btn-danger btn-xs"><i
                                        class="fa fa-trash"></i> 删除</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsProgram:cmsMovie:list">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('剧集管理', '${ctx}/cms/cmsMovie?isFromProgram=1&programId=${cmsProgram.id}','90%', '90%')"
                                   class="btn btn-warning btn-xs"><i class="fa fa-edit"></i> 剧集管理</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsProgram:cmsPicture:list">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('图片管理', '${ctx}/cms/cmsPictureInfo?programId=${cmsProgram.id}','90%', '90%')"
                                   class="btn btn-primary btn-xs"><i class="fa fa-image"></i> 图片管理</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsProgram:cmsCast:list">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('演员管理', '${ctx}/cms/cmsProgramCast?programId=${cmsProgram.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-users"></i> 演员管理</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- 分页代码 -->
            <table:page page="${page}"></table:page>
            <br/>
            <br/>
        </div>
    </div>
</div>
</body>
</html>
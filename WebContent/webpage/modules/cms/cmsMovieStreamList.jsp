<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>电影播放流管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
        });

        /**
         * @description 获取当前iframeId
         * @author leon
         * @date 2018/11/19
         */
        function getIframeId() {
            var iframeId = self.frameElement.getAttribute('id');
            return iframeId;
        }

        /**
         * @description 添加影片收藏
         * @author leon
         * @date 2018/11/19
         */
        function add() {
            openDialog("添加视频流信息", "${ctx}/cms/cmsMovieStream/form?movieId=${movieId}", "70%", "70%", getIframeId());
        }

        function editMovie(id) {
            openDialog("修改视频流信息", "${ctx}/cms/cmsMovieStream/form?id=" + id + "&movieId=${movieId}", "70%", "70%", getIframeId());
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>电影播放流列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsMovieStream" type="com.msframe.modules.cms.entity.CmsMovieStream"--%>
                    <form:form id="searchForm" modelAttribute="cmsMovieStream" action="${ctx}/cms/cmsMovieStream/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">分辨率类型</span>
                                <form:input path="resolutionId" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">审核情况</span>
                                <form:select path="auditNumber" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('cms_movie_stream_audit_number')}"
                                                  itemLabel="label" itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">状态</span>
                                <form:input path="status" htmlEscape="false" maxlength="11"
                                            class=" form-control input-sm screen-input-style"/>
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
                        <shiro:hasPermission name="cms:cmsMovieStream:add">
                            <!-- 增加按钮 -->
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="电影播放流"><i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovieStream:edit">
                            <table:editRow url="${ctx}/cms/cmsMovieStream/form" title="电影播放流"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovieStream:del">
                            <table:delRow url="${ctx}/cms/cmsMovieStream/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovieStream:import">
                            <table:importExcel url="${ctx}/cms/cmsMovieStream/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovieStream:export">
                            <table:exportExcel url="${ctx}/cms/cmsMovieStream/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th class="sort-column movieId">视频资源</th>
                    <th class="sort-column duration">时长（精确到秒）</th>
                    <th class="sort-column resolutionId">分辨率类型</th>
                    <th class="sort-column copyrightEndtime">版权到期日期</th>
                    <th class="sort-column volume">该流属于第几集</th>
                    <th class="sort-column moviesPlayinfo">播放地址</th>
                    <th class="sort-column auditNumber">审核情况</th>
                    <th class="sort-column status">状态</th>
                    <th class="sort-column sort">排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsMovieStream">
                    <tr>
                        <td><input type="checkbox" id="${cmsMovieStream.id}" class="i-checks"></td>
                        <td><a href="javaScript:void(0)"
                               onclick="openDialogView('查看电影播放流', '${ctx}/cms/cmsMovieStream/view?id=${cmsMovieStream.id}','90%', '90%')">
                                ${cmsMovieStream.movieId.name}
                        </a></td>
                        <td>
                                ${cmsMovieStream.duration}
                        </td>
                        <td>
                                ${cmsMovieStream.resolutionId}
                        </td>
                        <td>
                            <fmt:formatDate value="${cmsMovieStream.copyrightEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsMovieStream.volume, 'cms_movie_stream_volume', '')}
                        </td>
                        <td>
                                ${cmsMovieStream.moviesPlayinfo}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsMovieStream.auditNumber, 'cms_movie_stream_audit_number', '')}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsMovieStream.status,'general_status','')}
                        </td>
                        <td>
                                ${cmsMovieStream.sort}
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsMovieStream:view">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('查看电影播放流', '${ctx}/cms/cmsMovieStream/view?id=${cmsMovieStream.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsMovieStream:edit">
                                <a href="javaScript:void(0)"
                                   onclick="editMovie('${cmsMovieStream.id}')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsMovieStream:del">
                                <a href="${ctx}/cms/cmsMovieStream/delete?id=${cmsMovieStream.id}&movieId=${movieId}"
                                   onclick="return confirmx('确认要删除该电影播放流吗？', this.href)"
                                   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
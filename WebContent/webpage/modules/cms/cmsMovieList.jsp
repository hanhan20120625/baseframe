<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频管理</title>
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
            openDialog("视频", "${ctx}/cms/cmsMovie/form?programId=${programId}&isFromProgram=${isFromProgram}", "70%", "70%", getIframeId());
        }

        /**
         * @description 编辑剧集
         * @author leon
         * @date 2018/12/06
         */
        function editMovie(id) {
            openDialog("编辑剧集", "${ctx}/cms/cmsMovie/form?id=" + id + "&isFromProgram=${isFromProgram}", "70%", "70%", getIframeId());
        }

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

            var programName = $(".programId").val();
            var name = $("#name").val();
            var episode = $("#episode").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            if(isSearch=="true"){
                url = "${ctx}/cms/cmsMovie/changeSort?id=" + id + "&type=" + type + "&programName=" + programName +
                    "&name=" + name + "&episode=" + episode + "&status=" + status;
            }else{
                url = "${ctx}/cms/cmsMovie/changeSort?id=" + id + "&type=" + type;
            }
            console.log(url);
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
            <h5>视频列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsMovie" type="com.msframe.modules.cms.entity.CmsMovie"--%>
                    <form:form id="searchForm" modelAttribute="cmsMovie" action="${ctx}/cms/cmsMovie/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}">
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">影片名称</span>
                                <form:input path="programId.name" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style programId"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">剧集名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="128"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">第几集</span>
                                <form:input path="episode" htmlEscape="false" maxlength="11"
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
                        <shiro:hasPermission name="cms:cmsMovie:add">
                            <!-- 增加按钮 -->
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="添加剧集"><i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovie:edit">
                            <table:editRow url="${ctx}/cms/cmsMovie/form" title="视频" width="90%"
                                           height="90%" id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovie:del">
                            <table:delRow url="${ctx}/cms/cmsMovie/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovie:import">
                            <table:importExcel url="${ctx}/cms/cmsMovie/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsMovie:export">
                            <table:exportExcel url="${ctx}/cms/cmsMovie/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>影片名称</th>
                    <th>剧集名称</th>
                    <th>第几集</th>
                    <th>状态</th>
                    <th>排序操作</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsMovie">
                    <tr>
                        <td><input type="checkbox" id="${cmsMovie.id}" class="i-checks"></td>
                        <td><a href="javaScript:void(0)"
                               onclick="openDialogView('查看视频', '${ctx}/cms/cmsMovie/view?id=${cmsMovie.id}','90%', '90%')">
                                ${cmsMovie.programId.name}
                        </a></td>
                        <td>
                                ${cmsMovie.name}
                        </td>
                        <td>
                                ${cmsMovie.episode}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsMovie.status, 'general_status', '')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsMovie.id}','up',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsMovie.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsMovie:view">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('查看视频', '${ctx}/cms/cmsMovie/view?id=${cmsMovie.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsMovie:edit">
                                <a href="javaScript:void(0)"
                                   onclick="editMovie('${cmsMovie.id}')" class="btn btn-success btn-xs">
                                    <i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsMovie:del">
                                <a href="${ctx}/cms/cmsMovie/delete?id=${cmsMovie.id}"
                                   onclick="return confirmx('确认要删除该视频吗？', this.href)" class="btn btn-danger btn-xs"><i
                                        class="fa fa-trash"></i> 删除</a>
                            </shiro:hasPermission>
                            <a href="javaScript:void(0)"
                               onclick="openDialogView('图片管理', '${ctx}/cms/cmsPictureInfo?movieId=${cmsMovie.id}','90%', '90%')"
                               class="btn btn-primary btn-xs"><i class="fa fa-image"></i> 图片管理</a>
                            <shiro:hasPermission name="cms:cmsMovieStream:list">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('视频流管理', '${ctx}/cms/cmsMovieStream?movieId=${cmsMovie.id}','90%', '90%')"
                                   class="btn btn-warning btn-xs"><i class="fa fa-film icon-white"></i> 视频流管理</a>
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户收藏直播管理</title>
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
            openDialog("频道收藏", "${ctx}/zms/zmsUserChannelFavorite/form?user=${userInfoId}", "70%", "70%", getIframeId());
        }

        function editFav(id) {
            openDialog("编辑收藏", "${ctx}/zms/zmsUserChannelFavorite/form?id=" + id + "&user=${userInfoId}", "70%", "70%", getIframeId());
        }

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>用户收藏直播列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="zmsUserChannelFavorite" type="com.msframe.modules.zms.entity.ZmsUserChannelFavorite"--%>
                    <form:form id="searchForm" modelAttribute="zmsUserChannelFavorite"
                               action="${ctx}/zms/zmsUserChannelFavorite/" method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input type="hidden" name="user.id" value="${userInfoId}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">用户名</span>
                                <form:input path="username" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">昵称</span>
                                <form:input path="nickname" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="64"
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
                        <shiro:hasPermission name="zms:zmsUserChannelFavorite:add">
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="用户收藏直播"><i
                                    class="fa fa-plus"></i> 添加
                            </button>
                            <!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsUserChannelFavorite:edit">
                            <table:editRow url="${ctx}/zms/zmsUserChannelFavorite/form" title="用户收藏直播"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsUserChannelFavorite:del">
                            <table:delRow url="${ctx}/zms/zmsUserChannelFavorite/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsUserChannelFavorite:import">
                            <table:importExcel
                                    url="${ctx}/zms/zmsUserChannelFavorite/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsUserChannelFavorite:export">
                            <table:exportExcel
                                    url="${ctx}/zms/zmsUserChannelFavorite/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th class="sort-column channelId">频道编号</th>
                    <th class="sort-column user.name">用户编号</th>
                    <th class="sort-column username">用户名</th>
                    <th class="sort-column nickname">昵称</th>
                    <th class="sort-column name">名称</th>
                    <th class="sort-column status">状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="zmsUserChannelFavorite">
                    <tr>
                        <td><input type="checkbox" id="${zmsUserChannelFavorite.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看用户收藏直播', '${ctx}/zms/zmsUserChannelFavorite/view?id=${zmsUserChannelFavorite.id}','90%', '90%')">
                                ${zmsUserChannelFavorite.channelId.name}
                        </a></td>
                        <td>
                                ${zmsUserChannelFavorite.user.nickname}
                        </td>
                        <td>
                                ${zmsUserChannelFavorite.username}
                        </td>
                        <td>
                                ${zmsUserChannelFavorite.nickname}
                        </td>
                        <td>
                                ${zmsUserChannelFavorite.name}
                        </td>
                        <td>
                                ${fns:getDictLabel(zmsUserChannelFavorite.status, 'general_status', '')}
                        </td>
                        <td>
                            <shiro:hasPermission name="zms:zmsUserChannelFavorite:view">
                                <a href="#"
                                   onclick="openDialogView('查看用户收藏直播', '${ctx}/zms/zmsUserChannelFavorite/view?id=${zmsUserChannelFavorite.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsUserChannelFavorite:edit">
                                <a href="#"
                                   onclick="editFav('${zmsUserChannelFavorite.id}')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsUserChannelFavorite:del">
                                <a href="${ctx}/zms/zmsUserChannelFavorite/delete?id=${zmsUserChannelFavorite.id}"
                                   onclick="return confirmx('确认要删除该用户收藏直播吗？', this.href)" class="btn btn-danger btn-xs"><i
                                        class="fa fa-trash"></i> 删除</a>
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
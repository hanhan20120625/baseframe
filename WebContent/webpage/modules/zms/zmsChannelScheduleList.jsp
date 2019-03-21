<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>直播节目单列表管理</title>
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
            openDialog("添加节目单信息", "${ctx}/zms/zmsChannelSchedule/form?channelId=${channelId}", "70%", "70%", getIframeId());
        }

        function editChannelSchedule(id) {
            openDialog("编辑节目单信息", "${ctx}/zms/zmsChannelSchedule/form?id=" + id + "&channelId=${channelId}", "70%", "70%", getIframeId());
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>直播节目单列表列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="zmsChannelSchedule" type="com.msframe.modules.zms.entity.ZmsChannelSchedule"--%>
                    <form:form id="searchForm" modelAttribute="zmsChannelSchedule"
                               action="${ctx}/zms/zmsChannelSchedule/" method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="channelId" name="channelId" type="hidden" value="${channelId}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">内容标识</span>
                                <form:input path="contentId" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">直播单编号</span>
                                <form:input path="code" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">标识code</span>
                                <form:input path="channelCode" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">节目名称</span>
                                <form:input path="programName" htmlEscape="false" maxlength="128"
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
                        <shiro:hasPermission name="zms:zmsChannelSchedule:add">
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="增加节目单"><i class="fa fa-plus"></i>添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelSchedule:edit">
                            <table:editRow url="${ctx}/zms/zmsChannelSchedule/form" title="直播节目单列表"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelSchedule:del">
                            <table:delRow url="${ctx}/zms/zmsChannelSchedule/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelSchedule:import">
                            <table:importExcel
                                    url="${ctx}/zms/zmsChannelSchedule/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelSchedule:export">
                            <table:exportExcel
                                    url="${ctx}/zms/zmsChannelSchedule/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th class="sort-column contentId">内容标识</th>
                    <th class="sort-column code">直播单编号</th>
                    <th class="sort-column channelCode">标识code</th>
                    <th class="sort-column programName">节目名称</th>
                    <th class="sort-column startDate">播放日期</th>
                    <th class="sort-column startTime">开始时间</th>
                    <th class="sort-column duration">时长（精确到秒）</th>
                    <th class="sort-column description">描述</th>
                    <th class="sort-column orderNum">订阅人数</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="zmsChannelSchedule">
                    <tr>
                        <td><input type="checkbox" id="${zmsChannelSchedule.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看直播节目单列表', '${ctx}/zms/zmsChannelSchedule/view?id=${zmsChannelSchedule.id}','90%', '90%')">
                                ${zmsChannelSchedule.channelId.name}
                        </a></td>
                        <td>
                                ${zmsChannelSchedule.contentId}
                        </td>
                        <td>
                                ${zmsChannelSchedule.code}
                        </td>
                        <td>
                                ${zmsChannelSchedule.channelCode}
                        </td>
                        <td>
                                ${zmsChannelSchedule.programName}
                        </td>
                        <td>
                                ${zmsChannelSchedule.startDate}
                        </td>
                        <td>
                                ${zmsChannelSchedule.startTime}
                        </td>
                        <td>
                                ${zmsChannelSchedule.duration}
                        </td>
                        <td>
                                ${zmsChannelSchedule.description}
                        </td>
                        <td>
                                ${zmsChannelSchedule.orderNum}
                        </td>
                        <td>
                            <shiro:hasPermission name="zms:zmsChannelSchedule:view">
                                <a href="#"
                                   onclick="openDialogView('查看直播节目单列表', '${ctx}/zms/zmsChannelSchedule/view?id=${zmsChannelSchedule.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsChannelSchedule:edit">
                                <a href="#"
                                   onclick="editChannelSchedule('${zmsChannelSchedule.id}')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsChannelSchedule:del">
                                <a href="${ctx}/zms/zmsChannelSchedule/delete?id=${zmsChannelSchedule.id}"
                                   onclick="return confirmx('确认要删除该直播节目单列表吗？', this.href)"
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>直播节目流信息管理</title>
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
            openDialog("添加频道流信息", "${ctx}/zms/zmsChannelStream/form?channelId=${channelId}", "70%", "70%", getIframeId());
        }

        function editChannelStream(id) {
            openDialog("编辑频道流信息", "${ctx}/zms/zmsChannelStream/form?id=" + id + "&channelId=${channelId}", "70%", "70%", getIframeId());
        }

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>直播节目流信息列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="zmsChannelStream" type="com.msframe.modules.zms.entity.ZmsChannelStream"--%>
                    <form:form id="searchForm" modelAttribute="zmsChannelStream" action="${ctx}/zms/zmsChannelStream/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">视频编码格式</span>
                                <form:select path="videoType" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('general_video_type')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">音频编码格式</span>
                                <form:select path="audioType" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('general_audio_type')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">分辨率</span>
                                <form:select path="resolution" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('zms_channel_stream_resolution')}"
                                                  itemLabel="label" itemValue="value" htmlEscape="false"/>
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
                        <shiro:hasPermission name="zms:zmsChannelStream:add">
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="增加频道流信息">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelStream:edit">
                            <table:editRow url="${ctx}/zms/zmsChannelStream/form" title="直播节目流信息"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelStream:del">
                            <table:delRow url="${ctx}/zms/zmsChannelStream/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelStream:import">
                            <table:importExcel
                                    url="${ctx}/zms/zmsChannelStream/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannelStream:export">
                            <table:exportExcel
                                    url="${ctx}/zms/zmsChannelStream/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th class="sort-column contentId">内容标识</th>
                    <th class="sort-column channelId">频道编号</th>
                    <th class="sort-column channelContentId">频道标志</th>
                    <th class="sort-column playUrl">具体播放地址</th>
                    <th class="sort-column resolution">分辨率</th>
                    <th class="sort-column status">状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="zmsChannelStream">
                    <tr>
                        <td><input type="checkbox" id="${zmsChannelStream.id}" class="i-checks"></td>
                        <td><a href="javaScript:void(0)"
                               onclick="openDialogView('查看直播节目流信息', '${ctx}/zms/zmsChannelStream/view?id=${zmsChannelStream.id}','90%', '90%')">
                                ${zmsChannelStream.contentId}
                        </a></td>
                        <td>
                                ${zmsChannelStream.channelId.name}
                        </td>
                        <td>
                                ${zmsChannelStream.channelContentId}
                        </td>
                        <td>
                                ${zmsChannelStream.playUrl}
                        </td>
                        <td>
                                ${fns:getDictLabel(zmsChannelStream.resolution, 'zms_channel_stream_resolution', '')}
                        </td>
                        <td>
                                ${fns:getDictLabel(zmsChannelStream.status, 'general_status', '')}
                        </td>
                        <td>
                            <shiro:hasPermission name="zms:zmsChannelStream:view">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('查看直播节目流信息', '${ctx}/zms/zmsChannelStream/view?id=${zmsChannelStream.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsChannelStream:edit">
                                <a href="javaScript:void(0)"
                                   onclick="editChannelStream('${zmsChannelStream.id}')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsChannelStream:del">
                                <a href="${ctx}/zms/zmsChannelStream/delete?id=${zmsChannelStream.id}"
                                   onclick="return confirmx('确认要删除该直播节目流信息吗？', this.href)"
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
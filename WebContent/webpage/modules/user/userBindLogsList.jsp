<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户绑定iptv信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            laydate({
                elem: '#bindtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
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
         * @description 添加IPTV绑定
         * @author leon
         * @date 2018/11/19
         */
        function add() {
            openDialog("用户绑定IPTV信息", "${ctx}/user/userBindLogs/form?user=${userInfoId}", "70%", "70%", getIframeId());
        }

        /**
         * @description 编辑用户绑定的IPTV信息
         * @author leon
         * @date 2018/11/29
         * @param id
         */
        function edit(id) {
            openDialog("编辑用户绑定IPTV信息", "${ctx}/user/userBindLogs/form?id=" + id + "&user=${userInfoId}", "70%", "70%", getIframeId());
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>用户绑定IPTV信息列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="userBindLogs" type="com.msframe.modules.user.entity.UserBindLogs"--%>
                    <form:form id="searchForm" modelAttribute="userBindLogs" action="${ctx}/user/userBindLogs/"
                               method="post" class="form-inline">
                        <input type="hidden" name="user.id" value="${userInfoId}"/>
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">
                            

                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">设备编号</span>
                                <form:input path="stbid" htmlEscape="false" maxlength="64"
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
                        <shiro:hasPermission name="user:userBindLogs:add">
                            <!-- 增加按钮 -->
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="用户绑定iptv信息"><i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userBindLogs:del">
                            <table:delRow url="${ctx}/user/userBindLogs/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
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
                    <th class="sort-column user.nickname">用户编号</th>
                    <th class="sort-column bindtime">绑定结果</th>
                    <th class="sort-column stbid">设备编号</th>
                    <th class="sort-column status">状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="userBindLogs">
                    <tr>
                        <td><input type="checkbox" id="${userBindLogs.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看用户绑定iptv信息', '${ctx}/user/userBindLogs/view?id=${userBindLogs.id}','90%', '90%')">
                                ${userBindLogs.user.nickname}
                        </a></td>
                        <td>
                            <fmt:formatDate value="${userBindLogs.bindtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                                ${userBindLogs.stbid}
                        </td>
                        <td>
                                ${fns:getDictLabel(userBindLogs.status, 'general_status', '')}
                        </td>
                        <td>
                            <shiro:hasPermission name="user:userBindLogs:view">
                                <a href="#"
                                   onclick="openDialogView('查看用户绑定iptv信息', '${ctx}/user/userBindLogs/view?id=${userBindLogs.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userBindLogs:edit">
                                <a href="#"
                                   onclick="edit('${userBindLogs.id}')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userBindLogs:del">
                                <a href="${ctx}/user/userBindLogs/delete?id=${userBindLogs.id}"
                                   onclick="return confirmx('确认要删除该用户绑定iptv信息吗？', this.href)"
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
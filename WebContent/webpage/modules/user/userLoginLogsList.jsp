<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>客户登录日志管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            laydate({
                elem: '#lastLoginTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
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

            $.ajax({
                url: "${ctx}/user/userLoginLogs/changeSort?id=" + id + "&type=" + type,
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
            <h5>客户登录日志列表</h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="userLoginLogs" type="com.msframe.modules.user.entity.UserLoginLogs"--%>
                    <form:form id="searchForm" modelAttribute="userLoginLogs" action="${ctx}/user/userLoginLogs/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <%--<div class="from-screen-box from-screen-btnbox clearfloat">
                                <span class="screen-title-style">客户名称</span>
                                <sys:treeselect id="user" name="user.id" value="${userLoginLogs.user.id}"
                                                labelName="user.nickname" labelValue="${userLoginLogs.user.nickname}"
                                                title="用户" url="/sys/office/treeData?type=3"
                                                cssClass="form-control input-sm screen-control" allowClear="true"
                                                notAllowSelectParent="true"/>
                            </div>--%>

                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">客户名称</span>
                                <form:input path="user.id" htmlEscape="false" maxlength="20"
                                            class="form-control input-sm screen-input-style"/>
                            </div>

                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">最后登陆时间</span>
                                <input id="lastLoginTime" name="lastLoginTime" type="text" maxlength="20"
                                       class="laydate-icon form-control layer-date input-sm screen-control"
                                       value="<fmt:formatDate value="${userLoginLogs.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                            </div>

                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">登录IP</span>
                                <form:input path="loginIp" htmlEscape="false" maxlength="20"
                                            class="form-control input-sm screen-input-style"/>
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
                        <shiro:hasPermission name="user:userLoginLogs:add">
                            <table:addRow url="${ctx}/user/userLoginLogs/form"
                                          title="用户登录日志"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userLoginLogs:edit">
                            <table:editRow url="${ctx}/user/userLoginLogs/form" title="用户登录日志"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userLoginLogs:del">
                            <table:delRow url="${ctx}/user/userLoginLogs/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userLoginLogs:import">
                            <table:importExcel url="${ctx}/user/userLoginLogs/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userLoginLogs:export">
                            <table:exportExcel url="${ctx}/user/userLoginLogs/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>客户名称</th>
                    <th>最后登陆时间</th>
                    <th>登录IP</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="userLoginLogs">
                    <tr>
                        <td><input type="checkbox" id="${userLoginLogs.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看用户登录日志', '${ctx}/user/userLoginLogs/view?id=${userLoginLogs.id}','90%', '90%')">
                                ${userLoginLogs.user.nickname}
                        </a></td>
                        <td>
                            <fmt:formatDate value="${userLoginLogs.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                                ${userLoginLogs.loginIp}
                        </td>
                        <td>
                                ${fns:getDictLabel(userLoginLogs.status, 'general_status', '')}
                        </td>
                        <td style="display: none">
                            <a href="javaScript:void(0)" onclick="changeSort('${userLoginLogs.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${userLoginLogs.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="user:userLoginLogs:view">
                                <a href="#"
                                   onclick="openDialogView('查看用户登录日志', '${ctx}/user/userLoginLogs/view?id=${userLoginLogs.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userLoginLogs:edit">
                                <a href="#"
                                   onclick="openDialog('修改用户登录日志', '${ctx}/user/userLoginLogs/form?id=${userLoginLogs.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userLoginLogs:del">
                                <a href="${ctx}/user/userLoginLogs/delete?id=${userLoginLogs.id}"
                                   onclick="return confirmx('确认要删除该用户登录日志吗？', this.href)" class="btn btn-danger btn-xs"><i
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
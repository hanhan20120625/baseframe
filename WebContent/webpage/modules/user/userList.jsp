<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>客户信息管理</title>
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

            $.ajax({
                url: "${ctx}/user/user/changeSort?id=" + id + "&type=" + type,
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
            <h5>客户信息列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="userInfo" type="com.msframe.modules.user.entity.UserInfo"--%>
                    <form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/user/user/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">

                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">用户名</span>
                                <form:input path="loginName" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">真实名称</span>
                                <form:input path="realName" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">昵称</span>
                                <form:input path="nickname" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">证件号</span>
                                <form:input path="idNumber" htmlEscape="false" maxlength="50"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">手机号</span>
                                <form:input path="mobile" htmlEscape="false" maxlength="20"
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
                        <shiro:hasPermission name="user:user:add">
                            <table:addRow url="${ctx}/user/user/form" title="客户信息" width="90%"
                                          height="90%"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:user:edit">
                            <table:editRow url="${ctx}/user/user/form" title="客户信息" width="90%" height="90%"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:user:del">
                            <table:delRow url="${ctx}/user/user/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:user:import">
                            <table:importExcel url="${ctx}/user/user/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:user:export">
                            <table:exportExcel url="${ctx}/user/user/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>用户名</th>
                    <th>真实名称</th>
                    <th>昵称</th>
                    <th>性别</th>
                    <th>手机号</th>
                    <th>证件号码</th>
                    <th>是否是vip会员</th>
                    <th>积分数量</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="user">
                    <tr>
                        <td><input type="checkbox" id="${user.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看客户信息', '${ctx}/user/user/view?id=${user.id}','90%', '90%')">
                                ${user.loginName}
                        </a></td>
                        <td>
                                ${user.realName}
                        </td>
                        <td>
                                ${user.nickname}
                        </td>
                        <td>
                                ${fns:getDictLabel(user.sex, 'general_sex', '')}
                        </td>
                        <td>
                                ${user.mobile}
                        </td>
                        <td>
                                ${user.idNumber}
                        </td>
                        <td>
                                ${fns:getDictLabel(user.isvip, 'user_isvip', '')}
                        </td>
                        <td>
                                ${user.score}
                        </td>
                        <td>
                                ${fns:getDictLabel(user.status,'general_status','')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${user.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${user.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                                <%--<shiro:hasPermission name="user:user:view">
                                    <a href="#"
                                       onclick="openDialogView('查看客户信息', '${ctx}/user/user/view?id=${user.id}','90%', '90%')"
                                       class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                                </shiro:hasPermission>--%>
                            <shiro:hasPermission name="user:user:edit">
                                <a href="#"
                                   onclick="openDialog('修改客户信息', '${ctx}/user/user/form?id=${user.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:user:del">
                                <a href="${ctx}/user/user/delete?id=${user.id}"
                                   onclick="return confirmx('确认要删除该客户信息吗？', this.href)" class="btn btn-danger btn-xs"><i
                                        class="fa fa-trash"></i> 删除</a>
                            </shiro:hasPermission>
                            <a href="#"
                               onclick="openDialogView('查看客户绑定设备信息', '${ctx}/user/userBindStbid?user=${user.id}','100%', '100%')"
                               class="btn btn-warning btn-xs"><i class="fa fa-hdd-o"></i> 查看绑定设备</a>
                            <a href="#"
                               onclick="openDialogView('查看客户绑定IPTV信息', '${ctx}/user/userBindLogs?user=${user.id}','100%', '100%')"
                               class="btn btn-info btn-xs"><i class="fa fa-desktop"></i> 查看绑定IPTV</a>
                            <a href="#"
                               onclick="openDialogView('查看客户收藏影片', '${ctx}/user/userFavorite?user=${user.id}','100%', '100%')"
                               class="btn btn-primary btn-xs"><i class="fa fa-star"></i> 查看收藏影片</a>
                            <a href="#"
                               onclick="openDialogView('查看客户收藏频道', '${ctx}/zms/zmsUserChannelFavorite?user=${user.id}','100%', '100%')"
                               class="btn btn-success btn-xs"><i class="fa fa-bullhorn"></i> 查看收藏频道</a>
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
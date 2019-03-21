<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户播放记录管理</title>
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
                url: "${ctx}/user/userPlayHistory/changeSort?id=" + id + "&type=" + type,
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
            <h5>用户播放记录列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="userPlayHistory" type="com.msframe.modules.user.entity.UserPlayHistory"--%>
                    <form:form id="searchForm" modelAttribute="userPlayHistory" action="${ctx}/user/userPlayHistory/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <%--<div class="from-screen-box from-screen-btnbox clearfloat">
                                <span class="screen-title-style">用户编号</span>
                                <sys:treeselect id="user" name="user.id" value="${userPlayHistory.user.id}"
                                                labelName="user.nickname" labelValue="${userPlayHistory.user.nickname}"
                                                title="用户" url="/sys/office/treeData?type=3"
                                                cssClass="form-control input-sm screen-control" allowClear="true"
                                                notAllowSelectParent="true"/>
                            </div>--%>
                                <div class="from-screen-box clearfloat">
                                    <span class="screen-title-style">用户编号</span>
                                    <form:input path="user.nickname" htmlEscape="false" maxlength="64"
                                                class=" form-control input-sm screen-input-style"/>
                                </div>

                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">影片编号</span>
                                <form:input path="programId.id" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">视频编号</span>
                                <form:input path="moviceId.id" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">播放流编号</span>
                                <form:input path="movicestreamId.id" htmlEscape="false" maxlength="64"
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
                        <shiro:hasPermission name="user:userPlayHistory:add">
                            <table:addRow url="${ctx}/user/userPlayHistory/form"
                                          title="用户播放记录"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userPlayHistory:edit">
                            <table:editRow url="${ctx}/user/userPlayHistory/form" title="用户播放记录"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userPlayHistory:del">
                            <table:delRow url="${ctx}/user/userPlayHistory/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userPlayHistory:import">
                            <table:importExcel
                                    url="${ctx}/user/userPlayHistory/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userPlayHistory:export">
                            <table:exportExcel
                                    url="${ctx}/user/userPlayHistory/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>用户编号</th>
                    <th>影片编号</th>
                    <th>视频编号</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="userPlayHistory">
                    <tr>
                        <td><input type="checkbox" id="${userPlayHistory.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看用户播放记录', '${ctx}/user/userPlayHistory/view?id=${userPlayHistory.id}','90%', '90%')">
                                ${userPlayHistory.user.nickname}
                        </a></td>
                        <td>
                                ${userPlayHistory.programId.name}
                        </td>
                        <td>
                                ${userPlayHistory.moviceId.name}
                        </td>
                        <td>
                                ${fns:getDictLabel(userPlayHistory.status, 'general_status', '')}
                        </td>
                        <td>
                                ${userPlayHistory.sort}
                        </td>
                        <td style="display: none">
                            <a href="javaScript:void(0)" onclick="changeSort('${userPlayHistory.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${userPlayHistory.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="user:userPlayHistory:view">
                                <a href="#"
                                   onclick="openDialogView('查看用户播放记录', '${ctx}/user/userPlayHistory/view?id=${userPlayHistory.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userPlayHistory:edit">
                                <a href="#"
                                   onclick="openDialog('修改用户播放记录', '${ctx}/user/userPlayHistory/form?id=${userPlayHistory.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userPlayHistory:del">
                                <a href="${ctx}/user/userPlayHistory/delete?id=${userPlayHistory.id}"
                                   onclick="return confirmx('确认要删除该用户播放记录吗？', this.href)" class="btn btn-danger btn-xs"><i
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
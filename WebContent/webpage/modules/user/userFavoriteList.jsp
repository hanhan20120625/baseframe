<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>影片收藏信息管理</title>
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
            openDialog("添加影片收藏", "${ctx}/user/userFavorite/form?user=${userInfoId}", "70%", "70%", getIframeId());
        }

        /**
         * @description 修改影片收藏
         * @author leon
         * @date 2018/11/29
         * @param id
         */
        function edit(id) {
            openDialog("修改影片收藏", "${ctx}/user/userFavorite/form?id=" + id + "&user=${userInfoId}", "70%", "70%", getIframeId());
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>影片收藏信息列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="userFavorite" type="com.msframe.modules.user.entity.UserFavorite"--%>
                    <form:form id="searchForm" modelAttribute="userFavorite" action="${ctx}/user/userFavorite/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input type="hidden" name="user.id" value="${userInfoId}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">影片名称</span>
                                <sys:gridselect id="programId" name="programId.id" value="${userFavorite.programId.id}"
                                                labelName="programId.name" labelValue="${userFavorite.programId.name}"
                                                fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name"
                                                title="选择影片" url="${ctx}/user/userFavorite/selectProgramId"
                                                cssClass="form-control required screen-control" linkage="false"/>
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
                        <shiro:hasPermission name="user:userFavorite:add">
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="影片收藏信息"><i
                                    class="fa fa-plus"></i> 添加
                            </button>
                            <!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userFavorite:edit">
                            <table:editRow url="${ctx}/user/userFavorite/form" title="影片收藏信息"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userFavorite:del">
                            <table:delRow url="${ctx}/user/userFavorite/deleteAll"
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
                    <th class="sort-column programId">影片名称</th>
                    <th class="sort-column moviceId">视频编号</th>
                    <th class="sort-column status">状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="userFavorite">
                    <tr>
                        <td><input type="checkbox" id="${userFavorite.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看影片收藏信息', '${ctx}/user/userFavorite/view?id=${userFavorite.id}','90%', '90%')">
                                ${userFavorite.programId.name}
                        </a></td>
                        <td>
                                ${userFavorite.moviceId.name}
                        </td>
                        <td>
                                ${fns:getDictLabel(userFavorite.status, 'general_status', '')}
                        </td>
                        <td>
                            <shiro:hasPermission name="user:userFavorite:view">
                                <a href="#"
                                   onclick="openDialogView('查看影片收藏信息', '${ctx}/user/userFavorite/view?id=${userFavorite.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userFavorite:edit">
                                <a href="#"
                                   onclick="edit('${userFavorite.id}')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userFavorite:del">
                                <a href="${ctx}/user/userFavorite/delete?id=${userFavorite.id}"
                                   onclick="return confirmx('确认要删除该影片收藏信息吗？', this.href)" class="btn btn-danger btn-xs"><i
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
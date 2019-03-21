<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>用户绑定设备管理</title>
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
         * @description 添加设备绑定
         * @author leon
         * @date 2018/11/19
         */
        function add() {
            openDialog("添加用户设备绑定", "${ctx}/user/userBindStbid/form?user=${userInfoId}", "70%", "70%", getIframeId());
        }

        function edit(id) {
            openDialog("编辑用户设备绑定", "${ctx}/user/userBindStbid/form?id=" + id + "&user=${userInfoId}", "70%", "70%", getIframeId());
        }

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>用户绑定设备列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="userBindStbid" type="com.msframe.modules.user.entity.UserBindStbid"--%>
                    <form:form id="searchForm" modelAttribute="userBindStbid" action="${ctx}/user/userBindStbid/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input type="hidden" name="user.id" value="${userInfoId}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box from-screen-btnbox clearfloat">
                                <span class="screen-title-style">用户昵称</span>
                                <sys:gridselect id="user" name="user.id" value="${userBindStbid.user.id}"
                                                labelName="user.nickname" labelValue="${userBindStbid.user.nickname}"
                                                fieldLabels="用户昵称" fieldKeys="nickname" searchLabel="用户昵称"
                                                searchKey="nickname" title="选择用户"
                                                cssClass="form-control input-sm screen-input-style"
                                                url="${ctx}/user/user/selectUserInfo" linkage="false"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">设备编号</span>
                                <form:input path="stbid" htmlEscape="false" maxlength="64"
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
                        <shiro:hasPermission name="user:userBindStbid:add">
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="用户绑定设备"><i
                                    class="fa fa-plus"></i> 添加
                            </button>
                            <!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:userBindStbid:del">
                            <table:delRow url="${ctx}/user/userBindStbid/deleteAll"
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
                    <th class="sort-column user.name">用户编号</th>
                    <th class="sort-column stbid">设备编号</th>
                    <th class="sort-column status">状态</th>
                    <th class="sort-column sort">排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="userBindStbid">
                    <tr>
                        <td><input type="checkbox" id="${userBindStbid.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看用户绑定设备', '${ctx}/user/userBindStbid/view?id=${userBindStbid.id}','90%', '90%')">
                                ${userBindStbid.user.nickname}
                        </a></td>
                        <td>
                                ${userBindStbid.stbid}
                        </td>
                        <td>
                                ${fns:getDictLabel(userBindStbid.status,'general_status','')}
                        </td>
                        <td>
                                ${userBindStbid.sort}
                        </td>
                        <td>
                            <shiro:hasPermission name="user:userBindStbid:view">
                                <a href="#"
                                   onclick="openDialogView('查看用户绑定设备', '${ctx}/user/userBindStbid/view?id=${userBindStbid.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userBindStbid:edit">
                                <a href="#" onclick="edit('${userBindStbid.id}')" class="btn btn-success btn-xs">
                                    <i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="user:userBindStbid:del">
                                <a href="${ctx}/user/userBindStbid/delete?id=${userBindStbid.id}"
                                   onclick="return confirmx('确认要删除该用户绑定设备吗？', this.href)" class="btn btn-danger btn-xs"><i
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
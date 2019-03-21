<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频演员管理</title>
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
            openDialog("添加影片演员", "${ctx}/cms/cmsProgramCast/form?programId=${programId}", "70%", "70%", getIframeId());
        }

        /**
         * @description 编辑演员管理
         * @author leon
         * @date 2018/12/06
         * @param id
         */
        function editCast(id) {
            openDialog("编辑影片演员", "${ctx}/cms/cmsProgramCast/form?id=" + id + "programId=${programId}", "70%", "70%", getIframeId());

        }

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>视频演员列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsProgramCast" type="com.msframe.modules.cms.entity.CmsProgramCast"--%>
                    <form:form id="searchForm" modelAttribute="cmsProgramCast" action="${ctx}/cms/cmsProgramCast/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input type="hidden" name="programId.id" value="${programId}">
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">演员</span>
                                <form:input path="castId.name" htmlEscape="false" maxlength="64"
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
                        <shiro:hasPermission name="cms:cmsProgramCast:add">
                            <!-- 增加按钮 -->
                            <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left"
                                    onclick="add()" title="视频演员"><i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgramCast:edit">
                            <table:editRow url="${ctx}/cms/cmsProgramCast/form" title="视频演员"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgramCast:del">
                            <table:delRow url="${ctx}/cms/cmsProgramCast/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgramCast:import">
                            <table:importExcel url="${ctx}/cms/cmsProgramCast/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsProgramCast:export">
                            <table:exportExcel url="${ctx}/cms/cmsProgramCast/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>视频编号</th>
                    <th>演员</th>
                    <th>类型</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsProgramCast">
                    <tr>
                        <td><input type="checkbox" id="${cmsProgramCast.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看视频演员', '${ctx}/cms/cmsProgramCast/view?id=${cmsProgramCast.id}','90%', '90%')">
                                ${cmsProgramCast.programId.name}
                        </a></td>
                        <td>
                                ${cmsProgramCast.castId.name}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsProgramCast.castType, 'cms_program_cast_cast_type', '')}
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsProgramCast:view">
                                <a href="#"
                                   onclick="openDialogView('查看视频演员', '${ctx}/cms/cmsProgramCast/view?id=${cmsProgramCast.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsProgramCast:edit">
                                <a href="#"
                                   onclick="editCast('${cmsProgramCast.id}')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsProgramCast:del">
                                <a href="${ctx}/cms/cmsProgramCast/delete?id=${cmsProgramCast.id}&programId=${programId}"
                                   onclick="return confirmx('确认要删除该视频演员吗？', this.href)" class="btn btn-danger btn-xs"><i
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
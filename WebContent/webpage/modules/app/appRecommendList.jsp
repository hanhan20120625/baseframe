<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>banner推荐管理</title>
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
            var url = "";

            var name = $("#name").val();
            var programId = $("#programIdId").val();
            var categoryId = $("#categoryIdId").val();
            var isindex = $("#isindex").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            console.log(status);
            if(isSearch=="true"){
                url = "${ctx}/app/appRecommend/changeSort?id=" + id + "&type=" + type + "&name=" + name + "&programId=" + programId +
                    "&categoryId=" + categoryId + "&isindex=" + isindex + "&status=" + status;
            }else{
                url = "${ctx}/app/appRecommend/changeSort?id=" + id + "&type=" + type;
            }

            $.ajax({
                url: url,
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
            <h5>banner推荐列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="appRecommend" type="com.msframe.modules.app.entity.AppRecommend"--%>
                    <form:form id="searchForm" modelAttribute="appRecommend" action="${ctx}/app/appRecommend/"
                               method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}">
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">推荐名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="128"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box from-screen-btnbox clearfloat">
                                <span class="screen-title-style">影片名称</span>
                                <sys:gridselect url="${ctx}/app/appRecommend/selectprogramId" id="programId"
                                                name="programId" value="${appRecommend.programId.id}" title="选择影片编号"
                                                labelName="programId.name"
                                                labelValue="${appRecommend.programId.name}"
                                                cssClass="form-control required screen-control" fieldLabels="名称"
                                                fieldKeys="name" searchLabel="名称" searchKey="name"
                                                linkage="false"></sys:gridselect>
                            </div>


                            <div class="from-screen-box from-screen-btnbox clearfloat">
                                <span class="screen-title-style">影片栏目</span>
                                <sys:gridselect url="${ctx}/app/appRecommend/selectCategoryId" id="categoryId"
                                                name="categoryId" value="${appRecommend.categoryId.id}" title="选择影片编号"
                                                labelName="categoryId.name"
                                                labelValue="${appRecommend.categoryId.name}"
                                                cssClass="form-control required screen-control" fieldLabels="名称"
                                                fieldKeys="name" searchLabel="名称" searchKey="name"
                                                linkage="false"></sys:gridselect>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">是否首页</span>
                                <form:select path="isindex" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('general_is_index')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
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
                        <shiro:hasPermission name="app:appRecommend:add">
                            <table:addRow url="${ctx}/app/appRecommend/form"
                                          title="banner推荐"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="app:appRecommend:edit">
                            <table:editRow url="${ctx}/app/appRecommend/form" title="banner推荐"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="app:appRecommend:del">
                            <table:delRow url="${ctx}/app/appRecommend/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="app:appRecommend:import">
                            <table:importExcel url="${ctx}/app/appRecommend/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="app:appRecommend:export">
                            <table:exportExcel url="${ctx}/app/appRecommend/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>推荐名称</th>
                    <th>影片名称</th>
                    <th>影片栏目</th>
                    <th>是否首页</th>
                    <th>业务类型</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="appRecommend">
                    <tr>
                        <td><input type="checkbox" id="${appRecommend.id}" class="i-checks"></td>
                        <td>
                            <a href="#"
                               onclick="openDialogView('查看banner推荐', '${ctx}/app/appRecommend/view?id=${appRecommend.id}','90%', '90%')">
                                    ${appRecommend.name}
                            </a>
                        </td>
                        <td>
                                ${appRecommend.programId.name}
                        </td>
                        <td>
                                ${appRecommend.categoryId.name}
                        </td>
                        <td>
                                ${fns:getDictLabel(appRecommend.isindex, 'general_is_index', '')}
                        </td>
                        <td>
                                ${appRecommend.bizType}
                        </td>
                        <td>
                                ${fns:getDictLabel(appRecommend.status, 'general_status', '')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${appRecommend.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${appRecommend.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="app:appRecommend:view">
                                <a href="#"
                                   onclick="openDialogView('查看banner推荐', '${ctx}/app/appRecommend/view?id=${appRecommend.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="app:appRecommend:edit">
                                <a href="#"
                                   onclick="openDialog('修改banner推荐', '${ctx}/app/appRecommend/form?id=${appRecommend.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="app:appRecommend:del">
                                <a href="${ctx}/app/appRecommend/delete?id=${appRecommend.id}"
                                   onclick="return confirmx('确认要删除该banner推荐吗？', this.href)"
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
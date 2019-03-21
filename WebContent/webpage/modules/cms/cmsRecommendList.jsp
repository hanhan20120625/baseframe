<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>影片推荐管理</title>
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
            if(isSearch=="true"){
                url = "${ctx}/cms/cmsRecommend/changeSort?id=" + id + "&type=" + type + "&name=" + name + "&programId=" + programId +
                    "&categoryId=" + categoryId + "&isindex=" + isindex + "&status=" + status;
            }else{
                url = "${ctx}/cms/cmsRecommend/changeSort?id=" + id + "&type=" + type;
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
            <h5>影片推荐列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsRecommend" type="com.msframe.modules.cms.entity.CmsRecommend"--%>
                    <form:form id="searchForm" modelAttribute="cmsRecommend" action="${ctx}/cms/cmsRecommend/"
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
                                <sys:gridselect url="${ctx}/cms/cmsRecommend/selectprogramId" id="programId"
                                                name="programId" value="${cmsRecommend.programId.id}" title="选择影片编号"
                                                labelName="programId.name"
                                                labelValue="${cmsRecommend.programId.name}"
                                                cssClass="form-control required screen-control" fieldLabels="名称"
                                                fieldKeys="name" searchLabel="名称" searchKey="name" linkage="false"></sys:gridselect>
                            </div>


                            <div class="from-screen-box from-screen-btnbox clearfloat">
                                <span class="screen-title-style">影片栏目</span>
                                <sys:gridselect url="${ctx}/cms/cmsRecommend/selectCategoryId" id="categoryId"
                                                name="categoryId" value="${cmsRecommend.categoryId.id}" title="选择影片编号"
                                                labelName="categoryId.name"
                                                labelValue="${cmsRecommend.categoryId.name}"
                                                cssClass="form-control required screen-control" fieldLabels="名称"
                                                fieldKeys="name" searchLabel="名称" searchKey="name" linkage="false"></sys:gridselect>
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
                        <shiro:hasPermission name="cms:cmsRecommend:add">
                            <table:addRow url="${ctx}/cms/cmsRecommend/form" title="影片推荐"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRecommend:edit">
                            <table:editRow url="${ctx}/cms/cmsRecommend/form" title="影片推荐"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRecommend:del">
                            <table:delRow url="${ctx}/cms/cmsRecommend/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRecommend:import">
                            <table:importExcel url="${ctx}/cms/cmsRecommend/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRecommend:export">
                            <table:exportExcel url="${ctx}/cms/cmsRecommend/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>副标题</th>
                    <th>是否首页</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsRecommend">
                    <tr>
                        <td><input type="checkbox" id="${cmsRecommend.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看影片推荐', '${ctx}/cms/cmsRecommend/view?id=${cmsRecommend.id}','90%', '90%')">
                                ${cmsRecommend.name}
                        </a></td>
                        <td>
                                ${cmsRecommend.programId.name}
                        </td>
                        <td>
                                ${cmsRecommend.categoryId.name}
                        </td>
                        <td>
                                ${cmsRecommend.subhead}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsRecommend.isindex, 'general_is_index', '')}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsRecommend.status,'general_status','')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsRecommend.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsRecommend.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsRecommend:view">
                                <a href="#"
                                   onclick="openDialogView('查看影片推荐', '${ctx}/cms/cmsRecommend/view?id=${cmsRecommend.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsRecommend:edit">
                                <a href="#"
                                   onclick="openDialog('修改影片推荐', '${ctx}/cms/cmsRecommend/form?id=${cmsRecommend.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsRecommend:del">
                                <a href="${ctx}/cms/cmsRecommend/delete?id=${cmsRecommend.id}"
                                   onclick="return confirmx('确认要删除该影片推荐吗？', this.href)" class="btn btn-danger btn-xs"><i
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
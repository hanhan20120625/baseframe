<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>视频内容地区管理</title>
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
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            if (isSearch == "true") {
                url = "${ctx}/cms/cmsRegion/changeSort?id=" + id + "&type=" + type + "&name=" + name + "&status=" + status;
            }else{
                url = "${ctx}/cms/cmsRegion/changeSort?id=" + id + "&type=" + type;
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
            <h5>视频内容地区列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsRegion" type="com.msframe.modules.cms.entity.CmsRegion"--%>
                    <form:form id="searchForm" modelAttribute="cmsRegion" action="${ctx}/cms/cmsRegion/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">地区/国家名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="128"
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
                        <shiro:hasPermission name="cms:cmsRegion:add">
                            <table:addRow url="${ctx}/cms/cmsRegion/form" title="视频内容地区"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRegion:edit">
                            <table:editRow url="${ctx}/cms/cmsRegion/form" title="视频内容地区"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRegion:del">
                            <table:delRow url="${ctx}/cms/cmsRegion/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRegion:import">
                            <table:importExcel url="${ctx}/cms/cmsRegion/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsRegion:export">
                            <table:exportExcel url="${ctx}/cms/cmsRegion/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>地区/国家</th>
                    <th>描述</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsRegion">
                    <tr>
                        <td><input type="checkbox" id="${cmsRegion.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看视频内容地区', '${ctx}/cms/cmsRegion/view?id=${cmsRegion.id}','90%', '90%')">
                                ${cmsRegion.name}
                        </a></td>
                        <td>
                                ${cmsRegion.description}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsRegion.status,'general_status','')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsRegion.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsRegion.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsRegion:view">
                                <a href="#"
                                   onclick="openDialogView('查看视频内容地区', '${ctx}/cms/cmsRegion/view?id=${cmsRegion.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsRegion:edit">
                                <a href="#"
                                   onclick="openDialog('修改视频内容地区', '${ctx}/cms/cmsRegion/form?id=${cmsRegion.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsRegion:del">
                                <a href="${ctx}/cms/cmsRegion/delete?id=${cmsRegion.id}"
                                   onclick="return confirmx('确认要删除该视频内容地区吗？', this.href)" class="btn btn-danger btn-xs"><i
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>演员信息管理</title>
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
            var hometown = $("#hometown").val();
            var education = $("#education").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            if(isSearch=="true"){
                url = "${ctx}/cms/cmsCast/changeSort?id=" + id + "&type=" + type + "&name=" + name + "&hometown=" + hometown +
                    "&education=" + education + "&status=" + status;
            }else{
                url = "${ctx}/cms/cmsCast/changeSort?id=" + id + "&type=" + type;
            }
            console.log(url);
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
            <h5>演员信息列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsCast" type="com.msframe.modules.cms.entity.CmsCast"--%>
                    <form:form id="searchForm" modelAttribute="cmsCast" action="${ctx}/cms/cmsCast/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}">
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">姓名</span>
                                <form:input path="name" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">家乡</span>
                                <form:input path="hometown" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">教育</span>
                                <form:input path="education" htmlEscape="false" maxlength="64"
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
                        <shiro:hasPermission name="cms:cmsCast:add">
                            <table:addRow url="${ctx}/cms/cmsCast/form" title="演员信息" width="90%"
                                          height="90%"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsCast:edit">
                            <table:editRow url="${ctx}/cms/cmsCast/form" title="演员信息" width="90%" height="90%"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsCast:del">
                            <table:delRow url="${ctx}/cms/cmsCast/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsCast:import">
                            <table:importExcel url="${ctx}/cms/cmsCast/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsCast:export">
                            <table:exportExcel url="${ctx}/cms/cmsCast/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>姓名</th>
                    <th>显示名称</th>
                    <th>性别</th>
                    <th>生日</th>
                    <th>家乡</th>
                    <th>教育</th>
                    <th>身高</th>
                    <th>体重</th>
                    <th>血型</th>
                    <th>婚姻状况</th>
                    <th>状态</th>
                    <th>排序操作</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsCast">
                    <tr>
                        <td><input type="checkbox" id="${cmsCast.id}" class="i-checks"></td>
                        <td><a href="javaScript:void(0)"
                               onclick="openDialogView('查看演员信息', '${ctx}/cms/cmsCast/view?id=${cmsCast.id}','90%', '90%')">
                                ${cmsCast.name}
                        </a></td>
                        <td>
                                ${cmsCast.personDisplayName}
                        </td>
                        <td>
                                ${fns:getDictLabels(cmsCast.sex,'general_sex','')}
                        </td>
                        <td>
                            <fmt:formatDate value="${cmsCast.birthday}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                                ${cmsCast.hometown}
                        </td>
                        <td>
                                ${cmsCast.education}
                        </td>
                        <td>
                                ${cmsCast.height}
                        </td>
                        <td>
                                ${cmsCast.weight}
                        </td>
                        <td>
                                ${cmsCast.bloodGroup}
                        </td>
                        <td>
                                ${fns:getDictLabels(cmsCast.marriage,'cms_cast_marriage','')}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsCast.status, 'general_status','')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsCast.id}','up',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsCast.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsCast:view">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('查看演员信息', '${ctx}/cms/cmsCast/view?id=${cmsCast.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsCast:edit">
                                <a href="javaScript:void(0)"
                                   onclick="openDialog('修改演员信息', '${ctx}/cms/cmsCast/form?id=${cmsCast.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsCast:del">
                                <a href="${ctx}/cms/cmsCast/delete?id=${cmsCast.id}"
                                   onclick="return confirmx('确认要删除该演员信息吗？', this.href)" class="btn btn-danger btn-xs"><i
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
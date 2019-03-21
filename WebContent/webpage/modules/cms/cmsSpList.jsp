<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>基本信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            laydate({
                elem: '#compactStarttime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#compactEndtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });

        /**
         * 更改排序
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
            var linkMan = $("#linkMan").val();
            var mobile = $("#mobile").val();
            var compactNumber = $("#compactNumber").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            if(isSearch =="true"){
                url = "${ctx}/cms/cmsSp/changeSort?id=" + id + "&type=" + type + "&name=" + name +
                "&linkMan=" + linkMan + "&mobile=" + mobile + "&compactNumber=" + compactNumber + "&status=" + status;
            }else{
                url = "${ctx}/cms/cmsSp/changeSort?id=" + id + "&type=" + type;
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
            <h5>基本信息列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsSp" type="com.msframe.modules.cms.entity.CmsSp"--%>
                    <form:form id="searchForm" modelAttribute="cmsSp" action="${ctx}/cms/cmsSp/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}">
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="30"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">联系人</span>
                                <form:input path="linkMan" htmlEscape="false" maxlength="30"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">手机号</span>
                                <form:input path="mobile" htmlEscape="false" maxlength="20"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">合同编号</span>
                                <form:input path="compactNumber" htmlEscape="false" maxlength="50"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style" >状态</span>
                                <form:select path="status"  class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('general_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
                        <shiro:hasPermission name="cms:cmsSp:add">
                            <table:addRow url="${ctx}/cms/cmsSp/form" title="基本信息" width="90%"
                                          height="90%"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsSp:edit">
                            <table:editRow url="${ctx}/cms/cmsSp/form" title="基本信息" width="90%" height="90%"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsSp:del">
                            <table:delRow url="${ctx}/cms/cmsSp/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsSp:import">
                            <table:importExcel url="${ctx}/cms/cmsSp/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsSp:export">
                            <table:exportExcel url="${ctx}/cms/cmsSp/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>名称</th>
                    <th>联系人</th>
                    <th>手机号</th>
                    <th>联系电话</th>
                    <th>是否提供下载</th>
                    <th>是否提供高清流</th>
                    <th>公司全称</th>
                    <th>CP类型</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="cmsSp">
                    <tr>
                        <td><input type="checkbox" id="${cmsSp.id}" class="i-checks"></td>
                        <td><a href="javaScript:void(0)"
                               onclick="openDialogView('查看基本信息', '${ctx}/cms/cmsSp/view?id=${cmsSp.id}','90%', '90%')">
                                ${cmsSp.name}
                        </a></td>
                        <td>
                                ${cmsSp.linkMan}
                        </td>
                        <td>
                                ${cmsSp.mobile}
                        </td>
                        <td>
                                ${cmsSp.telephone}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsSp.movieDowned, 'cms_sp_movie_downed', '')}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsSp.supplyStream, 'cms_sp_supply_stream', '')}
                        </td>
                        <td>
                                ${cmsSp.companyName}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsSp.ctype, 'cms_sp_ctype', '')}
                        </td>
                        <td>
                                ${fns:getDictLabel(cmsSp.status,'general_status','')}
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsSp.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${cmsSp.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="cms:cmsSp:view">
                                <a href="javaScript:void(0)"
                                   onclick="openDialogView('查看基本信息', '${ctx}/cms/cmsSp/view?id=${cmsSp.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsSp:edit">
                                <a href="javaScript:void(0)"
                                   onclick="openDialog('修改基本信息', '${ctx}/cms/cmsSp/form?id=${cmsSp.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cms:cmsSp:del">
                                <a href="${ctx}/cms/cmsSp/delete?id=${cmsSp.id}"
                                   onclick="return confirmx('确认要删除该基本信息吗？', this.href)" class="btn btn-danger btn-xs"><i
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>栏目管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/webpage/include/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
            var data = ${fns:toJson(list)}, ids = [], rootIds = [];
            for (var i = 0; i < data.length; i++) {
                ids.push(data[i].id);
            }
            ids = ',' + ids.join(',') + ',';
            for (var i = 0; i < data.length; i++) {
                if (ids.indexOf(',' + data[i].parentId + ',') == -1) {
                    if ((',' + rootIds.join(',') + ',').indexOf(',' + data[i].parentId + ',') == -1) {
                        rootIds.push(data[i].parentId);
                    }
                }
            }
            for (var i = 0; i < rootIds.length; i++) {
                addRow("#treeTableList", tpl, data, rootIds[i], true);
            }
            $("#treeTable").treeTable({expandLevel: 5});
        });

        function addRow(list, tpl, data, pid, root) {
            for (var i = 0; i < data.length; i++) {
                var row = data[i];
                if ((${fns:jsGetVal('row.parentId')}) == pid) {
                    $(list).append(Mustache.render(tpl, {
                        dict: {
                            status: getDictLabel(${fns:toJson(fns:getDictList('general_status'))}, row.status),
                            blank123: 0
                        }, pid: (root ? 0 : pid), row: row
                    }));
                    addRow(list, tpl, data, row.id);
                }
            }
        }

        function refresh() {//刷新

            window.location = "${ctx}/cms/cmsCategory/";
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-title">
            <h5>栏目列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="cmsCategory" type="com.msframe.modules.cms.entity.CmsCategory"--%>
                    <form:form id="searchForm" modelAttribute="cmsCategory" action="${ctx}/cms/cmsCategory/"
                               method="post" class="form-inline">
                        <div class="form-group">

                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">栏目名称</span>
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
                        <shiro:hasPermission name="cms:cmsCategory:add">
                            <table:addRow url="${ctx}/cms/cmsCategory/form" title="栏目"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left"
                                onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新
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

            <table id="treeTable"
                   class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th>栏目名称</th>
                    <th>描述</th>
                    <th>图标</th>
                    <th>海报</th>
                    <th>背景图</th>
                    <th>状态</th>
                    <th>排序</th>
                    <%--<th>状态</th>--%>
                    <shiro:hasPermission name="cms:cmsCategory:edit">
                        <th>操作</th>
                    </shiro:hasPermission>
                </tr>
                </thead>
                <tbody id="treeTableList"></tbody>
            </table>
            <script type="text/template" id="treeTableTpl">
                <tr id="{{row.id}}" pId="{{pid}}">
                    <td><a href="javaScript:void(0)"
                           onclick="openDialogView('查看栏目', '${ctx}/cms/cmsCategory/view?id={{row.id}}','90%', '90%')">
                        {{row.name}}
                    </a></td>
                    <td>
                        {{row.description}}
                    </td>
                    <td>
                        <img src="${crc}{{row.icon}}" class="category-img-style"/>
                    </td>
                    <td>
                        {{row.poster}}
                    </td>
                    <td>
                        {{row.bgImage}}
                    </td>
                    <td>
                        {{dict.status}}
                    </td>
                    <td>
                        {{row.sort}}
                    </td>
                    <td>
                        <shiro:hasPermission name="cms:cmsCategory:view">
                            <a href="javaScript:void(0)"
                               onclick="openDialogView('查看栏目', '${ctx}/cms/cmsCategory/view?id={{row.id}}','90%', '90%')"
                               class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsCategory:edit">
                            <a href="javaScript:void(0)"
                               onclick="openDialog('修改栏目', '${ctx}/cms/cmsCategory/form?id={{row.id}}','90%', '90%')"
                               class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cms:cmsCategory:del">
                            <a href="${ctx}/cms/cmsCategory/delete?id={{row.id}}"
                               onclick="return confirmx('确认要删除该栏目及所有子栏目吗？', this.href)" class="btn btn-danger btn-xs"><i
                                    class="fa fa-trash"></i> 删除</a>
                        </shiro:hasPermission>
                        <%--<shiro:hasPermission name="cms:cmsCategory:add">--%>
                        <%--<a href="javaScript:void(0)"--%>
                        <%--onclick="openDialog('添加下级栏目', '${ctx}/cms/cmsCategory/form?parent.id={{row.id}}','90%', '90%')"--%>
                        <%--class="btn btn-primary btn-xs"><i class="fa fa-plus"></i> 添加下级栏目</a>--%>
                        <%--</shiro:hasPermission>--%>
                    </td>
                </tr>
            </script>
</body>
</html>
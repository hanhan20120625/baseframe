<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>频道信息管理管理</title>
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
            <!--排序时判断当前页面显示是否根据条件筛选了-->
            var name = $("#name").val();
            var channelNumber = $("#channelNumber").val();
            var zmsType = $("#type").val();
            var language = $("#language").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            console.log(isSearch);
            if(isSearch=="true"){
                url = "${ctx}/zms/zmsChannel/changeSort?id=" + id + "&type=" + type + "&name=" + name +
                    "&channelNumber=" + channelNumber + "&zmsType=" + zmsType + "&language=" +
                    language + "&status=" + status + "&isSearch=" + isSearch;
            }else{
                url = "${ctx}/zms/zmsChannel/changeSort?id=" + id + "&type=" + type + "&isSearch=" + isSearch;
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
            <h5>频道信息管理列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="zmsChannel" type="com.msframe.modules.zms.entity.ZmsChannel"--%>
                    <form:form id="searchForm" modelAttribute="zmsChannel" action="${ctx}/zms/zmsChannel/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">频道名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">频道号</span>
                                <form:input path="channelNumber" htmlEscape="false" maxlength="11"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>


                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">频道类别</span>
                                <form:select path="type" class="form-control m-b screen-control ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('zms_channel_type')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">语言</span>
                                <form:input path="language" htmlEscape="false" maxlength="4"
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
                        <shiro:hasPermission name="zms:zmsChannel:add">
                            <table:addRow url="${ctx}/zms/zmsChannel/form" title="频道信息管理"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannel:edit">
                            <table:editRow url="${ctx}/zms/zmsChannel/form" title="频道信息管理"
                                           id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannel:del">
                            <table:delRow url="${ctx}/zms/zmsChannel/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannel:import">
                            <table:importExcel url="${ctx}/zms/zmsChannel/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="zms:zmsChannel:export">
                            <table:exportExcel url="${ctx}/zms/zmsChannel/export"></table:exportExcel><!-- 导出按钮 -->
                        </shiro:hasPermission>
                        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left"
                                onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新
                        </button>

                    </div>
                    <div class="pull-right">
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" type="button"><i
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
                    <th>频道号</th>
                    <th>频道类别</th>
                    <th>子类别</th>
                    <th>台标名称</th>
                    <th>存储时长</th>
                    <th>国家</th>
                    <th>省</th>
                    <th>城市</th>
                    <th>语言</th>
                    <th>状态</th>
                    <th>排序操作</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="zmsChannel">
                    <tr>
                        <td><input type="checkbox" id="${zmsChannel.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看频道信息管理', '${ctx}/zms/zmsChannel/view?id=${zmsChannel.id}','90%', '90%')">
                                ${zmsChannel.name}
                        </a></td>
                        <td>
                                ${zmsChannel.channelNumber}
                        </td>
                        <td>
                                ${fns:getDictLabel(zmsChannel.type, 'zms_channel_type', '')}
                        </td>
                        <td>
                                ${fns:getDictLabel(zmsChannel.subtype, 'zms_channel_subtype', '')}
                        </td>
                        <td>
                                ${zmsChannel.callSign}
                        </td>
                        <td>
                                ${zmsChannel.storageDuration}
                        </td>
                        <td>
                                ${zmsChannel.country}
                        </td>
                        <td>
                                ${zmsChannel.province}
                        </td>
                        <td>
                                ${zmsChannel.city}
                        </td>
                        <td>
                                ${zmsChannel.language}
                        </td>
                        <td>
                                ${fns:getDictLabel(zmsChannel.status,'general_status','')}
                        </td>
                        <td>
                            <a href="#" onclick="changeSort('${zmsChannel.id}','up',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="#" onclick="changeSort('${zmsChannel.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="zms:zmsChannel:view">
                                <a href="#"
                                   onclick="openDialogView('查看频道信息管理', '${ctx}/zms/zmsChannel/view?id=${zmsChannel.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsChannel:edit">
                                <a href="#"
                                   onclick="openDialog('修改频道信息管理', '${ctx}/zms/zmsChannel/form?id=${zmsChannel.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="zms:zmsChannel:del">
                                <a href="${ctx}/zms/zmsChannel/delete?id=${zmsChannel.id}"
                                   onclick="return confirmx('确认要删除该频道信息管理吗？', this.href)" class="btn btn-danger btn-xs"><i
                                        class="fa fa-trash"></i> 删除</a>
                            </shiro:hasPermission>
                            <a href="#"
                               onclick="openDialogView('查看节目单','${ctx}/zms/zmsChannelSchedule?channelId=${zmsChannel.id}','90%','90%')"
                               class="btn btn-warning btn-xs"><i
                                    class="fa fa-list-alt icon-white"></i> 节目单管理</a>
                            <shiro:hasPermission name="zms:zmsChannelStream:list">
                                <a href="#"
                                   onclick="openDialogView('查看节目流', '${ctx}/zms/zmsChannelStream?channelId=${zmsChannel.id}','90%', '90%')"
                                   class="btn btn-primary btn-xs"><i class="fa fa-film icon-white"></i> 节目流管理</a>
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
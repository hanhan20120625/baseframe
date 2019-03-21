<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>产品信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            laydate({
                elem: '#startTimeBegin', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#startTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#expireTimeBegin', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#expireTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#expireTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#reviewTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
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
            var productIdent = $("#productIdent").val();
            var startTimeBegin = $("#startTimeBegin").val();
            var startTimeEnd = $("#startTimeEnd").val();
            var expireTimeBegin = $("#expireTimeBegin").val();
            var expireTimeEnd = $("#expireTimeEnd").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            if(isSearch=="true"){
                url = "${ctx}/bms/bmsProduct/changeSort?id=" + id + "&type=" + type + "&productIdent=" + productIdent + "&status=" + status +
                    "&startTimeBegin=" + startTimeBegin + "&startTimeEnd=" + startTimeEnd + "&expireTimeBegin=" + expireTimeBegin + "&expireTimeEnd=" + expireTimeEnd;
            }else{
                url = "${ctx}/bms/bmsProduct/changeSort?id=" + id + "&type=" + type;
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
            <h5>产品信息列表 </h5>
        </div>

        <div class="ibox-content">
            <sys:message content="${message}"/>

            <!--查询条件-->
            <div class="row">
                <div class="col-sm-12">
                    <%--@elvariable id="bmsProduct" type="com.msframe.modules.cms.entity"--%>
                    <form:form id="searchForm" modelAttribute="bmsProduct" action="${ctx}/bms/bmsProduct/" method="post"
                               class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="isSearch" name="isSearch" type="hidden" value="${isSearch}">
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                          callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">


                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">名称</span>
                                <form:input path="name" htmlEscape="false" maxlength="128"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>

                            <div class="from-screen-box clearfloat">
                                <span class="screen-title-style">产品标识</span>
                                <form:input path="productIdent" htmlEscape="false" maxlength="64"
                                            class=" form-control input-sm screen-input-style"/>
                            </div>

                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">开始时间：</span>
                                <input id="startTimeBegin" name="startTimeBegin" type="text" maxlength="20"
                                       class="laydate-icon form-control layer-date input-sm screen-control" readonly="readonly"
                                       value="<fmt:formatDate value="${bmsProduct.startTimeBegin}" pattern="yyyy-MM-dd"/>"/> -
                                <input id="startTimeEnd" name="startTimeEnd" type="text" maxlength="20"
                                       class="laydate-icon form-control layer-date input-sm screen-control" readonly="readonly"
                                       value="<fmt:formatDate value="${bmsProduct.startTimeEnd}" pattern="yyyy-MM-dd"/>"/>
                            </div>

                            <div class="from-screen-box clearfloat from-screen-btnbox">
                                <span class="screen-title-style">过期时间：</span>
                                <input id="expireTimeBegin" name="expireTimeBegin" type="text" maxlength="20"
                                       class="laydate-icon form-control layer-date input-sm screen-control"  readonly="readonly"
                                       value="<fmt:formatDate value="${bmsProduct.expireTimeBegin}" pattern="yyyy-MM-dd"/>"/> -
                                <input id="expireTimeEnd" name="expireTimeEnd" type="text" maxlength="20"
                                       class="laydate-icon form-control layer-date input-sm screen-control"  readonly="readonly"
                                       value="<fmt:formatDate value="${bmsProduct.expireTimeEnd}" pattern="yyyy-MM-dd"/>"/>
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
                        <shiro:hasPermission name="bms:bmsProduct:add">
                            <table:addRow url="${ctx}/bms/bmsProduct/form" title="产品信息" width="90%"
                                          height="90%"></table:addRow><!-- 增加按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="bms:bmsProduct:edit">
                            <table:editRow url="${ctx}/bms/bmsProduct/form" title="产品信息" width="90%"
                                           height="90%" id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="bms:bmsProduct:del">
                            <table:delRow url="${ctx}/bms/bmsProduct/deleteAll"
                                          id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="bms:bmsProduct:import">
                            <table:importExcel url="${ctx}/bms/bmsProduct/import"></table:importExcel><!-- 导入按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="bms:bmsProduct:export">
                            <table:exportExcel url="${ctx}/bms/bmsProduct/export"></table:exportExcel><!-- 导出按钮 -->
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
                    <th>产品标识</th>
                    <th>产品类型</th>
                    <th>租用有效期</th>
                    <th>计费类别</th>
                    <th>价格</th>
                    <th>使用方式</th>
                    <th>试用时间（天）</th>
                    <th>开始时间</th>
                    <th>过期时间</th>
                    <th>审核时间</th>
                    <th>状态</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="bmsProduct">
                    <tr>
                        <td><input type="checkbox" id="${bmsProduct.id}" class="i-checks"></td>
                        <td><a href="javaScript:void(0)"
                               onclick="openDialogView('查看产品信息', '${ctx}/bms/bmsProduct/view?id=${bmsProduct.id}','90%', '90%')">
                                ${bmsProduct.name}
                        </a></td>
                        <td>
                            ${bmsProduct.productIdent}
                        </td>
                        <td>
                                ${fns:getDictLabel(bmsProduct.productType, 'bms_product_product_type', '')}
                        </td>
                        <td>
                                ${bmsProduct.renderPeriod}
                        </td>
                        <td>
                                ${fns:getDictLabel(bmsProduct.feeType, 'bms_product_fee_type', '')}
                        </td>
                        <td>
                                ${bmsProduct.listPrice}
                        </td>
                        <td>
                                ${fns:getDictLabel(bmsProduct.usageMethod, 'bms_product_usage_method', '')}
                        </td>
                        <td>
                                ${bmsProduct.trialDuration}
                        </td>
                        <td>
                            <fmt:formatDate value="${bmsProduct.startTime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${bmsProduct.expireTime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${bmsProduct.reviewTime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            ${fns:getDictLabel(bmsProduct.status,'general_status' ,'' ) }
                        </td>
                        <td>
                            <a href="javaScript:void(0)" onclick="changeSort('${bmsProduct.id}','up',this);"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
                            <a href="javaScript:void(0)" onclick="changeSort('${bmsProduct.id}','down',this)"
                               class="btn btn-success btn-xs"><i
                                    class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
                        </td>
                        <td>
                            <shiro:hasPermission name="bms:bmsProduct:view">
                                <a href="#"
                                   onclick="openDialogView('查看产品信息', '${ctx}/bms/bmsProduct/view?id=${bmsProduct.id}','90%', '90%')"
                                   class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i> 查看</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="bms:bmsProduct:edit">
                                <a href="#"
                                   onclick="openDialog('修改产品信息', '${ctx}/bms/bmsProduct/form?id=${bmsProduct.id}','90%', '90%')"
                                   class="btn btn-success btn-xs"><i class="fa fa-edit"></i> 修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="bms:bmsProduct:del">
                                <a href="${ctx}/bms/bmsProduct/delete?id=${bmsProduct.id}"
                                   onclick="return confirmx('确认要删除该产品信息吗？', this.href)" class="btn btn-danger btn-xs"><i
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
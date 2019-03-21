<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>频道推荐管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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

            var channelId = $("#channelIdId").val();
            var categoryId = $("#categoryIdId").val();
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
			if(isSearch=="true"){
                url = "${ctx}/zms/zmsChannelRecommed/changeSort?id=" + id + "&type=" + type + "&channelId=" + channelId +
                    "&categoryId=" + categoryId + "&status=" + status;
			}else{
			    url = "${ctx}/zms/zmsChannelRecommed/changeSort?id=" + id + "&type=" + type
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
		<h5>频道推荐列表 </h5>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="zmsChannelRecommed" action="${ctx}/zms/zmsChannelRecommed/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="isSearch" name="isSearch" type="hidden" value="${isSearch}">
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			
			
					<div class="from-screen-box from-screen-btnbox clearfloat">
						<span class="screen-title-style" >频道编号</span>
						<sys:gridselect url="${ctx}/zms/zmsChannelRecommed/selectchannelId" id="channelId" name="channelId"  value="${zmsChannelRecommed.channelId.id}"  title="选择频道编号" labelName="channelId.name"
							labelValue="${zmsChannelRecommed.channelId.name}" cssClass="form-control required screen-control" fieldLabels="频道" fieldKeys="name" searchLabel="频道" searchKey="name" linkage="false"></sys:gridselect>
					</div>
				
			
					<div class="from-screen-box from-screen-btnbox clearfloat">
						<span class="screen-title-style" >隶属分类</span>
						<sys:gridselect url="${ctx}/zms/zmsChannelRecommed/selectcategoryId" id="categoryId" name="categoryId"  value="${zmsChannelRecommed.categoryId.id}"  title="选择隶属分类" labelName="categoryId.name"
							labelValue="${zmsChannelRecommed.categoryId.name}" cssClass="form-control required screen-control" fieldLabels="名称" fieldKeys="name" searchLabel="名称" searchKey="name" linkage="false"></sys:gridselect>
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
			<shiro:hasPermission name="zms:zmsChannelRecommed:add">
				<table:addRow url="${ctx}/zms/zmsChannelRecommed/form" title="频道推荐"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelRecommed:edit">
			    <table:editRow url="${ctx}/zms/zmsChannelRecommed/form" title="频道推荐" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelRecommed:del">
				<table:delRow url="${ctx}/zms/zmsChannelRecommed/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelRecommed:import">
				<table:importExcel url="${ctx}/zms/zmsChannelRecommed/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelRecommed:export">
	       		<table:exportExcel url="${ctx}/zms/zmsChannelRecommed/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th>频道编号</th>
				<th>隶属分类</th>
				<th>状态</th>
				<th>排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zmsChannelRecommed">
			<tr>
				<td> <input type="checkbox" id="${zmsChannelRecommed.id}" class="i-checks"></td>
				<td><a  href="javaScript:void(0)" onclick="openDialogView('查看频道推荐', '${ctx}/zms/zmsChannelRecommed/view?id=${zmsChannelRecommed.id}','90%', '90%')">
					${zmsChannelRecommed.channelId.name}
				</a></td>
				<td>
					${zmsChannelRecommed.categoryId.name}
				</td>
				<td>
					${fns:getDictLabel(zmsChannelRecommed.status, 'general_status', '')}
				</td>
				<td>
					<a href="javaScript:void(0)" onclick="changeSort('${zmsChannelRecommed.id}','up',this);"
					   class="btn btn-success btn-xs"><i
							class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
					<a href="javaScript:void(0)" onclick="changeSort('${zmsChannelRecommed.id}','down',this)"
					   class="btn btn-success btn-xs"><i
							class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
				</td>
				<td>
					<shiro:hasPermission name="zms:zmsChannelRecommed:view">
						<a href="javaScript:void(0)" onclick="openDialogView('查看频道推荐', '${ctx}/zms/zmsChannelRecommed/view?id=${zmsChannelRecommed.id}','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="zms:zmsChannelRecommed:edit">
    					<a href="javaScript:void(0)" onclick="openDialog('修改频道推荐', '${ctx}/zms/zmsChannelRecommed/form?id=${zmsChannelRecommed.id}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="zms:zmsChannelRecommed:del">
						<a href="${ctx}/zms/zmsChannelRecommed/delete?id=${zmsChannelRecommed.id}" onclick="return confirmx('确认要删除该频道推荐吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
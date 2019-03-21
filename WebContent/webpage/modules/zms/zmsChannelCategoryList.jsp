<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>直播频道类别管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>直播频道类别列表 </h5>
<!--		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="javaScript:void(0)">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="javaScript:void(0)">选项1</a>
				</li>
				<li><a href="javaScript:void(0)">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>-->
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="zmsChannelCategory" action="${ctx}/zms/zmsChannelCategory/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >频道编号</span>
						<form:input path="channelId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >类别编号</span>
						<form:input path="categoryId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
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
			<shiro:hasPermission name="zms:zmsChannelCategory:add">
				<table:addRow url="${ctx}/zms/zmsChannelCategory/form" title="直播频道类别"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelCategory:edit">
			    <table:editRow url="${ctx}/zms/zmsChannelCategory/form" title="直播频道类别" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelCategory:del">
				<table:delRow url="${ctx}/zms/zmsChannelCategory/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelCategory:import">
				<table:importExcel url="${ctx}/zms/zmsChannelCategory/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsChannelCategory:export">
	       		<table:exportExcel url="${ctx}/zms/zmsChannelCategory/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column channelId">频道编号</th>
				<th  class="sort-column categoryId">类别编号</th>
				<th  class="sort-column status">状态</th>
				<th  class="sort-column sort">排序</th>
				<th  class="sort-column updateDate">最后修改时间</th>
				<th  class="sort-column remarks">附件字段</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zmsChannelCategory">
			<tr>
				<td> <input type="checkbox" id="${zmsChannelCategory.id}" class="i-checks"></td>
				<td><a  href="javaScript:void(0)" onclick="openDialogView('查看直播频道类别', '${ctx}/zms/zmsChannelCategory/view?id=${zmsChannelCategory.id}','90%', '90%')">
					${zmsChannelCategory.channelId}
				</a></td>
				<td>
					${zmsChannelCategory.categoryId}
				</td>
				<td>
					${fns:getDictLabel(zmsChannelCategory.status, 'general_status', '')}
				</td>
				<td>
					${zmsChannelCategory.sort}
				</td>
				<td>
					<fmt:formatDate value="${zmsChannelCategory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${zmsChannelCategory.remarks}
				</td>
				<td>
					<shiro:hasPermission name="zms:zmsChannelCategory:view">
						<a href="javaScript:void(0)" onclick="openDialogView('查看直播频道类别', '${ctx}/zms/zmsChannelCategory/view?id=${zmsChannelCategory.id}','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="zms:zmsChannelCategory:edit">
    					<a href="javaScript:void(0)" onclick="openDialog('修改直播频道类别', '${ctx}/zms/zmsChannelCategory/form?id=${zmsChannelCategory.id}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="zms:zmsChannelCategory:del">
						<a href="${ctx}/zms/zmsChannelCategory/delete?id=${zmsChannelCategory.id}" onclick="return confirmx('确认要删除该直播频道类别吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
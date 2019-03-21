<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>视频区域关联管理</title>
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
		<h5>视频区域关联列表 </h5>
<!--		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
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
	<form:form id="searchForm" modelAttribute="cmsProgramRegion" action="${ctx}/cms/cmsProgramRegion/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >影片编号</span>
						<form:input path="programId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >区域编号</span>
						<form:input path="region" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
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
			<shiro:hasPermission name="cms:cmsProgramRegion:add">
				<table:addRow url="${ctx}/cms/cmsProgramRegion/form" title="视频区域关联"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramRegion:edit">
			    <table:editRow url="${ctx}/cms/cmsProgramRegion/form" title="视频区域关联" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramRegion:del">
				<table:delRow url="${ctx}/cms/cmsProgramRegion/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramRegion:import">
				<table:importExcel url="${ctx}/cms/cmsProgramRegion/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramRegion:export">
	       		<table:exportExcel url="${ctx}/cms/cmsProgramRegion/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column programId">影片编号</th>
				<th  class="sort-column region">区域编号</th>
				<th  class="sort-column status">状态</th>
				<th  class="sort-column sort">排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsProgramRegion">
			<tr>
				<td> <input type="checkbox" id="${cmsProgramRegion.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看视频区域关联', '${ctx}/cms/cmsProgramRegion/view?id=${cmsProgramRegion.id}','90%', '90%')">
					${cmsProgramRegion.programId}
				</a></td>
				<td>
					${cmsProgramRegion.region}
				</td>
				<td>
					${fns:getDictLabel(cmsProgramRegion.status, 'general_status', '')}
				</td>
				<td>
					${cmsProgramRegion.sort}
				</td>
				<td>
					<shiro:hasPermission name="cms:cmsProgramRegion:view">
						<a href="#" onclick="openDialogView('查看视频区域关联', '${ctx}/cms/cmsProgramRegion/view?id=${cmsProgramRegion.id}','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:cmsProgramRegion:edit">
    					<a href="#" onclick="openDialog('修改视频区域关联', '${ctx}/cms/cmsProgramRegion/form?id=${cmsProgramRegion.id}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="cms:cmsProgramRegion:del">
						<a href="${ctx}/cms/cmsProgramRegion/delete?id=${cmsProgramRegion.id}" onclick="return confirmx('确认要删除该视频区域关联吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
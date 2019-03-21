<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>影片上上线记录管理</title>
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
		<h5>影片上上线记录列表 </h5>
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
	<form:form id="searchForm" modelAttribute="cmsProgramOnline" action="${ctx}/cms/cmsProgramOnline/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >剧集编号</span>
						<form:input path="movieId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >影片编号</span>
						<form:input path="programId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat from-screen-btnbox">
						<span class="screen-title-style" >是否处理通知</span>
						<form:select path="dealed"  class="form-control m-b screen-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('cms_program_online_dealed')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
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
			<shiro:hasPermission name="cms:cmsProgramOnline:add">
				<table:addRow url="${ctx}/cms/cmsProgramOnline/form" title="影片上上线记录"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramOnline:edit">
			    <table:editRow url="${ctx}/cms/cmsProgramOnline/form" title="影片上上线记录" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramOnline:del">
				<table:delRow url="${ctx}/cms/cmsProgramOnline/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramOnline:import">
				<table:importExcel url="${ctx}/cms/cmsProgramOnline/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="cms:cmsProgramOnline:export">
	       		<table:exportExcel url="${ctx}/cms/cmsProgramOnline/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column movieId">剧集编号</th>
				<th  class="sort-column programId">影片编号</th>
				<th  class="sort-column dealed">是否处理通知</th>
				<th  class="sort-column status">状态</th>
				<th  class="sort-column sort">排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsProgramOnline">
			<tr>
				<td> <input type="checkbox" id="${cmsProgramOnline.id}" class="i-checks"></td>
				<td><a  href="javaScript:void(0)" onclick="openDialogView('查看影片上上线记录', '${ctx}/cms/cmsProgramOnline/view?id=${cmsProgramOnline.id}','90%', '90%')">
					${cmsProgramOnline.movieId}
				</a></td>
				<td>
					${cmsProgramOnline.programId}
				</td>
				<td>
					${fns:getDictLabel(cmsProgramOnline.dealed, 'cms_program_online_dealed', '')}
				</td>
				<td>
					${fns:getDictLabel(cmsProgramOnline.status, 'general_status', '')}
				</td>
				<td>
					${cmsProgramOnline.sort}
				</td>
				<td>
					<shiro:hasPermission name="cms:cmsProgramOnline:view">
						<a href="javaScript:void(0)" onclick="openDialogView('查看影片上上线记录', '${ctx}/cms/cmsProgramOnline/view?id=${cmsProgramOnline.id}','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:cmsProgramOnline:edit">
    					<a href="javaScript:void(0)" onclick="openDialog('修改影片上上线记录', '${ctx}/cms/cmsProgramOnline/form?id=${cmsProgramOnline.id}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="cms:cmsProgramOnline:del">
						<a href="${ctx}/cms/cmsProgramOnline/delete?id=${cmsProgramOnline.id}" onclick="return confirmx('确认要删除该影片上上线记录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
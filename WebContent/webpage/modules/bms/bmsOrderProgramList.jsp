<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>产品内容管理</title>
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
		<h5>产品内容列表 </h5>
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
	<form:form id="searchForm" modelAttribute="bmsOrderProgram" action="${ctx}/bms/bmsOrderProgram/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >产品编号</span>
						<form:input path="productId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box from-screen-btnbox clearfloat">
						<span class="screen-title-style" >用户编号</span>
						<sys:treeselect id="user" name="user.id" value="${bmsOrderProgram.user.id}" labelName="user.name" labelValue="${bmsOrderProgram.user.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm screen-control" allowClear="true" notAllowSelectParent="true"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >影片编号</span>
						<form:input path="programId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >订单编号</span>
						<form:input path="orderId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >开始时间</span>
						<form:input path="startTime" htmlEscape="false" maxlength="16"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >edntime</span>
						<form:input path="endTime" htmlEscape="false" maxlength="4"  class=" form-control input-sm screen-input-style"/>
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
			<shiro:hasPermission name="bms:bmsOrderProgram:add">
				<table:addRow url="${ctx}/bms/bmsOrderProgram/form" title="产品内容"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrderProgram:edit">
			    <table:editRow url="${ctx}/bms/bmsOrderProgram/form" title="产品内容" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrderProgram:del">
				<table:delRow url="${ctx}/bms/bmsOrderProgram/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrderProgram:import">
				<table:importExcel url="${ctx}/bms/bmsOrderProgram/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrderProgram:export">
	       		<table:exportExcel url="${ctx}/bms/bmsOrderProgram/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column productId">产品编号</th>
				<th  class="sort-column user.name">用户编号</th>
				<th  class="sort-column programId">影片编号</th>
				<th  class="sort-column orderId">订单编号</th>
				<th  class="sort-column startTime">开始时间</th>
				<th  class="sort-column endTime">edntime</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bmsOrderProgram">
			<tr>
				<td> <input type="checkbox" id="${bmsOrderProgram.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看产品内容', '${ctx}/bms/bmsOrderProgram/view?id=${bmsOrderProgram.id}','90%', '90%')">
					${bmsOrderProgram.productId}
				</a></td>
				<td>
					${bmsOrderProgram.user.name}
				</td>
				<td>
					${bmsOrderProgram.programId}
				</td>
				<td>
					${bmsOrderProgram.orderId}
				</td>
				<td>
					${bmsOrderProgram.startTime}
				</td>
				<td>
					${bmsOrderProgram.endTime}
				</td>
				<td>
					<shiro:hasPermission name="bms:bmsOrderProgram:view">
						<a href="#" onclick="openDialogView('查看产品内容', '${ctx}/bms/bmsOrderProgram/view?id=${bmsOrderProgram.id}','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="bms:bmsOrderProgram:edit">
    					<a href="#" onclick="openDialog('修改产品内容', '${ctx}/bms/bmsOrderProgram/form?id=${bmsOrderProgram.id}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="bms:bmsOrderProgram:del">
						<a href="${ctx}/bms/bmsOrderProgram/delete?id=${bmsOrderProgram.id}" onclick="return confirmx('确认要删除该产品内容吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户订单产品信息管理</title>
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
		<h5>用户订单产品信息列表 </h5>
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
	<form:form id="searchForm" modelAttribute="bmsOrder" action="${ctx}/bms/bmsOrder/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			
			
					<div class="from-screen-box from-screen-btnbox clearfloat">
						<span class="screen-title-style" >用户编号</span>
						<sys:treeselect id="user" name="user.id" value="${bmsOrder.user.id}" labelName="user.name" labelValue="${bmsOrder.user.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm screen-control" allowClear="true" notAllowSelectParent="true"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >订单编号</span>
						<form:input path="orderNumber" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >用户名</span>
						<form:input path="username" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >产品编号</span>
						<form:input path="productId" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
					</div>
				
			
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >产品名称</span>
						<form:input path="productName" htmlEscape="false" maxlength="64"  class=" form-control input-sm screen-input-style"/>
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
			<shiro:hasPermission name="bms:bmsOrder:add">
				<table:addRow url="${ctx}/bms/bmsOrder/form" title="用户订单产品信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrder:edit">
			    <table:editRow url="${ctx}/bms/bmsOrder/form" title="用户订单产品信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrder:del">
				<table:delRow url="${ctx}/bms/bmsOrder/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrder:import">
				<table:importExcel url="${ctx}/bms/bmsOrder/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="bms:bmsOrder:export">
	       		<table:exportExcel url="${ctx}/bms/bmsOrder/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th>用户编号</th>
				<th>用户名</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>产品价格</th>
				<th>是否支付</th>
				<th>支付类型</th>
				<th>支付订单号</th>
				<th>支付时间</th>
				<th>支付结果</th>
				<th>自动续订</th>
				<th>滚账状态</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bmsOrder">
			<tr>
				<td> <input type="checkbox" id="${bmsOrder.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用户订单产品信息', '${ctx}/bms/bmsOrder/view?id=${bmsOrder.id}','90%', '90%')">
					${bmsOrder.user.name}
				</a></td>
				<td>
					${bmsOrder.username}
				</td>
				<td>
					${bmsOrder.productId}
				</td>
				<td>
					${bmsOrder.productName}
				</td>
				<td>
					${bmsOrder.price}
				</td>
				<td>
					${fns:getDictLabel(bmsOrder.ispay, 'bms_order_ispay', '')}
				</td>
				<td>
					${bmsOrder.payType}
				</td>
				<td>
					${bmsOrder.payOrderNumber}
				</td>
				<td>
					<fmt:formatDate value="${bmsOrder.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bmsOrder.result}
				</td>
				<td>
					${fns:getDictLabel(bmsOrder.rollCharge, 'bms_order_roll_charge', '')}
				</td>
				<td>
					${fns:getDictLabel(bmsOrder.lastAccountStatus, 'bms_order_last_account_status', '')}
				</td>
				<td>
					${fns:getDictLabel(bmsOrder.status, 'general_status', '')}
				</td>
				<td>
					<shiro:hasPermission name="bms:bmsOrder:view">
						<a href="#" onclick="openDialogView('查看用户订单产品信息', '${ctx}/bms/bmsOrder/view?id=${bmsOrder.id}','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="bms:bmsOrder:edit">
    					<a href="#" onclick="openDialog('修改用户订单产品信息', '${ctx}/bms/bmsOrder/form?id=${bmsOrder.id}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="bms:bmsOrder:del">
						<a href="${ctx}/bms/bmsOrder/delete?id=${bmsOrder.id}" onclick="return confirmx('确认要删除该用户订单产品信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
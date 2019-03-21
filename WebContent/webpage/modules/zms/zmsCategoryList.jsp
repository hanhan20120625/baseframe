<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>直播类别管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							syncState: getDictLabel(${fns:toJson(fns:getDictList('general_sync_state'))}, row.syncState),
							status: getDictLabel(${fns:toJson(fns:getDictList('general_status'))}, row.status),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		
		function refresh(){//刷新
			
			window.location="${ctx}/zms/zmsCategory/";
		}


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
            var status = $("#status").val();
            var isSearch = $("#isSearch").val();
            console.log(isSearch);
            if(isSearch=="true"){
                url = "${ctx}/zms/zmsCategory/changeSort?id=" + id + "&type=" + type + "&name=" + name + "&status=" + status;
			}else{
                url = "${ctx}/zms/zmsCategory/changeSort?id=" + id + "&type=" + type;
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
			<h5>直播类别列表 </h5>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>

	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="zmsCategory" action="${ctx}/zms/zmsCategory/" method="post" class="form-inline">
		<input id="isSearch" name="isSearch" type="hidden" value="${isSearch}">
		<div class="form-group">
					<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >名称</span>
						<form:input path="name" htmlEscape="false" maxlength="30"  class="form-control input-sm screen-input-style"/>
					</div>
					<div class="from-screen-box clearfloat from-screen-btnbox">
						<span class="screen-title-style">状态</span>
						<form:select path="status" class="form-control m-b screen-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('general_status')}" itemLabel="label"
								  itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<%--<div class="from-screen-box clearfloat">
						<span class="screen-title-style" >所有父级编号</span>
						<form:input path="parentIds" htmlEscape="false" maxlength="2000"  class=" form-control input-sm screen-input-style"/>
					</div>--%>
		</div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="zms:zmsCategory:add">
				<table:addRow url="${ctx}/zms/zmsCategory/form" title="直播类别"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<table id="treeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th>名称</th>
				<th>描述</th>
				<th>同步状态</th>
				<th>状态</th>
				<th>排序</th>
				<shiro:hasPermission name="zms:zmsCategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a  href="javaScript:void(0)" onclick="openDialogView('查看直播类别', '${ctx}/zms/zmsCategory/view?id={{row.id}}','90%', '90%')">
				{{row.name}}
			</a></td>
			<td>
				{{row.description}}
			</td>
			<td>
				{{dict.syncState}}
			</td>
			<td>
				{{dict.status}}
			</td>
			<td>
				<a href="javaScript:void(0)" onclick="changeSort('{{row.id}}','up',this);"
				   class="btn btn-success btn-xs"><i
						class="fa fa-arrow-circle-o-up icon-white"></i> 上移</a>
				<a href="javaScript:void(0)" onclick="changeSort('{{row.id}}','down',this)"
				   class="btn btn-success btn-xs"><i
						class="fa fa-arrow-circle-o-down icon-white"></i> 下移</a>
			</td>
			<td>
			<shiro:hasPermission name="zms:zmsCategory:view">
				<a href="javaScript:void(0)" onclick="openDialogView('查看直播类别', '${ctx}/zms/zmsCategory/view?id={{row.id}}','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>  查看</a>
				</shiro:hasPermission>
			<shiro:hasPermission name="zms:zmsCategory:edit">
   				<a href="javaScript:void(0)" onclick="openDialog('修改直播类别', '${ctx}/zms/zmsCategory/form?id={{row.id}}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
   			</shiro:hasPermission>
   			<shiro:hasPermission name="zms:zmsCategory:del">
				<a href="${ctx}/zms/zmsCategory/delete?id={{row.id}}" onclick="return confirmx('确认要删除该直播类别及所有子直播类别吗？', this.href)" class="btn btn-danger btn-xs" ><i class="fa fa-trash"></i> 删除</a>
			</shiro:hasPermission>
   			<%--<shiro:hasPermission name="zms:zmsCategory:add">--%>
				<%--<a href="javaScript:void(0)" onclick="openDialog('添加下级直播类别', '${ctx}/zms/zmsCategory/form?parent.id={{row.id}}','90%', '90%')" class="btn btn-primary btn-xs" ><i class="fa fa-plus"></i> 添加下级直播类别</a>--%>
			<%--</shiro:hasPermission>--%>
			</td>
		</tr>
	</script>
</body>
</html>
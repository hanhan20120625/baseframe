<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			$("#name").focus();
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">上级机构</label></td>
		         <td class="width-35"><sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="机构" url="/sys/office/treeData" extId="${office.id}"  cssClass="form-control" allowClear="${office.currentUser.admin}"/></td>
		         <td  class="width-15"  class="active"><label class="pull-right"><font color="red">*</font>归属区域</label></td>
		         <td class="width-35"><sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="form-control required"/></td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构名称</label></td>
		         <td class="width-35">
					 ${office.name}
				 </td>
		         <td  class="width-15"  class="active"><label class="pull-right">机构编码</label></td>
		         <td class="width-35">
					 ${office.code}
				 </td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right">机构类型</label></td>
		         <td class="width-35">
						 ${fns:getDictLabel(office.type, 'sys_office_type', '')}
				 </td>
		         <td  class="width-15"  class="active"><label class="pull-right">机构级别</label></td>
		         <td class="width-35">
						 ${fns:getDictLabel(office.grade, 'sys_office_grade', '')}
				 </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">是否可用</label></td>
		         <td class="width-35">
						 ${fns:getDictLabel(office.useable, 'yes_no', '')}
					<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span></td>
		         <td class="width-15"  class="active"><label class="pull-right">主负责人</label></td>
		         <td class="width-35"><sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">副负责人</label></td>
		         <td class="width-35"><sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/></td>
		         <td class="width-15"  class="active"><label class="pull-right">联系地址</label></td>
		         <td class="width-35">
					 ${office.address}
				 </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">邮政编码</label></td>
		         <td class="width-35">
					 ${office.zipCode}
				 </td>
		         <td  class="width-15" class="active"><label class="pull-right">负责人</label></td>
		         <td class="width-35">
					 ${office.master}
				 </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">电话</label></td>
		         <td class="width-35">
					 ${office.phone}
				 </td>
		         <td  class="width-15"  class="active"><label class="pull-right">传真</label></td>
		         <td class="width-35">
					 ${office.fax}
				 </td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">邮箱</label></td>
		         <td class="width-35">
					 ${office.email}
				 </td>
		         <td  class="width-15" class="active"><label class="pull-right">备注</label></td>
		         <td class="width-35">
					 ${office.remarks}
				 </td>
		      </tr>
	      </tbody>
	      </table>
	</form:form>
</body>
</html>
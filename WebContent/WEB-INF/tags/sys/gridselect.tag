<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="fieldLabels" type="java.lang.String" required="true" description="表格Th里显示的名字"%>
<%@ attribute name="fieldKeys" type="java.lang.String" required="true" description="表格Td里显示的值"%>
<%@ attribute name="searchLabel" type="java.lang.String" required="true" description="表格Td里显示的值"%>
<%@ attribute name="searchKey" type="java.lang.String" required="true" description="表格Td里显示的值"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="数据地址"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="linkage" type="java.lang.Boolean" required="true" description="是否联动,如果联动则在第二级处设为true，当前仅支持二级联动"%>
<%@ attribute name="linkageId" type="java.lang.String" required="false" description="联动id，如果联动则在第二级处设为一级的id"%>
<%@ attribute name="prompt" type="java.lang.String" required="false" description="联动时上一级没有选择时，提示的内容"%>
<script type="text/javascript">
function searchGrid${id}(){
    var linkageId = "";
    if(${linkage}){
        linkageId = $("#${linkageId}Id").val();
        if(linkageId=="" || linkageId==null || linkageId == undefined){
            layer.msg("${prompt}");
            return;
        }
	}
	top.layer.open({
	    type: 2,  
	    area: ['1000px', '500px'],
	    title:"${title}",
	    name:'friend',
	    content: "${url}?fieldLabels=${fieldLabels}&fieldKeys=${fieldKeys}&url=${url}&searchLabel=${searchLabel}&searchKey=${searchKey}<c:if test='${linkage}'>&linkageId="+linkageId+"</c:if>" ,
	    btn: ['确定', '关闭'],
	    yes: function(index, layero){
	    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	    	 var item = iframeWin.getSelectedItem();

	    	 if(item == "-1"){
		    	 return;
	    	 }
	    	 $("#${id}Id").val(item.split('_item_')[0]);
	    	 $("#${id}Name").val(item.split('_item_')[1]);
			 top.layer.close(index);//关闭对话框。
		  },
		  cancel: function(index){ 
	       }
	}); 
};
</script>

	<input id="${id}Id" name="${name}"  type="hidden" value="${value}" linkage="${linkage}"/>
	<div class="input-group">
		<input id="${id}Name"  name="${labelName }" ${allowInput?'':'readonly="readonly"'}  type="text" value="${labelValue}" data-msg-required="${dataMsgRequired}"
		class="${cssClass}" style="${cssStyle}"/>
       		 <span class="input-group-btn">
	       		 <button type="button" onclick="searchGrid${id}()" id="${id}Button" class="btn <c:if test="${fn:contains(cssClass, 'input-sm')}"> btn-sm </c:if><c:if test="${fn:contains(cssClass, 'input-lg')}"> btn-lg </c:if>  btn-primary ${disabled} ${hideBtn ? 'hide' : ''}"><i class="fa fa-search"></i>
	             </button> 
       		 </span>
       		
    </div>
	 <label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>

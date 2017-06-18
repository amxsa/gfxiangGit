<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<%
	String tempProIds = request.getParameter("tempProIds");
	StringBuffer tempProName=new StringBuffer();
	if(tempProIds!=null){
		List<TProperty> list = TPropertyBiz.queryProperty(tempProIds.split(",")) ;
		if(list!=null){
			for(int i=0;i<list.size();i++){
				tempProName.append(list.get(i).getProName()).append(",");
			}
		}
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>模板添加</title>
</head>
<body style="overflow: hidden;">
<!-- 添加模板 -->
<form action="javascript:void(0)" method="post" id="checkForm">
	<input type="hidden" name="contentType"/>
	<ul class="list02a w50" id="addTempletUl" style="width:798px;font: 12px/1.11 'Microsoft Yahei', Tahoma, Arial, Helvetica, STHeiti;">
		<li class="w100">
			<label>名称：</label>
			<input type="text" name="name" style="width:200px;"/>
		</li>
		<li class="w100" style="display:block;height:483px;">
			<label style="display:block;">内容：</label>
				<div style="width:85%;float:left;height: 100%;">
	 				<script id="container" name="content" type="text/plain">
    			</script>
	 			</div>
		</li>
	</ul>
</form>

<script type="text/javascript">
$(document).ready(function() {
	var ue = UE.getEditor('container',{
		toolbars: [ ['fontfamily', 'fontsize','paragraph','forecolor','backcolor','emotion',
		             'spechars','justifyleft','justifyright','justifycenter','justifyjustify',
		             'insertorderedlist','insertunorderedlist','rowspacingtop','rowspacingbottom', 'imageleft','imageright',
		             'lineheight','touppercase','tolowercase']],
		autoHeightEnabled:false,
		initialFrameHeight:330
	});
});
function addDataImpl(){
	var contentType=art.dialog.data('contentType');
	$('#checkForm input[name="contentType"]').val(contentType);
	var ret=true;
	$.ajax({ 
		url:sysPath+'/templet/add.do',
		async : false,
		type:"post", 
		dataType:"json",
		data: $('#checkForm').serialize(),
		success:function(data){
			alert(data.msg);
			if(data.state!='S'){
				ret=false;
			}
		}
	});
	return ret;
}
</script>
</body>
</html>
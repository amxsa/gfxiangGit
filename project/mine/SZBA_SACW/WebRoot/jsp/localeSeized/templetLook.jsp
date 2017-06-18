<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>模板</title>
</head>
<body>
<ul class="list02a w50" style="overflow: auto;" id="zcyUl">
	<li class="w100">
		<label>名称：</label>
		<span id="name"></span>
	</li>
	<li class="w100">
		<label>内容：</label>
		<div id="content"></div>
	</li>
</ul>
<script type="text/javascript">
$(document).ready(function() {
	var localUrl=sysPath+'/client/clientRequestMap.do';
	var clientUrl='/templet/queryDetail.do';
	var params={
		'id':art.dialog.data('temId')
	}
	var cb=function(data){
		if(data.state=='Y'){
			var templet=data.templet;
			$('#name').html(templet.name);
			$('#content').html(templet.content);
		}
	}
	clientAjaxPost(localUrl,clientUrl,params,cb);
});
</script>
</body>
</html>
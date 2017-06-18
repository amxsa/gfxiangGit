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
	<input type="hidden" name="id" id="id" value="${mapping:generateUUID()}"/>
	<li class="w100">
		<label><span class="need">*</span>名称：</label>
		<input name="name" type="text" id="name"/>
		<div></div>
	</li>
	<li class="w100">
		<label><span class="need">*</span>内容：</label>
		<textarea name="result" id="content" cols="30" rows="10" style="width:380px;"></textarea>
		<div></div>
	</li>
</ul>
<script type="text/javascript">
function saveTemplet(){
	var ret=verifyForm.inputNotNull($('#name'), '名称',null, null);
	if(!ret){
		return ret;
	}
	var ret=verifyForm.inputNotNull($('#content'), '内容',null, {'margin-left':'90px'});
	if(!ret){
		return ret;
	}
	var localUrl=sysPath+'/client/clientRequestMap.do';
	var clientUrl='/templet/add.do';
	var params={
		'id':$('#id').val(),
		'name':$('#name').val(),
		'content':$('#content').val(),
		'type':'1',
		'contentType':art.dialog.data('contentType')
	}
	var cb=function(data){
		if(data.state=='Y'){
			ret=true;
		}else{
			ret=false;
		}
	}
	clientAjaxPostSync(localUrl,clientUrl,params,cb);
	return ret;
}
</script>
</body>
</html>
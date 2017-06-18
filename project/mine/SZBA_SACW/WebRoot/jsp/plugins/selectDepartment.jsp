<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.all-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/departmentMgr.js"></script>
<title>选择部门</title>
</head>
<body>
	<input id="checked" type="hidden"/>
	<div style="height:100px;">
		<ul id="departmentTree" class="ztree"></ul>
	</div>
<script type="text/javascript">
var selectDepartment={
	startEvent:function(){
		$.ajax({
			type: 'GET',
			async: false,
			url: sysPath+'/account/loadDepartments.do',
			dataType:"json",
			success: function(data){
				departmentMgr.dataCache=data.data;
				departmentMgr.showTreeData($('#checked').val());
			}
		});
	},
	
	init:function(){
		selectDepartment.startEvent();
	}
}
function initData(){
	selectDepartment.init();
}
function getCheckedData(){
	return departmentMgr.getSelData();
}
</script>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>修改用户</title>
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 修改用户</span></div>
<div class="content">
 <form id="checkForm" action="<%=path%>/account/save.do?from=editAccount" method="post">
 <div class="toptit"><h1 class="h1tit">用户信息</h1></div>
 <ul class="list02a w50">
 	<input type="hidden" name="ystoreroomType" id="ystoreroomType" value="${entity.storeroomType }"/>
   <li><label>保管室编号：</label>${entity.storeroomNum }</li>
   <li><label>保管室名称：</label>${entity.storeroomName }</li>
   <li><label>保管室面积：</label>${entity.storeroomArea }</li>
   <li><label>保管室类型：</label><span id="storeroomType"></span></li>
   <li><label>所属地址：</label>${entity.storeroomAddr }</li>
   <li><label>货架数量：</label>${entity.roomNum }</li>
   <li><label>所属仓库：</label>${entity.storehouseName }</li>
	<li><label></label></li>
 </ul>

 <div class="dbut">
 	<a href="#" onclick="goback();">返回</a>
 </div>
 </form>
</div>
<div id="selectDataDiv" style="display:none;">
	<select name="storeroomType">
		<jsp:include page="/jsp/plugins/storeroomType_options.jsp" />
	</select>
</div>
<script type="text/javascript">
	//返回按钮点击事件
function getParams(){
	var storeroomNum = '<%=RequestUtils.getAttribute(params,"storeroomNumQ",0)%>';
	var storehouseId = '<%=RequestUtils.getAttribute(params,"storehouseIdQ",0)%>';
	var currentIndex = '<%=RequestUtils.getAttribute(params,"currentIndex",0)%>';
	var sizePerPage = '<%=RequestUtils.getAttribute(params,"sizePerPage",0)%>';
	
	var params = "&storeroomNum="+storeroomNum
	+"&storehouseId="+storehouseId+"&currentIndex="+currentIndex+"&sizePerPage="+sizePerPage;
	return params;
}

	
function goback(){
	window.location.href=sysPath+"/storeroom/queryForPage.do?"+ getParams();
}
$(document).ready(function(){
	if($('#ystoreroomType').val()!=''){
		$('#storeroomType').html($('#selectDataDiv select[name="storeroomType"] option[value="'+$('#ystoreroomType').val()+'"]').text());
	}
});
</script>
</body>
</html>
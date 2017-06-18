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
<title>修改货架</title>
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 修改货架</span></div>
<div class="content">
 <form id="checkForm" action="<%=path%>/rack/save.do?from=editRack" method="post">
 <div class="toptit"><h1 class="h1tit">货架信息</h1></div>
 <ul class="list02a w50">
   <li><label>货架编号：</label>${entity.rackNum }</li>
   <li><label>货架高度：</label>${entity.rackHeight }</li>
   <li><label>货架长度：</label>${entity.rackLength }</li>
   <li><label>货架载重：</label>${entity.rackLoad }</li>
   <li><label>所属保管室：</label>${entity.storeroomName}</li>
   <li></li>
 </ul>

 <div class="dbut">
 	<a href="#" onclick="goback();">返回</a>
 </div>
 </form>
</div>
<script type="text/javascript">
function getParams(){
	var rackNum = '<%=RequestUtils.getAttribute(params,"rackNumQ",0)%>';
	var rackHeight = '<%=RequestUtils.getAttribute(params,"rackHeightQ",0)%>';
	var rackLength = '<%=RequestUtils.getAttribute(params,"rackLengthQ",0)%>';
	var rackLoad = '<%=RequestUtils.getAttribute(params,"rackLoadQ",0)%>';
	var storeroomId = '<%=RequestUtils.getAttribute(params,"storeroomIdQ",0)%>';
	var currentIndex = '<%=RequestUtils.getAttribute(params,"currentIndex",0)%>';
	var sizePerPage = '<%=RequestUtils.getAttribute(params,"sizePerPage",0)%>';
	
	var params = "&rackNum="+rackNum+"&rackHeight="+rackHeight+"&rackLength="+rackLength+
	"&rackLoad="+rackLoad+"&storeroomId="+storeroomId+"&currentIndex="+currentIndex+"&sizePerPage="+sizePerPage;
	return params;
}

	
function goback(){
	window.location.href=sysPath+"/rack/queryForPage.do?"+ getParams();
}
</script>
</body>
</html>
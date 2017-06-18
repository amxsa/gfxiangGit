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
 <form id="checkForm" action="<%=path%>/locker/save.do?from=editLocker" method="post">
 <div class="toptit"><h1 class="h1tit">用户信息</h1></div>
 <ul class="list02a w50">
 <li><label>存储柜编号 </label>${entity.lockerNum }</li>       
 <li><label>存储柜面积 </label>${entity.lockerVolume }</li>    
 <li><label>存储柜类型 </label>${entity.lockerType }</li>      
 <li><label>所属货架： </label>${entity.goodsNum }</li>        
 <li><label>清查状态： </label>${entity.inventoryStatus }</li> 
 <li><label>RFID标签： </label>${entity.rfidNum }</li>         
 <li><label>所属货架： </label><c:forEach var="r" items="${rackList }">
				<c:if test="${entity.rackID==r.rackNum }">${r.rackName }</c:if>
				</c:forEach></li>          
<li><label></label></li>                                      
 </ul>

 <div class="dbut">
 	<a href="#" onclick="goback();">返回</a>
 </div>
 </form>
</div>
<script type="text/javascript">
	//返回按钮点击事件
	function goback(){
		var lockerNum = '<%=RequestUtils.getAttribute(params,"lockerNumQ",0)%>';
		var lockerVolume = '<%=RequestUtils.getAttribute(params,"lockerVolumeQ",0)%>';
		var lockerType = '<%=RequestUtils.getAttribute(params,"lockerTypeQ",0)%>';
		var goodsNum = '<%=RequestUtils.getAttribute(params,"goodsNumQ",0)%>';
		var inventoryStatus = '<%=RequestUtils.getAttribute(params,"inventoryStatusQ",0)%>';
		var rfidNum = '<%=RequestUtils.getAttribute(params,"rfidNumQ",0)%>';
		var rackID = '<%=RequestUtils.getAttribute(params,"rackIDQ",0)%>';
		var params = "?lockerNum="+lockerNum+"&lockerVolume="+lockerVolume+"&lockerType="+lockerType+
		"&goodsNum="+goodsNum+"&inventoryStatus="+inventoryStatus+"&rfidNum="+rfidNum+"&rackID="+rackID;
		/* 跳转页面 */
		window.location.href="<%=path%>/locker/queryForPage.do?"+params;
	}
</script>
</body>
</html>
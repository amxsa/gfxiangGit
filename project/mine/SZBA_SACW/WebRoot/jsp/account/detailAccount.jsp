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
   <li><label>账号：</label>${entity.account }</li>
   <li><label>工号：</label>${entity.workno }</li>
   <li><label>姓名：</label>${entity.name }</li>
   <li><label>性别：</label>
	<c:if test="${entity.gender=='M' }">男</c:if>
	<c:if test="${entity.gender=='F' }">女</c:if>
   </li>
   <li><label>状态：</label>
   <c:if test="${entity.status=='Y' }">激活</c:if>
   <c:if test="${entity.status=='N' }">禁用</c:if>
   </li>
   <li><label>角色：</label>
   <c:forEach var="aRecord" items="${roleList}" varStatus="sta">
		${aRecord.name}<c:if test="${!sta.last&&!empty aRecord.name}">,</c:if>
    </c:forEach>
   </li>
   <li><label>手机号码：</label>${entity.mobile }</li>
   <li><label>所属部门：</label>
   <span class="departmentIDs">${entity.departmentID }</span></li>
   <li><label>证件类型：</label>
   <c:if test="${entity.idType=='IDCard' }">身份证</c:if>
   </li>
   <li><label>证件号码：</label>${entity.idNum }</li>
   <li><label>用户功能：</label>
  	<c:forEach items="${iconList}" var="aRecord">
  		<span>${aRecord.name}</span>
  	</c:forEach>
   </li>
	<li><label></label></li>
 </ul>

 <div class="dbut">
 	<a href="#" onclick="goback();")>返回</a>
 </div>
 </form>
</div>
<script type="text/javascript">
	//返回按钮点击事件
	function goback(){
		var account = '<%=RequestUtils.getAttribute(params,"accountQ",0)%>';
		var workno = '<%=RequestUtils.getAttribute(params,"worknoQ",0)%>';
		var status = '<%=RequestUtils.getAttribute(params,"statusQ",0)%>';
		var name = '<%=RequestUtils.getAttribute(params,"nameQ",0)%>';
		var mobile = '<%=RequestUtils.getAttribute(params,"mobileQ",0)%>';
		var gender = '<%=RequestUtils.getAttribute(params,"genderQ",0)%>';
		var departmentID = '<%=RequestUtils.getAttribute(params,"departmentIDQ",0)%>';
		var params = "&account="+account+"&workno="+workno+"&status="+status+"&name="+name+"&mobile="+mobile+"&gender="+gender+"&departmentID="+departmentID;
		window.location.href="<%=path%>/account/queryForPage.do?"+params;
	}
	//部门初始化
	$(document).ready(function() {
		Common.departments.setting.checkall=true;
		Common.departments.method.initDropdown();
		$('.departmentIDs').each(function(){
			$(this).html(Common.departments.method.getName($(this).html()));
		});
	});	
</script>
</body>
</html>
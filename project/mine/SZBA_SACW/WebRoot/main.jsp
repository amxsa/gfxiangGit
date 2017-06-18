<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils"%>
<%@ include file="../../common/client.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link type="text/css" href="css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/json2.js"></script>

<!--[if lte IE 8]> 
<script src="${pageContext.request.contextPath }/js/compatibleJs/IE8.js" type=”text/javascript”></script>
<![endif]-->

</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a> </span>
	</div>
	<div class="content">
		<div class="user_c">
			<div class="pic">
				<img src="images/pic.jpg?v=201508281000" />
			</div>
			<ul class="user">
				<li>欢迎您：<em>${loginForm.NAME}</em> <c:forEach var="aRecord"
						items="${rolesInfo}" varStatus="sta">
		${aRecord.NAME}<c:if test="${!sta.last&&!empty aRecord.NAME}">,</c:if>
					</c:forEach>
				</li>
				<li>您上次登录时间： <i><fmt:formatDate
							value="${lastLogin.LOGIN_TIME}" pattern="yyyy-MM-dd HH:mm:ss" />
				</i>
					<li>IP：<i>${lastLogin.IP_ADDRESS}</i></li> <!-- <li>上次操作：<em>财物登记</em></li> -->
			</ul>
		</div>

		<ul class="nav">
			<c:forEach items="${iconsInfo}" var="aRecord">
				<li><a href="${pageContext.request.contextPath}${aRecord.URL}"
					class="${aRecord.ICON}">${aRecord.NAME}</a></li>
			</c:forEach>
		</ul>

		<div class="list01">
			<h2>待处理信息</h2>
			<ul id="message">

			</ul>
		</div>
		<div class='page'></div>

	</div>
	<script type="text/javascript">
   <%--  function showMessage(){
     var result="";
	 $.ajax({
					type: "POST",
					url: "<%=path%>/message/queryForPage2.do",
					async:true,
					dataType:"json",
					success: function(data){
	                   var listMessages = data.list;
	                   for(var i=0;i<listMessages.length;i++){
	                     result+="<li><a href=<%=path%>/message/queryById.do?id="+listMessages[i].id+" "+"class=new"+">"+listMessages[i].title+"</a><span>【"+listMessages[i].createTimeStr2+"】</span></li>";
	                   }
	                   $("#message").append(result);
	                   page = data.page;
	                   // 生成页码
						Common.method.pages.genPageNumber("searchForm", page.currentIndex, page.sizePerPage, page.totalPage);
					}
				});
  }
  
 $(document).ready(function() {
      showMessage();
  });  --%>
   
   var messageRecord={
	    currentIndex:0,
	    sizePerPage:5,
	
	    showMessage:function(){
		   var localUrl='<%=path%>/message/queryForPage2.do';
		   var params={
			  'currentIndex':messageRecord.currentIndex,
			  'sizePerPage':messageRecord.sizePerPage
		   }
	       var cb=function(data){
		       var result="";
			   var listMessages = data.list;
	           for(var i=0;i<listMessages.length;i++){
	               result+="<li><a href=<%=path%>/message/queryById.do?id="+listMessages[i].id+" "+"class=new"+">"+listMessages[i].title+"</a><span>【"+listMessages[i].createTimeStr2+"】</span></li>";
	           }
	           $("#message").html('');
	           $("#message").append(result); 
	           page = data.page;
	           // 生成页码
			   CommonAjax.method.pages.genPageNumber(messageRecord.currentIndex, messageRecord.sizePerPage, page.totalPage); 

		 }
		 AjaxPost(localUrl,params,cb); 
	  },   
	
	   init:function(){
		  messageRecord.showMessage();
	   }
   }  
   
   $(document).ready(function() {
	    messageRecord.init();
   });
   
 //提供给分页调用
   function comAjaxPage(currentIndex,sizePerPage){
	    messageRecord.currentIndex=currentIndex;
	    messageRecord.sizePerPage=sizePerPage;
	    messageRecord.showMessage();
   }
</script>
</body>
</html>
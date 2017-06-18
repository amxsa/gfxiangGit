<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.cellcom.szba.common.DateTool" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>消息列表</title>
<link type="text/css" href="<%=path%>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 消息列表 </span></div>
 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
<form id="searchForm" action="<%=path%>/message/queryForPage.do" method="post">
 <div class="search">
  <p>
      <label>开始时间</label>
      <input name="condiStartTime" type="text"  value="${condiStartTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
      <label>结束时间</label>
      <input name="condiEndTime" type="text"  value="${condiEndTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>  
  </p>
  <div class="sbut"><input name="" type="submit" value="查询" /></div>
 </div>
 
<div class="content"> 
 <div class="toptit">
	  <h1 class="h1tit">消息列表</h1>
	  <div class="but1"><input name="" type="button" value="批量删除"  onclick="batchDelete();"/></div>
</div>

 <table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
  <tr>
   <th><input type="checkbox" name="all" value="全选" onclick="selectAll('all')"/>全选</th>
    <th>消息编号</th>
	<th>标题</th>
    <th>状态</th>
    <th>操作</th>
  </tr>
  
   <c:forEach var="v" items="${data}" varStatus="sta">
      <tr class="red">
        <td ><input type="checkbox" id="messageId" name="messageIds" value="${v.id }"/></td>
        <td>${sta.count }</td>
	    <td>${v.title }</td>
	     <c:if test="${v.readstatus=='N'}">
           <td>未读</td>
         </c:if>
         <c:if test="${v.readstatus!='N'}">
           <td>已读</td>
         </c:if>
        <td><span class="tdbut"><a href="<%=path%>/message/deleteById.do?id=${v.id }">删除</a><a href="<%=path%>/message/queryById.do?id=${v.id }">查看</a></span></td>
       </tr>
   </c:forEach>
 </table>
   <div class="page"></div>
</div>   
 </form>
</div>
<script type="text/javascript">

$(document).ready(function() {
	// 生成页码
	Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	
});

function selectAll(myName) {
	  var selectOne=document.getElementsByTagName("input");
	  for(var i=0;i<selectOne.length;i++){
		if(selectOne[i].type=="checkbox" && selectOne[i].name != myName){
			selectOne[i].checked = document.getElementsByName(myName)[0].checked;
		 }
	  }
   }


    function batchDelete(){
       var selectOne=document.getElementsByTagName("input");
	   var flag = false;
	   for(var i=0;i<selectOne.length;i++){
		   if(selectOne[i].type=="checkbox" && selectOne[i].name != "all"){
			   flag = selectOne[i].checked;
			   if ( flag ) {
			 	  break;
			 }
		 }
	 }
	  if(flag == false){
	      alert("请选择要删除的消息");
	      return flag;
	   }else{
	     if(confirm("删除消息将无法恢复，是否确认删除？")){
	         var f=document.getElementById('searchForm');
	         f.action="<%=request.getContextPath()%>/message/batchDeleteByIds.do";
	         f.submit();
	     }
	   }
    }
    $(document).ready(function() {
       var message='${message}';
		if(message==""){
		}else{
		  if(message=="success"){
		     alert("该条消息删除成功");
		  }
		}
		
	}); 
	
</script>
</body>
</html>
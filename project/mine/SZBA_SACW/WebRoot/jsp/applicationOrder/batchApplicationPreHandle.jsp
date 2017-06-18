<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>批量审批</title>
    
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	
	<link type="text/css" href="<%=path%>/css/main.css" rel="stylesheet" />
  </head>
  
  <body>
  <form action="<%=path%>/applicationOrder/batchApproveHandle.do" id="submitForm" method="post">
   <div class="toptit"><h1 class="h1tit">批量审核</h1></div>
 	<ul class="list02">
		<li><label>审批结果:</label>
			<select name="status">
				<option value="Y">同意</option>
				<option value="N">不同意</option>
			</select>
		</li>
		<li><label>审批意见：</label>
		<textarea name="result" cols="" rows=""></textarea></li>
	</ul>
	<input type="hidden" name="applicationJsonStr" value="" id="applications"/>
	</form>
  </body>
  
</html>

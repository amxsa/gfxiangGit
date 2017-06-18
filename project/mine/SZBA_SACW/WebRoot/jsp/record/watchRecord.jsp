<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>笔录信息</title>
<style type="text/css">
	.list02a .ztree li{
		clear:both;
	}

</style>

</head>
<body>
<div class="crumb"><span>当前位置：<a href="${pageContext.request.contextPath}/main.jsp" target="main">首页 </a>>> 
<c:choose>
	<c:when test="${record.type == '4' }">提取笔录</c:when>
	<c:when test="${record.type == '5' }">扣押笔录</c:when>
	<c:when test="${record.type == '6' }">搜查笔录</c:when>
	<c:when test="${record.type == '7' }">查封笔录</c:when>
	<c:when test="${record.type == '8' }">检查笔录</c:when>
	<c:otherwise>笔录信息</c:otherwise>
</c:choose>
</span></div>
<div class="content">
	
 <div class="toptit"><h1 class="h1tit">警员人员信息</h1></div>
 <ul class="list02a w50">
 	<c:forEach items="${record.polices }" var="poli" >
 		<c:if test="${poli.poliType == 15 }">
 			<li><label>侦查员：</label>${poli.poliName }</li>
   			<li><label>单位：</label>${poli.deptName }</li>
   		</c:if>
 		<c:if test="${poli.poliType == 16 }">
	   		<li><label>记录员：</label>${poli.poliName }</li>
   			<li><label>单位：</label>${poli.deptName }</li>
   		</c:if>
   	</c:forEach>
 </ul>
 <div class="toptit"><h1 class="h1tit">涉案人员信息</h1></div>
 <ul class="list02a w25">
 	<c:forEach items="${record.civilians }" var="civi">
 		<c:if test="${civi.civiType == 15 }">
		   <li><label>当事人：</label>${civi.civiName }</li>
		   <li><label>性别：</label>${civi.civiGender }</li>
		   <li><label>证件类型：</label>${civi.idType }</li>
		   <li><label>证件号：</label>${civi.idNum }</li>
  		</c:if>
   </c:forEach>
 </ul>
  <div class="toptit"><h1 class="h1tit">笔录详细信息</h1></div>
   <ul class="list02a w50">
   <li class="w100 h50"><label>时间：</label>
     	<fmt:formatDate value="${record.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
     	&nbsp;&nbsp;至 &nbsp;&nbsp;
     	<fmt:formatDate value="${record.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
   	</li>
   <li class="w100 h50"><label>对象：</label>
   		${record.target }
   	</li>
   <li class="w100 h50"><label>地点：</label>
   		${record.place }
   </li>
   <li class="w100 h50"><label>事由和目的：</label>
   		${record.reason }
   	</li>
   <li class="w100 h50" style="height:30%"><label>过程和结果：</label>
   		<div>${record.result }</div>
   </li>	
   </ul>
 <div class="dbut">
 <a href="javascript:history.go(-1)">返回</a>
 </div>
</div>

<script type="text/javascript">	

$(function() {
	
});
</script>

</body>
</html>

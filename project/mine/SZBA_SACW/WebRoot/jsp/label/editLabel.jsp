<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String msg=(String)request.getAttribute("msg");


%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 
    
    <title>更改标签</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="<%=path%>/css/main.css" type="text/css" href="styles.css">
	<style type="text/css">
	
	.mydiv{background-color:#fff;border:1px solid #ccc;
text-align:center;font-size:12px;font-weight:normal;z-index:999;width:900px;height:290px;padding-bottom:15px;left:25%;top:30%;margin-left:-150px!important;/*FF IE7 锟斤拷值为锟斤拷锟斤拷锟斤拷一锟斤拷 */margin-top:-60px!important;/*FF IE7 锟斤拷值为锟斤拷锟斤拷叩锟揭伙拷锟�/margin-top:0px;position:fixed!important;/* FF IE7*/position:absolute;/*IE6*/_top:expression(eval(document.compatMode &&document.compatMode=='CSS1Compat') ?documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/}
	</style>
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>

  </head>
  
  <body>
 <div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 修改标签</span></div>
<div class="content">
 <form action="<%=path%>/label/editLabel.do?action=edit" method="post" id="adform" name="adform">

  <input type="hidden"  name="id" value="${tLabel.id }"/>
  <input type="hidden" id="pno" name="pno" value="${tLabel.proNO }"/>
 <div class="toptit"><h1 class="h1tit">修改标签</h1></div>
 <ul class="list02">
 	
   <li><label>财物：</label><input type="text" id="pname" name="pname" value="${tLabel.proName }"/><!-- <input type="button" value="选择" onclick="opendiv();"/> --></li>
   <li><label>新标签：</label><input name="newName" type="text"  value="8989" /><input type="button" value="制作" onclick="opendiv();"/></li>

   <li><label>更换原因：</label><input type="text" name="description" value="${tLabel.description }"/></li>
 </ul>
 <div class="dbut"><a href="#" onclick="tijiao()">修改</a><a href="#" onclick="window.history.back();">返回</a></div>
 </form>
 <!--  
<div id="popDiv2" class="mydiv" style="display:none;">
  <form id="searchForm" action="<%=path%>/label/queryForEditPage.do?action=fenye" method="post">
<input type="hidden" name="lid" value="${tLabel.id }"/>
  <p><label>财物编号:</label><input name="condiProId" type="text" value="${condition.condiProId}"/><label>财物名称:</label><input name="condiProName" type="text" value="${condition.condiProName }"/> <input type="submit" value="查询"/> <input type="button" value="选择" onclick="doSelect()"/>
 </p>


<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
<tr>
<td>选择</td><td>案件名称</td><td>案件编号</td><td>财物名称</td><td>财物类别</td><td>财物来源</td><td>财物数量</td><td>单位</td><td>财物特征</td>
</tr>
 	<c:forEach var="tProperties" items="${tProperties}">
 	<tr>
	   <td><input type="radio" name="inputgroupId" id="inputgroupId" value="${tProperties.proId }" /></td>
	   <td>${tProperties.caseName }</td>
	   <td>${tProperties.jzcaseId }</td>
	   <td><a id="${tProperties.proId }">${tProperties.proName }</a></td>
	 
	   <c:if test="${tProperties.proType=='YBCW' }">
	 	<td>一般财物</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='YHBZ' }">
	 	<td>烟花爆竹</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='JHQZ' }">
	 	<td>缴获枪支</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='GZDJLA' }">
	 	<td>管制刀具(立案)</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='GZDJBLA' }">
	 	<td>管制刀具(立案)</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='YHWPTS' }">
	 	<td>淫秽物品（图书）</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='YHWPGP' }">
	 	<td>淫秽物品（光盘）</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='DBGJ' }">
	 	<td>赌博工具</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='BBCCW' }">
	 	<td>不保存的财物</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='DZWZY' }">
	 	<td>电子物证（有存储介质)</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='DZWZW' }" >
	 	<td>电子物证（无存储介质）</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='' }">
	 	<td></td>
	 	</c:if>
	   
	   <td>${tProperties.proOrigin }</td>
	   <td>${tProperties.proQuantity }</td>
	   <td>${tProperties.proUnit }</td>
	   <td>${tProperties.proCharacteristic }</td>
	   
   </tr>
   </c:forEach>
</table>

  <div style="position:absolute;left:10;width:30;"><a class="d2" href="javascript:closeDiv2()">取消</a></div><div class='page' style="position:absolute;right:0;width:430;"></div>
   </form>
</div>
-->
<script type="text/javascript">

function tijiao(){

$("#adform").submit();

}

$(document).ready(function() {
		var message = '<%=msg%>';
		
		if(message != ''){
			if(message=='Y'){
				alert('修改成功！');
				window.location.href="${path}/label/queryForPage.do";
			} if(message=='F'){
				alert('修改失败，请稍后重试！');
			}
			if(message=='ok'){
				document.getElementById("popDiv2").style.display="";
			}
		}
});
</script>
  </body>
</html>

<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 损毁登记</span></div>
<form id="searchForm" action="<%=path%>/damage/queryForPage.do" method="post">
<div class="content">
 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
 <div class="search">
<div class="sp">
  <p><label>财物编号</label><input name="condiProNo" type="text" value="<%=RequestUtils.getAttribute(params,"condiProNo",0)%>" style="width:170px;"/><label>财物名称</label><input name="condiProName" type="text" value="<%=RequestUtils.getAttribute(params,"condiProName",0)%>" style="width:120px;"/>
  	 
  </p>
  <p><label>案件编号</label><input name="condiJzcaseId" type="text" value="<%=RequestUtils.getAttribute(params,"condiJzcaseId",0)%>" style="width:130px;"/><label>案件名称</label><input name="condiCaseName" type="text" value="<%=RequestUtils.getAttribute(params,"condiCaseName",0)%>" style="width:120px;"/></p>
  <p><label>损毁时间</label><input name="condiStartTime" type="text"  value="<%=RequestUtils.getAttribute(params,"condiStartTime",0)%>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/><i>至</i><input name="condiEndTime" type="text"  value="<%=RequestUtils.getAttribute(params,"condiEndTime",0)%>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/></p>
 </div>
  <div class="sbut"><input name="" type="button" value="查询" onclick="searchPro()"/> <input style="background:#1F6BB2  ;padding-left:23px;margin:0 0 0 20px" type="button" onclick="tiao()" value="登记"/></div>
  </div>
 
 
  <table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
  <tr>
    <th>序号</th>
    <th>案件名称</th>
    <th>案件编号</th>
    <th>财物编号</th>
	<th>财物名称</th>
	<th>财物类别</th>
	<th>数量</th>
	<th>单位</th>
	<th>损毁时间</th>    
	<th>损毁人</th>
    <th>操作</th>
  </tr>
 <c:forEach items="${tDemages }" var="tDemages" varStatus="sta">
	 <tr>
	 	<td>${sta.count }</td>
	 	<td>${tDemages.caseName }</td>
	 	<td>${tDemages.jzcaseId }</td>
	 	<td>${tDemages.proNO }</td>
	 	<td>${tDemages.proName }</td>
	 	<c:if test="${tDemages.proType=='YBCW' }">
	 	<td>一般财物</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='YHBZ' }">
	 	<td>烟花爆竹</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='JHQZ' }">
	 	<td>缴获枪支</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='GZDJLA' }">
	 	<td>管制刀具(立案)</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='GZDJBLA' }">
	 	<td>管制刀具(立案)</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='YHWPTS' }">
	 	<td>淫秽物品（图书）</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='YHWPGP' }">
	 	<td>淫秽物品（光盘）</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='DBGJ' }">
	 	<td>赌博工具</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='BBCCW' }">
	 	<td>不保存的财物</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='DZWZY' }">
	 	<td>电子物证（有存储介质)</td>
	 	</c:if>
	 	<c:if test="${tDemages.proType=='DZWZW' }" >
	 	<td>电子物证（无存储介质）</td>
	 	</c:if>
	 	<c:if test="${empty tDemages.proType}">
	 	<td></td>
	 	</c:if>
	 	<td>${tDemages.proQuantity }</td>
	 	<td>${tDemages.proUnit }</td>
	 	<td><fmt:formatDate value='${tDemages.createTime }' pattern='yyyy-MM-dd HH:mm:ss'/></td>
	 	
	 	<td>${tDemages.damageAccount }</td>
	 	<td><span class="tdbut"><a href="<%=path %>/damage/editDamage.do?action=goto&did=${tDemages.id }">修改</a>
	    	<a href="javascript:deleteData('${tDemages.id }')">删除</a></span>
	    </td>
	 </tr>  
	</c:forEach>	
	

 </table>
 
	    
 <div class='page'></div>

</form>
</div>
<script type="text/javascript">
$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm",'${page.currentIndex}','${page.sizePerPage}','${page.totalPage}');
	});
function tiao(){
window.location.href="<%=path %>/jsp/damage/addDamage.jsp";
}
function searchPro(){
		
		$("#searchForm").submit();
	}
function deleteData(id){
		var flag = window.confirm("确定删除此记录？");
		if(flag){
			$.ajax({
				url:"<%=path%>/damage/delete.do",
				data:"DId="+id,
				type:"post",
				success:function(backData){
					if(backData == "Y"){
						alert("删除成功");
						searchPro();
					}else{
						alert("删除失败");
					}
				},
				error:function(){
					alert("网络连接出错，请稍候重试");
				}
				
			});
		}
	}

</script>
</body>
</html>
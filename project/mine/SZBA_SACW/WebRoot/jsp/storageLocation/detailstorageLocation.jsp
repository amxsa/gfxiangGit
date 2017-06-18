<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils"%>
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
<link type="text/css" href="<%=path%>/css/zTreeStyle.css"
	rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript"
	src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			库位详情
		</span>
	</div>
	<div class="content">
		<form id="checkForm"
			action="<%=path%>/account/save.do?from=editAccount" method="post">
			<div class="toptit">
				<h1 class="h1tit">库位信息</h1>
			</div>
			<ul class="list02a w50">
				<li><label>库位编号：</label>${entity.locationNum }</li>
				<li><label>库位名称：</label>${entity.locationName }</li>
				
				<li><label>库位类型：</label><span name="locationType">${entity.locationType }</span></li>
				<li><label>体积大小：</label>${entity.capacity }</li>
				<li><label>存放数量：</label>${entity.depositNum }</li>
				<li><label>已放数量：</label>${entity.goodsNum }</li>
				<li><label>楼号：</label>${entity.buildNum }</li>
				<li><label>楼层：</label>${entity.buildLevel }</li>
				<li><label>柜号：</label>${entity.counterNum }</li>
				<li><label>RFID编号：</label>${entity.rfidNum }</li>
				<li><label>地址：</label>${entity.address }</li>
				<li><label>当前状态：</label><span name="inventoryStatus">${entity.inventoryStatus }</span></li>
				<li><label>所属保管室：</label>
				<c:forEach var="r" items="${storeRoomList }">
						<c:if test="${entity.storeroomID==r.id }">${r.storeroomName }</c:if>
					</c:forEach></li>
				<li><label>所属货架：</label>${entity.rackName }</li>
			</ul>

			<div class="dbut">
				<a href="#" onclick="goback();">返回</a>
			</div>
		</form>
	</div>
<div id="selectDataDiv" style="display:none;">
	<select name="locationType">
		<jsp:include page="/jsp/plugins/storageLocation_options.jsp" />
	</select>
	<select name="inventoryStatus">
		<jsp:include page="/jsp/plugins/storageLocation_status_options.jsp" />
	</select>
</div>
<script type="text/javascript">
function getParams(){
	var locationNum = '<%=RequestUtils.getAttribute(params,"locationNumQ",0)%>';
	var locationType = '<%=RequestUtils.getAttribute(params,"locationTypeQ",0)%>';
	var inventoryStatus = '<%=RequestUtils.getAttribute(params,"inventoryStatusQ",0)%>';
	var rfidNum = '<%=RequestUtils.getAttribute(params,"rfidNumQ",0)%>';
	var storeroomID = '<%=RequestUtils.getAttribute(params,"storeroomIDQ",0)%>';
	var rackId = '<%=RequestUtils.getAttribute(params,"rackIdQ",0)%>';
	var currentIndex = '<%=RequestUtils.getAttribute(params,"currentIndex",0)%>';
	var sizePerPage = '<%=RequestUtils.getAttribute(params,"sizePerPage",0)%>';
	/* 获取参数*/
	var params = "&locationNum="+locationNum+"&locationType="+locationType+
	"&inventoryStatus="+inventoryStatus+"&rfidNum="+rfidNum+"&storeroomID="+storeroomID
	+"&rackId="+rackId+"&currentIndex="+currentIndex+"&sizePerPage="+sizePerPage;
	
	return params;
}
function goback(){
	/* 跳转页面 */
	window.location.href="<%=path%>/storageLocation/queryForPage.do?"+ getParams();
}
$(document).ready(function() {
	//显示库位类型
	$('#checkForm span').each(function(idx,el){
		var type=$(el).attr('name');
		if(type=='locationType'||type=='inventoryStatus'){
			if($(el).text()!=null&&$(el).text()!=''){
				var text=$('#selectDataDiv select[name="'+type+'"] option[value="'+$(el).text()+'"]').text();
				$(el).html(text);
			}
		}
	});
});
</script>
</body>
</html>
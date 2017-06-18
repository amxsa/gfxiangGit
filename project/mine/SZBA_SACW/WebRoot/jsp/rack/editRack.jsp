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
<link href="<%=request.getContextPath()%>/css/validform.css" type="text/css" rel="Stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Validform/Validform_v5.3.2.js"></script>
<script language="javascript"
	src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			修改货架
		</span>
	</div>
	<div class="content">
		<form id="checkForm" action="javascript:void(0);" method="post">
			<div class="toptit">
				<h1 class="h1tit">货架信息</h1>
			</div>
			<ul id="rackEntity" class="list02a w50">
				<li>
					<label>货架编号：</label>
					${entity.rackNum }<input name="rackNum" type="hidden" value="${entity.rackNum }" />
				</li>
				<li>
					<label>货架高度：</label>
					<input name="rackHeight" type="text" value="${entity.rackHeight }" ignore="ignore" datatype="n" nullmsg="货架高度只能为数字类型！" errormsg="货架高度只能为数字类型！"/>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>货架长度：</label>
					<input name="rackLength" type="text" value="${entity.rackLength }" ignore="ignore" datatype="n" nullmsg="货架长度只能为数字类型！" errormsg="货架长度只能为数字类型！"/>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>货架载重：</label>
					<input name="rackLoad" type="text" value="${entity.rackLoad }" ignore="ignore" datatype="n" nullmsg="货架长度只能为数字类型！" errormsg="货架长度只能为数字类型！"/>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>所属保管室：</label>
						<select id="storeroom" name="storeroomId" style="width:120px;">
						<option value="">请选择</option>
						<c:forEach var="r" items="${storeRoomList }">
							<c:if test="${entity.storeroomId==r.id }">
								<option selected="selected" value="${r.id }">${r.storeroomName }</option>
							</c:if>
							<c:if test="${entity.storeroomId!=r.id }">
								<option value="${r.id }">${r.storeroomName }</option>
							</c:if>
						</c:forEach>
				</select>
				</li>
				<li></li>
			</ul>
			<input id="result" type="hidden" value="" />
			<div class="dbut">
				<input type="submit" value="修改" />
				<input type="button" value="返回" onclick="goback();"/>
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
$(document).ready(function() {
	$("#checkForm").Validform({
		tipSweep:true,
		tiptype:function(msg,o,cssctl){
			if(!o.obj.is("form")){
				var objtip=o.obj.siblings(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
			}
		},
		beforeSubmit:function(curform){
			$.ajax({ 
				type:"post", 
				dataType:"json",
				url:sysPath+'/rack/save.do?from=editRack', 
				data: $('#checkForm').serialize(),
				success:function(data){
					if(data.state=='S'){
						alert('修改成功');
						window.location.href=sysPath+"/rack/queryForPage.do?"+ getParams();
					}else if(data.state=='F'){
						alert('修改失败，请重试');
					}
				}
			});
			return false;
		}
	});
});
</script>
</body>
</html>
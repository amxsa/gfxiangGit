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
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
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
			修改保管室
		</span>
	</div>
	<div class="content">
		<form id="checkForm"
			action="javascript:void(0);" method="post">
			<div class="toptit">
				<h1 class="h1tit">保管室信息</h1>
			</div>
			<ul id="storeroomEntity" class="list02a w50">
				<li><label>保管室编号：</label>
					${entity.storeroomNum }
					<input name="storeroomNum" type="hidden" value="${entity.storeroomNum }" />
				</li>
				<li><label>保管室名称：</label>
					<input name="storeroomName" type="text" value="${entity.storeroomName }" />
				</li>
				<li><label>保管室面积：</label>
				    <input name="storeroomArea" type="text" value="${entity.storeroomArea }" ignore="ignore" datatype="n" nullmsg="保管室面积只能为数字类型！" errormsg="保管室面积只能为数字类型！"/>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>保管室类型：</label>
					<input name="ystoreroomType" type="hidden" value="${entity.storeroomType }" />
					<select name="storeroomType">
						<jsp:include page="/jsp/plugins/storeroomType_options.jsp" />
					</select>
				</li>
				<li><label>所属地址：</label>
					<input name="storeroomAddr" type="text" value="${entity.storeroomAddr }" />
				</li>
				<li><label>货架数量：</label>
					<input name="roomNum" type="text" value="${entity.roomNum }" ignore="ignore" datatype="n" nullmsg="货架数量只能为数字类型！" errormsg="货架数量只能为数字类型！"/>
					<div class="Validform_checktip"></div>
				</li>
				<li><label>所属仓库：</label>
					<select id="storehouseId" name="storehouseId">
		   				<option value="">请选择</option>
						<c:forEach var="r" items="${storeHouseList }">
						<c:if test="${entity.storehouseId==r.id }">
							<option selected="selected" value="${r.id }">${r.storehouseName }</option>
						</c:if>
						<c:if test="${entity.storehouseId!=r.id }">
							<option value="${r.id }">${r.storehouseName }</option>
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
	var storeroomNum = '<%=RequestUtils.getAttribute(params,"storeroomNumQ",0)%>';
	var storehouseId = '<%=RequestUtils.getAttribute(params,"storehouseIdQ",0)%>';
	var currentIndex = '<%=RequestUtils.getAttribute(params,"currentIndex",0)%>';
	var sizePerPage = '<%=RequestUtils.getAttribute(params,"sizePerPage",0)%>';
	
	var params = "&storeroomNum="+storeroomNum
	+"&storehouseId="+storehouseId+"&currentIndex="+currentIndex+"&sizePerPage="+sizePerPage;
	return params;
}

	
function goback(){
	window.location.href=sysPath+"/storeroom/queryForPage.do?"+ getParams();
}

$(document).ready(function() {
	//显示原来类型
	$('#checkForm select[name="storeroomType"]').val($('#checkForm input[name="ystoreroomType"]').val());
	
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
				url:sysPath+'/storeroom/save.do?from=editStoreroom', 
				data: $('#checkForm').serialize(),
				success:function(data){
					if(data.state=='S'){
						alert('修改成功');
						window.location.href=sysPath+"/storeroom/queryForPage.do?"+ getParams();
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
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
<style type="text/css">
.list02a li select{
	height:22px;
}
</style>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			修改库位
		</span>
	</div>
	<div class="content">
		<form id="checkForm" action="javascript:void(0)"
			method="post">
			<div class="toptit">
				<h1 class="h1tit">库位信息</h1>
			</div>
			<ul id="storageLocationEntity" class="list02a w50">
				<li><label>库位编号：</label>
					${entity.locationNum }
					<input name="locationNum" type="hidden" value="${entity.locationNum }" />
				</li>
				<li><label>库位名称：</label>
					<input name="locationName" type="text" value="${entity.locationName }" />
				</li>
				<li><label>库位类型：</label>
					<input name="elocationType" type="hidden" value="${entity.locationType }" />
					<select name="locationType">
						<jsp:include page="/jsp/plugins/storageLocation_options.jsp" />
					</select>
				</li>
					
				<li><label>体积大小：</label>
					<input name="capacity" type="text" value="${entity.capacity }" />
				</li>
				<li><label>存放数量：</label>
					<input name="depositNum" type="text" value="${entity.depositNum }" />
				</li>
				<li><label>已放数量：</label>
					<input name="goodsNum" type="text" value="${entity.goodsNum }" />
				</li>
				<li><label>楼号：</label>
					<input name="buildNum" type="text" value="${entity.buildNum }" />
				</li>
				<li><label>楼层：</label>
					<input name="buildLevel" type="text" value="${entity.buildLevel }" />
				</li>
				<li><label>柜号：</label>
					<input name="counterNum" type="text" value="${entity.counterNum }" />
				</li>
				<li><label>RFID编号：</label>
					<input name="rfidNum" type="text" value="${entity.rfidNum }" />
				</li>
				<li><label>地址：</label>
					<input name="address" type="text" value="${entity.address }"/>
				</li>
				<li><label>当前状态：</label>
					<input name="einventoryStatus" type="hidden" value="${entity.inventoryStatus }" />
					<select name="inventoryStatus">
						<jsp:include page="/jsp/plugins/storageLocation_status_options.jsp" />
					</select>
				</li>
				<li><label>所属保管室</label><select id="storeroom"
					name="storeroomID">
						<option value="">请选择</option>
						<c:forEach var="r" items="${storeRoomList }">
							<c:if test="${entity.storeroomID==r.id }">
								<option selected="selected" value="${r.id }">${r.storeroomName }</option>
							</c:if>
							<c:if test="${entity.storeroomID!=r.id }">
								<option value="${r.id }">${r.storeroomName }</option>
							</c:if>
						</c:forEach>
				</select></li>
				<li><label>所属货架：</label> <input name="rackId"
					type="hidden" value="${entity.rackId }" /> <input name="rackName"
					type="text" disabled="disabled" value="${entity.rackName }" /> <input
					type="button" value="选择" style="width: 50px;" id="selectRack" /></li>
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
			if(parseInt($('#checkForm input[name="goodsNum"]').val())>parseInt($('#checkForm input[name="depositNum"]').val())){
				alert('已放数量不能大于存放数量');
				return false;
			}
			
			$.ajax({ 
				type:"post", 
				dataType:"json",
				url:sysPath+'/storageLocation/save.do?from=editStorageLocation', 
				data: $('#checkForm').serialize(),
				success:function(data){
					if(data.state=='S'){
						alert('修改成功');
						window.location.href = sysPath +'/storageLocation/queryForPage.do?'+ getParams();
					}else if(data.state=='F'){
						alert('修改失败，请重试');
					}
				}
			});
			return false;
		}
	});
	
	//显示库位类型  状态
	$('#checkForm select[name="locationType"]').val($('#checkForm input[name="elocationType"]').val());
	$('#checkForm select[name="inventoryStatus"]').val($('#checkForm input[name="einventoryStatus"]').val());
	var message = '${message}';
	if (message != '') {
		if (message = "success") {
			alert('保存成功');
		} else {
			alert('保存失败');
		}
	}

	//选择货架信息
	$('#selectRack').on('click',function() {
		var path = sysPath+ '/jsp/storageLocation/addstorageLocationRackList.jsp';
		art.dialog.open(path,{
			title : '选择货架',
			width : 600,
			top : 10,// 在open()方法中，init会等待iframe加载完毕后执行
			init : function() {
				var iframe = this.iframe.contentWindow;
				var searchForm = iframe.document.getElementById('searchForm');
				$(searchForm).submit(); //自动查询
			},
			ok : function() {
				var iframe = this.iframe.contentWindow;
				var table_hover = iframe.document.getElementById('table_hover');
				var checkedLen = $(table_hover).find('input[name="id"]:checked').length;
				var rackId = '';
				var rackName = '';
				if (checkedLen != 0) {
					var checkT = $(table_hover).find('input[name="id"]:checked');
					var checkTr = checkT.parent().parent();
					rackId = checkT.val();
					rackName = $(checkTr).children('td').eq(1).html();
				}
				$('#checkForm input[name="rackId"]').val(rackId);
				$('#checkForm input[name="rackName"]').val(rackName);
			},
			cancel : true
		});
	});
});
	</script>
</body>
</html>
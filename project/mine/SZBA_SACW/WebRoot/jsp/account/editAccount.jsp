<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
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
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 修改用户</span></div>
<div class="content">
 <form id="checkForm" action="<%=path%>/account/save.do?from=editAccount" method="post">
 <div class="toptit"><h1 class="h1tit">用户信息</h1></div>
 <ul class="list02a w50">
   <li><label>账号：</label>${entity.account }<input name="account" type="hidden" value="${entity.account }"/></li>
   <li><label>工号：</label><input name="workno" type="text" value="${entity.workno }"/></li>
   <li><label>姓名：</label><input name="name" type="text" value="${entity.name }"/></li>
   <li><label>性别：</label><select name="gender"><option value=""></option><option value="M">男</option><option value="F">女</option></select></li>
   <li><label>手机号码：</label><input name="mobile" type="text" value="${entity.mobile }"/></li>
   <li><label>所属部门：</label>
   		<input name="departmentID" type="hidden" value="${entity.departmentID }"/>
		<input name="departmentName" type="text" disabled="disabled" value="${mapping:mappingDepartmentNameById(entity.departmentID) }"/>
		<input type="button" value="选择" style="width:50px;" class="selectDepartment"/>
   </li>
   <li><label>证件类型：</label><select name="idType"><option value=""></option><option value="IDCard">身份证</option></select></li>
   <li><label>证件号码：</label><input name="idNum" type="text" value="${entity.idNum }"/></li>
   <li class="w100"><label>状态：</label><select name="status"><option value="Y">激活</option><option value="N">禁用</option></select></li>
 </ul>
 <label>角色：</label><c:forEach items="${rMap }" var="bRecord" >
   <c:if test="${bRecord.value[1]==1 }"><input name="role" type="checkbox"  checked="checked" value="${bRecord.key }"/></c:if>
   <c:if test="${bRecord.value[1]==0 }"><input name="role" type="checkbox" value="${bRecord.key }"/></c:if>${bRecord.key }
   ${bRecord.value[0]}
   </c:forEach>
   <br/><br/>
 <label>用户功能：</label><c:forEach items="${iMap}" var="aRecord">
  <c:if test="${aRecord.value[1]==1 }"><input name="icon" type="checkbox" checked="checked" value="${aRecord.key }" disabled="disabled"/></c:if>
   <c:if test="${aRecord.value[1]==0 }"><input name="icon" type="checkbox" value="${aRecord.key }" disabled="disabled"/></c:if>
   ${aRecord.value[0] }
   </c:forEach>
 <div class="dbut">
 	<a href="#" onclick="save();">保存</a>
 	<a href="#" onclick="goback();">返回</a>
 </div>
 </form>
</div>
<script type="text/javascript">
	
	function save(){
		var r = document.getElementsByName("role");
		var role = "";
		for( var i = 0; i < r.length; i++ ){
			if ( r[i].checked )role += r[i].value+",";
		}
		var ic = document.getElementsByName("icon");
		var icon = "";
		for( var i = 0; i < ic.length; i++ ){
			if ( ic[i].checked )icon += ic[i].value+",";
		}
		var account = '<%=RequestUtils.getAttribute(params,"accountQ",0)%>';
		var workno = '<%=RequestUtils.getAttribute(params,"worknoQ",0)%>';
		var status = '<%=RequestUtils.getAttribute(params,"statusQ",0)%>';
		var name = '<%=RequestUtils.getAttribute(params,"nameQ",0)%>';
		var mobile = '<%=RequestUtils.getAttribute(params,"mobileQ",0)%>';
		var gender = '<%=RequestUtils.getAttribute(params,"genderQ",0)%>';
		var departmentID = '<%=RequestUtils.getAttribute(params,"departmentIDQ",0)%>';
		var params = "&accountQ="+account+"&worknoQ="+workno+"&statusQ="+status+"&nameQ="+name+"&mobileQ="+mobile+"&genderQ="+gender+"&departmentIDQ="+departmentID+"&role="+role+"&icon="+icon;
		var f = $('#checkForm');
		f.attr('action',f.attr('action')+params);
		f.submit();
	}
	
	function goback(){
		var account = '<%=RequestUtils.getAttribute(params,"accountQ",0)%>';
		var workno = '<%=RequestUtils.getAttribute(params,"worknoQ",0)%>';
		var status = '<%=RequestUtils.getAttribute(params,"statusQ",0)%>';
		var name = '<%=RequestUtils.getAttribute(params,"nameQ",0)%>';
		var mobile = '<%=RequestUtils.getAttribute(params,"mobileQ",0)%>';
		var gender = '<%=RequestUtils.getAttribute(params,"genderQ",0)%>';
		var departmentID = '<%=RequestUtils.getAttribute(params,"departmentIDQ",0)%>';
		
		var params = "&account="+account+"&workno="+workno+"&status="+status+"&name="+name+"&mobile="+mobile+"&gender="+gender+"&departmentID="+departmentID;
		
		window.location.href="<%=path%>/account/queryForPage.do?"+params;
	}

	$(document).ready(function() {
		var message = '${message}';
		if(message!=''){
			if(message="success"){
				alert('保存成功');
			} else{
				alert('保存失败');
			}
		}
		
		$('select[name="gender"]').val('${entity.gender }');
		$('select[name="departmentID"]').val('${entity.departmentID }');
		$('select[name="idType"]').val('${entity.idType }');
		$('select[name="status"]').val('${entity.status }');
		
		$(".selectDepartment").click(function(){
			var thisObj=$(this);
			var path=sysPath+'/jsp/plugins/selectDepartment.jsp';
			art.dialog.open(path, {
			    title: '选择部门',
			    width: 400,
			    height:300,
			    top:10,
			    // 在open()方法中，init会等待iframe加载完毕后执行
			    init: function () {
			    	var iframe = this.iframe.contentWindow;
			    	$(iframe.document.getElementById('checked')).val(thisObj.prev().prev().val());
			    	this.iframe.contentWindow.initData();	
			    },
			    ok: function () {
			    	var retData=this.iframe.contentWindow.getCheckedData();	
			    	if(retData==null){
			    		return false;
			    	}else{
			    		var selId=retData.id;
			    		var selName=retData.name;
			    		thisObj.prev().val(selName);
			    		thisObj.prev().prev().val(selId);
			    	}
			    },
			    cancel: true
			});
		});
	});

</script>
</body>
</html>
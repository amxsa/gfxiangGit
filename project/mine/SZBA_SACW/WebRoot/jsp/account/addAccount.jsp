<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String message = (String) request.getAttribute("message");
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>添加用户</title>
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 添加用户</span></div>
<div class="content">
 <form id="checkForm" action="<%=path %>/account/add.do" method="post">
 <div class="toptit"><h1 class="h1tit">用户信息</h1></div>
 <ul class="list02a w50">
   <li><label><span style="color:red;">*</span>账号：</label><input name="account" type="text" value=""/></li>
   <li><label>工号：</label><input name="workno" type="text" value=""/></li>
   <li><label><span style="color:red;">*</span>密码：</label><input name="password" type="password" value=""/></li>
   <li><label><span style="color:red;">*</span>确认密码：</label><input name="rePassword" type="password" value=""/></li>
   <li><label>姓名：</label><input name="name" type="text" value=""/></li>
   <li><label>性别：</label><select name="gender"><option value=""></option><option value="M">男</option><option value="F">女</option></select></li>
   <li><label>手机号码：</label><input name="mobile" type="text" value=""/></li>
   <li><label>所属部门：</label>
   		<input name="departmentID" type="hidden"/>
		<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位"/>
		<input type="button" value="选择" style="width:50px;" class="selectDepartment"/>
   </li>
   <li><label>证件类型：</label><select name="idType"><option value=""></option><option value="IDCard">身份证</option></select></li>
   <li><label>证件号码：</label><input name="idNum" type="text" value=""/></li>
   <li class="w100"><label>状态：</label><select name="status"><option value="Y">激活</option><option value="N">禁用</option></select></li>
 </ul>

 <div class="dbut"><a href="#" onclick="save();">保存</a><a href="#" onclick="goback();">返回</a></div>
 </form>
</div>
<script type="text/javascript">
	function save(){
		if($('input[name="account"]').val()==''){
			alert("帐号不能为空");
			return;
		}
		if($('input[name="password"]').val()==''){
			alert("密码不能为空");
			return;
		}
		if($('input[name="password"]').val()!=$('input[name="rePassword"]').val()){
			alert("两次输入的密码不一致");
			return;
		}
		var account = '<%=RequestUtils.getAttribute(params,"accountQ",0)%>';
		var workno = '<%=RequestUtils.getAttribute(params,"worknoQ",0)%>';
		var status = '<%=RequestUtils.getAttribute(params,"statusQ",0)%>';
		var name = '<%=RequestUtils.getAttribute(params,"nameQ",0)%>';
		var mobile = '<%=RequestUtils.getAttribute(params,"mobileQ",0)%>';
		var gender = '<%=RequestUtils.getAttribute(params,"genderQ",0)%>';
		var departmentID = '<%=RequestUtils.getAttribute(params,"departmentIDQ",0)%>';
	
		var params = "?accountQ="+account+"&worknoQ="+workno+"&statusQ="+status+"&nameQ="+name+"&mobileQ="+mobile+"&genderQ="+gender+"&departmentIDQ="+departmentID;
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
		var message = '<%=message%>'
		if(message != ''){
			if(message=='success'){
				alert('添加成功！');
			} else if(message=='fail'){
				alert('添加失败，请稍后重试！');
			} else if(message=='exist'){
				alert('添加失败：账号已存在！');
			}
		}

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
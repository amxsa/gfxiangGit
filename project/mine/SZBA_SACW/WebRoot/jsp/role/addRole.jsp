<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加桌面图标</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
	function save(){
	    var name = $('input[name="name"]').val();
	    var description = $('input[name="description"]').val();
	    var priority = $('select[name="priority"]').val();
	    var roleKey = $('input[name="roleKey"]').val();
		if(name==''){
		    alert("名称不能为空");
			return;
		}
		
		if(description==''){
		    alert("描述不能为空");
			return;
		}
		if(priority==''){
		    alert("角色优先级不能为空");
			return;
		}
		if(roleKey==''){
		    alert("关键字不能为空");
			return;
		}
		//$('#checkForm').submit();
		 var result="";
	     $.ajax({
					type: "POST",
					url: "<%=request.getContextPath()%>/role/add.do?name="+name+"&description="+description+"&priority="+priority+"&roleKey="+roleKey,
					async:true,
					dataType:"json",
					success: function(data){
					   result = data.result;
	                   if(result!='success'){
		                  alert("添加角色失败");
		               }
		               else{
		                  alert("添加成功");
		                  window.location.href="<%=request.getContextPath()%>/role/queryForPage.do";
		               }
					}
				});
	}
	
	$(document).ready(function() {
		<%-- var result = <%=result%>;
		if(result != ''){
			if(result=='success'){
				alert('添加成功！');
			} else if(result=='fail'){
				alert('添加失败，请稍后重试！');
		}
	   } --%>
	});
</script>

<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 添加角色</span></div>
<div class="content">
 <form id="checkForm" action="<%=path %>/role/add.do" method="post">
 <div class="toptit"><h1 class="h1tit">角色信息</h1></div>
 <ul class="list02 w50">
   <li><label><span style="color:red;">*</span>名称 ：</label><input name="name" type="text" value=""/></li>
   <li><label><span style="color:red;">*</span>描述：</label><input name="description" type="text" value=""/></li>
   <li><label><span style="color:red;">*</span>关键字：</label><input name="roleKey" type="text" value=""/><span style="color:red;">(如：办案名警(BAMJ)首字母拼音大写)</span></li>
   <li><label><span style="color:red;">*</span>角色优先级：</label>
   		<select name="priority">
   			<option value=""></option>
			<option value="1">一级</option>
			<option value="2">二级</option>
			<option value="3">三级</option>
			<option value="4">四级</option>
			<option value="5">五级</option>
			<option value="6">六级</option>
			<option value="7">七级</option>
			<option value="8">八级</option>
			<option value="9">九级</option>
			<option value="10">十级</option>
		</select>
   </li>
 </ul>
 <div class="dbut"><a href="#" onclick="save();">保存</a><a href="javascript:history.back();">返回</a></div>
 </form>
</div>
</body>
</html>

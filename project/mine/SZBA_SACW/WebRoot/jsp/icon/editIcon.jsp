<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String result = (String) request.getAttribute("result");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加桌面图标</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>
<body onload="load()">
<script type="text/javascript">
function load(){
  var options=document.getElementById("priorityId").options;
  var selValue = ${entity.priority };
  for(var i=0;i<options.length;i++){
     if(selValue==options[i].value){
       options[i].selected=true;
    }
  }
}
$(function() {
	//上传照片
	$('#imageInfo').uploadify({
		'formData'     : {},
    	'fileTypeDesc' : 'Excel Files',
        'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.bmp;*.gif;*.tiff',
        'sizeLimit': 5000000,//控制上传文件的大小，单位byte服务器默认只支持30MB(30000000)，修改服务器设置请查看相关资料
		'queueID':'queue',
		'swf'      : '<%=path%>/js/uploadify/uploadify.swf',
		'uploader' : '<%=path%>/upload/image.do',
		'buttonText' : '选择Excel文件',
		'onUploadSuccess' : function(file, data, response) {
		//	alert(response+","+data+","+JSON.stringify(file));
			//将data中的值，全路径放在hidden中去提交
		//	$('#uploadFilePath').val(data);
			var dataObj = JSON.parse(data);
			if(dataObj){
				if(dataObj == "ok"){
					alert("上传成功");
				}else{
					alert("失败");
				}
			}
		}
		});
	});	
	function save(){
		if($('input[name="name"]').val()==''){
			return;
		}
		if($('input[name="url"]').val()==''){
			return;
		}
		if($('input[name="priority"]').val()==''){
			return;
		}
		$('#checkForm').submit();
	}
	
	$(document).ready(function() {
		var result = <%=result%>;
		if(result != ''){
			if(result=='success'){
				alert('添加成功！');
			} else if(result=='fail'){
				alert('添加失败，请稍后重试！');
		}
	   }
	});
</script>

<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 添加图标</span></div>
<div class="content">
 <form id="checkForm" action="<%=path %>/icon/toSave.do?id=${entity.id }" method="post">
 <div class="toptit"><h1 class="h1tit">图标信息</h1></div>
 <ul class="list02 w50">
   <li><label><span style="color:red;">*</span>名称 ：</label><input name="name" type="text" value="${entity.name }"/></li>
   <li><label>描述：</label><input name="description" type="text" value="${entity.description }"/></li>
   <li><label>图标：</label><input id="imageInfo" name="icon" type="text" value="${entity.icon }"/></li>
   <li><label><span style="color:red;">*</span>链接地址：</label><input name="url" type="text" value="${entity.url }"/></li>
   <li><label><span style="color:red;">*</span>图标优先级：</label>
   		<select name="priority" id="priorityId">
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
   <li><label>目标窗口：</label><input name="target" type="text" value="${entity.target }"/></li>
 </ul>
 <div class="dbut"><a href="#" onclick="save();">保存</a><a href="#" onclick="javascript:history.back()">返回</a></div>
 </form>
</div>
</body>
</html>
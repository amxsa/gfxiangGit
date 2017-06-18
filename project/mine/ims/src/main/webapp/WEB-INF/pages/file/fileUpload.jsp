<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/pages/common.jspf"%>
<html>
<head>
<script type="text/javascript" src="${path }/common/js/ajaxfileupload.js"></script>
<title>文件上传</title>
<script type="text/javascript">
	
	function uploadImg(){
		var imgVal = $("#file").val();
		$.ajaxFileUpload({ 
            url:'${path}/user/uploadFile.do',//用于文件上传的服务器端请求地址
            secureuri:false,//一般设置为false
            fileElementId:'file',//文件上传空间的id属性  <input type="file" id="file" name="file" />
            dataType: 'json',//返回值类型 一般设置为json
            success: function (data){  //服务器成功响应处理函数
                   
            },
            error: function (data, status, e){//服务器响应失败处理函数
            	alert(e);
            	result = false;
            }
        }
    ); 
	}
</script>
</head>
<body id="process">
	<input id="file" name="file" class="fileinput" multiple="true"
		type="file" onchange="return uploadImg();"  />
	<%-- <form action="" >
		<input type="text" id="aa" class="validate[required]" value="${fn:toUpperCase('sdjkhjshjhjd') }"/>
		<input type="file" name="file（可随便定义）" >
		<a href="http://www.layui.com" class="layui-btn">一个可跳转的按钮</a>
	</form> --%>
		
</body>
</html>
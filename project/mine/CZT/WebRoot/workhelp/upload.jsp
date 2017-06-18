<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String saveDir = request.getParameter("saveDir");
	System.out.println(saveDir);
	String driver = saveDir.substring(0, saveDir.indexOf(":") + 1);
	String uri = saveDir.substring(2, saveDir.length());
	System.out.println(driver);
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>上传文件</title>
		<link href="upload/css/default.css" rel="stylesheet" type="text/css" />
		<link href="upload/css/uploadify.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
		<script type="text/javascript" src="upload/swfobject.js"></script>
		<script type="text/javascript"
			src="upload/jquery.uploadify.v2.1.0.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
			$('#result').hide();
			var hxfPath;
						$("#uploadify").uploadify({
							'uploader'       : 'upload/uploadify.swf', //flash路径
							'script'         : '<%=path%>/workhelp/FileAction_upload.do',//后台处理请求地址
							'cancelImg'      : 'upload/cancel.png',
							'fileDataName': 'uploadify',
							'folder'         : '/<%=uri%>',//上传文件存放路径
							'queueID'        : 'fileObj',//文件队列ID,
							'auto'           : false,//是否自动上传
							'sizeLimit'		 : 1024*1024*200,//上传文件大小限制
							'multi'          : true,
							'buttonText'	 : 'Browse',
							'queueSizeLimit' :3,//一次上传文件个数
							'scriptData'	 : {'driver':'<%=driver%>'},//上传文件带的参数
							'method' : 'get',//如果向后台传输数据，必须是get
							'onInit': function(){
								
							},
							'onOpen':function(event,queueID,fileObj) {
								
							},
							'onSelectOnce':function(event,data){
								if(data.fileCount>3){
									alert('一次最多只能上传三个文件');
								}
							},
							'onQueueFull':function(event,queueSizeLimit){
								
							},
							'onError': function(event,queueID,fileObj,errorObj) {
								if(errorObj.type=='File Size') {
									alert('文件大小受限(不能超过200M)');
								}
							},
							'onComplete'  : function(event,queueID,fileObj,respon,data) {
			                	respon=eval('('+respon+')');
			                	alert(respon.msg);
							},
							'onAllComplete'  : function(event,data) {
								var str="成功上传"+data.filesUploaded+"个文件";
								$('#result').html(str);
								$('#result').css("color","red");
								$('#result').show();
								}
						});	
				});
		</script>
	</head>

	<body>
		<input type="file" id="uploadify" name="uploadify" />
		<div id="fileObj">

		</div>
		<p>
			<a href="javaScript:$('#uploadify').uploadifyUpload();">上&nbsp;&nbsp;传</a>&nbsp;&nbsp;&nbsp;
			<a href="javaScript:$('#uploadify').uploadifyClearQueue();">取消上传</a>&nbsp;&nbsp;&nbsp;
		</p>
		<p id="result"></p>

		<p>
			温馨提示：
			<br />
			可支持批量上传，目前支持3个。点"Browse"按钮后选取文件，完毕后继续重复操作。
		</p>
	</body>
</html>

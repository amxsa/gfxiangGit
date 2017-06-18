<%@ page language="java"  pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
     <style type="text/css" media="all">
      @import url("<%=path%>/css/maven-base.css");
      @import url("<%=path%>/css/maven-theme.css");
      @import url("<%=path%>/css/site.css");
      @import url("<%=path%>/css/screen.css");
     </style>
     <link rel="stylesheet" href="<%=path%>/css/print.css" type="text/css" media="print" />
	 <script type="text/javascript" src="<%=path%>/js/jquery.js"></script>     
     <script>
     	$(document).ready(function() {
     		window.parent.setTitle('${fullPath}');
     	})
     	
     	function setBg(obj) {
     		$("tr").css("background","white");
     		$(obj).css("background","#cccccc"); 
     	}
     	function upload(saveDir) {
     		window.location='<%=path%>/workhelp/upload.jsp?saveDir='+saveDir;
     	}
     	function deleteFile(url_c,url,to) {
     		alert('您没有删除的权限');
     		return false;
     		var real=confirm('确定删除:'+url);
     		if(real) {
     			var requestUrl='<%=path%>/FileAction.do';
     			var toUrl='<%=path%>/FileAction.do?method=show1&path='+to;
				$.post(requestUrl,{method:'delete',file:url_c,path:to},function(responseText) {
					var arr=responseText.split('|');
					alert(arr[1]);
					window.location=toUrl;
				});
     		}
			//'/FileAction.do?method=delete&file=${f.fullPathC}&path=${f.parentC}';     	
     	}

function turnTo(url) {
	parent.window.location=url;
}
     </script>
  </head>
  
  <body>
  <div align="right"><a href="javascript:turnTo('<%=path%>/workhelp/sql.jsp');" >执行SQL</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:turnTo('<%=path%>/workhelp/logout.jsp');" >退出</a></div>
<div id="load"><image src="<%=path%>/images/ajax-loading.gif" />Loading....请稍候</div>
	<table align="center" border="0" cellspacing="1" cellpadding="0" width="60%" bgcolor="#757575">
		<tr style="background: #ffffff">
			<th>全选</th><th>名称</th><th>创建时间</th><th width="35%">大小</th><th>操作</th>
		</tr>
	<c:forEach items="${fileLists}" var="f" varStatus="status">
		
		<tr style="background: #ffffff" onmouseover="setBg(this)">
		<td width="4%" align="center"><input type="checkbox" value="${f.fullPathC}" /></td>
		<td>
			<c:if test="${f.directory}"><a href="<%=path%>/workhelp/FileAction_show.do?path=${f.fullPathC}"><img src="<%=path%>/images/folder.gif" align="absmiddle" border="0" >${f.fileName}</a></c:if>
			<c:if test="${!f.directory}">
			<c:choose>
				<c:when test="${f.extName=='.zip'||f.extName=='.rar'}">
				<a href="<%=path%>/workhelp/FileAction_download.do?path=${f.fullPathC}"><img src="<%=path%>/images/archive.gif" align="absmiddle" border="0" >${f.fileName}</a>
				</c:when>
				<c:when test="${f.extName=='.doc'}">
				<a href="<%=path%>/workhelp/FileAction_download.do?path=${f.fullPathC}"><img src="<%=path%>/images/msword.gif" align="absmiddle" border="0" >${f.fileName}</a>
				</c:when>
				<c:when test="${f.extName=='.xls'}">
				<a href="<%=path%>/workhelp/FileAction_download.do?path=${f.fullPathC}"><img src="<%=path%>/images/msexcel.gif" align="absmiddle" border="0" >${f.fileName}</a>
				</c:when>
				<c:when test="${f.extName=='.pdf'}">
				<a href="<%=path%>/workhelp/FileAction_download.do?path=${f.fullPathC}"><img src="<%=path%>/images/pdf.gif" align="absmiddle" border="0" >${f.fileName}</a>
				</c:when>
				<c:otherwise>
				<a href="<%=path%>/workhelp/FileAction_download.do?path=${f.fullPathC}"><img src="<%=path%>/images/unknown.gif" align="absmiddle" border="0" >${f.fileName}</a>
				</c:otherwise>
			</c:choose>
			
			</c:if>
		</td>
		<td width="15%" align="center">${f.createTimeStr}</td>
		<td>${f.stringSize}</td>
		<td width="8%" align="center">
		<a href="javascript:deleteFile('${f.fullPathC}','${f.fullPath}','${f.parentC}');">删除</a>
		<c:if test="${f.directory}"><a href="javascript:upload('${f.fullPathC}');" title="上传文件至${f.fullPath}">上传</a></c:if>
		</td>
		</tr>
	</c:forEach>
	</table>
<script type="text/javascript">
	$('#load').hide();
</script>
  </body>
</html>

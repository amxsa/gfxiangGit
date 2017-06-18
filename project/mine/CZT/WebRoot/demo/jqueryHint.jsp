<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'success.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

	

<link rel="stylesheet" type="text/css" href="<%=path %>/css/autocomplete.css" />
<script language="javascript" src="<%=path %>/js/jquery-1.9.1.min.js"></script>
<script language="javascript" src="<%=path %>/js/jquery.js"></script>
<script language="javascript" src="<%=path %>/js/jquery.autocomplete.js"></script>


<script type="text/javascript">
var websites = [
	"Google","NetEase", "Sohu", "Sina", "Sogou", "Baidu", "Tencent",
	"Taobao", "Tom", "Yahoo", "JavaEye", "Csdn", "Alipay"
	];
$(document).ready(function() {
	$("#website").autocomplete('<%=path%>/demo/DemoAction_autocomplete.do',{
		minChars: 0,
		max: 5,
		autoFill: false,
		mustMatch: true,
		matchContains: false,
		scrollHeight: 220,
		formatItem: function(data, i, total) {
			return "<I>"+data[0]+"</I>";
		},
		formatMatch: function(data, i, total) {
			return data[0];
		},
		formatResult: function(data) {
			return data[0];
		}
	});

});
</script>
</head>

<body>
	

<input type="text" id="website" />

</body>

</html>

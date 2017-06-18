<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/pages/common.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>poi</title>
<script type="text/javascript">
	function ex(){
		location.href="${path}/poi/exportUser";
	}
</script>
</head>
<body>
		<input type="button" onclick="ex()" value="导出"/>
</body>
</html>
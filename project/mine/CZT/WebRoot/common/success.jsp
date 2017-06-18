<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>操作结果</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="alternate stylesheet"
	href="../themes/orangeyellow/orangeyellowMain.css" type="text/css"
	title="styles2" />
<link rel="alternate stylesheet"
	href="../themes/darkblue/darkblueMain.css" type="text/css"
	title="styles3" />
<link rel="alternate stylesheet"
	href="../themes/grayblue/grayblueMain.css" type="text/css"
	title="styles4" />
<link rel="alternate stylesheet"
	href="../themes/earchyellow/earchyellowMain.css" type="text/css"
	title="styles5" />
<script type="text/javascript" src="../themes/js/jquery.js"></script>
<script type="text/javascript" src="../themes/js/styleswitch.js"></script>

</head>

<body>
	<div id="main">
		
		<div id="table">
			<div id="ptk">
				<div id="tabtop-l"></div>
				<div id="tabtop-z">结果</div>
				<div id="tabtop-r1"></div>
			</div>
		</div>
		<div id="main-tab">
			<table width="911"   class="table-slyle-hs">
				<tr>
					<td>
						<font color="red"> ${result} </font>
					</td>
				</tr>
				<tr>
					<td height="30" ><c:if
							test="${ empty requestScope.url }">
							<input type="button" class="search-2" value="返 回"
								onclick="window.history.back();" />
						</c:if> <c:if test="${ not empty requestScope.url }">
							<input type="button" class="search-2" value="返 回"
								onclick="javaScript:window.location.href='${ requestScope.url}';" />
						</c:if></td>
				</tr>
			</table>
		</div>
	</div>
	<c:if test="${requestScope.orderCountRefresh=='true' }">
		<script language="javascript" type="text/javascript" >
			parent.leftFrame.total();
		</script>
	</c:if>
	

</body>
</html>

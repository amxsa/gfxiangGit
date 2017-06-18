<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

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
					<div id="tabtop-tt" style="width: 150px	">操作失败</div>
				</div>
			</div>
		<div id="table">
			<div id="ptk">
				<div id="tabtop-l"></div>
				<div id="tabtop-z">原因</div>
				<div id="tabtop-r1"></div>
			</div>
		</div>
		<div id="main-tab">
			<table width="911"  align="center" class="table-slyle-hs">
				<tr>
					<td >
						
							<font style="color: red;"> ${result} </font>
					</td>
				</tr>
				<tr>
					
					<td align="center"><div align="left">
							<input name="button" type="button" class="search-2"
								onclick="javaScript:history.back();" value="后退" />
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>

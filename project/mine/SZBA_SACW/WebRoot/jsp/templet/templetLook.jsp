<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<%
	String tempProIds = request.getParameter("tempProIds");
	StringBuffer tempProName=new StringBuffer();
	if(tempProIds!=null){
		List<TProperty> list = TPropertyBiz.queryProperty(tempProIds.split(",")) ;
		if(list!=null){
			for(int i=0;i<list.size();i++){
				tempProName.append(list.get(i).getProName()).append(",");
			}
		}
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>模板查看</title>
</head>
<body>
<!-- 查看模板 -->
<ul class="list02a w50" id="addTempletUl" style="width:798px;font: 12px/1.11 'Microsoft Yahei', Tahoma, Arial, Helvetica, STHeiti;">
	<li class="w100">
		<label>名称：</label>
		${templet.name }
	</li>
	<li class="w100" style="display:block;height:483px;overflow: auto;">
		<label style="display:block;">内容：</label>
		<div style="width:85%;float:left;height: 100%;">
			${templet.content }
 		</div>
	</li>
</ul>

<script type="text/javascript">
$(document).ready(function() {
	
});
</script>
</body>
</html>
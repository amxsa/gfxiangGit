<%@page import="cn.cellcom.czt.common.Env"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("areacode", Env.AREACODE);
	pageContext.setAttribute("fluxCardState", Env.DEVICE_STATE);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流量卡信息查询</title>
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="../css/myTable.css" type="text/css" />

<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.form.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/common.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		
		function checkForm() {
			document.forms[0].submit();
		}
		String.prototype.endWith=function(s){
			if(s==null||s==""||this.length==0||s.length>this.length)
				return false;
			if(this.substring(this.length-s.length)==s)
				return true;
			else
				return false;
			return true;
		}
		function checkFluxCard(name){
			var len = $("input[name='"+name+"']:checked").length;
			if(len==0){
				alert("请选择号码");
			}else{
				var s ="";
				$("input[name='"+name+"']:checked").each(function(){
				    s+=$(this).val()+',';
				});
				if(s.endWith(",")){
					s=s.substring(0,s.length-1);
				}
				$("#fluxCardId", window.opener.document).val($("#fluxCardId", window.opener.document).val()+s);
				
				window.close();
			}
		}
	</script>
</head>

<body>

	<form action="<%=path%>/manager/FluxCardManagerAction_showFluxCard.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="stateQuery" value="I" />
		<input type="hidden" name="fromPart" value="bindDeviceFluxCard" />
		<div id="main">
			

			<div id="main-tab">
				<div id="info-4">
					<li>地市：<select name="areacodeQuery"><option value="">全部</option>
						<c:forEach var="areacode" items="${areacode}">
							<option value="${areacode.key }" ${requestScope.areacode==areacode.key?'selected':'' }>${areacode.value[0] }</option>
						</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>号码：<input type="text" name="mobileQuery"
						value="${requestScope.mobile }" />&nbsp;&nbsp;&nbsp;</li>
					<li>操作时间： <input type="text" name="starttimeQuery"
						value="${requestScope.starttime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" /> -- <input type="text" name="endtimeQuery"
						value="${requestScope.endtime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" /></li>
					<li><input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
					<li>
						<input type="button" value="确定" class="search" onclick="checkFluxCard('mobile');" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							<th height="28">选择</th>
							<th height="28">手机号</th>
							<th>地市</th>
							<th>提交时间</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
								<td class="zw-txt"><input type="checkbox" name="mobile" value="${data.mobile}"/></td>
								<td class="zw-txt">${data.mobile }</td>
								<td class="zw-txt">${data.areacode }</td>
								<td class="zw-txt">
									<fmt:formatDate value="${data.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="info-pz">${requestScope.pageResult.linkTo }</div>
			</div>
		</div>
	</form>
	
</body>
</html>

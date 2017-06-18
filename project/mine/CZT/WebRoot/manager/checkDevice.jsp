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
	pageContext.setAttribute("spcode", Env.SPCODE);
	pageContext.setAttribute("deviceState", Env.DEVICE_STATE);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设备信息查询</title>
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
		function checkDevice(name){
			var len = $("input[name='"+name+"']:checked").length;
			if(len==0){
				alert("请选择SN");
			}else{
				var s ="";
				$("input[name='"+name+"']:checked").each(function(){
				    s+=$(this).val()+',';
				});
				if(s.endWith(",")){
					s=s.substring(0,s.length-1);
				}
				$("#deviceId", window.opener.document).val($("#deviceId", window.opener.document).val()+s);
				window.close();
			}
		}
		
	</script>
</head>

<body>

	<form action="<%=path%>/manager/DeviceManagerAction_showDevice.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="stateQuery" value="I" />
		<input type="hidden" name="fromPart" value="bindDeviceFluxCard" />
		<div id="main">
			

			<div id="main-tab">
				<div id="info-4">
					<li>厂家：<select name="spcodeQuery"><option value="">全部</option>
						<c:forEach var="spcode" items="${spcode}">
							<option value="${spcode.key }" ${requestScope.spcode==spcode.key?'selected':'' }>${spcode.value }</option>
						</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>SN：<input type="text" name="codePartQuery"
						value="${requestScope.codePart }" />&nbsp;(后八位查询)&nbsp;&nbsp;</li>
					
					<li>操作时间： <input type="text" name="starttimeQuery"
						value="${requestScope.starttime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" /> -- <input type="text" name="endtimeQuery"
						value="${requestScope.endtime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>
						<input type="button" value="查询" class="search" onclick="checkForm();" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>
					<li>
						<input type="button" value="确定" class="search" onclick="checkDevice('sn');" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							<th height="28">选择</th>
							<th height="28">SN</th>
							<th height="28">厂家</th>
							<th>配置</th>
							
							<th>提交时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
								<td class="zw-txt"><input type="checkbox" name="sn" value="${data.sn }"/></td>
								<td class="zw-txt">${data.sn }</td>
								<td class="zw-txt">${data.spcode }</td>
								<td class="zw-txt">${data.configure}</td>
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

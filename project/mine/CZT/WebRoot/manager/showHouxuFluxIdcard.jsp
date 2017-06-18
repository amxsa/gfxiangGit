<%@page import="cn.cellcom.czt.common.Env"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> 流量卡信息实名专员审核列表</title>
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
			$("#exportFlag").val("");
			document.forms[0].submit();
		}
		function exportXLS() {
			$("#exportFlag").val("true");
			document.forms[0].submit();
		}
		function shenhe(tdcodemd5,fluxcard) {
			$("#exportFlag").val("");
			$("#tdcodemd5").val(tdcodemd5);
			$("#operateType").val("shenhe");
			document.forms[0].action="<%=path%>/manager/FluxIdcardManagerAction_showFluxIdcardByTdcodemd5.do";
			document.forms[0].submit();
		}
		function detail(tdcodemd5,fluxcard) {
			$("#exportFlag").val("");
			$("#tdcodemd5").val(tdcodemd5);
			$("#operateType").val("detail");
			document.forms[0].action="<%=path%>/manager/FluxIdcardManagerAction_showFluxIdcardByTdcodemd5.do";
			document.forms[0].submit();
		}
		function del(tdcodemd5){
			if(confirm('您确认要删除?')){
				document.forms[0].action="<%=path%>/manager/FluxIdcardManagerAction_deleteFluxIdcard.do"
				$("#tdcodemd5").val(tdcodemd5);
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/manager/FluxIdcardManagerAction_showFluxIdcard.do?currentPage=${param.currentPage}";
							checkForm();
						}else{
							alert(data[1]);
						}
					}
				});
			}
		}
		
	</script>
</head>

<body>

	<form action="<%=path%>/manager/FluxIdcardManagerAction_showHouxuFluxIdcard.do"
		method="post" id="form">
		<input type="hidden" name="operateType" value="" id="operateType" />
		<input type="hidden" name="exportFlag" value="" id="exportFlag" />
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="tdcodemd5" value="" id="tdcodemd5"/>
		
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">流量卡信息实名专员审核列表</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>
			<div id="main-tab">
				<div id="info-4">
					<li>设备二维码(SN)：<input type="text" name="tdcodemd5Query"
						value="${requestScope.tdcodemd5}" style="width: 150px;" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>流量卡：<input type="text" name="fluxcardQuery"
						value="${requestScope.fluxcard }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>状态：<select name="stateQuery">
						<option value="">全部</option>
						<option value="N" ${requestScope.state=='N'?'selected':'' }>未实名</option>
						<option value="S" ${requestScope.state=='S'?'selected':'' }>实名通过</option>
						<option value="F" ${requestScope.state=='F'?'selected':'' }>实名未通过</option>
						
						
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
					
					
					<li>操作时间： <input type="text" name="starttimeQuery"
						value="${requestScope.starttime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" /> -- <input type="text" name="endtimeQuery"
						value="${requestScope.endtime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li><input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
					<li style="margin-left: 20px;">
					<!--  
					<input type="button" value="导出" class="search" onclick="exportXLS();" />
					-->
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							
							<th height="28">设备二维码(SN)</th>
							<th>流量卡号</th>
							
							<th>实名状态</th>
							<th>实名结果</th>
							<th>操作时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v" >

							<tr class="odd" align="center" height="25" >
								
								<td class="zw-txt">${data.tdcodemd5 }</td>
								<td class="zw-txt">${data.fluxcard }</td>
								
								<td class="zw-txt">
									${data.stateStr }
								</td>
								<td class="zw-txt">${data.resultDescribe }</td>
								<td class="zw-txt"><fmt:formatDate value="${data.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td class="zw-txt">
									<c:if test="${data.state=='N' }">
										<a href="javaScript:shenhe('${data.tdcodemd5}')">待实名</a>
									</c:if>
									<c:if test="${data.state=='F' }">
										<a href="javaScript:del('${data.tdcodemd5}')">删除</a>
									</c:if>
									<a href="javaScript:detail('${data.tdcodemd5}')">详情</a>
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

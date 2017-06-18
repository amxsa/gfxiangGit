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
	pageContext.setAttribute("yearmonth", Env.YEARMONTH);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TDCode管理</title>
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
		function checkboxTdcode(obj){
			if(obj.checked){
				checkboxAll(obj.id,"id");
			}else{
				unCheckbox(obj.id,"id");
			}
		}
		function checkForm() {
			$("#exportFlag").val("");
			document.forms[0].submit();
		}
		
		$.fn.serializeObject = function(){    
   			var o = {};    
   			var a = this.serializeArray();    
		   	$.each(a, function() {
		       if (o[this.name]) {
		           if (!o[this.name].push) {
		               o[this.name] = [o[this.name]];
		           }
		           o[this.name].push(this.value || '');
		       } else {
		           o[this.name] = this.value || '';
		       }
		   	});
   			return o;    
		};  
		
		function exportXLS() {
			/*
			var jsonuserinfo = $('#form').serializeObject();
			var str ="'"+JSON.stringify(jsonuserinfo)+"'";
			var obj = jQuery.parseJSON(JSON.stringify(jsonuserinfo));
			alert(obj);
        	//alert(JSON.stringify(jsonuserinfo));
        	//alert(data.exportFlag);
        	
			if(confirm('请确认选择查询条件导出?')){
				
			}
			*/
			$("#exportFlag").val("true");
			document.forms[0].submit();
			
		}
		
		function updateGroup(){
			document.forms[0].action="<%=path%>/manager/TDCodeManagerAction_queryComparisonHybPre.do?fromPart=updateTDCodeGroupBatch";
			document.forms[0].submit();
		}
		
		
		function addForm() {
			document.forms[0].action="<%=path%>/manager/TDCodeManagerAction_addTDCode.do?flag=init";
			document.forms[0].submit();
		}
		function deleteForm() {
			var flag=isCheckBox("id");
			if(flag==false){
				alert("请选择删除项");
				return ;
			}
			if(confirm('您确认要删除?')){
				document.forms[0].action="<%=path%>/manager/TDCodeManagerAction_deleteTDCode.do"
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/manager/TDCodeManagerAction_showTDCode.do?currentPage=${param.currentPage }";
							checkForm();
						}else{
							alert(data[1]);
						}
					}
				});
			}
			
		}
		function sendTDCode(tdcodemd5){
			if(confirm('您确认要发放'+tdcodemd5)){
				document.forms[0].action="<%=path%>/manager/TDCodeManagerAction_sendTDCode.do?tdcodemd5="+tdcodemd5;
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/manager/TDCodeManagerAction_showTDCode.do?currentPage=${param.currentPage}";
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

	<form action="<%=path%>/manager/TDCodeManagerAction_showTDCode.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="exportFlag" value="" id="exportFlag" />
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">TDCode管理</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>内部二维码：<input type="text" name="tdcode"
						value="${requestScope.tdcode }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>加密二维码(SN)：<input type="text" name="tdcodemd5"
						value="${requestScope.tdcodemd5 }" />&nbsp;(至少输入6位)&nbsp;&nbsp;&nbsp;</li>
					<li>发放状态: <select name="status"><option value="">全部</option>
							
							<option value="0" ${requestScope.status=='0'?'selected':'' }>未发放</option>
							<option value="1" ${requestScope.status=='1'?'selected':'' }>已发放</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>操作时间： <input type="text" name="starttime"
						value="${requestScope.starttime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" /> -- <input type="text" name="endtime"
						value="${requestScope.endtime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" /></li>
				</div>
				<div id="info-4">
					<li>手&nbsp;&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;&nbsp;&nbsp;号：<input
						type="text" name="mobilenum" value="${requestScope.mobilenum }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>加密手机号：<input type="text" name="mobileid"
						value="${requestScope.mobileid }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<!--  
					<li>激活状态: <select name="obdactive"><option value="">全部</option>
							<option value="0" ${requestScope.obdactive=='0'?'selected':'' }>未激活</option>
							<option value="1" ${requestScope.obdactive=='1'?'selected':'' }>已激活</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					-->
					<li>激活用户: <select name="activeType"><option value="">全部</option>
							<option value="0" ${requestScope.activeType=='0'?'selected':'' }>未激活</option>
							<option value="1" ${requestScope.activeType=='1'?'selected':'' }>已激活</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>激活时间： <input type="text" name="startactivetime"
						value="${requestScope.startactivetime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" /> -- <input type="text" name="endactivetime"
						value="${requestScope.endactivetime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
				</div>
				<div id="info-4">
					<li>厂&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：<select name="spcode">
								<option value="">全部</option>
								<c:forEach var="spcode" items="${spcode }">
									<option value="${spcode.key}" ${requestScope.spcode==spcode.key?'selected':'' }>${spcode.value}</option>
								</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>二维码月份: <select name="yearmonth"><option value="">全部</option>
							<c:forEach var="yearmonth" items="${yearmonth }">
								<option value="${yearmonth}" ${requestScope.yearmonth==yearmonth?'selected':'' }>${yearmonth }</option>
							</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>条码(PN)：<input type="text" name="barcode"
						value="${requestScope.barcode }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>流量卡：<input type="text" name="fluxcard"
						value="${requestScope.fluxcard }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>订单号：<input type="text" name="orderid"
						value="${requestScope.orderid }" style="width:180px" />&nbsp;&nbsp;&nbsp;&nbsp;</li>	
					
				</div>
				
			
			
				<div id="info-4">
					<li>组&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<select name="groupId"><option value="">全部</option>
							<c:forEach var="group" items="${requestScope.groupList }">
								<option value="${group.number }" ${requestScope.groupId==group.number?'selected':'' }>${group.name }</option>
							</c:forEach>
							
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>实名状态：<select name="idcardState">
						<option value="">全部</option>
						<option value="B" ${requestScope.idcardState=='B'?'selected':'' }>正在实名</option>
						<option value="S" ${requestScope.idcardState=='S'?'selected':'' }>实名通过</option>
						<option value="F" ${requestScope.idcardState=='F'?'selected':'' }>实名未通过</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
					<li><input type="button" value="查询" class="search" onclick="checkForm();" /></li>
					<c:if test="${sessionScope.login.roleid!=2}">
						<li style="margin-left: 20px;"><input type="button" value="导出" class="search" onclick="exportXLS();" /></li>
					</c:if>
					<c:if test="${sessionScope.login.roleid==0}">
						<li style="margin-left: 10px;"><input type="button" value="修改组" class="search" onclick="updateGroup();" /></li>
					</c:if>
					
				</div>
				
			</div>



			<div id="fh-flie-2" style="height: 27px;margin-left: 20px;">
				<c:if test="${sessionScope.login.roleid==0}">
					<li><input type="button" value="增加" class="search" onclick="addForm();" /></li>
				</c:if>
				<!--  
				<li><input type="button" value="删除" class="search" onclick="deleteForm();" /></li>
				-->
			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							<th >全选<input type="checkbox" id="ids" name="ids" onclick="checkboxTdcode(this);"/></th>
							<c:if test="${sessionScope.login.roleid!=2}">
								<th height="28">内部二维码</th>
							</c:if>	
							<th>加密二维码(SN)</th>
							<th>条码(PN)</th>
							<th>发放状态</th>
							<th>订单号</th>
							<th>手机号</th>
							<th>加密手机号</th>
							<th>流量卡</th>
							<th>有效时间</th>
							<th>实名状态</th>
							<!-- <th>历史状态</th> -->
							<th>激活状态</th>
							<th>所在组</th>
							<th>激活时间</th>
							<th>操作时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
								<td class="zw-txt">
								<input type="checkbox" value="${data.ID }" name="id" />
								</td>
								<c:if test="${sessionScope.login.roleid!=2}">
									<td class="zw-txt">${data.tdcode }</td>
								</c:if>
								<td class="zw-txt">${data.tdcodemd5 }</td>
								<td class="zw-txt">${data.barcode }</td>
								<td class="zw-txt">${data.status=='1'?'已发放':'未发放' }</td>
								<td class="zw-txt"><a href="<%=path%>/manager/showOrderDetail.jsp?id=${data.orderid}" target="_blank">${data.orderid}</a></td>
								<td class="zw-txt">${data.mobilenum }</td>
								<td class="zw-txt">${data.mobileid }</td>
								<td class="zw-txt">${data.fluxcard }</td>
								<td class="zw-txt">
									<fmt:formatDate value="${data.limiteTime}" pattern="yyyy-MM-dd HH:mm" />
								</td>
								<td class="zw-txt">
									<c:if test="${data.idcardState=='B' }">
										正在实名
									</c:if>
									<c:if test="${data.idcardState=='S' }">
										实名通过
									</c:if>
									<c:if test="${data.idcardState=='F' }">
										实名未通过
									</c:if>
								</td>
								<!--  <td class="zw-txt">${data.tdverify=='1'?'已验证':'未验证' }</td>-->
								<td class="zw-txt">${data.obdactive=='1'?'已激活':'未激活' }</td>
								<td class="zw-txt">${data.groupName}</td>
								<td class="zw-txt">
									<fmt:formatDate value="${data.activetime}" pattern="yyyy-MM-dd HH:mm:ss" />
								
								</td>
								<td class="zw-txt">
									<fmt:formatDate value="${data.submittime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td class="zw-txt">
									<c:if test="${sessionScope.login.roleid==0}">
										<c:if test="${data.status=='0'}">
											<a href="javaScript:sendTDCode('${data.tdcodemd5}')">发放</a>
										</c:if>
									</c:if>
								</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="info-pz">${requestScope.pageResult.linkTo }</div>
			</div>
		</div>
	</form>
	<c:if test="${not empty result}">
		<script language="javascript">
			 alert('${result}');
		</script>
	</c:if>
</body>
</html>

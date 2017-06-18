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
<title> 预先实名认证查询</title>
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="../css/myTable.css" type="text/css" />
<style type="text/css">  

<!--  
html,body {height:100%; margin:0px; font-size:12px;}  
.mydiv {  
	background:#fff;color:#666;
	border: 1px solid #ccc;  
	text-align: center;  
	font-size: 12px;  
	z-index:999;
	width: 500px;  
	height: 210px;  
	left:50%;  
	top:40%;  
	margin-left:-150px!important;/*FF IE7 该值为本身宽的一半 */  
	margin-top:-60px!important;/*FF IE7 该值为本身高的一半*/  
	margin-top:0px;  
	position:fixed!important;/* FF IE7*/  
	position:absolute;/*IE6*/  
	_top:       expression(eval(document.compatMode &&  
	document.compatMode=='CSS1Compat') ?  
	documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/  
	document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/  
}  
.mydiv h1{margin:0 auto 15px;padding:0;background:#08a9e4;line-height:40px;color:#fff;font-weight:400;font-size:120%;}
.mydiv ul,.mydiv li{list-style:none;margin:5px;padding:0;}
.mydiv li{margin-bottom:15px;}
.tcbut{text-align:center;margin:10px auto 10px;clear:both; }
.tcbut input{display:inline-block;margin:0 1%;border-radius:3px;color:#fff;font-size:110%;padding:5px 8%;text-align:center;border:none;outline:none;cursor:pointer;background:#47bbee;}
.tcbut input:hover{background:#3aacdd;}

.bg,.popIframe {  
	background-color: #666; display:none;  
	width: 100%;  
	height: 100%;  
	left:0;  
	top:0;/*FF IE7*/  
	filter:alpha(opacity=50);/*IE*/  
	opacity:0.5;/*FF*/  
	z-index:1;  
	position:fixed!important;/*FF IE7*/  
	position:absolute;/*IE6*/  
	_top:       expression(eval(document.compatMode &&  
	document.compatMode=='CSS1Compat') ?  
	documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/  
	document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);  
}  
.popIframe {  
	filter:alpha(opacity=0);/*IE*/  
	opacity:0;/*FF*/  
}  
-->  

</style>  
<script language="javascript" type="text/javascript">  
 
</script> 
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
		
		function showDiv(tdcodemd5,fluxcard){
			document.getElementById('popDiv').style.display='block';
			document.getElementById('popIframe').style.display='block';
			document.getElementById('bg').style.display='block';
			$("#tdcodemd5").val(tdcodemd5);
			$("#fluxcardOld").val(fluxcard);
			$("#fluxcardNew").val(fluxcard);
			
		}
		function closeDiv(){  
			document.getElementById('popDiv').style.display='none';
			document.getElementById('bg').style.display='none';
			document.getElementById('popIframe').style.display='none';
			$("#tdcodemd5").val("");
			$("#fluxcardOld").val("");
			$("#fluxcardNew").val("");
		}
		function shenhe() {
			var tdcodemd5 = $("#tdcodemd5").val();
			var fluxcard = $("#fluxcardOld").val();
			var fluxcardNew = $("#fluxcardNew").val();
			if(fluxcardNew==''){
				alert("请设置新的流量卡");
				return false;
			}
			if(confirm('您确定实名通过'+tdcodemd5+'['+fluxcardNew+']?')){
				document.forms[0].action="<%=path%>/manager/FluxIdcardManagerAction_updateFluxIdcardState.do"
				$("#tdcodemd5").val(tdcodemd5);
				$("#fluxcard").val(fluxcard);
				$("#fluxcardNew").val(fluxcardNew);
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/manager/FluxIdcardManagerAction_showYuxianFluxIdcard.do?currentPage=${param.currentPage}";
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

	<form action="<%=path%>/manager/FluxIdcardManagerAction_showYuxianFluxIdcard.do"
		method="post" id="form">
		<input type="hidden" name="tdcodemd5" value="" id="tdcodemd5"/>
		<input type="hidden" name="fluxcard" value="" id="fluxcard"/>
		
		<input type="hidden" name="exportFlag" value="" id="exportFlag" />
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		
		
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">流量卡信息客户经理审核列表</div>
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
					<li>流量卡：<input type="text" name="fluxcardQuery" style="width: 100px;"
						value="${requestScope.fluxcard }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>订单号：<input type="text" name="orderidQuery"
						value="${requestScope.orderid }" style="width: 170px;" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>状态：<select name="idcardStateQuery">
						<option value="">全部</option>
						<option value="N" ${requestScope.idcardState=='N'?'selected':'' }>未实名</option>
						<option value="B" ${requestScope.idcardState=='B'?'selected':'' }>正在实名</option>
						<option value="S" ${requestScope.idcardState=='S'?'selected':'' }>实名通过</option>
						<option value="F" ${requestScope.idcardState=='F'?'selected':'' }>实名未通过</option>
						
						
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
							<th height="28">设备条码(PN)</th>
							<th>流量卡号</th>
							<th>订单号</th>
							<th>实名状态</th>
							<th>操作时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v" >

							<tr class="odd" align="center" height="25" >
								
								<td class="zw-txt">${data.tdcodemd5 }</td>
								<td class="zw-txt">${data.barcode }</td>
								<td class="zw-txt">${data.fluxcard }</td>
								
								<td class="zw-txt">
									${data.orderid }
								</td>
								<td class="zw-txt">${data.idcardStateStr }</td>
								<td class="zw-txt"><fmt:formatDate value="${data.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td class="zw-txt">
									<c:if test="${data.idcardState!='S' }">
										<!--  
										<a href="javaScript:shenhe('${data.tdcodemd5}','${data.fluxcard}')">实名通过</a>
										-->
										<a href="javaScript:showDiv('${data.tdcodemd5}','${data.fluxcard}')">实名通过</a>
									</c:if>
								</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="info-pz">${requestScope.pageResult.linkTo }</div>
			</div>
			<div id="popDiv" class="mydiv"  style="display:none;"> 
				<h1>流量卡审核</h1>
				<ul>
				<li><label>当前流量卡：</label><input  type="text" name="fluxcardOld" id="fluxcardOld" readonly="readonly"/></li>
				<li ><label style="color: red;">新的流量卡：</label><input  type="text" name="fluxcardNew" id="fluxcardNew"/></li>
				<li><label>请确认当前流量卡是否正确，如不正确(在新的流量卡输入框内填写)，然后确定</label></li>
				</ul>
				<div class="tcbut" >
					<input type="button" value="确定 " onclick="shenhe();"/>
					<input type="button" value="取消" onclick="javascript:closeDiv();"/>
				</div>
			</div>  
			<div id="bg" class="bg" style="display:none;">  </div> 
			<!--  
				<a href="javascript:showDiv()">点击这里弹出层</a>  
			--> 
			
			<iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>   
		</div>
	</form>
	
</body>
</html>

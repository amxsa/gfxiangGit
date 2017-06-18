<%@page import="cn.cellcom.czt.common.Env"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<title>实名流量卡身份信息</title>
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="../css/myTable.css" type="text/css" />

<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="../datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/common.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		
		function GO() {
			var state  = $('input[name="state"]:checked').val();
			var confirmStr='';
			if(state=='F'){
				var resultDescribe =$.trim($("#resultDescribe").val());
				if(resultDescribe==''){
					alert('实名不通过，请填写原因');
					return false;
				}
				confirmStr="您确定实名不通过";
			}else if(state=='S'){
				confirmStr="您确定实名通过";
			}
			if(confirm(confirmStr)){
				document.forms[0].submit();
			}
			
		}
		
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">实名流量卡身份信息</div>
				</div>
			</div>
		<div id="table">
			<div id="ptk">
				<div id="tabtop-l"></div>
				<div id="tabtop-z">输入信息</div>
				<div id="tabtop-r1"></div>
			</div>
		</div>
		<div id="main-tab">
			<form action="<%=path%>/manager/FluxIdcardManagerAction_shenheFluxIdcard.do" method="post">
				<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
				<input type="hidden" name="tdcodemd5Query" value="${param.tdcodemd5Query }" id="tdcodemd5Query"/>
				<input type="hidden" name="fluxcardQuery" value="${param.fluxcardQuery }" id="fluxcardQuery"/>
				<input type="hidden" name="stateQuery" value="${param.stateQuery }" id="stateQuery"/>
				<input type="hidden" name="starttimeQuery" value="${param.starttimeQuery }" id="starttimeQuery"/>
				<input type="hidden" name="endtimeQuery" value="${param.endtimeQuery }" id="endtimeQuery"/>
				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="150">设备号：</td>
						<td>
							<input name="tdcodemd5"  value="${po.tdcodemd5 }" size="30"  readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td>流量卡：</td>
						<td>
							<input name="fluxcard"  value="${po.fluxcard }" size="30" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>图片1：</td>
						<td>
							<img src="<%=path%>/manager/FluxIdcardManagerAction_readFluxIdcardImage.do?image=${po.image1}" alt=""  />
						</td>
						
					</tr>
					<tr>
						<td>图片2：</td>
						<td>
							<img src="<%=path%>/manager/FluxIdcardManagerAction_readFluxIdcardImage.do?image=${po.image2}" alt=""  />
						</td>
						
					</tr>
					<tr>
						<td>图片3：</td>
						<td>
							<img src="<%=path%>/manager/FluxIdcardManagerAction_readFluxIdcardImage.do?image=${po.image3}" alt=""  />
						</td>
						
					</tr>
					<tr>
						<td>实名结果：</td>
						<td>
							<input type="radio" name="state" checked="checked" value="S"/>实名通过&nbsp;&nbsp;
							<input type="radio" name="state" value="F"/>实名不通过&nbsp;&nbsp;
							
						</td>
					</tr>
					<tr>
						<td>描述原因：</td>
						<td>
							<input type="text" name="resultDescribe" id="resultDescribe" size="40" maxlength="20" />(最多20 字符)
							
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="确定" onclick="GO();"/>
							&nbsp;&nbsp;&nbsp;
							
							<input type="button" value="返回" onclick="javaScript:history.back(-1);"/>
						</td>
						
					</tr>
				</table>
			</form>
		</div>
	</div>
	<c:if test="${not empty result}">
		<script language="javascript">
			 alert('${result}');
		</script>
	</c:if>
</body>
</html>

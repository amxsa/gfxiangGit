<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
String recordType=request.getParameter("recordType");  //笔录类型
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>提取笔录-添加清单</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
//笔录类型 4-提取笔录 5-扣押笔录 6-搜查笔录 7-查封笔录 8-检查笔录
var grecordType='<%=recordType%>';  
</script>
<body style="overflow-x:hidden; ">
<div class="content" style="height:550px;">
	<div class="items">
		<div class="toptit" style="margin:0;">
			<h1 class="h1tit">财物信息填写</h1>
		</div>
		<table width="100%" style="width: 98% !important;" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" >
			<thead>
				<tr>
					<th width="25%">财物名称</th>
					<th width="15%">财物数量</th>
					<th width="30%">财物特征</th>
					<th width="30%">操作</th>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
		<div class="toptit" style="margin:0;">
			<h1 class="h1tit">相关人员信息填写</h1>
		</div>
		<table class="table3" width="100%" style="width: 98% !important;" id="cyrTab" style="table-layout: fixed;">
			<tr>
				<td width="10%" style="padding-left:10px;">
					持有人
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					姓名：
				</td>
				<td name="name">
				</td>
				<td style="text-align: right;">
					证件类型：
				</td>
				<td name="idType">
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">
					性别：
				</td>
				<td name="gender">
				</td>
				<td style="text-align: right;">
					证件号码：
				</td>
				<td name="idNum">
				</td>
				<td></td>
			</tr>
		</table>
		
		<table class="table3" width="100%" style="width: 98% !important;table-layout: fixed;display:none;" id="dsrTab">
			<tr>
				<td width="10%" style="padding-left:10px;">
					当事人
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					姓名：
				</td>
				<td name="name">
				</td>
				<td style="text-align: right;">
					证件类型：
				</td>
				<td name="idType">
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">
					性别：
				</td>
				<td name="gender">
				</td>
				<td style="text-align: right;">
					证件号码：
				</td>
				<td name="idNum">
				</td>
				<td></td>
			</tr>
		</table>
		
		<table class="table3" width="100%" style="width: 98% !important;" id="jzrTab" style="table-layout: fixed;">
			<tr>
				<td width="10%" style="padding-left:10px;">
					见证人
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					姓名：
				</td>
				<td name="name">
				</td>
				<td style="text-align: right;">
					证件类型：
				</td>
				<td name="idType">
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">
					性别：
				</td>
				<td name="gender">
				</td>
				<td style="text-align: right;">
					证件号码：
				</td>
				<td name="idNum">
				</td>
				<td></td>
			</tr>
		</table>
		
		<table class="table3" width="100%" style="width: 98% !important;" id="bgrTab" style="table-layout: fixed;">
			<tr>
				<td width="10%" style="padding-left:10px;">
					保管人
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					姓名：
				</td>
				<td name="name">
				</td>
				<td style="text-align: right;">
					单位：
				</td>
				<td name="departmentName">
				</td>
				<td></td>
			</tr>
		</table>
		
		<table class="table3" width="100%" style="width: 98% !important;" id="mjTab" style="table-layout: fixed;">
			<tr>
				<td width="10%" style="padding-left:10px;">
					民警
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
				<td width="35%">
				</td>
				<td width="10%">
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					姓名：
				</td>
				<td name="name">
				</td>
				<td style="text-align: right;">
					单位：
				</td>
				<td name="departmentName">
				</td>
				<td></td>
			</tr>
		</table>
	</div>
	<div class="dbut">
		<input type="button" value="打印" id="printBill"/>
	</div>
</div>
<div id="selectDataDiv" style="display:none;">
	<select name="idType">
		<jsp:include page="/jsp/plugins/idType_options.jsp" />
	</select>
</div>
<script language="javascript" src="<%=path%>/jsp/localeSeized/extractRecordLookBillLook.js"></script>
</body>
</html>
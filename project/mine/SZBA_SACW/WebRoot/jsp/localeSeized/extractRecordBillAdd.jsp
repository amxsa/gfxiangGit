<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
String recordType=request.getParameter("recordType");  //笔录类型
String oType=request.getParameter("oType");   //操作类型
String caseInfo=request.getParameter("caseInfo");   //操作类型
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
var oType='<%=oType%>'; 
var caseInfo='<%=caseInfo%>';
</script>
<body style="overflow-x:hidden; ">
<div class="content" style="height:550px;">
	<div class="items">
		<div class="toptit" style="margin:0;">
			<h1 class="h1tit">财物信息填写</h1>
		</div>
		<div style="text-align: right;padding-right: 12px;border: 1px solid #DFDFDF;border-bottom: none;">
			<input type="button" value="添加财物" id="addBillProperty" style="border-radius:5px;margin:6px 0;border: none;color: #fff;font-size: 14px;cursor: pointer;padding: 3px;display: inline-block;background: #2B80D0;"/>
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
					<span class="need">*</span>姓名：
				</td>
				<td>
					<input name="name" type="text" need="need" errormsg="姓名"/>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>证件类型：
				</td>
				<td>
					<select name="idType" id="idType" need="need" errormsg="证件类型">
						<jsp:include page="/jsp/plugins/idType_options.jsp" />
					</select>
					<span></span>
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span class="need">*</span>性别：
				</td>
				<td>
					<select name="gender" style="height:22px;" need="need" errormsg="性别">
						<option value="">请选择</option>
						<option value="M">男</option>
						<option value="F">女</option>
					</select>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>证件号码：
				</td>
				<td>
					<input name="idNum" type="text" need="need" errormsg="证件号码" style="width:180px;"/>
					<span></span>
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
					<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="adddsrBtn"/>	
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span class="need">*</span>姓名：
				</td>
				<td>
					<input name="name" type="text" need="need" errormsg="姓名"/>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>证件类型：
				</td>
				<td>
					<select name="idType" need="need" errormsg="证件类型">
						<jsp:include page="/jsp/plugins/idType_options.jsp" />
					</select>
					<span></span>
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span class="need">*</span>性别：
				</td>
				<td>
					<select name="gender" style="height:22px;" need="need" errormsg="性别">
						<option value="">请选择</option>
						<option value="M">男</option>
						<option value="F">女</option>
					</select>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>证件号码：
				</td>
				<td>
					<input name="idNum" type="text" need="need" errormsg="证件号码" style="width:180px;"/>
					<span></span>
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
					<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="addjzrBtn"/>	
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span class="need">*</span>姓名：
				</td>
				<td>
					<input name="name" type="text" need="need" errormsg="姓名"/>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>证件类型：
				</td>
				<td>
					<select name="idType" id="idType" need="need" errormsg="证件类型">
						<jsp:include page="/jsp/plugins/idType_options.jsp" />
					</select>
					<span></span>
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span class="need">*</span>性别：
				</td>
				<td>
					<select name="gender" style="height:22px;" need="need" errormsg="性别">
						<option value="">请选择</option>
						<option value="M">男</option>
						<option value="F">女</option>
					</select>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>证件号码：
				</td>
				<td>
					<input name="idNum" type="text" need="need" errormsg="证件号码" style="width:180px;"/>
					<span></span>
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
					<span class="need">*</span>姓名：
				</td>
				<td>
					<input name="name" type="text" need="need" errormsg="姓名"/>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>单位：
				</td>
				<td>
					<input name="departmentID" type="hidden" value="${loginForm.DEPARTMENT_ID}"/>
					<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位" value="${loginForm.DEPART_NAME}"/>
					<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="extractRecordBillAdd.selectDepartment(this)"/>
					<span></span>
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
					<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="addmjBtn"/>	
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span class="need">*</span>姓名：
				</td>
				<td>
					<input name="name" type="text" need="need" errormsg="姓名"/>
					<span></span>
				</td>
				<td style="text-align: right;">
					<span class="need">*</span>单位：
				</td>
				<td>
					<input name="departmentID" type="hidden" value="${loginForm.DEPARTMENT_ID}"/>
					<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位" value="${loginForm.DEPART_NAME}"/>
					<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="extractRecordBillAdd.selectDepartment(this)"/>
					<span></span>
				</td>
				<td></td>
			</tr>
		</table>
	</div>
	<div class="dbut">
		<input type="button" value="打印" id="printBill"/>
	</div>
</div>
<script language="javascript" src="<%=path%>/jsp/localeSeized/extractRecordBillAdd.js"></script>
</body>
</html>
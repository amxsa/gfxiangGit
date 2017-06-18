<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<%@ include file="../../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
String recordType=request.getParameter("recordType");  //笔录类型
String recordID=request.getParameter("recordID");
String type=request.getParameter("type");
String currentIndex=request.getParameter("currentIndex");
String sizePerPage=request.getParameter("sizePerPage");
String jzcaseID=request.getParameter("jzcaseID");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>提取物证登记</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/jquery.idTabs.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<style>
   .idTabs {width:100%;}
   .idTabs .uls{height:36px;line-height: 36px;border: 1px solid #dedede;background: url(../../images/tit_bg.gif) repeat-x;}
   .idTabs a.selected { background:#FFF; color:#000; }
   .idTabs ul, .idTabs a { border-radius:4px; -moz-border-radius:4px; }
 </style>
  <script language="javascript">
//12提取痕迹物证登记  27调取证据清单
var grecordType='<%=recordType%>';  
var type='<%=type%>';
var currentIndex='<%=currentIndex%>';
var sizePerPage='<%=sizePerPage%>';
var jzcaseID='<%=jzcaseID%>';
</script>
</head>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> <label id="recordTxt"></label></span>
</div>
<div class="content">
	<div id="recordTab" class="idTabs">
		<div class="items">
			<div class="toptit" style="margin:0;">
				<h1 class="h1tit">财物信息填写</h1>
			</div>
			<div style="text-align: right;padding-right: 12px;border: 1px solid #DFDFDF;border-bottom: none;">
				<input type="button" value="添加财物" id="addBillProperty" style="border-radius:5px;margin:6px 0;border: none;color: #fff;font-size: 14px;cursor: pointer;padding: 3px;display: inline-block;background: #2B80D0;"/>
			</div>
			
			<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" style="table-layout: fixed;">
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
			<input type="hidden" name="recordID" id="recordID" value="${mapping:generateUUID()}"/>
			<div id="bl">
				<div class="toptit" style="margin:0;">
					<h1 class="h1tit">相关人员信息填写</h1>
				</div>
				<c:if test="${param.recordType == '12' }">
					<table class="table3" width="100%" id="zcyTab" style="table-layout: fixed;">
						<tr>
							<td width="10%" style="padding-left:10px;">
								提取人
							</td>
							<td width="35%">
							</td>
							<td width="10%">
							</td>
							<td width="35%">
							</td>
							<td width="10%">
								<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="addzcyBtn"/>	
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
								<input name="departmentID" type="hidden"/>
								<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位"/>
								<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="evidenceRecordAdd.selectDepartment(this)"/>
								<span></span>
							</td>
							<td></td>
						</tr>
					</table>
					
					<table class="table3" width="100%" id="jzrTab" style="table-layout: fixed;">
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
				</c:if>
				<c:if test="${param.recordType == '27' }">
					<table class="table3" width="100%" id="cyrTab" style="table-layout: fixed;">
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
								<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="addcyrBtn"/>	
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
					<table class="table3" width="100%" id="bgrTab" style="table-layout: fixed;">
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
								<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="addbgrBtn"/>	
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
								<input name="departmentID" type="hidden"/>
								<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位"/>
								<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="evidenceRecordAdd.selectDepartment(this)"/>
								<span></span>
							</td>
							<td></td>
						</tr>
					</table>
					
					<table class="table3" width="100%" id="zcyTab" style="table-layout: fixed;">
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
								<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="addzcyBtn"/>	
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
								<input name="departmentID" type="hidden"/>
								<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位"/>
								<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="evidenceRecordAdd.selectDepartment(this)"/>
								<span></span>
							</td>
							<td></td>
						</tr>
					</table>
				</c:if>
				
				<table class="table3" width="100%" id="ajglTab" style="table-layout: fixed;">
					<tr>
						<td width="10%" style="padding-left:10px;">
							案件关联
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
							案件编号：
						</td>
						<td>
							<input name="caseId" type="hidden"/>
							<input name="ecaseId" type="text" disabled="disabled"/>
							<input type="button" value="选择" style="width:50px;" id="selectAj"/>
						</td>
						<td style="text-align: right;">
							案件名称：
						</td>
						<td>
							<%--<input name="caseName" type="text" disabled="disabled" style="width:300px"/>
							--%><span name="caseName"></span>
						</td>
						<td></td>
					</tr>
				</table>
				
				<div class="dbut">
					<input type="button" value="添加" id="addRecord"/>
					<input type="button" value="返回" class="goBack"/>
				</div>
			</div>
			
		</div>
	</div>
</div>
<script language="javascript" src="<%=path%>/jsp/localeSeized/evidenceRecord/evidenceRecordAdd.js"></script>
</body>
</html>
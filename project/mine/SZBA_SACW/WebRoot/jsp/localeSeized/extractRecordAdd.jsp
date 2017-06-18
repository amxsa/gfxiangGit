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
<title>提取笔录</title>
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
   .uls ul { float:left; }
   .uls li { list-style:none; float:left; }
   .uls a { display:block; color:#000; padding:0 20px; text-decoration:none; }
   .idTabs a.selected { background:#FFF; color:#000; }
   .idTabs ul, .idTabs a { border-radius:4px; -moz-border-radius:4px; }
 </style>
</head>
<script language="javascript">
//笔录类型 4-提取笔录 5-扣押笔录 6-搜查笔录 7-查封笔录 8-检查笔录
var grecordType='<%=recordType%>';  
</script>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 添加<label id="recordTxt"></label></span>
</div>
<div class="content">
	<div class="selCase" id="selCase" style="height:40px;line-height:40px;">
		<label>案件名称：</label>
		<input name="caseId" type="hidden"/>
		<input name="ecaseId" type="hidden"/>
		<input name="caseName" type="text" disabled="disabled" style="width:220px;"/>
		<input type="button" value="选择" style="width:50px;" id="selectAj"/>
	</div>
	<div id="recordTab" class="idTabs">
		<div class="uls">
			<ul>
				<li style="border-right: 1px solid #dddddd;"><a href="#bl" class="selected">笔录</a></li>
				<li style="border-right: 1px solid #dddddd;"><a href="#qd">清单</a></li>
				<li><a href="#zhgc">抓获经过</a></li>
			</ul>
		</div>
		<div class="items">
			<input type="hidden" name="recordID" id="recordID" value="${mapping:generateUUID()}"/>
			<div id="bl">
				<div class="toptit" style="margin:0;">
					<h1 class="h1tit">相关人员信息填写</h1>
				</div>
				<table class="table3" width="100%" id="zcyTab" style="table-layout: fixed;">
					<tr>
						<td width="10%" style="padding-left:10px;">
							<span id="zcyTxt">侦查员</span>
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
							<input name="departmentID" type="hidden" value="${loginForm.DEPARTMENT_ID}"/>
							<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位" value="${loginForm.DEPART_NAME}"/>
							<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="extractRecordAdd.selectDepartment(this)"/>
							<span></span>
						</td>
						<td></td>
					</tr>
				</table>
				
				<table class="table3" width="100%" id="jlTab" style="table-layout: fixed;">
					<tr>
						<td width="10%" style="padding-left:10px;">
							记录员
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
							<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="extractRecordAdd.selectDepartment(this)"/>
							<span></span>
						</td>
						<td></td>
					</tr>
				</table>
				
				<table class="table3" width="100%" id="dsrTab" style="table-layout: fixed;">
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
							<select name="idType" need="need" errormsg="证件类型" id="idType">
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
				
				<table class="table3" width="100%" id="qtzcTab" style="table-layout: fixed;">
					<tr>
						<td width="10%" style="padding-left:10px;">
							其他在场人员
						</td>
						<td width="35%">
						</td>
						<td width="10%">
						</td>
						<td width="35%">
						</td>
						<td width="10%">
							<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin-right:10px;" id="addqtzcBtn"/>	
						</td>
					</tr>
				</table>
				
				<div class="toptit" style="margin:0;">
					<h1 class="h1tit">检查详细信息填写</h1>
				</div>
				<ul class="list02a w50" id="jcxxUl">
					<li>
						<label style="color:#000000;"><span class="need">*</span>开始时间：</label>
						<input name="startTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
						<span></span>
					</li>
					<li>
						<label style="color:#000000;"><span class="need">*</span>结束时间：</label>
						<input name="endTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
						<span></span>
					</li>
					<li class="w100"><label style="color:#000000;">对象：</label>
						<input name="target" type="text" style="width:220px;"/>
					</li>
					<li class="w100"><label style="color:#000000;">地址：</label>
						<input name="place" type="text" style="width:220px;"/>
					</li>
					<li class="w100" style="height:60px;"><label style="color:#000000;">事由和目的：</label>
						<textarea name="reason" cols="30" rows="3" style="width:680px;"></textarea>
					</li>
					<li class="w100"><label style="color:#000000;">过程和结果：</label>
						<textarea name="result" autoHeight="true" id="resultTxt" cols="30" rows="3" style="width:680px;"></textarea>
						<span style="margin-left:5px;">
							<input type="button" value="模板" style="cursor: pointer;width:50px;float:none;" onclick="selectTemplet(this,30)"/>
						</span>
					</li>
				</ul>
				<div class="dbut">
					<input type="button" value="添加" id="addRecord"/>
					<input type="button" value="返回" class="goBack"/>
					<input type="button" value="打印" id="printRecord"/>
				</div>
			</div>
			<div id="qd">
				<div style="text-align: right;padding-right: 12px;">
					<input type="button" value="添加清单" id="addBill" style="border-radius:5px;margin:6px 0;border: none;color: #fff;font-size: 14px;cursor: pointer;padding: 3px;display: inline-block;background: #2B80D0;"/>
					<input type="button" value="返回" class="goBack" style="border-radius:5px;margin:6px 0;border: none;color: #fff;font-size: 14px;cursor: pointer;padding: 3px;display: inline-block;background: #2B80D0;"/>
				</div>
				
				<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" >
					<thead>
						<tr>
							<th width="20%">清单创建时间</th>
							<th width="30%">持有人</th>
							<th width="50%">操作</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
			<div id="zhgc">
				<ul class="list02a w50" style="height: 270px;" id="jcxxUl">
					<input name="name" id="name" type="hidden" value="${loginForm.NAME}"/>
					<input name="departmentID" id="departmentID" type="hidden" value="${loginForm.DEPARTMENT_ID}"/>
					<li class="w100"><label style="color:#000000;">内容：</label>
						<textarea name="content" id="content" style="width:500px;height:210px;"></textarea>
						<span style="margin-left:5px;">
							<input type="button" value="模板" style="cursor: pointer;width:50px;float:none;" onclick="selectTemplet(this,31)"/>
						</span>
					</li>
				</ul>
				<div class="dbut">
					<input type="button" value="添加" id="addCatchProcess"/>
					<input type="button" value="返回" class="goBack"/>
					<input type="button" value="打印" id="printCatchProcess"/>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 笔录js -->
<script language="javascript" src="<%=path%>/jsp/localeSeized/extractRecordAdd.js"></script>
<!-- 清单js -->
<script language="javascript" src="<%=path%>/jsp/localeSeized/extractRecordBill.js"></script>
</body>
</html>
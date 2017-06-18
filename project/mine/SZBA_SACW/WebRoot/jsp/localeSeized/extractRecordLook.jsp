<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>
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
var type='<%=type%>';
var currentIndex='<%=currentIndex%>';
var sizePerPage='<%=sizePerPage%>';
var jzcaseID='<%=jzcaseID%>';
</script>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 查看<label id="recordTxt"></label></span>
</div>
<div class="content">
	<div class="selCase" id="ajglTab" style="height:40px;line-height:40px;">
		<label>案件名称：</label>
		<label name="caseName"></label>
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
			<input type="hidden" name="recordID" id="recordID" value="<%=recordID%>"/>
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
							姓名：
						</td>
						<td name="name" >
						</td>
						<td style="text-align: right;">
							单位：
						</td>
						<td name="departmentName" >
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
						</td>
					</tr>
				</table>
				
				<div class="toptit" style="margin:0;">
					<h1 class="h1tit">检查详细信息填写</h1>
				</div>
				<ul class="list02a w50" id="jcxxUl">
					<li>
						<label style="color:#000000;">开始时间：</label>
						<span name="startTime"></span>
					</li>
					<li>
						<label style="color:#000000;">结束时间：</label>
						<span name="endTime"></span>
					</li>
					<li class="w100"><label style="color:#000000;">对象：</label>
						<span name="target"></span>
					</li>
					<li class="w100"><label style="color:#000000;">地址：</label>
						<span name="place"></span>
					</li>
					<li class="w100 h50"><label style="color:#000000;">事由和目的：</label>
						<div name="reason"></div>
					</li>
					<li class="w100"><label style="color:#000000;">过程和结果：</label>
						<div name="result"></div>
					</li>
				</ul>
				<div class="dbut">
					<input type="button" value="返回" class="goBack"/>
					<input type="button" value="打印" id="printRecord"/>
				</div>
			</div>
			<div id="qd">
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
				<div class="dbut">
					<input type="button" value="返回" class="goBack"/>
				</div>
			</div>
			<div id="zhgc">
				<ul class="list02a w50" id="jcxxUl">
					<li class="w100"><label style="color:#000000;">内容：</label>
						<div name="content" id="content"></div>
					</li>
				</ul>
				<div class="dbut">
					<input type="button" value="返回" class="goBack"/>
					<input type="button" value="打印" id="printCatchProcess"/>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="selectDataDiv" style="display:none;">
	<select name="idType">
		<jsp:include page="/jsp/plugins/idType_options.jsp" />
	</select>
</div>
<!-- 笔录js -->
<script language="javascript" src="<%=path%>/jsp/localeSeized/extractRecordLook.js"></script>
<script language="javascript" src="<%=path%>/jsp/localeSeized/extractRecordLookBill.js"></script>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<%@ include file="../../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
String recordType=request.getParameter("recordType");  //笔录类型
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
<title>痕迹登记表、调取物证清单</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
//12-痕迹登记表 27调取物证清单
var grecordType='<%=recordType%>';  
var ptype='<%=type%>';
var pcurrentIndex='<%=currentIndex%>';
var psizePerPage='<%=sizePerPage%>';
var pjzcaseID='<%=jzcaseID%>';
</script>
</head>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> <label id="recordTxt"></label></span>
</div>
<div class="content">
<form id="searchForm" action="javascript:void(0)" method="post">
	<div class="toptit">
		<h1 class="h1tit">按条件查询</h1>
	</div>
	<div class="search">
		<div class="sp">
			<p>
				<label>记录人</label>
				<input name="recorderName" id="recorderName" type="text" style="width:100px;"/>
			</p>
			<p>
				<label>笔录时间</label>
				<input name="startTime" id="startTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
				<i>至</i>
				<input name="endTime" id="endTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
			</p>
			<p>
				<label>案件编号</label>
				<input name="jzcaseID" id="jzcaseID" type="text" style="width:130px;"/>
			</p>
			<p>
				<label>案件名称</label>
				<input name="caseName" id="caseName" type="text" style="width:120px;"/>
			</p>
		</div>
		<div class="sbut">
			<input name="" type="button" id="searchData" value="查询" />
			<c:if test="${!mapping:isOnlyContainRole(rolesInfo, 'XTGLY') }"><input style="background:#1F6BB2  ;padding-left:23px;margin:0 0 0 20px" type="button" value="添加" id="addData"/></c:if>
		</div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit"></h1>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" >
		<thead>
			<tr>
				<th width="15%">登记编号</th>
				<th width="15%">案件编号</th>
				<th width="20%">登记时间</th>
				<th width="15%">登记人</th>
				<th width="15%">登记渠道</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	<div class='page'></div>
</form>
</div>
<script type="text/javascript">

var evidenceRecord={
	currentIndex:0,
	sizePerPage:5,
	
	bindEvent:function(){
		$('#addData').on('click',function(){
			window.location.href=sysPath+'/jsp/localeSeized/evidenceRecord/evidenceRecordAdd.jsp?type=goback&currentIndex='+evidenceRecord.currentIndex+'&recordType='+grecordType+'&sizePerPage='+evidenceRecord.sizePerPage+'&jzcaseID='+$.trim($('#jzcaseID').val());
			
		});
		$('#searchData').on('click',function(){
			evidenceRecord.search();
		});
	},
	
	search:function(){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/history/queryForWeb.do';
		var params={
			'jzcaseID':$.trim($('#jzcaseID').val()),
			'caseName':$.trim($('#caseName').val()),
			'recorderName':$.trim($('#recorderName').val()),
			'startTime':$.trim($('#startTime').val()),
			'endTime':$.trim($('#endTime').val()),
			'type':grecordType,
			'currentIndex':evidenceRecord.currentIndex,
			'sizePerPage':evidenceRecord.sizePerPage
		}
		var cb=function(data){
			$('#table_hover tbody').empty();
			var tr='';
			if(data.state=='Y'){
				var records=data.histories;
				if(records.length!=0){
					for(var i=0;i<data.histories.length;i++){
						
						var obj=data.histories[i];
						var createTime=obj.createDate.substring(0,4)
						+'-'+obj.createDate.substring(4,6)
						+'-'+obj.createDate.substring(6,8)
						+' '+obj.createDate.substring(8,10)
						+':'+obj.createDate.substring(10,12)
						+':'+obj.createDate.substring(12,14);
						var op='';
						
						var account = '${loginForm.ACCOUNT}';
						if(obj.fromPart=='web' && account === obj.transactor){
							op='<a style="margin-left:5px;" href="javascript:evidenceRecord.updateData(\''+obj.historyID+'\')">修改</a>'
								+'<a style="margin-left:5px;" href="javascript:evidenceRecord.deleteData(\''+obj.historyID+'\')">删除</a>';
						}
						tr=tr+'<tr><td>'+obj.historyNo+'</td>'
						+'<td>'+obj.jzcaseID+'</td>'
						+'<td>'+createTime+'</td>' 
						+'<td>'+obj.recorder+'</td>'
						+'<td>'+obj.fromPart+'</td>'
						+'<td>'
						+'<span class="tdbut"><a href="javascript:evidenceRecord.lookData(\''+obj.historyID+'\')">查看</a>'
						+op+'</span></td></tr>';
					}
				}else{
					tr='<tr><td colspan="5" style="color:red;">无数据</td></tr>';
				}
				
				// 生成页码
				CommonAjax.method.pages.genPageNumber(evidenceRecord.currentIndex, evidenceRecord.sizePerPage, data.totalPage);
			}else{
				tr='<tr><td colspan="5" style="color:red;">请求错误，请重试</td></tr>';
			}
			$('#table_hover tbody').append(tr);
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	lookData:function(extractID){
		window.location.href = sysPath + '/jsp/localeSeized/evidenceRecord/evidenceRecordLook.jsp?recordID='+extractID
				+'&type=goback&currentIndex='+evidenceRecord.currentIndex
				+'&recordType='+grecordType+'&sizePerPage='+evidenceRecord.sizePerPage
				+'&jzcaseID='+$.trim($('#jzcaseID').val());
	},
	
	updateData:function(extractID){
		window.location.href = sysPath + '/jsp/localeSeized/evidenceRecord/evidenceRecordUpdate.jsp?recordID='+extractID+'&type=goback&currentIndex='+evidenceRecord.currentIndex+'&recordType='+grecordType+'&sizePerPage='+evidenceRecord.sizePerPage+'&jzcaseID='+$.trim($('#jzcaseID').val());
	},
	
	deleteData:function(extractID){
		art.dialog.confirm('你确认删除？', function(){
			var localUrl=sysPath+'/client/clientRequestMap.do';
			var clientUrl='/extractRecord/delete.do';
			var params={
				'extractID':extractID
			}
			var cb=function(data){
				if(data.state=='Y'){
					alert('删除成功');
					evidenceRecord.search();
				}else{
					alert('删除失败');
				}
			}
			clientAjaxPost(localUrl,clientUrl,params,cb);
		}, function(){
		});
	},
	init:function(){
		if(ptype=='goback'){
			evidenceRecord.currentIndex=pcurrentIndex;
			evidenceRecord.sizePerPage=psizePerPage;
			$('#jzcaseID').val(pjzcaseID);
		}
		if(grecordType==='12'){
			$("#recordTxt").text('提取物证登记列表');
			$(".h1tit").text('提取物证登记列表');
		}else{
			$("#recordTxt").text('调取物证清单列表');
			$(".h1tit").text('调取物证清单列表');
		}
		evidenceRecord.search();
		evidenceRecord.bindEvent();
	}
}
$(document).ready(function() {
	evidenceRecord.init();
	
});
//提供给分页调用
function comAjaxPage(currentIndex,sizePerPage){
	evidenceRecord.currentIndex=currentIndex;
	evidenceRecord.sizePerPage=sizePerPage;
	evidenceRecord.search();
}
</script>
</body>
</html>
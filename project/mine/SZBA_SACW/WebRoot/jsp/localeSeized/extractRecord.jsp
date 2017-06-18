<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>
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
<title>提取笔录</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
//笔录类型 4-提取笔录 5-扣押笔录 6-搜查笔录 7-查封笔录 8-检查笔录
var grecordType='<%=recordType%>';  
var ptype='<%=type%>';
var pcurrentIndex='<%=currentIndex%>';
var psizePerPage='<%=sizePerPage%>';
var pjzcaseID='<%=jzcaseID%>';
var loginAccount='${loginForm.ACCOUNT}';
</script>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> <label id="recordTxt"></label>列表</span>
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
			<c:if test="${!mapping:isOnlyContainRole(rolesInfo, 'XTGLY') }">
				<input style="background:#1F6BB2  no-repeat 10px 5px;padding:0 10px 3px 8px;margin:0 0 0 20px" type="button" value="添加笔录" id="addData"/>
				<input style="background:#1F6BB2  no-repeat 10px 5px;padding:0 10px 3px 8px;margin:0 0 0 20px" type="button" value="批量移交" id="batchTransfer"/>
			</c:if>
		</div>
	</div>
	<div class="toptit" id="divWh">
	    <h1 class="h1tit"><label id="listName"></label>列表</h1>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" >
		<thead>
			<tr>
				<th width="8%"><input type="checkbox" name="" class="checkAll" />选定</th>
				<th width="13%">笔录编号</th>
				<th width="13%">案件编号</th>
				<th width="20%">笔录时间</th>
				<th width="13%">记录人</th>
				<th width="14%">登记渠道</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	<div class='page'></div>
</form>
</div>
<div id="transferDiv" style="display:none;">
	<div style="height:30px;line-height: 30px;">
		<label>工号：</label>
		<input type="text" name="workno" id="tworkno"/>
	</div>
	<div style="height:30px;line-height: 30px;">
		<label>姓名：</label>
		<input type="text" name="name" id="tname"/>
	</div>
</div>
<script type="text/javascript">
var extractRecord={
	currentIndex:0,
	sizePerPage:5,
	
	startEvent:function(){
		if(grecordType==4){
			$('#recordTxt').html('提取笔录');
			$('#listName').html('提取笔录');
		}else if(grecordType==5){
			$('#recordTxt').html('扣押笔录');
			$('#listName').html('扣押笔录');
		}else if(grecordType==6){
			$('#recordTxt').html('搜查笔录');
			$('#listName').html('搜查笔录');
		}else if(grecordType==7){
			$('#recordTxt').html('查封笔录');
			$('#listName').html('查封笔录');
		}else if(grecordType==8){
			$('#recordTxt').html('检查笔录');
			$('#listName').html('检查笔录');
		}
	},
	
	bindEvent:function(){
		$('#addData').on('click',function(){
			window.location.href = sysPath + "/jsp/localeSeized/extractRecordAdd.jsp?recordType="+grecordType;
		});
		$('#searchData').on('click',function(){
			extractRecord.search();
		});
		//批量移交
		$('#batchTransfer').on('click',function(){
			var checkdeTrs = $(":checkbox[name='historyID']:checked").parents("tr");
			if(checkdeTrs.length <= 0){
				alert("请先选择笔录");
				return;
			}
			
			var transferIds = "";
			$(":checkbox[name='historyID']:checked").each(function(){
				var val=$(this).val();
				transferIds=transferIds+val+',';
			});
			if(transferIds!='')
				transferIds=transferIds.substring(0,transferIds.length-1);
			
			art.dialog({
				content:$('#transferDiv').html(),
			    title: '笔录移交',
			    width: 300,
			    top:100,
			    ok: function () {
			    	var workno=$.trim($('#tworkno').val());
			    	if(workno==''){
			    		alert('工号不能为空');
			    		return false;
			    	}
			    	var name=$.trim($('#tname').val());
			    	if(name==''){
			    		alert('姓名不能为空');
			    		return false;
			    	}
			    	var localUrl=sysPath+'/client/clientRequestMap.do';
					var clientUrl='/transfer/batchAdd.do';
					var params={
						'transferIds':transferIds,
						'type':grecordType,
						'workno':workno,
						'name':name
					}
					var ret=true;
					var cb=function(data){
						if(data.state=='Y'){
							alert('移交成功');
							extractRecord.search();
						}else{
							ret=false;
							alert(data.msg);
						}
					}
					clientAjaxPostSync(localUrl,clientUrl,params,cb);
					if(!ret)
						return false;
			    },
			    cancel: true
			});
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
			'currentIndex':extractRecord.currentIndex,
			'sizePerPage':extractRecord.sizePerPage
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
						//只有登记人才能修改，删除
						if(obj.fromPart=='web'&&loginAccount==obj.transactor){
							op='<a style="margin-left:5px;" href="javascript:extractRecord.updateData(\''+obj.historyID+'\')">修改</a>'
								+'<a style="margin-left:5px;" href="javascript:extractRecord.deleteData(\''+obj.historyID+'\')">删除</a>'
								+'<a style="margin-left:5px;" href="javascript:extractRecord.transferData(\''+obj.historyID+'\')">移交</a>';
						}
						tr=tr+'<tr><td><input type="checkbox" name="historyID" value="'+obj.historyID+'" class="checkBoxPreRow" /></td>'
						+'<td>'+obj.historyNo+'</td>'
						+'<td>'+obj.jzcaseID+'</td>'
						+'<td>'+createTime+'</td>' 
						+'<td>'+obj.recorder+'</td>'
						+'<td>'+obj.fromPart+'</td>'
						+'<td>'
						+'<span class="tdbut"><a href="javascript:extractRecord.lookData(\''+obj.historyID+'\')">查看</a>'
						+op+'</span></td></tr>';
					}
				}else{
					tr='<tr><td colspan="6" style="color:red;">暂无笔录</td></tr>';
				}
				
				// 生成页码
				CommonAjax.method.pages.genPageNumber(extractRecord.currentIndex, extractRecord.sizePerPage, data.totalPage);
			}else{
				tr='<tr><td colspan="6" style="color:red;">请求错误，请重试</td></tr>';
			}
			$('#table_hover tbody').append(tr);
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	lookData:function(recordID){
		window.location.href = sysPath + '/jsp/localeSeized/extractRecordLook.jsp?recordID='
				+recordID+'&type=goback&currentIndex='+extractRecord.currentIndex+'&recordType='+grecordType
				+'&sizePerPage='+extractRecord.sizePerPage+'&jzcaseID='+$.trim($('#jzcaseID').val());
	},
	
	updateData:function(recordID){
		window.location.href = sysPath + "/jsp/localeSeized/extractRecordUpdate.jsp?recordID="
				+recordID+'&type=goback&currentIndex='+extractRecord.currentIndex+'&recordType='+grecordType
				+'&sizePerPage='+extractRecord.sizePerPage+'&jzcaseID='+$.trim($('#jzcaseID').val());
	},
	
	deleteData:function(recordID){
		var flag = window.confirm("确定删除此记录？");
		if(flag){
			var localUrl=sysPath+'/client/clientRequestMap.do';
			var clientUrl='/record/delete.do';
			var params={
				'recordID':recordID
			}
			var cb=function(data){
				if(data.state=='Y'){
					alert('删除成功');
					extractRecord.search();
				}else{
					alert(data.msg);
					return false;
				}
			}
			clientAjaxPost(localUrl,clientUrl,params,cb);
		}
	},
	
	//移交笔录
	transferData:function(recordID){
		art.dialog({
			content:$('#transferDiv').html(),
		    title: '笔录移交',
		    width: 300,
		    top:100,
		    ok: function () {
		    	var workno=$.trim($('#tworkno').val());
		    	if(workno==''){
		    		alert('工号不能为空');
		    		return false;
		    	}
		    	var name=$.trim($('#tname').val());
		    	if(name==''){
		    		alert('姓名不能为空');
		    		return false;
		    	}
		    	var localUrl=sysPath+'/client/clientRequestMap.do';
				var clientUrl='/transfer/add.do';
				var params={
					'transferID':recordID,
					'type':grecordType,
					'workno':workno,
					'name':name
				}
				var ret=true;
				var cb=function(data){
					if(data.state=='Y'){
						alert('移交成功');
						extractRecord.search();
					}else{
						ret=false;
						alert(data.msg);
					}
				}
				clientAjaxPostSync(localUrl,clientUrl,params,cb);
				if(!ret)
					return false;
		    },
		    cancel: true
		});
	},
	
	init:function(){
		if(ptype=='goback'){
			extractRecord.currentIndex=pcurrentIndex;
			extractRecord.sizePerPage=psizePerPage;
			$('#jzcaseID').val(pjzcaseID);
		}
		extractRecord.startEvent();
		extractRecord.search();
		extractRecord.bindEvent();
	}
}
$(document).ready(function() {
	extractRecord.init();
});
//提供给分页调用
function comAjaxPage(currentIndex,sizePerPage){
	extractRecord.currentIndex=currentIndex;
	extractRecord.sizePerPage=sizePerPage;
	extractRecord.search();
}
</script>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>入库选择库位</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.selTable td{
	padding: 10px 5px;
    border: 1px solid #DEDEDE;
    text-align: center;
    font-size: 12px;
    cursor: pointer;
}
.byellow{
	background-color: yellow;
}
.bgray{
	background-color: gray;
}
.bred{
	background-color: red;
}
.bblue{
	background-color: #87CCFF;
}
.dd{
	width: 12px;
	height:12px;
	display: inline-block;
	vertical-align: middle;
	margin-right: 2px;
}
</style>
</head>
<body>
<div class="content">
<form id="searchForm" action="<%=path%>/storageLocation/queryForPage.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search">
		<label>所属库房</label>
		<select id="storeroom" name="storeroomId">
			<option value="">请选择</option>
		</select>
		<label>所属货架</label>
		<select id="rack" name="rackId">
			<option value="">请选择</option>
		</select>
		<div class="sbut"><input name="" type="button" id="loadData" value="查询" /></div>
	</div>
	<div style="height:30px;line-height: 30px;width:300px;">
		<span class="dd byellow"></span>为空闲，
		<span class="dd bblue"></span>为未满，
		<span class="dd bgray"></span>为已满，
		<span class="dd bred"></span>为选中状态！
	</div>
	<table width="99%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="selTable">
		
	</table>
</form>
</div>
<script type="text/javascript">
var selStorageId=null;
var storeRoomCache=null;
var selStoreroomType=''; //保管室类型，自动仓就不用再选择库位
var selStorehouseId=null;  //选中的仓库id

var preInStorageLocationList={
	startEvent:function(){
		preInStorageLocationList.getData();
		
		$.ajax({
			type: 'GET',
			cache:false,
			url: sysPath+'/storageLocation/getStoreAndRack.do',
			dataType:"json",
			success: function(data){
				if(data!=null){
					var storeRoomList=data.storeRoomList;
					storeRoomCache=data.storeRoomList;
					$('#storeroom').empty();
					var option='<option value="">请选择</option>';
					if(storeRoomList!=null){
						for(var i=0;i<storeRoomList.length;i++){
							option=option+'<option value="'+storeRoomList[i].id+'">'+storeRoomList[i].storeroomName+'</option>';
						}
					}
					$('#storeroom').append(option);
					
					var rackList=data.rackList;
					$('#rack').empty();
					option='<option value="">请选择</option>';
					if(rackList!=null){
						for(var i=0;i<rackList.length;i++){
							option=option+'<option value="'+rackList[i].id+'">'+rackList[i].rackNum+'</option>';
						}
					}
					$('#rack').append(option);
				}
			}
		});
	},
	
	//根据id获取保管室类型
	getStoreRoomType:function(srId){
		for(var i=0;i<storeRoomCache.length;i++){
			if(storeRoomCache[i].id==srId){
				selStorehouseId=storeRoomCache[i].storehouseId;
				selStoreroomType=storeRoomCache[i].storeroomType;
			}
		}
	},
	
	bindEvent:function(){
		$('#loadData').on('click',function(){
			preInStorageLocationList.getData();
		});
		
		$('#storeroom').on('change',function(){
			var selVal=$(this).val();
			preInStorageLocationList.getStoreRoomType(selVal);
			
			//如果是自动仓，不用选择库位
			if(selStoreroomType=='ZDC'){
				$('#table_hover').html('');
			}
		});
	},
	
	getData:function(){
		var storeroomId=$('#storeroom').val();
		var rackId=$('#rack').val();
		$.ajax({
			type: 'GET',
			cache:false,
			url: sysPath+'/storageLocation/queryBySidOrRid.do?storeroomId='+storeroomId+'&rackId='+rackId,
			dataType:"json",
			success: function(data){
				if(data!=null){
					var tr='';
					for(var i=0;i<data.length;i++){
						if(i%6==0)
							tr=tr+'<tr>';
							
						if(data[i].inventoryStatus=='U'){
							tr=tr+'<td class="byellow">';
						}else if(data[i].inventoryStatus=='E'){
							tr=tr+'<td class="bblue">';
						}else if(data[i].inventoryStatus=='F'){
							tr=tr+'<td class="bgray">';
						}else{
							tr=tr+'<td class="bgray">';
						}
						var ableNum=data[i].depositNum-data[i].goodsNum;
						tr=tr+'<input type="hidden" name="locationId" value="'+data[i].id+'"/>'
						+'<input type="hidden" name="locationFreeNum" value="'+ableNum+'"/>'
						+'<input type="hidden" name="storeHouseId" value="'+data[i].storeHouseId+'"/>'
						+data[i].locationNum+'(<span name="ableNum">'+ableNum+'</span>)</td>';
						
						if(i%6==5)
							tr=tr+'</tr>';
					}
					$('#table_hover').html(tr);
					//显示已选中
					$('#table_hover tr td').each(function(idx,el){
						var inpVal=$(el).find('input[name="locationId"]').val();
						if(inpVal==selStorageId){
							$(el).removeClass();
							$(el).addClass('bred');
						}
					});
					
					$('#table_hover tr td').unbind();
					$('#table_hover tr td').on('click',function(){
						var thisObj=$(this);
						if(thisObj.hasClass('byellow')||thisObj.hasClass('bblue')){
							$('#table_hover tr td').each(function(idx,el){
								if($(el).hasClass('bred')){
									var ableNum=parseInt($($(el).find('span')[0]).text());
									if(ableNum==0){
										$(el).addClass('byellow');
									}else{
										$(el).addClass('bblue');
									}
									
									$(el).removeClass('bred');
								}
							});
							thisObj.removeClass();
							thisObj.addClass('bred');
						}
					});
				}
			}
		});
	},
	
	init:function(){
		preInStorageLocationList.startEvent();
		preInStorageLocationList.bindEvent();
	}
}
function initData(){
	preInStorageLocationList.init();
}
function getSelData(){
	var ret=[];
	if(selStoreroomType=='ZDC'){
		ret[0]=selStorehouseId;  //选中的所属仓库id
		ret[1]=0;  //选中的库位id
		ret[2]='';  //选中库位名称
		ret[3]='';  //选择库位格子剩余数量
		return ret;
	}
	$('#table_hover').find('td').each(function(idx,el){
		if($(el).hasClass('bred')){
			var storeHouseId=$(el).find('input[name="storeHouseId"]').val();
			var locationId=$(el).find('input[name="locationId"]').val();
			var locationName=$(el).text();
			var freeNums=$(el).find('input[name="locationFreeNum"]').val();
			
			ret[0]=storeHouseId;  //选中的所属仓库id
			ret[1]=locationId;  //选中的库位id
			ret[2]=locationName;  //选择的库位名称
			ret[3]=freeNums;  //选择库位格子剩余数量
		}
	});
	return ret;
}
</script>
</body>
</html>
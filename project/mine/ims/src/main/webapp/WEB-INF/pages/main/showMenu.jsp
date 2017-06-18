<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/pages/common.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>菜单管理</title>
<link type="text/css" href="${basePath}/css/main.css" rel="stylesheet" />
<link type="text/css" href="${basePath}/js/zTree/zTreeStyle.css" rel="stylesheet">
<script language="javascript" src="${basePath}/js/zTree/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="${basePath}/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script language="javascript" src="${basePath}/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	function getIcon(){
		 layer.open({
			  type: 2,
			  area: ['800px', '500px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: '${basePath}/main/icon.do'
			}); 
	}
</script>
</head>
<body>
<div class="crumb"><span>当前位置：菜单管理</span></div>
<div class="content">
<div>
	<div class="menu-list mid_bar" style="width:205px;float:left;"><ul id="menu-list-tree" class="ztree"></ul></div>
	<div class="content1"style="margin-left:205px;">
		<div class="toptit"><h1 class="h1tit">菜单编辑</h1></div>
		<ul class="list02">
       	<li><label>名称：</label><input id="name" type="text" value=""/></li>
       	<li><label>链接：</label><input id="url" type="text" value=""/></li>
       	<li><label>显示目标：</label><input id="target" type="text" value=""/></li>
       	<li><label>样式：</label><input id="css" type="text" value="" /></li>
       	<li><label>图标：</label>
       		<input id="icon" type="hidden"  value="&#xe66c;" />
       		<input id="icon" type="button"  value="选择图标"  onclick="getIcon()" style="width: 100px"/>
       		<i class="icon iconfont" id="getIcon" style="font-size: 30px;margin-left: 10px">&#xe66c;</i>
       	</li>
       	<li><label>描述：</label><input id="description" type="text" value=""/></li>
       	<li><label>级别：</label><input id="levels" type="text" value=""  /></li>
       	<li><label>显示顺序：</label><input id="priority" type="text" value="" /></li>
       	<li><label>是否叶子结点：</label><input id="isLeaf" type="text" value="" /></li>
       	<li><label>计算统计名：</label><input id="cntidName" type="text" value="" /></li>
       	<li><label>查询条件：</label><input id="sqlCondition" type="text" value="" /></li>
       	<li><label>是否有效：</label><select name="status" id="status"><option value="Y">是</option><option value="N">否</option><option value="D">删除</option></select></li>
		</ul>
		<div class="dbut">
		 	<a href="#" onclick="save();">保存</a>
		 	<!--  
		 	<a href="#" onclick="savePermission();">设置权限</a>
		 	-->
		 </div>
	</div>
</div>
</div>
<script type="text/javascript">
var zNodes, newCount = 1;
var onNode, newLock = false, newNode, updateParent;
function initData(){
	$.ajax({
		type: 'post',
		async: false,
		data:{"type":"1"},
		url: '${basePath}/menu/getMenus.do',
		dataType:"json",
		success: function(data){
			zNodes = [];
			for(var i = 0; i<data.length; ++i){
				var node = {
					"id": data[i].id,
					"pId": data[i].parentId,
					"name": data[i].name,
					"data": data[i]
				}
				zNodes.push(node);
			}
		}
	});
}

function save(flag){

	var data={
		"id": onNode.data.id,
		"name": $('#name').val(),
		"url": $('#url').val(),
		"target": $('#target').val(),
		"css": $('#css').val(),
		"icon": $('#icon').val(),
		"description": $('#description').val(),
		"parentId": onNode.data.parentId,
		"isLeaf": $('#isLeaf').val(),
		"priority": $('#priority').val(),
		"levels": $('#levels').val(),
		"status": $('#status').val() ,
		"cntidName":$('#cntidName').val(),
		"sqlCondition":$('#sqlCondition').val()
	}
	$.ajax({
	  type: 'POST',
	  async: false,
	  url: '${basePath}/menu/saveMenu.do',
	  data: data,
	  dataType:"json",
	  success: function(data){
		  if(data.success==true){
			  onNode.data=data.data;
			  onNode.id=data.data.id;
			  onNode.pId=data.data.parentId;
		      onNode.name=data.data.name;
			  $.fn.zTree.getZTreeObj("menu-list-tree").updateNode(onNode);
			  newLock=false;
			  if(flag==true){
				layer.alert("删除成功。");  
			  }else{
			  	layer.alert("保存成功。");
			  }
			 //top.$("#leftFrame").attr("src",top.$("#leftFrame").attr("src"));
			  if(updateParent!=null){
				  saveNode(updateParent);
				  updateParent = null;
			  } 
			  return true;
		  }else{
			  return false;
		  }
	  }
	});
}

function savePermission(){
	 layer.alert(onNode.data.id);
}


function saveNode(node){
	var data={
		"id": node.data.id,
		"name": node.data.name,
		"url": node.data.url,
		"target": node.data.target,
		"css": node.data.css,
		"icon": node.data.icon,
		"description": node.data.description,
		"parentId": node.data.parentId,
		"isLeaf": node.data.isLeaf,
		"priority": node.data.priority,
		"levels": node.data.levels,
		"status": node.data.status,
		"cntidName":node.data.cntidName,
		"sqlCondition":node.data.sqlCondition
	}

	$.ajax({
	  type: 'POST',
	  async: false,
	  url: '${basePath}/menu/saveMenu.do',
	  data: data,
	  dataType:"json",
	  success: function(data){
		  if(data.success==true){
			  $.fn.zTree.getZTreeObj("menu-list-tree").updateNode(node);
			  return true;
		  }else{
			  return false;
		  }
	  }
	});
}

function showRemoveBtn(treeId, treeNode) {
	return treeNode.level!=0 && !treeNode.isParent;
}

function addHoverDom(treeId, treeNode) {
	if(treeNode.data.levels==5) return;
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='添加子分类' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		//if(treeNode.data.levels==4){
		//	addGrid();
	   // } 
		if(newLock==true){
			cancelAdd();
			return false;
		}
		newLock = true;
		var zTree = $.fn.zTree.getZTreeObj("menu-list-tree");
		data = {
			"name": "",
			"levels": treeNode.data.levels+1,
			"parentId": treeNode.data.id,
			"status": "Y",
			"priority": treeNode.children != null?(treeNode.children.length + 1):1,
			"url": "",
			"target": "mainFrame",
			"css": "",
			"icon": "",
			"description": "",
			"isLeaf": "Y",
			"cntidName": "",
			"sqlCondition": ""
		}
		zTree.addNodes(treeNode, 
			{
			id: '-1', 
			pId: treeNode.id, 
			name: "", 
			data:data}
		);
		newNode=treeNode.children[treeNode.children.length-1];
		zTree.selectNode(newNode);
		showNode(newNode);
		treeNode.data.isLeaf="N";
		$.fn.zTree.getZTreeObj("menu-list-tree").updateNode(treeNode);
		updateParent=treeNode;
		return false;
	});
}

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
}

function beforeDrag(treeId, treeNodes) {
	for (var i=0,l=treeNodes.length; i<l; i++) {
		dragId = treeNodes[i].pId; 
	} 
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) { 
	if(moveType != 'inner' && targetNode.pId == dragId){
		
		return true;
	}else{
		return false; 
	} 
} 

function onDrop(event, treeId, treeNodes, targetNode, moveType) {
	nodes = treeNodes[0].getParentNode().children;
	for(var i=0;i<nodes.length; ++i){
		if(nodes[i].data.priority != i+1){
			nodes[i].data.priority = i+1;
			saveNode(nodes[i]);
		}
	}
	
}

function onRemove(event, treeId, treeNode) {
	nodes = treeNode.getParentNode().children;
	for(var i=0;i<nodes.length; ++i){
		if(nodes[i].data.priority != i+1){
			nodes[i].data.priority = i+1;
			saveNode(nodes[i]);
		}
	}
}

function beforeRemove(treeId, treeNode) {
	if(newLock==true){
		cancelAdd();
		return false;
	}
	var zTree = $.fn.zTree.getZTreeObj("menu-list-tree");
	zTree.selectNode(treeNode);
	var result = confirm("确认删除菜单 -- " + treeNode.name + " 吗？");
	if(result==true){
		treeNode.data.status='D';
		$('#status').val("D")
		onNode=treeNode;
		result=save(true);
	}
	return result;
}

function onClick(event, treeId, treeNode, clickFlag) {
	if(newLock==true){
		cancelAdd();
		return false;
	}
	showNode(treeNode);
}
function isCondition(ele){
	if (ele!=null&&ele!=""&&ele!=undefined) {
		return true;
	}
	return false;
}
function showNode(treeNode){
	onNode = treeNode;
	if(treeNode&&treeNode.data){
		$('#name').val(treeNode.data.name);
		$('#url').val(treeNode.data.url);
		$('#target').val(treeNode.data.target);
		$('#css').val(treeNode.data.css);
		$('#icon').val(treeNode.data.icon);
		$("#icon").next().next().html(treeNode.data.icon);
		$('#description').val(treeNode.data.description);
		$('#levels').val(treeNode.data.levels);
		$('#priority').val(treeNode.data.priority);
		$('#isLeaf').val(treeNode.data.isLeaf=='Y'?'是':'否');
		$('#status option[value='+treeNode.data.status+']').prop("selected",true);
		$('#cntidName').val(treeNode.data.cntidName);
		$('#sqlCondition').val(treeNode.data.sqlCondition);
	} else { 
		$('#name').val('');
		$('#url').val('');
		$('#target').val('main');
		$('#css').val('');
		$('#icon').val('');
		$('#description').val('');
		$('#levels').val('');
		$('#priority').val('');
		$('#isLeaf').val('');
		$('#cntidName').val('');
		$('#sqlCondition').val('');
	}
}

function cancelAdd(){
	var result = confirm("新添加菜单尚未保存, 是否删除菜单？");
	if(result==true){
		newLock = false;
		$.fn.zTree.getZTreeObj("menu-list-tree").removeNode(newNode);
		if(updateParent.children.length==0){
			updateParent.data.isLeaf='Y';
		}else {
			updateParent.data.isLeaf='N';
		}
		$.fn.zTree.getZTreeObj("menu-list-tree").updateNode(updateParent);
		updateParent = null;
	}else{
		$.fn.zTree.getZTreeObj("menu-list-tree").selectNode(newNode);
	}
}

$(document).ready(function() {
	
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
		,edit: {
			enable: true
			,showRemoveBtn: showRemoveBtn
			,showRenameBtn: false
		}
		,view: {
			showIcon: false
			,selectedMulti: false
			,addHoverDom: addHoverDom
			,removeHoverDom: removeHoverDom
		}
		,callback: {
			beforeDrag: beforeDrag
			, beforeDrop: beforeDrop
			, onDrop: onDrop
			, beforeRemove: beforeRemove
			, onRemove: onRemove
			, onClick: onClick
		}
	}
	
	initData();
	$.fn.zTree.init($("#menu-list-tree"), setting, zNodes);
	
	tree=$.fn.zTree.getZTreeObj("menu-list-tree");
	tree.expandNode(tree.getNodeByTId('1'));
	showNode(tree.getNodeByTId('1'));
	
	
});
</script>
</body>
</html>
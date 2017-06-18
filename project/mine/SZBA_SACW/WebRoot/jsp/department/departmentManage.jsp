<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>财物分类管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css?v=201508281350" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js?v=20150928180000"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 组织管理</span></div>
<div class="content" style="width:98%;float: left;">
	<div class="department-list" id="departmentTree" style="float: left;width: 30%;padding: 10px 5px;border-right: 1px solid #D8E1E6;text-align: left;overflow: auto;">
		<ul id="department-list-tree" class="ztree"></ul>
	</div>
	<div style="text-align: left;width:65%;padding: 10px;float: left;">
		<div class="toptit"><h1 class="h1tit">部门编辑</h1></div>
		<ul class="list02">
		  <li><label>名称：</label><input id="name" type="text" value=""/></li>
		  <li><label>库房属性：</label><select id="category" name="category">
			  <option value="0">无库房</option><option value="1">暂存库</option><option value="2">中心库</option></select>
		  </li>
		  <li><label>级别属性：</label><select id="workPriority" name="workPriority">
			  <option value="1">派出所</option><option value="2">大队</option><option value="100">非办案部门</option></select>
		  </li>
		</ul>
		<div class="dbut">
		 	<a href="#" onclick="save();">保存</a>
		 </div>
	</div>
</div>
<script type="text/javascript">
	var onNode, newLock = false, newNode;
	function save(flag){
		var data={
			"id": onNode.data.id,
			"name": $('#name').val(),
			"levels": onNode.data.levels,
			"parentId": onNode.data.parentId,
			"workPriority": $('#workPriority').val(),
			"status": onNode.data.status,
			"category": $('#category').val()
		}
		
		$.ajax({
		  type: 'POST',
		  async: false,
		  url: sysPath+'/account/saveDepartment.do',
		  data: data,
		  dataType:"json",
		  success: function(data){
			  if(data.success==true){
				  onNode.data=data.data;
				  onNode.id=data.data.id;
				  onNode.pId=data.data.parentId;
				  onNode.name=data.data.name;
				  $.fn.zTree.getZTreeObj("department-list-tree").updateNode(onNode);
				  newLock=false;
				  if(flag==true){
					alert("删除成功。");  
				  }else{
				  	alert("保存成功。");
				  }
				  return true;
			  }else{
				  return false;
			  }
		  }
		});
	}

	$(document).ready(function() {
		var wh=$(document.body).height();
		$('#departmentTree').css('height',wh-75);
		
		var newCount = 1
		
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
				, beforeRemove: beforeRemove
				, onClick: onClick
			}
		}
		
		function showRemoveBtn(treeId, treeNode) {
			return treeNode.level!=0 && !treeNode.isParent;
		}
		
		function addHoverDom(treeId, treeNode) {
			if(treeNode.data.levels==3) return;
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='添加子分类' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				if(newLock==true){
					cancelAdd();
					return false;
				}
				newLock = true;
				var zTree = $.fn.zTree.getZTreeObj("department-list-tree");
				data = {
					"name": "",
					"levels": treeNode.data.levels+1,
					"parentId": treeNode.data.id,
					"status": "Y",
					"priority": null,
					"workPriority": '1',
					"category": '0'
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
				return false;
			});
		}
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		}
		
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		
		function beforeRemove(treeId, treeNode) {
			if(newLock==true){
				cancelAdd();
				return false;
			}
			var zTree = $.fn.zTree.getZTreeObj("department-list-tree");
			zTree.selectNode(treeNode);
			var result = confirm("确认删除部门 -- " + treeNode.name + " 吗？");
			if(result==true){
				treeNode.data.status='N';
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
		
		function showNode(treeNode){
			onNode = treeNode;
			if(treeNode&&treeNode.data){
				$('#name').val(treeNode.data.name);
				$('#category').val(treeNode.data.category);
				$('#workPriority').val(treeNode.data.workPriority);
			} else {
				$('#name').val('');
				//$('#category').val('');
				//$('#workPriority').val('');
			}
		}
		
		function cancelAdd(){
			var result = confirm("新添加部门尚未保存, 是否删除部门？");
			if(result==true){
				newLock = false;
				$.fn.zTree.getZTreeObj("department-list-tree").removeNode(newNode);
			}else{
				$.fn.zTree.getZTreeObj("department-list-tree").selectNode(newNode);
			}
		}
		
		Common.departments.setting.showTop = true;
		var zNodes = Common.departments.method.getZTreeNodes();
		$.fn.zTree.init($("#department-list-tree"), setting, zNodes);
		
		tree=$.fn.zTree.getZTreeObj("department-list-tree");
		tree.expandNode(tree.getNodeByTId('1'));
		showNode(tree.getNodeByTId('1'));
		
	});
</script>
</body>
</html>
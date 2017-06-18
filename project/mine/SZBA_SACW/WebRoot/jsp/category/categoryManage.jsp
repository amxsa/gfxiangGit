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
<script language="javascript" src="<%=path%>/js/category.js?v=201509021000"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物分类管理</span></div>
<div class="content">
<div>
	<div class="category-list mid_bar" style="width:260px;float:left;"><ul id="category-list-tree" class="ztree"></ul></div>
	<div class="content1" style="margin-left:270px;">
       <div style="margin-bottom: 5px;overflow:hidden;zoom:1;">
  			<input style="background:#1F6BB2  no-repeat 10px 5px;color:#fff;outline:none; float:right;border:none;" 
  				type="button" value="分类属性" onclick="categoryAttribute()"></div>
		<div class="toptit"><h1 class="h1tit">财物分类编辑</h1></div>
		<ul class="list02a">
		  <li><label>名称：</label><input id="name" type="text" value=""/></li>
		  <li><label>保管要求：</label><input id="saveDemand" type="text" value=""/></li>
		  <li><label>包装要求：</label><input id="packDemand" type="text" value=""/></li>
		  <li><label>保管环境要求：</label><input id="enviDemand" type="text" value=""/></li>
		  <li><label>类别：</label>
		  	<select name="type" id="type">
				<jsp:include page="/jsp/plugins/cateType_options.jsp" />
			</select>
		  </li>
		</ul>
		<div class="dbut">
		 	<a href="#" onclick="save();">保存</a>
		 </div>
	</div>
</div>
</div>
<script type="text/javascript">
	var attrString="";
	var onNode, newLock = false, newNode;
	function save(flag){
		data={
			"id": onNode.data.id,
			"name": $('#name').val(),
			"saveDemand": $('#saveDemand').val(),
			"packDemand": $('#packDemand').val(),
			"enviDemand": $('#enviDemand').val(),
			"type": $('#type').val(),
			"levels": onNode.data.levels,
			"parentId": onNode.data.parentId,
			"priority": onNode.data.priority,
			"validStatus": onNode.data.validStatus,
			"attrString": attrString
		}
		
		$.ajax({
		  type: 'POST',
		  async: false,
		  url: sysPath+'/category/save.do',
		  data: data,
		  dataType:"json",
		  success: function(data){
			  if(data.success==true){
				  onNode.data=data.data;
				  onNode.id=data.data.id;
				  onNode.pId=data.data.parentId;
				  onNode.name=data.data.name;
				  $.fn.zTree.getZTreeObj("category-list-tree").updateNode(onNode);
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
				var zTree = $.fn.zTree.getZTreeObj("category-list-tree");
				data = {
					"name": "",
					"saveDemand": "",
					"packDemand": "",
					"enviDemand": "",
					"type":"",
					"levels": treeNode.data.levels+1,
					"parentId": treeNode.data.id,
					"validStatus": "Y"
					//id, name, save_demand, pack_demand, envi_demand, levels, parent_id, priority, valid_status
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
			var zTree = $.fn.zTree.getZTreeObj("category-list-tree");
			zTree.selectNode(treeNode);
			var result = confirm("确认删除 财物分类 -- " + treeNode.name + " 吗？");
			if(result==true){
				treeNode.data.validStatus='N';
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
				$('#saveDemand').val(treeNode.data.saveDemand);
				$('#packDemand').val(treeNode.data.packDemand);
				$('#enviDemand').val(treeNode.data.enviDemand);
				$('#type').val(treeNode.data.type);
			} else {
				$('#name').val('');
				$('#saveDemand').val('');
				$('#packDemand').val('');
				$('#enviDemand').val('');
				$('#type').val('');
			}
		}
		
		function cancelAdd(){
			var result = confirm("新添加分类尚未保存, 是否删除分类？");
			if(result==true){
				newLock = false;
				$.fn.zTree.getZTreeObj("category-list-tree").removeNode(newNode);
			}else{
				$.fn.zTree.getZTreeObj("category-list-tree").selectNode(newNode);
			}
		}
		
		var zNodes = Common.category.method.getZTreeNodes(true);
		$.fn.zTree.init($("#category-list-tree"), setting, zNodes);
		
		tree=$.fn.zTree.getZTreeObj("category-list-tree");
		tree.expandNode(tree.getNodeByTId('1'));
		showNode(tree.getNodeByTId('1'));
		
	});
	
	//分类属性 pansenxin
	function categoryAttribute(){
		var pId=onNode.data.parentId;
		if(pId!=1){
			alert('只有二级分类才能设置分类属性');
			return;
		}
		var path=sysPath+'/jsp/category/categoryAttributeList.jsp';
		art.dialog.open(path, {
		    title: '分类属性',
		    width: 700,
		    height:500,
		    top:10,
		    init: function () {
		    	if(typeof(onNode.data.id) != "undefined"){
		    		var iframe = this.iframe.contentWindow;
			    	iframe.categoryAttrMgr.initData(onNode.data.id);
		    	}
		    },
		    ok: function () {
		    	var iframe = this.iframe.contentWindow;
		    	var ret=iframe.categoryAttrMgr.checkData();
		    	if(!ret){
		    		return false;
		    	}else{
		    		attrString=iframe.categoryAttrMgr.getData();
		    	}
		    },
		    cancel: true
		});
	}
</script>
</body>
</html>
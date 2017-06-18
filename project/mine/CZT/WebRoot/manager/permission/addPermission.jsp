<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>菜单管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css?v=201508281350" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.exedit-3.5.js"></script>

</head>
<body>

<div class="content">
<div>
	<div class="menu-list mid_bar" style="width:260px;float:left;"><ul id="menu-list-tree" class="ztree"></ul>
		<input type="hidden" name="menuId" id="menuId" />
		<input type="button" value="确定" onclick="saveRoleMenu();"/>
		
	</div>
	<div class="content1"style="margin-left:270px;">
		<div class="toptit"><h1 class="h1tit">菜单编辑</h1></div>
		<ul class="list02" id="permission">
		</ul>
		
		<div class="dbut"  style="display: none;">
		 	<a href="#" onclick="save();">保存</a>
		 </div>	
	</div>
</div>
</div>
<script type="text/javascript">
var roleId;
var setting = {
	check: {
		enable: true
	}
	, data: {
		simpleData: {
			enable: true
		}
	}
	, view: {
		showIcon: false
	}
	/**
	,callback: {
		onClick: zTreeOnClick
	}
	*/
};
function zTreeOnClick(event, treeId, treeNode) {
	
    if(treeNode.checked){
    	$("#permission").empty();
    	$.ajax({
			  type: 'GET',
			  async: false,
			  url: '<%=path%>/permission/PermissionAction_showUrlByMenuId.do',
			  data: {menuId:treeNode.id},
			  dataType:"json",
			  success: function(data){
				  var treeNodes = [];
				  var str ="";
				  for(i=0; i<data.length; ++i){
					  str+="<li style=\"width: 300px;margin-left: 10px;\">";
					  str+="<input type=\"checkbox\" name=\"permissionId\" value="+data[i].id+" />"+data[i].name;
					  str+="</li>";
				  }
				  $("#permission").append(str);
				  if(str.length>0){
				  	$(".dbut").show();
				  }else{
				  	$(".dbut").hide();
				  }
				 	 
			  }
		});
    }else{
    	 $(".dbut").hide();
    	 $("#permission").empty();
    }
 
}
$(document).ready(function() {
	initData();
});
function initData(){
		$.ajax({
			  type: 'GET',
			  async: false,
			  url: '<%=path%>/permission/PermissionAction_showMenuTreeByRoleId.do',
			  data: {roleId:'${param.roleId}'},
			  dataType:"json",
			  success: function(data){
				  var treeNodes = [];
				  for(i=0; i<data.length; ++i){
					  var node = {
						  "id": data[i].id,
						  "pId": data[i].parentId,
						  "name": data[i].name,
						  "checked": (data[i].checked=="Y"?true:false),
						  "data": data[i]
					  }
					  treeNodes.push(node);
				  }
				  $.fn.zTree.init($("#menu-list-tree"), setting, treeNodes);
				  $.fn.zTree.getZTreeObj("menu-tree").expandAll(true);
				 
			  }
		});
}

function saveRoleMenu(){
	var treeObj=$.fn.zTree.getZTreeObj("menu-list-tree");
    nodes=treeObj.getCheckedNodes(true);
    v="";
    for(var i=0;i<nodes.length;i++){
    	v+=nodes[i].id + ",";
    }
   	if(v.length>0){
   		$("#menuId").val(v);
   		$.ajax({
			  type: 'GET',
			  async: false,
			  url: '<%=path%>/permission/MenuAction_saveRoleMenu.do',
			  data: {roleId:'${param.roleId}',menuId:$("#menuId").val()},
			  dataType:"json",
			  success: function(data){
			  	/**
				  var treeNodes = [];
				  for(i=0; i<data.length; ++i){
					  var node = {
						  "id": data[i].id,
						  "pId": data[i].parentId,
						  "name": data[i].name,
						  "checked": (data[i].checked=="Y"?true:false),
						  "data": data[i]
					  }
					  treeNodes.push(node);
				  }
				  $.fn.zTree.init($("#menu-list-tree"), setting, treeNodes);
				  $.fn.zTree.getZTreeObj("menu-tree").expandAll(true);
				  */
				  if(data.state){
				  		alert('操作成功');
				  }else{
				  		alert('操作失败');
				  }
				 
			  }
		});
   		
   	}else{
   		alert("请选择菜单");
   	}
   	
}
</script>
</body>
</html>
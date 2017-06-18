<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'treeTest.jsp' starting page</title>
    
  <link type="text/css" href="<%=path%>/css/main.css" rel="stylesheet">
  <link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
	<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
   <script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.exedit-3.5.js"></script>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	
  	<a href="javascript:void(0)" class="distributeMenu" >选择审批人</a>
    <div id="popDiv1" class="mydiv" style="display: none;">
		<h1>选择审批人</h1>
		<div class="mtree">
			<ul id="common-tree" class="ztree"></ul>
		</div>
		<div class="dbut2"><a href="#" id="submitMenuBtn">提交</a><a href="#" id="closeBtn">关闭</a></div>
	</div>
	<div id="bg1" class="bg" style="display: none;"></div>
	<iframe id='popIframe1' class='popIframe' frameborder='0'></iframe>
	
  </body>
  
  <script type="text/javascript">
  
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
	};

	function showDiv(){
		document.getElementById('popDiv1').style.display='block';
		document.getElementById('popIframe1').style.display='block';
		document.getElementById('bg1').style.display='block';
	}
	
	function closeDiv(){
		document.getElementById('popDiv1').style.display='none';
		document.getElementById('bg1').style.display='none';
		document.getElementById('popIframe1').style.display='none';
	}

	$(document).ready(function() {
		
		$('.distributeMenu').click(function(){
			
		})
		
		function showTree(data){
			$.ajax({
				  type: 'post',
				  async: false,
				  url: 'test/tree.json',
				  data: data,
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
					  $.fn.zTree.init($("#common-tree"), setting, treeNodes);
					  $.fn.zTree.getZTreeObj("common-tree").expandAll(true);
					  showDiv();
				  }
				});
		}
		
		$('#submitMenuBtn').click(function(){
			nodes=$.fn.zTree.getZTreeObj("common-tree").getNodesByParam("checked",true,null);
			menucheck='[';
			for(i=0;i<nodes.length;++i){
				if(i!=0){
					menucheck=menucheck+',';
				}
				menucheck=menucheck+'{"id":"'+nodes[i].id+'","checked":true}'
			};
			menucheck=menucheck+']';

			data={
				data: menucheck
			}
			$.ajax({
			  type: 'POST',
			  async: false,
			  url: sysPath+'/role/distributeMenu.do',
			  data: data,
			  dataType:"json",
			  success: function(data){
				  alert("提交成功。");
			  },
			  complete: function(data){
				  closeDiv();
			  }
			});
		})
		
		$('#closeBtn').click(function(){
			closeDiv();
		})
	});
  
  
  </script>
</html>

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
<title>角色管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 角色管理</span></div>
<div class="content">
<form id="searchForm" action="<%=path%>/role/queryForPage.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search"><div class="sp">
		<p>
			<label>角色名称</label><input name="name" type="text" value="<%=RequestUtils.getAttribute(params,"name",0)%>"  style="width:100px;"/>
		</p></div>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">角色列表</h1>
	    <div class="but1"><input name="" type="button" value="添加角色"  onclick="location.href='<%=path%>/jsp/role/addRole.jsp'"/></div>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>角色名称</th>
			<th>角色描述</th>
			<th>操作</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
				<td>${v.name }</td>
				<td>${v.description }</td>
				<td><span class="tdbut">
						<!-- <a href="#">分配权限</a> -->
						<a href="<%=path%>/account/queryByList.do?id=${v.id }">分配用户</a>
						<a href="#" class="distributeMenu">分配菜单<input class="roleId" type="hidden" value="${v.id }"/></a>
						<a href="<%=path%>/icon/queryForPage.do?id=${v.id }">分配图标</a>
						<!-- <a href="#">分配URL（组）</a> -->
						<!-- <a href="#">修改角色</a> -->
					</span>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class='page'></div>
</form>
	<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>

	<div id="popDiv1" class="mydiv" style="display: none;">
		<h1>分配菜单</h1>
		<div class="mtree">
			<ul id="menu-tree" class="ztree"></ul>
			<input name="roleId" type="hidden"/><input name="menus" type="hidden"/>
		</div>
		<div class="dbut2"><a href="#" id="submitMenuBtn">提交</a><a href="#" id="closeBtn">关闭</a></div>
	</div>
	<div id="bg1" class="bg" style="display: none;"></div>
	<iframe id='popIframe1' class='popIframe' frameborder='0'></iframe>
</div>
<script type="text/javascript">
	function getQueryParams(){
		return "";
	}
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
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
		
		$('.distributeMenu').click(function(){
			roleId = $('.roleId',this).val();
			var data = {
				roleId: $('.roleId',this).val()
			}
			$.ajax({
			  type: 'GET',
			  async: false,
			  url: sysPath+'/role/toDistributeMenu.do',
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
				  $.fn.zTree.init($("#menu-tree"), setting, treeNodes);
				  $.fn.zTree.getZTreeObj("menu-tree").expandAll(true);
				  showDiv();
			  }
			});
		})
		
		$('#submitMenuBtn').click(function(){
			nodes=$.fn.zTree.getZTreeObj("menu-tree").getNodesByParam("checked",true,null);
			menucheck='[';
			for(i=0;i<nodes.length;++i){
				if(i!=0){
					menucheck=menucheck+',';
				}
				menucheck=menucheck+'{"id":"'+nodes[i].id+'","checked":true}'
			};
			menucheck=menucheck+']';

			data={
				roleId: roleId,
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
</body>
</html>
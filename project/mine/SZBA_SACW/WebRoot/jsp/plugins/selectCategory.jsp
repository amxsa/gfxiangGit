<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.all-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/categoryMgr.js"></script>
<title>选择部门</title>
</head>
<body>
	<input id="checked" type="hidden"/>
	<div id="categoryTreeDiv" style="height:100px;">
		<ul id="categoryTree" class="ztree"></ul>
	</div>
	
<script type="text/javascript">
var selectCategory={
	startEvent:function(){
		$.ajax({
			type: 'GET',
			async: false,
			cache:false,
			url: sysPath+'/category/queryCategorys.do',
			dataType:"json",
			success: function(data){
				categoryMgr.dataCache=data;
				categoryMgr.showTreeData($('#checked').val());
			}
		});
	},
	
	init:function(){
		selectCategory.startEvent();
	}
}
function initData(){
	selectCategory.init();
}
function getCheckedData(){
	return categoryMgr.getSelData();
}
var categoryMgr={
	dataCache:null,
	
	setCheck:function(){
		var zTree = $.fn.zTree.getZTreeObj("categoryTree"),
		py = "",
		sy = "s",
		pn = "",
		sn = "s",
		type = { "Y":py + sy, "N":pn + sn};
		zTree.setting.check.chkboxType = type;
	},
	
	showTreeData:function(selVal){
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				nameIsHTML: true
			}
		};
		var zNodes = [];
		var dataCache=categoryMgr.dataCache;
		for (var i = 0; i < dataCache.length; i++) {
			zNodes[i]={
				id:dataCache[i].id,
				name:dataCache[i].name,
				pId:dataCache[i].parentId
			};
			if(i==0){
				zNodes[i].open=true;
			}
			if(selVal==dataCache[i].id){
				zNodes[i].checked=true;
			};
		}
		$.fn.zTree.init($("#categoryTree"), setting, zNodes);
		categoryMgr.setCheck();
		$("#py").bind("change", categoryMgr.setCheck());
		$("#sy").bind("change", categoryMgr.setCheck());
		$("#pn").bind("change",	categoryMgr.setCheck());
		$("#sn").bind("change",	categoryMgr.setCheck());
	},
	
	//选择返回事件
	getSelData:function(){
		var retData=null;
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		var nodes = zTree.getCheckedNodes();  // 取得所有选中的
		var len=nodes.length;
		if(len!=1){
			alert('请选择一个分类');
			return retData;
		}
		var selId=nodes[0].id;
		var dataCache=categoryMgr.dataCache;
		for(var i=0;i<dataCache.length;i++){
			if(selId==dataCache[i].id){
				retData=dataCache[i];
				return retData;
			}
		}
	}
}
</script>
</body>
</html>
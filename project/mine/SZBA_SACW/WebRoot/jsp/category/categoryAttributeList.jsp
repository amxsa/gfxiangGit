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
<title>分类属性</title>
</head>
<body>
	<div class="tdbut">
		<a href="javascript:void(0)" style="float: right;margin: 5px;" id="addData">添加</a>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0"
		id="table_hover" class="table"  style="word-break:break-all; word-wrap:break-all;">
		<thead>
			<tr>
				<th style="width:30%;">属性名称</th>
				<th style="width:10%;">属性类型</th>
				<th style="width:40%;">属性值（多值以","分隔）</th>
				<th style="width:10%;">支持查询</th>
				<th style="width:10%;">操作</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
<script type="text/javascript">
var categoryAttrMgr={
	attrType: '<select name="attrType"><option value="">请选择</option><option value="1">输入框</option><option value="2">下拉框</option>'
	+'<option value="3">单选框</option><option value="4">复选框</option><option value="5">文本值</option></select>',
	
	bindEvent:function(){
		$('#addData').on('click',function(){
			var tr='<tr><td><input name="attrName"/></td>'
			+'<td>'+categoryAttrMgr.attrType+'</td>'
			+'<td><input name="attrValue"/></td>'
			+'<td><select name="searchType"><option value="">请选择</option><option value="1">是</option><option value="2">否</option></select></td>'
			+'<td><span class="tdbut"><a href="javascript:void(0)" onclick="categoryAttrMgr.delTr(this)">删除</a></span></td></tr>';
			
			$('#table_hover tbody').append(tr);
		});
	},
	
	delTr:function(obj){
		$(obj).parent().parent().parent().remove();
	},

	checkData:function(){
		var ret=true;
		$('#table_hover tbody tr').each(function(idx,el){
			var attrName=$.trim($(el).find('input[name="attrName"]').val());
			if(attrName==''){
				alert('存在属性名称为空的数据');
				ret=false;
				return ret;
			};
			var attrType=$.trim($(el).find('select[name="attrType"]').val());
			if(attrType==''){
				alert('存在属性类型为空的数据');
				ret=false;
				return ret;
			};
			var attrValue=$.trim($(el).find('input[name="attrValue"]').val());
			if(attrValue==''&&(attrType!=1&&attrType!=5)){
				alert('下拉框、单选框、复选框必须存在属性值');
				ret=false;
				return ret;
			};
			var searchType=$.trim($(el).find('select[name="searchType"]').val());
			if(searchType==''){
				alert('存在支持查询为空的数据');
				ret=false;
				return ret;
			};
		});
		return ret;
	},
	
	getData:function(){
		var attrString='';
		$('#table_hover tbody tr').each(function(idx,el){
			var attrName=$.trim($(el).find('input[name="attrName"]').val());
			attrString+=attrName+'|';
			var attrType=$.trim($(el).find('select[name="attrType"]').val());
			attrString+=attrType+'|';
			var attrValue=$.trim($(el).find('input[name="attrValue"]').val());
			attrString+=attrValue+'|';
			var searchType=$.trim($(el).find('select[name="searchType"]').val());
			attrString+=searchType+";";
		});
		if(attrString!='')
			attrString=attrString.substring(0,attrString.length-1);
		return attrString;
	},
	
	//修改则初始化数据
	initData:function(categoryId){
		$.ajax({
			type: 'GET',
			async: false,
			url: sysPath+'/category/findCategoryAttributeByCategoryId.do?categoryId='+categoryId,
			dataType:"json",
			success: function(data){
				if(data!=null){
					$('#table_hover tbody').empty();
					for(var i=0;i<data.length;i++){
						var singleData=data[i];
						var attrType='<select name="attrType"><option value="">请选择</option>'
						+(singleData.attrType!=1?'<option value="1">输入框</option>':'<option value="1" selected="selected">输入框</option>')
						+(singleData.attrType!=2?'<option value="2">下拉框</option>':'<option value="2" selected="selected">下拉框</option>')
						+(singleData.attrType!=3?'<option value="3">单选框</option>':'<option value="3" selected="selected">单选框</option>')
						+(singleData.attrType!=4?'<option value="4">复选框</option>':'<option value="4" selected="selected">复选框</option>')
						+(singleData.attrType!=5?'<option value="5">文本值</option>':'<option value="5" selected="selected">文本值</option>')
						+'</select>';
						var selectType='<select name="searchType"><option value="">请选择</option>'
						+(singleData.searchType!=1?'<option value="1">是</option>':'<option value="1" selected="selected">是</option>')
						+(singleData.searchType!=2?'<option value="2">否</option>':'<option value="2" selected="selected">否</option>')
						+'</select>';
						var tr='<tr><td><input name="attrName" value="'+singleData.attrName+'"/></td>'
							+'<td>'+attrType+'</td>'
							+'<td><input name="attrValue" value="'+singleData.attrValue+'"/></td>'
							+'<td>'+selectType+'</td>'
							+'<td><span class="tdbut"><a href="javascript:void(0)" onclick="categoryAttrMgr.delTr(this)">删除</a></span></td></tr>';
							$('#table_hover tbody').append(tr);
					}
				}
			}
		});
	},
	
	init:function(){
		categoryAttrMgr.bindEvent();
	}
}
$(document).ready(function() {
	categoryAttrMgr.init();
});
</script>
</body>
</html>
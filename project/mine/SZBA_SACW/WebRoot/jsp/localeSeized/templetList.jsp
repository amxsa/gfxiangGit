<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>模板</title>
</head>
<body>
	<div style="height:30px;">
		<input type="button" value="添加" style="cursor: pointer;width:50px;float: right;margin: 6px;" id="addTemplet"/>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0"
		id="table_hover" class="table"  style="word-break:break-all; word-wrap:break-all;">
		<thead>
			<tr>
				<th style="width:5%;">选择</th>
				<th style="width:8%;">序号</th>
				<th style="width:37%;">模板名称</th>
				<th style="width:20%;">类型</th>
				<th style="width:30%;">操作</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
<script type="text/javascript">
var templetList={
	dataCache:null,
	
	bindEvent:function(){
		//添加模板
		$('#addTemplet').on('click',function(){
			var path=sysPath + "/jsp/localeSeized/templetAdd.jsp";
			art.dialog.open(path, {
			    title: '添加模板',
			    width: 500,
			    height:300,
			    top:10,
			    ok: function () {
			    	var ret=this.iframe.contentWindow.saveTemplet();
			    	if(!ret)
			    		return false;
			    	alert('添加成功');
			    	templetList.search();
			    },
			    cancel: true
			});
		});
	},
	
	search:function(){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/templet/queryAll.do';
		var params={
			'type':'',
			'contentType':art.dialog.data('contentType')
		}
		var cb=function(data){
			$('#table_hover tbody').empty();
			var tr='';
			if(data.state=='Y'){
				templetList.dataCache=data.templets;
				var checkedId=art.dialog.data('checkedId');
				for(var i=0;i<data.templets.length;i++){
					var obj=data.templets[i];
					var op='';
					if(obj.type!=0){
						op='<input type="hidden" value="'+obj.id+'"/><input type="button" value="查看" style="cursor: pointer;width:50px;float: left;" onclick="templetList.lookTemplet(this)"/>'
						+'<input type="button" value="修改" style="cursor: pointer;width:50px;float: left;margin-left: 6px;" onclick="templetList.updateTemplet(this)"/>'
						+'<input type="button" value="删除" style="cursor: pointer;width:50px;float: left;margin-left: 6px;" onclick="templetList.deleteTemplet(this)"/>';
					}else{
						op='<input type="hidden" value="'+obj.id+'"/>'
						+'<input type="button" value="查看" style="cursor: pointer;width:50px;float: left;" onclick="templetList.lookTemplet(this)"/>';
					}
					tr=tr+'<tr><td><input type="radio" name="id" value="'+obj.id+'" '+(obj.id==checkedId?'checked="checked"':'')+'/></td>'
					+'<td>'+(i+1)+'</td>' 
					+'<td>'+obj.name+'</td>'
					+'<td>'+(obj.type==0?'默认':'自定义')+'</td>'
					+'<td>'+op+'</td></tr>';
				}
			}else{
				tr='<tr><td colspan="4">请求错误，请重试</td></tr>';
			}
			$('#table_hover tbody').append(tr);
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	//删除模板
	deleteTemplet:function(obj){
		var temId=$(obj).prev().prev().prev().val();
		var flag = window.confirm("确定删除此模板？");
		if(flag){
			var localUrl=sysPath+'/client/clientRequestMap.do';
			var clientUrl='/templet/delete.do';
			var params={
				'id':temId
			}
			var cb=function(data){
				if(data.state=='Y'){
					alert('删除成功');
					templetList.search();
				}else{
					alert(data.msg);
					return false;
				}
			}
			clientAjaxPost(localUrl,clientUrl,params,cb);
		}
	},
	
	//修改模板
	updateTemplet:function(obj){
		var temId=$(obj).prev().prev().val();
		art.dialog.data('temId',temId);
		var path=sysPath + "/jsp/localeSeized/templetUpdate.jsp";
		art.dialog.open(path, {
		    title: '修改模板',
		    width: 500,
		    height:300,
		    top:10,
		    ok: function () {
		    	var ret=this.iframe.contentWindow.saveTemplet();
		    	if(!ret)
		    		return false;
		    	alert('修改成功');
		    	templetList.search();
		    },
		    cancel: true
		});
	},
	
	//查看模板
	lookTemplet:function(obj){
		var temId=$(obj).prev().val();
		art.dialog.data('temId',temId);
		var path=sysPath + "/jsp/localeSeized/templetLook.jsp";
		art.dialog.open(path, {
		    title: '查看模板',
		    width: 500,
		    height:300,
		    top:10,
		    cancel: true
		});
	},
	
	init:function(){
		templetList.bindEvent();
	}
};
$(document).ready(function() {
	templetList.init();
});
function getTemplet(){
	templetList.search();
}
function getSelData(){
	var checkedLen=$('#table_hover').find('input[name="id"]:checked').length;
	if(checkedLen==0) { 
		alert('至少选择一个模板');
		return false;
	}
	
	var checkId=$('#table_hover').find('input[name="id"]:checked').val();
	var ret=[];
	for(var i=0;i<templetList.dataCache.length;i++){
		if(templetList.dataCache[i].id==checkId){
			ret[0]=checkId;
			ret[1]=templetList.dataCache[i].content;
		}
	}
	return ret;
}
</script>
</body>
</html>
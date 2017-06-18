<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
function clear(){
	$("#checkGroup input:checkbox").attr("checked",false);
}
function other(){
	$(":checkbox").each(function() {
		var flag = this.checked;
		$(this).attr("checked",!flag);
		//this.checked = !flag;
	});
}
function all(){
	 if($(":checkbox:checked").length == $(":checkbox").length){
		$(":checkbox").attr("checked", false);
	 }else {
		$(":checkbox").attr("checked", true);
	 }
}
function getDepts(){
	var ids="";
	var names="";
	$("input").each(function(){
		if($(this).attr("checked")==true){
			if($(this).val()!=""){
				ids+=$(this).val()+",";
				names+=$(this).attr("name")+",";
			}
		}
	});
	$("#deptIds").val(ids.substring(0,ids.length-1));
	$("#deptNames").val(names.substring(0,names.length-1));
	return "";
}
$(function(){
	$.ajax({
		type: "post",
		url:"/xms/deptManage/getDept.do",
		async:false,
		dataType: "json",
		success:function(data){
			if(data.result==0){
				var strs="";
				var list=data.list;
				for(var i=0;i<list.length;i++){
					strs+=list[i].name+"<input type='checkbox' value='"+list[i].id+"'  name='"+list[i].name+"'/> ";
				}
				$("#checkGroup").append(strs);
			}
		}
	})
	var strs="${deptIds }";
	var arrIds=strs.split(",");
	$("input").each(function(){
		for(var i=0;i<arrIds.length;i++){
			if($(this).val()==arrIds[i]){
				$(this).attr("checked",true);
			}
		}
	}); 
});
</script>
</head>
<body>
	<div id="checkGroup" class="render"></div>
	<input type="hidden" id="deptIds"/>
	<input type="hidden" id="deptNames"/>
</body>
</html>
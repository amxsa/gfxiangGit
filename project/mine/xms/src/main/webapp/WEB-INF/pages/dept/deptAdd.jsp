<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type: "post",
			url:"${contextPath}/deptManage/getLevel.do"	,
			dataType: "json",
			success:function(data){
				var options="";
				if(data.result==0){
					var list=data.list;
					for(var i=0;i<list.length;i++){
						options+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
					}
					$("#sel").append(options); 
					$("#sel option[value='${dept.level.id}']").attr("selected",true);
					selRefresh("sel");
				}
			}
		})
	});
	function goBack(){
		location.href="${contextPath}/deptManage/list.do";
	}
	function doSubmit(){
		$("#form").ajaxSubmit(function (responseResult) {
			if(responseResult==0){
				top.Dialog.alert("保存成功");
			}else if(responseResult==1){
				top.Dialog.alert("部门名称重复");
			}else{
				top.Dialog.alert("服务器异常");
			}
		});
	}
</script>
</head>
<body>
	<div id="scrollContent" class="border_gray">
		<form id="form" action="${contextPath }/deptManage/save.do" method="post" enctype="multipart/form-data">
			<input id="id" name="id" type="hidden" value="${dept.id }"/>
			<table class="tableStyle">
				<tr>
					<td width="15%"><span class="star">*</span>部门名称：</td>
					<td><input type="text" name="name" id="name" class="validate[required,length[1,32]]" value="${dept.name }" /></td>
				</tr>
				<tr>
					<td width="15%"><span class="star">*</span>logo：</td>
					<td><input type="file" name="aa" id="logo"  value="${dept.logo }" /></td>
				</tr>
				<tr>
					<td width="20%"><span class="star">*</span>部门等级：</td>
					<td><select  id="sel"  name="levelId"><option value="">请选择等级</option></select></td>
				</tr>
				<tr >
					<td colspan="2">
						<input type="button" value="提交" onclick="doSubmit()"></input>
						<input type="button" value="返回" onclick="goBack()"></input>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>
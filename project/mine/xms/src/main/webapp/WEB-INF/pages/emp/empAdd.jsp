<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
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
						strs+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
					}
					$("#sel").append(strs);
					selRefresh("sel");                                           
				}
			} 
		})
	});
	function goBack(){
		location.href="/xms/empManage/list.do";
	}
	function doSubmit(){
		$("#form").ajaxSubmit(function (responseResult) {
			if(responseResult==0){
				top.Dialog.alert("保存成功");
			}else if(responseResult==1){
				top.Dialog.alert("员工名称重复");
			}else{
				top.Dialog.alert("服务器异常");
			}
		});
	}
</script>
</head>
<body>
	<div id="scrollContent" class="border_gray">
		<form id="form" action="/xms/empManage/save.do" method="post" >
			<input id="id" name="id" type="hidden" value="${emp.id }"/>
			<table class="tableStyle">
				<tr>
					<td width="5%"><span class="star">*</span>员工名称：</td>
					<td><input type="text" name="name" id="name" class="validate[required,length[1,32]]" value="${emp.name }" /></td>
				</tr>
				<tr>
					<td width="5%"><span class="star">*</span>IdCard：</td>
					<td><input type="text" name="idCard" id="idCard" class="validate[required,length[1,10]]"  value="${emp.idCard}" /></td>
				</tr>
				<tr>
					<td width="5%"><span class="star">*</span>手机：</td>
					<td><input type="text" name="phone" id="phone" class="validate[required,length[1,12]]"  value="${emp.phone}" /></td>
				</tr>
				<tr>
					<td width="5%"><span class="star">*</span>生日：</td>
					<td><input type="text" name="birthday" id="birthday" value="${easyBirthday }" class="date"></input></td>
				</tr>
				<tr>
					<td width="5%"><span class="star">*</span>年龄：</td>
					<td><input type="text" name="age" id="age" value="${emp.age }"></input></td>
				</tr>
				<tr>
					<td width="5%"><span class="star">*</span>信息：</td>
					<td><textarea  name="info" id="info" >${emp.age }</textarea></td>
				</tr>
				<tr>
					<td width="5%"><span class="star">*</span>员工部门：</td>
					<td><select  id="sel"  name="deptId" class="validate[required]"><option value="">请选择部门</option></select></td>
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/pages/common.jspf"%>
<html>
<head>
<title>新增用户</title>
<script type="text/javascript" src="${path }/common/js/ajaxfileupload.js"></script>
<script type="text/javascript">
function save() {
	var str = $("#form").serialize();
	$.ajax({
		type : 'post',
		async : false,
		url : document.forms[0].action,
		data : str,
		success : function(result) {
			//layer.msg(result.message);
			layer.confirm(result.message, {
				  btn: ['停留在当前页面','返回上一层'] //按钮
				}, function(){
				 layer.closeAll();
				}, function(){
					goBack();
			});
		},
		dataType : 'json'
	}) 
}
function goBack() {
	location.href="${path}/user/userManage.do";
}

</script>
</head>
<body>
	<form class="layui-form" action="${path }/user/addUser.do" id="form" method="post">
		<div class="layui-form-item">
			<input type="hidden" name="id" value="${user.id }" /> 
			<div class="layui-input-inline">
				<input type="hidden" id="ur" name="ur" required  lay-verify="userCheckRole"  class="layui-input"/> 
			</div>
		</div>
			</br>
			<div class="layui-form-item">
				 <label class="layui-form-label">用户账号:</label>
				<div class="layui-input-block">
					<input type="text" name="userAccount" required
						lay-verify="userAccount" value="${user.userAccount }" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')"
						<c:if test="${not empty user.id }">readonly="readonly"</c:if> 
						placeholder="请输入账号" class="layui-input" style="width: 350px;">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">联系电话:</label>
				<div class="layui-input-block">
					<input type="text" name="mobile" required lay-verify="mobile"
						value="${user.mobile }" placeholder="请输入电话号码"  <c:if test="${not empty user.id }">readonly="readonly"</c:if> 
						class="layui-input" style="width: 350px;">
				</div>
			</div>
		    <div class="layui-form-item">
			    <label class="layui-form-label">用户类型:</label>
			    <div class="layui-input-inline">
			    	<select name="userType"  id="userType" lay-filter="userType" style="width: 250px">
						<option value="">请选择用户类型</option>
						<c:forEach items="${userTypeMap }" var="ut">
							<option value="${ut.value }" <c:if test="${ut.value==user.userType }">selected=''</c:if> >${ut.key }</option>
	    				</c:forEach>
					</select>
			    </div>
		   </div>
		<div class="layui-form-item">
		    <label class="layui-form-label">选择角色:</label>
		    <div class="layui-input-block">
		      <c:forEach items="${roleList }" var="role">
		      		<input type="checkbox" name="roleId" title="${role.roleName }" value="${role.id }" lay-filter="userRole" 
		      			<c:forEach items="${userRoleList }" var="ur">
		      				<c:if test="${ur.roleId==role.id }">checked=''</c:if> 
		      			</c:forEach>
		      		>
		      </c:forEach>
		    </div>
		</div>
		
		<div style="text-align: center;">
			<button class="layui-btn layui-btn-normal layui-btn-radius" lay-filter="save" lay-submit=""
				type="button" style="width: 80px; background-color: #009688">保存</button>
			<button class="layui-btn layui-btn-warm layui-btn-radius"
				type="button" style="width: 80px; background-color: #393D49"
				onclick="goBack()">返回</button>
		</div>
	</form>
	<script type="text/javascript">
	var $,form,$form;
	layui.use(['jquery', 'form'], function(){
		 $ = layui.jquery;
	     form = layui.form();
	     $form = $('form');
	     //var urlist="${userRoleList}";
		  //自定义验证规则
		  form.verify({
			  userCheckRole:function(value){
				if (!isVal(value)) {
					return "请选择角色类型";
				}
			},
			userAccount:function(value){
				if (value.length<5) {
					return '帐号至少得5个字符啊';
				}
			},
		    title: function(value){
		      if(value.length < 3){
		        return '标题至少得3个字符啊';
		      }
		    }
		    ,mobile:[/^1[34578]\d{9}$/,"手机号码格式不正确"]
		    ,content: function(value){
		      layedit.sync(editIndex);
		    }
		  });
		  form.render(); //更新全部
		  //监听提交
		  form.on('submit(save)', function(data){
		   	save();
		    return false;
		  });
		  form.on('checkbox(userRole)', function(data){
			  var str="";
		   	$(":checkbox").each(function(){
		   		if ($(this).prop("checked")==true) {
		   			str+=this.value+",";
				}
		   	});
		   	$("#ur").val(str);
		    return false;
		  });
	});
	$(function(){
		var str="";
	   	$(":checkbox").each(function(){
	   		if ($(this).prop("checked")==true) {
	   			str+=this.value+",";
			}
	   	});
	   	$("#ur").val(str);
	})
	</script>
</body>
</html>
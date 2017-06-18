<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/pages/common.jspf"%>
<html>
<head>
<style type="text/css">
</style>
<title>用户列表</title>
<script type="text/javascript">
	function find(page){
		document.forms[0].action="${basePath }/user/userManage.do?pageStr="+page;
		document.forms[0].submit();
	}
	function add(){
		location.href="${basePath}/user/userAdd.do";
		/* layer.open({
			  type: 2,
			  title: '新增角色',
			  maxmin: true,
			  shade: 0.8,//对比度
			  anim: 3,//弹出效果
			 // time:1000,
			 // offset: 'rb', //右下角弹出
			  //closeBtn: 0, //不显示关闭按钮
			 // shadeClose: true, //开启遮罩关闭
			  area: ['650px', '80%'],
			  end: function(){ //此处用于演示
				   //layer.msg("弹窗结束调用")
			  },
			  content: '${path}/role/roleAdd.do' 
		});  */
	}
	function addUserDetail(id){
		layer.open({
			  type: 2,
			  title: '完善用户资料',
			  maxmin: true,
			  shade: 0.8,//对比度
			  anim: 3,//弹出效果
			 // time:1000,
			 // offset: 'rb', //右下角弹出
			  //closeBtn: 0, //不显示关闭按钮
			 // shadeClose: true, //开启遮罩关闭
			  area: ['850px', '90%'],
			  end: function(){ //此处用于演示
				   //layer.msg("弹窗结束调用")
			  },
			  content: '${basePath}/user/userAddDetail.do?userId='+id 
		}); 
	}
	$(function(){
		layui.use(['laypage', 'layer'], function(){
			  var laypage = layui.laypage ,layer = layui.layer;
			  laypage({
			    cont: 'lpage',
			    skin: '#fb771f',
			    pages: "${pageResult.page.totalRecord}", //总页数
			    groups: "${pageResult.page.totalPage}",//连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取  
                  var page = "${pageResult.page.currentPage}";
                  return page;  
              }(),   
            /*   prev:"",//上一页   
              next:"[]",//下一页   */
			    jump:function(obj, first){
				     if(!first){
					        //layer.msg('第 '+ obj.curr +' 页');
					        find(obj.curr);
					  }
				}
			  });
		 }); 
	});
	function edit(id){
		location.href= '${path}/user/userAdd.do?id='+id ;
	}
	function deleteUser(id,ele){
		var tip="";
		var newButton="";
		var newStatus="";
		var status=$(ele).attr("status");
		if (status=="0") {
			tip='您确定要启用该用户？';
			newButton="注销";
			newStatus="1";
		}else{
			tip='您确定要注销该用户？'
			newButton="启用";
			newStatus="0";
		}
		document.forms[0].action="${path}/user/changeStatus.do?userId="+id;
		layer.confirm(tip, {
			  btn: ['确定','放弃'] //按钮
			}, function(){
			 //layer.msg('的确很重要', {icon: 1});
			 	$("form").ajaxSubmit(function(result){
					layer.msg(result.message);
					if (result.success) {
						$(ele).find("#zhuxiao").text(newButton);
						$(ele).attr("status",newStatus);
						document.forms[0].action="${path}/user/userManage.do";
					}
				})
			}, function(){
			 /*  layer.msg('也可以这样', {
			    time: 20000, //20s后自动关闭
			    btn: ['明白了', '知道了']
			  }); */
		});
		
	}
	function setPermission(id){
		document.forms[0].action="${basePath }/role/rolePermission.do?roleId="+id;
		document.forms[0].submit();
	}
</script>
</head>
<body>
	</br>
	<form class="layui-form" action="${basePath }/user/userManage.do" method="post">
		<div class="layui-input-inline">
			<input type="text" name="userName"  value="${userQueryBean.userName}" placeholder="请输入用户名称"
				class="layui-input" style="width: 150px; margin-left: 20px">
		</div>
		&nbsp;&nbsp;&nbsp;
		<button class="layui-btn layui-btn-normal layui-btn-radius" type="submit" style="width: 100px">查询</button>
		<button class="layui-btn layui-btn-warm layui-btn-radius" type="button" style="width: 100px" onclick="add()">新增</button>
	</form>
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>用户列表</legend>
	</fieldset>

	<table class="layui-table" lay-even="" lay-skin="row" style="text-align: center;">
		<colgroup>
			<col width="100">
			<col width="100">
			<col width="100">
			<col width="200">
		</colgroup>
		<thead>
			<tr style="text-align: center;">
				<th style="text-align: center;">用户账号</th>
				<th style="text-align: center;">用户手机</th>
				<th style="text-align: center;">用户类型</th>
				<th style="text-align: center;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageResult.content }" var="user">
				<tr style="text-align: center;">
					<td>${user.userAccount }</td>
					<td>${user.mobile }</td>
					<td>
						<c:forEach items="${userTypeMap }" var="ut">
							 <c:if test="${ut.value==user.userType }">${ut.key }</c:if>
	    				</c:forEach>
	    		   </td>
					<td>
						<button title="编辑" class="layui-btn layui-btn-normal layui-btn-small" onclick="edit('${user.id}')"><i class="layui-icon"></i> <span>编辑</span></button><!-- 修改 -->
					    <button title="" class="layui-btn layui-btn-normal layui-btn-small" 
					    	onclick="deleteUser('${user.id}',this)" status="${user.accountStatus }"><i class="layui-icon"></i> 
					    	<span id="zhuxiao"><c:if test="${user.accountStatus=='0' }">启用</c:if><c:if test="${user.accountStatus=='1' }">注销</c:if></span>
					    </button>
					    <button title="" class="layui-btn layui-btn-normal layui-btn-small" 
					    	onclick="addUserDetail('${user.id}')" ><i class="layui-icon"></i> 完善资料
					    </button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
			<span id="lpage" style="margin-right: 100px"></span>
</body>
</html>
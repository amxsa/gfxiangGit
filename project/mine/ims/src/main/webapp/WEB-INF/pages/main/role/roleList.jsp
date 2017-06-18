<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/pages/common.jspf"%>
<html>
<head>
<style type="text/css">
table{  
    table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */  
}
td{  
    word-break:keep-all;/* 不换行 */  
    white-space:nowrap;/* 不换行 */  
    overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */  
    text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用*/  
}
</style>
<title>角色列表</title>
<script type="text/javascript">
	function find(page){
		document.forms[0].action="${basePath }/role/roleManage.do?pageStr="+page;
		document.forms[0].submit();
	}
	function add(){
		layer.open({
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
		}); 
	}
	$(function(){
		layui.use(['laypage', 'layer'], function(){
			  var laypage = layui.laypage ,layer = layui.layer;
			  laypage({
			    cont: 'lpage',
			    skin: '#fb771f',
			    skip:true,//跳转
			  /*   first: false,
			    last: false, */
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
		layer.open({
			  type: 2,
			  title: '编辑角色',
			  maxmin: true,
			  area: ['650px', '80%'],
			  content: '${path}/role/roleAdd.do?roleId='+id 
		}); 
	}
	function deleteRole(id){
		document.forms[0].action="${path}/role/delete.do?roleId="+id;
		layer.confirm('您确定要删除该角色？', {
			  btn: ['确定','放弃'] //按钮
			}, function(){
			 //layer.msg('的确很重要', {icon: 1});
			 	$("form").ajaxSubmit(function(result){
					if (result.success) {
						document.forms[0].action="${path}/role/roleManage.do";
						find("1");
					}
				})
			}, function(){
			 /*  layer.msg('也可以这样', {
			    time: 20000, //20s后自动关闭
			    btn: ['明白了', '知道了']
			  }); */
		});
		
	}
</script>
</head>
<body>
	</br>
	<form class="layui-form" action="${basePath }/role/roleManage.do" method="post">
		<div class="layui-input-inline">
			<input type="text" name="roleName"  value="${roleQueryBean.roleName}" placeholder="请输入角色名称"
				class="layui-input" style="width: 150px; margin-left: 20px">
		</div>
		&nbsp;&nbsp;&nbsp;
		<button class="layui-btn layui-btn-normal layui-btn-radius" type="submit" style="width: 100px">查询</button>
		<button class="layui-btn layui-btn-warm layui-btn-radius" type="button" style="width: 100px" onclick="add()">新增</button>
	</form>
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>角色列表</legend>
	</fieldset>

	<table class="layui-table" lay-even="" lay-skin="row" style="text-align: center;">
		<colgroup>
			<col width="20">
			<col width="150">
			<col width="150">
			<col width="200">
		</colgroup>
		<thead>
			<tr style="text-align: center;">
				<th style="text-align: center;"><input type="checkbox"  id="checkAll"></th>
				<th style="text-align: center;">角色名称</th>
				<th style="text-align: center;">角色描述</th>
				<th style="text-align: center;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageResult.content }" var="role">
				<tr style="text-align: center;">
					<td><input type="checkbox"></td>
					<td>${role.roleName }</td>
					<td class="hoverTip">${role.roleNote }</td>
					<td>
						<button title="编辑" class="layui-btn layui-btn-normal layui-btn-small" onclick="edit('${role.id}')"><i class="layui-icon"></i> 编辑</button><!-- 修改 -->
					    <button title="删除" class="layui-btn layui-btn-normal layui-btn-small" onclick="deleteRole('${role.id}')"><i class="layui-icon"></i> 删除</button><!--删除-->
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
			<span id="lpage" style="margin-right: 100px"></span>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/pages/common.jspf"%>

<html>
<head>
<title>新增角色</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/zTree/zTreeStyle.css"  />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/zTree/jquery.ztree.core-3.5.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/zTree/jquery.ztree.excheck-3.5.js" ></script>
<script type="text/javascript">
	function getValFromTree(id){
		var val = '';
		var treeObj = jQuery.fn.zTree.getZTreeObj(id);
		var nodes = treeObj.getCheckedNodes(true);
		for(var i =0;i<nodes.length;i++){
			if(!nodes[i].isParent){
				val +=nodes[i].id+',';
			}
		}
		if(val.length>0)val=val.substring(0,val.length-1);	
		return val;	
	}
	function getJson(action,params){
	    var temp = "";
		jQuery
		.ajax({
			type : "POST",
			url : action,
			async:false,
			data : params,
			dataType : "text",
			success : function(json) {			
				temp = json;								
		    }
	    });	
		return temp;	
	}
	function save(){
		//layer.load();
		$("#menus").val(getValFromTree("treeDemo")); 
		var str=$("#form").serialize()
		$.ajax({
			type:'post',
			async:false,
			url:document.forms[0].action,
			data:str,
			success:function(result){
				//layer.closeAll("loading");
				layer.msg(result.message);
			},
			dataType:'json'
		}) 
	}
	function goBack(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.location.reload();
		parent.layer.close(index)
	}
	
	var setting = {
			check: {
				enable: true
			},
			data: {
				key: {
					children: "children",
					name: "name"
				  },
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId",
					rootPId: 0
				}
			}
		};
 		
		var zNodes = getJson("${path}/role/getMenus.do","roleId=");
		//把字符串转换成JSON对象
		var zNodes = jQuery.parseJSON(zNodes);
		var menuIds="";
		if ("${not empty role.id}") {//编辑
			menuIds =getJson("${path}/role/getCheckedMenus.do",{roleId:"${role.id}"});
		}
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
			var nodes = treeObj.getNodes();
			for (var i=0; i < nodes.length; i++) {
				if(menuIds!='noData'){
					var perimessionId = menuIds.split(",");
					if(perimessionId.length>0){
						for(var i=0;i<perimessionId.length;i++){
							 var node = treeObj.getNodeByParam("id",perimessionId[i]);
							 treeObj.checkNode(node, true, true)
						}
					}
				}
			}
		});
</script>
</head>
<body>
	<form class="layui-form" action="${path }/role/save.do" id="form" method="post">
		<input type="hidden" name="id"  value="${role.id }"/>
		<input type="hidden" name="createAccount" id="createAccount" value="${role.createAccount }"/>
		<input type="hidden" name="createTime" id="createTime" value="${role.createTime }"/>
		<input type="hidden" name="menus" id="menus" value=""/>
		<div class="layui-form-item">
			<label class="layui-form-label">角色名称:</label>
			<div class="layui-input-block">
				<input type="text" name="roleName" required lay-verify="title" value="${role.roleName }"
					placeholder="请输入标题"  class="layui-input" style="width: 350px;margin-top: 20px">
			</div>
			</br>
			<label class="layui-form-label">角色描述:</label>
			<div class="layui-input-block" style="width: 350px">
      			<textarea name="roleNote" placeholder="请输入内容" class="layui-textarea" >${role.roleNote }</textarea>
    		</div>
    		</br>
			<label class="layui-form-label">角色权限:</label>
			<div class="layui-input-block" style="width: 350px">
				<ul id="treeDemo" class="ztree"></ul>
    		</div>
		</div>
		<div style="text-align: center;">
			<!-- <button class="layui-btn layui-btn-normal layui-btn-radius" type="button" style="width: 80px;background-color: #009688" onclick="save()">保存</button> -->
   			<button class="layui-btn layui-btn-normal layui-btn-radius" lay-submit="" lay-filter="save" style="width: 80px;background-color: #009688">保存</button>
			<button class="layui-btn layui-btn-warm layui-btn-radius" type="button" style="width: 80px;background-color: #393D49" onclick="goBack()">返回</button>
		</div>
	</form>
	<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate'], function(){
		  var form = layui.form()
		  ,layer = layui.layer
		  ,layedit = layui.layedit
		  ,laydate = layui.laydate;
		  //创建一个编辑器
		  var editIndex = layedit.build('LAY_demo_editor');
		  //自定义验证规则
		  form.verify({
		    title: function(value){
		      if(value.length < 3){
		        return '标题至少得3个字符啊';
		      }
		    }
		    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
		    ,content: function(value){
		      layedit.sync(editIndex);
		    }
		  });
		  //监听提交
		  form.on('submit(save)', function(data){
		   	save();
		    return false;
		  });
	});
	</script>
</body>
</html>
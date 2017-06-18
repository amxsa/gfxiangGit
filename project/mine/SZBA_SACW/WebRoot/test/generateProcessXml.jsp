<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />

<meta http-equiv="X-UA-Compatible" content="IE=7">
<title>生成流程自定义xml</title>
<style type="text/css">
.list02a .ztree li {
	clear: both;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/json2.js"></script>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a
			href="${pageContext.request.contextPath}/property/queryForPage.do?viewName=registerProperty"
			target="main">首页 </a>>> 生成xml</span>
	</div>
	<div class="content">
		<div class="toptit">
		<h1 class="h1tit">目录：<input name="dirPath" type="text" value="G:/MyEclipse10/SZBA_SACW/WebRoot/flownode/temp" style="width:300px;height:30px"></h1>
			<h1 class="h1tit">文件名：<input name="fileName" type="text" value="temp.xml" style="width:500px;height:30px"></h1>
		</div>
		<form action=""
			method="post">
			<div class="flownode">
				<div class="toptit">
					<h1 class="h1tit">单个节点信息（flownode）</h1>
					<span class="add">
					<h1 class="h1tit" style="float:right;border:1px solid;border-radius:5px;width:30px;text-align:center;">+</h1> </span>
					<span class="remove" style="">
					<h1 class="h1tit" style="margin-right:10px;float:right;border:1px solid;border-radius:5px;width:30px;text-align:center;">-</h1> </span>
				</div>
				<ul class="list02a w50">
					<li><label>name：</label><input name="name" type="text" value="agree"/></li>
					<li><label  style="width:150px">isSameAsCurrentDept：</label> 
					<select name="isSameAsCurrentDept">
							<option value="true">相同部门</option>
							<option value="">不填</option>
							<option value="false">不同部门</option>
					</select>(下个节点的审批人是否与当前审批人同部门)
					</li>
					<li><label>roles：</label><input name="roles" type="text" value=""/>(下个节点审批的角色)</li>
					<li><label>propertyStatus：</label>
						<select name="propertyStatus">
							${mapping:generatePropertyStatusKeySelect() }
						</select>(审批结束后财物状态)</li>
					</li>
					<li><label>actualPosition：</label>
						<select name="actualPosition">
							${mapping:generateActualPositionSelect() }
						</select>(审批结束后财物位置)</li>
					</li>
					<li><label style="width:120px">applicationStatus：</label>
						<select name="applicationStatus">
							<option value="N">审批中</option>
							<option value="F">不同意</option>
							<option value="Y">同意</option>
							<option value="S">已实施</option>
						</select>(审批结束后审批单的状态)
						</li>
					<li class="w100"><label style="width:120px">conditionExpression：</label> 
					<textarea name="conditionExpression" row="" col=""></textarea>
					</li>
				</ul>
				
				<ul class="list02a w50 depts">
						<li class="w100">
							<span class="addDept">
							<h1 class="h1tit" style="float:right;border:1px solid;border-radius:5px;width:30px;text-align:center;">+</h1> </span>
							<span class="removeDept" style="">
							<h1 class="h1tit" style="margin-right:10px;float:right;border:1px solid;border-radius:5px;width:30px;text-align:center;">-</h1> </span>
							<label>departmentId：</label><input name="department" type="text" value=""/>
							<label style="width:120px">递归查询下级部门：</label>
							<label>是：</label><input type="checkbox" value="true" name="isRecursion"/>
							<label>否：</label><input type="checkbox" value="false" name="isRecursion" checked/>
							
							(下个节点审批人的具体部门，与当前节点审批人部门相同则不需要填)
					</li>
				</ul>
			</div>

			<div class="dbut">
				<a href="javascript:void(0)" class="generate">生成</a> 
				<input type="reset" value="重置"/>
					
			</div>
		</form>
		

	</div>

	<script type="text/javascript">
	var flownode=function(){
		var name;
		var isSameAsCurrentDept;
		var roles;
		var propertyStatus;
		var applicationStatus;
		var conditionExpression;
		var departments = [];
	};
	
	var DepartmentItem = function(){
		var departmentId;
		var isRecursion;
	}
	
	$(".add").click(function(){
		 //$(".flownode:eq(0)").clone(true).insertAfter(".flownode:eq(0)");
		 var flownode = $(this).parents(".flownode");
		 
		 flownode.clone(true).insertAfter(flownode);
	});
	$(".addDept").click(function(){
		 //$(".flownode:eq(0)").clone(true).insertAfter(".flownode:eq(0)");
		 var flownode = $(this).parents(".depts");
		 
		 var newNode = flownode.clone(true);
		 
		// var newRadio = newNode.find(":radio");
		// newRadio.attr("name", newRadio.attr("name")+"1");
		 newNode.insertAfter(flownode);
	});
	$(".remove").click(function(){
		if($(".flownode").length === 1){
			alert("只剩下最后一个了");
			return;
		}
		 $(this).parents(".flownode").remove();
	});
	$(".removeDept").click(function(){
		 $(this).parents(".depts").remove();
	});
	
	$(".generate").click(function(){
		var length = $(".flownode").length;
		var flownodes = new Array(length);
		for(var i = 0; i < length; i++){
			flownodes[i] = new flownode();		
		}
		
		var fileName = "";
		var dirPath = "";
		fileName = $(":text[name='fileName']").val();
		dirPath = $(":text[name='dirPath']").val();
		
		$(".flownode").each(function(i){
			
			flownodes[i].name=$(this).find(":text[name='name']").val();
			flownodes[i].isSameAsCurrentDept=$(this).find("select[name='isSameAsCurrentDept']").val();
			flownodes[i].roles=$(this).find(":text[name='roles']").val();
			flownodes[i].propertyStatus=$(this).find("select[name='propertyStatus']").val();
			flownodes[i].actualPosition=$(this).find("select[name='actualPosition']").val();
			flownodes[i].applicationStatus=$(this).find("select[name='applicationStatus']").val();
			flownodes[i].conditionExpression=$(this).find("textarea[name='conditionExpression']").val();
			flownodes[i].departments = [];
			var dept;
			$(this).find(".depts").each(function(j){
				dept = new DepartmentItem();
				dept.departmentId = $(this).find(":text[name='department']").val();
				dept.isRecursion = $(this).find(":checkbox[name='isRecursion']:checked:first").val();
				if(dept.departmentId != null && dept.departmentId !== ''){
					flownodes[i].departments.push(dept);
				}
			});
		});
		
		var jsonStr = JSON.stringify(flownodes);
		
		$.ajax({
			url:sysPath+"/test/generateProcessXml.do",
			type:"post",
			contentType: "application/json; charset=utf-8",
			data:jsonStr+"#"+fileName+"#"+dirPath,
			success:function(backData){
				alert(backData);
				jsonStr = "";
			},
			error:function(){
				alert("连接失败");
				jsonStr = "";
			}
		});
	
	});
	
		
		
	</script>

</body>
</html>

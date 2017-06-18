<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>选择损毁状态</title>
</head>
<body style="overflow: hidden;">
<!-- 添加模板 -->
<form action="javascript:void(0)" method="post" id="checkForm">
	<ul class="list02a w50" >
		<li class="w100">
			<label>损毁状态：</label>
			<select id="damageStatus" name="damageStatus">
				<option>请选择</option>
				${mapping:generateDamageStatusSelect() }
			</select>
		</li>
		<li class="w100">
		<label>损毁原因：</label>
			<textarea id="damageReason" name="damageReason" rows="" cols=""></textarea><span class="need" style="margin-left:10px">*</span>
		</li>
	</ul>
</form>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		var status = art.dialog.data('status');
		var reason = art.dialog.data('reason');
		if(status !== ''){
			$("#damageStatus").val(status);
		}
		if(reason !== ''){
			$("#damageReason").val(reason);
		}
		toggleReasonText(status);
	});
	$("#damageStatus").change(function(){
		toggleReasonText(this.value);
	});
	
	function toggleReasonText(status) {
		if(status === 'WH'){
			$("#damageReason").val("");
			$("#damageReason").prop("disabled", true);
			$("#damageReason").parent(".w100").hide();	
		}else{
			$("#damageReason").parent(".w100").show();
			$("#damageReason").prop("disabled", false);
		}
	}
</script>
</html>
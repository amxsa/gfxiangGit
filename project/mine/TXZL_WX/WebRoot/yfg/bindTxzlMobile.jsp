<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>开通通信助理一分购会员</title>
<link href="<%=path%>/css/onefengou.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" language="javaScript">
	function initDoc() {
		document.body.height = screen.height;
		document.getElementById("bodyDiv").style.height = screen.height;
		setHeight();
	}
	function showDtl() {
		document.getElementById("divMask").style.display = "block";
	}
	function hideDtl() {
		document.getElementById("divMask").style.display = "none";
	}
	
	function GO(){
		document.forms[0].submit();
	}
	function sendSms(){
		var mobile = $("#mobile").val();
		$.post("<%=path%>/yfg/UserAction_sendSms.do", {
			mobile : mobile
		}, function(data) {
			if (data != "") {
				array = data.split("|");
				$("#msg").text(array[0]);
				if(array.length>1){
					$("#passwordSms").val(array[1]);
				}
			}
		});
	}
</script>
</head>
<body>
	<form action="<%=path%>/yfg/UserAction_bindTxzlMobile.do" method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<input type="hidden" name="type" value="${param.type }" id="type" />
		
		<div class="top">
			<a class="back" onClick="history.back()"><img
				src="images/back.png" />
			</a>
			<h2>开通通信助理一分购会员</h2>
			<span>
				<a href="<%=path%>/user/index.jsp"><img src="<%=path%>/images/yfg/home.png"></a>
			</span>
		</div>

		<div class="box">
			<h3 class="tith3">绑定手机号码开通一分购通信助理会员</h3>
			<div class="user_1">
				<img src="<%=path %>/images/yfg/yfglogo2.jpg">
				<p>开通广东电信通信助理任何个套餐产品，即可成为优惠价会员，更可以成为一分购会员，优惠多多，尊享一分抢购商品。</p>
			</div>
			<div class="user_2">
				<ul class="list2">
					<li style="color: red;" id="msg">${requestScope.msg }</li>
					
					<li><label>手机号码</label><input type="text" name="mobile"
						maxlength="20" id="mobile" value="${requestScope.mobile}" placeholder="" class="b1" ${not empty requestScope.mobile?'readonly="readonly"':'' }/>
						<input name="" type="button" value="获取短信验证码" class="b2" onclick="sendSms();"  />
					</li>
					<li><label>短信验证</label><input type="text" name="passwordSms" id="passwordSms"  
						maxlength="20" value="" placeholder="输入6位验证码" />
					</li>
				</ul>
			</div>
			<c:if test="${empty requestScope.xieyi }">
				<div class="hyxy">
					<span class="select"><em></em><input type="radio" value=""
						name="gender" checked="checked" />
					</span>
					<h4>
						同时开通一分购会员<a onClick="javaScript:showDtl();" style="cursor:pointer;">《同意会员协议》</a>
					</h4>
				</div>
			</c:if>
			<div class="bbut3">
				<a href="javaScript:GO();">确认开通</a>
			</div>
		</div>


		<div id="divMask" class="mask" style="display:none;">
			<div class="mask_box">
				<h2>《同意会员协议》</h2>
				<div class="content">
					<p>
						<strong>1．本网站服务条款的确认和接纳</strong>
					</p>
					<p>本网站各项服务的所有权和运作权归本网站拥有。</p>
					<p>
						<strong>2．用户必须</strong>
					</p>
					<p>(1)自行配备上网的所需设备， 包括个人电脑、调制解调器或其他必备上网装置。</p>
					<p>(2)自行负担个人上网所支付的与此服务有关的电话费用、 网络费用。</p>
					<p>
						<strong>3． 有关个人资料</strong>
					</p>
					<p>用户同意：</p>
					<p>(1) 提供及时、详尽及准确的个人资料。</p>
					<p>(2).同意接收来自本网站的信息。</p>
					<p>(3) 不断更新注册资料，符合及时、详尽准确的要求。所有原始键入的资料将引用为注册资料。</p>
					<p>(4)本网站不公开用户的姓名、地址、电子邮箱和笔名，以下情况除外：</p>
					<p>（a）用户授权本网站透露这些信息。</p>
					<p>（b）相应的法律及程序要求本网站提供用户的个人资料。如果用户提供的资料包含有不正确的信息，本网站保留结束用户使用本网站信息服务资格的权利。</p>
				</div>
				<div class="bbut4">
					<a class="btn" href="javaScript:hideDtl();">确定</a>
				</div>
			</div>
		</div>

	</form>
</body>
</html>




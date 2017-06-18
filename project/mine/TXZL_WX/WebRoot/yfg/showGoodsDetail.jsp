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
<title>一分购产品详情</title>
<link href="<%=path %>/css/onefengou.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="<%=path %>/css/banner.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="<%=path %>/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
<script type="text/javascript">
	window.jQuery = window.Zepto;
</script>
<script type="text/javascript">
	function GO(){
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="<%=path%>/yfg/addOrder.jsp"  method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<input type="hidden" name="goodsid" value="${po.id }" />
		
		<input type="hidden" name="payType" id="payType" value="1" />
		<input type="hidden" name="unitPrice"  value="${po.discountPrice }" />
		<input type="hidden" name="payCnt"  value="1" />
		
		<div class="top">
			<a class="back" onClick="history.back()"><img
				src="<%=path%>/images/yfg/back.png" />
			</a>
			<h2>产品详情</h2>
			<a href="<%=path%>/user/index.jsp"><img src="<%=path%>/images/yfg/home.png">
				</a>
			</span>
		</div>
	
		<div class="banner scroll-wrapper" id="idContainer2"
			ontouchstart="touchStart(event)" ontouchmove="touchMove(event);"
			ontouchend="touchEnd(event);">
			<ul class="scroller" style="position:relative;left:0px;width:300%"
				id="idSlider2">
				 <c:forTokens var="str" items="${po.smallImg}" delims="," varStatus="status">
				 	<li style="width:-100.0%"><img src="<%=path%>/images/yfg/${str}" alt="" />
					</li>
				 </c:forTokens>
			</ul>
			<ul class="new-banner-num new-tbl-type" id="idNum">
			</ul>
		</div>
		<input type="hidden" value="3" id="activity" />
		<input type="hidden" value="3" id="crazy" />
		<script type="text/javascript" src="<%=path%>/js/scroll.js"></script>
	
		<div class="dt_cont1">
			<div class="dt_tit">
				<h1>${po.name }${po.nameDes }</h1>
				<span><a class="share" href="#"></a>
				</span>
			</div>
			<div class="dt_price">
				<div class="pr_lt">
					<h4>
						<span>￥${po.discountPrice }</span><i>优惠价</i>
					</h4>
					<p>
						市场价：<em>￥${po.unitPrice }</em>
					</p>
				</div>
				<div class="pr_rt">
					<p>
						<c:if test="${po.isFreePost=='Y' }">卖家包邮</c:if>
						<c:if test="${po.isFreePost=='N' }">邮费自付</c:if>
					</p>
					<p>${po.commodity }</p>
				</div>
			</div>
		</div>
		<div class="dt_cont2">
			<h2>请选择支付方式</h2>
			<div class="box">
				<ul class="list1">
					<li><label>支付宝</label><span class="select"><em></em><input
							type="radio" value="" name="gender" checked="checked" />
					</span>
					</li>
					<li><label>信用卡快捷支付（推荐）</label><span><em></em><input
							type="radio" value="" name="gender" />
					</span>
					</li>
					<li class="end"><label>储蓄卡快捷支付</label><span><em></em><input
							type="radio" value="" name="gender" />
					</span>
					</li>
				</ul>
			</div>
		</div>
		<div class="bbut3">
			<a href="javaScript:GO();">确定抢购</a>
		</div>
	</form>	
</body>
</html>




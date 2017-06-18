<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String basePath = SiteUtil.getSiteUrl();
%>

<!doctype html>
<head>
<base href="<%=basePath%>">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" /><!-- 删除苹果默认的工具栏和菜单栏 -->
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /><!-- 设置苹果工具栏颜色 -->
    <meta name="format-detection" content="telephone=no, email=no" /><!-- 忽略页面中的数字识别为电话，忽略email识别 -->
    <!-- 启用360浏览器的极速模式(webkit) -->
    <meta name="renderer" content="webkit">
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
    <meta name="HandheldFriendly" content="true">
    <!-- 微软的老式浏览器 -->
    <meta name="MobileOptimized" content="320">
    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="portrait">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="portrait">
    <!-- UC强制全屏 -->
    <meta name="full-screen" content="yes">
    <!-- QQ强制全屏 -->
    <meta name="x5-fullscreen" content="true">
    <!-- UC应用模式 -->
    <meta name="browsermode" content="application">
    <!-- QQ应用模式 -->
    <meta name="x5-page-mode" content="app">
    <!-- windows phone 点击无高光 -->
    <meta name="msapplication-tap-highlight" content="no">
    <!-- 适应移动端end --> 
    <title>客户回访评价</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/my_msg.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="bank/sg/js/loadData.js"></script>
    <script type="text/javascript" src="bank/sg/js/common.js"></script>
    <script type="text/javascript">
	//解析url  例如...a.html?aa=bbb&cc=ddd  那么获取参数可以用bbb=GetURLParameter("aa")
	function GetURLParameter(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
    
    $(function(){
        var status=GetURLParameter("status");
        if(status=="1"){
        	$(".confirm_btn").css({'background':"#d7d7d7","color":"#525252"});
        	$(".confirm_btn").html("已受理")
        	$(".confirm_btn").unbind("click");
        }
      //获取数据
        var managerId=GetURLParameter("managerId");
      	var d = {
      			managerId:managerId
         };
		sg_getList("${pageContext.request.contextPath}/front/amountOrder/getOrder", d,function(data){
			var cust=data.bus;
			$("#phone").text(cust.phone);
			$("#orderName").text(cust.orderName);
			$("#userPhoto").attr('src',data.userPhoto);
			$("#cphone").attr('href','tel:'+cust.phone);
			$("#custle").text(data.custle);
			$("#orderDate").text(data.orderDate);
			$("#amount").text(cust.amount);
		});
		
		 $('.confirm_btn').click(function(event) {
			 var d = {
		      		managerId:managerId
		     };
			 sg_getList("${pageContext.request.contextPath}/front/amountOrder/updateOrder", d,function(data){
				if (data.succ) {
					$(".confirm_btn").css({'background':"#d7d7d7","color":"#525252"});
		        	$(".confirm_btn").html("已受理")
					$(".confirm_btn").unbind("click");
		        	location.href="${pageContext.request.contextPath}/bank/sg/score/scoreMsg.jsp?status=3&managerId="+managerId;
				}else{
					msgBox("受理失败，请重试",function(){
					});
				}
			});
		});
    })
    </script>
</head>
    <body>
    <div class="box main_pd big_box">
        <img src="bank/sg/img/aa.png" alt="" class="my_p" id="userPhoto"/>
        <p class="name" id="orderName"></p>
        <p class="core" id="custle"></p>
        <p class="phone_num">电话：<em id="phone"></em>
        <a href="" class="call" id="cphone"></a> </p>  
    </div>
   
    <div class="box main_pd">
        <p class="time_p">预约时间：<em id="orderDate" style="display: inline-block;padding:0;width:auto;color: #ff6600;font-size: 16px;
  ;"></em></p>
        <p class="time_p">取现金额：<em id="amount"></em>万元</p>
        <div class="confirm_btn">受理</div>
        <!-- <div class="confirm_btn" style="background:#e1e1e1">取消</div> -->
        
    </div>
    </body>
</html>
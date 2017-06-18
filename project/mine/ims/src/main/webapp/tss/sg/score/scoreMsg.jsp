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
    <title>申请通过</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/scoreMsg.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="bank/sg/js/check.js"></script>
    <script type="text/javascript" src="bank/sg/js/getJson.js"></script>
    <script type="text/javascript" src="bank/sg/js/common.js"></script>
    <script type="text/javascript">
		function GetURLParameter(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]);
			return null;
		}
		function toPage(){
			location.href="${pageContext.request.contextPath}/bank/sg/managerCenter.jsp";
		}
        $(function(){
			var managerId=GetURLParameter("managerId");
			$("#managerId").val(managerId);
			var status=GetURLParameter("status");
			$("#status").val(status);
			if(status==1){
				$("#content").text("您好！您的贷款申请已受理，会有专职的客户经理跟您接洽，请耐心等待！");
			}else if(status==2){
				$("#content").text("您好！您的贷款申请未通过。");
			}else if(status==3){
				$("#content").text("您好！您的申请已受理。");
			}
            $('.confirm_btn').click(function(event) {
            	$("#msgContent").val($("#content").val());
				ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/applyLoan/confirmPass'},function(data){
				if (data.succ) {
					$('.layer').fadeIn(100);        
					$('.layer-bg').fadeTo(100,0)  // 半透明
					$(".box,.confirm_btn").hide();
					setTimeout("toPage()", 3000);
				}else{
					msgBox("失败，请重试",function(){
					});
				}
				});
        });

    });
    </script>
    
</head>
    <body>
    <div class="box main_pd">
		<input type="hidden" name="managerId" id="managerId" sg_json="*"/>
		<input type="hidden" name="status" id="status" sg_json="*"/>
		<input type="hidden" name="msgContent" id="msgContent" sg_json="*"/>
        <textarea name="content" id="content" sg_json="*" cols="30" rows="10" placeholder="请留下您的宝贵意见" class="area_box">您申请的额度为50万的住房贷款，已经预审核通过，请在10个工作日内，携带如下资料到XX网点</textarea>
    </div>
    <div class="confirm_btn">确认并通知用户</div>
    </body>
     <!-- 模态窗口 -->
        <div class="layer">
            <p>通知发送成功！</p>
        </div>
        <div class="layer-bg"></div>   
</html>
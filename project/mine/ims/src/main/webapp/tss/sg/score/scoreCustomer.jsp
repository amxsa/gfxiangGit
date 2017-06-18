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
    <title>申请人留言</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/scoreCustomer.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="bank/sg/js/check.js"></script>
    <script type="text/javascript" src="bank/sg/js/getJson.js"></script>
    <script type="text/javascript" src="bank/sg/js/loadData.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="bank/sg/js/common.js"></script>
    <script type="text/javascript">
        // 星星评分
	function GetURLParameter(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]);
			return null;
	}
    $(function(){
        var recordId=GetURLParameter("recordId");
		var d = {
			recordId:recordId
		};
            //获取数据
		sg_getList("${pageContext.request.contextPath}/front/score/getScoreDetail", d,function(data){
			if(data.status=="2"){
				$(".confirm_btn").css({'background':"#d7d7d7","color":"#525252"});
	        	$(".confirm_btn").html("您的留言已成功")
	        	$(".confirm_btn").unbind("click");
			}
			$("#userName").text(data.userName);
			$("#custName").text(data.custName);
			$("#msgContent").text(data.msgContent);
			$("#empNo").text(data.empNo);
			$("#phone").text(data.phone);
			$("#tphone").attr('href', "tel:"+data.phone);
			$("#userPhoto").attr('src',data.userPhoto);
			$("#managerId").val(data.managerId);
			var temp="评分：";
			if(data.score!=""){
				var s=data.score.split(".");
				if(s.length==1){
					for ( var i = 1; i <=s[0]; i++) {
						temp += '<em class="star_in"></em>';
					}
				}else{
					for ( var i = 1; i <=s[0]; i++) {
						temp += '<em class="star_in"></em>';
					}
					if(s[1]!='00'){
						temp += '<em class="minstar"></em>';
					}
				}
			}
			$("#score").html(temp);
		});

        $("#recordId").val(recordId);
		$('.confirm_btn').click(function(event) {
			$("#returnMsg").val($("#res").val());
			ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/score/saveMsg'},function(data){
				if (data.succ) {
					$('.layer').fadeIn(100);        
					$('.layer-bg').fadeTo(100,0)  // 半透明
					$(".box,.confirm_btn").hide();
					setTimeout("wx.closeWindow()", 3000);
				}else{
					msgBox("失败，请重试",function(){
					});
				}
			});
		});
    })
    </script>
</head>
    <body>
    <div class="box main_pd">
        <div class="thanks_box">
            <p class="top_p">尊敬的<em id="custName"></em>：</p>
            <p class="details_p" id="msgContent"></p>
        </div>
        <div class="item pd" style="border:0;">
            <div class="item_left">
                <img src="bank/sg/img/pic.png" alt="" id="userPhoto"/>
            </div>
            <div class="item_right">
                <p class="name_p">客户经理：<em id="userName"></em></p>
                <p style="padding-bottom:4px;">部门：<em id="empNo"></em></p>
                <p id="score"></p>            
            </div>  
               
        </div> 
        <p class="phone_num">联系电话：<em id="phone"></em>
        <a href="" class="call" id="tphone"></a> </p></p>
           
    </div>
    <div class="box main_pd">
        <p class="mark">请留言</p>
		<input type="hidden" name="recordId" id="recordId" sg_json="*"/>
		<input type="hidden" name="managerId" id="managerId" sg_json="*"/>
		<input type="hidden" name="returnMsg" id="returnMsg" sg_json="*"/>
        <textarea name="res" id="res"  cols="30" rows="10" placeholder="您好，请尽快处理" class="area_box"></textarea>
        <div class="confirm_btn">提交</div>
        
    </div>
    </body>
    <!-- 模态窗口 -->
       <div class="layer">
           <p id="ccccc">提交成功！</p>
       </div>
       <div class="layer-bg"></div>   
</html>
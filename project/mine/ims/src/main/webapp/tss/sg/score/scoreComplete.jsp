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
    <link rel="stylesheet" type="text/css" href="bank/sg/css/scoreComplete.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="bank/sg/js/check.js"></script>
    <script type="text/javascript" src="bank/sg/js/getJson.js"></script>
    <script type="text/javascript" src="bank/sg/js/loadData.js"></script>
    <script type="text/javascript" src="bank/sg/js/common.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
	function GetURLParameter(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]);
			return null;
	}
        // 星星评分
    $(function(){
		var recordId=GetURLParameter("recordId");
		var d = {
			recordId:recordId
		};
		var logType=GetURLParameter("logType");
		$(".cc").hide();
		if(logType=="0"){
			$(".cc").show();
		}
            //获取数据
		sg_getList("${pageContext.request.contextPath}/front/score/getScoreDetail", d,function(data){
			if(data.status=="2"){
				$(".confirm_btn").css({'background':"#d7d7d7","color":"#525252"});
	        	$(".confirm_btn").html("您已成功评价")
	        	$(".confirm_btn").unbind("click");
			}
			$("#userName").text(data.userName);
			$("#msgContent").text(data.msgContent);
			$("#phone").text(data.phone);
			$("#tphone").attr('href', "tel:"+data.phone);
			$("#userPhoto").attr('src',data.userPhoto);
			$("#empNo").text(data.empNo);
			$("#managerId").val(data.managerId);
			$("#custName").text(data.custName);
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
			$("#scores").html(temp);
		});

        $("#recordId").val(recordId);
		$('.confirm_btn').click(function(event) {
			$("#scoreInfo").val($("#scoreIn").val());
			ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/score/saveScore'},function(data){
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

        var fenshu= 1;                             // 全局
        $('.star li').click(function(event) {   
            var index = $(this).index();             // 获取当前索引值，和分数相关
            fenshu = index+1;                        // 索引值和分数的关系，分数比索引值多1
            $('.star span').html(fenshu);            // 改变文字
            $('.star li').removeClass();                 // 先清空，再点亮
            $('.star li:lt('+fenshu+')').addClass('on'); 
            $("#score").val(fenshu);
        })
        .mouseenter(function(event) { 
            var index = $(this).index();
            var fenshu = index+1;                        // 相对
            $('.star li').removeClass();                 // 先清空，再点亮
            $('.star li:lt('+fenshu+')').addClass('on'); 
        })
        .mouseleave(function(event) {                   // 鼠标离开的时候，回到真正的分数
            $('.star li').removeClass();                 // 先清空，再点亮
            $('.star li:lt('+fenshu+')').addClass('on'); 
        });
    })
    </script>
</head>
    <body>
    <div class="box main_pd">
        <div class="thanks_box">
            <p class="top_p">尊敬的<em id="custName"></em>：</p>
            <p class="details_p" id="msgContent"></p>
            <p class="cc">a)身份证原件及复印件；</p>
            <p class="cc">b)房产证原件及复印件；</p>
            <p class="cc">c)结婚证原件及复印件；</p>
            <p class="cc">d)银行账号流水（近3个月）；</p>
            <p class="cc">e)收入证明；</p>
        </div>
        <div class="item pd" style="border:0;">
            <div class="item_left">
                 <img src="bank/sg/img/pic.png" alt="" id="userPhoto"/>
            </div>
            <div class="item_right">
                <p class="name_p" >客户经理：<em id="userName"></em></p>
                <p style="font-size:18px;padding-bottom: 4px;">部门：<em id="empNo"></em></p>
                <p  id="scores" style="font-size:18px;"></p>
            </div>  
          
        </div> 
        <p class="phone_num">联系电话：<em id="phone"></em>
        <a href="" class="call" id="tphone"></a> </p>  
    </div>
    
    <div class="box main_pd">
        <p class="mark">请为本次服务评分</p>
        <div class="star_box">
            <ul class="star">
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        	</ul>   
        </div>
		<input type="hidden" name="recordId" id="recordId" sg_json="*"/>
		<input type="hidden" name="managerId" sg_json="*" id="managerId"/>
		<input type="hidden" name="score" sg_json="*" id="score"/>
		<input type="hidden" name="scoreInfo" sg_json="*" id="scoreInfo"/>
        <textarea name="scoreIn" id="scoreIn" cols="30" rows="10" placeholder="请留下您的宝贵意见" class="area_box"></textarea>
        <div class="confirm_btn">提交</div>
    </div>
    </body>
    <div class="layer">
           <p id="ccccc">提交成功！</p>
       </div>
       <div class="layer-bg"></div> 
</html>
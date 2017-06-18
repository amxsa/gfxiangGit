<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.threegms.sdplatform.util.StringUtil"%>
<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String weid = StringUtil.getReqId(request,"weid");
//String fansId = StringUtil.getReqId(request,"fansId");
String openid = StringUtil.getReqId(request,"openid");
String basePath = SiteUtil.getSiteUrl();
%>
<!doctype html>
<html>
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
    <title>申请页面</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bigappleform.css" />
   
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script src="bank/sg/js/common.js" type="text/javascript"></script>
    <script src="bank/sg/js/mobiscroll.js" type="text/javascript"></script>
    <link href="bank/sg/css/mobiscroll.css" rel="stylesheet" type="text/css">
    <script src="bank/sg/js/check.js" type="text/javascript"></script>
    <script src="bank/sg/js/getJson.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">   
    // 弹框
    function GetURLParameter(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
    $(function(){
    	var currYear = (new Date()).getFullYear();  
    	var curDate = new Date(); 
    	var nextDate = new Date(curDate.getTime() + 24*60*60*1000);  //后一天
        var opt={};
        opt.date = {preset : 'date'};
        opt.datetime = {preset : 'datetime'};
        opt.time = {preset : 'time'};
        opt.default = {
            theme: 'android-ics light', //皮肤样式
            display: 'modal', //显示方式 
            mode: 'scroller', //日期选择模式
            dateFormat: 'yyyy-mm-dd',
            lang: 'zh',
            showNow: true,
            minDate:nextDate,
            nowText: '今天',
            startYear: currYear - 10, //开始年份
            endYear: currYear + 10 //结束年份
        };

        $("#orderDate").mobiscroll($.extend(opt['date'], opt['default']));
        var optDateTime = $.extend(opt['datetime'], opt['default']);
        var optTime = $.extend(opt['time'], opt['default']);
        $("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
        $("#appTime").mobiscroll(optTime).time(optTime);
    	var d={};
    	sg_getList("${pageContext.request.contextPath}/front/amountOrder/getLobby", d,function(data){
			var rows = data.lobby;
			var c = "";
			for ( var i = 0; i < rows.length; i++) {
				c += '<option value="'+rows[i]["lobbyNo"]+'">'+rows[i]["name"]+'</option>';
			}
			$("#lobbyNo").html(c);
			var lobbyNo=GetURLParameter("lobbyNo");
	    	$("#lobbyNo").val(lobbyNo);
		});
        $('.confirm_btn').click(function(event) {
	        //取现金额
	        var amount = $("#amount").val();
	        if(amount==""|| amount<5 || !amount.match(/^[-\+]?\d+(\.\d+)?$/)){
	            msgBox("请输入正确的取现金额",function(){});
	            return;
	        }
	        ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/amountOrder/saveAOrder'},function(data){
				if (!data.succ) {
					$("#df").text("您有一笔申请正在处理中，请勿重复申请！");
				}
				$('.layer').fadeIn(100);        
				$('.layer-bg').fadeTo(100,0)  // 半透明
				$("#main-wrapper").hide();
				setTimeout("wx.closeWindow()", 3000);
			});
        });
     });  
    </script>
</head>
<body>
<div id="main-wrapper">
<img src="bank/sg/img/newban.png" alt="" class="newban" />
    <div class="main_content">
     <!-- 表单    -->
     <form action="">
        <div class="form_text">
         <!-- 每一个表单 -->
            <div class="text-fill">
                <!-- 姓名 -->
                <div class="item">
                	<input type="hidden" name="openid" value="<%=openid %>" sg_json="*"/>
                     <span class="left_name">姓名</span>
                     <input  type="text" placeholder="姓名"  name="orderName"  class="ipt" id="orderName" sg_json="*" sg_check="empty|请输入姓名"/>
                </div>
                <!-- 电话 -->
                <div class="item">
                     <span class="left_name">电话</span>
                     <input  type="text" placeholder="电话"  class="ipt" name="phone"  id="phone" sg_json="*" maxlength="11" sg_check="phone|请输入正确的联系电话"/>
                </div>
                <!-- 身份证号 -->
                <div class="item">
                     <span class="left_name">身份证号</span>
                     <input  type="text" placeholder="身份证号"  class="ipt" name="idCard" id="idCard" sg_json="*" maxlength="18" sg_check="idno|请输入正确的身份证号码"/>
                </div>
                 
                 <!-- 取现金额 -->
                 <div class="item">
                     <span class="left_name">取现金额</span>
                     <input  type="text" placeholder="取现金额不低于5万"  class="ipt"  name="amount" id="amount"  sg_json="*"  />万元
                </div>
                 <!-- 预期时间 -->
                 <div class="item">
                     <span class="left_name">预期时间</span>
                     <div class="sel_div">
                        <input placeholder="请选择时间" class="" readonly="readonly" name="orderDate" id="orderDate" type="text" sg_json="*" sg_check="empty|请输入预期时间">
                     </div>
                 </div>
                <!-- 网点 -->
                 <div class="item">
                     <span class="left_name">网点</span>
                     <div class="sel_div">
                         <select name="lobbyNo" id="lobbyNo" sg_json="*">
                         <option value="网点一">网点</option>
                         </select>
                     </div>
                 </div>
                
                 <div class="confirm_btn ">预约</div>
         </div>  
        </div>
     </form>
    </div>
</div>
  <!-- 模态窗口 -->
        <div class="layer">
            <p id="df">申请成功，请等待核查</p>
        </div>
        <div class="layer-bg"></div>   
</body>
</html>
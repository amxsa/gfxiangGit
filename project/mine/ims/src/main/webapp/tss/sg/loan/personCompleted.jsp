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
    <title>经理查看申请的信息</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/informationReade.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script src="bank/sg/js/common.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script type="text/javascript">
	function GetURLParameter(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
    $(function(){
		var managerId=GetURLParameter("managerId");
		$("#managerId").val(managerId);
		var d = {
			managerId:managerId
		};
            //获取数据
		sg_getList("${pageContext.request.contextPath}/front/applyLoan/getLoanApply", d,function(data){
			var cust=data["cust"];
			var bus=data["bus"];
			$("#custName").text(cust.custName);
			$("#typeName").text(data.typeName);
			$("#applyMoney").text(bus.applyMoney+"万元");
			$("#phone").text(cust.phone);
			$("#idType").text("身份证");
			$("#idCardNo").text(cust.idCardNo);
			$("#returnMsg").text(cust.returnMsg);
			$("#empName").text(cust.profession);
			$("#address").text(cust.address);
			$("#monthIncome").text(cust.monthIncome);
			$("#userPhoto").attr('src',data.userPhoto);
			$("#custli").text(data.custli);
			if(bus.docPass==1){
				$("#zlhc").text("已通过");
			}else{
				$("#zlhc").text("未通过");
			}
			if(bus.spotPass==1){
				$("#xchc").text("已通过");
			}else if(bus.spotPass==2){
				$("#xchc").text("不需要");
			}else{
				$("#xchc").text("未通过");
			}
		});
    })
    </script>
</head>
<body>
    <!-- 申请人信息 -->
    <div class="box">
        <div class="box_hd main_pd">
            申请人信息
        </div>
        <div class="box_bd main_pd2 ">
            <p>
                <span>申请人：</span>
                <em id="custName"></em>
            </p>
            <p>
            <p>
                <span>意　向：</span>
                <em id="typeName"></em>
            </p>
                <span>金　额：</span>
                <em id="applyMoney"></em>
            </p>
            <p>
                <span>电　话：</span>
                <em id="phone"></em>
            </p>
            <p>
                <span>证　件：</span>
                <em id="idType"></em>
            </p>
            <p class="bb">
                <span>　　　　</span>
                <em id="idCardNo"></em>
            </p> 
            <!-- 照片 -->
            <div class="pic_box">
                <img src="bank/sg/img/cilent.png" alt="" class="cilent" id="userPhoto"/>
                <p id="custli"></p>
            </div>
        </div> 
        <div class="box_foot main_pd2">
            <div>
                <span>职　业：</span>
                <em id="empName"></em>
            </div>
            <div>
                <span>房　产：</span>
                <em id="address"></em>
            </div>
            <div class="box_list">
                <span>月收入：</span>
                <em class="momey_num" id="monthIncome"></em>
            </div>
            <!-- 
            <div class="box_list">
                <span>是否已经同意《信贷征信协议》：</span>
                <em>同意</em>
            </div> --> 
              
        </div>
    </div>
   
  
    <!-- 信息补录 -->
    <div class="box">
        <div class="box_foot main_pd2">
            <div>
                <span>资料核查：</span>
                <em id="zlhc" style="color:#ff6601"></em>
            </div>
            <div>
                <span>现场核查：</span>
                <em id="xchc" style="color:#ff6601"></em>
            </div>
               
        </div>
       
        <div class="confirm_btn " style="background:#d7d7d7;color:#fff;">已完成核查</div> 
    </div>
     
</body>
</html>
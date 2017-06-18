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
    <title>经理查看客户信息</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/returnVisit.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="bank/sg/js/loadData.js"></script>
    <script type="text/javascript">
    function GetURLParameter(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
    function getItemHtml(row){
        var temp ='';  
        temp+='<li><em>'+row.visitTime+'</em>,';
        temp+='<i>'+row.userId.actualName+'</i>';
        temp+='<span>完成了'+row.visitType+'</span>';
        temp+='</li>';
        return temp;
   }
   function genItems(data) {
       var c = "";
       for ( var i = 0; i < data.length; i++) {  
           c += getItemHtml(data[i]);
       }
       $("#undo").html(c);
   }
    $(function(){
      //获取数据
        var custId=GetURLParameter("custId");
        var type=GetURLParameter("type");
        var openid=GetURLParameter("openid");
      	var d = {
      			custId:custId,
      			type:type,
      			openid:openid
         };
		sg_getList("${pageContext.request.contextPath}/front/cust/getCust", d,function(data){
			$("#custName").text(data.custName);
   			$("#birth").text(data.birth);
   			$("#phone").text(data.phone);
   			$("#idType").text(data.idType);
   			$("#idCardNo").text(data.idCardNo);
   			$("#dyck").text(data.dyck);
   			$("#dydk").text(data.dydk);
   			$("#clTime").text(data.clTime);
   			$("#dylc").text(data.dylc);
   			$("#userPhoto").attr('src',data.custImg);
   			$("#custli").text(data.custli);
		});
		var d2 = {
     			custId:custId
    		};
                //获取数据
    	sg_getList("${pageContext.request.contextPath}/front/revisit/revisitList", d2,function(data){
    			genItems(data.visitList);
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
        <div class="box_bd main_pd2">
            <p>
                <span>客    户：</span>
                <em id="custName"></em>
            </p>
            <p>
                <span>生    日：</span>
                <em id="birth"></em>
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
    </div>
    <!-- 账户信息 -->
    <div class="box">
        <div class="box_hd main_pd">
            账户信息
        </div>
        <div class="box_bd main_pd2">
            <p>
                <span>开户时间：</span>
                <em id="clTime"></em>
            </p>
            <p>
                <span>当月存款：</span>
                <em  id="dyck"></em>
            </p>
            <p>
                <span>当月贷款：</span>
                <em  id="dydk"></em>
            </p>
            <p>
                <span>当月理财：</span>
                <em  id="dylc"></em>
            </p>
        </div> 
    </div>
    <!-- 回访记录 -->
    <div class="box">
        <div class="box_hd main_pd">
            回访记录
        </div>
        <div class="box_bd main_pd2">
            <ul>
              
            </ul>    
        </div> 
    </div>
    
</body>
</html>
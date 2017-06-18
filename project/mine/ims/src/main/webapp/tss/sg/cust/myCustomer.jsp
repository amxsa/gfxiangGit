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
    <title>我的客户</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/my_customers.css" />
    <script src="bank/sg/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="bank/sg/js/mobiscroll.js" type="text/javascript"></script>
    <script src="bank/sg/js/common.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){                 
            loadList();   //加载列表 
            $('.soso_pho').click(function(event) {
            	loadList();
   			});
       });
       function getItemHtml(row){
            var temp ='';
            temp ='<div class="item pd undo_pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/cust/returnVisit.jsp?custId='+row.custId+'&type='+row.type+'&openid='+row.openid+'\'">';
            temp+='<div class="item_left">';
            if(row.custImg!=undefined){
            	 temp+='<img src="'+row.custImg+'" alt="" />';
            }else{
            	temp+='<img src="bank/sg/img/cilent.png" alt="" />';
            }
            temp+='</div>';
            temp+='<div class="item_right">';
            temp+='<p class="p1">'+row.name+'</p>';
            temp+='<p class="p2">电话：<em>'+row.phone+'</em></p></p>';
            temp+='</div></div>';
            return temp;
        }
        function genItems(data) {
            var c = "";
            for ( var i = 0; i < data.length; i++) {
                c += getItemHtml(data[i]);
            }
            $("#undo").html(c);
        }
        function loadList() {
            $(".undo_bd").remove();
            var so=$("#soso").val();
            var d = {
            	so:so
              };
            sg_getList("${pageContext.request.contextPath}/front/cust/custList", d,function(data){
				$("#custCount").text("("+data.custList.length+")");
				genItems(data.custList);
			});
        };
</script>
</head>
 <body>
    <!-- 列表 -->
    <div class="box">
    	 <div class="soso_box">
                <div class="soso_in">
                    <input type="text" class="soso_ipt" id="soso" placeholder="搜索"/>
                    <label for="" class="soso_pho"></label>    
                </div>
                    
            </div>
        <div class="box_hd main_pd">
            <span>我的客户</span>
            <i id="custCount"></i>
            
        </div>
        <div class="box_bd main_pd" id="undo">
            <!-- 左右结构的 图片 -->
        </div> 
    </div>
    </body>
</html>
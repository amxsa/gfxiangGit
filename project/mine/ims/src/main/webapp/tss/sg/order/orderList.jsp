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
    <title>申请审核列表</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/order_list.css" />
    <script src="bank/sg/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadObject.js" type="text/javascript"></script>
    <script src="bank/sg/js/getJson.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){     
            $(".top_tab li").click(function(event) {
                var index = $(this).index();
                    $(this).addClass('cur_c').siblings('li').removeClass('cur_c')
                    $(".box_bd").eq(index).addClass('show_box').siblings('.box_bd').removeClass('show_box');
                loadList($(this).attr("cc"));
            });
            loadList("undo");   //加载列表 
            
            function getItemHtml(row,dataid){
                var temp ='';
                if(dataid=="undo"){
                    temp ='<div class="item pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/order/myMsg.jsp?status=0&managerId='+row.managerId+'\'">';
                    temp+='<div class="item_left">';
                    temp+='<img src="'+row.userimg+'" alt="" />';
                    temp+='</div>';
                    temp+='<div class="item_right">';
                    temp+='<p class="p1"><i>'+row.orderName+'</i><em>大额取现</em></p>';
                    temp+='<p class="p2">电话：<em>'+row.phone+'</em></p>';
                    temp+='</div></div>';
                }else if(dataid=="done"){
                    temp ='<div class="item pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/order/myMsg.jsp?status=1&managerId='+row.managerId+'\'">';
                    temp+='<div class="item_left">';
                    temp+='<img src="'+row.userimg+'" alt="" />';
                    temp+='</div>';
                    temp+='<div class="item_right">';
                    temp+='<p class="p1"><i>'+row.orderName+'</i><em style="color:#b4b4b4;">大额取现</em></p>';
                    temp+='<p class="p2" >电话：<em>'+row.phone+'</em></p>';
                    temp+='</div></div>';
                }
                return temp;
            }
            function genItems(data,dataid) {
                var c = "";
                for ( var i = 0; i < data.length; i++) {
                    c += getItemHtml(data[i],dataid);
                }
                $("#"+dataid).html(c);
            }

            function loadList(dataid) {
                $(".undo_bd").remove();
                var d = {
                    };
				if(dataid=="undo"){
				    d.status=1;
					sg_getList("${pageContext.request.contextPath}/front/amountOrder/orderList", d,function(data){
						$("#blz").text("办理中("+data.blz+")");
						$("#ywc").text("已完成("+data.ywc+")");
						genItems(data.orderList,dataid);
					});
				}else if(dataid=="done"){
					d.status=2;
					sg_getList("${pageContext.request.contextPath}/front/amountOrder/orderList", d,function(data){
						genItems(data.orderList,dataid);
					});
				}	
            };
        })
    </script>
    </head>
    <body>
    <!-- 列表 -->
    <div class="box">
            <div class="main_pd" >
                <ul class="top_tab">
                    <li class="cur_c" cc="undo" id="blz"></li>
                    <li cc="done" id="ywc"></li>
                </ul>
            </div>
            <div class="box_bd main_pd show_box" id="undo">
                <!-- 左右结构的图片 -->
            </div> 
            <!-- 第二个 =============-->
             <div class="box_bd main_pd " id="done">
                <!-- 左右结构的图片 --> 
            </div> 
    </div>
    
    </body>
</html>
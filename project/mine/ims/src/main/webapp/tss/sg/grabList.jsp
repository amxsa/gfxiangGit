<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
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
    <title>抢单</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/grablist.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script src="bank/sg/js/common.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            loadList();   //加载列表 
        });
         // 点击抢单事件
        function fintBtn(obj,maid){
        	if(!confirm("确定抢此单吗？")){
    			return ;
    		}
        	$(obj).hide();
        	var d={
        			managerId:maid
        	};
	       	sg_getList("${pageContext.request.contextPath}/front/applyLoan/saveGrab", d,function(data){
	       		if(data.succ){
	       			$(obj).hide();
		            $(obj).siblings('.hide_box').show();
	       		}else{
	       			msgBox("抢单失败,请刷新重试",function(){
					});
	       		}
			});
        }

        function getItemHtml(row){
                var temp ='';
                temp ='<div class="item pd">';
                temp+='<div class="item_left">';
                temp+='<img src="'+row.userimg+'" alt="" />';
                if(row.custli==1){
                	temp+='<em class="vip">VIP</em>';
                }
                temp+='</div>';
                temp+='<div class="item_right">';
                temp+='<p class="first_p"><span>'+row.type+'</span><em>'+row.money+'</em>万元</p> ';
                temp+='<p class="last_p">联系电话：<em>'+row.phone+'</em></p>';
                temp+='<p class="last_p">申请时间：<em>'+row.time+'</em></p>';
                if(row.status==2){
                	temp+='<p class="hide_boxs">已被'+row.usernames+'抢单</p>';
                }else{
                	temp+='<div class="jion_btn" onclick="fintBtn(this,\''+row.managerId+'\');">立即抢单</div>';
                }
                temp+='<p class="hide_box">已被'+row.username+'抢单</p>';
                temp+='</div></div>';
                return temp;
            }
            function genItems(data) {
                var c = "";
                if(data.length==0){
                	c="暂时没有订单可抢哦";
                }else{
               	 	for ( var i = 0; i < data.length; i++) {
                        c += getItemHtml(data[i]);
                    }
                }
                $("#undo").html(c);
            }

            function loadList() {
                $(".undo_bd").remove();
                var d = {
                    };
                //获取列表
                sg_getList("${pageContext.request.contextPath}/front/manager/grabList", d,function(data){
                	genItems(data.grabList);
				});
            };
    </script>
    </head>
    <body>
    <!-- 列表 -->
    <div class="box" >
            <div class="box_bd main_pd" id="undo">
                <!-- 左右结构的图片 -->
            </div>
            
             
    </div>
    
    </body>
</html>
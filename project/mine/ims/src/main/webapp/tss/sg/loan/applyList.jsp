<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String basePath = SiteUtil.getSiteUrl();
%>

<!doctype html>
<head>
<base href="<%=basePath%>">
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
    <link rel="stylesheet" type="text/css" href="bank/sg/css/applyList.css" />
    <script src="bank/sg/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="bank/sg/js/mobiscroll.js" type="text/javascript"></script>
    <script src="bank/sg/js/common.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){   
        	//贷款核查列表页面（处理中）
             $(".top_tab li").click(function(event) {
                var index = $(this).index();
                $(this).addClass('cur_c').siblings('li').removeClass('cur_c')
                $(".box_bd").eq(index).addClass('show_box').siblings('.box_bd').removeClass('show_box');
				loadList($(this).attr("cc"));
            });
            loadList("undo");   //加载列表 
            // 数据开始
            function getItemHtml(row,dataid){
		      var temp ='';
                if(dataid=="undo"){
                	if(row.status=="30"){
                		temp ='<div class="item pd undo_pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/sceneVisit.jsp?managerId='+row.managerId+'\'">';
					}else{
						if(row.clientType=="2"){
							temp ='<div class="item pd undo_pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/companyInformation.jsp?managerId='+row.managerId+'\'">';
						}else{
							temp ='<div class="item pd undo_pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/informationReade.jsp?managerId='+row.managerId+'\'">';
						}
					}
	                temp+='<div class="item_left">';
	                temp+='<img src="'+row.userimg+'" alt="" />';
	                temp+='</div>';
	                temp+='<div class="item_right">';
	                temp+='<p class="p1">'+row.name+'</p>';
	                temp+='<p class="p2">'+row.type+'<span class="hours">'+row.hours+'</span></p>';
	                temp+='</div></div>';
				}else if(dataid=="done"){
					if(row.clientType=="2"){
						temp ='<div class="item pd undo_pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/companyCompleted.jsp?managerId='+row.managerId+'\'">';
					}else{
						temp ='<div class="item pd undo_pd" onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/personCompleted.jsp?managerId='+row.managerId+'\'">';
					}
	                temp+='<div class="item_left">';
	                temp+='<img src="'+row.userimg+'" alt="" />';
	                temp+='</div>';
	                temp+='<div class="item_right">';
	                temp+='<p class="p1">'+row.name+'</p>';
	                temp+='<p class="p2">电话：'+row.phone+'</p>';
	                temp+='</div></div>';}
                return temp;
            }
            function genItems(rows,dataid) {
				var c = "";
                for ( var i = 0; i < rows.length; i++) {
                    c += getItemHtml(rows[i],dataid);
                }
                $("#"+dataid).html(c);
            }
            function loadList(dataid) {
                $(".undo_bd").remove();
                var d = {
                		eventType:"0"
                    };
				if(dataid=="undo"){
				    d.status=1;
					sg_getList("${pageContext.request.contextPath}/front/manager/eventList", d,function(data){
						$("#blz").text("办理中("+data.blz+")");
						$("#ywc").text("已完成("+data.ywc+")");
						genItems(data.eventList,dataid);
					});
				}else if(dataid=="done"){
					d.status=2;
					sg_getList("${pageContext.request.contextPath}/front/manager/neventList", d,function(data){
						genItems(data.neventList,dataid);
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
            <div class="box_bd main_pd show_box" id="undo" >
            </div> 
            <div class="box_bd main_pd" id="done">
            </div>
    </div>
    </body>
</html>
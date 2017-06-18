<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
String basePath = SiteUtil.getSiteUrl();
%>
<!doctype html>
<html>
<base href="<%=basePath%>">
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    <title>经理个人主页</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/manager-me.css" />
    <script src="bank/sg/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script src="bank/sg/js/wxjsutil.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="bank/sg/js/hiddenMenu.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //loadList();   //加载列表 
        	$(".undo_bd").remove();
            var d = {};
            //获取列表
            sg_getList("${pageContext.request.contextPath}/front/manager/index", d,function(data){
            	genItems(data.pengding);
            	$("#userName").text(data.userName);
            	$("#empNo").text(data.pName);
            	$("#noreadCount").text(data.noreadCount);
            	$("#dbCount").text("("+data.pendingCount+")");
            	$("#loanCount").text(data.loanCount);
            	$("#visitCount").text(data.visitCount);
            	$("#vipCount").text(data.vipCount);
				$("#custCount").text(data.custCount);
				$("#userPhoto").attr('src',data.userPhoto);
				if(data.grabCount>0){
					 $(".rob").addClass('robon');
			         setInterval("ab()", 500);  
				}
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
       });
       function ab() {
            //alert(1);
            $(".robon").animate({"bottom": "8px"}, 500).animate({"bottom": "18px"}, 500)
       }

       function getItemHtml(row){
            var temp ='';  
            if(row.logType==0){
        	    if(row.status=="30"){
        	    	temp+='<li onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/sceneVisit.jsp?managerId='+row.managerId+'\'">';
				}else{
					if(row.clientType=="2"){
						temp+='<li onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/companyInformation.jsp?managerId='+row.managerId+'\'">';
					}else{
						temp+='<li onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/loan/informationReade.jsp?managerId='+row.managerId+'\'">';
					}
				}
				temp+='<span class="title_p">审核</span>';
            }else if(row.logType==1){
            	temp+='<li onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/visit/returnMsg.jsp?managerId='+row.managerId+'&custId='+row.custId+'&type='+row.type+'\'">';
				temp+='<span class="title_p">回访</span>';
            }else if(row.logType==2){
            	temp+='<li onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/visit/returnMsg.jsp?managerId='+row.managerId+'&custId='+row.custId+'&type='+row.type+'\'">';
				temp+='<span class="title_p">生日</span>';
            }else if(row.logType==3){
                temp+='<li onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/order/myMsg.jsp?status=0&managerId='+row.managerId+'\'">';
				temp+='<span class="title_p">大额</span>';
            }else if(row.logType==4){
            	temp+='<li onclick="javascript:location.href=\'${pageContext.request.contextPath}/bank/sg/grabList.jsp\'">';
				temp+='<span class="title_p">待抢</span>';
            }
            if(row.lstatus==0){
            	temp+='<em class="dot"></em>';
            }
            temp+='<i>'+row.message+'</i>';
            temp+='<em class="time">'+row.date+' '+row.time+'</em>';
            temp+='<em class="right_fx"></em>';
            temp+='</li>';
            return temp;
       }

		function genItems(rows) {
			var c = "";
			for ( var i = 0; i < rows.length; i++) {  
				c += getItemHtml(rows[i]);
			}
			$("#undo").html(c);
		}
		function loadList() {
		};
        // 换图像
        function preview1(){
        	getWxPara();
            wxReady();
       		wx.chooseImage({
     		  count: 1, // 默认9
     		  sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
     		  sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
     		  success: function (res) {
     			var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
     			wx.uploadImage({
     			  localId: localIds.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
     			  isShowProgressTips: 1, // 默认为1，显示进度提示
     			  success: function (res) {
     			    var serverId = res.serverId; 
     			  	var d = {
     			  		serverId:serverId
     				};
     		            //获取数据
     				sg_getList("${pageContext.request.contextPath}/front/upload/uploadImg", d,function(data){
     					$("#userPhoto").attr('src',data.imgUrl);
     				});
     				}
     			});
     		  }
     		});
        }
    </script>
    </head>
    <body>
    <!-- 个人资料 -->
    <div class="box">
        <div class="main_content">
            <div class="item pd">
                <div class="item_left photo_box" style="margin-left:0;">
                <input type="hidden" id="openid" />
				<input type="hidden" id="appid"/>
				<input type="hidden" id="secret"  />
				<input type="hidden" id="weid"  />
				<input type="hidden" id="signature"/>
				<input type="hidden" id="timestamp"/>
				<input type="hidden" id="nonceStr"/>
                    <div class="photo_bg" id="bg">
                        <img src="bank/sg/img/pic.png" alt="" id="userPhoto" onclick="preview1()"/>
                    </div>
                    <!-- <input type="file" onchange="preview1(this)" class="put_btn" /> -->
                </div>
                <div class="item_right">
                    <p style="font-size:22px;padding-bottom: 3px;" id="userName"></p>
                    <p style="font-size:15px;padding-bottom: 3px;" >职位：<i class="position" id="empNo"></i></p>
                    <p style="font-size:15px;padding-bottom: 3px;" id="score"></p>
                </div>  
            </div> 
            <!-- 消息提示     -->
            <div class="msg" onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/loan/reviewList.jsp'">
                <em id="noreadCount"></em>条新消息
            </div>
			<div id="grab">
            <img src="bank/sg/img/rob.png" alt="" class="rob " onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/grabList.jsp'"/>
			</div>
        </div>
    </div>
    <!-- 待办事项 -->
    <div class="box">
            <div class="box_hd main_pd">
                <span class="wait_msg">待办事项</span>
                <i id="dbCount"></i>
                <em class="right_fx"></em>
                <span class="all" onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/loan/reviewList.jsp'">全部</span>
                
            </div>
            <div class="box_bd main_pd">
                <ul id="undo">
                    
                </ul>
            </div> 
    </div>
    <!-- 菜单 -->
    <div class="box">
        <ul class="menu">
            <li>
                <div class="menu_box">
                    <div class="icon_box" onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/loan/applyList.jsp'">
                        <div class="menu_pic"></div>
                        <p>贷款核查</p>
                    </div> 
                    <em class="num_box" id="loanCount"></em> 
                </div>
            </li>
             <li>
                <div class="menu_box">
                    <div class="icon_box" onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/visit/visitCustomers.jsp'">
                        <div class="menu_pic"></div>
                        <p>客户回访</p>
                    </div> 
                    <em class="num_box" id="visitCount"></em> 
                </div>
            </li>
             <li>
                <div class="menu_box">
                    <div class="icon_box" onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/cust/myCustomers.jsp'">
                        <div class="menu_pic"></div>
                        <p>我的客户</p>
                    </div> 
                    <em class="num_box" id="custCount"></em> 
                </div>
            </li>
            <li>
                <div class="menu_box">
                    <div class="icon_box" onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/order/orderList.jsp'">
                        <div class="menu_pic"></div>
                        <p>VIP预约</p>
                    </div> 
                    <em class="num_box" id="vipCount"></em> 
                </div>
            </li>
           <!-- <li>
                <div class="menu_box" >
                    <div class="icon_box" style="background:#ededed;border:0 none;line-height:42px;">
                        <p>预留添加</p>
                    </div> 
                </div>
            </li>-->
          
          
        </ul>
    </div>
    </body>
</html>
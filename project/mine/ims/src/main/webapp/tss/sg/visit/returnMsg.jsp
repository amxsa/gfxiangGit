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
    <title>经理编写回访信息</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/returnMsg.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script src="bank/sg/js/mobiscroll.js" type="text/javascript"></script>
    <link href="bank/sg/css/mobiscroll.css" rel="stylesheet" type="text/css">
     <script src="bank/sg/js/check.js" type="text/javascript"></script>
     <script src="bank/sg/js/getJson.js" type="text/javascript"></script>
     <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
     <script type="text/javascript" src="bank/sg/js/common.js"></script>
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
        // 时间插件
        $(function () {
            var currYear = (new Date()).getFullYear();  
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
                nowText: "今天",
                startYear: currYear - 10, //开始年份
                endYear: currYear + 10 //结束年份
            };
            $("#appDate").mobiscroll($.extend(opt['date'], opt['default']));
            var optDateTime = $.extend(opt['datetime'], opt['default']);
            var optTime = $.extend(opt['time'], opt['default']);
            $("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
            $("#appTime").mobiscroll(optTime).time(optTime);
            
            var managerId=GetURLParameter("managerId");
            var custId=GetURLParameter("custId");
            var type=GetURLParameter("type");
            $("#custId").val(custId);
            $("#logId").val(managerId);
            $("#type").val(type);
            var d = {
        			managerId:managerId
       		};
                   //获取数据
       		sg_getList("${pageContext.request.contextPath}/front/revisit/getRevisit", d,function(data){
       			
       			$("#custName").text(data.custName);
       			$("#typeName").text(data.typeName);
       			//$("#applyMoney").text(bus.applyMoney+"万元");
       			$("#phone").text(data.phone);
       			$("#idType").text(data.idType);
       			$("#idCardNo").text(data.idCardNo);
       			$("#userPhoto").attr('src',data.userPhoto);
       			$("#custli").text(data.custli);
       		});
            //获取回访记录
       	 	var d2 = {
     			custId:custId
    		};
                //获取数据
    		sg_getList("${pageContext.request.contextPath}/front/revisit/revisitList", d2,function(data){
    			genItems(data.visitList);
    		});
                
    		$('.confirm_btn').click(function(event) {
    			$("#visitTime").val($("#appDate").val());
    			ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/revisit/saveVisit'},function(data){
    				if (data.succ) {
    					location.href="${pageContext.request.contextPath}/bank/sg/visit/visitCustomers.jsp";
    				}else{
    					msgBox("更新回访记录失败，请重试",function(){
    					});
    				}
    			});
    		});
        });
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
                <span>申请人：</span>
                <em id="custName"></em>
            </p>
            <p>
                <span>意　向：</span>
                <em id="typeName"></em>
            </p>
            <p>
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
    </div>
    <!-- 回访 -->
    <div class="box">
        <div class="box_hd main_pd">
            回访
        </div>
        <div class="box_bd main_pd2">
        <!-- 回访方式 -->
            <div class="item">
            	<input name="custId" id="custId" sg_json="*" type="hidden">
            	<input name="logId" id="logId" sg_json="*" type="hidden">
            	<input name="type" id="type" sg_json="*" type="hidden">
                <span class="left_name">回访方式：</span>
                <div class="sel_div">
                    <select name="visitType" id="visitType" sg_json="*" sg_check="empty|请选择回访方式">
                    <option value="现场回访">现场回访</option>
                    <option value="电话回访">电话回访</option>
                    </select>
                </div>
             </div>
             <!-- 回访时间 -->
            <div class="item">
                <span class="left_name">回访时间：</span>
                <div class="sel_div">
                 <input name="visitTime" id="visitTime" sg_json="*" sg_check="empty|请填写回访时间" type="hidden">
                 <input value="请选择时间" class="chose_time" readonly="readonly" name="appDate" id="appDate"  type="text">
                </div>
             </div>
             <!-- 回访备注 -->
            <div class="item">
                <span class="left_name">回访备注：</span>
                <textarea name="visitReason" id="visitReason" sg_json="*"  cols="30" rows="10" class="area_ipt"></textarea>
            </div>
            <!-- 核查人 -->
            <div class="item">
                <span class="left_name">　协办人：</span>
                <input  type="text"  class="ipt"  name="veriman" id="veriman" sg_json="*"/>
            </div>
        </div> 
    </div>
    <!-- 回访记录 -->
    <div class="box">
        <div class="box_hd main_pd">
            回访记录
        </div>
        <div class="box_bd main_pd2">
            <ul id="undo">
            </ul>
             
        </div> 
        <div class="confirm_btn">更新回访信息</div>   
    </div>
    
</body>
</html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>中国电信个人通信助理</title>
        <style type="text/css">
        *{margin: 0;padding: 0;}
        ol,ul{list-style: none;}
        body{font-family: "微软雅黑";}
        .model{width: 361px;height: 500px;border: 1px solid #000;margin: 100px auto;}
        .model_hd{width: 100%;height: 50px;line-height: 50px;text-align: center;background: #ea8d47;}
        .model_bd .left,.model_bd .right{float: left;width: 180px;height: 40px;line-height: 40px;text-align: center;background: #a9b67e;border-top: 1px solid #000;border-bottom: 1px solid #000;}
        .model_bd .left{border-right: 1px solid #000;}
        .left_bd,.right_bd{text-align: center;}
        ul{width: 180px;height: 409px;overflow: hidden;}
        .left_bd ul{width: 180px;height: 409px;overflow: hidden;border-right: 1px solid #000;}
        ul li{width: 128px;height: 30px;border: 1px solid #000;line-height: 32px;margin: 10px auto 0;cursor:pointer}

        </style>
        
        
<script type='text/javascript' src='<%=path%>/js/checkUtil/checkForm.js'></script>
<script type='text/javascript' src='<%=path%>/js/jquery-1.9.1.min.js'></script>
<script type='text/javascript' src='<%=path%>/js/jquery.form.js'></script>
<script type="text/javascript">
	<%-- $(document).ready(function(){
		var server="${server}";
		if (server!='Y') {
			$("#model").hide();
		}else{
			$("#info").text("您正在使用彩印服务");
			$("#server").empty();
		}
	});
	
	function openServer(){
		window.location="<%=path%>/colorprinting/use/setServer.do";
	} --%>
</script>
    </head>
    <body>
        <div class="model" id="model">
            <div class="model_hd">
              	  通讯助理彩印业务功能模块
            </div>
            <div class="model_bd">
                <div class="left">
                    <div class="left_hd">
                       	 彩印设置管理
                    </div>
                    <div class="left_bd">
                        <ul>
                            <li><a href="#">修改彩印</a></li>
                            <li><a href="<%=path%>/colorprinting/use/queryMyColorPrinting.do">设置个性化彩印</a></li>
                            <li><a href="#">我的原创彩印</a></li>
                            <li><a href="#">彩印收藏</a></li>
                            <li><a href="#">管理黑白名单</a></li>
                            <li><a href="#">拒接彩印设置</a></li>
                            <li><a href="#">专属彩印</a></li>
                            <li><a href="<%=path%>/colorprinting/part/queryPartColorPrinting.do">分时段彩印</a></li>
                            <li><a href="#">彩印设置分享</a></li>
                        </ul>
                    </div>
                </div>
                <div class="right">
                    <div class="right_hd">
                      	  彩印地带
                    </div>
                    <div class="right_bd">
                        <ul>
                            <li><a href="#">主题彩印</a></li>
                            <li><a href="#">彩印盒</a></li>
                            <li><a href="#">情景彩印</a></li>
                            <li><a href="#<%-- <%=path%>/manager/sysCP.do?method=showSysCPList --%>">系统彩印</a></li>
                       </ul>
                    </div>
                </div>
            </div>
            
        </div>
    </body>
</html>
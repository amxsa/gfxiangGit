<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/pages/common.jspf" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String ppp = request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>websocket</title>
<script>

    function keepalive(ws) {
        var device = new Object();
//        device.deviceIds = $('#msgText').val();
        device.optType = 'hb';
        var t = JSON.stringify(device);
        ws.send(t);
    }

    var websocket;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://<%=ppp%>/testSocket");
    } else if ('MozWebSocket' in window) {
    	alert("MozWebSocket")
        websocket = new MozWebSocket("ws://${path}/IBMS/point/webSocketServer.do");
    } else {
    	alert("SockJS")
        websocket = new SockJS("http://${path}/IBMS/sockjs/webSocketServer.do");
    }
    websocket.onopen = function (evnt) {
    	alert(evnt)
       <%--  var old = $("#msgcount").html();
        $("#msgcount").html(old + "</br><font color='green'>" + "onopen 方法" + "</font>");

        var old = $("#msgcount").html();
        $("#msgcount").html(old + "</br>ws URL：<font color='green'>" + "ws://<%=ppp%>/app/indexConfig/indexConfigWebSocket.do" + "</font>");
        setInterval(function () {
            keepalive(websocket)
        }, 3000); --%>
    };
    websocket.onmessage=function(evnt){
    	
    };
    websocket.onerror = function (e) {
       
    };
    websocket.onclose = function (evnt) {
      
    }

    function send() {
        var device = new Object();
        //device = {"data":[{"statisticsId":"设备1","statisticsTypeId":"1","statisticsData":"dt1"}, {"statisticsId":"报警1","statisticsTypeId":"2","statisticsData":"dt1"}, {"statisticsId":"点位1","statisticsTypeId":"3","statisticsData":"po9884"}, {"statisticsId":"属性1","statisticsTypeId":"4","statisticsData":"st32,sv91"}], "optType":""};
        var t = "aaaaaaa";
        websocket.send(t);
        /* if (true)return;
        var param = new Array();
        var point = new Object();
        point.pointId = '1';
        var point2 = new Object();
        point2.pointId = '2';
        point2.newValue = '789';
        var json = JSON.stringify(point);
        var json2 = JSON.stringify(point2);
        param[0] = point;
        param[1] = point2;
        var t = JSON.stringify(param);
        t = eval(t);
        var arrParam = JSON.stringify(t);
        websocket.send(arrParam); */
    }
    function pause() {

       /*  var device = new Object();
        device.deviceIds = $('#msgText').val();
        device.optType = 'pausePush';
        var t = JSON.stringify(device);

        var old = $("#msgcount").html();
        $("#msgcount").html(old + "</br>请求报文：<font color='blue'>" + t + "</font>");
 */
        websocket.send(t);
    }
</script>
</head>
<body>
		<input type="button" onclick="send()" value="链接"/>
</body>
</html>
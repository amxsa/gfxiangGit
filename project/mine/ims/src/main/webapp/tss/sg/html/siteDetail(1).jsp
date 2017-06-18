<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String openid = request.getParameter("openid");
	String fansId = request.getParameter("fansId");
	String weid = request.getParameter("weid");
	String workType = request.getParameter("workType");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="description" content="3GMS" />
<title>网点详情</title>
<link href="bank/site/css/base.css" rel="stylesheet" type="text/css"/>
<link href="bank/site/css/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="bank/site/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="bank/site/js/function.js"></script>
<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>

<script type="text/javascript">
var paramterMap = getRequestParamters();
var isBusinessOpen = true;

//得到参数
function getRequestParamters()
{ 
	var url=location.search; 
	var Request = new Object(); 
	if(url.indexOf("?")!=-1) 
	{ 
		var str = url.substr(1); //去掉?号 
		strs = str.split("&"); 
		for(var i=0;i<strs.length;i++) 
		{ 
			Request[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
		} 
	}
	return Request;
}



//得到网点详情
function getDetail() {
	var lobbyNo=paramterMap['lobbyid'];
	$("#vip").html(paramterMap['vipRows']);
	$("#comm").html(paramterMap['commRows']);
	$("#address").html(decodeURI(paramterMap['bankdizhi']));
	$("#phone").html(paramterMap['phone']);
	$("#callhphone").attr("href","tel:"+paramterMap['phone']);
	$("#bussinsess").html(decodeURI(paramterMap['bussinsess'])+'<br>'+decodeURI(paramterMap['holidayBusiness']));
	var islobbyopen = paramterMap['reservationStatus'];
	if(paramterMap['isNightBank']=="1"){
		$("#isNightBank").html("是");	
	}else{
		$("#isNightBank").html("否");
	}
	
	if(paramterMap['isSatWork']=="1"){
		$("#isSatWork").html("是");	
	}else{
		$("#isSatWork").html("否");
	}
	
	if(paramterMap['isSatWork']=="1"){
		$("#isSatWork").html("是");	
	}else{
		$("#isSatWork").html("否");
	}
	
	getOrderType(lobbyNo);
	
	if(islobbyopen == "1"){
	   if(isBusinessOpen == "1"){
	        $(".form-btn").html('<button onclick="toOrder()" type="button" class="theme-btn">预约</button>');	   
	   }else{	       
	        $(".form-btn").html('<button  type="button" class="theme-btn">该网点没有可预约业务</button>');
	   }
	   
	}else {
	   $(".form-btn").html('<button  type="button" class="theme-btn">该网点未开放预约</button>');
	}
	
	/*
	$.ajax({
		url : "app/lobby/queueDetail/"+lobbyNo,
		type : "post",
		dataType : "text",
		timeout : 10000,
		async : false,
		success : function(data) {
			
			data = eval("(" + data + ")");
			
			var business=data.nameList;
			
			var busstring="";
			for(var i=0;i<business.length;i++){
				if(i!=0){
					busstring+=",";
				}
				busstring+=business[i];
				
			}
			$("#orderType").html(busstring);
		}
	});
	*/

}

//是否有可预约的业务
function existOrderType(lobbyNo){

}
//获取预约的业务
function getOrderType(lobbyNo){
	var d = {};
 	var lobbyId = $("#lobbyId").val();
 	d.lobbyId=lobbyNo;
	$.ajax({
		url : "<%=path%>/orderType/list",
		type : "post",
		dataType : "text",
		timeout : 10000,
		data : d,
		async : false,
		success : function(data) {
			data = eval("(" + data + ")");
			var rows = data.rows;
			if(rows.length == 0   ){
			    isBusinessOpen = false;
			    $("#orderType").html("无");
			}else {
				var busstring = "";
				for(var i=0;i<rows.length;i++){
					if(i!=0){
						busstring+=",";
					}
					busstring+=rows[i].name;
					
				}
			    $("#orderType").html(busstring);
			}
			
		}
	});
}



//跳转到地图
function toMap(){
	var bx = paramterMap['bx'];
	var by = paramterMap['by'];
	var bname = decodeURI(paramterMap['bname']);
	var bankdizhi = decodeURI(paramterMap['bankdizhi']);
	var mpx = paramterMap['mpx'];
	var mpy = paramterMap['mpy'];
	
	
	if(mpx==''&&mpy==''){
		if(confirm("此操作需要获取您的个人坐标，是否继续？")){
		var map = new BMap.Map("allmap");
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
		    if(this.getStatus() == BMAP_STATUS_SUCCESS){
				var ypx=r.point.lng , ypy = r.point.lat;
				mpx=ypy;
				mpy=ypx;
				alert("坐标:  "+mpx+"   "+mpy);
				location.href='bank/site/lobby/map.jsp?bx='+bx+'&by='+by+'&mpx='+mpx+'&mpy='+mpy+'&bname='+encodeURI(encodeURI(bname))+'&bankdizhi='+encodeURI(encodeURI(bankdizhi));
				
				
		    }
		    else {
		    	pagehit('获取坐标失败,请确认是否已经开启GPRS,并重新获取!');
		        return false;
		    }        
		},{enableHighAccuracy: true});
		pagehit('获取坐标中,请稍等');
		return false;
	}else{
		return false;	
	}
		
	}else{
		location.href='bank/site/lobby/map.jsp?bx='+bx+'&by='+by+'&mpx='+mpx+'&mpy='+mpy+'&bname='+encodeURI(encodeURI(bname))+'&bankdizhi='+encodeURI(encodeURI(bankdizhi));
		
	}
}

//跳转到我要预约
function toOrder(){
	location.href="ibankorder/toSiteReserve?lobbyId="+paramterMap['lobbyid']+"&openid="+$("#openid").val()+"&fansId="+"<%=fansId%>"+"&weid="+"<%=weid%>"+"&workType="+"<%=workType%>";
}

$(document).ready(function(){
	getDetail();
	//getOrderType();
});
</script>
</head>
<body>
<div id="main-wrapper">
	<input type="hidden" id="lobbyId" value=""/>
	<input type="hidden" id="openid" value="<%=openid%>"/>
	<header>
    	<div class="top-nav">
        	<a class="top-left" href="javascript:history.go(-1);"></a>
            <p class="top-tit">网点详情</p>
            <a class="top-right"></a>
        </div>
    </header>
    <div id="main-content">
    	<p class="p-tit-b">
        	<span id="lname" class="pt-name"></span>
<!--             <span class="pt-gxsj">更新时间：<span class="sj">2014-3-18</span></span> -->
        </p>
    	
        <div class="info-box">
        	<div class="ib-item">
            	<span class="ibi-name">地址</span>
                <div class="ibi-right">
                	<p id="address" class="con"></p>
                    <a class="local" href="javascript:void(0)" onclick="toMap()"></a>
                </div>
            </div>
            <div class="ib-item">
            	<span class="ibi-name">电话</span>
                <div class="ibi-right">
                	<p id="phone"  class="con" ></p>
                    <a class="phone" id="callhphone"  href=""></a>
                </div>
            </div>
            <!-- <div class="ib-item">
            	<span class="ibi-name">存取币种</span>
                <div class="ibi-right">
                	<p class="con">人民币，港元，美元</p>
                </div>
            </div> -->
            <div class="ib-item">
            	<span class="ibi-name">营业时间</span>
                <div class="ibi-right">
                	<p id="bussinsess" class="con"></p>
                </div>
            </div>
            <div class="ib-item">
            	<span class="ibi-name">是否夜间银行</span>
                <div class="ibi-right">
                	<p id="isNightBank" class="con"></p>
                </div>
            </div>
            <div class="ib-item">
            	<span class="ibi-name">周六是否营业</span>
                <div class="ibi-right">
                	<p id="isSatWork" class="con"></p>
                </div>
            </div>
            <div class="ib-item">
            	<span class="ibi-name">周日是否营业</span>
                <div class="ibi-right">
                	<p id="isSunWork" class="con"></p>
                </div>
            </div>
            
            <div class="ib-item">
            	<span class="ibi-name">排队信息</span>
                <div class="ibi-right">
                	<div class="r-wrapper">
                    	<span class="r-item">
                            <span class="cla">金卡</span><span class="rs" id="vip"></span>人
                        </span>
                        <span class="r-item">
                            <span class="cla">普卡</span><span class="rs" id="comm"></span>人
                        </span>	
                    </div>
                		
                </div>
            </div>
            
            <div class="ib-item">
            	<span class="ibi-name">可预约业务</span>
                <div class="ibi-right">
                	<div class="r-wrapper" id="orderType">
                    	
                    </div>
                		
                </div>
            </div>
            
        </div>
        <div class="form-btn">
            
        </div>

    </div>
</div>
<div id="page-hit" style="display:none" >
	<div class="page-hit-bg"></div>
	<div class="page-hit">
		<div class="hit-con">
			<div class="hit-main">
				<p></p>
				<a style="display:none"></a>
			</div>
		</div>
	</div>
</div>
</body>
</html>
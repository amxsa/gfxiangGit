function doubleValue(price) {
	return (Math.round(price * 10000) / 10000).toFixed(2);
}
function Opentip(tip) {
	layer.open({
		closeBtn : '0',
		title : '',
		skin : 'demo-class',
		content : tip,
	});
}
// 获取URL参数
function GetURLParameter(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var orderNo = "";
var consumePrice = 0.0;// 消费金额
var commitPrice = 0.0;// 应付金额
var couponPrice = 0.0;// 优惠金额
var token = "";
var appid = "";
var prepayid = "";// 预支付id
var partnerid = "";
var package = "";
var noncestr = "";
var timestamp = "";
var sign = "";// 签名
var payType = 1;
var successUrl = "";
var failUrl = "";
var cancleCallBackUrl = "";// 取消支付回调url

function getOrder() {
	var data={};
	data.body={};
	data.header={
			token:"token",
			time_stamp:1111
	};
	data=JSON.stringify(data);
	$.ajax({
		type : "get",
		url : host + "/ims/servlet/saveOrder?str=" + data,
		async : false,
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		success : function(odata) {
			orderNo=odata.orderNo;
			$("#orderNo").html(orderNo);
		}
	});
}

$(document).ready(
		function() {
			setRefreshOnResume();
			var host = sessionStorage.getItem("host");
			// 时间创建
			var myDate = new Date();
			var Ptime = myDate.toLocaleDateString();
			var CHour = myDate.getHours();
			var Cminutes = myDate.getMinutes();
			if (Cminutes < 10)
				Cminutes = 0 + Cminutes.toString();
			document.getElementById("time").innerHTML = "创建时间：" + Ptime + " "
					+ CHour + ":" + Cminutes;

			clearHistory();
			setTitle("提交订单");
			$("#originalPrice").html("¥&nbsp;" + 0.01);
			$("#commitPrice").html("¥&nbsp;" + 0.01);
			$("#couponPrice").html("¥&nbsp;" + 0.00);
			$("#payPrice").html("¥&nbsp;" + 0.01);
			$(".type").click(function() {
				$(".type").removeClass("background-img");
				$(this).addClass("background-img");
				payType = $(this).attr("id");
				if ($("#getPay").hasClass("bk")) {
					$("#getPay").removeClass("bk");
				}
			});

		});
function getPay() {
	var successPage = "successfulappointment.htm";
	successUrl = host + "/ims/html5/common/pay/" + successPage + "?orderNo="
			+ orderNo + "&clientType=" + clientType;// 成功页面URL
	failUrl = host + "/mms/html5/common/pay/failappointment.htm?orderNo="
			+ orderNo + "&clientType=" + clientType;// 失败页面URL
	showProgressDialog();
	if (payType == 1) {// 微信支付
		var pay_method = "2";
		var appid = "wxe4219b2fbba0dd40";// 联享家
		// var appid="wxce429e955a2ac0c6";//绿岛明珠
		var channel_flag = "3";
		var time_stamp = Date.parse(new Date());
		var data = "{\"body\": {\"pay_method\": \"" + pay_method
				+ "\",\"orderNo\": \"" + orderNo + "\",\"appid\": \"" + appid
				+ "\",\"channel_flag\": \"" + channel_flag
				+ "\"},\"header\": {\"token\": \"" + token
				+ "\",\"time_stamp\": \"" + time_stamp + "\"}}";
		alert(host)
		$.ajax({
			type : "get",
			url : host + "/ims/servlet/getWxPrepayidRequest?str=" + data,
			async : false,
			dataType : "jsonp",
			jsonp : "jsoncallback",
			jsonpCallback : "success_jsonpCallback",
			success : function(odata) {
				if (odata.result == 0) {
					$("#getPay").addClass("bk");
					sign = odata.obj.sign;
					appid = odata.obj.appid;
					partnerid = odata.obj.partnerid;
					package = odata.obj.package;
					noncestr = odata.obj.noncestr;
					prepayid = odata.obj.prepayid;
					time_stamp = odata.obj.timestamp;
					var payData = "{\"appid\": \"" + appid
							+ "\",\"partnerid\": \"" + partnerid
							+ "\",\"package\": \"" + package
							+ "\",\"noncestr\": \"" + noncestr
							+ "\",\"timestamp\": \"" + time_stamp
							+ "\",\"prepayid\": \"" + prepayid
							+ "\",\"sign\": \"" + sign + "\"}";
					alert(JSON.stringify(payData))
					var i = pay(1, payData, host
							+ "/ims/servlet/getWxPayOrderStatus?orderNo="
							+ orderNo + "&pay_method=" + pay_method
							+ "&channel_flag=" + channel_flag + "&appid="
							+ appid, successUrl, failUrl, cancleCallBackUrl);
					if (i != 0) {
						$("#getPay").attr("onclick", "getPay()");
						if ($("#getPay").hasClass("bk")) {
							$("#getPay").removeClass("bk");
						}
					}
					// 调用接口调起微信支付
					setTimeout(function() {
						$("#getPay").attr("onclick", "getPay()");
						if ($("#getPay").hasClass("bk")) {
							$("#getPay").removeClass("bk");
						}
					}, 10000);
				} else {
					$("#getPay").attr("onclick", "getPay()");
					appAlert("提示", odata.reason);
				}
			}
		});
	} else if (payType == 2) {
		// 判断是否安装支付宝客户端
		var pay_method = "1";
		// var appid="2016090701865682";//绿岛明珠
		var appid = "2016050401363591";// 联享家
		var channel_flag = "3";
		var time_stamp = Date.parse(new Date());
		var data = "{\"body\": {\"pay_method\": \"" + pay_method
				+ "\",\"orderNo\": \"" + orderNo + "\",\"appid\": \"" + appid
				+ "\",\"channel_flag\": \"" + channel_flag
				+ "\"},\"header\": {\"token\": \"" + token
				+ "\",\"time_stamp\": \"" + time_stamp + "\"}}";
		$.ajax({
			type : "get",
			url : host + "/ims/servlet/getAlipayAppPrepayOrderRequest?str="
					+ data,
			async : false,
			dataType : "jsonp",
			jsonp : "jsoncallback",
			jsonpCallback : "success_jsonpCallback",
			success : function(odata) {
				if (odata.result == 0) {
					// $("#getPay").css("background-color","#CCCCCC");
					$("#getPay").addClass("bk");
					var payData = odata.obj.payInfo;
					// 调用接口调起支付宝
					pay(2, payData, host
							+ "/ims/servlet/getAliPayOrderStatus?orderNo="
							+ orderNo + "&pay_method=" + pay_method
							+ "&channel_flag=" + channel_flag + "&appid="
							+ appid, successUrl, failUrl, cancleCallBackUrl);
				} else {
					setTimeout(function() {
						$("#getPay").attr("onclick", "getPay()");
						if ($("#getPay").hasClass("bk")) {
							$("#getPay").removeClass("bk");
						}
					}, 10000);
					appAlert("提示", odata.reason);
				}
			},
			complete : function(odata) {
				$("#getPay").attr("onclick", "getPay()");
			}
		});
	}
}

function wxReady(){
	wx.ready(function(){
	});
}

function getWxPara(){
	var url = location.href.split('#')[0];
	var d = {
		url:url
	};
	$.ajax({
		url : 'front/manager/wxJsUtil',
		data : d, 
		type : "post",
		dataType : "text",
		cache:false,
		async:false,
		timeout : 20000,
		success : function(data) {
			data = eval("(" + data + ")");
			if(data){
				$("#timestamp").val(data.timestamp);
				$("#nonceStr").val(data.nonceStr);
				$("#appid").val(data.appId);
				$("#signature").val(data.signature);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("获取数据异常.share.param");
		}
	}); 
	wx.config({
		debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId: $("#appid").val(), // 必填，公众号的唯一标识
		timestamp:$("#timestamp").val() , // 必填，生成签名的时间戳
		nonceStr: $("#nonceStr").val(), // 必填，生成签名的随机串
		signature: $("#signature").val(),// 必填，签名，见附录1
		jsApiList: ["chooseImage","uploadImage"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
};

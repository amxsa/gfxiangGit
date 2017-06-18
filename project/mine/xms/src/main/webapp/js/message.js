$(document).ready(function(){
	if($("#returnMsg").val() == 'true') {
		var cur = Math.random();
		$.ajax({
			method:'post',
			url:'/mms/countSfjh.html?ss='+cur,
			success:function(msg){
				var totalSfjh = eval("("+msg+")").totalSfjh;
				if(totalSfjh != "0") {
					$.messager.lays(300, 100);
					$.messager.show('随访计划','<ul><li><a href="javascript:gotoPage()"><span class="icon_lightOn">' + 
						'您最近一个月内计划需要的随访有：<font id="counterA" color=red>' + totalSfjh + '</font> 条</span></a></li><div class="clear"></div></ul>', 
						'stay');
				}
				window.setInterval("openMessager()", 30000);//半分钟刷新一次
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			       
			} 
		});
	}
});

function openMessager(){
	var cur = Math.random();
	$.ajax({
		method:'post',
		url:'/mms/countSfjh.html?ss='+cur,
		success:function(msg){
			var totalSfjh = eval("("+msg+")").totalSfjh;
			if(totalSfjh != "0") {
				if($("#counterA").length == 0) {
					$.messager.lays(300, 100);
					$.messager.show('随访计划','<ul><li><a href="javascript:gotoPage()"><span class="icon_lightOn">' + 
						'您最近一个月内计划需要的随访有：<font id="counterA" color=red>' + totalSfjh + '</font> 条</span></a></li><div class="clear"></div></ul>', 
						'stay');
				} else {
					$("#counterA").text(totalSfjh);
				}
			} else {
				$.messager.mesclose();
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		       
		} 
	});
}
	
function gotoPage() {
	var diag = new top.Dialog();
	diag.Title = "近一个月随访计划";
	diag.URL = "/mms/searchSfjhPageByYs.html";
	diag.Width = 1020;
	diag.Height= 500;
	diag.show();
	showProgressBar();
}
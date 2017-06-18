/**
 * 
 */
function getCookiesByName(name){
	//获取cookie字符串 
	var strCookie=document.cookie; 
	//将多cookie切割为多个名/值对 
	var arrCookie=strCookie.split("; "); 
	var cook; 
	//遍历cookie数组，处理每个cookie对 
	for(var i=0;i<arrCookie.length;i++){ 
	         var arr=arrCookie[i].split("="); 
	         //找到名称为userId的cookie，并返回它的值 
	         if(name==arr[0]){ 
	                cook=arr[1]; 
	                break; 
	         } 
	} 
	return cook;
}

function formSet(){
	if($("#switch").val()==0){
		$("#dm,#hy,#mental").attr("disabled","disabled");
		//$("#hy").attr("disabled","disabled");
		//$("#mental").attr("disabled","disabled");
	}else{
		$("#dm,#hy,#mental").removeAttr("disabled");
	}
}

function setAlarm(){
	var date=new Date(); 
	var expireDays=30; 
	date.setTime(date.getTime()+expireDays*24*3600*1000); 
	if($("#switch").val()==1){
		document.cookie = "isAlarm=1; expires="+date.toGMTString();
		document.cookie = "dmAlarm="+$("#dm").val()+"; expires="+date.toGMTString();
		document.cookie = "hyAlarm="+$("#hy").val()+"; expires="+date.toGMTString();
		document.cookie = "mentalAlarm="+$("#mental").val()+"; expires="+date.toGMTString();
	}else if($("#switch").val()==0){
		document.cookie = "isAlarm=0; expires="+date.toGMTString();
	}
	top.Dialog.close();
}
$(function(){
	//formSet();
	var isAlarm = getCookiesByName("isAlarm");
	if(isAlarm!=null&&isAlarm==1){
		//alert($("#switch option[value=1]").attr("selected"));
		$("#switch option[value=1]").attr("selected",true);
		selRefresh("switch");

		if(getCookiesByName("dmAlarm")!=null){
			$("#dm").val(getCookiesByName("dmAlarm"));
		}
		if(getCookiesByName("hyAlarm")!=null){
			$("#hy").val(getCookiesByName("hyAlarm"));
		}
		if(getCookiesByName("mentalAlarm")!=null){
			$("#mental").val(getCookiesByName("mentalAlarm"));
		}
	}else{
		formSet();
	}
});
/**
 * 
 */
 var serviceMap = null;//地图对象
 var stationArray = new Array();//查询返回的station marker数组
 var alarmArray = new Array();//告警marker数组
 var dingshi = null;
 var windowsArray = new Array();//信息窗口数组
 var infowindow = new google.maps.InfoWindow();
 //自动缩放
 function setAutoZoom(markers,map){
	if(markers!=null&&markers.length>1){
		var i=markers.length,bounds = new google.maps.LatLngBounds();
	while(i--){
		bounds.extend(markers[i].getPosition());
	}
	map.fitBounds(bounds);
	map.setCenter(bounds.getCenter());
	map.panToBounds(bounds);
	}else{
		map.panTo(markers[0].getPosition());
	}
}	
//初始化地图
 function mapinit(lat,lon,zoom,mapid){
 	var latlng = new google.maps.LatLng(lat,lon);
 	var myOptions = {
 	  panControl:false,
      zoom: zoom,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
 	var winHeight;
 	var winWidth;
 	 if (document.documentElement  && document.documentElement.clientHeight && document.documentElement.clientWidth)  
 	   {  
 	   winHeight = document.documentElement.clientHeight;  
 	   winWidth = document.documentElement.clientWidth; 
 	   }
 	 
   $("#"+mapid).css("height",winHeight-85);
   return new google.maps.Map(document.getElementById(mapid), myOptions);
     
}

function addStation(){
	top.Dialog.confirm("请在地图右击服务站的位置",function(){
		if(serviceMap!=null){
			google.maps.event.addListener(serviceMap, 'rightclick', addMarker);
		}
	
	});
	}

function addMarker(event){
	google.maps.event.clearInstanceListeners(serviceMap);
	var diag = new top.Dialog();
	diag.Title = "添加社区服务站";
	diag.URL = "/mms/addStation.html?lonlat="+event.latLng.toUrlValue();
	diag.Width = 459;
	diag.Height=170;
	//diag.MessageTitle="表单标题";
	//diag.Message="这里写一些填表说明";
	//diag.CancelEvent = function(){
	//	diag.innerFrame.contentWindow.location.reload();
	//	diag.close();
	//	};
	diag.show();


}

function searchStationInfo(){
	var checkVal = '';
	 $('input[name="options"]:checked').each(function(){  
		 checkVal += $(this).val(); 
	});  
	//alert(checkVal);
	var selectVal=$("#disease").val();
	var stationName = $("#stationName").val();
	 $.ajax({
	    	url:"/mms/initStationMap.html",
	    	data:{checkVal:checkVal,disease:selectVal,stationName:stationName},
	    	dataType:"json",
	    	type:"post",
	    	success:function(data){
	    		if(data.length<1){
	    			top.Dialog.alert("服务站不存在");
	    			return;
	    		}
	    		for(var i=0;i<data.length;i++){
	    			//alert(data.length);
	    			if(i==0){
	    				if(stationArray!=null&&stationArray.length>0){
		    				for(var j = 0;j<stationArray.length;j++){
		    					stationArray[j].setMap(null);
		    				}
		    				stationArray = new Array();
		    			}
	    			}
	    			
	    				var myLatLng = new google.maps.LatLng(data[i].lat,data[i].lon);
	    	    		var stationMarker = new google.maps.Marker({
	    	     		 position: myLatLng,
	    	     		 map: serviceMap,
	    	     		icon: 'images/station32.png'
	    	  			});
	    	    		setInfoWindow(serviceMap,stationMarker,data[i]);
	    	    		stationMarker.setMap(serviceMap);
	    	    		stationArray.push(stationMarker);
	    			
	    		}
	    		setAutoZoom(stationArray,serviceMap);
	    		
	    	}
	   });
}

function setInfoWindow(map,marker,data){
 	google.maps.event.addListener(marker, 'click', function() {
  	var contentString = '<div>名称：'+data.name+'</div>';
  		if($("#contact").attr("checked")){
  			if(data.contact!=null&&data.contact!=""){
  				contentString +='<div>负责人：'+data.contact+'</div>';
  			}else{
  				contentString +='<div>负责人：</div>';
  			}
  			
  		}
  		if($("#tel").attr("checked")){
  			if(data.tel!=null&&data.tel!=""){
  				contentString +='<div>联系方式：'+data.tel+'</div>';
  			}else{
  				contentString +='<div>联系方式：</div>';
  			}
  			
  		}
  		if($("#document").attr("checked")){
  			if(data.document!=null&&data.document!=""){
  				contentString +='<div>总建档数：'+data.document+'</div>';
  			}else{
  				contentString +='<div>总建档数：0</div>';
  			}
  			
  		}
  		if($("#bed").attr("checked")){
  			if(data.bed!=null&&data.bed!=""){
  				contentString +='<div>总建床数：'+data.bed+'</div>';
  			}else{
  				contentString +='<div>总建床数：0</div>';
  			}
  			
  		}
  		if($("#outpatient").attr("checked")){
  			if(data.dayOutpatient!=null&&data.dayOutpatient!=""){
  				contentString +='<div>今天/月门诊总人数：'+data.dayOutpatient+"/"+data.monthOutpatient+'</div>';
  			}else{
  				contentString +='<div>今天/月门诊总人数：0</div>';
  			}
  			
  		}
  		if($("#guarder").attr("checked")){
  			if(data.guarder!=null&&data.guarder!=""){
  				contentString +='<div>无线监护病人数：'+data.guarder+'</div>';
  			}else{
  				contentString +='<div>无线监护病人数：0</div>';
  			}
  			
  		}
  		var selectVal=$("#disease").val();
  		if(selectVal==1){
  			if(data.hypertension!=null&&data.hypertension!=''){
  				contentString +='<div>高血压人数：'+data.hypertension+'</div>';
  			}else{
  				contentString +='<div>高血压人数：0</div>';
  			}
  			if(data.dm!=null&&data.dm!=''){
  				contentString +='<div>糖尿病人数：'+data.dm+'</div>';
  			}else{
  				contentString +='<div>糖尿病人数：0</div>';
  			}
  			if(data.mental!=null&&data.mental!=''){
  				contentString +='<div>精神病人数：'+data.mental+'</div>';
  			}else{
  				contentString +='<div>精神病人数：0</div>';
  			}
  			
  		}else if(selectVal==2){
  			if(data.hypertension!=null&&data.hypertension!=''){
  				contentString +='<div>高血压人数：'+data.hypertension+'</div>';
  			}else{
  				contentString +='<div>高血压人数：0</div>';
  			}
  		}else if(selectVal==3){
  			if(data.dm!=null&&data.dm!=''){
  				contentString +='<div>糖尿病人数：'+data.dm+'</div>';
  			}else{
  				contentString +='<div>糖尿病人数：0</div>';
  			}
  		}else if(selectVal==4){
  			if(data.mental!=null&&data.mental!=''){
  				contentString +='<div>精神病人数：'+data.mental+'</div>';
  			}else{
  				contentString +='<div>精神病人数：0</div>';
  			}
  		}
  			
	infowindow.setContent(contentString);
	infowindow.setPosition(marker.getPosition());
	infowindow.open(map,marker);
	//windowsArray.push(infowindow);
	});
 }

function setCookies(event){
	var oid = ($(event.target).attr("id"));
	//alert(oid);
	//获取当前时间 
	var date=new Date(); 
	var expireDays=10; 
	//将date设置为10天以后的时间 
	date.setTime(date.getTime()+expireDays*24*3600*1000); 
	var date2 = new Date();
	if(oid=="contact"){
		if(!$("#"+oid).attr('checked')){
			date.setTime(date.getTime()-20*24*3600*1000);
		}
		//alert(date.toGMTString());
		document.cookie = "contact=1; expires="+date.toGMTString();
	}
	else if(oid == "tel"){
		if(!$("#"+oid).attr('checked')){
			date.setTime(date.getTime()-20*24*3600*1000);
		}
		document.cookie ="tel=1;expires="+date.toGMTString();
		
	}else if(oid =="document"){
		if(!$("#"+oid).attr('checked')){
			date.setTime(date.getTime()-20*24*3600*1000);
		}
		document.cookie ="document=1;expires="+date.toGMTString();
	}else if(oid =="bed"){
		if(!$("#"+oid).attr('checked')){
			date.setTime(date.getTime()-20*24*3600*1000);
		}
		document.cookie="bed=1;expires="+date.toGMTString();
	}else if(oid =="outpatient"){
		if(!$("#"+oid).attr('checked')){
			date.setTime(date.getTime()-20*24*3600*1000);
		}
		document.cookie="outpatient=1;expires="+date.toGMTString();
	}else if(oid=="guarder"){
		if(!$("#"+oid).attr('checked')){
			date.setTime(date.getTime()-20*24*3600*1000);
		}
		document.cookie="guarder=1;expires="+date.toGMTString();
	}
	
}

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

function alarm(){
	$.ajax({
		url:'/mms/mapAlarm.html',
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.alarm){
				var myLatLng = new google.maps.LatLng(data.lat,data.lon);
	    		var stationMarker = new google.maps.Marker({
	     		 position: myLatLng,
	     		 map: serviceMap,
	     		icon: 'images/gif022.gif'
	  			});
	    		var infowindow = null;
	    		google.maps.event.addListener(stationMarker, 'click', function() {
	    		  	var contentString = '<div style="colour:red">'+data.content+'</div>';
	    		  			
	    			 infowindow = new google.maps.InfoWindow({
	    		    	content: contentString
	    			});
	    			infowindow.open(serviceMap,stationMarker);
	    		});
	    		stationMarker.setMap(serviceMap);
	    		serviceMap.panTo(myLatLng);
	    		//infowindow.open(serviceMap);
	    		alarmArray.push(stationMarker);
	    		$.messager.lays(300, 200);
	    		$.messager.show('服务站告警',data.content,10000,'sounds/ALARM1.WAV');

	    		//$("#alarmSound").append( '<embed src="sounds/ALARM1.WAV" loop="3" autostart="true" hidden="true"></embed>');
	    		//setTimeout(function(){$("#alarmSound").empty();},5000);
	    		
			}else{
				if(alarmArray!=null&&alarmArray.length>0){
					for(var i = 0;i<alarmArray.length;i++){
						alarmArray[i].setMap(null);
					}
					 searchStationInfo();
				}
			}
		}
	});
}

function alarmSetting(){
	var diag = new top.Dialog();
	diag.Title = "告警设置";
	diag.URL = "/mms/alarmSet.html";
	diag.Width = 460;
	diag.Height=205;
	//diag.MessageTitle="表单标题";
	//diag.Message="这里写一些填表说明";
	//diag.CancelEvent = function(){
	//	diag.innerFrame.contentWindow.location.reload();
	//	diag.close();
	//	};
	diag.show();

}

 $(function(){
		$(".categoryitems a[target*=frmright]").click(function(){
			showProgressBar();
		});
		$(":checkbox").bind("click",setCookies);
		//$("#disease").bind("change",setCookies);
		top.Dialog.close();
		//$("#mapbox").css('height','500px');
	    if(getCookiesByName('contact')==1){
	    	$("#contact").attr("checked",true);
	    }
	    if(getCookiesByName('tel')==1){
	    	$("#tel").attr("checked",true);
	    }
	    if(getCookiesByName('document')==1){
	    	$("#document").attr("checked",true);
	    }
	    if(getCookiesByName('bed')==1){
	    	$("#bed").attr("checked",true);
	    }
	    if(getCookiesByName('outpatient')==1){
	    	$("#outpatient").attr("checked",true);
	    }
	    if(getCookiesByName('guarder')==1){
	    	$("#guarder").attr("checked",true);
	    }
	    serviceMap = mapinit(22.997,113.344,12,"mapbox");
//	    $(window).resize(function(){
//	    	var winHeight;
//	     	var winWidth;
//	     	 if (document.documentElement  && document.documentElement.clientHeight && document.documentElement.clientWidth)  
//	     	   {  
//	     	   winHeight = document.documentElement.clientHeight;  
//	     	   winWidth = document.documentElement.clientWidth; 
//	     	   }
//	     	 
//	       $("#mapbox").css("height",winHeight-85);
//	    }); 
	    searchStationInfo();
	    if(getCookiesByName("isAlarm")==1){
	    	 dingshi = window.setInterval(alarm,60000);
	    }
	   
});
 

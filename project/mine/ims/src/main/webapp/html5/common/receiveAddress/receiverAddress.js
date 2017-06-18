
$(document).ready(function(){
	setTitle("选择地址");
	var host=sessionStorage.getItem("host");
	var token=sessionStorage.getItem("token");
	var sellerId=sessionStorage.getItem("sellerId");
	if (sellerId==undefined||sellerId==null) {
		sellerId="";
	}
	var time_stamp= Date.parse(new Date());
	var householdId= sessionStorage.getItem("householdId");
	var data="{" +
				"\"body\":{\"sellerId\":\""+sellerId+"\"}," +
				 "\"header\":{\"token\":\""+token+"\",\"time_stamp\":\""+time_stamp+"\"}" +
				 "}";
	$.ajax({
		type:"get",
		url:host+"/mms/servlet/getOrderReceiverInfo?str="+data,
		dataType: "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback: "success_jsonpCallback",
		success:function(odata) {
					if(odata.result==0){
						var ilist=odata.list; 
				   		for(var i=0;i<ilist.length;i++){
							var item=ilist[i];
							if(item.onServer==1){//在服务范围
								var ihtml=	"<li class='address' id='"+item.householdId+"'>"+
														"<div class='address-left'><div class='address-first' ><div class='picture'><img  src='../images/ic_name@3x.png' /></div>"+
														"<div class='a-number'>"+
														"<span class='name' id='"+item.id+"'>"+item.userName+" </span><span class='phone' style:'text-decoration: none;'>"+item.mobile+"</span>"+
														"</div></div>"+
														"<div class='address-first'><div class='picture'><img src='../images/ic_-Location-@3x.png' /></div>"+
													   "<div class='b-address'>"+
													  "<span>"+item.address+"</span></div></div></div></li>";
														
								$("#addresslist").append(ihtml);
								if(householdId==item.householdId){
									$("#"+householdId).addClass("background-img");	
								}
							}
						}
				   		$(".address").click(function() {
							$(".address").removeClass("background-img");
							$(this).addClass("background-img");
							receiverInfoId=$(this).find(".name").attr("id");
							sessionStorage.setItem("receiverInfoId",receiverInfoId);
							sessionStorage.setItem("householdId",$(this).attr("id"));
							sessionStorage.setItem("status","1");
							popUrl();
						});
						for(var j=0;j<ilist.length;j++){
						var item=ilist[j];
						if(item.onServer==0){
							var uhtml=	"<li class='disable-address' id='"+item.isDef+"'>"+
												"<div class='address-left'><div class='address-first' ><div class='picture'><img  src='../images/ic_name@3x.png' /></div>"+
												"<div class='a-number'>"+
												"<span class='name' id='"+item.id+"'>"+item.userName+" </span><span class='phone'>"+item.mobile+"</span>"+
												"</div></div>"+
												"<div class='address-first'><div class='picture'><img src='../images/ic_-Location-@3x.png' /></div>"+
											   "<div class='b-address'>"+
											  "<span>"+item.address+"</span></div></div></div></li>";
									$("#addresslist").append(uhtml);
							}
						}
					}
					
					}
		
				})
});
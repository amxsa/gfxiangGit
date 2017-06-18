function isCondition(param) {
	if(param != null && param != "" && param != undefined) {
		return true;
	}
	return false;
}

function GetURLParameter(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
}
var condition = "0"; //默认根据店铺获取收货地址信息 
var host = sessionStorage.getItem("host");
var token = sessionStorage.getItem("token");
var time_stamp = Date.parse(new Date());
var organizationSeq = GetURLParameter("orga"); //机构编号
var sellerId = GetURLParameter("sellerId"); //店铺id
var receiverInfoId = sessionStorage.getItem("receiverInfoId");
var defReceiverInfoId = ""; //默认地址id
var currentHouseholdId = sessionStorage.getItem("currentHouseholdId"); //当前住房id(householdId)
if(!isCondition(sellerId)) {
	sellerId = "";
}
setRefreshOnResume();
setTitle("选择地址");
if(isCondition(organizationSeq)) {
	condition = "1";
}
var isDefStatus = localStorage.getItem("isDefStatus");
if(isDefStatus == "1") {
	sessionStorage.setItem("receiverInfoId", "");
	localStorage.removeItem("isDefStatus");
}

function getData(condition) {
	var data = "";
	if(condition == "0") {
		data = "{" +
			"\"body\":{\"sellerId\":\"" + sellerId + "\",\"condition\":\"" + condition + "\",\"type\":\"" + 2 + "\"}," +
			"\"header\":{\"token\":\"" + token + "\",\"time_stamp\":\"" + time_stamp + "\"}" +
			"}";
	} else {
		data = "{" +
			"\"body\":{\"organizationSeq\":\"" + organizationSeq + "\",\"condition\":\"" + condition + "\",\"type\":\"" + 2 + "\"}," +
			"\"header\":{\"token\":\"" + token + "\",\"time_stamp\":\"" + time_stamp + "\"}" +
			"}";
	}
	$.ajax({
		type: "get",
		url: host + "/mms/servlet/getUserReceiverInfo?str=" + data,
		dataType: "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback: "success_jsonpCallback",
		success: function(odata) {
			if(odata.result == 0) {
				var addresslist = odata.list;
				if(addresslist.length <= 0) {
					$('html').css("background-color", "#fff");
					$("#no_have_address").show();
					sessionStorage.setItem("receiverInfoId", "");
					return false;
				}
				var defOnserver = 0; //默认地址是否在服务范围
				for(var i = 0; i < addresslist.length; i++) { //默认地址
					var item = addresslist[i];
					if(item.isDef == 1) {
						var address = addresslist[i];
						defReceiverInfoId = item.id;
						sessionStorage.setItem("defReceiverInfoId", defReceiverInfoId);
						var yesaddress;
						if(item.onServer == 1) { //且在服务范围
							defOnserver = 1;
							yesaddress = _.template($("#address_yes_use").html());
						} else {
							yesaddress = _.template($("#address_no_use").html());
						}
						$("#addresslist").append(yesaddress(address));
					}
				}
				var currentHouseId;
				var k = 0;
				for(var i = 0; i < addresslist.length; i++) { //当前住房地址(在范围 不是默认 当前住房)
					var item = addresslist[i];
					if(item.onServer == 1 && item.isDef != 1 && item.householdId == currentHouseholdId) {
						var address = addresslist[i];
						var yesaddress = _.template($("#address_yes_use").html());
						$("#addresslist").append(yesaddress(address));
						k++;
						if(k == 1) {
							currentHouseId = item.id; //取第一个
						}
					}
				}
				var otherHouseId;
				for(var i = 0; i < addresslist.length; i++) { //非住房地址(在范围 不是默认 不是当前住房)
					var item = addresslist[i];
					var c = 0;
					if(item.onServer == 1 && item.isDef != 1 && item.householdId != currentHouseholdId) {
						var address = addresslist[i];
						var yesaddress = _.template($("#address_yes_use").html());
						$("#addresslist").append(yesaddress(address));
						c++;
						if(c == 1) {
							otherHouseId = item.id; //取第一个
						}
					}

				}
				for(var i = 0; i < addresslist.length; i++) {
					var item = addresslist[i];
					if(item.onServer == 0 && item.isDef != 1) {
						var address2 = addresslist[i];
						var disaddress = _.template($("#address_no_use").html());
						$("#addresslist").append(disaddress(address2));
					}
				}
				/*if(addresslist.length == $(".disable-address").length) { //如果全都不在服务范围 选择地址置空
					sessionStorage.setItem("receiverInfoId", "");
				}*/
				var reId = sessionStorage.getItem("receiverInfoId");
				if(isCondition(reId)) { //有选中的地址
					$("#" + reId).addClass("background-img");
				} else {
					var id;
					if(isCondition(defReceiverInfoId) && defOnserver == 1) { //有默认地址且在范围
						id = defReceiverInfoId;
						$("#" + defReceiverInfoId).addClass("background-img");
					} else if(isCondition(currentHouseId)) { //选中在范围的当前住房地址
						id = currentHouseId;
						$("#" + currentHouseId).addClass("background-img");
					} else if(isCondition(otherHouseId)) {
						id = otherHouseId;
						$("#" + otherHouseId).addClass("background-img");
					} else {
						id = "";
					}
					sessionStorage.setItem("receiverInfoId", id);
				}
				$(".address_default img[value=1]").hide();
				$(".address_default img[value=1]").next().text("默认").addClass("address_default_red");
				$(".address_default img[value=0]").next().parent().hide();

				$(".address_isInarea .address_first_line").click(function() {
					$("li").removeClass("background-img");
					$(this).parent().addClass("background-img");
					//选择地址动作
					var id = $(this).parent().attr("id");
					sessionStorage.setItem("receiverInfoId", id);
					popUrl();
				});
			}
		}
	})
}
getData(condition);
//新增地址
function addInfo() {
	showActivity(host + "/mms/html5/common/receiverInfo/edit_receiveraddress.htm", "新增地址");
}
//----选择操作默认地址
function changeDefault(ele) {
	var receiverInfoId = $(ele).attr("name"); //选中默认地址的值
	if(!isCondition(receiverInfoId)) {
		return false;
	}
	//请求修改默认地址
	var data = "{" +
		"\"body\":{\"receiverInfoId\":\"" + receiverInfoId + "\",\"operateType\":\"" + 0 + "\"}," +
		"\"header\":{\"token\":\"" + token + "\",\"time_stamp\":\"" + time_stamp + "\"}" +
		"}";
	$.ajax({
		type: "get",
		url: host + "/mms/servlet/setUserReceiverInfo?str=" + data,
		dataType: "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback: "success_jsonpCallback",
		success: function(odata) {
			//console.log(JSON.stringify(odata));
			$(".address_default img").attr("src", 'ic_default@3x.png');
			$(".address_default span").text("设为默认").removeClass("address_default_red");
			$(".address_default img[name=" + receiverInfoId + "]").attr("src", 'ic_choise_red@3x.png');
			$(".address_default img[name=" + receiverInfoId + "]").next().text("默认地址");
			$(".address_default img[name=" + receiverInfoId + "]").next().addClass("address_default_red");
		}
	})
}

//----选择编辑地址操作
function editAddress(name) {
	//跳转编辑页面
	addressid = $(name).attr("name"); //带地址的ID过来
	var length = $("li").length; //地址数量  最后一条不可删除
	showActivity(host + "/mms/html5/common/receiverInfo/edit_receiveraddress.htm?receiverInfoId=" + addressid + "&length=" + length, "编辑地址");
}
//----执行删除地址操作
function deleAddress(name) {
	addressid = $(name).attr("name");
	layer.confirm('你确定删除该地址吗？', {
		title: '',
		closeBtn: '0',
		skin: 'demo_two'
	}, function(index) {
		deleYAddress();
		layer.close(index);
	});
}

//----确定删除地址操作
function deleYAddress() {
	$("#tip_bg").hide();
	$("#tip").hide();
	var receiverInfoId = sessionStorage.getItem("receiverInfoId");
	operateType = 3;
	var str = "{" +
		"\"body\":{\"receiverInfoId\":\"" + addressid + "\",\"operateType\":\"" + operateType + "\"}," +
		"\"header\":{\"token\":\"" + token + "\",\"time_stamp\":\"" + time_stamp + "\"}" +
		"}";
	$.ajax({
		type: "get",
		url: host + "/mms/servlet/setUserReceiverInfo?str=" + str,
		async: false,
		dataType: "jsonp",
		jsonp: "jsoncallback",
		jsonpCallback: "success_jsonpCallback",
		success: function(odata) {
			$("li[name=" + addressid + "]").remove();
			var defId = sessionStorage.getItem("defReceiverInfoId"); //默认地址
			var id = "";
			if(addressid == receiverInfoId) {
				var c = 0;
				$("li").each(function(i, e) {
					if(!$(e).hasClass("disable-address")) {
						if(i == 0 || i == 3) {
							id = $(this).attr("name");
							return false;
						}
					}
				});
				$("li").removeClass("background-img");
				$("#" + id).addClass("background-img");
				sessionStorage.setItem("receiverInfoId", id);
			}
			var length = $("li").length;
			if(length <= 2) {
				$('html').css("background-color", "#fff");
				$("#no_have_address").show();
			}
		}
	})
}
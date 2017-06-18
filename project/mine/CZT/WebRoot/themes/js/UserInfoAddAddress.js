var http_request = false;
var myName;
function changeAddress(theName){
	var url = "../user/TUserAddressDpAct.do?method=";
	var idValue = getMyObject(theName).value;
	if(theName.substring(0,7)=="country"){
		url = url + "changeCountry&&countryId=" + idValue ;
	} else if (theName.substring(0,8)=="province") {
		url = url + "changeProvince&&provinceId=" + idValue ;	
	} else if (theName.substring(0,4)=="city") {
		url = url + "changeCity&&cityId=" + idValue ;	
	} else {
		alert('操作失败');
		return false;
	}
	myName = theName;
	makeRequest(url);
}
function makeRequest(url) {	
	if (window.XMLHttpRequest) {
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType){
			http_request.overrideMimeType('text/xml');
		} 
	} else if (window.ActiveXObject) {
		try{
			http_request = new ActiveXObject("Msxml2.XMLHTTP"); 
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	} 
	if (!http_request) {
		alert('请求失败!');
		return false;
	}
	
	http_request.onreadystatechange = init; 
	http_request.open('GET', url, true); 
	http_request.setRequestHeader("If-Modified-Since","0"); 
	http_request.send(null);
}

function init() { 
	if (http_request.readyState == 4) {
		if (http_request.status == 0 || http_request.status == 200) {
			var result = http_request.responseText;
			var obj = getMyObject(myName);
			if(myName.substring(0,7)=="country"){
				var results = result.split(";");
				var lastDot = myName.substring(7,8);
				string2Object("province"+lastDot, results[0]);
				string2Object("city"+lastDot, results[1]);
				string2Object("cityArea"+lastDot, results[2]);
			} else if (myName.substring(0,8)=="province") {
				var results = result.split(";");
				var lastDot = myName.substring(8,9);
				string2Object("city"+lastDot, results[0]);
				string2Object("cityArea"+lastDot, results[1]);
			} else if (myName.substring(0,4)=="city") {
				var lastDot = myName.substring(4,5);
				string2Object("cityArea"+lastDot, result);
			}
		} else {//http_request.status != 200
			alert("请求失败"+http_request.status);
		}
	}
}

function string2Object(objectName, result){
	var object = getMyObject(objectName);
	var n = object.options.length-1;
	while(n>=0){
		var obj = object.options[n];
		object.remove(obj); 
		n = n-1;
	}
	//将得到的result分配得到新的值
	var objects = result.split(",");
	for(var v=0;v<objects.length;v++){
		if(objects[v]!="") {
			var opt = document.createElement("OPTION");						
			var values = objects[v].split("|");
			opt.value = values[0];
			opt.text = values[1];
			object[v]=opt;
		}
	}
}
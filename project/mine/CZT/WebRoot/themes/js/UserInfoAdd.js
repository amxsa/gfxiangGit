var phoneNumber=1;
function addPhone(){
	var obj;
	if(phoneNumber<10){
		phoneNumber++;
		obj = getMyObjectById("phone"+phoneNumber);
		obj.style.display='';
	} else {
		alert('仅允许为用户添加10个电话号码');
	}
	getMyObject("phoneNumber").value=phoneNumber;
}

function deletePhone(){
	var obj;
	if(phoneNumber>1){
		obj = getMyObjectById("phone"+phoneNumber);
		obj.style.display='none';
		phoneNumber--;
	}
	getMyObject("phoneNumber").value=phoneNumber;
}

var addressNumber=1;
function addAddress(){
	var obj;
	if(addressNumber<3){
		addressNumber++;
		obj = getMyObjectById("address"+addressNumber);
		obj.style.display='';
	} else {
		alert('仅允许为用户添加三个地址');
	}
	getMyObject("addressNumber").value=addressNumber;
}

function deleteAddress(){
	var obj;
	if(addressNumber>1){
		obj = getMyObjectById("address"+addressNumber);
		obj.style.display='none';
		addressNumber--;
	}
	getMyObject("addressNumber").value=addressNumber;
}


var emailNumber=1;
function addEmail(){
	var obj;
	if(emailNumber<3){
		emailNumber++;
		obj = getMyObjectById("aEmail"+emailNumber);
		obj.style.display='';
	} else {
		alert('仅允许为用户添加三个Email');
	}
	getMyObject("emailNumber").value=emailNumber;
}

function deleteEmail(){
	var obj;
	if(emailNumber>1){
		obj = getMyObjectById("aEmail"+emailNumber);
		obj.style.display='none';
		emailNumber--;
	}
	getMyObject("emailNumber").value=emailNumber;
}

function initShow(){
	addressNumber = getMyObject("addressNumber").value;
	for(var i=1;i<=addressNumber;i++) {
		var obj = getMyObjectById("address"+i);
		obj.style.display='';
	}
	emailNumber = getMyObject("emailNumber").value;
	for(var i=1;i<=emailNumber;i++) {
		var obj = getMyObjectById("aEmail"+i);
		obj.style.display='';
	}
	phoneNumber = getMyObject("phoneNumber").value;
	for(var i=1;i<=phoneNumber;i++) {
		var obj = getMyObjectById("phone"+i);
		obj.style.display='';
	}
}



function forSubmit(){
	if(checkNumbers('creditLevel','信用度','bankAccountNumber','银行帐号')){
		if(counters('creditLevel','信用度',5,'accountName','门户账号',20,'accountPwd','门户密码',20,'questionAsk','门户问题提示',50,'questionAnwser','门户问题回答',50,'cnName','用户名',20,'certificateNum','证件号码',20,'bankName','开户行',20,'bankAccountNumber','银行帐号',20,'bankAccountName','开户名',20,'memo','备注',250)) {
			if(getMyObject('certificateType').value == "0" && getMyObject('certificateNum').value.length>0) {
				if(getMyObject('certificateNum').value.length != 15 && getMyObject('certificateNum').value.length != 18 ) {
					alert('身份证的号码必须是15位或者18位');
					getMyObject('certificateNum').focus();
					return false;
				}	
			}
			if(getMyObject('phoneNumber1').value.length<7 ) {
				getMyObject('phoneNumber1').focus();
				alert('至少需要填入一个有效的电话号码');
				return false
			}
			return checkNull('cnName','姓名','userType','用户类型','fromChannel','渠道','fromGroup','客户组');
		} else {
				return false;
		}
	} else {
		return false;
	}
}


function intoMakeAirOrder(){
	if(getMyObject("netid").value == ""){
		alert('操作失误，请重新登录');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('主叫号码不能为空');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../business/PNR.jsp?userNetid='+getMyObject("netid").value + "&sourcePhone0="+getMyObject("phoneNumber1").value;
	}
}
function intoMakeAirOrder2(){
	if(getMyObject("netid").value == ""){
		alert('操作失误，请重新登录');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('主叫号码不能为空');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../business/PNR2.jsp?userNetid='+getMyObject("netid").value + "&sourcePhone0="+getMyObject("phoneNumber1").value;
	}
}

function intoMakeHotelOrderDirect(){
	var vp = getMyObject("phoneNumber1");
	if(vp.value == ""){
		alert('主叫号码不能为空');
		vp.focus();
		return false;
	}
	window.location="../hotel/THotelDpAct.do?method=list&commend=1&sourcePhone="+vp.value;
}

function intoMakeFoodOrderDirect(){
	var customerPhone = getMyObject("phoneNumber1");
	if(customerPhone.value == ""){
		alert('主叫号码不能为空');
		customerPhone.focus();
		return false;
	}
	window.location="../food/shop/TFoodShopShowDpAct.do?method=initList&customerPhone="+customerPhone.value;
}


function intoMakeFoodOrder(){
	var customerPhone = getMyObject("phoneNumber1");
	var customerNetid = getMyObject("netid").value;
	var customerName = getMyObject("cnName").value;
	if(customerPhone.value == ""){
		alert('主叫号码不能为空');
		customerPhone.focus();
		return false;
	}
	if(customerNetid == ""){
		alert('操作失误，请重新登录');
		return false;
	}
	if(customerName == ""){
		alert('用户名不能为空');
		return false;
	}
	window.location="../food/shop/TFoodShopShowDpAct.do?method=initList&customerPhone="+customerPhone.value+"&customerNetid="+customerNetid+"&customerName="+customerName;
}

function intoMakeHotelOrder(){
	if(getMyObject("netid").value == ""){
		alert('操作失误，请重新登录');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('主叫号码不能为空');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../hotel/THotelDpAct.do?method=list&commend=1&userNetid='+getMyObject("netid").value + "&sourcePhone="+getMyObject("phoneNumber1").value + "&bookCustomerName="+getMyObject("cnName").value;
	}
}


function intoMakeAirReport(){
	if(getMyObject("netid").value == ""){
		alert('操作失误，请重新登录');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('主叫号码不能为空');
		getMyObject("phoneNumber1").focus();
	} else {
		var d = new Date();
	    var nameStr = "newWindow"+d.getMilliseconds();
	    var features = "width=10000,height=10000,left=0,,scrollbars=yes";
	    var strURL = '../business/TAirOrderformDpAct.do?method=initListHistoryForUser&userNetid='+getMyObject("netid").value + "&sourcePhone0="+getMyObject("phoneNumber1").value + "&cnName="+getMyObject("cnName").value;
	    window.open(strURL, nameStr, features);	
		
	}
}

function intoMakeHotelReport(){
	if(getMyObject("netid").value == ""){
		alert('操作失误，请重新登录');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('主叫号码不能为空');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../hotel/HotelOrderOrReport.jsp?userNetid='+getMyObject("netid").value + "&sourcePhone1="+getMyObject("phoneNumber1").value + "&cnName="+getMyObject("cnName").value;
	}
}
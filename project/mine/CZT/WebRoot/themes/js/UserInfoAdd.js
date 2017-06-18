var phoneNumber=1;
function addPhone(){
	var obj;
	if(phoneNumber<10){
		phoneNumber++;
		obj = getMyObjectById("phone"+phoneNumber);
		obj.style.display='';
	} else {
		alert('������Ϊ�û����10���绰����');
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
		alert('������Ϊ�û����������ַ');
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
		alert('������Ϊ�û��������Email');
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
	if(checkNumbers('creditLevel','���ö�','bankAccountNumber','�����ʺ�')){
		if(counters('creditLevel','���ö�',5,'accountName','�Ż��˺�',20,'accountPwd','�Ż�����',20,'questionAsk','�Ż�������ʾ',50,'questionAnwser','�Ż�����ش�',50,'cnName','�û���',20,'certificateNum','֤������',20,'bankName','������',20,'bankAccountNumber','�����ʺ�',20,'bankAccountName','������',20,'memo','��ע',250)) {
			if(getMyObject('certificateType').value == "0" && getMyObject('certificateNum').value.length>0) {
				if(getMyObject('certificateNum').value.length != 15 && getMyObject('certificateNum').value.length != 18 ) {
					alert('���֤�ĺ��������15λ����18λ');
					getMyObject('certificateNum').focus();
					return false;
				}	
			}
			if(getMyObject('phoneNumber1').value.length<7 ) {
				getMyObject('phoneNumber1').focus();
				alert('������Ҫ����һ����Ч�ĵ绰����');
				return false
			}
			return checkNull('cnName','����','userType','�û�����','fromChannel','����','fromGroup','�ͻ���');
		} else {
				return false;
		}
	} else {
		return false;
	}
}


function intoMakeAirOrder(){
	if(getMyObject("netid").value == ""){
		alert('����ʧ�������µ�¼');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('���к��벻��Ϊ��');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../business/PNR.jsp?userNetid='+getMyObject("netid").value + "&sourcePhone0="+getMyObject("phoneNumber1").value;
	}
}
function intoMakeAirOrder2(){
	if(getMyObject("netid").value == ""){
		alert('����ʧ�������µ�¼');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('���к��벻��Ϊ��');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../business/PNR2.jsp?userNetid='+getMyObject("netid").value + "&sourcePhone0="+getMyObject("phoneNumber1").value;
	}
}

function intoMakeHotelOrderDirect(){
	var vp = getMyObject("phoneNumber1");
	if(vp.value == ""){
		alert('���к��벻��Ϊ��');
		vp.focus();
		return false;
	}
	window.location="../hotel/THotelDpAct.do?method=list&commend=1&sourcePhone="+vp.value;
}

function intoMakeFoodOrderDirect(){
	var customerPhone = getMyObject("phoneNumber1");
	if(customerPhone.value == ""){
		alert('���к��벻��Ϊ��');
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
		alert('���к��벻��Ϊ��');
		customerPhone.focus();
		return false;
	}
	if(customerNetid == ""){
		alert('����ʧ�������µ�¼');
		return false;
	}
	if(customerName == ""){
		alert('�û�������Ϊ��');
		return false;
	}
	window.location="../food/shop/TFoodShopShowDpAct.do?method=initList&customerPhone="+customerPhone.value+"&customerNetid="+customerNetid+"&customerName="+customerName;
}

function intoMakeHotelOrder(){
	if(getMyObject("netid").value == ""){
		alert('����ʧ�������µ�¼');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('���к��벻��Ϊ��');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../hotel/THotelDpAct.do?method=list&commend=1&userNetid='+getMyObject("netid").value + "&sourcePhone="+getMyObject("phoneNumber1").value + "&bookCustomerName="+getMyObject("cnName").value;
	}
}


function intoMakeAirReport(){
	if(getMyObject("netid").value == ""){
		alert('����ʧ�������µ�¼');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('���к��벻��Ϊ��');
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
		alert('����ʧ�������µ�¼');
	} else if(getMyObject("phoneNumber1").value == ""){
		alert('���к��벻��Ϊ��');
		getMyObject("phoneNumber1").focus();
	} else {
		window.location='../hotel/HotelOrderOrReport.jsp?userNetid='+getMyObject("netid").value + "&sourcePhone1="+getMyObject("phoneNumber1").value + "&cnName="+getMyObject("cnName").value;
	}
}
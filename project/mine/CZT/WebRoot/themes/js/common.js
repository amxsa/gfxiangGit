//ȫ�ֱ�������ѡ��ȫ�����Ŀؼ�������
var selectAllCheckbox;

/*
�ı�����checkbox��״̬��thisһ��
*/
function fSelectAll(myName){
	var selectOne=document.getElementsByTagName("input");
	for(var i=0;i<selectOne.length;i++){
		if(selectOne[i].type=="checkbox" && selectOne[i].name != myName){
			selectOne[i].checked = document.getElementsByName(myName)[0].checked;
		}
	}	
	selectAllCheckbox = myName;
}

function fSelectAll2(myName, othersName){
	var selectOne=document.getElementsByName(othersName);
	for(var i=0;i<selectOne.length;i++){
		if(selectOne[i].type=="checkbox"){
			selectOne[i].checked = document.getElementsByName(myName)[0].checked;
		}
	}	
	selectAllCheckbox = myName;
}


/*
���ƴ˿ؼ�������������
*/
function checkNumber(obj){
	checkNumber2(obj);
	if(obj){
		obj.value = obj.value.replace(/\D/gi,'')
	} else {
		alert('���÷���checkNumber�������ֵ�ʱ����봫��һ������,����ʧ��');
		return false;
	}
}


/**
���һ��ֵ�Ƿ�Ϊ����
**/
function checkNumber2(obj) {
	var value = obj.value;
	var string = "1234567890.";
	for(var i=0;i<value.length;i++){
		var ch = value.charAt(i);
		if(string.indexOf(ch)<0) {
			obj.value = value.substring(0,i);
			alert('�벻Ҫ��������ֵ��ַ�');
			return false;
		}
	}
}

function isNumberValue(obj) {	
	return isNumber(obj.value);
}

function isNumber(value) {	
	var string = "1234567890";
	for(var i=0;i<value.length;i++){
		var ch = value.charAt(i);
		if(string.indexOf(ch)<0) {
			return false;
		}
	}
	return true;
}

function checkNumbers(){
	if(arguments.length % 2!=0){
		alert("������ʽ������Ҫ��");
	}
	for(var i=0;i<arguments.length;i+=2) {
		var obj = getMyObject(arguments[i]);
		if(obj.value!=""){
			if(!isNumberValue(obj)){
				alert(arguments[i+1] + '������������');
				obj.focus();
				return false;
			}
		}
	}
	return true;
}



/**
ɾ����¼ʱ���form�ύ��
�ж�ҳ���ϵ�checkbox�����Ƿ��б�ѡ�еģ����û�У������ύ������� ����Ҫ��ȷ�ϡ�
**/
function deleteSelected(actTo, myFormName, showText){
	if(!showText || showText == "") {
		showText = "ִ�в�������Ϣ���ı䣬ȷ����?";
	}
	var selectOne=document.getElementsByTagName("input");
	var b = false;
	for(var i=0;i<selectOne.length;i++){
		if(selectOne[i].type=="checkbox"){
			if(selectOne[i].checked && selectOne[i].name!=selectAllCheckbox){
				b = true;
				break;
			}
		}
	}	
	if(!b) {
		alert('��û��ѡ��ļ�¼!');
		return false;
	} else {
		var myForm;
		if(myFormName) {
			myForm = getMyForm(myFormName);
		} else {
			myForm = getMyForm();
		}
		if(!confirm(showText)) 
			return false;
		myForm.action = actTo;
		myForm.submit();
	}
}

function deleteARecord(idsObjName, actTo, myFormName){
	//һ���Ե�����¼��ʱ����hidden�ؼ�����id��ɾ��
	var b = false;
	var idsObj = getMyObject(idsObjName);
	if(idsObj && idsObj.value != "")
		b = true;
	if(!b) {
		alert('�������ܽ��У����Ժ�����');
		return false;
	} 
	if(!confirm('ִ�в�������Ϣ���ı䣬ȷ����')) 
		return false;
	var myForm;
	if(myFormName) {
		myForm = getMyForm(myFormName);
	} else {
		myForm = getMyForm();
	}
	if(actTo)
		myForm.action = actTo;
	myForm.submit();
}

/*
ִ��һ��form���ύ
actTo��ʾ�ύ��Ŀ��
myTarget��ʾ����Ŀ�꣬��'_self','_blank'��
myFormName��ʾҪ�ύ��form�����֣����ҳ����ֻ��һ��form����ô�ò�������ʡ��
*/
function intoAction(actTo, myTarget, myFormName){
	if(myFormName) {
		myForm = getMyForm(myFormName);
	} else {
		myForm = getMyForm();
	}
	if(myForm == false){
		return false;
	} else {
		if(myTarget)
			myForm.target = myTarget;
		if(actTo)
			myForm.action = actTo;
		myForm.submit();
	}
}

/*
��ż���Ĳ�����ֵ������ָ���Ķ������磺setValues('a',1)���ʾ��1����a����.
*/
function setValues(){
	if(arguments.length % 2!=0){
		alert("������ʽ������Ҫ��");
	}
	for(var i=0;i<arguments.length;i+=2) {
		myObj = getMyObject(arguments[i]);
		if(myObj == false) {
			return false;
		} else {
			myObj.value = arguments[i+1];
		}
	}
}


/*
��ָ����select�ؼ���ѡ�е����ֵ��������һ��ָ������
*/
function showOptionTextTo(theObj, myValue, toObj){
	var tmp = "";
	var myObject = getMyObject(theObj);
	if(myObject.selectedIndex>=0){
		var toObject = getMyObject(toObj);
		toObject.value = myObject[myObject.selectedIndex].text;
	}
}

//��ָ����select�ؼ���ѡ��һ��ѡ���ѡ���ֵ��������
function selectOption(theObj,myValue){
	if(myValue.length<1) {
		alert('Ҫ��select������ѡ��һ��ѡ��������ֵΪ��');
		return ;
	}
	var number = 0;
	var myObject = getMyObject(theObj);
	for(var i=0;i<myObject.length;i++){
		if(myObject[i].text == myValue) {
			myObject.selectedIndex = i;
			number++;
		}
	}
	if(number<1) {
		alert('������û���Ч������ѡ��');
		myObject.focus();
	} else if(number>1) {
		alert('������������û�����Ӧ�ļ�¼����һ��');
		myObject.focus();
	}
}

//������ʱ����ʾ��ָ���Ŀؼ�������
function showTime(myName) {
	var timeVal = getDate0();
	myObj = getMyObject(myName);
	if(myObj == false) {
		return false;
	} else {
		myObj.value = timeVal;
	}
}
//��ȡһ������ʱ��
function getTime(){
	var now = new Date();
	var year = now.getYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hours = now.getHours();
	var mins = now.getMinutes();
	var secs = now.getSeconds();
	if(month<10)
		month = "0" + month;
	if(date<10)
		date = "0" + date;
	if(hours<10)
		hours = "0" + hours;
	if(mins<10)
		mins = "0" + mins;
	if(secs<10)
		secs = "0" + secs;
	var timeVal = "";
	timeVal = year + "-" + month + "-" + date + " " + hours + ":" + mins + ":" +  secs;
	return timeVal;
}
//��ȡһ����������
function getDate0(){
	var now = new Date();
	var year = now.getYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	if(month<10)
		month = "0" + month;
	if(date<10)
		date = "0" + date;
	var timeVal = "";
	timeVal = year + "-" + month + "-" + date ;
	return timeVal;
}

/*
���ƿؼ������볤�ȣ�һ����������������
*/
function textCounter(obj, maxlimit) {
	var counter = 0;
	var i;
	var chckcode;
	chckcode=obj.value.length;
	chckcode=chckcode-1;
    for (i = 0; i< obj.value.length; i++)
    {
    	if (obj.value.charCodeAt(i) > 127 || obj.value.charCodeAt(i) == 94)
    	{
    		counter+=2;
    		if (counter>maxlimit) 
    			break;
    	}
    	else 
    	{
	        counter++;
	        if (counter>maxlimit) 
	        	break;
	    }
    }

	if (counter > maxlimit)
		obj.value = obj.value.substring(0,i);
}

function checkNull(){
	if(arguments.length % 2!=0){
		alert("������ʽ������Ҫ��");
	}
	for(var i=0;i<arguments.length;i+=2) {
		var obj = getMyObject(arguments[i]);
		if(obj.value == ""){
			alert(arguments[i + 1] + "����Ϊ��");
			obj.focus();
			return false;
		}
	}
}

function counter(obj) {
	var counter = 0;
	var chckcode = obj.value.length;
    for (var i = 0; i< obj.value.length; i++) {
    	if (obj.value.charCodeAt(i) > 127 || obj.value.charCodeAt(i) == 94) {
    		counter+=2;
    	} else {
	        counter++;
	    }
    }
	return counter;
}


function counters() {
	if(arguments.length % 3!=0){
		alert("������ʽ������Ҫ��");
	}
	for(var i=0;i<arguments.length;i+=3) {
		var obj = getMyObject(arguments[i]);
		if(obj) {
			var c = counter(obj);
			if(c>arguments[i + 2]) {
				alert("'"+arguments[i + 1] + "'�ĳ��Ȳ��ܳ���"+arguments[i + 2]+"���ַ���һ�����ļ������ַ�");
				obj.focus();
				return false;
			}
		}
	}
	return true;
}





/*
��ȡ��ҳ�ϵ�һ��obj
if(�����radion����){
	���ض�������б�ѡ�е��Ǹ�
} else if(����radio����){
	if(ҳ���Ͻ���һ������) {
		���ظö���
	} else if(ҳ�����ж������) {
		if(����ָ��ĳ����) {
			����ָ���Ķ���
		} else {
			��ʾ����������
		}
	}
}
*/
function getMyObject(myName){
	var objs = document.getElementsByName(myName);
	if(objs.length>1){
		var tmp = false;
		for(var v = 0;v < objs.length;v++){
			if(objs[v].type != "radio") {
				tmp = true;
				break;
			}
		}
		if(tmp == true) {
			alert('ҳ���ϴ��ڶ�Ӧ�Ŀؼ���������1������ȡ����ʧ��' + myName);
			return false;
		} else {
			for(var v = 0;v < objs.length;v++){
				if(objs[v].checked == true) {
					return objs[v];
				}
			}
		}
	} else if (objs.length<1) {
		//alert('ҳ���ϲ����ڶ�Ӧ�Ŀؼ�����ȡ����ʧ��' + myName);
		return false;
	}
	return objs[0];
}
function getMyObjectById(myId){
	var obj = document.getElementById(myId);
	if(obj){
		return obj;
	} else if (obj.length<1) {
		alert('ҳ���ϲ����ڶ�Ӧ�Ŀؼ�����ȡ����ʧ��' + myId);
		return false;
	}
}
//��ȡ��ҳ�ϵ�һ��form�������ҳ���ж��form����ô����ͨ������ָ��form������
function getMyForm(myFormName){
	var forms = document.getElementsByTagName("form");
	if(forms.length>1){
		if(!myFormName) {
			//alert('ҳ���ϴ��ڶ��form���޷���λ,��ָ��form��');
			//return false;
			return forms[0];
		} else {
			forms = document.getElementsByName(myFormName);
			if(forms.length > 1){
				alert("��ָ����form�ĸ�������1�����޷���λ");
				return false;
			} else if (forms.length<1) {
				alert("��ָ����form����Ч���޷���λ");
				return false;
			}
		}
	} else if (forms.length<1) {
		alert('ҳ���ϲ�����form���޷��ύ');
		return false;
	}
	return forms[0];
}


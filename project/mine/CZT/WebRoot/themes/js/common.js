//全局变量，表’选择全部‘的控件的名字
var selectAllCheckbox;

/*
改变其他checkbox的状态与this一样
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
控制此控件仅能输入数字
*/
function checkNumber(obj){
	checkNumber2(obj);
	if(obj){
		obj.value = obj.value.replace(/\D/gi,'')
	} else {
		alert('调用方法checkNumber限制数字的时候必须传入一个对象,操作失败');
		return false;
	}
}


/**
检测一个值是否为数字
**/
function checkNumber2(obj) {
	var value = obj.value;
	var string = "1234567890.";
	for(var i=0;i<value.length;i++){
		var ch = value.charAt(i);
		if(string.indexOf(ch)<0) {
			obj.value = value.substring(0,i);
			alert('请不要输入非数字的字符');
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
		alert("参数格式不符合要求");
	}
	for(var i=0;i<arguments.length;i+=2) {
		var obj = getMyObject(arguments[i]);
		if(obj.value!=""){
			if(!isNumberValue(obj)){
				alert(arguments[i+1] + '必须输入数字');
				obj.focus();
				return false;
			}
		}
	}
	return true;
}



/**
删除记录时候的form提交，
判断页面上的checkbox对象是否有被选中的，如果没有，则不予提交，如果有 ，则要其确认。
**/
function deleteSelected(actTo, myFormName, showText){
	if(!showText || showText == "") {
		showText = "执行操作后信息将改变，确定吗?";
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
		alert('您没有选择的记录!');
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
	//一个对单条记录的时候用hidden控件保存id的删除
	var b = false;
	var idsObj = getMyObject(idsObjName);
	if(idsObj && idsObj.value != "")
		b = true;
	if(!b) {
		alert('操作不能进行，请稍后再试');
		return false;
	} 
	if(!confirm('执行操作后信息将改变，确定吗？')) 
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
执行一个form的提交
actTo表示提交的目的
myTarget表示窗口目标，如'_self','_blank'等
myFormName表示要提交的form的名字，如果页面上只有一个form，那么该参数可以省略
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
把偶数的参数赋值给奇数指定的对象，例如：setValues('a',1)则表示将1付给a对象.
*/
function setValues(){
	if(arguments.length % 2!=0){
		alert("参数格式不符合要求");
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
把指定的select控件中选中的项的值付给另外一个指定对象
*/
function showOptionTextTo(theObj, myValue, toObj){
	var tmp = "";
	var myObject = getMyObject(theObj);
	if(myObject.selectedIndex>=0){
		var toObject = getMyObject(toObj);
		toObject.value = myObject[myObject.selectedIndex].text;
	}
}

//从指定的select控件中选择一个选项，该选项的值与参数相等
function selectOption(theObj,myValue){
	if(myValue.length<1) {
		alert('要在select对象中选择一个选项，但输入的值为空');
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
		alert('输入的用户无效，请做选择');
		myObject.focus();
	} else if(number>1) {
		alert('与您您输入的用户名对应的记录超过一个');
		myObject.focus();
	}
}

//将本地时间显示在指定的控件对象中
function showTime(myName) {
	var timeVal = getDate0();
	myObj = getMyObject(myName);
	if(myObj == false) {
		return false;
	} else {
		myObj.value = timeVal;
	}
}
//获取一个本地时间
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
//获取一个本地日期
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
限制控件的输入长度，一个中文算两个长度
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
		alert("参数格式不符合要求");
	}
	for(var i=0;i<arguments.length;i+=2) {
		var obj = getMyObject(arguments[i]);
		if(obj.value == ""){
			alert(arguments[i + 1] + "不能为空");
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
		alert("参数格式不符合要求");
	}
	for(var i=0;i<arguments.length;i+=3) {
		var obj = getMyObject(arguments[i]);
		if(obj) {
			var c = counter(obj);
			if(c>arguments[i + 2]) {
				alert("'"+arguments[i + 1] + "'的长度不能超过"+arguments[i + 2]+"个字符，一个中文计两个字符");
				obj.focus();
				return false;
			}
		}
	}
	return true;
}





/*
获取网页上的一个obj
if(如果是radion类型){
	返回多个对象中被选中的那个
} else if(不是radio类型){
	if(页面上仅有一个对象) {
		返回该对象
	} else if(页面上有多个对象) {
		if(参数指定某个的) {
			返回指定的对象
		} else {
			提示多个对象存在
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
			alert('页面上存在对应的控件个数超过1个，获取对象失败' + myName);
			return false;
		} else {
			for(var v = 0;v < objs.length;v++){
				if(objs[v].checked == true) {
					return objs[v];
				}
			}
		}
	} else if (objs.length<1) {
		//alert('页面上不存在对应的控件，获取对象失败' + myName);
		return false;
	}
	return objs[0];
}
function getMyObjectById(myId){
	var obj = document.getElementById(myId);
	if(obj){
		return obj;
	} else if (obj.length<1) {
		alert('页面上不存在对应的控件，获取对象失败' + myId);
		return false;
	}
}
//获取网页上的一个form，如果网页上有多个form，那么必须通过参数指定form的名字
function getMyForm(myFormName){
	var forms = document.getElementsByTagName("form");
	if(forms.length>1){
		if(!myFormName) {
			//alert('页面上存在多个form，无法定位,请指定form！');
			//return false;
			return forms[0];
		} else {
			forms = document.getElementsByName(myFormName);
			if(forms.length > 1){
				alert("您指定的form的个数超过1个，无法定位");
				return false;
			} else if (forms.length<1) {
				alert("您指定的form的无效，无法定位");
				return false;
			}
		}
	} else if (forms.length<1) {
		alert('页面上不存在form，无法提交');
		return false;
	}
	return forms[0];
}


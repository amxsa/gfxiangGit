
//校验提交form，ajax方式提交
function vaildSubmitFormForAjax(formId,beforeSubmitFun,callbackFun){
	if(!formId || formId == null){
		formId = document.forms[0].id;alert(formId);
	}
	
	var setOption = {
			tiptype:function(msg,o,cssctl){
				//msg：提示信息;
				//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
				//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
				if(o && o.type == "3"){
					alert(msg);
				}
			},
			tipSweep:true,
			postonce:true,
			ajaxPost:true,
			beforeSubmit:function(curform){
				sysAjaxLoad.close();
				
				if(beforeSubmitFun != null){
					var beforeSubmit = beforeSubmitFun();
					if(beforeSubmit){
						sysAjaxLoad.show();
					}
					return beforeSubmit;
				}else{
					sysAjaxLoad.show();
				}
			},
			callback:function(data){//alert(JSON.stringify(data));
				sysAjaxLoad.close();
				if(callbackFun != null){
					callbackFun(data);
				}
			}
		};
		
	var validformObj=$("#"+formId).Validform(setOption);
}
//校验提交form，form方式提交
function vaildSubmitFormForForm(callbackFun){
	var formId = null;
	if(!formId || formId == null){
		
		if(document.forms[0].id == ""){
			document.forms[0].id = "vaildFormId";
		}
		
		formId = document.forms[0].id;
	}
	
	var setOption = {
		tiptype:function(msg,o,cssctl){
			if(o && o.type == "3"){
				alert(msg);
			}
		},
		tipSweep:true,
		postonce:false,
		ajaxPost:false,
		beforeSubmit:callbackFun
	};
	
	$("#"+formId).Validform(setOption);
}

//同步ajax提交
function ajax(url,param,callbackFuc){
  	$.ajax({ 
		type:"post", 
		url:url, 
		async:false,
		data: param,//要发送的数据
		success:callbackFuc
	});
}
//异步ajax提交
function asyncAjax(url,param,callbackFuc){
  	$.ajax({ 
		type:"post", 
		url:url, 
		data: param,//要发送的数据
		 beforeSend:function(XMLHttpRequest){
         	sysAjaxLoad.show();
         },
         success:function(data){
         	sysAjaxLoad.close();
         	callbackFuc(data);
         },
         complete:function(){
          	sysAjaxLoad.close();
         },
         error:function(){
           	sysAjaxLoad.close();
         }
	});
}

//将json字符串转为json对象
function getJSONObject(jsonStr){
	if(jsonStr != null && jsonStr != ''){
		jsonStr = jsonStr.replace(/\&quot;/g,'"');
		
		return eval(jsonStr);
	}
}
//保存时根据名称校验非空值
function checkField(name,alertMsg){
	var obj = document.getElementsByName(name);
	if(obj && obj.length > 0){
		if(obj[0].value == ""){
			alert(alertMsg+"不能为空");
			obj[0].focus();
			return false;
		}
	}
	return true;
}
//根据name获取对象
function getObjectByName(name){
	var obj = document.getElementsByName(name);
	if(obj && obj.length > 0){
		return obj[0];
	}
	return null;
}
//设置下拉列表选定值
function setSelectOption(objId, targetValue){      //objid：下拉列表框的ID；targetValue：当前所选值
  var obj = document.getElementById(objId);
  if(obj){
    var options = obj.options;
    if(options){
      var len = options.length;
      for(var i=0;i<len;i++){
        if(options[i].value == targetValue){
          options[i].defaultSelected = true;
          options[i].selected = true;
          return true;
        }
      }
    } else {
      alert('missing element(s)!');
    }
  } else {
    alert('missing element(s)!');
  }
}
//选择第一个非空的select值
function selectFirstOption(objId){
  var obj = document.getElementById(objId);
  if(obj){
    var options = obj.options;
    if(options){
      var len = options.length;
      if(len > 0){
          options[0].defaultSelected = true;
          options[0].selected = true;     	
      }
    } else {
      alert('missing element(s)!');
    }
  } else {
    alert('missing element(s)!');
  }
}

//照片宽高
function DrawImage(ImgD,iwidth,iheight){  
	if(iwidth == null) iwidth = "96"; 
	if(iheight == null) iheight = "128"; 
    //参数(图片,允许的宽度,允许的高度)   
    var image=new Image();   
    image.src=ImgD.src;   
    if(image.width>0 && image.height>0){   
      if(image.width/image.height>= iwidth/iheight){   
          if(image.width>iwidth){     
              ImgD.width=iwidth;   
              ImgD.height=(image.height*iwidth)/image.width;   
          }else{   
              ImgD.width=image.width;     
              ImgD.height=image.height;   
          }   
      }else{   
          if(image.height>iheight){     
              ImgD.height=iheight;   
              ImgD.width=(image.width*iheight)/image.height;           
          }else{   
              ImgD.width=image.width;     
              ImgD.height=image.height;   
          }   
      }   
    }   
}
//向select选项中 加入一个Item        
function addItemToSelect(selectName, objItemText, objItemValue) {   
	
	var objSelect = document.getElementById(selectName);
	     
    var varItem = new Option(objItemText, objItemValue);      
    objSelect.options.add(varItem);     
}       
//清空select的值
function cleanSelect(selectName){
	document.getElementById(selectName).options.length = 0;  
} 

//获取radio选中值
function getRadioValue(radioName){
	var radioObjs = document.getElementsByName(radioName);
	
	var radioValue = "";
	
	for(var i = 0; i < radioObjs.length; i++){
		if(radioObjs[i].checked){
			radioValue = radioObjs[i].value;
		}
	}
	
	return radioValue;
} 
//选择所有同名的Checkbox选中/不选择
function selectAllCheckbox(checked,boxName){
	
	if(boxName == null){
		boxName = "box";	
	}
	
	var boxObj = document.getElementsByName(boxName);
	for(var i = 0; i < boxObj.length; i++){
		boxObj[i].checked = checked;
	}
}
//获取Checkbox选中值
function getCheckboxValue(boxName){
	if(boxName == null){
		boxName = "box";	
	}
	var checkBox = document.getElementsByName(boxName);

	var boxValue = "";
	for(var i = 0 ; i < checkBox.length; i++){
		if(checkBox[i].checked){
			if(boxValue != "") boxValue += ",";
			boxValue += checkBox[i].value;
		}
	}
	
	return boxValue;
}
//获取Checkbox没选中值
function getNotCheckboxValue(boxName){
	if(boxName == null){
		boxName = "box";	
	}
	var checkBox = document.getElementsByName(boxName);

	var boxValue = "";
	for(var i = 0 ; i < checkBox.length; i++){
		if(!checkBox[i].checked){
			if(boxValue != "") boxValue += ",";
			boxValue += checkBox[i].value;
		}
	}
	
	return boxValue;
}
//获取Checkbox值为选中
function setCheckboxValue(values,boxName){
	if(values == ""){
		return;
	}
	
	var valuesAry = values.split(",");
	
	if(boxName == null){
		boxName = "box";	
	}
	var checkBox = document.getElementsByName(boxName);

	for(var i = 0 ; i < checkBox.length; i++){
		for(var j = 0; j < valuesAry.length; j++){
			if(checkBox[i].value == valuesAry[j]){;
				checkBox[i].checked = true;
				break;
			}
		}
	}
}
//单个box点击
function boxCheck(obj){
	if(obj.checked){
		addBoxValue(obj.value);
	}else{
		subBoxValue(obj.value);
	}
}
//全选box
function allBoxCheck(checked){
	selectAllCheckbox(checked);
	
	if(checked){
		addBoxValue(getCheckboxValue("box"));
	}else{
		subBoxValue(getNotCheckboxValue("box"));
	}
}
//把选中值叠加
function addBoxValue(pageCheckValue){
	if(pageCheckValue == "") return;
	
	var pageCheckValueAry = pageCheckValue.split(",");
	
	var allCheckValue = $("#allCheckValue").val();
	var allCheckValueAry = allCheckValue.split(",");
	for(var j = 0; j < pageCheckValueAry.length; j++){
		var isNotIn = true;
		for(var i = 0; i < allCheckValueAry.length; i++){
			if(pageCheckValueAry[j] == allCheckValueAry[i]){
				isNotIn = false;
				break;
			}
		}
		if(isNotIn){
			if(allCheckValue != "") allCheckValue += ",";
			allCheckValue += pageCheckValueAry[j];
		}
	}
	$("#allCheckValue").val(allCheckValue);
}
//把已存在的值去掉
function subBoxValue(pageCheckValue){
	if(pageCheckValue == "") return;
	
	var pageCheckValueAry = pageCheckValue.split(",");
	
	var allCheckValue = $("#allCheckValue").val();
	var allCheckValueAry = allCheckValue.split(",");
	for(var j = 0; j < pageCheckValueAry.length; j++){
		for(var i = allCheckValueAry.length - 1; i >= 0 ; i--){
			if(pageCheckValueAry[j] == allCheckValueAry[i]){
				allCheckValueAry.splice(i,1);
				break;
			}
		}
		
	}
	
	$("#allCheckValue").val(allCheckValueAry);
}
//根据权限显示按钮
function showButton(pageButton){
	
	if(pageButton == "" || pageButton == "null") return;
	
	var pageButtonAry = pageButton.split(",");
	for(var i = 0 ; i < pageButtonAry.length ; i++){
		var buttonName = pageButtonAry[i];
		
		if(buttonName == null || buttonName == "") continue;
		
		var objAry = document.getElementsByName(buttonName);
		if(objAry != null && objAry.length > 0){
			for(var j = 0 ; j < objAry.length; j++){
				objAry[j].style.display = "inline";
			}
		}else{//alert(document.getElementById(buttonName));
			if(document.getElementById(buttonName))
				document.getElementById(buttonName).style.display = "inline";
		}
	}	
}


//artDialog弹出框
function showArtDlg(title, url,returnFun, width, height, lock) {
    if (width == null || width == "") {
        width = '70%';
    }
    if (!width.indexOf('px') && !width.indexOf('%')) {
        width = width + 'px';
    }
    if (width.indexOf('px') < 0 && width.indexOf('%') < 0) {
        width = width + 'px';
    }

    if (height == null || height == "") {
        height = '90%'
    }
    if (height.indexOf('px') < 0 && height.indexOf('%') < 0) {
        height = height + 'px';
    }

    if (lock == null || lock == "") {
        lock = true;
    }
    if (returnFun == null || returnFun == "") {
        returnFun = null;
    }
    
    var artObj = art.dialog.open(url, { height: height, width: width, title: title, lock: lock,close:returnFun}, false); //打开子窗体

	return artObj;
}
function hiddeArtDlg(){
	art.dialog.close();
}

//下载文件
function downloadFile(url,fileName){
	location.href = sysPath+"/download/downloadAction_download.action?url="+url+"&fileName="+fileName;
}
//校验密码
function checkPassword(password,confimPassword){
	
	if(password.value == ""){
		alert("请输入新密码！");
		password.focus();
		return false;
	}
	
	/*
	var checkResult = password.value.match(/^(?=[\x21-\x7e]*[A-Z][\x21-\x7e]*)(?=[\x21-\x7e]*[a-z][\x21-\x7e]*)(?=[\x21-\x7e]*\d[\x21-\x7e]*)(?=[\x21-\x7e]*((?=[\x21-\x7e]+)[^A-Za-z0-9])[\x21-\x7e]*)[\x21-\x7e]{8,30}$/);
	
	if(checkResult == null){
		alert("密码不少于8位，含大小写英文字符、数字，特殊字符!");
		return;
	}
	*/
	/*
	if(getPasswordComplexity(password.value)<3){
 		alert("密码不少于8位，含大小写英文字符、数字，特殊字符!"); 
  		password.focus(); 
  		return false;   
	}
	*/
	if(confimPassword.value == ""){
		alert("请输入确认密码！");
		confimPassword.focus();
		return false;
	}
	if(password.value != confimPassword.value){
		alert("新密码与确认密码不相等！");
		return false;
	}

	return true;
}
//获取密码负责度
function getPasswordComplexity(s){
	if(s.length < 8){
   		return 0;   
	} 
	var ls = 0;
	if(s.match(/([a-z])+/)){
		ls++;
	}
	if(s.match(/([0-9])+/)){
		ls++;     
	}
	if(s.match(/([A-Z])+/)){
		ls++;  
	}   
	if(s.match(/[!@#$%^&*()_+|]+/)){
		ls++;
	}
	return ls;
}

function disableBtn(selector){
	$(selector).attr("onclick","");
	$(selector).unbind("click");
	$(selector).css("background", "#8e8e8e");
}
function enableBtn(selector){
	//$(selector).attr("onclick", "sendData()");
	$(selector).attr("onclick","").click(function(){
		eval("sendData()");
	});
	$(selector).css("background", "##2B80D0");
}
function disableBtn2(selector){
	$(selector).unbind("click");
	$(selector).css("background", "#8e8e8e");
}
function enableBtn2(selector){
	$(selector).bind("click", sendData());
	$(selector).css("background", "##2B80D0");
}

/**
 * 后台校验js
 */
var verifyForm = {

	/**
	 * 校验输入框不能为空。
	 * goalVal输入框的jq对象  必填
	 * msg输入框的名称 必填
	 * errDiv为显示错误div 可不填  null默认为next
	 * css错误div附加样式 可不填
	 */
	inputNotNull : function(goalObj, msg,errDiv,css) {
		var goalVal=goalObj.val();
		if (goalVal == '' || goalVal.length == 0 || goalVal == null) {
			var errMsg=msg + '不能为空！';
			if(errDiv==null){
				goalObj.next().html(errMsg);
				goalObj.next().addClass('formerr');
				if(css!=null){
					goalObj.next().css(css);
				}
			}else{
				errDiv.html(errMsg);
				errDiv.addClass('formerr');
				if(css!=null){
					errDiv.css(css);
				}
			}
			return false;
		} else {
			if(errDiv==null){
				goalObj.next().html('');
				goalObj.next().removeClass('formerr');
			}else{
				errDiv.html('');
				errDiv.removeClass('formerr');
			}
			return true;
		}
	},

	/**
	 * realityStr要判断的字符串；realityStrName要判断的字符串名称，realityStrLength要判断的字符串的限定长度
	 * */
	checkStrLength : function(realityStr, realityStrName, realityStrLength) {
		var setLength = realityStrLength;
		var realityLength = realityStr.length;
		if (realityLength > setLength) {
			alert(realityStrName + '输入错误，只能输入' + realityStrLength
			+ '个字符');
			return false;
		} else {
			return true;
		}
	},
	
	/**
	 * 判断输入是否是邮箱
	 * */
	isEmail : function(goajObj) {
		var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/; //验证邮箱的正则表达式
		if (!reg.test(goajObj)) {
			alert("输入的邮箱格式不对");
			return false;
		} else {
			return true;
		}
	},
	
	/**
	 * 判断输入是否是手机号码
	 * */
	isMobile : function(goajObj) {
		var type = "^1[3,5,8]\\d{9}$";
		var re = new RegExp(type);
		if (goajObj.match(re) == null) {
			alert('输入的手机号码格式不对');
			return false;
		} else {
			return true;
		}
	},
	/**
	 * 判断输入是否是QQ
	 * */
	isQQ : function(goajObj) {
		var type = "^[1-9]\\d{4,10}$";
		var re = new RegExp(type);
		if (goajObj.match(re) == null) {
			alert('输入的QQ格式不对');
			return false;
		} else {
			return true;
		}
	},
	

	/*
	 * 判断只能是整数
	 * @author pansenxin
	 * @params:checkObj,检查对象,noticStr,检查字段名称
	 * @return:
	 * version: 2013-8-27
	 */
	checkInt : function(checkObj, noticStr) {
		if (checkObj == '') {
			return true;
		}
		var type = "^-?\\d+$";
		var re = new RegExp(type);
		if (checkObj.match(re) == null) {
			alert(noticStr + '输入错误，只能为整数！');
			return false;
		} else {
			return true;
		}
	},
	
	checkMoney:function(checkObj, noticStr){
		if (checkObj == '') {
			return true;
		}
		var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");  
		if (!reg.test(checkObj)) {  
			alert(noticStr+"输入错误，只能是正数，且最多两位小数！");  
			return false;
		}else{
        	return true;
        }
	}
};

(function() {
  // Private array of chars to use
  var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');

  Math.uuid = function (len, radix) {
    var chars = CHARS, uuid = [], i;
    radix = radix || chars.length;

    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      // rfc4122, version 4 form
      var r;

      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';

      // Fill in random data.  At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }

    return uuid.join('');
  };

  // A more performant, but slightly bulkier, RFC4122v4 solution.  We boost performance
  // by minimizing calls to random()
  Math.uuidFast = function() {
    var chars = CHARS, uuid = new Array(36), rnd=0, r;
    for (var i = 0; i < 36; i++) {
      if (i==8 || i==13 ||  i==18 || i==23) {
        uuid[i] = '-';
      } else if (i==14) {
        uuid[i] = '4';
      } else {
        if (rnd <= 0x02) rnd = 0x2000000 + (Math.random()*0x1000000)|0;
        r = rnd & 0xf;
        rnd = rnd >> 4;
        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
      }
    }
    return uuid.join('');
  };

  // A more compact, but less performant, RFC4122v4 solution:
  Math.uuidCompact = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
      return v.toString(16);
    });
  };
})();

Date.prototype.format = function(format)//
{
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};
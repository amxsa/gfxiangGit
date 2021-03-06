/*edited by fukai*/

(function($) {
	$.fn.validationEngineLanguage = function() {};
	$.validationEngineLanguage = {
		newLang: function() {
			$.validationEngineLanguage.allRules = 	{"required":{   
						"regex":"none",
						"alertText":"* 非空选项.",
						"alertTextCheckboxMultiple":"* 请选择一个单选框.",
						"alertTextCheckboxe":"* 请选择一个复选框."},
					"length":{
						"regex":"none",
						"alertText":"* 长度必须在",
						"alertText2":" 至 ",
						"alertText3": "之间."},
					"maxCheckbox":{
						"regex":"none",
						"alertText":"* 最多选择 ",
						"alertText2":" 项."},	
					"minCheckbox":{
						"regex":"none",
						"alertText":"* 至少选择 ",
						"alertText2":" 项."},	
					"confirm":{
						"regex":"none",
						"alertText":"* 两次输入不一致,请重新输入."},		
					"telephone":{
						"regex":"/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/",
						"alertText":"* 请输入有效的电话号码,如:010-29292929."},
					"mobilephone":{
						"regex":"/(^0?[1][3-9][0-9]{9}$)/",
						"alertText":"* 请输入有效的手机号码."},	
					"imsphonevalid" : {   //LSP
							"regex" : "/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+([0-9]{1,4})?$)|(^0?[1][3-9][0-9]{9}$)/",
							"alertText" : "* 请输入有效手机号码或电话号码.",		
							"alertText2" : "* 号码只能由数字组成."
						},	
					"email":{
						"regex":"/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/",
						"alertText":"* 请输入有效的邮件地址."},	
					"date":{
                         "regex":"/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/",
                         "alertText":"* 请输入有效的日期,如:2008-08-08."},
					"ip":{
                         "regex":"/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/",
                         "alertText":"* 请输入有效的IP."},
                     "isidcard":{// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/  
     					 "regex":"/(^[1-9][0-7][0-9]{4}" +
     								"((19[0-9]{2}(0[13-9]|1[012])(0[1-9]|[12][0-9]|30))|(19[0-9]{2}(0[13578]|1[02])31)" +
     								"|(19[0-9]{2}02(0[1-9]|1[0-9]|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))[0-9]{3}[0-9]|X|x$)" +
     								"|(^[1-9][0-7][0-9]{4}" +
     								"(([0-9]{2}(0[13-9]|1[012])(0[1-9]|[12][0-9]|30))|([0-9]{2}(0[13578]|1[02])31)" +
     								"|([0-9]{2}02(0[1-9]|1[0-9]|2[0-8]))|(([13579][26]|[2468][048]|0[48])0229))[0-9]{3}$)/",
     					 "alertText":"* 请输入有效的身份证号."},
	                "validateIdCard":{
							"nname":"validateIdCardfun",
							"alertText":"* 请输入有效的身份证号."},
					"validateRandCode":{
							"nname":"validateRandCodefun",
							"alertText":"* 验证码错误."},
					"chinese":{
						"regex":"/^[\u4e00-\u9fa5]+$/",
						"alertText":"* 请输入中文."},
					"url":{
						"regex":"/^[a-zA-z]:\\/\\/[^s]$/",
						"alertText":"* 请输入有效的网址."},
					"zipcode":{
						"regex":"/^[1-9]\d{5}$/",
						"alertText":"* 请输入有效的邮政编码."},
					"qq":{
						"regex":"/^[1-9]\d{4,9}$/",
						"alertText":"* 请输入有效的QQ号码."},
					"onlyNumber":{
						"regex":"/^[0-9]+$/",
						"alertText":"* 请输入数字."},
					"onlyLetter":{
						"regex":"/^[a-zA-Z]+$/",
						"alertText":"* 请输入英文字母."},
					"noSpecialCaracters":{
						"regex":"/^[0-9a-zA-Z]+$/",
						"alertText":"* 请输入英文字母或数字."},
					"onlyNumberAndDot":{
							"regex":"/^[0-9]+(\.\[0-9]{1,2})?$/",
							"alertText":"* 请输入整数或两位以内小数."},
					"onlyNumOrDot":{
							"nname":"validateNumOrDotFun",
							"alertText":"* 请输入有效的数据."},	
					"onlyURL":{
								"nname":"IsURL",
								"alertText":"* 请输入有效的网址."},
					"onlyXueYa":{
						"nname":"validateXueYaFun",
						"alertText":"* 请输入有效的数据."},
					"checkPassword":{
						"nname":"checkRePassword",
						"alertText":"* 密码不一致."},
					"sfzCheck":{
						"file":"sfzCheck.html",
						"alertText":"身份证号已被使用.",
						"alertTextOk":"身份证号可以使用",	
						"alertTextLoad":"* 检查中, 请稍后..."},
					"accountCheck":{
							"file":"accountCheck.html",
							"alertText":"账号已被使用.",
							"alertTextOk":"账号可以使用",	
							"alertTextLoad":"* 检查中, 请稍后..."},
					"codeCheck":{
								"file":"codeCheck.html",
								"alertText":"工号已存在.",
								"alertTextOk":"工号可以使用",	
								"alertTextLoad":"* 检查中, 请稍后..."},
					"jchCheck":{
						"file":"checkUniqueJch.html",
						"alertText":"此家床号已经被分配.",
						"alertTextOk":"家床号可以使用",	
						"alertTextLoad":"* 检查中, 请稍后..."},	
					"checkJcsqSfzhRepeat":{
						"file":"checkJcsqSfzhRepeat.html",
						"alertText":"身份证号已经在建床记录中存在.",
						"alertTextOk":"身份证号可以使用",	
						"alertTextLoad":"* 检查中, 请稍后..."},	
					"checkDeviceSerial":{
							"file":"checkDeviceSerialServlet",
							"alertText":"该设备号不存在，请检查后再输入.",
							"alertTextOk":"该设备号已通过验证",	
							"alertTextLoad":"* 检查中, 请稍后..."},
					"checkDevicebindStatus":{
							"file":"checkDevicebindStatusServlet",
							"alertText":"该设备号已被注册，不能重复使用，请检查后再输入.",
							"alertTextOk":"该设备号已通过验证",	
							"alertTextLoad":"* 检查中, 请稍后..."},
					"validateUserAccount":{
						"file":"commonAction!validateUserAccount.html",
						"alertText":"该账号已被使用，不能重复使用，请检查后再输入.",
						"alertTextOk":"账号可用",	
						"alertTextLoad":"* 检查中, 请稍后..."},
					"validateUserMobile":{
						"file":"commonAction!validateUserMobile.html",
						"alertText":"该手机号已被使用，不能重复使用，请检查后再输入.",
						"alertTextOk":"手机号可用",	
						"alertTextLoad":"* 检查中, 请稍后..."},
					"custom1":{
    					"nname":"customFunc1",
    					"alertText":"* 必须输入123"}
					}	
		}
	}
})(jQuery);

$(document).ready(function() {	
	$.validationEngineLanguage.newLang()
});
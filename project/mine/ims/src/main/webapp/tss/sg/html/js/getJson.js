/**
 * 搜索页面input并组装成json 传回后台保存
 */
var ajaxSg = {
	/**
	 * 定义的参数
	 */
	options : {
		'method' : 'post',
		'data' : ''
	},
	/**
	 * 赋值
	 * 
	 * @return $this 当前对象
	 */
	setOptions : function($this, option) {
		if ("undefined" == option || null == option) {
			ajaxJx.options = $.extend({}, ajaxJx.options, {
				"url" : $this.attr("jx-href"),
				"method" : $this.attr("jx-method")
			});
		} else {
			ajaxJx.options = $.extend({}, ajaxJx.options, option);
		}
		return $this;
	},

	/**
	 * ajax 事件
	 * 
	 */
	sg_ajaxClick : function(option) {
		// 获取当前对象
		$this = ajaxJx.setOptions($(this),option);
		// 变为大写
		ajaxJx.options.method = ajaxJx.options.method.toUpperCase();
		/**
		 * 设置参数 v value;t text
		 */
		var _paramsArr = ajaxJx.attr2param();
		if(_paramsArr){
			if ("POST" == ajaxJx.options.method) {
				ajaxJx.options.data = _paramsArr;
			}
			var d = {
					dataJson : ajaxJx.options.data
			}; 
			$.ajax({
				type : ajaxJx.options.method,
				url : ajaxJx.options.url,
				dataType : 'json',
				data : d,
				timeout : 50000,
				cache : false,
				error : function(XMLHttpRequest, status, thrownError) {
				},
				success : function(msg) {
						return msg;
				}
			});
		}
	},

	/**
	 * 属性转换为参数
	 */
	attr2param : function() {
		var _paramsArr ="{";  
		/**
		 * 遍历页面所有input
		 */
		$(":input").each(function(i,input){
			var _check=$(input).attr("sg_check");
			var _json=$(input).attr("sg_json");
			var _name=$(input).attr("name");
			var _str=$(input).val();
			var _result=ajaxJx.check(_check,_str);
			if(_result){
				if(_json ==null){
					return false;
				}else{
					// 如果json为*则取name
					if(_json=="*"){
						_paramsArr += "\""+_name+"\":\""+_str+"\",";      
						// _paramsArr[_name] = ;
					}else{
						_paramsArr += "\""+_json+"\":\""+_str+"\",";      
						// _paramsArr[_json] = _str;
					}
				}
			}
			else{
				_paramsArr=false;
			}
		});
		/**
		 * 遍历页面所有select
		 */
		// $("select").each(function(i,_select){
		// var _check=$(_select).attr("sg_check");
		// var _json=$(_select).attr("sg_json");
		// var _name=$(_select).attr("name");
		// var _str=$(_select).val();
		// var _result=ajaxJx.check(_check,_str);
		// if(_result){
		// if(_json ==null){
		// return false;
		// }else{
		// //如果json为*则取name
		// if(_json=="*"){
		// _paramsArr += "\""+_name+"\":\""+_str+"\",";
		// //_paramsArr[_name] = ;
		// }else{
		// _paramsArr +="\""+_json+"\":\""+_str+"\",";
		// //_paramsArr[_json] = _str;
		// }
		// }
		// }else{
		// _paramsArr=false;
		// }
		//			
		// });
	    if (_paramsArr.lastIndexOf(",")) {  
	    	_paramsArr = _paramsArr.substring(0,_paramsArr.length -1);  
	    	_paramsArr += "}";  
        }  
		return _paramsArr;
	},
	/**
	 * 值校验
	 */
	check : function(datastr,str) {
		if(datastr==null) {return true;}
		else{
			var res= new Array();
			res=datastr.split("|");
			if(res!=null){
				switch (res[0]) {
					case "empty" :
						if(!isEmpty(str)){
							alert(res[1]);
							return false;
						}
						break;
					case "email" :
						if(!isEmail(str)){
							alert(res[1]);
							return false;
						}
						break;
					case "number" :
						if(!isNumber(str)){
							alert(res[1]);
							return false;
						}
						break;
					case "digits" :
						if(!isDigits(str)){
							alert(res[1]);
							return false;
						}
						break;
					case "tel" :
						if(!isTel(str)){
							alert(res[1]);
							return false;
						}
						break;
				}
			}
		}
		return true;
	}
};
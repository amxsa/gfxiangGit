/**
 * 通过后台
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
			ajaxSg.options = $.extend({}, ajaxSg.options, {
				"url" : $this.attr("jx-href"),
				"method" : $this.attr("jx-method")
			});
		} else {
			ajaxSg.options = $.extend({}, ajaxSg.options, option);
		}
		return $this;
	},

	/**
	 * ajax 事件
	 * 
	 */
	sg_loadObject : function(url,option) {
		// 获取当前对象
		$this = ajaxSg.setOptions($(this),option);
		// 变为大写
		ajaxSg.options.method = ajaxSg.options.method.toUpperCase();
		/**
		 * 设置参数 v value;t text
		 */
		if ("POST" == ajaxSg.options.method) {
			ajaxSg.options.data = option;
		}
		var d = {
				dataJson : ajaxSg.options.data
		}; 
		$.ajax({
			type : ajaxSg.options.method,
			url : url,
			dataType : 'json',
			data : d,
			timeout : 50000,
			cache : false,
			error : function(XMLHttpRequest, status, thrownError) {
			},
			success : function(msg) {
				var data = eval("(" + msg + ")");
				if (data.success) {
					$.each(data, function (name, ival) {
						var $oinput = $this.find("input:[name=" + name + "]");
						if($oinput ==null){
							$oinput = $this.find("input:[sg_json=" + name + "]");
						}
						$oinput.val()==ival;
					});
				}
			}
		});
	}
};
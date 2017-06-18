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





 function getItemHtml(row){
                var temp ='<div class="item pd" onclick="javascript:location.href=\'myMsg.html\'">';
                temp+='<div class="item_left">';
                temp+='<img src="img/pic.png" alt="" />';
                temp+='</div>';
                temp+='<div class="item_right">';
                temp+='<p class="p1"><i>'+row.aaa+'</i><em>'+row.aaa+'</em></p>';
                temp+='<p class="p2">电话：<em>'+row.bbb+'</em></p>';
                temp+='</div></div>';
                return temp;
            }

function getItemHtml(row){
		var temp ='<ul>';
		temp+='<li>';
		temp+='<span class="title_p">'+row.aaa+'</span>';
		temp+='<i>'+row.bbb+'</i>';
		temp+='<em class="time">'+row.bbb+' '+row.bbb+'</em>';
		temp+='</li>';
		return temp;
	}
function isVal(ele){
	if (ele!=null&&ele!=""&&ele!=undefined) {
		return true;
	}
	return false;
}
function getJson(action,params){
	jQuery
	.ajax({
		type : "get",
		url : action,
		async:false,
		data : params,
		dataType:"jsonp",
        jsonp: "jsoncallback",
        jsonpCallback: "success_jsonpCallback",
		success : function(json) {	
			//alert(JSON.stringify(json))
			return json;
	    },error:function(data){
	    	return null;
	    }
    });	
}

$(function(){
	/**
	 * 用于表格鼠标上浮显示全部内容
	 */
	$(".hoverTip").mouseover(function(){
		if (isVal($(this).text())) {
			layer.tips($(this).text(), this ,{tips:2/*[2,'#393D49']*/} )
		}})
	.mouseout(function(){layer.closeAll("tips")});
	/**
	 * 全选
	 */
	$("#checkAll").click(function(){
		$(":checkbox").prop("checked",this.checked)
	})
	
})
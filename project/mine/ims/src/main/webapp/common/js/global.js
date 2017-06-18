function isVal(ele){
	if (ele!=null&&ele!=""&&ele!=undefined) {
		return true;
	}
	return false;
}
function getJson(action,params){
    var temp = "";
	jQuery
	.ajax({
		type : "POST",
		url : action,
		async:false,
		data : params,
		dataType : "json",
		success : function(json) {			
			temp = json;								
	    }
    });	
	return temp;	
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
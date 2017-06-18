function gotoPage(urladdr) {
	$("#hospitalsList").attr("action",urladdr).submit();
}

/**
 * 分配医院到村委
 * @param urladdr
 * @param xm
 */
function gotoFpXzCw(urladdr,xm){
	var diag = new top.Dialog();
	diag.Title = "分配"+xm+"到村委";
	diag.URL = urladdr;
	diag.Width = 600;
	diag.Height= 480;
	
	diag.OKEvent = function(){
		var commitResult = diag.innerFrame.contentWindow.commit();//提交结果  只有提交成功才返回true
		if(commitResult){
			diag.close();
			location.href=location.href;
		}
	};
	
	diag.show();
	showProgressBar();
}
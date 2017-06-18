/**
 * 添加
 * @author liuqinghui
 */
function jumpToAddPage(){
	var type=$("#activityType").attr("value");
	var diag = new top.Dialog();
	diag.ID="addAreaDiag";
	if(type==""){
		top.Dialog.alert("请选择活动类型！");
	}else{
		diag.Title = "添加活动";
		diag.URL = "/mms/jumpToAddActivityPage.html?queryBean.type="+type;
		diag.Width = 850;
		diag.Height= 650;
		diag.ShowButtonRow=false;
		diag.show();
		showProgressBar();
	}
}

/**
 * 编辑
 * @author liuqinghui
 */
function jumpToEditPage(id){
	var diag = new top.Dialog();
	diag.Title = "修改活动";
	diag.URL = "/mms/jumpToEditActivityPage.html?queryBean.id="+id;
	diag.Width = 850;
	diag.Height= 650;
	diag.ID="addAreaDiag";
	diag.OKEvent = function(){
		var commitResult = diag.innerFrame.contentWindow.submitForm();//提交结果  只有提交成功才返回true
		if(commitResult==-1) return; //验证不通过
		
		if(commitResult==1){
			top.Dialog.alert("修改活动成功！");
			diag.close();
			location.href=location.href;
		}else{
			top.Dialog.alert("网络异常！");
		}
		
	};
	
	diag.show();
	showProgressBar();
}

/**
 * 查看详情
 * @param id
 */
function view(id){
	location.href="/mms/jumpToViewActivityPage.html?queryBean.id="+id;
}
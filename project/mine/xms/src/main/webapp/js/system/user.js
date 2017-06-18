/**
 * 是否禁用用户
 */
function singleDisableChecked(url,id,flag,newUrl){
	var tipmsg = ""
	if(flag == '0'){//禁用
		tipmsg = "是否禁用所选用户？";
	}else if(flag == '1'){//启用
		tipmsg = "是否启用所选用户？";
	}
	top.Dialog.confirm(tipmsg,function(){
		top.topMask();
		
		$.ajax({
			type: "POST",
			url: url,
			dataType: "json",
			data: "id="+id+"&flag="+flag,
			success: function(msg){
				top.topUnMask();

				if("true"==msg.result){
					top.Dialog.alert("操作成功！");
					window.location.href=newUrl;
				}else{
					top.Dialog.alert(msg.error);
				}				
			}
		});		
	});
}


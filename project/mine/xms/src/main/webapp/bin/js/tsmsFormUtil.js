

var ajaxFormError=-2;//ajax提交异常

var ajaxFormFalse=-1;//验证未通过

var ajaxFormTrue=1;//成功提交数据并返回结果

/**
 * 验证表单，通过提交数据
 * @param formId 
 * @returns {Number}
 */
function submitForm(formId){
	if(!$("#"+formId).validationEngine({returnIsValid:true}))
		return ajaxFormFalse;	
	else{
		$("#"+formId).submit();
	}
}

/**
 * ajax提交表单
 * @param dg  dialog
 * @param form 表单
 * @param data 提交的对象数据{property1:value1,property2:value2....}
 * 
 */
function ajaxForm(form){
	var result=ajaxFormFalse;
	if(!$(form).validationEngine({returnIsValid:true}))
		return result;
	
	$.ajax({
	    type: "POST",
	    async:false,
	    dataType: "html", 
	    url:$(form).attr("action"),
	    data:$(form).serialize(),
	    success: function(data) {
		   result=data;
	    },
		error: function () {
		   result=ajaxFormError;
		} 
	 });
		
	return result;		
}

/**
 * 带文件上传的表单处理
 * @param form
 * @param callbackFunction 表单提交后需要执行的函数
 * @returns {Boolean}
 */
	
function ajaxFormWithUploadFile(form,callbackFunction){
	var flag=$('#repairService').validationEngine({returnIsValid:true});
	if(!flag) return ajaxFormFalse;
	if ($("#callbackframe").size() == 0) {
		$("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
	}
	window.donecallback = callbackFunction;	
	$(form).attr("target","callbackframe");
	$(form).submit();
	return ajaxFormTrue;
}


/** 
 * 将数字转换成中文数字 
 * @author Prosper 
 * 
 */  
function intToZH(i)  
{  
    var zh = new Array("零","壹","貳","叄","肆","伍","陆","柒","捌","玖");    
    var unit = new Array("","拾","佰","迁","万","拾","佰","迁","亿","拾");    
    var str = "";  
    var xs = 0;
   var spl = i.split("\.");
   if(spl.length==2){
	   xs=spl[1];
   }
    var sb = spl[0].split("").reverse().join().replace(/,/g,"");  
    var r = 0;  
    var l = 0;  
    for (var j = 0; j < sb.length; j++)  
    {  
        /** 
         * 当前数字 
         */  
        r = sb.substring(j, j+1);  
          
        if (j != 0)  
            /** 
             * 上一个数字 
             */  
            l = sb.substring(j-1, j);  
          
        if (j == 0)  
        {  
            if (r != 0 || sb.length == 1)  
                str = zh[r];  
            continue;  
        }  
          
        if (j == 1 || j == 2 || j == 3 || j == 5 || j == 6 || j == 7 || j == 9)  
        {  
            if (r != 0)  
                str = zh[r] + unit[j] + str;  
            else if (l != 0)  
                str = zh[r] + str;  
            continue;  
        }  
          
        if (j == 4 || j == 8)  
        {  
            str =  unit[j] + str;  
            if ((l != 0 && r == 0) || r != 0)  
                str = zh[r] + str;  
            continue;  
        }  
    }  
    str=str+"圆";
    if(xs>0){
	    var xsy = xs.split("");
	    if(xsy.length>0){
	    	str=str+zh[xsy[0]]+"角"; 
	    }
	    if(xsy.length>1){
	    	str=str+zh[xsy[1]]+"分"; 
	    }
    }
    return str;  
}  	
/**
 * 针对QUI表格进行修改，添加判断复选框disabled属性来操作是否可以选中
 * 
 * 
 * 
 */
    	$(function(){
    
      		$("#cab").toggle(function(){//打印按钮  
	        	//选中未发布的checkbox,并增加选中样式
      			$("#cab").attr("src","/mms/bin/icons/checkAllOn.gif");
      			//获取所有的checkbox对象，判断checkbox的状态,若是disabled状态则不选
      			  $(":checkbox").each(function() {
      				  $(this).click(function(){
      					  if($(this).attr("checked")==true){
      						$(this).parent().parent().addClass("odd selected");
      					  }else{
      						$(this).parent().parent().removeClass("odd selected"); 
      					  }
      				  });
      				 if ($(this).attr("disabled") != true){
      					$(this).attr("checked", true);
      					//获取td的上一级对象tr
      					$(this).parent().parent().addClass("odd selected");
      				 }
      			  });
      					
	  		},
	  			 function(){
	  		   //取消选中状态,并取消选中样式
	  			$("#cab").attr("src","/mms/bin/icons/checkAllOff.gif");
	  		  	
	  		    $(":checkbox").each(function() {
   				 if ($(this).attr("disabled") != true){
   					$(this).attr("checked", false);
   					$(this).parent().parent().removeClass("odd selected");
   				 }
   			  });
	  		}); 

}); 
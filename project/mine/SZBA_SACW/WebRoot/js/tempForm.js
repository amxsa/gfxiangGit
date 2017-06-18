if(typeof (Common) === 'undefined' || Common == null) Common = {};
Common.tempForm = {
	
	initData: function(refererUrl, conditionsStr){
		var myForm = document.createElement('form'); 
		myForm.id='tempForm';   
		myForm.action= sysPath + refererUrl;  
		myForm.method='post'; 
		document.body.appendChild(myForm);
		
		var conditionNames = [];
		conditionNames.push('applicationNo');
		conditionNames.push('condiProNo');
		conditionNames.push('condiProName');
		conditionNames.push('condiJzcaseId');
		conditionNames.push('condiCaseName');
		conditionNames.push('condiStartTime');
		conditionNames.push('condiEndTime');
		conditionNames.push('currentIndex');
		conditionNames.push('totalCount');
		conditionNames.push('totalPage');
		conditionNames.push('sizePerPage');
		
		
		var params = JSON.parse(conditionsStr);
		
		for(var i = 0; i < conditionNames.length; i++ ){
			var name = conditionNames[i];
			var value = eval("params['"+name+"']");
			
			var inp="<input name='"+name+"' value='"+value+"' type='hidden'/>";
			
			if(value !== '' && value != null){
				$(myForm).append(inp);//在form中追加input表单
			}
			
		}
		$(myForm).submit();
	}
}

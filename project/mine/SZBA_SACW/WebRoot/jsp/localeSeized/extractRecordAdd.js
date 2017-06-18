var extractRecordAdd={
	startEvent:function(){
		//过程和结果输入框自动增长
		$('#resultTxt').autoHeight();
		
		if(grecordType==4){
			$('#recordTxt').html('提取笔录');
		}else if(grecordType==5){
			$('#recordTxt').html('扣押笔录');
		}else if(grecordType==6){
			$('#recordTxt').html('搜查笔录');
		}else if(grecordType==7){
			$('#recordTxt').html('查封笔录');
		}else if(grecordType==8){
			$('#recordTxt').html('检查笔录');
		}
		$("#recordTab ul").idTabs();
		
		//检查笔录 特殊处理
		if(grecordType==8){
			$('#zcyTxt').html('民警');
			$('#jlTab').css('display','none');
			$('#qtzcTab').css('display','none');
		}
	},
	
	//抓获过程
	addCatchProcessImpl:function(){
		var ret=[];
		//校验民警
		var name=$('#name').val();
		var departmentID=$('#departmentID').val();
		var acpId=Math.uuid();
		ret[0]=false;
		ret[1]=acpId;
		
		if($.trim($('#content').val())==''){
			alert('请输入内容');
			return ret;
		}
		var polices=[{
			'id':acpId,
			'name':name,
			'departmentID':departmentID
		}];
		var clientUrl='/catchProcess/update.do';
		var catchProcess={
			'id':Math.uuid(),
			'fromPart':'web',
			'recordType':grecordType,
			'recordID':$('#recordID').val(),
			'type':'28',  //抓获经过，类型为28
			'content':$('#content').val(),
			'polices':polices
		}
		var localUrl=sysPath+'/client/clientRequestJson.do';
		var params={
			'catchProcess':catchProcess
		};
		var cb=function(data){
			if(data.state=='Y'){
				ret[0]=true;
				alert('添加成功');
			}else{
				ret[0]=false;
				alert(data.msg);
			}
		}
		clientAjaxPostSync(localUrl,clientUrl,params,cb);
		return ret;
	},
	
	bindEvent:function(){
		//返回
		$('.goBack').on('click',function(){
			window.location.href = sysPath + "/jsp/localeSeized/extractRecord.jsp?recordType="+grecordType;
		});
		//打印抓获过程
		$('#printCatchProcess').on('click',function(){
			var ret=extractRecordAdd.addCatchProcessImpl();
			if(ret[0]){
				var recordID=$('#recordID').val();
				var printPath = sysPath + "/jsp/localeSeized/extractRecordCatchProcessPrint.jsp?recordType="+grecordType+"&recordID="+recordID;
				window.open(printPath);
			}
		});
		//添加抓获过程
		$('#addCatchProcess').on('click',function(){
			extractRecordAdd.addCatchProcessImpl();
		});
		
		//侦查员添加
		$('#addzcyBtn').on('click',function(){
			var nli=extractRecordAdd.getRelevantLi('zcy');
			$('#zcyTab').append(nli);
		});
		//当事人添加
		$('#adddsrBtn').on('click',function(){
			var nli=extractRecordAdd.getRelevantLi('dsr');
			$('#dsrTab').append(nli);
		});
		//见证人添加
		$('#addjzrBtn').on('click',function(){
			var nli=extractRecordAdd.getRelevantLi('jzr');
			$('#jzrTab').append(nli);
		});
		//其他在场人员
		$('#addqtzcBtn').on('click',function(){
			var nli=extractRecordAdd.getRelevantLi('qtzc');
			$('#qtzcTab').append(nli);
		});
		//添加笔录
		$('#addRecord').on('click',function(){
			extractRecordAdd.saveRecord();
		});
		//打印笔录
		$('#printRecord').on('click',function(){
			var ret=extractRecordAdd.saveRecord();
			if(ret){
				var recordID=$('#recordID').val();
				var printPath = sysPath + "/jsp/localeSeized/extractRecordPrint.jsp?recordType="+grecordType+"&recordID="+recordID;
				window.open(printPath);
			}
		});
		//选择案件信息
		$('#selectAj').on('click',function(){
			var path=sysPath+'/jsp/propertyInfo/propertyPreAddCaseList.jsp';
			art.dialog.open(path, {
			    title: '选择案件',
			    width: 600,
			    height:500,
			    top:10,
			    // 在open()方法中，init会等待iframe加载完毕后执行
			    init: function () {
			    	var iframe = this.iframe.contentWindow;
			    	var searchForm = iframe.document.getElementById('searchForm');
			    	$(searchForm).submit();  //自动查询
			    },
			    ok: function () {
			    	var iframe = this.iframe.contentWindow;
			    	var table_hover = iframe.document.getElementById('table_hover');
			    	var checkedLen=$(table_hover).find('input[name="caseID"]:checked').length;
			    	if(checkedLen==0) { 
			    		alert('至少选择一个案件');
			    		return false;
			    	}
			    	
			    	var checkTr=$(table_hover).find('input[name="caseID"]:checked').parent().parent();
			    	$('#selCase input[name="caseId"]').val($(checkTr).find('input[name="caseID"]').eq(0).val());
			    	$('#selCase input[name="ecaseId"]').val($(checkTr).children('td').eq(2).html());
			    	$('#selCase input[name="caseName"]').val($(checkTr).children('td').eq(1).html());
			    },
			    cancel: true
			});
		});
	},
	
	//检查输入框的值
	checkInput:function($input){
		var ret=true;
		$input.each(function(idx,el){
			if($(el).attr('need')=='need'){
				var errDiv=null;
				if($(el).attr('name')=='departmentName')
					errDiv=$(el).next().next();
				ret=verifyForm.inputNotNull($(el), $(el).attr('errormsg'),errDiv, null);
				if(!ret)
					return false;
			}
		});
		return ret;
	},
	
	//获取输入框的值
	getInputValOne:function($tr){
		var retArr=[];
		$tr.each(function(idx,el){
			var single={};
			if(idx!=0){
				single.id=Math.uuid();
				single.name=$(el).find('input[name="name"]').val();
				single.departmentID=$(el).find('input[name="departmentID"]').val();
				retArr.push(single);
			}
		});
		return retArr;
	},
	getInputValTwo:function($tr){
		var retArr=[];
		$tr.each(function(idx,el){
			var single={};
			if(idx!=0&&idx%2==1){
				single.id=Math.uuid();
				single.name=$(el).find('input[name="name"]').val();
				single.idType=$(el).find('select[name="idType"]').val();
				single.gender=$(el).next().find('select[name="gender"]').val();
				single.idNum=$(el).next().find('input[name="idNum"]').val();
				retArr.push(single);
			}
		});
		return retArr;
	},
	
	saveRecord:function(){
		var ret=true;
		//校验侦查员
		ret=extractRecordAdd.checkInput($('#zcyTab input'));
		//获取侦查员的值
		var polices=extractRecordAdd.getInputValOne($('#zcyTab tr'));
		
		//校验记录员 不是检查笔录才显示
		var recorders=[];
		if(grecordType!=8){
			ret=extractRecordAdd.checkInput($('#jlTab input'));
			//获取记录员的值
			recorders=extractRecordAdd.getInputValOne($('#jlTab tr'));
		}
		
		//校验当事人
		ret=extractRecordAdd.checkInput($('#dsrTab input'));
		ret=extractRecordAdd.checkInput($('#dsrTab select'));
		//获取当事人的值
		var clients=extractRecordAdd.getInputValTwo($('#dsrTab tr'));
		
		//校验见证人
		ret=extractRecordAdd.checkInput($('#jzrTab input'));
		ret=extractRecordAdd.checkInput($('#jzrTab select'));
		//获取见证人的值
		var witnesses=extractRecordAdd.getInputValTwo($('#jzrTab tr'));
		
		//校验其他在场人员
		ret=extractRecordAdd.checkInput($('#qtzcTab input'));
		ret=extractRecordAdd.checkInput($('#qtzcTab select'));
		//获取其他在场人员
		var personnels=extractRecordAdd.getInputValTwo($('#qtzcTab tr'));
		
		//校验录入时间
		ret=verifyForm.inputNotNull($('#jcxxUl input[name="startTime"]'), '开始时间',null, null);
		ret=verifyForm.inputNotNull($('#jcxxUl input[name="endTime"]'), '结束时间',null, null);
		
		//案件信息
		var caseInfo={};
    	caseInfo.caseID=$('#selCase input[name="caseId"]').val();
    	caseInfo.jzcaseID=$('#selCase input[name="ecaseId"]').val();
    	caseInfo.caseName=$('#selCase input[name="caseName"]').val();
    	if(ret){
    		var startTime=$.trim($('#jcxxUl input[name="startTime"]').val());
    		if(startTime!=''){
    			startTime=startTime.substring(0,4)+startTime.substring(5,7)+startTime.substring(8,10)
    					+startTime.substring(11,13)+startTime.substring(14,16)+startTime.substring(17,19);
    		}
    		var endTime=$.trim($('#jcxxUl input[name="endTime"]').val());
    		if(endTime!=''){
    			endTime=endTime.substring(0,4)+endTime.substring(5,7)+endTime.substring(8,10)
    					+endTime.substring(11,13)+endTime.substring(14,16)+endTime.substring(17,19);
    		}
    		var record={
				'recordID':$('#recordID').val(),
				'type':grecordType,
				'polices':polices,
				'recorders':recorders,
				'clients':clients,
				'witnesses':witnesses,
				'personnels':personnels,
				'case':caseInfo,
				'fromPart':'web',
				'startTime':startTime,
				'endTime':endTime,
				'target':$('#jcxxUl input[name="target"]').val(),
				'place':$('#jcxxUl input[name="place"]').val(),
				'reason':$('#jcxxUl textarea[name="reason"]').val(),
				'result':$('#jcxxUl textarea[name="result"]').val()
			}
			var localUrl=sysPath+'/client/clientRequestJson.do';
			var clientUrl='/record/add.do';
			var params={
				'record':record
			};
			var cb=function(data){
				if(data.state=='Y'){
					alert('添加成功');
				}else{
					alert(data.msg);
				}
			}
			clientAjaxPostSync(localUrl,clientUrl,params,cb);
    	}
    	return ret;
	},
	
	//获取相应行内容
	getRelevantLi:function(type){
		var nli='';
		if('zcy'==type){
			nli='<tr>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span>姓名：</td>'
				+'<td><input name="name" type="text" need="need" errormsg="姓名"/>'
				+'<span></span></td>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span>单位：</td>'
				+'<td>'
				+'<input name="departmentID" type="hidden"/>'
				+'<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位"/>'
				+'<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="extractRecordAdd.selectDepartment(this)"/>'
				+'<span class="formerr"></span>'
				+'</td>'
				+'<td>'
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="extractRecordAdd.deletezcyLi(this)"/>'
				+'</td></tr>';
		}else if('dsr'==type||'jzr'==type||'qtzc'==type){
			nli='<tr>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span>姓名：</td>'
				+'<td><input name="name" type="text" need="need" errormsg="姓名"/>'
				+'<span></span></td>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span>证件类型：</td>'
				+'<td>'
				+'<select name="idType" need="need" errormsg="证件类型">'+$('#idType').html()+'</select>'
				+'<span></span></td>'
				+'<td>'
				+'</td></tr>'
				+'<tr>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span>性别：</td>'
				+'<td>'
				+'<select name="gender" style="height:22px;" need="need" errormsg="性别"><option value="">请选择</option>'
				+'<option value="M">男</option><option value="F">女</option></select>'
				+'<span></span></td>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span>证件号码：</td>'
				+'<td>'
				+'<input name="idNum" type="text" need="need" errormsg="证件号码" style="width:180px;"/>'
				+'<span></span></td>'
				+'<td>'
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="extractRecordAdd.deletedsrLi(this)"/>'
				+'</td></tr>';
		}
		return nli;
	},
	
	//删除当前行
	deletezcyLi:function(obj){
		$(obj).parent().parent().remove();
	},
	
	deletedsrLi:function(obj){
		$(obj).parent().parent().prev().remove();
		$(obj).parent().parent().remove();
	},
	
	//选择部门
	selectDepartment:function(obj){
		var thisObj=$(obj);
		var path=sysPath+'/jsp/plugins/selectDepartment.jsp';
		art.dialog.open(path, {
		    title: '选择部门',
		    width: 400,
		    height:300,
		    top:10,
		    // 在open()方法中，init会等待iframe加载完毕后执行
		    init: function () {
		    	var iframe = this.iframe.contentWindow;
		    	$(iframe.document.getElementById('checked')).val(thisObj.prev().prev().val());
		    	this.iframe.contentWindow.initData();	
		    },
		    ok: function () {
		    	var retData=this.iframe.contentWindow.getCheckedData();	
		    	if(retData==null){
		    		return false;
		    	}else{
		    		var selId=retData.id;
		    		var selName=retData.name;
		    		thisObj.prev().val(selName);
		    		thisObj.prev().prev().val(selId);
		    	}
		    },
		    cancel: true
		});
	},
	
	init:function(){
		extractRecordAdd.startEvent();
		extractRecordAdd.bindEvent();
	}
}
$(document).ready(function() {
	extractRecordAdd.init();
});

//模板
function selectTemplet(obj,type){
	var thisObj=$(obj);
	art.dialog.data('contentType',type);
	var path=sysPath + "/jsp/localeSeized/templetList.jsp";
	art.dialog.open(path, {
	    title: '选择模板',
	    width: 700,
	    height:400,
	    top:10,
	    // 在open()方法中，init会等待iframe加载完毕后执行
	    init: function () {
	    	this.iframe.contentWindow.getTemplet();	
	    },
	    ok: function () {
	    	var ret=this.iframe.contentWindow.getSelData();
	    	if(!ret)
	    		return false;
	    	art.dialog.data('checkedId',ret[0]);
	    	thisObj.parent().prev().val(ret[1]);
	    	$('#resultTxt').autoHeight();
	    },
	    cancel: true
	});
}
var updDepartment=null;
var extractRecordUpdate={
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
		
		extractRecordUpdate.initLoadDepartment();
		extractRecordUpdate.showInfoById();
	},
	
	initLoadDepartment:function(){
		$.ajax({
			type: 'GET',
			async: false,
			url: sysPath+'/account/loadDepartments.do',
			dataType:"json",
			success: function(data){
				updDepartment=data.data;
			}
		});
	},
	
	updGetDeptNameById:function(deptId){
		for(var i=0;i<updDepartment.length;i++){
			if(updDepartment[i].id==deptId)
				return updDepartment[i].name;
		}
		return '';
	},
	
	//获取数据初始化
	showInfoById:function(){
		//笔录数据
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/record/queryDetails.do';
		var params={
			'recordID':$('#recordID').val()
		}
		var cb=function(data){
			if(data.state=='Y'){
				//侦查员，多个
				var record=data.record;
				var polices=record.polices;
				if(polices!=null){
					if(polices.length>1){
						for(var i=1;i<polices.length;i++){
							var nli=extractRecordUpdate.getRelevantLi('zcy');
							$('#zcyTab').append(nli);
						}
					}
					for(var i=0;i<polices.length;i++){
						var sp=polices[i];
						$($('#zcyTab tr').eq(i+1)).find('input[name="id"]').val(sp.id);
						$($('#zcyTab tr').eq(i+1)).find('input[name="name"]').val(sp.name);
						$($('#zcyTab tr').eq(i+1)).find('input[name="departmentID"]').val(sp.departmentID);
						$($('#zcyTab tr').eq(i+1)).find('input[name="departmentName"]').val(extractRecordUpdate.updGetDeptNameById(sp.departmentID));
					}
				}
				//记录员，只有一个
				var recorders=record.recorders;
				if(recorders!=null&&recorders[0]!=null){
					$('#jlTab input[name="id"]').val(recorders[0].id);
					$('#jlTab input[name="name"]').val(recorders[0].name);
					$('#jlTab input[name="departmentID"]').val(recorders[0].departmentID);
					$('#jlTab input[name="departmentName"]').val(extractRecordUpdate.updGetDeptNameById(recorders[0].departmentID));
				}
				//当事人，多个
				var clients=record.clients;
				if(clients!=null){
					if(clients.length>1){
						for(var i=1;i<clients.length;i++){
							var nli=extractRecordUpdate.getRelevantLi('dsr');
							$('#dsrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<clients.length;i++){
						var sp=clients[i];
						$($('#dsrTab tr').eq(idx)).find('input[name="id"]').val(sp.id);
						$($('#dsrTab tr').eq(idx)).find('input[name="name"]').val(sp.name);
						$($('#dsrTab tr').eq(idx)).find('select[name="idType"]').val(sp.idType);
						idx=idx+1;
						$($('#dsrTab tr').eq(idx)).find('select[name="gender"]').val(sp.gender);
						$($('#dsrTab tr').eq(idx)).find('input[name="idNum"]').val(sp.idNum);
						idx=idx+1;
					}
				}
				//见证人，多个
				var witnesses=record.witnesses;
				if(witnesses!=null){
					if(witnesses.length>1){
						for(var i=1;i<witnesses.length;i++){
							var nli=extractRecordUpdate.getRelevantLi('jzr');
							$('#jzrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<witnesses.length;i++){
						var sp=witnesses[i];
						$($('#jzrTab tr').eq(idx)).find('input[name="id"]').val(sp.id);
						$($('#jzrTab tr').eq(idx)).find('input[name="name"]').val(sp.name);
						$($('#jzrTab tr').eq(idx)).find('select[name="idType"]').val(sp.idType);
						idx=idx+1;
						$($('#jzrTab tr').eq(idx)).find('select[name="gender"]').val(sp.gender);
						$($('#jzrTab tr').eq(idx)).find('input[name="idNum"]').val(sp.idNum);
						idx=idx+1;
					}
				}
				//其他在场人员，多个
				var personnels=record.personnels;
				if(personnels!=null){
					if(personnels.length>0){
						for(var i=0;i<personnels.length;i++){
							var nli=extractRecordUpdate.getRelevantLi('qtzc');
							$('#qtzcTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<personnels.length;i++){
						var sp=personnels[i];
						$($('#qtzcTab tr').eq(idx)).find('input[name="id"]').val(sp.id);
						$($('#qtzcTab tr').eq(idx)).find('input[name="name"]').val(sp.name);
						$($('#qtzcTab tr').eq(idx)).find('select[name="idType"]').val(sp.idType);
						idx=idx+1;
						$($('#qtzcTab tr').eq(idx)).find('select[name="gender"]').val(sp.gender);
						$($('#qtzcTab tr').eq(idx)).find('input[name="idNum"]').val(sp.idNum);
						idx=idx+1;
					}
				}
				//案件信息
				var caseInfo=record['case'];
				if(caseInfo!=null){
					$('#selCase input[name="caseId"]').val(caseInfo.caseID);
					$('#selCase input[name="ecaseId"]').val(caseInfo.jzcaseID);
					$('#selCase input[name="caseName"]').val(caseInfo.caseName);
				}
				
				//检查详细信息
				var startTime=record.startTime.substring(0,4)
				+'-'+record.startTime.substring(4,6)
				+'-'+record.startTime.substring(6,8)
				+' '+record.startTime.substring(8,10)
				+':'+record.startTime.substring(10,12)
				+':'+record.startTime.substring(12,14);
				$('#jcxxUl input[name="startTime"]').val(startTime);
				var endTime=record.endTime.substring(0,4)
				+'-'+record.endTime.substring(4,6)
				+'-'+record.endTime.substring(6,8)
				+' '+record.endTime.substring(8,10)
				+':'+record.endTime.substring(10,12)
				+':'+record.endTime.substring(12,14);
				$('#jcxxUl input[name="endTime"]').val(endTime);
				$('#jcxxUl input[name="target"]').val(record.target);
				$('#jcxxUl input[name="place"]').val(record.place);
				$('#jcxxUl textarea[name="reason"]').val(record.reason);
				$('#jcxxUl textarea[name="result"]').val(record.result);
				$('#resultTxt').autoHeight();
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
		
		//抓获过程
		localUrl=sysPath+'/client/clientRequestMap.do';
		clientUrl='/catchProcess/detail.do';
		params={
			'recordID':$('#recordID').val()
		}
		cb=function(data){
			if(data.state=='Y'){
				var catchProcess=data.catchProcess;
				if(catchProcess!=null){
					$('#id').val(catchProcess.id);
					var polices=catchProcess.polices;
					if(polices!=null){
						$('#policeId').val(polices[0].id);
					}
					$('#content').val(catchProcess.content);
				}else{
					$('#id').val(Math.uuid());
					$('#policeId').val(Math.uuid());
				}
			}else{
				$('#policeId').val(Math.uuid());
				$('#id').val(Math.uuid());
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	updateCatchProcessImpl:function(){
		var ret=[];
		var name=$('#name').val();
		var departmentID=$('#departmentID').val();
		var acpId=$('#id').val();
		ret[0]=false;
		ret[1]=acpId;
		
		if($.trim($('#content').val())==''){
			alert('请输入内容');
			return ret;
		}
		var polices=[{
			'id':$('#policeId').val(),
			'name':name,
			'departmentID':departmentID
		}];
		
		var clientUrl='/catchProcess/update.do';
		var catchProcess={
			'id':$('#id').val(),
			'recordType':grecordType,  
			'fromPart':'web',
			'recordID':$('#recordID').val(),
			'type':'28',
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
				alert('修改成功');
			}else{
				ret[0]=false;
				alert(data.msg);
			}
		}
		clientAjaxPostSync(localUrl,clientUrl,params,cb);
		return ret;
	},
	
	bindEvent:function(){
		//打印抓获过程
		$('#printCatchProcess').on('click',function(){
			var ret=extractRecordUpdate.updateCatchProcessImpl();
			if(ret[0]){
				var recordID=$('#recordID').val();
				var printPath = sysPath + "/jsp/localeSeized/extractRecordCatchProcessPrint.jsp?recordType="+grecordType+"&recordID="+recordID;
				window.open(printPath);
			}
		});
		
		//打印笔录
		$('#printRecord').on('click',function(){
			var recordID=$('#recordID').val();
			var printPath = sysPath + "/jsp/localeSeized/extractRecordPrint.jsp?recordType="+grecordType+"&recordID="+recordID;
			window.open(printPath);
		});
		
		//返回
		$('.goBack').on('click',function(){
			window.location.href = sysPath + '/jsp/localeSeized/extractRecord.jsp?type='+type+'&currentIndex='+currentIndex
				+'&sizePerPage='+sizePerPage+'&jzcaseID='+jzcaseID+'&recordType='+grecordType;
		});
		
		//添加抓获过程
		$('#addCatchProcess').on('click',function(){
			extractRecordUpdate.updateCatchProcessImpl();
		});
		
		//侦查员添加
		$('#addzcyBtn').on('click',function(){
			var nli=extractRecordUpdate.getRelevantLi('zcy');
			$('#zcyTab').append(nli);
		});
		//当事人添加
		$('#adddsrBtn').on('click',function(){
			var nli=extractRecordUpdate.getRelevantLi('dsr');
			$('#dsrTab').append(nli);
		});
		//见证人添加
		$('#addjzrBtn').on('click',function(){
			var nli=extractRecordUpdate.getRelevantLi('jzr');
			$('#jzrTab').append(nli);
		});
		//其他在场人员
		$('#addqtzcBtn').on('click',function(){
			var nli=extractRecordUpdate.getRelevantLi('qtzc');
			$('#qtzcTab').append(nli);
		});
		//添加笔录
		$('#addRecord').on('click',function(){
			extractRecordUpdate.saveRecord();
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
				var id='';
				if($(el).find('input[name="id"]').val()==''){
					id=Math.uuid();
				}else{
					id=$(el).find('input[name="id"]').val();
				}
				single.id=id;
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
				var id='';
				if($(el).find('input[name="id"]').val()==''){
					id=Math.uuid();
				}else{
					id=$(el).find('input[name="id"]').val();
				}
				single.id=id;
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
		ret=extractRecordUpdate.checkInput($('#zcyTab input'));
		//获取侦查员的值
		var polices=extractRecordUpdate.getInputValOne($('#zcyTab tr'));
		
		//校验记录员 不是检查笔录才显示
		var recorders=[];
		if(grecordType!=8){
			ret=extractRecordUpdate.checkInput($('#jlTab input'));
			//获取记录员的值
			recorders=extractRecordUpdate.getInputValOne($('#jlTab tr'));
		}
		
		//校验当事人
		ret=extractRecordUpdate.checkInput($('#dsrTab input'));
		ret=extractRecordUpdate.checkInput($('#dsrTab select'));
		//获取当事人的值
		var clients=extractRecordUpdate.getInputValTwo($('#dsrTab tr'));
		
		//校验见证人
		ret=extractRecordUpdate.checkInput($('#jzrTab input'));
		ret=extractRecordUpdate.checkInput($('#jzrTab select'));
		//获取见证人的值
		var witnesses=extractRecordUpdate.getInputValTwo($('#jzrTab tr'));
		
		//校验其他在场人员
		ret=extractRecordUpdate.checkInput($('#qtzcTab input'));
		ret=extractRecordUpdate.checkInput($('#qtzcTab select'));
		//获取其他在场人员
		var personnels=extractRecordUpdate.getInputValTwo($('#qtzcTab tr'));
		
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
			var clientUrl='/record/update.do';
			var params={
				'record':record
			};
			var cb=function(data){
				if(data.state=='Y'){
					alert('修改成功');
				}else{
					alert(data.msg);
				}
			}
			clientAjaxPost(localUrl,clientUrl,params,cb);
    	}
	},
	
	//获取相应行内容
	getRelevantLi:function(type){
		var nli='';
		if('zcy'==type){
			nli='<tr>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span><input name="id" type="hidden"/>姓名：</td>'
				+'<td><input name="name" type="text" need="need" errormsg="姓名"/>'
				+'<span></span></td>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span>单位：</td>'
				+'<td>'
				+'<input name="departmentID" type="hidden"/>'
				+'<input name="departmentName" type="text" disabled="disabled" need="need" errormsg="单位"/>'
				+'<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="extractRecordUpdate.selectDepartment(this)"/>'
				+'<span class="formerr"></span>'
				+'</td>'
				+'<td>'
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="extractRecordUpdate.deletezcyLi(this)"/>'
				+'</td></tr>';
		}else if('dsr'==type||'jzr'==type||'qtzc'==type){
			nli='<tr>'
				+'<td style="text-align: right;">'
				+'<span class="need">*</span><input name="id" type="hidden"/>姓名：</td>'
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
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="extractRecordUpdate.deletedsrLi(this)"/>'
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
		extractRecordUpdate.startEvent();
		extractRecordUpdate.bindEvent();
	}
}
$(document).ready(function() {
	extractRecordUpdate.init();
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
var updDepartment=null;
var extractRecordLook={
	startEvent:function(){
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
		//检查笔录 特殊处理
		if(grecordType==8){
			$('#zcyTxt').html('民警');
			$('#jlTab').css('display','none');
			$('#qtzcTab').css('display','none');
		}
		
		$("#recordTab ul").idTabs();
		extractRecordLook.initLoadDepartment();
		extractRecordLook.showInfoById();
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
	
	//返回性别
	getGenderById:function(gender){
		return gender=gender=='M'?'男':'女';
	},
	
	//返回证件类型
	getIdTypeById:function(gender){
		var ret=$('#selectDataDiv select[name="idType"] option[value="'+gender+'"]').text();
		return ret;
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
							var nli=extractRecordLook.getRelevantLi('zcy');
							$('#zcyTab').append(nli);
						}
					}
					for(var i=0;i<polices.length;i++){
						var sp=polices[i];
						$($('#zcyTab tr').eq(i+1)).find('td[name="name"]').html(sp.name);
						$($('#zcyTab tr').eq(i+1)).find('td[name="departmentName"]').html(extractRecordLook.updGetDeptNameById(sp.departmentID));
					}
				}
				//记录员，只有一个
				var recorders=record.recorders;
				if(recorders!=null&&recorders[0]!=null){
					$('#jlTab td[name="name"]').html(recorders[0].name);
					$('#jlTab td[name="departmentName"]').html(extractRecordLook.updGetDeptNameById(recorders[0].departmentID));
				}
				//当事人，多个
				var clients=record.clients;
				if(clients!=null){
					if(clients.length>1){
						for(var i=1;i<clients.length;i++){
							var nli=extractRecordLook.getRelevantLi('dsr');
							$('#dsrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<clients.length;i++){
						var sp=clients[i];
						$($('#dsrTab tr').eq(idx)).find('td[name="name"]').html(sp.name);
						$($('#dsrTab tr').eq(idx)).find('td[name="idType"]').html(extractRecordLook.getIdTypeById(sp.idType));
						idx=idx+1;
						$($('#dsrTab tr').eq(idx)).find('td[name="gender"]').html(extractRecordLook.getGenderById(sp.gender));
						$($('#dsrTab tr').eq(idx)).find('td[name="idNum"]').html(sp.idNum);
						idx=idx+1;
					}
				}
				//见证人，多个
				var witnesses=record.witnesses;
				if(witnesses!=null){
					if(witnesses.length>1){
						for(var i=1;i<witnesses.length;i++){
							var nli=extractRecordLook.getRelevantLi('jzr');
							$('#jzrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<witnesses.length;i++){
						var sp=witnesses[i];
						$($('#jzrTab tr').eq(idx)).find('td[name="name"]').html(sp.name);
						$($('#jzrTab tr').eq(idx)).find('td[name="idType"]').html(extractRecordLook.getIdTypeById(sp.idType));
						idx=idx+1;
						$($('#jzrTab tr').eq(idx)).find('td[name="gender"]').html(extractRecordLook.getGenderById(sp.gender));
						$($('#jzrTab tr').eq(idx)).find('td[name="idNum"]').html(sp.idNum);
						idx=idx+1;
					}
				}
				//其他在场人员，多个
				var personnels=record.personnels;
				if(personnels!=null){
					if(personnels.length>0){
						for(var i=0;i<personnels.length;i++){
							var nli=extractRecordLook.getRelevantLi('qtzc');
							$('#qtzcTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<personnels.length;i++){
						var sp=personnels[i];
						$($('#qtzcTab tr').eq(idx)).find('td[name="name"]').html(sp.name);
						$($('#qtzcTab tr').eq(idx)).find('td[name="idType"]').html(extractRecordLook.getIdTypeById(sp.idType));
						idx=idx+1;
						$($('#qtzcTab tr').eq(idx)).find('td[name="gender"]').html(extractRecordLook.getGenderById(sp.gender));
						$($('#qtzcTab tr').eq(idx)).find('td[name="idNum"]').html(sp.idNum);
						idx=idx+1;
					}
				}
				//案件信息
				var caseInfo=record['case'];
				if(caseInfo!=null){
					$('#ajglTab label[name="caseName"]').html(caseInfo.caseName);
				}
				
				//检查详细信息
				var startTime=record.startTime.substring(0,4)
				+'-'+record.startTime.substring(4,6)
				+'-'+record.startTime.substring(6,8)
				+' '+record.startTime.substring(8,10)
				+':'+record.startTime.substring(10,12)
				+':'+record.startTime.substring(12,14);
				$('#jcxxUl span[name="startTime"]').html(startTime);
				var endTime=record.endTime.substring(0,4)
				+'-'+record.endTime.substring(4,6)
				+'-'+record.endTime.substring(6,8)
				+' '+record.endTime.substring(8,10)
				+':'+record.endTime.substring(10,12)
				+':'+record.endTime.substring(12,14);
				$('#jcxxUl span[name="endTime"]').html(endTime);
				$('#jcxxUl span[name="target"]').html(record.target);
				$('#jcxxUl span[name="place"]').html(record.place);
				$('#jcxxUl div[name="reason"]').html(record.reason);
				$('#jcxxUl div[name="result"]').html(record.result);
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
					$('#content').html(catchProcess.content);
				}
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	bindEvent:function(){
		//打印抓获过程
		$('#printCatchProcess').on('click',function(){
			var recordID=$('#recordID').val();
			var printPath = sysPath + "/jsp/localeSeized/extractRecordCatchProcessPrint.jsp?recordType="+grecordType+"&recordID="+recordID;
			window.open(printPath);
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
	},
	
	//获取相应行内容
	getRelevantLi:function(type){
		var nli='';
		if('zcy'==type){
			nli='<tr>'
				+'<td style="text-align: right;">姓名：</td>'
				+'<td name="name"></td>'
				+'<td style="text-align: right;">单位：</td>'
				+'<td name="departmentName">'
				+'</td>'
				+'<td>'
				+'</td></tr>';
		}else if('dsr'==type||'jzr'==type||'qtzc'==type){
			nli='<tr>'
				+'<td style="text-align: right;">姓名：</td>'
				+'<td name="name"></td>'
				+'<td style="text-align: right;">证件类型：</td>'
				+'<td name="idType"></td>'
				+'<td>'
				+'</td></tr>'
				+'<tr>'
				+'<td style="text-align: right;">性别：</td>'
				+'<td name="gender"></td>'
				+'<td style="text-align: right;">证件号码：</td>'
				+'<td name="idNum"></td>'
				+'<td>'
				+'</td></tr>';
		}
		return nli;
	},
	
	init:function(){
		extractRecordLook.startEvent();
		extractRecordLook.bindEvent();
	}
}
$(document).ready(function() {
	extractRecordLook.init();
});

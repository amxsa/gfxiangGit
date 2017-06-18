var updDepartment=null;
var evidenceRecordLook={
	proArr:[],
	startEvent:function(){
		$("#recordTab ul").idTabs();
		evidenceRecordLook.initLoadDepartment();
		evidenceRecordLook.showInfoById();
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
	
	//修改
	updateProperty:function(thisObj){
		//修改清单财物
		var id=$(thisObj).parents("tr").find('input[name="id"]').val();
		var ret=null;
		for(var i =0; i < evidenceRecordLook.proArr.length; i++){
			if(evidenceRecordLook.proArr[i].id === id){
				ret=evidenceRecordLook.proArr[i];
			}
		}
		art.dialog.data('ret',ret);
		var path=sysPath+'/jsp/localeSeized/evidenceRecord/evidenceRecordLookProperty.jsp';
		art.dialog.open(path, {
		    title: '查看财物',
		    width: 860,
		    height:280,
		    top:10,
		    init:function(){
		    	this.iframe.contentWindow.initProperty();
		    },
		    cancel: true
		});
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
		var clientUrl='/extractRecord/queryDetail.do';
		var params={
			'extractID':$('#recordID').val()
		}
		var cb=function(data){
			if(data.state=='Y'){
				//侦查员，多个
				var record=data.extractRecord;
				var polices=record.polices;
				setPoliceData('#zcyTab', 'zcy', polices);
				//保管人
				var keepers = record.keepers;
				setPoliceData('#bgrTab', 'bgr', keepers);
				
				//持有人
				var owners=record.owners;
				setCivilianData('#cyrTab', 'cyr', owners);
				
				//见证人，多个
				var witnesses=record.witnesses;
				setCivilianData('#jzrTab', 'jzr', witnesses);
				
				//显示财物
				if(record.properties!=null&&record.properties.length>0){
					evidenceRecordLook.proArr=record.properties;
					var tr='';
					for(var i=0;i<record.properties.length;i++){
						var ret=record.properties[i];
						tr=tr+'<tr><input name="id" value="'+ret.id+'" type="hidden"/>'
				    	+'<td>'+ret.name+'</td>'
				    	+'<td>'+ret.quantity+'</td>'
				    	+'<td>'+ret.characteristic+'</td>'
				    	+'<td><span class="tdbut"><a href="javascript:void(0)" onclick="evidenceRecordLook.updateProperty(this)" style="cursor: pointer;width:50px;margin-right:10px;">查看</a>'
				    	+'</span></td></tr>';
					}
			    	$('#table_hover tbody').append(tr);
				}
				
				//案件信息
				var caseInfo=record['case'];
				if(caseInfo!=null){
					$('#ajglTab td[name="jzcaseID"]').html(caseInfo.jzcaseID);
					$('#ajglTab td[name="caseName"]').html(caseInfo.caseName);
				}
				
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
		
	},
	
	bindEvent:function(){
		//返回
		$('.goBack').on('click',function(){
			window.location.href = sysPath + '/jsp/localeSeized/evidenceRecord/evidenceRecord.jsp?type='+type+'&currentIndex='+currentIndex
				+'&sizePerPage='+sizePerPage+'&jzcaseID='+jzcaseID+'&recordType='+grecordType;
		});
	},
	
	//获取相应行内容
	getRelevantLi:function(type){
		var nli='';
		if('zcy'==type || 'bgr' === type ){
			nli='<tr>'
				+'<td style="text-align: right;">姓名：</td>'
				+'<td name="name"></td>'
				+'<td style="text-align: right;">单位：</td>'
				+'<td name="departmentName">'
				+'</td>'
				+'<td>'
				+'</td></tr>';
		}else if('jzr'=== type || 'cyr' === type){
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
		if(grecordType==='12'){
			$("#recordTxt").text('查看提取物证登记表');
			$(".h1tit").text('查看提取物证登记表');
		}else{
			$("#recordTxt").text('查看调取物证清单');
			$(".h1tit").text('查看调取物证清单');
		}
		evidenceRecordLook.startEvent();
		evidenceRecordLook.bindEvent();
	}
}
$(document).ready(function() {
	evidenceRecordLook.init();
});

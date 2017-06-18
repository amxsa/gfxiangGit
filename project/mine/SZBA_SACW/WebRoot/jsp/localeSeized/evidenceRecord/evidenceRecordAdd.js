var updDepartment=null;

var evidenceRecordAdd={
	proArr:[],
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
	
	bindEvent:function(){
		//民警添加
		$('#addzcyBtn').on('click',function(){
			var nli=evidenceRecordAdd.getRelevantLi('zcy');
			$('#zcyTab').append(nli);
		});
		
		//见证人添加
		$('#addjzrBtn').on('click',function(){
			var nli=evidenceRecordAdd.getRelevantLi('jzr');
			$('#jzrTab').append(nli);
		});
		//持有人添加
		$('#addcyrBtn').on('click',function(){
			var nli=evidenceRecordAdd.getRelevantLi('cyr');
			$('#cyrTab').append(nli);
		});
		//保管人添加
		$('#addbgrBtn').on('click',function(){
			var nli=evidenceRecordAdd.getRelevantLi('bgr');
			$('#bgrTab').append(nli);
		});
		
		//添加笔录
		$('#addRecord').on('click',function(){
			evidenceRecordAdd.saveRecord();
		});
		
		//返回
		$('.goBack').on('click',function(){
			window.location.href = sysPath + '/jsp/localeSeized/evidenceRecord/evidenceRecord.jsp?type='+type+'&currentIndex='+currentIndex
				+'&sizePerPage='+sizePerPage+'&jzcaseID='+jzcaseID+'&recordType='+grecordType;
		});
		
		//添加财物
		$('#addBillProperty').on('click',function(){
			var path=sysPath+'/jsp/localeSeized/evidenceRecord/evidenceRecordAddProperty.jsp?recordType='+grecordType;
			art.dialog.open(path, {
			    title: '添加财物',
			    width: 860,
			    height:440,
			    top:10,
			    ok: function () {
			    	var ret=this.iframe.contentWindow.saveProperty();
			    	if(!ret)
			    		return false;
			    	var tr='<tr><input name="id" value="'+ret.id+'" type="hidden"/>'
			    	+'<td>'+ret.name+'</td>'
			    	+'<td>'+ret.quantity+'</td>'
			    	+'<td>'+ret.characteristic+'</td>'
			    	+'<td><input type="button" value="修改" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordAdd.updateProperty(this)"/>	'
			    	+'<input type="button" value="删除" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordAdd.deleteProperty(this)"/></td></tr>';
			    	evidenceRecordAdd.proArr.push(ret);
			    	$('#table_hover tbody').append(tr);
			    },
			    cancel: true
			});
		});
		
		//选择案件信息
		$('#selectAj').on('click',function(){
			var path=sysPath+'/jsp/propertyInfo/propertyPreAddCaseList.jsp';
			art.dialog.open(path, {
			    title: '选择案件',
			    width: 600,
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
			    	$('#ajglTab input[name="caseId"]').val($(checkTr).find('input[name="caseID"]').eq(0).val());
			    	$('#ajglTab input[name="ecaseId"]').val($(checkTr).children('td').eq(2).html());
			    	$('#ajglTab span[name="caseName"]').text($(checkTr).children('td').eq(1).html());
			    },
			    cancel: true
			});
		});
	},
	
	//获取相应行内容
	getRelevantLi:function(type){
		var nli='';
		if('zcy'==type || 'bgr' === type){
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
				+'<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="evidenceRecordAdd.selectDepartment(this)"/>'
				+'<span class="formerr"></span>'
				+'</td>'
				+'<td>'
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="evidenceRecordAdd.deletezcyLi(this)"/>'
				+'</td></tr>';
		}else if('jzr'=== type || 'cyr' === type){
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
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="evidenceRecordAdd.deletedsrLi(this)"/>'
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
	
	//修改
	updateProperty:function(thisObj){
		//修改清单财物
		var ttr=$(thisObj).parent().parent();
		var id=$(thisObj).parent().parent().find('input[name="id"]').val();
		var ret=null;
		for(var i =0; i < evidenceRecordAdd.proArr.length; i++){
			if(evidenceRecordAdd.proArr[i].id === id){
				ret=evidenceRecordAdd.proArr[i];
			}
		}
		art.dialog.data('ret',ret);
		var path=sysPath+'/jsp/localeSeized/evidenceRecord/evidenceRecordUpdateProperty.jsp?recordType='+grecordType;
		art.dialog.open(path, {
		    title: '修改财物',
		    width: 860,
		    height:380,
		    top:10,
		    init:function(){
		    	this.iframe.contentWindow.initProperty();
		    },
		    ok: function () {
		    	var ret=this.iframe.contentWindow.saveProperty();
		    	if(!ret)
		    		return false;
		    	ttr.find('input[name="ret"]').val(ret);
		    	ttr.find('td').eq(0).html(ret.name);
		    	ttr.find('td').eq(1).html(ret.quantity);
		    	ttr.find('td').eq(2).html(ret.characteristic);
		    	for(var i =0; i < evidenceRecordAdd.proArr.length; i++){
					if(evidenceRecordAdd.proArr[i].id == id){
						evidenceRecordAdd.proArr[i].name=ret.name;
						evidenceRecordAdd.proArr[i].quantity=ret.quantity;
						evidenceRecordAdd.proArr[i].characteristic=ret.characteristic;
						evidenceRecordAdd.proArr[i].unit=ret.unit;
						evidenceRecordAdd.proArr[i].remark=ret.remark;
						
					}
				}
		    },
		    cancel: true
		});
	},
	
	//删除
	deleteProperty:function(thisObj){
		var id=$(thisObj).parent().parent().find('input[name="id"]').val();
		for(var i =0; i < evidenceRecordAdd.proArr.length; i++){
			if(evidenceRecordAdd.proArr[i].id === id){
				evidenceRecordAdd.proArr.splice(i, 1);
			}
		}
		$(thisObj).parent().parent().remove();
	},
	
	/*//修改，初始化数据
	initDataById:function(listID){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/detailedList/queryDetails.do';
		var params={
			'listID':listID
		}
		var cb=function(data){
			if(data.state=='Y'){
				var bill=data.detailedList;
				
				//民警，多个
				var polices=bill.polices;
				if(polices!=null){
					if(polices.length>1){
						for(var i=1;i<polices.length;i++){
							var nli=evidenceRecordAdd.getRelevantLi('mj');
							$('#mjTab').append(nli);
						}
					}
					for(var i=0;i<polices.length;i++){
						var sp=polices[i];
						$($('#mjTab tr').eq(i+1)).find('input[name="name"]').val(sp.name);
						$($('#mjTab tr').eq(i+1)).find('input[name="departmentID"]').val(sp.departmentID);
						$($('#mjTab tr').eq(i+1)).find('input[name="departmentName"]').val(evidenceRecordAdd.updGetDeptNameById(sp.departmentID));
					}
				}
				//见证人，多个
				var witnesses=bill.witnesses;
				if(witnesses!=null){
					if(witnesses.length>1){
						for(var i=1;i<witnesses.length;i++){
							var nli=evidenceRecordAdd.getRelevantLi('jzr');
							$('#jzrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<witnesses.length;i++){
						var sp=witnesses[i];
						$($('#jzrTab tr').eq(idx)).find('input[name="name"]').val(sp.name);
						$($('#jzrTab tr').eq(idx)).find('select[name="idType"]').val(sp.idType);
						idx=idx+1;
						$($('#jzrTab tr').eq(idx)).find('select[name="gender"]').val(sp.gender);
						$($('#jzrTab tr').eq(idx)).find('input[name="idNum"]').val(sp.idNum);
						idx=idx+1;
					}
				}
				//显示财物
				if(bill.properties!=null&&bill.properties.length>0){
					evidenceRecordAdd.proArr=bill.properties;
					var tr='';
					for(var i=0;i<bill.properties.length;i++){
						var ret=bill.properties[i];
						tr=tr+'<tr><input name="id" value="'+ret.id+'" type="hidden"/>'
				    	+'<td>'+ret.name+'</td>'
				    	+'<td>'+ret.quantity+'</td>'
				    	+'<td>'+ret.characteristic+'</td>'
				    	+'<td><input type="button" value="修改" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordAdd.updateProperty(this)"/>	'
				    	+'<input type="button" value="删除" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordAdd.deleteProperty(this)"/></td></tr>';
					}
			    	$('#table_hover tbody').append(tr);
				}
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},*/
	saveRecord:function(){
		var ret=true;
		//校验提取人
		ret=evidenceRecordAdd.checkInput($('#zcyTab input'));
		//获取提取人的值
		var polices=evidenceRecordAdd.getInputValOne($('#zcyTab tr'));
		var witnesses = [];
		var owners = [];
		var keepers = [];
		if('12' === grecordType){
			//校验见证人
			ret=evidenceRecordAdd.checkInput($('#jzrTab input'));
			//获取见证人的值
			witnesses=evidenceRecordAdd.getInputValTwo($('#jzrTab tr'));
				
		}else if('27' === grecordType){
			//持有人
			ret = evidenceRecordAdd.checkInput($('#cyrTab input'));
			owners = evidenceRecordAdd.getInputValTwo($('#cyrTab tr'));
			
			//保管人
			ret = evidenceRecordAdd.checkInput($('#bgrTab input'));
			keepers = evidenceRecordAdd.getInputValOne($('#bgrTab tr'));
		}
		
		//案件信息
		var caseInfo={};
    	caseInfo.caseID=$('#ajglTab input[name="caseId"]').val();
    	caseInfo.jzcaseID=$('#ajglTab input[name="ecaseId"]').val();
    	caseInfo.caseName=$('#ajglTab span[name="caseName"]').text();
		var record={
			'extractID':$('#recordID').val(),
			'type':grecordType,
			'polices':polices,
			'witnesses':witnesses,
			'owners':owners,
			'keepers':keepers,
			'case':caseInfo,
			'fromPart':'web',
			'properties':evidenceRecordAdd.proArr
		}
		var localUrl=sysPath+'/client/clientRequestJson.do';
		var clientUrl='/extractRecord/add.do';
		var params={
			'extractRecord':record
		};
		var cb=function(data){
			if(data.state=='Y'){
				alert('添加成功');
			}else{
				alert(data.msg);
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
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
	
	init:function(){
		if(grecordType==='12'){
			$("#recordTxt").text('添加提取物证登记表');
			$(".h1tit").text('添加提取物证登记表');
		}else{
			$("#recordTxt").text('添加调取物证清单');
			$(".h1tit").text('添加调取物证清单');
		}
		evidenceRecordAdd.bindEvent();
		//先查询部门数据
		evidenceRecordAdd.initLoadDepartment();
		/*//有值，则为修改
		var listID=art.dialog.data('listID');
		if(listID!=null){
			//先查询部门数据
			evidenceRecordAdd.initLoadDepartment();
			evidenceRecordAdd.initDataById(listID);
		}*/
	}
}
$(document).ready(function() {
	evidenceRecordAdd.init();
});
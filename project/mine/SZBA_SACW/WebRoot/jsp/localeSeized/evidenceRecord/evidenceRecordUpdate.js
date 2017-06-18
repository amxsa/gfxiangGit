var updDepartment=null;
var evidenceRecordUpdate={
	proArr:[],
	startEvent:function(){
		if(grecordType==='12'){
			$("#recordTxt").text('修改提取物证登记表');
			$(".h1tit").text('修改提取物证登记表');
		}else{
			$("#recordTxt").text('修改调取物证清单');
			$(".h1tit").text('修改调取物证清单');
		}
		
		$("#recordTab ul").idTabs();
		evidenceRecordUpdate.initLoadDepartment();
		evidenceRecordUpdate.showInfoById();
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
		var clientUrl='/extractRecord/queryDetail.do';
		var params={
			'extractID':$('#recordID').val()
		}
		var cb=function(data){
			if(data.state=='Y'){
				//提取人，多个
				var record=data.extractRecord;
				var polices=record.polices;
				if(polices!=null){
					if(polices.length>1){
						for(var i=1;i<polices.length;i++){
							var nli=evidenceRecordUpdate.getRelevantLi('zcy');
							$('#zcyTab').append(nli);
						}
					}
					for(var i=0;i<polices.length;i++){
						var sp=polices[i];
						$($('#zcyTab tr').eq(i+1)).find('input[name="id"]').val(sp.id);
						$($('#zcyTab tr').eq(i+1)).find('input[name="name"]').val(sp.name);
						$($('#zcyTab tr').eq(i+1)).find('input[name="departmentID"]').val(sp.departmentID);
						$($('#zcyTab tr').eq(i+1)).find('input[name="departmentName"]').val(evidenceRecordUpdate.updGetDeptNameById(sp.departmentID));
					}
				}
				var keepers=record.keepers;
				if(keepers!=null){
					if(keepers.length>1){
						for(var i=1;i<keepers.length;i++){
							var nli=evidenceRecordUpdate.getRelevantLi('bgr');
							$('#bgrTab').append(nli);
						}
					}
					for(var i=0;i<keepers.length;i++){
						var sp=keepers[i];
						$($('#bgrTab tr').eq(i+1)).find('input[name="id"]').val(sp.id);
						$($('#bgrTab tr').eq(i+1)).find('input[name="name"]').val(sp.name);
						$($('#bgrTab tr').eq(i+1)).find('input[name="departmentID"]').val(sp.departmentID);
						$($('#bgrTab tr').eq(i+1)).find('input[name="departmentName"]').val(evidenceRecordUpdate.updGetDeptNameById(sp.departmentID));
					}
				}
				
				//见证人，多个
				var witnesses=record.witnesses;
				if(witnesses!=null){
					if(witnesses.length>1){
						for(var i=1;i<witnesses.length;i++){
							var nli=evidenceRecordUpdate.getRelevantLi('jzr');
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
				var owners=record.owners;
				if(owners!=null){
					if(owners.length>1){
						for(var i=1;i<owners.length;i++){
							var nli=evidenceRecordUpdate.getRelevantLi('cyr');
							$('#cyrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<owners.length;i++){
						var sp=owners[i];
						$($('#cyrTab tr').eq(idx)).find('input[name="id"]').val(sp.id);
						$($('#cyrTab tr').eq(idx)).find('input[name="name"]').val(sp.name);
						$($('#cyrTab tr').eq(idx)).find('select[name="idType"]').val(sp.idType);
						idx=idx+1;
						$($('#cyrTab tr').eq(idx)).find('select[name="gender"]').val(sp.gender);
						$($('#cyrTab tr').eq(idx)).find('input[name="idNum"]').val(sp.idNum);
						idx=idx+1;
					}
				}
				//显示财物
				if(record.properties!=null&&record.properties.length>0){
					evidenceRecordUpdate.proArr=record.properties;
					var tr='';
					for(var i=0;i<record.properties.length;i++){
						var ret=record.properties[i];
						tr=tr+'<tr><input name="id" value="'+ret.id+'" type="hidden"/>'
				    	+'<td>'+ret.name+'</td>'
				    	+'<td>'+ret.quantity+'</td>'
				    	+'<td>'+ret.characteristic+'</td>'
				    	+'<td><span class="tdbut"><a href="javascript:void(0)" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordUpdate.updateProperty(this)">修改</a>	'
				    	+'<a href="javascript:void(0)" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordUpdate.deleteProperty(this)">删除</a></span></td></tr>';
					}
			    	$('#table_hover tbody').append(tr);
				}
				//案件信息
				var caseInfo=record['case'];
				if(caseInfo!=null){
					$('#ajglTab input[name="caseId"]').val(caseInfo.caseID);
					$('#ajglTab input[name="ecaseId"]').val(caseInfo.jzcaseID);
					$('#ajglTab input[name="caseName"]').val(caseInfo.caseName);
				}
				
				
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	//删除
	deleteProperty:function(thisObj){
		var id=$(thisObj).parents("tr").find('input[name="id"]').val();
		for(var i =0; i < evidenceRecordUpdate.proArr.length; i++){
			if(evidenceRecordUpdate.proArr[i].id === id){
				evidenceRecordUpdate.proArr.splice(i, 1);
			}
		}
		$(thisObj).parents("tr").remove();
	},
	bindEvent:function(){
		//返回
		$('.goBack').on('click',function(){
			window.location.href = sysPath + '/jsp/localeSeized/evidenceRecord/evidenceRecord.jsp?type='+type+'&currentIndex='+currentIndex
				+'&sizePerPage='+sizePerPage+'&jzcaseID='+jzcaseID+'&recordType='+grecordType;
		});
		
		//提取人添加
		$('#addzcyBtn').on('click',function(){
			var nli=evidenceRecordUpdate.getRelevantLi('zcy');
			$('#zcyTab').append(nli);
		});
		//见证人添加
		$('#addjzrBtn').on('click',function(){
			var nli=evidenceRecordUpdate.getRelevantLi('jzr');
			$('#jzrTab').append(nli);
		});
		//添加笔录
		$('#addRecord').on('click',function(){
			evidenceRecordUpdate.saveRecord();
		});
		//添加清单财物
		$('#addBillProperty').on('click',function(){
			var path=sysPath+'/jsp/localeSeized/evidenceRecord/evidenceRecordAddProperty.jsp';
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
			    	+'<td><span class="tdbut"><a href="javascript:void(0)" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordUpdate.updateProperty(this)">修改</a>	'
			    	+'<a href="javascript:void(0)" style="cursor: pointer;width:50px;margin-right:10px;" onclick="evidenceRecordUpdate.deleteProperty(this)">删除</a></span></td></tr>';
			    	evidenceRecordUpdate.proArr.push(ret);
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
			    	$('#ajglTab input[name="caseId"]').val($(checkTr).find('input[name="caseID"]').eq(0).val());
			    	$('#ajglTab input[name="ecaseId"]').val($(checkTr).children('td').eq(2).html());
			    	$('#ajglTab input[name="caseName"]').val($(checkTr).children('td').eq(1).html());
			    	$('#ajglTab input[name="caseType"]').val($.trim($(checkTr).children('td').eq(3).html()));
			    	$('#ajglTab input[name="leader"]').val($(checkTr).children('td').eq(4).html());
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
		var polices = [];
		var keepers = [];
		var witnesses = [];
		var owners = [];
		
		//校验民警
		ret=evidenceRecordUpdate.checkInput($('#zcyTab input'));
		//获取民警的值
		polices=evidenceRecordUpdate.getInputValOne($('#zcyTab tr'));
		
		if('12' === grecordType){
			//校验见证人
			ret=evidenceRecordUpdate.checkInput($('#jzrTab input'));
			ret=evidenceRecordUpdate.checkInput($('#jzrTab select'));
			//获取见证人的值
			witnesses=evidenceRecordUpdate.getInputValTwo($('#jzrTab tr'));
		}else if('27' === grecordType){
			//校验保管人
			ret=evidenceRecordUpdate.checkInput($('#bgrTab input'));
			//获取保管人的值
			keepers=evidenceRecordUpdate.getInputValOne($('#bgrTab tr'));
			
			//校验持有人
			ret=evidenceRecordUpdate.checkInput($('#cyrTab input'));
			ret=evidenceRecordUpdate.checkInput($('#cyrTab select'));
			//获取持有人的值
			owners=evidenceRecordUpdate.getInputValTwo($('#cyrTab tr'));
		}
		
		
		//案件信息
		var caseInfo={};
    	caseInfo.caseID=$('#ajglTab input[name="caseId"]').val();
    	caseInfo.jzcaseID=$('#ajglTab input[name="ecaseId"]').val();
    	caseInfo.caseName=$('#ajglTab input[name="caseName"]').val();
		var record={
			'extractID':$('#recordID').val(),
			'type':grecordType,
			'polices':polices,
			'witnesses':witnesses,
			'keepers':keepers,
			'owners':owners,
			'case':caseInfo,
			'fromPart':'web',
			'properties':evidenceRecordUpdate.proArr
		}
		var localUrl=sysPath+'/client/clientRequestJson.do';
		var clientUrl='/extractRecord/update.do';
		var params={
			'extractRecord':record
		};
		var cb=function(data){
			if(data.state=='Y'){
				alert('修改成功');
			}else{
				alert(data.msg);
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	//修改
	updateProperty:function(thisObj){
		//修改清单财物
		var ttr=$(thisObj).parents("tr");
		var id=$(thisObj).parents("tr").find('input[name="id"]').val();
		var ret=null;
		for(var i =0; i < evidenceRecordUpdate.proArr.length; i++){
			if(evidenceRecordUpdate.proArr[i].id === id){
				ret=evidenceRecordUpdate.proArr[i];
			}
		}
		art.dialog.data('ret',ret);
		var path=sysPath+'/jsp/localeSeized/evidenceRecord/evidenceRecordUpdateProperty.jsp';
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
		    	for(var i =0; i < evidenceRecordUpdate.proArr.length; i++){
					if(evidenceRecordUpdate.proArr[i].id == id){
						evidenceRecordUpdate.proArr[i].name=ret.name;
						evidenceRecordUpdate.proArr[i].quantity=ret.quantity;
						evidenceRecordUpdate.proArr[i].characteristic=ret.characteristic;
						evidenceRecordUpdate.proArr[i].position=ret.position;
						evidenceRecordUpdate.proArr[i].method=ret.method;
						evidenceRecordUpdate.proArr[i].unit=ret.unit;
						evidenceRecordUpdate.proArr[i].remark=ret.remark;
						evidenceRecordUpdate.proArr[i].photographs=ret.photographs;
						
					}
				}
		    },
		    cancel: true
		});
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
				+'<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="evidenceRecordUpdate.selectDepartment(this)"/>'
				+'<span class="formerr"></span>'
				+'</td>'
				+'<td>'
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="evidenceRecordUpdate.deletezcyLi(this)"/>'
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
				+'<input name="idNum" type="text" need="need" errormsg="证件号码"/>'
				+'<span></span></td>'
				+'<td>'
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="evidenceRecordUpdate.deletedsrLi(this)"/>'
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
		evidenceRecordUpdate.startEvent();
		evidenceRecordUpdate.bindEvent();
	}
}
$(document).ready(function() {
	evidenceRecordUpdate.init();
});
var updDepartment=null;
var plistID=null;
var extractRecordBillAdd={
	proArr:[],
	
	startEvent:function(){
		//检查笔录 特殊处理
		if(grecordType==8){
			$('#cyrTab').css('display','none');
			$('#dsrTab').css('display','');
		}
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
		//打印清单
		$('#printBill').on('click',function(){
			var ret=saveBillImpl(oType,caseInfo);
			if(ret){
				alert('保存成功');
				var listID=plistID;
				var printPath = sysPath + "/jsp/localeSeized/extractRecordBillPrint.jsp?listID="+listID+'&recordType='+grecordType;
				window.open(printPath);
			}
		});
		
		//民警添加
		$('#addmjBtn').on('click',function(){
			var nli=extractRecordBillAdd.getRelevantLi('mj');
			$('#mjTab').append(nli);
		});
		
		//当事人添加
		$('#adddsrBtn').on('click',function(){
			var nli=extractRecordBillAdd.getRelevantLi('dsr');
			$('#dsrTab').append(nli);
		});
		
		//见证人添加
		$('#addjzrBtn').on('click',function(){
			var nli=extractRecordBillAdd.getRelevantLi('jzr');
			$('#jzrTab').append(nli);
		});
		
		//添加清单财物
		$('#addBillProperty').on('click',function(){
			var path=sysPath+'/jsp/localeSeized/extractRecordBillAddProperty.jsp?recordType='+grecordType;
			art.dialog.open(path, {
			    title: '添加清单财物',
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
			    	+'<td><input type="button" value="修改" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordBillAdd.updateProperty(this)"/>	'
			    	+'<input type="button" value="删除" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordBillAdd.deleteProperty(this)"/></td></tr>';
			    	extractRecordBillAdd.proArr.push(ret);
			    	$('#table_hover tbody').append(tr);
			    },
			    cancel: true
			});
		});
	},
	
	//获取相应行内容
	getRelevantLi:function(type){
		var nli='';
		if('mj'==type){
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
				+'<input type="button" value="选择" style="width:50px;" class="selectDepartment" onclick="extractRecordBillAdd.selectDepartment(this)"/>'
				+'<span class="formerr"></span>'
				+'</td>'
				+'<td>'
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="extractRecordBillAdd.deletezcyLi(this)"/>'
				+'</td></tr>';
		}else if('jzr'==type||'dsr'==type){
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
				+'<input type="button" value="删除" style="cursor: pointer;width:50px;float:right;margin-right:10px;" onclick="extractRecordBillAdd.deletedsrLi(this)"/>'
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
		for(var i =0; i < extractRecordBillAdd.proArr.length; i++){
			if(extractRecordBillAdd.proArr[i].id === id){
				ret=extractRecordBillAdd.proArr[i];
			}
		}
		art.dialog.data('ret',ret);
		var path=sysPath+'/jsp/localeSeized/extractRecordBillUpdateProperty.jsp?recordType='+grecordType;
		art.dialog.open(path, {
		    title: '修改清单财物',
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
		    	for(var i =0; i < extractRecordBillAdd.proArr.length; i++){
					if(extractRecordBillAdd.proArr[i].id == id){
						extractRecordBillAdd.proArr[i].name=ret.name;
						extractRecordBillAdd.proArr[i].quantity=ret.quantity;
						extractRecordBillAdd.proArr[i].characteristic=ret.characteristic;
						extractRecordBillAdd.proArr[i].unit=ret.unit;
						extractRecordBillAdd.proArr[i].remark=ret.remark;
						
						extractRecordBillAdd.proArr[i].place=ret.place;
						extractRecordBillAdd.proArr[i].authority=ret.authority;
						extractRecordBillAdd.proArr[i].owner=ret.owner;
						extractRecordBillAdd.proArr[i].phone=ret.phone;
					}
				}
		    },
		    cancel: true
		});
	},
	
	//删除
	deleteProperty:function(thisObj){
		var id=$(thisObj).parent().parent().find('input[name="id"]').val();
		for(var i =0; i < extractRecordBillAdd.proArr.length; i++){
			if(extractRecordBillAdd.proArr[i].id === id){
				extractRecordBillAdd.proArr.splice(i, 1);
			}
		}
		$(thisObj).parent().parent().remove();
	},
	
	//修改，初始化数据
	initDataById:function(listID){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/detailedList/queryDetails.do';
		var params={
			'listID':listID
		}
		var cb=function(data){
			if(data.state=='Y'){
				var bill=data.detailedList;
				//持有人，只有一个
				var owners=bill.owners;
				if(owners!=null&&owners[0]!=null){
					for(var i in owners[0]){
						var text=owners[0][i];
						if(i=='idType'||i=='gender'){
							$('#cyrTab select[name="'+i+'"]').val(text);
							continue;
						}
						$('#cyrTab input[name="'+i+'"]').val(text);
					}
				}
				//保管人，只有一个
				var keepers=bill.keepers;
				if(keepers!=null&&keepers[0]!=null){
					$('#bgrTab input[name="name"]').val(keepers[0].name);
					$('#bgrTab input[name="departmentID"]').val(keepers[0].departmentID);
					$('#bgrTab input[name="departmentName"]').val(extractRecordBillAdd.updGetDeptNameById(keepers[0].departmentID));
				}
				//民警，多个
				var polices=bill.polices;
				if(polices!=null){
					if(polices.length>1){
						for(var i=1;i<polices.length;i++){
							var nli=extractRecordBillAdd.getRelevantLi('mj');
							$('#mjTab').append(nli);
						}
					}
					for(var i=0;i<polices.length;i++){
						var sp=polices[i];
						$($('#mjTab tr').eq(i+1)).find('input[name="name"]').val(sp.name);
						$($('#mjTab tr').eq(i+1)).find('input[name="departmentID"]').val(sp.departmentID);
						$($('#mjTab tr').eq(i+1)).find('input[name="departmentName"]').val(extractRecordBillAdd.updGetDeptNameById(sp.departmentID));
					}
				}
				//当事人，多个
				var clients=bill.clients;
				if(clients!=null){
					if(clients.length>1){
						for(var i=1;i<clients.length;i++){
							var nli=extractRecordBillAdd.getRelevantLi('dsr');
							$('#dsrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<clients.length;i++){
						var sp=clients[i];
						$($('#dsrTab tr').eq(idx)).find('input[name="name"]').val(sp.name);
						$($('#dsrTab tr').eq(idx)).find('select[name="idType"]').val(sp.idType);
						idx=idx+1;
						$($('#dsrTab tr').eq(idx)).find('select[name="gender"]').val(sp.gender);
						$($('#dsrTab tr').eq(idx)).find('input[name="idNum"]').val(sp.idNum);
						idx=idx+1;
					}
				}
				//见证人，多个
				var witnesses=bill.witnesses;
				if(witnesses!=null){
					if(witnesses.length>1){
						for(var i=1;i<witnesses.length;i++){
							var nli=extractRecordBillAdd.getRelevantLi('jzr');
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
					extractRecordBillAdd.proArr=bill.properties;
					var tr='';
					for(var i=0;i<bill.properties.length;i++){
						var ret=bill.properties[i];
						tr=tr+'<tr><input name="id" value="'+ret.id+'" type="hidden"/>'
				    	+'<td>'+ret.name+'</td>'
				    	+'<td>'+ret.quantity+'</td>'
				    	+'<td>'+ret.characteristic+'</td>'
				    	+'<td><input type="button" value="修改" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordBillAdd.updateProperty(this)"/>	'
				    	+'<input type="button" value="删除" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordBillAdd.deleteProperty(this)"/></td></tr>';
					}
			    	$('#table_hover tbody').append(tr);
				}
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
		extractRecordBillAdd.startEvent();
		extractRecordBillAdd.bindEvent();
		//有值，则为修改
		var listID=art.dialog.data('listID');
		if(listID!=null){
			//先查询部门数据
			extractRecordBillAdd.initLoadDepartment();
			extractRecordBillAdd.initDataById(listID);
		}
	}
}
$(document).ready(function() {
	extractRecordBillAdd.init();
});
function saveBillImpl(type,caseInfo){
	var ret=true;
	//校验持有人不是检查笔录才显示
	var owners=[];
	if(grecordType!=8){
		ret=extractRecordBillAdd.checkInput($('#cyrTab input'));
		ret=extractRecordBillAdd.checkInput($('#cyrTab select'));
		//获取持有人的值
		owners=extractRecordBillAdd.getInputValTwo($('#cyrTab tr'));
	}
	
	//校验当事人是检查笔录才显示
	var clients=[];
	if(grecordType==8){
		ret=extractRecordBillAdd.checkInput($('#dsrTab input'));
		ret=extractRecordBillAdd.checkInput($('#dsrTab select'));
		//获取当事人的值
		clients=extractRecordBillAdd.getInputValTwo($('#dsrTab tr'));
	}
	
	//校验见证人
	ret=extractRecordBillAdd.checkInput($('#jzrTab input'));
	ret=extractRecordBillAdd.checkInput($('#jzrTab select'));
	//获取见证人的值
	var witnesses=extractRecordBillAdd.getInputValTwo($('#jzrTab tr'));
	
	//校验保管人
	ret=extractRecordBillAdd.checkInput($('#bgrTab input'));
	//获取保管人的值
	var keepers=extractRecordBillAdd.getInputValOne($('#bgrTab tr'));
	
	//校验民警
	ret=extractRecordBillAdd.checkInput($('#mjTab input'));
	//获取民警的值
	var polices=extractRecordBillAdd.getInputValOne($('#mjTab tr'));
	var clientUrl='';
	var detailedList={
		'fromPart':'web',
		'type':grecordType,  //清单类型跟笔录类型一致
		'owners':owners,
		'witnesses':witnesses,
		'keepers':keepers,
		'polices':polices,
		'clients':clients,
		'properties':extractRecordBillAdd.proArr,
		'case':caseInfo
	}
	if(type=='add'){
		detailedList.recordID=art.dialog.data('recordID');
		detailedList.listID=Math.uuid();
		clientUrl='/detailedList/add.do';
	}else if(type=='update'){
		detailedList.listID=art.dialog.data('listID');
		clientUrl='/detailedList/update.do';
	}
	var localUrl=sysPath+'/client/clientRequestJson.do';
	var params={
		'detailedList':detailedList
	};
	var cb=function(data){
		if(data.state=='Y'){
			ret=true;
			plistID=detailedList.listID;  //用于打印
		}else{
			ret=false;
			alert(data.msg);
		}
	}
	clientAjaxPostSync(localUrl,clientUrl,params,cb);
	return ret;
}
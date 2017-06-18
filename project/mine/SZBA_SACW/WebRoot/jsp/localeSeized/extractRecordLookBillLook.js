var updDepartment=null;
var extractRecordLookBillLook={
	proArr:[],
	
	startEvent:function(){
		//检查笔录 特殊处理
		if(grecordType==8){
			$('#cyrTab').css('display','none');
			$('#dsrTab').css('display','');
		}
	},
	
	bindEvent:function(){
		//打印清单
		$('#printBill').on('click',function(){
			var listID=art.dialog.data('listID');
			var printPath = sysPath + "/jsp/localeSeized/extractRecordBillPrint.jsp?listID="+listID+'&recordType='+grecordType;
			window.open(printPath);
		});
	},
	
	//修改
	updateProperty:function(thisObj){
		//修改清单财物
		var id=$(thisObj).parent().parent().find('input[name="id"]').val();
		var ret=null;
		for(var i =0; i < extractRecordLookBillLook.proArr.length; i++){
			if(extractRecordLookBillLook.proArr[i].id === id){
				ret=extractRecordLookBillLook.proArr[i];
			}
		}
		art.dialog.data('ret',ret);
		var path=sysPath+'/jsp/localeSeized/extractRecordBillLookProperty.jsp?recordType='+grecordType;
		art.dialog.open(path, {
		    title: '查看清单财物',
		    width: 860,
		    height:320,
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
						if(i=='gender'){
							$('#cyrTab td[name="'+i+'"]').html(extractRecordLookBillLook.getGenderById(text));
							continue;
						}else if(i=='idType'){
							$('#cyrTab td[name="'+i+'"]').html(extractRecordLookBillLook.getIdTypeById(text));
							continue;
						}
						$('#cyrTab td[name="'+i+'"]').html(text);
					}
				}
				//保管人，只有一个
				var keepers=bill.keepers;
				if(keepers!=null&&keepers[0]!=null){
					$('#bgrTab td[name="name"]').html(keepers[0].name);
					$('#bgrTab td[name="departmentName"]').html(extractRecordLookBillLook.updGetDeptNameById(keepers[0].departmentID));
				}
				//民警，多个
				var polices=bill.polices;
				if(polices!=null){
					if(polices.length>1){
						for(var i=1;i<polices.length;i++){
							var nli=extractRecordLookBillLook.getRelevantLi('mj');
							$('#mjTab').append(nli);
						}
					}
					for(var i=0;i<polices.length;i++){
						var sp=polices[i];
						$($('#mjTab tr').eq(i+1)).find('td[name="name"]').html(sp.name);
						$($('#mjTab tr').eq(i+1)).find('td[name="departmentName"]').html(extractRecordLookBillLook.updGetDeptNameById(sp.departmentID));
					}
				}
				//当事人，多个
				var clients=bill.clients;
				if(clients!=null){
					if(clients.length>1){
						for(var i=1;i<clients.length;i++){
							var nli=extractRecordLookBillLook.getRelevantLi('jzr');
							$('#dsrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<clients.length;i++){
						var sp=clients[i];
						$($('#dsrTab tr').eq(idx)).find('td[name="name"]').html(sp.name);
						$($('#dsrTab tr').eq(idx)).find('td[name="idType"]').html(extractRecordLookBillLook.getIdTypeById(sp.idType));
						idx=idx+1;
						$($('#dsrTab tr').eq(idx)).find('td[name="gender"]').html(extractRecordLookBillLook.getGenderById(sp.gender));
						$($('#dsrTab tr').eq(idx)).find('td[name="idNum"]').html(sp.idNum);
						idx=idx+1;
					}
				}
				//见证人，多个
				var witnesses=bill.witnesses;
				if(witnesses!=null){
					if(witnesses.length>1){
						for(var i=1;i<witnesses.length;i++){
							var nli=extractRecordLookBillLook.getRelevantLi('jzr');
							$('#jzrTab').append(nli);
						}
					}
					var idx=1;
					for(var i=0;i<witnesses.length;i++){
						var sp=witnesses[i];
						$($('#jzrTab tr').eq(idx)).find('td[name="name"]').html(sp.name);
						$($('#jzrTab tr').eq(idx)).find('td[name="idType"]').html(extractRecordLookBillLook.getIdTypeById(sp.idType));
						idx=idx+1;
						$($('#jzrTab tr').eq(idx)).find('td[name="gender"]').html(extractRecordLookBillLook.getGenderById(sp.gender));
						$($('#jzrTab tr').eq(idx)).find('td[name="idNum"]').html(sp.idNum);
						idx=idx+1;
					}
				}
				//显示财物
				if(bill.properties!=null&&bill.properties.length>0){
					extractRecordLookBillLook.proArr=bill.properties;
					var tr='';
					for(var i=0;i<bill.properties.length;i++){
						var ret=bill.properties[i];
						tr=tr+'<tr><input name="id" value="'+ret.id+'" type="hidden"/>'
				    	+'<td>'+ret.name+'</td>'
				    	+'<td>'+ret.quantity+'</td>'
				    	+'<td>'+ret.characteristic+'</td>'
				    	+'<td><input type="button" value="查看" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordLookBillLook.updateProperty(this)"/>	'
				    	+'</td></tr>';
					}
			    	$('#table_hover tbody').append(tr);
				}
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	//获取相应行内容
	getRelevantLi:function(type){
		var nli='';
		if('mj'==type){
			nli='<tr>'
				+'<td style="text-align: right;">姓名：</td>'
				+'<td name="name"></td>'
				+'<td style="text-align: right;">单位：</td>'
				+'<td name="departmentName">'
				+'</td>'
				+'<td>'
				+'</td></tr>';
		}else if('jzr'==type||'dsr'==type){
			nli='<tr>'
				+'<td style="text-align: right;">姓名：</td>'
				+'<td name="name">'
				+'</td>'
				+'<td style="text-align: right;">'
				+'证件类型：</td>'
				+'<td name="idType">'
				+'</td>'
				+'<td>'
				+'</td></tr>'
				+'<tr>'
				+'<td style="text-align: right;">'
				+'性别：</td>'
				+'<td name="gender">'
				+'</td>'
				+'<td style="text-align: right;">'
				+'证件号码：</td>'
				+'<td name="idNum"></td>'
				+'<td>'
				+'</td></tr>';
		}
		return nli;
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
		extractRecordLookBillLook.startEvent();
		extractRecordLookBillLook.bindEvent();
		//有值，则为修改
		var listID=art.dialog.data('listID');
		if(listID!=null){
			//先查询部门数据
			extractRecordLookBillLook.initLoadDepartment();
			extractRecordLookBillLook.initDataById(listID);
		}
	}
}
$(document).ready(function() {
	extractRecordLookBillLook.init();
});
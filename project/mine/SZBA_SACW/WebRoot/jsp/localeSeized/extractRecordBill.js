var extractRecordBill={
	
	billDialog:function(listID){
		var recordID=$('#recordID').val();
		art.dialog.data('recordID', recordID);
		art.dialog.data('listID', listID);
		var type='update';
		var title='修改清单';
		if(listID==null){
			title='添加清单';
			type='add';
		}
		var caseInfo={};
    	caseInfo.caseID=$('#selCase input[name="caseId"]').val();
    	caseInfo.jzcaseID=$('#selCase input[name="ecaseId"]').val();
    	caseInfo.caseName=$('#selCase input[name="caseName"]').val();
    	
		var path=sysPath+'/jsp/localeSeized/extractRecordBillAdd.jsp?recordType='+grecordType+'&oType='+type+'&caseInfo='+caseInfo;
		art.dialog.open(path, {
		    title: title,
		    width: 960,
		    height:520,
		    top:10,
		    ok: function () {
		    	//打印的时候会先添加，不用再添加
		    	if(type=='add'&&this.iframe.contentWindow.plistID!=null){
		    		extractRecordBill.searchData();
		    	}else{
		    		var ret=this.iframe.contentWindow.saveBillImpl(type,caseInfo);
			    	if(!ret)
			    		return false;
			    	extractRecordBill.searchData();
		    	}
		    },
		    cancel: true
		});
	},
	
	bindEvent:function(){
		//添加清单
		$('#addBill').on('click',function(){
			extractRecordBill.billDialog(null);
		});
	},
	
	searchData:function(){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/detailedList/queryAll.do';
		var params={
			'recordID':$('#recordID').val(),
			'type':grecordType   //清单类型跟笔录类型一致
		}
		var cb=function(data){
			var tr='';
			if(data.state=='Y'){
				if(data.detailedLists!=null&&data.detailedLists.length!=0){
					for(var i=0;i<data.detailedLists.length;i++){
						var obj=data.detailedLists[i];
						var createTime=obj.createDate.substring(0,4)
						+'-'+obj.createDate.substring(4,6)
						+'-'+obj.createDate.substring(6,8)
						+' '+obj.createDate.substring(8,10)
						+':'+obj.createDate.substring(10,12)
						+':'+obj.createDate.substring(12,14);
						tr=tr+'<tr><input type="hidden" value="'+obj.listID +'" name="id"/><td>'+createTime+'</td>'
						+'<td>'+obj.userName+'</td>'
						+'<td><input type="button" value="修改" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordBill.updateData(this)"/>	'
				    	+'<input type="button" value="删除" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordBill.deleteData(this)"/></td></tr>';
					}
				}else{
					tr='<tr><td colspan="3" style="color:red;">暂无清单</td></tr>';
				}
			}else{
				tr='<tr><td colspan="3" style="color:red;">暂无清单</td></tr>';
			}
			$('#table_hover tbody').html(tr);
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	//删除清单
	deleteData:function(obj){
		var id=$(obj).parent().parent().find('input[name="id"]').val();
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/detailedList/delete.do';
		var params={
			'listID':id
		}
		var cb=function(data){
			if(data.state=='Y'){
				alert('删除成功');
				extractRecordBill.searchData();
			}else{
				alert('删除失败');
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	//修改清单
	updateData:function(obj){
		var id=$(obj).parent().parent().find('input[name="id"]').val();
		extractRecordBill.billDialog(id);
	},
	
	init:function(){
		extractRecordBill.searchData();
		extractRecordBill.bindEvent();
	}
}
$(document).ready(function() {
	extractRecordBill.init();
});
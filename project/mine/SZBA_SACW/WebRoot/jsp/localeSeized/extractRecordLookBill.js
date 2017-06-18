var extractRecordLookBill={
	
	billDialog:function(listID){
		var recordID=$('#recordID').val();
		art.dialog.data('recordID', recordID);
		art.dialog.data('listID', listID);
		var path=sysPath+'/jsp/localeSeized/extractRecordLookBillLook.jsp?recordType='+grecordType;
		art.dialog.open(path, {
		    title: '查看清单',
		    width: 960,
		    height:520,
		    top:10,
		    cancel: true
		});
	},
	
	bindEvent:function(){
		
	},
	
	searchData:function(){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/detailedList/queryAll.do';
		var params={
			'recordID':$('#recordID').val(),
			'type':grecordType  // 清单类型跟笔录类型一致
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
						+'<td><input type="button" value="查看" style="cursor: pointer;width:50px;margin-right:10px;" onclick="extractRecordLookBill.lookData(this)"/>	'
				    	+'</td></tr>';
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
	
	//查看清单
	lookData:function(obj){
		var id=$(obj).parent().parent().find('input[name="id"]').val();
		extractRecordLookBill.billDialog(id);
	},
	
	init:function(){
		extractRecordLookBill.searchData();
		extractRecordLookBill.bindEvent();
	}
}
$(document).ready(function() {
	extractRecordLookBill.init();
});
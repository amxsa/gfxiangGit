/**
 * 选择部门js
 */
var departmentMgr={
	dataCache:null,
	
	setCheck:function(){
		var zTree = $.fn.zTree.getZTreeObj("departmentTree"),
		py = "",
		sy = "s",
		pn = "",
		sn = "s",
		type = { "Y":py + sy, "N":pn + sn};
		zTree.setting.check.chkboxType = type;
	},
	
	showTreeData:function(selVal){
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				nameIsHTML: true
			}
		};
		var zNodes = [];
		var dataCache=departmentMgr.dataCache;
		for (var i = 0; i < dataCache.length; i++) {
			zNodes[i]={
				id:dataCache[i].id,
				name:dataCache[i].name,
				pId:dataCache[i].parentId
			};
			if(i==0){
				zNodes[i].open=true;
			}
			if(selVal==dataCache[i].id){
				zNodes[i].checked=true;
			};
		}
		$.fn.zTree.init($("#departmentTree"), setting, zNodes);
		departmentMgr.setCheck();
		$("#py").bind("change", departmentMgr.setCheck());
		$("#sy").bind("change", departmentMgr.setCheck());
		$("#pn").bind("change",	departmentMgr.setCheck());
		$("#sn").bind("change",	departmentMgr.setCheck());
	},
	
	//选择返回事件
	getSelData:function(){
		var retData=null;
		var zTree = $.fn.zTree.getZTreeObj("departmentTree");
		var nodes = zTree.getCheckedNodes();  // 取得所有选中的
		var len=nodes.length;
		if(len!=1){
			alert('请选择一个部门');
			return retData;
		}
		var selId=nodes[0].id;
		var dataCache=departmentMgr.dataCache;
		for(var i=0;i<dataCache.length;i++){
			if(selId==dataCache[i].id){
				retData=dataCache[i];
				return retData;
			}
		}
	}
}
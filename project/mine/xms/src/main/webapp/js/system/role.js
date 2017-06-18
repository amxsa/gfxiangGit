/* This function is used to change the style class of an element */

/* This function is used when press enter key in the textfield of page navigation bar. */
//获取权限ID值，以逗号分开。	
function getValFromTree(id){
	var val = '';
	var treeObj = jQuery.fn.zTree.getZTreeObj(id);
	var nodes = treeObj.getCheckedNodes(true);
	for(var i =0;i<nodes.length;i++){
		if(!nodes[i].isParent){
			val +=nodes[i].id+',';
		}
	}
				
	if(val.length>0)val=val.substring(0,val.length-1);	
	return val;	
}

function saveRole(roleid,opttype){
	var rolename = jQuery.trim(jQuery("#roleName").val());
	var rolenote = jQuery.trim(jQuery("#roleNote").val());
	var permission = getValFromTree("treeDemo");
	var opttype = jQuery.trim(jQuery("#opttype").val());
	access=$("#editOrSaveRole").validationEngine({returnIsValid:true});
	if(access){
		if(permission==''){
			top.Dialog.alert("请选择角色对应的权限！");
			return false;
		}
		
		
		var params = {
				aclRoleId:roleid,
				roleName:rolename,
				roleNote:rolenote,
				permission:permission
		};
		var url = '';
		url = "/mms/saveRole.html";
		if(opttype=="edit") {
			url = "/mms/updateRole.html";	
		}
		
		$.ajax({
			type: "POST",
			url: url,
			data: params,
			success: function(msg){
				if(msg="success"){
					top.Dialog.alert("保存成功！");
					window.location.href="/mms/roles.html";
				}else if(msg="noCheckPermission"){
					top.Dialog.alert("请选择角色对应的权限！");
				}else if(msg="fail"){
					top.Dialog.alert("系统正忙，稍后重试！");
				}
			}
		});		
	
	}
}

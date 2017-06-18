
//--------------全选()----------
//调用方式：checkboxAll("but1","abc");
var checkboxAll = function(buttonID,name){
	if(buttonID!=""){
		if($("#"+buttonID).length>0){
			$("input[name='"+name+"']").attr("checked",true);
		}else{
			alert("buttonID not exist");
		}
	}else{
		$("input[name=\""+name+"\"]").attr("checked",true);
	}
}
//--------------全不选----------
//调用方式：unCheckbox("but2","abc");
var unCheckbox = function(buttonID,name){
	if(buttonID!=""){
		if($("#"+buttonID).length>0){
			$("input[name=\""+name+"\"]").attr("checked",false);
		}else{
			alert("button ID not exist");
		}
	}else{
		$("input[name=\""+name+"\"]").attr("checked",false);
	}
}

//---------------获取select option 值---------
//调用方式：var optionVal = selectText("selectid");
var selectText = function(selectID){
	if(selectID!=""){
		if($("#"+selectID).length>0){
			var obj = "#"+selectID;
			return $(obj).find("option:selected").text();
		}else{
			alert("selectID  not exist");
		}
	}else{
		alert("selectID is  null");
	}
}
//--------------异步初始化select数据查询-----------
// 调用方式：var map = new Map();map.put("id","0");initSourceSelect(map,"parentID","<%=path %>/user/CityAction!getCity");

function initSourceSelect(map,selectID,url){
	if(selectID!=""){
		if($("#"+selectID).length>0){
			var obj = $("#"+selectID);
			if(typeof(obj.attr("value2"))=="undefined"||typeof(obj.attr("default"))=="undefined"){
				alert(selectID+" not exist  'childID' or 'default' property");
			}
			var curVal = obj.attr("value2")==""?obj.attr("default"):obj.attr("value2");
			if(url.indexOf("?")>-1){
			}else{
				url=url+"?";
			}
			$.post(url+map.toURL(),function(data){
				if(data!=""){
					var items = ['<option value=""></option>'];
					$.each(data, function(i, n) {
						if(curVal == n.parentId){
							items.push('<option value="' + n.id + '" selected>' + n.name + '</option>');
						}else{
							items.push('<option value="' + n.id + '">' + n.name + '</option>');
						}
					});
					obj.html(items.join(''));
					if(obj.attr('value2')!='' || obj.attr('default')!=''){
						
						
					}
				}
			});
		}else{
			alert("selectID  not exist");
		}
	}else{
		alert("selectID is  null");
	}
}

function changeSourceSelect(map,selectID,url){
	if(selectID!=""){
		if($("#"+selectID).length>0){
			var obj = $("#"+selectID);
			if(typeof(obj.attr("childID"))=="undefined"){
				alert(selectID+" not exist  'childID' property");
			}
			var childID= $("#"+obj.attr("childID"));
			var curVal = childID.attr("value2")==""?obj.attr("default"):obj.attr("value2");
			if(url.indexOf("?")>-1){
			}else{
				url=url+"?";
			}
			$.post(url+map.toURL(),function(data){
				if(data!=""){
					var items = ['<option value=""></option>'];
					$.each(data, function(i, n) {
						if(curVal == n.parentId){
							items.push('<option value="' + n.id + '" selected>' + n.name + '</option>');
						}else{
							items.push('<option value="' + n.id + '">' + n.name + '</option>');
						}
					});
					childID.html(items.join(''));
					if(childID.attr('value2')!='' || childID.attr('default')!=''){
						
					}
				}
			});
		}else{
			alert("selectID  not exist");
		}
	}else{
		alert("selectID is  null");
	}
}

//--判断是否选中
function isCheckBox(name) {
	var array = document.getElementsByTagName("input");
	var len = array.length;
	for (var i = 0; i < len; i++) {
		if (array[i].type == "checkbox") {
			if (array[i].checked == true&&array[i].name==name) {
				return true;
			}
		}
	}
	return false;
}
String.prototype.endWith=function(s){
	 if(s==null||s==""||this.length==0||s.length>this.length)
		 return false;
	 if(this.substring(this.length-s.length)==s)
		 return true;
	 else
		 return false;
	 return true;
}
String.prototype.startWith=function(s){
	 if(s==null||s==""||this.length==0||s.length>this.length)
		 return false;
	 if(this.substr(0,s.length)==s)
		 return true;
	 else
		 return false;
	 return true;
}

	 





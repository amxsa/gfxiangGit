var Picture = function(){
		var picID;
		var type;
		var thumbnailUrl;
		var originalUrl;
		var picDescription;
		var name;
		var propertyID;
}

var Attachment = function(){
	var id;
	var type;
	var uploadUrl;
	var attaName;
	var description;
	var propertyId;
	var applicationId;
}
	
var fileArray = [];
var imgArray = [];

var idCardVarid = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X|x)$/i;

function updateData(proId, mythis){
	
	if(!validData()){
		return false;
	};
	
	disableBtn(mythis, "更新中……");
	beforeUpdate();  //更新时添加属性值
	var action = $("form:first").attr("action");
	$.ajax({
		url:action,
		data:$("form:first").serialize(),
		type:"post",
		success:function(backData){
			if(backData == "Y"){
				/*art.dialog({content: "更新成功", top: '10%',lock: true,button: [{name: '确定', callback:function(){
					window.location.href=sysPath + "/property/queryDetail.do?proId="+proId+"&viewName=propertyPreModify&"+getConditionStr();
				}}]});*/
				alert("更新成功");
				window.location.href=sysPath + "/property/queryDetail.do?proId="+proId+"&viewName=propertyPreModify&"+getConditionStr();
			}else{
				alert("更新失败");
				enableBtn(mythis, "更新", 'updateData('+proId+', this)');
				
			}
		},
		error:function(){
			alert("连接出错");
			enableBtn(mythis, "更新", 'updateData('+proId+', this)');
		}
	});
}

function pushFile(desc, uploadFileName,filePath, mixId, type,opeType){
	var num = 0;
	if(opeType === "1"){
		var att = new Attachment();
		att.type = type;
		att.uploadUrl = filePath;
		att.attaName = uploadFileName;
		att.description = desc;
		if(type === "21"){
			att.propertyId = mixId;
		}else if(type === "24") {
			att.applicationId = mixId;
		}
		
		fileArray.push(att);
		num = fileArray.length;
	}else{
		var picture = new Picture();
		picture.type=type;
		picture.originalUrl = filePath;
		picture.name = uploadFileName;
		picture.picDescription = desc;
		if(type === "21"){
			picture.propertyID = mixId;
		}
		
		imgArray.push(picture);
		num = imgArray.length;
	}
	
	showFileTable(opeType);
}

function showFileTable(type){
	
	var uploadText = '';
	if(type === "1"){
		for(var i = 0; i < fileArray.length; i++){
			if(fileArray[i] != ""){
				var fileItem = fileArray[i]; 
				var fileStr = JSON.stringify(fileItem).replace(/\"/g,"'");
				//文件
				//文件
				//var filePath=fileUrl+fileItem.uploadUrl;
				uploadText += '<li><a href="javascript:void(0)" onclick="downloadFile(\''+fileItem.uploadUrl+'\')">'+fileItem.attaName+'</a>'
				+'<input type="button" id="showFileBut'+(i+1)+'" name="showFileButName" value="删除" class="bb07" onclick="delSwfFile(\''+fileItem.uploadUrl+'\', \'1\')"/>'
				+'<input type="hidden" name="attachmentJson" value="'+fileStr+'"></li>';
			}
		}
		document.getElementById("attachmentList").innerHTML = uploadText;
	}else{
		for(var i = 0; i < imgArray.length; i++){
			if(imgArray[i] != ""){
				var imgItem = imgArray[i]; 
				var imgStr = JSON.stringify(imgItem).replace(/\"/g,"'");
				
				if(imgItem.name == null || imgItem.name === "undefined"){
					imgItem.name = "";
				}
				//图片
				uploadText += '<li><a href="javascript:void(0)" onclick="showImgDetail(this, 1)"><img src="'+photoUrl+imgItem.originalUrl+'" width="100px" height="100px"/>'+imgItem.name+'</a><input type="button" value="修改" onclick="showDiv2(this)"><input type="button" id="showFileBut'+(i+1)+'" name="showFileButName" value="删除" class="bb07" onclick="delSwfFile(\''+imgItem.originalUrl+'\', \'2\')"/><input type="hidden" name="pictureJson" value="'+imgStr+'"></li>';
			}
		}
		document.getElementById("imgList").innerHTML = uploadText;
	}
	
	
}

function delSwfFile(delFileUrl, type){
	if(type === "1"){
		for(var i =0; i < fileArray.length; i++){
			
			if(fileArray[i].uploadUrl === delFileUrl){
				fileArray.splice(i, 1);
			}
		}
	}else{
		for(var i = 0; i < imgArray.length; i++){
			if(typeof(imgArray[i].originalUrl) != 'undefined' && imgArray[i].originalUrl === delFileUrl){
				imgArray.splice(i, 1);
			}
			
		}
	}
	
	showFileTable(type);
}

//数据验证
function validData(){
	//手机号验证
	var civiPhone = $("input[name='civiPhone']").val();
	var regxMobilePhone =  /^0{0}(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])[0-9]{8}$/;
	
	
	if(civiPhone != null && civiPhone != ''){
		if(!regxMobilePhone.exec(civiPhone)){
			alert("手机号码格式不对");
			return false;
		}
	}
	
	return true;
}


function modelAlert(myContent){
	art.dialog({content: myContent, top: '10%',lock: true,button: [{name: '确定'}]});
}

$(document).ready(function(){
	//上传附件
	$('#attachmentApplication').uploadify({
		'formData' : {},
		'fileTypeDesc' : 'Files',
		'sizeLimit' : 50720000000,//控制上传文件的大小，单位byte服务器默认只支持30MB(30000000)，修改服务器设置请查看相关资料
		'queueID' : 'attachmentQuene',
		'swf' : sysPath+'/js/uploadify/uploadify.swf',
		'uploader' : sysPath + '/upload/attachment.do',
		'buttonText' : '添加档案资料',
		'onUploadSuccess' : function(file, data,response) {
			var dataObj = JSON.parse(data);
			if (dataObj) {
				if (dataObj.state == "Y") {
					if (dataObj.object) {
						var desc = "";
						pushFile(desc,dataObj.object.fileName,dataObj.object.path,null, "24","1");
					}
				} else {
					alert(dataObj.msg);
				}
			}
		}
	});
	//上传处置登记附件
	$('#attachmentHandleResult').uploadify({
		'formData' : {},
		'fileTypeDesc' : 'Files',
		'sizeLimit' : 50720000000,//控制上传文件的大小，单位byte服务器默认只支持30MB(30000000)，修改服务器设置请查看相关资料
		'queueID' : 'attachmentQuene',
		'swf' : sysPath+'/js/uploadify/uploadify.swf',
		'uploader' : sysPath + '/upload/attachment.do',
		'buttonText' : '添加档案资料',
		'onUploadSuccess' : function(file, data,response) {
			var dataObj = JSON.parse(data);
			if (dataObj) {
				if (dataObj.state == "Y") {
					if (dataObj.object) {
						var desc = "";
						pushFile(desc,dataObj.object.fileName,dataObj.object.path,null, "33","1");
					}
				} else {
					alert(dataObj.msg);
				}
			}
		}
	});
});
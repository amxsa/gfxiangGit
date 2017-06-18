var proId=null;
var extractRecordUpdateBillProperty={
	picArr:[],
	
	startEvent:function(){
		//7-查封笔录，特殊处理
		if(grecordType==7){
			$('#ap01Tr').css('display','');
			$('#jc02Tr').css('display','');
		}
		//8-检查笔录，特殊处理
		if(grecordType==8){
			$('#jc01Tr').css('display','');
			$('#jc02Tr').css('display','');
		}
	},
	
	bindEvent:function(){
		//弹框添加图片
		$('#addPicturePic').on('click',function(){
			picMgr.picId=Math.uuid();  //初始化图片id
			art.dialog({
				content:$('#uploadPicDiv').html(),
			    title: '选择图片',
			    width: 600,
			    padding:5,
			    top:10,
			    init: function () {
			    	$('#uploadPicUl input').val('');
			    	$('#uploadPicUl textarea').val('');
			    	picMgr.bindUpload();  //绑定上传图片
			    },
			    ok: function () {
			    	picMgr.setUploadVal();  //获取上传值
			    },
			    cancel: true
			});
		});
	},
	
	deletePic:function(obj){
		var picId=$(obj).parent().find('input[name="picId"]').val();
		//删除数据库图片
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/history/queryForWeb.do';
		var params={
			'picID':picId
		}
		var cb=function(data){}
		clientAjaxPost(localUrl,clientUrl,params,cb);
		
		for(var i =0; i < extractRecordUpdateBillProperty.picArr.length; i++){
			if(extractRecordUpdateBillProperty.picArr[i].picID == picId){
				extractRecordUpdateBillProperty.picArr.splice(i, 1);
			}
		}
		$(obj).parent().remove();
	},
	
	//打印
	printPic:function(obj){
		var thumbnailUrl=$(obj).parent().find('input[name="thumbnailUrl"]').val();
		var printPath = sysPath + "/jsp/localeSeized/extractRecordPicturePrint.jsp?thumbnailUrl="+thumbnailUrl+'&recordType='+grecordType;
		window.open(printPath);
	},
	
	init:function(){
		extractRecordUpdateBillProperty.startEvent();
		extractRecordUpdateBillProperty.bindEvent();
	}
}
$(document).ready(function() {
	extractRecordUpdateBillProperty.init();
});
function initProperty(){
	var ret=art.dialog.data('ret');
	$('#cwTab input[name="id"]').val(ret.id);
	$('#cwTab input[name="name"]').val(ret.name);
	$('#cwTab input[name="quantity"]').val(ret.quantity);
	$('#cwTab input[name="unit"]').val(ret.unit);
	$('#cwTab input[name="place"]').val(ret.place);
	$('#cwTab input[name="authority"]').val(ret.authority);
	$('#cwTab input[name="characteristic"]').val(ret.characteristic);
	$('#cwTab input[name="owner"]').val(ret.owner);
	$('#cwTab input[name="phone"]').val(ret.phone);
	$('#cwTab textarea[name="remark"]').val(ret.remark);
	extractRecordUpdateBillProperty.picArr=ret.photographs;
	var ps=extractRecordUpdateBillProperty.picArr;
	if(ps!=null&&ps.length!=0){
		var li='';
		for(var i=0;i<ps.length;i++){
			var sps=ps[i];
			li=li+'<li style="margin:0px;width: 120px;padding:0px;"><input type="hidden" name="picId" value="'+sps.picID+'"/>'
			+'<input type="hidden" name="thumbnailUrl" value="'+sps.thumbnailUrl+'"/><a href="'+sps.originalUrl+'" target="_blank">'
			+'<img src="'+sps.thumbnailUrl+'" width="50px" height="50px" style="margin-bottom:2px;"/></a>'
			+'<input type="button" value="删除" class="bb07" onclick="extractRecordUpdateBillProperty.deletePic(this)"/>'
			+'<input type="button" value="打印" style="margin-left:5px;" class="bb07" onclick="extractRecordUpdateBillProperty.printPic(this)"/></li>';
		}
		$('#imgList').append(li);
	}
}
function saveProperty(){
	var ret=verifyForm.inputNotNull($('#cwTab input[name="name"]'), '名称',null, null);
	if(!ret){
		return ret;
	}
	ret={};
	ret.id=$('#cwTab input[name="id"]').val();
	ret.name=$.trim($('#cwTab input[name="name"]').val());
	ret.quantity=$.trim($('#cwTab input[name="quantity"]').val());
	ret.unit=$.trim($('#cwTab input[name="unit"]').val());
	ret.place=$.trim($('#cwTab input[name="place"]').val());
	ret.authority=$.trim($('#cwTab input[name="authority"]').val());
	ret.characteristic=$.trim($('#cwTab input[name="characteristic"]').val());
	ret.owner=$.trim($('#cwTab input[name="owner"]').val());
	ret.phone=$.trim($('#cwTab input[name="phone"]').val());
	ret.remark=$.trim($('#cwTab textarea[name="remark"]').val());
	ret.photographs=extractRecordUpdateBillProperty.picArr;
	return ret;
}
var picMgr={
		picId:null,
		
		bindUpload:function(type){
			//上传照片
			$('#imageInfo').uploadify({
				'formData' : {
					'sessionID':clientSessionID,
					'timestamp':clientTimestamp,
					'authstring':clientAuthstring,
					'type':'21',
					'propertyID':$('#cwTab input[name="id"]').val(),
					'ids':picMgr.picId,
					'isFormal':'Y',
					'url':'/photographs/upload.do'
				},
				'fileTypeDesc' : 'Images',
				'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.bmp;*.gif;*.tiff',
				'sizeLimit' : 5000000,//控制上传文件的大小，单位byte服务器默认只支持30MB(30000000)，修改服务器设置请查看相关资料
				'queueID' : 'queue',
				'swf' : sysPath+'/js/uploadify/uploadify.swf',
				'uploader' : sysPath+'/client/clientRequestMapFile.do',
				'buttonText' : '添加照片',
				'onUploadSuccess' : function(file, data,response) {
					var dataObj = JSON.parse(data);
					if (dataObj.state == "Y") {
						var originalUrl=dataObj.photographs[0].originalUrl;
						var thumbnailUrl=dataObj.photographs[0].thumbnailUrl;
						var picLi='<a href="'+originalUrl+'" target="_blank">'
						+'<img src="'+originalUrl+'" width="50px" height="50px"/></a>';
						$('#showPicDiv').html(picLi);
						$('#uploadPicUl input[name="originalUrl"]').val(originalUrl);
						$('#uploadPicUl input[name="thumbnailUrl"]').val(thumbnailUrl);
						
						var pic = new Image();
						pic.src = originalUrl;
						pic.onload=function(){
							$('#uploadPicUl input[name="width"]').val(this.width);
							$('#uploadPicUl input[name="height"]').val(this.height);
						};
					} else {
						alert(dataObj.msg);
					}
				}
			});
		},
		
		setUploadVal:function(){
	    	var originalUrl=$('#uploadPicUl input[name="originalUrl"]').val();
	    	var thumbnailUrl=$('#uploadPicUl input[name="thumbnailUrl"]').val();
	    	var description=$('#uploadPicUl textarea[name="picDescription"]').val();
	    	//var capturer=$.trim($('#uploadPicUl input[name="capturer"]').val());
	    	var captureTime=$.trim($('#uploadPicUl input[name="captureTime"]').val());
	    	var capturePlace=$.trim($('#uploadPicUl input[name="capturePlace"]').val());
	    	var picWidth=$.trim($('#uploadPicUl input[name="width"]').val());
	    	var picHeight=$.trim($('#uploadPicUl input[name="height"]').val());
	    	
			var li='<li style="margin:0px;width: 120px;padding:0px;"><input type="hidden" name="picId" value="'+picMgr.picId+'"/>'
			+'<input type="hidden" name="thumbnailUrl" value="'+thumbnailUrl+'"/><a href="'+originalUrl+'" target="_blank">'
			+'<img src="'+originalUrl+'" width="50px" height="50px" style="margin-bottom:2px;"/></a>'
			+'<input type="button" value="删除" class="bb07" onclick="extractRecordUpdateBillProperty.deletePic(this)"/>'
			+'<input type="button" value="打印" style="margin-left:5px;" class="bb07" onclick="extractRecordUpdateBillProperty.printPic(this)"/></li>';
			$('#imgList').append(li);
			var pic={};
			pic.picID=picMgr.picId;
			pic.description=description;
			pic.captureTime=captureTime;
			//pic.capturer=capturer;
			pic.capturePlace=capturePlace;
			pic.originalUrl=originalUrl;
			pic.width=picWidth;
			pic.height=picHeight;
			
			extractRecordUpdateBillProperty.picArr.push(pic);
		}
	}
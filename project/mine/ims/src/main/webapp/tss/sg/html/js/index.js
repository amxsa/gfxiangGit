
$(function(){
	
	
});


function signUp(){
	if($("#fans").val()=="false"){
		msgBox("请先关注“韶关市区农村信用合作联社”公众号，那里才有报名入口哟~",function(){
		});
		return ;
	}
	
	if($("#passed").val()=="false"){
		msgBox("资料已经加急审核中，请留意公众号提示，8号就可以开始投票啦~~",function(){
		});
		return ;
	}
	
	window.location.href=basePath+"front/loveVote/toSignUp/"+$("#actId").val()+"/"+$("#openid").val();
}





/**
 * 跳转为谁投票页
 * @param tt
 */
function thumbUp(optid){
	
	window.location.href=basePath+"front/loveVote/toThumpUp/"+optid+"?openid="+$("#openid").val();
	
	
}
function getItemHtml(sort,nickname,picture, total,optid) {
	var temp ="";
	temp+="<div class=\"my_box\">";
	 temp+="           <img src=\""+basePath+picture+"\" alt=\"\" class=\"pic_border\" optid="+optid+" onclick=\"thumbUp('"+optid+"')\"/>";
	  temp+="          <p class=\"p_name\" style=\"color:#FFF;\">"+nickname+"</p>";
	  temp+="          <p><em class=\"love_pic\"></em><i class=\"num\" style=\"color:#FFF;\">"+total+"</i></p>";
	  temp+="          <div class=\" round\" style=\"color:#FFF;\">"+sort+"</div>";
	   temp+="     </div>";
	return temp;
}
function genItems(data) {
	
	var rows = data.rows;
	var opts = data.options;
	var c = "";
	
	if(opts!=null && opts[4]==1){
		c+="<div class=\"my_box\">";
		 c+="           <img src=\""+basePath+opts[1]+"\" alt=\"\" class=\"pic_border\" optid="+opts[3]+" onclick=\"thumbUp('"+opts[3]+"')\"/>";
		  c+="          <p class=\"p_name\" style=\"color:#FFF;\">"+opts[0]+"</p>";
		  c+="          <p><em class=\"love_pic\" ></em><i class=\"num\" style=\"color:#FFF;\">"+opts[2]+"</i></p>";
		  c+="          <div class=\" color_cur\" style=\"color:#FFF;\">"+data.mysort+"</div>";
		   c+="     </div>";
	}
	
	
	for ( var i = 0; i < rows.length; i++) {
		c += getItemHtml(i+1, rows[i][0],rows[i][1],rows[i][2],rows[i][3]);
	}
	
   /* c+="<div class=\"footer\">";
    c+="<img src=\"images/ad.jpg\" alt=\"\" onclick='jump()' />";
    c+="</div>";*/
	
	$(".people_box").html(c);
}

function load(parameters){
	parameters = parameters || {};
	parameters.page = parameters.page || 1;
	parameters.pageSize = parameters.pageSize || 10;
	var limit = parameters.page * parameters.pageSize;
	var d = { start : 0, limit : limit };
	d.openid=$("#openid").val();
	$.ajax({
		url : basePath+"front/loveVote/loadData/"+$("#actId").val(),
		type : "post",
		dataType : "text",
		timeout : 10000,
		cache : false,
		async : false,
		data : d,
		success : function(data) {
			data = eval("(" + data + ")");
			if (data) {
				genItems(data);
				page++;
				$("#total").html(data.total);
				
				/*if(data.rows.length<=0 && $("#passed").val()=="false" && $("#signTime").val()=="true"){
					$(".p_praise").show();
				}else {
					$(".p_praise").hide();
				}*/
				
			} else {
				showAlert('Errors encountered on the server.');
			}
		},
		error : function(a, b, c, d, e) {
		}
	});
}



/**
 * 上传资料
 * @returns {Boolean}
 */
function ajaxFileImport() {
	
	//var imageData = $('#plan').attr('data-src');
	var imageData = $('#bg').attr('src');
	//var photoExt=$("#file").val().substring($("#file").val().lastIndexOf(".")).toLowerCase();
	var lifeImageData = $('#life').attr('src');
	
	/*if (photoExt=="" || imageData=="" || ( photoExt!=".png" && photoExt!=".jpg" && photoExt!=".jpeg" && photoExt!=".bmp" &&photoExt!=".gif"  && 
			photoExt!=".tiff" && photoExt!= ".pcx" && photoExt!=".tga" && photoExt!=".exif" && photoExt!=".fpx" &&
			photoExt!=".svg" && photoExt!=".psd" && photoExt!=".cdr" && photoExt!=".pcd" && photoExt!=".hdri" &&
			photoExt!=".dxf" && photoExt!=".ufo" && photoExt!=".eps" && photoExt!=".ai" && photoExt!=".raw")) {
		msgBox("必须上传工作照片! ",function(){});
		return ;
	}*/
	
	if (imageData==undefined || imageData==null || imageData=="" || imageData=="images/photo_bg.png") {
		msgBox("必须上传工作照片! ",function(){});
		return ;
	}
	
	if(lifeImageData==undefined || lifeImageData==null || lifeImageData=="" || lifeImageData=="images/photo_bg.png"){
		msgBox("必须上传生活照片! ",function(){});
		return;
	}
	
	
	var name = $("#name").val();
	if(name==""){
		msgBox("请输入真实姓名",function(){});
		return;
	}
	
	var nickname = $("#nickname").val();
	if(nickname==""){
		msgBox("请输入昵称",function(){});
		return;
	}
	
	var creditUnion = $("#creditUnion").val();
	if(creditUnion==""){
		msgBox("请输入所在农合机构/中心",function(){});
		return;
	}
	
	var no = $("#no").val();
	if(no==""){
		msgBox("请输入工号",function(){});
		return;
	}
	
/*	var position = $("#position").val();
	if(position==""){
		msgBox("请输入岗位",function(){});
		return;
	}*/
	var position = "";
	
	var phone = $("#phone").val();
	if(phone=="" || !phone.match("^[0-9]{11}$")){
		msgBox("请输入正确的手机号码",function(){});
		return;
	}
	
	var show = "";
/*	var show = $("#show").val();
	if(show==""){
		msgBox("请输入自我介绍",function(){});
		return;
	}*/
	
	var reason = $("#reason").val();
	if(reason==""){
		msgBox("请输入美丽宣言",function(){});
		return;
	}
	
	var actId = $("#actId").val();
	var openid = $("#openid").val();
	
	
	var d = {};
	d.no = no;
	d.creditUnion = creditUnion;
	d.position = position;
	d.name = name;
	d.nickname = nickname;
	d.phone = phone;
	d.reason = reason;
	d.show = show;
	d.actId = actId;
	d.openid = openid;
	d.imageData = imageData;
	d.lifeImageData = lifeImageData;
	
	if(submit==false) {
		return;
	}
	submit = false;
	
	$.ajax({
		url : basePath+'front/loveVote/bUpload', // 需要链接到服务器地址
		type : "post",
		data:d,
		//fileElementId : 'fileimport', // 文件选择框的id属性
		dataType : 'json', // 服务器返回的格式，可以是json
		timeout : 10000,
		cache : false,
		async : false,
		success : function(data) {
			if(data.success==true){
				 $('.layer').fadeIn(100);        
			     $('.layer-bg').fadeTo(100,0.6);  // 半透明
			}else {
				 msgBox(data.msg,function(){
					 window.location.href=basePath+'front/loveVote/index/'+$("#actId").val()+"?openid="+$("#openid").val();
				 });
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			submit = true;
		}
	});
	
	
   	/*$.ajaxFileUpload({
		url : basePath+'front/loveVote/bUpload', // 需要链接到服务器地址
		secureuri : false,
		data:d,
		fileElementId : 'fileimport', // 文件选择框的id属性
		dataType : 'json', // 服务器返回的格式，可以是json
		success : function(data) {
			if(data.success==true){
				 $('.layer').fadeIn(100);        
			     $('.layer-bg').fadeTo(100,0.6);  // 半透明
			}else {
				 msgBox(data.msg,function(){
					 window.location.href=basePath+'front/loveVote/index/'+$("#actId").val()+"?openid="+$("#openid").val();
				 });
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			submit = true;
		}
	});*/
}



/**
 * text 弹窗内容 func 回调函数，点确定后执行
 */
function msgBox(text, func) {

	var temp = "</div>	<div class=\"vAlertBox\" style=\" width: 100%;  height: 100%;  display: none;  position: fixed;  top: 0;  left: 0;  z-index: 999; \"> ";
	temp += "<div class=\"vAlertbg\" style=\"  width: 100%;  height: 100%;  background: rgba(0,0,0,0.3); \" ></div>";
	temp += "<div class=\"vAlerttc\" style=\"display:block;    width: 250px;      background: rgba(255,255,255,0.9); ";
	temp += " position: fixed;    top: 45%;    margin-top: -65px;    left: 50%;    margin-left: -125px;    z-index: 999;    border-radius: 9px; \" >";
	temp += "	<span class=\"vAlertP\" style=\"  display: block;    text-align: center;    position: relative;    padding:20px 10px; \" >"
			+ text + "</span>";
	temp += "	<span class=\"vAlertBtn\" style=\"  display: block;    text-align: center;    position: relative;    padding: 10px 10px 10px; border-top: 1px solid #777777; \" >确定</span>";
	temp += "</div></div>";
	$("body").after(temp);
	$(".vAlertBox").show();
	$(".vAlertBtn").on("click", function() {
		$(".vAlertBox").css("display", "none");
		$(".vAlertBox").remove();
		func();
	});

}



/**
 *  getJSDK
 */
function getConfig() {
	
	var d = {
			url:url
		};
	console.log(d);
	$.ajax({
		url : basePath+"front/loveVote/getconfig/"+$("#actId").val(),
		type : "post",
		dataType : "text",
		cache : false,
		async : false,
		timeout : 10000,
		data : d,
		success : function(data) {
			console.log(data);
			data = eval("(" + data + ")");
			if (data) {
				$("#timestamp").val(data.timestamp);
				$("#nonceStr").val(data.nonceStr);
				$("#appid").val(data.appId);
				$("#signature").val(data.signature);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	
	console.log("请求服务器成功配置信息成功，开始配置微信");
	
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: $("#appid").val(), // 必填，公众号的唯一标识
	    timestamp:$("#timestamp").val() , // 必填，生成签名的时间戳
	    nonceStr: $("#nonceStr").val(), // 必填，生成签名的随机串
	    signature: $("#signature").val(),// 必填，签名，见附录1
	    jsApiList: ["hideOptionMenu","onMenuShareTimeline","onMenuShareAppMessage","showMenuItems","hideMenuItems"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
	
}


var title = "最美农信人";

var link;

var imgUrl;
var desc = "选出您心中最美的她/他";

function wxReady(){
	
	
	wx.ready(function(){
 		
    	  wx.onMenuShareTimeline({
    	    title: title, // 分享标题
    	    link: link,
    	    imgUrl: imgUrl, // 分享图标
    	    success: function () { 
    	    	addShareRecord("ShareTimeline",link);
    	    },
    	    cancel: function () { 
    	        // 用户取消分享后执行的回调函数
    	    }
    	}); 
    	 
    	 wx.onMenuShareAppMessage({
    		 
    	    title: title, // 分享标题
    	    desc: desc, // 分享描述
    	    link: link, // 分享链接
    	    imgUrl: imgUrl, // 分享图标
    	    type: '', // 分享类型,music、video或link，不填默认为link
    	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
    	    success: function () { 
    	    	addShareRecord("ShareAppMessage",link);
    	    },
    	    cancel: function () { 
    	        // 用户取消分享后执行的回调函数
    	    }
    	});
    	 
    	});
}

function addShareRecord(shareType,link){
	var d = {};
	d.actId = $("#actId").val();
	d.shareType=shareType;
	d.shareUrl=link;
	$.ajax({
		url : basePath+"front/loveVote/addShare",
		type : "post",
		dataType : "text",
		timeout : 10000,
		data : d,
		async : false,
		success : function(data) {
			data = eval("(" + data + ")");
			if (data.success) {
				
			} else {
				alert("保存失败，请检查输入！");
			}
		},
		error : function(a, b, c, d, e) {
		}
	}); 
}

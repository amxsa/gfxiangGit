function initProperty(){
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
	var ret=art.dialog.data('ret');
	$('#cwTab td[name="name"]').html(ret.name);
	$('#cwTab td[name="quantity"]').html(ret.quantity);
	$('#cwTab td[name="unit"]').html(ret.unit);
	$('#cwTab td[name="place"]').html(ret.place);
	$('#cwTab td[name="authority"]').html(ret.authority);
	$('#cwTab td[name="characteristic"]').html(ret.characteristic);
	$('#cwTab td[name="owner"]').html(ret.owner);
	$('#cwTab td[name="phone"]').html(ret.phone);
	$('#cwTab td[name="remark"]').html(ret.remark);
	var ps=ret.photographs;
	if(ps!=null&&ps.length!=0){
		var li='';
		for(var i=0;i<ps.length;i++){
			var sps=ps[i];
			li=li+'<li style="margin:0px;width: 60px;padding:0px;">'
			+'<input type="hidden" name="picId" value="'+sps.picID+'"/>'
			+'<input type="hidden" name="thumbnailUrl" value="'+sps.thumbnailUrl+'"/>'
			+'<a href="'+sps.originalUrl+'" target="_blank">'
			+'<img src="'+sps.thumbnailUrl+'" width="50px" height="50px" style="margin-bottom:2px;"/></a>'
			+'<input type="button" value="打印" class="bb07" onclick="printPic(this)"/></li>';
		}
		$('#imgList').append(li);
	}
}
function printPic(obj){
	var thumbnailUrl=$(obj).parent().find('input[name="thumbnailUrl"]').val();
	var printPath = sysPath + "/jsp/localeSeized/extractRecordPicturePrint.jsp?thumbnailUrl="+thumbnailUrl+'&recordType='+grecordType;
	window.open(printPath);
}

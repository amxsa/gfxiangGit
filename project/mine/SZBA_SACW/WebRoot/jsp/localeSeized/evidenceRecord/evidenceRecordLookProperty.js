function initProperty(){
	var ret=art.dialog.data('ret');
	$('#cwTab td[name="name"]').html(ret.name);
	$('#cwTab td[name="quantity"]').html(ret.quantity);
	$('#cwTab td[name="unit"]').html(ret.unit);
	$('#cwTab td[name="characteristic"]').html(ret.characteristic);
	$('#cwTab td[name="position"]').html(ret.position);
	$('#cwTab td[name="method"]').html(ret.method);
	$('#cwTab td[name="remark"]').html(ret.remark);
	var ps=ret.photographs;
	if(ps!=null&&ps.length!=0){
		var li='';
		for(var i=0;i<ps.length;i++){
			var sps=ps[i];
			li=li+'<li style="margin:0px;width: 60px;padding:0px;"><input type="hidden" name="picId" value="'+sps.picID+'"/>'
			+'<a href="'+sps.originalUrl+'" target="_blank">'
			+'<img src="'+sps.originalUrl+'" width="50px" height="50px" style="margin-bottom:2px;"/></a></li>';
		}
		$('#imgList').append(li);
	}
}

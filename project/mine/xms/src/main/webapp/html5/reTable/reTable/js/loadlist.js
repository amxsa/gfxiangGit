
//加载数据（获取数据+生成显示列表+分页）
function loadList(parameters) {

	parameters = parameters || {};//分页的参数
	parameters.page = parameters.page || 1;//当前为第几页
	parameters.pageSize = parameters.pageSize || 10;//每页显示条数
	var start = (parameters.page - 1) * parameters.pageSize;
	parameters.start = start;//当前页从第几条开始
	parameters.limit = parameters.pageSize;//当前页从第几条结束
	
	var title = $(".soso").val();
	
	var startTime = $("#starttime").val();//查询时间参数
	var endTime = $("#endtime").val();//查询时间参数
	
	parameters.title = title;
	
	//console.log(startTime);
	//console.log(endTime);
	
	if ($.trim(startTime)!="") {
		startTime+=" 00:00:00";
		parameters.startTime=startTime;
	}
	
	if ($.trim(endTime)!="") {
		endTime+=" 00:00:00";
		parameters.endTime=endTime;
	}
	
	//调用方法
	genItems(data);//生成数据列表
	genPageing(data, parameters);//分页
	//从后台获取数据
	$.ajax({
		url : "loanapply/allapply",
		type : "post",
		dataType : "text",
		cache : false,
		async : false,
		timeout : 10000,
		data : parameters,
		success : function(data) {
			data = eval("(" + data + ")");
			if (data.success == true) {
				genItems(data);
				genPageing(data, parameters);
			} else {
				showAlert("获取数据失败!");
			}
		}
	});

}
	
function getPageTemple(isCurrent, page, text) {
	text = text || page;
	return '<a style="' + (isCurrent ? "background-color: #85c526;" : "")
			+ '" href="javascript:void(0);" onclick="loadList({page:' + page
			+ '});" pagebegin="">' + text + '</a>';
}
//分页显示
function genPageing(data, parameters) {
	if (data.total <= parameters.pageSize) {
		$("#pageing")
				.html(
						'<a style="background-color: #85c526;" href="javascript:void(0)">1</a><a >共1页</a><input type="hidden" id="pagebegin" value="1">');
		return false;
	}
	var totlePage = Math.ceil(data.total / parameters.pageSize);
	var showPageNum = 5;
	var current = parameters.page;
	current = Number(current);
	var startPage = Math.max((current - 2), 1);
	var endPage = Math.min((current + 2), totlePage);
	var c = "";
	for ( var i = startPage; i <= endPage; i++) {
		c += getPageTemple((current == i), i);
	}
	if (startPage > 1) {
		c = getPageTemple(false, 1) + c;
	}
	if (current != 1) {
		c = getPageTemple(false, current - 1, "上一页") + c;
	}
	if (endPage < totlePage) {
		c += getPageTemple(false, totlePage);
	}
	if (current != totlePage) {
		c += getPageTemple(false, current + 1, "下一页");
	}
	c += '<input class="pagearticles" type="text" style="float: left;height:26px;" name="pagebegin" value="'
			+ current
			+ '"><a href="javascript:void(0)" id="pagearticles" maxpage="'
			+ totlePage
			+ '">跳转</a><input type="hidden" id="pagebegin" value="'
			+ current + '">';
	$("#pageing").html(c);

}

//创建列表
function genItems(data) {
	c += '';
	c += '<table width="100%" border="1" cellspacing="0" cellpadding="0"> ';
	c += '<thead> ';
	c += '    <tr> ';
	c += '      <th>申请人<i class="up down"></i></th> ';
	c += '      <th>贷款类型<i class="up down"></i></th> ';
	c += '      <th>额度<i class="up down"></i></th> ';
	c += '      <th>申请日期<i class="up down"></i></th> ';
	c += '      <th>办理网点<i class="up down"></i></th> ';
	c += '      <th>状态<i class="up down"></i></th> ';
	c += '      <th>操作<i class="up down"></i></th> ';
	c += '   </tr> ';
	c += '</thead> ';

	for (var i = 0; i < data.length; i++) {
		c += '<tbody> ';
		c += ' <tr> ';
		c += '  <td>'+data[i][0]+'</td> ';
		c += '  <td>'+data[i][1]+'</td> ';
		c += '  <td>'+data[i][2]+'</td> ';
		c += ' <td>'+data[i][3]+'</td> ';
		c += ' <td title="番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行">'+data[i][4]+'</td> ';
		c += '  <td>'+data[i][5]+'</td> ';
		c += '   <td><i></i><em></em></td> ';
		c += ' </tr>     ';
		c += '  </tbody> ';
		c += ' </table> ';
	}

	$(".centent_bd").html(c);
	
}




/*function getItemHtml(row) {
	var c = "";
	c += "<ul>";
	c += "<li class=\"num1\">"+row[1]+"</li>";
	c += "<li class=\"num2\">"+row[2]+"</li>";
	var storeName = row[3];
	if(storeName==null || storeName=="null" ) {
		storeName="--";
	}
	c += "<li class=\"num3\">"+storeName+"</li>";
	c += "<li class=\"num4\">"+row[4]+"</li>";
	
	var fee = row[5];
	if(fee==null || fee=="null元" ) {
		fee="--元";
	}
	
	c += "<li class=\"num5\">"+fee+"</li>";
	c += "<li class=\"num6\">"+row[6]+"</li>";
	c += "<li class=\"num7 no_bd\" style=\"color: #0897ff; cursor: pointer;\"";
	c += "onclick=\"javascript:location.href='"+basePath+"background/verification/toVerificationDetail?id="+row[0]+"'\">查看</li>";
	c += "</ul>";
	return c;
}*/

$(function() {

	loadList();
	//分页跳转
	$("#pagearticles").live('click', function() {
		var maxpage = $(this).attr("maxpage");
		var newpage = $.trim($(".pagearticles").val());
		newpage = Number(newpage);
		maxpage = Number(maxpage);
		if (Number(newpage) > Number(maxpage)) {
			newpage = maxpage;
		}
		if (Number(newpage) < 1) {
			newpage = 1;
		}
		loadList({
			page : newpage
		});
	});

});




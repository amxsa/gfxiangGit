<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<!-- 删除苹果默认的工具栏和菜单栏 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<!-- 设置苹果工具栏颜色 -->
<meta name="format-detection" content="telephone=no, email=no" />
<!-- 忽略页面中的数字识别为电话，忽略email识别 -->
<!-- 启用360浏览器的极速模式(webkit) -->
<meta name="renderer" content="webkit">
<!-- 避免IE使用兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
<meta name="HandheldFriendly" content="true">
<!-- 微软的老式浏览器 -->
<meta name="MobileOptimized" content="320">
<!-- uc强制竖屏 -->
<meta name="screen-orientation" content="portrait">
<!-- QQ强制竖屏 -->
<meta name="x5-orientation" content="portrait">
<!-- UC强制全屏 -->
<meta name="full-screen" content="yes">
<!-- QQ强制全屏 -->
<meta name="x5-fullscreen" content="true">
<!-- UC应用模式 -->
<meta name="browsermode" content="application">
<!-- QQ应用模式 -->
<meta name="x5-page-mode" content="app">
<!-- windows phone 点击无高光 -->
<meta name="msapplication-tap-highlight" content="no">
<!-- 适应移动端end -->
<title>广东南粤银行</title>
<link rel="stylesheet" type="text/css" href="css/global.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="js/check.js" type="text/javascript"></script>
<script src="js/getJson.js" type="text/javascript"></script>
<script>
	
	
	function getItemHtml(row){
		var temp ='<div class="SesConCer">';
		temp+='<ul>';
		temp+='<li style=" width: 220px;">'+row.s+'</li>';
		temp+='<li style=" width: 130px;">'+row.phone+'</li>';
		temp+='<li style=" width: 150px;">'+row.rolename+'</li>';
		temp+='<li style="width: 130px;">';
		temp+='<a class="edit" href="javascript:void(0)" title="编辑" onclick="openeditwindow(\''+row.id+'\')"><i></i></a>';
		temp+='<a class="delet" href="javascript:void(0)" title="删除" onclick="deleteUser(\''+row.id+'\')"><i></i></a>';
		temp+='</li></ul></div>';
		return temp;
	}
	function genItems(data) {
		var rows = data.rows;
		var c = "";
		for ( var i = 0; i < rows.length; i++) {
			c += getItemHtml(rows[i]);
		}
		$("div.SesConTop").after(c);
	}
	
	$.loadList = function(parameters) {
		parameters = parameters || {};
		parameters.page = parameters.page || 1;
		parameters.pageSize = parameters.pageSize ||5;
		$("#pageing").html("");
		var start = (parameters.page - 1) * parameters.pageSize;
		
		$(".SesConCer").remove();
		var d = {
				start : start,
				limit : parameters.pageSize
			};
		//获取列表
		var s =sg_getList('/XiaoITest/xiaoi',d);
		getItemHtml(s);
	};
</script>
</head>
<body>
	<section class="wrap">
		<script type="text/javascript">
		function verify() {
			//提交获取结果
			ajaxSg.sg_ajaxClick({ url:'http://127.0.0.1:8080/ims/test/test.do'});
			alert(2)
			//获取单个实体
			sg_loadObject('/XiaoITest/xiaoi',{data:'id'});
		}
		</script>
		<div>==========================================================
		=====================================================================测试一</div>
		<div class="box" id="test1">
			<span>真实姓名：</span> 
			<input type="text" id="realname" name="realname" sg_check="" sg_json="*"/>
			<span>昵称：</span> 
			<input type="text" id="nickName" name="nickName" sg_check="" sg_json="*"/>
			<span>电话：</span> 
			<input type="text" id="phone" name="phone" sg_check="tel|电话格式不正确" sg_json="tel"/>
			<span>帐号所属平台：</span>
			<select id="workType" name="workType" sg_check="" sg_json="*">
			<option value="">请选择</option>
			<option value="1">微信平台</option>
			<option value="2">支付宝平台</option>
			</select>
			<a href="#" class="btn" onclick="verify()">发送</a>
		</div>
		</br></br></br></br></br>
		<div>==========================================================
		=====================================================================</div>
		<!-- <div class="box" id="test2">
			<span>真实姓名：</span> 
			<input type="text" id="realname" name="realname" sg_check="" sg_json="*"/>
			<span>昵称：</span> 
			<input type="text" id="nickName" name="nickName" sg_check="" sg_json="cname"/>
			<span>电话：</span> 
			<input type="text" id="phone" name="phone" sg_check="tel|电话格式不正确" sg_json="tel"/>
			<span>帐号所属平台：</span>
			<select id="workType" name="workType" sg_check="" sg_json="*">
			<option value="">请选择</option>
			<option value="1">微信平台</option>
			<option value="2">支付宝平台</option>
			</select>
			<a href="#" class="btn" onclick="verify()">发送</a>
		</div> -->
	</section>
</body>
</html>
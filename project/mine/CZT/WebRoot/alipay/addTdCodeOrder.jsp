<%@page import="cn.cellcom.czt.common.ConfLoad"%>
<%@page import="cn.cellcom.czt.common.CityAreaTool"%>
<%@page import="cn.cellcom.czt.common.Env"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	System.out.println(">>>>>>"+request.getRemoteAddr());
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("citys",Env.CITY_AREA.keySet());
	String[] areas = CityAreaTool.getArea(Env.CITY_AREA.keySet().iterator().next());
	request.setAttribute("areas",areas);
	String targetUrl=ConfLoad.getProperty("WECHAT_PAY_BACK_URL")+"CZT/alipay/addTdCodeOrder.jsp?openid="+request.getParameter("openid");
	pageContext.setAttribute("targetUrl", targetUrl);
	//response.sendRedirect(path+"/alipay/zanting.jsp");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>提交订单</title>
<link href="<%=path%>/css/alipay.css" rel="stylesheet" type="text/css">
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
		
	//var targetUrl = location.href.split('#')[0];
	var targetUrl = '${targetUrl}';
	
	$(function(){
		$.ajax({
			url : '<%=path%>/service/OrderPayAction_getJsSdkSign.do',// 跳转到 action
			data : {
				url : targetUrl
			},
			type : 'post',
			cache : false,
			timeout : 10000, 
			dataType : 'json',
			success : function(result) {
				if (result != null) {
					var timestamp = result.timestamp;
					var nonceStr = result.nonceStr;
					var signature = result.signature;
					console.log(timestamp + '--------' + nonceStr + '--------' + signature);
					wx.config({
						"debug" : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
						"appId" : 'wx2dcc25fc80501211', // 必填，公众号的唯一标识 生产：wx2dcc25fc80501211// 体验：wx56bbc8972aa013ce
						"timestamp" : timestamp, // 必填，生成签名的时间戳
						"nonceStr" : nonceStr, // 必填，生成签名的随机串
						"signature" : signature,// 必填，签名，见附录1
						"jsApiList" : [ 'chooseWXPay' ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					});
				}
			},
			error : function() {
				alert('请求超时，请稍后再试！');
			}
		});
	});
	
	
	
	var ajaxFormOption = {
        type:"post",  //提交方式  
        dataType:"json", //数据类型  
        url:"<%=path%>/service/OrderPayAction_payOrderBefore.do",
        success: function (result) { //提交成功的回调函数  
        	if(result!=null){
        		if(result.state==false){
        			alert(result.msg);
        		}else{
        			data =result.obj;
        			if(data.code==0){
		               // openDialog(data);
		                wx.chooseWXPay({
		                    timestamp: data.timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		                    nonceStr: data.nonceStr, // 支付签名随机串，不长于 32 位
		                    package: 'prepay_id=' + data.prepay_id, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		                    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		                    paySign: data.paySign, // 支付签名
		                    success: function (res) {
		                        alert('支付成功');
		                        window.location.href="<%=path%>/alipay/showProduct2.jsp?openid=${param.openid}";
		                    }
		                });
		            }else{
		                alert(data.desc);
		            }
        		}
	        }
        },
        error : function(result) {
			alert('请求超时，请稍后再试！');
		}
    };
	
	function payWXZF() {
		$('#form1').ajaxSubmit(ajaxFormOption);
	}
	
	
	$(function(){
		var city =$("#city").val();
		var area =$("#area").val();
		if(area!=null){
			$("#area").val(area);
		}else{
			getArea(city);
		}
	});
	function getArea(city){
		if(city=='')
			city =$("#city").val();
		$.post("<%=path%>/service/OrderPayAction_getCityArea.do",{"city":city},function(data){
			if(data!=''){
				arr = data.split(",");
				$("#area").empty();
				var areaStr="";
				for(i=0;i<arr.length;i++){
					areaStr+="<option value='"+arr[i]+"'>"+arr[i]+"</option>";
				}
				$("#area").html(areaStr);
			}
		});
		
	}
	function payYZF(){
		document.forms[0].submit();
	}
	function pay(){
		var chk_value =[];//定义一个数组      
        $('input[name="payType"]:checked').each(function(){
       		chk_value.push($(this).val());//将选中的值添加到数组chk_value中
        });
        if(chk_value[0]=='1'){
        	payYZF();
        }else{
        	payWXZF();
        }
		
	}
	function checkPayType(type){
		if(type=='1'){
			$("#wxzf").attr("checked",false);
			$("#yzf").attr("checked",true);
			$("#payTypeOnly").val("1");
		}else if(type=='2'){
			$("#wxzf").attr("checked",true);
			$("#yzf").attr("checked",false);
			$("#payTypeOnly").val("2");
		}
	}
	function bak(){
		window.location.href="<%=path%>/alipay/showProduct.jsp?openid=${param.openid}";
	}
</script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form action="<%=path%>/service/OrderPayAction_payOrderBefore.do"
		method="post" id="form1">
		<input type="hidden" name="unitPrice" value="${not empty param.unitPrice?param.unitPrice:requestScope.unitPrice }"
			id="unitPrice" /> 
		<input type="hidden" name="openid"
			value="${param.openid }" id="openid" />
		<input type="hidden" name="payTypeOnly"
			value="1" id="payTypeOnly" />
		<div class="top">
			<a class="back" onclick="bak();"><img
				src="<%=path%>/images/back.png" />
			</a>
			<h2>提交订单</h2>
			<span><a href="#"><img src="<%=path%>/images/home.png">
			</a>
			</span>
		</div>
		<div class="layout">
			<div class="zhifu">
				<h2>收货人信息</h2>
				<ul class="addr">
					<li><label>收货人</label><input type="text" name="name"
						maxlength="10"  value="" placeholder="请输入收货人姓名"  />
					</li>
					<li><label>手机</label><input type="text" name="telephone"
						maxlength="11" value="" placeholder="请输入手机号码" />
					</li>
					<li><label>地址</label> <select name="city"
						onchange="getArea('');" id="city">
							<c:forEach var="city" items="${requestScope.citys}">
								<option value="${city}">${city}</option>
							</c:forEach>
					</select> <select name="area" id="area">
							<c:forEach var="area" items="${requestScope.areas}"
								varStatus="idx">
								<option value="${area}">${area}</option>
							</c:forEach>
					</select></li>
					<li><label>&nbsp;</label><input type="text" name="address"
						maxlength="30" value="" placeholder="请输入详细联系地址" />
					</li>
					<li><label>邮编</label><input type="text" name="zipcode"
						maxlength="6" value="" placeholder="请输入邮编" />
					</li>
				</ul>
				<h2>商品信息</h2>
				<dl class="pro_info">
					<dt>
						<img src="<%=path%>/images/apliay/pro.jpg">
					</dt>
					<dd>
						<h4>辘辘LULU信息服务</h4>
						<h5>
							<span class="fl"><c:if test="${param.unitPrice=='588' }">基础版</c:if>
								<c:if test="${param.unitPrice=='788' }">wifi版</c:if>
							</span> <span class="fr"><em>￥${param.unitPrice }</em> x 1</span>
						</h5>
					</dd>
				</dl>
			</div>

			<div class="zf_fs">
				<input name="payType" id="yzf" type="checkbox" value="1"
					checked="checked"  onclick="checkPayType('1');">翼支付
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="payType" id="wxzf" type="checkbox" value="2"
				 onclick="checkPayType('2');">微信支付
			</div>
			
		</div>
		<div class="dbut">
			<a href="javaScript:pay();">支付</a>
		</div>
		
	</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@page import="com.threegms.sdplatform.util.StringUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String weid = StringUtil.getReqId(request,"weid");
//String fansId = StringUtil.getReqId(request,"fansId");
String openid = StringUtil.getReqId(request,"openid");
String basePath = SiteUtil.getSiteUrl();

%>
<!doctype html>
<html>
<base href="<%=basePath%>">
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
<title>企业申请页面</title>
<link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
<link rel="stylesheet" type="text/css" href="bank/sg/css/city.css" />
<link rel="stylesheet" type="text/css" href="bank/sg/css/companyapplyForm.css" />
<script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
<script src="bank/sg/js/mobiscroll.js" type="text/javascript"></script>
<script src="bank/sg/js/time.js" type="text/javascript"></script>
<script src="bank/sg/js/common.js" type="text/javascript"></script>
<script src="bank/sg/js/loadData.js" type="text/javascript"></script>
<script src="bank/sg/js/check.js" type="text/javascript"></script>
<script src="bank/sg/js/getJson.js" type="text/javascript"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<link href="bank/sg/css/mobiscroll.css" rel="stylesheet" type="text/css">
<script type="text/javascript"> 
    $(function(){
    	var d2={};
    	sg_getList("${pageContext.request.contextPath}/front/applyLoan/getLobby", d2,function(data){
			var rows = data.lobby;
			var c = "<option value=''>请选择</option>";
			for ( var i = 0; i < rows.length; i++) {
				c += '<option value="'+rows[i]["lobbyNo"]+'">'+rows[i]["name"]+'</option>';
			}
			$("#lobbyNo").html(c);
		});
        // 点击确定按钮的表单验证
		var d = {
				loanType:1
		 };
		 sg_getList("${pageContext.request.contextPath}/front/applyLoan/getTypeList", d,function(data){
			if (data.succ) {
				var rows = data.typeList;
				var c = "<option value=''>请选择</option>";
				for ( var i = 0; i < rows.length; i++) {
					c += '<option value="'+rows[i]["typeId"]+'">'+rows[i]["typeName"]+'</option>';
				}
				$("#applyLoanTypeId").html(c);
			}
		});
		$('.confirm_btn').click(function(event) {
			if ($("#bakMsg").prop("checked")==false){
					return false;
			}
			ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/applyLoan/saveCompanyApply'},function(data){
				if (data.succ) {
					$('.layer').fadeIn(100);        
					$('.layer-bg').fadeTo(100,0)  // 半透明
					$("#main-wrapper").hide();
				}else{
					$("#ishave").text("您有一笔贷款申请正在处理中，请勿重复提交");
					$('.layer').fadeIn(100);        
					$('.layer-bg').fadeTo(100,0)  // 半透明
					$("#main-wrapper").hide();
				}
				setTimeout("wx.closeWindow()", 3000);
			});
		});
   });
    </script>
</head>
<body>
	<div id="main-wrapper">
		<div class="main_content">
			<!-- 表单    -->
			<form action="">
				<div class="form_text">
					<!-- 每一个表单 -->
					<input  type="hidden" name="weid"  sg_json="*" value="<%=weid%>"/>
			        <input  type="hidden" name="openid"  sg_json="*" value="<%=openid  %>"/>
					<div class="text-fill">
						<!-- 企业名称 -->
						<div class="item">
							<span class="left_name">企业名称</span> <input type="text"
								placeholder="企业名称" class="ipt" name="companyName"
								id="companyName" sg_json="*" sg_check="empty|企业名称不能为空"/>
						</div>
						<!-- 贷款类型 -->
						<div class="item">
							<span class="left_name">贷款类型</span>
							<div class="sel_div">
								<select name="applyLoanTypeId" id="applyLoanTypeId" sg_json="*" sg_check="empty|请选择贷款类型">
								</select>
							</div>
						</div>
						<!-- 贷款金额 -->
						<div class="item">
							<span class="left_name">贷款金额</span> <input type="text"
								placeholder="贷款金额" class="ipt" name="applyMoney" id="applyMoney" sg_check="empty|贷款金额不能为空" sg_json="*"/>万元
						</div>
						<!-- 预期时间 
						<div class="item">
							<span class="left_name">预期时间</span>
							<div class="sel_div">
								<input placeholder="请选择时间" class="" readonly="readonly" name="apllyMoneyTime"
									id="apllyMoneyTime" type="text" >
							</div>
						</div>-->
						<!-- 企业类型 -->
						<div class="item">
							<span class="left_name">企业类型</span>
							<div class="sel_div">
								<select name="companyType" id="companyType" sg_json="*" sg_check="empty|企业类型不能为空">
									<option value="国有企业">国有企业</option>
									<option value="股份制">股份制</option>
									<option value="私营、个体">私营、个体</option>
								</select>
							</div>
						</div>
						<!-- 注册资金 -->
						<div class="item">
							<span class="left_name">注册资金</span> <input type="text"
								placeholder="注册资金" class="ipt" name="regFunds" id="regFunds"
								sg_json="*" sg_check="number|注册资金只能是数字"/>万元
						</div>
						<!-- 统一社会信用代码 -->
						<div class="item">
							<span class="left_name">统一社会信用代码</span> <input type="text"
								placeholder="请输入" class="ipt" name="societyCode"
								id="societyCode" sg_json="*" sg_check="empty|信用代码不能为空"/>
						</div>
						<!-- 注册地 -->
                             <input type="hidden" name="regPlace" id="regPlace" sg_json="*" sg_check="empty|注册地不能为空"/>
                        <div class="item">
       					 	<span class="left_name">注册地</span>
        					 <a id="expressArea" href="javascript:void(0)" class="dl_list">
            				 <dl >
           					 	<dt></dt>
           						<dd style="color: #7e7e7e;font-size: 18px;">注册地</dd>
				        	</dl>
				         	</a>
				    </div>
						<!-- 选择网点 -->
						<div class="item">
							<span class="left_name">选择网点</span>
							<div class="sel_div">
								<select name="lobbyNo" id="lobbyNo" sg_json="*" sg_check="empty|网点不能为空">
								</select>
							</div>
						</div>
						<!-- 联系人 -->
						<div class="item">
							<span class="left_name">联系人</span> <input type="text"
								placeholder="联系人" class="ipt" name="contacts" id="contacts"
								sg_json="*"  sg_check="empty|联系人不能为空"/>
						</div>
						<!-- 电话 -->
						<div class="item">
							<span class="left_name">电话</span> <input type="text"
								placeholder="电话" class="ipt" name="phone" id="phone" sg_json="*" maxlength="11"  sg_check="tel|请输入正确的电话"/>
						</div>

						<div class="item pd">
							<div class="check_box">
								<input type="checkbox" id="bakMsg" checked="true"/>
							</div>
							<div class="item_right">
								<p class="txt">
									本人已阅读并同意<em onclick="javascript:location.href='${pageContext.request.contextPath}/bank/sg/loan/bankMsg.jsp'">《韶关市区农村信用合作联社信贷业务电子征信协议》</em>
								</p>
							</div>
						</div>
						<div class="confirm_btn">提交申请</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--选择地区弹层-->
        <section id="areaLayer" class="express-area-box">
            <header>
                <h3>选择地区</h3>
                <a id="backUp" class="back" href="javascript:void(0)" title="返回"></a>
                <a id="closeArea" class="close" href="javascript:void(0)" title="关闭"></a>
            </header>
            <article id="areaBox">
                <ul id="areaList" class="area-list"></ul>
            </article>
        </section>
        <!--遮罩层-->
        <div id="areaMask" class="mask"></div>
        <script src="bank/sg/js/jquery.area.js"></script>
	<!-- 模态窗口 -->
	<div class="layer">
		<p id="ishave">申请成功，请等待核查</p>
	</div>
	<div class="layer-bg"></div>
</body>
</html>
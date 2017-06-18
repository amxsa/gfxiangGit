<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String basePath = SiteUtil.getSiteUrl();
%>

<!doctype html>
<head>
<base href="<%=basePath%>">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" /><!-- 删除苹果默认的工具栏和菜单栏 -->
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /><!-- 设置苹果工具栏颜色 -->
    <meta name="format-detection" content="telephone=no, email=no" /><!-- 忽略页面中的数字识别为电话，忽略email识别 -->
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
    <title>经理查看申请的信息</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/CompanyInformation.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
     <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
	<script src="bank/sg/js/check.js" type="text/javascript"></script>
	 <script src="bank/sg/js/common.js" type="text/javascript"></script>
	<script src="bank/sg/js/getJson.js" type="text/javascript"></script>
    <script type="text/javascript">
	function GetURLParameter(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	function pageJump(status){
		var managerId=$("#managerId").val();
		var d = {
			status:status,
			managerId:managerId
		};
        //获取数据
		sg_getList("${pageContext.request.contextPath}/front/applyLoan/sendScore", d,function(data){
			if(data.succ){
				//是否需要进入现场核查  0为否  则流程结束  进入到消息通知界面
				if(data.status==0){
					location.href="${pageContext.request.contextPath}/bank/sg/score/scoreMsg.jsp?status=0&managerId="+managerId;
				}else{
					//1需要现场核查
					location.href="${pageContext.request.contextPath}/bank/sg/loan/sceneVisit.jsp?status=0&managerId="+managerId;
				}
			}
		});
	}
    $(function(){
    	var managerId=GetURLParameter("managerId");
    	$("#managerId").val(managerId);
		var d = {
			managerId:managerId
		};
        //获取数据
		sg_getList("${pageContext.request.contextPath}/front/applyLoan/getLoanApply", d,function(data){
			var cust=data["cust"];
			var bus=data["bus"];
			$("#custName").text(cust.contacts);
			$("#typeName").text(data.typeName);
			$("#applyMoney").text(bus.applyMoney+"万元");
			$("#phone").text(cust.phone);
			$("#monthIncome").text(data.monthIncome);
			if(bus.returnMsg!=null){
				$("#returnMsg").text(bus.returnMsg);
			}
			$("#userPhoto").attr('src',data.userPhoto);
			$("#companyName").text(cust.companyName);
			$("#companyType").text(cust.companyType);
			$("#regFunds").text(cust.regFunds+"万元");
			$("#regPlace").text(cust.regPlace);
			$("#societyCode").text(cust.societyCode);
			$("#custli").text(data.custli);
		});
        $('.confirm_btn').click(function(event) {
        	var ispass=$('input[name="ispass"]:checked').val();
			$('#isPass').val(ispass);
			ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/applyLoan/checkDoc'},function(data){
				if (data.succ) {
					if(ispass=="0"){
						location.href="${pageContext.request.contextPath}/bank/sg/score/scoreMsg.jsp?status=2&managerId="+managerId;
					}else{
						$('.layer').fadeIn(100);        
						$('.layer-bg').fadeTo(100,0.5)  // 半透明
					}
				}else{
					msgBox("资料核查失败，请重试",function(){
					});
				}
			});
		});
    	// 点击关闭按钮，关闭窗口，     点击背景也关闭
    	// 用了并集选择器
	    $('.layer .yes_btn,.layer-bg,.no_btn').click(function(event) {
	        $('.layer,.layer-bg').fadeOut(100)
	        // fadeOut淡出最终 display:none
	    });
    })
    </script>
</head>
<body>
    <!-- 申请人信息 -->
    <div class="box">
        <div class="box_hd main_pd">
            申请人信息
        </div>
        <div class="box_bd main_pd2 ">
           <p>
                <span>申请人：</span>
                <em id="custName"></em>
            </p>
            <p>
                <span>意　向：</span>
                <em id="typeName"></em>
            </p>
            <p>
                <span>金　额：</span>
                <em id="applyMoney"></em>
            </p>
            <p>
                <span>电　话：</span>
                <em id="phone"></em>
            </p>
            <!-- 照片 -->
            <div class="pic_box">
                <img src="bank/sg/img/cilent.png" alt="" class="cilent" id="userPhoto"/>
                <p id="custli"></p>
            </div>
        </div>
       
    </div>
    <div class="box">
        <div class="box_hd main_pd">
            企业信息
        </div>
        <div class="box_bd main_pd2 ">
            <p>
                <span>企业名称：</span>
                <em id="companyName"></em>
            </p>
            <p>
                <span>企业类型：</span>
                <em id="companyType"></em>
            </p>
            <p>
                <span>注册资金：</span>
                <em id="regFunds"></em>
            </p>
            <p>
                <span>注册地点：</span>
                <em id="regPlace"></em>
            </p>
            <p>
                <span>统一社会信用代码：</span>
                <em id="societyCode"></em>
            </p>
             <!-- <p>
                <span>是否已经同意《信贷征信协议：</span>
                <em>同意</em>
            </p> -->
        </div>
       
    </div>
    <!-- 客户留言 -->
    <div class="box">
        <div class="box_hd main_pd">
            客户留言
        </div>
        <div class="box_bd main_pd2">
            <p class="leave_p" id="returnMsg"></p>
        </div>
    </div>
    <!-- 审核 -->
    <div class="box">
        <div class="box_hd main_pd">
            审核
        </div>
        <div class="box_bd main_pd2">
            <div class="box_list">
                <span>是否通过审核：</span>
                <p class="radio_box mw">
                    <input type="radio" name="ispass" class="get_chose" value="1"/>
                    <label for="">是</label>
                </p>
                <p class="radio_box">
                    <input type="radio" name="ispass" class="get_chose" value="0"/>
                    <label for="">否</label>
                </p>
                 <input type="hidden" name="isPass" id="isPass" sg_json="*"/>
                  <input type="hidden" name="managerId" id="managerId" sg_json="*"/>
            </div>
            <textarea name="checkDataInfo" id="checkDataInfo" sg_json="*" cols="30" rows="10" class="txt_area" placeholder="审核意见：例如所交材料齐全，已经核实均为真实材料。"></textarea>
        </div>
    </div>
    <!-- 信息补录 -->
    <div class="box">
        <div class="box_hd main_pd">
            信息补录
        </div>
        <div class="box_bd main_pd2 nb">
            <textarea name="addInfo" id="addInfo" sg_json="*" cols="30" rows="10" class="txt_area" placeholder="该客户属于我行钻石VIP客户，在我行信用良好。"></textarea>
        </div>
        <div class="confirm_btn ">确认审核</div>
    </div>
     <!-- 模态窗口 -->
        <div class="layer">
            <p>是否需要现场核查？</p>
            <div class="no_btn" onclick="pageJump(0)">否</div>
            <div class="yes_btn" onclick="pageJump(1)">是</div>
        </div>
        <div class="layer-bg"></div>
</body>
</html>
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
    <title>现场审核</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/sceneVisit.css" />
    <script type="text/javascript" src="bank/sg/js/jquery-1.9.1.min.js"></script>
    <script src="bank/sg/js/mobiscroll.js" type="text/javascript"></script>
    <link href="bank/sg/css/mobiscroll.css" rel="stylesheet" type="text/css">
    <script src="bank/sg/js/loadData.js" type="text/javascript"></script>
    <script src="bank/sg/js/common.js" type="text/javascript"></script>
	<script src="bank/sg/js/check.js" type="text/javascript"></script>
	<script src="bank/sg/js/getJson.js" type="text/javascript"></script>
    <script src="bank/sg/js/wxjsutil.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript"> 
	function GetURLParameter(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
    $(document).ready(function(){
        //编辑时调用百度地图，以该网点作为地图作为中心点展现
        var bjwpx = $("#centerbjwpx").val();
        var bjwpy = $("#centerbjwpy").val();
        var map = new BMap.Map("bd-map-pick");
        var pointnew = new BMap.Point(bjwpx, bjwpy);
        map.centerAndZoom(pointnew, 15);
        map.enableScrollWheelZoom();
        map.clearOverlays();
        var marker = new BMap.Marker(pointnew);
        map.addOverlay(marker);
        //点击图标获取当前坐标
        map.addEventListener("click",function(e) {
			 G("lobbylong").value = e.point.lng;
			 document.getElementById("lobbylat").value = e.point.lat;
        });
		
		var managerId=GetURLParameter("managerId");
		$("#managerId").val(managerId);
		var d = {
			managerId:managerId
		};
            //获取数据
		sg_getList("${pageContext.request.contextPath}/front/applyLoan/getLoanApply", d,function(data){
			var cust=data["cust"];
			var bus=data["bus"];
			if(bus.clientType==1){
				$("#custName").text(cust.custName);
				$("#idType").text("身份证");
				$("#idCardNo").text(cust.idCardNo);
			}else{
				$("#custName").text(cust.companyName);
				$("#idType").text("社会信用代码");
				$("#idCardNo").text(cust.societyCode);
			}
			$("#typeName").text(data.typeName);
			$("#applyMoney").text(bus.applyMoney+"万元");
			$("#phone").text(cust.phone);
			$("#userPhoto").attr('src',data.userPhoto);
			$("#custli").text(data.custli);
			if(bus.returnMsg!=null){
				$("#returnMsg").text(bus.returnMsg);
			}
		});
        $('.confirm_btn').click(function(event) {
        	var ispass=$('input[name="ispass"]:checked').val();
			$('#isPass').val(ispass);
			$('#checkOfflineInfo').val($('#checkOffline').val());
			$('#checkTime').val($('#appDate').val());
			ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/applyLoan/checkSpot'},function(data){
				if (data.succ) {
					if(ispass=="1"){
						location.href="${pageContext.request.contextPath}/bank/sg/score/scoreMsg.jsp?status=0&managerId="+managerId;
					}else{
						location.href="${pageContext.request.contextPath}/bank/sg/score/scoreMsg.jsp?status=2&managerId="+managerId;
					}
				}else{
					msgBox("资料核查失败，请重试",function(){
					});
				}
			});
		});
        
        $('.add_pic').click(function(event) {
        	preview1();
		});
    })
     
    //根据地址读取坐标
 function loadlngandlatbyaddress() {
	var address=$("#checkPlace").val();
	if(address){
			$.ajax({
				url : "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=PTDPK1xWEnT7WY65lPDW93Ly",
				type : "post",
				dataType : "jsonp",
				timeout : 10000,
				async : false,
				cache : false,
				success : function(data) {
					var map = new BMap.Map("bd-map-pick");
					var pointnew = new BMap.Point(data.result.location.lng,data.result.location.lat);
					map.centerAndZoom(pointnew, 15);
					map.enableScrollWheelZoom();
					map.clearOverlays();
					var marker = new BMap.Marker(pointnew);
					map.addOverlay(marker);
					//点击图标获取当前坐标
					map.addEventListener("click",function(e) {
						G("lobbylong").value = e.point.lng;
						document.getElementById("lobbylat").value = e.point.lat;
					}); 
				},
				error : function(a, b, c, d, e) {
					//alert(e);
				}
			});
	}
}
    // 上传图片
        var id=0;
        function preview1()
		{
			id++;
			$(".add_pic").before('<div id='+id+' class="photo" ></div>');
			getWxPara();
            wxReady();
       		wx.chooseImage({
     		  count: 1, // 默认9
     		  sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
     		  sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
     		  success: function (res) {
     			var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
     			wx.uploadImage({
     			  localId: localIds.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
     			  isShowProgressTips: 1, // 默认为1，显示进度提示
     			  success: function (res) {
     			    var serverId = res.serverId; 
     			    var managerId=$("#managerId").val();
     			  	var d = {
     			  		serverId:serverId,
     			  		managerId:managerId
     				};
     		            //获取数据
     				sg_getList("${pageContext.request.contextPath}/front/upload/uploadImgForCheck", d,function(data){
     					$("#checkPic").val(data.ckpic);
     					$("#"+id).html('<img src="' + data.imgUrl + '" />'+'<span class="close_btn"></span>');
    					$(".close_btn").on("click",function() {
    						moveImg(data.imgUrl);
    						$(this).parent(".photo").remove();
    						$(this).parent(".photo").children('img,.close_btn').remove();
    					}); 
    					$(".pic_right .photo").on("click",function(){
                            $(this).children('.close_btn').hide();
                            $(".big_img").show();
                            $(".big_img img").attr("src",data.imgUrl )
                        });
                        $(".big_img img").on("click",function(){
                            $(".photo").children('.close_btn').show();
                            $(".big_img").hide();
                            $(".big_img img").attr("src","" )
                        })
     				});
     				}
     			});
     		  }
     		});
		};
		
		function moveImg(imgurl){
			var d = {
 			  		imgurl:imgurl
 				};
			sg_getList("${pageContext.request.contextPath}/front/upload/deleteImg", d,function(data){
					
			}); 
		}
            
    // 时间插件
        $(function () {
            var currYear = (new Date()).getFullYear();  
            var opt={};
            opt.date = {preset : 'date'};
            opt.datetime = {preset : 'datetime'};
            opt.time = {preset : 'time'};
            opt.default = {
                theme: 'android-ics light', //皮肤样式
                display: 'modal', //显示方式 
                mode: 'scroller', //日期选择模式
                dateFormat: 'yyyy-mm-dd',
                lang: 'zh',
                showNow: true,
                nowText: "今天",
                startYear: currYear - 10, //开始年份
                endYear: currYear + 10 //结束年份
            };

            $("#appDate").mobiscroll($.extend(opt['date'], opt['default']));
            var optDateTime = $.extend(opt['datetime'], opt['default']);
            var optTime = $.extend(opt['time'], opt['default']);
            $("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
            $("#appTime").mobiscroll(optTime).time(optTime);
        });
    </script>
</head>
<body>
    <!-- 申请人信息 -->
    <div class="box">
        <div class="box_hd main_pd">
        <input type="hidden" id="openid" />
		<input type="hidden" id="appid"/>
		<input type="hidden" id="secret"  />
		<input type="hidden" id="weid"  />
		<input type="hidden" id="signature"/>
		<input type="hidden" id="timestamp"/>
		<input type="hidden" id="nonceStr"/>
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
            <p>
                <span>证　件：</span>
                <em id="idType"></em>
            </p>
            <p class="bb">
                <span>　　　　</span>
                <em id="idCardNo"></em>
            </p> 
            <!-- 照片 -->
            <div class="pic_box">
                <img src="bank/sg/img/cilent.png" alt="" class="cilent" id="userPhoto"/>
                <p id="custli"></p>
            </div>
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
                    <input type="radio" name="ispass" id="ispass" class="get_chose" value="1"/>
                    <label for="">是</label>
                </p>
                <p class="radio_box">
                    <input type="radio" name="ispass" id="ispass" class="get_chose" value="0"/>
                    <label for="">否</label>
                </p>
                <input type="hidden" name="isPass" id="isPass" sg_json="*" sg_check="empty|请选择结果"/>
                <input type="hidden" name="checkOfflineInfo" id="checkOfflineInfo" sg_json="*"/>
                <input type="hidden" name="managerId" id="managerId" sg_json="*"/>
                <input type="hidden" name="checkTime" id="checkTime" sg_json="*" sg_check="empty|核查时间不能为空"/>
            </div>
            <!-- 地图 -->
            <div class="item" style="margin-bottom:10px;border:0;">
                 <span class="left_name place">地　点：</span>
                  <input  type="text" placeholder="地点" class="ipt" name="checkPlace" sg_check="empty|请输入核查地点" id="checkPlace" sg_json="*" onBlur="loadlngandlatbyaddress()"/>
              </div>
                 <input type="hidden" id="centerbjwpx" value="113.347702" />
                <input type="hidden" id="centerbjwpy" value="23.142596" /> 
                 <!-- 地图 -->
                <script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>
                <script type="text/javascript">
                    function G(id) {
                        return document.getElementById(id);
                    }
                </script>
                 <div id="bd-map-pick" style="width: 100%; height: 250px; margin: 0 auto;"></div>
           
            <!-- 上传照片 -->
            <div class="item pd" style="border:0;">
                 <div class="pic_left">
                     <p>上　传</p>
                     <p>照　片</p>
                 </div>
                 <div class="pic_right">
                     <div class="add_pic">
                        <input type="hidden" name="checkPic" id="checkPic" sg_json="*"/>
                    </div>
                 </div>
             </div>
            <!-- 时间 -->
            <div class="item" style="margin-bottom:10px;border:0;">
                 <span class="left_name place">时　间：</span>
                 <div class="sel_div">
                    <input class="" readonly="readonly" name="appDate" id="appDate" type="text" style="border: 1px solid #bfbfbf;">
                 </div>
             </div>
             <!-- 核查人 -->
             <div class="item" style="margin-bottom:10px;border:0;">
                 <span class="left_name place">核查人：</span>
                 <input  type="text" placeholder=""  class="ipt" name="veriman" id="veriman" sg_json="*"/>
            </div>
            <textarea name="checkOffline" id="checkOffline" cols="30" rows="10" class="txt_area" placeholder="审核意见：例如所交材料齐全，已经核实均为真实材料。"></textarea>
            <div class="confirm_btn">确认并完成核查</div>    
        </div> 
    </div>
    <!-- 弹出大照片 -->
    <div class="big_img">
        <img src="img/bigimg.png" alt="" />
    </div>
      
</body>
</html>
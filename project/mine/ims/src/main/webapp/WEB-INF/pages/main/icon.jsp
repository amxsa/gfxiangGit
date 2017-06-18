<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/pages/common.jspf"%>
<html>
<head>
<title>选择图标</title>
 <link rel="stylesheet" href="${basePath}/common/font/demo.css">
 <style type="text/css">
 	.code{display: none}
 	.name{
 		cursor: pointer;
 	}
 	
 </style>
 <script type="text/javascript">
 	$(function(){
 		$(":radio").click(function(){
	 		parent.$("#getIcon").html('&#xe'+$(this).val())
	 		parent.$("#icon").val('&#xe'+$(this).val())
 		});
 	})
 </script>
</head>

<div class="main markdown">
        <ul class="icon_lists clear">
                <li>
                <i class="icon iconfont">&#xe600;</i>
                    <div class="name">客服优先</div>
                    <div class="code" >&amp;#xe600;</div>
                    <input type="radio"  name="icon" value="600;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe601;</i>
                    <div class="name">咖啡</div>
                    <div class="code">&amp;#xe601;</div>
                    <input type="radio"  name="icon" value="601;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe602;</i>
                    <div class="name">图书</div>
                    <div class="code">&amp;#xe602;</div>
                    <input type="radio"  name="icon" value="602;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe603;</i>
                    <div class="name">列表</div>
                    <div class="code">&amp;#xe603;</div>
                    <input type="radio"  name="icon" value="603;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe604;</i>
                    <div class="name">购物车满</div>
                    <div class="code">&amp;#xe604;</div>
                    <input type="radio"  name="icon" value="604;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe605;</i>
                    <div class="name">皇冠</div>
                    <div class="code">&amp;#xe605;</div>
                    <input type="radio"  name="icon" value="605;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe606;</i>
                    <div class="name">钻石</div>
                    <div class="code">&amp;#xe606;</div>
                    <input type="radio"  name="icon" value="606;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe607;</i>
                    <div class="name">天猫形象2</div>
                    <div class="code">&amp;#xe607;</div>
                    <input type="radio"  name="icon" value="607;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe608;</i>
                    <div class="name">换一批</div>
                    <div class="code">&amp;#xe608;</div>
                    <input type="radio"  name="icon" value="608;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe616;</i>
                    <div class="name">AIS_行列icon</div>
                    <div class="code">&amp;#xe616;</div>
                    <input type="radio"  name="icon" value="616;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe63a;</i>
                    <div class="name">iconfont-5</div>
                    <div class="code">&amp;#xe63a;</div>
                    <input type="radio"  name="icon" value="63a;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe642;</i>
                    <div class="name">二维码</div>
                    <div class="code">&amp;#xe642;</div>
                    <input type="radio"  name="icon"value="642;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe645;</i>
                    <div class="name">垃圾箱</div>
                    <div class="code">&amp;#xe645;</div>
                    <input type="radio"  name="icon" value="645;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe646;</i>
                    <div class="name">链接</div>
                    <div class="code">&amp;#xe646;</div>
                    <input type="radio"  name="icon" value="646;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe648;</i>
                    <div class="name">闹钟</div>
                    <div class="code">&amp;#xe648;</div>
                    <input type="radio"  name="icon" value="648;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe649;</i>
                    <div class="name">扫一扫</div>
                    <div class="code">&amp;#xe649;</div>
                    <input type="radio"  name="icon" value="649;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe64a;</i>
                    <div class="name">上翻</div>
                    <div class="code">&amp;#xe64a;</div>
                    <input type="radio"  name="icon" value="64a;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe64b;</i>
                    <div class="name">设置</div>
                    <div class="code">&amp;#xe64b;</div>
                    <input type="radio"  name="icon" value="64b;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe64c;</i>
                    <div class="name">声音</div>
                    <div class="code">&amp;#xe64c;</div>
                    <input type="radio"  name="icon" value="64c;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe64d;</i>
                    <div class="name">时间</div>
                    <div class="code">&amp;#xe64d;</div>
                    <input type="radio"  name="icon" value="64d;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe64e;</i>
                    <div class="name">收货地址</div>
                    <div class="code">&amp;#xe64e;</div>
                    <input type="radio"  name="icon" value="64e;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe64f;</i>
                    <div class="name">首页</div>
                    <div class="code">&amp;#xe64f;</div>
                    <input type="radio"  name="icon" value="64f;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe650;</i>
                    <div class="name">刷新</div>
                    <div class="code">&amp;#xe650;</div>
                    <input type="radio"  name="icon" value="650;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe651;</i>
                    <div class="name">搜索</div>
                    <div class="code">&amp;#xe651;</div>
                    <input type="radio"  name="icon" value="651;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe652;</i>
                    <div class="name">锁</div>
                    <div class="code">&amp;#xe652;</div>
                    <input type="radio"  name="icon" value="652;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe655;</i>
                    <div class="name">我的订单</div>
                    <div class="code">&amp;#xe655;</div>
                    <input type="radio"  name="icon" value="655;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe656;</i>
                    <div class="name">我的反馈</div>
                    <div class="code">&amp;#xe656;</div>
                    <input type="radio"  name="icon" value="656;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe657;</i>
                    <div class="name">我的红包</div>
                    <div class="code">&amp;#xe657;</div>
                    <input type="radio"  name="icon" value="657;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe65a;</i>
                    <div class="name">我的优惠券</div>
                    <div class="code">&amp;#xe65a;</div>
                    <input type="radio"  name="icon" value="65a;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe65d;</i>
                    <div class="name">向上箭头</div>
                    <div class="code">&amp;#xe65d;</div>
                    <input type="radio"  name="icon" value="65d;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe65e;</i>
                    <div class="name">向下箭头</div>
                    <div class="code">&amp;#xe65e;</div>
                    <input type="radio"  name="icon" value="65e;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe65f;</i>
                    <div class="name">向右箭头</div>
                    <div class="code">&amp;#xe65f;</div>
                    <input type="radio"  name="icon" value="65f;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe660;</i>
                    <div class="name">向左箭头</div>
                    <div class="code">&amp;#xe660;</div>
                    <input type="radio"  name="icon" value="660;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe661;</i>
                    <div class="name">眼睛</div>
                    <div class="code">&amp;#xe661;</div>
                    <input type="radio"  name="icon"value="661;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe662;</i>
                    <div class="name">意见反馈</div>
                    <div class="code">&amp;#xe662;</div>
                    <input type="radio"  name="icon" value="662;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe663;</i>
                    <div class="name">照相机</div>
                    <div class="code">&amp;#xe663;</div>
                    <input type="radio"  name="icon" value="663;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe665;</i>
                    <div class="name">消息中心</div>
                    <div class="code">&amp;#xe665;</div>
                    <input type="radio"  name="icon" value="665;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe666;</i>
                    <div class="name">另存为</div>
                    <div class="code">&amp;#xe666;</div>
                    <input type="radio"  name="icon" value="666;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe667;</i>
                    <div class="name">new</div>
                    <div class="code">&amp;#xe667;</div>
                    <input type="radio"  name="icon" value="667;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe66c;</i>
                    <div class="name">火</div>
                    <div class="code">&amp;#xe66c;</div>
                    <input type="radio"  name="icon" value="66c;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe653;</i>
                    <div class="name">导航</div>
                    <div class="code">&amp;#xe653;</div>
                    <input type="radio"  name="icon" value="653;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe654;</i>
                    <div class="name">地址</div>
                    <div class="code">&amp;#xe654;</div>
                    <input type="radio"  name="icon" value="654;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe6a7;</i>
                    <div class="name">radio button</div>
                    <div class="code">&amp;#xe6a7;</div>
                    <input type="radio"  name="icon" value="6a7;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe6b0;</i>
                    <div class="name">编辑</div>
                    <div class="code">&amp;#xe6b0;</div>
                    <input type="radio"  name="icon" value="6b0;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe6bc;</i>
                    <div class="name">个人_2</div>
                    <div class="code">&amp;#xe6bc;</div>
                    <input type="radio"  name="icon" value="6bc;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe6c5;</i>
                    <div class="name">个人_fill</div>
                    <div class="code">&amp;#xe6c5;</div>
                    <input type="radio"  name="icon" value="6c5;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe6db;</i>
                    <div class="name">tab_收起</div>
                    <div class="code">&amp;#xe6db;</div>
                    <input type="radio"  name="icon" value="6db;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe6dc;</i>
                    <div class="name">tab_下拉</div>
                    <div class="code">&amp;#xe6dc;</div>
                    <input type="radio"  name="icon" value="6dc;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe718;</i>
                    <div class="name">safari</div>
                    <div class="code">&amp;#xe718;</div>
                    <input type="radio"  name="icon" value="718;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe67c;</i>
                    <div class="name">30－1</div>
                    <div class="code">&amp;#xe67c;</div>
                    <input type="radio"  name="icon" value="67c;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe719;</i>
                    <div class="name">赞_S</div>
                    <div class="code">&amp;#xe719;</div>
                    <input type="radio"  name="icon" value="719;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe684;</i>
                    <div class="name">聚收藏gift</div>
                    <div class="code">&amp;#xe684;</div>
                    <input type="radio"  name="icon" value="684;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe685;</i>
                    <div class="name">礼物</div>
                    <div class="code">&amp;#xe685;</div>
                    <input type="radio"  name="icon" value="685;"/>
                </li>
            
                <li>
                <i class="icon iconfont">&#xe687;</i>
                    <div class="name">语音</div>
                    <div class="code">&amp;#xe687;</div>
                    <input type="radio"  name="icon" value="687;"/>
                </li>
        </ul>
     </div>
     <!-- <div><input type="button" value="关闭" onclick="close()" style="margin-bottom: 0px;width: 30px;height: 20px;position: absolute;" /></div> -->
</body>
</html>
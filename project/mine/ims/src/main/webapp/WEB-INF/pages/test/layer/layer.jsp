<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/pages/common.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>layer</title>
<style type="text/css">
	.testDiv{
        height: 30px;
	    line-height: 30px;
	    width: 150px;
	    text-align: center;
	    cursor:pointer;
	    border-style: solid;
	    border-width: 1px;
	    border-color: blue;
	    display: inline-block;
    }
</style>
</head>
<body>
		<script type="text/javascript">
				function cty(){
					layer.alert('内容')
				}
		</script>
		<div class="testDiv" onclick="cty()">初体验</div>
		
		<script type="text/javascript">
				function kzpf(){
					layer.alert('内容', {
						  icon: 1,
						  skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
					})
				}
		</script>
		<div class="testDiv" onclick="kzpf()">第三方扩展皮肤</div>
		
		<script type="text/javascript">
				function xwk(){
					layer.confirm('您是如何看待前端开发？', {
						  btn: ['重要','奇葩'] //按钮
						}, function(){
						  layer.msg('的确很重要', {icon: 1});
						}, function(){
						  layer.msg('也可以这样', {
						    time: 20000, //20s后自动关闭
						    btn: ['明白了', '知道了']
						  });
					});
				}
		</script>
		<div class="testDiv" onclick="xwk()">询问框</div>
		
		<script type="text/javascript">
				function tsc(){
					layer.msg('玩命提示中');
				}
		</script>
		<div class="testDiv" onclick="tsc()">提示层</div>
		
		<script type="text/javascript">
				function mlslf(){
					layer.alert('墨绿风格，点击确认看深蓝', {
						  skin: 'layui-layer-molv' //样式类名
						  ,closeBtn: 0
						}, function(){
						  layer.alert('偶吧深蓝style', {
						    skin: 'layui-layer-lan'
						    ,closeBtn: 0
						    ,anim: 4 //动画类型
						  });
					});
				}
		</script>
		<div class="testDiv" onclick="mlslf()">墨绿深蓝风</div>
		
		<script type="text/javascript">
				function bhy(){
					layer.open({
						  type: 1,
						  shade: false,
						  title: false, //不显示标题
						  content: $('.layer_notice'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
						  cancel: function(){
						    layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
						  }
						});
				}
		</script>
		<div class="testDiv" onclick="bhy()">捕获页</div>
		
		<script type="text/javascript">
				function ymc(){
					layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['420px', '240px'], //宽高
						  content: 'html内容'
					});
				}
		</script>
		<div class="testDiv" onclick="ymc()">页面层</div>
		
		<script type="text/javascript">
				function zdy(){
					layer.open({
						  type: 1,
						  skin: 'layui-layer-demo', //样式类名
						  closeBtn: 0, //不显示关闭按钮
						  anim: 2,
						  shadeClose: true, //开启遮罩关闭
						  content: '内容'
					});
				}
		</script>
		<div class="testDiv" onclick="zdy()">自定页</div>
		
		<script type="text/javascript">
				function tips(){
					layer.tips('Hi，我是tips', '吸附元素选择器，如#id');
				}
		</script>
		<div class="testDiv" onclick="tips()">tips层</div>
		
		<script type="text/javascript">
				function iframe1(){
					layer.open({
						  type: 2,
						  title: 'layer mobile页',
						  shadeClose: true,
						  shade: 0.8,
						  area: ['380px', '90%'],
						  content: 'http://layer.layui.com/mobile/' //iframe的url
						}); 
				}
		</script>
		<div class="testDiv" onclick="iframe1()">iframe层</div>
		
		<script type="text/javascript">
				function iframe2(){
					layer.open({
						  type: 2,
						  title: false,
						  closeBtn: 0, //不显示关闭按钮
						  shade: [0],
						  area: ['340px', '215px'],
						  offset: 'rb', //右下角弹出
						  time: 2000, //2秒后自动关闭
						  anim: 2,
						  content: ['test/guodu.html', 'no'], //iframe的url，no代表不显示滚动条
						  end: function(){ //此处用于演示
						    layer.open({
						      type: 2,
						      title: '很多时候，我们想最大化看，比如像这个页面。',
						      shadeClose: true,
						      shade: false,
						      maxmin: true, //开启最大化最小化按钮
						      area: ['893px', '600px'],
						      content: 'http://fly.layui.com/'
						    });
						  }
					});
				}
		</script>
		<div class="testDiv" onclick="iframe2()">iframe窗</div>
		
		<script type="text/javascript">
				function jzc(){
					//加载层
					var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
				}
		</script>
		<div class="testDiv" onclick="jzc()">加载层</div>
		
		<script type="text/javascript">
				function litileTips(){
					layer.tips('我是另外一个tips，只不过我长得跟之前那位稍有些不一样。', '吸附元素选择器', {
						  tips: [2, '#3595CC'],
						  time: 4000
						});
				}
		</script>
		<div class="testDiv" onclick="litileTips()">小tips</div>
		
		<script type="text/javascript">
				function prompt(){
					layer.prompt({title: '输入任何口令，并确认', formType: 1}, function(pass, index){
						  layer.close(index);
						  layer.prompt({title: '随便写点啥，并确认', formType: 2}, function(text, index){
						    layer.close(index);
						    layer.msg('演示完毕！您的口令：'+ pass +'您最后写下了：'+text);
						  });
						});
				}
		</script>
		<div class="testDiv" onclick="cty()">prompt层</div>
		
		<script type="text/javascript">
				function tab(){
					layer.tab({
						  area: ['600px', '300px'],
						  tab: [{
						    title: 'TAB1', 
						    content: '内容1'
						  }, {
						    title: 'TAB2', 
						    content: '内容2'
						  }, {
						    title: 'TAB3', 
						    content: '内容3'
						  }]
					});
				}
		</script>
		<div class="testDiv" onclick="tab()">tab层</div>
		
		<script type="text/javascript">
				function xcc(){
					$.getJSON('test/photos.json?v='+new Date, function(json){
						  layer.photos({
						    photos: json //格式见API文档手册页
						    ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
						  });
						});
				}
		</script>
		<div class="testDiv" onclick="xcc()">相册层</div>
		
		<script type="text/javascript">
				function loading(){
					//loading层
					var index = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
				}
		</script>
		<div class="testDiv" onclick="loading()">loading层</div>
		
		<script type="text/javascript">
				function mprompt(){
					//默认prompt
					layer.prompt(function(val, index){
					  layer.msg('得到了'+val);
					  layer.close(index);
					});
				}
		</script>
		<div class="testDiv" onclick="mprompt()">默认prompt</div>
		
		<script type="text/javascript">
				function dmt(){
					//iframe层-多媒体
					layer.open({
					  type: 2,
					  title: false,
					  area: ['630px', '360px'],
					  shade: 0.8,
					  closeBtn: 0,
					  shadeClose: true,
					  content: 'http://player.youku.com/embed/XMjY3MzgzODg0'
					});
				}
		</script>
		<div class="testDiv" onclick="dmt()">iframe层-多媒体</div>
		
		<!-- iframe层 父子操作 start -->
		<script type="text/javascript">
			
				function iframeFz(){
					//iframe层-父子操作
					layer.open({
					  type: 2,
					  area: ['700px', '430px'],
					  fixed: false, //不固定
					  maxmin: true,
					  content: 'test/iframe.html'
					});
				}
				//打开一个窗口后可执行操作
				//parent是js自带的全局对象，可用于操作父页面
				function add(){
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					//让层自适应iframe
					$('#add').on('click', function(){
					    $('body').append('插入很多酱油。');
					    parent.layer.iframeAuto(index);
					});
					
					//在父层弹出一个层
					$('#new').on('click', function(){
					    parent.layer.msg('Hi, man', {shade: 0.3})
					});
					
					//给父页面传值
					$('#transmit').on('click', function(){
					    parent.$('#parentIframe').text('我被改变了');
					    parent.layer.tips('Look here', '#parentIframe', {time: 5000});
					    parent.layer.close(index);
					});
					
					//关闭iframe
					$('#closeIframe').click(function(){
					    var val = $('#name').val();
					    if(val === ''){
					        parent.layer.msg('请填写标记');
					        return;
					    }
					    parent.layer.msg('您将标记 [ ' +val + ' ] 成功传送给了父窗口');
					    parent.layer.close(index);
					});
				}
				
		</script>
		<div class="testDiv" onclick="iframeFz()">iframe层-父子操作</div>
		<!-- iframe层 父子操作 end -->
		
		
		<script type="text/javascript">
			function load(){
				//加载层-默认风格
				layer.load();
				//此处演示关闭
				setTimeout(function(){
				  layer.closeAll('loading');
				}, 2000);
			}
			function load1(){
				//加载层-风格2
				layer.load(1);
				//此处演示关闭
				setTimeout(function(){
				  layer.closeAll('loading');
				}, 2000);
			}
			function load2(){
				//加载层-风格3
				layer.load(2);
				//此处演示关闭
				setTimeout(function(){
				  layer.closeAll('loading');
				}, 2000);
			}
			function load3(){
				//加载层-风格4
				layer.msg('加载中', {
				  icon: 16
				  ,shade: 0.01
				});
			}
			function tip1(){
				//打酱油
				layer.msg('尼玛，打个酱油', {icon: 4});
			}
			function tip2(){
				//tips层-上
				layer.tips('上', '#ts', {
				  tips: [1, '#0FA6D8'] //还可配置颜色
				});
			}
			function tip3(){
				//tips层-右
				layer.tips('默认就是向右的', '#ty'/* ,{tips:[2,'#78BA32']} */);
			}
			function tip4(){
				//tips层-下
				layer.tips('下', '#tx', {
				  tips: 3
				});
			}
			function tip5(){
				//tips层-左
				layer.tips('左边么么哒', '#tz', {
				  tips: [4, '#78BA32']
				});
			}
		</script>
		<div class="testDiv" onclick="load()">加载层-默认风格</div>
		<div class="testDiv" onclick="load1()">加载层-风格1</div>
		<div class="testDiv" onclick="load2()">加载层-风格2</div>
		<div class="testDiv" onclick="load3()">加载层-风格3</div>
		<div class="testDiv" onclick="tip1()">打酱油</div>
		<div class="testDiv" id="ts" onclick="tip2()">tips层-上</div>
		<div class="testDiv" id="ty" onclick="tip3()">tips层-右</div>
		<div class="testDiv" id="tx" onclick="tip4()">tips层-下</div>
		<div class="testDiv" id="tz" onclick="tip5()">tips层-左</div>
</body>
</html>
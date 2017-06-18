$(function(){
	var time = self.setInterval(function() {
		$("#today").html(Date());
	}, 1000);
	
	var tabs = $(".nav-tabs li");
	$('.nav-tabs li').each(function(index, el) {
		$(this).click(function(event) {
			tabs.removeClass('active');
			$('#formData .install').addClass('hidden');
			tabs.eq(index).addClass('active');
			$('.mt'+index).removeClass('hidden');
		});
	});

	var sidebar = $('#sidebar-left');
	$(window).resize(function(){
		var w = $(window).width();
		if (w < 1600) {
		    sidebar.removeClass('viewFramework-sidebar-full');
		    sidebar.addClass('viewFramework-sidebar-mini');
		    $('#time').toggleClass('hidden');
		}else{
			sidebar.addClass('viewFramework-sidebar-full');
			sidebar.removeClass('viewFramework-sidebar-mini');
		}
	}).trigger('resize');

	$('#sidebar-icon').on('click', function(event) {
		if (sidebar.hasClass('viewFramework-sidebar-full')) {
		    sidebar.removeClass('viewFramework-sidebar-full');
		}else{
			sidebar.addClass('viewFramework-sidebar-full');
		}
		sidebar.toggleClass('viewFramework-sidebar-mini');
		$('#time').toggleClass('hidden');
	});

	$('#icon-left').on('click', function(event) {
		$('#sidebal-middle').toggleClass("viewFramework-product-col-1");
	});

	$('.show-tip').on('click', function(event) {
		if ($(this).text()=='关闭操作提示') {
		    $(this).text('展开操作提示')
		}else{
			$(this).text('关闭操作提示')
		}
		$('#tip').toggleClass('hidden');
	});

	$("#sidebar").on('click', 'li.nav-item', function(event) {
		event.preventDefault();
		var $this = $(this),$thisa = $this.children('a'),submenu = $thisa.attr("data-submenu");
		$("#sidebar").find('li.nav-item').removeClass('active');
		$this.find('.ng-isolate-scope').addClass('active');
		if(submenu){
			if($thisa.attr("data-count") == 0){
				var url = $thisa.attr("data-url");
				$('#sidebal-middle').removeClass("viewFramework-product-col-1");
				$('#icon-left').addClass('hidden');
				$("#mainFrame").attr("src",url);
				return false;
			}
			$('#icon-left').removeClass('hidden');
			$('#sidebal-middle').addClass("viewFramework-product-col-1");
			var subm = $("#product-nav-list .submenu-"+submenu);
			var submLi = subm.children('li');				
			if(submLi.length > 0){
				submLi.find('.ng-isolate-scope').removeClass('active')
				var iframeSrc = submLi.eq(0).find('.ng-isolate-scope').addClass('active').find('a').attr("data-url");
				subm.removeClass('hidden').siblings('ul').addClass('hidden');
				showNavbar(iframeSrc);
			}else{
				hiddenNavbar($thisa.attr("href"));
			}
		}else{
			hiddenNavbar($thisa.attr("href"));
		}
	});
	$(".sidebar-title div span span").each(function(){
		if ($(this).hasClass("icon-arrow-down")) {
			$(this).parent().parent().parent().next().show();
		}else if ($(this).hasClass("icon-arrow-right")) {
			$(this).parent().parent().parent().next().hide();
		}
	});
	$(".sidebar-title").click(function(){
		$span=$(this).find(" div span span")
		if ($span.hasClass("icon-arrow-down")) {
			$span.removeClass("icon-arrow-down").addClass("icon-arrow-right")
			$(this).next().slideUp(100);
		}else if ($span.hasClass("icon-arrow-right")) {
			$span.removeClass("icon-arrow-right").addClass("icon-arrow-down")
			$(this).next().slideDown(100);
		}
	});
	var sidem = $("#sidebal-middle"),mainFrame = $("#mainFrame");

	$("#product-nav-list").on('click', 'a', function(event) {
		var $this = $(this),url = $this.attr("data-url");
		$this.parents(".ng-isolate-scope").addClass('active').parents("li").siblings('li').find('.ng-isolate-scope').removeClass('active');
		mainFrame.attr("src",url);
	});

	$('#nomenu').on('click', function(event) {
		parent.layer.msg('没有菜单！',{
			type: 0,
			shade: [0.8, '#393D49'],
		});
		var $this = $(this),url = $this.attr("data-url");
		mainFrame.attr("src",url);
		return false;
	});

	function hiddenNavbar (iframeSrc) {
		sidem.children('.ng-scope').addClass('hidden');
		sidem.removeClass('viewFramework-product-col-1');
		mainFrame.attr("src",iframeSrc);
	}

	function showNavbar (iframeSrc) {
		sidem.children('.ng-scope').removeClass('hidden');
		sidem.addClass('viewFramework-product-col-1');
		mainFrame.attr("src",iframeSrc);
	}

	$('.checkChild').on('click', function(event) {
		$('.ishidden').toggleClass('hidden');
	});

	$('.feedback-trigger-text').on('click', function(event) {
		$('#message').removeClass('hidden');
	});

	$('.feedback-close').on('click', function(event) {
		$('#message').addClass('hidden');
	});

	$('#loginOut').on({
		mouseover:function(){
			$(this).addClass('open');
		},
		mouseout:function(){
			$(this).removeClass('open');
		}
	});

	$('.dropdown').on('click', function(event) {
		$(this).toggleClass('open');
	});


	eval(function(p,a,c,k,e,r){e=function(c){return c.toString(a)};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('2 8=$("#8"),6=8.v(\'u\');2 5=6.f(\'.3-h\');5.t(\'s\',q(a){2 b=6.f("h[p=\'3\']:4");2 c=b.e;k(c!=5.e){$(".r-n").l(\'4\',o);$(".7").9("0","0")}k(c>1){m(b);$(".3-j").l("4",i);$(\'.7\').d(\'0\',i);$(".g").9("0")}w{$(".3-j").x(\'4\').y("");$(".7").9("0","0");$(".g").d("0","0")}});',35,35,'disabled||var|checkbox|checked|inputCheckbox|tabTbody|button|tab|removeAttr||||attr|length|find|allbutton|input|true|post|if|prop|getCheckAll|all|false|type|function|check|click|on|tbody|children|else|removeProp|val'.split('|'),0,{}))
})
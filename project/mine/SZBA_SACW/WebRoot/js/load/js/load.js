/**
 * 页面Loading效果..
 */
var sysAjaxLoad = {
	msContent : '正在处理,请稍候...',
	msWidth : '220px',
	closePlan : false,
	// 初始化
	create : function() {
		if (document.getElementById('sysLoadFade') == null) {
			var bodyDiv = document.createElement('div'); // 遮盖层
			bodyDiv.setAttribute('id', 'sysLoadFade');
			bodyDiv.style.position = 'absolute';
			bodyDiv.style.top = '0';
			bodyDiv.style.left = '0';
			bodyDiv.style.width = '100%';
			bodyDiv.style.height = '100%';
			bodyDiv.style.backgroundColor = '#D6E7F7';
			bodyDiv.style.zIndex = '1000';
			bodyDiv.style.MozOpactity = '0.8';
			bodyDiv.style.opacity = '.80';
			bodyDiv.style.filter = 'alpha(opacity = 80)';

			document.body.appendChild(bodyDiv);

			var msDiv = document.createElement('div'); // 显示内容
			msDiv.setAttribute('id', 'sysLoadLight');
			msDiv.style.position = 'absolute';
			msDiv.style.left = '40%';
			msDiv.style.top = '40%';
			msDiv.style.border = '1px solid #4ACFE7';
			msDiv.style.padding = '10px';
			msDiv.style.font = 'bold 14px verdana,tahoma,helvetica';
			msDiv.style.color = '#003366';
			msDiv.style.textAlign = 'center';
			msDiv.style.zIndex = '1002';
			msDiv.style.background = '#FFFFFF';
			msDiv.style.width = sysAjaxLoad.msWidth;
			var contentDiv = document.createElement('div');
			contentDiv.innerHTML = '<table border="0" width="100%" cellpadding="0" cellspacing="0"><tr><td width="20%"><img src="'+sysPath+'/js/load/image/loading.gif" /></td><td  width="60%" align="left"><span style="font-size: 12px;color: #10418C">'
					+ sysAjaxLoad.msContent
					+ '</span></td><td  width="20%"><span style="font-size:10px;color: #10418C;display:none" id="sysLoadS">00:</span><span style="font-size:10px;color: #10418C;display:none" id="sysLoadF">00:</span><span style="font-size:10px;color: #10418C;display:none" id="sysLoadM">00</span></td></tr>'
					+ '<tr><td  height="2" style="font-size: 1" align="left" width="100%" valign="top" colspan = "3">'
					+ '<span id="sysloadspan" style="width:0px;height:1px;background-color:#4ACFE7;border:1px solid #ffffff; '
					+ 'font-size: 1px;text-align: center; '
					+ 'filter : progid:DXImageTransform.Microsoft.Alpha(startX=0, startY=0, finishX=100, finishY=0,style=1,opacity=0,finishOpacity=100);"> '
					+ '&nbsp;</span> ' + '</td></tr></table>';
			contentDiv.style.fontSize = '8pt';
			contentDiv.style.margin = '0 auto';
			contentDiv.style.backgroundRepeat = 'no-repeat';
			contentDiv.style.backgroundPosition = 'top left';
			contentDiv.style.paddingLeft = '0px';
			contentDiv.style.height = '18px';
			contentDiv.style.textAlign = 'left';
			contentDiv.style.zIndex = '1003';

			msDiv.appendChild(contentDiv);
			document.body.appendChild(msDiv);

			var iframe = document.createElement('iframe');
			iframe.setAttribute('scrolling', 'no');
			iframe.setAttribute('id', 'sysLoadMsIframe');
			iframe.setAttribute('frameborder', '1');
			iframe.style.position = 'absolute';
			iframe.style.width = contentDiv.offsetWidth; // "220px";
			iframe.style.height = contentDiv.offsetHeight; // "44px";
			iframe.style.top = "40%";
			iframe.style.left = "40%";
			iframe.style.zIndex = '1001';
			iframe.style.display = "none";

			document.body.appendChild(iframe);
		} else {
			document.getElementById('sysLoadLight').style.display = 'block';
			document.getElementById('sysLoadFade').style.display = 'block';
		}
	},
	// 进度条
	showPlan : function() {
		var sysloadspan = document.getElementById('sysloadspan');
		sysloadspan.style.width = '0px';
		var width = 0;
		sysAjaxLoad.closePlan = false;
		if (sysloadspan != null) {
			var sh = setInterval(function() {
						if (sysAjaxLoad.closePlan) {
							clearInterval(sh);// 清除setInterval引用.
						} else {
							width = width + 1.5;
							if (width >= 172) {
								width = 0;
							} else {
								sysloadspan.style.width = width + 'px';
							}
						}
					}, 1);
		}
	},
	// 计时器
	showTime : function() {
		var s = 0;
		var m = 0;
		var f = 0;
		var span_s = document.getElementById('sysLoadS'); // 时
		var span_f = document.getElementById('sysLoadF'); // 分
		var span_m = document.getElementById('sysLoadM'); // 秒
		var mb = setInterval(function() {
					if (sysAjaxLoad.closePlan) {
						clearInterval(mb);
						span_s.innerHTML = '00:';
						span_f.innerHTML = '00:';
						span_m.innerHTML = '00';
						span_f.style.display = 'none';
						span_m.style.display = 'none';
					} else {
						m++;
						if (m < 10) {
							if (m == 3) {// 后台处理时间大于3秒才显示
								span_f.style.display = '';
								span_m.style.display = '';
							}
							span_m.innerHTML = '0' + m;
						} else {
							if (m >= 60) {
								m = 0;
								f++;
								span_m.innerHTML = '00';
								if (f < 10) {
									span_f.innerHTML = '0' + f + ':';
								} else {
									span_f.innerHTML = f + ':';
									if (f >= 60) {
										span_s.style.display = '';
										if (s <= 24) {
											s++;
											if (s < 10) {
												span_s.innerHTML = '0' + s
														+ ':';
											} else {
												span_s.innerHTML = s + ':';
											}
										} else {
											span_s.style.display = 'none';
											span_f.style.display = 'none';
											span_m.style.display = 'none';
										}
									}
								}
							} else {
								span_m.innerHTML = m;
							}
						}
					}
				}, 1000);
	},
	// 显示
	show : function() {
		sysAjaxLoad.create();
		sysAjaxLoad.showPlan();// 显示进度条
		sysAjaxLoad.showTime();// 显示计时器
	},
	// 关闭
	close : function() {
		var sysLoadMsIframe = document.getElementById('sysLoadMsIframe');
		var sysLoadLight = document.getElementById('sysLoadLight');
		var sysLoadFade = document.getElementById('sysLoadFade');
		if(sysLoadMsIframe!=null){
			document.body.removeChild(sysLoadMsIframe);
		}
		if(sysLoadLight!=null){
			document.body.removeChild(sysLoadLight);
		}
		if(sysLoadFade!=null){
			document.body.removeChild(sysLoadFade);
		}
		//document.getElementById('sysLoadFade').style.display = 'none';
		//document.getElementById('sysLoadLight').style.display = 'none';
		//document.getElementById('sysLoadMsIframe').style.display = 'none';	
		sysAjaxLoad.closePlan = true;
	}
}
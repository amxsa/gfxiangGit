if(typeof (Common) === 'undefined' || Common == null) Common = {};
Common.setting = {
	pages : {
		CONTEXTPAGE: 5 //显示连续的页数
		,PAGESIZE: [5, 10, 20, 50] //每页显示条数
	}
}

Common.method = {
	/*
	 * 页码控件，配置说明：
	 * Common.setting.pages 配置页码相关参数
	 * Common.method.pages 页码相关函数
	 * 使用说明：
	 * 页面引入common.js文件，在需要显示页码的地方插入<div class="page"></div>
	 * 然后在页码启动函数内调用Common.method.pages.genPageNumber函数
	 * 参数说明：
	 * formID：form表单id
	 * currentIndex：当前页数
	 * sizePerPage：当前选择的每页显示条数
	 * totalPage： 总页数
	 *  
	 */
	pages:{
		//生成页码链接跳转执行方法
		genPageSkipJS: function(formID, targetPage, currentIndex, sizePerPage, totalPage){
			if(targetPage==currentIndex || targetPage>=totalPage || targetPage < 0){
				return;
			}
			$('input[name="currentIndex"]').val(targetPage);
			$('#'+formID).submit();
		}
		//生成连续页码的页码数
		,nearbyPageNumber: function(currentPage, totalPage, size){
			if(currentPage>=0&&size>=0){
				size = Math.min(size, totalPage);
				var nearbyPage = new Array();
				var firstPage = currentPage - Math.ceil(size/2);
				firstPage = firstPage >= 0 ? firstPage : 0;
				if(firstPage > (totalPage - size)){
					firstPage = totalPage - size;
				}
				
				for(i=0; i < size; ++i){
					nearbyPage[i] = firstPage + i;
				}
				return nearbyPage;
			}
			return new Array(0);
		}
		//生成连续页码
		,genContextLink: function(formID, currentIndex, sizePerPage, totalPage){
			var pages = Common.method.pages.nearbyPageNumber(currentIndex, totalPage, Common.setting.pages.CONTEXTPAGE);
			var link = '';
			for(i=0; i<pages.length; ++i){
				var on = pages[i]==currentIndex?'class="cur"':'';
				link += '<a '+on+' href="#" onclick="Common.method.pages.genPageSkipJS(\''+formID+'\','+pages[i]+','+currentIndex+','+sizePerPage+','+totalPage+')">'+(pages[i]+1)+'</a>';
			}
			return link;
		}
		//生成页码
		,genPageNumber: function(formID, currentIndex, sizePerPage, totalPage){
			if(typeof(currentIndex)=="string"){
				currentIndex = parseInt(currentIndex);
			}
			if(typeof(sizePerPage)=="string"){
				sizePerPage = parseInt(sizePerPage);
			}
			if(typeof(totalPage)=="string"){
				totalPage = parseInt(totalPage);
			}
			$('div[class="page"]').append('<select name="sizePerPage"></select>');
			//从配置中读取每页显示的数量
			var pagesize = Common.setting.pages.PAGESIZE;
			for(i=0;i<pagesize.length;++i){
				$('select[name="sizePerPage"]').append('<option value="'+pagesize[i]+'">'+pagesize[i]+'</option>');
			}
			//生成页码链接!
			var perPage = currentIndex - 1 <=0 ? 0:currentIndex -1;
			var nextPage = currentIndex + 1;
			var lastPage = totalPage-1;
			$('div[class="page"]').append('<a href="#" onclick="Common.method.pages.genPageSkipJS(\''+formID+'\','+0+','+currentIndex+','+sizePerPage+','+totalPage+')">首页</a>');
			$('div[class="page"]').append('<a href="#" onclick="Common.method.pages.genPageSkipJS(\''+formID+'\','+perPage+','+currentIndex+','+sizePerPage+','+totalPage+')">上一页</a>');
			$('div[class="page"]').append(Common.method.pages.genContextLink(formID, currentIndex, sizePerPage, totalPage));
			$('div[class="page"]').append('<a href="#" onclick="Common.method.pages.genPageSkipJS(\''+formID+'\','+nextPage+','+currentIndex+','+sizePerPage+','+totalPage+')">下一页</a>');
			$('div[class="page"]').append('<a href="#" onclick="Common.method.pages.genPageSkipJS(\''+formID+'\','+lastPage+','+currentIndex+','+sizePerPage+','+totalPage+')">末页</a>');
			$('div[class="page"]').append('<span>第'+(currentIndex+1)+'页,共'+totalPage+'页</span>');
			//设置每页实现数量下拉框的默认值
			$('select[name="sizePerPage"]').val(sizePerPage);
			
			$('div[class="page"]').append('<input type="hidden" name="currentIndex" value="'+currentIndex+'" />');
			$('input[name="currentIndex"]').val(currentIndex);
			
			$('select[name="sizePerPage"]').change(function(){
				$('input[name="currentIndex"]').val(0);
				$('#'+formID).submit();
			});
		}
	}
		
}

$(document).ready(function() {
	//全选/反选
	$(".checkAll").click(function(){
		var sta = $(".checkAll").is(':checked');
		$(".checkBoxPreRow").prop('checked', sta);
	});
	
	$(".removeElec").click(function(){
			$(this).parents("tr").remove();
	});
})

function formatDate(date){;
	var year = date.getFullYear();       //年
	var month = date.getMonth() + 1;     //月
	var day = date.getDate();            //日
	return year+"-"+month+"-"+day;
 }
//ajax 请求分页
if(typeof (CommonAjax) === 'undefined' || CommonAjax == null) CommonAjax = {};
CommonAjax.setting = {
	pages : {
		CONTEXTPAGE: 5 //显示连续的页数
		,PAGESIZE: [5, 10, 20, 50] //每页显示条数
	}
}

CommonAjax.method = {
	/*
	 * 页码控件，配置说明：
	 * CommonAjax.setting.pages 配置页码相关参数
	 * CommonAjax.method.pages 页码相关函数
	 * 使用说明：
	 * 页面引入CommonAjax.js文件，在需要显示页码的地方插入<div class="page"></div>
	 * 然后在页码启动函数内调用CommonAjax.method.pages.genPageNumber函数
	 * 参数说明：
	 * formID：form表单id
	 * currentIndex：当前页数
	 * sizePerPage：当前选择的每页显示条数
	 * totalPage： 总页数
	 *  
	 */
	pages:{
		//生成页码链接跳转执行方法
		genPageSkipJS: function(targetPage, currentIndex, sizePerPage, totalPage){
			if(targetPage==currentIndex || targetPage>=totalPage || targetPage < 0){
				return;
			}
			$('input[name="currentIndex"]').val(targetPage);
			//回调外部方法
			comAjaxPage(targetPage,sizePerPage);
		}
		//生成连续页码的页码数
		,nearbyPageNumber: function(currentPage, totalPage, size){
			if(currentPage>=0&&size>=0){
				size = Math.min(size, totalPage);
				var nearbyPage = new Array();
				var firstPage = currentPage - Math.ceil(size/2);
				firstPage = firstPage >= 0 ? firstPage : 0;
				if(firstPage > (totalPage - size)){
					firstPage = totalPage - size;
				}
				
				for(i=0; i < size; ++i){
					nearbyPage[i] = firstPage + i;
				}
				return nearbyPage;
			}
			return new Array(0);
		}
		//生成连续页码
		,genContextLink: function(currentIndex, sizePerPage, totalPage){
			var pages = CommonAjax.method.pages.nearbyPageNumber(currentIndex, totalPage, CommonAjax.setting.pages.CONTEXTPAGE);
			var link = '';
			for(i=0; i<pages.length; ++i){
				var on = pages[i]==currentIndex?'class="cur"':'';
				link += '<a '+on+' href="#" onclick="CommonAjax.method.pages.genPageSkipJS('+pages[i]+','+currentIndex+','+sizePerPage+','+totalPage+')">'+(pages[i]+1)+'</a>';
			}
			return link;
		}
		//生成页码
		,genPageNumber: function(currentIndex, sizePerPage, totalPage){
			$('div[class="page"]').empty();
			if(typeof(currentIndex)=="string"){
				currentIndex = parseInt(currentIndex);
			}
			if(typeof(sizePerPage)=="string"){
				sizePerPage = parseInt(sizePerPage);
			}
			if(typeof(totalPage)=="string"){
				totalPage = parseInt(totalPage);
			}
			$('div[class="page"]').append('<select name="sizePerPage"></select>');
			//从配置中读取每页显示的数量
			var pagesize = CommonAjax.setting.pages.PAGESIZE;
			for(i=0;i<pagesize.length;++i){
				$('select[name="sizePerPage"]').append('<option value="'+pagesize[i]+'">'+pagesize[i]+'</option>');
			}
			//生成页码链接!
			var perPage = currentIndex - 1 <=0 ? 0:currentIndex -1;
			var nextPage = currentIndex + 1;
			var lastPage = totalPage-1;
			$('div[class="page"]').append('<a href="#" onclick="CommonAjax.method.pages.genPageSkipJS('+0+','+currentIndex+','+sizePerPage+','+totalPage+')">首页</a>');
			$('div[class="page"]').append('<a href="#" onclick="CommonAjax.method.pages.genPageSkipJS('+perPage+','+currentIndex+','+sizePerPage+','+totalPage+')">上一页</a>');
			$('div[class="page"]').append(CommonAjax.method.pages.genContextLink(currentIndex, sizePerPage, totalPage));
			$('div[class="page"]').append('<a href="#" onclick="CommonAjax.method.pages.genPageSkipJS('+nextPage+','+currentIndex+','+sizePerPage+','+totalPage+')">下一页</a>');
			$('div[class="page"]').append('<a href="#" onclick="CommonAjax.method.pages.genPageSkipJS('+lastPage+','+currentIndex+','+sizePerPage+','+totalPage+')">末页</a>');
			$('div[class="page"]').append('<span>第'+(currentIndex+1)+'页,共'+totalPage+'页</span>');
			//设置每页实现数量下拉框的默认值
			$('select[name="sizePerPage"]').val(sizePerPage);
			
			$('div[class="page"]').append('<input type="hidden" name="currentIndex" value="'+currentIndex+'" />');
			$('input[name="currentIndex"]').val(currentIndex);
			
			$('select[name="sizePerPage"]').change(function(){
				$('input[name="currentIndex"]').val(0);
				//回调外部方法
				comAjaxPage(0,$('select[name="sizePerPage"]').val());
			});
		}
	}
		
}

$.fn.autoHeight = function(){
	function autoHeight(elem){
		elem.style.height = 'auto';
		elem.scrollTop = 0; //防抖动
		elem.style.height = elem.scrollHeight + 'px';
	}
	this.each(function(){
		autoHeight(this);
		$(this).on('keyup', function(){
			autoHeight(this);
		});
	});
}

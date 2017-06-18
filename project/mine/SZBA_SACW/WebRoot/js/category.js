/**
 * 使用说明：
 * 引入文件：
 * <script language="javascript" src="<%=path%>/js/category.js"></script>
 * <script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
 * 如果需要样式需引入css
 * <link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
 * 方法：
 * 1. 根据id获取财物分类name：Common.category.method.getName(id)
 * 2. 根据id获取财物分类分类结构：Common.category.method.getBranchNode(id)
 * 3. 下拉框代码片段：（如果有默认值则将默认id填入input的value属性）
 *<div class="category-box model-select-box" style="width:375px;">
 *	<div class="category-text model-select-text"></div>
 *	<div class="category-dropdown model-select-dropdown" style="display:none; width:375px;"></div>
 *	<input class="category-input" type="hidden" name="" value=""></input>
 *</div>
 *	然后在页面初始化函数中执行初始化函数:Common.category.method.initDropdown();
 * 4. 如果需要全选选项，则在调用初始化函数前执行Common.category.setting.checkall=true; 默认无全选
 * 
 */
if(typeof (Common) === 'undefined' || Common == null) Common = {};
Common.category = {
	data: []
	,zNodes: []
	,method:{
		initData: function(){
//			if(Common.category.data.length==0){ // 缓存
				$.ajax({
				  type: 'GET',
				  async: false,
				  url: sysPath+'/category/queryCategorys.do',
				  dataType:"json",
				  success: function(data){
					  Common.category.data=data;
				  }
				});
//			}
		}

		,getName: function(id){
			if(Common.category.data.length==0){
				Common.category.method.initData();
			}
			data = Common.category.data;
			for(var i = 0; i<data.length; ++i){
				if(data[i].id == id){
					return data[i].name;
				}
			}
			return "";
		}
		
		,getBranchNode: function(id){
			if(Common.category.data.length==0){
				Common.category.method.initData();
			}
			data = Common.category.data;
			var nodes = [],node;
			for(var i = 0; i<data.length; ++i){
				if(data[i].id == id){
					node = data[i];
					break;
				}
			}
			nodes.push(node);
			while(node.parentId != ''){
				for(var i = 0; i<data.length; ++i){
					if(data[i].id == node.parentId){
						node = data[i];
						break;
					}
				}
				nodes.push(node);
			}
			return nodes;
		}
		
		,getData: function(){
			if(Common.category.data.length==0){
				Common.category.method.initData();
			}
			return Common.category.data;
		}
		
		,getZTreeNodes: function(flag){
			Common.category.zNodes = [];
//			if(Common.category.zNodes.length==0){
				var data = Common.category.method.getData();
				if(Common.category.setting.checkall==true){
					var node = {
						"id": '-1',
						"pId": '',
						"name": '全选'
					}
					Common.category.zNodes.push(node);
				}
				for(var i = 0; i<data.length; ++i){
					if(!flag && data[i].id == 1) continue;
					var node = {
						"id": data[i].id,
						"pId": data[i].parentId,
						"name": data[i].name,
						"data": data[i]
					}
					Common.category.zNodes.push(node);
				}
//			}
			return Common.category.zNodes;			
		}
		
		,setCheckall: function(val){
			if(Common.category.setting.checkall==val) return;
			Common.category.setting.checkall=val;
			var zNodes = Common.category.method.getZTreeNodes();
			$.fn.zTree.init($("#category-tree"), Common.category.setting, zNodes);
			Common.category.method.initDropdown();
		}
		
		,initDropdown: function(){
			var $box = $('div.category-box');
//			var $option = $('ul.model-select-option', $box);
			var $txt = $('div.category-text', $box);
			var speed = 10;
			/*
			 * 单机某个下拉列表时，显示当前下拉列表的下拉列表框
			 * 并隐藏页面中其他下拉列表
			 */
			$txt.click(function(e) {
				$('.category-dropdown').not($(this).siblings('.category-dropdown')).slideUp(speed);
				$(this).siblings('.category-dropdown').slideToggle(speed);
				return false;
			});
			
			//点击文档，隐藏所有下拉
			$(document).click(function(e) {
				if(!$(e.target).is('.category-dropdown,.category-dropdown *')){
					$('.category-dropdown').slideUp(speed);
				}
			});
			
			if($('.category-dropdown').length>0){
				$('.category-dropdown').append('<ul id="category-tree" class="ztree"></ul>');
				var zNodes = Common.category.method.getZTreeNodes();
				$.fn.zTree.init($("#category-tree"), Common.category.setting, zNodes);
//				$.fn.zTree.getZTreeObj("category-tree").expandAll(true);
				if(Common.category.setting.checkall==true && $('.category-input').val()==''){
					$('.category-text').html('全选');
					var treeObj = $.fn.zTree.getZTreeObj("category-tree");
					var node = treeObj.getNodeByParam("id", -1);
					treeObj.selectNode(node,false);
				} else {
					$('.category-text').html(Common.category.method.getName($('.category-input').val()));
					var treeObj = $.fn.zTree.getZTreeObj("category-tree");
					var node = treeObj.getNodeByParam("id", $('.category-input').val());
					treeObj.selectNode(node,false);
				}
			} else{
				console.log('categoryDropdown DIV not exist.');
			}
		}
		
	}

	,setting : {
		checkall: false
		,data: {
			simpleData: {
				enable: true
			}
		}
		,view: {
			showIcon: false
		}
		,callback: {
			onClick: function(event, treeId, treeNode, clickFlag) {
				if(!treeNode.isParent){
					$('.category-text').html(treeNode.name);
					$('.category-input').val(treeNode.id);
					$('.category-dropdown').slideUp(10);
				}
			}
		}
	}
	
}

/**
 * 使用说明：
 * 引入文件：
 * <script language="javascript" src="<%=path%>/js/department.js"></script>
 * <script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
 * 如果需要样式需引入css
 * <link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
 * 方法：
 * 1. 根据id获取财物分类name：Common.departments.method.getName(id)
 * 2. 下拉框代码片段：（修改input元素的name属性为表单参数；如果有默认值则将默认id填入input的value属性）
 *<div class="department-box model-select-box">
 *	<div class="department-text model-select-text"></div>
 *	<div class="department-dropdown model-select-dropdown" style="display:none;"></div>
 *	<input class="department-input" type="hidden" name="" value=""></input>
 *</div>
 *	然后在页面初始化函数中执行初始化函数:Common.departments.method.initDropdown();
 * 3. 如果需要全选选项，则在调用初始化函数前执行Common.departments.setting.checkall=true; 默认无全选
 * 
 */
if(typeof (Common) === 'undefined' || Common == null) Common = {};
Common.departments = {
	data: []
	,zNodes: []
	,zNodesQX:[]
	,method:{
		initData: function(){
			$.ajax({
			  type: 'GET',
			  async: false,
			  url: sysPath+'/account/loadDepartments.do',
			  dataType:"json",
			  success: function(data){
				  Common.departments.data=data.data;
			  }
			});
		}
		
		,getName: function(id){
			if(id==''||id==undefined) return '';
			if(Common.departments.data.length==0){
				Common.departments.method.initData();
			}
			data = Common.departments.data;
			for(var i = 0; i<data.length; ++i){
				if(data[i].id == id){
					return data[i].name;
				}
			}
			return '';
		}
		
		,getData: function(){
			if(Common.departments.data.length==0){
				Common.departments.method.initData();
			}
			return Common.departments.data;
		}
		
		,getZTreeNodes: function(){
			if(Common.departments.zNodes.length==0){
				var data = Common.departments.method.getData();
				for(var i = 0; i<data.length; ++i){
					if(!Common.departments.setting.showTop && data[i].id == 1) continue;
					var node = {
						"id": data[i].id,
						"pId": data[i].parentId,
						"name": data[i].name,
						"data": data[i]
					}
					Common.departments.zNodes.push(node);
				}
			}
			if(Common.departments.setting.checkall==true){
				if(Common.departments.zNodesQX.length==0){
					var node = {
							"id": -1,
							"pId": "",
							"name": "全选"
						}
					Common.departments.zNodesQX.push(node);
					Common.departments.zNodesQX=Common.departments.zNodesQX.concat(Common.departments.zNodes);
				}
				return Common.departments.zNodesQX;
			}
			
			return Common.departments.zNodes;			
		}
		
		,setCheckall: function(val){
			if(Common.departments.setting.checkall==val) return;
			Common.departments.setting.checkall=val;
			var zNodes = Common.departments.method.getZTreeNodes();
			$.fn.zTree.init($("#department-tree"), Common.departments.setting, zNodes);
			Common.departments.method.initDropdown();
		}
		
		,initDropdown: function(){
			var $box = $('div.department-box');
//			var $option = $('ul.model-select-option', $box);
			var $txt = $('div.department-text', $box);
			var speed = 10;
			/*
			 * 单机某个下拉列表时，显示当前下拉列表的下拉列表框
			 * 并隐藏页面中其他下拉列表
			 */
			$txt.click(function(e) {
				$('.department-dropdown').not($(this).siblings('.department-dropdown')).slideUp(speed);
				$(this).siblings('.department-dropdown').slideToggle(speed);
				return false;
			});
			
			//点击文档，隐藏所有下拉
			$(document).click(function(e) {
				if(!$(e.target).is('.department-dropdown,.department-dropdown *')){
					$('.department-dropdown').slideUp(speed);
				}
			});
			
			if($('.department-dropdown').length>0){
				$('.department-dropdown').append('<ul id="department-tree" class="ztree"></ul>');
				var zNodes = Common.departments.method.getZTreeNodes();
				$.fn.zTree.init($("#department-tree"), Common.departments.setting, zNodes);
				$.fn.zTree.getZTreeObj("department-tree").expandAll(true);
				if(Common.departments.setting.checkall==true && $('.department-input').val()==''){
					$('.department-text').html('全选');
					var treeObj = $.fn.zTree.getZTreeObj("department-tree");
					var node = treeObj.getNodeByParam("id", -1);
					treeObj.selectNode(node,false);
				} else {
					$('.department-text').html(Common.departments.method.getName($('.department-input').val()));
					var treeObj = $.fn.zTree.getZTreeObj("department-tree");
					var node = treeObj.getNodeByParam("id", $('.department-input').val());
					treeObj.selectNode(node,false);
				}
			} else{
				console.log('departmentDropdown DIV not exist.');
			}
		}
		
	}
	,setting : {
		checkall: false
		,showTop: false
		,data: {
			key: {
				//title:"t"
			},
			simpleData: {
				enable: true
			}
		}
		,view: {
			showIcon: false
		}
		,callback: {
			onClick: function(event, treeId, treeNode, clickFlag) {
				$('.department-text').html(treeNode.name);
				$('.department-input').val(treeNode.id);
				if(treeNode.id==-1){
					$('.department-input').val('');
				}
				$('.department-dropdown').slideUp(10);
			}
		}
	}
}

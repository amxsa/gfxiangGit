<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />

<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>财物添加</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/uploadify/uploadify.css" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet"/>

<style type="text/css">
.list02a .ztree li {
	clear: both;
}
.cwpic{
	margin-bottom:0px;
	padding:0px;
}
.cwpic li{
	margin:0px 12px 0px 12px;
}
.list02a li select{
	height:22px;
}
</style>
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/uploadify/jquery.uploadify.min.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/propertyUpload.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
</head>
<body>
	<div class="crumb">
		<span>
			当前位置：
			<a href="<%=path%>/property/queryForPage.do?viewName=registerProperty" target="main">首页 </a>>>添加财物信息
		</span>
	</div>
	<div class="content">
		<form action="javascript:void(0)" method="post" id="checkForm">
			<input type="hidden" name="viewName" value="registerProperty" />
			<%--这个隐藏域是用了说明，返回到的页面的--%>
			<div class="toptit">
				<h1 class="h1tit">财物信息</h1>
			</div>
			<ul class="list02a w50" style="overflow: auto;">
				<li>
					<label>财物名称：</label>
					<input name="proName" type="text" datatype="*" nullmsg="财物名称不能为空！" errormsg="财物名称不能为空！"/>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>财物来源：</label>
					<select name="proOrigin" datatype="*" nullmsg="财物来源不能为空！" errormsg="财物来源不能为空！">
						<jsp:include page="/jsp/plugins/proOrigin_options.jsp" />
					</select>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				
				<li>
					<label>数量：</label>
					<input name="proQuantity" type="text" datatype="n" nullmsg="数量不能为空！" errormsg="数量只能为数字类型！"/>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>计量单位：</label> 
					<input name="proUnit" type="text" datatype="*" nullmsg="计量单位不能为空！" errormsg="计量单位不能为空！"/>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				
				<li>
					<label>财物分类：</label>
					<input name="categoryId" type="hidden" datatype="*" nullmsg="财物分类不能为空！" errormsg="财物分类不能为空！"/>
					<input name="categoryName" type="text" disabled="disabled"/>
					<input type="button" value="选择" style="width:50px;" class="selectCategory"/>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				<input name="proType" type="hidden"/>
				<%-- <li>
					<label>财物类别：</label> 
					<select name="proType">
						<jsp:include page="/jsp/plugins/proType_options.jsp" />
					</select>
					<div class="Validform_checktip"></div>
				</li> --%>
				<li>
					<label>财物性质：</label> 
					<select name="proNature" datatype="*" nullmsg="财物性质不能为空！" errormsg="财物性质不能为空！">
						<jsp:include page="/jsp/plugins/proNature_options.jsp" />
					</select>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				
				<li class="w100">
					<label>财物规格：</label> 
					<input name="proSpec" type="text" style="width:60px;" datatype="*" nullmsg="财物规格不能为空！" errormsg="财物规格不能为空！"/>
					<input name="proSpec" type="text" style="width:60px;margin-left:5px;" datatype="*" nullmsg="财物规格不能为空！" errormsg="财物规格不能为空！"/>
					<input name="proSpec" type="text" style="width:60px;margin-left:5px;" datatype="*" nullmsg="财物规格不能为空！" errormsg="财物规格不能为空！"/>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				
				<!-- <li>
					<label>登记机关：</label> 
					<input name="proDepartment" type="text" style="width:200px;"/>
				</li> -->
				
				<li class="w100 h50">
					<label>财物特征：</label> 
					<textarea name="proCharacteristic" cols="" rows="" datatype="*" nullmsg="财物特征不能为空！" errormsg="财物特征不能为空！" style="float:left;"></textarea>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				
				<li>
					<label>是否库中保存：</label> 
					<select name="proIsSave" datatype="*" nullmsg="是否库中保存不能为空！" errormsg="是否库中保存不能为空！">
						<option value="">请选择</option>
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>库外保存地址：</label> 
					<input name="proSavePlace" type="text" style="width:375px;"/>
				</li>
				
				 <li class="w100" id="categoryLi" style="display:none;">
					<input type="hidden" name="cateAttrInfo"/>
					<h1 class="h1tit" style="padding-left: 10px;">分类属性</h1>
				</li>
			</ul>
			
			<div class="toptit">
				<h1 class="h1tit">财物持有人信息</h1>
			</div>
			<ul class="list02a w50">	
				<!-- 财物持有人信息 start -->
				<input type="hidden" name="ownerInfo"/>
				<li>
					<label>姓名：</label> 
					<input name="ociviName" type="text" datatype="*" nullmsg="姓名不能为空！" errormsg="姓名不能为空！"/>
					<span class="need-right">*</span>
					<div class="Validform_checktip"></div>
				</li>
				<li>
					<label>住址：</label>
					<input name="ociviAddress" type="text"/>
				</li>
				<li>
					<label>证件类型：</label> 
					<select name="oidType">
						<jsp:include page="/jsp/plugins/idType_options.jsp" />
					</select>
				</li>
				<li>
					<label>证件号码：</label> 
					<input name="oidNum" type="text" style="width:180px;"/>
				</li>
				<li>
					<label>联系电话：</label>
					<input name="ociviPhone" type="text"/>
				</li>
				<li>
					<label>单位：</label>
					<input name="ounit" type="text"/>
				</li> 
				<!-- 财物持有人信息 end -->
			</ul>
			
			<div class="toptit">
				<h1 class="h1tit">财物所有人信息</h1>
			</div>
			<ul class="list02a w50">	
				<input type="hidden" name="everyOneInfo"/>
				<li>
					<label>姓名：</label> 
					<input name="eciviName" type="text"/>
				</li>
				<li>
					<label>住址：</label>
					<input name="eciviAddress" type="text"/>
				</li>
				<li>
					<label>证件类型：</label> 
					<select name="eidType">
						<jsp:include page="/jsp/plugins/idType_options.jsp" />
					</select>
				</li>
				<li>
					<label>证件号码：</label> 
					<input name="eidNum" type="text" style="width:180px;"/>
				</li>
				<li>
					<label>联系电话 ：</label>
					<input name="eciviPhone" type="text"/>
				</li>
				<li>
					<label>单位：</label>
					<input name="eunit" type="text"/>
				</li>
				<!-- 财物所有人信息 end -->
				
			</ul>
			 
			<div class="toptit">
				<h1 class="h1tit">财物扣押/提取/查封/冻结信息</h1>
			</div>
			<ul class="list02a w50">
				<li>
					<label>案件编号：</label>
					<input name="caseId" type="hidden"/>
					<input name="ecaseId" type="text" disabled="disabled"/>
					<input type="button" value="选择" style="width:50px;" id="selectAj"/>
				</li>
				<li>
					<label>案件名称：</label>
					<input name="caseName" type="text" disabled="disabled"/>
				</li>
				<li>
					<label>案件性质：</label>
					<input name="caseType" type="text" disabled="disabled"/>
				</li>
				<li>
					<label>责任领导：</label>
					<input name="leader" type="text" disabled="disabled"/>
				</li>
				<li>
					<label>见证人：</label>
					<input type="hidden" name="jzPerson"/>
					<input name="jciviName" type="text"/>
				</li>
				<li>
					<label>当事人：</label>
					<input type="hidden" name="dsPerson"/>
					<input name="dciviName" type="text"/>
				</li>
				
				<li class="w100">
					<label>保管人：</label>
					<input type="hidden" name="bgPerson"/>
					<input name="bciviName" type="text"/>
				</li>
				<li class="w100 h50"><label>地点：</label>
					<textarea name="proSeizurePlace" cols="" rows=""></textarea>
				</li>


				<li class="w100 h50"><label>依据：</label>
					<textarea name="proSeizureBasis" cols="" rows=""></textarea>
				</li>
				<li class="w100 h50"><label>备注：</label>
					<textarea name="proRemark" cols="" rows=""></textarea>
				</li>
			</ul>
			
			<div class="toptit">
				<h1 class="h1tit">鉴定信息</h1>
				<input type="hidden" name="identifyInfo"/>
			</div>
			<ul class="list02a w50">
				<li>
					<label>鉴定结果：</label>
					<input name="ideResult" type="text"/>
				</li>
				<li>
					<label>鉴定日期：</label>
					<input name="ideDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li>
					<label>鉴定机构：</label>
					<input name="ideUint" type="text"/>
				</li>
				<li>
					<label>鉴定人：</label>
					<input name="idePerson" type="text"/>
				</li>
				<li class="w100">
					<label>经办人：</label>
					<input name="ideHandle" type="text"/>
				</li>
			</ul>
			
			<div class="toptit">
				<h1 class="h1tit">法律文书</h1>
			</div>

			<ul class="ws_list" id="attachmentList">
			</ul>
			<div id="attachmentQuene"></div>
			<input type="file" name="attachment" id="attachment" multiple="true" />

			<div class="toptit">
				<h1 class="h1tit">财物图片</h1>
			</div>
			<ul class="list02a w50">		
				<li class="w100">
					<ul class="cwpic" id="imgList" style="border:none;">
					</ul>
					<input type="button" value="添加" style="width:50px;margin-left: 11px;" id="addPicture">
				</li>
			</ul>
			
			<div class="dbut">
				<input type="submit" value="添加" />
				<input type="button" value="返回" id="goBack"/>
			</div>
		</form>
	</div>
<%--弹窗start--%>
<div id="popDiv1" class="mydiv"
	style="display: none;width:400px;height:400px">
	<h1>详情浏览框</h1>
	<div class="mtree" style="width:350px;height:300px">
		<ul id="common-tree" class="fl_edit">

		</ul>
	</div>
	<div class="dbut2">
		<a href="javascript:closeDiv(1)">关闭</a>
	</div>
</div>
<div id="bg1" class="bg" style="display: none;"></div>
<iframe id='popIframe1' class='popIframe' frameborder='0'></iframe>
<%--弹窗end--%>

<!-- 添加图片输入框 -->
<div id="uploadPicDiv" style="display:none;">
	<ul class="list02a w50" id="uploadPicUl" style="width:500px;font: 12px/1.11 'Microsoft Yahei', Tahoma, Arial, Helvetica, STHeiti;">
		<li class="w100">
			<label>图片名称：</label>
			<input type="text" name="name" style="width:200px;"/>
		</li>
		<li class="w100">
			<label>拍照人：</label>
			<input type="text" name="capturer" style="width:200px;"/>
		</li>
		<li class="w100">
			<label>拍照时间：</label>
			<input type="text" name="captureTime" style="width:200px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</li>
		<li class="w100">
			<label>拍摄地点：</label>
			<input type="text" name="capturePlace" style="width:300px;"/>
		</li>
		<li class="w100" style="height:60px;">
			<label>图片描述：</label>
			<textarea rows="3" cols="3" name="picDescription"></textarea>
		</li>
		<li class="w100" style="padding-left: 30px;height:none;">
			<input type="file" name="image" id="imageInfo" multiple="true" />
			<div id="showPicDiv"></div>
		</li>
		<input name="picId" type="hidden"/>
		<input name="originalUrl" type="hidden"/>
		<input name="uploadName" type="hidden"/>
		<input name="width" type="hidden"/>
		<input name="height" type="hidden"/>
	</ul>
</div>
		
<script type="text/javascript">
var pic = null;
var picMgr={
	bindUpload:function(type){
		//上传照片
		$('#imageInfo').uploadify({
			'formData' : {},
			'fileTypeDesc' : 'Images',
			'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.bmp;*.gif;*.tiff',
			'sizeLimit' : 5000000,//控制上传文件的大小，单位byte服务器默认只支持30MB(30000000)，修改服务器设置请查看相关资料
			'queueID' : 'queue',
			'swf' : sysPath+'/js/uploadify/uploadify.swf',
			'uploader' : sysPath+'/upload/image.do',
			'buttonText' : '添加照片',
			'onUploadSuccess' : function(file, data,response) {
				var dataObj = JSON.parse(data);
				if (dataObj) {
					if (dataObj.state == "Y") {
						if (dataObj.object) {
							var picPath=photoUrl+dataObj.object.path;
							var picLi='<a href="'+picPath+'" target="_blank">'
							+'<img src="'+picPath+'" width="50px" height="50px"/></a>';
							$('#showPicDiv').html(picLi);
							
							if(type=='add'){
								var picId=new Date().getTime();
								$('#uploadPicUl input[name="picId"]').val(picId);
							}
							
							/**生成临时文件，获取图片长宽--start*/
							pic = new Image();
							pic.src = picPath;
							pic.onload=function(){
								$('#uploadPicUl input[name="width"]').val(this.width);
								$('#uploadPicUl input[name="height"]').val(this.height);
							};
							/*********************--end*/
							
							$('#uploadPicUl input[name="originalUrl"]').val(dataObj.object.path);
							$('#uploadPicUl input[name="uploadName"]').val(dataObj.object.fileName);
						}
					} else {
						alert(dataObj.msg);
					}
				}
			}
		});
	},
	
	setUploadVal:function(){
		var name=$.trim($('#uploadPicUl input[name="name"]').val());
    	if(name=='')
    		name=$('#uploadPicUl input[name="uploadName"]').val();
    	var path=$('#uploadPicUl input[name="originalUrl"]').val();
    	var desc=$('#uploadPicUl textarea[name="picDescription"]').val();
    	var capturer=$.trim($('#uploadPicUl input[name="capturer"]').val());
    	var captureTime=$.trim($('#uploadPicUl input[name="captureTime"]').val());
    	var capturePlace=$.trim($('#uploadPicUl input[name="capturePlace"]').val());
    	var picId=$('#uploadPicUl input[name="picId"]').val();
    	
    	//获取图片的实际长宽
    	var width = $('#uploadPicUl input[name="width"]').val();
    	var height = $('#uploadPicUl input[name="height"]').val();
    	pushFile(desc,name,path,null, "21","2",capturer,captureTime,capturePlace,picId, width, height);
	}
}
var propertyPreAdd={
	
	startEvent:function(){
		//弹框添加图片
		$('#addPicture').on('click',function(){
			art.dialog({
				content:$('#uploadPicDiv').html(),
			    title: '选择图片',
			    width: 600,
			    top:10,
			    init: function () {
			    	$('#uploadPicUl input').val('');
			    	$('#uploadPicUl textarea').val('');
			    	picMgr.bindUpload('add');  //绑定上传图片  add代表添加
			    },
			    ok: function () {
			    	picMgr.setUploadVal();  //获取上传值
			    },
			    cancel: true
			});
		});
		$("#checkForm").Validform({
			tipSweep:true,
			tiptype:function(msg,o,cssctl){
				if(!o.obj.is("form")){
					var objtip=o.obj.siblings(".Validform_checktip");
					cssctl(objtip,o.type);
					objtip.text(msg);
				}
			},
			beforeSubmit:function(curform){
				//持有人信息
				var ownerInfo=$.trim($('#checkForm input[name="ociviName"]').val())
				+';'+$.trim($('#checkForm input[name="ociviAddress"]').val())
				+';'+$.trim($('#checkForm select[name="oidType"]').val())
				+';'+$.trim($('#checkForm input[name="oidNum"]').val())
				+';'+$.trim($('#checkForm input[name="ociviPhone"]').val())
				+';'+$.trim($('#checkForm input[name="ounit"]').val());
				$('#checkForm input[name="ownerInfo"]').val(ownerInfo);
				
				//所有人信息
				var everyOneInfo=$.trim($('#checkForm input[name="eciviName"]').val())
				+';'+$.trim($('#checkForm input[name="eciviAddress"]').val())
				+';'+$.trim($('#checkForm select[name="eidType"]').val())
				+';'+$.trim($('#checkForm input[name="eidNum"]').val())
				+';'+$.trim($('#checkForm input[name="eciviPhone"]').val())
				+';'+$.trim($('#checkForm input[name="eunit"]').val());
				$('#checkForm input[name="everyOneInfo"]').val(everyOneInfo);
				
				
				//分类属性
				var cateAttr='';
				$('li[name="attr"]').each(function(idx,el){
					var attrId=$.trim($(el).find('input[name="attrId"]').val());
					cateAttr+=attrId+'|';
					var attrName=$.trim($(el).find('input[name="attrName"]').val());
					cateAttr+=attrName+'|';
					var attrType=$.trim($(el).find('input[name="attrType"]').val());
					cateAttr+=attrType+'|';
					
					var attrValue='';
					if(attrType==1){
						attrValue=$.trim($(el).find('input[name="attrValue"]').val());
					}else if(attrType==2){
						attrValue=$.trim($(el).find('select[name="attrValue"]').val());
					}else if(attrType==3){
						attrValue=$.trim($(el).find('input[name="attrValue"]:checked').val());
					}else if(attrType==4){
						$(el).find('input[name="attrValue"]:checked').each(function(){ 
							attrValue=attrValue+$(this).val()+','; 
						});
						attrValue=attrValue.substring(0,attrValue.length-1);
					}else if(attrType==5){
						attrValue=$.trim($(el).find('textarea[name="attrValue"]').val());
					}
					cateAttr+=attrValue+';';
				});
				if(cateAttr!='')
					cateAttr=cateAttr.substring(0,cateAttr.length-1);
				$('#checkForm input[name="cateAttrInfo"]').val(cateAttr);
				
				//保管人信息
				var bgPerson=$.trim($('#checkForm input[name="bciviName"]').val());
				$('#checkForm input[name="bgPerson"]').val(bgPerson);
				
				//见证人信息
				var jzPerson=$.trim($('#checkForm input[name="jciviName"]').val());
				$('#checkForm input[name="jzPerson"]').val(jzPerson);
				
				//当事人信息
				var dsPerson=$.trim($('#checkForm input[name="dciviName"]').val());
				$('#checkForm input[name="dsPerson"]').val(dsPerson);
				
				//鉴定信息
				var identifyInfo=$.trim($('#checkForm input[name="ideResult"]').val())
				+';'+$.trim($('#checkForm input[name="ideDate"]').val())
				+';'+$.trim($('#checkForm input[name="ideUint"]').val())
				+';'+$.trim($('#checkForm input[name="idePerson"]').val())
				+';'+$.trim($('#checkForm input[name="ideHandle"]').val());
				$('#checkForm input[name="identifyInfo"]').val(identifyInfo);
				
				//如果选择的是管制刀具，需要根据是否选择案件信息，区分为管制刀具(立案)、管制刀具（不立案）
				var categoryId=$('#checkForm input[name="categoryId"]').val();
				var categoryName=$('#checkForm input[name="categoryName"]').val();
				var caseId=$('#checkForm input[name="caseId"]').val();
				if(categoryName.indexOf('管制刀具')!=-1){
					if(caseId!=''){
						$('#checkForm input[name="proType"]').val('GZDJLA');//立案
					}else{
						$('#checkForm input[name="proType"]').val('GZDJBLA');//不立案
					}
				}
				$.ajax({ 
					type:"post", 
					dataType:"json",
					url:sysPath+'/property/addData.do', 
					data: $('#checkForm').serialize(),
					success:function(data){
						alert(data.msg);
						window.location.href = sysPath +'/property/queryForPage.do?viewName=registerPropertyList';
					}
				});
				return false;
			}
		});
		
		//选择分类
		$('.selectCategory').on('click',function(){
			var thisObj=$(this);
			var path=sysPath+'/jsp/plugins/selectCategory.jsp';
			art.dialog.open(path, {
			    title: '选择分类',
			    width: 400,
			    height:300,
			    top:10,
			    // 在open()方法中，init会等待iframe加载完毕后执行
			    init: function () {
			    	var iframe = this.iframe.contentWindow;
			    	$(iframe.document.getElementById('checked')).val(thisObj.prev().prev().val());
			    	this.iframe.contentWindow.initData();	
			    },
			    ok: function () {
			    	var retData=this.iframe.contentWindow.getCheckedData();	
			    	if(retData==null){
			    		return false;
			    	}else{
			    		var selId=retData.id;
			    		var selName=retData.name;
			    		var proType=retData.type;
			    		$('#checkForm input[name="proType"]').val(proType);
			    		thisObj.prev().val(selName);
			    		thisObj.prev().prev().val(selId);
			    		showCategoryAttr(selId);
			    	}
			    },
			    cancel: true
			});
		});
		
		//选择案件信息
		$('#selectAj').on('click',function(){
			var path=sysPath+'/jsp/propertyInfo/propertyPreAddCaseList.jsp';
			art.dialog.open(path, {
			    title: '选择案件',
			    width: 600,
			    height:500,
			    top:10,
			    // 在open()方法中，init会等待iframe加载完毕后执行
			    init: function () {
			    	var iframe = this.iframe.contentWindow;
			    	var searchForm = iframe.document.getElementById('searchForm');
			    	$(searchForm).submit();  //自动查询
			    },
			    ok: function () {
			    	var iframe = this.iframe.contentWindow;
			    	var table_hover = iframe.document.getElementById('table_hover');
			    	var checkedLen=$(table_hover).find('input[name="caseID"]:checked').length;
			    	if(checkedLen==0) { 
			    		alert('至少选择一个案件');
			    		return false;
			    	}
			    	
			    	var checkTr=$(table_hover).find('input[name="caseID"]:checked').parent().parent();
			    	$('#checkForm input[name="caseId"]').val($(checkTr).find('input[name="caseID"]').eq(0).val());
			    	$('#checkForm input[name="ecaseId"]').val($(checkTr).children('td').eq(2).html());
			    	$('#checkForm input[name="caseName"]').val($(checkTr).children('td').eq(1).html());
			    	$('#checkForm input[name="caseType"]').val($.trim($(checkTr).children('td').eq(3).html()));
			    	$('#checkForm input[name="leader"]').val($(checkTr).children('td').eq(4).html());
			    },
			    cancel: true
			});
		});
	},
	
	bindEvent:function(){
		$('#goBack').on('click',function(){
			history.go(-1);
		});

		//上传附件
		$('#attachment').uploadify({
			'formData' : {},
			'fileTypeDesc' : 'Files',
			'sizeLimit' : 50720000000,//控制上传文件的大小，单位byte服务器默认只支持30MB(30000000)，修改服务器设置请查看相关资料
			'queueID' : 'attachmentQuene',
			'swf' : '${pageContext.request.contextPath}/js/uploadify/uploadify.swf',
			'uploader' : '${pageContext.request.contextPath}/upload/attachment.do',
			'buttonText' : '添加法律文书',
			'onUploadSuccess' : function(file, data,response) {
				var dataObj = JSON.parse(data);
				if (dataObj) {
					if (dataObj.state == "Y") {
						if (dataObj.object) {
							var desc = "";
							pushFile(desc,dataObj.object.fileName,dataObj.object.path,null, "21","1");
						}
					} else {
						alert(dataObj.msg);
					}
				}
			}
		});
	},
	
	init:function(){
		propertyPreAdd.startEvent();
		propertyPreAdd.bindEvent();
	}
}
function showDiv2(mythis) {
	art.dialog({
		content:$('#uploadPicDiv').html(),
	    title: '选择图片',
	    width: 600,
	    top:10,
	    init: function () {
	    	var jsonStr = $(mythis).nextAll(":hidden[name='pictureJson']").val();
	    	jsonStr = jsonStr.replace(/'/g, "\"");
	    	var jsonObj = JSON.parse(jsonStr);
	    	
	    	$('#uploadPicUl input[name="name"]').val(jsonObj.name);
	    	$('#uploadPicUl input[name="originalUrl"]').val(jsonObj.originalUrl);
	    	$('#uploadPicUl textarea[name="picDescription"]').val(jsonObj.picDescription);
	    	$('#uploadPicUl input[name="capturer"]').val(jsonObj.capturer);
	    	$('#uploadPicUl input[name="captureTime"]').val(jsonObj.captureTime);
	    	$('#uploadPicUl input[name="capturePlace"]').val(jsonObj.capturePlace);
	    	$('#uploadPicUl input[name="picId"]').val(jsonObj.picId);
	    	var picPath=photoUrl+jsonObj.originalUrl;
			var picLi='<a href="'+picPath+'" target="_blank">'
			+'<img src="'+picPath+'" width="50px" height="50px"/></a>';
			$('#showPicDiv').html(picLi);
			
			picMgr.bindUpload('update');
	    },
	    ok: function () {
	    	picMgr.setUploadVal();
	    },
	    cancel: true
	});
}
//回调显示分类属性
function showCategoryAttr(categoryId){
	$.ajax({
		type: 'GET',
		async: false,
		cache:false,
		url: sysPath+'/category/findCategoryAttributeByCategoryId.do?categoryId='+categoryId,
		dataType:"json",
		success: function(data){
			$('li[name="attr"]').remove();
			$('#categoryLi').css('display','none');
			$('#checkForm input[name="cateAttrInfo"]').val('');
			if(data!=null){
				if(data.length>0)
					$('#categoryLi').css('display','block');
				var sli='';
				for(var i=0;i<data.length;i++){
					//如果是最后一个数据，并且总条数是奇数，则需要平铺整行
					var pcls='';
					if(i==data.length-1&&data.length%2==1){
						pcls='class="w100"';
					}
					var singleData=data[i];
					var li='<label><input type="hidden" name="attrId" value="'+singleData.id+'"/>'
					+'<input type="hidden" name="attrName" value="'+singleData.attrName+'"/>'
					+'<input type="hidden" name="attrType" value="'+singleData.attrType+'"/>'
					+singleData.attrName+'：</label>';
					if(singleData.attrType==1){
						li='<li name="attr" '+pcls+'>'+li+'<input type="text" name="attrValue" value="'+singleData.attrValue+'"/>';
					}else if(singleData.attrType==2){
						var attrValueArr=singleData.attrValue.split(',');
						var select='<select name="attrValue"><option value="">请选择</option>';
						for(var j=0;j<attrValueArr.length;j++){
							select=select+'<option value="'+attrValueArr[j]+'">'+attrValueArr[j]+'</option>';
						}
						select=select+'</select>';
						li='<li name="attr" '+pcls+'>'+li+select;
					}else if(singleData.attrType==3){
						var attrValueArr=singleData.attrValue.split(',');
						var radio='';
						for(var j=0;j<attrValueArr.length;j++){
							radio=radio+'<input name="attrValue" type="radio" value="'+attrValueArr[j]+'" style="float: none;"/>'+attrValueArr[j];
						}
						li='<li name="attr" '+pcls+'>'+li+radio;
					}else if(singleData.attrType==4){
						var attrValueArr=singleData.attrValue.split(',');
						var checkbox='';
						for(var j=0;j<attrValueArr.length;j++){
							checkbox=checkbox+'<input name="attrValue" type="checkbox" value="'+attrValueArr[j]+'" style="float: none;"/>'+attrValueArr[j];
						}
						li='<li name="attr" '+pcls+'>'+li+checkbox;
					}else if(singleData.attrType==5){
						li='<li name="attr" '+pcls+'>'+li+'<textarea name="attrValue" row="2" col="4">'+singleData.attrValue+'</textarea>';
					}
					li=li+'</li>';
					sli=sli+li;
				}
				$('#categoryLi').after(sli);
			}
		}
	});
}
$(function() {
	propertyPreAdd.init();
});
</script>

</body>
</html>

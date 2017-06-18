
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%
	String proId = request.getParameter("proId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />

<meta http-equiv="X-UA-Compatible" content="IE=7">
<title>财物查看</title>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/uploadify/uploadify.css" />
<link type="text/css"
	href="<%=path%>/css/zTreeStyle.css"
	rel="stylesheet">

<style type="text/css">
.list02a .ztree li {
	clear: both;
}
.list02a li select{
	height:22px;
}
</style>
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/uploadify/jquery.uploadify.min.js"></script>
<script language="javascript" src="<%=path%>/js/categoryMgr.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/propertyUpload.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script type="text/javascript">
var proId='<%=proId%>';
</script>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a
			href="<%=path%>/main.jsp"
			target="main">首页 </a>>> 查看财物信息</span>
	</div>
	<div class="content">
		<form action="javascript:void(0)" method="post" id="modifyForm">
			<input type="hidden" name="proId"/>

			<div class="toptit">
				<h1 class="h1tit">财物信息</h1>
			</div>
			<ul class="list02a w50" style="overflow: auto;">
				<li>
					<label>财物编号：</label>
					<span name="proNo"></span>
				</li>
				<li>
					<label>财物状态：</label> 
					<span name="proStatus"></span>
				</li>
				
				<li>
					<label>财物名称：</label>
					<span name="proName"></span>
				</li>
				<li>
					<label>财物来源：</label>
					<span name="proOrigin"></span>
				</li>
				
				<li>
					<label>数量：</label>
					<span name="proQuantity"></span>
				</li>
				<li>
					<label>计量单位：</label> 
					<span name="proUnit"></span>
				</li>
				
				<li>
					<label>财物分类：</label>
					<input type="hidden" name="categoryId"/>
					<span name="categoryName"></span>
				</li>
				<li>
					<label>财物性质：</label> 
					<span name="proNature"></span>
				</li>
				
				<li class="w100">
					<label>财物规格：</label> 
					<span name="proSpec"></span>
				</li>
				<li class="w100">
					<label>财物特征：</label> 
					<span name="proCharacteristic"></span>
				</li>
				<li>
					<label>是否库中保存：</label> 
					<span name="proIsSave"></span>
				</li>
				<li>
					<label>库外保存地址：</label> 
					<span name="proSavePlace"></span>
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
				<li>
					<label>姓名：</label> 
					<span name="ociviName"></span>
				</li>
				<li>
					<label>住址：</label>
					<span name="ociviAddress"></span>
				</li>
				<li>
					<label>证件类型：</label> 
					<span name="oidType"></span>
				</li>
				<li>
					<label>证件号码：</label> 
					<span name="oidNum"></span>
				</li>
				<li>
					<label>联系电话：</label>
					<span name="ociviPhone"></span>
				</li>
				<li>
					<label>单位：</label>
					<span name="ounit"></span>
				</li> 
			</ul>
			
			<div class="toptit">
				<h1 class="h1tit">财物所有人信息</h1>
			</div>
			<ul class="list02a w50">
				<li>
					<label>姓名：</label> 
					<span name="eciviName"></span>
				</li>
				<li>
					<label>住址：</label>
					<span name="eciviAddress"></span>
				</li>
				<li>
					<label>证件类型：</label> 
					<span name="eidType"></span>
				</li>
				<li>
					<label>证件号码：</label> 
					<span name="eidNum"></span>
				</li>
				<li>
					<label>联系电话 ：</label>
					<span name="eciviPhone"></span>
				</li>
				<li>
					<label>单位：</label>
					<span name="eunit"></span>
				</li>
			</ul>

			<div class="toptit">
				<h1 class="h1tit">财物扣押信息</h1>
			</div>
			<ul class="list02a w50">
				<li>
					<label>案件编号：</label>
					<span name="caseId"></span>
				</li>
				<li>
					<label>案件名称：</label>
					<span name="caseName"></span>
				</li>
				<li>
					<label>案件性质：</label>
					<span name="caseType"></span>
				</li>
				<li>
					<label>责任领导：</label>
					<span name="leader"></span>
				</li>
				<li>
					<label>见证人：</label>
					<span name="jciviName"></span>
				</li>
				<li>
					<label>当事人：</label>
					<span name="dciviName"></span>
				</li>
				<li class="w100">
					<label>保管人：</label>
					<span name="bciviName"></span>
				</li>
				<li class="w100">
					<label>扣押地点：</label>
					<span name="proSeizurePlace"></span>
				</li>


				<li class="w100">
					<label>扣押依据：</label>
					<span name="proSeizureBasis"></span>
				</li>
				<li class="w100">
					<label>备注：</label>
					<span name="proRemark"></span>
				</li>
			</ul>
			<div class="toptit">
				<h1 class="h1tit">鉴定信息</h1>
			</div>
			<ul class="list02a w50">
				<li>
					<label>鉴定结果：</label>
					<span name="ideResult"></span> 
				</li>
				<li>
					<label>鉴定日期：</label>
					<span name="ideDate"></span>
				</li>
				<li>
					<label>鉴定机构：</label>
					<span name="ideUint"></span>
				</li>
				<li>
					<label>鉴定人：</label>
					<span name="idePerson"></span>
				</li>
				<li class="w100">
					<label>经办人：</label>
					<span name="ideHandle"></span>
				</li>
			</ul>
			
			<div class="toptit">
				<h1 class="h1tit">法律文书</h1>
			</div>

			<ul class="ws_list" id="attachmentList">
			</ul>

			<div class="toptit">
				<h1 class="h1tit">财物图片</h1>
			</div>
			<ul class="list02a w50">
				<li class="w100">
					<ul class="cwpic" id="imgList" style="border:none;margin-bottom:0px;">
					</ul>
				</li>
			</ul>
			
			<div class="dbut">
				<input type="button" value="返回" id="goBack"/>
			</div>
		</form>
	</div>
<div id="selectDataDiv" style="display:none;">
	<select name="proOrigin">
		<jsp:include page="/jsp/plugins/proOrigin_options.jsp" />
	</select>
	<select name="proType">
		<jsp:include page="/jsp/plugins/proType_options.jsp" />
	</select>
	<select name="idType">
		<jsp:include page="/jsp/plugins/idType_options.jsp" />
	</select>
	<select name="proStatus">
		<jsp:include page="/jsp/plugins/proStatus_options.jsp" />
	</select>
	<select name="proNature">
		<jsp:include page="/jsp/plugins/proNature_options.jsp" />
	</select>
</div>
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
		<li class="w100">
			<label>图片描述：</label>
			<textarea rows="3" cols="3" name="picDescription"></textarea>
		</li>
		<li class="w100" style="padding-left: 30px;">
			<div id="showPicDiv"></div>
		</li>
	</ul>
</div>
<script type="text/javascript">
//分类属性操作
var cateAttrMgr={
	showCategoryInit:function(){
		var categoryId=$('input[name="categoryId"]').val();
		$.ajax({
			type: 'GET',
			cache:false,
			url: sysPath+'/category/findCategoryAttributeByCategoryId.do?categoryId='+categoryId,
			dataType:"json",
			success: function(data){
				$('li[name="attr"]').remove();
				$('#categoryLi').css('display','none');
				$('input[name="cateAttrInfo"]').val('');
				if(data!=null){
					if(data.length>0)
						$('#categoryLi').css('display','block');
					var li='';
					for(var i=0;i<data.length;i++){
						//如果是最后一个数据，并且总条数是奇数，则需要平铺整行
						var pcls='';
						if(i==data.length-1&&data.length%2==1){
							pcls='class="w100"';
						}
						
						var singleData=data[i];
						li=li+'<li name="attr" '+pcls+'><label><input type="hidden" name="attrId" value="'+singleData.id+'"/>'
						+'<input type="hidden" name="attrName" value="'+singleData.attrName+'"/>'
						+'<input type="hidden" name="attrType" value="'+singleData.attrType+'"/>'
						+singleData.attrName+'：</label>';
						if(singleData.attrType==1){
							li=li+'<span name="attrValue">'+singleData.attrValue+'</span>';
						}else if(singleData.attrType==2){
							var attrValueArr=singleData.attrValue.split(',');
							var select='<select name="attrValue" disabled="disabled"><option value="">请选择</option>';
							for(var j=0;j<attrValueArr.length;j++){
								select=select+'<option value="'+attrValueArr[j]+'">'+attrValueArr[j]+'</option>';
							}
							select=select+'</select>';
							li=li+select;
						}else if(singleData.attrType==3){
							var attrValueArr=singleData.attrValue.split(',');
							var radio='';
							for(var j=0;j<attrValueArr.length;j++){
								radio=radio+'<input disabled="disabled" name="attrValue" type="radio" value="'+attrValueArr[j]+'" style="float: none;"/>'+attrValueArr[j];
							}
							li=li+radio;
						}else if(singleData.attrType==4){
							var attrValueArr=singleData.attrValue.split(',');
							var checkbox='';
							for(var j=0;j<attrValueArr.length;j++){
								checkbox=checkbox+'<input disabled="disabled" name="attrValue" type="checkbox" value="'+attrValueArr[j]+'" style="float: none;"/>'+attrValueArr[j];
							}
							li=li+checkbox;
						}else if(singleData.attrType==5){
							li=li+'<span name="attrValue">'+singleData.attrValue+'</span>';
						}
						li=li+'</li>';
					}
					$('#categoryLi').after(li);
					cateAttrMgr.getAttrValueAndSet();
				}
			}
		});
	},
	
	getAttrValueAndSet:function(){
		$.ajax({
			type: 'GET',
			cache:false,
			url: sysPath+'/property/findPropertyAttributeByPropertyId.do?proId='+proId,
			dataType:"json",
			success: function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){
						var singleData=data[i];
						$('li[name="attr"]').each(function(idx,el){
							var attrId=$.trim($(el).find('input[name="attrId"]').val());
							if(attrId==singleData.attrId){
								if(singleData.attrType==1){
									$(el).find('span[name="attrValue"]').html(singleData.attrValue);
								}else if(singleData.attrType==2){
									$(el).find('select[name="attrValue"]').val(singleData.attrValue);
								}else if(singleData.attrType==3){
									$(el).find('input[name="attrValue"][value="'+singleData.attrValue+'"]').prop('checked',true);
								}else if(singleData.attrType==4){
									var attrValueArr=singleData.attrValue.split(',');
									for(var j=0;j<attrValueArr.length;j++){
										$(el).find('input[name="attrValue"][value="'+attrValueArr[j]+'"]').prop('checked',true);
									}
								}else if(singleData.attrType==5){
									$(el).find('span[name="attrValue"]').html(singleData.attrValue);
								}
							}
						});
					}
				}
			}
		});
	}
}
var propertyPreModify={
	startEvent:function(){
		$.ajax({
			type: 'get',
			cache:false,
			url: sysPath+'/property/queryDetailById.do?proId='+proId,
			dataType:"json",
			success: function(data){
				if(data!=null){
					//基本信息
					var pro=data.tproperty;
					
					for(var i in pro){
						var text=pro[i];
						if(i=='proOrigin'||i=='proType'||i=='proStatus'||i=='proNature'){
							if(text!=null&&text!='')
								text=$('#selectDataDiv select[name="'+i+'"] option[value="'+text+'"]').text();
						}else if(i=='proIsSave'){
							text=text==1?'是':'否';
						}else if(i=='categoryId'){
							$('#modifyForm input[name="'+i+'"]').val(text);
							continue;
						}else if(i=='proSpec'&&text!=''){
							var proSpec='长：'+text.split(',')[0]+'，宽：'+text.split(',')[1]+'，高：'+text.split(',')[2];
							$('#modifyForm span[name="proSpec"]').html(proSpec);
							continue;
						}
						$('#modifyForm span[name="'+i+'"]').html(text);
					}
					//图片信息
					var tpictureList=data.tpictureList;
					var uploadText = '';
					for(var i=0;i<tpictureList.length;i++){
						var captureTime=tpictureList[i].captureTime;
						if(captureTime.indexOf('.')!=-1){
							captureTime=captureTime.substring(0,captureTime.indexOf('.'));
						}
						var imgStr = JSON.stringify(tpictureList[i]).replace(/\"/g,"'");
						
						uploadText += '<li><a href="javascript:void(0)" onclick="showImgDetail(this, 1)">'
						+'<img src="'+photoUrl+tpictureList[i].originalUrl+'" width="100px" height="100px"/>'
						+tpictureList[i].name+'</a><input type="button" value="查看" onclick="showDiv2(this)">'
						+'<input type="hidden" name="pictureJson" value="'+imgStr+'"></li>';
					}
					$('#imgList').html(uploadText);
					
					//持有人信息
					propertyPreModify.setRelatePerson(data.ownerInfo,'o');
					//所有人信息
					propertyPreModify.setRelatePerson(data.everyOneInfo,'e');
					//案件信息
					var caseInfo=data.caseInfo;
					for(var i in caseInfo){
						var text=caseInfo[i];
						if(i=='caseType'){
							text=text==1?'刑事案件':'行政案件';
						}
						$('#modifyForm span[name="'+i+'"]').html(text);
					}
					//保管人信息
					propertyPreModify.setRelatePerson(data.bgPerson,'b');
					//见证人信息
					propertyPreModify.setRelatePerson(data.jzPerson,'j');
					//当事人信息
					propertyPreModify.setRelatePerson(data.dsPerson,'d');
					//鉴定信息
					var identifyInfo=data.identifyInfo;
					for(var i in identifyInfo){
						var text=identifyInfo[i];
						if(i=='ideDate'&&text!=null&&text!=''){
							var ideDate=text.substring(0,10);
							$('#modifyForm span[name="'+i+'"]').html(ideDate);
							continue;
						}
						$('#modifyForm span[name="'+i+'"]').html(text);
					}
					
					//显示附件信息
					var attachmentList=data.attachmentList;
					var li='';
					for(var i=0;i<attachmentList.length;i++){
						li=li+'<li><a href="javascript:void(0)" onclick="downloadFile(\''+attachmentList[i].uploadUrl+'\')">'+attachmentList[i].attaName+'</a></li>';
					}
					$('#attachmentList').html(li);
					
					//显示分类属性
					cateAttrMgr.showCategoryInit();
				}
			}
		});
	},
	
	//设置所有人，持有人，保管人，见证人，当事人
	setRelatePerson:function(data,type){
		for(var i in data){
			var text=data[i];
			var name=type+i;
			if(i=='idType'){
				if(text!=null&&text!='')
					text=$('#selectDataDiv select[name="'+i+'"] option[value="'+text+'"]').text();
			}else if(i=='civiGender'){
				text=text==1?'男':'女';
			}
			$('#modifyForm span[name="'+name+'"]').html(text);
		}
	},
	
	bindEvent:function(){
		$('#goBack').on('click',function(){
			history.go(-1);
		});
	},
	
	init:function(){
		propertyPreModify.bindEvent();
		propertyPreModify.startEvent();
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
	    	var captureTime=jsonObj.captureTime;
	    	if(captureTime.indexOf('.')!=-1){
				captureTime=captureTime.substring(0,captureTime.indexOf('.'));
			}
	    	$('#uploadPicUl input[name="captureTime"]').val(captureTime);
	    	$('#uploadPicUl input[name="capturePlace"]').val(jsonObj.capturePlace);
	    	$('#uploadPicUl input[name="picId"]').val(jsonObj.picId);
	    	var picPath=photoUrl+jsonObj.originalUrl;
			var picLi='<a href="'+picPath+'" target="_blank">'
			+'<img src="'+picPath+'" width="50px" height="50px"/></a>';
			$('#showPicDiv').html(picLi);
	    },
	    cancel: true
	});
}
$(function() {
	propertyPreModify.init();
});
</script>

</body>
</html>

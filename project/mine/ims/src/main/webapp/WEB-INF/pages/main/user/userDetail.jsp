<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/pages/common.jspf"%>

<html>
<head>

<title>新增用户</title>
<script type="text/javascript" src="${path }/common/js/ajaxfileupload.js"></script>
<script type="text/javascript">
function getData(url,params,appendId){
	var dataList=getJson(url,params);
	var options="";
	for (var i = 0; i < dataList.length; i++) {
		if (appendId=="province") {
			options+="<option value="+dataList[i].provinceId+">"+dataList[i].province+"</option>";
		}else if (appendId=="city") {
			options+="<option value="+dataList[i].cityId+">"+dataList[i].city+"</option>";
		}else{
			options+="<option value="+dataList[i].areaId+">"+dataList[i].area+"</option>";
		}
	}
	return options;
}


function loadQuestions(){
	var questions=getJson("${path}/user/getQuestions.do", {});
	var html="";
	for (var i = 0; i < questions.length; i++) {
		var parentId=questions[i].parentId;
		var id=questions[i].id;
		var title=questions[i].question;
		if (parentId==null) {
			html+="<optgroup label='"+title+"'>";
			for ( var j = 0; j < questions.length; j++) {
				if (questions[j].parentId==id) {
					html+="<option value='"+questions[j].id+"'>"+questions[j].question+"</option>";
				}
			}
			html+=" </optgroup>";
		}
	}
	$form.find('select[class=question]').append(html);
	form.render();
	
	form.on('select(question)', function(data) {
     	var q1=$('select[id=question1]').val();
     	var q2=$('select[id=question2]').val();
     	var q3=$('select[id=question3]').val(); 
     	if (isVal(q1)) {
     		$form.find('select[id=question2] optgroup option[value='+q1+']').attr("disabled",'');
     		$form.find('select[id=question3] optgroup option[value='+q1+']').attr("disabled",'');
		}
     	if (isVal(q2)) {
     		$form.find('select[id=question1] optgroup option[value='+q2+']').attr("disabled",'');
     		$form.find('select[id=question3] optgroup option[value='+q2+']').attr("disabled",'');
		}
     	if (isVal(q3)){
     		$form.find('select[id=question1] optgroup option[value='+q3+']').attr("disabled",'');
     		$form.find('select[id=question2] optgroup option[value='+q3+']').attr("disabled",'');
		}
     	form.render();
    })
}
function loadProvince(){
	 var proHtml=getData("${path}/user/getProvinces.do",{},"province");
     $form.find('select[name=province]').append(proHtml);
     form.render();
     form.on('select(province)', function(data) {
     	var provinceId=$('select[name=province]').val();
     	$("select[name=city] option:gt(0)").remove();
     	$("select[name=area] option:gt(0)").remove();
     	loadCity(provinceId);
      })
}
function loadCity(provinceId){
	 var proHtml=getData("${path}/user/getCitys.do",{fatherId:provinceId},"city");
     $form.find('select[name=city]').append(proHtml);
     form.render();
     form.on('select(city)', function(data) {
    	var cityId=$('select[name=city]').val();
    	$("select[name=area] option:gt(0)").remove();
    	loadArea(cityId);
     })
}
function loadArea(cityId){
	 var proHtml=getData("${path}/user/getAreas.do",{fatherId:cityId},"area");
     $form.find('select[name=area]').append(proHtml);
     form.render();
}

function save() {
	var str = $("#form").serialize();
	$.ajax({
		type : 'post',
		async : false,
		url : document.forms[0].action,
		data : str,
		success : function(result) {
			//layer.msg(result.message);
			layer.confirm(result.message, {
				  btn: ['停留在当前页面','返回上一层'] //按钮
				}, function(){
				 layer.closeAll();
				}, function(){
					goBack();
			});
		},
		dataType : 'json'
	}) 
}
function goBack() {
	 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.location.reload();
	parent.layer.close(index)
	//location.href="${path}/user/userManage.do";
}

function uploadImg(){
	var imgVal = $("#file").val();
	var uploadFiles =document.getElementById("file").files; 
  	var maxSize=1 * 1024 * 1024;
 	var patrn = /^.+(jpg|JPG|jpeg|JPEG|png|PNG)$/;
  	var result = true;
	if(!patrn.exec(imgVal)){
		layer.alert("图片文件只能是png,jpg,jpeg,gif格式的图片");
		$("#file").val("");
		 result =  false;
	}
 	 if(uploadFiles){   
 		if(uploadFiles.length>1){
 			  layer.alert("只能上传一张图片");
 			$("#file").val("");
 			result = false;
 		  }else if(uploadFiles[0].size>maxSize){
 			 layer.alert("支持最大的图片大小是1024K！！");
 			$("#file").val("");
 			result = false;
 		}
 	 }else{
 		layer.alert("请选择一张图片！！");
		 result = false;
 	 }
	  	 
  	 if(result){
  		layer.load();
  		$.ajaxFileUpload({ 
                url:'${path}/user/upload.do',//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:'file',//文件上传空间的id属性  <input type="file" id="file" name="file" />
                dataType: 'json',//返回值类型 一般设置为json
                success: function (data){  //服务器成功响应处理函数
                       if(data!=null){
                        	//将隐藏文本域,图片路径保存
                        	$("#BrandImg").attr("src",data[0]);
                        	$("#imagePath").val(data[1]);
                        	$("#thumImagePath").val(data[2]);
                        	result = true;
                        }else{
                        	layer.alert("很抱歉，网络异常，请稍后重试！");
                        	result = false;
                        }
                },
                error: function (data, status, e){//服务器响应失败处理函数
                	alert(e);
                	result = false;
                }
            }
        ); 
  	 }
	if(result){
		layer.closeAll("loading");
		return true;
	}else{
		return false;
	}
}

</script>
</head>
<body>
	<form class="layui-form" action="${path }/user/save.do" id="form" method="post">
		<input type="hidden" id="userId" name="userId" value="${user.id }" />
		<input type="hidden" id="proId" value="${ud.provinceId }" />
		<input type="hidden" id="citId" value="${ud.cityId }" />
		<input type="hidden" id="areId" value="${ud.areaId }" />
		<div class="layui-form-item">
			<label class="layui-form-label">用户名称:</label>
			<div class="layui-input-block">
				<input type="text" name="userName" required lay-verify="required"
					value="${user.userName }" placeholder="请输入用户名" class="layui-input"
					style="width: 350px; margin-top: 20px">
			</div>
			</br> <label class="layui-form-label">用户昵称:</label>
			<div class="layui-input-block">
				<input type="text" name="nickName" required
					lay-verify="required" value="${user.nickName }"
					placeholder="请输入昵称" class="layui-input" style="width: 350px;">
			</div>
		<%-- 	</br> <label class="layui-form-label">用户账号:</label>
			<div class="layui-input-block">
				<input type="text" name="userAccount" required
					lay-verify="userAccount" value="${user.userAccount }"
					placeholder="请输入账号" class="layui-input" style="width: 350px;">
			</div> --%>
			<%-- <div class="layui-form-item">
				<label class="layui-form-label">密码:</label>
				<div class="layui-input-inline">
					<input type="password" name="password" placeholder="请输入密码" required
						lay-verify="pass" value="${user.password }" class="layui-input"
						style="width: 200px;">
				</div>
				<div class="layui-form-mid layui-word-aux">请填写6到12位密码</div>
			</div> --%>
			</br><label class="layui-form-label">性别：</label>
		    <div class="layui-input-block">
		      <input type="radio" name="sex" value="0" title="男" <c:if test="${ud.sex=='0' }">checked=''</c:if> >
		      <input type="radio" name="sex" value="1" title="女" <c:if test="${ud.sex=='1' }">checked=''</c:if>>
		      <input type="radio" name="sex" value="禁" title="无性" disabled="">
		    </div>
		    </br>
			<div class="layui-form-item">
				<label class="layui-form-label">居住省市:</label>
					<div class="layui-input-inline">
						<select name="province"  id="province" lay-filter="province"  required lay-verify="required">
							<option value="">请选择省</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<select name="city" id="city" lay-filter="city" required lay-verify="required">
							<option value="">请选择市</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<select name="area" id="area" lay-filter="area" required lay-verify="required">
							<option value="">请选择县/区</option>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" >账号保护:</label>
					<div class="layui-input-inline">
						<select class="question" id="question1" name="question1"  lay-filter="question">
							<option value="">请选择回答问题一</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<input type="text" name="answer1"    placeholder="请输入问题一答案" class="layui-input" style="width: 250px;display: inline-block;">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" ></label>
					<div class="layui-input-inline">
						<select class="question" id="question2" name="question2"  lay-filter="question">
							<option value="">请选择回答问题二</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<input type="text" name="answer2"    placeholder="请输入问题二答案" class="layui-input" style="width: 250px;display: inline-block;">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" ></label>
					<div class="layui-input-inline">
						<select class="question" id="question3" name="question3"  lay-filter="question">
							<option value="">请选择回答问题三</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<input type="text" name="answer3"    placeholder="请输入问题三答案" class="layui-input" style="width: 250px;display: inline-block;">
					</div>
				</div>
				<label class="layui-form-label" style="display: inline;">具体地址:</label>
				<div class="layui-input-block" style="width: 750px">
					<textarea name="detailAddress" placeholder="请输入具体地址" lay-verify="required" class="layui-input" >${ud.detailAddress }</textarea>
				</div>
				</br>
				<label class="layui-form-label" style="display: inline;">上传头像:</label>
				<div class="layui-input-inline">
					<input type="hidden" name="imagePath" id="imagePath" value="${ud.imagePath }"  lay-verify="image"/>
					<input type="hidden" name="thumImagePath" id="thumImagePath" value="${ud.thumImagePath }"  lay-verify="image"/>
					<img id="BrandImg"  
						<c:if test="${not empty ud.imagePath }">src='${ud.imagePath }'</c:if>
						<c:if test="${empty ud.imagePath }">src='${path }/images/uploadImg.gif'</c:if>
					  alt="图片logo" style="width: 100px;height:100px;"/>
					<input type="file" name="file" id="file" onchange="return uploadImg();" multiple="true">
				</div>
		</div>
		
		<div style="text-align: center;">
			<button class="layui-btn layui-btn-normal layui-btn-radius" lay-filter="save" lay-submit=""
				type="button" style="width: 80px; background-color: #009688">保存</button>
			<button class="layui-btn layui-btn-warm layui-btn-radius"
				type="button" style="width: 80px; background-color: #393D49"
				onclick="goBack()">返回</button>
		</div>
	</form>
	<script type="text/javascript">
	var $,form,$form;
	layui.use(['jquery', 'form'], function(){
		 $ = layui.jquery;
	     form = layui.form();
	     $form = $('form');
	     
	     var proId=$("#proId").val();
	     if (!isVal(proId)) {
		     //加载省下拉框
		     loadProvince();
		}else{
			var citId=$("#citId").val();
			var areId=$("#areId").val();
			loadProvince();
			loadCity(proId);
			loadArea(citId);
			$("select[name=province] option[value='"+proId+"']").prop("selected",true);
			$("select[name=city] option[value='"+citId+"']").prop("selected",true);
			$("select[name=area] option[value='"+areId+"']").prop("selected",true);
		}
	     loadQuestions();
		  //自定义验证规则
		  form.verify({
		    title: function(value){
		      if(value.length < 3){
		        return '标题至少得3个字符啊';
		      }
		    },
		    image: function(value){
		      if(!isVal(value)){
		        return '请上传头像';
		      }
		    }
		    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
		    ,mobile:[/^1[34578]\d{9}$/,"手机号码格式不正确"]
		    ,content: function(value){
		      layedit.sync(editIndex);
		    }
		  });
		  form.render(); //更新全部
		  //监听提交
		  form.on('submit(save)', function(data){
		   	save();
		    return false;
		  });
		  
	});
	</script>
</body>
</html>
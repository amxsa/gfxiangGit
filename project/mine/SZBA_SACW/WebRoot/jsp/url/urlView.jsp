<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>URL详情</title>
<%@ include file="../../common/common.jsp"%>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> URL管理 >> URL详情</span></div>
<div class="content">
 <form id="saveForm" action="${path}/url/save.do" method="post">
  <input type="hidden" name="id" value="${po.id }"/> 
 <div class="toptit"><h1 class="h1tit">详细信息</h1></div>
 <ul class="list02 w50">
   <li><label>URL：</label><input name="url" type="text" value="${po.url }"  maxlength="200" datatype="*1-200" nullmsg="请输入URL！"/></li>
   <li><label>类型：</label><input name="type" type="text" value="${po.type }" maxlength="1" datatype="n1-1" nullmsg="请选择类型！"/></li>
   <li><label>描述：</label><input name="description" type="text" value="${po.description }" maxlength="100"/></li>
   <li><label>组ID：</label><input name="groupId" type="text" value="${po.groupId }" maxlength="18"/></li>
 </ul>
 <div class="dbut"><input type="submit" name="saveBut" value="保存"/><input type="button" onclick="history.go(-1)" value="返回"/></div>
 </form>
</div>
<script type="text/javascript">
//校验提交form
vaildSubmitFormForAjax("saveForm",
	function(){//提交前做其他校验
		if(!window.confirm("确认保存？")){
			return false;
		}	
		return true;
	},
	function(data){//ajax后返回json
		if(data && data.state){
			if(data.msg != ""){
				alert(data.msg);
			}
			if(data.state == "Y"){
				location.href = "${path }/url/queryList.do";	
			}
		}
	}
);

/*
			var options = {
				success: function(rep) {alert(rep);
				}
			};
			$("#saveForm").ajaxSubmit(options);		
*/
</script>
</body>
</html>
<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String msg=(String)request.getAttribute("msg");


%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 
    
    <title>标签更换</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="<%=path%>/css/main.css" type="text/css" href="styles.css">
	<style type="text/css">
	
	.mydiv{background-color:#fff;border:1px solid #ccc;
text-align:center;font-size:12px;font-weight:normal;z-index:999;width:900px;height:290px;padding-bottom:15px;left:25%;top:30%;margin-left:-150px!important;/*FF IE7 锟斤拷值为锟斤拷锟斤拷锟斤拷一锟斤拷 */margin-top:-60px!important;/*FF IE7 锟斤拷值为锟斤拷锟斤拷叩锟揭伙拷锟�/margin-top:0px;position:fixed!important;/* FF IE7*/position:absolute;/*IE6*/_top:expression(eval(document.compatMode &&document.compatMode=='CSS1Compat') ?documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/}
	</style>
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>

  </head>
  
  <body>
 <div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 标签更换</span></div>
<div class="content">
 <form action="<%=path%>/label/insert.do" method="post" id="adform" name="adform">
 <input type="hidden" id="pno" name="pno"/>

 <div class="toptit"><h1 class="h1tit">标签更换</h1></div>
 <ul class="list02">
 	
   <li><label>选择财物：</label><input type="text" id="pname" name="pname"/><input type="button" value="选择" style="width:50px;" id="selectAj"/><font color="red">*必填项</font></li>
   <li><label>新标签：</label><input name="newName" id="newname" type="text"  value="8989" /><input type="button" value="制作" style="width:50px;" onclick="tishi();"/><font color="red">*必填项</font></li>
  
   <li><label>更换原因：</label><input type="text" name="description"/></li>
 </ul>
 <div class="dbut"><a href="#" onclick="tijiao()"/>保存</a><a href="#" onclick="window.history.back();">返回</a></div>
 </form>

<script type="text/javascript">
function tijiao(){
var a=document.getElementById("newname").value;
var b=document.getElementById("pname").value;
	
	if(b==null||b==""){
		alert("请选择财物！");
		return false;
	}
	if(a==null||a==""){
		alert("新标签不能为空！");
		return false;
	}
	else{
	document.getElementById("adform").submit();
	}
}
function tishi(){
alert('需连打印机，暂缓开发');

}
$(document).ready(function() {
$('#selectAj').on('click',function(){
			var path=sysPath+'/jsp/label/labelPreAddorEditProList.jsp';
			art.dialog.open(path, {
			    title: '选择财物',
			    width: 900,
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
			    	var checkedLen=$(table_hover).find('input[name="proNo"]:checked').length;
			    	if(checkedLen==0) { 
			    		alert('至少选择一个财物');
			    		return false;
			    	}
			    	else{
			    	var aa=$(table_hover).find('input[name="proNo"]:checked').val();
			    	document.getElementById("pno").value=aa;
			    	var bb = iframe.document.getElementById(aa).innerHTML;
			    	document.getElementById('pname').value=bb;
			    	
			    	}
			    	
			    
			    },
			    cancel: true
			});
		});
});		
$(document).ready(function() {
		var message = '<%=msg%>';
		
		if(message != ''){
			if(message=='success'){
				alert('添加成功！');
				window.location.href="${path}/label/queryForPage.do";
			} if(message=='fail'){
				alert('添加失败，请稍后重试！');
			}
			
		}
});
</script>
  </body>
</html>

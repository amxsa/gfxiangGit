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
 
    
    <title>修改损毁登记</title>
    
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
 <div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 修改损毁登记</span></div>
<div class="content">
 <form action="/SZBA_SACW/damage/editDamage.do?action=edit" method="post" id="adform" name="adform">
  <input type="hidden"  name="id" value="${tDemage.id }"/>
  <input type="hidden" id="pno" name="pno" value="${tDemage.proNO }"/>
 <div class="toptit"><h1 class="h1tit">修改损毁登记</h1></div>
 <ul class="list02">
 	
   <li><label>选择财物：</label><input type="text" id="pname" name="pname" value="${tDemage.proName }"/><input type="button" value="选择" style="width:50px;" id="selectAj"/></li>
   <li><label>损毁时间：</label><input name="DTime" type="text"  value="<fmt:formatDate value='${tDemage.createTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></li>
   <li><label>损毁人：</label><input type="text" name="dAccount" value="${tDemage.damageAccount }"/></li>
   <li><label>损毁原因：</label><input type="text" name="description" value="${tDemage.description }"/></li>
 </ul>
 <div class="dbut"><a href="#" onclick="tijiao()">修改</a><a href="#" onclick="window.history.back();">返回</a></div>
 </form>

<script type="text/javascript">
function tijiao(){

$("#adform").submit();

}
$(document).ready(function() {
$('#selectAj').on('click',function(){
			var path=sysPath+'/jsp/damage/damagePreAddorEditProList.jsp';
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
				alert('修改成功！');
				window.location.href="${path}/damage/queryForPage.do";
			} if(message=='fail'){
				alert('修改失败，请稍后重试！');
			}
			
		}
});
</script>
  </body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	if (session.getAttribute("workSQL") == null) {
		response.sendRedirect(path + "/util.jsp");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>执行SQL</title>
		<style type="text/css" media="all">
			@import url("<%=path%>/css/maven-base.css");
			
			@import url("<%=path%>/css/maven-theme.css");
			
			@import url("<%=path%>/css/site.css");
			
			@import url("<%=path%>/css/screen.css");
		</style>
		<link rel="stylesheet" href="<%=path%>/css/print.css"
			type="text/css" media="print" />
		<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
		<script type="text/javascript" language="javascript">
    	$(document).ready(function (){
    		var sqlTextarea=$('#sqlTextarea');
    		var claerBtn=$('#clearBtn');
    		var logoutBtn=$('#logoutBtn');
    		var execBtn=$('#execBtn');
    		sqlTextarea.focus();
    		
    		//清空按钮
    		claerBtn.click(function(){
    			sqlTextarea.val('');
    			sqlTextarea.focus();
    		})
    		
    		//退出按钮
    		logoutBtn.click(function() {
    			logout();
    		})
    		
    		//执行按钮
    		execBtn.click(function () {
    			var textareaValue=$.trim(sqlTextarea.val());
    			if(textareaValue=='') {
    				alert('请输入合法的SQL语句');
    				return false;
    			} else {
    				submit();
    			}
			})
    	})
    	function logout() {
			window.location='logout.jsp'
		}
		function isKeyTrigger(e,keyCode){    
		    var argv = isKeyTrigger.arguments;    
		    var argc = isKeyTrigger.arguments.length;    
		    var bCtrl = false;    
		    if(argc > 2){    
		        bCtrl = argv[2];    
		    }    
		    var bAlt = false;    
		    if(argc > 3){    
		        bAlt = argv[3];    
		    }    
		    var nav4 = window.Event ? true : false;    
		    if(typeof e == 'undefined') {    
		        e = event;    
		    }    
		    if(bCtrl &&    
		        !((typeof e.ctrlKey != 'undefined') ?     
		        e.ctrlKey :    
		        e.modifiers & Event.CONTROL_MASK > 0)) {    
		        return false;    
		    }    
		    if( bAlt &&    
		        !((typeof e.altKey != 'undefined') ?     
		            e.altKey : e.modifiers & Event.ALT_MASK > 0)){    
		        return false;    
		    }    
		    var whichCode = 0;    
		    if (nav4) whichCode = e.which;    
		    else if (e.type == "keypress" || e.type == "keydown") whichCode = e.keyCode;    
		    else whichCode = e.button;    
		        
		    return (whichCode == keyCode);    
		}    
		   
		function ctrlEnter(e){    
		    var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
		    if(ie){    
		        if(event.ctrlKey && event.keyCode == 13) {
		            submit();    
		        }    
		        else {    
		            if(isKeyTrigger(e,13,true)){
		             	submit();
		             }    
		        }    
		    }    
		}    
		   
		function submit() {
		    document.getElementById('sqlForm').submit();    
		}  
    </script>

	</head>

	<body>

		<center>
			<p align="left">
				<a href="<%=path%>/workhelp/view.jsp">文件操作</a>
			</p>
			<font color="#ff0000">提交SQL语句(请避免大数据量的查询操作，必要时请在相关表加上对应的索引)<a
				href="logout.jsp">退出</a> </font>
			<form action="<%=path%>/workhelp/SQLAction_executeSql.do"
				method="post" name="sqlForm" id="sqlForm">
				<textarea id="sqlTextarea" name="sql" rows="6" cols="100"
					onkeydown="ctrlEnter(event)">${sql}</textarea>
				<br />
				数据库  <select name="dataBaseName">
					<option value="CZT">CZT</option>
					<option value="CZT_WX">CZT_WX</option>
				</select>
				&nbsp;
				<input type="button" value="执行" id="execBtn" />
				&nbsp;
				<input type="button" id="clearBtn" value="清空" />
				<input type="button" value="退出" id="logoutBtn" />
				<br />
				提示:可按Ctrl+Enter执行
			</form>
			<c:if test="${!empty total}">
				<font color="#ff0000">找到${total}条记录.SQL执行时间：${costTimes}ms</font>
			</c:if>
			<c:if test="${!empty count}">
				<font color='red'>${count}条记录被成功执行.SQL执行时间：${costTimes}ms</font>
			</c:if>

			<c:if test="${! empty content}">
				<c:out value="${content}" escapeXml="false" />
			</c:if>
		</center>
	</body>
</html>

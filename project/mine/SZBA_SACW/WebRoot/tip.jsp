<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>温馨提醒</title>
      <script language="javascript">
      	var excessPros = "${excessPros}";
      	var referer = "${referer}";
	    
	    if(parent)
			parent.window.location="<%=request.getContextPath()%>/login.jsp";
		if(parent.parent)
			parent.parent.window.location="<%=request.getContextPath()%>/login.jsp";
     </script>
  </head>
  
  <body>
  </body>
</html>

<%@ page language="java"  pageEncoding="UTF-8" contentType="image/png" %>
<%@ page import="java.io.*,org.activiti.engine.*" %>

<%  
    InputStream is = (InputStream)request.getAttribute("inputStream");
    byte[] b = new byte[1024];  
    int len = -1;  
    
    while((len = is.read(b, 0, 1024)) != -1) {  
        response.getOutputStream().write(b, 0, len); 
        // 防止异常：getOutputStream() has already been called for this response
        out.clear();
        out = pageContext.pushBody();
    }
%>

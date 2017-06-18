<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>top</title>
<script type="text/javascript">
var NewsPos = 0;
var MagazinePos = 0;
var NewsMouserover = 0;
function selectNews(move,divID,layerCnt)
{
 if(!divID)  divID = 'news_';
 if(!move) move = 1;
 if(!layerCnt) layerCnt = 3;
 if (divID=='news_'){
  if(NewsMouserover==0){
   NewsPos = (NewsPos + layerCnt + move) % layerCnt ; 
  }
 }
 else { MagazinePos = (MagazinePos + layerCnt + move) % layerCnt ; }
 for(var i=0; i<layerCnt; i++){
  obj = document.getElementById(divID+i).style;
  if (divID=='news_'){
   if(NewsMouserover==0){
    if(i == NewsPos){obj.display = '';}
    else{obj.display = 'none';}
   }
  } else {
   if(i == MagazinePos){obj.display = '';}
   else{obj.display = 'none';}
  }
 }
}
</script>

<script type="text/javascript" src="../themes/js/xiaxi/utilities.js"></script>
<script type="text/javascript" src="../themes/js/xiaxi/container_core-min.js"></script>

<script type="text/javascript" src="../themes/js/jquery.js"></script>
<script type="text/javascript" src="../themes/js/styleswitch.js"></script>
<link rel="stylesheet" href="../themes/skyblue/skyblueTop.css" type="text/css" title="styles1" />
<script type="text/javascript">
function reloadPage()
  {
  parent.topControl.location.reload(); 
  parent.leftFrame.location.reload();
  parent.leftControl.location.reload();
  parent.mainFrame.location.reload();

  }
</script>
<script type="text/javascript" src="js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="js/top.js"></script>
</head>
<body>
<div id="top">
  <div id="guangao">
  <div id="info-main">
    <span style="font-size: 30px;font-weight: bolder;color:black;margin-left: 10px">
  
    </span>
    </div>
  </div>
  <div id="toolbar">
    <div id="bar">
      <div id="user"></div>
      <div id="userinfo">
        <ul>
           <li> 账号：${sessionScope.login.account}</li> 
        </ul>
      </div>
      <div id="userinfo2">
        <ul class="userlink">
       
       		 <li><a href="<%=path %>/manager/updateManager.jsp" target="mainFrame">个人资料</a></li>
          <li><a href="<%=path %>/manager/updatePassword.jsp" target="mainFrame">修改密码</a></li>
       	
         
          <li class="close"><a href="<%=path %>/manager/LoginManagerAction_logout.do" target="_parent">退出系统</a></li>
         
        </ul>
      </div>
    </div>
  </div>
</div>
</body>
</html>

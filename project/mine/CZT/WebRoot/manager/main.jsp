<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.jfree.chart.*,org.jfree.chart.plot.PiePlot,org.jfree.chart.title.TextTitle,org.jfree.chart.labels.*,  
org.jfree.data.general.DefaultPieDataset,org.jfree.chart.servlet.ServletUtilities,java.awt.*,java.text.NumberFormat"%>  
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if(request.getAttribute("map")!=null){
	Map map = (Map)request.getAttribute("map");
	//设置饼图数据集  
	DefaultPieDataset dataset = new DefaultPieDataset();  
	dataset.setValue("已激活", (Integer)map.get("cztCount"));  
	dataset.setValue("未激活", (Integer)map.get("noActivateCount"));  
	//通过工厂类生成JFreeChart对象  
	JFreeChart chart = ChartFactory.createPieChart("OBD激活分布图", dataset, true, true, false);  
	//加个副标题  
	//chart.addSubtitle(new TextTitle("2010年度"));  
	PiePlot pieplot = (PiePlot) chart.getPlot();  
	pieplot.setLabelFont(new Font("宋体", 0, 11));  
	//设置饼图是圆的（true），还是椭圆的（false）；默认为true  
	pieplot.setCircular(true);  
	StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1},{2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());  
	pieplot.setLabelGenerator(standarPieIG);  
	  
	//没有数据的时候显示的内容  
	pieplot.setNoDataMessage("无数据显示");  
	pieplot.setLabelGap(0.02D);  
	  
	String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, session);  
	String graphURL = path + "/DisplayChart?filename=" + filename;  
	pageContext.setAttribute("graphURL", graphURL);
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作界面</title>
<style type="text/css">
*{margin:0;padding:0;border:0;font-size:12px;font-family:"Microsoft YaHei";font-style:normal;font-weight:normal;}
body{margin:0;padding:0;}
a{text-decoration:none;}
em{font-style:normal;}
ul,li{list-style:none;}
.fl{float:left;}
.fr{float:right;}
.cz_main{margin:0 auto;padding:10px;}
.cz_part{border:1px solid #cdcdcd;margin-bottom:20px;}
.cz_part .title{overflow:hidden;zoom:1;border-bottom:1px solid #cdcdcd;width:100%;background-color:#f7f7f7;height:30px;overflow:hidden;zoom:1;}
.cz_part .title span{width:30px;height:30px;border-right:1px solid #cdcdcd;}
.cz_part .title h2{line-height:30px;color:#6e829b;font-size:16px;font-weight:400;padding-left:10px;}
.t01 .title span{background:url(../images/title.jpg) 0 0 no-repeat;}
.t02 .title span{background:url(../images/title.jpg) 0 -30px no-repeat;}
.t03 .title span{background:url(../images/title.jpg) 0 -60px no-repeat;}
.cz_cont{overflow:hidden;zoom:1;background-color:#fff;padding:16px 0;margin:0;}
.nav{margin:0;padding:0;}
.nav li{float:left;width:110px;height:130px;margin:0 25px;padding:0;}
.nav li a{display:block;width:110px;height:130px;margin:0 20px;border-radius:5px;position:relative;font-size:14px;line-height:40px;text-align:center;color:#fff;}
.nav li a em{display:block;position:absolute;width:110px;top:60px;left:0;font-size:40px;line-height:40px;color:#fff;text-align:center;font-family:Arial, Helvetica, sans-serif;}
.nav li.bb1 a{background:#85AFD6;}
.nav li.bb1 a:hover{background:#6896C1;}
.nav li.bb2 a{background:#D69AB5;}
.nav li.bb2 a:hover{background:#C2819E;}
.nav li.bb3 a{background:#FF9C72;}
.nav li.bb3 a:hover{background:#F28658;}
.nav li.bb4 a{background:#B8CA7C;}
.nav li.bb4 a:hover{background:#A2B464;}
.nav li.bb5 a{background:#7AC2AF;}
.nav li.bb5 a:hover{background:#60AD99;}
.nav li.bb6 a{background:#93D750;}
.nav li.bb6 a:hover{background:#82C53F;}
.nav li.bb7 a{background:#7BDCC4;}
.nav li.bb7 a:hover{background:#5EC6AC;}
.nav li.bb8 a{background:#70A9E8;}
.nav li.bb8 a:hover{background:#5A95D5;}
.nav li.bb9 a{background:#EF9F4C;}
.nav li.bb9 a:hover{background:#E39039;}
.nav li.bb10 a{background:#CFCC6D;}
.nav li.bb10 a:hover{background:#B6B35B;}

.bar1_canvas{text-align:left;margin:0 auto;}
</style>
</head>
	
<body>
<c:if test="${sessionScope.login.roleid==0}">
<div class="cz_main">
 <div class="cz_part t01">
   <div class="title"><span class="fl"></span><h2 class="fl">业务统计</h2></div>
   <div class="cz_cont">
     <ul class="nav">
      <li class="bb1"><a href="<%=path%>/manager/OrderManagerAction_showOrder.do?fromPartQuery=1">线上订单<em>${map['xianshangOrder'] }</em></a></li>
      <li class="bb2"><a href="<%=path%>/manager/OrderManagerAction_showOrder.do?fromPartQuery=2">渠道订单<em>${map['qudaoOrder'] }</em></a></li>
      <li class="bb3"><a href="<%=path %>/manager/TDCodeManagerAction_showTDCode.do">OBD总数<em>${map['obdCount'] }</em></a></li>
      <li class="bb4"><a href="<%=path %>/manager/TDCodeManagerAction_showTDCode.do?activeType=1">车主总数<em>${map['cztCount'] }</em></a></li>
      <li class="bb5"><a href="<%=path %>/manager/TDCodeManagerAction_showTDCodeHistory.do?operateType=D">注销用户数<em>${map['cancelCount'] }</em></a></li>
    </ul>
   </div>
 </div>
 
 <div class="cz_part t02">
   <div class="title"><span class="fl"></span><h2 class="fl">待处理事务提醒</h2></div>
   <div class="cz_cont">
    <ul class="nav">
     <li class="bb6"><a href="<%=path %>/manager/OrderManagerAction_showReviewOrder.do">待审核订单<em>${map['reviewOrderCount'] }</em></a></li>
     <li class="bb7"><a href="<%=path %>/manager/OrderManagerAction_showOrder.do?stateQuery=7&typeQuery=showOutOrder">待出库订单<em>${map['outOrderCount'] }</em></a></li>
     <li class="bb8"><a href="<%=path %>/manager/OrderManagerAction_showOrder.do?stateQuery=8&typeQuery=showSendOrder">待配送订单<em>${map['sendOrderCount'] }</em></a></li>
     <li class="bb9"><a href="<%=path %>/manager/BindManagerAction_showLimiteBind.do?type=1">到期预警<em>${map['expireCount'] }</em></a></li>
     <li class="bb10"><a href="<%=path %>/manager/BindManagerAction_showLimiteBind.do?type=2">到期用户<em>${map['exceedCount'] }</em></a></li>
    </ul>
   </div>
 </div>
 
 <div class="cz_part t03">
   <div class="title"><span class="fl"></span><h2 class="fl">统计报表</h2></div>
   <div class="cz_cont">
     <div class="bar1_canvas"><img src="${graphURL}" width=490 height=306 border=0 > </div>
  </div>
</div>

</div>
</c:if>
</body>
</html>

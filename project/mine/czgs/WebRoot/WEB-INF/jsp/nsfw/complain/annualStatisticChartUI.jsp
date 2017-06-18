<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Calendar cal=Calendar.getInstance();
	int curYear=cal.get(Calendar.YEAR);
	List yearList=new ArrayList();
	for(int i=curYear;i>curYear-5;i--){
		yearList.add(i);
	}
	request.setAttribute("curYear", curYear);
	request.setAttribute("yearList",yearList);
%>

<!DOCTYPE HTML>
<html>
<head>
    <%@include file="/common/header.jsp"%>
<title>My first chart using FusionCharts Suite XT</title>
<script type="text/javascript" src="${basePath }/js/fusioncharts/fusioncharts.js"></script>
<script type="text/javascript" src="${basePath }/js/fusioncharts/themes/fusioncharts.theme.fint.js"></script>
<script type="text/javascript">

	//进入页面就加载当前年度统计数
	$(document).ready(doAnnualStatistic());
  
  	function doAnnualStatistic(){
  		var year=$("#year").val();
  		if (year==null||year==undefined||year=="") {
			year="${curYear}";//域中取到当年值
		}
  		
  		$.ajax({
  			url:"${basePath}/nsfw/complain_getAnnualStatisticChartData.action",
  			data:{"year":year},
  			type:"get",
  			dataType:"json",
  			success:function(data){
  				if (data!=null&&data!=""&&data!=undefined) {
  					 var revenueChart = new FusionCharts({
  	  			        "type": "line",//column2d,pie3d,line
  	  			        "renderAt": "chartContainer",
  	  			        "width": "700",
  	  			        "height": "500",
  	  			        "dataFormat": "json",
  	  			        "dataSource":  {
  	  			          "chart": {
  	  			            "caption": "年度投诉",
  	  			         //   "subCaption": "Harry's SuperMart",
  	  			            "xAxisName": "月份",
  	  			            "yAxisName": "投诉数",
  	  			            "theme": "fint"
  	  			         },
  	  			         "data":data.chartData
  	  			      }

  	  			  });
  	  			revenueChart.render();
				}else{
					alert("统计图加载失败！");
				}
  			},
  			error:function(){
  				alert("统计图加载失败！");
  			}
  			
  		});
  	}
</script>
</head>
<body>
	<div style="width: 100%;text-align: center">
		<s:select list="#request.yearList" id="year" onchange="doAnnualStatistic()"></s:select>
  		<div id="chartContainer" ></div>
  	</div>	
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%

	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>菜单列表</title>
	
	    <link rel="stylesheet" href="../themes/skyblue/skyblueLeft.css"type="text/css" title="styles1" />
		<script language="javascript" type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript">
			/*function init(){
				var stretchers = document.getElementsByClassName('stretcher2'); //div that stretches
				var toggles = document.getElementsByClassName('display'); //h3s where I click on
				var myAccordion = new fx.Accordion(
					toggles, stretchers, {opacity: false, duration: 300}
				);

				var found = false;
				toggles.each(function(h3, i){
					var div = Element.find(h3, 'nextSibling'); //element.find is located in prototype.lite
					if (window.location.href.indexOf(h3.title) > 0) {
						myAccordion.showThisHideOpen(div);
						found = true;
					}
				});
				if (!found) myAccordion.showThisHideOpen(stretchers[0]);
			}*/
			function total(){
				$.post("<%=request.getContextPath()%>/manager/TotalManagerAction_totalMenuOrder.do", {},
				   function(data){
				   	var obj = eval('(' + data + ')');
				    if(obj.state=='S'){
				    	if($("#hbReviewOrderCount").length>0)
				    		$("#hbReviewOrderCount").html("("+obj.hbReviewOrderCount+")");
				    	if($("#reviewOrderCount").length>0)
				    		$("#reviewOrderCount").html("("+obj.reviewOrderCount+")");
				    	if($("#bindOrderCount").length>0)
				    		$("#bindOrderCount").html("("+obj.bindOrderCount+")");
				    	if($("#outOrderCount").length>0)
				    		$("#outOrderCount").html("("+obj.outOrderCount+")");
				    	if($("#sendOrderCount").length>0)
				    		$("#sendOrderCount").html("("+obj.sendOrderCount+")");
				    	if($("#endOrderCount").length>0)
				    		$("#endOrderCount").html("("+obj.endOrderCount+")");
				    }
				}, "json");
				
				$.post("<%=request.getContextPath()%>/manager/TotalManagerAction_totalMenuSettle.do", {},
				   function(data){
				   	var obj = eval('(' + data + ')');
				    if(obj.state=='S'){
				    	if($("#waitSettleCount").length>0)
				    		$("#waitSettleCount").html("("+obj.waitSettleCount+")");
				    	if($("#settleVerifyCount").length>0)
				    		$("#settleVerifyCount").html("("+obj.settleVerifyCount+")");
				    }
				}, "json");
			}
			function settleTotal(){
				
				
			}
		</script>
	</head>

	<body onload="total();" on>
	
    <div >
      <div id="meunimg-bg">
        <div id="meunbar" >
          <div id="dhtmlxTree">
				<%@ include file="menu.jsp"%>
          </div>
        </div>
      </div>
    </div>
	<script type="text/javascript">
			//Element.cleanWhitespace('content');
			//init();
		</script>	
		<script type="text/javascript" src="../themes/js/jquery.js"></script>
		<script type="text/javascript" src="../themes/js/styleswitch.js"></script>			
</body>
</html>
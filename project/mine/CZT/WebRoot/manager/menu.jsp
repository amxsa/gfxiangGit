<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<script type="text/javascript">
function expandIt(divId,url){
 	var objDiv = document.getElementById("extends"+divId);
 	
 	if (objDiv.style.display=="none"){ 
 		
 		objDiv.style.display="";
 	}else{
 		
 		objDiv.style.display="none";
 	}
 }
</script>
<div id="content"> 
	<!--  
	<c:if test="${sessionScope.login.roleid==0}">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>首页</a> </h3></div>
		<div class="stretcher2">
			<ul>
				<li><a href="<%=path %>/manager/TotalManagerAction_indexTotal.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>首页</a></li>
				
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.login.roleid==0}">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>TDCode管理</a> </h3></div>
		
		<div class="stretcher2">
			<ul>
				<li><a href="<%=path %>/manager/TDCodeManagerAction_showTDCodeGroup.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>TDCode组管理</a></li>
				<li><a href="<%=path %>/manager/TDCodeManagerAction_showTDCode.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>TDCode查询</a></li>
				<li><a href="<%=path %>/manager/TDCodeManagerAction_showTDCodeHistory.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>TDCode操作记录查询</a></li>
				<li><a href="<%=path %>/manager/registTDCode.jsp" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>TDCode激活</a></li>
				<li><a href="<%=path %>/manager/releasebindTDCode.jsp" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>TDCode注销绑定</a></li>
				<li><a href="<%=path %>/manager/addBatchBarCodeBindTDCode.jsp" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>SN与条码捆绑</a></li>
				<li><a href="<%=path %>/manager/TDCodeManagerAction_queryComparisonHybPre.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>行业版数据比对</a></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.login.roleid==2}">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>TDCode管理</a> </h3></div>
		
		<div class="stretcher2">
			<ul>
				<li><a href="<%=path %>/manager/TDCodeManagerAction_showTDCode.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>TDCode查询</a></li>
				<li><a href="<%=path %>/manager/TDCodeManagerAction_showTDCodeHistory.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>TDCode操作记录查询</a></li>
			</ul>
		</div>
	</c:if>	
	<c:if test="${sessionScope.login.roleid==0||sessionScope.login.roleid==1}">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>订单管理</a> </h3></div>
		<div class="stretcher2">
			<ul>
				<c:if test="${sessionScope.login.account!='gd_hb0001'}">
					<li><a href="<%=path %>/manager/addOrder.jsp" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>订单新增</a></li>
				</c:if>
				
				<c:if test="${sessionScope.login.account=='gd_hb0001'}">
					<li><a href="<%=path %>/manager/OrderManagerAction_showHBReviewOrder.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>待审核订单<font id="hbReviewOrderCount" style="color: red;">(0)</font></a></li>
				</c:if>
				<c:if test="${sessionScope.login.roleid==0}">
					<li><a href="<%=path %>/manager/OrderManagerAction_showReviewOrder.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>待审核订单<font id="reviewOrderCount" style="color: red;">(0)</font></a></li>
					<li><a href="<%=path %>/manager/OrderManagerAction_showOrder.do?stateQuery=7&typeQuery=showOutOrder " target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>待出库订单<font id="outOrderCount" style="color: red;">(0)</font></a></li>
					<li><a href="<%=path %>/manager/OrderManagerAction_showOrder.do?stateQuery=8&typeQuery=showSendOrder " target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>待配送订单<font id="sendOrderCount" style="color: red;">(0)</font></a></li>
					<li><a href="<%=path %>/manager/OrderManagerAction_showOrder.do?stateQuery=9&typeQuery=showEndOrder " target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>待归档订单<font id="endOrderCount" style="color: red;">(0)</font></a></li>
				</c:if>
				<li><a href="<%=path %>/manager/OrderManagerAction_showOrder.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>订单查询</a></li>
				
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.login.roleid==0}">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>出库管理</a> </h3></div>
		<div class="stretcher2">
			<ul>
				<li><a href="<%=path %>/manager/BindManagerAction_showBind.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>设备流量捆绑查询</a></li>
				<li><a href="<%=path %>/manager/addBatchBind.jsp" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>设备流量捆绑<font id="bindOrderCount" style="color: red;">(0)</font></a></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.login.roleid==0}">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>设备管理</a> </h3></div>
		<div class="stretcher2">
			<ul>
				<li><a href="<%=path %>/manager/DeviceManagerAction_showDevice.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>设备查询</a></li>
				<li><a href="<%=path %>/manager/addBatchDevice.jsp" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>设备导入</a></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.login.roleid==0}">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>流量卡管理</a> </h3></div>
		<div class="stretcher2">
			<ul>
				<li><a href="<%=path %>/manager/FluxCardManagerAction_showFluxCard.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>流量卡查询</a></li>
				<li><a href="<%=path %>/manager/addBatchFluxCard.jsp" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>流量卡导入</a></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${sessionScope.login.roleid==0||sessionScope.login.roleid=='1' }">
		<div id="meunacrbg"><h3 class="display" ><a href="#"><div class="img"></div>流量卡身份信息</a> </h3></div>
		<div class="stretcher2">
			<ul>
				<c:if test="${ sessionScope.login.account!='gz_0001' ||sessionScope.login.roleid==0}">
					<li><a href="<%=path %>/manager/FluxIdcardManagerAction_showYuxianFluxIdcard.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>客户经理审核</a></li>
				</c:if>
				<c:if test="${sessionScope.login.roleid==0||sessionScope.login.account=='gz_0001'}">
					<li><a href="<%=path %>/manager/FluxIdcardManagerAction_showHouxuFluxIdcard.do" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>实名专员审核</a></li>
				</c:if>
			</ul>
		</div>
	</c:if>
	-->
	
	<c:forEach var="aRecord" items="${sessionScope.menus}">
		<c:if test="${aRecord.levels==1}">
			<div id="meunacrbg"><h3 class="display" >
			<c:if test="${empty aRecord.url}">
				<a href="#" onclick="expandIt('${aRecord.id}','${aRecord.url}')" ><div class="img"></div>${aRecord.name }</a> </h3></div>
			</c:if>
			<c:if test="${not empty aRecord.url}">
				<a href="${pageContext.request.contextPath}/${aRecord.url}" onclick="expandIt('${aRecord.id}','${aRecord.url}')" target="mainFrame"><div class="img"></div>${aRecord.name }</a> </h3></div>
			</c:if>
			<span id="extends${aRecord.id}" style="display:none;">
				<c:forEach var="second" items="${sessionScope.menus}">
					<c:if test="${second.parentId==aRecord.id}">
						<div class="stretcher2"    >
							<ul>
								<li>
									<a href="<%=path %>/${second.url}" target="mainFrame"><img src="../themes/images/ico/user_add.gif" width="16" height="16" class="icoimg"/>${second.name}
											
										<c:if test="${not empty second.cntidName }">
											<font id="${second.cntidName }" style="color: red;">(0)</font>
										</c:if>
									</a>
								
								</li>
								
							</ul>
						</div>
					</c:if>
				</c:forEach>	
			</span>
		</c:if>
	</c:forEach>
	
</div>

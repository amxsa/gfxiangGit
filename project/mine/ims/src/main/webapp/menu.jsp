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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>

<c:forEach var="file" items="${requestScope.fileLists}">
	<c:if test="${file.directory}">
		<li id='${file.fullPathC}'>
			<span class="text">${file.fileName}</span>
			<ul class="ajax" id="${file.fullPathC}">
				<li id='${file.fullPath}'>
					{url:<%=path%>/workhelp/FileAction_load.do?path=${file.fullPathC}}
				</li>
			</ul>
		</li>
	</c:if>
</c:forEach>
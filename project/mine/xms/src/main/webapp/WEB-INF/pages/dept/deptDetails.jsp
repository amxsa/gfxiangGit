<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<div id="scrollContent" class="border_gray">
		<form id="form" action="${contextPath }/deptManage/save.do" method="post" enctype="multipart/form-data">
			<table class="tableStyle">
				<tr>
					<td width="15%">部门名称：</td>
					<td><input type="text" name="name" id="name" class="validate[required,length[1,32]]" value="${dept.name }" readonly="readonly"/></td>
				</tr>
				<tr>
					<td width="15%">logo：</td>
					<td><input type="file" name="aa" id="logo"  value="${dept.logo }" /></td>
				</tr>
				<tr>
					<td width="15%">部门等级：</td>
					<td><input type="text" name="levelId" id="levelId" class="validate[required,length[1,32]]" value="${dept.level.name }" readonly="readonly"/></td>
				</tr>
				<tr >
					<td width="15%">部门人员</td>
					<td>
							${names }
						<!-- <textarea rows="5" cols="20" readonly="readonly" >
						</textarea> -->
					</td>	
				</tr>
			</table>
		</form>
	</div>

</body>
</html>
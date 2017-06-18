<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<div id="scrollContent" class="border_gray">
			<table class="tableStyle">
				<tr>
					<td width="5%">员工名称：</td>
					<td><input type="text" readonly="readonly"  value="${emp.name }" /></td>
				</tr>
				<tr>
					<td width="5%">IdCard：</td>
					<td><input type="text" nreadonly="readonly"  value="${emp.idCard}" /></td>
				</tr>
				<tr>
					<td width="5%">手机：</td>
					<td><input type="text" readonly="readonly"   value="${emp.phone}" /></td>
				</tr>
				<tr>
					<td width="5%">生日：</td>
					<td><input type="text" readonly="readonly"  value="${emp.stringBirthday }" ></input></td>
				</tr>
				<tr>
					<td width="5%">年龄：</td>
					<td><input type="text" readonly="readonly"  value="${emp.age }"></input></td>
				</tr>
				<tr>
					<td width="5%">信息：</td>
					<td><textarea  readonly="readonly" >${emp.age }</textarea></td>
				</tr>
				<tr>
					<td width="5%">员工部门：</td>
					<td><input type="text" readonly="readonly" value="${emp.deptName}" ></input></td>
				</tr>
			</table>
	</div>

</body>
</html>
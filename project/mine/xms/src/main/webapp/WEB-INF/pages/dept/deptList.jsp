<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script type="text/javascript">
		function addDept(){
			$("form")[0].action="${contextPath}/base/goURL/pages/dept/deptAdd.do";
			$("form")[0].submit();
		}
		function deleteDept(id){
			$("form")[0].action="${contextPath}/deptManage/deleteDept.do?id="+id;
			$("form")[0].submit();
		}
		function updateDept(id){
			$("form")[0].action="${contextPath}/deptManage/updateDept.do?id="+id;
			$("form")[0].submit();
		}
		function viewDept(id){
			var diag = new top.Dialog();
			var titleName='部门详情';
			diag.Title = titleName;
			diag.URL = "${contextPath}/deptManage/viewDept.do?id="+id;
			diag.Width = 600;
			diag.Height = 340;
			diag.OKEvent = function(){
				diag.close();
			};
			diag.show();
		}
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：部门管理 > 部门管理 > 信息列表</span>
				</div>
			</div>
		</div>
	</div>

	<div  id="scrollContent" class="border_gray">
		<form id="form" action="${contextPath }/deptManage/list.do"  method="post">
		<div class="box2" roller="false">
			<table>
				<tr>
					<td>名称：</td>
					<td style="width: 286px;"><input type="text"   name="name" value="${dept.name}" style="width: 286px; "/></td>
					<td>等级：</td>
					<td style="width: 286px;"><input type="text"   name="levelName" value="${queryBean.levelName}" style="width: 286px; "/></td>
                    <td ><button type="submit" onclick=""><span class="icon_find">搜索</span></button></td>
				</tr>
			</table>
			<button type="button" onclick="addDept()"><span class="icon_add">新增</span></button>
		</div>
          <display:table name="deptList" class="tableStyle" requestURI="" id="d" 
		export="false" pagesize="10" cellpadding="0" cellspacing="0" style="text-align:center;">
        <display:column  sortable="false" headerClass="sortable"
	 	 		property="id"  title="ID" style="width: 10%;word-break:break-all;text-align:center;" >			
		</display:column>
        <display:column  sortable="false" headerClass="sortable"  property="name"
	 	 		 title="部门名称" style="width: 5%;word-break:break-all;text-align:center;" >
		</display:column>
        <display:column  sortable="false" headerClass="sortable" 
	 	 		 title="部门logo" style="width: 5%;word-break:break-all;text-align:center;" >	
	 	 		 <img src="${d.logo }"></img>
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="levelName"
	 	 		 title="部门等级" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="emps"
	 	 		 title="部门员工个数" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" 
	 	 		 title="操作" style="width: 5%;word-break:break-all;text-align:center;" >	
	 	 		 <a onclick="deleteDept('${d.id}')"   style="color: blue;">删除</a>
	 	 		 <a onclick="updateDept('${d.id}')"  style="color: blue;">修改</a>
	 	 		 <a onclick="viewDept('${d.id}')"  style="color: blue;">详情</a>
		</display:column>
	</display:table>
</form>
	</div>

</body>
</html>
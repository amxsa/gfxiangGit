<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：菜单管理 > 菜单管理 > 信息列表</span>
				</div>
			</div>
		</div>
	</div>

	<div  id="scrollContent" class="border_gray">
		<form id="form" action="${contextPath }/menuManage/list.do"  method="post">
		<div class="box2" roller="false">
			<table>
				<tr>
					<td>菜单中文名称：</td>
					<td style="width: 286px;"><input type="text"   name="cnname" value="${menu.cnname}" style="width: 286px; "/></td>
					<td>父级ID：</td>
					<td style="width: 286px;"><input type="text"   name="parentId" value="${queryBean.parentId}" style="width: 286px; "/></td>
                    <td ><button type="submit" onclick=""><span class="icon_find">搜索</span></button></td>
				</tr>
			</table>
		</div>
          <display:table name="menuList" class="tableStyle" requestURI="" id="m" 
		export="false" pagesize="10" cellpadding="0" cellspacing="0" style="text-align:center;">
        <display:column  sortable="false" headerClass="sortable"
	 	 		property="id"  title="ID" style="width: 5%;word-break:break-all;text-align:center;" >			
		</display:column>
        <display:column  sortable="false" headerClass="sortable" 
	 	 		 title="中文名" style="width: 5%;word-break:break-all;text-align:center;" >
	 	 		 ${m.cnname }	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="parentid"
	 	 		 title="父节点ID" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="mainurl"
	 	 		 title="菜单URL" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="parentmenu"
	 	 		 title="父菜标识" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="owner"
	 	 		 title="拥有者" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" 
	 	 		 title="操作" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
	</display:table>
</form>
	</div>

</body>
</html>
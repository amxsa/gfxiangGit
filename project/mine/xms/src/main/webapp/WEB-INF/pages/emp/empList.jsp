<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script type="text/javascript">
		function addEmp(){
			$("form")[0].action="/xms/base/goURL/pages/emp/empAdd.do";
			$("form")[0].submit();
		}
		function deleteEmp(id){
			$("form")[0].action="/xms/empManage/deleteEmp.do?id="+id;
			$("form")[0].submit();
		}
		function updateEmp(id){
			$("form")[0].action="/xms/empManage/updateEmp.do?id="+id;
			$("form")[0].submit();
		}
		function viewEmp(id){
			var diag = new top.Dialog();
			var titleName='员工详情';
			diag.Title = titleName;
			diag.URL = "/xms/empManage/viewEmp.do?id="+id;
			diag.Width = 600;
			diag.Height = 340;
			diag.OKEvent = function(){
				diag.close();
			};
			diag.show();
		}
		function getDept(){
			var diag = new top.Dialog();
			var titleName='选择部门';
			diag.Title = titleName;
			diag.URL = "/xms/base/goURL/pages/dept/selectDept.do";
			diag.Width = 500;
			diag.Height = 300;
			diag.OKEvent = function(){
				diag.innerFrame.contentWindow.getDepts();
				var names = diag.innerFrame.contentWindow.document.getElementById('deptNames').value;
				var ids = diag.innerFrame.contentWindow.document.getElementById('deptIds').value;
				$("#deptNames").val(names);
				$("#deptIds").val(ids);
				$.ajax({
					type: "post",
					url:"/xms/empManage/saveDeptInfo.do",
					data:{"deptIds":ids,"deptNames":names},
					async:false,
					dataType: "json",
					success:function(data){}
				})
				diag.close();
			};
			diag.show();
			diag.addButton("next"," 清空 ",function(){
				diag.innerFrame.contentWindow.clear();
			});
			diag.addButton("next"," 全选/全不选 ",function(){
				diag.innerFrame.contentWindow.all();
			});
			diag.addButton("next"," 反选 ",function(){
				diag.innerFrame.contentWindow.other();
			});
		}
	</script>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：员工管理 > 员工管理 > 信息列表</span>
				</div>
			</div>
		</div>
	</div>

	<div  id="scrollContent" class="border_gray">
		<form id="form" action="/xms/empManage/list.do?type=form"  method="post">
		<div class="box2" roller="false">
			<table>
				<tr>
					<td>名称：</td>
					<td style="width: 300px;"><input type="text"   name="name" value="${emp.name}" style="width: 300px; "/></td>
					<td>IdCard：</td>
					<td style="width: 300px;"><input type="text"   name="idCard" value="${queryBean.idCard}" style="width: 300px; "/></td>
				</tr>
				<tr>
					<td>生日区间：</td>
					<td style="width: 300px;">
						<input type="text" class="date"   name="startTime" value="${queryBean.startTime}" style="width: 139px; "/> -
						<input type="text" class="date"   name="endTime" value="${queryBean.endTime}" style="width: 139px; "/>
					</td>
					<td>所属部门：</td>
					<td style="width: 300px;">
						<input title="${deptNames }"  name="deptNames" style="width: 300px;" id="deptNames" onclick="getDept()" value="${queryBean.deptNames}" readonly="readonly"/>
						<input type="hidden"  name="deptIds" style="width: 300px;" id="deptIds" value="${queryBean.deptIds}"/>
					</td>
                    <td ><button type="submit" onclick=""><span class="icon_find">搜索</span></button></td>
				</tr>
			</table>
			<button type="button" onclick="addEmp()"><span class="icon_add">新增</span></button>
		</div>
          <display:table name="empList" class="tableStyle" requestURI="" id="e" 
		export="false" pagesize="10" cellpadding="0" cellspacing="0" style="text-align:center;">
        <display:column  sortable="false" headerClass="sortable"  property="name"
	 	 		 title="员工名称" style="width: 5%;word-break:break-all;text-align:center;" >
		</display:column>
        <display:column  sortable="false" headerClass="sortable"  property="idCard"
	 	 		 title="员工idCard" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="phone"
	 	 		 title="员工phone" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" 
	 	 		 title="员工birthday" style="width: 5%;word-break:break-all;text-align:center;" >	
	 	 		 <fmt:formatDate value="${e.birthday }" pattern="yyyy.MM.dd"/>
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="info"
	 	 		 title="员工info" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="age"
	 	 		 title="员工age" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" property="dept.name"
	 	 		 title="员工部门" style="width: 5%;word-break:break-all;text-align:center;" >	
		</display:column>
        <display:column  sortable="false" headerClass="sortable" 
	 	 		 title="操作" style="width: 5%;word-break:break-all;text-align:center;" >	
	 	 		 <a onclick="deleteEmp('${e.id}')"   style="color: blue;">删除</a>
	 	 		 <a onclick="updateEmp('${e.id}')"  style="color: blue;">修改</a>
	 	 		 <a onclick="viewEmp('${e.id}')"  style="color: blue;">详情</a>
		</display:column>
	</display:table>
</form>
	</div>

</body>
</html>
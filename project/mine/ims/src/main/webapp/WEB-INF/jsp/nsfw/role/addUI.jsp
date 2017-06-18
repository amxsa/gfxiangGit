<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>角色管理</title>
</head>
<script type="text/javascript">
	function doSubmit(){
		//alert($("#privileges"));
		$("#privileges").each(function(index,obj){
			alert(1);
			if ($(obj).prop("checked")) {
				alert($(this).val());
			}
		});
		
		var frm=document.forms[0];
		frm.action="${basePath }nsfw/role_save.action";
		frm.submit();
	}
</script>
<body class="rightBody">
<form id="form" name="form" action="" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>角色管理</strong>&nbsp;-&nbsp;新增角色</div></div>
    <div class="tableH2">新增角色</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">角色名称：</td>
            <td><s:textfield name="role.name" /></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色权限：</td> 
            <td>
            	<s:checkboxlist  name="privileges" list="#privilegeMap" ></s:checkboxlist>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">状态：</td>
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="role.state"/></td>
        </tr>
    </table>
    <s:hidden name="strName"></s:hidden>
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存" onclick="doSubmit()"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
    
   
</form>
</body>
</html>
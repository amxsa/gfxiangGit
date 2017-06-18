<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>用户管理</title>
    <script type="text/javascript" src="${basePath }/js/datepicker/WdatePicker.js"></script>
</head>
 <script type="text/javascript">
    	var res=true;
    	function doVerifyAccount(asyncFlag){
    		if (asyncFlag!=false) {
				asyncFlag=true;
			}
    		var account=$("#account").val();
    		if (account!=null) {
    			$.ajax({
        			url:"${basePath}/nsfw/user_verifyAccount.action",
        			data:{"user.account":account,"user.id":"${user.id}"},//----------${user.id} why
        			type:"post",
        			async:asyncFlag,
        			success:function(msg){
        				if (msg!="true") {//返回结果为true的话 就说明该账号不存在  否则存在
							alert("该账号已经被注册！");
        					$("#account").focus();
        					res=false;
						}else{
							res=true;
						}
        			}
        			
        		});
			}
    		
    	}
    	function doSubmit(){
    		if ($("#name").val()=="") {
				alert("名字不能为空");
				$("#name").focus();
				return false;
			}
    		if($("#password").val()==""){
    			alert("密码不能为空");
    			$("#password").focus();
    			return false;
    		}
    		//同步验证   
    		doVerifyAccount(false);
    		
    		if (res) {
				document.forms[0].action="${basePath }/nsfw/user_update.action";
				document.forms[0].submit();
			}
    		
    	}
    </script>
<body class="rightBody">
<form id="form" name="form" action="#" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>用户管理</strong>&nbsp;-&nbsp;编辑用户</div></div>
    <div class="tableH2">编辑用户</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <td><s:select name="user.dept" list="#{'部门A':'部门A','部门B':'部门B'}"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
            <!-- 头像回显  -->
                <s:if test="%{user.headImg!=null&&user.headImg!=''}">
                    <img src="${basePath }upload/<s:property value='user.headImg'/>" width="100" height="100"/>
                    <!-- 隐藏域保存图片  -->
                    <s:hidden name="user.headImg"></s:hidden>
                </s:if>
                <input type="file" name="headImg"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">用户名：</td>
            <td><s:textfield id="name" name="user.name"/> </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">帐号：</td>
            <td><s:textfield id="account" name="user.account"  onchange="doVerifyAccount()"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">密码：</td>
            <td><s:textfield id="password" name="user.password"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td><s:radio list="#{'true':'男','false':'女'}" name="user.gender"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
            <td>
            	<s:checkboxlist list="#rolelist" name="userRoleIds" listKey="roleId" listValue="name"></s:checkboxlist>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">电子邮箱：</td>
            <td><s:textfield name="user.email"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">手机号：</td>
            <td><s:textfield name="user.mobile"/></td>
        </tr>        
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td>
           		 <s:textfield id="birthday" name="user.birthday" readonly="true" onfocus="new WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" >
            		<s:param name="value"><s:date name="user.birthday" format="yyyy-MM-dd"/></s:param>
            	</s:textfield>
            </td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="user.state"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><s:textarea name="user.memo" cols="75" rows="3"/></td>
        </tr>
    </table>
    <s:hidden name="user.id"></s:hidden>
    <s:hidden name="strName"></s:hidden>
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存" onclick="doSubmit()" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>
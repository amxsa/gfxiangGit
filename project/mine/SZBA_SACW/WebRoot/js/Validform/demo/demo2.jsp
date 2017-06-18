<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="../../../common/common.jsp"%>
<link href="<%=request.getContextPath()%>/css/validform.css" type="text/css" rel="Stylesheet" />
<script language="javascript" type="text/javascript" src='<%=request.getContextPath()%>/js/jquery/jquery-1.9.1.min.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Validform/Validform_v5.3.2_ncr_min.js"></script>
<script type="text/javascript">
	function checkSubmit(){
		return true;
	}
</script>
</head>

<body>  
<div class="main">
    <div class="wraper">
    	<h2 class="green">提示效果六：【自定义提示效果】使用自定义弹出框</h2>
        
        <form class="registerform" action="ajax_post.php">
            <table width="100%" style="table-layout:fixed;">
                <tr>
                    <td class="need" style="width:10px;">*</td>
                    <td style="width:70px;">昵称：</td>
                    <td style="width:205px;"><input type="text" value="" name="name" class="inputxt" datatype="s6-18" nullmsg="请输入昵称！" errormsg="昵称至少6个字符,最多18个字符！" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td>密码：</td>
                    <td><input type="password" value="" name="userpassword" class="inputxt" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="need">*</td>
                    <td style="width:205px;">确认密码：</td>
                    <td><input type="password" value="" name="userpassword2" class="inputxt" datatype="*" recheck="userpassword" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="need"></td>
                    <td></td>
                    <td colspan="2" style="padding:10px 0 18px 0;">
                        <input type="submit" value="提 交" /> <input type="reset" value="重 置" /><span id="msgdemo2" style="margin-left:30px;"></span>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
$(function(){
	$.Tipmsg.r=null;
		
	$(".registerform").Validform({
		tiptype:function(msg){
			alert(msg);
		},
		tipSweep:true,
		ajaxPost:true
	});
})
</script>
</body>
</html>
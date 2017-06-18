<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("basePath", request.getContextPath()+"/") ;
%>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>我要投诉</title>
    
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script>
    	window.UEDITOR_HOME_URL = "${basePath }js/ueditor/";
    	var ue = UE.getEditor('editor');
    </script>
    
    <script type="text/javascript">
    	function doChangeDept(){
    		var dept=$("#toCompDept option:selected").val();
    		if (dept!="") {
				$.ajax({
					//url:"${basePath}sys/homeJson_getUserJson.action",
					url:"${basePath}sys/home_getUserJson1.action",
					data:{"dept":dept},
					type:"post",
					dataType:"json",
					success:function(data){
						if (data!=null&&data!=""&&data!=undefined) {
							if (data.msg=="success") {
								var toCompName=$("#toCompName");
								//先清空列表
								toCompName.empty();
								$.each(data.userList,function(index,obj){
									toCompName.append("<option value='"+obj.name+"'>"+obj.name+"</option>");
								});
							}else{
								alert("部门人员列表获取失败");
							}
						}else{
							alert("部门人员列表获取失败");
						}
					},
					error:function(){
						alert("部门人员列表获取失败");
					}
				});
			}else{
				$("#toCompName").empty();
			}
    		
    	}
    	
    	function doSubmit(){//避免在子窗口保存后无法回到主窗口  因此使用ajax保存后自动关闭子窗口
    		
    		$.ajax({
    			url:"${basePath}sys/home_addComplain.action",
    			data:$("form").serialize(),//序列化表单（获取所有的表单值）
    			type:"post",
    			async:false,
    			success:function(msg){
    				if (msg=="success") {
						alert("投诉成功！");
						window.opener.parent.location.reload(true);
						window.close();
					}else{
						alert("投诉失败！");
					}
    			},
    			error:function(){
    				alert("投诉失败！");
    			}
    		});
    	}
    	
    	
    </script>
</head>
<body>
<form id="form" name="form" action="" method="post" enctype="multipart/form-data">
    <div class="vp_d_1">
        <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
        <div class="vp_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>工作主页</strong>&nbsp;-&nbsp;我要投诉</div></div>
    <div class="tableH2">我要投诉</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="250px">投诉标题：</td>
            <td><s:textfield name="comp.compTitle"/></td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人部门：</td>
            <td>
            	<s:select name="comp.toCompDept" id="toCompDept" 
            	list="#{'':'请选择','部门A':'部门A','部门B':'部门B' }"  onchange="doChangeDept()"></s:select>
            </td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人姓名：</td>
            <td>
            	<select name="comp.toCompName" id="toCompName"></select>
            </td>
        </tr>
        <tr>
            <td class="tdBg">投诉内容：</td>
            <td><s:textarea id="editor" name="comp.compContent" cssStyle="width:90%;height:160px;" /></td>
        </tr>
        <tr>
            <td class="tdBg">是否匿名投诉：</td>
            <td><s:radio name="comp.isNm" list="#{'false':'非匿名投诉','true':'匿名投诉' }" value="true"/></td>
        </tr>
       
    </table>
	<s:hidden name="comp.state" value="0"></s:hidden>
	<s:hidden name="comp.compName" value="%{#session.SYS_USER.name}"></s:hidden>
	<s:hidden name="comp.compCompany" value="%{#session.SYS_USER.dept}"></s:hidden>
	<s:hidden name="comp.compMobile" value="%{#session.SYS_USER.mobile}"></s:hidden>
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存" onclick="doSubmit()" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:window.close()" class="btnB2" value="关闭" />
    </div>
    </div></div>
    <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
    </div>
</form>
</body>
</html>
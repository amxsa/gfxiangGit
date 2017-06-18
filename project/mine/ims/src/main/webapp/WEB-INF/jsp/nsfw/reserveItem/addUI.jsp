<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>预约服务管理</title>

    <script>
    function doChangeDept(){
		var dept=$("#selectDept option:selected").val();
		if (dept!="") {
			$.ajax({
				url:"${basePath}nsfw/reserveItem_getUserJson.action",
				data:{"dept":dept},
				type:"post",
				dataType:"json",
				success:function(data){
					if (data!=null&&data!=""&&data!=undefined) {
						if (data.msg=="success") {
							var dealName=$("#dealName");
							//先清空列表
							dealName.empty();
							$.each(data.userList,function(index,obj){
								dealName.append("<option value='"+obj.name+"'>"+obj.name+"</option>");
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
			$("#dealName").empty();
		}
		
	}
    
    
    function add(){
    	document.forms[0].action="${basePath}/nsfw/reserveItem_add.action";
    	document.forms[0].submit();
    }
    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath}nsfw/reserveItem_add.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>预约事项</strong>&nbsp;-&nbsp;新增事项</div></div>
    <div class="tableH2">新增事项</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">事项编号：</td>
            <td><s:select name="item.itemNo" list="#itemIdList"/></td>
            <td class="tdBg" width="200px">事项名称：</td>
            <td><s:textfield name="item.itemName"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">处理部门：</td>
            
            <td colspan="3"><s:select name="item.itemDealDept" id="selectDept" list="#{'':'请选择','部门A':'部门A','部门B':'部门B' }"  onchange="doChangeDept()"  /></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">处理人：</td>
            <td colspan="3"><select name="item.itemDealName" id="dealName" /></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td colspan="3"><s:radio name="item.itemState" list="#{'1':'有效','0':'无效'}"/></td>
        </tr> 
        
    </table>

    <div class="tc mt20">
        <input type="submit" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>
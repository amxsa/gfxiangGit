<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>纳税咨询管理</title>
    
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>纳税咨询管理</strong></div> </div>
                <div class="search_art">
                    <li>
                       	咨询标题：<s:textfield name="consult.compTitle" cssClass="s_text"  cssStyle="width:160px;"/>
                    </li>
                    <li>
                       	咨询时间：<s:textfield id="startTime" name="startTime" cssClass="s_text"  cssStyle="width:160px;"/>
                              - 
                             <s:textfield id="endTime" name="endTime" cssClass="s_text"  cssStyle="width:160px;"/>
                    </li>
                    <li>
                       	状态：<s:select name="consult.state" list=""/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>

                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td align="center">咨询标题</td>
                            <td width="120" align="center">咨询人单位</td>
                            <td width="120" align="center">咨询人</td>
                            <td width="140" align="center">咨询时间</td>
                            <td width="100" align="center">状态</td>
                            <td width="100" align="center">操作</td>
                        </tr>
                       
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"></td>
                                <td align="center"></td>
                                <td align="center"></td>
                                <td align="center"></td>
                                <td align="center"></td>
                                <td align="center">
                                    <a href="javascript:doDeal()">处理</a>
                                </td>
                            </tr>
                        
                    </table>
                </div>
            </div>

        <div class="c_pate" style="margin-top: 5px;">
		<table width="100%" class="pageDown" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td align="right">
                 	总共1条记录，当前第 1 页，共 1 页 &nbsp;&nbsp;
                            <a href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页</a>
					到&nbsp;<input type="text" style="width: 30px;" onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" min="1"
					max="" value="1" /> &nbsp;&nbsp;
			    </td>
			</tr>
		</table>	
        </div>

        </div>
    </div>
</form>

</body>
</html>
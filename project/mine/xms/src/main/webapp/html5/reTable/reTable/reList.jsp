 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int activePage = 3;

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="0">
<html lang="en">
<head>
<title>韶关后台</title>
<link rel="stylesheet" type="text/css" href="bank/reTable/css/global.css" />
<link rel="stylesheet" type="text/css" href="bank/reTable/css/cancel_record_table.css" />
<script src="js/jquery-1.11.1.min.js" type="bank/reTable/text/javascript"></script>
<script type="text/javascript" src="bank/reTable/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	
/* });
        $(function(){ */
            // 大菜单的tab栏切换
            $(".tab_top li").click(function(e) {
            $(this).addClass("now").siblings("li").removeClass("now")
            var index = $(this).index()
            //alert(index)
            $(".centent_bd").eq(index).addClass("current").siblings("div").removeClass("current")
            });
            // 点击三角排序
            $("thead th").click(function(event) {
            $(this).children('i').toggleClass('down');
            });
            });
        </script>

       
    </head>
    <body>
    <div class="matter_wap">
        <div class="right_centent">
            <div class="sort">
                <a href="javascript:;">客户服务</a>
                &nbsp;&nbsp;>&nbsp;&nbsp;
                <a href="javascript:;">贷款申请</a>
            </div>
            <div class="centent_r_btm">
                
                <div class="main_box">
                    <ul class="tab_top">
                        <li class="now">A<em>(10)</em></li>
                        <li style="background:#d24726">B<em>(10)</em></li>
                        <li style="background:#ff8f33">C<em>(4)</em></li> 
                    </ul>
                    <div class="centent_hd">
                   <div class="search_box">
                            <div class="ipt_box">
                                <input type="text" class="search" placeholder="仅支持申请人、贷款类型、额度的搜索"/>
                                <div class="search_btn"></div>
                            </div>  
                        </div>
                    <span>申请日期:</span>
                    
                    <input type="text" class="cancer_time Wdate" id="d4311" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/> 至
                    <input type="text "  id="d4312" class="cancer_time Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
                    <div class="find">查询</div>
                    
                    </div>
                    <div class="centent_bd current now">  
                        <!-- <table width="100%" border="1" cellspacing="0" cellpadding="0">
                        <thead>
                            <tr>
                                <th>申请人<i class="up down"></i></th>
                                <th>贷款类型<i class="up down"></i></th>
                                <th>额度<i class="up down"></i></th>
                                <th>申请日期<i class="up down"></i></th>
                                <th>办理网点<i class="up down"></i></th>
                                <th>状态<i class="up down"></i></th>
                                <th>操作<i class="up down"></i></th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                                <td>张三</td>
                                <td>企业贷款</td>
                                <td>10万</td>
                                <td>2016/12/23</td>
                                <td title="番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行">番禺区天安科技园支行科技园支行</td>
                                <td>已审核</td>
                                <td><i></i><em></em></td>
                            </tr>    
                          </tbody>
                         
                        </table> -->
                    </div>
                    <div class="centent_bd ">  
                        <table width="100%" border="1" cellspacing="0" cellpadding="0">
                        <thead>
                            <tr>
                                <th>申请人<i class="up down"></i></th>
                                <th>贷款类型<i class="up down"></i></th>
                                <th>额度<i class="up down"></i></th>
                                <th>申请日期<i class="up down"></i></th>
                                <th>办理网点<i class="up down"></i></th>
                                <th>状态<i class="up down"></i></th>
                                <th>操作<i class="up down"></i></th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                                <td>张dada</td>
                                <td>个人贷款</td>
                                <td>20万</td>
                                <td>2016/12/23</td>
                                <td title="番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行">番禺区天安科技园支行科技园支行</td>
                                <td>已审核</td>
                                <td><i></i><em></em></td>
                            </tr>    
                          </tbody>
                         
                        </table>
                    </div>
                    <div class="centent_bd ">  
                        <table width="100%" border="1" cellspacing="0" cellpadding="0">
                        <thead>
                            <tr>
                                <th>申请人<i class="up down"></i></th>
                                <th>贷款类型<i class="up down"></i></th>
                                <th>额度<i class="up down"></i></th>
                                <th>申请日期<i class="up down"></i></th>
                                <th>办理网点<i class="up down"></i></th>
                                <th>状态<i class="up down"></i></th>
                                <th>操作<i class="up down"></i></th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                                <td>啊dada</td>
                                <td>个人贷款</td>
                                <td>20万</td>
                                <td>2016/12/23</td>
                                <td title="番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行番禺区天安科技园支行">番禺区天安科技园支行科技园支行</td>
                                <td>已审核</td>
                                <td><i></i><em></em></td>
                            </tr>    
                          </tbody>
                         
                        </table>
                    </div> 
                </div>

                
            </div>
        </div> 

 
    </div>

    </body>
</html>

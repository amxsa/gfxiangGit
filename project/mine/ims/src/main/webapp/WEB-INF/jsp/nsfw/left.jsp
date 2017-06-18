<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/pages/common.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <link href="${ctx}/css/nsfw/css.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/nsfw/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
    	function isCondition(ele){
    		if (ele!=null&&ele!=""&&ele!=undefined) {
				return true;
			}
    		return false;
    	}
        //隐藏菜单
        $(document).ready(function () {
        	
            $("dt a").click(function () {
                var cur = $(this);
            	if (!isCondition($(this).attr("tip"))) {
	                cur.parent().next().toggle(500);
				}
                var cur_dl = cur.parent().parent();
                doRemoveCurClass();
                $(cur_dl).addClass("curr");
            });

            $("dd a").each(function () {
                $(this).bind("click", function () {
                    doRemoveCurClass();
                    $(this).addClass("cur");
                });
            });
           
        });

        function doRemoveCurClass() {
            $("dl").each(function () {
                $(this).removeClass("curr");
                $("dd a").each(function () {
                    $(this).removeClass("cur");
                });
            });
        }

        function closeOtherDD(id) {
            $("dd").each(function () {
                if ($(this).attr("id") != id + "dd") {
                    $(this).hide(700);
                }
            });
        }
    </script>
    <!--[if IE 6]>
    <script type="text/javascript" src="${basePath}js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('b, s, img, span, .prev, .next, a, input,');
    </script>
    <![endif]-->
    <style>
        * {
            scrollbar-face-color: #dee3e7; /*立体滚动条的颜色（包括箭头部分的背景色）*/
            scrollbar-highlight-color: #ffffff; /*滚动条的高亮颜色（左阴影？）*/
            scrollbar-shadow-color: #dee3e7; /*立体滚动条阴影的颜色*/
            scrollbar-3dlight-color: #eceaea; /*立体滚动条亮边的颜色*/
            scrollbar-arrow-color: #006699; /*三角箭头的颜色*/
            scrollbar-track-color: #efefef; /*立体滚动条背景颜色*/
            scrollbar-darkshadow-color: #eceaea; /*滚动条的基色*/
        }
    </style>
</head>

<body>
<div class="xzfw" style="width: 210px;">
    <div class="xzfw_nav" style="width:214px;min-height:500px;">
        <div class="nBox" style="width:214px;">
            <div class="x_top" style="width:214px;"></div>
            <div class="sm">
                <c:if test="${sessionScope.menus!=null }">
                	<c:forEach items="${sessionScope.menus }" var="m" >
	                		<c:if test="${m.levels==1 }">
		                		<dl class="">
		                			<dt><a class="fwyy" style="cursor: pointer;" tip="${m.url }" <c:if test="${not empty m.url }">href='${basePath }/${m.url}' target='${m.target }'</c:if> ><p style="width: 30px;display: inline-block;"><i class="icon iconfont" style="font-size: 22px;margin-right: 10px;">${m.icon }</i></p>${m.name }<s class="down"></s> </a></dt>
									<dd  style="display:none;">
			                			<c:forEach items="${sessionScope.menus }" var="me">
											<c:if test="${m.id==me.parentId }">
						                        	<a class="" href="${basePath }/${me.url}" target="${me.target }"><p style="width: 20px;display: inline-block;"><i class="icon iconfont" style="font-size: 20px;margin-left:0px;">${me.icon }</i></p><b></b><span style="display: inline-block;margin-left: 5px">${me.name }</span></a>
											</c:if>
										</c:forEach>
		                    		</dd>	
		                		</dl>
	                		</c:if>
               		</c:forEach>	
                </c:if>
                <%-- <dl class="">
                    <dt><a class="fwyy" style="cursor: pointer;"><b></b>服务预约管理<s class="down"></s> </a></dt>
                    <dd id="fwyygl" style="display:none;">
                        <a class="" href="#" target="mainFrame"><b></b>预约服务</a>
                        <a class="" href="${ctx }/nsfw/reserveItem_listUI.action" target="mainFrame"><b></b>预约事项</a>
                    </dd>
                </dl> --%>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.threegms.sdplatform.util.site.SiteUtil"%>
<%@page import="com.threegms.sdplatform.util.StringUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String weid = StringUtil.getReqId(request,"weid");
//String fansId = StringUtil.getReqId(request,"fansId");
String openid = StringUtil.getReqId(request,"openid");
String basePath = SiteUtil.getSiteUrl();

%>



<!dOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" /><!-- 删除苹果默认的工具栏和菜单栏 -->
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /><!-- 设置苹果工具栏颜色 -->
    <meta name="format-detection" content="telephone=no, email=no" /><!-- 忽略页面中的数字识别为电话，忽略email识别 -->
    <!-- 启用360浏览器的极速模式(webkit) -->
    <meta name="renderer" content="webkit">
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
    <meta name="HandheldFriendly" content="true">
    <!-- 微软的老式浏览器 -->
    <meta name="MobileOptimized" content="320">
    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="portrait">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="portrait">
    <!-- UC强制全屏 -->
    <meta name="full-screen" content="yes">
    <!-- QQ强制全屏 -->
    <meta name="x5-fullscreen" content="true">
    <!-- UC应用模式 -->
    <meta name="browsermode" content="application">
    <!-- QQ应用模式 -->
    <meta name="x5-page-mode" content="app">
    <!-- windows phone 点击无高光 -->
    <meta name="msapplication-tap-highlight" content="no">
    <!-- 适应移动端end --> 
    <title>银行信贷业务电子征信协议</title>
    <link rel="stylesheet" type="text/css" href="bank/sg/css/bath.css" />
    <link rel="stylesheet" type="text/css" href="bank/sg/css/Msg.css" />
 </head>  
    <body>
   	<div>
        <p class="top_p">韶关市区农村信用合作联社信贷业务电子征信协议</p>
        <p style="text-indent: 2em;">在线申请韶关市区农村信用合作联社信贷业务，需向中国人民银行征信中心金融信用信息基础数据库、其他依法设立的征信机构、其他有权机关建立的信息数据库查询、使用申请人的个人信息。根据《征信业管理条例》第18条规定“向征信机构查询个人信息的，应当取得信息主体本人的书面同意并约定用途”，以及《中华人民共和国合同法》第11条规定“书面形式不限于传统纸质形式，凡是能够有形地表现所载内容的电子形式也具有和纸质形式同等法律效力”，申请人同意如下事项：</p>
        <p> 一、申请人通过互联网（Internet）点击确认或以其他方式选择接受本协议，即表示申请人同意接受本协议的全部约定内容，确认承担由此产生的一切责任。</p>
        <p> 二、申请人同意并以电子形式授权韶关农信银行，在申请人在线申请韶关农信银行零售信贷业务时，可以向包括但不限于中国人民银行征信中心金融信用信息基础数据库、其他依法设立的征信机构、其他有权机关建立的信息数据库以“零售信贷业务审查和贷款审批”原因查询包括但不限于本业务相关信息、借款人的个人信息和财产信息，以及其他有权机关要求或依法律规定需要提供的信息等，并有权对查询到的申请人信息进行打印、保存和使用。</p>
        <p> 三、申请人在此同意并授权韶关农信银行向包括但不限于中国人民银行征信中心金融信用信息基础数据库或其他依法设立的征信机构、产权登记机构等提供申请人的信息（含逾期记录等不良信息、本次业务申请审批结论等相关贷款信息，下同）。同意及授权韶关农信银行出于为申请人提供综合化服务的目的视情况向韶关农信银行股份有限公司集团成员（包括韶关农信银行股份有限公司境内全资子公司、控股子公司等）、韶关农信银行的服务机构、代理人、外包作业机构、联名卡合作方以及其他韶关农信银行认为必要的业务合作机构提供申请人的信息。韶关农信银行承诺将要求接收韶关农信银行披露资料的机构对申请人的信息承担保密义务。</p>
        <p> 四、由于通过网络、手机提供和传输信息存在特定的泄密风险，申请人已经充分考虑到该风险，愿意承担该风险并使用韶关农信银行的服务，如果因网络手机传输通讯故障、计算机病毒感染、黑客攻击窃取资料等韶关农信银行无法控制的不可抗因素导致个人隐私泄密等后果，由申请人自行承担。</p>
        <p> 五、本协议自申请人点击勾选“本人已阅读并同意《韶关农信银行零售信贷业务电子征信授权协议》”起生效，在申请人接受韶关农信银行提供金融产品、金融服务以及与韶关农信银行开展其他业务期间持续有效，非经韶关农信银行书面同意本协议不可撤销。</p>
        <p> 六、申请人已经注意到有关免除或限制韶关农信银行责任、韶关农信银行单方拥有某些权利、增加申请人责任或限制申请人权利的条款，并对其有全面、准确的理解。申请人和韶关农信银行对本协议条款的理解完全一致且完全接受。</p>
        <p> 七、韶关农信银行与申请人在履行本协议中发生的争议，由双方协商解决，协商不成提起诉讼的，由韶关农信银行总行所在地人民法院管辖。</p>
        <p> 八、本协议未尽事宜，依据国家相关法律规定及金融惯例办理。</p>
        <p style="text-indent: 2em;padding-bottom:10px;">申请人点击勾选"本人已阅读并同意《韶关农信银行零售信贷业务电子征信授权协议》"，明确表示申请人清楚知晓并理解、确认上述内容及法律责任，并同意遵守上述内容及约定。</p>
    </div>
    
    </body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cn.cellcom.szba.domain.ClientAccount"%>
<%
	ClientAccount ca = null;
	if (session.getAttribute("ClientAccount")!= null) {
		ca = (ClientAccount) session.getAttribute("ClientAccount");
	}
	String sessionID="";
	String timestamp="";
	String authstring="";
	if(ca!=null){
		sessionID=ca.getSessionID();
		timestamp=ca.getTimestamp();
		authstring=ca.getAuthstring();
	}
%>
<script type="text/javascript">
var clientSessionID="<%=sessionID%>";
var clientTimestamp="<%=timestamp%>";
var clientAuthstring="<%=authstring%>";

/**
 * localUrl:本地请求url
 * clientUrl：请求客户端url
 * params：参数
 * callback：回调函数
 */
function clientAjaxPost(localUrl,clientUrl,params,callback,scope){
	if(params==null){
		params={};
	}
	params.sessionID=clientSessionID;
	params.timestamp=clientTimestamp;
	params.authstring=clientAuthstring;
	var rp={
		'url':clientUrl,
		'params':JSON.stringify(params)
	}
	$.ajax({ 
		type:"post", 
		dataType:"json",
		url:localUrl, 
		data: rp,
		success:function(result,textStatus){
			callback.call(scope, result);
		}
	});
}
/**
 * 同步
 * localUrl:本地请求url
 * clientUrl：请求客户端url
 * params：参数
 * callback：回调函数
 */
function clientAjaxPostSync(localUrl,clientUrl,params,callback,scope){
	if(params==null){
		params={};
	}
	params.sessionID=clientSessionID;
	params.timestamp=clientTimestamp;
	params.authstring=clientAuthstring;
	var rp={
		'url':clientUrl,
		'params':JSON.stringify(params)
	}
	$.ajax({ 
		async:false,
		type:"post", 
		dataType:"json",
		url:localUrl, 
		data: rp,
		success:function(result,textStatus){
			callback.call(scope, result);
		}
	});
}

/**
 * localUrl:本地请求url
 * params：参数
 * callback：回调函数
 */
function AjaxPost(localUrl,params,callback,scope){
	var rp={
		'params':JSON.stringify(params)
	}
	$.ajax({ 
		type:"post", 
		dataType:"json",
		url:localUrl, 
		data: rp,
		success:function(result){
			callback.call(scope, result);
		}
	});
}
</script>

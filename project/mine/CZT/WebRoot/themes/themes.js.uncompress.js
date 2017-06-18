/**
 * @author 聂勇
 * @since 2008年04月09日
 */
<!--
function createCssStyle(themeType) {
	var app_name="GZ_114DG_WEB"; 
	// gddg114test
	// gddg114
	
	var themeObjs = new Array();
	themeObjs[0] = new Array("gz114_dg_web", ".css");
	themeObjs[1] = new Array("gz114_dg_web_login", "Login.css");
	themeObjs[2] = new Array("gz114_dg_web_left", "Left.css");
	themeObjs[3] = new Array("gz114_dg_web_top", "Top.css");
	themeObjs[4] = new Array("gz114_dg_web_main", "Main.css");
	
	for(var index=0; index<themeObjs.length; index++) {
		createSingleCssStyle(app_name, themeType, themeObjs[index][0] ,  themeObjs[index][1] );
	}
}

/**
 * 生成样式文件对象.
 * 
 * @param appName 应用名称
 * @param themeType 主题
 * @param linkId Css样式对象的ID值
 * @param styleFileSuffix CSS文件的后缀名称
 */
function createSingleCssStyle(appName, themeType, linkId, styleFileSuffix) {
	var styleObj = document.getElementById(linkId);
    if (null != styleObj && "" != styleObj) {
        styleObj.setAttribute("rel", "stylesheet");
        styleObj.setAttribute("type", "text/css");
        styleObj.setAttribute("href", "/" + appName + "/themes/" + themeType  + "/" + themeType + styleFileSuffix);
    }
}

/**
 * 主题管理.
 */
function Theme() {
    var theme = "skyblue";
    var cookieName = "gz114dg.web.theme";
    var expiresTime = 30 * 24 * 60 * 60 * 1000;
    
    this.getTheme = function()  {
        var preTheme = this.getCookieValue();
         return (null == preTheme || "" == preTheme) ? theme  : preTheme;
    }
    
    this.setTheme = function(themeType) {
        theme = themeType;
        
        this.setCooikeValue(theme);
    }
    
    this.setCooikeValue = function(value) {
        var expriesDate = new Date();
        expriesDate.setTime( expriesDate.getTime() + expiresTime);
        document.cookie = cookieName + "=" + value + ";expires=" +expriesDate.toGMTString() ;
    }
    
    this.getCookieValue = function() {
        var cookieValue = document.cookie;
        if (null != cookieValue) {
            var cookies = cookieValue.split(";");
            var searchCookie = cookieName + "=";
            
            var singleCookie = null;
            var singleCookiePoint = 0;
            for(var index=0; index<cookies.length; index++) {
                singleCookie = cookies[index];
                singleCookiePoint =  singleCookie.indexOf(searchCookie) ;
                if (null != singleCookie && -1 != singleCookiePoint) {
                    return singleCookie.substring(singleCookiePoint + searchCookie.length, singleCookie.length);
                } 
            }
        } 
        
        return "";
    }
}

/**
 * 初始化界面主题.
 */
function initCssStyle() {
//    var theme = new Theme();
//    var themeStyle = theme.getTheme();
    
    changeTheme("skyblue");
}

/**
 * 更换主题.
 * 
 * @param themeStyle 主题
 */
function changeTheme(themeStyle) {
    createCssStyle(themeStyle);
    
    var theme = new Theme();
    var themeStyle = theme.setTheme(themeStyle);
}

function changeMultiTheme(themeStyle) {
	var theme = new Theme();
    theme.setTheme(themeStyle);
   
    var parentObj = window.parent;
    if (null != parentObj) {
        var frameObjs = parentObj.document.getElementsByTagName("FRAME");
        if (null != frameObjs) {
            if (frameObjs.length >= 1) {
                for(var index=0; index<frameObjs.length; index++) {
                    try {
                         frameObjs[index].createCssStyle(themeStyle);
                    } catch (e) {
                        // TODO: handle exception
                    }
                }
            } else {
                createCssStyle(themeStyle);
            }
        } 
    }  else {
        createCssStyle(themeStyle);
    }
    
    var theme = new Theme();
    var themeStyle = theme.setTheme(themeStyle);
}

initCssStyle();
//-->
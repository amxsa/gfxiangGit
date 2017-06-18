/**
 * @author ����
 * @since 2008��04��09��
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
 * ������ʽ�ļ�����.
 * 
 * @param appName Ӧ������
 * @param themeType ����
 * @param linkId Css��ʽ�����IDֵ
 * @param styleFileSuffix CSS�ļ��ĺ�׺����
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
 * �������.
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
 * ��ʼ����������.
 */
function initCssStyle() {
//    var theme = new Theme();
//    var themeStyle = theme.getTheme();
    
    changeTheme("skyblue");
}

/**
 * ��������.
 * 
 * @param themeStyle ����
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
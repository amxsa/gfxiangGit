package com.jlit.xms.api.message;

import org.apache.log4j.Logger;

import com.jlit.memcached.session.SessionService;

/**
 * servlet处理公共方法
 * @author viliam
 *
 */
public class ServletMessageCommon {
	static Logger log=Logger.getLogger(ServletMessageCommon.class);
	/**
	 * 根据令牌取账号
	 * @param token
	 * @return
	 */
	public static String getAccountToken(String token){
		SessionService ses = SessionService.getInstance();
		String account = (String) ses.get(token, true);
		log.info("token："+token+"<|>acount:"+account);
		//暂返回success
		//account = "success";
		//ses.remove(token);
		return account;
	}

}

package com.gf.ims.web.acion;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gf.ims.common.env.Env;
import com.gf.ims.service.RedisCacheService;
import com.gf.imsCommon.ims.module.User;
import com.gf.imsCommon.memcache.SessionService;
import com.gf.imsCommon.memcache.TestMemcached;

@Controller
@RequestMapping("/test")
public class TestAction{
	Logger log =Logger.getLogger(TestAction.class);
	@Resource
	private RedisCacheService redisCacheService;
	
	@RequestMapping(value="/test")
	@ResponseBody
	public Object Test(){
		try {
			String sql="select * from t_emp where 1=1 ";
			SessionService.getInstance().save("token", "12345555");
			Object token = SessionService.getInstance().get("token");
			TestMemcached tm = TestMemcached.getInstance();
			tm.add("test", "111");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("token", token);
			map.put("test", tm.get("test"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/layer")
	public String layer(){
		
		return "/pages/test/layer/layer";
	}
	
	@RequestMapping(value="/redisTest")
	public void redisTest(){
		redisCacheService.putCache("cache_user1", new User(Env.getUUID(), "userType1", "userAccount1", "mobile1"));
		redisCacheService.putCacheWithExpireTime("cache_user2", new User(Env.getUUID(), "userType2", "userAccount2", "mobile2"), 1000*10);
		User user11 = redisCacheService.getCache("cache_user1", User.class);
		User user22 = redisCacheService.getCache("cache_user2", User.class);
		System.out.println(user11.getUserType());
		System.out.println(user22.getUserType());
		try {
			Thread.sleep(1100*10);
			User user1 = redisCacheService.getCache("cache_user1", User.class);
			User user2 = redisCacheService.getCache("cache_user2", User.class);
			System.out.println(user1.getUserType());
			System.out.println(user2.getUserType());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

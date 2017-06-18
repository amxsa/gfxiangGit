/**
 * 
 */
package com.gf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gf.mapper.UserMapper;
import com.gf.model.User;
import com.gf.service.UserService;

/**
 * @author gfxiang
 * @time 2017年5月4日 下午2:12:23
 *	@UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	/* 
	 * @see com.gf.service.UserService#getUserInfo()
	 */
	public User getUserInfo() {
		// TODO Auto-generated method stub
		return userMapper.findUserInfo();
	}

}

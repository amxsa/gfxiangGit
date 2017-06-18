package com.gf.ims.service;

import java.util.List;

import com.gf.ims.common.page.PageResult;
import com.gf.ims.web.queryBean.UserQueryBean;
import com.gf.imsCommon.ims.module.Questions;
import com.gf.imsCommon.ims.module.User;
import com.gf.imsCommon.ims.module.UserCheck;
import com.gf.imsCommon.ims.module.UserDetail;
import com.gf.imsCommon.ims.module.UserRole;

public interface UserService {

	List<Questions> getQuestions();

	User saveUser(User user, UserDetail userDetail, List<UserCheck> userCheckList);

	PageResult<User> findUserList(UserQueryBean userQueryBean);

	User addUser(User user, String[] roleIds);

	User getById(String userId);

	User getByAccount(String userAccount);

	void updateUser(User u, String[] roleIds);

	List<UserRole> getUserRoleListByUserId(String id);

	void update(User user);

	User getByMobile(String mobile);

	UserDetail getUserDetailById(String userId);

	List<UserCheck> getUserCheckList(String userId);
	
}

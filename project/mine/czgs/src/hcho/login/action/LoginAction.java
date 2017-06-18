package hcho.login.action;

import hcho.core.constant.Constant;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.entity.UserRole;
import hcho.nsfw.user.service.UserService;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{
	
	private User user;
	private String loginResult;
	
	@Resource
	private UserService userService;

	public String toLoginUI(){
		
		return "loginUI";
	}
	
	public String login(){
		
		if (user!=null) {
			if (StringUtils.isNotBlank(user.getAccount())&&StringUtils.isNotBlank(user.getPassword())) {
				//根据账号和密码去数据库查询
				List<User> list=userService.findUsersByAccountAndPass(user.getAccount(),user.getPassword());
				if (list!=null&&list.size()>0) {
					//登陆成功  获取用户信息
					user=list.get(0);
					//设置用户角色信息********************************这里设置了在过滤器才能使用
					user.setUserRoles(userService.findUserRolesByUserId(user.getId()));
					//设置用户信息到session域中
					ActionContext.getContext().getSession().put(Constant.USER, user);
					//设置日志记录
					Log log = LogFactory.getLog(getClass());
					log.info("用户:"+user.getName()+"登陆了系统");
					
					return "home";
				}else{
					loginResult="账号或密码错误！";
				}
			}else{
				loginResult="账号或密码不存在！";
			}
		}else{
			loginResult="账号或密码不存在！";
		}
		
		return toLoginUI();
	}
	//注销登录
	public String logout(){
		
		ActionContext.getContext().getSession().remove(Constant.USER);
		
		return toLoginUI();
	}
	public String toNoPermissionUI(){
		
		return "noPermissionUI";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
	
}

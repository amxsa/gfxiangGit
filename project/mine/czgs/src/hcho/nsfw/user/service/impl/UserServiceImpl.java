package hcho.nsfw.user.service.impl;

import hcho.core.service.impl.BaseServiceImpl;
import hcho.nsfw.role.entity.Role;
import hcho.nsfw.user.dao.UserDao;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.entity.UserRole;
import hcho.nsfw.user.entity.UserRoleId;
import hcho.nsfw.user.service.UserService;
import hcho.nsfw.user.util.ExcelUtil;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	
	private UserDao userDao;
	
	@Resource
	public void setUserDao(UserDao userDao){
		super.setBaseDao(userDao);
		this.userDao=userDao;
	}
	

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		//先删除用户对应的角色
		userDao.findUserRolesByUserId(id);
		userDao.delete(id);
	}


	@Override
	public void export(List<User> userList, ServletOutputStream outputStream) {
		// TODO Auto-generated method stub
		ExcelUtil.exprot(userList,outputStream);
	}

	@Override
	public void importExcel(File userExcel) {
		// TODO Auto-generated method stub
		
		try {
			//读取工作簿
			Workbook workbook = WorkbookFactory.create(userExcel);
			//得到工作表
			Sheet sheet = workbook.getSheetAt(0);
			
			//若工作表的行数>2 就读取数据
			if (sheet.getPhysicalNumberOfRows()>2) {
					User user=null;
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
					user=new User();
					Row row = sheet.getRow(i);
					user.setName(row.getCell(0).getStringCellValue());
					user.setAccount(row.getCell(1).getStringCellValue());
					user.setDept(row.getCell(2).getStringCellValue());
					user.setGender(row.getCell(3).getStringCellValue().equals("男"));//返回的是布尔值
					
					//手机号 因表格中的手机号会被默认当成数值以科学计数法表示  所以要用读取大数据的方式
					String mobile="";
					try {
						//若碰到异常 就使用读取大数据的方式
						mobile = row.getCell(4).getStringCellValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						double d = row.getCell(4).getNumericCellValue();
						mobile=BigDecimal.valueOf(d).toString();
					}
					user.setMobile(mobile);
					
					user.setEmail(row.getCell(5).getStringCellValue());
					user.setBirthday(row.getCell(6).getDateCellValue());
					
					user.setPassword("123456");
					user.setState(User.USER_STATE_VALID);
					save(user);
				}
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<User> findByAccountOrId(String account, String id) {
		// TODO Auto-generated method stub
		return userDao.findByAccountOrId(account,id);
	}

	@Override
	public void saveUserAndRole(User user, String... userRoleIds) {
		// TODO Auto-generated method stub
		save(user);
		if (userRoleIds!=null) {
			for (String roleId : userRoleIds) {
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
			}
		}
	}

	
	/**
	 * 根据用户Id查询该用户的角色
	 */
	@Override
	public List<UserRole> findUserRolesByUserId(Serializable id) {
		// TODO Auto-generated method stub
		return userDao.findUserRolesByUserId(id);
	}

	/**
	 * 更新用户和角色  需先删除用户角色的关系  再保存用户和角色
	 */
	@Override
	public void updateUserAndRole(User user, String... userRoleIds) {
		// TODO Auto-generated method stub
		userDao.deleteUserRoleByUserId(user.getId());
		userDao.update(user);
		for (String roleId : userRoleIds) {
			userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));
		}
	}

	
	@Override
	public Set<String> findPrivilegeById(Serializable id) {
		// TODO Auto-generated method stub
		Set<String> pvSet=new HashSet<String>();
		
		List<UserRole> userRoles = findUserRolesByUserId(id);
		for (UserRole userRole : userRoles) {
			Set<String> privileges = userDao.findPrivilegeByUserRole(userRole);
			pvSet.addAll(privileges);
			return pvSet;
		}
		
		return null;
	}

	@Override
	public List<User> findUsersByAccountAndPass(String account, String password) {
		// TODO Auto-generated method stub
		return userDao.findUsersByAccountAndPass(account, password);
	}

	
}

package com.gf.ims.web.acion;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gf.ims.common.enums.UserType;
import com.gf.ims.common.env.CommonResponse;
import com.gf.ims.common.env.Env;
import com.gf.ims.common.page.PageResult;
import com.gf.ims.common.util.HttpClientUtil;
import com.gf.ims.file.utils.FileUploadUtil;
import com.gf.ims.file.utils.FileUtil;
import com.gf.ims.file.utils.PictureUtil;
import com.gf.ims.service.CommonService;
import com.gf.ims.service.RoleService;
import com.gf.ims.service.UserService;
import com.gf.ims.web.queryBean.UserQueryBean;
import com.gf.imsCommon.ims.module.Area;
import com.gf.imsCommon.ims.module.City;
import com.gf.imsCommon.ims.module.Province;
import com.gf.imsCommon.ims.module.Questions;
import com.gf.imsCommon.ims.module.Role;
import com.gf.imsCommon.ims.module.User;
import com.gf.imsCommon.ims.module.UserCheck;
import com.gf.imsCommon.ims.module.UserDetail;
import com.gf.imsCommon.ims.module.UserRole;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction{
	Logger log =Logger.getLogger(UserAction.class);
	@Resource
	private UserService userService;
	@Resource
	private CommonService commonService;
	@Resource
	private RoleService roleService;
	
	@RequestMapping(value="/userManage")
	public String userManage(UserQueryBean userQueryBean,HttpServletRequest request,HttpServletResponse response){
		String pageStr=request.getParameter("pageStr");
		Integer page=pageStr==null?1:Integer.parseInt(pageStr);
		if (userQueryBean==null) {
			userQueryBean=new UserQueryBean();
		}
		userQueryBean.setPageNumber(page);
		userQueryBean.setPageSize(5);
		PageResult<User> userData = userService.findUserList(userQueryBean);
		request.setAttribute("pageResult", userData);
		request.setAttribute("roleQueryBean", userQueryBean);
		Map<String, String> userTypeMap = UserType.getUserTypeMap();
		request.setAttribute("userTypeMap", userTypeMap);
		return "/pages/main/user/userList";
	}
	
	@RequestMapping(value="/userAdd")
	public String userAdd(HttpServletRequest request,ModelMap map){
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			User user = userService.getById(id);
			map.put("user", user);
			List<UserRole> userRoleList=userService.getUserRoleListByUserId(user.getId());
			
			map.put("userRoleList",userRoleList);
		}
		Map<String, String> userTypeMap = UserType.getUserTypeMap();
		map.put("userTypeMap", userTypeMap);
		List<Role> roleList=roleService.getRoleList();
		map.put("roleList",roleList);
		return "/pages/main/user/userAdd";
	}
	
	@RequestMapping(value="/userAddDetail")
	public String userAddDetail(HttpServletRequest request,ModelMap map){
		String userId = request.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			User user = userService.getById(userId);
			UserDetail ud=userService.getUserDetailById(userId);
			List<UserCheck> userCheckList=userService.getUserCheckList(userId);
			map.put("user", user);
			map.put("ud", ud);
			map.put("userCheckList", userCheckList);
		}
		return "/pages/main/user/userDetail";
	}
	
	
	
	@RequestMapping(value="/getQuestions")
	@ResponseBody
	public Object getQuestions(HttpServletRequest request){
		List<Questions> questions=userService.getQuestions();
		return questions;
	}
	
	@RequestMapping(value="/getProvinces")
	@ResponseBody
	public Object getProvince(HttpServletRequest request){
		List<Province> provinceList=commonService.getProvince();
		return provinceList.toArray();
	}
	
	@RequestMapping(value="/getCitys")
	@ResponseBody
	public Object getCitys(HttpServletRequest request){
		String fatherId = request.getParameter("fatherId");
		List<City> cityList = commonService.getCityByFather(fatherId);
		return cityList.toArray();
	}
	
	@RequestMapping(value="/getAreas")
	@ResponseBody
	public Object getAreas(HttpServletRequest request){
		String fatherId = request.getParameter("fatherId");
		List<Area> areaList = commonService.getAreaByFather(fatherId);
		return areaList.toArray();
	}
	
	@RequestMapping(value="/uploadFile")
	@ResponseBody
	public Object uploadFile(HttpServletRequest request,MultipartFile file) throws Exception{
		List<String> listData=new ArrayList<String>();
//		CommonsMultipartFile cf= (CommonsMultipartFile)file; //这个myfile是MultipartFile的 转file
//        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
//        File fileIo = fi.getStoreLocation();
        String filename = file.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("/upload/" + filename);// 存放位置
        File f=new File(path);
        FileUtils.copyInputStreamToFile(file.getInputStream(), f);
        String val=HttpClientUtil.transFiles(new File[]{f},"http://115.28.56.254:8090/fms/filesUploadNoRename");
        System.out.println(val);
//		listData.add(thumImgUrl260);
		return listData;
	}
	
	public static void main(String[] args) throws Exception {
		//http://www.cnblogs.com/kcher90/p/3579777.html  上传文件中文名乱码问题解决方案
		File fileIo=new File("D:\\联享家商城合作商信息明细.xlsx");
		String string = HttpClientUtil.transFiles(new File[]{fileIo}, "http://115.28.56.254:8090/fms/filesUploadNoRename");
		System.out.println(string);
	}
	
	@RequestMapping(value="/upload")
	@ResponseBody
	public Object upload(HttpServletRequest request,MultipartFile file) throws Exception{
		List<String> listData=new ArrayList<String>();
		String filedir=application.getRealPath("/upload/serviceImg/").replace("\\", "/");
//		String filedir=request.getSession().getServletContext().getRealPath("/upload/serviceImg/").replace("\\", "/");
		String filename = file.getOriginalFilename();
		//原图文件路径
		String filepath=filedir+File.separator+filename;
		//文件后缀
		String fileSuffix=filename.substring(filename.lastIndexOf(".")+1);
		String tempFileName=PictureUtil.generateFileName();
		//缩列图文件名
		String thumbnailFileName=tempFileName+"_thumbnail"+"."+fileSuffix;
		//缩列图保存路径
		String thumbnailFilePath=filedir+File.separator+thumbnailFileName  ;
		
		
		String tempFileName260=PictureUtil.generateFileName();
		//缩列图文件名
		String thumbnailFileName260=tempFileName260+"_thumbnail"+"."+fileSuffix;
		//缩列图保存路径
		String thumbnailFilePath260=filedir+File.separator+thumbnailFileName260  ;
		
		CommonsMultipartFile cf= (CommonsMultipartFile)file; //这个myfile是MultipartFile的 转file
        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
        File fileIo = fi.getStoreLocation();
		//上传文件
		FileUploadUtil.fileUpload(fileIo, filename, filedir);
		File currentUpFile=FileUtil.changeSuffix(fileIo, filename);
		Thumbnails.of(filepath).size(49, 60).toFile(thumbnailFilePath);
		Thumbnails.of(filepath).size(260, 320).toFile(thumbnailFilePath260);
		
		File uploadFile  = new File(filedir + "/" + thumbnailFileName);  //获取本地的缩略图
		File uploadFile260  = new File(filedir + "/" + thumbnailFileName260);  //获取本地的缩略图
		String serverUrl="http://127.0.0.1/ims/imagesUpload";
		
		String curVal=HttpClientUtil.transImgToFms(new File[]{currentUpFile},serverUrl);
		JSONObject curObj=JSONObject.fromObject(curVal);
		JSONArray curList=curObj.getJSONArray("list");
		String curImgUrl  = curList.getJSONObject(0).getString("o_path");  //图片路径
		
		
		String retVal=HttpClientUtil.transImgToFms(new File[]{uploadFile},serverUrl);
		JSONObject retObj=JSONObject.fromObject(retVal);
		JSONArray list=retObj.getJSONArray("list");
		String thumImgUrl  = list.getJSONObject(0).getString("o_path");  //图片路径
		
		String retVal260=HttpClientUtil.transImgToFms(new File[]{uploadFile260},serverUrl);
		JSONObject retObj260=JSONObject.fromObject(retVal260);
		JSONArray list260=retObj260.getJSONArray("list");
		String thumImgUrl260 = list260.getJSONObject(0).getString("o_path");  //图片路径
		
		//删除原来的图片
		FileUploadUtil.deleteFile(filedir + "/" + filename);
		FileUploadUtil.deleteFile(filedir+ "/" + thumbnailFileName); //删除本地的缩略图
		FileUploadUtil.deleteFile(filedir+ "/" + thumbnailFileName260); //删除本地的缩略图
		
		listData.add(curImgUrl);//上传图片路径
		listData.add(thumImgUrl);
		listData.add(thumImgUrl260);
		return listData;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(HttpServletRequest request) throws Exception {
		String userId = request.getParameter("userId");
//		String userType = request.getParameter("userType");
		String userName = request.getParameter("userName");
//		String userAccount = request.getParameter("userAccount");
		String nickName = request.getParameter("nickName");
//		String password = request.getParameter("password");
//		String mobile = request.getParameter("mobile");
		String question1 = request.getParameter("question1");
		String question2 = request.getParameter("question2");
		String question3 = request.getParameter("question3");
		String answer1 = request.getParameter("answer1");
		String answer2 = request.getParameter("answer2");
		String answer3 = request.getParameter("answer3");
		String imagePath = request.getParameter("imagePath");
		String thumImagePath = request.getParameter("thumImagePath");
		String provinceId = request.getParameter("province");
		String cityId = request.getParameter("city");
		String areaId = request.getParameter("area");
		String detailAddress = request.getParameter("detailAddress");
		String sex = request.getParameter("sex");
		CommonResponse cr=new CommonResponse();
		try {
			User user=userService.getById(userId);
			user.setUserName(userName);
			user.setNickName(nickName);
			UserDetail userDetail=new UserDetail(user.getId(), thumImagePath, imagePath, Integer.parseInt(provinceId), 
					Integer.parseInt(cityId), Integer.parseInt(areaId), detailAddress, sex);
			List<UserCheck> userCheckList=new ArrayList<UserCheck>();
			UserCheck userCheck1=new UserCheck(Env.getUUID(), user.getId(), question1, answer1);
			UserCheck userCheck2=new UserCheck(Env.getUUID(), user.getId(), question2, answer2);
			UserCheck userCheck3=new UserCheck(Env.getUUID(), user.getId(), question3, answer3);
//			userCheckList.add(userCheck1);
//			userCheckList.add(userCheck2);
//			userCheckList.add(userCheck3);
			User u=userService.saveUser(user,userDetail,userCheckList);
			cr.setMessage("保存成功");
			cr.setData(u);
		} catch (Exception e1) {
			cr.setSuccess(false);
			cr.setMessage("服务器异常");
			e1.printStackTrace();
		}
		return cr;
	}
	
	@RequestMapping(value="/addUser")
	@ResponseBody
	public Object addUser(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		String userType = request.getParameter("userType");
		String userAccount = request.getParameter("userAccount");
		String mobile = request.getParameter("mobile");
		String[] roleIds = request.getParameterValues("roleId");
		CommonResponse cr=new CommonResponse();
		cr.setSuccess(true);
		User userByAccount=userService.getByAccount(userAccount);
		User userByPhone=userService.getByMobile(mobile);
		
		if (StringUtils.isNotBlank(id)) {
			if (userByAccount!=null) {
				if (!id.equals(userByAccount.getId())) {
					cr.setSuccess(false);
					cr.setMessage("帐号已存在");
					return cr;
				}
			}
			if (userByPhone!=null) {
				if (!id.equals(userByPhone.getId())) {
					cr.setSuccess(false);
					cr.setMessage("手机号已绑定,请更换手机号");
					return cr;
				}
			}
		}else{
			if (userByAccount!=null) {
				cr.setSuccess(false);
				cr.setMessage("帐号已存在");
				return cr;
			}
			if (userByPhone!=null) {
				cr.setSuccess(false);
				cr.setMessage("手机号已绑定,请更换手机号");
				return cr;
			}
		}
		
		try {
			if (StringUtils.isNotBlank(id)) {//编辑
				userByAccount.setUserType(userType);
				userByAccount.setMobile(mobile);
				userService.updateUser(userByAccount,roleIds);
				cr.setMessage("更新成功");
			}else{
				User user=new User(Env.getUUID(), userType, userAccount,mobile);
				user.setAccountStatus("1");
				user.setCreateTime(new Date());
				userService.addUser(user,roleIds);
				cr.setMessage("保存成功");
			}
		} catch (Exception e1) {
			cr.setSuccess(false);
			cr.setMessage("服务器异常");
			e1.printStackTrace();
		}
		return cr;
	}
	
	@RequestMapping(value="/changeStatus")
	@ResponseBody
	public Object changeStatus(HttpServletRequest request) throws Exception {
		String id = request.getParameter("userId");
		CommonResponse cr=new CommonResponse();
		cr.setSuccess(true);
		try {
			User user = userService.getById(id);
			String accountStatus=user.getAccountStatus();
			if (accountStatus.equals("0")) {
				accountStatus="1";
			}else{
				accountStatus="0";
			}
			user.setAccountStatus(accountStatus);
			userService.update(user);
			cr.setMessage("操作成功");
		} catch (Exception e1) {
			cr.setSuccess(false);
			cr.setMessage("服务器异常");
			e1.printStackTrace();
		}
		return cr;
	}
	
	
	/*@RequestMapping(value="/save")
	@ResponseBody
	public Object save(HttpServletRequest request) throws ParseException {
		String id = request.getParameter("id");
		String roleName = request.getParameter("roleName");
		String roleNote = request.getParameter("roleNote");
		String createAccount = request.getParameter("createAccount");
		String time = request.getParameter("createTime");
		String menus = request.getParameter("menus");
		Role role=new Role(id,roleName,roleNote,createAccount,null);
		if (StringUtils.isNotBlank(time)) {
			Date createTime = new Date(time);
			role.setCreateTime(createTime);
		}
		CommonResponse cr=new CommonResponse();
		if (StringUtils.isBlank(role.getRoleName())) {
			cr.setSuccess(false);
			cr.setMessage("角色名称不可为空");
			return cr;
		}
		Role r=roleService.findByName(role.getRoleName());
		if (r!=null) {
			if (StringUtils.isNotBlank(role.getId())) {
				if (!r.getId().equals(role.getId())) {
					cr.setSuccess(false);
					cr.setMessage("角色名称重复");
					return cr;
				}
			}else{
				cr.setSuccess(false);
				cr.setMessage("角色名称重复");
				return cr;
			}
		}
		
		try {
			if (StringUtils.isNotBlank(role.getId())) {//编辑
					roleService.update(role);
			}else{
				role.setRoleName(roleName);
				role.setRoleNote(roleNote);
				role.setId(Env.getUUID());
				role.setCreateTime(new Date());
				role.setCreateAccount("admin");
				role=roleService.save(role);
			}
			roleService.saveRoleMenus(role.getId(),menus);
		} catch (Exception e1) {
			cr.setSuccess(false);
			cr.setMessage("服务器异常");
			e1.printStackTrace();
		}
		cr.setMessage("保存成功");
		return cr;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request){
		CommonResponse cr=new CommonResponse();
		String roleId = request.getParameter("roleId");
		try {
			if (StringUtils.isNotBlank(roleId)) {
				roleService.deleteRoleById(roleId);
			}
		} catch (Exception e1) {
			cr.setSuccess(false);
			cr.setMessage("服务器异常");
			e1.printStackTrace();
		}
		cr.setMessage("删除成功");
		return cr;
	}
	
	//获取所有菜单json
	@RequestMapping(value="/getMenus")
	@ResponseBody
	public Object getMenus(HttpServletRequest request){
		List<Menu> menuList = menuService.queryMenuTree("");
		return menuList;
	}
	
	//已选菜单json
	@RequestMapping(value="/getCheckedMenus")
	@ResponseBody
	public Object getCheckedMenus(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		String json=roleService.getMenusJsonByRoleId(roleId);
		if (json==null) {
			json = "noData";
		}
		return json;
	}*/
	
}

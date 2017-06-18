package cn.cellcom.szba.controller;

import static cn.cellcom.szba.common.Env.DATA_MSG;
import static cn.cellcom.szba.common.Env.DS;
import static cn.cellcom.szba.common.Env.ENV_PRO;
import static cn.cellcom.szba.common.Env.ERROR_VIEW;
import static cn.cellcom.szba.common.Env.OK_VIEW;
import static cn.cellcom.szba.common.Env.REQUIRE_PARAM;
import static cn.cellcom.szba.common.Env.SYS_ERROR;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TAccountBiz;
import cn.cellcom.szba.biz.TIconBiz;
import cn.cellcom.szba.biz.TRoleBiz;
import cn.cellcom.szba.common.CommonResponse;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.common.utils.HttpHelper;
import cn.cellcom.szba.domain.ClientAccount;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TDepartment;
import cn.cellcom.szba.domain.TIcon;
import cn.cellcom.szba.domain.TRole;
import cn.open.db.JDBC;
import cn.open.db.JDBC.PROCEDURE_PARAMTER_TYPE;
import cn.open.db.JdbcResultSet;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.ProcedureParameter;
import cn.open.db.ProcedureResult;
import cn.open.encrypt.Md5Util;
import cn.open.http.HttpClientHelper;
import cn.open.http.HttpResult;
import cn.open.reflect.JavaBase;
import cn.open.util.ArrayUtil;
import cn.open.util.CommonUtil;
import cn.open.web.ServletUtil;

@Controller
@RequestMapping("/account")
public class TAccountController {

	private static Log log = LogFactory.getLog(TAccountController.class);
	
	private TAccountBiz accountBiz = new TAccountBiz();
	private TRoleBiz roleBiz=new TRoleBiz();
	private TIconBiz iconBiz=new TIconBiz();

	String loginParameters = ENV_PRO.getProperty("login.parameter");
	String procedureName = ENV_PRO.getProperty("login.storeprocedure");
	String clientUrl = ENV_PRO.getProperty("client_url");
	
	Integer pairs = CommonUtil.isInteger(
			ENV_PRO.getProperty("storeprocedure.result.pairs"), true);
	String indexes = ENV_PRO.getProperty("storeprocedure.result.index");

	/**
	 * 登录前的参数校验
	 * 
	 * @param pojo
	 * @param request
	 * @return
	 */
	private DataMsg checkLogin(TAccount pojo, HttpServletRequest request) {
		DataMsg dataMsg = null;
		if (StringUtils.isBlank(pojo.getAccount())
				|| StringUtils.isBlank(pojo.getPassword())) {
			return REQUIRE_PARAM;
		}
		return dataMsg;
	}

	@RequestMapping("/login")
	public String login(TAccount account, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			JavaBase.trimObject(account, null, true);
			DataMsg dataMsg = checkLogin(account, request);
			if (dataMsg != null) {
				request.setAttribute(DATA_MSG, dataMsg);
				return ERROR_VIEW;
			}
			pairs = pairs == null ? 0 : pairs;
			// 存放哪些下标对应的结果集需要当作一个Map<String,Object>来对待.其余的都以List<Map<String,Object>>来存放，哪怕是只有一个。
			List<Integer> indexList = new ArrayList<Integer>();

			if (StringUtils.isNotBlank(indexes)) {
				String[] indexArray = indexes.split(",");
				List<String> tempList = Arrays.asList(indexArray);
				indexList = ArrayUtil.convert(tempList, Integer.class);
			}

			String[] parameterList = loginParameters.split(",");
			if (ArrayUtil.isNotEmpty(parameterList)) {
				List<ProcedureParameter> procedureParameterList = new ArrayList<ProcedureParameter>();

				// 为存储过程设置入参,从请求参数中取
				for (String parameter : parameterList) {
					String parameterValue = ServletUtil.getParamterValue(
							request, parameter);
					if("password".equals(parameter)){
						parameterValue = Md5Util.md5Encrypt(parameterValue);
					}
					procedureParameterList.add(new ProcedureParameter(
							parameterValue, PROCEDURE_PARAMTER_TYPE.IN));
				}
				// 第一个出参，表示登录结果
				procedureParameterList.add(new ProcedureParameter(
						Types.VARCHAR, PROCEDURE_PARAMTER_TYPE.OUT));
				// 一系列出参，个数由config配置文件中的storeprocedure.result.pairs决定
				for (int i = 0; i < pairs; i++) {
					procedureParameterList.add(new ProcedureParameter(
							Types.VARCHAR, PROCEDURE_PARAMTER_TYPE.OUT));
					procedureParameterList.add(new ProcedureParameter(
							oracle.jdbc.OracleTypes.CURSOR,
							PROCEDURE_PARAMTER_TYPE.OUT));
				}
				// 调用
				JDBC jdbc = SpringWebApplicataionContext.getJdbc();
				ProcedureResult pr = jdbc.execProcedureQueryOracle(DS,
						procedureName, procedureParameterList);

				// 得到基本的输出参数列表
				List<Object> outputList = pr.getOutputList();

				if (ArrayUtil.isNotEmpty(outputList)) {
					String loginResult = (String) ArrayUtil
							.getTopOneElement(outputList);
					// 登录成功
					if (StringUtils.isBlank(loginResult)) {
						// 得到若干个结果集
						List<JdbcResultSet> resultList = pr.getResultList();
						if (ArrayUtil.isNotEmpty(resultList)) {
							for (int i = 0; i < resultList.size(); i++) {
								List<Map<String, Object>> list = resultList
										.get(i).toList();

								Object sessionObj = null;
								if (indexList.contains(i)) {
									if(ArrayUtil.isNotEmpty(list)) {
										sessionObj = ArrayUtil.getTopOneElement(list);
									}
								} else {
									sessionObj = list;
								}

								String key = String.valueOf(outputList
										.get(i + 1));
								
								log.info("session setAttribute " + key + "===="
										+ sessionObj);
								session.setAttribute(key, sessionObj);
							}
						}
						
						//登录客户端，获取sessionId
						clientLogin(account,session);
					} else {
						request.setAttribute(DATA_MSG, new DataMsg("999",
								String.valueOf(loginResult)));
						return ERROR_VIEW;
					}
				}
			} else {
				request.setAttribute(DATA_MSG, REQUIRE_PARAM);
				return ERROR_VIEW;
			}
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute(DATA_MSG, SYS_ERROR);
			return ERROR_VIEW;
		}
		return OK_VIEW;
	}
	
	//登录客户端，获取sessionId
	private void clientLogin(TAccount account,HttpSession session){
		Map<String, String> params=new HashMap<String, String>();
		params.put("account", account.getAccount());
		
		String password=Md5Util.md5Encrypt(account.getPassword());
		params.put("password", password);
		
		String timestamp=String.valueOf(new Date().getTime());
		params.put("timestamp", timestamp);
		
		String str=account.getAccount()+password+timestamp+"XckyDx#ab";
		String authstring=Md5Util.md5Encrypt(str);
		params.put("authstring", authstring);
		
		String url=clientUrl + "/account/login.do";
		log.info("client url="+url);
		log.info("client params="+params.toString());
		try {
			HttpClientHelper hh=new HttpClientHelper();
			HttpResult hr=hh.post(url, params, null);
			String response=hr.getText();
			ClientAccount ca=(ClientAccount) JsonUtil.jsonToBean(response, ClientAccount.class);
			ca.setTimestamp(timestamp);
			authstring=Md5Util.md5Encrypt(timestamp+"XckyDx#ab");
			ca.setAuthstring(authstring);
			session.setAttribute("ClientAccount", ca);
		} catch (Exception e) {
			log.error("", e);
		}
		
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		while(names.hasMoreElements()) {
			String name=names.nextElement();
			session.removeAttribute(name);
		}
		session.invalidate();
		try {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//用户管理
	
	/**
	 * 查询用户列表
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page){
		MyPagerUtil.initPager(page);
		
		Map<String, String[]> map = requ.getParameterMap();
		ListAndPager<TAccount> list;
		try {
			list = accountBiz.queryForPage(map, page);
		 
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TAccount> accounts = list.getList();

			requ.setAttribute("data", accounts);
			requ.setAttribute("page", proPage);
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/account/accountManage";
	}
	
	public String queryForPage2(HttpServletRequest requ,Pager page){
		MyPagerUtil.initPager(page);
		ListAndPager<TAccount> list;
		try {
			list = accountBiz.queryForPage2(page);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TAccount> accounts = list.getList();

			requ.setAttribute("data", accounts);
			requ.setAttribute("page", proPage);
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 查询用户列表
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryByList")
	public String queryByList(HttpServletRequest requ, Pager page){
		MyPagerUtil.initPager(page);
		
		Map<String, String[]> map = requ.getParameterMap();
		long roleId = Long.parseLong(requ.getParameter("id"));
		ListAndPager<TAccount> list;
		try {
			list = accountBiz.queryForPage(map, page);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TAccount> accounts = list.getList();
			requ.setAttribute("data", accounts);
			requ.setAttribute("page", proPage);
			requ.setAttribute("params", requ.getParameterMap());
			requ.setAttribute("roleId", roleId);
			
			List<TAccount> accountList= accountBiz.roleAccounts(roleId);
			String accountStr="";
			if(accountList!=null&&!accountList.isEmpty()){
				for(TAccount account:accountList){
					accountStr=accountStr+account.getAccount()+";";
				}
			}
			accountStr=accountStr!=""?accountStr.substring(0,accountStr.length()-1):"";
			requ.setAttribute("accountStr", accountStr);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/account/accountList";
	}
	/**
	 * 跳转用户信息修改页面
	 * @param requ
	 * @return
	 */
	@RequestMapping("toEditPage")
	public String toEditPage(HttpServletRequest requ){
		String account = requ.getParameter("account");
		TAccount entity = accountBiz.queryById(account);
		List<TRole> roleList= roleBiz.accountRoles(account);
		List<TIcon> iconList= iconBiz.accountIcons(account);
		List<TRole> rlist= roleBiz.roleList();
		List<TIcon> ilist= iconBiz.iconList();
		Map<String, String[]> rMap=new HashMap<String, String[]>();
		for (TRole tRole : rlist) {
			boolean exist=false;
			for (TRole atRole : roleList) {
				if(tRole.getName().equals(atRole.getName())){
					rMap.put(String.valueOf(tRole.getId()),new String[]{tRole.getName(),"1"});
					exist=true;
					break;
				}
			}
			if(exist==false){
				rMap.put(String.valueOf(tRole.getId()),new String[]{tRole.getName(),"0"});
			}
		}
		requ.setAttribute("rMap",rMap );
		Map<String, String[]> iMap=new HashMap<String, String[]>();
		for (TIcon tIcon : ilist) {
			boolean exist=false;
			for (TIcon atIcon : iconList) {
				if(tIcon.getName().equals(atIcon.getName())){
					iMap.put(String.valueOf(tIcon.getId()),new String[]{tIcon.getName(),"1"});
					exist=true;
					break;
				}
			}
			if(exist==false){
				iMap.put(String.valueOf(tIcon.getId()),new String[]{tIcon.getName(),"0"});
			}
		}
		requ.setAttribute("iMap",iMap );

		requ.setAttribute("entity", entity);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/account/editAccount";
	}
	
	/**
	 * 跳转用户详细页面
	 * @param requ
	 * @return
	 */
	@RequestMapping("todetailPage")
	public String todetailPage(HttpServletRequest requ){
		
		String account = requ.getParameter("account");
		TAccount entity = accountBiz.queryById(account);
		List<TRole> roleList= roleBiz.accountRoles(account);
		List<TIcon> iconList= iconBiz.accountIcons(account);
		requ.setAttribute("iconList", iconList);
		requ.setAttribute("roleList", roleList);
		requ.setAttribute("entity", entity);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/account/detailAccount";
	}
	
	/**
	 * 跳转添加用户页面
	 * @param requ
	 * @return
	 */
	@RequestMapping("toAddPage")
	public String toAddPage(HttpServletRequest requ){
		
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/account/addAccount";
	}
	
	/**
	 * 添加用户
	 * @param requ
	 * @param entity
	 * @return
	 */
	@RequestMapping("add")
	public String add(HttpServletRequest requ, TAccount entity){
		entity.setPassword(Md5Util.md5Encrypt(entity.getPassword()));
		String result = accountBiz.insert(entity);
		requ.setAttribute("message", result);
		
		return toAddPage(requ);
	}
	
	/**
	 * 修改用户信息统一接口
	 * @param requ
	 * @param entity
	 * @return
	 */
	@RequestMapping("save")
	public String save(HttpServletRequest requ, TAccount entity){
		String role=requ.getParameter("role");
		if(StringUtils.isEmpty(entity.getAccount())){
			requ.setAttribute("message", "acount must not empty");
		} else{
			if(StringUtils.isNotBlank(entity.getPassword())){
				entity.setPassword(Md5Util.md5Encrypt(entity.getPassword()));
			}
			int result = accountBiz.update(entity);
			roleBiz.updateAccountRole(entity.getAccount(), role);
			if(result>0){
				requ.setAttribute("message", "success");
			}
		}
		String from = requ.getParameter("from");
		if(from.equals("editAccount")){
			requ.setAttribute("account", entity.getAccount());
			return toEditPage(requ);
		}
		
		return "/jsp/account/addAccount";
	}
	
	/**
	 * 跳转管理员修改用户密码页面
	 * @param requ
	 * @return
	 */
	@RequestMapping("toChangePassword")
	public String toChangePassword(HttpServletRequest requ){
		
		requ.setAttribute("accountPass", requ.getParameter("account"));

		return "/jsp/account/changePassword";
	}
	
	@RequestMapping("/changePassword")
	public String changePassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String accStr = request.getParameter("accountPass");
		String newPsd = (String) request.getParameter("newPsd");
		String newPsdMd5 = Md5Util.md5Encrypt(newPsd);
		String message = "false";
		TAccountBiz tb = new TAccountBiz();
	    try {
			int rows = tb.modifyAccountInfo(accStr, newPsdMd5);
			if(rows>0){
				message = "true";
			}
		} catch (Exception e) {
			log.error("", e);
			PrintTool.print(response, "重置密码失败", null);
		}
	    Map<String,Object> map = new HashMap<String,Object>();
        map.put("message", message);
        PrintTool.print(response, PrintTool.printJSON(map), "json");
		//request.setAttribute("message", message);
	    return null;
	}
	
	//个人中心
	/**
	 * 账号信息修改
	 * 
	 * @param pojo
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/accountInfoModify")
	public String accountInfoModify(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String accStr = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
			String psd = (String) request.getParameter("psd");
			String newPsd = (String) request.getParameter("newPsd");
			String psdMd5 = Md5Util.md5Encrypt(psd);
			String newPsdMd5 = Md5Util.md5Encrypt(newPsd);
			TAccount acc = accountBiz.getPassword(accStr);
			String message = "false";
			request.setAttribute("message", message);
			if(acc.getPassword().equals(psdMd5)){
				message = "true";
				request.setAttribute("message", message);
			}else{
			    return "/jsp/account/accountInfo";
			}
			TAccountBiz tb = new TAccountBiz();
			tb.modifyAccountInfo(accStr, newPsdMd5);
			//PrintTool.print(response, "修改成功", null);
		} catch (Exception e) {
			log.error("", e);
			PrintTool.print(response, "修改失败", null);
		}
		return "/jsp/account/accountInfo";
	  // return "/index";
	}
	
	// 部门列表
	@RequestMapping("/loadDepartments")
	public void loadDepartments(HttpServletRequest request,
			HttpServletResponse response){
		List<TDepartment> list = accountBiz.findDepartment();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", list);
		JSONObject json = JSONObject.fromObject(data);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	@RequestMapping("/saveDepartment")
	public void saveDepartment(HttpServletRequest request, HttpServletResponse response, TDepartment entity){
		try {
			TDepartment department = accountBiz.saveDepartment(entity);
			CommonResponse cr = new CommonResponse();
			if(null!=department){
				cr.setSuccess(true);
				cr.setData(department);
			} else {
				cr.setSuccess(false);
			}
			PrintTool.print(response, PrintTool.printJSON(cr), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
}


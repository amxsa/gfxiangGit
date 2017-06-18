package cn.cellcom.czt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.rmi.RemoteException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.common.file.FileTool;
import cn.cellcom.common.file.excel.ExcelTemplate;
import cn.cellcom.common.file.excel.ExcelTool;
import cn.cellcom.czt.bus.LoginBus;
import cn.cellcom.czt.bus.TDCodeBus;
import cn.cellcom.czt.bus.TDCodeGroupBus;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.DataPo;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.SpTdcode;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TTdcodeGroup;
import cn.cellcom.czt.po.TTdcodeOperateHistory;
import cn.cellcom.czt.po.UploadExcel;

import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class TDCodeManagerAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(TDCodeManagerAction.class);
	private UploadExcel uploadBarCodeBindTDCode = new UploadExcel();
	private UploadExcel tdcodeGroup = new UploadExcel();

	

	public UploadExcel getTdcodeGroup() {
		return tdcodeGroup;
	}

	public void setTdcodeGroup(UploadExcel tdcodeGroup) {
		this.tdcodeGroup = tdcodeGroup;
	}

	public UploadExcel getUploadBarCodeBindTDCode() {
		return uploadBarCodeBindTDCode;
	}

	public void setUploadBarCodeBindTDCode(UploadExcel uploadBarCodeBindTDCode) {
		this.uploadBarCodeBindTDCode = uploadBarCodeBindTDCode;
	}

	public String showTDCode() {
		String tdcode = getParameter("tdcode");
		String tdcodemd5 = getParameter("tdcodemd5");
		String status = getParameter("status");
		String mobilenum = getParameter("mobilenum");
		String mobileid = getParameter("mobileid");
		String starttime = getParameter("starttime");
		String endtime = getParameter("endtime");

		StringBuffer sql = new StringBuffer();
		sql.append("select * from sp_tdcode where 1=1  ");
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(tdcode)) {
			sql.append(" and tdcode = ? ");
			params.add(tdcode);
			getRequest().setAttribute("tdcode", tdcode);
		}
		if (StringUtils.isNotBlank(tdcodemd5)) {
			if (tdcodemd5.length() == 20) {
				sql.append(" and tdcodemd5  = ?  ");
				params.add(tdcodemd5);
				getRequest().setAttribute("tdcodemd5", tdcodemd5);
			} else if (tdcodemd5.length() < 8) {
				getRequest().setAttribute("result", "设备号至少输入8位");
				return "error";
			} else if (tdcodemd5.length() > 20) {
				getRequest().setAttribute("result", "SN输入错误");
				return "error";
			} else {
				sql.append(" and tdcodemd5 like ?  ");
				params.add("%" + tdcodemd5);
				getRequest().setAttribute("tdcodemd5", tdcodemd5);
			}

		}
		if (StringUtils.isNotBlank(status)) {
			sql.append(" and status = ? ");
			params.add(Integer.parseInt(status));
			getRequest().setAttribute("status", status);
		}
		if (StringUtils.isNotBlank(mobilenum)) {
			sql.append(" and mobilenum = ? ");
			params.add(Long.parseLong(mobilenum));
			getRequest().setAttribute("mobilenum", mobilenum);
		}
		if (StringUtils.isNotBlank(mobileid)) {
			sql.append(" and mobileid = ? ");
			params.add(mobileid);
			getRequest().setAttribute("mobileid", mobileid);
		}

		/**
		 * String obdactive = getParameter("obdactive"); if
		 * (StringUtils.isNotBlank(obdactive)) {
		 * sql.append(" and obdactive = ? ");
		 * params.add(Integer.parseInt(obdactive));
		 * getRequest().setAttribute("obdactive", obdactive); }
		 */
		String activeType = getParameter("activeType");
		if (StringUtils.isNotBlank(activeType)) {
			if ("0".equals(activeType)) {
				sql.append(" and mobilenum is null ");
			} else if ("1".equals(activeType)) {
				sql.append(" and mobilenum is not null ");
			}
			getRequest().setAttribute("activeType", activeType);
		}

		String spcode = getParameter("spcode");
		if (StringUtils.isNotBlank(spcode)) {
			if ("O10".equals(spcode)) {
				sql.append(" and (tdcode like ? or tdcode like ? ) ");
				params.add(spcode + "%");
				params.add(spcode.toLowerCase() + "%");
			} else {
				sql.append(" and tdcode like ?  ");
				params.add(spcode + "%");
			}

			getRequest().setAttribute("spcode", spcode);
		}
		String yearmonth = getParameter("yearmonth");
		if (StringUtils.isNotBlank(yearmonth)) {
			sql.append(" and right(tdcode,4)= ? ");
			params.add(yearmonth);
			getRequest().setAttribute("yearmonth", yearmonth);
		}
		String barcode = getParameter("barcode");
		if (StringUtils.isNotBlank(barcode)) {
			sql.append(" and barcode = ? ");
			params.add(barcode);
			getRequest().setAttribute("barcode", barcode);
		}

		String fluxcard = getParameter("fluxcard");
		if (StringUtils.isNotBlank(fluxcard)) {
			sql.append(" and fluxcard = ? ");
			params.add(fluxcard);
			getRequest().setAttribute("fluxcard", fluxcard);
		}

		String orderid = getParameter("orderid");
		if (StringUtils.isNotBlank(orderid)) {
			sql.append(" and orderid = ? ");
			params.add(orderid);
			getRequest().setAttribute("orderid", orderid);
		}

		String groupId = getParameter("groupId");
		if (StringUtils.isNotBlank(groupId)) {
			sql.append(" and group_id = ? ");
			params.add(groupId);
			getRequest().setAttribute("groupId", groupId);
		}
		String idcardState = getParameter("idcardState");
		if (StringUtils.isNotBlank(idcardState)) {
			sql.append(" and idcard_state = ? ");
			params.add(idcardState);
			getRequest().setAttribute("idcardState", idcardState);
		}

		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and submittime>? and submittime< ? ");
			params.add(starttime);
			params.add(endtime);
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);

		String startactivetime = getParameter("startactivetime");
		String endactivetime = getParameter("endactivetime");
		if (StringUtils.isNotBlank(startactivetime)
				&& StringUtils.isNotBlank(endactivetime)) {
			sql.append(" and activetime>? and activetime< ? ");
			params.add(startactivetime);
			params.add(endactivetime);
		}
		getRequest().setAttribute("startactivetime", startactivetime);
		getRequest().setAttribute("endactivetime", endactivetime);

		sql.append(" order by createtime desc ");

		PageBus pageBus = new PageBus();
		JDBC jdbc = ApplicationContextTool.getJdbc();
		TDCodeBus bus = new TDCodeBus();
		List<TTdcodeGroup> groupList = bus.queryGroup(jdbc);
		Map<Integer, String> groupMap = new HashMap<Integer, String>();
		if (groupList != null) {
			getRequest().setAttribute("groupList", groupList);
			for (int i = 0, len = groupList.size(); i < len; i++) {
				groupMap.put(groupList.get(i).getNumber(), groupList.get(i)
						.getName());
			}
		}
		try {
			if ("true".equals(getParameter("exportFlag"))) {
				List<SpTdcode> list = jdbc.query(
						ApplicationContextTool.getDataSource(), sql.toString(),
						SpTdcode.class, params.toArray(), 0, 0);
				 if (list != null && list.size() > 0) {
					 SpTdcode po = null;
					 for (int i = 0, len = list.size(); i < len; i++) {
						 po = list.get(i);
						 if(po.getGroupId()!=null)
							 po.setGroupName(groupMap.get(po.getGroupId()));
					 }
				 }
				
				getResponse().setContentType(
						"application/vnd.ms-excel;charset=gb2312");
				getResponse().setHeader(
						"Content-Disposition",
						"attachment;"
								+ " filename="
								+ new String("TDCode列表.xlsx".getBytes(),
										"ISO-8859-1"));
				ExcelTool.writeExcelByList("TDCode列表", list, new String[] {
						"tdcode", "tdcodemd5", "barcode", "statusStr",
						"orderid", "mobilenum", "mobileid", "fluxcard",
						"limiteTime","idcardStateStr", "tdverifyStr", "obdactiveStr","groupName",
						"activetime", "submittime" }, new String[] { "内部二维码",
						"加密二维码(SN)", "条码(PN)", "发放状态", "订单号", "手机号", "加密手机号",
						"流量卡", "有效时间","实名状态", "历史状态", "激活状态","所在组", "激活时间", "操作时间" },
						getResponse().getOutputStream(),
						ExcelTemplate.ExcelVersion.XLSX);
				return null;
			}
			PageResult<SpTdcode> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), SpTdcode.class);
			if (pageResult != null) {
					List<SpTdcode> list = pageResult.getContent();
					if (list != null && list.size() > 0) {
						SpTdcode po = null;
						for (int i = 0, len = list.size(); i < len; i++) {
							po = list.get(i);
							 if(po.getGroupId()!=null)
								 po.setGroupName(groupMap.get(po.getGroupId()));
						}
					}
				
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showTDCode";
	}

	public String addTDCode() {
		if ("init".equals(getParameter("flag"))) {
			getRequest().setAttribute("date", Env.YEARMONTH);
			getRequest().setAttribute("spcode", Env.SPCODE);
			// 查询组
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				List<TTdcodeGroup> list = jdbc.query(
						ApplicationContextTool.getDataSource(),
						"select number, name from t_tdcode_group ",
						TTdcodeGroup.class, null, 0, 0);
				getRequest().setAttribute("list", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "addTDCode";
		} else {
			String spcode = getParameter("spcode");
			String version = getParameter("version");
			String groupId = getParameter("groupId");
			// 生成产品模式 1=单个，2=批量生成
			String productMode = getParameter("productMode");
			String date = getParameter("date");
			if (StringUtils.isNotBlank(spcode) && StringUtils.isNotBlank(date)
					&& StringUtils.isNotBlank(version)) {
				// 二维码格式：o10l0000071410 格式
				// 前3位代表厂家，第4位代表配置,5-11代表数字(000001-999999),最后4位代表年月
				if ("1".equals(productMode)) {
					StringBuffer tdcode = new StringBuffer();
					String startNum = getParameter("startNum");
					tdcode.append(spcode).append(version).append(startNum)
							.append(date);
					// 判断是否存在
					JDBC jdbc = ApplicationContextTool.getJdbc();

					try {
						String tdcodemd5 = StringUtils
								.substring(MD5.getMD5((startNum + date)
										.getBytes()), 8, 24);
						SpTdcode po = jdbc
								.queryForObject(
										ApplicationContextTool.getDataSource(),
										"select ID from sp_tdcode where  right(tdcodemd5,8) = ?  ",
										SpTdcode.class,
										new String[] { StringUtils.right(
												tdcodemd5, 8) });
						if (po == null) {
							po = new SpTdcode();
							TDCodeBus bus = new TDCodeBus();
							po.setTdcode(tdcode.toString());
							// tdcodemd5规则 厂家+版本+ （ 编号+年月 的MD5取8-24位）
							po.setTdcodemd5(spcode + version + tdcodemd5);
							po.setGroupId(new Integer(groupId));
							bus.addTDCode(po);
							getRequest().setAttribute("result", "新增成功");
							getRequest()
									.setAttribute(
											"url",
											getRequest().getContextPath()
													+ "/manager/TDCodeManagerAction_showTDCode.do");
							return "success";
						} else {
							getRequest().setAttribute(
									"result",
									tdcode + "生成的SN[" + tdcodemd5
											+ "]已存在或后八位存在");
							return "error";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("2".equals(productMode)) {
					String startNum = getParameter("startNum");
					String endNum = getParameter("endNum");
					TDCodeBus bus = new TDCodeBus();
					JSONObject json = bus.addBatchTDCode(spcode, version,
							startNum, endNum, date, groupId);
					if (json != null) {
						if (json.containsKey("failed")) {
							String failed = json.getString("failed");
							getRequest().setAttribute("result", failed);
							return "error";
						}
						JSONArray success = (JSONArray) json.get("success");
						JSONArray error = (JSONArray) json.get("error");
						StringBuffer result = new StringBuffer();
						result.append("成功条数：").append(success.size())
								.append("条，失败条数：").append(error.size())
								.append("条");
						result.append("<br />成功：");
						if (success.size() > 0) {
							result.append("<br />");
							for (int i = 0, len = success.size(); i < len; i++) {
								result.append(success.get(i)).append("，");
								if (i == 5) {
									result.append("<br/>");
								}
							}
						} else {
							result.append("无<br/>");
						}

						result.append("<br />失败：");
						if (error.size() > 0) {
							result.append("<br />");
							for (int i = 0, len = error.size(); i < len; i++) {
								result.append(error.get(i)).append("，");
								result.append("<br/>");
							}
						} else {
							result.append("无<br/>");
						}
						getRequest().setAttribute("result", result.toString());
						return "success";

					}
				} else {
					getRequest().setAttribute("result", "非正常访问");
					return "error";
				}
			} else {
				getRequest().setAttribute("result", "非正常参数访问");
				return "error";
			}
		}
		return "error";
	}

	public String deleteTDCode() throws IOException {
		String[] ids = getRequest().getParameterValues("id");
		if (ids != null && ids.length > 0) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from sp_tdcode where id in (");
			for (int i = 0; i < ids.length; i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(")");
			log.info(">>>>>>" + sql.toString());
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				int count = jdbc.update(ApplicationContextTool.getDataSource(),
						sql.toString(), ids);
				if (count > 0) {
					PrintTool.print(getResponse(), "true|删除成功", null);
				} else {
					PrintTool.print(getResponse(), "false|删除失败", null);
				}
				PrintTool.print(getResponse(), "true|删除成功", null);
			} catch (Exception e) {
				log.error("删除TDCode失败", e);
				PrintTool.print(getResponse(), "false|删除失败", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择删除项", null);
		}
		return null;
	}

	public String showTDCodeHistory() {

		String mobile = getParameter("mobile");
		String tdcodemd5 = getParameter("tdcodemd5");
		String operateType = getParameter("operateType");
		String fromPart = getParameter("fromPart");
		String starttime = getParameter("starttime");
		String endtime = getParameter("endtime");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_tdcode_operate_history  where 1=1  ");

		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(tdcodemd5)) {
			sql.append(" and tdcodemd5 = ? ");
			params.add(tdcodemd5);
			getRequest().setAttribute("tdcodemd5", tdcodemd5);
		}
		if (StringUtils.isNotBlank(mobile)) {
			sql.append(" and mobile = ? ");
			params.add(mobile);
			getRequest().setAttribute("mobile", mobile);
		}
		if (StringUtils.isNotBlank(operateType)) {
			sql.append(" and operate_type = ? ");
			params.add(operateType);
			getRequest().setAttribute("operateType", operateType);
		}
		if (StringUtils.isNotBlank(fromPart)) {
			sql.append(" and from_part = ? ");
			params.add(fromPart);
			getRequest().setAttribute("fromPart", fromPart);
		}

		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and operate_time>? and operate_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by operate_time desc ");

		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TTdcodeOperateHistory> pageResult = pageBus
					.queryPageDataMysql(jdbc,
							ApplicationContextTool.getDataSource(), page,
							sql.toString(), params.toArray(),
							TTdcodeOperateHistory.class);
			if (pageResult != null) {
				List<TTdcodeOperateHistory> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					TTdcodeOperateHistory po = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						po = list.get(i);
						po.setOperateType(Env.OPERATE_TYPE.get(po
								.getOperateType()));
					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showTDCodeHistory";
	}

	public String releasebindTDCode() {
		String mobileNum = getParameter("mobileNum");
		String fromPart = getParameter("fromPart");
		LoginBus bus = new LoginBus();
		Data data = bus.releasebindManager(mobileNum, fromPart);
		getRequest().setAttribute("result", data.getMsg());
		if (data.isState()) {
			return "success";
		}
		return "error";
	}

	public String registTDCode() {
		String mobileNum = getParameter("mobileNum");
		String tdCode = getParameter("tdCode");
		String fromPart = getParameter("fromPart");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("tdCode", tdCode);
		params.put("mobileNum", mobileNum);
		params.put("fromPart", fromPart);
		params.put("serviceIp", getRequest().getRemoteAddr());
		PrintTool.printLog(params);
		LoginBus bus = new LoginBus();
		Data data = null;
		;
		try {
			data = bus.registManager(params);
			getRequest().setAttribute("result", data.getMsg());
			if (data.isState()) {
				return "success";
			}
		} catch (RemoteException e) {
			log.error("", e);
			getRequest().setAttribute("result", "激活失败");
		}

		return "error";
	}

	public String sendTDCode() throws IOException {
		String tdcodemd5 = getParameter("tdcodemd5");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("tdcodemd5", tdcodemd5);
		params.put("serviceIp", getRequest().getRemoteAddr());
		TDCodeBus bus = new TDCodeBus();
		Data msg = bus.sendTDCode(params);
		try {
			if (msg.isState()) {
				PrintTool.print(getResponse(), "true|" + msg.getMsg(), null);
			} else {
				PrintTool.print(getResponse(), "false|" + msg.getMsg(), null);
			}
		} catch (Exception e) {
			log.error(tdcodemd5 + "发放失败", e);
			PrintTool.print(getResponse(), "false|" + tdcodemd5 + "发放失败", null);
		}
		return null;
	}

	public String addBatchBarCodeBindTDCode() {
		TManager manager = (TManager) getSession().getAttribute("login");
		StringBuffer filename = new StringBuffer();
		if (uploadBarCodeBindTDCode.getFile() != null
				&& uploadBarCodeBindTDCode.getFile().length() > 0) {
			try {
				filename.append(ConfLoad.getProperty("ORDER_UPLOAD"))
						.append("barcode_bind_tdcode_upload")
						.append(File.separator)
						.append(DateTool.formateTime2String(new Date(),
								"yyyyMMdd")).append(File.separator);
				FileTool.isExistDir(new File(filename.toString()), true);
				filename.append(UUID.randomUUID())
						.append(".")
						.append(FileTool.getExtName(uploadBarCodeBindTDCode
								.getFileFileName()));
				if (filename.toString().endsWith(".xls")
						|| filename.toString().endsWith(".xlsx")) {
					FileOutputStream fos = new FileOutputStream(
							filename.toString());
					FileInputStream fis = new FileInputStream(
							uploadBarCodeBindTDCode.getFile());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					if (fos != null)
						fos.close();
					if (fis != null)
						fis.close();
				} else {
					getRequest().setAttribute("result", "上传文件非Excel格式");
					return "error";
				}

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			getRequest().setAttribute("result", "上传文件为空");
			return "error";
		}
		File file = new File(filename.toString());
		TDCodeBus bus = new TDCodeBus();
		try {
			Data msg = bus.addBatchBarCodeBindTDCode(manager, file);
			if (msg.isState()) {
				getRequest().setAttribute("result", msg.getMsg());
				return "success";
			} else {
				getRequest().setAttribute("result", msg.getMsg());
				return "error";

			}
		} catch (Exception e) {
			log.error("SN和条码批量上传异常", e);
			getRequest().setAttribute("result", "SN和条码批量上传异常");
			return "error";
		}

	}

	public String showTDCodeGroup() {
		String name = getParameter("name");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_tdcode_group  where 1=1  ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and name like ? ");
			params.add("%" + name + "%");
			getRequest().setAttribute("name", name);
		}
		sql.append(" order by submit_time desc ");

		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TTdcodeGroup> pageResult = pageBus.queryPageDataMysql(
					jdbc, ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TTdcodeGroup.class);
			if (pageResult != null) {
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showTDCodeGroup";
	}

	public String modifyTDCodeGroup() throws IOException {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		
		String number = getParameter("number");
		String name = getParameter("name");
		String operateType = getParameter("operateType");

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("number", number);
		params.put("name", name);
		params.put("operateType", operateType);
		TDCodeBus bus = new TDCodeBus();
		Data msg = bus.modifyTDCodeGroup(params);
		try {
			if (msg.isState()) {
				
				Map<Integer, TTdcodeGroup> list = new TDCodeGroupBus().getGroup();
				if(list!=null){
					Env.TDCODE_GROUP = list;
					ApplicationContextTool.getContext().getServletContext().setAttribute("TDCODE_GROUP", list);
				}
				PrintTool.print(getResponse(), "true|" + msg.getMsg(), null);
			} else {
				PrintTool.print(getResponse(), "false|" + msg.getMsg(), null);
			}
		} catch (Exception e) {
			log.error("组操作失败", e);
			PrintTool.print(getResponse(), "false|组操作失败", null);
		}
		return null;
	}

	public String queryComparisonHybPre() {
		// 查询组返回到页面
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			TDCodeBus bus = new TDCodeBus();
			List<TTdcodeGroup> groupList = bus.queryGroup(jdbc);
			if (groupList != null) {
				getRequest().setAttribute("groupList", groupList);
			}
			String fromPart=getParameter("fromPart");
			if(fromPart!=null){
				return fromPart;
			}
			return "showSyncTDCode";
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("result", "查询组失败");
			return "error";
		}
	}

	public String queryComparisonHyb() {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		String groupId = getParameter("groupId");
		// groupId="10002";
		String tdcodemd5 = getParameter("tdcodemd5");

		try {
			TDCodeBus bus = new TDCodeBus();
			List<TTdcodeGroup> groupList = bus.queryGroup(jdbc);
			if (groupList != null) {
				getRequest().setAttribute("groupList", groupList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("result", "查询组失败");
			return "error";
		}
		if (StringUtils.isBlank(groupId) && StringUtils.isBlank(tdcodemd5)) {
			getRequest().setAttribute("result", "请选择组或者设备二维码");
			return "showSyncTDCode";
		}
		getRequest().setAttribute("groupId", groupId);
		getRequest().setAttribute("tdcodemd5", tdcodemd5);
		String currentPage = getParameter("currentPage");
		// currentPage="1";
		PageTool page = new PageTool(currentPage, 30, getRequest()
				.getRequestURI());
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (tdcodemd5 != null)
			map.put("tdcodemd5", tdcodemd5);
		if (groupId != null)
			map.put("groupId", groupId);
		map.put("currentPage", String.valueOf(page.getCurrentPage()));
		map.put("pageSize", String.valueOf(page.getPageRecord()));
		TDCodeBus bus = new TDCodeBus();
		DataPo data = bus.querySyncHyb(map);
		if ("S".equals(data.getState())) {
			page.init(page.getPageRecord(), Integer.parseInt(data.getMsg()),
					page.getCurrentPage());
			PageResult<JSONObject> pageResult = new PageResult<JSONObject>();
			pageResult.setPage(page);
			JSONArray jsoArray = data.getParambuf();
			if (jsoArray != null && jsoArray.size() > 0) {
				List<JSONObject> list = new ArrayList<JSONObject>();
				for (int i = 0, len = jsoArray.size(); i < len; i++) {
					list.add(jsoArray.getJSONObject(i));
				}
				pageResult.setContent(list);
			} else {
				getRequest().setAttribute("result", "查询接口无数据");
			}
			getRequest().setAttribute("pageResult", pageResult);
			return "showSyncTDCode";
		} else {
			getRequest().setAttribute("result", "查询接口异常");
			return "error";
		}
	}
	
	
	public String uploadTDCodeGroupBatch() {
		TManager manager = (TManager) getSession().getAttribute("login");
		StringBuffer filename = new StringBuffer();
		if (tdcodeGroup.getFile() != null
				&& tdcodeGroup.getFile().length() > 0) {
			try {
				filename.append(ConfLoad.getProperty("ORDER_UPLOAD"))
						.append("syncTDCode")
						.append(File.separator)
						.append(DateTool.formateTime2String(new Date(),
								"yyyyMMdd")).append(File.separator);
				FileTool.isExistDir(new File(filename.toString()), true);
				filename.append(UUID.randomUUID())
						.append(".")
						.append(FileTool.getExtName(tdcodeGroup
								.getFileFileName()));
				if (filename.toString().endsWith(".xls")
						|| filename.toString().endsWith(".xlsx")) {
					FileOutputStream fos = new FileOutputStream(
							filename.toString());
					FileInputStream fis = new FileInputStream(
							tdcodeGroup.getFile());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					if (fos != null)
						fos.close();
					if (fis != null)
						fis.close();
				} else {
					getRequest().setAttribute("result", "上传文件非Excel格式");
					return "error";
				}

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			getRequest().setAttribute("result", "上传文件为空");
			return "error";
		}
		File file = new File(filename.toString());
		TDCodeBus bus = new TDCodeBus();
		Integer groupId =Integer.parseInt(getParameter("groupId"));
		try {
			Data msg = bus.updateTDCodeGroup(manager,groupId, file);
			if (msg.isState()) {
				getRequest().setAttribute("result", msg.getMsg());
				return "success";
			} else {
				getRequest().setAttribute("result", msg.getMsg());
				return "error";

			}
		} catch (Exception e) {
			log.error("设备组上传异常", e);
			getRequest().setAttribute("result", "设备组上传异常");
			return "error";
		}

	}

}

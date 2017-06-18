package cn.cellcom.czt.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.file.FileTool;
import cn.cellcom.common.file.excel.ExcelTemplate;
import cn.cellcom.common.file.excel.ExcelTool;
import cn.cellcom.czt.bus.FLuxIdcardBus;
import cn.cellcom.czt.bus.LoginBus;
import cn.cellcom.czt.bus.TDCodeBus;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;

import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.SpTdcode;
import cn.cellcom.czt.po.TFluxIdcard;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class FluxIdcardManagerAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory
			.getLog(FluxIdcardManagerAction.class);
	
	/**
	 * 
	 * @return
	 */
	public String showYuxianFluxIdcard() {
		String tdcodemd5 = getParameter("tdcodemd5Query");
		String fluxcard = getParameter("fluxcardQuery");
		String idcardState = getParameter("idcardStateQuery");
		String orderid= getParameter("orderidQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select b.orderid,b.tdcodemd5,b.fluxcard,b.idcard_state,b.barcode,a.submit_time ");
		sql.append(" from t_order a left join sp_tdcode b on a.id =b.orderid ");
		sql.append(" where b.orderid is not null and  b.fluxcard is not null   ");
		List<Object> params = new ArrayList<Object>();
		TManager manager = (TManager) getSession().getAttribute("login");
		if (manager.getRoleid() == 0) {
			sql.append(" and a.account!='weixin' ");
		}else{
			sql.append(" and  a.account = ?  ");
			params.add(manager.getAccount());
			
//			if(manager.getRoleid()==1){
//				if("gz_0001".equals(manager.getAccount())){
//					sql.append(" and ( c.areacode not in ('020')  or (c.areacode='020' and a.account = ?) ) ");
//					params.add(manager.getAccount());
//				}else{
//					sql.append(" and  a.account = ?  ");
//					params.add(manager.getAccount());
//				}
//			}else{
//				getRequest().setAttribute("result", "无法使用该功能");
//				return "error";
//			}
		}
		if (StringUtils.isNotBlank(tdcodemd5)) {
			sql.append(" and b.tdcodemd5 =  ? ");
			params.add(tdcodemd5);
			getRequest().setAttribute("tdcodemd5", tdcodemd5);
		}
		if (StringUtils.isNotBlank(fluxcard)) {
			sql.append(" and b.fluxcard =  ? ");
			params.add(fluxcard);
			getRequest().setAttribute("fluxcard", fluxcard);
		}
		if (StringUtils.isNotBlank(orderid)) {
			sql.append(" and a.id =  ? ");
			params.add(orderid);
			getRequest().setAttribute("orderid", orderid);
		}
		
		if (StringUtils.isNotBlank(idcardState)) {
			if("N".equals(idcardState)){
				sql.append(" and b.idcard_state is null ");
			}else{
				sql.append(" and b.idcard_state =  ? ");
				params.add(idcardState);
			}
			getRequest().setAttribute("idcardState", idcardState);
		}
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and a.submit_time>? and a.submit_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by a.submit_time desc ");
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			if ("true".equals(getParameter("exportFlag"))) {
//				List<TFluxCard> list = jdbc.query(ApplicationContextTool.getDataSource(), sql.toString(), TFluxCard.class, params.toArray(), 0, 0);
//				getResponse().setContentType(
//						"application/vnd.ms-excel;charset=gb2312");
//				getResponse().setHeader(
//						"Content-Disposition",
//						"attachment;"
//								+ " filename="
//								+ new String("流量卡列表.xlsx".getBytes(),
//										"ISO-8859-1"));
//				ExcelTool.writeExcelByList("流量卡列表", list, new String[] { "mobile",
//						"areacodeStr", "stateStr", "submitTime" },
//						new String[] { "手机号", "地市", "状态", "操作时间" },
//						getResponse().getOutputStream(),
//						ExcelTemplate.ExcelVersion.XLSX);
				return null;
			}
			PageResult<TFluxIdcard> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TFluxIdcard.class);
			if (pageResult != null) {
				List<TFluxIdcard> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					TFluxIdcard po = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						po = list.get(i);
					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "showYuxianFluxIdcard";

	}
	
	
	
	/**
	 * 散户+微信订单+超级管理员订单 由特定的实名专员审核 
	 * @return
	 */
	public String showHouxuFluxIdcard() {
		String tdcodemd5 = getParameter("tdcodemd5Query");
		String fluxcard = getParameter("fluxcardQuery");
		String state = getParameter("stateQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.tdcodemd5,a.state,a.result_describe,a.submit_time ,b.mobile as fluxcard from t_flux_idcard a  left join t_bind_device_fluxcard b on a.tdcodemd5=b.sn where 1=1  ");
		
		List<Object> params = new ArrayList<Object>();
		TManager manager = (TManager) getSession().getAttribute("login");
		
		if (StringUtils.isNotBlank(tdcodemd5)) {
			sql.append(" and a.tdcodemd5 =  ? ");
			params.add(tdcodemd5);
			getRequest().setAttribute("tdcodemd5", tdcodemd5);
		}
		if (StringUtils.isNotBlank(fluxcard)) {
			sql.append(" and a.fluxcard =  ? ");
			params.add(fluxcard);
			getRequest().setAttribute("fluxcard", fluxcard);
		}
		if (StringUtils.isNotBlank(state)) {
			sql.append(" and a.state =  ? ");
			params.add(state);
			getRequest().setAttribute("state", state);
		}
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and a.submit_time>? and a.submit_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by a.submit_time2 desc ");
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			if ("true".equals(getParameter("exportFlag"))) {
//				List<TFluxCard> list = jdbc.query(ApplicationContextTool.getDataSource(), sql.toString(), TFluxCard.class, params.toArray(), 0, 0);
//				getResponse().setContentType(
//						"application/vnd.ms-excel;charset=gb2312");
//				getResponse().setHeader(
//						"Content-Disposition",
//						"attachment;"
//								+ " filename="
//								+ new String("流量卡列表.xlsx".getBytes(),
//										"ISO-8859-1"));
//				ExcelTool.writeExcelByList("流量卡列表", list, new String[] { "mobile",
//						"areacodeStr", "stateStr", "submitTime" },
//						new String[] { "手机号", "地市", "状态", "操作时间" },
//						getResponse().getOutputStream(),
//						ExcelTemplate.ExcelVersion.XLSX);
				return null;
			}
			PageResult<TFluxIdcard> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TFluxIdcard.class);
			if (pageResult != null) {
				List<TFluxIdcard> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					TFluxIdcard po = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						po = list.get(i);
					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "showHouxuFluxIdcard";

	}
	
	public String showFluxIdcardByTdcodemd5() throws IOException {
		String tdcodemd5 = getParameter("tdcodemd5");
		if(StringUtils.isBlank(tdcodemd5)){
			getRequest().setAttribute("result", "设备二维码未找到");
			return "error";
		}
		JDBC jdbc = ApplicationContextTool.getJdbc();
		FLuxIdcardBus fluxIdcardBus = new FLuxIdcardBus();
		TFluxIdcard po = fluxIdcardBus.queryByTdcodemd5(jdbc, tdcodemd5);
		if(po==null){
			getRequest().setAttribute("result", "设备二维码未找到");
			return "error";
		}
		getRequest().setAttribute("po", po);
		String operateType = getParameter("operateType");
		if("shenhe".equals(operateType)){
			return "updateHouxuFluxIdcard";
		}else if("detail".equals(operateType)){
			return "showHouxuFluxIdcardDetail";
		}
		return "error";
	}
	
	
	public String readFluxIdcardImage() throws IOException {
		String image = getParameter("image");
		File file = new File(ConfLoad.getProperty("IDCARD_PATH"),image);
		if(file.exists()){
			getResponse().getOutputStream().write(FileUtils.readFileToByteArray(file));
		}
		return null;
	}
	
	public String shenheFluxIdcard() throws IOException {
		String tdcodemd5 = getParameter("tdcodemd5");
		String fluxcard = getParameter("fluxcard");
		if(StringUtils.isBlank(tdcodemd5)){
			getRequest().setAttribute("result", "设备二维码未找到");
			return "error";
		}
		String state = getParameter("state");
		
		if(StringUtils.isBlank(state)){
			getRequest().setAttribute("result", "审核状态错误");
			return "error";
		}
		String resultDescribe = getParameter("resultDescribe");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		TManager manager = (TManager) getSession().getAttribute("login");
		params.put("tdcodemd5", tdcodemd5);
		params.put("fluxcard", fluxcard);
		params.put("state", state);
		params.put("resultDescribe", resultDescribe);
		params.put("account", manager.getAccount());
		FLuxIdcardBus fluxIdcardBus = new FLuxIdcardBus();
		Data dataMsg = fluxIdcardBus.shenheFluxIdcard(params);
		
		log.info(manager.getAccount()+"审核设备二维码["+tdcodemd5+"]结果:"+dataMsg.getMsg());
		if(!dataMsg.isState()){
			getRequest().setAttribute("result", dataMsg.getMsg());
			return "error";
		}
		return showHouxuFluxIdcard();
	}
	
	public String updateFluxIdcardState() throws IOException {
		String tdcodemd5 = getParameter("tdcodemd5");
		String fluxcard = getParameter("fluxcard");
		String fluxcardNew = StringUtils.trimToNull(getParameter("fluxcardNew"));
		if (StringUtils.isNotBlank(tdcodemd5)) {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			TManager manager = (TManager) getSession().getAttribute("login");
			try {
				if(fluxcardNew!=null&&!fluxcardNew.equals(fluxcard)){
					Connection conn = null;
					try {
						conn = ApplicationContextTool.getConnection();
						conn.setAutoCommit(false);
						int count = jdbc.update(conn,
								"update sp_tdcode set idcard_state = ?,fluxcard = ?  where tdcodemd5 = ? ",
								new Object[] {"S",fluxcardNew,tdcodemd5  });
						jdbc.update(
								conn,
								"update t_flux_card set mobile = ?  where mobile = ?",
								new Object[] { fluxcardNew,
										fluxcard });
						jdbc.update(
								conn,
								"update t_bind_device_fluxcard set mobile=? where mobile = ? ",
								new Object[] { fluxcardNew,fluxcard });
						conn.commit();
						conn.setAutoCommit(true);
						TDCodeBus bus = new TDCodeBus();
						LoginBus loginBus = new LoginBus();
						SpTdcode po = bus.queryTDCodeByMD5(jdbc, tdcodemd5);
						loginBus.syncTDCode(jdbc, po, "U", bus);
					}catch (Exception e) {
						try {
							if (conn != null)
								conn.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
						log.error("", e);
						PrintTool.print(getResponse(), "false|实名认证失败", null);
					} finally {
						if(conn!=null){
							try {
								conn.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						jdbc.closeAll();
						
					}
				}else{
					int count = jdbc.update(ApplicationContextTool.getDataSource(),
							"update sp_tdcode set idcard_state = ? where tdcodemd5 = ? ",
							new Object[] {"S",tdcodemd5  });
				}
				log.info(manager.getAccount()+"实名认证通过"+tdcodemd5+"["+fluxcard+"]");
				PrintTool.print(getResponse(), "true|实名认证通过", null);
				
				
			} catch (Exception e) {
				log.error("实名认证失败", e);
				PrintTool.print(getResponse(), "false|实名认证失败", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择审核的流量身份信息", null);
		}
		return null;
	}
	
	public String deleteFluxIdcard() throws IOException {
		String tdcodemd5 = getParameter("tdcodemd5");
		if (StringUtils.isNotBlank(tdcodemd5)) {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			FLuxIdcardBus fluxIdcardBus = new FLuxIdcardBus();
			TFluxIdcard po = fluxIdcardBus.queryByTdcodemd5(jdbc, tdcodemd5);
			if(po==null){
				PrintTool.print(getResponse(), "false|审核的流量身份信息未找到", null);
				return null;
			}
			StringBuffer sql = new StringBuffer();
			sql.append("delete from t_flux_idcard  where tdcodemd5 = ?  ");

			TManager manager = (TManager) getSession().getAttribute("login");
			
			try {
				int count = jdbc.update(ApplicationContextTool.getDataSource(),
						sql.toString(),
						new Object[] {po.getTdcodemd5()  });
				if (count > 0) {
					PrintTool.print(getResponse(), "true|删除成功", null);
				} else {
					PrintTool.print(getResponse(), "false|删除失败", null);
				}
				PrintTool.print(getResponse(), "true|删除成功", null);
			} catch (Exception e) {
				log.error("删除流量卡失败", e);
				PrintTool.print(getResponse(), "false|删除失败", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择审核的流量身份信息", null);
		}
		return null;
	}

}

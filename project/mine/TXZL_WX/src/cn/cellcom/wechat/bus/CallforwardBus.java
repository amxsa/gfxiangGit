package cn.cellcom.wechat.bus;


import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import java.util.Map;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import cn.cellcom.common.SequenceGenerator;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TCallforward;
import cn.cellcom.wechat.webservice.client.ClientAuthenticationHandler;
import cn.cellcom.wechat.webservice.service.InternalService;

public class CallforwardBus {
	private static final Log log = LogFactory.getLog(CallforwardBus.class);

	public TCallforward showCallforwardByID(Long ID) {
		if (ID == null)
			return null;
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		
		try {
			TCallforward po = jdbc.queryForObject(ApplicationContextTool.getDataSource(),
					"select  * from t_callforward where id =  ? ",
					TCallforward.class, new Object[] { ID });
			if (po != null)
				return po;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return null;
	}

	public TCallforward showCallforwardByType(String regNo, String type) {
		if (StringUtils.isBlank(regNo) || StringUtils.isBlank(type))
			return null;
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		TCallforward po;
		try {
			po = jdbc
					.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select  * from t_callforward where reg_no =  ?  and cf_type = ? ",
							TCallforward.class, new Object[] { regNo, type });
			if (po != null)
				return po;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public DataMsg sendHLR(Map<String, String> params, Login login) {
		
		DataMsg msg = new DataMsg();
		msg.setState(false);
		/**
		 * 呼转设置操作过程 
		 * 1、写入或者修改t_callforword(没则写，有则改，一个号码可以有多条，但每种状态只能有一条)
		 * 2、写入t_callforword_log
		 * 3、设置HRL(请求28,28在请求INAS)
		 * 4、获取请求结果，修改t_callforword的handle_status
		 * ，handle_desc等，再次写t_callforword_log
		 */
		String cfNumber = params.get("cfNumber");
		String type = params.get("type");
		String operateType = params.get("operateType");
		String operator = params.get("operator");
		if("S".equals(operateType))
			msg.setMsg("设置呼转失败");
		if("C".equals(operateType))
			msg.setMsg("取消呼转失败");
		try{
			TCallforward po = modifyCallforward(cfNumber, login, type, operateType,
					operator);
			po = sendHLR(po, operator);
			if("0".equals(po.getResult())){
				log.info("设置或取消呼转>>>>>>>>"+po.getHandleStatus());
				if("S".equals(operateType))
					msg.setMsg("设置呼转成功");
				if("C".equals(operateType))
					msg.setMsg("取消呼转成功");
				msg.setState(true);
				return msg;
			}
		}catch(Exception e){
			log.error("",e);
			return msg;
		}
		return msg;
	}

	public TCallforward modifyCallforward(String cfNumber, Login login,
			String type, String operateType, String operator) {
		TCallforward po = getCallforwardPo(cfNumber, login, type, operateType,
				operator);
		int count = 0;
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			Long seqid = po.getSeqid();
			Timestamp timeStamp = DateTool.getTimestamp(null);
			if (po.getId() == null) {

				count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_callforward(reg_no,cf_type,cf_number,seqid,handle_status,operate_type,handle_desc,submit_time ,operate_time) values(?,?,?,?,?,?,?,?,?)",
								new Object[] { login.getNumber(), type,
										cfNumber, seqid, "N", operateType, "",
										timeStamp, timeStamp });
			} else {
				count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"update t_callforward set cf_type = ? ,cf_number= ?,seqid = ? ,handle_status=?,operate_type=?,handle_desc=?,submit_time = ? ,operate_time = ?  where id = ? ",
								new Object[] { type, cfNumber, seqid, "N",
										operateType, "", timeStamp, timeStamp,
										po.getId() });
			}
			if (count > 0) {
				po.setOperator(operator);
				if (po != null) {
					addCallforwardLog(po, null);
				}
				return po;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("t_callforward写入失败", e);
		}
		return null;
	}

	/**
	 * 
	 * @param cfNumber
	 *            呼转的号码
	 * @param loginForm
	 *            登录号码对象
	 * @param type
	 *            设置类型 U-无条件呼转,B-遇忙呼转,N-无应答呼转,D-不可及呼转
	 * @param operateType
	 *            操作类型 S:设置,C:取消
	 * @param operater
	 *            操作人
	 * @return
	 */
	private TCallforward getCallforwardPo(String cfNumber, Login login,
			String type, String operateType, String operater) {
		TCallforward po = showCallforwardByType(login.getNumber(), type);
		if (po == null) {
			po = new TCallforward();
		}
		po.setRegNo(login.getNumber());
		po.setCfType(type);
		po.setCfNumber(UserUtil.addPostCode(cfNumber, login.getAreacode()));
		// 生成唯一标识
		po.setSeqid(Long.parseLong(SequenceGenerator.generate()));
		po.setHandleStatus("N");
		po.setOperateType(operateType);
		po.setHandleDesc("");
		Date date = new Date();
		po.setSubmitTime(date);
		po.setOperateTime(date);
		return po;
	}

	public TCallforward sendHLR(TCallforward callforward, String operator) {
		callforward.setHandleStatus("S");
		callforward.setOperateTime(new Date());
		String result = send2Internal(String.valueOf(callforward.getSeqid()),
				callforward.getRegNo(), callforward.getCfNumber(),
				callforward.getCfType(), operator);
		if ("0".equals(result)) {
			callforward.setResult(result);
		} else {
			callforward.setHandleStatus("F");
			callforward.setHandleDesc("发送请求失败");
			callforward.setOperateTime(new Date());
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			try {
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update t_callforward set handle_status=?,handle_desc=?,operate_time = ?  where seqid= ? ",
						new Object[] { callforward.getHandleStatus(),
								callforward.getHandleDesc(),
								DateTool.getTimestamp(null),
								callforward.getSeqid()});
				addCallforwardLog(callforward, jdbc);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("设置HLR失败后，修改t_callforward失败", e);
			}
			callforward.setResult("-1");
		}

		return callforward;
	}

	private void addCallforwardLog(TCallforward callforward, JDBC jdbc) {
		if (jdbc == null)
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"insert into t_callforward_log(reg_no,cf_type,cf_number,seqid,handle_status,operate_type,handle_desc,submit_time ,operate_time,operator) values(?,?,?,?,?,?,?,?,?,?)",
					new Object[] {
							callforward.getRegNo(),
							callforward.getCfType(),
							callforward.getCfNumber(),
							callforward.getSeqid(),
							callforward.getHandleStatus(),
							callforward.getOperateType(),
							callforward.getHandleDesc(),
							DateTool.getTimestamp(callforward.getSubmitTime()),
							DateTool.getTimestamp(callforward.getOperateTime()),
							callforward.getOperator() });
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("写入t_callforward_log异常", e);
		}
	}

	/**
	 * 该请求会添加soap header
	 * 
	 * @param seqid
	 * @param regNo
	 * @param cfNumber
	 * @param characterCode
	 * @param operator
	 * @return
	 */
	public String send2Internal(String seqid, String regNo, String cfNumber,
			String characterCode, String operator) {

		String result = "-1";
		log.info("id:" + seqid + ",regNo:" + regNo + ",cfNumber:" + cfNumber
				+ ",characterCode:" + characterCode + ",operator:" + operator);
		log.info("注册号码:" + regNo + "请求设置HLR............................");

		Service model = new ObjectServiceFactory().create(InternalService.class);
		XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
		try {
			InternalService service = (InternalService)factory.create(model,Env.INTERNAL_ADDRESS);
			Client client = ((XFireProxy) Proxy.getInvocationHandler(service)).getClient(); 
			
			HttpClientParams params = new HttpClientParams();
			params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
			params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, 5000L); 
			client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);
			client.addOutHandler(new ClientAuthenticationHandler(operator));
			result = service.InternalCallforwardAPI(seqid, regNo, cfNumber, characterCode);
		} catch (MalformedURLException e) {
			log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		log.info("注册号码:"+regNo+"请求设置HLR结果："+result);
		return result;

	}

	private String checkRegNoCallforwardHLR(Login login) {
		 boolean bool  = UserUtil.isJCOrXXUser(login.getNumber(),login.getSetid(),login.getUsage(),login.getServNbr());
		if (!bool
				|| PatternTool.checkStr(login.getNumber(), Env.C_PATTERN,
						"您不是通信助理C网用户") != null) {
			return "您无法设置或取消呼转";
		}
		return null;
	}

	public String send2Internal2(String seqid, String regNo, String cfNumber,
			String characterCode, String operator) {
		long start = System.currentTimeMillis();
		String result = "-1";
		log.info("id:" + seqid + ",regNo:" + regNo + ",cfNumber:" + cfNumber
				+ ",characterCode:" + characterCode + ",operator:" + operator);
		log.info("注册号码:" + regNo + "请求设置HLR............................");
		Service serviceModel = new AnnotationServiceFactory()
				.create(InternalService.class);

		InternalService client;
		try {
			client = (InternalService) new XFireProxyFactory().create(
					serviceModel, Env.INTERNAL_ADDRESS);
			result = client.InternalCallforwardAPI(seqid, regNo, cfNumber,
					characterCode);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.error("", e);

		}
		log.info("注册号码:" + regNo + "请求设置HLR时长："
				+ (System.currentTimeMillis() - start) + "ms");
		log.info("注册号码:" + regNo + "请求设置HLR结果：" + result);
		return result;
	}

	public static void main(String[] args) {
		String seqid = "201212281818511001";
		String regNo = "15322311659";
		String cfNumber = "13632445442";
		/**
		 * U:无条件呼转 B:遇忙呼转 N:无应答呼转 D:不可及呼转
		 */
		String characterCode = "B";
		String operator = "15322311659";
		String result = new CallforwardBus().send2Internal2(seqid, regNo,
				cfNumber, characterCode, operator);
		System.out.println(">>>>>>>>>>>>" + result);
	}
}

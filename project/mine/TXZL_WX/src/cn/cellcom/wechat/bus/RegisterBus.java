package cn.cellcom.wechat.bus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;

import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TRegister;

public class RegisterBus {
	public TRegister queryByRegNo(JDBC jdbc, String regNo, String column) {
		if (StringUtils.isBlank(regNo)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			if (column == null) {
				sql.append("*");
			} else {
				sql.append(column).append(" ");
			}
			sql.append(" from t_register where number = ?");
			TRegister po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(), sql.toString(),
					TRegister.class, new String[] { regNo });
			return po;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Login setLogin(TRegister po, String openid) {
		if (po != null) {
			Login login = new Login();
			login.setNumber(po.getNumber());
			login.setAreacode(po.getAreacode());
			login.setName(po.getName());
			login.setServNbr(po.getServNbr());
			login.setSetid(po.getSetid());
			login.setWechatNo(openid);
			login.setUsage(po.getUsage());
			login.setStatus(po.getStatus());
			return login;
		}
		return null;
	}

	public DataMsg saveRegister(String ip, String regNo) {
		DataMsg msg = new DataMsg(false,"注册失败");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		TRegister po = queryByRegNo(jdbc, regNo, null);
		Date date = new Date();
		if (po == null) {
			po = new TRegister();
			po.setNumber(regNo);
			po.setSetid(new Long(51));

			po.setName(regNo);

			String areacode = UserUtil.getAreaCode(regNo);
			po.setAreacode(areacode == null ? "" : areacode);
			po.setType(1);
			po.setStatus("Y");
			po.setRegTime(new Date());
			po.setLan("M");
			po.setUsage(2);
			po.setIsFree(1);
			po.setSaleDepart("WX");
			po.setPassword(Env.PASSWORD);
			po.setSetinfo("DCP");
			// 插入数据
			StringBuilder SQL2 = new StringBuilder();
			SQL2.append("insert into t_register(password,name,sale_depart,type,setid,status,reg_time,usage,lan,is_free,setinfo,areacode,number) values (?, ?, ?, ?, ?, ?, getdate(), ?, ?, ?, ?, ?, ?)");
			Object[] param = { po.getPassword(), po.getName(),
					po.getSaleDepart(), po.getType(), po.getSetid(),
					po.getStatus(), po.getUsage(), po.getLan(), po.getIsFree(),
					po.getSetinfo(), po.getAreacode(), po.getNumber() };
			try {
				jdbc.update(ApplicationContextTool.getDataSource(),
						SQL2.toString(), param);
			} catch (Exception e) {
				e.printStackTrace();
				return msg;
			}

		} else {
			if ("D".equals(po.getStatus())) {
				if("WX".equals(po.getDepart())){
					msg.setMsg("亲，您已经体验过通信助理，赶紧开通享受更多服务");
					return msg;
				}
				po.setRegTime(new Date());
				po.setCancelTime(null);
				po.setSetid(51l);
				po.setIsFree(1);
				po.setServNbr(null);
				po.setSaleDepart("WX");
				po.setStatus("Y");
				po.setSetinfo("DCP");
				po.setUsage(2);
				try {
					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"update t_register set reg_time=getdate(),cancel_time=null,setid=51,is_free=1,sale_depart='WX',status='Y',setinfo='DCP',usage=2 where number = ?",
							new String[] { po.getNumber() });
				} catch (Exception e) {
					e.printStackTrace();
					return msg;
				}

			}else if("B".equals(po.getStatus())){
				msg.setMsg("该号码暂停使用");
				return msg;
			}else{
				msg.setMsg("该号码已存在");
				return msg;
			}
		}
		// 设置HLR
		StringBuilder SQL = new StringBuilder();
		SQL.append("insert into t_hlr_request(number,operate_type,state,submit_time,update_time,from_part)  values(");
		SQL.append("?,'S','N',getdate(),null,'WX')");
		try {
			jdbc.update(ApplicationContextTool.getDataSource(), SQL.toString(),
					new String[] { po.getNumber() });
			OperateLogBus operate = new OperateLogBus();
			operate.addOperateLog(jdbc, ip, regNo, regNo, TRegister.class, "A",
					"微信开通通信助理");
			msg.setState(true);
			msg.setMsg("注册成功");
			List<TRegister> list = new ArrayList<TRegister>();
			list.add(po);
			msg.setList(list);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return msg;
		}

	}
}

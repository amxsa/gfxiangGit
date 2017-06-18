package cn.cellcom.czt.bus;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.DataMsg;
import cn.cellcom.czt.po.OrderSettleAccounts;
import cn.cellcom.czt.po.TOrder;
import cn.cellcom.web.spring.ApplicationContextTool;

public class SettleAccountsManagerBus {
	public OrderSettleAccounts querySettleOrderById(JDBC jdbc, String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			OrderSettleAccounts po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select a.account as account, a.id as orderId,a.submit_time as submitTime,a.remark as remark,a.unit_price as originalUnitPrice,a.receive_telephone as telephone ,a.configure,a.unit_price,a.count,a.total as originalTotalPrice,a.state as orderState,b.areacode,a.settle_accounts_flag as state,c.id as id from t_order a left join t_manager b on a.account = b.account left join t_order_settle_accounts c on a.account = c.account where a.id =  ? ", OrderSettleAccounts.class,
					new String[] { id });
			return po;
		} catch (Exception e) {
			throw new RuntimeException("t_order_settle_accounts查询异常", e);
		}
		
	}

	public DataMsg launchSettleAccounts(Map<String, String> params, JDBC jdbc) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		int unitPrice = Integer.parseInt(params.get("unitPrice"));
		int count = Integer.parseInt(params.get("count"));
		try {
			String originator = new String(params.get("originator").getBytes("iso8859-1"), "utf-8");
			String projectName = new String(params.get("projectName").getBytes("iso8859-1"), "utf-8");
			
			OrderSettleAccounts osa = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_order_settle_accounts where order_id=?", OrderSettleAccounts.class, new String[]{params.get("orderId")});
			if (osa!=null) {//已存在
				jdbc.update(ApplicationContextTool.getDataSource(), 
						"update t_order_settle_accounts set account=?,originator=?,project_name=?,unit_price=?,total=?,contract_no=?,invoice_no=?,submit_time=?,state=? where order_id=?",
						new String[]{params.get("account"),originator,projectName,String.valueOf(unitPrice),String.valueOf(unitPrice*count),params.get("contractNo")
					,params.get("invoiceNo"),DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss"),"B",params.get("orderId")});
			}else{
				jdbc.update(ApplicationContextTool.getDataSource(), 
						"insert into t_order_settle_accounts(order_id,account,originator,project_name,unit_price,total,contract_no,invoice_no,submit_time,state)values(?,?,?,?,?,?,?,?,?,?)",
						new String[]{params.get("orderId"),params.get("account"),originator,projectName,String.valueOf(unitPrice),String.valueOf(unitPrice*count),params.get("contractNo")
					,params.get("invoiceNo"),DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss"),"B"});
			}
			//更新订单表状态
			jdbc.update(ApplicationContextTool.getDataSource(), "update t_order set settle_accounts_flag=? where id=?",new String[]{ "B",params.get("orderId")});
			return new DataMsg("true", "成功发起结算，订单将等待审核");
		} catch (Exception e) {
			throw new RuntimeException("t_order_settle_accounts添加数据异常", e);
		}
	}
	
	public OrderSettleAccounts queryVerifySettleOrderById(JDBC jdbc, String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			OrderSettleAccounts po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select a.account as account, a.id as orderId,a.submit_time as orderTime," +
					"a.remark as remark,a.receive_telephone as telephone ,a.configure,a.unit_price," +
					"a.count,a.total as originalTotalPrice,a.state as orderState,b.areacode,a.settle_accounts_flag as state," +
					"c.id as id,c.originator as originator,c.submit_time as submitTime,c.contract_no,c.invoice_no,c.unit_price as unitPrice,c.project_name " +
					"from t_order a left join t_manager b " +
					"on a.account = b.account left join t_order_settle_accounts c on a.id=c.order_id  where a.id =  ? ",
					OrderSettleAccounts.class,new String[] { id });
			return po;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("t_order_settle_accounts查询异常", e);
		}
	}

	public DataMsg disVerify(JDBC jdbc, String orderId, String remark, String verifyName) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			remark = new String(remark.getBytes("iso8859-1"), "utf-8");
			verifyName = new String(verifyName.getBytes("iso8859-1"), "utf-8");
			TOrder order = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_order where id=?", TOrder.class, new String[]{orderId});
			if (order==null) {
				return new DataMsg("false","审核失败，该订单号不存在");
			}
			jdbc.update(ApplicationContextTool.getDataSource(), 
					"update t_order set settle_accounts_flag='F' where id=?", new String[]{orderId});
			jdbc.update(ApplicationContextTool.getDataSource(), 
					"update t_order_settle_accounts set state=?,result_reason=?,verify_name=? where order_id=?", new String[]{"F",remark,verifyName,orderId});
			return new DataMsg("true", "操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public DataMsg verify(JDBC jdbc, String orderId, String verifyName) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			verifyName = new String(verifyName.getBytes("iso8859-1"), "utf-8");
			TOrder order = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_order where id=?", TOrder.class, new String[]{orderId});
			if (order==null) {
				return new DataMsg("false","审核失败，该订单号不存在");
			}
			jdbc.update(ApplicationContextTool.getDataSource(), 
					"update t_order set settle_accounts_flag='Y' where id=?", new String[]{orderId});
			jdbc.update(ApplicationContextTool.getDataSource(), 
					"update t_order_settle_accounts set state=?,verify_time=?,verify_name=? where order_id=?", 
					new String[]{"Y",DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss"),verifyName,orderId});
			return new DataMsg("true", "操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//获取审核详情
	public OrderSettleAccounts getVerifyDetail(JDBC jdbc, String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			OrderSettleAccounts po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select a.account as account, a.id as orderId,a.submit_time as orderTime," +
					"a.remark as remark,a.receive_telephone as telephone ,a.configure,a.unit_price," +
					"a.count,a.total as originalTotalPrice,a.state as orderState,b.areacode,a.settle_accounts_flag as state," +
					"c.id as id,c.originator as originator,c.submit_time as submitTime,c.contract_no,c.invoice_no,c.unit_price,c.project_name,c.verify_name,c.verify_time " +
					"from t_order a left join t_manager b " +
					"on a.account = b.account left join t_order_settle_accounts c on a.id=c.order_id  where a.id =? ",
					OrderSettleAccounts.class,new String[] { orderId });
			return po;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("t_order_settle_accounts查询异常", e);
		}
	}

	public OrderSettleAccounts querySettleAccountsDetailById(JDBC jdbc,
			String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			OrderSettleAccounts po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select a.account as account, a.id as orderId,a.submit_time as orderTime," +
					"a.remark as remark,a.receive_telephone as telephone ,a.configure,a.unit_price as originalUnitPrice," +
					"a.count,a.total as originalTotalPrice,a.state as orderState,b.areacode,a.settle_accounts_flag as state," +
					"c.id as id,c.originator as originator,c.submit_time as submitTime,c.contract_no,c.invoice_no,c.unit_price,c.project_name,c.verify_name,c.verify_time " +
					",c.total from t_order a left join t_manager b " +
					"on a.account = b.account left join t_order_settle_accounts c on a.id=c.order_id  where a.id =? ",
					OrderSettleAccounts.class,new String[] { id });
			return po;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("t_order_settle_accounts查询异常", e);
		}
	}
}

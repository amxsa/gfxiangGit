package cn.cellcom.szba.biz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TArguments;
import cn.open.db.JDBC;

public class TArgumentsBiz {
	private static Log log = LogFactory.getLog(TArgumentsBiz.class);
	
	//查询flag值为1的数据
	private static String SQL_QUERY_ARGUMENTS="select * from t_arguments where flag=1";
	
	//将flag值全部设置为0
	private static String SQL_UPDATE_ARGUMENTS="update  t_arguments set flag=0 where service=?";
	
	public static List<TArguments> checkModify(){
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		List<TArguments> list = null;
		try {
			list=jdbc.query(Env.DS,SQL_QUERY_ARGUMENTS,TArguments.class);
		} catch (Exception e) {
			log.error("",e);
		}finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public static void updateArguments(String service){
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		try {
			jdbc.update(Env.DS, SQL_UPDATE_ARGUMENTS,service);
		} catch (Exception e) {	
			log.error("",e);
		}finally{
			jdbc.closeAll();
		}
	}
}

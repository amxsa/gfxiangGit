package cn.cellcom.demo.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.DB.JDBC.PROCEDURE_PARAMTER_TYPE;
import cn.cellcom.common.DB.JdbcResultSet;
import cn.cellcom.common.DB.ProcedureParameter;
import cn.cellcom.common.DB.ProcedureResult;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.demo.pojo.TCallRecord;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class DemoAction extends Struts2Base {
	public TCallRecord callrecord = new TCallRecord() ;
	public TCallRecord getCallrecord() {
		return callrecord;
	}
	public void setCallrecord(TCallRecord callrecord) {
		this.callrecord = callrecord;
	}
	public String queryJdbcOraclePojo(){
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			List<TCallRecord> list1 = jdbc.query(ApplicationContextTool.getDataSource(), "SELECT A.* FROM T_CALL_RECORD A LEFT JOIN T_NONWORKING_REC B ON A.ID = B.ID  WHERE 1=1 ORDER BY A.STARTTIME DESC", TCallRecord.class, null, 0, 0);
			getRequest().setAttribute("list1", list1);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "queryJdbcOraclePojo";
	}
	public String queryJdbcOracleMap(){
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			List<Map<String, Object>> list1 = jdbc.query(ApplicationContextTool.getDataSource(), "SELECT A.ANUM,A.BNUM,A.STARTTIME,B.DEELNO FROM T_CALL_RECORD A LEFT JOIN T_NONWORKING_REC B ON A.ID = B.ID  WHERE 1=1 ORDER BY A.STARTTIME DESC", null, 0, 0);
			getRequest().setAttribute("list1", list1);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "queryJdbcOracleMap";
	}
	
	public String queryJdbcOraclePojoPage(){
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"),2,getRequest().getRequestURI());
	
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT A.ANUM,A.BNUM,A.STARTTIME,B.DEELNO FROM T_CALL_RECORD A LEFT JOIN T_NONWORKING_REC B ON A.ID = B.ID  WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		String starttime = getParameter("starttime");
		String endtime = getParameter("endtime");
		if(StringUtils.isNotBlank(starttime)){
			SQL.append(" AND STARTTIME>TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') ");
			params.add(starttime);
			getRequest().setAttribute("starttime", starttime);
		}
		if(StringUtils.isNotBlank(endtime)){
			SQL.append(" AND STARTTIME<TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') ");
			params.add(endtime);
			getRequest().setAttribute("endtime", endtime);
		}
		SQL.append("ORDER BY STARTTIME DESC ");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TCallRecord> pageResult = pageBus.queryPageDataOracle(jdbc, ApplicationContextTool.getDataSource(), page, SQL.toString(), params.toArray(), TCallRecord.class, "T");
			if(pageResult!=null){
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "queryJdbcOraclePojoPage";
	}
	
	public String queryJdbcOracleProc(){
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			List<ProcedureParameter> procedureParameterList = new ArrayList<ProcedureParameter>();
			procedureParameterList.add(new ProcedureParameter("18925008300",PROCEDURE_PARAMTER_TYPE.IN) );
			procedureParameterList.add(new ProcedureParameter(DateTool.getTimestamp(DateTool.formateString2Time("2014-01-01", "yyyy-MM-dd")),PROCEDURE_PARAMTER_TYPE.IN) );
			procedureParameterList.add(new ProcedureParameter(DateTool.getTimestamp(null),PROCEDURE_PARAMTER_TYPE.IN) );
			procedureParameterList.add(new ProcedureParameter(oracle.jdbc.OracleTypes.CURSOR,PROCEDURE_PARAMTER_TYPE.OUT) );
			ProcedureResult result = jdbc.execProcedureQueryOracle(ApplicationContextTool.getDataSource(), "SP_TEST", procedureParameterList);
			List<JdbcResultSet> list = result.getResultList();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					JdbcResultSet resultSet  = list.get(i);
					List<Object[]> datas = resultSet.getDatas();
					getRequest().setAttribute("data", datas);
					List<String> fields = resultSet.getFields();
					for(int j=0;j<datas.size();j++){
						Object[] objs = datas.get(j);
						for(int n=0;n<objs.length;n++){
							System.out.print(fields.get(n)+"="+objs[n]+",");
						}
						System.out.println();
					}
				}
			}
	
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "queryJdbcOracleProc";
	}
	
	public void autocomplete() throws IOException{
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("a1");
		PrintTool.print(getResponse(), list.toArray(new String[]{}), "text");
		
	}
	
	public static void main(String[] args) {
		String sql="SELECT A    From   T_FROM T_CALL_RECORD WEHRE 1=1 ORDER BY STARTTIME DESC";
		int a = PatternTool.indexOfFirst(sql, "\\s+(?i)from");
		StringBuffer str = new StringBuffer("SELECT COUNT(1) AS CNT");
		str.append(StringUtils.substring(sql, a));
		System.out.println(str.toString());
	}
}

package cn.cellcom.common.file.excel;

import java.beans.IntrospectionException;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.SQLException;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.data.ArrayTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.DB.ConnConf;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.DB.JdbcResultSet;
import cn.cellcom.common.bean.JavaBean;



public class ExcelTool {
	private static Log log = LogFactory.getLog(ExcelTool.class);

	public static void main(String[] args) throws FileNotFoundException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		// List list = new ArrayList();
		// for (int i = 0; i < 22; i++) {
		// String s1 = String.valueOf(CommonUtil.randomLong(10L, 100L));
		// String s2 = String.valueOf(CommonUtil.randomLong(10L, 100L));
		// String s3 = String.valueOf(CommonUtil.randomLong(10L, 100L));
		// String s4 = String.valueOf(CommonUtil.randomLong(10L, 100L));
		// String s5 = String.valueOf(CommonUtil.randomLong(10L, 100L));
		// String s6 = String.valueOf(CommonUtil.randomLong(10L, 100L));
		// String s7 = String.valueOf(CommonUtil.randomLong(10L, 100L));
		// list.add(new MyPoint(s1, s2, s3, s4, s5, s6, s7));
		// }
		// OutputStream os = new FileOutputStream(new File("C:/result.xlsx"));
		// Map cons = new HashMap();
		// cons.put("abc", "广州华工信元通信技术有限公司");
		// cons.put("dep", "技术开发部");
		// cons.put("date", DateUtil.getYYYYMMDD(new Date()));
		// fillExcelByList(new File("c:/m.xlsx"), list, null, os, 1, 2, 1, 1, 1,
		// 1, 0, cons, ExcelTemplate.StyleType.ROW);
		ConnConf conf = new ConnConf("com.sybase.jdbc3.jdbc.SybDataSource",
				"sa", "cellcom",
				"jdbc:sybase:Tds:192.168.7.224:5000/SZTXZL?CHARSET=cp936");
		JDBC jdbc = new JDBC(conf);
		// jdbc.execProcedureUpdate(jdbc.getConnection(), "sp_test", new
		// Object[] {
		// "18925008300", "20130906" }, false);
		// List<TAccountLog> list2 = jdbc
		// .execSQLQuery(
		// jdbc.getConnection(),
		// "select * from t_account_log where reg_no=? and login_time>?",
		// TAccountLog.class, new Object[] { "18925008300",
		// "2012-08-21" });
		// writeExcelByList("test", list2, new String[]{"regNo","loginTime"},
		// new String[]{"注册号码","注册时间"}, new FileOutputStream(new
		// File("E:/a.xls")), ExcelTemplate.ExcelVersion.XLSX);
		JdbcResultSet set = jdbc.query(jdbc.getConnection(),
				"select * from t_account_log where reg_no=? and login_time>?",
				new Object[] { "18925008300", "2012-08-21" });
		List fields = new ArrayList<String>();
		fields.add("regNo");
		fields.add("loginTime");
		set.setFields(fields);
		writerExcel("teset2", set, ExcelTemplate.ExcelVersion.XLS,
				new FileOutputStream(new File("E:/b.xls")));
	}

	public static void writerExcel(String sheetName, JdbcResultSet result,
			ExcelTemplate.ExcelVersion version, OutputStream os) {
		if(ArrayTool.isEmpty(result.getFields())){
			throw new RuntimeException("字段列表为空或者导入的数据为空");
		}
		ExcelTemplate myExcelTemplate = ExcelTemplate.getInstance();
		myExcelTemplate.createFile(sheetName, version);
		myExcelTemplate.initTemplate(0, 0, 12.0F);
		try {
			myExcelTemplate.createNewRow();
			for (String filed : result.getFields()) {
				myExcelTemplate.createCell(filed, null);
			}
			if(!ArrayTool.isEmpty(result.getDatas())){
				for (Object[] objectArray : result.getDatas()) {
					myExcelTemplate.createNewRow();
					for (Object returnVal : objectArray) {
						if (returnVal == null) {
							myExcelTemplate.createCell("", null);
						} else {
							Class returnType = returnVal.getClass();
							if (returnType == String.class)
								myExcelTemplate.createCell(String.valueOf(returnVal), null);
							else if ((returnType == Integer.TYPE)
									|| (returnType == Integer.class))
								myExcelTemplate.createCell(Integer.parseInt(String
										.valueOf(returnVal)), null);
							else if ((returnType == Boolean.TYPE)
									|| (returnType == Boolean.class))
								myExcelTemplate.createCell(Boolean.valueOf(
										String.valueOf(returnVal)).booleanValue(), null);
							else if ((returnType == Double.TYPE)
									|| (returnType == Double.class))
								myExcelTemplate.createCell(Double.valueOf(
										String.valueOf(returnVal)).doubleValue(), null);
							else if (returnType == Date.class
									|| returnType == java.sql.Timestamp.class
									|| returnType == java.sql.Date.class)
								myExcelTemplate.createCell(DateTool.formateTime2String(
										(Date) returnVal, "yyyy-MM-dd HH:mm:ss"), null);
							else {
								myExcelTemplate.createCell(String.valueOf(returnVal), null);
							}
						}
					}
				}
			}else{
				log.info("导入的数据为空");
			}
			myExcelTemplate.write2Stream(os);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e.getMessage());
		}finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void writeTitle(ExcelTemplate instance, String[] titles) {
		instance.createNewRow();
		for (String title : titles)
			instance.createCell(title, null);
	}

	public static <T> void writeExcelByList(String sheetName, List<T> list,
			String[] fields, String[] titles, OutputStream os,
			ExcelTemplate.ExcelVersion version) {
		if (ArrayTool.isEmpty(list)) {
			throw new RuntimeException("数据集为空(blank)");
		}
		if ((fields != null) && (titles != null)
				&& (fields.length != titles.length)) {
			throw new RuntimeException("字段列表与其要显示的名称列表不匹配");
		}

		ExcelTemplate myExcelTemplate = ExcelTemplate.getInstance();
		myExcelTemplate.createFile(sheetName, version);
		myExcelTemplate.initTemplate(0, 0, 12.0F);
		try {
			if (ArrayTool.isEmpty(fields)) {
				fields = JavaBean.getFileds(list.get(0).getClass());
			}
			if (ArrayTool.isEmpty(titles))
				writeTitle(myExcelTemplate, fields);
			else {
				writeTitle(myExcelTemplate, titles);
			}
			for (Object t : list) {
				myExcelTemplate.createNewRow();
				for (String field : fields) {
					String getter = JavaBean.getGetterByFieldName(field);
					Method method = JavaBean.getGetter(t.getClass(), getter);
					if (method != null) {
						setCellVal(t, method, myExcelTemplate, null);
					} else {
						myExcelTemplate.createCell("", null);
					}
				}
			}
			myExcelTemplate.write2Stream(os);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static <T> void fillExcelByList(File template, List<T> list,
			String[] fields, OutputStream os, int initRowIndex,
			int initColIndex, int defaultStyleRowIndex,
			int defaultStyleColIndex, int copyStyleRowIndex,
			int copyStyleColIndex, int sheetIndex,
			Map<String, String> constants, ExcelTemplate.StyleType styleType) {
		if (ArrayTool.isEmpty(list)) {
			throw new RuntimeException("数据集为空(blank)");
		}
		ExcelTemplate myExcelTemplate = ExcelTemplate.getInstance();
		myExcelTemplate.readTemplateByFile(template);
		myExcelTemplate.initTemplate(initRowIndex, initColIndex, sheetIndex);
		myExcelTemplate.initDefaultStyle(defaultStyleRowIndex,
				defaultStyleColIndex);
		myExcelTemplate.initStyles(copyStyleRowIndex, copyStyleColIndex);
		try {
			if (ArrayTool.isEmpty(fields)) {
				fields = JavaBean.getFileds(list.get(0).getClass());
			}
			for (Object t : list) {
				for (String field : fields) {
					String getter = JavaBean.getGetterByFieldName(field);
					Method method = JavaBean.getGetter(t.getClass(), getter);
					if (method != null) {
						setCellVal(t, method, myExcelTemplate, styleType);
					} else {
						myExcelTemplate.createCell("", styleType);
					}
				}
				myExcelTemplate.doBreak();
			}
		} catch (IntrospectionException e) {
			log.error("", e);
			throw new RuntimeException(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error("", e);
			throw new RuntimeException(e.getMessage());
		} catch (InvocationTargetException e) {
			log.error("", e);
			throw new RuntimeException(e.getMessage());
		}
		myExcelTemplate.replaceFinalData(constants);
		myExcelTemplate.write2Stream(os);
	}

	private static void setCellVal(Object t, Method method,
			ExcelTemplate myExcelTemplate, ExcelTemplate.StyleType styleType)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Object returnVal = method.invoke(t, new Object[0]);
		if (returnVal != null) {
			Class returnType = returnVal.getClass();
			if (returnType == String.class)
				myExcelTemplate.createCell(String.valueOf(returnVal), null);
			else if ((returnType == Integer.TYPE)
					|| (returnType == Integer.class))
				myExcelTemplate.createCell(Integer.parseInt(String
						.valueOf(returnVal)), null);
			else if ((returnType == Boolean.TYPE)
					|| (returnType == Boolean.class))
				myExcelTemplate.createCell(Boolean.valueOf(
						String.valueOf(returnVal)).booleanValue(), null);
			else if ((returnType == Double.TYPE)
					|| (returnType == Double.class))
				myExcelTemplate.createCell(Double.valueOf(
						String.valueOf(returnVal)).doubleValue(), null);
			else if (returnType == Date.class
					|| returnType == java.sql.Timestamp.class
					|| returnType == java.sql.Date.class)
				myExcelTemplate.createCell(DateTool.formateTime2String(
						(Date) returnVal, "yyyy-MM-dd HH:mm:ss"), null);
			else {
				myExcelTemplate.createCell(String.valueOf(returnVal), null);
			}
		} else {
			myExcelTemplate.createCell("", null);
		}
	}
}
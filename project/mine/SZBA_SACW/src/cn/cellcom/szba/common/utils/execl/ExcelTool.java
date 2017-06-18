package cn.cellcom.szba.common.utils.execl;

import java.beans.IntrospectionException;
import java.io.File;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import java.util.Date;


import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateTool;
import cn.open.db.JdbcResultSet;
import cn.open.reflect.JavaBase;
import cn.open.util.ArrayUtil;

public class ExcelTool {
	private static Log log = LogFactory.getLog(ExcelTool.class);

	public static void writerExcel(String sheetName, JdbcResultSet result,
			ExcelTemplate.ExcelVersion version, OutputStream os) {
		if (ArrayUtil.isEmpty(result.getFields())) {
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
			if (!ArrayUtil.isEmpty(result.getDatas())) {
				for (Object[] objectArray : result.getDatas()) {
					myExcelTemplate.createNewRow();
					for (Object returnVal : objectArray) {
						if (returnVal == null) {
							myExcelTemplate.createCell("", null);
						} else {
							Class returnType = returnVal.getClass();
							if (returnType == String.class)
								myExcelTemplate.createCell(
										String.valueOf(returnVal), null);
							else if ((returnType == Integer.TYPE)
									|| (returnType == Integer.class))
								myExcelTemplate.createCell(Integer
										.parseInt(String.valueOf(returnVal)),
										null);
							else if ((returnType == Boolean.TYPE)
									|| (returnType == Boolean.class))
								myExcelTemplate.createCell(
										Boolean.valueOf(
												String.valueOf(returnVal))
												.booleanValue(), null);
							else if ((returnType == Double.TYPE)
									|| (returnType == Double.class))
								myExcelTemplate.createCell(
										Double.valueOf(
												String.valueOf(returnVal))
												.doubleValue(), null);
							else if (returnType == Date.class
									|| returnType == java.sql.Timestamp.class
									|| returnType == java.sql.Date.class)
								myExcelTemplate.createCell(DateTool
										.formateTime2String((Date) returnVal,
												"yyyy-MM-dd HH:mm:ss"), null);
							else {
								myExcelTemplate.createCell(
										String.valueOf(returnVal), null);
							}
						}
					}
				}
			} else {
				log.info("导入的数据为空");
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

	private static void writeTitle(ExcelTemplate instance, String[] titles) {
		instance.createNewRow();
		for (String title : titles)
			instance.createCell(title, null);
	}

	public static <T> void writeExcelByList(String sheetName, List<T> list,
			String[] fields, String[] titles, OutputStream os,
			ExcelTemplate.ExcelVersion version) {
		if (ArrayUtil.isEmpty(list)) {
			//throw new RuntimeException("数据集为空(blank)");
		}
		if ((fields != null) && (titles != null)
				&& (fields.length != titles.length)) {
			throw new RuntimeException("字段列表与其要显示的名称列表不匹配");
		}

		ExcelTemplate myExcelTemplate = ExcelTemplate.getInstance();
		myExcelTemplate.createFile(sheetName, version);
		myExcelTemplate.initTemplate(0, 0, 12.0F);
		try {
			if (ArrayUtil.isEmpty(fields)) {
				fields = JavaBase.getFileds(list.get(0).getClass());
			}
			if (ArrayUtil.isEmpty(titles))
				writeTitle(myExcelTemplate, fields);
			else {
				writeTitle(myExcelTemplate, titles);
			}
			for (Object t : list) {
				myExcelTemplate.createNewRow();
				for (String field : fields) {
					String getter = JavaBase.getGetterByFieldName(field);
					Method method = JavaBase.getGetter(t.getClass(), getter);
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
		if (ArrayUtil.isEmpty(list)) {
			throw new RuntimeException("数据集为空(blank)");
		}
		ExcelTemplate myExcelTemplate = ExcelTemplate.getInstance();
		myExcelTemplate.readTemplateByFile(template);
		myExcelTemplate.initTemplate(initRowIndex, initColIndex, sheetIndex);
		myExcelTemplate.initDefaultStyle(defaultStyleRowIndex,
				defaultStyleColIndex);
		myExcelTemplate.initStyles(copyStyleRowIndex, copyStyleColIndex);
		try {
			if (ArrayUtil.isEmpty(fields)) {
				fields = JavaBase.getFileds(list.get(0).getClass());
			}
			for (Object t : list) {
				for (String field : fields) {
					String getter = JavaBase.getGetterByFieldName(field);
					Method method = JavaBase.getGetter(t.getClass(), getter);
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
				myExcelTemplate.createCell(
						Integer.parseInt(String.valueOf(returnVal)), null);
			else if ((returnType == Boolean.TYPE)
					|| (returnType == Boolean.class))
				myExcelTemplate.createCell(
						Boolean.valueOf(String.valueOf(returnVal))
								.booleanValue(), null);
			else if ((returnType == Double.TYPE)
					|| (returnType == Double.class))
				myExcelTemplate
						.createCell(Double.valueOf(String.valueOf(returnVal))
								.doubleValue(), null);
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
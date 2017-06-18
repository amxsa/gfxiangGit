package cn.cellcom.common.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;


import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.data.ArrayTool;
import cn.cellcom.common.data.DataTool;
import cn.cellcom.common.data.PatternTool;


public class JavaBean {

	public static PropertyDescriptor[] getPropertyDescriptor(Class<?> clazz)
			throws IntrospectionException {
		BeanInfo bean = Introspector.getBeanInfo(clazz);
		return bean.getPropertyDescriptors();
	}

	public static List<Method> getGetter(Class<?> clazz)
			throws IntrospectionException {
		PropertyDescriptor[] propertyDescriptors = getPropertyDescriptor(clazz);
		List<Method> getters = new ArrayList<Method>();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Method method = propertyDescriptor.getReadMethod();
			if ((method != null) && (!"getClass".equals(method.getName()))) {
				getters.add(method);
			}
		}
		return getters;
	}

	public static String[] getFileds(Class<?> clazz)
			throws IntrospectionException {
		List getMethodList = getGetter(clazz);
		String[] fields = new String[getMethodList.size()];
		for (int i = 0; i < getMethodList.size(); i++) {
			String fieldName = getFieldNameByGetter(((Method) getMethodList
					.get(i)).getName());
			fields[i] = fieldName;
		}
		return fields;
	}

	public static Method getGetter(Class<?> clazz, String name)
			throws IntrospectionException {
		List<Method> getters = getGetter(clazz);
		for (Method method : getters) {
			String methodName = method.getName();
			if (methodName.equals(name)) {
				return method;
			}
		}
		return null;
	}

	public static List<Method> getSetter(Class<?> clazz)
			throws IntrospectionException {
		PropertyDescriptor[] propertyDescriptors = getPropertyDescriptor(clazz);
		List<Method> setters = new ArrayList();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Method method = propertyDescriptor.getWriteMethod();
			if (method != null) {
				setters.add(method);
			}
		}
		return setters;
	}

	public static Method getSetter(Class<?> clazz, String name)
			throws IntrospectionException {
		List<Method> setters = getSetter(clazz);
		for (Method method : setters) {
			String methodName = method.getName();
			if (methodName.equals(name)) {
				return method;
			}
		}
		return null;
	}

	public static boolean isJavaString(Class<?> clazz) {
		Class[] strClazz = { String.class, Character.class, Character.TYPE };
		return ArrayTool.contains(strClazz, clazz);
	}

	public static boolean isJavaNumber(Class<?> clazz) {
		Class[] strClazz = { Integer.class, Long.class, Double.class,
				Float.class, Integer.TYPE, Long.TYPE, Double.TYPE, Float.TYPE };
		return ArrayTool.contains(strClazz, clazz);
	}

	public static boolean isJavaBase(Class<?> clazz) {
		return (isJavaString(clazz)) || (isJavaNumber(clazz));
	}
	
	

	public static boolean isJavaDatetime(Class<?> clazz) {
		Class[] strClazz = { java.util.Date.class, java.sql.Date.class,
				Timestamp.class, Time.class };
		return ArrayTool.contains(strClazz, clazz);
	}

	public static String getFieldNameBySetter(String setter) {
		if (StringUtils.isBlank(setter)) {
			throw new RuntimeException("setter不能为空");
		}
		String prefix = "set";

		if ((setter.length() <= 3) || (!setter.startsWith(prefix))) {
			throw new RuntimeException(setter + "方法不符合JavaBean标准");
		}
		String str = setter.substring(3);
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String getSetterByFieldName(String fieldName) {
		if (StringUtils.isBlank(fieldName)) {
			throw new RuntimeException("filedName不能为空");
		}
		String prefix = "set";
		return prefix + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	public static String getFieldNameByGetter(String getter) {
		if (StringUtils.isBlank(getter)) {
			throw new RuntimeException("getter不能为空");
		}
		String prefix = "get";
		if ((getter.length() <= 3) || (!getter.startsWith(prefix))) {
			throw new RuntimeException(getter + "方法不符合JavaBean标准");
		}
		String str = getter.substring(3);
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String getGetterByFieldName(String fieldName) {
		if (StringUtils.isBlank(fieldName)) {
			throw new RuntimeException("filedName不能为空");
		}
		String prefix = "get";
		return prefix + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	public static String columnName2FiledName(String columnName) {
		if (StringUtils.isBlank(columnName)) {
			throw new RuntimeException("columnName不能为空");
		}
		StringBuilder sBuilder = new StringBuilder();
		if (PatternTool.getMatchCount(columnName, "[-,_]").size() == 0) {
			return columnName;
		}
		String[] array = columnName.split("[-,_]", -1);
		for (int i = 0; i < array.length; i++) {
			int len = array[i].length();
			if (i == 0) {
				if (len == 1)
					sBuilder.append(array[i].toUpperCase());
				else
					sBuilder.append(array[i]);
			} else
				sBuilder.append(StringUtils.left(array[i], 1).toUpperCase())
						.append(StringUtils.right(array[i], len - 1));

		}

		return sBuilder.toString();
	}

	public static String columnName2FiledNameOracle(String columnName) {
		if (StringUtils.isBlank(columnName)) {
			throw new RuntimeException("columnName不能为空");
		}
		StringBuilder sBuilder = new StringBuilder();
		if (PatternTool.getMatchCount(columnName, "[-,_]").size() == 0) {
			if (columnName.equals(columnName.toUpperCase())) {
				return columnName.toLowerCase();
			}
			return columnName;
		}
		String[] array = columnName.split("[-,_]", -1);
		for (int i = 0; i < array.length; i++) {
			int len = array[i].length();
			if (i == 0)
				sBuilder.append(array[i].toLowerCase());
			else {
				sBuilder.append(StringUtils.left(array[i], 1).toUpperCase())
						.append(StringUtils.right(array[i], len - 1)
								.toLowerCase());
			}
		}

		return sBuilder.toString();
	}

	/**
	 * @param args
	 * @throws IntrospectionException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		
		

	}

}

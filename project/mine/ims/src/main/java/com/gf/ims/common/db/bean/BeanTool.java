package com.gf.ims.common.db.bean;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.gf.ims.common.db.data.ArrayTool;


public class BeanTool {
	
	public static void trimList(List<?> list, String[] exceptionalFields,
			boolean null2empty) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			IntrospectionException {
		for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
			Object javabean = localIterator.next();
			trimObject(javabean, exceptionalFields, null2empty);
		}
	}

	/**
	 * 去掉对象中的空格
	 * @param javabean 对象
	 * @param exceptionalFields 非处理的属性，
	 * @param null2empty 是否将null转成""
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void trimObject(Object javabean, String[] exceptionalFields,
			boolean null2empty) throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (javabean != null) {
			Class clz = javabean.getClass();
			List<Method> getters = JavaBean.getGetter(clz);
			for (Method getter : getters) {
				if (!JavaBean.isJavaString(getter.getReturnType()))
					continue;
				String fieldName = JavaBean.getFieldNameByGetter(getter.getName());
				if ((!ArrayTool.isEmpty(exceptionalFields))
						&& (ArrayTool.contains(exceptionalFields, fieldName))) {
					continue;
				}
				String setterName = JavaBean.getSetterByFieldName(fieldName);
				Method setter = JavaBean.getSetter(clz, setterName);

				String originalValue = (String) getter.invoke(javabean,
						new Object[0]);
				if (originalValue != null) {
					if(setter!=null)
					setter.invoke(javabean, new Object[] { StringUtils
							.trim(originalValue) });
				} else if (null2empty)
					if(setter!=null)
					setter.invoke(javabean, new Object[] { "" });
			}
		}
	}

	/**
	 * bean转成 map对象
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object bean) {
		try {
			Map<String, Object> beanMap = BeanUtils.describe(bean);
			return beanMap;
		} catch (IllegalAccessException e) {
			throw  new RuntimeException("bean 转成 map异常",e);
		} catch (InvocationTargetException e) {
			throw  new RuntimeException("bean 转成 map异常",e);
		} catch (NoSuchMethodException e) {
			throw  new RuntimeException("bean 转成 map异常",e);
		}
	}

	/**
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) {
		

		
	}

}

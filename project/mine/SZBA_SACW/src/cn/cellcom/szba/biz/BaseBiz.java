package cn.cellcom.szba.biz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.open.db.JDBC;

public class BaseBiz<T> {
	private Class<T> clazz;
	public BaseBiz(){
		ParameterizedType type = (ParameterizedType) BaseBiz.class.getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	} 	
	
	public int save(Connection conn, T pojo) throws Exception{
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int i = jdbc.saveObject(conn, pojo);
		return i;
	}
	public int update(Connection conn, T pojo) throws Exception{
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int i = jdbc.updateObject(conn, pojo);
		return i;
	}
	public List<T> queryForPage(Connection conn){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try {
			jdbc.query(conn, "", clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

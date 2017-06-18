package hcho.core.dao;

import hcho.core.util.PageResult;
import hcho.core.util.QueryHelper;
import hcho.nsfw.info.entity.Info;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
	
	public void save(T entity);
	
	public void delete(Serializable id);
	
	public void update(T entity);
	
	public T findById(Serializable id);
	
	public List<T> findObjects();

	public List<Info> findObjects(String sql, List<Object> params);

	public PageResult findObjects(QueryHelper queryHelper, int pageNo,
			int pageSize);

	public List<T> findObjects(QueryHelper queryHelper);
}

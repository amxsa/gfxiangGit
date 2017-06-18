package hcho.core.service;

import hcho.core.util.PageResult;
import hcho.core.util.QueryHelper;
import hcho.nsfw.info.entity.Info;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
	
	public void save(T entity);
	
	public void delete(Serializable id);
	
	public void update(T entity);
	
	public T findById(Serializable id);
	
	public List<T> findObjects();
	
	//根据sql语句 和 条件参数 查询数据列表  
	@Deprecated
	public List<Info> findObjects(String sql, List<Object> params);
	public List<T> findObjects(QueryHelper queryHelper);
	//根据条件 分页查询
	public PageResult findObjects(QueryHelper queryHelper, int pageNo, int pageSize);
}

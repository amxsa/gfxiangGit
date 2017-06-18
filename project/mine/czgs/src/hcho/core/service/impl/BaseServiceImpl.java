package hcho.core.service.impl;

import java.io.Serializable;
import java.util.List;

import hcho.core.dao.BaseDao;
import hcho.core.service.BaseService;
import hcho.core.util.PageResult;
import hcho.core.util.QueryHelper;
import hcho.nsfw.info.entity.Info;

public class BaseServiceImpl<T> implements BaseService<T>{

	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao){
		this.baseDao=baseDao;
	}
	
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		baseDao.save(entity);
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		baseDao.delete(id);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		baseDao.update(entity);
	}

	@Override
	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.findById(id);
	}

	@Override
	public List<T> findObjects() {
		// TODO Auto-generated method stub
		return baseDao.findObjects();
	}

	
	
	@Override
	public List<Info> findObjects(String sql, List<Object> params) {
		// TODO Auto-generated method stub
		return baseDao.findObjects(sql,params);
	}

	@Override
	public PageResult findObjects(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return baseDao.findObjects(queryHelper,pageNo,pageSize);
	}

	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		// TODO Auto-generated method stub
		return baseDao.findObjects(queryHelper);
	}
	
	
}

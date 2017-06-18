package hcho.core.dao.impl;

import hcho.core.dao.BaseDao;
import hcho.core.util.PageResult;
import hcho.core.util.QueryHelper;
import hcho.nsfw.info.entity.Info;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		
		ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
		
		this.clazz=(Class<T>) pt.getActualTypeArguments()[0];
		
	}
	
	protected Session getCurrentSession(){
		
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(findById(id));
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findObjects() {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery("from "+clazz.getSimpleName());
		return query.list();
	}

	@Override
	public List<Info> findObjects(String sql, List<Object> params) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(sql);
		if (params!=null) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return query.list();
	}

	@Override
	public PageResult findObjects(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		
		Query listQuery = getCurrentSession().createQuery(queryHelper.getListHql());
		
		List<Object> params=queryHelper.getParameter();
		if (params!=null) {
			for (int i = 0; i < params.size(); i++) {
				listQuery.setParameter(i, params.get(i));
			}
		}
		//设置默认第一页
		if (pageNo<1) {
			pageNo=1;
		}
		listQuery.setFirstResult((pageNo-1)*pageSize);//设置起始行数
		listQuery.setMaxResults(pageSize);//设置每页行数
		
		
		//查询总记录数
		Query countQuery = getCurrentSession().createQuery(queryHelper.getCountHql());
		if (params!=null) {
			for (int i = 0; i < params.size(); i++) {
				countQuery.setParameter(i, params.get(i));
			}
		}
		long totalCount = (Long) countQuery.uniqueResult();
		
		return new PageResult(pageSize, totalCount, pageNo, listQuery.list());
	}

	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(queryHelper.getListHql());
		List<Object> params=queryHelper.getParameter();
		if (params!=null) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		return query.list();
	}
	
}

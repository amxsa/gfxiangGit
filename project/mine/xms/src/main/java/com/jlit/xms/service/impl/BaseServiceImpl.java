package com.jlit.xms.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.jlit.db.support.Page;
import com.jlit.xms.mapper.BaseMapper;
import com.jlit.xms.mapper.DeptMapper;
import com.jlit.xms.mapper.EmpMapper;
import com.jlit.xms.mapper.LevelMapper;
import com.jlit.xms.mapper.MenuMapper;
import com.jlit.xms.mapper.TestCommodityMapper;
import com.jlit.xms.model.PageData;
import com.jlit.xms.service.BaseService;


public class BaseServiceImpl<T> implements BaseService<T>{

	
	@Resource
	protected TestCommodityMapper testCommodityMapper;
	@Resource
	protected MenuMapper menuMapper;
	@Resource
	protected DeptMapper deptMapper;
	@Resource
	protected EmpMapper empMapper;
	@Resource
	protected LevelMapper levelMapper;
	
	
	protected BaseMapper<T> baseMapper;
	
	@PostConstruct//构造方法后  初始化方法前执行
	protected void initBaseMapper() throws Exception{
		ParameterizedType type= (ParameterizedType) this.getClass().getGenericSuperclass();
		Class clazz = (Class) type.getActualTypeArguments()[0];

		String localField=clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1)+"Mapper";//获取当前运行的mapper的字段名称
		
		Field field = this.getClass().getSuperclass().getDeclaredField(localField);//获取当前mapper对应的对象
		
		Field baseField = this.getClass().getSuperclass().getDeclaredField("baseMapper");//获取baseMapper对象
		
		baseField.set(this, field.get(this));//把baseMapper对象指向已经在ioc容器中实例化了的子类对象
		
		
	}
	@Override
	public int insert(T entity) {
		
		return baseMapper.insert(entity);
	}

	@Override
	public T getById(String id) {
		
		return baseMapper.getById(id);
	}

	@Override
	public int deleteById(String id) {
		
		return baseMapper.deleteById(id);
	}

	@Override
	public int update(T entity) {
		
		return baseMapper.update(entity);
	}

	@Override
	public List<T> queryForList(T entity) {
		// TODO Auto-generated method stub
		return baseMapper.queryForList(entity);
	}
	
	@Override
	public List<T> selectPageList(PageData<T> pd) {
		
		return baseMapper.selectPageList(pd);
	}

	@Override
	public PageData<T> selectPageData(PageData<T> pd) {
		
		pd.setList(baseMapper.selectPageList(pd));
		pd.setTotalRecords(baseMapper.selectRecords(pd));
		return pd;
	}

	@Override
	public int deleteByIds(String[] ids) {
		
		return baseMapper.deleteByIds(ids);
	}
	
	@Override
	public Page getPageData(PageData<T> pd) {
		List<T> list = baseMapper.selectPageList(pd);
		Integer records = baseMapper.selectRecords(pd);
		return new Page(Page.getStartOfPage(pd.getPageNo(), pd.getPageSize()), records, pd.getPageSize(), list);
	}
	
	
}

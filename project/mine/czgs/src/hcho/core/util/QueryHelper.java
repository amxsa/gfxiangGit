package hcho.core.util;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {

	/**
	 * 查询语句一般由from子句、where子句、order子句组成  
	 * 
	 * from子句是必须的  所以用构造方法实现  
	 */
	private String fromClause="";
	private String whereClause="";
	private String orderClause="";
	
	private List<Object> parameter;//用来保存条件参数
	
	public static String ORDER_BY_DESC="DESC";//降序
	public static String ORDER_BY_ASC="ASC";//升序 
	
	/**
	 * 
	 * @param clazz 当前类
	 * @param alias 类名的别名
	 */
	public QueryHelper(Class clazz,String alias){
		fromClause=" FROM "+clazz.getSimpleName()+ " "+ alias;
	}
	
	/**
	 * 根据添加的条件和参数  构成where子句  并且把条件参数放进集合
	 * @param condition  查询条件  如 p.name like ?
	 * @param params     查询条件参数    "%"+名称+"%"
	 */
	public void addCondition(String condition,Object...params){
		if (whereClause.length()>0) {
			whereClause+=" AND "+condition;
		}else{
			//当前是第一次使用where";
			whereClause=" WHERE "+condition;
		}
		
		if (params.length>0) {
			if (parameter==null) {
				parameter=new ArrayList<Object>();
			}
			for (Object param : params) {
				parameter.add(param);
			}
		}
	}
	
	/**
	 * 
	 * @param property 根据property属性进行排序  如 p.name
	 * @param order  升序或降序  使用常量值  如 QueryHelper.ORDER_BY_DESC
	 */
	public void addOrderByProperty(String property,String order){
		if (orderClause.length()>0) {
			orderClause+=" ,"+property+" "+order;
		}else{
			//第一次使用ORDER BY 
			orderClause=" ORDER BY "+property+" "+order;
		}
	}
	
	//返回列表查询语句
	public String getListHql(){
		
		return fromClause+whereClause+orderClause;
	}
	
	//返回记录数查询语句 
	public String getCountHql(){
		return " SELECT COUNT(*) "+fromClause+whereClause;
	}
	
	//返回条件查询所对应的参数的集合
	public List<Object> getParameter(){
		
		return parameter;
	}
}

package hcho.nsfw.complain.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import hcho.core.dao.impl.BaseDaoImpl;
import hcho.nsfw.complain.dao.ComplainDao;
import hcho.nsfw.complain.entity.Complain;

public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements
		ComplainDao {

	@Override
	public void updateComplainStateInLastDay(String undoneState,
			String invalidState, Date time) {
		// TODO Auto-generated method stub
		String hql="UPDATE Complain set state=? where state=? AND compTime< ?";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, invalidState);
		query.setParameter(1, undoneState);
		query.setParameter(2, time);
		query.executeUpdate();
	}

	@Override
	public List<Object[]> getStatisticDataByYear(int year) {
		/*
		 * SELECT MONTH(comp_time),COUNT(comp_id)
		FROM complain 
		WHERE YEAR(comp_time)=2015
		GROUP BY MONTH(comp_time)
		
		
		SELECT imonth,COUNT(comp_id)
		FROM tmonth LEFT JOIN complain
		ON imonth=MONTH(comp_time)
		AND YEAR(comp_time)=2015
		GROUP BY imonth
		ORDER BY imonth
		
		
		提高效率（子查询）
		SELECT imonth,c2
		FROM tmonth LEFT JOIN
		
		(
		SELECT MONTH(comp_time) c1,COUNT(comp_id) c2
		FROM complain 
		WHERE YEAR(comp_time)=2015
		GROUP BY MONTH(comp_time)
		)t
		
		ON imonth=c1
		ORDER BY imonth
		
		 */
		StringBuffer sb=new StringBuffer();
		/*sb.append(" SELECT imonth,c2 ")
		.append(" FROM tmonth LEFT JOIN ( ")
		.append(" SELECT MONTH(comp_time) c1,COUNT(comp_id) c2 ")
		.append(" FROM complain  ")
		.append(" WHERE YEAR(comp_time)=? ")
		.append(" GROUP BY MONTH(comp_time) )t ")
		.append(" ON imonth=c1 ")
		.append(" ORDER BY imonth ");*/
		sb.append("select imonth,count(comp_id) ")
		.append(" from tmonth left join complain ") 
		.append(" on imonth=extract(month from comp_time) and extract(year from comp_time)=? ")
		.append(" group by imonth")
		.append(" order by imonth");
		SQLQuery query = getCurrentSession().createSQLQuery(sb.toString());
		query.setParameter(0, year);
		List<Object[]> list = query.list();
		return list;
	}

	
}

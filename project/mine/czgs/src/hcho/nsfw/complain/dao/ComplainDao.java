package hcho.nsfw.complain.dao;

import java.util.Date;
import java.util.List;

import hcho.core.dao.BaseDao;
import hcho.nsfw.complain.entity.Complain;

public interface ComplainDao extends BaseDao<Complain> {

	void updateComplainStateInLastDay(String undoneState,
			String invalidState, Date time);

	List<Object[]> getStatisticDataByYear(int year);

}

package cn.cellcom.szba.biz;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.IdHolder;
import cn.open.db.JDBC;

public class TIdHolderBiz {
	private static Log log = LogFactory.getLog(TIdHolderBiz.class);

	private static String SQL_QUERY_IDHOLDER_BYID = "SELECT id, id_type, id_value "
			+ "FROM t_idholder WHERE id=?";

	private static String SQL_QUERY_IDHOLDER_BYTYPE = "SELECT id, id_type, id_value "
			+ "FROM t_idholder WHERE id_type=?";

	private static String SQL_INSERT_IDHOLDER = "INSERT INTO t_idholder(id_type, id_value) "
			+ "VALUES(?,?)";

	private static String SQL_UPDATE_IDHOLDER = "UPDATE t_idholder SET id_value = ? WHERE id = ?";

	// 根据类型查询
	public static IdHolder findIdHolderByIdType(String idType) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<IdHolder> list = null;
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_IDHOLDER_BYTYPE,
					IdHolder.class, idType);
		} catch (Exception e) {
			log.error(e);
		} finally {
			jdbc.closeAll();
		}
		return list != null ? list.get(0) : null;
	}

	/**
	 * 新增
	 * 
	 * @param entity
	 * @return
	 */
	public static String insert(IdHolder idHolder) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			int rows = jdbc.update(Env.DS, SQL_INSERT_IDHOLDER,
					idHolder.getIdType(), idHolder.getIdValue());
			if (rows > 0) {
				result = "success";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			jdbc.closeAll();
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 */
	public static int update(IdHolder idHolder) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try {
			IdHolder record = jdbc.queryForObject(Env.DS,
					SQL_QUERY_IDHOLDER_BYID, IdHolder.class, idHolder.getId());
			if (null == record) {
				return 0;
			}
			if (idHolder.getIdValue() != null) {
				record.setIdValue(idHolder.getIdValue());
			}
			result = jdbc.update(Env.DS, SQL_UPDATE_IDHOLDER,
					record.getIdValue(), record.getId());
		} catch (Exception e) {
			log.error("", e);
		} finally {
			jdbc.closeAll();
		}
		return result;
	}
}

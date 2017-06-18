package cn.cellcom.szba.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.common.SqlHelper2;
import cn.cellcom.szba.domain.TUrl;
import cn.cellcom.szba.vo.SqlAndParamVO;
import cn.cellcom.szba.vo.url.UrlVO;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlAndValue;

public class TUrlBiz {
	Log log = LogFactory.getLog(this.getClass());
	
	private JDBC jdbc = SpringWebApplicataionContext.getJdbc();
	
	private static String SQL_INSERT_T_URL = "insert into t_url (url,type,description,group_id) values (?,?,?,?)";
	private static String SQL_UPDATE_T_URL = "update t_url set url=?,type=?,description=?,group_id=?,create_time=sysdate() where id=?";
	

	/**
	 * 带条件的分页查询
	 * @param  条件
	 * @param page   分页参数
	 * @return
	 */
	public ListAndPager queryList(UrlVO vo, Pager page) throws Exception{
		
		SqlAndParamVO sqlVO = this.queryListSqlAndParam(vo);
		
		ListAndPager list = jdbc.queryPage(Env.DS, sqlVO.getSql(), TUrl.class, page.getCurrentIndex(), page.getSizePerPage(), sqlVO.getParam().toArray());
	
		return list;
	}
	/**
	 * 查询
	 * @param  条件
	 * @return
	 */
	public List queryList(UrlVO vo) throws Exception{
		
		SqlAndParamVO sqlVO = this.queryListSqlAndParam(vo);
		
		List list = jdbc.query(Env.DS, sqlVO.getSql(), TUrl.class,sqlVO.getParam().toArray());
		
		return list;
	}
	
	/**
	 * 构造查询列表sql和参数值
	 * @param vo
	 * @return
	 */
	public SqlAndParamVO queryListSqlAndParam(UrlVO vo){
		List param = new ArrayList();
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select * from t_url u");
		sqlBuff.append(" where 1 = 1");
		
		if(vo.getId() != null){
			sqlBuff.append(" and id = ?");
			param.add(vo.getId());
		}
		if(StringUtils.isNotBlank(vo.getUrl())){
			sqlBuff.append(" and url like ?");
			param.add("%"+vo.getUrl()+"%");
		}
		
		SqlAndParamVO sqlVO = new SqlAndParamVO();
		sqlVO.setSql(sqlBuff.toString());
		sqlVO.setParam(param);
		
		return sqlVO;
	}
	
	/**
	 * 获取单个对象
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public TUrl getUrl(UrlVO vo) throws Exception{
		
		TUrl po = null;
		
		List list = this.queryList(vo);
		
		if(list.size() > 0){
			po = (TUrl)list.get(0);
		}
	
		return po;
	}
	/**
	 * 保存
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public TUrl save(TUrl po) throws Exception{
		SqlAndValue sav = null;
		//新增
		if(po.getId() == null){
			sav = SqlHelper2.generateDymicInsert(po,SQL_INSERT_T_URL);
		}else{
			//更新
			sav = SqlHelper2.generateDymicUpate(po,SQL_UPDATE_T_URL);
		}
		if (sav != null){
			jdbc.update(Env.DS, sav.getSql(), sav.getValues());
		}
		
		return po;
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public void del(String id) throws Exception{
		String sql = "delete from t_url where id in ("+id+")";
		jdbc.update(Env.DS, sql,null);
	}
	
}

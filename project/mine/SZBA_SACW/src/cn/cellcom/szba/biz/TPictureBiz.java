package cn.cellcom.szba.biz;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TElecEvidence;
import cn.cellcom.szba.domain.TPicture;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.JDBC;

public class TPictureBiz {

	private static String SQL_QUERY_PICTURE = "select pic.pic_id as \"picID\", pic.type as type, " +
			" pic.thumbnail_url as \"thumbnailUrl\", pic.original_url as \"originalUrl\", " +
			" pic.create_time as \"uploadDate\", pic.description as \"picDescription\", " +
			" pic.name as \"name\", pic.capturer as \"capturer\", pic.capture_time as \"captureTime\", pic.capture_place as \"capturePlace\" " +
			" from t_picture pic " +
			" where (pic.property_id = ? or pic.case_id = ? or elec_evidence_id = ?) " +
			" and (pic.is_display = 'Y')" +
			" order by priority asc";
	
	private static String SQL_QUERY_SEQCURRVAL = "select seq_picture.currval as currval from dual";
	
	private static String SQL_INSERT_PIC = "insert into t_picture(pic_id,type, thumbnail_url, original_url, description, creator, case_id, property_id, elec_evidence_id, priority,"
			+ "name, capture_time, capture_place, capturer, width, height)" +
			" values(?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'),?,?, ?, ?)";
	
	private static String SQL_UPDATE_PIC_INFO = "update t_picture set description = ? where pic_id = ?";
	
	private static String SQL_UPDATE_TO_DELSTATUS = "update t_picture set is_display = 'N' where pic_id = ?";
	
	private static String SQL_DEL_BY_PROID = 
			"delete from t_picture where property_id = ?";
	
	/**
	 * 查看照片列表
	 * @param propertyId
	 * @param caseId
	 * @return
	 */
	public static List<TPicture> queryPhotographs(String propertyId,String caseId, String elecEvidenceId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TPicture> list = null;
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_PICTURE, TPicture.class, propertyId, caseId, elecEvidenceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list!=null?list:new ArrayList<TPicture>();
	}
	
	/**
	 * 添加图片信息。先全部删除图片信息，再添加
	 * @param picList 图片列表
	 * @return
	 */
	public static List<TPicture> addPicInfo(Connection conn, List<TPicture> picList, String proId){
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		try {
			jdbc.update(conn, SQL_DEL_BY_PROID, proId);
			if(picList.size() > 0){
			
				int i = 0;
				for(TPicture p : picList){
					List<Object> params = new ArrayList<Object>();
					params.add(UUID.randomUUID().toString());
					params.add(p.getType());
					params.add(p.getThumbnailUrl());
					params.add(p.getOriginalUrl());
					params.add(p.getPicDescription());
					params.add(p.getCreator().getAccount());
					params.add(p.getCaseID());
					params.add(p.getPropertyID());
					params.add(p.getElecEvidenceID());
					params.add(i);
					params.add(p.getName());
					params.add(p.getCaptureTime());
					params.add(p.getCapturePlace());
					params.add(p.getCapturer());
					params.add(p.getWidth());
					params.add(p.getHeight());
					i++;
					
					jdbc.update(conn, SQL_INSERT_PIC, params.toArray());
					
				}
			}
			
			//conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		return picList;
	}

	/**
	 * 修改图片信息（如果修改的时候有新图片加入，也在这里处理）
	 * @return
	 */
	public boolean exitPicInfo(Object obj){
		
		
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			
			List<TPicture> existPics = null;
			List<TPicture> picList = null; //obj.getPhotographs();
			//正式图片表中已存在的财物图片
			if(obj instanceof TProperty){
				picList = ((TProperty) obj).getPhotographs();
				existPics = jdbc.query(conn, SQL_QUERY_PICTURE, TPicture.class,  ((TProperty) obj).getProId(), "", "");
			}else if(obj instanceof TElecEvidence){
				picList = ((TElecEvidence) obj).getPhotographs();
				existPics = jdbc.query(conn, SQL_QUERY_PICTURE, TPicture.class, "", "", ((TElecEvidence) obj).getElecEvidenceID());
			}
			//修改的时候，添加的图片
			List<TPicture> noExistPics = new ArrayList<TPicture>();
			
			for(TPicture p : picList){
				if(isExistByUrl(existPics, p.getOriginalUrl())){
					jdbc.update(conn, SQL_UPDATE_PIC_INFO, p.getPicDescription(),p.getPicID());
				}else{
					noExistPics.add(p);
				}
			}
			
			int i = 0;
			for(TPicture p : noExistPics){
				List<Object> params = new ArrayList<Object>();
				params.add(UUID.randomUUID().toString());
				params.add(p.getType());
				params.add(p.getThumbnailUrl());
				params.add(p.getOriginalUrl());
				params.add(p.getPicDescription());
				params.add(p.getCreator().getAccount());
				params.add(p.getCaseID());
				params.add(p.getPropertyID());
				params.add(p.getElecEvidenceID());
				params.add(i);
				i++;
				
				jdbc.update(conn, SQL_INSERT_PIC, params.toArray());
				
			}
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	/**
	 * 将图片改为删除状态
	 * @param picList
	 * @return
	 */
	public boolean delPicInfo(Long picId){
		
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		try {
			jdbc.update(Env.DS, SQL_UPDATE_TO_DELSTATUS, picId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return true;
	}
	
	/**
	 * 判断指定id是否存在
	 * @return
	 */
	public boolean isExist(List<TPicture> list, Long id){
		for(TPicture p : list){
			if(id.equals(p.getPicID())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 通过url来判断图片是否已存在
	 * @param list
	 * @param id
	 * @return
	 */
	public boolean isExistByUrl(List<TPicture> list, String url){
		for(TPicture p : list){
			if(p.getOriginalUrl().equals(url)){
				return true;
			}
		}
		return false;
	}
}

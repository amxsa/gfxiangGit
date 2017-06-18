package cn.cellcom.wechat.bus;


import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TColorPrintPart;
import cn.cellcom.wechat.po.TColorPrintSys;
import cn.cellcom.wechat.po.TColorPrintUse;


public class ColorPrintingUseBus {
	private static final Log log = LogFactory.getLog(ColorPrintingBus.class);
	
	public TColorPrintUse query(JDBC jdbc, Login login) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TColorPrintUse po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_color_printing_use where reg_no = ? ",
					TColorPrintUse.class, new String[] { login.getNumber() });
			if (po != null)
				return po;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询彩印异常", e);
			return null;
		}
		return null;
	}
	public DataMsg saveOrUpdate(Map<String, String> params, Login login,
			DataMsg dataMsg) {
		String content = params.get("content");
		String state =params.get("state");
		if("Y".equals(state)){
			if(StringUtils.isBlank(content)){
				dataMsg.setMsg("彩印内容不能为空");
				return dataMsg;
			}
			if(content.length()>50){
				dataMsg.setMsg("彩印内容不能超过50个字符");
				return dataMsg;
			}
			content = content.replaceAll("[^\\u0000-\\uFFFF]", "");
			if(StringUtils.isBlank(content)){
				dataMsg.setMsg("彩印内容含有非法字符");
				return dataMsg;
			}
		}
		
		//state=Y 设置(新增或修改)，N 删除，D 取消服务
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			TColorPrintUse po = query(jdbc, login);
			if (po != null) {//数据库中有相关记录
				if("Y".equals(state)){
					if(StringUtils.isBlank(po.getContent())){//取消服务后
						jdbc.update(ApplicationContextTool.getDataSource(), 
								"update t_color_printing_use set cp_server=?,content=?,my_flag=?,part_flag=?,scene_flag=?,proper_flag=? where reg_no=?  ", new Object[]{
							TColorPrintUse.SERVER_VALID,content,TColorPrintUse.STATUS_VALID,po.getPartFlag(),po.getSceneFlag(),po.getProperFlag(),po.getRegNo()
						});
						dataMsg.setMsg("设置成功");
					}else{
						// 修改设置
						jdbc.update(ApplicationContextTool.getDataSource(), 
								"update t_color_printing_use set cp_server=?,content=?,my_flag=?,part_flag=?,scene_flag=?,proper_flag=? where reg_no=?  ", new Object[]{
							po.getCpServer(),content,po.getMyFlag(),po.getPartFlag(),po.getSceneFlag(),po.getProperFlag(),po.getRegNo()
						});
						dataMsg.setMsg("修改成功");
					}
					
				}else if("N".equals(state)){//删除个人彩印
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"update t_color_printing_use set reg_no=?,cp_server=?,content=null,my_flag=?,part_flag=?,scene_flag=?,proper_flag=? where id=?  ", new Object[]{
						po.getRegNo(),po.getCpServer(),TColorPrintUse.STATUS_INVALID,po.getPartFlag(),po.getSceneFlag(),po.getProperFlag(),po.getId()
					});
					dataMsg.setMsg("删除成功");
				}else if ("D".equals(state)) {//取消彩印服务（内容制空，各种彩印为无效状态）
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"update t_color_printing_use set reg_no=?,cp_server=?,content=null,my_flag=?,part_flag=?,scene_flag=?,proper_flag=? where id=?  ", new Object[]{po.getRegNo(),
						TColorPrintUse.SERVER_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,po.getId()
					});
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"delete from t_color_printing_part where reg_no=?", new Object[]{po.getRegNo()});
					dataMsg.setMsg("取消彩印服务成功");
				}
			} else {//第一次进入  开启服务并加入彩印内容
				jdbc.update(ApplicationContextTool.getDataSource(), 
						"insert into t_color_printing_use(cp_server,reg_no,content,my_flag,part_flag,scene_flag,proper_flag)values(?,?,?,?,?,?,?)", new Object[]{
					TColorPrintUse.SERVER_VALID,login.getNumber(),content,TColorPrintUse.STATUS_VALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID});
				return new DataMsg(true, "成功开启彩印服务");
			}
			dataMsg.setState(true);
			return dataMsg;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改彩印异常", e);
			return dataMsg;
		}
		
	}
	public DataMsg queryColorPrintingUse(String regNo, String number) {
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			TColorPrintUse cpu = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_color_printing_use where reg_no=?", TColorPrintUse.class, new Object[]{});
			
			if (cpu.getProperFlag().equals(TColorPrintUse.STATUS_VALID)) {//开启了专属彩印(查询专属彩印表)
				
				if (true) {
					return new DataMsg(true, "已查询到彩印内容");
				}
			}
			if (cpu.getSceneFlag().equals(TColorPrintUse.STATUS_VALID)) {//开启了情景彩印(查询情景彩印表)
				if (true) {
					return new DataMsg(true, "已查询到彩印内容");
				}
			}
			if (cpu.getPartFlag().equals(TColorPrintUse.STATUS_VALID)) {//开启了分时段彩印(查询分时段彩印表)
				TColorPrintPart cpp = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
						"select * from t_color_printing_part where reg_no=? and start_time<=getdate() and end_time>=getdate()", TColorPrintPart.class, 
						new Object[]{cpu.getRegNo()});
				if (cpp!=null) {
					return new DataMsg(true, cpp.getContent());
				}
			}
			if (cpu.getMyFlag().equals(TColorPrintUse.STATUS_VALID)) {//开启了我的彩印 返回总彩印表内容
				return new DataMsg(true, cpu.getContent());
			}
			//使用系统默认彩印
			TColorPrintSys cps = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_color_printing_sys where status=?", TColorPrintSys.class, new Object[]{TColorPrintSys.SYSCP_STATUS_VALID});
			if (cps!=null) {
				return new DataMsg(true, cps.getContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new DataMsg(false,"查询彩印异常");
			// TODO: handle exception
		}
		return new DataMsg(false,"未找到彩印内容");
	}
	public DataMsg queryMyServer(Login login) {
		// 深圳1336009号段具备该功能
		
		return null;
	}
	public DataMsg openServer(String number) {
		// TODO Auto-generated method stub
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			TColorPrintUse po = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_color_printing_use where reg_no=?", TColorPrintUse.class, new Object[]{number});
			if (po!=null) {//数据库有记录
				jdbc.update(ApplicationContextTool.getDataSource(), 
						"update t_color_printing_use set reg_no=?,cp_server=?,content=null,my_flag=?,part_flag=?,scene_flag=?,proper_flag=? where id=?  ", new Object[]{number,
					TColorPrintUse.SERVER_VALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,po.getId()
				});
				return new DataMsg(true, "成功开启彩印服务");
			}
			//无记录 ，添加
			jdbc.update(ApplicationContextTool.getDataSource(), 
					"insert into t_color_printing_use(cp_server,reg_no,content,my_flag,part_flag,scene_flag,proper_flag)values(?,?,null,?,?,?,?)", new Object[]{
				TColorPrintUse.SERVER_VALID,number,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID});
			return new DataMsg(true, "成功开启彩印服务");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new DataMsg(false, "开启彩印异常");
		}
	}
	
}

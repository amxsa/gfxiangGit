package cn.cellcom.wechat.bus;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TColorPrintPart;
import cn.cellcom.wechat.po.TColorPrintUse;


public class ColorPrintingPartBus {
	private static final Log log = LogFactory.getLog(ColorPrintingBus.class);
	
	public List<TColorPrintPart> query(JDBC jdbc, Login login) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			List<TColorPrintPart> list = jdbc.query(ApplicationContextTool.getDataSource(), 
					"select * from t_color_printing_part where reg_no=? order by start_time", TColorPrintPart.class,new String[] { login.getNumber() }, 0, 0);
			if (list != null)
				return list;
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
		String id = params.get("id");
		String flag = params.get("flag");
		String isloop = params.get("isloop");
		String content = params.get("content");
		String state =params.get("state");
		String startTime =params.get("startTime");
		String endTime =params.get("endTime");
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
		
		try {
			//state=Y 新增，N 删除
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			ColorPrintingUseBus bus=new ColorPrintingUseBus(); 
			TColorPrintUse po = bus.query(jdbc, login);
			if (po != null) {//总表有记录时
				if("Y".equals(state)){
					if (flag.equals("false")) {
						dataMsg.setState(false);
						dataMsg.setMsg("您设置的时间和其他时间段有冲突,请重新设置");
						return dataMsg;
					}
					//新增分时段彩印
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"insert into t_color_printing_part(reg_no,content,is_loop,start_time,end_time)values(?,?,?,?,?)", new Object[]{
							po.getRegNo(),content,isloop,startTime,endTime
					});
					//设置状态到总彩印表
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"update t_color_printing_use set cp_server=?,part_flag=? where id=?  ", new Object[]{
						TColorPrintUse.SERVER_VALID,TColorPrintUse.STATUS_VALID,po.getId()
					});
					dataMsg.setMsg("设置成功");
				}else if("N".equals(state)){
					//根据id删除分时段彩印
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"delete from t_color_printing_part where id=? ", new Object[]{Long.parseLong(id)});
					//根据号码检查时段表是否还有开启的彩印内容
					TColorPrintPart cpp = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
							"select * from t_color_printing_part where reg_no=?", TColorPrintPart.class, new Object[]{po.getRegNo()});
					//若无分时段彩印 更新总彩印表状态
					if (cpp==null) {
						jdbc.update(ApplicationContextTool.getDataSource(), 
								"update t_color_printing_use set part_flag=? where reg_no=?  ", new Object[]{
							TColorPrintUse.STATUS_INVALID,po.getRegNo()
						});
					}
					dataMsg.setMsg("删除成功");
				}else if ("UC".equals(state)) {//更新内容
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"update t_color_printing_part set content=? where id=?", new Object[]{content,Long.parseLong(id)});
					dataMsg.setMsg("更新成功");
				}else if ("UT".equals(state)) {//更新时间和状态
					if (flag.equals("false")) {
						dataMsg.setState(false);
						dataMsg.setMsg("您设置的时间和其他时间段有冲突,请重新设置");
						return dataMsg;
					}
					jdbc.update(ApplicationContextTool.getDataSource(), 
							"update t_color_printing_part set start_time=?,end_time=?,is_loop=? where id=?", new Object[]{startTime,endTime,isloop,Long.parseLong(id)});
					dataMsg.setMsg("更新成功");
				}else{//
					
				}
			}else{//总表无记录（第一次进入） 开启服务
				jdbc.update(ApplicationContextTool.getDataSource(), 
						"insert into t_color_printing_use(cp_server,reg_no,content,my_flag,part_flag,scene_flag,proper_flag)values(?,?,null,?,?,?,?)", new Object[]{
					TColorPrintUse.SERVER_VALID,login.getNumber(),TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_VALID,TColorPrintUse.STATUS_INVALID,TColorPrintUse.STATUS_INVALID});
				//新增分时段彩印
				jdbc.update(ApplicationContextTool.getDataSource(), 
						"insert into t_color_printing_part(reg_no,content,is_loop,start_time,end_time)values(?,?,?,?,?)", new Object[]{
					login.getNumber(),content,isloop,startTime,endTime
				});
				dataMsg.setMsg("设置成功");
			}
			
			dataMsg.setState(true);
			return dataMsg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("修改彩印异常", e);
			return dataMsg;
		}
	
	}
	
		
}

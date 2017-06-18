package cn.cellcom.wechat.bus;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.po.ColorPrinting;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;

public class ColorPrintingBus {
	private static final Log log = LogFactory.getLog(ColorPrintingBus.class);

	public ColorPrinting query(JDBC jdbc, Login login) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			ColorPrinting po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_color_printing where number = ? ",
					ColorPrinting.class, new String[] { login.getNumber() });
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
		
		//state=Y 设置(新增或修改)，N 删除，D 取消
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			ColorPrinting po = query(jdbc, login);
			if (po != null) {
				if("Y".equals(state)){
					//删除或取消后，重新设置
					if(StringUtils.isBlank(po.getContent())){
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update t_color_printing set content = ?,state=?,create_time=getdate(),update_time= null,cancel_time=null where number = ? ",
								new String[] { content,state, login.getNumber() });
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update t_color_printing_argument set flag=1,update_time=getdate() where flag=0 ",
								null);
						dataMsg.setMsg("设置成功");
					}else{
						// 修改设置
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update t_color_printing set content = ?,state=?,update_time= getdate(),cancel_time=null where number = ? ",
								new String[] { content,state, login.getNumber() });
						dataMsg.setMsg("修改成功");
					}
					
				}else if("N".equals(state)||"D".equals(state)){
					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"update t_color_printing set content =null,state=?,cancel_time=getdate() where number = ? ",
							new String[] { state, login.getNumber() });
					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"update t_color_printing_argument set flag=1,update_time=getdate() where flag=0 ",
							null);
					if("N".equals(state)){
						dataMsg.setMsg("删除成功");
					}else{
						dataMsg.setMsg("取消成功");
					}
				}
			} else {
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"insert into t_color_printing(number,content,state,create_time,update_time,cancel_time) values(?,?,?,getdate(),null,null)",
						new String[] { login.getNumber(), content,"Y" });
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update t_color_printing_argument set flag=1,update_time=getdate() where flag=0 ",
						null);
			}
			dataMsg.setState(true);
			return dataMsg;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改彩印异常", e);
			return dataMsg;
		}
		
	}

}

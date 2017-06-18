package cn.cellcom.szba.controller.setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.setting.TMenuBiz;
import cn.cellcom.szba.common.CommonResponse;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.domain.TMenu;

@Controller
@RequestMapping("/menu")
public class TMenuController {
	
	private static Log log = LogFactory.getLog(TMenuController.class);

	/** 
	 * 菜单管理
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest request){
		
		return "/jsp/menu/menuManage";
	}
	
	@RequestMapping("/loadMenus")
	public void loadMenus(HttpServletRequest request,
			HttpServletResponse response){
		List<TMenu> list = TMenuBiz.findMenus();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", list);
		JSONObject json = JSONObject.fromObject(data);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	@RequestMapping("/saveMenu")
	public void saveMenu(HttpServletRequest request, HttpServletResponse response, TMenu entity){
		try {
			TMenu record = TMenuBiz.saveMenu(entity);
			CommonResponse cr = new CommonResponse();
			if(null!=record){
				cr.setSuccess(true);
				cr.setData(record);
			} else {
				cr.setSuccess(false);
			}
			PrintTool.print(response, PrintTool.printJSON(cr), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
}


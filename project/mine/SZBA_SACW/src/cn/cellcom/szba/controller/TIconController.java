package cn.cellcom.szba.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TIconBiz;
import cn.cellcom.szba.biz.TRoleBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.domain.TIcon;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.domain.TRoleDesktopIcon;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.encrypt.Md5Util;



@Controller
@RequestMapping("/icon")
public class TIconController {
	
    private static Log log = LogFactory.getLog(TIconController.class);
	private static TIconBiz tIconBiz = new TIconBiz();
	
	/*
	 * 执行桌面图标分页查询
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest request, Pager page){
		MyPagerUtil.initPager(page);
		String accStr = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
		ListAndPager<TIcon> list;
		String roleId = request.getParameter("id");
		try {
			list = tIconBiz.queryIconPage(accStr, page);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TIcon> icons = list.getList();
			request.setAttribute("data", icons);
			request.setAttribute("page", proPage);
			request.setAttribute("params", request.getParameterMap());
			if(roleId!=null){
				List<TRoleDesktopIcon> iconList=tIconBiz.roleIconList(roleId);
				String iconStr="";
				if(iconList!=null&&!iconList.isEmpty()){
					for(TRoleDesktopIcon ri:iconList){
						iconStr=iconStr+ri.getDesktopIconId()+";";
					}
				}
				iconStr=iconStr!=""?iconStr.substring(0,iconStr.length()-1):"";
				request.setAttribute("iconStr", iconStr);
				request.setAttribute("roleId", roleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(roleId!=null){
			return "/jsp/icon/iconManageRe";
		}
		return "/jsp/icon/iconManage";
	}
	
	/*
	 * 添加桌面图标
	 */
	@RequestMapping("add")
	public String add(HttpServletRequest request, TIcon entity){
		String result = tIconBiz.insert(entity);
		request.setAttribute("result", result);
		Pager page = new Pager();
		queryForPage(request,page);
		return "/jsp/icon/iconManage";
	}
	/*
	 * 修改桌面图标信息
	 */
	@RequestMapping("toEditPage")
	public String toEditPage(HttpServletRequest request){
		long id = Long.parseLong(request.getParameter("id"));
		TIcon entity = tIconBiz.queryById(id);

		request.setAttribute("entity", entity);
		
		return "/jsp/icon/editIcon";
	}
	
	/*
	 * 保存修改桌面图标信息
	 */
	@RequestMapping("toSave")
	public String toSave(HttpServletRequest request,TIcon entity){
		long id = Long.parseLong(request.getParameter("id"));
		String result = tIconBiz.save(entity,id);
		request.setAttribute("result", result);
		Pager page = new Pager();
		queryForPage(request,page);
		return "/jsp/icon/iconManage";
	}
	
	/*
	 * 删除桌面图标信息
	 */
	@RequestMapping("toDelete")
	public String toDelete(HttpServletRequest request){
		long id = Long.parseLong(request.getParameter("id"));
		String result = tIconBiz.del(id);
		request.setAttribute("result", result);
		Pager page = new Pager();
		queryForPage(request,page);
		return "/jsp/icon/iconManage";
	}

}

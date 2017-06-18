package cn.cellcom.szba.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TUrlBiz;
import cn.cellcom.szba.common.DataMsgTool;
import cn.cellcom.szba.common.ExceptionTool;
import cn.cellcom.szba.common.FileUpload;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TCivilian;
import cn.cellcom.szba.domain.TDetail;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TUrl;
import cn.cellcom.szba.vo.url.UrlVO;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/url")
public class TUrlController {

	private Log log = LogFactory.getLog(this.getClass());
	
	private TUrlBiz biz = new TUrlBiz();
	private FileUpload upload = new FileUpload();
	
	@RequestMapping("/queryList")
	public String queryForPage(HttpServletRequest requ, UrlVO vo, Pager page){
		MyPagerUtil.initPager(page);
		List list = new ArrayList();
		String link = "";
		try {
			ListAndPager listPage = biz.queryList(vo, page);
		 
			Pager proPage = listPage.getPager();
			if(proPage != null){
				proPage.setSizePerPage(page.getSizePerPage());
				list = listPage.getList();
				link = MyPagerUtil.getLink(proPage, "searchForm");
				
			}
			requ.setAttribute("page", proPage);
			requ.setAttribute("link", link);
			requ.setAttribute("list", list);
			requ.setAttribute("vo", vo);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ExceptionTool.error(requ, e);
		}
		return "/jsp/url/urlList";
	}
	
	/**
	 * 查看详情
	 * @param requ
	 * @param vo
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest requ,UrlVO vo){
		
		try {
			TUrl po = new TUrl();
			//修改
			if(vo.getId() != null){
				po = biz.getUrl(vo);
			}else{
				//新增
			}
			requ.setAttribute("po", po);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ExceptionTool.error(requ, e);
		}
		return "/jsp/url/urlView";
	}
	
	/**
	 * 保存
	 * @param requ
	 * @param vo
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest requ,HttpServletResponse response,TUrl po){
		
		try {
			
			biz.save(po);
			
			PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgSuccess(po)), "json");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			try {
				PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgError(e, 1)), "json");
			} catch (IOException e1) {
				log.error(e1.getMessage(),e1);
			}
		}
		return null;
	}
	/**
	 * 删除
	 * @param requ
	 * @return
	 */
	@RequestMapping("/del")
	public String del(HttpServletRequest requ,HttpServletResponse response,String id){
		
		try {
			
			biz.del(id);
			
			PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgSuccess()), "json");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			try {
				PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgError(e, 1)), "json");
			} catch (IOException e1) {
				log.error(e1.getMessage(),e1);
			}
		}
		return null;
	}
}

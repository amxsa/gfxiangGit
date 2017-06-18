package hcho.core.action;

import hcho.core.util.PageResult;

import java.net.URLDecoder;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	
	protected String[] selectedRow;
	
	private int pageSize;
	private int pageNo;
	protected PageResult pageResult;
	
	//设置页面默认 数据行数
	private static int DEFAULT_PAGESIZE=5;

	//给字符串解码 
	public String decode(String str){
		
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	public int getPageSize() {
		if (pageSize<1) {
			pageSize=DEFAULT_PAGESIZE;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}
	
}

package hcho.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面所需要的列表数据和分页导航条所需要的数据
 * @author Administrator
 *
 */
public class PageResult {
	
	private int pageSize;
	private long totalCount;
	private int totalPageCount;
	private int pageNo;
	private List items;
	public PageResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageResult(int pageSize, long totalCount, int pageNo, List items) {

		this.pageSize = pageSize;
		this.totalCount = totalCount;
		
		this.items = items!=null?items:new ArrayList<Object>();
		if (totalCount==0) {
			this.pageNo=0;
			this.totalPageCount=0;
		}else{
			this.pageNo = pageNo;
			int tem=(int)totalCount/pageSize;
			this.totalPageCount=(totalCount%pageSize)==0?tem:(tem+1);
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	
	
	
}

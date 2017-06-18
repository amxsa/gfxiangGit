package com.gf.ims.web.queryBean;

public class BaseQueryBean {
	
	private int pageSize=10;
	private int pageNumber=1;
	public BaseQueryBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseQueryBean(int pageSize, int pageNumber) {
		super();
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}

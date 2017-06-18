package com.jlit.xms.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageData<T> {
	
	private static Integer PAGE_SIZE=10;
	private List<T> list;//数据列表
	private Integer pageSize=PAGE_SIZE;//页大小
	private Integer pageNo;//当前页号
	private Integer start;//起始行 
	private Integer totalRecords;//总记录数
	private T paramModel;//查询条件实体
	
	private Map<String,Object> keyWord=new HashMap<String, Object>();//用来存储各种需要的查询关键字
	
	public static Integer getPAGE_SIZE() {
		return PAGE_SIZE;
	}
	public static void setPAGE_SIZE(Integer pAGE_SIZE) {
		PAGE_SIZE = pAGE_SIZE;
	}
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public T getParamModel() {
		return paramModel;
	}
	public void setParamModel(T paramModel) {
		this.paramModel = paramModel;
	}
	public PageData(List<T> list, Integer pageSize, Integer pageNo,
			Integer start, Integer totalRecords, T paramModel,
			Map<String, Object> keyWord) {
		super();
		this.list = list;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.start = start;
		this.totalRecords = totalRecords;
		this.paramModel = paramModel;
		this.keyWord = keyWord;
	}
	
	public PageData(Integer pageSize, Integer pageNo, T paramModel,
			Map<String, Object> keyWord) {
		super();
		if (pageSize!=null) {
			this.pageSize = pageSize;
		}else{
			this.pageSize=PAGE_SIZE;
		}
		this.pageNo = pageNo;
		this.paramModel = paramModel;
		this.keyWord = keyWord;
	}
	public PageData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getStart() {
		start=(pageNo-1)*pageSize;
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Map<String,Object> getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(Map<String,Object> keyWord) {
		this.keyWord = keyWord;
	}
}

package com.jlit.xms.web.queryBean;
/**
 * 优惠查询bean
 */
public class MenuQueryBean extends BaseQueryBean {

	private String name;
	
	private String parentId;

	public MenuQueryBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenuQueryBean(String name, String parentId) {
		super();
		this.name = name;
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}

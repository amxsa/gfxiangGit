package com.jlit.xms.web.queryBean;
/**
 * 优惠查询bean
 */
public class DeptQueryBean extends BaseQueryBean {

	private String levelName;

	public DeptQueryBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeptQueryBean(String levelName) {
		super();
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}

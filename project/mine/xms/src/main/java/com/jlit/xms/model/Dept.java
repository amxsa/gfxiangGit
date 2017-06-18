package com.jlit.xms.model;

import java.util.List;

public class Dept {

	private String id;
	
	private String name;
	
	private String logo;
	
	private Level level;
	
	private List<Emp> emps;

	public Dept() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Dept(String id, String name, String logo, Level level, List<Emp> emps) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.level = level;
		this.emps = emps;
	}


	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public List<Emp> getEmps() {
		return emps;
	}

	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}

	
	
}

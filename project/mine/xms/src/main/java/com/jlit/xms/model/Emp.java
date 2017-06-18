package com.jlit.xms.model;

import java.util.Date;

public class Emp {

	private String id;
	
	private String name;
	
	private String idCard;
	
	private String phone;
	
	private Date birthday;
	
	private String info;
	
	private Integer age;
	
	private Dept dept;

	public Emp() {
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




	public Emp(String id, String name, String idCard, String phone,
			Date birthday, String info, Integer age, Dept dept) {
		super();
		this.id = id;
		this.name = name;
		this.idCard = idCard;
		this.phone = phone;
		this.birthday = birthday;
		this.info = info;
		this.age = age;
		this.dept = dept;
	}




	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}




	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}




	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	
}

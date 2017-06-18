package com.jlit.xms.web.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmpVo {

	private String id;
	
	private String name;
	
	private String idCard;
	
	private String phone;
	
	private Date birthday;
	
	private String info;
	
	private Integer age;
	
	private String deptName;

	private String stringBirthday;
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	public EmpVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpVo(String id, String name, String idCard, String phone,
			Date birthday, String info, Integer age, String deptName,
			String stringBirthday) {
		super();
		this.id = id;
		this.name = name;
		this.idCard = idCard;
		this.phone = phone;
		this.birthday = birthday;
		this.info = info;
		this.age = age;
		this.deptName = deptName;
		this.stringBirthday = stringBirthday;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getStringBirthday() {
		return sdf.format(this.birthday);
	}
	
	
}

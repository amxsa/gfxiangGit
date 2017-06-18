/**
 * 
 */
package com.gf.model;

/**
 * @author gfxiang
 * @time 2017年5月4日 下午2:06:08
 *	@User
 */
public class User {
	private String id;
	private String name;
	private int age;
	private String password;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String id, String name, int age, String password) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.password = password;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

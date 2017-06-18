package com.gf.imsCommon.ims.module;

public class Questions {
	
	private int id;
	private String question;
	private String parentId;
	public Questions() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Questions(int id, String question, String parentId) {
		super();
		this.id = id;
		this.question = question;
		this.parentId = parentId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}

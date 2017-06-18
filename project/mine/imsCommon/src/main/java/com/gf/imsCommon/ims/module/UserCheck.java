package com.gf.imsCommon.ims.module;

public class UserCheck {
	
	private String id;
	private String userId;
	private String questionId;
	private String answer;
	public UserCheck() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserCheck(String id, String userId, String questionId, String answer) {
		super();
		this.id = id;
		this.userId = userId;
		this.questionId = questionId;
		this.answer = answer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}

package hcho.nsfw.complain.entity;

import java.sql.Timestamp;

/**
 * CompReply entity. @author MyEclipse Persistence Tools
 */

public class CompReply implements java.io.Serializable {

	// Fields

	private String replyId;
	private Complain complain;
	private String replyer;
	private String replyDept;
	private Timestamp replyTime;
	private String content;

	// Constructors

	/** default constructor */
	public CompReply() {
	}

	/** minimal constructor */
	public CompReply(Complain complain) {
		this.complain = complain;
	}

	/** full constructor */
	public CompReply(Complain complain, String replyer, String replyDept,
			Timestamp replyTime, String content) {
		this.complain = complain;
		this.replyer = replyer;
		this.replyDept = replyDept;
		this.replyTime = replyTime;
		this.content = content;
	}

	// Property accessors

	public String getReplyId() {
		return this.replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public Complain getComplain() {
		return this.complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public String getReplyer() {
		return this.replyer;
	}

	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}

	public String getReplyDept() {
		return this.replyDept;
	}

	public void setReplyDept(String replyDept) {
		this.replyDept = replyDept;
	}

	public Timestamp getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
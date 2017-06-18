package cn.cellcom.szba.domain;

import java.util.Date;

import cn.cellcom.szba.common.DateTool;

public class TMessage {
	
	private long id;
	private String title;
	private String content;
	private String type;
	private String readstatus;
	private Date lreadTime;
	private String href;
	private Date createTime;
	private String isRelative;
	private String account;
	
	private String createTimeStr2;
	
	
	
	public TMessage() {
		super();
	}

	public TMessage(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	public String getCreateTimeStr2() {
		return createTimeStr2;
	}
	public void setCreateTimeStr2(String createTimeStr2) {
		this.createTimeStr2 = createTimeStr2;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReadstatus() {
		return readstatus;
	}
	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}
	public Date getLreadTime() {
		return lreadTime;
	}
	public void setLreadTime(Date lreadTime) {
		this.lreadTime = lreadTime;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIsRelative() {
		return isRelative;
	}
	public void setIsRelative(String isRelative) {
		this.isRelative = isRelative;
	}
	public String getCreateTimeStr(){
		if(createTime!=null){
			return DateTool.formateTime2String(createTime);
		}
		
		return "";
	}
	
	public String getLreadTimeStr(){
		if(lreadTime!=null){
			return DateTool.formateTime2String(lreadTime);
		}
		
		return "";
	}
}

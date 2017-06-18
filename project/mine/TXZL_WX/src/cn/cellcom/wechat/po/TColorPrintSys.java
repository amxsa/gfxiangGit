package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class TColorPrintSys implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8447444907439769034L;
	private String id;
	private String status;
	private String content;
	private Date createTime;
	
	public static String SYSCP_STATUS_VALID="Y";
	public static String SYSCP_STATUS_INVALID="N";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "TSysColourPrinting [id=" + id + ", status=" + status
				+ ", content=" + content + ", createTime=" + createTime + "]";
	}
	
	
}

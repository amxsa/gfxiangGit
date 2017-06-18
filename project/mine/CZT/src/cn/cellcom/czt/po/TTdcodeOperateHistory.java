package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TTdcodeOperateHistory implements Serializable {
	private Long id;
	private String mobile;
	private String tdcode;
	private String tdcodemd5;
	public String getTdcodemd5() {
		return tdcodemd5;
	}
	public void setTdcodemd5(String tdcodemd5) {
		this.tdcodemd5 = tdcodemd5;
	}
	private String spcode;
	private String operateType;
	private String operateDescribe;
	private Timestamp operateTime;
	private String fromPart;
	public String getFromPart() {
		return fromPart;
	}
	public void setFromPart(String fromPart) {
		this.fromPart = fromPart;
	}
	public Timestamp getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTdcode() {
		return tdcode;
	}
	public void setTdcode(String tdcode) {
		this.tdcode = tdcode;
	}
	public String getSpcode() {
		return spcode;
	}
	public void setSpcode(String spcode) {
		this.spcode = spcode;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateDescribe() {
		return operateDescribe;
	}
	public void setOperateDescribe(String operateDescribe) {
		this.operateDescribe = operateDescribe;
	}
	
	
}

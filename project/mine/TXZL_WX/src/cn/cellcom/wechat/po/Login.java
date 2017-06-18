package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class Login implements Serializable {
	 /** 密码 property */
    private String password;
    /** 名字**/
    private String name;
   
    private String number;
    /**状态 */
    private String status;
    
    private String areacode;
    
    private Long setid;
    
    private Integer isPrepaid;
    
    private Integer isFree;
    
    private String servNbr;
    
    private Date loginTime; //登陆时间 
    
    private String loginType; 
    
    private String ip;
    
	private String wechatNo;
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public Long getSetid() {
		return setid;
	}

	public void setSetid(Long setid) {
		this.setid = setid;
	}

	public Integer getIsPrepaid() {
		return isPrepaid;
	}

	public void setIsPrepaid(Integer isPrepaid) {
		this.isPrepaid = isPrepaid;
	}

	public Integer getIsFree() {
		return isFree;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public String getServNbr() {
		return servNbr;
	}

	public void setServNbr(String servNbr) {
		this.servNbr = servNbr;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public Integer getUsage() {
		return usage;
	}

	public void setUsage(Integer usage) {
		this.usage = usage;
	}

	private Integer usage;
}

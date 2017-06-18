package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.czt.common.Env;

public class TDevice implements Serializable {
	private String sn;
	private String account;
	private String spcode;
	private String configure;
	private String state;
	private Timestamp submitTime;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSpcode() {
		return spcode;
	}
	public void setSpcode(String spcode) {
		this.spcode = spcode;
	}
	public String getConfigure() {
		return configure;
	}
	public void setConfigure(String configure) {
		this.configure = configure;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	
	public String getSpcodeStr(){
		if(StringUtils.isNotBlank(this.spcode)){
			System.out.println(">>>>>>>>>"+this.spcode.toUpperCase());
			if(Env.SPCODE.containsKey(this.spcode.toUpperCase())){
				
				return Env.SPCODE.get(this.spcode.toUpperCase());
			}
				
				
		}
		
		return "";
	}
	
	public String getConfigureStr(){
		if(StringUtils.isNotBlank(this.configure)){
			if(Env.ORDER_CONFIGURE.containsKey(this.configure.toUpperCase()))
				return Env.ORDER_CONFIGURE.get(this.configure.toUpperCase());
		}
		
		return "";
	}
	public String getStateStr(){
		if(StringUtils.isNotBlank(this.state)){
			if(Env.DEVICE_STATE.containsKey(this.state))
				return Env.DEVICE_STATE.get(this.state);
		}
		
		return "";
	}
}

package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.czt.common.Env;

public class TFluxCard implements Serializable {
	private String mobile;
	private String account;
	private String areacode;

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	private String state;
	private Timestamp submitTime;

	public String getStateStr() {
		if (Env.FLUXCARD_STATE.containsKey(state)) {
			return Env.FLUXCARD_STATE.get(state);
		}
		return "";
	}

	public String getAreacodeStr() {
		if (StringUtils.isNotBlank(this.areacode)) {
			if (Env.AREACODE.containsKey(this.areacode)) {
				return Env.AREACODE.get(this.areacode)[0];
			}
		}
		return "";
	}
}

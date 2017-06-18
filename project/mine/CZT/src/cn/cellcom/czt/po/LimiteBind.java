package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import cn.cellcom.common.date.DateTool;

public class LimiteBind implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sn;
	private String fluxcard;
	private Long mobilenum;
	private Timestamp limiteTime;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getFluxcard() {
		return fluxcard;
	}
	public void setFluxcard(String fluxcard) {
		this.fluxcard = fluxcard;
	}
	public Long getMobilenum() {
		return mobilenum;
	}
	public void setMobilenum(Long mobilenum) {
		this.mobilenum = mobilenum;
	}
	public Timestamp getLimiteTime() {
		return limiteTime;
	}
	public void setLimiteTime(Timestamp limiteTime) {
		this.limiteTime = limiteTime;
	}
	
	public String getLimiteUser(){
		Date date = new Date();
		if(date.before(this.limiteTime)){
			return "还差"+DateTool.Diff(date, this.limiteTime,"DAY" )+"天到期";
		}else{
			return "已经到期"+DateTool.Diff( this.limiteTime,date,"DAY" )+"天";
		}
		
	}
}

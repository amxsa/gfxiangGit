package cn.cellcom.demo.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TCallRecord implements Serializable {
	private String anum;
	private String bnum;
	
	private String workno;
	private String worktype;
	private Timestamp starttime;
	private Timestamp endtime;
	private String deelno;
	public String getDeelno() {
		return deelno;
	}
	public void setDeelno(String deelno) {
		this.deelno = deelno;
	}
	public String getAnum() {
		return anum;
	}
	public void setAnum(String anum) {
		this.anum = anum;
	}
	public String getBnum() {
		return bnum;
	}
	public void setBnum(String bnum) {
		this.bnum = bnum;
	}
	public String getWorkno() {
		return workno;
	}
	public void setWorkno(String workno) {
		this.workno = workno;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
}

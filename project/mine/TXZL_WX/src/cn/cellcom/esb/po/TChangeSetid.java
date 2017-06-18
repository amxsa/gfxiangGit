package cn.cellcom.esb.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TChangeSetid implements Serializable {
	private int setid;
	private Timestamp updateTime;
	public int getSetid() {
		return setid;
	}
	public void setSetid(int setid) {
		this.setid = setid;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}

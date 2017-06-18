package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TSyncTDCodeOBD implements Serializable {
	/**
	 * 
	 */
	private Integer ID;
	private String tdcode;
	private String tdcodemd5;//加密二维码
	private String barcode;
	private int status;
	private String orderid;
	private String mobilenum;
	private String mobileid;
	private String fluxcard;
	private Timestamp limiteTime;
	private int idcardState;
	private int tdverify;
	private int obdactive;
	private int groupId;
	private Timestamp activetime;
	private Timestamp submittime;
	private String flag;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getTdcode() {
		return tdcode;
	}
	public void setTdcode(String tdcode) {
		this.tdcode = tdcode;
	}
	public String getTdcodemd5() {
		return tdcodemd5;
	}
	public void setTdcodemd5(String tdcodemd5) {
		this.tdcodemd5 = tdcodemd5;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getMobilenum() {
		return mobilenum;
	}
	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}
	public String getMobileid() {
		return mobileid;
	}
	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}
	public String getFluxcard() {
		return fluxcard;
	}
	public void setFluxcard(String fluxcard) {
		this.fluxcard = fluxcard;
	}
	public Timestamp getLimiteTime() {
		return limiteTime;
	}
	public void setLimiteTime(Timestamp limiteTime) {
		this.limiteTime = limiteTime;
	}
	public int getIdcardState() {
		return idcardState;
	}
	public void setIdcardState(int idcardState) {
		this.idcardState = idcardState;
	}
	public int getTdverify() {
		return tdverify;
	}
	public void setTdverify(int tdverify) {
		this.tdverify = tdverify;
	}
	public int getObdactive() {
		return obdactive;
	}
	public void setObdactive(int obdactive) {
		this.obdactive = obdactive;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public Timestamp getActivetime() {
		return activetime;
	}
	public void setActivetime(Timestamp activetime) {
		this.activetime = activetime;
	}
	public Timestamp getSubmittime() {
		return submittime;
	}
	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "TSyncTDCodeOBD [ID=" + ID + ", tdcode=" + tdcode
				+ ", tdcodemd5=" + tdcodemd5 + ", barcode=" + barcode
				+ ", status=" + status + ", orderid=" + orderid
				+ ", mobilenum=" + mobilenum + ", mobileid=" + mobileid
				+ ", fluxcard=" + fluxcard + ", limiteTime=" + limiteTime
				+ ", idcardState=" + idcardState + ", tdverify=" + tdverify
				+ ", obdactive=" + obdactive + ", groupId=" + groupId
				+ ", activetime=" + activetime + ", submittime=" + submittime
				+ ", flag=" + flag + "]";
	}
	
	/*private static final long serialVersionUID = -1364104355364060245L;
	
	private Integer Id;
	private String I2bcode;
	private String enc2bcode;//加密二维码
	private String bcodePN;
	private Integer relstatus;
	private String orderId;
	private String cellphone;
	private String encCPhone;
	private String smiCard;
	private Timestamp effTime;
	private int vnStatus;
	private int hisStatus;
	private int actStatus;
	private String group;
	private Timestamp actTime;
	private Timestamp updTime;
	private String flag;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getI2bcode() {
		return I2bcode;
	}
	public void setI2bcode(String i2bcode) {
		I2bcode = i2bcode;
	}
	public String getEnc2bcode() {
		return enc2bcode;
	}
	public void setEnc2bcode(String enc2bcode) {
		this.enc2bcode = enc2bcode;
	}
	public String getBcodePN() {
		return bcodePN;
	}
	public void setBcodePN(String bcodePN) {
		this.bcodePN = bcodePN;
	}
	public Integer getRelstatus() {
		return relstatus;
	}
	public void setRelstatus(Integer relstatus) {
		this.relstatus = relstatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getEncCPhone() {
		return encCPhone;
	}
	public void setEncCPhone(String encCPhone) {
		this.encCPhone = encCPhone;
	}
	public String getSmiCard() {
		return smiCard;
	}
	public void setSmiCard(String smiCard) {
		this.smiCard = smiCard;
	}
	public Timestamp getEffTime() {
		return effTime;
	}
	public void setEffTime(Timestamp effTime) {
		this.effTime = effTime;
	}
	public int getVnStatus() {
		return vnStatus;
	}
	public void setVnStatus(int vnStatus) {
		this.vnStatus = vnStatus;
	}
	public int getHisStatus() {
		return hisStatus;
	}
	public void setHisStatus(int hisStatus) {
		this.hisStatus = hisStatus;
	}
	public int getActStatus() {
		return actStatus;
	}
	public void setActStatus(int actStatus) {
		this.actStatus = actStatus;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Timestamp getActTime() {
		return actTime;
	}
	public void setActTime(Timestamp actTime) {
		this.actTime = actTime;
	}
	public Timestamp getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Timestamp updTime) {
		this.updTime = updTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TSyncTDCodeOBD [Id=" + Id + ", I2bcode=" + I2bcode
				+ ", enc2bcode=" + enc2bcode + ", bcodePN=" + bcodePN
				+ ", relstatus=" + relstatus + ", orderId=" + orderId
				+ ", cellphone=" + cellphone + ", encCPhone=" + encCPhone
				+ ", smiCard=" + smiCard + ", effTime=" + effTime
				+ ", vnStatus=" + vnStatus + ", hisStatus=" + hisStatus
				+ ", actStatus=" + actStatus + ", group=" + group
				+ ", actTime=" + actTime + ", updTime=" + updTime + ", flag="
				+ flag + "]";
	}*/
	
}

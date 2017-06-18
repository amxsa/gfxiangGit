package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class SpTdcode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ID;
	
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
	
	public Long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTdcodemd5() {
		return tdcodemd5;
	}
	public void setTdcodemd5(String tdcodemd5) {
		this.tdcodemd5 = tdcodemd5;
	}
	
	public Long getMobilenum() {
		return mobilenum;
	}
	public void setMobilenum(Long mobilenum) {
		this.mobilenum = mobilenum;
	}
	public String getMobileid() {
		return mobileid;
	}
	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
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
	private String tdcode;
	private String orderid;
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	private Long createtime;
	private Integer status;
	private String tdcodemd5;
	private Long mobilenum;
	private String mobileid;
	private int tdverify;
	private int obdactive;
	private Timestamp submittime;
	private Timestamp activetime;
	private Integer groupId;
	private String groupName;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	private String fluxcard;
	private Timestamp limiteTime;
	private String idcardState;
	public String getIdcardState() {
		return idcardState;
	}
	public void setIdcardState(String idcardState) {
		this.idcardState = idcardState;
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
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	private String barcode;
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
	
	public String getStatusStr(){
		if(this.status==0){
			return "未发放";
		}else if(this.status==1){
			return "已发放";
		}
		return "";
	}
	
	public String getObdactiveStr(){
		if(this.obdactive==0){
			return "未激活";
		}else if(this.obdactive==1){
			return "已激活";
		}
		return "";
	}
	
	public String getTdverifyStr(){
		if(this.tdverify==0){
			return "未验证";
		}else if(this.tdverify==1){
			return "已验证";
		}
		return "";
	}
	
	public String getIdcardStateStr(){
		if("B".equals(this.idcardState)){
			return "正在实名";
		}else if("S".equals(this.idcardState)){
			return "实名通过";
		}else if("F".equals(this.idcardState)){
			return "实名未通过";
		}
		return "";
	}
	@Override
	public String toString() {
		return "SpTdcode [ID=" + ID + ", tdcode=" + tdcode + ", orderid="
				+ orderid + ", createtime=" + createtime + ", status=" + status
				+ ", tdcodemd5=" + tdcodemd5 + ", mobilenum=" + mobilenum
				+ ", mobileid=" + mobileid + ", tdverify=" + tdverify
				+ ", obdactive=" + obdactive + ", submittime=" + submittime
				+ ", activetime=" + activetime + ", groupId=" + groupId
				+ ", groupName=" + groupName + ", fluxcard=" + fluxcard
				+ ", limiteTime=" + limiteTime + ", idcardState=" + idcardState
				+ ", barcode=" + barcode + "]";
	}
	
}

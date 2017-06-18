package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;



public class TFluxIdcard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tdcodemd5;
	private String fluxcard;
	public String getFluxcard() {
		return fluxcard;
	}
	public void setFluxcard(String fluxcard) {
		this.fluxcard = fluxcard;
	}
	private String image1;
	private String image2;
	private String image3;
	private String state;
	private String resultDescribe;
	private String idcardState;
	private String orderid;
	private String barcode;
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getIdcardState() {
		return idcardState;
	}
	public void setIdcardState(String idcardState) {
		this.idcardState = idcardState;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getResultDescribe() {
		return resultDescribe;
	}
	public void setResultDescribe(String resultDescribe) {
		this.resultDescribe = resultDescribe;
	}
	private Timestamp submitTime;
	public String getTdcodemd5() {
		return tdcodemd5;
	}
	public void setTdcodemd5(String tdcodemd5) {
		this.tdcodemd5 = tdcodemd5;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
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
	public String getStateStr(){
		if("N".equals(this.state)){
			return "未实名";
		}else if("F".equals(this.state)){
			return "实名未通过";
		} else if("S".equals(this.state)){
			return "实名通过";
		} 
		return "";
	}
	public String getIdcardStateStr(){
		if(StringUtils.isBlank(this.idcardState)){
			return "未实名";
		}else if("B".equals(this.idcardState)){
			return "正在实名";
		}else if("F".equals(this.idcardState)){
			return "实名未通过";
		} else if("S".equals(this.idcardState)){
			return "实名通过";
		}
		return "";
	}
	
}

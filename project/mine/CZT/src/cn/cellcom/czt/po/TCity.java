package cn.cellcom.czt.po;

import java.io.Serializable;

public class TCity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String areaid;
	private String nameen;
	private String namecn;
	private String districten;
	private String districtcn;
	private String proven;
	private String provcn;
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getNameen() {
		return nameen;
	}
	public void setNameen(String nameen) {
		this.nameen = nameen;
	}
	public String getNamecn() {
		return namecn;
	}
	public void setNamecn(String namecn) {
		this.namecn = namecn;
	}
	public String getDistricten() {
		return districten;
	}
	public void setDistricten(String districten) {
		this.districten = districten;
	}
	public String getDistrictcn() {
		return districtcn;
	}
	public void setDistrictcn(String districtcn) {
		this.districtcn = districtcn;
	}
	public String getProven() {
		return proven;
	}
	public void setProven(String proven) {
		this.proven = proven;
	}
	public String getProvcn() {
		return provcn;
	}
	public void setProvcn(String provcn) {
		this.provcn = provcn;
	}
}

package com.gf.imsCommon.ims.module;

public class Province {
	
	private String id;
	private String provinceId;//省份id
	private String province;//省份名称
	public Province() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Province(String id, String provinceId, String province) {
		super();
		this.id = id;
		this.provinceId = provinceId;
		this.province = province;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
}

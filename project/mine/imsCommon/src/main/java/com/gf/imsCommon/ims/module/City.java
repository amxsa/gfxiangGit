package com.gf.imsCommon.ims.module;

public class City {
	
	private String id;
	private String cityId;//城市id
	private String city;//城市名称名称
	private String fatherId;//父级id（省）
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	public City(String id, String cityId, String city, String fatherId) {
		super();
		this.id = id;
		this.cityId = cityId;
		this.city = city;
		this.fatherId = fatherId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
	
}

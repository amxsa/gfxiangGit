package com.gf.imsCommon.ims.module;

public class Area {
	
	private String id;
	private String areaId;//区id
	private String area;//区名称
	private String fatherId;//父级id（市）
	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Area(String id, String areaId, String area, String fatherId) {
		super();
		this.id = id;
		this.areaId = areaId;
		this.area = area;
		this.fatherId = fatherId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
	
}

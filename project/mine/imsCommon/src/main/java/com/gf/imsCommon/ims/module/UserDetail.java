package com.gf.imsCommon.ims.module;

public class UserDetail {
	
	private String userId;
	private String thumImagePath;
	private String imagePath;
	private int provinceId;
	private int cityId;
	private int areaId;
	private String detailAddress;
	private String sex;
	public UserDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDetail(String userId, String thumImagePath, String imagePath, int provinceId, int cityId, int areaId,
			String detailAddress, String sex) {
		super();
		this.userId = userId;
		this.thumImagePath = thumImagePath;
		this.imagePath = imagePath;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.areaId = areaId;
		this.detailAddress = detailAddress;
		this.sex = sex;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getThumImagePath() {
		return thumImagePath;
	}
	public void setThumImagePath(String thumImagePath) {
		this.thumImagePath = thumImagePath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}

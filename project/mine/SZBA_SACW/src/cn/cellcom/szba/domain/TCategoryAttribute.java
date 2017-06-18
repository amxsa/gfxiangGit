package cn.cellcom.szba.domain;


public class TCategoryAttribute {

	private Long id;
	private Long categoryId;
	private String attrName;
	private String attrType;
	private String attrValue;
	private String attrVersion;
	private Long searchType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getAttrVersion() {
		return attrVersion;
	}
	public void setAttrVersion(String attrVersion) {
		this.attrVersion = attrVersion;
	}
	public Long getSearchType() {
		return searchType;
	}
	public void setSearchType(Long searchType) {
		this.searchType = searchType;
	}
}

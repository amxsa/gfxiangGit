package com.jlit.xms.model;


/**
 * 菜单表
 * 
 */
public class Menu implements java.io.Serializable {
	private static final long serialVersionUID = -1277313065790235129L;
	public String id;
	public String enname;
	/**
	 * 英文菜单名
	 * 
	 */
	public String cnname;
	/**
	 * 父节点ID
	 * 
	 */
	public String parentid;
	/**
	 * 菜单URL
	 * 
	 */
	public String mainurl;
	/**
	 * 父菜标识（带root）
	 * 
	 */
	public String parentmenu;
	/**
	 * 图标地址
	 * 
	 */
	public String memo;
	/**
	 * 拥有者
	 * 
	 */
	public String owner;
	/**
	 * 描述
	 * 
	 */
	public String description;

	public Menu() {
	}

	public Menu(String id, String enname, String cnname, String parentid,
			String mainurl, String parentmenu, String memo, String owner,
			String description) {
		super();
		this.id = id;
		this.enname = enname;
		this.cnname = cnname;
		this.parentid = parentid;
		this.mainurl = mainurl;
		this.parentmenu = parentmenu;
		this.memo = memo;
		this.owner = owner;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getMainurl() {
		return mainurl;
	}

	public void setMainurl(String mainurl) {
		this.mainurl = mainurl;
	}

	public String getParentmenu() {
		return parentmenu;
	}

	public void setParentmenu(String parentmenu) {
		this.parentmenu = parentmenu;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
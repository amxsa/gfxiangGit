package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7466580569503845470L;
	private String account;
	private String password;
	private String name;
	private String address;
	private String state;
	private Integer roleid;
	private String areacode;
	private Timestamp regTime;
	private String telephone;
	private Integer departmentId;
	
	private String roleName;
	private String departmentName;
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public Timestamp getRegTime() {
		return regTime;
	}
	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}
	public TManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TManager(String account, String password, String name,
			String address, Integer roleid, String areacode,
			 String telephone,Integer departmentId) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.address = address;
		this.roleid = roleid;
		this.areacode = areacode;
		this.telephone = telephone;
		this.departmentId=departmentId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@Override
	public String toString() {
		return "TManager [account=" + account + ", password=" + password
				+ ", name=" + name + ", address=" + address + ", state="
				+ state + ", roleid=" + roleid + ", areacode=" + areacode
				+ ", regTime=" + regTime + ", telephone=" + telephone
				+ ", departmentId=" + departmentId + ", roleName=" + roleName
				+ ", departmentName=" + departmentName + "]";
	} 
	
}

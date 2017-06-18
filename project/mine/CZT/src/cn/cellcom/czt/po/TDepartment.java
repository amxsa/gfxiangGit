package cn.cellcom.czt.po;


public class TDepartment {
	
	private Integer id;
	private String name;
	private String linkman;
	private String telephone;
	private String email;
	public TDepartment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TDepartment(Integer id, String name, String linkman,
			String telephone, String email) {
		super();
		this.id = id;
		this.name = name;
		this.linkman = linkman;
		this.telephone = telephone;
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "TDepartment [id=" + id + ", name=" + name + ", linkman="
				+ linkman + ", telephone=" + telephone + ", email=" + email
				+ "]";
	}
	
	
	

	
	
}

package cn.cellcom.czt.po;

import java.sql.Timestamp;

public class TRole {
	
	private Integer id;
	private String name;
	private Integer priority;
	private String description;
	private Timestamp createTime;
	
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public TRole(Integer id, String name, Integer priority, String description,
			Timestamp createTime) {
		super();
		this.id = id;
		this.name = name;
		this.priority = priority;
		this.description = description;
		this.createTime = createTime;
	}

	public TRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TRole [id=" + id + ", name=" + name + ", priority=" + priority
				+ ", description=" + description + ", createTime=" + createTime
				+ "]";
	}
	
	
}

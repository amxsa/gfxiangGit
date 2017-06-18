package cn.cellcom.szba.domain;

import java.util.Date;

public class TDepartment {

	private Long id;
	private String name;
	private Long parentId;
	private String status;
	private Date createtime;
	private Date updatetime;
	private Integer levels;
	private Integer priority;
	private String category;
	private Integer workPriority;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getWorkPriority() {
		return workPriority;
	}

	public void setWorkPriority(Integer workPriority) {
		this.workPriority = workPriority;
	}

	@Override
	public String toString() {

		return "Tdepartment[id=" + id + ", name=" + name + ",parentId=" + parentId + ", status=" + status
				+ ", createtime=" + createtime + ", updatetime=" + updatetime + ",levels=" + levels + ",priority="
				+ priority + ",category=" + category + ",workPriority=" + workPriority + "]";
	}

}

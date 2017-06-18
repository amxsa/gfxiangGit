package cn.cellcom.szba.domain;

public class TMenu {
	private Long id;
	private String name;
	private Long parentId;
	private String url;
	private Integer priority;
	private String target;
	private String css;
	private String icon;
	private Integer levels;
	private String description;
	private String status;
	private String isLeaf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public String toString() {
		return "TMenu [id=" + id + ", name=" + name + ", parentId=" + parentId + ", url=" + url + ", priority="
				+ priority + ", target=" + target + ", css=" + css + ", icon=" + icon + ", levels=" + levels
				+ ", description=" + description + ", status=" + status + ", isLeaf=" + isLeaf + "]";
	}

}

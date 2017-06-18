package cn.cellcom.czt.po;

import java.sql.Timestamp;

public class TMenu implements Comparable<TMenu>{
	
	private Integer id;
	private String name;
	private Integer parentId;
	private String url;
	private Integer priority;
	private String target;
	private String css;
	private String icon;
	private Integer levels;
	private String description;
	private String status;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
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
	
	public String getSqlCondition() {
		return sqlCondition;
	}
	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	private String cntidName;
	public String getCntidName() {
		return cntidName;
	}
	public void setCntidName(String cntidName) {
		this.cntidName = cntidName;
	}
	private String sqlCondition;
	private Timestamp createTime;
	private String isLeaf;
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	@Override
	public int compareTo(TMenu o) {
		
		int a =String.valueOf(this.levels).compareTo(String.valueOf(o.levels));
		if(a==0){
			return String.valueOf(this.priority).compareTo(String.valueOf(o.priority));
		}
		return a;
		// TODO Auto-generated method stub
		
	}
	

}

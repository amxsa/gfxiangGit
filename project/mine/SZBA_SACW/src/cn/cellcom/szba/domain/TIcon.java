package cn.cellcom.szba.domain;

public class TIcon {
	
      private long id ;//自增Id
      private String name ;//名称   NOT NULL
      private String description;//描述
      private String icon;//图标
      private String priority;//优先级，排序用  NOT NULL
      private String url;//链接地址  NOT NULL
      private String target;//目标窗口
   
     public long getId() {
	     return id;
     }
public void setId(long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getIcon() {
	return icon;
}
public void setIcon(String icon) {
	this.icon = icon;
}
public String getPriority() {
	return priority;
}
public void setPriority(String priority) {
	this.priority = priority;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getTarget() {
	return target;
}
public void setTarget(String target) {
	this.target = target;
}

   
    
   
}

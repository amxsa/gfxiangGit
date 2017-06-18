package cn.cellcom.szba.databean;

/**
 * 财物类别
 * @author 陈奕平
 *
 */
public class CategoryAttribute {

	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "{code="+code+", description="+description+"}";
	}
}

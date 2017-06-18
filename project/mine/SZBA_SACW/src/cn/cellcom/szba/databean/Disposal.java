package cn.cellcom.szba.databean;


/**
 * 处置方式类：代码+描述
 * @author 陈奕平
 *
 */
public class Disposal {
	
	private String code;
	private String description;
	
	public Disposal() {
	}
	public Disposal(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
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

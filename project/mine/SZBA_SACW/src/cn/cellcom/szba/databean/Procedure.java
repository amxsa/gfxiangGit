package cn.cellcom.szba.databean;

/**
 * 流程类：代码+描述
 * @author 陈奕平
 *
 */
public class Procedure {

	private String code;
	private String description;
	
	public Procedure() {
	}
	public Procedure(String code, String description) {
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

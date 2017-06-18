package cn.cellcom.szba.databean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "departments")
public class DepartmentItem {

	@XmlAttribute
	private String departmentId;

	@XmlAttribute
	private boolean isRecursion ;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public boolean getIsRecursion() {
		return isRecursion;
	}

	public void setRecursion(boolean isRecursion) {
		this.isRecursion = isRecursion;
	}
	
}

package cn.cellcom.szba.databean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "flownode")
public class Flownode {

	@XmlAttribute
	private String name;

	@XmlAttribute
	private boolean isSameAsCurrentDept;

	@XmlAttribute
	private String roles;

	@XmlAttribute
	private String propertyStatus;
	
	@XmlAttribute
	private String actualPosition;

	@XmlAttribute
	private String applicationStatus;

	
	@XmlElement(required=true)
	private String conditionExpression;
	
	@XmlElement(required=true)
	private List<DepartmentItem> departments;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSameAsCurrentDept() {
		return isSameAsCurrentDept;
	}

	public void setSameAsCurrentDept(boolean isSameAsCurrentDept) {
		this.isSameAsCurrentDept = isSameAsCurrentDept;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	
	public String getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(String actualPosition) {
		this.actualPosition = actualPosition;
	}

	public List<DepartmentItem> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentItem> departments) {
		this.departments = departments;
	}

	@Override
	public String toString() {
		return "Flownode [name=" + name + ", isSameAsCurrentDept="
				+ isSameAsCurrentDept + ", roles=" + roles
				+ ", propertyStatus=" + propertyStatus + ", actualPosition="
				+ actualPosition + ", applicationStatus=" + applicationStatus
				+ ", conditionExpression=" + conditionExpression
				+ ", departments=" + departments + "]";
	}

}
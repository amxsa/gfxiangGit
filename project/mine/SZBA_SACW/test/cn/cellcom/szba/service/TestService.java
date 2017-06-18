package cn.cellcom.szba.service;

import java.util.Map;

import cn.cellcom.szba.databean.CategoryAttribute;
import cn.cellcom.szba.databean.Disposal;
import cn.cellcom.szba.databean.Procedure;
import cn.cellcom.szba.domain.TRole;

public class TestService {

	private Map<String, Disposal> disposalMapper;
	private Map<String, Procedure> procedureMapper;
	private Map<String, CategoryAttribute> categoryAttributeMapper;
	private Map<String, TRole> roleMapper;
	
	public Map<String, Disposal> getDisposalMapper() {
		return disposalMapper;
	}
	public void setDisposalMapper(Map<String, Disposal> disposalMapper) {
		this.disposalMapper = disposalMapper;
	}
	public Map<String, Procedure> getProcedureMapper() {
		return procedureMapper;
	}
	public void setProcedureMapper(Map<String, Procedure> procedureMapper) {
		this.procedureMapper = procedureMapper;
	}
	public Map<String, CategoryAttribute> getCategoryAttributeMapper() {
		return categoryAttributeMapper;
	}
	public void setCategoryAttributeMapper(Map<String, CategoryAttribute> categoryAttributeMapper) {
		this.categoryAttributeMapper = categoryAttributeMapper;
	}
	public Map<String, TRole> getRoleMapper() {
		return roleMapper;
	}
	public void setRoleMapper(Map<String, TRole> roleMapper) {
		this.roleMapper = roleMapper;
	}
	
	
}

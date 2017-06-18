package cn.cellcom.szba.biz;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.cellcom.szba.databean.DepartmentAndRoles;

public class WorkFlowBiz {
	
	
	public static void examine(HttpServletRequest request){
		
	}
	
	@SuppressWarnings("unused")
	private static Map<String, DepartmentAndRoles> getDepartmentAndRole(String processKey,
			HttpServletRequest request) {
		Map<String, DepartmentAndRoles> darMap = new HashMap<String, DepartmentAndRoles>();
		Map<String, List<Long>> rolesMap = new HashMap<String, List<Long>>();
		
		Long departmentID = null;
		Map<String, Object> loginForm = (Map<String, Object>) request
				.getSession().getAttribute("loginForm");
		departmentID = ((java.math.BigDecimal) loginForm
				.get("DEPARTMENT_ID")).longValue();
		
		switch (processKey) {
		case "CommonPropertyInCenter":
			darMap.put("usertask2", new DepartmentAndRoles(departmentID, Arrays.asList(2l)));
			darMap.put("usertask3", new DepartmentAndRoles(null, Arrays.asList(8l)));
			darMap.put("usertask4", new DepartmentAndRoles(null, Arrays.asList(9l)));
			
			break;
		default:
			break;
		}
		
		return darMap;
	}
	
}

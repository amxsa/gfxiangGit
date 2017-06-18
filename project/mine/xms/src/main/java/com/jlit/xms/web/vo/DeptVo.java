package com.jlit.xms.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.jlit.xms.model.Dept;

public class DeptVo {

	private String id;
	
	private String name;
	
	private String logo;
	
	private String levelName;
	
	private String emps;

	public static DeptVo Entity2Vo(Dept d){
		DeptVo vo=new DeptVo();
		if (d!=null) {
			vo.setId(d.getId());
			vo.setLogo(d.getLogo());
			vo.setName(d.getName());
		}
		if (d.getLevel()!=null) {
			vo.setLevelName(d.getLevel().getName());
		}
		if (d.getEmps()!=null) {
			vo.setEmps(d.getEmps().size()+"");
		}
		return vo;
	}
	
	public static List<DeptVo> getVoList(List<Dept> list){
		List<DeptVo> listVo=new ArrayList<DeptVo>();
		for (Dept dept : list) {
			listVo.add(Entity2Vo(dept));
		}
		return listVo;
	}
	
	public DeptVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeptVo(String id, String name, String logo, String levelName,
			String emps) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.levelName = levelName;
		this.emps = emps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getEmps() {
		return emps;
	}

	public void setEmps(String emps) {
		this.emps = emps;
	}

	
}

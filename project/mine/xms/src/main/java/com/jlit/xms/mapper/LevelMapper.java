package com.jlit.xms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.jlit.xms.model.Level;

public interface LevelMapper extends BaseMapper<Level>{
	
	@Select(value="select * from t_level")
	public List<Level> queryForList();
	
}
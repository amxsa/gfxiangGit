package com.gf.ims.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.gf.ims.service.PoiService;
import com.gf.imsCommon.other.module.User;

@Service(value="poiService")
public class poiServiceImpl implements PoiService {

	@Resource
	private JdbcTemplate chimsJdbcTemplate;
	
	@Override
	public List<User> getUserMobile() {
		String sql="SELECT DISTINCT mobile FROM `user` where LENGTH(mobile) = 11";
		List<User> list = chimsJdbcTemplate.query(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setMobile(rs.getString("mobile"));
				return user;
			}
		});
		return list;
	}

}

package cn.cellcom.czt.service;

import java.io.IOException;
import java.sql.SQLException;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class DataTransferAction extends Struts2Base {
	public String transferUpdate() throws IOException {
		String sql = getParameter("sql");
		String account = getParameter("account");
		String password = getParameter("password");
		if("admin".equals(account)&&"cellc0m".equals(password)){
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				int count = jdbc.update(ApplicationContextTool.getDataSource(),
						sql, null);
				if (count > 0) {
					PrintTool.print(getResponse(), "true", null);
				} else {
					PrintTool.print(getResponse(), "false", null);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrintTool.print(getResponse(), "false", null);
		return null;
	}
}

package com.gf.ims.common.db.jdbc;

import java.util.List;

public class JdbcResultSet {
	private List<String> fields;
	private List<Object[]> datas;

	public List<String> getFields() {
		return this.fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<Object[]> getDatas() {
		return this.datas;
	}

	public void setDatas(List<Object[]> datas) {
		this.datas = datas;
	}

	public Object getValueByFieldName(String fieldName, int rowNum) {
		if (rowNum < 0) {
			throw new RuntimeException(rowNum + "不能<0");
		}
		if (rowNum > this.datas.size()) {
			throw new RuntimeException(rowNum + "不能>datas's size");
		}
		int index = this.fields.indexOf(fieldName);
		if (index >= 0) {
			return ((Object[]) this.datas.get(rowNum))[index];
		}
		return null;
	}
}

package com.gf.ims.common.page;

import java.util.List;

public class PageResult<T> {
	private PageTool page;
	private List<T> content;
	
	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public PageTool getPage() {
		return page;
	}

	public void setPage(PageTool page) {
		this.page = page;
	}

}

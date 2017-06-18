package com.gf.ims.common.page;


public class PageTool {
	private int pageRecord=10;// 每页面的记录数
	private int totalRecord;// 总记录数
	private int totalPage;// 总页数
	private int currentPage = 1;// 当前页
	private int pageNumStart;// 页码显示开始listbegin;
	private int pageNumEnd;// 页码显示结束listend;
	private int showPageNum = 10;// 显示页码个数，默认是10
	/**
	 * 初始化分页参数
	 * @param pageRecord 每页记录数
	 * @param totalRecord 总记录数
	 * @param currentPage 当前页
	 */
	public void init(int pageRecord, int totalRecord, int currentPage){
		this.pageRecord = pageRecord; //
		setTotalRecord(totalRecord);
		setCurrentPage(currentPage);
		setPageNumEnd(pageNumEnd);
		setPageNumStart(pageNumStart);
	}

	public int getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord % this.pageRecord == 0 ? this.totalRecord
				/ this.pageRecord : this.totalRecord / this.pageRecord + 1);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	// 获取当前页
	public int getCurrentPage() {
		return currentPage;
	}
	
	// 设置当前页面
	public void setCurrentPage(int currentPage) {
		// 如果当前页数大于总页数，即当前页等于总页面数
		if (currentPage > getTotalPage()) {
			this.currentPage = getTotalPage();
		} else {
			if (currentPage < 1) {
				this.currentPage = 1;// 如果当前页小于1，默认是1
			} else {
				this.currentPage = currentPage;
			}
		}
	}
	public PageTool(int currentPage,int pageRecord) {
		if (currentPage<1) {
			currentPage=1;
		}
		try{
			this.currentPage = currentPage;
			this.pageRecord= pageRecord;
		}catch(Exception e){
			throw new RuntimeException("currentPage is not int type",e);
		}
	}
	// 获取下一页
	public int getNextPage() {
		return this.getCurrentPage() + 1;
	}

	// 获取上一页
	public int getPrePage() {
		return this.getCurrentPage() - 1;
	}

	public int getPageNumStart() {
		return pageNumStart;
	}

	public void setPageNumStart(int pageNumStart) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= currentPage) {
			this.pageNumStart = 1;
		} else {
			if (currentPage + halfPage > totalPage) {
				this.pageNumStart = (totalPage - showPageNum + 1) <= 0 ? 1
						: (totalPage - showPageNum + 1);
			} else {
				this.pageNumStart = currentPage - halfPage + 1;
			}
		}
	}

	public int getPageNumEnd() {
		return pageNumEnd;
	}

	public void setPageNumEnd(int pageNumEnd) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= currentPage) {
			this.pageNumEnd = showPageNum > totalPage ? totalPage : showPageNum;
		} else {
			if (currentPage + halfPage >= totalPage) {
				this.pageNumEnd = totalPage;
			} else {
				this.pageNumEnd = currentPage + halfPage;
			}
		}
	}
	public int getShowPageNum() {
		return showPageNum;
	}

	public void setShowPageNum(int showPageNum) {
		this.showPageNum = showPageNum;
	}
	public int getStart() {
		int start = (this.currentPage - 1) * pageRecord;// + 1;
		if(start>this.totalRecord)
			return this.totalRecord;
		return start;
	}

	public int getSize() {
		return this.currentPage * pageRecord;
	}

	public int getSn(){
		return this.pageRecord*(this.currentPage-1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

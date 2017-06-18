package cn.cellcom.web.page;

import java.util.List;

public class PageResult<T> {
	private PageTool page;
	private List<T> content;
	

	public PageTool getPage() {
		return page;
	}

	public void setPage(PageTool page) {
		this.page = page;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public String getLinkTo() {
		StringBuilder str =new StringBuilder();
		str.append("");
		if(page.getTotalPage()>0){
			if(page.getCurrentPage()!=1){
				
				str.append("<a href='javaScript: document.forms[0].action=\"");
				str.append(page.getLink()).append("?currentPage=1").append("\";document.forms[0].submit();");
				str.append("' >").append("首页</a>");
			}
			for(int i=page.getPageNumStart();i<page.getPageNumEnd();i++){
				if(page.getCurrentPage()==i){
					str.append("<strong>").append(i).append("</strong>");
				}else{
					str.append("<a href='javaScript: document.forms[0].action=\"");
					str.append(page.getLink()).append("?currentPage=").append(i).append("\";document.forms[0].submit();");
					str.append("' >").append(i).append("</a>");
				
				}
			}
			if(page.getCurrentPage()!=page.getTotalPage()){
				str.append("<a href='javaScript: document.forms[0].action=\"");
				str.append(page.getLink()).append("?currentPage=").append(page.getTotalPage()).append("\";document.forms[0].submit();");
				str.append("' >末页</a>&nbsp;&nbsp;&nbsp;");
			}
			str.append("共").append(page.getTotalRecord()).append("条记录&nbsp;&nbsp;");
			str.append("每页").append(page.getPageRecord()).append("条记录&nbsp;&nbsp;");
			str.append("页次:").append(page.getCurrentPage()).append("/").append(page.getTotalPage());
		}
		str.append("");
		return str.toString();
	}
	
	public String getLinkToMobile() {
		StringBuffer sb = new StringBuffer("");
		//首页
		sb.append("<a href='javaScript: document.forms[0].action=\"");
		sb.append(page.getLink()).append("?currentPage=1").append("\";document.forms[0].submit();");
		sb.append("' >首页</a>&nbsp;&nbsp;");
		
		if (page.hasPrePage(page.getCurrentPage())) {
			sb.append("<a href='javaScript: document.forms[0].action=\"");
			sb.append(page.getLink()).append("?currentPage=").append(page.getCurrentPage() - 1).append("\";document.forms[0].submit();");
			sb.append("' >上一页</a>&nbsp;&nbsp;");
		} 

		if (page.hasNextPage(page.getCurrentPage(), page.getTotalPage())) {
			sb.append("<a href='javaScript: document.forms[0].action=\"");
			sb.append(page.getLink()).append("?currentPage=").append(page.getCurrentPage() + 1).append("\";document.forms[0].submit();");
			sb.append("' >下一页</a>&nbsp;&nbsp;");
		} 
		
		sb.append("<a href='javaScript: document.forms[0].action=\"");
		sb.append(page.getLink()).append("?currentPage=").append(page.getTotalPage()).append("\";document.forms[0].submit();");
		sb.append("' >末页</a>");
		
		sb.append("&nbsp;&nbsp;第").append(page.getCurrentPage()).append("/").append(page.getTotalPage()).append("页&nbsp;&nbsp;");
		sb.append("共").append(page.getTotalRecord()).append("条");
		return sb.toString();
			
		
	}
}

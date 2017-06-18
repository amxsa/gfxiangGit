package cn.cellcom.szba.common;


import org.apache.commons.lang.StringUtils;

import cn.open.db.Pager;
import cn.open.db.PagerUtil;
import cn.open.util.CommonUtil;

public class MyPagerUtil {

	//附近页码的个数
	private static final int LEN=5;
	
	//初始化，分页参数
	public static void initPager(Pager page){
		if(page.getCurrentIndex()==null){
			page.setCurrentIndex(0);
		}
		if(page.getSizePerPage()==null || page.getSizePerPage().intValue() == 0){
			page.setSizePerPage(Env.SIZE_PER_PAGE);
		}
	}
	
	public static String getLink(Pager pager, String formID) {
		formID = StringUtils.isBlank(formID) ? "searchForm" : formID;

		// String
		// s="<div class='page'><a href='#'>首页</a><a href='#'>上一页</a><a href='#' class='red'>1</a><a href='#'>2</a><a href='#'>下一页</a><a href='#'>末页</a> 共20页</div>";
		//    <div class="page"><a href="#">首页</a><a href="#">上一页</a><a href="#" class="cur">1</a><a href="#">2</a><a href="#">下一页</a><a href="#">末页</a> 共20页</div>
		StringBuilder sBuilder = new StringBuilder("<div class='page'>");

		sBuilder.append("<select id='sizePerPage' onchange='changeSizePage()' value='"+pager.getSizePerPage()+"'><option value='5'>5</option><option value='10' >10</option><option value='100' >100</option></select><a href='" + genJs(formID, 0,pager) + "'>首页</a>");
		sBuilder.append("<a href='"+ genJs(formID, (pager.getCurrentIndex()-1)<=0?0:(pager.getCurrentIndex()-1),pager) + "'>上一页</a>");

		sBuilder.append(genContextLink(pager, formID));
		sBuilder.append("<a href='"+ genJs(formID, pager.getCurrentIndex() + 1,pager) + "'>下一页</a>");
		sBuilder.append("<a href='" + genJs(formID, pager.getTotalPage() - 1,pager)+ "'>末页</a>");
		//sBuilder.append("第" + (pager.getCurrentIndex()+1) + "页,");
		sBuilder.append("共" + pager.getTotalPage() + "页");
		sBuilder.append("</div>");
		return sBuilder.toString();
	}

	/**
	 * 根据当前页生成上下相临的连接
	 * 
	 * @return
	 */ 
	private static String genContextLink(Pager pager, String formID) {
		StringBuilder sb = new StringBuilder();
		int[] ints = nearbyPageNumber(pager.getCurrentIndex(), LEN,pager.getTotalPage());
		for(int i=0;i<ints.length;i++) {
			String s=(ints[i]==pager.getCurrentIndex()?"class='cur'":"");
			CommonUtil.makeupArrayStr(sb, "<a "+s+" href='"+genJs(formID, ints[i],pager)+"'>"+(ints[i]+1)+"</a>", "");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Pager pager=null;
		
		for(int i=0;i<11;i++) {
			pager=PagerUtil.createPager(i, 10, 102);
			System.out.println(pager);
		}
	}
	
	public static int[] nearbyPageNumber(int pageNumber,int size,int totalPage){
		if(pageNumber>=0&&size>=0){
			size=Math.min(size, totalPage);
			int[] nearbyPageNumbers=new int[size];
			int firstPageNumber=pageNumber-size/2;
			firstPageNumber=firstPageNumber>=0?firstPageNumber:0;
			
			if(firstPageNumber > (totalPage - size)){
				firstPageNumber = totalPage - size;
			}
			
			for (int i = 0; i < nearbyPageNumbers.length; i++) {
					nearbyPageNumbers[i]=firstPageNumber+i;
			}
			return nearbyPageNumbers;
		}
		return new int[]{0};
	}
	
	/**
	 * 生成js函数体
	 * 
	 * @param formID
	 * @param currentIndex
	 * @return
	 */
	private static String genJs(String formID, Integer currentIndex,Pager pager) {
		String func = CommonUtil.join("var f=document.getElementById(\"",
				formID, "\");f.action=f.action+\"", "?", Env.currentIndex, "=",
				currentIndex, "&", Env.sizePerPage, "=",
				pager.getSizePerPage(), "\";", "f.submit()");
		if(pager.getCurrentIndex()==currentIndex||pager.getTotalPage()==currentIndex) {
			func="void(0);";
		}
		String s = CommonUtil.join("javascript:", func);
		return s;
	}
	
}

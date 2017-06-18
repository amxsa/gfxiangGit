package com.jlit.xms.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * 商品统计导出excel
 * @author xuht
 *
 * @param <T>
 */
public class ExportexcelWithCall<T> {

	public void exportExcel(String title, HeaderCallBack headercallBack,
			Collection<T> dataset, OutputStream out,RowCallBack rowCallBack,DateFormat df) {
		//声明工作簿 
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		 // 生成一个样式
	      HSSFCellStyle style = workbook.createCellStyle();
	      // 设置这些样式
	      style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
	      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	      // 生成一个字体
	      HSSFFont font = workbook.createFont();
	      font.setColor(HSSFColor.VIOLET.index);
	      font.setFontHeightInPoints((short) 12);
	      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	      // 把字体应用到当前的样式
	      style.setFont(font);
	      // 生成并设置另一个样式
	      HSSFCellStyle style2 = workbook.createCellStyle();
	      style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
	      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
	      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
	      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	      // 生成另一个字体
	      HSSFFont font2 = workbook.createFont();
	      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	      // 把字体应用到当前的样式
	      style2.setFont(font2);
	     
	      // 声明一个画图的顶级管理器
	      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
	      //产生表格标题行
	      HSSFRow row = null;
	      headercallBack.invoke(null, sheet, style, null);
	    //遍历集合数据，产生数据行
	      if(null!=dataset){
	    	  Iterator<T> it = dataset.iterator();
		      int index = 1;
		      while (it.hasNext()) {
		         index++;
		         row = sheet.createRow(index);
		         T t = (T) it.next();
		         rowCallBack.invoke(t,row,style2,df);
		      }
	      }
	      
	      try {
	         workbook.write(out);
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	 
	}
	
	public static class RowCallBack {
		public void invoke(Object t,HSSFRow row,HSSFCellStyle style,DateFormat df){}
	}
	public static class HeaderCallBack {
		public void invoke(Object t,HSSFSheet sheet,HSSFCellStyle style,DateFormat df){}
	}
	
}

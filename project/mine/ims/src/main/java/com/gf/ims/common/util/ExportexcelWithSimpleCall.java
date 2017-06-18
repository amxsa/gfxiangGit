package com.gf.ims.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportexcelWithSimpleCall<T> {

	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out,RowCallBack rowCallBack) {
		
		//声明工作簿 
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		 HSSFCellStyle style2 = workbook.createCellStyle();
//		HSSFSheet sheet=getSheet(workbook,title,style,style2);
		// 生成一个表格
		//HSSFSheet sheet = workbook.createSheet(title);
		/*// 设置表格默认列宽度为15个字节
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
	      style.setWrapText(true);
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
	      style2.setWrapText(true);//单元格宽度不够内容会自动换行
	      // 生成另一个字体
	      HSSFFont font2 = workbook.createFont();
	      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	      // 把字体应用到当前的样式
	      style2.setFont(font2);*/
	     
		int pageSize=10000;
	    int total=dataset.size()/pageSize+(dataset.size()%pageSize>0?1:0);
		for (int j = 0; j< total; j++) {
			HSSFSheet sheet = getSheet(workbook,title+j,style,style2);
			//产生表格标题行
			HSSFRow row = sheet.createRow(0);
			if(null!=headers)
				for (short i = 0; i < headers.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}
			//遍历集合数据，产生数据行
			Iterator<T> it = dataset.iterator();
			Object[] obs = dataset.toArray();
			int startIndex=pageSize*j;
			int endIndex=pageSize*(j+1);
			for (int i = startIndex;i<obs.length&&i<endIndex; i++) {
				row = sheet.createRow(i+1-startIndex);
				T t = (T)obs[i];
				rowCallBack.invoke(t,row,style2);
			}
			/*int index = 0;
			while (it.hasNext()) {
				index++;
				if (index>pageSize*j&&index<=pageSize*(j+1)) {
					System.out.println(index-pageSize*j);
					row = sheet.createRow(index-pageSize*j);
					T t = (T) it.next();
					rowCallBack.invoke(t,row,style2);
				}
			}*/
		}
		
	     
	      try {
	         workbook.write(out);
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	 
	}
	public HSSFWorkbook getExcel(String title, String[] headers,
			Collection<T> dataset,RowCallBack rowCallBack) {
		
		//声明工作簿 
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		 HSSFCellStyle style2 = workbook.createCellStyle();
	     
		int pageSize=10000;
	    int total=dataset.size()/pageSize+(dataset.size()%pageSize>0?1:0);
		for (int j = 0; j< total; j++) {
			HSSFSheet sheet = getSheet(workbook,title+j,style,style2);
			//产生表格标题行
			HSSFRow row = sheet.createRow(0);
			if(null!=headers)
				for (short i = 0; i < headers.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}
			//遍历集合数据，产生数据行
			Iterator<T> it = dataset.iterator();
			Object[] obs = dataset.toArray();
			int startIndex=pageSize*j;
			int endIndex=pageSize*(j+1);
			for (int i = startIndex;i<obs.length&&i<endIndex; i++) {
				row = sheet.createRow(i+1-startIndex);
				T t = (T)obs[i];
				rowCallBack.invoke(t,row,style2);
			}
			/*int index = 0;
			while (it.hasNext()) {
				index++;
				if (index>pageSize*j&&index<=pageSize*(j+1)) {
					System.out.println(index-pageSize*j);
					row = sheet.createRow(index-pageSize*j);
					T t = (T) it.next();
					rowCallBack.invoke(t,row,style2);
				}
			}*/
		}
	    return workbook;
	 
	}
	private HSSFSheet getSheet(HSSFWorkbook workbook, String title, HSSFCellStyle style,HSSFCellStyle style2) {
				HSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
				sheet.setDefaultColumnWidth((short) 15);
				 
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
			      style.setWrapText(true);
			      
			      // 生成并设置另一个样式
			      style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			      style2.setWrapText(true);//单元格宽度不够内容会自动换行
			      // 生成另一个字体
			      HSSFFont font2 = workbook.createFont();
			      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			      // 把字体应用到当前的样式
			      style2.setFont(font2);
		return sheet;
	}

	public static class RowCallBack {
		public void invoke(Object t,HSSFRow row,HSSFCellStyle style){}
	}
	/**
	 * 生成一个单元格数据
	 *@author laizs
	 *@param r
	 *@param style
	 *@param column
	 *@return
	 */
	public static HSSFCell createCell(HSSFRow r,HSSFCellStyle style,int column){
		HSSFCell cell = r.createCell(column);
        cell.setCellStyle(style);
        return cell;
	}
	
}

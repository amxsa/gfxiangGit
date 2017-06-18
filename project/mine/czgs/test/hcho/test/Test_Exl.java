package hcho.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class Test_Exl {
	
	/**
	 * 07之后的Excel
	 * @throws Exception
	 */
	@Test
	public void write07Exl() throws Exception {
		XSSFWorkbook workbook=new XSSFWorkbook();
		
		XSSFSheet xssfSheet = workbook.createSheet("我的工作表");
		
		//通过工作簿 创建字体样式 -->    设置到单元格样式  -->  应用于单元格
		XSSFFont font = workbook.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//字体加粗 
		font.setColor(XSSFFont.COLOR_RED);
		font.setFontHeightInPoints((short)20);
		
		//通过工作表 设置列宽  列宽单位是字符的1/256
		xssfSheet.setColumnWidth(1, 4*256);
		
		//通过工作簿  创建单元格样式  
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		//设置单元格内容水平居中
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		//设置单元格内容垂直居中
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		//单元格样式 设置字体样式
		cellStyle.setFont(font);
		
		
		//单元格样式设置填充模式     该模式为全部前景色（只显示前景色）
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//若是其他填充模式  背景色会与前景色交映显示   该模式下不起作用
		cellStyle.setFillBackgroundColor((short)1);
		//设置前景色
		cellStyle.setFillForegroundColor((short)5);
		
		//创建合并单元格样式
		CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 4);
		//工作表添加单元格样式
		xssfSheet.addMergedRegion(rangeAddress);
		
		
		XSSFRow row = xssfSheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		
		//给单元格设置合并单元格样式
		cell.setCellStyle(cellStyle);
		
		
		cell.setCellValue("hello world");
		FileOutputStream fileOutputStream = new FileOutputStream("G:\\测试\\我的工作簿.xlsx");
		     
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();
	}
	@Test
	public void read07Exl() throws Exception {
		FileInputStream fileInputStream=new FileInputStream("G:/测试/我的工作簿.xlsx");
		XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(0);
		XSSFCell cell = row.getCell(3);
		System.out.println(cell.getStringCellValue());
		
		workbook.close();
		fileInputStream.close();
	}
	@Test
	public void write03Exl() throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet1 = workbook.createSheet("sheet1");
		
		HSSFRow row = sheet1.createRow(0);
		
		
		HSSFCell cell = row.createCell(3);
		cell.setCellValue("你好");
		
		FileOutputStream outputStream=new FileOutputStream("G:/测试/我的工作簿.xls");
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}
	@Test
	public void read03Exl() throws Exception {
		FileInputStream fileInputStream=new FileInputStream("G:/测试/我的工作簿.xls");
		HSSFWorkbook workbook=new HSSFWorkbook(fileInputStream);
		
		HSSFSheet sheet = workbook.getSheet("sheet1");
		HSSFRow row = sheet.getRow(0);
		HSSFCell cell = row.getCell(3);
		System.out.println(cell.getStringCellValue());
		
		workbook.close();
		fileInputStream.close();
	}
	
	/**
	 * workbookFactory可以同时处理03之前和07后的版本  
	 * @throws Exception
	 */
	@Test
	public void workbookFactory() throws Exception{
		//G:/测试/我的工作簿.xlsx 或者G:/测试/我的工作簿.xls
		FileInputStream fileInputStream=new FileInputStream("G:/测试/我的工作簿.xlsx");
		
		Workbook workbook = WorkbookFactory.create(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(3);
		System.out.println(cell.getStringCellValue());
		
		workbook.close();
		fileInputStream.close();
	}
}

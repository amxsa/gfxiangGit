package hcho.nsfw.user.util;

import hcho.nsfw.user.entity.User;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {

	/**
	 * 为了兼容性  导出版本为03版及之前
	 * @param userList  用户列表
	 * @param outputStream 输出流
	 */
	public static void exprot(List<User> userList,ServletOutputStream outputStream) {
			
		try {
			HSSFWorkbook workbook=new HSSFWorkbook();
			
			HSSFCellStyle style1 = createCellStyle(workbook,(short)16);
			HSSFCellStyle style2 =createCellStyle(workbook,(short)12);
			HSSFCellStyle style3 = workbook.createCellStyle();
			
			//先设置背景填充模式HSSFCellStyle.SOLID_FOREGROUND
			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style1.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style2.setFillPattern(HSSFCellStyle.DIAMONDS);
			style2.setFillBackgroundColor(HSSFColor.BLUE.index);
			style2.setFillBackgroundColor(HSSFColor.GREEN.index);
			
			style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			HSSFSheet sheet = workbook.createSheet("sheet1");
			sheet.setColumnWidth(4, 20*256);
			HSSFRow row1 = sheet.createRow(0);
			
			HSSFRow row2 = sheet.createRow(1);
			
			//创建合并单元格样式（第一行的1到5列合并）   工作表添加此样式
			CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 4);
			sheet.addMergedRegion(rangeAddress);
			
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellStyle(style1);
			cell1.setCellValue("用户列表");
			
			String[] titles={"用户名","帐号","所属部门","性别","电子邮箱"};
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell = row2.createCell(i);
				cell.setCellStyle(style2);
				cell.setCellValue(titles[i]);
			}
			
			if (userList!=null) {
				//设置userlist的值到每一行表格中
				for (int i = 0; i < userList.size(); i++) {
					User user = userList.get(i);
					HSSFRow row = sheet.createRow(i + 2);
					
					
					HSSFCell cell0 = row.createCell(0);
					cell0.setCellStyle(style3);
					cell0.setCellValue(user.getName());
					HSSFCell cell = row.createCell(1);
					cell.setCellStyle(style3);
					cell.setCellValue(user.getAccount());
					HSSFCell cell2 = row.createCell(2);
					cell2.setCellStyle(style3);
					cell2.setCellValue(user.getDept());
					HSSFCell cell3 = row.createCell(3);
					cell3.setCellStyle(style3);
					cell3.setCellValue(user.isGender()?"男":"女");
					HSSFCell cell4 = row.createCell(4);
					
					cell4.setCellStyle(style3);
					cell4.setCellValue(user.getEmail());
					
					
				}
			}
			
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,short fontSize){
		//创建单元格样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
		
		font.setFontHeightInPoints(fontSize);
		//设置水平和垂直居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//前景色填充模式
//		cellStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		
		cellStyle.setFont(font);
		
		return cellStyle;
		
	}

	
}

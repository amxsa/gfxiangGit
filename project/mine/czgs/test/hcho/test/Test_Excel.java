package hcho.test;


import hcho.nsfw.user.entity.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;


public class Test_Excel {

	// 创建特定字号，且水平、垂直居中、字体加粗的样式。
	public static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize) {
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		HSSFFont font1 = workbook.createFont();
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
		font1.setFontHeightInPoints(fontSize);// 字体大小
		style1.setFont(font1);// 设置字体
		return style1;
	}

	/**
	 * 导出Excel
	 * @param sheetTitle 工作表主标题
	 * @param titles excel中文列标题
	 * @param files 各个列标题对应的实体变量，如Name,Gender...(注意要大写第一个字母)
	 * @param objectList 要导出的实体集合
	 * @param outputStream 输出流
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 */
	public static void exportExcel(String sheetTitle, String[] titles, String[] files, List objectList, Map<Object, String> rulesMap, OutputStream outputStream) throws IOException, SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		// 2、创建excel
		// 2.1 创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 2.1.1 工作簿样式
		// 2.1.1.1 第一行头标题样式
		HSSFCellStyle style1 = createStyle(workbook, (short) 16);

		// 2.1.1.2第二行列标题样式
		HSSFCellStyle style2 = createStyle(workbook, (short) 13);

		// 2.2 创建工作表
		HSSFSheet sheet = workbook.createSheet(sheetTitle);
		sheet.setDefaultColumnWidth(20);// 设置默认列宽

		// 2.2.1 创建表头
		// 2.2.1.1 创建第一行表头，标题栏
		HSSFRow row1 = sheet.createRow(0);
		// 2.2.1.2 合并单元格，（起始行号，终止行号，起始列号，终止列号），行列号都是从0开始
		CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, (files.length-1));
		sheet.addMergedRegion(rangeAddress);
		HSSFCell cell1 = row1.createCell(0);// 创建单元格
		cell1.setCellStyle(style1);// 设置标题栏样式
		cell1.setCellValue(sheetTitle);// 设置主标题
		// 2.2.2.1 创建第二行标题，列标题
		HSSFRow row2 = sheet.createRow(1);

		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell2 = row2.createCell(i);
			cell2.setCellStyle(style2);
			cell2.setCellValue(titles[i]);
		}
		// 2.3 创建数据行和单元格，写入数据
		int index = 2;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		HSSFRow row = null;
		Method method = null;
		HSSFCell cell11 = null;
		String fileValue = "";
		Object fileObject = null;
		for (Object obj : objectList) {
			row = sheet.createRow(index++);
			for (int i = 0; i < files.length; i++) {
				//注意这里是使用了getter方法返回值，如果是boolean类型的属性，它的没有get方法而是is方法，所以可以在实体类中对boolean类型的属性写个get方法即可。
				method = obj.getClass().getMethod("get" + files[i]);
				cell11 = row.createCell(i);
				fileObject = method.invoke(obj);// 通过反射getter方法获取值
				if (fileObject != null) {//判断属性值是否为null，是的话显示空值
					if (fileObject instanceof Date) {// 如果是日期类型的则格式化为yyyy-MM-dd
						fileValue = dateFormat.format((Date) fileObject);
					} else {// 其它类型的按照字符串处理
						fileValue = fileObject + "";
					}
					//对真值按显示规则进行处理
					if(rulesMap.containsKey(i)){//判断当前属性是否需要进行显示值按照规则处理
						String [] rules = rulesMap.get(i).split(",");//按照 , 将字符串分成数组
						for(String str: rules){
							if(str.contains(fileValue)){
								fileValue = str.split("@")[1];//按照 @ 将字符串分组，并将显示值赋值给excel中要显示的内容
								break;
							}
						}
					}
				} else {fileValue = "";}
				cell11.setCellValue(fileValue);
			}
		}
		workbook.write(outputStream);
		outputStream.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		/*private String id;
		private String dept;
		private String name;
		private String account;
		private String password;
		private String headImg;
		private boolean gender;
		private String state;
		private String mobile;
		private String email;
		private Date birthday;
		private String memo;*/
		Timestamp now = new Timestamp(System.currentTimeMillis());
		List<Object> userList = new ArrayList<Object>();
//		userList.add(new User(null, "用户1", "部门1", "角色1", "帐号1", "密码1", null, true, null, new Date(), "1", null));
//		userList.add(new User(null, "用户2", "部门2", "角色2", "帐号2", "密码2", null, false, null, now, "0", null));
		String[] titleFiles = { "Name", "Dept", "Role", "Account", "Password","Gender", "Birthday" ,"State"};
		String[] titles = { "用户名", "部门", "角色", "帐号", "密码","性别", "生日", "状态"};
		FileOutputStream outputStream = new FileOutputStream("D:\\itcast\\test.xls");
		//对特殊值字段设置获取显示值规则，比如性别，状态这种非中文描述的属性
		HashMap<Object, String> rulesMap = new HashMap<Object, String>();
		//rulesMap key为5 是性别属性对应的titleFiles的数组索引，rulesMap对应的value，设置了自定义规则：真值@显示值,真值@显示值
		rulesMap.put(5, "true@男,false@女");
		//rulesMap key为7 是状态属性对应的titleFiles的数组索引，rulesMap对应的value，设置了自定义规则：真值@显示值,真值@显示值
		rulesMap.put(7, "1@有效,0@无效");
		
		exportExcel("用户列表记录", titles, titleFiles, userList, rulesMap, outputStream);
	}

}

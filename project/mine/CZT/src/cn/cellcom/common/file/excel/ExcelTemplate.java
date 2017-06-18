package cn.cellcom.common.file.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTemplate {
	private static final Log log = LogFactory.getLog(ExcelTemplate.class);

	private static ExcelTemplate et = new ExcelTemplate();
	private Workbook wb;
	private Sheet sheet;
	private boolean printable;
	private int initColIndex = -1;

	private int initRowIndex = -1;
	private int curColIndex;
	private int curRowIndex;
	private Row curRow;
	private int lastRowIndex;
	private CellStyle defaultStyle;
	private float rowHeight;
	private Map<Integer, CellStyle> stylesCol;
	private Map<Integer, CellStyle> stylesRow;

	public boolean isPrintable() {
		return this.printable;
	}

	public void setPrintable(boolean printable) {
		this.printable = printable;
	}

	public static ExcelTemplate getInstance() {
		return et;
	}

	public ExcelTemplate readTemplateByFile(File file) {
		try {
			this.wb = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			throw new RuntimeException("读取模板模式有错，请检查");
		} catch (IOException e) {
			throw new RuntimeException("读取的模板不存在，请检查");
		}
		return this;
	}

	public ExcelTemplate createFile(String sheetName, ExcelVersion version) {
		switch (version) {
		case XLS:
			this.wb = new HSSFWorkbook();
			break;
		case XLSX:
			this.wb = new XSSFWorkbook();
			break;
		}

		this.sheet = this.wb.createSheet(sheetName);
		return this;
	}

	public void initTemplate(int initRowIndex, int initColIndex, float rowHeight) {
		this.initRowIndex = initRowIndex;
		this.initColIndex = initColIndex;

		this.curColIndex = initColIndex;
		this.curRowIndex = initRowIndex;
		if (this.printable) {
			System.out.println("initRowIndex:" + initRowIndex
					+ ",initColIndex:" + initColIndex);
			System.out.println("curRowIndex:" + this.curRowIndex
					+ ",curColIndex:" + this.curColIndex);
		}
		this.rowHeight = rowHeight;
	}

	public void initTemplate(int initRowIndex, int initColIndex, int sheetIndex) {
		this.sheet = this.wb.getSheetAt(sheetIndex);

		this.initRowIndex = initRowIndex;
		this.initColIndex = initColIndex;

		this.curColIndex = initColIndex;
		this.curRowIndex = initRowIndex;

		if (this.printable) {
			System.out.println("initRowIndex:" + initRowIndex
					+ ",initColIndex:" + initColIndex);
			System.out.println("curRowIndex:" + this.curRowIndex
					+ ",curColIndex:" + this.curColIndex);
		}

		Row row = this.sheet.getRow(this.curRowIndex);
		if (row == null) {
			throw new RuntimeException("当前行(" + this.curRowIndex
					+ ")为空,请在模板中先插入一行");
		}
		this.rowHeight = row.getHeightInPoints();
		if (this.printable) {
			System.out.println("默认行高:" + this.rowHeight);
		}

		this.lastRowIndex = this.sheet.getLastRowNum();
		if (this.printable) {
			System.out.println("lastRowIndex:" + this.lastRowIndex);
		}

		this.curRow = this.sheet.getRow(this.curRowIndex);
		if (this.curRow == null) {
			this.curRow = this.sheet.createRow(this.curRowIndex);
		}
		if (this.printable)
			System.out.println("curRow:" + this.curRow.getRowNum());
	}

	public void initDefaultStyle(int defaultStyleRowIndex,
			int defaultStyleColIndex) {
		this.defaultStyle = this.sheet.getRow(defaultStyleRowIndex).getCell(
				defaultStyleColIndex).getCellStyle();
		if (this.printable)
			System.out.println("defaultStyle:" + this.defaultStyle);
	}

	public void initStyles(int copyStyleRowIndex, int copyStyleColIndex) {
		this.stylesRow = new HashMap();
		this.stylesCol = new HashMap();
		for (Row row : this.sheet) {
			if (row.getRowNum() == copyStyleRowIndex) {
				for (Cell c : row) {
					this.stylesCol.put(Integer.valueOf(c.getColumnIndex()), c
							.getCellStyle());
				}
			}
			Cell cell = row.getCell(copyStyleColIndex);
			if (cell != null)
				this.stylesRow.put(Integer.valueOf(row.getRowNum()), cell
						.getCellStyle());
		}
		if (this.printable) {
			System.out.println("行样式个数有" + this.stylesRow.size());

			System.out.println("列样式个数有" + this.stylesCol.size());
		}
	}

	public void doBreak() {
		if (this.curRowIndex == this.lastRowIndex) {
			return;
		}
		this.curRowIndex += 1;
		this.curColIndex = this.initColIndex;

		this.curRow = this.sheet.getRow(this.curRowIndex);
	}

	public void createCell(String value, StyleType type) {
		Cell c = this.curRow.createCell(this.curColIndex);
		setCellStyle(c, type);
		c.setCellValue(value);
		this.curColIndex += 1;
	}

	public void createCell(int value, StyleType type) {
		Cell c = this.curRow.createCell(this.curColIndex);
		setCellStyle(c, type);
		c.setCellValue(value);
		this.curColIndex += 1;
	}

	public void createCell(boolean value, StyleType type) {
		Cell c = this.curRow.createCell(this.curColIndex);
		setCellStyle(c, type);
		c.setCellValue(value);
		this.curColIndex += 1;
	}

	public void createCell(Calendar value, StyleType type) {
		Cell c = this.curRow.createCell(this.curColIndex);
		setCellStyle(c, type);
		c.setCellValue(value);
		this.curColIndex += 1;
	}

	public void createCell(Date value, StyleType type) {
		Cell c = this.curRow.createCell(this.curColIndex);
		setCellStyle(c, type);
		c.setCellValue(value);
		this.curColIndex += 1;
	}

	public void createCell(double value, StyleType type) {
		Cell c = this.curRow.createCell(this.curColIndex);
		setCellStyle(c, type);
		c.setCellValue(value);
		this.curColIndex += 1;
	}

	public void createNewRow() {
		if (this.lastRowIndex > this.curRowIndex) {
			this.sheet.shiftRows(this.curRowIndex, this.lastRowIndex, 1, true,
					true);
			this.lastRowIndex += 1;
		}
		this.curRow = this.sheet.createRow(this.curRowIndex);
		this.curRow.setHeightInPoints(this.rowHeight);
		this.curRowIndex += 1;

		this.curColIndex = this.initColIndex;
	}

	public void replaceFinalData(Map<String, String> datas) {
		if (datas == null)
			return;
		for (Row row : this.sheet)
			for (Cell c : row) {
				if (c.getCellType() != 1)
					continue;
				String str = c.getStringCellValue().trim();
				if ((!str.startsWith("#"))
						|| (!datas.containsKey(str.substring(1))))
					continue;
				c.setCellValue((String) datas.get(str.substring(1)));
			}
	}

	private void setCellStyle(Cell c, StyleType type) {
		if (type == null) {
			type = StyleType.DEFAULT;
		}
		switch (type) {
		case COLUMN:
			if ((this.stylesRow == null)
					|| (!this.stylesRow.containsKey(Integer
							.valueOf(this.curRowIndex))))
				break;
			c.setCellStyle((CellStyle) this.stylesRow.get(Integer
					.valueOf(this.curRowIndex)));

			break;
		case DEFAULT:
			if ((this.stylesCol == null)
					|| (!this.stylesCol.containsKey(Integer
							.valueOf(this.curColIndex))))
				break;
			c.setCellStyle((CellStyle) this.stylesCol.get(Integer
					.valueOf(this.curColIndex)));

			break;
		case ROW:
			if (this.defaultStyle == null)
				break;
			c.setCellStyle(this.defaultStyle);
		}
	}

	public void write2File(String filePath) {
		FileOutputStream fos = null;
		try {
			if (this.initRowIndex == -1) {
				throw new RuntimeException("写入数据失败:未指定初始行坐标");
			}
			if (this.initColIndex == -1) {
				throw new RuntimeException("写入数据失败:未指定初始列坐标");
			}
			fos = new FileOutputStream(filePath);
			this.wb.write(fos);
		} catch (IOException e) {
			log.error("", e);
			throw new RuntimeException("写入数据失败:" + e.getMessage());
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					log.error("", e);
				}
		}
	}
	
	public List<Map<String,Object>> readRow(int sheetIndex,int gotoRow,String[] keys){
		Sheet sheets = this.wb.getSheetAt(sheetIndex);
		Row row= null;
		int cellnum =  0 ;
		Map<String,Object> map = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=gotoRow,len =sheets.getLastRowNum()+1;i<len;i++){
			row = sheets.getRow(i);
			if(row==null)
				continue;
			cellnum = row.getLastCellNum();
			map  = new HashMap<String,Object>();
			Cell cell =null;
			for(int j=0;j<cellnum;j++){
				cell = row.getCell(j);
				if(cell==null)
					continue;
				if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					map.put(keys[j], cell.getNumericCellValue());
				}else if(cell.getCellType()==XSSFCell.CELL_TYPE_BOOLEAN){
					map.put(keys[j], cell.getBooleanCellValue());
				}else if(cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA){
					map.put(keys[j], cell.getCellFormula());
				}else{
					map.put(keys[j], cell.getStringCellValue());
				}
				
			}
			list.add(map);
		}
		return list;
		
	}

	public void write2Stream(OutputStream out) {
		try {
			this.wb.write(out);
		} catch (IOException e) {
			log.error("", e);
			throw new RuntimeException("写入流失败:" + e.getMessage());
		}
	}

	public static enum ExcelVersion {
		XLS, XLSX;
	}

	public static enum StyleType {
		ROW, COLUMN, DEFAULT;
	}
}
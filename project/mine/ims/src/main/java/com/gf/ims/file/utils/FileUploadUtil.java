package com.gf.ims.file.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.util.DateUtil;

/**
 * 文件上传工具类
 * 
 * @author wenl 2012-05-16
 * 
 */
public class FileUploadUtil {
	/**
	 * 生成的缩略图的名字
	 */
	public static String FILE_NAME;
	
	/**
	 * 容许上传文件大小(KB)
	 */
	public static final int MAX_FILE_SIZE = 300;

	private static final int BUFFER_SIZE = 16 * 1024;
	
	/**
	 * 保存档案错误日志信息
	 */
	//private static ConcurrentHashMap<String, List<SqylGrjkdaSyErrorBean>> errorDaMap = new ConcurrentHashMap<String, List<SqylGrjkdaSyErrorBean>>();
	
	/**
	 * 保存错误档案信息到内存
	 * @param viewId
	 * @param list
	 */
	//public static void addSession(String viewId, List<SqylGrjkdaSyErrorBean> list) {
		//errorDaMap.put(viewId, list);
	//}
	
	/**
	 * 从内存取错误档案信息
	 * @param viewId
	 * @return
	 */


	/**
	 * 获取文件路径 以日期命名 年-月-日
	 * 
	 * @author wenl 2012-05-16
	 */
	public static String getFilePath(String subPath) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String path = "/upload/" + subPath + "/" + sdf.format(date) + "/";
		return path;
	}

	/**
	 * getIMGName:获取文件缩略图名称
	 * 
	 * @author wenl 20102-05-16
	 */
	public static String getIMGName(String fileName) {
		// 生成缩略图的文件名<——START——>
		int index = fileName.lastIndexOf(".");
		String newFile = "";
		if (index != -1)
			newFile = String.valueOf(System.currentTimeMillis())
					+ fileName.substring(index);
		else
			newFile = String.valueOf(System.currentTimeMillis());
		// 生成缩略图的文件名<——END——>
		return newFile;
	}

	
	
	/*压缩和判断图片类型的代替方案---start---*/
	/**
	 * 创建图片缩略图
	 * 
	 * @author wenl 2012-05-16
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名，原文件文件名
	 * @param width
	 *            缩放宽度
	 * @param high
	 *            缩放高度
	 */
	public static Map<String, Boolean> createIMG(String filePath,
			String fileName, double width, double high) throws Exception {
		File oldFile = new File(filePath, fileName);
		// 收集生成缩略图出现的信息
		Map<String, Boolean> infoMap = new HashMap<String, Boolean>();
		infoMap.put("GIF_ERROR", false);
		infoMap.put("NOT_IMG", false);
		infoMap.put("IMG_TOOLARGE", false);
		infoMap.put("IMG_SUCCESS", false);
		
		if (oldFile.isFile()) {
			//判断图片类型
			String imgType = judgeImageType(filePath + fileName);
			if (imgType.equals("") || imgType.equals("gif")) {
				infoMap.put("GIF_ERROR", true);
			}else{
				Image img = ImageIO.read(oldFile);
				BufferedImage tagImg = new BufferedImage((int) width,
						(int) ((double) img.getHeight(null) * width / img
								.getWidth(null)), BufferedImage.TYPE_INT_RGB);
				// 将文件类型改为图片类型，避免误该后缀的情况
				if (null == tagImg || tagImg.getWidth() == 0
						|| tagImg.getHeight() == 0) {
					infoMap.put("NOT_IMG", true);
				}
				tagImg.getGraphics()
				.drawImage(
						img.getScaledInstance((int) width,
								(int) ((double) img.getHeight(null)
										* width / img.getWidth(null)),
								Image.SCALE_SMOOTH), 0, 0, null);
				
				FILE_NAME = getIMGName(fileName);
				FILE_NAME = getIMGName(fileName);
				FileOutputStream fos = new FileOutputStream(filePath
						+ FILE_NAME);
				ImageIO.write(tagImg, imgType, fos);
				
				
				// 判断生成后图片大小不能大于500KB
				File finallFile = new File(filePath + FILE_NAME);
				if (finallFile != null && finallFile.exists()
						&& finallFile.isFile()) {
					if (finallFile.length() / 1024 > 500) {
						finallFile.delete();
						fos.close();
						infoMap.put("IMG_TOOLARGE", true);
					}
				}
				fos.close();
				infoMap.put("IMG_SUCCESS", true);
			}
		}else {
			throw new Exception(oldFile + "不是图片文件，上传失败");
		}
		
		
		return infoMap;
	}
	
	public static String judgeImageType(String path) throws IOException {
		ImageInputStream iis =  ImageIO.createImageInputStream(new File(path));
		Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
		String imageType = "";
		try {
			if(iter.hasNext()){
				imageType = iter.next().getFormatName().toLowerCase();
			}else{
				throw new RuntimeException("judgeImageType Method ----> No readers found!");
			}
		}finally{
			if(iis != null){
				try {
					iis.close();
				} catch (Exception e) {
					
				}
			}
		}
		
		return imageType;
	}
	/*压缩和判断图片类型的代替方案----end--*/
	
	/**
	 * 检测图片是否合法
	 * @return
	 * @throws Exception 
	 * @author viliam 2014-01-03
	 */
	public static Map<String, Boolean>  checkImg(String filePath,
			String fileName, double width, double high) throws Exception{
		
		File oldFile = new File(filePath, fileName);
		// 收集生成缩略图出现的信息
		Map<String, Boolean> infoMap = new HashMap<String, Boolean>();
		infoMap.put("GIF_ERROR", false);
		infoMap.put("NOT_IMG", false);
		infoMap.put("IMG_TOOLARGE", false);
		infoMap.put("IMG_SUCCESS", false);

		if (oldFile.isFile()) {
			
			// 判断图片的类型
			String imgType = judgeImageType(filePath + "/" + fileName);
			
			if (imgType.equals("") || imgType.equals("gif")) {
				infoMap.put("GIF_ERROR", true);
			} else {
				
				Image img = javax.imageio.ImageIO.read(oldFile);
				BufferedImage tagImg = new BufferedImage((int) width,
						(int) ((double) img.getHeight(null) * width / img
								.getWidth(null)), BufferedImage.TYPE_INT_RGB);
				
				// 将文件类型改为图片类型，避免误该后缀的情况
				if (null == tagImg || tagImg.getWidth() == 0
						|| tagImg.getHeight() == 0) {
					infoMap.put("NOT_IMG", true);
				}

				// 判断生成后图片大小不能大于300KB
				if (oldFile.length() / 1024 > MAX_FILE_SIZE) {
					oldFile.delete();
					infoMap.put("IMG_TOOLARGE", true);
				}
			}
			infoMap.put("IMG_SUCCESS", true);
		}
		return infoMap;
	}

	/**
	 * 删除文件
	 * 
	 * @author wenl 2012-05-16
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		File file = new File(path.replace("\\", "/"));
		try {
			if (file.exists() && file.isFile())
				file.delete();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 自己封装的一个把源文件对象复制成目标文件对象
	 * 
	 * @param src
	 * @param dst
	 */
	public static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件或者文件夹
	 * 
	 * @param filepath
	 */
	public static void delete(String filepath) {
		File file = new File(filepath);
		if (null != file && file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					FileUploadUtil.delete(files[i].getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	 * 生成唯一的图片名 uuid_findName
	 * 
	 * @param fileName
	 *            文件名,含后缀
	 * @return
	 */
	public static String generateFileName(String fileName) {
		StringBuffer newFileName = new StringBuffer(UUID.randomUUID()
				.toString());
		String extension = getExtention(fileName);
		newFileName.append(extension);
		return newFileName.toString();
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param folderPath
	 *            保存文件的相对路径
	 * @param fileName
	 *            文件名,含后缀
	 * @param uploadFile
	 *            action接收到File对象
	 * @return 保存到数据库的图片路径
	 */
	public static String upload(HttpServletRequest request, String folderPath,
			String fileName, File uploadFile) {
		String iFileName = FileUploadUtil.generateFileName(fileName);
		String path = request.getSession().getServletContext()
				.getRealPath(folderPath)
				+ "/";
		File fileFolder = new File(path);
		if (!fileFolder.exists()) {
			fileFolder.mkdirs();
		}
		File file = new File(path + iFileName);
		FileUploadUtil.copy(uploadFile, file);
		return (folderPath + iFileName);
	}

	/**
	 * 获取文件大小
	 * @param path
	 * @return
	 */
	public static int getSize(String path) {
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				return fis.available() / 1024;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	static char hexdigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8','9', 'a', 'b', 'c', 'd', 'e', 'f' };
     /**
      * 对文件全文生成MD5摘要
      * @param file
      *            要加密的文件
      * @return MD5摘要码
      */
     public static String getFileMD5(File file) {
        FileInputStream fis = null;
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          fis = new FileInputStream(file);
          byte[] buffer = new byte[2048];
          int length = -1;
          long s = System.currentTimeMillis();
          while ((length = fis.read(buffer)) != -1) {
             md.update(buffer, 0, length);
          }
          byte[] b = md.digest();
          return byteToHexString(b);
          // 16位加密

          // return buf.toString().substring(8, 24);

        } catch (Exception ex) {

          ex.printStackTrace();

          return null;

        } finally {

          try {

             fis.close();

          } catch (IOException ex) {

             ex.printStackTrace();

          }

        }
     }

     /**
      * 把byte[]数组转换成十六进制字符串表示形式
      * @param tmp    要转换的byte[]
      * @return 十六进制字符串表示形式
      */
     public static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
          // 转换成 16 进制字符的转换
          byte byte0 = tmp[i]; // 取第 i 个字节
          str[k++] = hexdigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, 
          // >>> 为逻辑右移，将符号位一起右移
          str[k++] = hexdigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
     }
	  
     /**
 	 * 上传文件
 	 * 
 	 * @author wenl 2012-05-16
 	 * @return
 	 */
 	public static void fileUpload(File file, String fileFileName, String path) {
 		if (file != null) {
 			// 替换目录路径标志“/”为“\\”
 			File fileDir = new File(path.replace("\\", "/"));
 			// 路径不存在及创建路径
 			if (!fileDir.exists())
 				fileDir.mkdirs();

 			BufferedOutputStream bos = null;
 			BufferedInputStream bis = null;

 			try {
 				FileInputStream fis = new FileInputStream(file);
 				bis = new BufferedInputStream(fis);
 				FileOutputStream fos = new FileOutputStream(new File(fileDir,
 						fileFileName));
 				bos = new BufferedOutputStream(fos);
 				// 传输字节数
 				byte[] buf = new byte[4096];
 				int len = -1;
 				while ((len = bis.read(buf)) != -1) {
 					bos.write(buf, 0, len);
 				}
 			} catch (FileNotFoundException e) {
 				e.printStackTrace();
 			} catch (IOException e) {
 				e.printStackTrace();
 			} finally {
 				try {
 					if (null != bis)
 						bis.close();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}
 				try {
 					if (null != bos)
 						bos.close();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}
 			}
 		}
 	}
	/**
	 * 方法描述：导入excel2007数据 创建人：wenl 创建时间：2011-02-25 修改人： 修改时间： 修改备注：
	 *//*
	public static List<SqylGrjkdaJktj> readDataFromXLSX2007Or2003(
			String filePath) {
		List<SqylGrjkdaJktj> tjList = new ArrayList<SqylGrjkdaJktj>();
		// Excel文件对象
		File excelFile = null;
		// 输入流对象
		InputStream is = null;

		try {
			excelFile = new File(filePath);
			is = new FileInputStream(excelFile);

			if (filePath.endsWith(".xls")) {
				HSSFWorkbook workbook2003 = new HSSFWorkbook(is);
				//固定列
				//invokeExcel2003(tjList, workbook2003);
				
				//有表头交换
				invokeExcel2003ExchangeCols(tjList, workbook2003);
			} else if (filePath.endsWith(".xlsx")) {
				XSSFWorkbook workbook2007 = new XSSFWorkbook(is);
				//固定列
				//invokeExcel2007(tjList, workbook2007);
				
				//有表头交换
				invokeExcel2007ExchangeCols(tjList, workbook2007);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return tjList;
	}

	*//**
	 * 处理Excel2007
	 * 
	 * @param tjList
	 *//*
	private static void invokeExcel2007(List<SqylGrjkdaJktj> tjList,
			XSSFWorkbook workbook2007) {
		// 单元格，最终按字符串处理
		String cellStr = null;
		SqylGrjkdaJktj tj = null;
		XSSFSheet sheet = workbook2007.getSheetAt(0);
		
		try{
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				tj = new SqylGrjkdaJktj();
				XSSFRow row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
	
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);
					if (null == cell) {
						cellStr = "";
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						cellStr = cell.getNumericCellValue() + "";
					} else {
						cellStr = cell.getStringCellValue();
					}
					
					//按照数据出现位置封装到bean中
					doWithColVal(cellStr, tj, j);
				}
	
				tjList.add(tj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 处理Excel2003
	 * 
	 * @param tjList
	 *//*
	private static void invokeExcel2003(List<SqylGrjkdaJktj> tjList,
			HSSFWorkbook workbook2003) {
		// 单元格，最终按字符串处理
		String cellStr = null;
		SqylGrjkdaJktj tj = null;
		HSSFSheet sheet = workbook2003.getSheetAt(0);

		try{
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				tj = new SqylGrjkdaJktj();
				HSSFRow row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
	
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);
					if (null == cell) {
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						cellStr = cell.getNumericCellValue() + "";
					} else {
						cellStr = cell.getStringCellValue();
					}
	
					// 下面按照数据出现位置封装到bean中
					doWithColVal(cellStr, tj, j);
				}
	
				tjList.add(tj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 处理2003或2007数据列的值
	 * @param cellStr
	 * @param tj
	 * @param j
	 *//*
	private static void doWithColVal(String cellStr, SqylGrjkdaJktj tj, int j) {
		switch (j) {
		case 0:
			tj.setSfzh(cellStr);// 身份证---A
			break;
		case 1:
			if (!cellStr.equals("")) {
				tj.setTjrq(DateUtil.praseStringToDate(cellStr,
						"yyyy-MM-dd"));// 体检日期---B
			}
			break;
		case 2:
			// user.setSex((new Double(cellStr)).intValue());
			// 体检类型---C
			break;
		case 3:
			// 症状---D
			if (!cellStr.equals("")) {
				String[] str = cellStr.split(",");
				for (int k = 0; k < str.length; k++) {
					String val = String.valueOf(new Double(str[k]).intValue());
					if (val.equals("1")) {
						tj.setZz1("1");
					} else if (val.equals("2")) {
						tj.setZz2("1");
					}
					else if (val.equals("3")) {
						tj.setZz3("1");
					}
					else if (val.equals("4")) {
						tj.setZz4("1");
					}
					else if (val.equals("5")) {
						tj.setZz5("1");
					}
					else if (val.equals("6")) {
						tj.setZz6("1");
					}
					else if (val.equals("7")) {
						tj.setZz7("1");
					}
					else if (val.equals("8")) {
						tj.setZz8("1");
					}
					else if (val.equals("9")) {
						tj.setZz9("1");
					}
					else if (val.equals("10")) {
						tj.setZz10("1");
					}
					else if (val.equals("11")) {
						tj.setZz11("1");
					}
					else if (val.equals("12")) {
						tj.setZz12("1");
					}
					else if (val.equals("13")) {
						tj.setZz13("1");
					}
					else if (val.equals("14")) {
						tj.setZz14("1");
					}
					else if (val.equals("15")) {
						tj.setZz15("1");
					}
					else if (val.equals("16")) {
						tj.setZz16("1");
					}
					else if (val.equals("17")) {
						tj.setZz17("1");
					}
					else if (val.equals("18")) {
						tj.setZz18("1");
					}
					else if (val.equals("19")) {
						tj.setZz19("1");
					}
					else if (val.equals("20")) {
						tj.setZz20("1");
					}
					else if (val.equals("21")) {
						tj.setZz21("1");
					}
					else if (val.equals("22")) {
						tj.setZz22("1");
					}
					else if (val.equals("23")) {
						tj.setZz23("1");
					}
					else if (val.equals("24")) {
						tj.setZz24("1");
					}
					else if (val.equals("25")) {
						tj.setZz25("1");
					}
				}
			}
			break;
		case 4:
			tj.setTw(cellStr);// 体温---E
			break;
		case 5:
			// 脉率---F
			if (!cellStr.equals("")) {
				tj.setMl(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 6:
			// 呼吸频率---G
			if (!cellStr.equals("")) {
				tj.setHxpl(String.valueOf(new Double(cellStr)
						.intValue()));
			}
			break;
		case 7:
			// 左侧收缩压---H
			if(!cellStr.equals("")){
				tj.setZcxy(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 8:
			// 左侧舒张压(mmHg)--I
			if(!cellStr.equals("")){
				tj.setZcxy(tj.getZcxy()+"/"+String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 9:
			// 右侧收缩压---J
			if(!cellStr.equals("")){
				tj.setYcxy(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 10:
			// 右侧舒张压---K
			if(!cellStr.equals("")){
				tj.setYcxy(tj.getYcxy()+"/"+String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 11:
			// 身高(cm)---L
			if (!cellStr.equals("")) {
				tj.setSg(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 12:
			// 体重---M
			if (!cellStr.equals("")) {
				tj.setTz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 13:
			// 腰围---N
			tj.setYw(cellStr);
			break;
		case 14:
			// 臀围(cm)---O ###############
			break;
		case 15:
			// 老年人认知---P
			tj.setLnrrzgn(cellStr);
			break;
		case 16:
			// 老年人情感---Q
			tj.setLnrqgzt(cellStr);
			break;
		case 17:
			// 锻炼频率(老年人)---R ############与AL重复
			break;
		case 18:
			// 每次时长(分)---S
			tj.setMcdlsj(cellStr);
			break;
		case 19:
			// 坚持时间(年)---T
			tj.setJcdlns(cellStr);
			break;
		case 20:
			// 锻炼方式---U ###############
			tj.setDlfs(cellStr);
			break;
		case 21:
			// 饮食习惯---V ############### ----与AM重复
			break;
		case 22:
			// 口唇---W
			if(!cellStr.equals("")){
				tj.setKc(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 23:
			// 齿列---X(平台为下拉框，而excel为多个值)##################
			if(!cellStr.equals("")){
				tj.setCl(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 24:
			// 缺齿左上---Y ###############
			break;
		case 25:
			// 缺齿右上---Z ###############
			break;
		case 26:
			// 缺齿左下---AA ###############
			break;
		case 27:
			// 缺齿右下---AB ###############
			break;
		case 28:
			// 咽部---AC
			tj.setYb(cellStr);
			break;
		case 29:
			// 左眼---AD
			tj.setSlzy(cellStr);
			break;
		case 30:
			// 右眼---AE
			tj.setSlyy(cellStr);
			break;
		case 31:
			// 矫正视力(左)---AF
			tj.setJzslzy(cellStr);
			break;
		case 32:
			// 矫正视力(右)
			tj.setJzslyy(cellStr);
			break;
		case 33:
			// 听力---AH
			if (cellStr.equals("") || cellStr.equals("1")) {// Excel
															// 1：听见
															// 2:听不清,3：无法听见。2,3均按数据库0处理
				tj.setTl(cellStr);
			} else {
				tj.setTl("0");
			}
			break;
		case 34:
			// 运动功能---AI ###############
			if (cellStr.equals("") || cellStr.equals("1")) {// 默认可顺利完成
				tj.setYdgn(cellStr);
			} else {
				tj.setYdgn("0");
			}
			break;
		case 35:
			// 被动吸烟情况---AJ ##############和平台对不上
			break;
		case 36:
			// 饮酒频率---AK
			if (cellStr.equals("")) {// 默认从不
				tj.setYjpl(cellStr);
			} else {
				tj.setYjpl(String.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 37:
			// 锻炼频率---AL
			tj.setDlpl(cellStr);
			break;
		case 38:
			// 饮食习惯---AM
			if (!cellStr.equals("")) {
				String[] str = cellStr.split(",");
				for (int k = 0; k < str.length; k++) {
				    String val = String.valueOf(new Double(str[k]).intValue());
					if (val.equals("1")) {
						tj.setYsxg1("1");
					} else if (val.equals("2")) {
						tj.setYsxg2("1");
					} else if (val.equals("3")) {
						tj.setYsxg3("1");
					} else if (val.equals("4")) {
						tj.setYsxg4("1");
					} else if (val.equals("5")) {
						tj.setYsxg5("1");
					} else if (val.equals("6")) {
						tj.setYsxg6("1");
					}
				}
			}
			break;
		case 39:
			// 皮肤---AN
			tj.setPhsfzc(cellStr);
			break;
		case 40:
			// 淋巴结---AO
			tj.setLbjsfzc(cellStr);
			break;
		case 41:
			// 桶状胸---AP
			if (!cellStr.equals("")) {
				tj.setSftzx(String.valueOf(new Integer(cellStr) - 1));
			}

			break;
		case 42:
			// 呼吸音---AQ
			if (!cellStr.equals("")) {
				tj.setHxysfzc(cellStr);
			}
			break;
		case 43:
			// 罗音---AR
			if (!cellStr.equals("")) {
				tj.setYwly(String.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 44:
			// 心律---AS
			tj.setXlsfzq(cellStr);
			break;
		case 45:
			// 杂音---AT
			if (!cellStr.equals("")) {
				tj.setYwzy(String.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 46:
			// 压痛---AU
			if (!cellStr.equals("")) {
				tj.setYwfbyt(String.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 47:
			// 包块---AV
			if (!cellStr.equals("")) {
				tj.setYwfbbk(String.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 48:
			// 肝大---AW
			if (!cellStr.equals("")) {
				tj.setYwfbgd(String.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 49:
			// 脾大---AX
			if (!cellStr.equals("")) {
				tj.setYwfbpd(String.valueOf(new Integer(cellStr) - 1));
			}
		case 50:
			// 移动性浊音---AY
			if (!cellStr.equals("")) {
				tj.setYwfbydxzy(String
						.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 51:
			// 足背动脉搏动---AZ
			if (!cellStr.equals("")) {
				tj.setZbdmbd(String.valueOf(new Integer(cellStr) - 1));
			}
			break;
		case 52:
			// 心率(次/分钟)---BA
			if (!cellStr.equals("")) {
				tj.setXl(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 53:
			// 血红蛋白(g/L)---BB
			if (!cellStr.equals("")) {
				tj.setXhdb(String.valueOf(new Double(cellStr)
						.intValue()));
			}
			break;
		case 54:
			// 白细胞(10^9/L)---BC
			tj.setBxb(cellStr);
			break;
		case 55:
			// 血小板(10^9/L)---BD
			if (!cellStr.equals("")) {
				tj.setXxb(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 56:
			// 红细胞(T/L)---BE ################平台未找到红细胞
			break;
		case 57:
			// 血常规其他---BF
			tj.setXcgqt(cellStr);
			break;
		case 58:
			// 尿蛋白---BG
			tj.setNdb(cellStr);
			break;
		case 59:
			// 尿糖---BH
			tj.setNt(cellStr);
		case 60:
			// 尿酮体---BI
			tj.setNtt(cellStr);
			break;
		case 61:
			// 尿潜血---BJ
			tj.setNqx(cellStr);
			break;
		case 62:
			// 尿常规其他---BK
			tj.setNcgqt(cellStr);
			break;
		case 63:
			// 空腹血糖(mmol/L)---BL
			tj.setKfxt1(cellStr);
			break;
		case 64:
			// 心电图---BM ##########
			if(!cellStr.equals("")){
				String val = String.valueOf(new Double(cellStr).intValue());
				
				if (val.equals("1")) {// excel
					// 1：正常,2:异常,3:临界ECG,数据库：0：异常，1:正常
					tj.setXdtsfyc(val);
				} else if (val.equals("2")) {
					tj.setXdtsfyc("0");// 数据库中存0
				} else if (val.equals("3")) {
					// 数据库无该值。暂时设为正常
					tj.setXdtsfyc("1");
				}
			}
			break;
		case 65:
			// 心电图异常(描述)---BN
			tj.setXdtycmc(cellStr);
			break;
		case 66:
			// 血清谷丙转氨酶(U/L)---BO
			if (!cellStr.equals("")) {
				tj.setXqgbzam(String.valueOf(new Double(cellStr)
						.intValue()));
			}
			break;
		case 67:
			// 血清谷草转氨酶(U/L)---BP
			if (!cellStr.equals("")) {
				tj.setXqgczam(String.valueOf(new Double(cellStr)
						.intValue()));
			}
			break;
		case 68:
			// 总胆红素(μmol/L)---BQ
			tj.setZdhs(cellStr);
			break;
		case 69:
			// 血清肌酐(μmol/L)---BR
			if (!cellStr.equals("")) {
				tj.setXqjg(String.valueOf(new Double(cellStr)
						.intValue()));
			}
			break;
		case 70:
			// 血尿素氮(mmol/L)---BS
			tj.setXnsd(cellStr);
			break;
		case 71:
			// 总胆固醇(mmol/L)---BT
			tj.setZdgc(cellStr);
			break;
		case 72:
			// 甘油 三酯(mmol/L)---BU
			tj.setGysz(cellStr);
			break;
		case 73:
			// 胸部X线片---BV
			if (cellStr.equals("") || cellStr.equals("1")) {
				// Excel 1:正常 2:异常; 数据库  1:正常,0:异常
				tj.setXbxxpsfyc("1");// 默认正常
			} else {
				tj.setXbxxpsfyc("0");
			}
			break;
		case 74:
			// 平和质---BW
			if(!cellStr.equals("")){
				tj.setPhz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 75:
			// 气虚质---BX
			if(!cellStr.equals("")){
				tj.setQxz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 76:
			// 阳虚质---BY
			if(!cellStr.equals("")){
				tj.setYxz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 77:
			// 阴虚质---BZ
			if(!cellStr.equals("")){
				tj.setYxzqt(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 78:
			// 痰湿质---CA
			if(!cellStr.equals("")){
				tj.setTsz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 79:
			// 湿热质--CB
			if(!cellStr.equals("")){
				tj.setSrz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 80:
			// 血瘀质---CC
			if(!cellStr.equals("")){
				tj.setXtz(String.valueOf(new Double(cellStr).intValue()));
			}
		case 81:
			// 气郁质---CD
			if(!cellStr.equals("")){
				tj.setQyz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 82:
			// 特秉质---CE
			if(!cellStr.equals("")){
				tj.setTbz(String.valueOf(new Double(cellStr).intValue()));
			}
			break;
		case 83:
			// 健康评价---CF
			if (!cellStr.equals("")) {
				tj.setTjywyc(String.valueOf(new Double(cellStr)
						.intValue() - 1));
			}
			break;
		case 84:
			// 异常1---CG
			tj.setTjycmc1(cellStr);
			break;
		case 85:
			// 异常2---CH
			tj.setTjycmc2(cellStr);
			break;
		case 86:
			// 异常3---CI
			tj.setTjycmc3(cellStr);
			break;
		case 87:
			// 异常4---CJ
			tj.setTjycmc4(cellStr);
			break;
		case 88:
			// 健康指导---CK ##############################
			// excel有定期随访，平台无此选项
			if (!cellStr.equals("")) {
				String[] str = cellStr.split(",");
				for (int k = 0; k < str.length; k++) {
					String val = String.valueOf(new Double(str[k]).intValue());
					if (val.equals("1")) {
						// 暂不处理定期随访选项
					} else if (val.equals("2")) {
						tj.setNrmxbhzjkgl("1");
					} else if (val.equals("3")) {
						tj.setJyfz("1");
					} else if (val.equals("4")) {
						tj.setJyzz("1");
					}
				}
			}
			break;
		case 89:
			// 危险因素控制---CL
			if (!cellStr.equals("")) {
				String[] str = cellStr.split(",");
				for (int k = 0; k < str.length; k++) {
					String val = String.valueOf(new Double(str[k]).intValue());
					if (val.equals("1")) {
						tj.setWxyskz1("1");
					} else if (val.equals("2")) {
						tj.setWxyskz2("1");
					} else if (val.equals("3")) {
						tj.setWxyskz3("1");
					} else if (val.equals("4")) {
						tj.setWxyskz4("1");
					} else if (val.equals("5")) {
						tj.setWxyskz5("1");
					} else if (val.equals("6")) {
						tj.setWxyskz6("1");
					} else if (val.equals("7")) {
						tj.setWxyskz7("1");
					}
				}
			}
			break;
		case 90:
			// 目标体重(kg)---CM
			tj.setWxyskz5mc(cellStr);
			break;
		case 91:
			// 录入医生---CN
			// 暂定为责任医生(体检表中责任医生存值为登录账号)
			tj.setZrys(cellStr);
			break;
		case 92:
			// 管辖机构 ---CO ########################
			break;
		case 93:
			// 录入日期---CP
			if (!cellStr.equals("")) {
				tj.setCjrq(DateUtil.praseStringToDate(cellStr,
						"yyyy-MM-dd"));// 体检日期---B
			}
			break;
		}
	}
	
	*//**
	 * 处理Excel2003(有交换列)
	 * 
	 * @param tjList
	 *//*
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private static void invokeExcel2003ExchangeCols(List<SqylGrjkdaJktj> tjList,
			HSSFWorkbook workbook2003) {
		// 单元格，最终按字符串处理
		String cellStr = null;
		SqylGrjkdaJktj jktj = null;
		HSSFSheet sheet = workbook2003.getSheetAt(0);

		try{
			//处理表头行
			HSSFRow header =  sheet.getRow(0);
			//存放表头名称，便于交换列做比对，取表头名称
			Map<Integer,String> headerMap = new HashMap<Integer,String>();
			if (null != header) {
				for(int h = 0; h < header.getLastCellNum(); h++){
					headerMap.put(h, header.getCell(h).getStringCellValue());
				}
			} else {
				System.out.println("-----Excel文件表头不存在-----");
				return ;
			}
			
			
			//存放记录表头中文和英文对应
			Map<String,String> enCnMap = initHeadMap();
		
		    //处理记录行
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				
				//反射处理设置除表头以外的记录数据
				jktj = new SqylGrjkdaJktj();
				Class tjclass = jktj.getClass();
				
				HSSFRow row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
	
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);
					if (null == cell) {
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						cellStr = cell.getNumericCellValue() + "";
					} else {
						cellStr = cell.getStringCellValue();
					}
	
					//按照数据出现位置封装到bean中
					extractDeclareMethod(cellStr, headerMap, enCnMap, jktj,tjclass, j);

				}
				
				//注意部分值EXCEL并未有列，需要手动给定默认值,否则word打印和平台显示不一致
				excelOtherColExtract(jktj);
	
				tjList.add(jktj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 处理Excel2007(有交换列)
	 * 
	 * @param tjList
	 *//*
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private static void invokeExcel2007ExchangeCols(List<SqylGrjkdaJktj> tjList,
			XSSFWorkbook workbook2007) {
		// 单元格，最终按字符串处理
		String cellStr = null;
		SqylGrjkdaJktj jktj = null;
		XSSFSheet sheet = workbook2007.getSheetAt(0);

		try{
			//处理表头行
			XSSFRow header =  sheet.getRow(0);
			//存放表头名称，便于交换列做比对，取表头名称
			Map<Integer,String> headerMap = new HashMap<Integer,String>();
			if (null != header) {
				for(int h = 0; h < header.getLastCellNum(); h++){
					headerMap.put(h, header.getCell(h).getStringCellValue());
				}
			} else {
				System.out.println("-----Excel文件表头不存在-----");
				return ;
			}
			
			
			//存放记录表头中文和英文对应
			Map<String,String> enCnMap = initHeadMap();
		
		    //处理记录行
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				
				//反射处理设置除表头以外的记录数据
				jktj = new SqylGrjkdaJktj();
				Class tjclass = jktj.getClass();
				
				XSSFRow row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
	
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);
					if (null == cell) {
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						cellStr = cell.getNumericCellValue() + "";
					} else {
						cellStr = cell.getStringCellValue();
					}
	
					//按照数据出现位置封装到bean中
					extractDeclareMethod(cellStr, headerMap, enCnMap, jktj,tjclass, j);
				}
	
				//注意部分值EXCEL并未有列，需要手动给定默认值,否则word打印和平台显示不一致
				excelOtherColExtract(jktj);
				
				tjList.add(jktj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * EXCEL其他不存在的列,默认值处理
	 * @param jktj
	 *//*
	private static void excelOtherColExtract(SqylGrjkdaJktj jktj) {
		jktj.setRx1("1");//乳腺,未见异常
		jktj.setDbqx("1");//大便潜血,阴性
		jktj.setYxgybmky("1");//乙型肝炎表面抗原,阴性
		jktj.setNxgjb1("1");//脑血管疾病,未发现
		jktj.setSzjb1("1");//肾脏疾病,未发现
		jktj.setXzjb1("1");//心脏疾病,未发现
		jktj.setXgjb1("1");//血管疾病,未发现
		jktj.setYbjb1("1");//眼部疾病,未发现
		jktj.setYwsjxtjb("0");//神经系统疾病,无
		jktj.setYwqtxtjb("0");//其他系统疾病,无
	}

	*//**
	 * 处理反射调用set方法
	 * @param cellStr
	 * @param headerMap
	 * @param enCnMap
	 * @param jktj
	 * @param tjclass
	 * @param j
	 *//*
	private static void extractDeclareMethod(String cellStr,
			Map<Integer, String> headerMap, Map<String, String> enCnMap,
			SqylGrjkdaJktj tj, Class tjclass, int j) {
		try {
			String enStr = enCnMap.get(headerMap.get(j));
			
			if(null != enStr && !enStr.equals("")){
				if(enStr.equals("tjrq")){
					if (!cellStr.equals("")) {
						tj.setTjrq(DateUtil.praseStringToDate(cellStr,"yyyy-MM-dd"));// 体检日期---B
					}
				}else if(enStr.equals("zz")){
					// 症状---D
					if (!cellStr.equals("")) {
						String[] str = cellStr.split(",");
						for (int k = 0; k < str.length; k++) {
							String val = String.valueOf(new Double(str[k]).intValue());
							if (val.equals("1")) {
								tj.setZz1("1");
							} else if (val.equals("2")) {
								tj.setZz2("1");
							}
							else if (val.equals("3")) {
								tj.setZz3("1");
							}
							else if (val.equals("4")) {
								tj.setZz4("1");
							}
							else if (val.equals("5")) {
								tj.setZz5("1");
							}
							else if (val.equals("6")) {
								tj.setZz6("1");
							}
							else if (val.equals("7")) {
								tj.setZz7("1");
							}
							else if (val.equals("8")) {
								tj.setZz8("1");
							}
							else if (val.equals("9")) {
								tj.setZz9("1");
							}
							else if (val.equals("10")) {
								tj.setZz10("1");
							}
							else if (val.equals("11")) {
								tj.setZz11("1");
							}
							else if (val.equals("12")) {
								tj.setZz12("1");
							}
							else if (val.equals("13")) {
								tj.setZz13("1");
							}
							else if (val.equals("14")) {
								tj.setZz14("1");
							}
							else if (val.equals("15")) {
								tj.setZz15("1");
							}
							else if (val.equals("16")) {
								tj.setZz16("1");
							}
							else if (val.equals("17")) {
								tj.setZz17("1");
							}
							else if (val.equals("18")) {
								tj.setZz18("1");
							}
							else if (val.equals("19")) {
								tj.setZz19("1");
							}
							else if (val.equals("20")) {
								tj.setZz20("1");
							}
							else if (val.equals("21")) {
								tj.setZz21("1");
							}
							else if (val.equals("22")) {
								tj.setZz22("1");
							}
							else if (val.equals("23")) {
								tj.setZz23("1");
							}
							else if (val.equals("24")) {
								tj.setZz24("1");
							}
							else if (val.equals("25")) {
								tj.setZz25("1");
							}
						}
					}
				}else if(enStr.equals("ml")){
					// 脉率---F
					if (!cellStr.equals("")) {
						tj.setMl(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("hxpl")){
					// 呼吸频率---G
					if (!cellStr.equals("")) {
						tj.setHxpl(String.valueOf(new Double(cellStr)
								.intValue()));
					}
				}else if(enStr.equals("zcxyssy")){
					//左侧血压收缩压
					if (!cellStr.equals("")){
						tj.setZcxy(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("zcxyszy")){
					//左侧血压舒张压
					if (!cellStr.equals("")){
						tj.setZcxy(tj.getZcxy()+"/"+String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("ycxyssy")){
					//右侧血压收缩压
					if (!cellStr.equals("")){
						tj.setYcxy(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("ycxyszy")){
					//右侧血压舒张压
					if (!cellStr.equals("")){
						tj.setYcxy(tj.getYcxy()+"/"+String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("sg")){
					// 身高(cm)---L
					if (!cellStr.equals("")) {
						tj.setSg(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("tz")){
					// 体重---M
					if (!cellStr.equals("")) {
						tj.setTz(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("kc")){
					// 口唇---W
					if(!cellStr.equals("")){
						tj.setKc(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("cl")){
					// 齿列---X(平台为下拉框，而excel为多个值)##################
					if(!cellStr.equals("")){
						tj.setCl(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("tl")){
					// 听力---AH
					if(cellStr.equals("")){
						tj.setTl("1");//默认
					}else if (cellStr.equals("1")) {
						// Excel 1：听见  2:听不清,3：无法听见。2,3均按数据库0处理
						tj.setTl(cellStr);
					} else {
						tj.setTl("0");
					}
				}else if(enStr.equals("ydgn")){
					// 运动功能---AI ###############
					if (cellStr.equals("") || cellStr.equals("1")) {// 默认可顺利完成
						tj.setYdgn("1");
					} else {
						tj.setYdgn("0");
					}
				}else if(enStr.equals("yjpl")){
					// 饮酒频率---AK
					if (cellStr.equals("")) {// 默认从不
						tj.setYjpl(cellStr);
					} else {
						tj.setYjpl(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ysxg")){
					// 饮食习惯---AM
					if (!cellStr.equals("")) {
						String[] str = cellStr.split(",");
						for (int k = 0; k < str.length; k++) {
						    String val = String.valueOf(new Double(str[k]).intValue());
							if (val.equals("1")) {
								tj.setYsxg1("1");
							} else if (val.equals("2")) {
								tj.setYsxg2("1");
							} else if (val.equals("3")) {
								tj.setYsxg3("1");
							} else if (val.equals("4")) {
								tj.setYsxg4("1");
							} else if (val.equals("5")) {
								tj.setYsxg5("1");
							} else if (val.equals("6")) {
								tj.setYsxg6("1");
							}
						}
					}
				}else if(enStr.equals("sftzx")){
					// 桶状胸---AP
					if (!cellStr.equals("")) {
						tj.setSftzx(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ywly")){
					// 罗音---AR
					if (!cellStr.equals("")) {
						tj.setYwly(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ywzy")){
					// 杂音---AT
					if (!cellStr.equals("")) {
						tj.setYwzy(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ywfbyt")){
					// 压痛---AU
					if (!cellStr.equals("")) {
						tj.setYwfbyt(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ywfbbk")){
					// 包块---AV
					if (!cellStr.equals("")) {
						tj.setYwfbbk(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ywfbgd")){
					// 肝大---AW
					if (!cellStr.equals("")) {
						tj.setYwfbgd(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ywfbpd")){
					// 脾大---AX
					if (!cellStr.equals("")) {
						tj.setYwfbpd(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("ywfbydxzy")){
					// 移动性浊音---AY
					if (!cellStr.equals("")) {
						tj.setYwfbydxzy(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("zbdmbd")){
					// 足背动脉搏动---AZ
					if (!cellStr.equals("")) {
						tj.setZbdmbd(String.valueOf(new Integer(cellStr) - 1));
					}
				}else if(enStr.equals("xl")){
					// 心率(次/分钟)---BA
					if (!cellStr.equals("")) {
						tj.setXl(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("xhdb")){
					// 血红蛋白(g/L)---BB
					if (!cellStr.equals("")) {
						tj.setXhdb(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("xxb")){
					// 血小板(10^9/L)---BD
					if (!cellStr.equals("")) {
						tj.setXxb(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("xdtsfyc")){
					// 心电图---BM ##########
					if(!cellStr.equals("")){
						String val = String.valueOf(new Double(cellStr).intValue());
						
						if (val.equals("1")) {// excel
							// 1：正常,2:异常,3:临界ECG,数据库：0：异常，1:正常
							tj.setXdtsfyc(val);
						} else if (val.equals("2")) {
							tj.setXdtsfyc("0");// 数据库中存0
						} else if (val.equals("3")) {
							// 数据库无该值。暂时设为正常
							tj.setXdtsfyc("1");
						}
					}
				}else if(enStr.equals("xqgbzam")){
					// 血清谷丙转氨酶(U/L)---BO
					if (!cellStr.equals("")) {
						tj.setXqgbzam(String.valueOf(new Double(cellStr)
								.intValue()));
					}
				}else if(enStr.equals("xqgczam")){
					// 血清谷草转氨酶(U/L)---BP
					if (!cellStr.equals("")) {
						tj.setXqgczam(String.valueOf(new Double(cellStr)
								.intValue()));
					}
				}else if(enStr.equals("xqjg")){
					// 血清肌酐(μmol/L)---BR
					if (!cellStr.equals("")) {
						tj.setXqjg(String.valueOf(new Double(cellStr)
								.intValue()));
					}
				}else if(enStr.equals("xbxxpsfyc")){
					// 胸部X线片---BV
					if (cellStr.equals("") || cellStr.equals("1")) {// Excel 1:正常  2:异常;数据库  1:正常,0:异常
						tj.setXbxxpsfyc("1");// 默认正常
					} else {
						tj.setXbxxpsfyc("0");
					}
				}else if(enStr.equals("phz") || enStr.equals("qxz") || enStr.equals("yxz")
						|| enStr.equals("yxzqt") || enStr.equals("tsz") || enStr.equals("srz")
						|| enStr.equals("xtz") || enStr.equals("qyz") || enStr.equals("tbz") || enStr.equals("dlpl")){
					
					//平和质、气虚质、阳虚质、阴虚质、痰湿质、湿热质、血瘀质、气郁质、特秉质、锻炼频率
					if(!cellStr.equals("")){
						Method m1 = tjclass.getDeclaredMethod("set"+toUpperCaseFirstOneOther(enStr), String.class);
						m1.invoke(tj, String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("tjywyc")){
					// 健康评价---CF
					if (!cellStr.equals("")) {
						tj.setTjywyc(String.valueOf(new Double(cellStr).intValue() - 1));
					}
				}else if(enStr.equals("jkzd")){
					// 健康指导---CK ##############################
					// excel有定期随访，平台无此选项
					if (!cellStr.equals("")) {
						String[] str = cellStr.split(",");
						for (int k = 0; k < str.length; k++) {
							String val = String.valueOf(new Double(str[k]).intValue());
							if (val.equals("1")) {
								// 暂不处理定期随访选项
							} else if (val.equals("2")) {
								tj.setNrmxbhzjkgl("1");
							} else if (val.equals("3")) {
								tj.setJyfz("1");
							} else if (val.equals("4")) {
								tj.setJyzz("1");
							}
						}
					}
				}else if(enStr.equals("wxyskz")){
					// 危险因素控制---CL
					if (!cellStr.equals("")) {
						String[] str = cellStr.split(",");
						for (int k = 0; k < str.length; k++) {
							String val = String.valueOf(new Double(str[k]).intValue());
							
							if (val.equals("1")) {
								tj.setWxyskz1("1");
							} else if (val.equals("2")) {
								tj.setWxyskz2("1");
							} else if (val.equals("3")) {
								tj.setWxyskz3("1");
							} else if (val.equals("4")) {
								tj.setWxyskz4("1");
							} else if (val.equals("5")) {
								tj.setWxyskz5("1");
							} else if (val.equals("6")) {
								tj.setWxyskz6("1");
							} else if (val.equals("7")) {
								tj.setWxyskz7("1");
							}
						}
					}
				}else if(enStr.equals("cjrq")){
					// 录入日期---CP
					if (!cellStr.equals("")) {
						tj.setCjrq(DateUtil.praseStringToDate(cellStr,
								"yyyy-MM-dd"));// 体检日期---B
					}
				}else if(enStr.equals("xb")){
					// 性别
					if (!cellStr.equals("")) {
						tj.setXb(String.valueOf(new Double(cellStr).intValue()));
					}
				}else if(enStr.equals("lnrrzgn")){
					//老年人认知
					if (!cellStr.equals("")) {
						tj.setLnrrzgn(String.valueOf(new Double(cellStr).intValue()));
					}else{
						tj.setLnrrzgn("1");//初筛阴性
					}
				}else if(enStr.equals("lnrqgzt")){
					//老年人情感
					if (!cellStr.equals("")) {
						tj.setLnrqgzt(String.valueOf(new Double(cellStr).intValue()));
					}else{
						tj.setLnrqgzt("1");//初筛阴性
					}
				}else{
					Method m1 = tjclass.getDeclaredMethod("set"+toUpperCaseFirstOneOther(enStr), String.class);
					m1.invoke(tj, cellStr);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 初始化表头Map
	 * @return
	 *//*
	private static Map<String,String> initHeadMap() {
		Map<String,String> enCnMap = new HashMap<String,String>();
		enCnMap.put("身份证","sfzh");
		enCnMap.put("体检时间","tjrq");
		enCnMap.put("姓名", "xm");//EXCEL新加入姓名列头,2013-09-16
		enCnMap.put("性别", "xb");//EXCEL新加入性别列头,2013-09-16
		enCnMap.put("户籍地址", "hjdz");//EXCEL新加入户籍地址列头(辅助字段，数据库中无此字段),2013-09-16
		//enCnMap.put("体检类型","tjlx");//DB库中无此项
		enCnMap.put("症状","zz");//自定义字段，另行处理
		enCnMap.put("体温(℃)","tw");
		enCnMap.put("脉率(次/分)","ml");
		enCnMap.put("呼吸频率(次/分)","hxpl");
		enCnMap.put("左侧收缩压(mmHg)","zcxyssy");//文档无值
		enCnMap.put("左侧舒张压(mmHg)","zcxyszy");//文档无值
		enCnMap.put("右侧收缩压(mmHg)","ycxyssy");
		enCnMap.put("右侧舒张压(mmHg)","ycxyszy");
		enCnMap.put("身高(cm)","sg");
		enCnMap.put("体重(kg)","tz");
		enCnMap.put("腰围(cm)","yw");
		//enCnMap.put("臀围(cm)","tw");//Db无选项
		enCnMap.put("老年人认知","lnrrzgn");
		enCnMap.put("老年人情感","lnrqgzt");
		//enCnMap.put("锻炼频率","");//与AL重复
		enCnMap.put("每次时长(分)","mcdlsj");
		enCnMap.put("坚持时间(年)","jcdlns");
		enCnMap.put("锻炼方式","dlfs");
		//enCnMap.put("饮食习惯","");//----与AM重复
		enCnMap.put("口唇","kc");
		enCnMap.put("齿列","cl");
		//enCnMap.put("缺齿左上","");//Db无选项
		//enCnMap.put("缺齿右上","");//Db无选项
		//enCnMap.put("缺齿左下","");//Db无选项
		//enCnMap.put("缺齿右下","");//Db无选项
		enCnMap.put("咽部","yb");
		enCnMap.put("左眼","slzy");
		enCnMap.put("右眼","slyy");
		enCnMap.put("矫正视力(左)","jzslzy");
		enCnMap.put("矫正视力(右)","jzslyy");
		enCnMap.put("听力","tl");
		enCnMap.put("运动功能","ydgn");
		//enCnMap.put("被动吸烟情况","");//Db无选项
		enCnMap.put("饮酒频率","yjpl");
		enCnMap.put("锻炼频率","dlpl");//重复
		enCnMap.put("饮食习惯","ysxg");//重复，自定义字段，另行处理
		enCnMap.put("皮肤","phsfzc");
		enCnMap.put("淋巴结","lbjsfzc");
		enCnMap.put("桶状胸","sftzx");
		enCnMap.put("呼吸音","hxysfzc");
		enCnMap.put("罗音","ywly");
		enCnMap.put("心律","xlsfzq");
		enCnMap.put("杂音","ywzy");
		enCnMap.put("压痛","ywfbyt");
		enCnMap.put("包块","ywfbbk");
		enCnMap.put("肝大","ywfbgd");
		enCnMap.put("脾大","ywfbpd");
		enCnMap.put("移动性浊音","ywfbydxzy");
		enCnMap.put("足背动脉搏动","zbdmbd");
		enCnMap.put("心率(次/分钟)","xl");
		enCnMap.put("血红蛋白(g/L)","xhdb");
		enCnMap.put("白细胞(10^9/L)","bxb");
		enCnMap.put("血小板(10^9/L)","xxb");
		//enCnMap.put("红细胞(T/L)","");//Db无选项
		enCnMap.put("血常规其他","xcgqt");
		enCnMap.put("尿蛋白","ndb");
		enCnMap.put("尿糖","nt");
		enCnMap.put("尿酮体","ntt");
		enCnMap.put("尿潜血","nqx");
		enCnMap.put("尿常规其他","ncgqt");
		enCnMap.put("空腹血糖(mmol/L)","kfxt1");
		enCnMap.put("心电图","xdtsfyc");
		enCnMap.put("心电图异常","xdtycmc");
		enCnMap.put("血清谷丙转氨酶(U/L)","xqgbzam");
		enCnMap.put("血清谷草转氨酶(U/L)","xqgczam");
		enCnMap.put("总胆红素(μmol/L)","zdhs");
		enCnMap.put("血清肌酐(μmol/L)","xqjg");
		enCnMap.put("血尿素氮(mmol/L)","xnsd");
		enCnMap.put("总胆固醇(mmol/L)","zdgc");
		enCnMap.put("甘油三酯(mmol/L)","gysz");//此处表头之间有空格，需要去掉空格
		enCnMap.put("胸部X线片","xbxxpsfyc");
		enCnMap.put("平和质","phz");
		enCnMap.put("气虚质","qxz");
		enCnMap.put("阳虚质","yxz");
		enCnMap.put("阴虚质","yxzqt");
		enCnMap.put("痰湿质","tsz");
		enCnMap.put("湿热质","srz");
		enCnMap.put("血瘀质","xtz");
		enCnMap.put("气郁质","qyz");
		enCnMap.put("特秉质","tbz");
		enCnMap.put("健康评价","tjywyc");
		enCnMap.put("异常1","tjycmc1");
		enCnMap.put("异常2","tjycmc2");
		enCnMap.put("异常3","tjycmc3");
		enCnMap.put("异常4","tjycmc4");
		enCnMap.put("健康指导","jkzd");//自定义字段，另行处理
		enCnMap.put("危险因素控制","wxyskz");//自定义字段，另行处理
		enCnMap.put("目标体重(kg)","Wxyskz5mc");
		enCnMap.put("录入医生","zrys");
		//enCnMap.put("管辖机构","gxjg");//Db无选项
		enCnMap.put("录入日期","cjrq");
		
		return enCnMap;
	}
	

    //首字母转小写
	public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	
	//首字母转大写(方式一)
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    //首字母转大写(方式二)
    public static String toUpperCaseFirstOneOther(String s){
	    StringBuilder sb = new StringBuilder(s);
	    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
	    s = sb.toString(); 
	    return s;
    }
    
	*//**
	 * 方法描述：导入excel2007数据,主要针对健康档案
	 *//*
	public static List<SqylGrjkdaSy> readDataFromXLSX2007Or2003ForJkda(String filePath) {
		
		// Excel文件对象
		File excelFile = null;
		// 输入流对象
		InputStream is = null;

		List<SqylGrjkdaSy> daList = new ArrayList<SqylGrjkdaSy>();
		try {
			excelFile = new File(filePath);
			is = new FileInputStream(excelFile);
			
			if (filePath.endsWith(".xls")) {
				HSSFWorkbook workbook2003 = new HSSFWorkbook(is);

				//有表头交换
				invokeExcel2003ExchangeColsForJkda(daList, workbook2003);
			} else if (filePath.endsWith(".xlsx")) {
				XSSFWorkbook workbook2007 = new XSSFWorkbook(is);

				//有表头交换
				invokeExcel2007ExchangeColsForJkda(daList, workbook2007);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return daList;
	}
	
	*//**
	 * 处理Excel2003(有交换列),主要针对档案
	 * 
	 * @param tjList
	 *//*
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private static void invokeExcel2003ExchangeColsForJkda(List<SqylGrjkdaSy> daList,HSSFWorkbook workbook2003) {
		// 单元格，最终按字符串处理
		String cellStr = null;
		SqylGrjkdaSy jkda = null;
		HSSFSheet sheet = workbook2003.getSheetAt(0);

		try{
			//处理表头行
			HSSFRow header =  sheet.getRow(0);
			//存放表头名称，便于交换列做比对，取表头名称
			Map<Integer,String> headerMap = new HashMap<Integer,String>();
			if (null != header) {
				for(int h = 0; h < header.getLastCellNum(); h++){
					headerMap.put(h, header.getCell(h).getStringCellValue());
				}
			} else {
				System.out.println("-----Excel文件表头不存在-----");
				return ;
			}
			
			
			//存放记录表头中文和英文对应
			Map<String,String> enCnMap = initHeadMapForJkda();
		
		    //处理记录行
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				
				//反射处理设置除表头以外的记录数据
				jkda = new SqylGrjkdaSy();
				Class daclass = jkda.getClass();
				
				HSSFRow row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
	
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);
					if (null == cell) {
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						cellStr = cell.getNumericCellValue() + "";
					} else {
						cellStr = cell.getStringCellValue();
					}
	
					//按照数据出现位置封装到bean中
					extractDeclareMethodForJkda(cellStr, headerMap, enCnMap, jkda,daclass, j);

				}
	
				daList.add(jkda);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 处理Excel2007(有交换列),主要针对档案
	 * 
	 * @param tjList
	 *//*
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private static void invokeExcel2007ExchangeColsForJkda(List<SqylGrjkdaSy> daList,
			XSSFWorkbook workbook2007) {
		// 单元格，最终按字符串处理
		String cellStr = null;
		SqylGrjkdaSy jkda = null;
		XSSFSheet sheet = workbook2007.getSheetAt(0);

		try{
			//处理表头行
			XSSFRow header =  sheet.getRow(0);
			//存放表头名称，便于交换列做比对，取表头名称
			Map<Integer,String> headerMap = new HashMap<Integer,String>();
			if (null != header) {
				for(int h = 0; h < header.getLastCellNum(); h++){
					headerMap.put(h, header.getCell(h).getStringCellValue());
				}
			} else {
				System.out.println("-----Excel文件表头不存在-----");
				return ;
			}
			
			
			//存放记录表头中文和英文对应
			Map<String,String> enCnMap = initHeadMapForJkda();
		
		    //处理记录行
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				
				//反射处理设置除表头以外的记录数据
				jkda = new SqylGrjkdaSy();
				Class daclass = jkda.getClass();
				
				XSSFRow row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
	
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);
					if (null == cell) {
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						cellStr = cell.getNumericCellValue() + "";
					} else {
						cellStr = cell.getStringCellValue();
					}
	
					//按照数据出现位置封装到bean中
					extractDeclareMethodForJkda(cellStr, headerMap, enCnMap, jkda,daclass, j);
				}
				
				daList.add(jkda);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 处理反射调用set方法,主要针对档案
	 * @param cellStr
	 * @param headerMap
	 * @param enCnMap
	 * @param jktj
	 * @param tjclass
	 * @param j
	 *//*
	@SuppressWarnings({ "rawtypes", "unused" })
	private static void extractDeclareMethodForJkda(String cellStr,
			Map<Integer, String> headerMap, Map<String, String> enCnMap,
			SqylGrjkdaSy da, Class daclass, int j) {
		try {
			String enStr = enCnMap.get(headerMap.get(j));
			
               if(enStr.equals("xb")){
					//老年人认知
					if (cellStr.equals("男")) {
						da.setXb("1");
					}else if(cellStr.equals("女")){
						da.setXb("2");
					}
			   }else{
					Method m1 = daclass.getDeclaredMethod("set"+toUpperCaseFirstOneOther(enStr), String.class);
					m1.invoke(da, cellStr);
			   }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 初始化表头Map,主要针对档案
	 * @return
	 *//*
	private static Map<String,String> initHeadMapForJkda() {
		Map<String,String> enCnMap = new HashMap<String,String>();
		enCnMap.put("身份证","sfzh");
		enCnMap.put("姓名", "xm");
		enCnMap.put("性别", "xb");
		enCnMap.put("户籍地址", "xxdzZrc");
		enCnMap.put("手机", "phone");
		return enCnMap;
	}
	*/
	/**
	 * 导出数据保存至 excel2003
	 * @param fileName
	 * @param list
	 */
	
}

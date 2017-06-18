package cn.cellcom.szba.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class FileTool {
	private static final Log log = LogFactory.getLog(FileTool.class);
	private static final String CONTENTTYPE = "application/x-msdownload";
	private static final String HEADER = "Content-Disposition";
	private static final String[] EXL = { "XLS", "XLSX", "xls", "xls" };
	private static final String[] TXT = { "txt", "TXT" };
	private static final String[] RAR = { "rar", "RAR" };
	private static final String[] IMAGE = { "JPG", "jpg", "gif", "GIF", "png",
			"PNG" };

	/**
	 * 判断一个文件夹是否存在,不存在,则创建
	 * 
	 * @param filePath
	 * @param isCreate
	 * @return
	 */
	public static File isExistFile(String filePath, boolean isCreate) {
		if (StringUtils.isBlank(filePath))
			return null;
		else {
			return isExistDir(new File(filePath), isCreate);
		}
	}

	/**
	 * 判断一个文件夹是否存在,不存在,则创建
	 * 
	 * @param filePath
	 * @param isCreate
	 * @return
	 */
	public static File isExistDir(File filePath, boolean isCreate) {
		if (filePath.isDirectory())
			return filePath;
		else {
			if (isCreate && !filePath.exists())
				try {
					filePath.mkdirs();
					return filePath;
				} catch (SecurityException e) {
					throw new RuntimeException(filePath + "创建失败", e);
				}
			return null;
		}
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param file
	 * @return
	 */
	public static String getExtName(File file) {
		String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtName(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	public static  boolean download(HttpServletResponse response,
			File file) {
		if (file.exists()) {
			BufferedInputStream bufferedin = null;
			ServletOutputStream out = null;
			try {
				bufferedin = new BufferedInputStream(new FileInputStream(file));
				byte[] content = new byte[bufferedin.available()];
				response.setContentLength(content.length);
				response.setContentType("multipart/form-data");
				response.setHeader(HEADER, "attachment;filename="
						+ new String(file.getName().getBytes("GBK"), "ISO-8859-1"));
				
				out = response.getOutputStream();
				while (bufferedin.read(content) != -1) {
					out.write(content);
				}
//				int b = 0;
//	            byte[] buffer = new byte[1024];
//	            while (b != -1){
//	                b = bufferedin.read(buffer);
//	                out.write(buffer,0,b);
//	            }
				return true;
			} catch (FileNotFoundException e) {
				log.info(e);
				throw new RuntimeException(e);
			} catch (IOException e) {
				log.info(e);
				throw new RuntimeException(e);
			} finally {
				try {
					if (bufferedin != null) {
						bufferedin.close();
					}
					if (out != null) {
						out.close();
			            out.flush();  
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			throw new RuntimeException(file.getPath() + "文件未找到");
		}
	}
	
	public static DataMsg upload(HttpServletRequest request, String path,
			String newName, long max) {
		FileTool.isExistFile(path, true); // 文件保存目录
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		DataMsg data = new DataMsg(false, "上传文件失败");
		if (isMultipart) {
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setSizeMax(max);
			fileUpload.setHeaderEncoding("UTF-8");// 解决文件乱码问题
			try {
				List items = fileUpload.parseRequest(request);
				int i=0;
				StringBuffer names = new StringBuffer(); 
				for (Iterator ite = items.iterator(); ite.hasNext();) {
					FileItem item = (FileItem) ite.next();
					if (!item.isFormField()) {
						String name = item.getName();
						if(name==null){
							continue;
						}
						
						String ext=FileTool.getExtName(name);
						if (newName != null) {
							name = newName+"."+ext;
						}
						log.info("文件路径：" + path + "新文件名：" + name);
						item.write(new File(path + name));
						names.append(name);
					}
				}
				
				data.setObj(names.toString());
				data.setState(true);
				data.setMsg("上传成功");
				
				return data;
			} catch (FileUploadException e) {
				data.setMsg(e.getMessage());
				log.error("上传文件失败", e);
			} catch (Exception e) {
				data.setMsg(e.getMessage());
				log.error("上传文件失败", e);
			}
		} else {
			data.setMsg("the enctype must be multipart/form-data");
			return data;
		}
		return data;
	}

}

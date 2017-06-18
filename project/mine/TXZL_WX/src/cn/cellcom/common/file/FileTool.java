package cn.cellcom.common.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
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





import cn.cellcom.common.data.PrintTool;

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

	public static Msg upload(HttpServletRequest request, String path,
			String[] newName, long max) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Msg msg = new Msg(false, "");
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setSizeMax(max);
			fileUpload.setHeaderEncoding("UTF-8");// 解决文件乱码问题
			try {
				List items = fileUpload.parseRequest(request);
				int i=0;
				for (Iterator ite = items.iterator(); ite.hasNext();) {
					i++;
					FileItem item = (FileItem) ite.next();
					if (!item.isFormField()) {
						String name = item.getName();
						if (newName != null) {
							if (StringUtils.isNotBlank(newName[i])) {
								name = newName[i];
							}
						}
						log.info("文件路径：" + path + "新文件名：" + name);
						item.write(new File(path + name));
					}
				}
				msg.setFlag(true);
				msg.setMsg("上传成功");
				PrintTool.printBean(null,msg, true);
				return msg;
			} catch (FileUploadException e) {
				msg.setMsg(e.getMessage());
				log.error("上传文件失败", e);
			} catch (Exception e) {
				msg.setMsg(e.getMessage());
				log.error("上传文件失败", e);
			}
		} else {
			msg.setMsg("the enctype must be multipart/form-data");
			return msg;
		}
		PrintTool.printBean(null,msg, true);
		return msg;
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
				response.setContentType(CONTENTTYPE);
				response.setHeader(HEADER, "attachment;filename="
						+ URLEncoder.encode(file.getName(), "UTF-8"));
				out = response.getOutputStream();
				while (bufferedin.read(content) != -1) {
					out.write(content);
				}
				out.flush();
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
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			throw new RuntimeException(file.getPath() + "文件未找到");
		}
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

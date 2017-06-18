package cn.cellcom.szba.common;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.vo.UploadFileVO;


public class FileUpload{
	private Log log = LogFactory.getLog(this.getClass());
	
	// 1:  获取文件扩展名
	private String getFileExt(File file){
		String extFileName = FilenameUtils.getExtension(file.getName());
		return extFileName;
	}
	
	// 2: 通过UUID生成唯一文件名
	private String createNewName(String fileName){
		String ext = FilenameUtils.getExtension(fileName);
		
		System.out.println(FilenameUtils.getName(fileName));
		
		String newName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext;
		return newName;
	}
	
	private String saveFile(String saveDir, String fileName, FileItem item)
			throws Exception {
		if (!new File(saveDir).exists()) {
			new File(saveDir).mkdirs();
		}
		File f = new File(saveDir, fileName);
		log.info("saving file : " + f);
		item.write(f);
		return f.toString();
	}
	
	// 3： 实现文件上传
	public UploadFileVO upload(HttpServletRequest request, String type, String filePath){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		log.info(isMultipart + "##########");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext servletContext = request.getSession().getServletContext();
		File repository = (File) servletContext
				.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		String fileName = "";
		
		UploadFileVO vo = new UploadFileVO();
		try {
			List<FileItem> items = upload.parseRequest(request);

			Iterator<FileItem> iter = items.iterator();
			String path="";
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (item.isFormField()) {
					log.info(item.isFormField() + ">>>" + item.getFieldName()
							+ "," + item.getString());
				} else {
					log.info(item.isFormField() + ">>>" + item.getFieldName()
							+ ","+ item.getName()+ ","+ item);

					String dateStr = DateTool.formateTime2StringYMD(new Date());
					fileName = createNewName(item.getName());
					path=saveFile(filePath+dateStr, fileName, item);
					
					//如果是上传图片
					if(type == "2"){
						PicUtil.getThumbnailPic(filePath+dateStr+"/"+fileName, dateStr+"/"+fileName);
					}
					
					vo.setFileName(item.getName());
					vo.setPath(dateStr+"/"+fileName);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		
		return vo;
	}

	
}

package com.gf.ims.file.servlet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gf.ims.file.bean.ImagePathVo;
import com.gf.ims.file.common.GlobalStaticValue;
import com.gf.ims.file.enums.ServletRspErrorCode;
import com.gf.ims.file.utils.PictureUtil;
import com.gf.ims.file.utils.ServletUtil;

import net.sf.json.JSONObject;
/**
 * 图片上传serlvet
 * 支持多个文件上传
 * 上传图片后生成图片的缩列图
 */
public class ImagesUploadServlet extends HttpServlet {
	private final static Logger  logger=Logger.getLogger(ImagesUploadServlet.class);
	private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	private static final long serialVersionUID = 1L;
	//图片文件存放目录名
	private static final String DIRETORY="upload/images";
	//生成缩列图的宽度
	private static final int MIN_PIC_WIDTH=300;
	//生成缩列图的高度
	private static final int MIN_PIC_HEIGHT=300;
	private ServletFileUpload upload;
	private int MAXSize=20*1024*1024;//文件上传大小限制
	/**
	 * 设置文件上传的初始化信息
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory for disk-based file items
		this.upload = new ServletFileUpload(factory);// Create a new file upload handler
		this.upload.setSizeMax(this.MAXSize);// Set overall request size constraint 4194304
		
	}
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int l=request.getContentLength();
		logger.info("数据长度："+l);
		String result = ServletRspErrorCode.MESSAGE_RSP_SUCCESS;
		String error = "";
		List<ImagePathVo> imagePaths=new ArrayList<ImagePathVo>();
		//文件存放的路径
		String filedir=null;
		filedir= request.getSession().getServletContext().getRealPath(File.separator);
		filedir=new File(filedir).getParent();//文件存储的目录与项目同级
		//当天的日期分出的文件夹
		String dateStr=sdf.format(new Date());
		//文件存放的目录按天划分
		filedir=filedir+File.separator+DIRETORY+File.separator+dateStr;
		logger.info("文件上传存放的路径："+filedir);
		File directory=new File(filedir);
		if(!directory.exists()){
			directory.mkdir();
		}
		//图片访问路径前缀
		String picUrlPrefix=GlobalStaticValue.upload_server_address+"/images/"+dateStr;
		try {
			logger.info("获取上传文件域开始..");
			List<FileItem> items = this.upload.parseRequest(request);
			logger.info("获取上传文件域结束..");
			if(items!=null	&& !items.isEmpty()){
				for (FileItem fileItem : items) {
					String filename=fileItem.getName();
					//文件后缀
					String fileSuffix=filename.substring(filename.lastIndexOf(".")+1);
					String tempFileName=PictureUtil.generateFileName();
					//新文件名
					String newFileName=tempFileName+"."+fileSuffix;
					//缩列图文件名
					String thumbnailFileName=tempFileName+"_thumbnail"+"."+fileSuffix;
					//原图文件路径
					String filepath=filedir+File.separator+newFileName;
					//缩列图保存路径
					String thumbnailFilePath=filedir+File.separator+thumbnailFileName  ;
					File file=new File(filepath);
					logger.info("保存本地文件开始..");
					InputStream inputSteam=fileItem.getInputStream();
					FileUtils.copyInputStreamToFile(inputSteam, file);
					logger.info("保存本地文件结束..");
					inputSteam.close();
					logger.info("文件上传成功，文件名："+newFileName+",文件保存路径："+filepath);
					logger.info("开始生成缩略图..");
					//生成缩列图
					PictureUtil.zoomPic(filepath, thumbnailFilePath, MIN_PIC_WIDTH, MIN_PIC_HEIGHT);
					logger.info("生成缩略图完成..");
					logger.info("缩列图生成成功，文件名："+thumbnailFileName+",文件保存路径："+thumbnailFilePath);
					//文件的访问路劲
					ImagePathVo imagePathVo=new ImagePathVo();
					imagePathVo.setO_path(picUrlPrefix+"/"+newFileName);
					imagePathVo.setT_path(picUrlPrefix+"/"+thumbnailFileName);
					imagePaths.add(imagePathVo);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			result = ServletRspErrorCode.MESSAGE_FORMAT_ERROR_CODE;
			error = ServletRspErrorCode.MESSAGE_FORMAT_ERROR_CONTENT;
			
			
		}
		//消息响应
		Map resultMap=new HashMap<String,String>();
		resultMap.put("result", result);
		resultMap.put("reason", error);
		resultMap.put("list",imagePaths);
		ServletUtil.sendResponse(response, JSONObject.fromObject(resultMap).toString());
	}
	

}

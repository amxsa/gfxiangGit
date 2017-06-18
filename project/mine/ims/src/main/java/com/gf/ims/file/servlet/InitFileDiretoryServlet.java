package com.gf.ims.file.servlet;
import java.io.File;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.gf.ims.file.enums.FileDiretorys;

/**
 * 初始化并生成文件存放的目录Servlet
 * web容器加载此servlet时需要初始化执行
 *
 */
public class InitFileDiretoryServlet extends HttpServlet {
	private final static Logger  logger=Logger.getLogger(InitFileDiretoryServlet.class);
	private static final long serialVersionUID = 1L;
	//验证码图片文件存放目录名
	private static final String VALIDATECODE_DIRETORY="upload"+File.separator+"validateCode";
	//普通图片文件存放目录名
	private static final String IMAGES_DIRETORY="upload"+File.separator+"images";
	//普通文件存放目录名
	private static final String FILES_DIRETORY="upload"+File.separator+"files";
	//条形码放目录名
	private static final String BARCODE_DIRETORY="upload"+File.separator+"barcode";
	/**
	 * 设置文件上传的初始化信息
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		logger.info("开始生成文件存放的目录....");
		//文件存放的路径
		String parentFileDir=null;
		parentFileDir= config.getServletContext().getRealPath(File.separator);
		parentFileDir=new File(parentFileDir).getParent();//文件存储的目录与项目同级
		String validateCodeFiledir=parentFileDir+File.separator+VALIDATECODE_DIRETORY;
		logger.info("验证码图片文件存放目录路径："+validateCodeFiledir);
		File directory=new File(validateCodeFiledir);
		if(!directory.exists()){
			directory.mkdirs();
		}
		FileDiretorys.VALIDATECODE_FILE_DIRETORY=validateCodeFiledir;
		
		String imagesFiledir=parentFileDir+File.separator+IMAGES_DIRETORY;
		logger.info("普通图片文件存放目录路径："+imagesFiledir);
		directory=new File(imagesFiledir);
		if(!directory.exists()){
			directory.mkdirs();
		}
		FileDiretorys.IMAGES_FILE_DIRETORY=imagesFiledir;
		
		String filesFiledir=parentFileDir+File.separator+FILES_DIRETORY;
		logger.info("普通文件存放目录路径："+filesFiledir);
		directory=new File(filesFiledir);
		if(!directory.exists()){
			directory.mkdirs();
		}
		FileDiretorys.FILES_FILE_DIRETORY=filesFiledir;
		
		String bracodeFiledir=parentFileDir+File.separator+BARCODE_DIRETORY;
		logger.info("条形码存放目录路径："+bracodeFiledir);
		directory=new File(bracodeFiledir);
		if(!directory.exists()){
			directory.mkdirs();
		}
		FileDiretorys.BARCODE_FILE_DIRETORY=bracodeFiledir;
		logger.info("开始生成文件存放的目录....结束");
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	

}

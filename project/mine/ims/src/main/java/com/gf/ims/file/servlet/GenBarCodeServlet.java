package com.gf.ims.file.servlet;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gf.ims.file.bean.ImagePathVo;
import com.gf.ims.file.common.GlobalStaticValue;
import com.gf.ims.file.enums.FileDiretorys;
import com.gf.ims.file.enums.ServletRspErrorCode;
import com.gf.ims.file.utils.BarcodeUtil;
import com.gf.ims.file.utils.PictureUtil;
import com.gf.ims.file.utils.ServletUtil;

import net.sf.json.JSONObject;

/**
 * 生成一个二维码图片并返回图片的访问链接
 *
 */
public class GenBarCodeServlet extends HttpServlet {
	private final static Logger  logger=Logger.getLogger(GenBarCodeServlet.class);
	private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	private static final long serialVersionUID = 1L;
	/**
	 * 设置文件上传的初始化信息
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = ServletRspErrorCode.MESSAGE_RSP_SUCCESS;
		String error = "";
		ImagePathVo imagePathVo=new ImagePathVo();
		//参数
		String barCode=request.getParameter("barCode");//条形码字符串
//		String barCode="788515004012";
		if(StringUtils.isBlank(barCode)){
			Map resultMap=new HashMap<String,String>();
			resultMap.put("result", ServletRspErrorCode.MESSAGE_TOKEN_VALIDATE_ERROR_CODE);
			resultMap.put("reason", "参数barCode不能为空");
			resultMap.put("img",imagePathVo);
			String rspStr=JSONObject.fromObject(resultMap).toString();
			logger.info("rsp:"+rspStr);
			ServletUtil.sendResponse(response, rspStr);
			return;
		}
		
		//当天的日期分出的文件夹
		String dateStr=sdf.format(new Date());
		//文件存放的目录按天划分
		String filedir=FileDiretorys.BARCODE_FILE_DIRETORY+File.separator+dateStr;
		logger.info("文件上传存放的路径："+filedir);
		File directory=new File(filedir);
		if(!directory.exists()){
			directory.mkdirs();
		}
		//图片访问路径前缀
		String picUrlPrefix=GlobalStaticValue.upload_server_address+"/barcode/"+dateStr;
		try {
			//文件后缀
			String fileSuffix="png";
			String tempFileName=PictureUtil.generateFileName();
			//新文件名
			String newFileName=tempFileName+"."+fileSuffix;
			//原图文件路径
			String filepath=filedir+File.separator+newFileName;
			boolean r=BarcodeUtil.createBarCodeByJBarCode(barCode, filepath);
			if(r){//生成图片成功
				//文件的访问路劲
				imagePathVo.setO_path(picUrlPrefix+"/"+newFileName);
				imagePathVo.setT_path(picUrlPrefix+"/"+newFileName);
				logger.info("生成条形码图片成功，barCode："+barCode+",图片路径："+filepath);
			}else{
				throw new RuntimeException("生成条形码图片失败，barCode："+barCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = ServletRspErrorCode.MESSAGE_FORMAT_ERROR_CODE;
			error = ServletRspErrorCode.MESSAGE_FORMAT_ERROR_CONTENT;
		}
		//消息响应
		Map resultMap=new HashMap<String,String>();
		resultMap.put("result", result);
		resultMap.put("reason", error);
		resultMap.put("img",imagePathVo);
		String rspStr=JSONObject.fromObject(resultMap).toString();
		logger.info("rsp:"+rspStr);
		ServletUtil.sendResponse(response, rspStr);
	}
	

}

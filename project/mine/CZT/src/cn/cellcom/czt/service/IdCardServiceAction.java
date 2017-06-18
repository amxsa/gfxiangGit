package cn.cellcom.czt.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.file.FileTool;
import cn.cellcom.czt.bus.LoginBus;
import cn.cellcom.czt.po.DataMsg;
import cn.cellcom.web.struts.Struts2Base;

public class IdCardServiceAction extends Struts2Base {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(IdCardServiceAction.class);
	
	private File image1; //上传的文件
    private String image1FileName; //文件名称
    private String image1ContentType; //文件类型
    
    private File image2; //上传的文件
    private String image2FileName; //文件名称
    private String image2ContentType; //文件类型
    
    private File image3; //上传的文件
    private String image3FileName; //文件名称
    private String image3ContentType; //文件类型
    
	
	public String checkIDCard() throws IOException{
		//
		String tdCode = StringUtils.trimToNull(getParameter("tdCode"));
		String mobileNum = StringUtils.trimToNull(getParameter("mobileNum"));
		String fromPart = getParameter("fromPart");
		String timestamp =getParameter("timestamp");
		String authstring =getParameter("authstring");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		
		params.put("tdCode", tdCode);
		params.put("mobileNum", mobileNum);
		params.put("image1ExtName", FileTool.getExtName(image1FileName));
		params.put("image2ExtName", FileTool.getExtName(image2FileName));
		params.put("image3ExtName", FileTool.getExtName(image3FileName));
		params.put("fromPart", fromPart);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		PrintTool.printLog(params);
		
		LoginBus bus = new LoginBus();
		DataMsg msg = bus.checkIDCard(params, image1, image2, image3);
		log.info(tdCode+"执行身份验证:"+JSONObject.fromObject(msg));
		PrintTool.print(getResponse(), JSONObject.fromObject(msg), "json");
		return null;
	
	}
	
	
	public String queryIDCardState() throws IOException{
		
		
		String mobileNum = StringUtils.trimToNull(getParameter("mobileNum"));
		String fromPart = getParameter("fromPart");
		String timestamp =getParameter("timestamp");
		String authstring =getParameter("authstring");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		
	
		params.put("mobileNum", mobileNum);
		
		params.put("fromPart", fromPart);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		PrintTool.printLog(params);
		LoginBus bus = new LoginBus();
		DataMsg msg = bus.queryIDCardState(params);
		log.info(mobileNum+"查询实名身份信息:"+JSONObject.fromObject(msg));
		PrintTool.print(getResponse(), JSONObject.fromObject(msg), "json");
		return null;
	}
	
	public File getImage1() {
		return image1;
	}
	public void setImage1(File image1) {
		this.image1 = image1;
	}
	public String getImage1FileName() {
		return image1FileName;
	}
	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}
	public String getImage1ContentType() {
		return image1ContentType;
	}
	public void setImage1ContentType(String image1ContentType) {
		this.image1ContentType = image1ContentType;
	}
	public File getImage2() {
		return image2;
	}
	public void setImage2(File image2) {
		this.image2 = image2;
	}
	public String getImage2FileName() {
		return image2FileName;
	}
	public void setImage2FileName(String image2FileName) {
		this.image2FileName = image2FileName;
	}
	public String getImage2ContentType() {
		return image2ContentType;
	}
	public void setImage2ContentType(String image2ContentType) {
		this.image2ContentType = image2ContentType;
	}
	public File getImage3() {
		return image3;
	}
	public void setImage3(File image3) {
		this.image3 = image3;
	}
	public String getImage3FileName() {
		return image3FileName;
	}
	public void setImage3FileName(String image3FileName) {
		this.image3FileName = image3FileName;
	}
	public String getImage3ContentType() {
		return image3ContentType;
	}
	public void setImage3ContentType(String image3ContentType) {
		this.image3ContentType = image3ContentType;
	}
	
}

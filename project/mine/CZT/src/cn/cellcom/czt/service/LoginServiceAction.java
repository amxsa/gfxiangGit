package cn.cellcom.czt.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.file.FileTool;
import cn.cellcom.czt.bus.LoginBus;
import cn.cellcom.czt.po.DataMsg;
import cn.cellcom.web.struts.Struts2Base;

public class LoginServiceAction extends  Struts2Base  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(LoginServiceAction.class);
	
	private File image1; //上传的文件
    private String image1FileName; //文件名称
    private String image1ContentType; //文件类型
    
    private File image2; //上传的文件
    private String image2FileName; //文件名称
    private String image2ContentType; //文件类型
    
    private File image3; //上传的文件
    private String image3FileName; //文件名称
    private String image3ContentType; //文件类型
	
	
	public String execMethod() throws Exception {
		/**
		 * http://183.62.251.19:8082/thinkczt/index.php?g=Cztapi&m=Shb&a=Regist (激活)
		 * http://183.62.251.19:8082/thinkczt/index.php?g=Cztapi&m=Shb&a=Logins(登录)
		 * http://183.62.251.19:8082/thinkczt/index.php?g=Cztapi&m=Shb&a=Releasebind(解绑)
		 * 以前的PHP做的，要改成java且尽量保证接口地址不变，故采取以下写法,另外返回值也是要按照php以前定的接口处理
		 */
		if("Logins".equals(getParameter("a"))){
			login();
			return null;
		}else if("Regist".equals(getParameter("a"))){
			regist();
			return null;
		}else if("Releasebind".equals(getParameter("a"))){
			releasebind();
			return null;
		}else if("RegistSN".equals(getParameter("a"))){
			registSN();
			return null;
		}
		return null;
		
	}
	public void regist() throws IOException{
		String tdCode = StringUtils.trimToNull(getParameter("tdCode")) ;
		String mobileNum = StringUtils.trimToNull(getParameter("mobileNum"));
		String fromPart = getParameter("fromPart");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		params.put("a", "Regist");
		params.put("requestAddr", getRequest().getRequestURI());
		params.put("tdCode", tdCode);
		params.put("mobileNum", mobileNum);
		params.put("fromPart", fromPart);
		params.put("serviceIp", getRequest().getRemoteAddr());
		PrintTool.printLog(params);
		LoginBus bus = new LoginBus();
		DataMsg msg = bus.regist(params);
		log.info(mobileNum+"执行激活返回:"+JSONObject.fromObject(msg));
		
		PrintTool.print(getResponse(), JSONObject.fromObject(msg), "json");
	}
	
	public void registSN() throws IOException{
		
		String tdCode = StringUtils.trimToNull(getParameter("tdCode"));
		String mobileNum = StringUtils.trimToNull(getParameter("mobileNum"));
		String fromPart = getParameter("fromPart");
		String timestamp =getParameter("timestamp");
		String authstring =getParameter("authstring");
	
		Map<String ,String > params = new LinkedHashMap<String, String>();
		params.put("a", "registSn");
		params.put("requestAddr", getRequest().getRequestURI());
		params.put("tdCode", tdCode);
		
		params.put("mobileNum", mobileNum);
		params.put("fromPart", fromPart);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		params.put("serviceIp", getRequest().getRemoteAddr());
		PrintTool.printLog(params);
		LoginBus bus = new LoginBus();
		DataMsg msg = bus.registSN(params);
		log.info(mobileNum+"执行手工激活返回:"+JSONObject.fromObject(msg));
		
		PrintTool.print(getResponse(), JSONObject.fromObject(msg), "json");
	}
	
	public void login() throws IOException{
		String mobileNum = StringUtils.trimToNull(getParameter("mobileNum"));
		String timestamp =getParameter("timestamp");
		String authstring =getParameter("authstring");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		params.put("a", "login");
		params.put("requestAddr", getRequest().getRequestURI());
		params.put("mobileNum", mobileNum);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		PrintTool.printLog(params);
		LoginBus bus = new LoginBus();
		DataMsg msg = bus.login(params);
		log.info(mobileNum+"端执行登录返回:"+JSONObject.fromObject(msg));
		
		PrintTool.print(getResponse(), JSONObject.fromObject(msg), "json");
	}
	
	public void releasebind() throws IOException{
		//
		String tdCode = StringUtils.trimToNull(getParameter("tdCode"));
		String mobileNum = StringUtils.trimToNull(getParameter("mobileNum"));
		String fromPart = getParameter("fromPart");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		params.put("a", "releasebind");
		params.put("requestAddr", getRequest().getRequestURI());
		params.put("tdCode", tdCode);
		params.put("mobileNum", mobileNum);
		params.put("fromPart", fromPart);
		PrintTool.printLog(params);
		LoginBus bus = new LoginBus();
		DataMsg msg = bus.releasebind(params);
		log.info(mobileNum+"端执行解除绑定返回:"+JSONObject.fromObject(msg));
		
		PrintTool.print(getResponse(), JSONObject.fromObject(msg), "json");
	}
	
	
	public void checkIDCard() throws IOException{
		//
		String tdCode = StringUtils.trimToNull(getParameter("tdCode"));
		String fromPart = getParameter("fromPart");
		String timestamp =getParameter("timestamp");
		String authstring =getParameter("authstring");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		params.put("a", "checkIDCard");
		params.put("tdCode", tdCode);
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

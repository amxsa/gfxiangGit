package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class TColorPrintUse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6182548747948834665L;
	private Long id;
	private String regNo;//主叫号码（开通彩印服务号码）
	private String cpServer;
	private String content;
	private String myFlag;
	private String partFlag;
	private String sceneFlag;
	private String properFlag;
	
	public static String SERVER_VALID="Y";//开启服务状态
	public static String SERVER_INVALID="N";//开启服务状态
	public static String STATUS_VALID="Y";//正在使用状态
	public static String STATUS_INVALID="N";//正常状态
	public TColorPrintUse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TColorPrintUse(Long id, String regNo, String cpServer,
			String content, String myFlag, String partFlag, String sceneFlag,
			String properFlag) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.cpServer = cpServer;
		this.content = content;
		this.myFlag = myFlag;
		this.partFlag = partFlag;
		this.sceneFlag = sceneFlag;
		this.properFlag = properFlag;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getCpServer() {
		return cpServer;
	}

	public void setCpServer(String cpServer) {
		this.cpServer = cpServer;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMyFlag() {
		return myFlag;
	}
	public void setMyFlag(String myFlag) {
		this.myFlag = myFlag;
	}
	public String getPartFlag() {
		return partFlag;
	}
	public void setPartFlag(String partFlag) {
		this.partFlag = partFlag;
	}
	public String getSceneFlag() {
		return sceneFlag;
	}
	public void setSceneFlag(String sceneFlag) {
		this.sceneFlag = sceneFlag;
	}
	public String getProperFlag() {
		return properFlag;
	}
	public void setProperFlag(String properFlag) {
		this.properFlag = properFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TColorPrintUse [id=" + id + ", regNo=" + regNo + ", cpServer="
				+ cpServer + ", content=" + content + ", myFlag=" + myFlag
				+ ", partFlag=" + partFlag + ", sceneFlag=" + sceneFlag
				+ ", properFlag=" + properFlag + "]";
	}
}

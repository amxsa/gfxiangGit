package cn.cellcom.szba.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;
import cn.open.db.SqlParser.SqlParsingException;

public class TAttachment {

	private long id;
	private String type;
	private String uploadUrl;
	private String attaName;
	private String description;
	private Date createTime;
	private TAccount creator;
	private String account;
	private String caseID;   //案件id
	private String propertyID; //财物id
	private String elecEvidenceID; //电子物证id
	private int priority;
	private String valid_status;

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttaName() {
		return attaName;
	}

	public void setAttaName(String attaName) {
		this.attaName = attaName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TAccount getCreator() {
		return creator;
	}

	public void setCreator(TAccount creator) {
		this.creator = creator;
	}

	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public String getPropertyID() {
		return propertyID;
	}

	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}

	public String getElecEvidenceID() {
		return elecEvidenceID;
	}

	public void setElecEvidenceID(String elecEvidenceID) {
		this.elecEvidenceID = elecEvidenceID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getValid_status() {
		return valid_status;
	}

	public void setValid_status(String valid_status) {
		this.valid_status = valid_status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public static void main(String[] args) throws SqlParsingException {
		String sql = "select * from t_account " + " where 1=1 and (account like string*account) and (password=string:password)";
		Map<String, String[]> requestParameters = new HashMap<String, String[]>();
		requestParameters.put("account", new String[] { "11111111111111111" });
		requestParameters.put("ww", new String[] { "123456" });
		DbRequest requ = SqlParser.parse(sql, requestParameters);
		
		System.out.println(requ.getSql());
		Object[] objs = requ.getParameters();
		System.out.println();
	}
}

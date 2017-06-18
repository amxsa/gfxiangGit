package com.gf.ims.pay.module;

import java.util.Date;
public class TradePartner {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 合作方编号（唯一性约束）
	 */
	private String partnerNo;
	/**
	 * 合作方名称
	 */
	private String name;
	/**
	 * 联系人
	 */
	private String contactor;
	/**
	 * 联系电话
	 */
	private String contactTel;
	/**
	 * 合作方确定的加密密钥（唯一性约束）
	 */
	private String secretKey;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 生成时间
	 */
	private Date updateTime;
	/**
	 * 状态：0失效 1有效
	 */
	private Integer status;
	public TradePartner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TradePartner(String id, String partnerNo, String name, String contactor, String contactTel, String secretKey,
			Date createTime, Date updateTime, Integer status) {
		super();
		this.id = id;
		this.partnerNo = partnerNo;
		this.name = name;
		this.contactor = contactor;
		this.contactTel = contactTel;
		this.secretKey = secretKey;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

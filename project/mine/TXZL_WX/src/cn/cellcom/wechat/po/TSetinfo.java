package cn.cellcom.wechat.po;

/**
 * TSetinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TSetinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String type;
	private String name;
	private Integer payType;
	private Integer limitAddrList;
	private Integer limitAddrlistType;
	private Integer limitBind;
	private Integer limitBindType;
	private Integer limitSms;
	private Integer limitFreeSms;
	private Integer limitSmsType;
	private Integer limitVoiceSms;
	private Integer limitFreeVsms;
	private Integer limitVoiceSmsType;
	private Integer isTestNolong;
	private Integer isLhtx;
	private Integer isGhtz;
	private Integer isRgms;

	// Constructors

	/** default constructor */
	public TSetinfo() {
	}

	/** minimal constructor */
	public TSetinfo(String type, Integer payType) {
		this.type = type;
		this.payType = payType;
	}

	/** full constructor */
	public TSetinfo(String type, String name, Integer payType, Integer limitAddrList, Integer limitAddrlistType,
			Integer limitBind, Integer limitBindType, Integer limitSms, Integer limitFreeSms, Integer limitSmsType,
			Integer limitVoiceSms, Integer limitFreeVsms, Integer limitVoiceSmsType, Integer isTestNolong,
			Integer isLhtx, Integer isGhtz, Integer isRgms) {
		this.type = type;
		this.name = name;
		this.payType = payType;
		this.limitAddrList = limitAddrList;
		this.limitAddrlistType = limitAddrlistType;
		this.limitBind = limitBind;
		this.limitBindType = limitBindType;
		this.limitSms = limitSms;
		this.limitFreeSms = limitFreeSms;
		this.limitSmsType = limitSmsType;
		this.limitVoiceSms = limitVoiceSms;
		this.limitFreeVsms = limitFreeVsms;
		this.limitVoiceSmsType = limitVoiceSmsType;
		this.isTestNolong = isTestNolong;
		this.isLhtx = isLhtx;
		this.isGhtz = isGhtz;
		this.isRgms = isRgms;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getLimitAddrList() {
		return this.limitAddrList;
	}

	public void setLimitAddrList(Integer limitAddrList) {
		this.limitAddrList = limitAddrList;
	}

	public Integer getLimitAddrlistType() {
		return this.limitAddrlistType;
	}

	public void setLimitAddrlistType(Integer limitAddrlistType) {
		this.limitAddrlistType = limitAddrlistType;
	}

	public Integer getLimitBind() {
		return this.limitBind;
	}

	public void setLimitBind(Integer limitBind) {
		this.limitBind = limitBind;
	}

	public Integer getLimitBindType() {
		return this.limitBindType;
	}

	public void setLimitBindType(Integer limitBindType) {
		this.limitBindType = limitBindType;
	}

	public Integer getLimitSms() {
		return this.limitSms;
	}

	public void setLimitSms(Integer limitSms) {
		this.limitSms = limitSms;
	}

	public Integer getLimitFreeSms() {
		return this.limitFreeSms;
	}

	public void setLimitFreeSms(Integer limitFreeSms) {
		this.limitFreeSms = limitFreeSms;
	}

	public Integer getLimitSmsType() {
		return this.limitSmsType;
	}

	public void setLimitSmsType(Integer limitSmsType) {
		this.limitSmsType = limitSmsType;
	}

	public Integer getLimitVoiceSms() {
		return this.limitVoiceSms;
	}

	public void setLimitVoiceSms(Integer limitVoiceSms) {
		this.limitVoiceSms = limitVoiceSms;
	}

	public Integer getLimitFreeVsms() {
		return this.limitFreeVsms;
	}

	public void setLimitFreeVsms(Integer limitFreeVsms) {
		this.limitFreeVsms = limitFreeVsms;
	}

	public Integer getLimitVoiceSmsType() {
		return this.limitVoiceSmsType;
	}

	public void setLimitVoiceSmsType(Integer limitVoiceSmsType) {
		this.limitVoiceSmsType = limitVoiceSmsType;
	}

	public Integer getIsTestNolong() {
		return this.isTestNolong;
	}

	public void setIsTestNolong(Integer isTestNolong) {
		this.isTestNolong = isTestNolong;
	}

	public Integer getIsLhtx() {
		return this.isLhtx;
	}

	public void setIsLhtx(Integer isLhtx) {
		this.isLhtx = isLhtx;
	}

	public Integer getIsGhtz() {
		return this.isGhtz;
	}

	public void setIsGhtz(Integer isGhtz) {
		this.isGhtz = isGhtz;
	}

	public Integer getIsRgms() {
		return this.isRgms;
	}

	public void setIsRgms(Integer isRgms) {
		this.isRgms = isRgms;
	}

}
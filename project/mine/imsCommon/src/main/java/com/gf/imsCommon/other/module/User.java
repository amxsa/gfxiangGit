/***********************************************************************
 * Module:  User.java
 * Author:  daihf
 * Purpose: Defines the Class User
 ***********************************************************************/

package com.gf.imsCommon.other.module;

import java.util.Date;

/**
 * 用户信息表
 * 
 * @pdOid 9d4b363c-85ce-49a2-8f6f-9aa37954332e
 */
public class User implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 * 
	 * @pdOid 5da4829d-df92-491a-bed9-e7f7ee9e69d9
	 */
	public java.lang.String id;
	/**
	 * 用户类型（1、病人 2、系统超级管理员 3、普通管理员 4、医生 5、主任医生 6、护士 7、护士长
	 * 8、院长与副院长9、平台管理员10、医院管理员 11、代理商管理员账号,12商家管理员，13 医生账号 ）
	 * 
	 * @pdOid 6e636dbf-c7f1-4f25-86fe-0ed28ff966ff
	 */
	public java.lang.String userType;
	/**
	 * 客户的名称
	 * 
	 * @pdOid ccf6e943-4dec-45c4-a6e4-afe8d66e4878
	 */
	public java.lang.String userName;
	/**
	 * 昵称
	 */
	public java.lang.String nickName;
	/**
	 * 客户的帐号
	 * 
	 * @pdOid a35d6cd5-3ac2-4a51-9e59-3f6f957de030
	 */
	public java.lang.String userAccount;
	/**
	 * 身份证号
	 * 
	 * @pdOid 4b3ec308-3824-4562-8af8-ff5a1a86aef1
	 */
	public java.lang.String sfzh;
	/**
	 * 客户的密码
	 * 
	 * @pdOid 7fa5cc18-0119-499d-90b1-4def6671c070
	 */
	public java.lang.String password;
	/**
	 * 手机
	 * 
	 * @pdOid d6e304cd-9bcf-4b18-92f5-038fa4de653c
	 */
	public java.lang.String mobile;
	/**
	 * 联系电话
	 * 
	 * @pdOid bfd9ef8a-4a38-42b1-a6fb-da6bb37bdeda
	 */
	public java.lang.String phone;
	/**
	 * 电子邮箱
	 * 
	 * @pdOid d93d8b1d-2773-4caf-b4c9-b240db9999ea
	 */
	public java.lang.String email;
	/**
	 * 图像地址
	 * 
	 * @pdOid 57f35e7b-4a48-43db-b1cc-6dd52d635593
	 */
	public java.lang.String imagePath;
	/**
	 * 地址
	 * 
	 * @pdOid 91a5b3fc-43c0-4371-83c8-bcd1fc0fecab
	 */
	public java.lang.String address;
	/**
	 * 帐号的状态 0 表示帐号失效 1 表示帐号可用
	 * 
	 * @pdOid 58f6117c-8835-4f5b-b129-f6553e9baa06
	 */
	public java.lang.String accountStatus;
	/**
	 * 记录创建时间
	 * 
	 * @pdOid bb9913c9-c2af-45ec-92d9-8fad43c28ab2
	 */
	public java.util.Date createTime;
	/**
	 * 社区服务站ID
	 * 
	 * @pdOid 8165b03f-3e96-40a3-8935-439c64639f2f
	 */
	public java.lang.String communityServiceStationId;
	/**
	 * 上级的ID
	 * 
	 * @pdOid 02e53bed-5226-4524-a210-e43feb95adb3
	 */
	public java.lang.String parentId;
	/**
	 * 角色ID
	 * 
	 * @pdOid a7c8e669-505a-4e31-89bd-8e1fb432e2f9
	 */
	public java.lang.String roleId;
	/**
	 * 级别编号(系统维护 超级管理员默认为000，每多一级加三位)
	 * 
	 * @pdOid 8ff9ccf0-1710-429d-ae3b-5d36b0c3f675
	 */
	public java.lang.String levelNo;
	/**
	 * 健康档案ID
	 * 
	 * @pdOid 9b67695d-8c3b-401f-96ad-bbe8952e4cf1
	 */
	public java.lang.String grjkdaId;

	/**
	 * 医护人员ID
	 * 
	 * @pdOid 9b67695d-8c3b-401f-96ad-bbe8952e4cf1
	 */
	public java.lang.String hcwId;
	/**
	 * 所属医院ID
	 * 
	 * @pdOid 9b67695d-8c3b-401f-96ad-bbe8952e4cf1
	 */
	public java.lang.String hospitalId;
	/**
	 * 所属平台ID
	 * 
	 * @pdOid 9b67695d-8c3b-401f-96ad-bbe8952e4cf1
	 */
	public java.lang.String platformId;
	/**
	 * 性别：1男 2女
	 */
	public String sex;
	/**
	 * 出生日期
	 */
	public Date birthday;

	/**
	 * 用户的推荐码
	 */
	public java.lang.String recommendCode;
	/**
	 * 来源客户端客户端编码
	 */
	private String sourceClientCode;
	/**
	 * 来源客户端客户端名称
	 */
	private String sourceClientName;
	/**
	 * 用户会员卡号
	 */
	private String memberCard;
	/**
	 * 暗文密码（可逆）
	 */
	private String cipherPassword;
	/**
	 * 密码是否已经修改过
	 */
	private String editPassword;
	/**
	 * 最后一次登录的app的所属系统：1:ios 2:android 3:wp
	 */
	private String lastLoginOs;
	/**
	 * 最后一次登录的app客户端的资源号，如lxj_u
	 */
	private String lastLoginClient;
	/**
	 * 最后一次登录的app客户端标志手机app的token，如IOS的deviceToken
	 */
	private String lastLoginDeviceToken;

	/**
	 * Empty constructor which is required by EJB 3.0 spec.
	 *
	 */
	public User() {
		// TODO Add your own initialization code here.
	}

	public User(String id, String userType, String userName, String nickName, String userAccount, String sfzh,
			String password, String mobile, String phone, String email, String imagePath, String address,
			String accountStatus, Date createTime, String communityServiceStationId, String parentId, String roleId,
			String levelNo, String grjkdaId, String hcwId, String hospitalId, String platformId, String sex,
			Date birthday, String recommendCode, String sourceClientCode, String sourceClientName, String memberCard,
			String cipherPassword, String editPassword, String lastLoginOs, String lastLoginClient,
			String lastLoginDeviceToken) {
		super();
		this.id = id;
		this.userType = userType;
		this.userName = userName;
		this.nickName = nickName;
		this.userAccount = userAccount;
		this.sfzh = sfzh;
		this.password = password;
		this.mobile = mobile;
		this.phone = phone;
		this.email = email;
		this.imagePath = imagePath;
		this.address = address;
		this.accountStatus = accountStatus;
		this.createTime = createTime;
		this.communityServiceStationId = communityServiceStationId;
		this.parentId = parentId;
		this.roleId = roleId;
		this.levelNo = levelNo;
		this.grjkdaId = grjkdaId;
		this.hcwId = hcwId;
		this.hospitalId = hospitalId;
		this.platformId = platformId;
		this.sex = sex;
		this.birthday = birthday;
		this.recommendCode = recommendCode;
		this.sourceClientCode = sourceClientCode;
		this.sourceClientName = sourceClientName;
		this.memberCard = memberCard;
		this.cipherPassword = cipherPassword;
		this.editPassword = editPassword;
		this.lastLoginOs = lastLoginOs;
		this.lastLoginClient = lastLoginClient;
		this.lastLoginDeviceToken = lastLoginDeviceToken;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getUserType() {
		return userType;
	}

	public void setUserType(java.lang.String userType) {
		this.userType = userType;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getNickName() {
		return nickName;
	}

	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}

	public java.lang.String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(java.lang.String userAccount) {
		this.userAccount = userAccount;
	}

	public java.lang.String getSfzh() {
		return sfzh;
	}

	public void setSfzh(java.lang.String sfzh) {
		this.sfzh = sfzh;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getImagePath() {
		return imagePath;
	}

	public void setImagePath(java.lang.String imagePath) {
		this.imagePath = imagePath;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(java.lang.String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getCommunityServiceStationId() {
		return communityServiceStationId;
	}

	public void setCommunityServiceStationId(java.lang.String communityServiceStationId) {
		this.communityServiceStationId = communityServiceStationId;
	}

	public java.lang.String getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	public java.lang.String getRoleId() {
		return roleId;
	}

	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}

	public java.lang.String getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(java.lang.String levelNo) {
		this.levelNo = levelNo;
	}

	public java.lang.String getGrjkdaId() {
		return grjkdaId;
	}

	public void setGrjkdaId(java.lang.String grjkdaId) {
		this.grjkdaId = grjkdaId;
	}

	public java.lang.String getHcwId() {
		return hcwId;
	}

	public void setHcwId(java.lang.String hcwId) {
		this.hcwId = hcwId;
	}

	public java.lang.String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(java.lang.String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public java.lang.String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(java.lang.String platformId) {
		this.platformId = platformId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getRecommendCode() {
		return recommendCode;
	}

	public void setRecommendCode(java.lang.String recommendCode) {
		this.recommendCode = recommendCode;
	}

	public String getSourceClientCode() {
		return sourceClientCode;
	}

	public void setSourceClientCode(String sourceClientCode) {
		this.sourceClientCode = sourceClientCode;
	}

	public String getSourceClientName() {
		return sourceClientName;
	}

	public void setSourceClientName(String sourceClientName) {
		this.sourceClientName = sourceClientName;
	}

	public String getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(String memberCard) {
		this.memberCard = memberCard;
	}

	public String getCipherPassword() {
		return cipherPassword;
	}

	public void setCipherPassword(String cipherPassword) {
		this.cipherPassword = cipherPassword;
	}

	public String getEditPassword() {
		return editPassword;
	}

	public void setEditPassword(String editPassword) {
		this.editPassword = editPassword;
	}

	public String getLastLoginOs() {
		return lastLoginOs;
	}

	public void setLastLoginOs(String lastLoginOs) {
		this.lastLoginOs = lastLoginOs;
	}

	public String getLastLoginClient() {
		return lastLoginClient;
	}

	public void setLastLoginClient(String lastLoginClient) {
		this.lastLoginClient = lastLoginClient;
	}

	public String getLastLoginDeviceToken() {
		return lastLoginDeviceToken;
	}

	public void setLastLoginDeviceToken(String lastLoginDeviceToken) {
		this.lastLoginDeviceToken = lastLoginDeviceToken;
	}

}
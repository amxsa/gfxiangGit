package cn.cellcom.czt.po;

import java.sql.Timestamp;

import cn.cellcom.czt.common.Env;

public class OrderSettleAccounts {
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	public String getConfigure() {
		return configure;
	}
	public void setConfigure(String configure) {
		this.configure = Env.ORDER_CONFIGURE.get(configure);
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = Env.ORDER_STATE.get(orderState);
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		if (areacode!=null) {
			String[] a = Env.AREACODE.get(areacode);
			String str="";
			if(a[0].equals("广东")){
				str="号百";
			}else{
				str=a[0];
			}
			this.areacode = str+"渠道商";
		}else {
			this.areacode=null;
		}
	}

	
	private String account;
	private String originator;//结算发起人
	private String orderId;//订单编号
	private Timestamp submitTime;//结算发起时间
	private Timestamp orderTime;//订单时间
	private Timestamp verifyTime;//结算审核通过时间
	private String configure;//版本
	private Integer count;//数量
	private Integer unitPrice;//结算单价
	private Integer total;//结算总价
	private String orderState;
	private String areacode;
	private String state;
	private String id;
	private String resultReason;//审核结果
	private String projectName;
	private String contractNo;//合同编号
	private String invoiceNo;//发票编号
	private String remark;//订单备注
	private String telephone;//电话号码
	private Integer originalUnitPrice;//原始单价
	private Integer originalTotalPrice;//原始总价 
	private String verifyName;//审核人
	private String timeStr;//日期字符串
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if (state!=null) {
			this.state = Env.SETTLEACCOUNTS.get(state);
		}else {
			this.state=null;
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResultReason() {
		return resultReason;
	}
	public void setResultReason(String resultReason) {
		this.resultReason = resultReason;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getOriginalTotalPrice() {
		return originalTotalPrice;
	}
	public void setOriginalTotalPrice(Integer originalTotalPrice) {
		this.originalTotalPrice = originalTotalPrice;
	}
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	public Timestamp getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Timestamp verifyTime) {
		this.verifyTime = verifyTime;
	}
	public String getVerifyName() {
		return verifyName;
	}
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public Integer getOriginalUnitPrice() {
		return originalUnitPrice;
	}
	public void setOriginalUnitPrice(Integer originalUnitPrice) {
		this.originalUnitPrice = originalUnitPrice;
	}
	
}

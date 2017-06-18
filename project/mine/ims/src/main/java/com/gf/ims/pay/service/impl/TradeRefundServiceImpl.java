package com.gf.ims.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gf.ims.common.enums.TradePartnerStatus;
import com.gf.ims.common.util.NotifyTimer;
import com.gf.ims.common.util.PropertyContext;
import com.gf.ims.mapper.TradeRefundMapper;
import com.gf.ims.pay.module.TradeOrder;
import com.gf.ims.pay.module.TradePartner;
import com.gf.ims.pay.module.TradeRefund;
import com.gf.ims.pay.module.TradeRefundApply;
import com.gf.ims.pay.service.TradeOrderService;
import com.gf.ims.pay.service.TradePartnerService;
import com.gf.ims.pay.service.TradeRefundApplyService;
import com.gf.ims.pay.service.TradeRefundService;
import com.gf.ims.pay.util.AlipayConfig;
import com.gf.ims.pay.util.MyPayRefundUtil;


@Service("tradeRefundService")
public class TradeRefundServiceImpl implements TradeRefundService {
	public Logger log = Logger.getLogger(this.getClass());
	/**
	 * 加载退款错误码的资源文件
	 */
	private static PropertyContext propertyContext = PropertyContext.PropertyContextFactory("com/jlit/upp/resources/AlipayRefundError.properties");
	@Resource(name="tradeRefundMapper")
	private TradeRefundMapper tradeRefundMapper;
	@Resource(name="tradeOrderService")
	private TradeOrderService tradeOrderService;
	@Resource(name="tradeRefundApplyService")
	private TradeRefundApplyService tradeRefundApplyService;
	@Resource(name="tradePartnerService")
	private TradePartnerService tradePartnerService;
	
	public TradeRefund getById(String id) {
		return this.tradeRefundMapper.get(id);
	}
	
	public TradeRefund getByBatch_no(String batch_no) {
		String hql="from TradeRefund  where batch_no=?";
		List<TradeRefund> list=this.tradeRefundMapper.find(hql,batch_no);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public void save(TradeRefund tradeRefund) {
		this.tradeRefundMapper.save(tradeRefund);
		
	}
	
	public void update(TradeRefund tradeRefund) {
		this.tradeRefundMapper.update(tradeRefund);
		
	}
	
	
	/**
	 * 微信退款成功后逻辑
	 * @param batch_no
	 * @param rsp
	 */
	public void updateWxTradeRefund(String batch_no,Map<String,String> wxRspMap){
		//根据退款批次号找到退款批次记录
		TradeRefund tradeRefund=this.getByBatch_no(batch_no);
		//如果这批退款记录已经处理，则不再重复处理
		if(0!=tradeRefund.getStatus()){
			return;
		}
		//更新trade_refund
		tradeRefund.setSuccessNum(1);
		tradeRefund.setResultDetails(wxRspMap.toString());
		tradeRefund.setStatus(1);
		tradeRefund.setFinishTime(new Date());
		this.update(tradeRefund);		
	}
	
	
	public void updateTradeRefund(String batch_no, Integer success_num,
			String result_details) {
		//根据退款批次号找到退款批次记录
		TradeRefund tradeRefund=this.getByBatch_no(batch_no);
		//如果这批退款记录已经处理，则不再重复处理
		if(0!=tradeRefund.getStatus()){
			return;
		}
		//更新trade_refund
		tradeRefund.setSuccessNum(success_num);
		tradeRefund.setResultDetails(result_details);
		tradeRefund.setStatus(1);
		tradeRefund.setFinishTime(new Date());
		this.update(tradeRefund);
		//解析result_details并更新trade_order_refund
		//格式：第一笔交易#第二笔交易#第三笔交易
		//第N笔交易格式：交易退款信息
		//交易退款信息格式：原付款支付宝交易号^退款总金额^处理结果码^结果描述
		String[] dataArray=result_details.split("#");
		String trade_no=null;
		double refund_fee;
		String  refund_result=null;
		//更行TradeOrder 和TradeRefundApply
		TradeOrder tradeOrder=null;
		TradeRefundApply tradeRefundApply=null;
		for(String data:dataArray){
			 String[] details=data.split("\\^");
			 trade_no=details[0];
			 refund_fee=Double.parseDouble(details[1]);
			 refund_result=details[2];
			 tradeRefundApply=this.tradeRefundApplyService.findByTrade_noAndBatch_no(trade_no, batch_no);
			 tradeOrder=this.tradeOrderService.findByOrder_no(tradeRefundApply.getOrderNo());
			 //更新tradeRefundApply
			 tradeRefundApply.setRefundFinishTime(new Date());
			 if("SUCCESS".equals(refund_result)){//退款成功
				 tradeRefundApply.setRefundStatus(1);
				 //退款成功，更新trade_order的订单的剩余金额
				 if(null==tradeOrder.getLeftFee() || 0.00==tradeOrder.getLeftFee()){
					 tradeOrder.setLeftFee(tradeOrder.getTotalFee()-refund_fee);
				 }else{
					 tradeOrder.setLeftFee(tradeOrder.getLeftFee()-refund_fee);
				 }
			 }else{//退款失败
				 tradeRefundApply.setRefundStatus(2);
				 tradeRefundApply.setErrorCode(refund_result);
				 //从资源文件中获取结果码的中文含义，获取不到则直接存放结果码
				 String error_code_desc=propertyContext.get(refund_result);
				 if(StringUtils.isNotBlank(error_code_desc)){
					 tradeRefundApply.setErrorCodeDesc(error_code_desc);
				 }else{
					 tradeRefundApply.setErrorCodeDesc(refund_result);
				 }
			 }
			 this.tradeRefundApplyService.update(tradeRefundApply);
			 //更新trade_order
			 this.tradeOrderService.update(tradeOrder);
			 //异步通知合作方
			 this.refundNotifyClient(tradeRefundApply);
		}
		
	}
	
//	public Page list(TradeRefundQueryBean qb, int pageSize) {
//		return this.tradeRefundMapper.searchRefund(qb, pageSize);
//	}
	
	public TradeRefund createTradeRefund(List<String> applyIds, String oprator_account) {
		List<TradeRefundApply> tradeRefundApplyList=new ArrayList<TradeRefundApply>();
		TradeRefundApply tradeRefundApply=null;
		if(null!=applyIds && applyIds.size()>0){
			for(String applyId:applyIds){
				tradeRefundApply=this.tradeRefundApplyService.getById(applyId);
				if(null!=tradeRefundApply){
					tradeRefundApplyList.add(tradeRefundApply);
				}
			}
		}
		//保存trade_refund
		String batch_no=MyPayRefundUtil.generateBatchNo();
		StringBuffer detail_data=new StringBuffer();
		//封装detail_data
		for(int i=0;i<tradeRefundApplyList.size();i++){
			tradeRefundApply=tradeRefundApplyList.get(i);
			if(i==tradeRefundApplyList.size()-1){
				detail_data.append(tradeRefundApply.getTradeNo()+"^"+tradeRefundApply.getRefundFee()+"^"+tradeRefundApply.getRefundReason());
			}else{
				detail_data.append(tradeRefundApply.getTradeNo()+"^"+tradeRefundApply.getRefundFee()+"^"+tradeRefundApply.getRefundReason()+"#");
			}
		}
		TradeRefund tradeRefund=new TradeRefund();
		tradeRefund.setSellerEmail(AlipayConfig.seller_email);
		tradeRefund.setBatchNo(batch_no);
		tradeRefund.setBatchNum(tradeRefundApplyList.size());
		tradeRefund.setDetailData(detail_data.toString());
		tradeRefund.setOpratorAccount(oprator_account);
		tradeRefund.setCreateTime(new Date());
		tradeRefund.setStatus(0);
		this.tradeRefundMapper.save(tradeRefund);
		//更新TradeRefundApply的记录
		for(int i=0;i<tradeRefundApplyList.size();i++){
			tradeRefundApply=tradeRefundApplyList.get(i);
			tradeRefundApply.setRefundId(tradeRefund.getId());
			tradeRefundApply.setBatchNo(tradeRefund.getBatchNo());
			tradeRefundApply.setReadTime(new Date());
			tradeRefundApply.setReadStatus(1);
			tradeRefundApply.setOperatorAccount(oprator_account);
			this.tradeRefundApplyService.update(tradeRefundApply);
		}
		return tradeRefund;
	}
	
	public void refundNotifyClient(TradeRefundApply tradeRefundApply) {
		//构造返回给合作方同步页面return_url的参数
		Map<String, String> myParams=new HashMap<String, String>();
		//申请单号
		myParams.put("refund_apply_no", tradeRefundApply.getRefundApplyNo());
		//合作方编号
		myParams.put("partner_no", tradeRefundApply.getPartnerNo());
		//回传合作方的订单号
		myParams.put("out_trade_no", tradeRefundApply.getOut_tradeNo());
		//支付宝的交易流水号
		myParams.put("trade_no", tradeRefundApply.getTradeNo());
		//回传合作方退款时间
		myParams.put("refund_time_str", tradeRefundApply.getRefundTimeStr());
		//退款状态
		if(1==tradeRefundApply.getRefundStatus()){//退款成功
			myParams.put("refund_status", "SUCCESS");
		}else{//退款失败
			myParams.put("refund_status", tradeRefundApply.getErrorCode());
		}
		TradePartner tradePartner=this.tradePartnerService.findByPartner_no(tradeRefundApply.getPartnerNo(),TradePartnerStatus.VALID.getValue());
		//启动通知定时器
		NotifyTimer notifyTimer=new NotifyTimer(myParams,tradeRefundApply.getNotifyUrl(), tradePartner.getSecretKey());
		notifyTimer.timer=new Timer();
		//定时器1秒后启动
		notifyTimer.timer.schedule(notifyTimer.getMyTask(),1000);
		//通知合作方
		//return MypaySubmit.sendNotifyToPartner(myParams, tradeRefundApply.getNotify_url(), tradePartner.getSecret_key());
	}
	
	

}

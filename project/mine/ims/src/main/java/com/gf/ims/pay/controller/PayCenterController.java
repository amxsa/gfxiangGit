package com.gf.ims.pay.controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gf.ims.common.enums.TradeOrderTradeStatus;
import com.gf.ims.common.enums.TradePartnerStatus;
import com.gf.ims.common.util.HttpConnectUtil;
import com.gf.ims.pay.config.WxPayConfig;
import com.gf.ims.pay.module.TradeOrder;
import com.gf.ims.pay.module.TradePartner;
import com.gf.ims.pay.module.WxPayAppInfo;
import com.gf.ims.pay.service.AliPayAppInfoService;
import com.gf.ims.pay.service.TradeOrderService;
import com.gf.ims.pay.service.TradePartnerService;
import com.gf.ims.pay.service.WxPayAppInfoService;
import com.gf.ims.pay.servlet.PayRequest;
import com.gf.ims.pay.util.MyPayUtil;
import com.gf.ims.pay.util.MypaySubmit;
import com.gf.ims.pay.util.WXPayUtil;

import net.sf.json.JSONObject;
@Controller
@RequestMapping(value = "/pay")
public class PayCenterController extends MyBashController {
	@Resource(name="tradeOrderService")
	private TradeOrderService tradeOrderService;
	
	@Resource(name="tradePartnerService")
	private TradePartnerService tradePartnerService;
	
	@Resource(name="wxPayAppInfoService")
	private WxPayAppInfoService wxPayAppInfoService;
	
	@Resource(name="aliPayAppInfoService")
	private AliPayAppInfoService aliPayAppInfoService;
	
	/**
	 * 获取微信支付预付单信息，返回微信app支付-APP端调起支付需要的参数
	 * @author laizs
	 * @param pay_method 支付方式（1 支付宝 2...预留）
	 * @param partner_no 支付交易或者伙伴编号（请向宜居平台获取）
	 * @param subject 订单商品名称（多个商品名称使用逗号分隔，长度在长度小于256字符）
	 * @param total_fee 订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
	 * @param out_trade_no 唯一的订单号
	 * @param return_url 支付请求完后，当前页面自动跳转到商户网站里指定页面的http路径。
	 * @param notify_url 服务器主动通知商户网站里指定的页面http路径。
	 * @param body	交易的具体描述信息（长度小于1000字符）
	 * @param show_url 商品展示的超链接
	 * @param sign_type 加密方式  只支持MD5
	 * @param sign 签名
	 * @param channel_flag 渠道标识：1 web端 2：wap端（无线移动端）
	 * @param request
	 * @param response
	 *@return json格式的数据  return_code表示结果码，只有success才表示成功 return_msg表示错误的信息
	 *@throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/external/getWxPrepayidRequest")
	@ResponseBody
	public String getWxPrepayidRequest(int pay_method,String partner_no, String subject,double total_fee,
			String out_trade_no,String notify_url, String body,String sign_type,String sign,Integer channel_flag, String paymethod, String appid,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
			//打印请求参数日志
			this.requestParametersLog("/external/getWxPrepayidRequest",request);
			log.info("---------------getWxPrepayidRequest---------");
			//响应结果
			Map rspMap=new HashMap();
			rspMap.put("return_code", "SUCCESS");
			rspMap.put("return_msg", "");
			//获取支付请求GET过来反馈信息
			Map<String,String> params = MyPayUtil.getParams(request);
			//验证请求参数
			String verifyResult=verify(params);
			if(!"success".equals(verifyResult)){
				rspMap.put("return_code", "FAIL");
				rspMap.put("return_msg", "参数验证失败，错误码："+verifyResult);
				return JSONObject.fromObject(rspMap).toString();
			}
			//先根据appid获取微信支付的配置信息
			WxPayAppInfo wxPayAppInfo= this.wxPayAppInfoService.getByAppid(appid);
			if(wxPayAppInfo==null){
				rspMap.put("return_code", "FAIL");
				rspMap.put("return_msg", "查询不到此appid（"+appid+"）对应的配置信息");
				return JSONObject.fromObject(rspMap).toString();
			}
			//创建订单
			TradeOrder tradeOrder=this.createWxPayTradeOrder(params);
			if(null==tradeOrder){
				//创建系统订单失败
				rspMap.put("return_code", "FAIL");
				rspMap.put("return_msg", "创建系统订单失败，错误码：CREATE_SYSTEM_ORDER_FAIL");
				return JSONObject.fromObject(rspMap).toString();
			}
			//---------------调用微信统一下单接口生成预付单----------------
			String url = String.format(WxPayConfig.wx_pay_unifiedorder);
			String requestxml=getWxPrepayidRequestXml(tradeOrder, wxPayAppInfo);
			try {
				log.info("##微信统一下单接口，请求数据："+requestxml);
				String rspXml=HttpConnectUtil.getInstance().sendStrOfPost(url, requestxml);
				log.info("##微信统一下单接口，微信响应数据："+rspXml);
				Map wxRspMap=WXPayUtil.decodeXml(rspXml);
				if("SUCCESS".equals(wxRspMap.get("return_code"))){//接口结果码，与业务无关
					if("SUCCESS".equals(wxRspMap.get("result_code"))){//与业务相关的结果码
						String appId = (String)wxRspMap.get("appid");
						String partnerId = (String)wxRspMap.get("mch_id");
						String prepayId = (String)wxRspMap.get("prepay_id");
						String packageValue = "Sign=WXPay";
						String nonceStr = WXPayUtil.genNonceStr();
						String timeStamp = String.valueOf(WXPayUtil.genTimeStamp());
						//将参数经行签名
						List<NameValuePair> signParams = new LinkedList<NameValuePair>();
						signParams.add(new BasicNameValuePair("appid", appId));
						signParams.add(new BasicNameValuePair("noncestr", nonceStr));
						signParams.add(new BasicNameValuePair("package", packageValue));
						signParams.add(new BasicNameValuePair("partnerid", partnerId));
						signParams.add(new BasicNameValuePair("prepayid", prepayId));
						signParams.add(new BasicNameValuePair("timestamp", timeStamp));
						String appSign = WXPayUtil.genPackageSign(signParams, wxPayAppInfo.getApiKey());
						//返回给客户端调起支付的参数列表
						rspMap.put("appid", appId);
						rspMap.put("noncestr", nonceStr);
						rspMap.put("package", packageValue);
						rspMap.put("partnerid", partnerId);
						rspMap.put("prepayid", prepayId);
						rspMap.put("timestamp", timeStamp);
						rspMap.put("sign", appSign);
					}else{
						rspMap.put("return_code", "FAIL");
						rspMap.put("return_msg", "调用微信统一下单交易失败，错误代码："+wxRspMap.get("err_code")
								+",错误代码描述："+wxRspMap.get("err_code_de"));
					}
				}else{
					rspMap.put("return_code", "FAIL");
					rspMap.put("return_msg", "调用微信统一下单接口失败，失败信息："+wxRspMap.get("return_msg"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				rspMap.put("return_code", "FAIL");
				rspMap.put("return_msg", "调用微信统一下单接口异常："+e.getMessage());
			}
			
			return JSONObject.fromObject(rspMap).toString();
	}

	/**
	 *获取订单微信支付的支付状态（客户端在微信快捷支付流程完成后调用）
	 *@author laizs
	 *@param pay_method
	 *@param partner_no
	 *@param out_trade_no
	 *@param sign_type
	 *@param sign
	 *@param channel_flag
	 *@param appid
	 *@param request
	 *@param response
	 *@return
	 *@throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/external/getWxPayOrderStatus")
	@ResponseBody
	public String getWxPayOrderStatus(
			@RequestParam(value = "pay_method", required = false) int pay_method,
			@RequestParam(value = "partner_no", required = false) String partner_no,
			@RequestParam(value = "out_trade_no", required = false) String out_trade_no,
			@RequestParam(value = "sign_type", required = false) String sign_type,
			@RequestParam(value = "sign", required = false) String sign,
			@RequestParam(value ="channel_flag",required=false) Integer channel_flag,
			@RequestParam(value ="appid",required=false) String appid,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
			//打印请求参数日志
			this.requestParametersLog("/external/getWxPayOrderStatus",request);
			log.info("---------------getWxPayOrderStatus---------");
			//响应结果
			Map rspMap=new HashMap();
			rspMap.put("return_code", "SUCCESS");
			rspMap.put("return_msg", "");
			//获取支付请求GET过来反馈信息
			//Map<String,String> reqParams = MyPayUtil.getParams(request);
			//验证请求参数
			//先根据appid获取微信支付的配置信息
			WxPayAppInfo wxPayAppInfo= this.wxPayAppInfoService.getByAppid(appid);
			if(wxPayAppInfo==null){
				rspMap.put("return_code", "FAIL");
				rspMap.put("return_msg", "查询不到此appid（"+appid+"）对应的配置信息");
				return JSONObject.fromObject(rspMap).toString();
			}
			//查询对应的订单记录
			TradeOrder tradeOrder=this.tradeOrderService.GetWxPayOrderByCondition(out_trade_no, pay_method, appid, "t.create_time desc");
			if(null ==tradeOrder){
				rspMap.put("return_code", "FAIL");
				rspMap.put("return_msg", "根据合作伙伴方订单号获取不到微信支付类型的订单记录，合作伙伴订单号："+out_trade_no);
				return JSONObject.fromObject(rspMap).toString();
			}
			//向微信服务器查询订单信息
			try {
				String order_no=tradeOrder.getOrderNo();
				Map<String,String> reqParams = new HashMap<String,String>();
				reqParams.put("appid", appid);
				reqParams.put("mch_id", wxPayAppInfo.getMchId());
				reqParams.put("nonce_str", WXPayUtil.genNonceStr());
				reqParams.put("out_trade_no", order_no);
				//生成的签名
				reqParams.put("sign", WXPayUtil.getMySign(reqParams, wxPayAppInfo.getApiKey()));
				String entity = WXPayUtil.readMapToXml(reqParams);
				log.info("查询微信支付订单状态，向微信服务器请求数据："+entity);
				String url = String.format(WxPayConfig.wx_pay_orderquery);
				String rsp=HttpConnectUtil.getInstance().sendStrOfPost(url, entity);
				log.info("查询微信支付订单状态，微信服务器响应结果："+rsp);
				Map wxRspMap=WXPayUtil.decodeXml(rsp);
				//从微信服务器响应的结果中拿数据
				String return_code= (String) wxRspMap.get("return_code");
				rspMap.put("return_code", wxRspMap.get("return_code"));
				rspMap.put("return_msg", wxRspMap.get("return_msg"));
				if("SUCCESS".equals(return_code)){
					//业务结果
					String result_code=(String) wxRspMap.get("result_code");
					rspMap.put("result_code",result_code);//业务结果
					rspMap.put("err_code", wxRspMap.get("err_code"));//错误代码
					rspMap.put("err_code_des", wxRspMap.get("err_code_des"));//错误代码描述
					if("SUCCESS".equals(result_code)){
						//交易状态结果 
						/*
						SUCCESS—支付成功
						REFUND—转入退款
						NOTPAY—未支付
						CLOSED—已关闭
						REVOKED—已撤销（刷卡支付）
						USERPAYING--用户支付中
						PAYERROR--支付失败(其他原因，如银行返回失败)
						*/
						String trade_state=(String) wxRspMap.get("trade_state");
						rspMap.put("trade_state", wxRspMap.get("trade_state"));//
						if("SUCCESS".equals(trade_state)){
							//微信订单号
							String trade_no=(String) wxRspMap.get("transaction_id");
							//用户在商户appid下的唯一标识
							String buyer_email=(String) wxRspMap.get("openid");
							//微信支付分配的商户号
							String seller_email=(String) wxRspMap.get("mch_id");
							//判断订单是否处理过,没处理过则更新订单状态
							if(TradeOrderTradeStatus.NOT_COMPLETE.getValue()==tradeOrder.getTradeStatus()){
								//订单支付状态：已完成
								tradeOrder.setTradeStatus(TradeOrderTradeStatus.COMPLETE.getValue());
								//微信订单号
								tradeOrder.setTradeNo(trade_no);
								//户在商户appid下的唯一标识
								tradeOrder.setBuyerEmail(buyer_email);
								//微信商家账号
								tradeOrder.setSellerEmail(seller_email);
								//支付完成时间
								String time_end=(String) wxRspMap.get("time_end");
								if(StringUtils.isNotBlank(time_end)){
									tradeOrder.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(time_end));
								}
								this.tradeOrderService.update(tradeOrder);
								//######通知合作伙伴订单成功
								//构造返回给合作方同步页面return_url的参数
								Map<String, String> myParams=new HashMap<String, String>();
								//支付方式
								myParams.put("pay_method", tradeOrder.getPayMethod().toString());
								//来源渠道
								myParams.put("channel_flag", tradeOrder.getChannelFlag().toString());
								//合作方编号
								myParams.put("partner_no", tradeOrder.getPartnerNo());
								//回传合作方的订单号
								myParams.put("out_trade_no", tradeOrder.getOutTradeNo());
								//宜居平台系统的订单号
								myParams.put("trade_no", tradeOrder.getOrderNo());
								//微信的交易流水号
								myParams.put("pay_trade_no", trade_no);
								//回传合作方的商品名称subject
								myParams.put("subject", tradeOrder.getOrderSubject());
								//订单总金额
								myParams.put("total_fee", String.valueOf(tradeOrder.getTotalFee()));
								//用户在商户appid下的唯一标识
								myParams.put("buyer_email", buyer_email);
								//支付成功状态
								myParams.put("trade_status", "TRADE_SUCCESS");
								TradePartner tradePartner=this.tradePartnerService.findByPartner_no(tradeOrder.getPartnerNo(),TradePartnerStatus.VALID.getValue());
								//通知合作方
								boolean sendNofityResult=MypaySubmit.sendNotifyToPartner(myParams, tradeOrder.getNotifyUrl(), tradePartner.getSecretKey());
							}
						}
					
					}
				}
				return JSONObject.fromObject(rspMap).toString();
			} catch (Exception e) {
				e.printStackTrace();
				rspMap.put("return_code", "FAIL");
				rspMap.put("return_msg", e.getMessage());
				return JSONObject.fromObject(rspMap).toString();
			}
			
	}
	
	/**
	 * 微信支付完成，服务器异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wx_notifyUrl")
	@ResponseBody
	public String notifyUrl(
			HttpServletRequest request, HttpServletResponse response) {
		//打印请求日志
		System.out.println("微信返回信息成功==================");
		requestParametersLog("/wxPay/notifyUrl", request);
		Map<String, String> reqXML = new HashMap<String,String>();
		Element rootElement = DocumentHelper.createElement("xml");//先创建根节点
		//返回给微信的数据
		Document document = DocumentHelper.createDocument(rootElement);//根据根节点创建一个全文对象
		reqXML = WXPayUtil.readRequestToXml(request);
		log.info("微信返回信息："+JSONObject.fromObject(reqXML).toString());
		if("SUCCESS".equals(reqXML.get("return_code"))){//接口结果码，与业务无关
			if("SUCCESS".equals(reqXML.get("result_code"))){//与业务相关的结果码
				try {
					String orderNo = reqXML.get("out_trade_no");
					//获取订单信息，并从订单记录中获取api_key
					TradeOrder tradeOrder=this.tradeOrderService.findByOrder_no(orderNo);
					if(null==tradeOrder){
						log.warn("微信支付异步通知，根据订单号获取不到订单记录，订单号："+orderNo);
						throw new RuntimeException("微信支付异步通知，根据订单号获取不到订单记录，订单号："+orderNo);
					}
					String appid=tradeOrder.getAppid();
					WxPayAppInfo wxPayAppInfo=this.wxPayAppInfoService.getByAppid(appid);
					if(null==wxPayAppInfo){
						log.warn("微信支付异步通知，获取不到微信商家账户设置的API密钥，appid:"+appid);
						throw new RuntimeException("微信支付异步通知，获取不到微信商家账户设置的API密钥，appid:"+appid);
					}
					//判断签名
					boolean checkSign = WXPayUtil.checkSign(reqXML,wxPayAppInfo.getApiKey());
					if(checkSign){//验证成功,处理订单信息
						log.info("微信返回信息："+JSONObject.fromObject(reqXML).toString());
						//从微信传过来的数据中拿到我们的orderNo,去数据库中查询订单,并修改订单状态
						log.info("订单处理成功===============================");
						//微信订单号
						String trade_no=reqXML.get("transaction_id");
						//用户在商户appid下的唯一标识
						String buyer_email=reqXML.get("openid");
						//微信支付分配的商户号
						String seller_email=reqXML.get("mch_id");
						//判断订单是否处理过,没处理过则更新订单状态
						if(TradeOrderTradeStatus.NOT_COMPLETE.getValue()==tradeOrder.getTradeStatus()){
							//订单支付状态：已完成
							tradeOrder.setTradeStatus(TradeOrderTradeStatus.COMPLETE.getValue());
							//微信订单号
							tradeOrder.setOutTradeNo(trade_no);
							//户在商户appid下的唯一标识
							tradeOrder.setBuyerEmail(buyer_email);
							//微信商家账号
							tradeOrder.setSellerEmail(seller_email);
							//支付完成时间
							String time_end=(String) reqXML.get("time_end");
							if(StringUtils.isNotBlank(time_end)){
								tradeOrder.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(time_end));
							}
							this.tradeOrderService.update(tradeOrder);
							log.info("tradeOrder更新成功===============================");
						}
						//构造返回给合作方同步页面return_url的参数
						Map<String, String> myParams=new HashMap<String, String>();
						//支付方式
						myParams.put("pay_method", tradeOrder.getPayMethod().toString());
						//来源渠道
						myParams.put("channel_flag", tradeOrder.getChannelFlag().toString());
						//合作方编号
						myParams.put("partner_no", tradeOrder.getPartnerNo());
						//回传合作方的订单号
						myParams.put("out_trade_no", tradeOrder.getOutTradeNo());
						//宜居平台系统的订单号
						myParams.put("trade_no", tradeOrder.getOrderNo());
						//微信的交易流水号
						myParams.put("pay_trade_no", trade_no);
						//回传合作方的商品名称subject
						myParams.put("subject", tradeOrder.getOrderSubject());
						//订单总金额
						myParams.put("total_fee", String.valueOf(tradeOrder.getTotalFee()));
						//用户在商户appid下的唯一标识
						myParams.put("buyer_email", buyer_email);
						//支付成功状态
						myParams.put("trade_status", "TRADE_SUCCESS");
						TradePartner tradePartner=this.tradePartnerService.findByPartner_no(tradeOrder.getPartnerNo(),TradePartnerStatus.VALID.getValue());
						//通知合作方
						boolean sendNofityResult=MypaySubmit.sendNotifyToPartner(myParams, tradeOrder.getNotifyUrl(), tradePartner.getSecretKey());
						if(sendNofityResult){
							//给微信返回SUCCESS，表示支付结果处理成功
							rootElement.addElement("return_code").addText("SUCCESS");
							rootElement.addElement("return_msg").addText("OK");
						}else{
							//不给微信返回success,表示没有成功通知合作方，让微信重发异步通知
							rootElement.addElement("return_code").addText("FAIL");
							rootElement.addElement("return_msg").addText("通知合作伙伴失败");
						}
					}else{//验证失败
						rootElement.addElement("return_code").addText("FAIL");
						rootElement.addElement("return_msg").addText("02签名失败");
					}
				} catch (Exception e) {//异常
					e.printStackTrace();
					rootElement.addElement("return_code").addText("FAIL");
					rootElement.addElement("return_msg").addText(e.getMessage());
					return document.asXML();
				}
			}else{
				rootElement.addElement("return_code").addText("FAIL");
				rootElement.addElement("return_msg").addText("业务结果为FAIL");
			}
		}else{
			rootElement.addElement("return_code").addText("FAIL");
			rootElement.addElement("return_msg").addText("交易结果为FAIL错误");
		}
		String rspXml=document.asXML();
		log.info("微信支付异步通知处理，返回给微信的结果："+document.asXML());
		return rspXml;
	}
	
	 /**
     * 验证支付请求参数
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
	private String verify(Map<String, String> params){
		String pay_method=params.get("pay_method");
		if(StringUtils.isBlank(pay_method)){
			//支付方式不能为空
			return "PAY_METHOD_MUST_NOT_BU_NULL";
		}
		if(StringUtils.isBlank(PayRequest.supportPayMethodMap.get(pay_method))){
			//支付方式不支持
			return "PAY_METHOD_NOT_SUPPORT";
		}
		//验证channel_flag
		String channel_flag=params.get("channel_flag");
		
		if(StringUtils.isNotBlank(channel_flag) ){
			if(StringUtils.isBlank(PayRequest.supportChannelFlagMap.get(channel_flag))){
				return "CHANNEL_FLAG_NOT_SUPPORT";
			}
			
		}
		
		//验证subject
		String subject=params.get("subject");
		if(StringUtils.isBlank(subject)){
			//商品名称不能为空
			return "SUBJECT_MUST_NOT_BE_NULL";
		}
//		if(subject.length()>128){
//			//商品名称长度超出128长度
//			return "SUBJECT_LENGTH_OUT_OF_RANGE";
//		}
		//验证body
		String body=params.get("body");
//		if(StringUtils.isNotBlank(body)&& body.length()>500){
//			//商品描述超出500长度限制
//			return "BODY_LENGTH_OUT_OF_RANGE";
//		}
		//验证订单总金额
		String total_fee=params.get("total_fee");
		if(StringUtils.isBlank(total_fee)){
			//订单总金额不能为空
			return "TOTAL_FEE_MUST_NOT_BE_NULL";
		}
		try{
			if(Double.parseDouble(total_fee)<0.01 || Double.parseDouble(total_fee)>100000000.00){
				//订单总金额超出范围
				return "TOTAL_FEE_OUT_OF_RANGE";
			}
		}catch (Exception e) {
			return "TOTAL_FEE_OUT_OF_RANGE";
		}
		
		//验证out_trade_no
		String out_trade_no=params.get("out_trade_no");
		if(StringUtils.isBlank(out_trade_no)){
			//合作伙伴系统订单编号不能为空
			return "OUT_TRADE_NO_MUST_NOT_BE_NULL";
		}
		String return_url=params.get("return_url");
		if(StringUtils.isBlank(return_url)){
			//return_url不能为空
			return "RETURN_URL_MUST_NOT_BE_NULL";
		}
		String notify_url=params.get("notify_url");
		if(StringUtils.isBlank(notify_url)){
			//notify_url不能为空
			return "NOTIFY_URL_MUST_NOT_BE_NULL";
		}
		String sign_type=params.get("sign_type");
		if(StringUtils.isBlank(sign_type)){
			//签名方式不能为空
			return "SIGN_TYPE_MUST_NOT_BE_NULL";
		}
		if(!"MD5".equalsIgnoreCase(sign_type)){
			//签名方式不支持
			return "SIGN_TYPE_NOT_SUPPORT";
		}
		//验证合作方编号
		String partner_no=params.get("partner_no");
		if(StringUtils.isBlank(partner_no)){
			//合作方编号不能为空
			return "PARTNER_NO_MUST_NOT_BE_NULL";
		}
		TradePartner tradePartner=this.tradePartnerService.findByPartner_no(partner_no,TradePartnerStatus.VALID.getValue());
		if(null==tradePartner){
			//合作方编号不存在
			return "PARTNER_NO_NOT_EXITS";
		}
		//验证签名
		String sign=params.get("sign");
		if(StringUtils.isBlank(sign)){
			//签名不能为空
			return "SIGN_MUST_NOT_BE_NULL";
		}
		String mysign = MyPayUtil.getMysign(params, tradePartner.getSecretKey());
		if(null==sign || !mysign.equals(sign)){
			//签名验证失败
			return "ILLEGAL_SIGN";
		}
		return "success";
	}
	/**
	 * 创建订单
	 * @param params
	 * @return 创建失败返回null
	 */
	private TradeOrder createTradeOrder(Map<String, String> params){
		try {
			TradeOrder tradeOrder=new TradeOrder();
			int pay_method=Integer.parseInt(params.get("pay_method"));
			String partner_no=params.get("partner_no");
			String subject=params.get("subject");
			String body=params.get("body");
			double total_fee=Double.parseDouble(params.get("total_fee"));
			String show_url=params.get("show_url");
			String out_trade_no=params.get("out_trade_no");
			String return_url=params.get("return_url");
			String notify_url=params.get("notify_url");
			String paymethod=params.get("paymethod");
			String defaultbank=params.get("defaultbank");
			Integer channel_flag=1;
			if(StringUtils.isNotBlank(params.get("channel_flag"))){
				channel_flag=Integer.parseInt(params.get("channel_flag"));
			}
			if(StringUtils.isNotBlank(params.get("appid"))){
				tradeOrder.setAppid(params.get("appid"));
			}
			//支付方式
			tradeOrder.setPayMethod(pay_method);
			//系统唯一订单号
			tradeOrder.setOrderNo(this.tradeOrderService.createOrder_no());
			//标识是来自合作方的订单
			tradeOrder.setOrderSourceFlag(2);
			//合作方编号
			tradeOrder.setPartnerNo(partner_no);
			//商品名称
			tradeOrder.setOrderSubject(subject);
			//订单总金额
			tradeOrder.setTotalFee(total_fee);
			//商品描述
			tradeOrder.setOrderBody(body);
			//合作方真正的订单
			tradeOrder.setOutTradeNo(out_trade_no);
			//合作方订单页面跳转同步通知页面路径
			tradeOrder.setReturnUrl(return_url);
			//合作方订单服务器异步通知页面路径
			tradeOrder.setNotifyUrl(notify_url);
			//订单支付状态:0未支付
			tradeOrder.setTradeStatus(TradeOrderTradeStatus.NOT_COMPLETE.getValue());
			//订单审核状态：0 未审
			tradeOrder.setCheckStatus(0);
			//订单生成时间
			tradeOrder.setCreateTime(new Date());
			tradeOrder.setShowUrl(show_url);
			//是否有退款记录
			tradeOrder.setIsRefunded(0);
			//退款次数
			tradeOrder.setRefundTimes(0);
			//订单剩余金额
			tradeOrder.setLeftFee(total_fee);
			//渠道标识：1 web端 2：wap端（无线移动端）
			tradeOrder.setChannelFlag(channel_flag);
			
			tradeOrder.setPaymethod(paymethod);
			tradeOrder.setDefaultbank(defaultbank);
			
			this.tradeOrderService.save(tradeOrder);
			log.info("第三方支付订单创建成功:"+tradeOrder.toString());
			return tradeOrder;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("第三方支付订单创建失败，e:"+e.getMessage());
		}
		return null;
	}
	/**
	 * 创建微信支付这种支付方式的订单
	 * 因为微信支付有预付单号，而且生成预付单号时订单号不能重复，所以订单需要做特殊处理
	 *@author laizs
	 *@param params
	 *@return
	 */
	private TradeOrder createWxPayTradeOrder(Map<String, String> params){
		try {
			int pay_method=Integer.parseInt(params.get("pay_method"));
			String partner_no=params.get("partner_no");
			String subject=params.get("subject");
			String body=params.get("body");
			double total_fee=Double.parseDouble(params.get("total_fee"));
			String show_url=params.get("show_url");
			String out_trade_no=params.get("out_trade_no");
			String return_url=params.get("return_url");
			String notify_url=params.get("notify_url");
			String paymethod=params.get("paymethod");
			String defaultbank=params.get("defaultbank");
			Integer channel_flag=1;
			if(StringUtils.isNotBlank(params.get("channel_flag"))){
				channel_flag=Integer.parseInt(params.get("channel_flag"));
			}
			String appid=params.get("appid");//支付 appid
			
			//先根据out_trade_no、appid、pay_method删除支付状态为“未支付 ”的订单记录
			//int delNum=this.tradeOrderService.delWxPayOrder(out_trade_no, pay_method, appid, TradeOrderTradeStatus.NOT_COMPLETE.getValue());
			//再重新生成一条订单记录
			
			TradeOrder tradeOrder=new TradeOrder();
			//支付方式
			tradeOrder.setPayMethod(pay_method);
			//系统唯一订单号
			tradeOrder.setOrderNo(this.tradeOrderService.createOrder_no());
			//标识是来自合作方的订单
			tradeOrder.setOrderSourceFlag(2);
			//合作方编号
			tradeOrder.setPartnerNo(partner_no);
			//商品名称
			tradeOrder.setOrderSubject(subject);
			//订单总金额
			tradeOrder.setTotalFee(total_fee);
			//商品描述
			tradeOrder.setOrderBody(body);
			//合作方真正的订单
			tradeOrder.setOutTradeNo(out_trade_no);
			//合作方订单页面跳转同步通知页面路径
			tradeOrder.setReturnUrl(return_url);
			//合作方订单服务器异步通知页面路径
			tradeOrder.setNotifyUrl(notify_url);
			//订单支付状态:0未支付
			tradeOrder.setTradeStatus(TradeOrderTradeStatus.NOT_COMPLETE.getValue());
			//订单审核状态：0 未审
			tradeOrder.setCheckStatus(0);
			//订单生成时间
			tradeOrder.setCreateTime(new Date());
			tradeOrder.setShowUrl(show_url);
			//是否有退款记录
			tradeOrder.setIsRefunded(0);
			//退款次数
			tradeOrder.setRefundTimes(0);
			//订单剩余金额
			tradeOrder.setLeftFee(total_fee);
			//渠道标识：1 web端 2：wap端（无线移动端）
			tradeOrder.setChannelFlag(channel_flag);
			
			tradeOrder.setPaymethod(paymethod);
			tradeOrder.setDefaultbank(defaultbank);
			tradeOrder.setAppid(appid);
			this.tradeOrderService.save(tradeOrder);
			log.info("第三方支付订单创建成功:"+tradeOrder.toString());
			return tradeOrder;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("第三方支付订单创建失败，e:"+e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 生成获取微信预付单信息请求的参数
	 *@author laizs
	 *@param order
	 *@param appInfo
	 *@return
	 */
	private String getWxPrepayidRequestXml(TradeOrder order,WxPayAppInfo appInfo) {
		StringBuffer xml = new StringBuffer();
		String	nonceStr = WXPayUtil.genNonceStr();			
		String body =  order.getOrderSubject().length()>32?order.getOrderSubject().substring(0,32):order.getOrderSubject();
		xml.append("</xml>");
        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", appInfo.getAppid()));
		packageParams.add(new BasicNameValuePair("body",body));
		packageParams.add(new BasicNameValuePair("mch_id", appInfo.getMchId()));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		packageParams.add(new BasicNameValuePair("notify_url", WxPayConfig.wx_pay_notify_url));
		packageParams.add(new BasicNameValuePair("out_trade_no",order.getOrderNo()));
		packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
		//####因为位置支付的价格单位是分，而upp系统价格单位是元，所以需要转换价格
		packageParams.add(new BasicNameValuePair("total_fee", String.valueOf((int)(order.getTotalFee()*100))));
		packageParams.add(new BasicNameValuePair("trade_type", "APP"));
		String sign = WXPayUtil.genPackageSign(packageParams,appInfo.getApiKey());
		packageParams.add(new BasicNameValuePair("sign", sign));
	    String xmlstring =WXPayUtil.toXml(packageParams);
	    log.info(" 生成获取微信预付单信息请求的参数:"+xmlstring);
		return xmlstring;
	}
	

}

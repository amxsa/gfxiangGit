package com.gf.ims.pay.proccesser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

//import com.jlit.mms.enums.OrderPayStatus;
//import com.jlit.mms.enums.OrderStatusForGoods;
//import com.jlit.mms.service.CommodityOrdersService;
//import com.jlit.mms.service.SellerService;
//import com.jlit.mms.web.queryBean.OrdersQueryBean;
//import com.jlit.model.CommodityOrder;
//import com.jlit.model.Orders;
//import com.jlit.model.Seller;
//import com.jlit.model.TotalOrder;
//import com.trisun.message.sms.SmsSender;

/**
 * 商品订单支付成功后处理器
 */
@Service(value="commodityOrderPayProcesser")
public class CommodityOrderPayProcesser implements IPayProcesser {

	public void payAfterProcess(com.gf.imsCommon.ims.module.Orders orders, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	public int checkOrderPayValid(com.gf.imsCommon.ims.module.Orders order) {
		if(null==order){//订单不存在
			return 2;
		}
		if(order.getPayStatus().intValue()==1){//订单已经支付
			return 4;
		}
		//判断是否超过了支付时间--此中商品不需要判断
		return 1;
	}

	public void paySuccessPage(com.gf.imsCommon.ims.module.Orders orders, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	public void payTimeOutProcess(com.gf.imsCommon.ims.module.Orders orders) {
		// TODO Auto-generated method stub
		
	}
//	private final static Logger logger=Logger.getLogger(CommodityOrderPayProcesser.class);
//	private CommodityOrdersService commodityOrdersService;
//	private  SellerService sellerService;
//	private SmsSender smsSender; //发送短信
//	
//	public CommodityOrdersService getCommodityOrdersService() {
//		return commodityOrdersService;
//	}
//
//	public void setCommodityOrdersService(
//			CommodityOrdersService commodityOrdersService) {
//		this.commodityOrdersService = commodityOrdersService;
//	}
//	public SellerService getSellerService() {
//		return sellerService;
//	}
//
//	public void setSellerService(SellerService sellerService) {
//		this.sellerService = sellerService;
//	}
//
//	public SmsSender getSmsSender() {
//		return smsSender;
//	}
//
//	public void setSmsSender(SmsSender smsSender) {
//		this.smsSender = smsSender;
//	}
//	@Override
//	public void payAfterProcess(Orders orders, HttpServletRequest request,
//			HttpServletResponse response) {
//		try {
//			senSmsAfterProcess(orders);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("发送短信异常!");
//		}finally{
//			try {
//				this.commodityOrdersService.completeOrderPay(orders.getOrderNo());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		logger.info("商城商品订购单支付成功后业务处理成功！！");
//	}
//
//	private void senSmsAfterProcess(Orders orders) {
//		// TODO Auto-generated method stub
//		/**处理发送短信的逻辑 START **/
////		Seller seller = sellerService.getByBusinessId(orders.getBusinessId());
//		String phone="";
//		Seller seller = sellerService.findById(orders.getSellerId());
//		if (seller!=null) {
//			phone = seller.getMobilephone();  //店铺管理员联系方式
//			if (StringUtils.isBlank(phone)) {
//				phone=seller.getContactPhone();
//			}
//			if (phone!=null&&!phone.equals("")) {
//				try {
//					smsSender.sendSms(phone, "订单提醒：您有订单需要配送，快快登录联享家商业后台查看。");
//					logger.info("已发送短信通知店铺管理员进行配送！");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}else {
//				logger.info("店铺管理员的联系方式为空，不能够发送短信进行通知发货！店铺管理员的账号为："+seller.getAccount());
//			}
//		}else {
//			logger.info("不存在对应的店铺管理员，订单编号为："+orders.getOrderNo());
//		}
//		/**处理发送短信的逻辑 END **/
//	}
//
//	@Override
//	public int checkOrderPayValid(Orders order) {
//		if(null==order){//订单不存在
//			return 2;
//		}
//		if(order.getPayStatus().intValue()==OrderPayStatus.HASED_PAY.getValue()){//订单已经支付
//			return 4;
//		}
//		//判断是否超过了支付时间--此中商品不需要判断
//		return 1;
//	}
//	
//	@Override
//	public int checkTotalOrderPayValid(TotalOrder totalOrder) {
//		if(null==totalOrder){//订单不存在
//			return 2;
//		}
//		if(totalOrder.getPayStatus().intValue()==OrderPayStatus.HASED_PAY.getValue()){//订单已经支付
//			return 4;
//		}
//		//判断是否超过了支付时间--此中商品不需要判断
//		return 1;
//	}	
//
//	@Override
//	public void paySuccessPage(Orders orders, HttpServletRequest request,
//			HttpServletResponse response) {
//		try {
//			logger.info("订单支付成功后跳转至支付成功的页面<|>orderNo<|>"+orders.getOrderNo());
//			//
//			request.setAttribute("orders", orders);
//			request.getRequestDispatcher("/WEB-INF/pages/android/orderPaySuccessPage.jsp").forward(request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//	}
//
//	@Override
//	public void payTimeOutProcess(Orders orders) {
//		//只有订单是“未付款”状态的订单才做超时处理
//		if(OrderStatusForGoods.SUBMIT.getValue()==orders.getStatus()){
//			OrdersQueryBean queryBean=new OrdersQueryBean();
//			queryBean.setOrderNo(orders.getOrderNo());
//			List<CommodityOrder> commodityOrders= this.commodityOrdersService.getListByOrderQueryBean( queryBean);
//			if(null!=commodityOrders && commodityOrders.size()>0){
//				for(CommodityOrder co:commodityOrders){
//					co.setStatus(OrderStatusForGoods.CLOSE.getValue());//状态修改成“交易关闭”状态
//				}
//				this.commodityOrdersService.updateAll(commodityOrders);//批量更新
//			}
//		}
//		
//	}

	

}

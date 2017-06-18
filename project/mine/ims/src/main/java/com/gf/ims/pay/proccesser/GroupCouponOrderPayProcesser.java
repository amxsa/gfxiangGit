package com.gf.ims.pay.proccesser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.gf.imsCommon.ims.module.Orders;

//import com.jlit.mms.enums.OrderPayStatus;
//import com.jlit.mms.enums.OrderStatusForGoods;
//import com.jlit.mms.service.CommodityOrdersService;
//import com.jlit.mms.service.GroupCouponCommodityService;
//import com.jlit.mms.service.GroupCouponService;
//import com.jlit.mms.service.SellerService;
//import com.jlit.mms.util.DateUtil;
//import com.jlit.mms.web.queryBean.OrdersQueryBean;
//import com.jlit.model.CommodityOrder;
//import com.jlit.model.GroupCoupon;
//import com.jlit.model.Orders;
//import com.jlit.model.Seller;
//import com.jlit.model.TotalOrder;
//import com.jlit.model.vo.UserVO;
//import com.jlit.uums.webservice.IUserService;
//import com.trisun.message.sms.SmsSender;

/**
 * 团购订单支付成功后处理器
 * @author xianggf
 */
@Service(value="groupCouponOrderPayProcesser")
public class GroupCouponOrderPayProcesser implements IPayProcesser {

	public void payAfterProcess(Orders orders, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	public int checkOrderPayValid(Orders order) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void paySuccessPage(Orders orders, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	public void payTimeOutProcess(Orders orders) {
		// TODO Auto-generated method stub
		
	}
//	private final static Logger logger=Logger.getLogger(GroupCouponOrderPayProcesser.class);
//	private CommodityOrdersService commodityOrdersService;
//	private GroupCouponCommodityService groupCouponCommodityService;
//	private SmsSender smsSender; //发送短信
//	private IUserService userService;
//	private GroupCouponService groupCouponService;
//	
//	@Override
//	public void payAfterProcess(Orders orders, HttpServletRequest request,
//			HttpServletResponse response) {
//		try {
//			GroupCoupon groupCoupon = groupCouponCommodityService.getByOrderNo(orders.getOrderNo());
//			groupCoupon.setStatus(1);//设置待使用状态
//			groupCouponService.update(groupCoupon);
//			senSmsAfterProcess(orders);
//			logger.info("---------------------------------团购订单成功发送短信");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("团购订单发送短信异常!订单号为:"+orders.getOrderNo());
//		}finally{
//			try {
//				this.commodityOrdersService.completeGroupOrderPay(orders.getOrderNo());
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
//		String userMobile="";
//		UserVO user = userService.findByUserAccount(orders.getUserAccount());
//		GroupCoupon gc=null;
//		userMobile=user.getMobile();
//		gc= groupCouponCommodityService.getByOrderNo(orders.getOrderNo());
//		String code = gc.getCode();
//		String name = gc.getCommodity().getName();
//		String validityDate=DateUtil.formatDateToString("yyyy-MM-dd", gc.getValidityStartDate())+"至"+DateUtil.formatDateToString("yyyy-MM-dd", gc.getValidityEndDate());
//		smsSender.sendSms(userMobile, "订单提醒：你已成功购买【"+name+"】团购券一张,快去消费吧！团购券码为:"+code+",有效期为："+validityDate);
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
//	public CommodityOrdersService getCommodityOrdersService() {
//		return commodityOrdersService;
//	}
//
//	public void setCommodityOrdersService(
//			CommodityOrdersService commodityOrdersService) {
//		this.commodityOrdersService = commodityOrdersService;
//	}
//
//	public SmsSender getSmsSender() {
//		return smsSender;
//	}
//
//	public void setSmsSender(SmsSender smsSender) {
//		this.smsSender = smsSender;
//	}
//
//	public void setUserService(IUserService userService) {
//		this.userService = userService;
//	}
//
//	public IUserService getUserService() {
//		return userService;
//	}
//
//	public void setGroupCouponCommodityService(GroupCouponCommodityService groupCouponCommodityService) {
//		this.groupCouponCommodityService = groupCouponCommodityService;
//	}
//
//	public GroupCouponCommodityService getGroupCouponCommodityService() {
//		return groupCouponCommodityService;
//	}
//
//	public void setGroupCouponService(GroupCouponService groupCouponService) {
//		this.groupCouponService = groupCouponService;
//	}
//
//	public GroupCouponService getGroupCouponService() {
//		return groupCouponService;
//	}
//
//	@Override
//	public int checkTotalOrderPayValid(TotalOrder totalOrder) {
//		//什么都不做.
//		return 0;
//	}
//	

}

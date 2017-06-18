package com.gf.ims.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gf.ims.pay.util.MypaySubmit;
/**
 * 服务器异步通知定时器
 * 如果合作方反馈给服务器的字符不是success这7个字符，服务器会不断重发通知，直到超出8次尝试
 * 如果合作方反馈给服务器success,表示合作方已经正确收到服务器发送的交易结果的通知，停止发送通知
 * 通知的间隔频率一般是：2m,10m,10m,1h,2h,6h,15h
 * @author lzs
 *
 */
public class NotifyTimer {
	//日志对象
	public Logger log = Logger.getLogger(this.getClass());
	// 任务尝试执行次数限定
	public final static int TOTAL_TIMES = 8;
	//8次尝试的时间间隔
	public final static int[] TimeSpace={1*1000,2*60*1000,10*60*1000,10*60*1000,60*60*1000,2*60*60*1000,6*60*60*1000,15*60*60*1000};
	// 记录当前任务执行次数
	private int NOWTIME = 0;
	//是否终止定时器标识 true 终止 false不终止
	private boolean stopFlag=false;
	//定时器
	public Timer timer;
	//请求参数
	private Map<String, String> requestParams;
	//notify_utl
	private String notify_url;
	//MD5加密密钥
	private String secret_key;
	/*
	 * 是有构造函数
	 */
	private NotifyTimer(){
		
	}
	/**
	 * 构造器
	 * @param requestParams
	 * @param notify_url
	 * @param secret_key
	 */
	public NotifyTimer( Map<String, String> requestParams,String notify_url,String secret_key){
		this.requestParams=requestParams;
		this.notify_url=notify_url;
		this.secret_key=secret_key;
	}
	/**
	 * 业务逻辑，给合作方发送交易结果通知
	 * 只有合作方放回success这7个字符才不再重复发送
	 * @return
	 */
	private boolean sendPost(){
		try {
			//通知合作方
			String responseStr=MypaySubmit.sendPostInfo(this.requestParams, this.notify_url, this.secret_key);
			log.info("服务器给作方发送交易结果通知<|>notify_url<|>"+this.notify_url+"<|>params<|>"
			+this.requestParams.toString()+"<|>response<|>"+responseStr);
			if(StringUtils.isNotBlank(responseStr)&&responseStr.equals("success")){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 自定义任务类 （内部类）
	 * 
	 * @author
	 * 
	 */
	private class MyTask extends TimerTask {
		@Override
		public void run() {
			++NOWTIME;
			log.info("服务器通知合作伙伴定时器启动...,当前是第"+NOWTIME+"次通知。");
			//执行逻辑
			stopFlag=sendPost();
			//将现在的定时器取消
			timer.cancel();
			//判断是否执行下一次
			if(NOWTIME<TOTAL_TIMES && !stopFlag){
				timer=new Timer();
				timer.schedule(getMyTask(), TimeSpace[NOWTIME]);
				log.info("下次服务器通知合作伙伴在"+TimeSpace[NOWTIME]/1000+"s后执行。");
			}
		}

	}
	/**
	 * 获取新的内部类对象
	 * @return
	 */
	public MyTask getMyTask(){
		return new MyTask();
	}
	public static void main(String[] args) {
		String notify_url="http://192.168.0.121:9090/upp/payClient/notify_url.do";
		String secrect_key="9953ea00bf3b598c8fd25f36b6d79ec3";
		Map<String,String> myParams=new HashMap<String, String>();
		myParams.put("trade_no","2012110647920687");
		myParams.put("partner_no", "lhd");
		myParams.put("refund_time_str","20121107092744");
		myParams.put("out_trade_no", "707");
		myParams.put("refund_apply_no", "135");
		myParams.put("subject", "大杀器");
		myParams.put("total_fee", "0.05");
		myParams.put("trade_status", "TRADE_SUCCESS");
		NotifyTimer timeUtil=new NotifyTimer(myParams,notify_url,secrect_key);
		timeUtil.timer=new Timer();
		timeUtil.timer.schedule(timeUtil.getMyTask(),TimeSpace[0]);
	}
}

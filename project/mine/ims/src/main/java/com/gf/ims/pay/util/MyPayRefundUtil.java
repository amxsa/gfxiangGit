package com.gf.ims.pay.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gf.ims.common.util.NumericUtil;

/**
 * 退款请求简单工具类
 * 
 * @author lzs
 * 
 */
public class MyPayRefundUtil {
	/**
	 * 生成退款批次号
	 * 格式为：退款日期（14位当天日期）+流水号（5位随机数）
	 * @return
	 */
	public final static String generateBatchNo(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.format(new Date())+NumericUtil.genenateThree();
	}
	/**
	 * 生成退款请求时间
	 * @return
	 */
	public final static String generateRefundDate(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}
}

package com.gf.ims.common.util;

import java.util.Random;

/**
 * 数字简单工具类
 * @author lzs
 *
 */
public class NumericUtil {
	/**
	 * 返回一个五位数的随机数
	 * "00000"除外
	 * @return
	 */
	public final static String genenateFine(){
		Random rad=new Random();
		while(true){
			StringBuffer sb=new StringBuffer();
			sb.append(rad.nextInt(10))
			.append(rad.nextInt(10))
			.append(rad.nextInt(10))
			.append(rad.nextInt(10))
			.append(rad.nextInt(10));
			if(!"00000".equals(sb.toString())){
				return sb.toString();
			}
		}
	}
	/**
	 * 返回一个五位数的随机数
	 * "00000"除外
	 * @return
	 */
	public final static String genenateThree(){
		Random rad=new Random();
		while(true){
			StringBuffer sb=new StringBuffer();
			sb.append(rad.nextInt(10))
			.append(rad.nextInt(10))
			.append(rad.nextInt(10));
			if(!"000".equals(sb.toString())){
				return sb.toString();
			}
		}
	}
	public static void main(String[] args) {
		for(int i=0;i<80;i++){
			System.out.println(genenateThree());
		}
		
	}
}

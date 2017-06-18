package com.gf.ims.file.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片处理工具
 */
public class PictureUtil {
	/**
	 * 时间格式，精确到毫秒
	 */
	public final static SimpleDateFormat SDF=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 * 指定大小进行缩放 
	 * 如，width 200 height 300
	 * 若图片横比200小，高比300小，不变   
	 * 若图片横比200小，高比300大，高缩小到300，图片比例不变   
	 * 若图片横比200大，高比300小，横缩小到200，图片比例不变   
	 * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300   
	 * @param srcPath
	 * @param desPath
	 * @param width
	 * @param height
	 * @throws IOException 
	 */
	public static void zoomPic(String srcPath,String desPath,int width,int height) throws IOException{
		
		Thumbnails.of(srcPath).size(width, height).toFile(desPath);  
		
	}
	public static void zoomPicJAI(String srcPath,String desPath,int width,int height) throws IOException{
		
		Thumbnails.of(srcPath).size(width, height).toFile(desPath);  
	}
	/**
	 * 生成系统唯一的文件名
	 * 采用时间格式精确到毫秒与4位随机数的方式结合
	 * @param oldFileName  旧文件名
	 * @return
	 */
	public static String generateFileName( ){
		String timeStr=SDF.format(new Date());
		//四位随机号，不足四位补全至4位
		int i= RandomUtils.nextInt(10000);
		String no=String.format("%04d",i);
		return timeStr+no;
	}
	public static void main(String[] args) {
		System.out.println(generateFileName());
	}
}

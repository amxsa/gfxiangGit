package com.gf.ims.file.enums;
/**
 * 文件存放目录的定义
 * 在web容器启动时，需生成相应的目录，而且将完成目录静态变量的赋值
 * @author laizs
 * @time 2016-2-26下午3:25:18
 * @file FileDiretorys.java
 *
 */
public class FileDiretorys {
	/**
	 * 验证码图片文件存放目录名
	 */
	public static String VALIDATECODE_FILE_DIRETORY="";
	/**
	 * 普通图片文件存放目录名
	 */
	public static String IMAGES_FILE_DIRETORY="";
	/**
	 * 普通文件存放目录名
	 */
	public static String FILES_FILE_DIRETORY="";
	/**
	 * 条形码存放目录名
	 */
	public static String BARCODE_FILE_DIRETORY="";
}

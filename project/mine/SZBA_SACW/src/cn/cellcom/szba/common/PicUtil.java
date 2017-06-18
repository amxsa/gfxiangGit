package cn.cellcom.szba.common;

import net.coobird.thumbnailator.Thumbnails;

public class PicUtil {

	/**
	 * 生成缩略图
	 * @param oriPicName 原图地址
	 * @return 缩略图地址
	 */
	public static String getThumbnailPic(String oriPicPath, String urlPath){
		String thumPicPath = makeThumPicPath(oriPicPath);
		
		String thumPicUrl = makeThumPicPath(urlPath);
		
		try {
			Thumbnails.of(oriPicPath)   
			  .size(Env.WIDTH, Env.HEIGHT)   
		        .keepAspectRatio(false)  
			  .toFile(thumPicPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thumPicUrl;
	}
	
	public static String makeThumPicPath(String oriPicPath){
		String prefix = "";
		String suffix = "";
		if(oriPicPath.lastIndexOf(".") != -1){
			prefix = oriPicPath.substring(0, oriPicPath.lastIndexOf("."));
			suffix = oriPicPath.substring(oriPicPath.lastIndexOf("."));
		}else{
			prefix = oriPicPath;
			suffix = ".jpg";
		}
		String thumPicPath = prefix+"_thum"+suffix;
		return thumPicPath;
	}
	
	public static void main(String[] args) throws Exception{
		String thumPicUrl = getThumbnailPic("D:\\web\\photo\\20150805\\a.jpg", "http://192.168.7.107:8080/photo/20150805/a.jpg");
		
		System.out.println(thumPicUrl);
		
	}
}

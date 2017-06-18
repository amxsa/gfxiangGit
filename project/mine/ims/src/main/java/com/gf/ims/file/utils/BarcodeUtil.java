package com.gf.ims.file.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 * 条形码生成工具
 * @author laizs
 * @time 2016-5-11上午9:58:07
 * @file BarcodeUtil.java
 *
 */
public class BarcodeUtil {
	
/*	private static String uploadURL = "";
	
	static{
		URL location = BarcodeUtil.class.getProtectionDomain().getCodeSource().getLocation();
    	String webPath = location.toString();
		int indexOf = webPath.indexOf("WEB-INF");
		String substring = webPath.substring(6, indexOf);//截取到该文件所在项目的webapp目录
		uploadURL = substring+"/upload/img/barcode";//图片上传到本项目的目录
	}*/
	
	/**
	 * Barcode4j jar包生成条形码并存到文件中
	 *@author laizs
	 *@param bracode
	 *@param filePath
	 */
	public static boolean createBarcodeByBarcode4j(String barcode,String filePath){
		try {
			 //Create the barcode bean
	        Code39Bean bean = new Code39Bean();
	        final int dpi = 150;
	        //Configure the barcode generator
	        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar 
	        bean.setWideFactor(3);
	        bean.doQuietZone(true);
	        bean.setHeight(15);
            bean.setFontSize(4);
	        //Open output file
	        File outputFile = new File(filePath);
	        OutputStream out = new FileOutputStream(outputFile);
	        try {
	            //Set up the canvas provider for monochrome JPEG output 
	            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
	                    out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
	         
	            //Generate the barcode
	            bean.generateBarcode(canvas, barcode);
	            //Signal end of generation
	            canvas.finish();
	        } finally {
	            out.close();
	        }
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	/**
	 * jbarCode	jar包生成条形码(在条形码下方显示生成的条形码的字符)
	 * context 用于生成条码的字符串
	 * barcodeImgFilePath 条形码文件的路径
	 */
	public static Boolean createBarCodeByJBarCode(String context,String barcodeImgFilePath){
		try {  
			TextPainter instance = BaseLineTextPainter.getInstance();
            JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(),WidthCodedPainter.getInstance(),instance);  
            // 尺寸，面积，大小
            localJBarcode.setXDimension(Double.valueOf(0.5).doubleValue());
			// 条形码高度
			localJBarcode.setBarHeight(Double.valueOf(30).doubleValue());
			// 宽度率
			localJBarcode.setWideRatio(Double.valueOf(25).doubleValue());
			// 是否校验最后一位，默认是false
			localJBarcode.setShowCheckDigit(true);
            BufferedImage localBufferedImage = localJBarcode.createBarcode(context);  
            saveToGIF(localBufferedImage, barcodeImgFilePath);
            
            return true;
        }  
        catch (Exception localException) {  
        	
            localException.printStackTrace(); 
            
            return false;
        }  
	}
	
    static void saveToGIF(BufferedImage barcodeImg, String barcodeImgFilePath) {  
        saveToFile(barcodeImg, barcodeImgFilePath, "png");  
    }  
    static void saveToFile(BufferedImage paramBufferedImage, String barcodeImgFilePath, String imgType) {  
        try {
            FileOutputStream localFileOutputStream = new FileOutputStream(barcodeImgFilePath);  
            ImageUtil.encodeAndWrite(paramBufferedImage, imgType, localFileOutputStream, 300, 300);  
            localFileOutputStream.close();  
        }  
        catch (Exception localException) {  
            localException.printStackTrace();
        }  
    } 
//    //上传图片
//    private static String upImg(File file,String fileName,String fmsUrl){
//		
//		
//		File path = new File(uploadURL);
//		if(!path.exists() || !path .isDirectory()){
//			path.mkdirs();
//		}
//		String serverUrl = fmsUrl + "/imagesUpload";
//		
//		String imgUpUrl = "";
//		try {
//		if(file!=null){
//			FileInputStream fis = new FileInputStream(file);
//			FileOutputStream fos = new FileOutputStream(realPath+"/"+fileName);
//			
//			byte[] buffer = new byte[1024]; 
//			int len = 0;
//			//对文件进行边度边写
//			while((len = fis.read(buffer))>0){
//				fos.write(buffer,0,len);
//			}
//			fos.close();
//			fis.close();
//			
//			
//			File destFile = FileUtil.changeSuffix(file, fileName);
//			File[] files = { file };
//			String retVal = HttpClientUtil.transImgToFms(files,
//						serverUrl);
//			
//			JSONObject retObj = JSONObject.fromObject(retVal);
//			
//			imgUpUrl = retObj.getJSONArray("list").getJSONObject(0).getString("t_path");
//			
//			File upFile = new File(uploadURL+"/"+fileName);
//			upFile.delete();
//			file.delete();
//			return imgUpUrl;
//		}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		return null;
//	}
    public static void main(String[] args) {
    	BarcodeUtil.createBarCodeByJBarCode("1467182309221", "c://tt.png");
	}
    
}
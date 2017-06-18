package cn.cellcom.czt.test;



import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
 
import org.apache.commons.io.IOUtils;
 
public class HttpPostJson {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String URL = "http://carwx.gd118114.cn/api/weixinCoupon/get_wx_js_sdk_sign";
        String Json = findXmlInfo();
     
        Json = "{\"tokenId\" : \"07fa3cd9adea8244235e617f1552a3a020150924181316910\",\"url\" : \"http://localhost:8080/CZT/alipay/addTdCodeOrder.jsp\"}";
        String postResult =  doHttpPost(Json,URL); 
        System.out.println("postResult:"+postResult);       
         
         
 
    }
     
     
    private static String findXmlInfo() {
        // TODO Auto-generated method stub
        return null;
    }
 
 
    public static String doHttpPost(String xmlInfo,String URL){         
         System.out.println("发起的数据:"+xmlInfo);       
        byte[] xmlData = xmlInfo.getBytes();            
         InputStream instr  = null; 
         java.io.ByteArrayOutputStream out = null;              
          try{                          
                 URL url = new URL(URL);                
                 URLConnection urlCon = url.openConnection();               
                 urlCon.setDoOutput(true);              
                 urlCon.setDoInput(true);               
                 urlCon.setUseCaches(false);                            
                 urlCon.setRequestProperty("Content-Type", "text/xml");       
                 urlCon.setRequestProperty("Content-length",String.valueOf(xmlData.length)); 
                 System.out.println(String.valueOf(xmlData.length));
                 DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());            
                 printout.write(xmlData);               
                 printout.flush();              
                 printout.close();              
                 instr = urlCon.getInputStream();   
                 byte[] bis = IOUtils.toByteArray(instr);
                 String ResponseString = new String(bis, "UTF-8");  
                if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                     System.out.println("返回空");
                    }
               System.out.println("返回数据为:" + ResponseString);
              return ResponseString;    
             
          }catch(Exception e){              
                 e.printStackTrace();
                return "0";
          }             
          finally {             
                 try {          
                        out.close();   
                        instr.close();  
                         
                 }catch (Exception ex) {      
                        return "0";
                     }                  
                 }                  
          }                     
     
 
}

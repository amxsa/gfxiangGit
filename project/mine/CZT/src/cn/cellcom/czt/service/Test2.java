package cn.cellcom.czt.service;
import java.io.File;  
import java.io.IOException;  
  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.mime.MultipartEntity;  
import org.apache.http.entity.mime.content.FileBody;  
import org.apache.http.entity.mime.content.StringBody;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.util.EntityUtils;  

import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.common.Env;
  
public class Test2 {  
      
    public static void main(String[] args) throws ClientProtocolException, IOException {  
        //post();  
    	//System.out.println(new File("E:/a.txt").getName());
    	String a = "13788052976";
    	Long b = 1449714989L;
    	String authstring =MD5.getMD5((a+Env.SERVICE_KEY_APP+String.valueOf(b)).getBytes());
    	System.out.println(authstring);
    }  
  
    public static void post() throws ClientProtocolException, IOException {  
        HttpClient httpclient = new DefaultHttpClient();
       // String tdCode = "O10Hf54811b643d83cd1";
        String tdCode = "448815000005";
        //long timestamp =System.currentTimeMillis();
        long timestamp =20151209175535L;
        String url = "http://183.62.251.19:8089/thinkczt/IdCardServiceAction_checkIDCard.do?";
        //String url = "http://localhost:8080/CZT/IdCardServiceAction_checkIDCard.do?";
        String authstring =MD5.getMD5((tdCode+Env.SERVICE_KEY_APP+String.valueOf(timestamp)).getBytes());
        StringBuffer str = new StringBuffer(url+"g=Cztapi&m=Shb&a=checkIDCard");
        str.append("&tdCode=").append(tdCode);
        str.append("&fromPart=");
        str.append("&timestamp=").append(timestamp);
        str.append("&authstring=").append(authstring);
        
        HttpPost post = new HttpPost(str.toString());  
        FileBody fileBody1 = new FileBody(new File("E:/Temp/test/1.jpg"));  
        FileBody fileBody2 = new FileBody(new File("E:/Temp/test/2.jpg"));  
        FileBody fileBody3 = new FileBody(new File("E:/Temp/test/3.jpg"));  
      
        MultipartEntity entity = new MultipartEntity();  
        entity.addPart("image1", fileBody1);  
        entity.addPart("image2", fileBody2); 
        entity.addPart("image3", fileBody3); 
        post.setEntity(entity);  
        HttpResponse response = httpclient.execute(post);  
        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){    
              
            HttpEntity entitys = response.getEntity();  
            if (entity != null) {  
                System.out.println(entity.getContentLength());  
                System.out.println(EntityUtils.toString(entitys));  
            }  
        }  
        httpclient.getConnectionManager().shutdown();  
    }  
}  
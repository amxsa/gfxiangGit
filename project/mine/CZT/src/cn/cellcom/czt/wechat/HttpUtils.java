package cn.cellcom.czt.wechat;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;









import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by guan on 15-9-9.
 */
public class HttpUtils {
    public static JSONObject post(String url,JSONObject req){
        //HttpClient client = new DefaultHttpClient();
        HttpClient client =  HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        JSONObject resp = null;
        try {
            StringEntity s = new StringEntity(req.toString(), Charset.forName("UTF-8"));
            s.setContentType("application/json");
            post.setEntity(s);
 
            HttpResponse res = client.execute(post);
           
            if(res.getStatusLine().getStatusCode() == 200){
                //String charset = EntityUtils.getContentCharSet(entity);
                //读取响应内容
                String entity = EntityUtils.toString(res.getEntity(), "UTF-8");
                resp =  JSONObject.fromObject(entity);
            }
            return resp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static JSONObject postObd(String url,JSONObject req){
        //HttpClient client = new DefaultHttpClient();
        HttpClient client =  HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        JSONObject resp = null;
        Object object = req.get("tokenId");
        Object object2 = req.get("obdpdm");
        String postStr="{"+"\"tokenId\":\""+object+"\","+"\"obdpdm\":\""+object2+"\"}";
        System.out.println(postStr);
        try {
            StringEntity s = new StringEntity(postStr, Charset.forName("UTF-8"));
            s.setContentType("application/json");
            post.setEntity(s);
 
            HttpResponse res = client.execute(post);
           
            if(res.getStatusLine().getStatusCode() == 200){
                //String charset = EntityUtils.getContentCharSet(entity);
                //读取响应内容
                String entity = EntityUtils.toString(res.getEntity(), "UTF-8");
                resp =  JSONObject.fromObject(entity);
            }
            return resp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public  static String login(){
        String tokenId;
        JSONObject obj = new JSONObject();
        obj.put("account", "api_weixin");
        obj.put("password","API!@#890");
        obj.put("accountType","1");

        JSONObject respJ = post("http://59.41.186.92/api/sys/login", obj);
        tokenId = respJ.getString("tokenId");

        return tokenId;
    }
    /**
     * 登陆获取令牌 tokenid
     * @return
     */
    public  static String loginObd(){
    	String tokenId=null;
    	JSONObject obj = new JSONObject();
    	obj.put("account", "123");
    	obj.put("password","123");
    	obj.put("accountType","1");
    	
    	try {
			HttpClient client =  HttpClientBuilder.create().build();
			HttpPost post = new HttpPost("http://obd.chelulu.cn:8888/obd-web/api/login");
			post.addHeader("Content-Type", "application/json;charset=UTF-8");
			
			StringEntity s = new StringEntity(obj.toString(), Charset.forName("UTF-8"));
			s.setContentType("application/json");
			post.setEntity(s);
			JSONObject resp;
			HttpResponse res = client.execute(post);
			if(res.getStatusLine().getStatusCode() == 200){
			    String entity = EntityUtils.toString(res.getEntity(), "UTF-8");
			    resp =  JSONObject.fromObject(entity);
			    tokenId = resp.getString("tokenId");
			    return tokenId;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	return tokenId;
    }

}

package cn.cellcom.czt.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.bus.LoginBus;
import cn.cellcom.czt.bus.TDCodeBus;
import cn.cellcom.czt.common.DataPo;
import cn.cellcom.czt.common.ServiceTool;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.SpTdcode;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class TDCodeServiceAction extends Struts2Base {
	private static final Log log = LogFactory.getLog(TDCodeServiceAction.class);
	public String queryMobile() throws IOException {
		String SN = getParameter("SN");
		String timestamp = getParameter("timestamp");
		String authstring = getParameter("authstring");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("SN", SN);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		PrintTool.printLog("SN查询手机号",params);
		DataPo dataPo = new DataPo("F", -1001);
		TDCodeBus bus = new TDCodeBus();
		try {
			dataPo = bus.queryMobile(params, dataPo);
			Object obj = JSONObject.fromObject(dataPo);
			log.info("SN查询手机号码返回:" + obj.toString());
			PrintTool.print(getResponse(), obj, "json");
		} catch (Exception e) {
			ServiceTool.errorDB("SN查询手机号码异常", getResponse(), e);
		}
		return null;
	}
	
	public String querySN() throws IOException {
		String mobileNum = getParameter("mobileNum");
		String timestamp = getParameter("timestamp");
		String authstring = getParameter("authstring");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("mobileNum", mobileNum);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		PrintTool.printLog("手机号查询SN",params);
		DataPo dataPo = new DataPo("F", -3001);
		TDCodeBus bus = new TDCodeBus();
		try {
			dataPo = bus.querySNByMobile(params, dataPo);
			Object obj = JSONObject.fromObject(dataPo);
			log.info("手机号查询SN返回:" + obj.toString());
			PrintTool.print(getResponse(), obj, "json");
		} catch (Exception e) {
			ServiceTool.errorDB("手机号查询SN异常", getResponse(), e);
		}
		return null;
	}
	
	public String releaseBind() throws IOException {
		
		String mobileNum = getParameter("mobileNum");
		String fromPart = getParameter("fromPart");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("mobileNum", mobileNum);
		params.put("fromPart", fromPart);
		PrintTool.printLog("手机号解绑",params);
		Data data=new Data(false, "解绑失败");
		DataPo dp=new DataPo();
		LoginBus bus=new LoginBus();
		try {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			
			if (!(StringUtils.isBlank(mobileNum)||StringUtils.isNotBlank(fromPart))) {
				dp.setCode(-5002);
				dp.setState("F");
				dp.setTime(DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH-mm-ss"));
				dp.setMsg("解绑失败，原因缺少必要参数");
				log.info("手机号解绑返回:" + JSONObject.fromObject(dp).toString());
				PrintTool.print(getResponse(), JSONObject.fromObject(dp).toString(), "json");
				return null;
			}
			SpTdcode po = jdbc
					.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select ID,mobilenum, mobileid,tdcode,tdcodemd5 , status,barcode,group_id,fluxcard,limite_time  from sp_tdcode where mobilenum = ?  ",
							SpTdcode.class, new String[] { mobileNum });
			if (po == null) {
				dp.setCode(1);
				dp.setState("F");
				dp.setTime(DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH-mm-ss"));
				dp.setMsg(mobileNum + "手机号码不存在");
				log.info("手机号解绑返回:" + JSONObject.fromObject(dp).toString());
				PrintTool.print(getResponse(), JSONObject.fromObject(dp).toString(), "json");
				return null;
			}
			
			data=bus.releasebindManager(mobileNum, fromPart);
			
			if (data.isState()) {
				dp.setCode(0);
				dp.setState("Y");
				dp.setTime(DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH-mm-ss"));
				dp.setMsg(mobileNum + "解绑成功");
			}else{//其他原因 解绑失败
				dp.setCode(-5003);
				dp.setState("F");
				dp.setTime(DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH-mm-ss"));
				dp.setMsg("解绑失败，原因"+data.getMsg());
			}
			log.info("手机号解绑返回:" + JSONObject.fromObject(dp).toString());
			PrintTool.print(getResponse(), JSONObject.fromObject(dp).toString(), "json");
		} catch (Exception e) {
			ServiceTool.errorDB("手机号解绑异常", getResponse(), e);
		}
		return null;
	}

	/*private void insertIntoObd() {//插入数据到中间表
		// TODO Auto-generated method stub
		insertIntoObd();
		JDBC jdbc= (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			List<SpTdcode> list = jdbc.query(ApplicationContextTool.getDataSource(),
					"select * from sp_tdcode where tdcode like 'O13%'", SpTdcode.class, null, 0, 0);
			for (SpTdcode sp : list) {
				jdbc.update(ApplicationContextTool.getDataSource(), 
						"insert into t_sync_tdcode_obd(ID,tdcode,orderid,status,tdcodemd5,mobilenum,mobileid," +
						"tdverify,obdactive,activetime,submittime,barcode,group_id,fluxcard,limite_time,idcard_state)" +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{sp.getID(),sp.getTdcode(),sp.getOrderid(),
					sp.getStatus(),sp.getTdcodemd5(),sp.getMobilenum(),sp.getMobileid(),sp.getTdverify(),
					sp.getObdactive(),sp.getActivetime(),sp.getSubmittime(),sp.getBarcode(),sp.getGroupId(),
					sp.getFluxcard(),sp.getLimiteTime(),sp.getIdcardState()});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	/*public String test1(){
		String url="http://192.168.7.21:8080/CZT/service/TDCodeServcieAction_obd.do";
		HttpClient client =  HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
       
        JSONObject jo=new JSONObject();
		JSONObject pdm=new JSONObject();
		List<String> pdmList=new ArrayList<String>();
        pdm.put("aa", "aa");
        pdm.put("bb", "bb");
        pdm.put("cc", "cc");
		pdmList.add(pdm.toString());
        jo.put("tokenid", "123");
        jo.put("obdpdm",pdmList.toString());
        System.out.println(jo.toString());
        try {
            StringEntity s = new StringEntity(jo.toString(), Charset.forName("UTF-8"));
            s.setContentType("application/json");
            post.setEntity(s);
 
            HttpResponse res = client.execute(post);
           
            if(res.getStatusLine().getStatusCode() == 200){
                //String charset = EntityUtils.getContentCharSet(entity);
                //读取响应内容
                String entity = EntityUtils.toString(res.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
		return null;
	}
	
	
	public String obd(){
		String type = getRequest().getContentType();
		return null;
	}*/
}

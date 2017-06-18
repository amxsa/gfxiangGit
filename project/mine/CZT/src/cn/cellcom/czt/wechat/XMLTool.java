package cn.cellcom.czt.wechat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.encrypt.DESTool;
import cn.cellcom.czt.common.Env;

public class XMLTool {
	public static Map doXMLParse(String strxml) throws JDOMException, IOException {

        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));

        SAXBuilder builder = new SAXBuilder();

        Document doc = builder.build(in);

        Element root = doc.getRootElement();

        List list = root.getChildren();

        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";

            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();

            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        in.close();

        return m;
	}
	public static String getChildrenText(List children) {

        StringBuffer sb = new StringBuffer();

        if(!children.isEmpty()) {

            Iterator it = children.iterator();

            while(it.hasNext()) {

                Element e = (Element) it.next();

                String name = e.getName();

                String value = e.getTextNormalize();

                List list = e.getChildren();

                sb.append("<" + name + ">");

                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }

                sb.append(value);

                sb.append("</" + name + ">");

            }

        }

        return sb.toString();

    }
	
	public static void main(String[] args) throws Exception {
		String str="<xml><appid><![CDATA[wx2dcc25fc80501211]]></appid><attach><![CDATA[attach]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><data><![CDATA[j1ESf/wy 5nxIfYNdwfpDCzdG7xADna/SOoS0DFYw0YlhGzJmH 4vQ==]]></data><device_info><![CDATA[WEB]]></device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1220892601]]></mch_id><nonce_str><![CDATA[ydv1etqfxd4u66puneoxab17i0c1issx]]></nonce_str><openid><![CDATA[okU2Tjjidurcjd-peWw0PCnLtppo]]></openid><out_trade_no><![CDATA[20151015203659]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[A9C772BC0B535603352687D6626F7F49]]></sign><time_end><![CDATA[20151015203710]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1006470755201510151210004043]]></transaction_id></xml>";
		Map map = doXMLParse(str);
		String data = map.get("data").toString();
		System.out.println(data);
		data = DESTool.decryptDES(data, Env.SERVICE_SP);
		System.out.println(data);
	}
        

}

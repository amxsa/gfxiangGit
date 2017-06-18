package cn.cellcom.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.cellcom.common.file.Msg;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.DefaultMapper;

public class XMLTool {
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws IOException, DocumentException {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
	
	private static final String title = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

	/**
	 * 集合转成XML格式
	 * 
	 * @param collection
	 * @param alias
	 *            别名
	 * @param isTitle
	 *            是否需要xml头
	 * @return
	 */
	public static String list2Xml(Collection<?> collection, String alias,
			boolean isTitle) {
		// 创建XStream对象
		XStream xs = new XStream(new DomDriver());
		// 为所有的类创建别名，别名为不包含包名的类名
		for (int i = 0; i < collection.size(); i++) {
			Class clzz = collection.iterator().next().getClass();
			if (StringUtils.isBlank(alias)) {
				// 得到全限定类名
				String fullName = clzz.getName();
				// 以"."分割字符串
				String[] temp = fullName.split("\\.");
				xs.alias(temp[temp.length - 1], clzz);
			} else {
				xs.alias(alias, clzz);
			}
		}
		if (isTitle) {
			return new StringBuilder(title).append(xs.toXML(collection))
					.toString();
		}
		return xs.toXML(collection);
	}

	/**
	 * bean转成XML格式
	 * 
	 * @param object
	 * @param alias
	 * @param isTitle
	 * @return
	 */
	public static String bean2Xml(Object bean, String alias, boolean isTitle) {
		XStream xs = new XStream(new DomDriver());
		Class clazz = bean.getClass();
		if (StringUtils.isBlank(alias)) {
			String[] temp = clazz.getName().split("\\.");
			alias = temp[temp.length - 1];
		}
		xs.alias(alias, clazz);
		if (isTitle) {
			return new StringBuilder(title).append(xs.toXML(bean)).toString();
		}
		return xs.toXML(bean);
	}

	/**
	 * map转成XML格式
	 * 
	 * @param map
	 * @param isTitle
	 * @return
	 */
	public static String map2Xml(Map map, boolean isTitle) {
		StringBuilder xml = new StringBuilder("");
		if (isTitle) {
			xml.append(title);
		}
		XStream xStream = new XStream();
		xStream.registerConverter(new MapCustomConverter(
				new DefaultMapper(null)));
		return xml.append(xStream.toXML(map)).toString();

	}

	public static void main(String[] args) {

		List inList = new ArrayList();
		Msg msg1 = new Msg();
		msg1.setFlag(true);
		msg1.setMsg("123");
		Msg msg2 = new Msg();
		msg2.setFlag(false);
		msg2.setMsg("456");
		inList.add(msg1);
		inList.add(msg2);
		System.out.println(XMLTool.bean2Xml(msg1, "user", false));
		// System.out.println(objectToXml(msg1,false));
		// System.out.println(objectToXml(new String[]{"123","456"},true));
		Map map = new HashMap<String, String>();
		map.put("msg1", "123");
		map.put("msg2", "456");
		System.out.println(map2Xml(map, true));

	}
}

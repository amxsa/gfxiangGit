package cn.cellcom.szba.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.utils.XmlConvertUtil;
import cn.cellcom.szba.databean.Flownode;
import cn.cellcom.szba.databean.Flownodes;

/**
 *	流程结点引擎，用于获取当前结点的下一个结点连线。
 *	@author ypchen
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class FlownodeEngine {
	private static Log log = LogFactory.getLog(FlownodeEngine.class);

	private static Map<String, Map<String, Flownode>> flownodeMapper;
	private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private static String rootDirectoryPath = "/flownode";
	private static String realPath = "";

	/**
	 * 从xml文件夹中读取所有文件，并初始化flownodeMapper
	 */
	public static void init() {
		flownodeMapper = new HashMap<String, Map<String, Flownode>>();
		File xmlRoot = new File(realPath);

		File[] flownodeDirectory = xmlRoot.listFiles();
		if (null == flownodeDirectory || 0 == flownodeDirectory.length) {
			log.info("找不到xml文件。");
			return;
		}
		for (File file : flownodeDirectory) {
			if(file.isDirectory()){
				for(File cFile : file.listFiles()){
					FlownodeEngine.refresh(cFile);
				}
			} else{
				FlownodeEngine.refresh(file);
			}
		}
		
		log.info("FlownodeEngine init success.");
		FlownodeEngine.printFlownodes();
	}

	/**
	 * 刷新某个xml文件的对应的 flownodeMapper内容。
	 */
	public static void refresh(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".xml") < 0) return;
		String flowname = fileName.substring(0, fileName.lastIndexOf(".xml"));
		if (!file.exists()) {
			log.info("找不到xml文件。");
			return;
		}
		InputStream in;
		try {
			in = new FileInputStream(file);
			StringWriter writer = new StringWriter();
			IOUtils.copy(in, writer, "UTF-8");
			String xml = writer.toString();
			Flownodes flownodes = XmlConvertUtil.convertToJavaBean(xml, Flownodes.class);
			Map<String, Flownode> map = new HashMap<String, Flownode>();
			for (Flownode flownode : flownodes.getFlownodes()) {
				map.put(flownode.getName(), flownode);
			}
			flownodeMapper.put(flowname, map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据{流程名_结点名}和当前参数获得流程结点连线
	 */
	public static Flownode getFlownode(String flowname, Map<String, String[]> params) {
		Map<String, Flownode> flownodes = flownodeMapper.get(flowname);
		if (null == flownodes) {
			log.info("找不到结点:" + flowname);
			return null;
		}
		for (String key : flownodes.keySet()) {
			Flownode flownode = flownodes.get(key);
			String expression = flownode.getConditionExpression();
			try {
				Boolean result = FlownodeEngine.runCondition(expression, params);
				if (true == result) {
					return flownode;
				}
			} catch (Exception e) {
				log.error("Flownode key:" + key + ", expression:{" + expression + "} Exception:" + e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * 根据{流程名_结点名}和流程结点连线名获得流程结点连线
	 */
	public static Flownode getFlownode(String flowname, String flownode) {
		Map<String, Flownode> flownodes = flownodeMapper.get(flowname);
		if (null == flownodes) {
			log.info("找不到结点:" + flowname);
			return null;
		}
		return flownodes.get(flownode);
	}

	public static Boolean runCondition(String expression, Map<String, String[]> params) throws Exception {
		ScriptEngine engine = scriptEngineManager.getEngineByName("js");
		for (String key : params.keySet()) {
			String[] val = params.get(key);
			if (null != val && 0 != val.length) {
				engine.put(key, val[0]);
			}
		}
		Object result;
		try {
			result = engine.eval(expression);
		} catch (Exception e) {
			throw e;
		}
		if (result instanceof Boolean) {
			return (Boolean) result;
		} else {
			throw new Exception("表达式结果类型有误。");
		}
	}
	
	public static void printFlownodes(){
		for(String key: flownodeMapper.keySet()){
			log.info("#### " + key);
			Map<String, Flownode> map = flownodeMapper.get(key);
			for(String k: map.keySet()){
				log.info("\t"+map.get(k));
			}
		}
	}

	public static String getRealPath() {
		return realPath;
	}

	public static void setRealPath(String realPath) {
		FlownodeEngine.realPath = realPath;
	}

	public static String getRootDirectoryPath() {
		return rootDirectoryPath;
	}
	
}

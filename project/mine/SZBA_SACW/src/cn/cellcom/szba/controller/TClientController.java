package cn.cellcom.szba.controller;

import static cn.cellcom.szba.common.Env.ENV_PRO;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.common.DateTool;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.utils.HttpHelper;

@RequestMapping("/client")
@Controller
public class TClientController {
	Log log = LogFactory.getLog(this.getClass());
	String clientUrl = ENV_PRO.getProperty("client_url");

	// 客户端通用请求方法 参数为json
	@RequestMapping("/clientRequestJson")
	public void clientRequestJson(HttpServletRequest requ,
			HttpServletResponse response) {
		String rurl = requ.getParameter("url");
		String url = clientUrl + rurl;
		String params = requ.getParameter("params");
		log.info("clientRequest url=" + url + ";params=" + params);
		String ret = HttpHelper.postStr(url, params, null);
		try {
			PrintTool.print(response,
					PrintTool.printJSON(ret.replaceAll("[\\t\\n\\r]", "")),
					"json");
		} catch (IOException e) {
			log.error("", e);
		}
	}

	// 客户端通用请求方法 参数为map
	@RequestMapping("/clientRequestMap")
	public void clientRequestMap(HttpServletRequest requ,
			HttpServletResponse response) {
		String rurl = requ.getParameter("url");
		String url = clientUrl + rurl;
		String params = requ.getParameter("params");
		log.info("clientRequest url=" + url + ";params=" + params);
		String ret = HttpHelper.postMap(url, params, null);
		try {
			PrintTool.print(response,
					PrintTool.printJSON(ret.replaceAll("[\\t\\n\\r]", "")),
					"json");
		} catch (IOException e) {
			log.error("", e);
		}
	}

	// 客户端通用请求方法 参数为map
	@RequestMapping("/clientRequestMapFile")
	public void clientRequestMapFile(HttpServletRequest requ,
			HttpServletResponse response) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext servletContext = requ.getSession().getServletContext();
		File repository = (File) servletContext
				.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		try {
			List<FileItem> items = upload.parseRequest(requ);
			Iterator<FileItem> iter = items.iterator();
			String ret = "";
			Map<String, String> p1 = new HashMap<String, String>();
			Map<String, File> p2 = new HashMap<String, File>();
			String url = "";
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (item.isFormField()) {
					log.info(item.isFormField() + ">>>" + item.getFieldName()
							+ "," + item.getString());
					if (!"Filename".equals(item.getFieldName())
							&& !"url".equals(item.getFieldName())) {
						p1.put(item.getFieldName(), item.getString());
					}
					if ("url".equals(item.getFieldName())) {
						url = clientUrl + item.getString();
						p1.put(item.getFieldName(), url);
						log.info("clientRequest url=" + url);
					}
				} else {
					String filePath = Env.ENV_PRO.getProperty("photo_dir")
							+ DateTool.formateTime2StringYMD(new Date());
					String fileName = createNewName(item.getName());
					if (!new File(filePath).exists()) {
						new File(filePath).mkdirs();
					}
					File file = new File(filePath, fileName);
					log.info("saving file : " + file);
					item.write(file);
					p2.put("photographs", file);
				}
			}
			ret = HttpHelper.postMapFile(url, p1, p2, null);
			PrintTool.print(response,
					PrintTool.printJSON(ret.replaceAll("[\\t\\n\\r]", "")),
					"json");
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	private String createNewName(String fileName){
		String ext = FilenameUtils.getExtension(fileName);
		
		System.out.println(FilenameUtils.getName(fileName));
		
		String newName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext;
		return newName;
	}
}

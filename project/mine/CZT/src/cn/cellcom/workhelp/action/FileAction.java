package cn.cellcom.workhelp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.file.FileTool;
import cn.cellcom.common.file.Msg;
import cn.cellcom.web.struts.Struts2Base;
import cn.cellcom.workhelp.bus.WorkHelpCommon;
import cn.cellcom.workhelp.po.FileItem;
import cn.cellcom.workhelp.po.WorkSQL;

public class FileAction extends Struts2Base {
	private static final Log log = LogFactory.getLog(FileAction.class);
	private File uploadify;

	private String uploadifyFileName;

	public File getUploadify() {
		return uploadify;
	}

	public void setUploadify(File uploadify) {
		this.uploadify = uploadify;
	}

	public String getUploadifyFileName() {
		return uploadifyFileName;
	}

	public void setUploadifyFileName(String uploadifyFileName) {
		this.uploadifyFileName = uploadifyFileName;
	}

	private FileItem getFileItem(File file) {
		FileItem fileItem = new FileItem();
		fileItem.setParent(WorkHelpCommon.changeSeparator(file.getParent()));
		fileItem.setDirectory(file.isDirectory());
		fileItem.setFile(file);
		fileItem.setFileName(file.getName());
		fileItem.setCreateTime(new Date(file.lastModified()));
		fileItem.setSize(file.length());
		return fileItem;
	}

	public String init() {
		WorkSQL workSQL = (WorkSQL) getSession().getAttribute("workSQL");
		if (workSQL == null) {
			return "util";
		}
		Set<String> drivers = new HashSet<String>();
		drivers.add("/home");
		getRequest().setAttribute("drivers", drivers);
		return "left";
	}

	public String load() throws IOException {
		WorkSQL workSQL = (WorkSQL) getSession().getAttribute("workSQL");
		if (workSQL == null) {
			return "util";
		}
		PrintWriter out = getResponse().getWriter();
		String path = getParameter("path");
		if (StringUtils.isBlank(path)) {
			out.println("加载目录出错");
			return null;
		}
		path = WorkHelpCommon.unescapeSecond(path);
		List<FileItem> fileLists = new ArrayList<FileItem>();
		File file = new File(path);
		if (!file.exists()) {
			out.println("加载目录出错");
			return null;
		}
		File[] files = null;
		files = file.listFiles();
		for (File f : files) {
			if (!f.isHidden()) {
				fileLists.add(this.getFileItem(f));
			}
		}
		getRequest().setAttribute("fileLists", fileLists);
		return "load";
	}

	public String show() {
		WorkSQL workSQL = (WorkSQL) getSession().getAttribute("workSQL");
		if (workSQL == null) {
			return "util";
		}
		String path = getParameter("path");
		if (StringUtils.isBlank(path)) {
			path = StringUtils.EMPTY;
		} else {
			path = WorkHelpCommon.unescapeSecond(path);
		}

		List<FileItem> fileLists = new ArrayList<FileItem>();
		File file = new File(path);
		if (file.exists()) {
			File[] files = null;
			files = file.listFiles();
			for (File f : files) {
				if (!f.isHidden()) {
					fileLists.add(this.getFileItem(f));
				}
			}
			getRequest().setAttribute("fullPath",
					WorkHelpCommon.changeSeparator(path));
			getRequest().setAttribute(
					"parent",
					StringUtils.isBlank(file.getParent()) ? StringUtils.EMPTY
							: WorkHelpCommon.escapeSecond(file.getParent()));
			getRequest().setAttribute("fileLists", fileLists);
		}
		return "right";
	}

	public String download() throws Exception {
		WorkSQL workSQL = (WorkSQL) getSession().getAttribute("workSQL");
		if (workSQL == null) {
			return "util";
		}
		String fileName = getParameter("path");
		fileName = WorkHelpCommon.unescapeSecond(fileName);
		if (StringUtils.isNotBlank(fileName)) {
			FileTool.download(getResponse(), new File(fileName));
		}
		return null;
	}

	public String upload() {

		String driver = getParameter("driver");
		String folder = getParameter("folder");
		log.info("driver:"+driver);
		log.info("folder:"+folder);
		String saveDir = WorkHelpCommon.unescapeSecond(driver
				+ folder.substring(2));
		String str =File.separator+saveDir+File.separator+uploadifyFileName;
		
		
		if (folder != null) {
			try {
				FileOutputStream fos = new FileOutputStream(File.separator+saveDir+File.separator+uploadifyFileName);
				FileInputStream fis = new FileInputStream(uploadify);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				if (fos != null)
					fos.close();
				if (fis != null)
					fis.close();
				Msg result = new Msg(true, "上传成功");
				System.out.println(result.isFlag());
				try {
					PrintTool.print(getResponse(),
							JSONObject.fromObject(result), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}
}

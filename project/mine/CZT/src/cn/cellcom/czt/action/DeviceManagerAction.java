package cn.cellcom.czt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.file.FileTool;
import cn.cellcom.common.file.excel.ExcelTemplate;
import cn.cellcom.common.file.excel.ExcelTool;

import cn.cellcom.czt.bus.DeviceBus;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TDevice;
import cn.cellcom.czt.po.TLinkman;
import cn.cellcom.czt.po.TManager;

import cn.cellcom.czt.po.UploadExcel;

import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class DeviceManagerAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UploadExcel uploadDevice = new UploadExcel();

	public UploadExcel getUploadDevice() {
		return uploadDevice;
	}

	public void setUploadDevice(UploadExcel uploadDevice) {
		this.uploadDevice = uploadDevice;
	}

	private static final Log log = LogFactory.getLog(DeviceManagerAction.class);

	public String showDevice() {
		String spcode = getParameter("spcodeQuery");
		String codePart = getParameter("codePartQuery");
		String state = getParameter("stateQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_device where account =  ?  ");
		TManager manager = (TManager) getSession().getAttribute("login");
		if (manager.getRoleid() != 0) {
			getRequest().setAttribute("result", "非正常访问");
			return "error";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(manager.getAccount());
		if (StringUtils.isNotBlank(spcode)) {
			sql.append(" and spcode =  ? ");
			params.add(spcode);
			getRequest().setAttribute("spcode", spcode);
		}
		if (StringUtils.isNotBlank(codePart)) {
			if (codePart.length() < 8) {
				getRequest().setAttribute("result", "SN至少输入8位");
				return "error";
			} else if(codePart.length()==20) {
				sql.append(" and sn = ?   ");
				params.add(codePart);
				getRequest().setAttribute("codePart", codePart);
			}else if(codePart.length()>=8&&codePart.length()<20){
				sql.append(" and sn like ?  ");
				params.add("%" + codePart);
				getRequest().setAttribute("codePart", codePart);
			}else{
				getRequest().setAttribute("result", "SN输入错误");
				return "error";
			}
		}
		if (StringUtils.isNotBlank(state)) {
			sql.append(" and state =  ? ");
			params.add(state);
			getRequest().setAttribute("state", state);
		}
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and submit_time>? and submit_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by submit_time2 desc ");
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			if ("true".equals(getParameter("exportFlag"))) {
				List<TDevice> list = jdbc.query(
						ApplicationContextTool.getDataSource(), sql.toString(),
						TDevice.class, params.toArray(), 0, 0);
				getResponse().setContentType(
						"application/vnd.ms-excel;charset=gb2312");
				getResponse().setHeader(
						"Content-Disposition",
						"attachment;"
								+ " filename="
								+ new String("设备列表.xlsx".getBytes(),
										"ISO-8859-1"));
				ExcelTool.writeExcelByList("设备列表", list, new String[] { "sn",
						"spcodeStr", "configureStr", "stateStr", "submitTime" },
						new String[] { "SN", "厂家", "配置", "状态", "操作时间" },
						getResponse().getOutputStream(),
						ExcelTemplate.ExcelVersion.XLSX);
				return null;
			}
			PageResult<TDevice> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TDevice.class);
			if (pageResult != null) {
				List<TDevice> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					TDevice po = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						po = list.get(i);
						if (StringUtils.isNotBlank(po.getSpcode())) {
							if (Env.SPCODE.containsKey(po.getSpcode()
									.toUpperCase()))
								po.setSpcode(Env.SPCODE.get(po.getSpcode()
										.toUpperCase()));
						}
						if (StringUtils.isNotBlank(po.getConfigure())) {
							if (Env.ORDER_CONFIGURE.containsKey(po
									.getConfigure().toUpperCase())) {
								po.setConfigure(Env.ORDER_CONFIGURE.get(po
										.getConfigure().toUpperCase()));
							}
						}
						// po.setState(Env.DEVICE_STATE.get(po.getState()));
					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 来自设备流量捆绑的请求
		String fromPart = getParameter("fromPart");
		if ("bindDeviceFluxCard".equals(fromPart)) {
			return "checkDevice";
		}

		return "showDevice";

	}

	public String addBatchDevice() {
		TManager manager = (TManager) getSession().getAttribute("login");
		StringBuffer filename = new StringBuffer();
		if (uploadDevice.getFile() != null
				&& uploadDevice.getFile().length() > 0) {
			try {
				filename.append(ConfLoad.getProperty("ORDER_UPLOAD"))
						.append("device_upload")
						.append(File.separator)
						.append(DateTool.formateTime2String(new Date(),
								"yyyyMMdd")).append(File.separator);
				FileTool.isExistDir(new File(filename.toString()), true);
				filename.append(UUID.randomUUID())
						.append(".")
						.append(FileTool.getExtName(uploadDevice
								.getFileFileName()));
				if (filename.toString().endsWith(".xls")
						|| filename.toString().endsWith(".xlsx")) {
					FileOutputStream fos = new FileOutputStream(
							filename.toString());
					FileInputStream fis = new FileInputStream(
							uploadDevice.getFile());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					if (fos != null)
						fos.close();
					if (fis != null)
						fis.close();
				} else {
					getRequest().setAttribute("result", "上传文件非Excel格式");
					return "error";
				}

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			getRequest().setAttribute("result", "上传文件为空");
			return "error";
		}
		File file = new File(filename.toString());
		DeviceBus bus = new DeviceBus();
		try {
			Data msg = bus.addBatchDevice(manager, file);
			if (msg.isState()) {
				getRequest().setAttribute("result", msg.getMsg());
				return "success";
			} else {
				getRequest().setAttribute("result", msg.getMsg());
				return "error";

			}
		} catch (Exception e) {
			log.error("设备批量上传异常", e);
			getRequest().setAttribute("result", "设备批量上传异常");
			return "error";
		}

	}

	public String deleteDevice() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from t_device where account = ? and sn = ?  ");

			TManager manager = (TManager) getSession().getAttribute("login");
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				int count = jdbc.update(ApplicationContextTool.getDataSource(),
						sql.toString(),
						new Object[] { manager.getAccount(), id });
				if (count > 0) {
					PrintTool.print(getResponse(), "true|删除成功", null);
				} else {
					PrintTool.print(getResponse(), "false|删除失败", null);
				}
				PrintTool.print(getResponse(), "true|删除成功", null);
			} catch (Exception e) {
				log.error("删除流量卡失败", e);
				PrintTool.print(getResponse(), "false|删除失败", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择设备", null);
		}
		return null;
	}
}

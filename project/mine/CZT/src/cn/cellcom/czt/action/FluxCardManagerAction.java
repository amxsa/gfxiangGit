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

import cn.cellcom.czt.bus.FluxCardBus;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TFluxCard;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.UploadExcel;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class FluxCardManagerAction extends Struts2Base {
	private static final long serialVersionUID = 1L;
	private UploadExcel uploadFluxCard = new UploadExcel();

	public UploadExcel getUploadFluxCard() {
		return uploadFluxCard;
	}

	public void setUploadFluxCard(UploadExcel uploadFluxCard) {
		this.uploadFluxCard = uploadFluxCard;
	}

	private static final Log log = LogFactory
			.getLog(FluxCardManagerAction.class);

	public String showFluxCard() {
		String mobile = getParameter("mobileQuery");
		String areacode = getParameter("areacodeQuery");
		String state = getParameter("stateQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_flux_card where account =  ?  ");
		TManager manager = (TManager) getSession().getAttribute("login");
		if (manager.getRoleid() != 0) {
			getRequest().setAttribute("result", "非正常访问");
			return "error";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(manager.getAccount());
		if (StringUtils.isNotBlank(mobile)) {
			sql.append(" and mobile =  ? ");
			params.add(mobile);
			getRequest().setAttribute("mobile", mobile);
		}
		if (StringUtils.isNotBlank(areacode)) {
			sql.append(" and areacode =  ? ");
			params.add(areacode);
			getRequest().setAttribute("areacode", areacode);
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
				List<TFluxCard> list = jdbc.query(ApplicationContextTool.getDataSource(), sql.toString(), TFluxCard.class, params.toArray(), 0, 0);
				getResponse().setContentType(
						"application/vnd.ms-excel;charset=gb2312");
				getResponse().setHeader(
						"Content-Disposition",
						"attachment;"
								+ " filename="
								+ new String("流量卡列表.xlsx".getBytes(),
										"ISO-8859-1"));
				ExcelTool.writeExcelByList("流量卡列表", list, new String[] { "mobile",
						"areacodeStr", "stateStr", "submitTime" },
						new String[] { "手机号", "地市", "状态", "操作时间" },
						getResponse().getOutputStream(),
						ExcelTemplate.ExcelVersion.XLSX);
				return null;
			}
			PageResult<TFluxCard> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TFluxCard.class);
			if (pageResult != null) {
				List<TFluxCard> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					TFluxCard po = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						po = list.get(i);
						po.setAreacode(Env.AREACODE.get(po.getAreacode())[0]);
						
					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//来自设备流量捆绑的请求
		String fromPart = getParameter("fromPart");
		if("bindDeviceFluxCard".equals(fromPart)){
			return "checkFluxCard";
		}
		return "showFluxCard";

	}

	public String addBatchFluxCard() {
		TManager manager = (TManager) getSession().getAttribute("login");
		if (manager.getRoleid() != 0) {
			getRequest().setAttribute("result", "非正常访问");
			return "error";
		}

		StringBuffer filename = new StringBuffer();
		if (uploadFluxCard.getFile() != null
				&& uploadFluxCard.getFile().length() > 0) {
			try {
				filename.append(ConfLoad.getProperty("ORDER_UPLOAD"))
						.append("fluxcard_upload")
						.append(File.separator)
						.append(DateTool.formateTime2String(new Date(),
								"yyyyMMdd")).append(File.separator);
				FileTool.isExistDir(new File(filename.toString()), true);
				filename.append(UUID.randomUUID())
						.append(".")
						.append(FileTool.getExtName(uploadFluxCard
								.getFileFileName()));
				if (filename.toString().endsWith(".xls")
						|| filename.toString().endsWith(".xlsx")) {
					FileOutputStream fos = new FileOutputStream(
							filename.toString());
					FileInputStream fis = new FileInputStream(
							uploadFluxCard.getFile());
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
		}
		File file = new File(filename.toString());
		FluxCardBus bus = new FluxCardBus();
		try {
			Data msg = bus.addBatchFluxCard(manager, file);
			if (msg.isState()) {
				getRequest().setAttribute("result", msg.getMsg());
				return "success";
			} else {
				getRequest().setAttribute("result", msg.getMsg());
				return "error";

			}
		} catch (Exception e) {
			log.error("流量卡批量上传异常", e);
			getRequest().setAttribute("result", "流量卡批量上传异常");
			return "error";
		}

	}

	public String deleteFluxCard() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from t_flux_card  where account = ? and mobile = ?  ");

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
			PrintTool.print(getResponse(), "false|请选择流量卡", null);
		}
		return null;
	}
}

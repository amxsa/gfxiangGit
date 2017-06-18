package hcho.nsfw.complain.action;

import hcho.core.action.BaseAction;
import hcho.core.util.QueryHelper;
import hcho.nsfw.complain.entity.CompReply;
import hcho.nsfw.complain.entity.Complain;
import hcho.nsfw.complain.service.ComplainService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

public class ComplainAction extends BaseAction {

	@Resource
	private ComplainService complainService;

	private Complain complain;
	private CompReply reply;
	private String startTime;
	private String endTime;
	private String strState;
	private String strTitle;
	
	private Map<String,Object> statisticDataMap;
	
	/**
	 * 实现根据标题  时间段  状态 联合搜索列表
	 * 顺序  时间段-->状态-->标题   （提高效率）
	 * 
	 * 搜索的结果须排序  根据受理状态 和 创建时间
	 * @return
	 */
	public String listUI() {
		
		ServletActionContext.getContext().getContextMap().put("complainStateMap",Complain.COMPLAIN_STATE_MAP);
		
		
		try {
			QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
			if (StringUtils.isNotBlank(startTime)) {
				startTime = decode(startTime);//解码
				queryHelper.addCondition("c.compTime >= ?", DateUtils.parseDate(startTime+":00", "yyyy-MM-dd HH:mm:ss"));
			}
			if (StringUtils.isNotBlank(endTime)) {
				endTime=decode(endTime);
				queryHelper.addCondition("c.compTime <= ?", DateUtils.parseDate(endTime+":59", "yyyy-MM-dd HH:mm:ss"));
			}
			if (complain!=null) {
				if (StringUtils.isNotBlank(complain.getState())) {
					queryHelper.addCondition("c.state=?", complain.getState());
				}
				
				if (StringUtils.isNotBlank(complain.getCompTitle())) {
					queryHelper.addCondition("c.compTitle like ?", "%"+complain.getCompTitle()+"%");
				}
			}
			
			 queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BY_ASC); //升序 0 1 2 
			 
			 queryHelper.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_DESC);//升序排列  先查看先投诉的
			
			
			pageResult = complainService.findObjects(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listUI";
	}
	
	public String dealUI(){
		ServletActionContext.getContext().getContextMap().put("complainStateMap",Complain.COMPLAIN_STATE_MAP);
		strState=complain.getState();
		strTitle=complain.getCompTitle();
		complain = complainService.findById(complain.getCompId());
		return "dealUI";
	}
	
	//保存受理结果  同时保存投诉信息受理状态
	public String deal(){
		try {
			if (complain!=null) {
				Complain temp = complainService.findById(complain.getCompId());
				//保存受理状态
				if (Complain.COMPLAIN_STATE_UNDONE.equals(complain.getState())) {
					temp.setState(Complain.COMPLAIN_STATE_DONE);
				}
				reply.setComplain(temp);
				//设置当前时间
				reply.setReplyTime(new Timestamp(new Date().getTime()));
				temp.getCompReplies().add(reply);
				
				complainService.update(temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "list";
	}
	
	public String annualStatisticChartUI(){
		
		return "annualStatisticChartUI";
	}
	
	//获取年度投诉统计数信息
	public String getAnnualStatisticChartData(){
		//获取 当前年份  默认是当前年份
		
		int year = Calendar.getInstance().get(Calendar.YEAR);//默认当前年份
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getParameter("year") != null) {
			year = Integer.parseInt(request.getParameter("year"));
		}
		List<Map<String,Object>> dataList=complainService.getStatisticDataByYear(year);
		statisticDataMap=new HashMap<String, Object>();
		statisticDataMap.put("msg", "success");
		//转换为json格式传过去
		statisticDataMap.put("chartData",dataList);
		
		return "statisticData";
	}
	

	public ComplainService getComplainService() {
		return complainService;
	}

	public void setComplainService(ComplainService complainService) {
		this.complainService = complainService;
	}

	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public CompReply getReply() {
		return reply;
	}
	public void setReply(CompReply reply) {
		this.reply = reply;
	}
	public String getStrState() {
		return strState;
	}
	public void setStrState(String strState) {
		this.strState = strState;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Map<String, Object> getStatisticDataMap() {
		return statisticDataMap;
	}

	public void setStatisticDataMap(Map<String, Object> statisticDataMap) {
		this.statisticDataMap = statisticDataMap;
	}
	
}

package hcho.home.action;

import hcho.core.constant.Constant;
import hcho.core.util.PageResult;
import hcho.core.util.QueryHelper;
import hcho.nsfw.complain.entity.CompReply;
import hcho.nsfw.complain.entity.Complain;
import hcho.nsfw.complain.service.ComplainService;
import hcho.nsfw.info.entity.Info;
import hcho.nsfw.info.service.InfoService;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.service.UserService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
	
	@Resource
	private UserService userService;
	
	private Map<String, Object> return_map;
	
	@Resource
	private ComplainService complainService;
	private Complain comp;
	@Resource
	private InfoService infoService;
	private Info info;
	
	private CompReply compReply;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ServletActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		QueryHelper queryHelper1 = new QueryHelper(Info.class, "i");
		queryHelper1.addOrderByProperty("i.createTime","");
		List infoList = infoService.findObjects(queryHelper1, 1, 5).getItems();
		ServletActionContext.getContext().getContextMap().put("infoList", infoList);
		
		//获取当前登录的用户
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(Constant.USER);
		ServletActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		QueryHelper queryHelper2 = new QueryHelper(Complain.class, "c");
		queryHelper2.addCondition("c.compName = ?", user.getName());
		queryHelper2.addOrderByProperty("c.state","");
		queryHelper2.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_DESC);
		List compList = complainService.findObjects(queryHelper2, 1,7).getItems();
		ServletActionContext.getContext().getContextMap().put("compList", compList);
		
		return "home";
	}
	
	public String complainAddUI(){
		
		return "complainAddUI";
	}
	
	public String getUserJson(){
		
		String dept = ServletActionContext.getRequest().getParameter("dept");
		try {
			if (dept!=null) {
				QueryHelper queryHelper = new QueryHelper(User.class, "u");
				queryHelper.addCondition("u.dept like ?", "%"+dept+"%");
				List<User> userList = userService.findObjects(queryHelper);
				
				return_map = new HashMap<String, Object>();
				return_map.put("msg", "success");
				return_map.put("userList", userList);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "success";
	}
	
	public void getUserJson1(){
		String dept = ServletActionContext.getRequest().getParameter("dept");
		try {
			if (dept!=null) {
				QueryHelper queryHelper = new QueryHelper(User.class, "u");
				queryHelper.addCondition("u.dept like ?", "%"+dept+"%");
				List<User> userList = userService.findObjects(queryHelper);
				
				JSONObject job = new JSONObject();
				job.accumulate("userList", userList);
				job.put("msg", "success");
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(job.toString().getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addComplain(){
		
		try {
			if (comp!=null) {
				comp.setCompTime(new Timestamp(new Date().getTime()));
				complainService.save(comp);
			
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("success".getBytes());
				outputStream.close();
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String complainViewUI(){
		
		ServletActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if (comp!=null) {
			comp = complainService.findById(comp.getCompId());
		}
		return "complainViewUI";
	}
	
	public String infoViewUI(){
		ServletActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if (info!=null) {
			info = infoService.findById(info.getInfoId());
		}
		return "infoViewUI";
	}
	
	
	public void findCompReply(){
		String compId = ServletActionContext.getRequest().getParameter("compId");
		try {
			if (compId!=null) {
				Complain complain = complainService.findById(compId);
				Set replies = complain.getCompReplies();
				JSONObject jso=new JSONObject();
				jso.accumulate("replies", replies);
				jso.put("msg","success");
				
				HttpServletResponse response = ServletActionContext.getResponse();
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(jso.toString().getBytes("utf-8"));
				outputStream.close();
			}
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Map<String, Object> getReturn_map() {
		return return_map;
	}

	public void setReturn_map(Map<String, Object> return_map) {
		this.return_map = return_map;
	}

	public Complain getComp() {
		return comp;
	}

	public void setComp(Complain comp) {
		this.comp = comp;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public CompReply getCompReply() {
		return compReply;
	}

	public void setCompReply(CompReply compReply) {
		this.compReply = compReply;
	}
	
	
	
}

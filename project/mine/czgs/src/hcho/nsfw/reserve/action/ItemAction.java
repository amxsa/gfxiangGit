package hcho.nsfw.reserve.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import hcho.core.action.BaseAction;
import hcho.core.util.PageResult;
import hcho.core.util.QueryHelper;
import hcho.nsfw.reserve.entity.Item;
import hcho.nsfw.reserve.service.ItemService;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionSupport;


public class ItemAction extends BaseAction{

	private Item item;
	
	@Resource
	private ItemService itemService;
	private Map<String, Object> return_map;
	
	@Resource
	private UserService userService;
	
	public String listUI(){
		QueryHelper queryHelper = new QueryHelper(Item.class, "i");
		pageResult = itemService.findObjects(queryHelper, getPageNo(), getPageSize());
		return "listUI";
	}
	
	public String addUI(){
		
		ServletActionContext.getContext().getContextMap().put("itemIdList", Item.itemIdList);
		return "addUI";
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
		
		return "deptUsers";
	}
	
	public String add(){
		if (item!=null) {
			
			itemService.save(item);
		}
		
		return "success";
	}

	public String delete(){
		
		if (item!=null) {
			itemService.delete(item.getItemId());
		}
		
		return "success";
	}
	
	public String editUI(){
		ServletActionContext.getContext().getContextMap().put("itemIdList", Item.itemIdList);
		if (item!=null) {
			item=itemService.findById(item.getItemId());
		}
		
		return "editUI";
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Map<String, Object> getReturn_map() {
		return return_map;
	}

	public void setReturn_map(Map<String, Object> return_map) {
		this.return_map = return_map;
	}
}

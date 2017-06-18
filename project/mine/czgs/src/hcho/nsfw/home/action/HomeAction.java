package hcho.nsfw.home.action;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
	
	public String frame(){
		
		return "frame";
	}
	
	public String left(){
		return "left";
	}
	
	public String top(){
		return "top";
	}
}


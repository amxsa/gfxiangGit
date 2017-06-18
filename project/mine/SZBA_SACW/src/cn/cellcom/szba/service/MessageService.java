package cn.cellcom.szba.service;

public interface MessageService {

	public boolean SendMessage(String title,String content,String type,String account,String isRelative);
	
	public boolean SendMessageToMore(String title,String content,String type,String handlers,String isRelative);
	
	public Boolean publicMessage(String type,String dayCounts);
}

package cn.cellcom.szba.vo;

import java.util.Date;

/**
 * 流程跟踪
 * @author hzj
 *
 */
public class ProcessTrack {
	//提交时间
	private Date startTime;
	//完成时间
	private Date finishTime;
	//环节名称
	private String userTaskName;
	//处理人
	private String handlers;
	//审批意见
	private String comment;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getHandlers() {
		return handlers;
	}
	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}
	public String getUserTaskName() {
		return userTaskName;
	}
	public void setUserTaskName(String userTaskName) {
		this.userTaskName = userTaskName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}

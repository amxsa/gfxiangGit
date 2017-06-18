package hcho.core.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;

public class SysResultAction extends StrutsResultSupport {

	/**
	 * 在有特殊情况时；如果没有异常信息，但是有错误并且有错误信息等内容；此时也需要进行友好的错误处理的话，
	 * 那么可以借助StrutsResultSupport 返回结果类型来实现特定处理。此种方式先需要继承StrutsResultSupport
	 * ，然后可以在子类中获取本次请求的相关信息，再根据相关信息进行结果处理：
	 */
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		BaseAction baseAction = (BaseAction) invocation.getAction();
		//此时可以做任何操作。。。
		//转发或重定向。。
	}

}

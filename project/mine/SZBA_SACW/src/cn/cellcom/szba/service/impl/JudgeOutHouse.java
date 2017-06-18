package cn.cellcom.szba.service.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.biz.TApplicationBiz;
import cn.cellcom.szba.biz.TPropertyBiz;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TAppProTransLog;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.enums.ActualPosition;
import cn.cellcom.szba.enums.DisposalKey;
import cn.cellcom.szba.enums.PropertyStatusKey;
import cn.cellcom.szba.vo.ApplicationVO;
import cn.open.db.JDBC;
import cn.open.util.CommonUtil;

/**
 * activiti的服务任务处理类
 * @author Administrator
 *
 */
public class JudgeOutHouse implements JavaDelegate, Serializable {
	
	private Log log = LogFactory.getLog(JudgeOutHouse.class);
	
	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		String disposalKey = (String) arg0.getVariable("disposalKey");
		String oriActualPosition = (String) arg0.getVariable("oriActualPosition");
		
		log.info("disposalKey = "+disposalKey + "##########oriActualPosition = " + oriActualPosition);
		
		HashMap<String, Object> variables=new HashMap<String, Object>();
		if( (DisposalKey.TH.toString().equals(disposalKey) || DisposalKey.FH.toString().equals(disposalKey) || DisposalKey.BM.toString().equals(disposalKey)) 
		&& (ActualPosition.ZCK.toString().equals(oriActualPosition) || ActualPosition.ZXK.toString().equals(oriActualPosition))){
			variables.put("status", "Y");
		}else{
			variables.put("status", "N");
		}
		arg0.setVariables(variables);
		
		log.info("status = " + variables.get("status"));
	}
	
}

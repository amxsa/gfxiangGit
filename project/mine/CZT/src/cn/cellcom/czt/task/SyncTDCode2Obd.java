package cn.cellcom.czt.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.czt.bus.TDCodeBus;
import cn.cellcom.czt.common.ConfLoad;

public class SyncTDCode2Obd {
	private static final Log log = LogFactory.getLog(SyncTDCode2Obd.class);
	public void run(){
		if("true".equals(ConfLoad.getProperty("SYNC_TDCODE_FALG"))){
			TDCodeBus bus = new TDCodeBus();
			bus.syncTDCodeTask();
		}
	}
	
}

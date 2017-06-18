package cn.cellcom.czt.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.czt.bus.TDCodeBus;
import cn.cellcom.czt.common.ConfLoad;

public class SyncTDCode2HGObd {
	private static final Log log = LogFactory.getLog(SyncTDCode2HGObd.class);
	public void run(){
		if("true".equals(ConfLoad.getProperty("SYNC_TDCODE_OBD_FLAG"))){
			TDCodeBus bus = new TDCodeBus();
			bus.syncTDCodeTaskHGObd();
		}
	}
	
}

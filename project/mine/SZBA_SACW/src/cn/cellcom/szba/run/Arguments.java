package cn.cellcom.szba.run;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.biz.GlobalListener;
import cn.cellcom.szba.biz.TAccountBiz;
import cn.cellcom.szba.biz.TArgumentsBiz;
import cn.cellcom.szba.domain.TArguments;

public class Arguments implements Runnable {
	private static Log log = LogFactory.getLog(GlobalListener.class);

	@Override
	public void run() {
		while (true) {
			try {
				List<TArguments> argumentsList = TArgumentsBiz.checkModify();
				if (!argumentsList.isEmpty()) {
					for (TArguments arg : argumentsList) {
						if ("account".equals(arg.getService())) {
							TAccountBiz.initAccounts();
							TArgumentsBiz.updateArguments(arg.getService());
						}
						if ("department".equals(arg.getService())) {
							TAccountBiz.initDepartments();
							TArgumentsBiz.updateArguments(arg.getService());
						}
					}
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				log.error("", e);
			}
		}
	}

}

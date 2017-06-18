package cn.cellcom.szba.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cellcom.szba.databean.Flownode;
import cn.cellcom.szba.service.impl.FlownodeEngine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring.xml",
		"classpath:spring-flow.xml"
	})
public class FlownodeEngineTest {
	private Log log = LogFactory.getLog(FlownodeEngineTest.class);

	@Test
	public void getFlownodeTest(){
		FlownodeEngine.setRealPath("G:\\MyEclipse10\\SZBA_SACW\\WebRoot\\flownode\\temp");
		FlownodeEngine.init();
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("outcome", new String[] { "N" });
		map.put("status", new String[] { "ZXK" });
		Flownode flownode = FlownodeEngine.getFlownode("temp.xml", map);
		log.info(flownode);
	}
}

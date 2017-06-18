package hcho.nsfw.complain.service;

import java.util.List;
import java.util.Map;

import hcho.core.service.BaseService;
import hcho.nsfw.complain.entity.Complain;

public interface ComplainService extends BaseService<Complain> {

	
	public void autoDeal();

	public List<Map<String, Object>> getStatisticDataByYear(int year);
}

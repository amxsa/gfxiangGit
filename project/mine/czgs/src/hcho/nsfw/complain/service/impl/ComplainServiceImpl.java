package hcho.nsfw.complain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hcho.core.service.impl.BaseServiceImpl;
import hcho.nsfw.complain.dao.ComplainDao;
import hcho.nsfw.complain.entity.Complain;
import hcho.nsfw.complain.service.ComplainService;

@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements
		ComplainService {

	private ComplainDao complainDao;
	@Resource
	public void setComplainDao(ComplainDao complainDao){
		super.setBaseDao(complainDao);
		this.complainDao=complainDao;
	}
	@Override
	public void autoDeal() {
		// TODO Auto-generated method stub
		//获取本月第一天00:00:00
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		complainDao.updateComplainStateInLastDay(Complain.COMPLAIN_STATE_UNDONE,Complain.COMPLAIN_STATE_INVALID,cal.getTime());
	}
	@Override
	public List<Map<String, Object>> getStatisticDataByYear(int year) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		//获取当前月份
		int curMonth=cal.get(Calendar.MONTH)+1;
		//判断是否为当前年份
		boolean isCurYear=(year==cal.get(Calendar.YEAR));
		
		//dao从数据库查询到的每条数据都是对象数组 
		List<Object[]> dataList=complainDao.getStatisticDataByYear(year);
		
		List<Map<String, Object>> resList=new ArrayList<Map<String,Object>>();
		
		//转换为fusionChart需要的格式
		/*
		 * {
         "label": "Jan",
         "value": "420000"
      		},
		 */
		if (dataList!=null) {
			Map<String, Object> map=null;
			for (Object[] obj : dataList) {
				int month = Integer.parseInt(obj[0]+"");
				map=new HashMap<String, Object>();
				map.put("label", month+"月");
				if (isCurYear) {//本年
					if (month<=curMonth) {//已经过了的月份（包括本月）
						map.put("value",obj[1]==null?"0":obj[1]);
					}else{
						map.put("value","");//没到的月份 将投诉数置空
					}
				}else{
					map.put("value",obj[1]==null?"0":obj[1] );
				}
				resList.add(map);
			}
		}
		
		return resList;
	}
	
}

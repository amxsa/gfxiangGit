package cn.cellcom.szba.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.enums.ActualPosition;
import cn.cellcom.szba.enums.CategoryAttributeKey;
import cn.cellcom.szba.enums.PropertyStatusKey;
import cn.cellcom.szba.service.JudgeProcessService;

public class JudgeProcessServiceImpl implements JudgeProcessService {

	private static Map<String, String> commonProInTemporaryMap;
	private static Map<String, String> commonProInCenterMap;
	private static Map<String, String> specialProInCenterMap;
	private static Map<String, String> proInvokeOutTemporaryMap;
	private static Map<String, String> proInvokeOutCenterMap;
	private static Map<String, String> proInvokeIntoTemporaryMap;
	private static Map<String, String> proInvokeIntoCenterMap;
	
	static{
		
		commonProInTemporaryMap = new HashMap<String, String>();
		commonProInTemporaryMap.put(CategoryAttributeKey.YBCW.toString(), CategoryAttributeKey.YBCW.getCnKey());
		commonProInTemporaryMap.put(CategoryAttributeKey.GZDJLA.toString(), CategoryAttributeKey.GZDJLA.getCnKey());
		commonProInTemporaryMap.put(CategoryAttributeKey.JHQZ.toString(), CategoryAttributeKey.JHQZ.getCnKey());
		commonProInTemporaryMap.put(CategoryAttributeKey.YHWPTS.toString(), CategoryAttributeKey.YHWPTS.getCnKey());
		commonProInTemporaryMap.put(CategoryAttributeKey.DBGJ.toString(), CategoryAttributeKey.DBGJ.getCnKey());
		
		commonProInCenterMap = commonProInTemporaryMap;
		
		specialProInCenterMap = new HashMap<String, String>();
		specialProInCenterMap.put(CategoryAttributeKey.GZDJBLA.toString(), CategoryAttributeKey.GZDJBLA.getCnKey());
		specialProInCenterMap.put(CategoryAttributeKey.YHWPGP.toString(), CategoryAttributeKey.YHWPGP.getCnKey());
		specialProInCenterMap.put(CategoryAttributeKey.YHBZ.toString(), CategoryAttributeKey.YHBZ.getCnKey());
		
	}
	
	@Override
	public boolean judgeCommonPropertyInTemporary(List<String> types, List<String> actualPositions) {
		
		return judgePosition(actualPositions, ActualPosition.BAMJ.toString()) && commonProInTemporaryMap.keySet().containsAll(types);
	}
	
	@Override
	public boolean judgeCommonPropertyInCenter(List<String> types, List<String> actualPositions) {
		
		return judgePosition(actualPositions, ActualPosition.ZCK.toString()) && commonProInCenterMap.keySet().containsAll(types);
	}
	
	@Override
	public boolean judgeSpecailPropertyInCenter(List<String> types, List<String> actualPositions) {
		
		return judgePosition(actualPositions, ActualPosition.BAMJ.toString()) && specialProInCenterMap.keySet().containsAll(types);
	}
	
	@Override
	public boolean judgeProInvokeOutTemporary(List<String> types,
			List<String> actualPositions) {
		return judgePosition(actualPositions, ActualPosition.ZCK.toString());
	}

	@Override
	public boolean judgeProInvokeOutCenter(List<String> types,
			List<String> actualPositions) {
		return judgePosition(actualPositions, ActualPosition.ZXK.toString());
	}

	@Override
	public boolean judgeProInvokeIntoTemporary(List<String> proStatus) {
		return judgePosition(proStatus, PropertyStatusKey.DYCZCK.toString());
	}

	@Override
	public boolean judgeProInvokeIntoCenter(List<String> proStatus){
		return judgePosition(proStatus, PropertyStatusKey.DYCZXK.toString());
	}
	
	
	@Override
	public boolean judgeProDoDestroy(List<String> proStatus) {
		return judgePosition(proStatus, PropertyStatusKey.DXH.toString());
	}

	@Override
	public boolean judgeProHandle(List<String> proStatus) {
		return judgePosition(proStatus, PropertyStatusKey.YDJ.toString());
	}

	public boolean judgePosition(List<String> collection, String currValue){
		boolean flag = true;
		for(String position:collection){
			if(!currValue.equals(position)){
				flag = false;
				break;
			}
		}
		
		return flag;
	}

	@Override
	public boolean judgeCommonPropertyInTemporary(TProperty property) {
		return ActualPosition.BAMJ.toString().equals(property.getActualPosition()) && commonProInTemporaryMap.containsKey(property.getProType());
	}

	@Override
	public boolean judgeCommonPropertyInCenter(TProperty property) {
		return  ActualPosition.ZCK.toString().equals(property.getActualPosition()) && commonProInCenterMap.containsKey(property.getProType());
	}

	@Override
	public boolean judgeSpecailPropertyInCenter(TProperty property) {
		return ActualPosition.BAMJ.toString().equals(property.getActualPosition()) && specialProInCenterMap.containsKey(property.getProType());
	}

	@Override
	public boolean judgeProInvokeOutTemporary(TProperty property) {
		return ActualPosition.ZCK.toString().equals(property.getActualPosition());
	}

	@Override
	public boolean judgeProInvokeOutCenter(TProperty property) {
		return ActualPosition.ZXK.toString().equals(property.getActualPosition());
	}

	@Override
	public boolean judgeProInvokeIntoTemporary(TProperty property) {
		return PropertyStatusKey.DYCZCK.toString().equals(property.getProStatus());
	}

	@Override
	public boolean judgeProInvokeIntoCenter(TProperty property) {
		return PropertyStatusKey.DYCZXK.toString().equals(property.getProStatus());
	}

	@Override
	public boolean judgeProDoDestroy(TProperty property) {
		return PropertyStatusKey.DXH.toString().equals(property.getProStatus());
	}

	@Override
	public boolean judgeProHandle(TProperty property) {
		return PropertyStatusKey.YDJ.toString().equals(property.getProStatus());
	}

	
}

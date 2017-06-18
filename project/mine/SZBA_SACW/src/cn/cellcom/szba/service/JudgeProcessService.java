package cn.cellcom.szba.service;

import java.util.List;

import cn.cellcom.szba.domain.TProperty;

public interface JudgeProcessService {

	/**
	 * 判断财物是否符合入一般财物入暂存库的规则
	 */
	public boolean judgeCommonPropertyInTemporary(List<String> types, List<String> actualPositions);
	public boolean judgeCommonPropertyInTemporary(TProperty property);
	
	/**
	 * 判断财物是否符合入一般财物入中心库的规则
	 */
	public boolean judgeCommonPropertyInCenter(List<String> types, List<String> actualPositions);
	public boolean judgeCommonPropertyInCenter(TProperty property);
	/**
	 * 判断财物是否符合入特殊财物入中心库的规则
	 */
	public boolean judgeSpecailPropertyInCenter(List<String> types, List<String> actualPositions);
	public boolean judgeSpecailPropertyInCenter(TProperty property);
	
	/**
	 * 判断财物是否符合财物调用出暂存库的规则
	 */
	public boolean judgeProInvokeOutTemporary(List<String> types, List<String> actualPositions);
	public boolean judgeProInvokeOutTemporary(TProperty property);
	
	/**
	 * 判断财物是否符合财物调用出中心库的规则
	 */
	public boolean judgeProInvokeOutCenter(List<String> types, List<String> actualPositions);
	public boolean judgeProInvokeOutCenter(TProperty property);
	/**
	 * 判断财物是否符合财物调用归还暂存库的规则
	 */
	public boolean judgeProInvokeIntoTemporary(List<String> proStatus); 
	public boolean judgeProInvokeIntoTemporary(TProperty property); 
	
	/**
	 * 判断财物是否符合财物调用归还中心库的规则
	 */
	public boolean judgeProInvokeIntoCenter(List<String> proStatus);
	public boolean judgeProInvokeIntoCenter(TProperty property);
	/**
	 * 判断财物是否符合财物集中销毁的规则
	 */
	public boolean judgeProDoDestroy(List<String> proStatus);
	public boolean judgeProDoDestroy(TProperty property);
	/**
	 * 判断财物是否符合财物处置的规则
	 */
	public boolean judgeProHandle(List<String> proStatus);
	public boolean judgeProHandle(TProperty property);
}

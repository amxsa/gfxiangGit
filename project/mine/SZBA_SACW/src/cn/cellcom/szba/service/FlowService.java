package cn.cellcom.szba.service;

import java.util.List;

import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.domain.TRole;

/**
 * 流程操作类接口
 * @author 陈奕平
 *
 */
public interface FlowService {

	public List<DisposalAndProcedure> getDisposalAndProcedure(List<TRole> roles, String categoryId, String status); 
}

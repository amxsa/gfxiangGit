package cn.cellcom.szba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.utils.TRoleUtils;
import cn.cellcom.szba.databean.CategoryAttribute;
import cn.cellcom.szba.databean.Disposal;
import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.databean.Procedure;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.enums.CategoryAttributeKey;
import cn.cellcom.szba.enums.DisposalKey;
import cn.cellcom.szba.enums.ProcedureKey;
import cn.cellcom.szba.enums.PropertyStatusKeyOld;
import cn.cellcom.szba.enums.RoleKey;
import cn.cellcom.szba.service.FlowService;

/**
 * 流程操作实现类
 * 
 * @author 陈奕平
 *
 */
public class FlowServiceImpl implements FlowService {
	private Log log = LogFactory.getLog(FlowServiceImpl.class);

	private Map<String, Disposal> disposalMapper;
	private Map<String, Procedure> procedureMapper;
	private Map<String, CategoryAttribute> categoryAttributeMapper;

	@Override
	public List<DisposalAndProcedure> getDisposalAndProcedure(List<TRole> roles, String categoryId, String status) {
		List<DisposalAndProcedure> result = new ArrayList<DisposalAndProcedure>();
		// 入暂存库
		result.addAll(this.processRuZanCunKu(roles, categoryId, status));
		// 入中心库
		result.addAll(this.processRuZhongXinKu(roles, categoryId, status));
		// 出暂存库
		result.addAll(this.processChuZanCunKu(roles, categoryId, status));
		// 出中心库
		result.addAll(this.processChuZhongXinKu(roles, categoryId, status));
		// 退还
		result.addAll(this.processTuiHuan(roles, categoryId, status));
		// 销毁
		result.addAll(this.processXiaoHui(roles, categoryId, status));
		// 拍卖
		result.addAll(this.processPaiMai(roles, categoryId, status));
		// 调用
		result.addAll(this.processDiaoYong(roles, categoryId, status));
		log.info("Results=[" + PrintTool.printStrByArray(result.toArray()) + "]");
		return result;
	}
	
	
	/**
	 * 处理入暂存库方式
	 */
	private List<DisposalAndProcedure> processRuZanCunKu(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.YDJ.toString().equalsIgnoreCase(status)// 已登记
					|| PropertyStatusKeyOld.ZCK_DY.toString().equalsIgnoreCase(status) // 暂存库调用
					){ 
				if(!CategoryAttributeKey.YHBZ.toString().equalsIgnoreCase(categoryId) // 烟花爆竹
						&& !CategoryAttributeKey.GZDJBLA.toString().equalsIgnoreCase(categoryId) // 管制刀具（不立案）
						&& !CategoryAttributeKey.YHWPGP.toString().equalsIgnoreCase(categoryId) // 淫秽物品
						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
						){ // 除外的财物
					// 办案民警+一般财物+已登记/暂存库调用：入暂存库走流程1
					disposal = disposalMapper.get(DisposalKey.RZCK.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE001.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		return list;
	}
	
	/**
	 * 处理入中心库方式
	 */
	private List<DisposalAndProcedure> processRuZhongXinKu(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.ZCKGLY.toString())){ // 暂存库管理员
			if(PropertyStatusKeyOld.ZCK.toString().equalsIgnoreCase(status) // 暂存库
					|| PropertyStatusKeyOld.ZXK_DY.toString().equalsIgnoreCase(status) // 中心库调用 
					){ 
				if(CategoryAttributeKey.YBCW.toString().equalsIgnoreCase(categoryId) // 一般财物
						|| CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
						|| CategoryAttributeKey.GZDJLA.toString().equalsIgnoreCase(categoryId) // 管制刀具（立案）
						|| CategoryAttributeKey.JHQZ.toString().equalsIgnoreCase(categoryId) // 缴获枪支
						|| CategoryAttributeKey.YHWPTS.toString().equalsIgnoreCase(categoryId) // 淫秽物品（图书类）
						|| CategoryAttributeKey.DBGJ.toString().equalsIgnoreCase(categoryId) // 赌博工具
						){
					// 暂存库管理员+一般财物+暂存库/中心库调用：一般财物入中心库流程2
					disposal = disposalMapper.get(DisposalKey.RZXK.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE002.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.YDJ.toString().equalsIgnoreCase(status) // 已登记
					|| PropertyStatusKeyOld.ZXK_DY.toString().equalsIgnoreCase(status) // 中心库调用 
					){ 
				if(CategoryAttributeKey.YHBZ.toString().equalsIgnoreCase(categoryId) // 烟花爆竹
						|| CategoryAttributeKey.GZDJBLA.toString().equalsIgnoreCase(categoryId) // 管制刀具（不立案）
						|| CategoryAttributeKey.YHWPGP.toString().equalsIgnoreCase(categoryId) // 淫秽物品（光盘）
						){
					// 办案民警+特殊物品+已登记/中心库调用：特殊财物直接入中心库流程10
					disposal = disposalMapper.get(DisposalKey.RZXK.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE010.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				} else if(CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId)){ // 电子物证(无存储介质)
					// 办案民警+特殊物品+已登记：无存储介质电子物证储存流程9
					disposal = disposalMapper.get(DisposalKey.RZXK.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE009.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		return list;
	}

	/**
	 * 处理出暂存库
	 */
	private List<DisposalAndProcedure> processChuZanCunKu(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.ZCK.toString().equalsIgnoreCase(status)){ // 暂存库
				if(!CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
						){
					// 办案民警+一般物品+暂存库：一般财物出暂存库流程6
					disposal = disposalMapper.get(DisposalKey.CZCK.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE006.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		return list;
	}

	/**
	 * 处理出中心库
	 */
	private List<DisposalAndProcedure> processChuZhongXinKu(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.ZXK.toString().equalsIgnoreCase(status)){ // 中心库
				if(!CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
						){
					// 办案民警+一般物品+中心库：一般财物出中心库流程7
					disposal = disposalMapper.get(DisposalKey.CZXK.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE007.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		return list;
	}

	/**
	 * 处理退还
	 */
	private List<DisposalAndProcedure> processTuiHuan(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.ZCK.toString().equalsIgnoreCase(status) // 暂存库
				|| PropertyStatusKeyOld.ZXK.toString().equalsIgnoreCase(status) // 中心库
				|| PropertyStatusKeyOld.YDJ.toString().equalsIgnoreCase(status) // 已登记
				){
				if(!CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
						){
					// 办案民警+一般物品+暂存库/中心库：一般财物处置流程3
					disposal = disposalMapper.get(DisposalKey.TH.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE003.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		return list;
	}

	/**
	 * 处理销毁
	 */
	private List<DisposalAndProcedure> processXiaoHui(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.ZCK.toString().equalsIgnoreCase(status) // 暂存库
				|| PropertyStatusKeyOld.ZXK.toString().equalsIgnoreCase(status) // 中心库
				|| PropertyStatusKeyOld.YDJ.toString().equalsIgnoreCase(status) // 已登记
				){
				if(CategoryAttributeKey.YBCW.toString().equalsIgnoreCase(categoryId)){
					// 办案民警+一般物品+暂存库/中心库：一般财物处置流程3 (申请将财物标记为可销毁)
					disposal = disposalMapper.get(DisposalKey.XH.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE003.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				} 
//				else if(!CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
//						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
//						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
//						){
//					// 治安科民警+一般物品+暂存库/中心库：特殊财物销毁（移交）处置流程11
//					disposal = disposalMapper.get(DisposalKey.XH.toString());
//					procedure = procedureMapper.get(ProcedureKey.PROCEDURE011.toString());
//					list.add(new DisposalAndProcedure(disposal, procedure));
//				}
			}
		}
		return list;
	}

	/**
	 * 处理拍卖
	 */
	private List<DisposalAndProcedure> processPaiMai(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.ZCK.toString().equalsIgnoreCase(status) // 暂存库
				|| PropertyStatusKeyOld.ZXK.toString().equalsIgnoreCase(status) // 中心库
				|| PropertyStatusKeyOld.YDJ.toString().equalsIgnoreCase(status) // 已登记
				){
				if(!CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
						){
					// 办案民警+一般物品+暂存库/中心库：一般财物处置流程3
					disposal = disposalMapper.get(DisposalKey.PM.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE003.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		return list;
	}
	
	/**
	 * 处理调用
	 */
	private List<DisposalAndProcedure> processDiaoYong(List<TRole> roles, String categoryId, String status){
		List<DisposalAndProcedure> list = new ArrayList<DisposalAndProcedure>();
		Disposal disposal = null;
		Procedure procedure = null;
		if(TRoleUtils.contain(roles, RoleKey.BAMJ.toString())){ // 办案民警
			if(PropertyStatusKeyOld.ZCK.toString().equalsIgnoreCase(status)){ // 暂存库
				if(!CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
						){
					// 办案民警+一般物品+暂存库：暂存库一般财物调用流程4
					disposal = disposalMapper.get(DisposalKey.DY.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE004.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			} else if(PropertyStatusKeyOld.ZXK.toString().equalsIgnoreCase(status)){ // 中心库
				if(!CategoryAttributeKey.DZWZY.toString().equalsIgnoreCase(categoryId) // 电子物证（有存储介质）
						&& !CategoryAttributeKey.DZWZW.toString().equalsIgnoreCase(categoryId) // 电子物证（无存储介质）
						&& !CategoryAttributeKey.BBCCW.toString().equalsIgnoreCase(categoryId) // 不保存财物
						){
					// 办案民警+一般物品+中心库：中心库一般财物调用流程5
					disposal = disposalMapper.get(DisposalKey.DY.toString());
					procedure = procedureMapper.get(ProcedureKey.PROCEDURE005.toString());
					list.add(new DisposalAndProcedure(disposal, procedure));
				}
			}
		}
		return list;
	}

	public void setDisposalMapper(Map<String, Disposal> disposalMapper) {
		this.disposalMapper = disposalMapper;
	}

	public void setProcedureMapper(Map<String, Procedure> procedureMapper) {
		this.procedureMapper = procedureMapper;
	}

	public void setCategoryAttributeMapper(Map<String, CategoryAttribute> categoryAttributeMapper) {
		this.categoryAttributeMapper = categoryAttributeMapper;
	}

}

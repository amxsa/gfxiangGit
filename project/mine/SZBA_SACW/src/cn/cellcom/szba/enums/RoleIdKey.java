package cn.cellcom.szba.enums;

/**
 * 角色id枚举类，
 * @author pansenxin
 *
 */
public enum RoleIdKey {

	/**
	 * 办案民警
	 */
	BAMJ(1L),
	/**
	 * 办案单位领导           
	 */
	BADWLD(2L),
	/**
	 * 暂存库管理员
	 */
	ZCKGLY(3L),
	/**
	 * 法制科
	 */
	FZK(4L),
	/**
	 * 治安科民警
	 */
	ZAKMJ(5L),
	/**
	 * 治安科领导
	 */
	ZAKLD(6L),
	/**
	 * 分局领导
	 */
	FJLD(7L),
	/**
	 * 中心库管理员
	 */
	ZXKGLY(8L),
	/**
	 * 中心库领导
	 */
	ZXKLD(9L),
	/**
	 * 系统管理员
	 */
	XTGLY(21L);
	
	

	private Long roleId;
	private RoleIdKey(Long id){
		roleId = id;
	}
	public Long getRoleId(){
		return roleId;
	}
}

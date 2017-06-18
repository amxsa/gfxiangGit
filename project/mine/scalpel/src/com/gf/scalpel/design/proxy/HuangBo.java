/**
 * 
 */
package com.gf.scalpel.design.proxy;

/**
 * @author gfxiang
 * @time 2017年5月2日 下午5:15:08
 *	@HuangBo
 */
public class HuangBo implements Actor{

	/* 
	 * @see com.gf.scalpel.design.proxy.Actor#sing()
	 */
	@Override
	public void sing() {
		// TODO Auto-generated method stub
		System.out.println("HuangBo is singing");
	}

	/* 
	 * @see com.gf.scalpel.design.proxy.Actor#dance()
	 */
	@Override
	public void dance() {
		// TODO Auto-generated method stub
		System.out.println("HuangBo is dancing");
	}

}

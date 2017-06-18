/**
 * 
 */
package com.gf.scalpel.design.proxy;

/**
 * @author gfxiang
 * @time 2017年5月2日 下午5:05:25
 *	@Proxyer
 */
public class Proxyer implements Actor{

	private Actor actor;
	
	//默认是胡歌的代理
	public Proxyer() {
		super();
		this.actor=new Huge();
	}

	//可以是任何一个Actor的代理 只要是这个类型
	public Proxyer(Actor actor) {
		super();
		this.actor = actor;
	}
	/* 
	 * @see com.gf.scalpel.design.proxy.Actor#sing()
	 */
	@Override
	public void sing() {
		// TODO Auto-generated method stub
		actor.sing();
	}

	/* 
	 * @see com.gf.scalpel.design.proxy.Actor#dance()
	 */
	@Override
	public void dance() {
		// TODO Auto-generated method stub
		actor.dance();
	}

}

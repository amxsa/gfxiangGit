/**
 * 
 */
package com.gf.scalpel.design.proxy;

/**
 * @author gfxiang
 * @time 2017��5��2�� ����5:05:25
 *	@Proxyer
 */
public class Proxyer implements Actor{

	private Actor actor;
	
	//Ĭ���Ǻ���Ĵ���
	public Proxyer() {
		super();
		this.actor=new Huge();
	}

	//�������κ�һ��Actor�Ĵ��� ֻҪ���������
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

/**
 * 
 */
package com.gf.scalpel.design.proxy;

/**
 * @author gfxiang
 * @time 2017年5月2日 下午5:13:42
 *	@RunningMan\
 *	代理模式：
 *				主要使用java的多态属性 干活的是被代理类，接活的是代理类（代理类和被代理类实现同一个接口）
 */
public class RunningMan {
	public static void main(String[] args) {
		Proxyer proxyer=new Proxyer();
		proxyer.sing();
		proxyer.dance();
		Proxyer proxyer1=new Proxyer(new HuangBo());
		proxyer1.sing();
		proxyer1.dance();
	}
}

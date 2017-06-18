/**
 * 
 */
package com.gf.scalpel.design.strategy;

/**
 * @author gfxiang
 * @time 2017��5��2�� ����4:48:49
 *	@Context
 */
public class Context {
	private IStrategy strategy;

	public Context(IStrategy strategy) {
		super();
		this.strategy = strategy;
	}
	
	public void run(){
		this.strategy.run();
	}
	
}

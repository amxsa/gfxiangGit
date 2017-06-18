/**
 * 
 */
package com.gf.scalpel.design.strategy;

/**
 * @author gfxiang
 * @time 2017年5月2日 下午4:50:54
 *	@RunningMan
 *
 *	策略模式：
 *				好处：体现高内聚低耦合特点、符合OCP原则 可持续扩展
 */
public class RunningMan {
	public static void main(String[] args) {
		run1();
		run2();
		run3();
	}

	private static void run1() {
		// TODO Auto-generated method stub
		Context context = new Context(new StrategyOne());
		context.run();
	}
	private static void run2() {
		// TODO Auto-generated method stub
		Context context = new Context(new StrategyTwo());
		context.run();
	}
	private static void run3() {
		// TODO Auto-generated method stub
		Context context = new Context(new StrategyThree());
		context.run();
	}
}

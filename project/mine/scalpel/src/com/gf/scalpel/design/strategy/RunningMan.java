/**
 * 
 */
package com.gf.scalpel.design.strategy;

/**
 * @author gfxiang
 * @time 2017��5��2�� ����4:50:54
 *	@RunningMan
 *
 *	����ģʽ��
 *				�ô������ָ��ھ۵�����ص㡢����OCPԭ�� �ɳ�����չ
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

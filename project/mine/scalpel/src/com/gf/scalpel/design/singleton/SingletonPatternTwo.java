/**
 * 
 */
package com.gf.scalpel.design.singleton;

/**
 * @author gfxiang
 * @time 2017��5��2�� ����5:28:57
 *	@SingletonPatternTwo
 */
public class SingletonPatternTwo {
	private static final SingletonPatternTwo singletonPatternTwo=new SingletonPatternTwo();
	
	/**
	 * ���Ʋ���ֱ�Ӳ���ʵ������
	 */
	private SingletonPatternTwo() {
		
	}
	
	public static synchronized SingletonPatternTwo getSingletonPattern(){
		return singletonPatternTwo;
	}
	
	public void run(){
		System.out.println("HHHHHHH");
	}
	
}

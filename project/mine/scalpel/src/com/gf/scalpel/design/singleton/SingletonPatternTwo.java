/**
 * 
 */
package com.gf.scalpel.design.singleton;

/**
 * @author gfxiang
 * @time 2017年5月2日 下午5:28:57
 *	@SingletonPatternTwo
 */
public class SingletonPatternTwo {
	private static final SingletonPatternTwo singletonPatternTwo=new SingletonPatternTwo();
	
	/**
	 * 限制不能直接产生实例对象
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

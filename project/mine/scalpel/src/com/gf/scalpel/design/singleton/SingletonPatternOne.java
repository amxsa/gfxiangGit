/**
 * 
 */
package com.gf.scalpel.design.singleton;

/**
 * @author gfxiang
 * @time 2017年5月2日 下午5:21:19
 *	@SingletonPattern
 */
public class SingletonPatternOne {
	private static SingletonPatternOne singletonPattern=null;

	//限制不能直接产生实例
	private SingletonPatternOne() {
		
	}
	
	//此方法产生一个实例 但高并发情况下会出问题：A线程正申请内存分配时（需0.01秒），此时B线程判断singletonPattern==null 为True 此时便会产生两个实例
	public static SingletonPatternOne getSingletonPattern(){
		if (singletonPattern==null) {
			singletonPattern= new SingletonPatternOne();
		}
		return singletonPattern;
	}
	
	public void run(){
		System.out.println("hhhhhhhh");
	}
}

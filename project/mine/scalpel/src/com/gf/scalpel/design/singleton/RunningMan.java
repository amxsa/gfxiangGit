/**
 * 
 */
package com.gf.scalpel.design.singleton;

/**
 * @author gfxiang
 * @time 2017��5��2�� ����5:25:20
 *	@RunningMan
 */
public class RunningMan {
	public static void main(String[] args) {
		SingletonPatternOne.getSingletonPattern().run();
		SingletonPatternTwo.getSingletonPattern().run();
	}
}

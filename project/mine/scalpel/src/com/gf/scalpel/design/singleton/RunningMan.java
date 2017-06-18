/**
 * 
 */
package com.gf.scalpel.design.singleton;

/**
 * @author gfxiang
 * @time 2017年5月2日 下午5:25:20
 *	@RunningMan
 */
public class RunningMan {
	public static void main(String[] args) {
		SingletonPatternOne.getSingletonPattern().run();
		SingletonPatternTwo.getSingletonPattern().run();
	}
}

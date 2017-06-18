/**
 * 
 */
package com.gf.scalpel.design.singleton;

/**
 * @author gfxiang
 * @time 2017��5��2�� ����5:21:19
 *	@SingletonPattern
 */
public class SingletonPatternOne {
	private static SingletonPatternOne singletonPattern=null;

	//���Ʋ���ֱ�Ӳ���ʵ��
	private SingletonPatternOne() {
		
	}
	
	//�˷�������һ��ʵ�� ���߲�������»�����⣺A�߳��������ڴ����ʱ����0.01�룩����ʱB�߳��ж�singletonPattern==null ΪTrue ��ʱ����������ʵ��
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

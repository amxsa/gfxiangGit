/**
 * 
 */
package com.gf.scalpel.design.proxy;

/**
 * @author gfxiang
 * @time 2017��5��2�� ����5:13:42
 *	@RunningMan\
 *	����ģʽ��
 *				��Ҫʹ��java�Ķ�̬���� �ɻ���Ǳ������࣬�ӻ���Ǵ����ࣨ������ͱ�������ʵ��ͬһ���ӿڣ�
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

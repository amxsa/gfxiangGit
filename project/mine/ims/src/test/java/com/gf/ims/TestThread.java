package com.gf.ims;

import java.io.PrintWriter;
/**
 * 1：start():用于开始执行run()方法中定义的线程体
2：sleep()：调整java运行时间，指定调用线程的睡眠时间
3：jion()：用于调用线程等待本线程结束
4：yield()：暂时停止调用线程并将其放在队列末尾，等待另一轮执行，使同一优先级的其他线程有机会运行
 * @author Administrator
 *
 */
public class TestThread {
	static PrintWriter out = new PrintWriter(System.out, true);

	public static void main(String[] args) {
		FristThread frist = new FristThread();
		SecondThread second = new SecondThread();
		frist.start();
		second.start();
		try {
			out.println("waiting for first thread to finishing...");
			frist.join();
			out.println("it is a long wait!");
			out.println("waking up second thread...");
			synchronized (second) {
				second.notify();
			}
			out.println("waking for second thread to finishing ...");
			second.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		out.println("i'm ready to finish too.");
	}

}

class FristThread extends Thread {
	public void run() {
		TestThread.out.println("First thread starts running");
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TestThread.out.println("First thread finishes running");
	}
}

class SecondThread extends Thread {
	public synchronized void run() {
		TestThread.out.println("Second thread starts running");
		TestThread.out.println("Second thread suspends running");
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TestThread.out.println("Second thread run again and finishes ");

	}
}

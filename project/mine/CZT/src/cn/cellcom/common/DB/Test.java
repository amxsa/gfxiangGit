package cn.cellcom.common.DB;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Test implements Runnable {

	public static void main(String[] args) {
		int count = 10;
		
//		ExecutorService executorService = Executors.newFixedThreadPool(count);
//		long start = System.currentTimeMillis();;
//		for (int i = 0; i < count; i++)
//			executorService
//					.execute(new Test());
//
//		executorService.shutdown();
//		while (!executorService.isTerminated()) {
//			try {
//				Thread.sleep(0);
//				
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println((System.currentTimeMillis()-start));
		
		for(int i=0;i<10;i++){
			if(i==2){
				continue;
			}
			System.out.println(">>>>>>>>>."+i);
		}
	}

	public void run() {
		
		
	}

	

}

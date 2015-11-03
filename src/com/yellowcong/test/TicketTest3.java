package com.yellowcong.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketTest3 {
	
	public static void main(String [] args){
		
		//创建一个固定的线程池
		ExecutorService  pool  =  Executors.newFixedThreadPool(3);
		
		for(int i=0;i<3;i++){
		 
			//执行方法
			pool.execute(new Runnable() {
				int ticket = 100;
				public synchronized void sall(){
					if(ticket>0){
						System.out.println("线程:"+Thread.currentThread().getName()+"\t还剩下"+(--ticket)+"张票");
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(ticket>0){
						sall();
					}
				}
			});
		}
		
		//清空线程池
		pool.shutdown();
		
	}
}	